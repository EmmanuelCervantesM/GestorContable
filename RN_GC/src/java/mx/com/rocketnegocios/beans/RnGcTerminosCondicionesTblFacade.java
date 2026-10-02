package mx.com.rocketnegocios.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcTerminosCondicionesTbl;

@Stateless
public class RnGcTerminosCondicionesTblFacade extends AbstractFacade<RnGcTerminosCondicionesTbl> {
    
    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcTerminosCondicionesTblFacade() {
        super(RnGcTerminosCondicionesTbl.class);
    }
    
    public RnGcTerminosCondicionesTbl obtenerVigente(){
        RnGcTerminosCondicionesTbl lista = null;
        try {
            lista = em.createNamedQuery("RnGcTerminosCondicionesTbl.findVigente", RnGcTerminosCondicionesTbl.class)
                    .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("Error obtenerVigente: " + ex.getLocalizedMessage());
        }
        return lista;
    }
    public void marcarNoVigentes(){
        try {
            em.createQuery("UPDATE RnGcTerminosCondicionesTbl r SET r.vigente = 'N' WHERE r.vigente = 'S'").executeUpdate();
        } catch (NoResultException ex) {
            System.out.println("Error marcarNoVigentes: " + ex.getLocalizedMessage());
        }
    }
    
}
