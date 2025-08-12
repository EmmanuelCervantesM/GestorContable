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
import mx.com.rocketnegocios.entities.RnGcNomTipootropagoTbl;

/**
 *
 * @author LenovoZ40
 */
@Stateless
public class RnGcNomTipootropagoTblFacade extends AbstractFacade<RnGcNomTipootropagoTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcNomTipootropagoTblFacade() {
        super(RnGcNomTipootropagoTbl.class);
    }
    
    public RnGcNomTipootropagoTbl obtenerXClave(String clave){
        RnGcNomTipootropagoTbl tipoPago = null;
        try{
            tipoPago = em.createNamedQuery("RnGcNomTipootropagoTbl.findByCveTipoOtroPago", RnGcNomTipootropagoTbl.class)
                    .setParameter("cveTipoOtroPago", clave)
                    .getSingleResult();
        }catch(NoResultException ex){
            System.out.println(ex);
        }
        return tipoPago;
    }
    
}
