package mx.com.rocketnegocios.beans;

// ver el historial de los cambios en las verisones 
import java.util.List; 
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
    public RnGcTerminosCondicionesTbl obtenerPorVersion(String version){
        RnGcTerminosCondicionesTbl resultado = null; 
            try {
                resultado = em.createNamedQuery("RnGcTerminosCondicionesTbl.findByVersion", RnGcTerminosCondicionesTbl.class).setParameter("version", version).getSingleResult();
            } catch (NoResultException ex){
                // no existe esta version 
            }
            return resultado; 
        }
        public List<RnGcTerminosCondicionesTbl> obtenerHistorial(){
            return em.createNamedQuery("RnGcTerminosCondicionesTbl.findAllOrdenado", RnGcTerminosCondicionesTbl.class).getResultList();
        }
}
