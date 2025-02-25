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
import mx.com.rocketnegocios.entities.RnGcCfdisTbl;
import mx.com.rocketnegocios.entities.RnGcFirmasTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcFirmasTblFacade extends AbstractFacade<RnGcFirmasTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcFirmasTblFacade() {
        super(RnGcFirmasTbl.class);
    }
    
    public RnGcFirmasTbl obtenerFirmasPorCfdi(RnGcCfdisTbl cfdiId) {
        RnGcFirmasTbl firmas = null;
        try {
            firmas = em.createNamedQuery("RnGcFirmasTbl.findByCfdiId", RnGcFirmasTbl.class)
                    .setParameter("cfdiId", cfdiId)
                    .getSingleResult();
        } catch(NoResultException ex) {
            System.out.println("No hay firmas para el cfdi: " + ex);
        }
        return firmas;
    }
    
}
