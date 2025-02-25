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
import mx.com.rocketnegocios.entities.RnGcExportacionTbl;

/**
 *
 * @author maxoc
 */
@Stateless
public class RnGcExportacionTblFacade extends AbstractFacade<RnGcExportacionTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcExportacionTblFacade() {
        super(RnGcExportacionTbl.class);
    }
    
    public RnGcExportacionTbl obtenerUnValor(){
        RnGcExportacionTbl lista = null;
        try {
            lista = em.createNamedQuery("RnGcExportacionTbl.findById", RnGcExportacionTbl.class)
                    .setParameter("id", 1)
                    .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("Error obtenerUnValor: " + ex.getLocalizedMessage());
        }
        return lista;
    }
}
