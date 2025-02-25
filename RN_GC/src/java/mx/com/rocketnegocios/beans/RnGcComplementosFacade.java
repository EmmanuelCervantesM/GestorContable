/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcComplementos;

/**
 *
 * @author EBIW1
 */
@Stateless
public class RnGcComplementosFacade extends AbstractFacade<RnGcComplementos> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcComplementosFacade() {
        super(RnGcComplementos.class);
    }
    
     public RnGcComplementos refreshFromDB(RnGcComplementos complementoId) {
        RnGcComplementos complementolocal = null;
        complementolocal = em.merge(complementoId);
        return complementolocal;
    }
    
}
