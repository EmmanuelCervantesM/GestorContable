/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcNomDeduccionesTbl;

/**
 *
 * @author LenovoZ40
 */
@Stateless
public class RnGcNomDeduccionesTblFacade extends AbstractFacade<RnGcNomDeduccionesTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcNomDeduccionesTblFacade() {
        super(RnGcNomDeduccionesTbl.class);
    }
    
    public RnGcNomDeduccionesTbl obtenerXClave(String clave){
        RnGcNomDeduccionesTbl deduccion = null;
        try{
            deduccion = em.createNamedQuery("RnGcNomDeduccionesTbl.findByCveTipoDeduccion", RnGcNomDeduccionesTbl.class)
                    .setParameter("cveTipoDeduccion", clave)
                    .getSingleResult();
        }catch(NoResultException ex){
            System.out.println(ex);
        }
        return deduccion;
    }
    
    
}
