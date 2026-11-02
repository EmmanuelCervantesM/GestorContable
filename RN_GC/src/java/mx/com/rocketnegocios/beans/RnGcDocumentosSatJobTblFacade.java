package mx.com.rocketnegocios.beans;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcDocumentosSatJobTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;

/**
 * Acceso a los trabajos en segundo plano de Opinión de cumplimiento y
 * Constancia de situación fiscal. Todas las consultas reciben el id del
 * usuario (no la entidad) para poder usarse fuera de una peticion JSF.
 *
 * Los metodos marcarXxx releen el registro dentro de su propia transaccion
 * para no pisar cambios hechos por otros hilos (aviso visto, expiracion).
 */
@Stateless
public class RnGcDocumentosSatJobTblFacade extends AbstractFacade<RnGcDocumentosSatJobTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcDocumentosSatJobTblFacade() {
        super(RnGcDocumentosSatJobTbl.class);
    }

    /** Crea un trabajo PENDIENTE y devuelve la entidad con su id asignado. */
    public RnGcDocumentosSatJobTbl crearPendiente(Integer usuarioId, String tipo, String lote) {
        Date ahora = new Date();
        RnGcDocumentosSatJobTbl job = new RnGcDocumentosSatJobTbl();
        job.setUsuariosId(em.getReference(RnGcUsuariosTbl.class, usuarioId));
        job.setTipo(tipo);
        job.setLote(lote);
        job.setEstado(RnGcDocumentosSatJobTbl.ESTADO_PENDIENTE);
        job.setFechaSolicitud(ahora);
        job.setAvisoVisto(false);
        job.setCorreoEnviado(false);
        job.setCreadoPor(usuarioId);
        job.setFechaCreacion(ahora);
        job.setUltimaActualizacionPor(usuarioId);
        job.setUltimaFechaActualizacion(ahora);
        em.persist(job);
        em.flush();
        return job;
    }

    /** Trabajo PENDIENTE o EN_PROCESO del usuario para ese tipo, o null. */
    public RnGcDocumentosSatJobTbl obtenerActivo(Integer usuarioId, String tipo) {
        List<RnGcDocumentosSatJobTbl> lista = em.createNamedQuery("RnGcDocumentosSatJobTbl.findActivosByUsuarioIdTipo", RnGcDocumentosSatJobTbl.class)
                .setParameter("usuarioId", usuarioId)
                .setParameter("tipo", tipo)
                .setMaxResults(1)
                .getResultList();
        return lista.isEmpty() ? null : lista.get(0);
    }

    public List<RnGcDocumentosSatJobTbl> obtenerActivosDeUsuario(Integer usuarioId) {
        return em.createNamedQuery("RnGcDocumentosSatJobTbl.findActivosByUsuarioId", RnGcDocumentosSatJobTbl.class)
                .setParameter("usuarioId", usuarioId)
                .getResultList();
    }

    /** Ultimo trabajo (cualquier estado) del usuario para ese tipo, o null. */
    public RnGcDocumentosSatJobTbl obtenerUltimo(Integer usuarioId, String tipo) {
        List<RnGcDocumentosSatJobTbl> lista = em.createNamedQuery("RnGcDocumentosSatJobTbl.findUltimosByUsuarioIdTipo", RnGcDocumentosSatJobTbl.class)
                .setParameter("usuarioId", usuarioId)
                .setParameter("tipo", tipo)
                .setMaxResults(1)
                .getResultList();
        return lista.isEmpty() ? null : lista.get(0);
    }

    /** Trabajos terminados (LISTO/ERROR) cuyo aviso aun no se ha mostrado. */
    public List<RnGcDocumentosSatJobTbl> obtenerAvisosDeUsuario(Integer usuarioId) {
        return em.createNamedQuery("RnGcDocumentosSatJobTbl.findAvisosByUsuarioId", RnGcDocumentosSatJobTbl.class)
                .setParameter("usuarioId", usuarioId)
                .getResultList();
    }

    /** Consulta ligera para decidir si el aviso global (poll) debe seguir activo. */
    public boolean tieneActivosOAvisos(Integer usuarioId) {
        Long total = em.createNamedQuery("RnGcDocumentosSatJobTbl.countActivosOAvisosByUsuarioId", Long.class)
                .setParameter("usuarioId", usuarioId)
                .getSingleResult();
        return total != null && total > 0;
    }

    /** Lote PENDIENTE mas reciente del usuario creado despues de {@code desde}, o null. */
    public String obtenerLotePendienteDesde(Integer usuarioId, Date desde) {
        List<RnGcDocumentosSatJobTbl> lista = em.createNamedQuery("RnGcDocumentosSatJobTbl.findLotesPendientesByUsuarioIdDesde", RnGcDocumentosSatJobTbl.class)
                .setParameter("usuarioId", usuarioId)
                .setParameter("desde", desde)
                .setMaxResults(1)
                .getResultList();
        return lista.isEmpty() ? null : lista.get(0).getLote();
    }

    public List<RnGcDocumentosSatJobTbl> obtenerPendientesDeLote(String lote) {
        return em.createNamedQuery("RnGcDocumentosSatJobTbl.findPendientesByLote", RnGcDocumentosSatJobTbl.class)
                .setParameter("lote", lote)
                .getResultList();
    }

    public List<RnGcDocumentosSatJobTbl> obtenerDeLote(String lote) {
        return em.createNamedQuery("RnGcDocumentosSatJobTbl.findByLote", RnGcDocumentosSatJobTbl.class)
                .setParameter("lote", lote)
                .getResultList();
    }

    public List<RnGcDocumentosSatJobTbl> obtenerListosExpirados(Date ahora) {
        return em.createNamedQuery("RnGcDocumentosSatJobTbl.findListosExpirados", RnGcDocumentosSatJobTbl.class)
                .setParameter("ahora", ahora)
                .getResultList();
    }

    /** @return true si el trabajo seguia PENDIENTE y paso a EN_PROCESO. */
    public boolean marcarEnProceso(Integer jobId) {
        RnGcDocumentosSatJobTbl job = em.find(RnGcDocumentosSatJobTbl.class, jobId);
        if (job == null || !RnGcDocumentosSatJobTbl.ESTADO_PENDIENTE.equals(job.getEstado())) {
            return false;
        }
        Date ahora = new Date();
        job.setEstado(RnGcDocumentosSatJobTbl.ESTADO_EN_PROCESO);
        job.setFechaInicio(ahora);
        job.setUltimaFechaActualizacion(ahora);
        return true;
    }

    public void marcarListo(Integer jobId, String rutaArchivo, Date fechaExpira) {
        RnGcDocumentosSatJobTbl job = em.find(RnGcDocumentosSatJobTbl.class, jobId);
        if (job == null) {
            return;
        }
        Date ahora = new Date();
        job.setEstado(RnGcDocumentosSatJobTbl.ESTADO_LISTO);
        job.setRutaArchivo(rutaArchivo);
        job.setMensaje(null);
        job.setFechaFin(ahora);
        job.setFechaExpira(fechaExpira);
        job.setUltimaFechaActualizacion(ahora);
    }

    public void marcarError(Integer jobId, String mensaje) {
        RnGcDocumentosSatJobTbl job = em.find(RnGcDocumentosSatJobTbl.class, jobId);
        if (job == null) {
            return;
        }
        Date ahora = new Date();
        job.setEstado(RnGcDocumentosSatJobTbl.ESTADO_ERROR);
        job.setMensaje(mensaje);
        job.setFechaFin(ahora);
        job.setUltimaFechaActualizacion(ahora);
    }

    /**
     * El PDF ya se borro. Tambien se marca el aviso como visto: un aviso de
     * "documento listo" ya no tendria sentido.
     */
    public void marcarExpirado(Integer jobId) {
        RnGcDocumentosSatJobTbl job = em.find(RnGcDocumentosSatJobTbl.class, jobId);
        if (job == null) {
            return;
        }
        job.setEstado(RnGcDocumentosSatJobTbl.ESTADO_EXPIRADO);
        job.setRutaArchivo(null);
        job.setAvisoVisto(true);
        job.setUltimaFechaActualizacion(new Date());
    }

    /** Al arrancar el servidor: nada puede seguir PENDIENTE/EN_PROCESO. */
    public int marcarInterrumpidos(String mensaje) {
        int actualizados = em.createNamedQuery("RnGcDocumentosSatJobTbl.marcarInterrumpidos")
                .setParameter("mensaje", mensaje)
                .setParameter("ahora", new Date())
                .executeUpdate();
        // El UPDATE masivo no pasa por la cache L2 de EclipseLink.
        em.getEntityManagerFactory().getCache().evict(RnGcDocumentosSatJobTbl.class);
        return actualizados;
    }

    public int marcarAvisosVistos(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }
        int actualizados = em.createNamedQuery("RnGcDocumentosSatJobTbl.marcarAvisosVistos")
                .setParameter("ids", ids)
                .executeUpdate();
        em.getEntityManagerFactory().getCache().evict(RnGcDocumentosSatJobTbl.class);
        return actualizados;
    }
}
