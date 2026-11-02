package mx.com.rocketnegocios.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import mx.com.rocketnegocios.entities.RnGcDocumentosSatJobTbl;
import mx.com.rocketnegocios.util.SatDocsAuto.AlmacenDocumentosSat;

/**
 * Recibe las solicitudes de Opinión de cumplimiento / Constancia de situación
 * fiscal y las agrupa en lotes por usuario:
 * <ul>
 * <li>Si el usuario ya tiene un trabajo de ese tipo PENDIENTE o EN_PROCESO, no
 * se duplica: se devuelve el existente.</li>
 * <li>Si tiene un lote PENDIENTE abierto (creado hace menos de
 * {@link #VENTANA_AGRUPACION_MS}), el trabajo se suma a ese lote.</li>
 * <li>Si no, se crea un lote nuevo que arranca a los
 * {@link #VENTANA_AGRUPACION_MS} ms en {@link EjecutorLotesDocumentosSat}.</li>
 * </ul>
 * Asi, pedir ambos documentos casi al mismo tiempo produce un solo aviso.
 *
 * Tambien limpia al arrancar los trabajos que quedaron colgados por un
 * reinicio y cada 5 minutos borra los PDF expirados.
 *
 * Sin transaccion propia: cada metodo del facade abre la suya.
 */
@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DespachadorDocumentosSat {

    private static final Logger LOG = Logger.getLogger(DespachadorDocumentosSat.class.getName());

    /** Ventana para agrupar solicitudes del mismo usuario en un solo lote. */
    public static final long VENTANA_AGRUPACION_MS = 10_000L;

    static final String MENSAJE_INTERRUMPIDO = "Interrumpido por reinicio del servidor. Solicita el documento de nuevo.";

    @Resource(lookup = "java:comp/DefaultManagedScheduledExecutorService")
    private ManagedScheduledExecutorService programador;

    @EJB
    private RnGcDocumentosSatJobTblFacade jobsFacade;

    @EJB
    private EjecutorLotesDocumentosSat ejecutor;

    /**
     * Lotes programados que aun aceptan trabajos (no han arrancado). Solo se
     * modifica bajo @Lock(WRITE).
     */
    private final Set<String> lotesAbiertos = new HashSet<>();

    @PostConstruct
    public void alIniciar() {
        try {
            int interrumpidos = jobsFacade.marcarInterrumpidos(MENSAJE_INTERRUMPIDO);
            if (interrumpidos > 0) {
                LOG.log(Level.WARNING, "Documentos SAT: {0} trabajo(s) pendientes marcados como ERROR por reinicio del servidor.", interrumpidos);
            }
        } catch (RuntimeException ex) {
            // No impedir el despliegue (p.ej. si aun no se ejecuto el script SQL).
            LOG.log(Level.SEVERE, "Documentos SAT: no se pudieron limpiar los trabajos interrumpidos.", ex);
        }
    }

    /**
     * Registra la solicitud del usuario (sin FacesContext: el id se recibe
     * explicito).
     *
     * @return el trabajo nuevo, o el que ya estaba en curso para ese tipo.
     */
    @Lock(LockType.WRITE)
    public RnGcDocumentosSatJobTbl solicitar(final Integer usuarioId, String tipo) {
        RnGcDocumentosSatJobTbl activo = jobsFacade.obtenerActivo(usuarioId, tipo);
        if (activo != null) {
            return activo;
        }
        String lote = jobsFacade.obtenerLotePendienteDesde(usuarioId,
                new Date(System.currentTimeMillis() - VENTANA_AGRUPACION_MS));
        boolean loteNuevo = lote == null || !lotesAbiertos.contains(lote);
        if (loteNuevo) {
            lote = UUID.randomUUID().toString();
        }
        RnGcDocumentosSatJobTbl job = jobsFacade.crearPendiente(usuarioId, tipo, lote);
        if (loteNuevo) {
            final String loteProgramado = lote;
            lotesAbiertos.add(loteProgramado);
            try {
                programador.schedule(new Runnable() {
                    @Override
                    public void run() {
                        ejecutor.ejecutarLote(loteProgramado, usuarioId);
                    }
                }, VENTANA_AGRUPACION_MS, TimeUnit.MILLISECONDS);
            } catch (RuntimeException ex) {
                lotesAbiertos.remove(loteProgramado);
                jobsFacade.marcarError(job.getId(), "No se pudo programar la generación. Intenta de nuevo.");
                throw ex;
            }
        }
        return job;
    }

    /**
     * Cierra el lote (ya no acepta trabajos nuevos) y devuelve los ids de sus
     * trabajos PENDIENTE en orden de solicitud.
     */
    @Lock(LockType.WRITE)
    public List<Integer> tomarLote(String lote) {
        lotesAbiertos.remove(lote);
        List<Integer> ids = new ArrayList<>();
        for (RnGcDocumentosSatJobTbl job : jobsFacade.obtenerPendientesDeLote(lote)) {
            ids.add(job.getId());
        }
        return ids;
    }

    /** Borra los PDF vencidos y marca sus trabajos como EXPIRADO. */
    @Schedule(minute = "*/5", hour = "*", persistent = false)
    @Lock(LockType.READ)
    public void depurarExpirados() {
        try {
            for (RnGcDocumentosSatJobTbl job : jobsFacade.obtenerListosExpirados(new Date())) {
                AlmacenDocumentosSat.borrar(job.getRutaArchivo());
                jobsFacade.marcarExpirado(job.getId());
            }
        } catch (RuntimeException ex) {
            LOG.log(Level.WARNING, "Documentos SAT: fallo la depuracion de documentos expirados.", ex);
        }
    }
}
