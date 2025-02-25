/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcMonedasTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcMonedasTblFacade extends AbstractFacade<RnGcMonedasTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcMonedasTblFacade() {
        super(RnGcMonedasTbl.class);
    }
     public RnGcMonedasTbl obtenerMoneda() {
        RnGcMonedasTbl monedaMxm = null;

        try {
            monedaMxm = em.createNamedQuery("RnGcMonedasTbl.findByCMoneda", RnGcMonedasTbl.class)
                    .setParameter("cMoneda", "MXN")
                    .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("No hay Moneda MXN");
        }

        return monedaMxm;
    }
    
    public RnGcMonedasTbl obtenerMonedas(String moneda) {
        RnGcMonedasTbl monedaMxm = null;

        try {
            monedaMxm = em.createNamedQuery("RnGcMonedasTbl.findByCMoneda", RnGcMonedasTbl.class)
                    .setParameter("cMoneda", moneda)
                    .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("No hay Moneda");
        }

        return monedaMxm;
    }
}
