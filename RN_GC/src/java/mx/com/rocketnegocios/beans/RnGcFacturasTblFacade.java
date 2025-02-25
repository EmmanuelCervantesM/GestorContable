/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcFacturasTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcFacturasTblFacade extends AbstractFacade<RnGcFacturasTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcFacturasTblFacade() {
        super(RnGcFacturasTbl.class);
    }

    public RnGcFacturasTbl refreshFromDB(RnGcFacturasTbl facturaId) {
        RnGcFacturasTbl factlocal = null;
        factlocal = em.merge(facturaId);
        return factlocal;
    }

}
