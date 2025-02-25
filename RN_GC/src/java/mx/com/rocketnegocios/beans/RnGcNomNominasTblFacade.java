/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcNomNominasTbl;

/**
 *
 * @author LenovoZ40
 */
@Stateless
public class RnGcNomNominasTblFacade extends AbstractFacade<RnGcNomNominasTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcNomNominasTblFacade() {
        super(RnGcNomNominasTbl.class);
    }
    
    public List<RnGcNomNominasTbl> obtenerNominaPorId(Integer nominaId){
        List<RnGcNomNominasTbl> nomina = new ArrayList<>();
        System.out.println("nominaID: " + nominaId);
        try{
            nomina = em.createNamedQuery("RnGcNomNominasTbl.findById", RnGcNomNominasTbl.class)
                    .setParameter("id", nominaId)
                    .getResultList();
        }catch(NoResultException ex){
            System.out.println("No se encontraron nominas");
        }
        return nomina;
    }
    
    public RnGcNomNominasTbl obtenerNominaPorIdUnico(Integer nominaId){
        RnGcNomNominasTbl nomina = null;
        System.out.println("nominaID: " + nominaId);
        try{
            nomina = em.createNamedQuery("RnGcNomNominasTbl.findById", RnGcNomNominasTbl.class)
                    .setParameter("id", nominaId)
                    .getSingleResult();
        }catch(NoResultException ex){
            System.out.println("No se encontraron nominas");
        }
        return nomina;
    }
    
    public List<RnGcNomNominasTbl> obtenerNominaCreadoPor(Integer creadoPor){
        List<RnGcNomNominasTbl> listaNominas = new ArrayList<>();
        try{
            listaNominas = em.createNamedQuery("RnGcNomNominasTbl.findByCreadoPor", RnGcNomNominasTbl.class)
                    .setParameter("creadoPor", creadoPor)
                    .getResultList();
        }catch(NoResultException ex){
            System.out.println("No se encontraron nominas");
        }
        return listaNominas;
    }
    
    public RnGcNomNominasTbl refreshFromDB(RnGcNomNominasTbl nominaId){
        RnGcNomNominasTbl nomina = null;
        nomina = em.merge(nominaId);
        return nomina;
    }
}
