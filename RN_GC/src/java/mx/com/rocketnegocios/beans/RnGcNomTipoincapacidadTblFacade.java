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
import mx.com.rocketnegocios.entities.RnGcNomTipoincapacidadTbl;

/**
 *
 * @author LenovoZ40
 */
@Stateless
public class RnGcNomTipoincapacidadTblFacade extends AbstractFacade<RnGcNomTipoincapacidadTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcNomTipoincapacidadTblFacade() {
        super(RnGcNomTipoincapacidadTbl.class);
    }
    
    
    public RnGcNomTipoincapacidadTbl obtenerXClave(String clave){
        RnGcNomTipoincapacidadTbl incapacidad = null;
        try{
            incapacidad = em.createNamedQuery("RnGcNomTipoincapacidadTbl.findByCveTipoIncapacidad", RnGcNomTipoincapacidadTbl.class)
                    .setParameter("cveTipoIncapacidad", clave)
                    .getSingleResult();
        }catch(NoResultException ex){
            System.out.println(ex);
        }
        return incapacidad;
    }
    
    public RnGcNomTipoincapacidadTbl obtenerXDescripcion(String descripcion){
        RnGcNomTipoincapacidadTbl incapacidad = null;
        try{
            incapacidad = em.createNamedQuery("RnGcNomTipoincapacidadTbl.findByDescripcion", RnGcNomTipoincapacidadTbl.class)
                    .setParameter("descripcion", descripcion)
                    .getSingleResult();
        }catch(NoResultException ex){
            System.out.println(ex);
        }
        return incapacidad;
    }
    
}
