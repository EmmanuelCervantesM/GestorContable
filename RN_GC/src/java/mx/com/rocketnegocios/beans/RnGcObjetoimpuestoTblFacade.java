/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcObjetoimpuestoTbl;

/**
 *
 * @author maxoc
 */
@Stateless
public class RnGcObjetoimpuestoTblFacade extends AbstractFacade<RnGcObjetoimpuestoTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcObjetoimpuestoTblFacade() {
        super(RnGcObjetoimpuestoTbl.class);
    }
    
}
