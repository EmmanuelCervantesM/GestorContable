package mx.com.rocketnegocios.beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcAceptacionTerminosTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;

// versión en esta fecha" (requisito CTR-01, reglas de negocio #3 y #5 del documento funcional).
@Stateless
public class RnGcAceptacionTerminosTblFacade extends AbstractFacade<RnGcAceptacionTerminosTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcAceptacionTerminosTblFacade() {
        super(RnGcAceptacionTerminosTbl.class);
    }

    // Regresa el último registro de aceptación de un usuario (la fila más reciente por fecha).
    public RnGcAceptacionTerminosTbl obtenerUltimaAceptacion(RnGcUsuariosTbl usuarioId) {
        try {
            List<RnGcAceptacionTerminosTbl> lista = em.createNamedQuery("RnGcAceptacionTerminosTbl.findUltimaPorUsuario", RnGcAceptacionTerminosTbl.class)
                    .setParameter("usuarioId", usuarioId)
                    .setMaxResults(1)
                    .getResultList();
            return lista.isEmpty() ? null : lista.get(0);
        } catch (NoResultException ex) {
            return null;
        }
    }
}
