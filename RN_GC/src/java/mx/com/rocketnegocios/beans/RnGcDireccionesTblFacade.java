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
import mx.com.rocketnegocios.entities.RnGcCpConductoresTbl;
import mx.com.rocketnegocios.entities.RnGcDireccionesTbl;
import mx.com.rocketnegocios.entities.RnGcPersonasTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcDireccionesTblFacade extends AbstractFacade<RnGcDireccionesTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcDireccionesTblFacade() {
        super(RnGcDireccionesTbl.class);
    }
    
    public List<RnGcDireccionesTbl> obtenerDireccionesPorPersona(RnGcPersonasTbl personaId){
        List<RnGcDireccionesTbl> listaDirecciones = null;
        
        try {
            listaDirecciones = em.createNamedQuery("RnGcDireccionesTbl.findByPersonaId", RnGcDireccionesTbl.class)
                    .setParameter("personasId", personaId)
                    .getResultList();
            System.out.println("listadirecciones: " + listaDirecciones);
        }catch (NoResultException ex){
            System.out.println("No hay direcciones para esa persona");
        }
        
        return listaDirecciones;
    }
    
    public RnGcDireccionesTbl obtenerDireccionesPorConductor(RnGcCpConductoresTbl conductorId){
        RnGcDireccionesTbl listaDirecciones = null;
        
        try {
            listaDirecciones = em.createNamedQuery("RnGcDireccionesTbl.findByConductorId", RnGcDireccionesTbl.class)
                    .setParameter("conductorId", conductorId)
                    .getSingleResult();
            System.out.println("listadirecciones: " + listaDirecciones);
        }catch (NoResultException ex){
            System.out.println("No hay direcciones para ese conductor");
        }
        
        return listaDirecciones;
    }
    // Metodo para filtrar a los usuarios 
    public List<RnGcDireccionesTbl> obtenerCreadorPor(int creadoPor){
        List<RnGcDireccionesTbl> lista = null; 
        try {
            lista = em.createNamedQuery("RnGcDireccionesTbl.findByCreadoPor", RnGcDireccionesTbl.class).setParameter("creadoPor", creadoPor).getResultList();
        } catch (NoResultException ex){
            System.out.println("No hay direcciones para ese usuario :(");
        }
        return lista;
    }
    // Este método consulta y devuelve las personas de la tabla RnGcPersonasTbl cuyo campo creadoPor coincide con alguno de los identificadores incluidos en la lista ids
    public List<RnGcDireccionesTbl> obtenerCreadoPorLista(List<Integer>ids){
        List<RnGcDireccionesTbl> lista = null; 
        try {
            lista = em.createQuery("SELECT r FROM RnGcDireccionesTbl r WHERE r.creadoPor IN:ids", RnGcDireccionesTbl.class).setParameter("ids", ids).getResultList();
        } catch (NoResultException ex){
            System.out.println ("No hay CFDIs para ese grupo de usuarios");
        }
        return lista;
    }
}
