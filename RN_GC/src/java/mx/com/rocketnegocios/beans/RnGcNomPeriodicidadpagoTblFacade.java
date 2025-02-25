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
import mx.com.rocketnegocios.entities.RnGcNomPeriodicidadpagoTbl;

/**
 *
 * @author LenovoZ40
 */
@Stateless
public class RnGcNomPeriodicidadpagoTblFacade extends AbstractFacade<RnGcNomPeriodicidadpagoTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcNomPeriodicidadpagoTblFacade() {
        super(RnGcNomPeriodicidadpagoTbl.class);
    }
    
    public RnGcNomPeriodicidadpagoTbl refreshFromDB(RnGcNomPeriodicidadpagoTbl periodicidadPago){
        RnGcNomPeriodicidadpagoTbl periodicidad = null;
        periodicidad = em.merge(periodicidadPago);
        return periodicidad;
    }
    
    public RnGcNomPeriodicidadpagoTbl obtenerXDescr(String descripcion){
        RnGcNomPeriodicidadpagoTbl periodicidadNomina = new RnGcNomPeriodicidadpagoTbl();
        try{
            periodicidadNomina = em.createNamedQuery("RnGcNomPeriodicidadpagoTbl.findByDescripcion", RnGcNomPeriodicidadpagoTbl.class)
                    .setParameter("descripcion", descripcion)
                    .getSingleResult();
        }catch(NoResultException ex){
            System.out.println("No encontro periodicidad Pago");
        }
        return periodicidadNomina;
    }
    
}