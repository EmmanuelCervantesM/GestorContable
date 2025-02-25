/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcTiporelacionTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcTiporelacionTblFacade extends AbstractFacade<RnGcTiporelacionTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcTiporelacionTblFacade() {
        super(RnGcTiporelacionTbl.class);
    }
    
}
