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
import mx.com.rocketnegocios.entities.RnGcNomOrigenrecursoTbl;

/**
 *
 * @author LenovoZ40
 */
@Stateless
public class RnGcNomOrigenrecursoTblFacade extends AbstractFacade<RnGcNomOrigenrecursoTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcNomOrigenrecursoTblFacade() {
        super(RnGcNomOrigenrecursoTbl.class);
    }
    
    public RnGcNomOrigenrecursoTbl obtenerXDescripcion(String descripcion){
        RnGcNomOrigenrecursoTbl periodicidadNomina = new RnGcNomOrigenrecursoTbl();
        try{
            periodicidadNomina = em.createNamedQuery("RnGcNomOrigenrecursoTbl.findByDescripcion", RnGcNomOrigenrecursoTbl.class)
                    .setParameter("descripcion", descripcion)
                    .getSingleResult();
        }catch(NoResultException ex){
            System.out.println("No encontro origen de pago");
        }
        return periodicidadNomina;
    }
}
