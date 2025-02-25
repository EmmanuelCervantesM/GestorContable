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
import mx.com.rocketnegocios.entities.RnGcTransaccionesTbl;
import mx.com.rocketnegocios.entities.RnGcTrxlineasTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcTrxlineasTblFacade extends AbstractFacade<RnGcTrxlineasTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcTrxlineasTblFacade() {
        super(RnGcTrxlineasTbl.class);
    }    

    public RnGcTrxlineasTbl refreshFromDB(RnGcTrxlineasTbl transaccionLineaId){
        RnGcTrxlineasTbl trxlocal = null;
        trxlocal = em.merge(transaccionLineaId);
        return trxlocal;
    }
    
    public List<RnGcTrxlineasTbl> obtenerTrxLineas(RnGcTransaccionesTbl transaccionId){
        List<RnGcTrxlineasTbl> listaTrxLineas = null;
        try {
            listaTrxLineas = em.createNamedQuery("RnGcTrxlineasTbl.findByTransaccionesId", RnGcTrxlineasTbl.class)
                    .setParameter("transaccionesId", transaccionId)
                    .getResultList();            
        } catch (NoResultException ex) {
            System.out.println("No se encontro TrxLineas con transaccionId");
        }
        return listaTrxLineas;
    }
    
}
