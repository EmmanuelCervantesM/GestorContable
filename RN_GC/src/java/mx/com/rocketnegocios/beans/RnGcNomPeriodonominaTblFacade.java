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
import mx.com.rocketnegocios.entities.RnGcNomNominasTbl;
import mx.com.rocketnegocios.entities.RnGcNomPeriodonominaTbl;

/**
 *
 * @author LenovoZ40
 */
@Stateless
public class RnGcNomPeriodonominaTblFacade extends AbstractFacade<RnGcNomPeriodonominaTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcNomPeriodonominaTblFacade() {
        super(RnGcNomPeriodonominaTbl.class);
    }
    
    public RnGcNomPeriodonominaTbl refreshFromDB(RnGcNomPeriodonominaTbl periodoNomina){
        RnGcNomPeriodonominaTbl periodo = null;
        periodo = em.merge(periodoNomina);
        return periodo;
    }
    
    public RnGcNomPeriodonominaTbl obtenerXNomina(RnGcNomNominasTbl nomina){
        RnGcNomPeriodonominaTbl periodoNomina = null;
        try{
            periodoNomina = em.createNamedQuery("RnGcNomPeriodonominaTbl.findByNomina", RnGcNomPeriodonominaTbl.class)
                    .setParameter("nominaId", nomina)
                    .getSingleResult();
        }catch(NoResultException ex){
            System.err.println("No se encontraro periodo nomina");
        }
        return periodoNomina;
    }
}
