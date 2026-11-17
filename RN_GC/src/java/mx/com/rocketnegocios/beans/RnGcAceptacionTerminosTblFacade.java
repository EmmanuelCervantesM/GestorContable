package mx.com.rocketnegocios.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext; 
import mx.com.rocketnegocios.entities.RnGcAceptacionTerminosTbl;
//entrar a la base de datooos
@Stateless 
public class RnGcAceptacionTerminosTblFacade extends AbstractFacade<RnGcAceptacionTerminosTbl> {
    @PersistenceContext (unitName="RN_GCPU")
    private EntityManager em;

    @Override 
    protected EntityManager getEntityManager(){
        return em;
    }
    
    public RnGcAceptacionTerminosTblFacade(){
        super(RnGcAceptacionTerminosTbl.class);
    }
}
