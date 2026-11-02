package mx.com.rocketnegocios.beans;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcSolicitudesDescargaSatTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;

/**
 * CTR-06: acceso a las solicitudes de Descarga Masiva SAT.
 */
@Stateless
public class RnGcSolicitudesDescargaSatTblFacade extends AbstractFacade<RnGcSolicitudesDescargaSatTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcSolicitudesDescargaSatTblFacade() {
        super(RnGcSolicitudesDescargaSatTbl.class);
    }

    /** Solicitudes visibles del usuario: las vencidas no se muestran. */
    public List<RnGcSolicitudesDescargaSatTbl> obtenerSolicitudesDeUsuario(RnGcUsuariosTbl usuario) {
        marcarVencidas(usuario);
        return em.createNamedQuery("RnGcSolicitudesDescargaSatTbl.findByUsuarioId", RnGcSolicitudesDescargaSatTbl.class)
                .setParameter("usuariosId", usuario)
                .getResultList();
    }

    /** Solicitudes Aceptadas/En proceso que aun hay que verificar con el SAT. */
    public List<RnGcSolicitudesDescargaSatTbl> obtenerPendientesDeUsuario(RnGcUsuariosTbl usuario) {
        return em.createNamedQuery("RnGcSolicitudesDescargaSatTbl.findPendientesByUsuarioId", RnGcSolicitudesDescargaSatTbl.class)
                .setParameter("usuariosId", usuario)
                .getResultList();
    }

    /** Solicitudes en estado final cuyo aviso aun no ha visto el usuario (banner CTR-03). */
    public List<RnGcSolicitudesDescargaSatTbl> obtenerAvisosDeUsuario(RnGcUsuariosTbl usuario) {
        // Una descarga ya vencida no debe avisarse como "lista".
        marcarVencidas(usuario);
        return em.createNamedQuery("RnGcSolicitudesDescargaSatTbl.findAvisosByUsuarioId", RnGcSolicitudesDescargaSatTbl.class)
                .setParameter("usuariosId", usuario)
                .getResultList();
    }

    public int marcarAvisosVistos(RnGcUsuariosTbl usuario) {
        int actualizados = em.createNamedQuery("RnGcSolicitudesDescargaSatTbl.marcarAvisosVistos")
                .setParameter("usuariosId", usuario)
                .executeUpdate();
        // El UPDATE masivo no pasa por la cache L2 de EclipseLink.
        em.getEntityManagerFactory().getCache().evict(RnGcSolicitudesDescargaSatTbl.class);
        return actualizados;
    }

    /** true si el usuario tiene solicitudes por verificar o avisos sin ver (aviso global en templateRN). */
    public boolean tienePendientesOAvisos(Integer usuarioId) {
        Long total = em.createNamedQuery("RnGcSolicitudesDescargaSatTbl.countPendientesOAvisosByUsuarioId", Long.class)
                .setParameter("usuarioId", usuarioId)
                .getSingleResult();
        return total != null && total > 0;
    }

    /** true si el usuario tiene solicitudes Aceptadas/En proceso. */
    public boolean tienePendientes(Integer usuarioId) {
        Long total = em.createNamedQuery("RnGcSolicitudesDescargaSatTbl.countPendientesByUsuarioId", Long.class)
                .setParameter("usuarioId", usuarioId)
                .getSingleResult();
        return total != null && total > 0;
    }

    /** Marca como vistos solo los avisos ya mostrados al usuario. */
    public int marcarAvisosVistos(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }
        int actualizados = em.createNamedQuery("RnGcSolicitudesDescargaSatTbl.marcarAvisosVistosPorId")
                .setParameter("ids", ids)
                .executeUpdate();
        // El UPDATE masivo no pasa por la cache L2 de EclipseLink.
        em.getEntityManagerFactory().getCache().evict(RnGcSolicitudesDescargaSatTbl.class);
        return actualizados;
    }

    /**
     * Pasa a Vencida (y da por visto su aviso) toda solicitud Terminada cuya
     * fecha de solicitud supera {@link RnGcSolicitudesDescargaSatTbl#HORAS_VIGENCIA_PAQUETES}.
     */
    public int marcarVencidas(RnGcUsuariosTbl usuario) {
        long ahora = System.currentTimeMillis();
        int actualizados = em.createNamedQuery("RnGcSolicitudesDescargaSatTbl.marcarVencidas")
                .setParameter("usuariosId", usuario)
                .setParameter("ahora", new Date(ahora))
                .setParameter("limite", new Date(ahora - RnGcSolicitudesDescargaSatTbl.HORAS_VIGENCIA_PAQUETES * 3600_000L))
                .executeUpdate();
        if (actualizados > 0) {
            // El UPDATE masivo no pasa por la cache L2 de EclipseLink.
            em.getEntityManagerFactory().getCache().evict(RnGcSolicitudesDescargaSatTbl.class);
        }
        return actualizados;
    }
}
