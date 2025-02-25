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
import mx.com.rocketnegocios.entities.RnGcCfdisLineasTbl;
import mx.com.rocketnegocios.entities.RnGcImpuestosCfdisTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcImpuestosCfdisTblFacade extends AbstractFacade<RnGcImpuestosCfdisTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcImpuestosCfdisTblFacade() {
        super(RnGcImpuestosCfdisTbl.class);
    }
    
    public RnGcImpuestosCfdisTbl refreshFromDB(RnGcImpuestosCfdisTbl impuestoCfdis) {
        RnGcImpuestosCfdisTbl impCfdis = null;
        impCfdis = em.merge(impuestoCfdis);
        System.out.println("impCfdis: " + impCfdis);
        return impCfdis;
    }
    
    public List<RnGcImpuestosCfdisTbl> listaImpuestosCfdis(RnGcCfdisLineasTbl lineaId) {
        System.out.println("lineaId: " + lineaId);
        List<RnGcImpuestosCfdisTbl> listaImpuestos = null;
        try {
            listaImpuestos = em.createNamedQuery("RnGcImpuestosCfdisTbl.findByCfdiLinea", RnGcImpuestosCfdisTbl.class)
                    .setParameter("cfdisLineasId", lineaId)
                    .getResultList();
            System.out.println("");
        } catch (NoResultException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return listaImpuestos;
    }
    
}
