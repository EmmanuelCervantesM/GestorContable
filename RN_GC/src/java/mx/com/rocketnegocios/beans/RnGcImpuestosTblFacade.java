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
import javax.ws.rs.NotFoundException;
import mx.com.rocketnegocios.entities.RnGcImpuestosTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcImpuestosTblFacade extends AbstractFacade<RnGcImpuestosTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcImpuestosTblFacade() {
        super(RnGcImpuestosTbl.class);
    }
    
}
