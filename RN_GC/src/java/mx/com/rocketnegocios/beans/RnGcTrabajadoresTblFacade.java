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
import mx.com.rocketnegocios.entities.RnGcTrabajadoresTbl;

/**
 *
 * @author LenovoZ40
 */
@Stateless
public class RnGcTrabajadoresTblFacade extends AbstractFacade<RnGcTrabajadoresTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcTrabajadoresTblFacade() {
        super(RnGcTrabajadoresTbl.class);
    }
    
    public RnGcTrabajadoresTbl refreshFromDB(RnGcTrabajadoresTbl trabajadoiresId){
        RnGcTrabajadoresTbl trabajadorLocal = null;
        trabajadorLocal = em.merge(trabajadorLocal);
        return trabajadorLocal;
    }
    
    public List<RnGcTrabajadoresTbl> trabajadoresCreadoPor(Integer usuarioId){
        List<RnGcTrabajadoresTbl> listaTrabajadores = new ArrayList<>();
        try{
            listaTrabajadores = em.createNamedQuery("RnGcTrabajadoresTbl.findByCreadoPor", RnGcTrabajadoresTbl.class)
                    .setParameter("creadoPor", usuarioId)
                    .getResultList();
        }catch(NoResultException ex){
            System.out.println("No se encontraron trabajadores");
        }
        return listaTrabajadores;
    }//RnGcTrabajadoresTbl.findByCreadoPorYNombre
    
    public RnGcTrabajadoresTbl obtenerCreadoPorYNombre(String nombre, Integer usuarioId){
        RnGcTrabajadoresTbl trabajador = null;
        try{
            trabajador = em.createNamedQuery("RnGcTrabajadoresTbl.findByCreadoPorYNombre", RnGcTrabajadoresTbl.class)
                    .setParameter("creadoPor", usuarioId)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        }catch(NoResultException ex){
            System.out.println("No se encontro trabajador");
        }
        return trabajador;
    }
    
    
}
