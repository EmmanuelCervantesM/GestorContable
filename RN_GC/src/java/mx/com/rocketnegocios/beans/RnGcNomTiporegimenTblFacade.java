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
import mx.com.rocketnegocios.entities.RnGcNomTiporegimenTbl;

/**
 *
 * @author LenovoZ40
 */
@Stateless
public class RnGcNomTiporegimenTblFacade extends AbstractFacade<RnGcNomTiporegimenTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcNomTiporegimenTblFacade() {
        super(RnGcNomTiporegimenTbl.class);
    }
    
     public RnGcNomTiporegimenTbl obternerRegimenByDescripcion(String descripcion){
        RnGcNomTiporegimenTbl regimenFiscal = null;
        try {
            regimenFiscal = em.createNamedQuery("RnGcNomTiporegimenTbl.findByDescripcion", RnGcNomTiporegimenTbl.class)
                    .setParameter("descripcion", descripcion)
                    .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("Error obtenerUnValor: " + ex.getLocalizedMessage());
        }
        return regimenFiscal;
    }
    
}
