package mx.com.rocketnegocios.beans;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import mx.com.rocketnegocios.entities.RnGcCertificadosTbl;
import mx.com.rocketnegocios.entities.RnGcSolicitudesDescargaSatTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.SatDescargaMasiva.DescargaMasivaSatCliente;
import mx.com.rocketnegocios.util.SatDescargaMasiva.DescargaMasivaSatException;
import mx.com.rocketnegocios.util.SatDescargaMasiva.FielSat;
import mx.com.rocketnegocios.util.SatDescargaMasiva.ResultadoVerificacionSat;

/**
 * CTR-06: verificacion de solicitudes de Descarga Masiva pendientes (estado 1
 * o 2) contra el SAT. Se dispara en segundo plano al iniciar sesion (sin
 * FacesContext) y tambien a peticion desde la pantalla de descarga.
 *
 * Sin transaccion propia: las llamadas al SAT pueden tardar y no deben
 * retener una transaccion JTA; cada edit() del facade abre la suya.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class VerificadorDescargaSat {

    private static final Logger LOG = Logger.getLogger(VerificadorDescargaSat.class.getName());

    /** No se vuelve a consultar al SAT una solicitud verificada hace menos de esto. */
    private static final long INTERVALO_MINIMO_MS = 5 * 60 * 1000L;

    /** Evita dos verificaciones simultaneas del mismo usuario (p.ej. doble login). */
    private static final Set<Integer> USUARIOS_EN_PROCESO = Collections.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>());

    @EJB
    private RnGcUsuariosTblFacade usuariosFacade;

    @EJB
    private RnGcCertificadosTblFacade certificadosFacade;

    @EJB
    private RnGcSolicitudesDescargaSatTblFacade solicitudesFacade;

    /** Disparo desde el login: no bloquea al usuario. */
    @Asynchronous
    public void verificarPendientesAsync(Integer usuarioId) {
        try {
            verificarPendientes(usuarioId, false);
        } catch (RuntimeException ex) {
            LOG.log(Level.WARNING, "CTR-06: fallo la verificacion en segundo plano del usuario " + usuarioId, ex);
        }
    }

    /**
     * @param forzar true ignora el intervalo minimo entre verificaciones (boton
     * "Actualizar estado").
     * @return numero de solicitudes consultadas al SAT.
     */
    public int verificarPendientes(Integer usuarioId, boolean forzar) {
        if (usuarioId == null || !USUARIOS_EN_PROCESO.add(usuarioId)) {
            return 0;
        }
        try {
            RnGcUsuariosTbl usuario = usuariosFacade.obtenerUsuarioPorId(usuarioId);
            if (usuario == null) {
                return 0;
            }
            List<RnGcSolicitudesDescargaSatTbl> pendientes = solicitudesFacade.obtenerPendientesDeUsuario(usuario);
            long ahora = System.currentTimeMillis();
            if (!forzar) {
                pendientes.removeIf(s -> s.getFechaUltimaVerificacion() != null
                        && ahora - s.getFechaUltimaVerificacion().getTime() < INTERVALO_MINIMO_MS);
            }
            if (pendientes.isEmpty()) {
                return 0;
            }
            DescargaMasivaSatCliente cliente;
            try {
                cliente = crearCliente(usuario);
            } catch (DescargaMasivaSatException ex) {
                LOG.log(Level.WARNING, "CTR-06: no se pudo preparar la e.firma del usuario {0}: {1}",
                        new Object[]{usuarioId, ex.getMessage()});
                return 0;
            }
            int consultadas = 0;
            for (RnGcSolicitudesDescargaSatTbl solicitud : pendientes) {
                verificarSolicitud(cliente, solicitud, usuarioId);
                consultadas++;
            }
            return consultadas;
        } finally {
            USUARIOS_EN_PROCESO.remove(usuarioId);
        }
    }

    /**
     * Arma el cliente SAT con la FIEL vigente del usuario, validando que el
     * RFC del certificado coincida con el del usuario.
     */
    public DescargaMasivaSatCliente crearCliente(RnGcUsuariosTbl usuario) throws DescargaMasivaSatException {
        RnGcCertificadosTbl fielRegistro = certificadosFacade.obtenerFielVigente(usuario);
        if (fielRegistro == null) {
            throw new DescargaMasivaSatException("No tienes una e.firma (FIEL) activa registrada.");
        }
        FielSat fiel = new FielSat(fielRegistro.getCertificadoSelloDigital(), fielRegistro.getLlavePrivada(),
                fielRegistro.getContraseniaLlavePrivada());
        if (!fiel.isVigente()) {
            throw new DescargaMasivaSatException("Tu e.firma (FIEL) no esta vigente.");
        }
        String rfcUsuario = usuario.getRfc() == null ? "" : usuario.getRfc().trim().toUpperCase();
        if (fiel.getRfc() == null || !fiel.getRfc().equals(rfcUsuario)) {
            throw new DescargaMasivaSatException("El RFC de la e.firma (" + fiel.getRfc()
                    + ") no coincide con el RFC del usuario (" + rfcUsuario + ").");
        }
        return new DescargaMasivaSatCliente(fiel);
    }

    private void verificarSolicitud(DescargaMasivaSatCliente cliente, RnGcSolicitudesDescargaSatTbl solicitud, Integer usuarioId) {
        Date ahora = new Date();
        try {
            ResultadoVerificacionSat resultado = cliente.verificar(solicitud.getIdSolicitud());
            solicitud.setEstadoSolicitud(resultado.getEstadoSolicitud());
            solicitud.setCodEstatus(resultado.getCodEstatus());
            solicitud.setCodigoEstadoSolicitud(resultado.getCodigoEstadoSolicitud());
            solicitud.setMensaje(resultado.getMensaje());
            solicitud.setNumeroCfdis(resultado.getNumeroCfdis());
            if (!resultado.getIdsPaquetes().isEmpty()) {
                solicitud.setIdsPaquetes(String.join(",", resultado.getIdsPaquetes()));
            }
        } catch (DescargaMasivaSatException ex) {
            // El estado no cambia: se reintenta en la siguiente verificacion.
            LOG.log(Level.WARNING, "CTR-06: verificacion fallida de {0}: {1}",
                    new Object[]{solicitud.getIdSolicitud(), ex.getMessage()});
            if (ex.getCodEstatus() != null) {
                solicitud.setCodEstatus(ex.getCodEstatus());
            }
            solicitud.setMensaje(ex.getMessage());
        }
        solicitud.setFechaUltimaVerificacion(ahora);
        solicitud.setUltimaActualizacionPor(usuarioId);
        solicitud.setUltimaFechaActualizacion(ahora);
        try {
            solicitudesFacade.edit(solicitud);
        } catch (RuntimeException ex) {
            LOG.log(Level.WARNING, "CTR-06: no se pudo guardar la verificacion de " + solicitud.getIdSolicitud(), ex);
        }
    }
}
