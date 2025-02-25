/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcCpPartetransportesatTbl;

/**
 *
 * @author maxoc
 */
@Stateless
public class RnGcCpPartetransportesatTblFacade extends AbstractFacade<RnGcCpPartetransportesatTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcCpPartetransportesatTblFacade() {
        super(RnGcCpPartetransportesatTbl.class);
    }
    
}
