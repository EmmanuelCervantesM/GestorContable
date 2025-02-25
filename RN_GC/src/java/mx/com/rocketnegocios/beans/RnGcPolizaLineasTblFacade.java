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
import mx.com.rocketnegocios.entities.RnGcPolizaHeaderTbl;
import mx.com.rocketnegocios.entities.RnGcPolizaLineasTbl;

/**
 *
 * @author Consultor
 */
@Stateless
public class RnGcPolizaLineasTblFacade extends AbstractFacade<RnGcPolizaLineasTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcPolizaLineasTblFacade() {
        super(RnGcPolizaLineasTbl.class);
    }
    
    public RnGcPolizaLineasTbl refreshFromDB(RnGcPolizaLineasTbl poliza) {
        RnGcPolizaLineasTbl polizaL = null;
        polizaL = em.merge(poliza);
        return polizaL;
    }
    
     public List<RnGcPolizaLineasTbl> obtenerPolizaLineas(RnGcPolizaHeaderTbl polizaHeaderId) {
        List<RnGcPolizaLineasTbl> listaPolizaLineas = null;
        try {
            listaPolizaLineas = em.createNamedQuery("RnGcPolizaLineasTbl.findByPolizaHeaderId", RnGcPolizaLineasTbl.class)
                    .setParameter("polizaHeaderId", polizaHeaderId)
                    .getResultList();
        }catch (NoResultException ex){
            System.out.println("No encontro lista de poliza lineas con polizaHeaderId"+polizaHeaderId.getId());
        }
        return listaPolizaLineas;        
    }
    
}
