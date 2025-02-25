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
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;

/**
 *
 * @author Consultor
 */
@Stateless
public class RnGcPolizaHeaderTblFacade extends AbstractFacade<RnGcPolizaHeaderTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcPolizaHeaderTblFacade() {
        super(RnGcPolizaHeaderTbl.class);
    }
    
    public RnGcPolizaHeaderTbl refreshFromDB(RnGcPolizaHeaderTbl poliza) {
        RnGcPolizaHeaderTbl polizaH = null;
        polizaH = em.merge(poliza);
        return polizaH;
    }
    
     public List<RnGcPolizaHeaderTbl> obtenerListaPolizas(RnGcUsuariosTbl usuarioId) {
        List<RnGcPolizaHeaderTbl> polizas = null;
        try {
            polizas = em.createNamedQuery("RnGcPolizaHeaderTbl.findByCreadoPor", RnGcPolizaHeaderTbl.class)
                    .setParameter("creadoPor", usuarioId.getId())
                    .getResultList();
            System.out.println("El tamaño de la lista de polizas es: " + polizas.size() + " para el usuario: " + usuarioId.getNombreCompleto());
        } catch (NoResultException ex) {
            System.out.println("No hay lista de polizas");
        }
        return polizas;
    }
    
}
