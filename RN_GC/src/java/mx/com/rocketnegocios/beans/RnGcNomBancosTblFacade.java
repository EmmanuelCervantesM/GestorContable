/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcNomBancosTbl;

/**
 *
 * @author LenovoZ40
 */
@Stateless
public class RnGcNomBancosTblFacade extends AbstractFacade<RnGcNomBancosTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcNomBancosTblFacade() {
        super(RnGcNomBancosTbl.class);
    }
    
    public RnGcNomBancosTbl refreshFromDB(RnGcNomBancosTbl banco){
        RnGcNomBancosTbl bancoLocal = null;
        bancoLocal = em.merge(banco);
        return bancoLocal;
    }
    
}
