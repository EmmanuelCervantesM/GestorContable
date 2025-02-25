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
import mx.com.rocketnegocios.entities.RnGcFacturasTbl;
import mx.com.rocketnegocios.entities.RnGcFacturaslineasTbl;
import mx.com.rocketnegocios.entities.RnGcTrxlineasTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcFacturaslineasTblFacade extends AbstractFacade<RnGcFacturaslineasTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcFacturaslineasTblFacade() {
        super(RnGcFacturaslineasTbl.class);
    }

    public List<RnGcFacturaslineasTbl> obtenerFactLineas(RnGcFacturasTbl facturaId) {
        List<RnGcFacturaslineasTbl> listaFactLineas = null;
        try {
            listaFactLineas = em.createNamedQuery("RnGcFacturaslineasTbl.findByRngcfacturasId", RnGcFacturaslineasTbl.class)
                    .setParameter("rngcfacturasId", facturaId)
                    .getResultList();            
        } catch (NoResultException ex) {
            System.out.println("No se encontro FactLineas con facturaId");
        }
        return listaFactLineas;
    }
    
}
