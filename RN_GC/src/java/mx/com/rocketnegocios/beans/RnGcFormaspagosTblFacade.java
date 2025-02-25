/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcFormaspagosTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcFormaspagosTblFacade extends AbstractFacade<RnGcFormaspagosTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcFormaspagosTblFacade() {
        super(RnGcFormaspagosTbl.class);
    }
    
    public RnGcFormaspagosTbl obtenerFormaPagoXClave(String claveFormaPago){
        RnGcFormaspagosTbl formaPago = null;
        try{
            formaPago = em.createNamedQuery("RnGcFormaspagosTbl.findByCFormaPago", RnGcFormaspagosTbl.class)
                    .setParameter("cFormaPago", claveFormaPago)
                    .getSingleResult();
        }catch(NoResultException ex){
            
        }
        return formaPago;
    }
}
