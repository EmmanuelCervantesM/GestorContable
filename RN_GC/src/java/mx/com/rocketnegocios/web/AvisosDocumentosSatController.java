package mx.com.rocketnegocios.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import mx.com.rocketnegocios.beans.RnGcDocumentosSatJobTblFacade;
import mx.com.rocketnegocios.beans.RnGcSolicitudesDescargaSatTblFacade;
import mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade;
import mx.com.rocketnegocios.beans.VerificadorDescargaSat;
import mx.com.rocketnegocios.entities.RnGcDocumentosSatJobTbl;
import mx.com.rocketnegocios.entities.RnGcSolicitudesDescargaSatTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import org.primefaces.context.RequestContext;

/**
 * Aviso global (p:poll en templateRN.xhtml) de los documentos SAT generados
 * en segundo plano. El poll solo arranca si el usuario tiene trabajos en
 * curso o avisos sin ver, y se detiene solo (callback {@code docsSatActivo})
 * cuando ya no hay nada pendiente. Muestra un solo mensaje por lote, cuando
 * todos sus trabajos terminaron.
 *
 * Tambien avisa de la Descarga Masiva SAT (CTR-06) con la sesion abierta:
 * mientras haya solicitudes pendientes dispara la verificacion con el SAT en
 * segundo plano (como maximo cada {@link #INTERVALO_VERIFICACION_DESCARGA_MS}
 * por sesion; el verificador ademas espera 5 min por solicitud) y muestra un
 * aviso por cada solicitud que llega a estado final.
 */
@Named("avisosDocumentosSatController")
@RequestScoped
public class AvisosDocumentosSatController {

    private static final Logger LOG = Logger.getLogger(AvisosDocumentosSatController.class.getName());

    private static final long INTERVALO_VERIFICACION_DESCARGA_MS = 60 * 1000L;
    private static final String SESION_ULTIMA_VERIFICACION = "ctr06UltimaVerificacionMs";

    @EJB
    private RnGcDocumentosSatJobTblFacade jobsFacade;

    @EJB
    private RnGcSolicitudesDescargaSatTblFacade solicitudesFacade;

    @EJB
    private RnGcUsuariosTblFacade usuariosFacade;

    @EJB
    private VerificadorDescargaSat verificador;

    private Boolean activo;

    /**
     * Id del usuario en sesion o null si no hay sesion iniciada (no se usa
     * UsuarioFirmado porque devuelve 1 por omision).
     */
    private Integer usuarioEnSesion() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        if (ctx == null) {
            return null;
        }
        Object valor = ctx.getExternalContext().getSessionMap().get("usuarioId");
        if (valor == null) {
            return null;
        }
        try {
            return Integer.valueOf(valor.toString());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public boolean isUsuarioFirmado() {
        return usuarioEnSesion() != null;
    }

    /** true si el poll debe arrancar al cargar la pagina. */
    public boolean isActivo() {
        if (activo == null) {
            Integer usuarioId = usuarioEnSesion();
            try {
                activo = usuarioId != null && (jobsFacade.tieneActivosOAvisos(usuarioId)
                        || solicitudesFacade.tienePendientesOAvisos(usuarioId));
            } catch (RuntimeException ex) {
                LOG.log(Level.WARNING, "Documentos SAT: no se pudo consultar el estado de los avisos.", ex);
                activo = false;
            }
        }
        return activo;
    }

    /** Listener del poll. */
    public void revisar() {
        RequestContext rc = RequestContext.getCurrentInstance();
        Integer usuarioId = usuarioEnSesion();
        if (usuarioId == null) {
            rc.addCallbackParam("docsSatActivo", false);
            return;
        }
        List<RnGcDocumentosSatJobTbl> avisos = jobsFacade.obtenerAvisosDeUsuario(usuarioId);
        List<RnGcDocumentosSatJobTbl> enCurso = jobsFacade.obtenerActivosDeUsuario(usuarioId);
        Set<String> lotesEnCurso = new HashSet<>();
        for (RnGcDocumentosSatJobTbl job : enCurso) {
            lotesEnCurso.add(job.getLote());
        }
        Map<String, List<RnGcDocumentosSatJobTbl>> porLote = new LinkedHashMap<>();
        for (RnGcDocumentosSatJobTbl job : avisos) {
            if (lotesEnCurso.contains(job.getLote())) {
                continue; // El aviso sale cuando termine todo el lote.
            }
            List<RnGcDocumentosSatJobTbl> lista = porLote.get(job.getLote());
            if (lista == null) {
                lista = new ArrayList<>();
                porLote.put(job.getLote(), lista);
            }
            lista.add(job);
        }
        List<Integer> vistos = new ArrayList<>();
        for (List<RnGcDocumentosSatJobTbl> lote : porLote.values()) {
            FacesContext.getCurrentInstance().addMessage(null, construirMensaje(lote));
            for (RnGcDocumentosSatJobTbl job : lote) {
                vistos.add(job.getId());
            }
        }
        jobsFacade.marcarAvisosVistos(vistos);
        boolean descargaPendiente = revisarDescargaMasiva(usuarioId, rc);
        rc.addCallbackParam("docsSatFinalizado", !porLote.isEmpty());
        rc.addCallbackParam("docsSatActivo", !enCurso.isEmpty() || avisos.size() > vistos.size() || descargaPendiente);
    }

    /**
     * CTR-06: verifica en segundo plano las solicitudes pendientes y avisa de
     * las que ya terminaron.
     *
     * @return true si aun quedan solicitudes por verificar (el poll sigue).
     */
    private boolean revisarDescargaMasiva(Integer usuarioId, RequestContext rc) {
        try {
            boolean pendientes = solicitudesFacade.tienePendientes(usuarioId);
            if (pendientes) {
                lanzarVerificacionSiToca(usuarioId);
            }
            List<Integer> vistos = new ArrayList<>();
            RnGcUsuariosTbl usuario = usuariosFacade.obtenerUsuarioPorId(usuarioId);
            if (usuario != null) {
                for (RnGcSolicitudesDescargaSatTbl solicitud : solicitudesFacade.obtenerAvisosDeUsuario(usuario)) {
                    boolean terminada = solicitud.getEstadoSolicitud() != null
                            && solicitud.getEstadoSolicitud() == RnGcSolicitudesDescargaSatTbl.ESTADO_TERMINADA;
                    String detalle = RnGcSolicitudesDescargaSatTblController.textoAviso(solicitud)
                            + (terminada ? " Descárgala en Descarga Masiva SAT." : "");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            terminada ? FacesMessage.SEVERITY_INFO : FacesMessage.SEVERITY_WARN,
                            "Descarga masiva SAT", detalle));
                    vistos.add(solicitud.getId());
                }
            }
            solicitudesFacade.marcarAvisosVistos(vistos);
            rc.addCallbackParam("descargaSatFinalizado", !vistos.isEmpty());
            return pendientes;
        } catch (RuntimeException ex) {
            LOG.log(Level.WARNING, "Descarga masiva SAT: no se pudo revisar el estado de las solicitudes.", ex);
            return false;
        }
    }

    /** Dispara la verificacion asincrona como maximo una vez por intervalo y sesion. */
    private void lanzarVerificacionSiToca(Integer usuarioId) {
        Map<String, Object> sesion = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        long ahora = System.currentTimeMillis();
        Object ultima = sesion.get(SESION_ULTIMA_VERIFICACION);
        if (ultima instanceof Long && ahora - (Long) ultima < INTERVALO_VERIFICACION_DESCARGA_MS) {
            return;
        }
        sesion.put(SESION_ULTIMA_VERIFICACION, ahora);
        verificador.verificarPendientesAsync(usuarioId);
    }

    private FacesMessage construirMensaje(List<RnGcDocumentosSatJobTbl> lote) {
        List<String> listos = new ArrayList<>();
        List<String> errores = new ArrayList<>();
        Date expiraPrimero = null;
        for (RnGcDocumentosSatJobTbl job : lote) {
            if (job.isListo()) {
                listos.add("tu " + job.getNombreDocumento());
                if (job.getFechaExpira() != null
                        && (expiraPrimero == null || job.getFechaExpira().before(expiraPrimero))) {
                    expiraPrimero = job.getFechaExpira();
                }
            } else {
                String detalle = job.getMensaje() == null ? "" : " " + job.getMensaje();
                errores.add("No se pudo generar tu " + job.getNombreDocumento() + "." + detalle);
            }
        }
        StringBuilder texto = new StringBuilder();
        if (!listos.isEmpty()) {
            boolean varios = listos.size() > 1;
            String unidos = String.join(" y ", listos);
            texto.append(Character.toUpperCase(unidos.charAt(0))).append(unidos.substring(1))
                    .append(varios ? " están listas." : " está lista.")
                    .append(varios ? " Descárgalas" : " Descárgala")
                    .append(" en Documentos fiscales");
            if (expiraPrimero != null) {
                texto.append(" antes de las ").append(new SimpleDateFormat("HH:mm").format(expiraPrimero));
            }
            texto.append('.');
        }
        for (String error : errores) {
            if (texto.length() > 0) {
                texto.append(' ');
            }
            texto.append(error);
        }
        FacesMessage.Severity severidad = errores.isEmpty() ? FacesMessage.SEVERITY_INFO
                : (listos.isEmpty() ? FacesMessage.SEVERITY_ERROR : FacesMessage.SEVERITY_WARN);
        return new FacesMessage(severidad, "Documentos SAT", texto.toString());
    }
}
