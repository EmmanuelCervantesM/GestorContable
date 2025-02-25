/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcMenusTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcMenusTblFacade extends AbstractFacade<RnGcMenusTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcMenusTblFacade() {
        super(RnGcMenusTbl.class);
    }
    
}
