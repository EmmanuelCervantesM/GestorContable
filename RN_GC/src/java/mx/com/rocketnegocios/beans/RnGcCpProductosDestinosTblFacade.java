/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcCpClavetransportesatTbl;
import mx.com.rocketnegocios.entities.RnGcCpProductosDestinosTbl;

/**
 *
 * @author maxoc
 */
@Stateless
public class RnGcCpProductosDestinosTblFacade extends AbstractFacade<RnGcCpProductosDestinosTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcCpProductosDestinosTblFacade() {
        super(RnGcCpProductosDestinosTbl.class);
    }
    
    public RnGcCpProductosDestinosTbl refreshFromDB(RnGcCpProductosDestinosTbl prodDestino) {
        RnGcCpProductosDestinosTbl productoDestino = null;
        productoDestino = em.merge(prodDestino);
        return prodDestino;
    }
}
