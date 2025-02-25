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
import mx.com.rocketnegocios.entities.RnGcCfdisTbl;
import mx.com.rocketnegocios.entities.RnGcPagosRelacionadosTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcPagosRelacionadosTblFacade extends AbstractFacade<RnGcPagosRelacionadosTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcPagosRelacionadosTblFacade() {
        super(RnGcPagosRelacionadosTbl.class);
    }
    
    public List<RnGcPagosRelacionadosTbl> obtenerPagosRelacionado(RnGcCfdisTbl cfdisId) {
        List<RnGcPagosRelacionadosTbl> listaPagosRelacionados = null;
        try {
            listaPagosRelacionados = em.createNamedQuery("RnGcPagosRelacionadosTbl.findByCfdisId", RnGcPagosRelacionadosTbl.class)
                    .setParameter("cfdisId", cfdisId)
                    .getResultList();
        } catch(NoResultException ex) {
            System.out.println("No hay pagos relacionados: " + ex.getMessage());
        }
        return listaPagosRelacionados;
    }
    
}
