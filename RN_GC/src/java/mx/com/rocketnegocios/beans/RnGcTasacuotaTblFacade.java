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
import mx.com.rocketnegocios.entities.RnGcTasacuotaTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcTasacuotaTblFacade extends AbstractFacade<RnGcTasacuotaTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcTasacuotaTblFacade() {
        super(RnGcTasacuotaTbl.class);
    }
    
    public List<RnGcTasacuotaTbl> tasaPorImpuesto(String Strimpuesto) {
        List<RnGcTasacuotaTbl> listaTasas = null;
        
        try{
            listaTasas = em.createNamedQuery("RnGcTasacuotaTbl.findByImpuesto", RnGcTasacuotaTbl.class)
                    .setParameter("impuesto", Strimpuesto)
                    .getResultList();
            System.out.println("TipoTasas: " + listaTasas);
        } catch(NoResultException ex){
            System.out.println("No hay Tasas con ese tipo de impuesto");
        }
        
        return listaTasas;
        
    }
    
    public List<RnGcTasacuotaTbl> listaTasas(String traslado, String retencion, String factor, String impuesto) {
        System.out.println("listaTasas: " + traslado + " | " + retencion + " | " + factor + " | " + impuesto);
        List<RnGcTasacuotaTbl> listaTasas = null;
        try {
            listaTasas = em.createNamedQuery("RnGcTasaCuotaTbl.findByTipoFactorImpuesto", RnGcTasacuotaTbl.class)
                    .setParameter("traslado", traslado)
                    .setParameter("retencion", retencion)
                    .setParameter("factor", factor)
                    .setParameter("impuesto", impuesto)
                    .getResultList();
            System.out.println("listaTasas2: " + listaTasas);
        } catch (NoResultException ex) {
            System.out.println("No hay datos: " + ex.getMessage());
        }
        return listaTasas;
    }
    
}
