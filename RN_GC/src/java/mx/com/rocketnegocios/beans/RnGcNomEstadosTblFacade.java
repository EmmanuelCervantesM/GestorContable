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
import mx.com.rocketnegocios.entities.RnGcNomEstadosTbl;

/**
 *
 * @author LenovoZ40
 */
@Stateless
public class RnGcNomEstadosTblFacade extends AbstractFacade<RnGcNomEstadosTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcNomEstadosTblFacade() {
        super(RnGcNomEstadosTbl.class);
    }
    
    public RnGcNomEstadosTbl obtenerXClave(String claveEstado){
        RnGcNomEstadosTbl estado = null;
        try{
            estado = em.createNamedQuery("RnGcNomEstadosTbl.findByCveEstado", RnGcNomEstadosTbl.class)
                    .setParameter("cveEstado", claveEstado)
                    .getSingleResult();
        }catch(NoResultException ex){
            System.out.println("No se encontro estadoXClave");
        }
        return estado;
    }
    
    public RnGcNomEstadosTbl obtenerXId(Integer estadoId){
        RnGcNomEstadosTbl estado = null;
        try{
            estado = em.createNamedQuery("RnGcNomEstadosTbl.findById", RnGcNomEstadosTbl.class)
                    .setParameter("id", estadoId)
                    .getSingleResult();
        }catch(NoResultException ex){
            System.out.println("No se encontro estadoXId");
        }
        return estado;
    }
    
    public RnGcNomEstadosTbl refreshFromDB(RnGcNomEstadosTbl estado){
        RnGcNomEstadosTbl estadoLocal = null;
        estadoLocal = em.merge(estado);
        return estadoLocal;
    }    
    
    public RnGcNomEstadosTbl obtenerByNombre(String nombre){
        RnGcNomEstadosTbl estado = null;
        try{
            estado = em.createNamedQuery("RnGcNomEstadosTbl.findByNombre", RnGcNomEstadosTbl.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        }catch(NoResultException ex){
            System.out.println("No se encontro obtenerByNombre");
        }
        return estado;
    }
}
