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
import mx.com.rocketnegocios.entities.RnGcNomRiesgopuestoTbl;

/**
 *
 * @author LenovoZ40
 */
@Stateless
public class RnGcNomRiesgopuestoTblFacade extends AbstractFacade<RnGcNomRiesgopuestoTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcNomRiesgopuestoTblFacade() {
        super(RnGcNomRiesgopuestoTbl.class);
    }
    

    public RnGcNomRiesgopuestoTbl obtenerByDescripcion(String descripcion){
        RnGcNomRiesgopuestoTbl riesgoPuesto = null;
        try{
            riesgoPuesto = em.createNamedQuery("RnGcNomRiesgopuestoTbl.findByDescripcion", RnGcNomRiesgopuestoTbl.class)
                    .setParameter("descripcion", descripcion)
                    .getSingleResult();
        }catch(NoResultException ex){
            System.out.println("No se encontraron obtenerByDescripcion");
        }
        return riesgoPuesto;
    }
}
