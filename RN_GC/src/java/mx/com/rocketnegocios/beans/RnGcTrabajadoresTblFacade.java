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
    
    public String obtenerSiguienteNoTrabajador(Integer usuarioId) {
    try {
        Number maxNoTrabajador = (Number) em.createQuery(
            "SELECT COALESCE(MAX(CAST(r.noTrabajador AS integer)), 0) + 1 FROM RnGcTrabajadoresTbl r WHERE r.creadoPor = :creadoPor")
            .setParameter("creadoPor", usuarioId)
            .getSingleResult();

        int siguiente = (maxNoTrabajador != null) ? maxNoTrabajador.intValue() : 1;

        System.out.println("Se proceso el id: " + siguiente);
        return String.valueOf(siguiente);
    } catch (Exception e) {
        e.printStackTrace();
        return "1";
    }
}
    
    public RnGcTrabajadoresTbl refreshFromDB(RnGcTrabajadoresTbl trabajadoiresId){
        RnGcTrabajadoresTbl trabajadorLocal = null;
        trabajadorLocal = em.merge(trabajadorLocal);
        return trabajadorLocal;
    }
    /// Agregrando funcion para contar 
    
    public Long contarTrabajador(String curp, String rfc, String nss){
        return em.createNamedQuery("RnGcTrabajadoresTbl.countTrabajador", Long.class)
                 .setParameter("curp", curp)
                 .setParameter("rfc", rfc)
                 .setParameter("nss", nss)
                 .getSingleResult();
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
    
    public RnGcTrabajadoresTbl obtenerPorId (Integer id){
        RnGcTrabajadoresTbl trabajador = null;
        try{
            trabajador = em.createNamedQuery("RnGcTrabajadoresTbl.findById", RnGcTrabajadoresTbl.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }catch(NoResultException ex){
            System.out.println("No se encontro trabajador");
        }
        return trabajador;
    }
    
    public RnGcTrabajadoresTbl obtenerPorNoTrabajador(String numero, Integer usuarioId) {
    try {
        List<RnGcTrabajadoresTbl> resultado = em.createNamedQuery(
                "RnGcTrabajadoresTbl.findByNoTrabajador", RnGcTrabajadoresTbl.class)
            .setParameter("noTrabajador", numero)
            .setParameter("creadoPor", usuarioId)
            .getResultList();

        if (!resultado.isEmpty()) {
            return resultado.get(0); // o aplicas lógica si hay duplicados
        }
    } catch (Exception ex) {
        System.out.println("Error al consultar trabajador: " + ex.getMessage());
    }
    return null;
}
    
}
