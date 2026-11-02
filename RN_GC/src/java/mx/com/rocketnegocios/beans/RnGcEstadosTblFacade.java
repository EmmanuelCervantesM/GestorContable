/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcEstadosTbl;

/**
 *
 * @author mmiva
 */
@Stateless
public class RnGcEstadosTblFacade extends AbstractFacade<RnGcEstadosTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcEstadosTblFacade() {
        super(RnGcEstadosTbl.class);
    }

    public List<RnGcEstadosTbl> findByCPais(String cPais) {
        return em.createNamedQuery("RnGcEstadosTbl.findByCPais", RnGcEstadosTbl.class)
                .setParameter("cPais", cPais)
                .getResultList();
    }

}
