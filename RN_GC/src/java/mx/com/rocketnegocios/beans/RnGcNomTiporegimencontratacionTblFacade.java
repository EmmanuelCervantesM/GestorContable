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
import mx.com.rocketnegocios.entities.RnGcNomTiporegimencontratacionTbl;

/**
 *
 * @author Joaquin
 */
@Stateless
public class RnGcNomTiporegimencontratacionTblFacade extends AbstractFacade<RnGcNomTiporegimencontratacionTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcNomTiporegimencontratacionTblFacade() {
        super(RnGcNomTiporegimencontratacionTbl.class);
    }
    
     public RnGcNomTiporegimencontratacionTbl obternerRegimenContratacionByDescripcion(String descripcion){
        RnGcNomTiporegimencontratacionTbl regimenContratacion = null;
        try {
            regimenContratacion = em.createNamedQuery("RnGcNomTiporegimencontratacionTbl.findByDescripcion", RnGcNomTiporegimencontratacionTbl.class)
                    .setParameter("descripcion", descripcion)
                    .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("Error obtenerUnValor: " + ex.getLocalizedMessage());
        }
        return regimenContratacion;
    }
}
