package mx.com.rocketnegocios.beans;

import java.net.SocketTimeoutException;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import mx.com.rocketnegocios.entities.RnGcCertificadosTbl;
import mx.com.rocketnegocios.entities.RnGcDocumentosSatJobTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.SatDocsAuto.AlmacenDocumentosSat;
import mx.com.rocketnegocios.util.SatDocsAuto.GeneradorDocumentosSat;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;

/**
 * Ejecuta en segundo plano los trabajos de un lote (uno tras otro) con el
 * mismo flujo Selenium que antes corria dentro de la peticion JSF. Como cada
 * corrida abre un Chrome headless de 2-4 minutos, se limita a
 * {@link #MAX_SESIONES_CHROME} sesiones simultaneas en todo el servidor.
 *
 * Sin transaccion propia (igual que VerificadorDescargaSat): cada
 * actualizacion de estado se guarda en su propia transaccion del facade.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class EjecutorLotesDocumentosSat {

    private static final Logger LOG = Logger.getLogger(EjecutorLotesDocumentosSat.class.getName());

    /** Sesiones de Chrome headless simultaneas permitidas en el servidor. */
    public static final int MAX_SESIONES_CHROME = 2;

    private static final Semaphore SESIONES_CHROME = new Semaphore(MAX_SESIONES_CHROME, true);

    /** Mensaje cuando no se reconoce la causa del error. */
    static final String MENSAJE_ERROR_GENERICO = "Ocurrió un problema en el portal del SAT. Verifica que tu FIEL esté vigente e intenta de nuevo más tarde.";

    /** Mensaje cuando el portal del SAT no responde a tiempo (no es culpa de la FIEL). */
    static final String MENSAJE_SAT_SIN_RESPUESTA = "El portal del SAT no respondió a tiempo. El problema es del SAT, no de tu FIEL; intenta de nuevo más tarde.";

    /** Largo maximo del texto de una alerta del SAT que se guarda (la columna mensaje es de 500). */
    private static final int MAX_TEXTO_ALERTA = 300;

    @EJB
    private DespachadorDocumentosSat despachador;

    @EJB
    private RnGcDocumentosSatJobTblFacade jobsFacade;

    @EJB
    private RnGcUsuariosTblFacade usuariosFacade;

    @EJB
    private RnGcCertificadosTblFacade certificadosFacade;

    @EJB
    private NotificadorCorreoDocumentosSat notificadorCorreo;

    @Asynchronous
    public void ejecutarLote(String lote, Integer usuarioId) {
        try {
            List<Integer> ids = despachador.tomarLote(lote);
            if (ids.isEmpty()) {
                return;
            }
            for (Integer jobId : ids) {
                ejecutarTrabajo(jobId, usuarioId);
            }
            try {
                notificadorCorreo.notificarLoteTerminado(usuarioId, jobsFacade.obtenerDeLote(lote));
            } catch (RuntimeException ex) {
                LOG.log(Level.WARNING, "Documentos SAT: fallo el aviso por correo del lote " + lote, ex);
            }
        } catch (RuntimeException ex) {
            LOG.log(Level.SEVERE, "Documentos SAT: fallo la ejecucion del lote " + lote, ex);
        }
    }

    private void ejecutarTrabajo(Integer jobId, Integer usuarioId) {
        RnGcDocumentosSatJobTbl job = jobsFacade.find(jobId);
        if (job == null) {
            return;
        }
        String tipo = job.getTipo();
        String nombre = job.getNombreDocumento();
        boolean adquirido = false;
        boolean terminado = false;
        String mensajeError = MENSAJE_ERROR_GENERICO;
        Path destino = null;
        try {
            SESIONES_CHROME.acquire();
            adquirido = true;
            if (!jobsFacade.marcarEnProceso(jobId)) {
                // Ya no estaba PENDIENTE (p.ej. marcado como interrumpido).
                terminado = true;
                return;
            }
            RnGcCertificadosTbl fiel = obtenerFielVigente(usuarioId);
            if (fiel == null) {
                jobsFacade.marcarError(jobId, "No tienes una e.firma (FIEL) activa y vigente registrada. Cárgala en Certificados e intenta de nuevo.");
                terminado = true;
                return;
            }
            destino = AlmacenDocumentosSat.rutaPara(usuarioId, tipo, jobId);
            GeneradorDocumentosSat.generar(tipo, fiel.getCertificadoSelloDigital(), fiel.getLlavePrivada(),
                    fiel.getContraseniaLlavePrivada(), destino);
            Date expira = new Date(System.currentTimeMillis() + AlmacenDocumentosSat.ttlMinutos() * 60_000L);
            jobsFacade.marcarListo(jobId, destino.toString(), expira);
            terminado = true;
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            LOG.log(Level.WARNING, "Documentos SAT: trabajo " + jobId + " interrumpido", ex);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Documentos SAT: error al generar " + nombre + " (trabajo " + jobId
                    + ", usuario " + usuarioId + ")", ex);
            mensajeError = mensajeParaUsuario(ex);
        } finally {
            if (adquirido) {
                SESIONES_CHROME.release();
            }
            if (!terminado) {
                if (destino != null) {
                    AlmacenDocumentosSat.borrar(destino.toString());
                }
                // El aviso antepone "No se pudo generar tu <documento>.".
                marcarErrorSeguro(jobId, mensajeError);
            }
        }
    }

    /**
     * Traduce la falla a un mensaje para el usuario. Si el portal del SAT
     * mostro una alerta propia (p. ej. un servicio suyo caido) se muestra ese
     * texto, para no culpar a la FIEL de un problema del SAT.
     */
    static String mensajeParaUsuario(Throwable error) {
        for (Throwable causa = error; causa != null; causa = causa.getCause()) {
            if (causa instanceof UnhandledAlertException) {
                String alerta = ((UnhandledAlertException) causa).getAlertText();
                if (alerta != null && !alerta.trim().isEmpty()) {
                    String texto = alerta.trim().replaceAll("\\s+", " ");
                    if (texto.length() > MAX_TEXTO_ALERTA) {
                        texto = texto.substring(0, MAX_TEXTO_ALERTA) + "…";
                    }
                    return "Mensaje del SAT: «" + texto + "». El error viene del portal del SAT, no de tu FIEL; intenta de nuevo más tarde.";
                }
            }
            // SocketTimeoutException: el navegador quedo esperando al SAT mas que el
            // cliente HTTP de Selenium (llega envuelto en UnreachableBrowserException).
            if (causa instanceof TimeoutException || causa instanceof SocketTimeoutException) {
                return MENSAJE_SAT_SIN_RESPUESTA;
            }
            if (causa.getCause() == causa) {
                break;
            }
        }
        return MENSAJE_ERROR_GENERICO;
    }

    /** FIEL vigente del usuario por id (sin FacesContext). */
    private RnGcCertificadosTbl obtenerFielVigente(Integer usuarioId) {
        RnGcUsuariosTbl usuario = usuariosFacade.find(usuarioId);
        if (usuario == null) {
            return null;
        }
        return certificadosFacade.obtenerFielVigente(usuario);
    }

    private void marcarErrorSeguro(Integer jobId, String mensaje) {
        try {
            jobsFacade.marcarError(jobId, mensaje);
        } catch (RuntimeException ex) {
            LOG.log(Level.SEVERE, "Documentos SAT: no se pudo marcar como ERROR el trabajo " + jobId, ex);
        }
    }
}
