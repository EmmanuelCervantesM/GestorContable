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
import mx.com.rocketnegocios.entities.RnGcProductserviciosTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcProductserviciosTblFacade extends AbstractFacade<RnGcProductserviciosTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcProductserviciosTblFacade() {
        super(RnGcProductserviciosTbl.class);
    }
    
    public RnGcProductserviciosTbl refreshFromDB(RnGcProductserviciosTbl prodServ) {
        RnGcProductserviciosTbl prodServlocal = null;
        prodServlocal = em.merge(prodServ);
        return prodServlocal;
    }
    
    public List<RnGcProductserviciosTbl> obtenerPorUsuario(int creadoPor){
        List<RnGcProductserviciosTbl> listaProducServicios = null;
        try{
            listaProducServicios = em.createNamedQuery("RnGcProductserviciosTbl.findByCreadoPor", RnGcProductserviciosTbl.class)
                    .setParameter("creadoPor", creadoPor)
                    .getResultList();
        }catch (NoResultException ex){
            System.out.println("No hay Productos con el usuarioId");
        }
        return listaProducServicios;
    }
    
    public RnGcUsuariosTbl obtenerUsuarioPorClave(String usuarioClave) {
        RnGcUsuariosTbl usuarioId = null;
        //System.out.println("usuarioClave: " + usuarioClave);
        try {
            usuarioId = em.createNamedQuery("RnGcUsuariosTbl.findByUsuarioClave", RnGcUsuariosTbl.class)
                    .setParameter("usuarioClave", usuarioClave)
                    .getSingleResult();
            //System.out.println("Datos: " + usuarioId.getNombreCompleto());
        } catch (NoResultException ex) {
            System.out.println("No se encontro usuarioId utilizando usuarioClave");
        }
        return usuarioId;
    }  
    
    public RnGcUsuariosTbl obtenerUsuarioPorId(int IdUsuario){
        RnGcUsuariosTbl usuarioId = null;
        try{
            usuarioId = em.createNamedQuery("RnGcUsuariosTbl.findById", RnGcUsuariosTbl.class)
                    .setParameter("id", IdUsuario)
                    .getSingleResult();
            //System.out.println("Usuario: " + usuarioId);
        }catch(NoResultException ex){
            System.out.println("No hay Usuario");
        }
        return usuarioId;
    }

    public RnGcProductserviciosTbl obtenerCreadoPorDescripcion(int creadoPor, String descripcion){
        RnGcProductserviciosTbl listaProducServicios = null;
        try{
            listaProducServicios = em.createNamedQuery("RnGcProductserviciosTbl.findByCreadoPorDescripcion", RnGcProductserviciosTbl.class)
                    .setParameter("creadoPor", creadoPor)
                    .setParameter("descripcion", descripcion)
                    .getSingleResult();
        }catch (NoResultException ex){
            System.out.println("No hay Productos con el usuarioId y lña desccripcion: " + creadoPor + " | " + descripcion);
        }
        return listaProducServicios;
    }
    
    
}
