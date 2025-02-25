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
import mx.com.rocketnegocios.entities.RnGcPersonasTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcPersonasTblFacade extends AbstractFacade<RnGcPersonasTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcPersonasTblFacade() {
        super(RnGcPersonasTbl.class);
    }
    
    public List<RnGcPersonasTbl> obtenerPersonasPorTipo(String TipoPersonaStr) {
        List<RnGcPersonasTbl> tipoPersona = null;
        try {
            tipoPersona = em.createNamedQuery("RnGcPersonasTbl.findByTipoPersona", RnGcPersonasTbl.class)
                    .setParameter("tipoPersona", TipoPersonaStr)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No encontro tipoPersona");
        }
        return tipoPersona;
    }
    
    public RnGcPersonasTbl obtenerporRfcCreadoPor(String rfc, int creadoPor) {
        RnGcPersonasTbl persona= null;
        try {
            persona = em.createNamedQuery("RnGcPersonasTbl.findByRfcreadoPor", RnGcPersonasTbl.class)
                    .setParameter("rfc", rfc)
                    .setParameter("creadoPor", creadoPor)
                    .getSingleResult();
        } catch(NoResultException ex) {
            System.out.println("No se encontro registron con el rfc: " + rfc);
        }
        return persona;
    }

    public RnGcPersonasTbl refreshFromDB(RnGcPersonasTbl personas) {
        RnGcPersonasTbl personalocal = null;
        personalocal = em.merge(personas);
        return personalocal;
    }
    
    public List<RnGcPersonasTbl> obtenerPersonasPorUsuario(RnGcUsuariosTbl usuarioId){
        List<RnGcPersonasTbl> personasLista = null;
        try{
            personasLista = em.createNamedQuery("RnGcPersonasTbl.findByUsuarioId", RnGcPersonasTbl.class)
                    .setParameter("usuarioId", usuarioId)
                    .getResultList();
            //System.out.println("personasLista: " + personasLista);
        }catch(NoResultException ex){
            System.out.println("No encontro personas");
        }
        
        return personasLista;
    }
    
    public RnGcUsuariosTbl obtenerUsuarioPorId(int IdUsuario){
        RnGcUsuariosTbl usuarioId = null;
        try{
            usuarioId = em.createNamedQuery("RnGcUsuariosTbl.findById", RnGcUsuariosTbl.class)
                    .setParameter("id", IdUsuario)
                    .getSingleResult();
            System.out.println("Usuario: " + usuarioId);
        }catch(NoResultException ex){
            System.out.println("No hay Usuario");
        }
        return usuarioId;
    }
    
    public RnGcUsuariosTbl obtenerUsuarioPorClave(String usuarioClave) {
        RnGcUsuariosTbl usuarioId = null;
        try {
            usuarioId = em.createNamedQuery("RnGcUsuariosTbl.findByUsuarioClave", RnGcUsuariosTbl.class)
                    .setParameter("usuarioClave", usuarioClave)
                    .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("No se encontro usuarioId utilizando usuarioClave");
        }
        return usuarioId;
    }
    
    public List<RnGcPersonasTbl> obtenerCreadoPor(int creadoPor){
        List<RnGcPersonasTbl> listaPersonas = null;
        try{
            listaPersonas = em.createNamedQuery("RnGcPersonasTbl.findByCreadoPor", RnGcPersonasTbl.class)
                    .setParameter("creadoPor", creadoPor)
                    .getResultList();     
        }catch(NoResultException ex){
            System.out.println("No hay Personas");
        }
        return listaPersonas;
    }
    
    public RnGcPersonasTbl obtenerCreadoPorNombre(int creadoPor, String rfc){
        RnGcPersonasTbl listaPersonas = null;
        try{
            listaPersonas = em.createNamedQuery("RnGcPersonasTbl.findByCreadoPorNombre", RnGcPersonasTbl.class)
                    .setParameter("creadoPor", creadoPor)
                    .setParameter("rfc", rfc)
                    .getSingleResult();
        }catch(NoResultException ex){
            System.out.println("No hay Personas");
        }
        return listaPersonas;
    }
    
    public RnGcPersonasTbl obtenerPersona(Integer Id){
        RnGcPersonasTbl personaId = null;
        try {
            personaId = em.createNamedQuery("RnGcPersonasTbl.findById", RnGcPersonasTbl.class)
                    .setParameter("id", Id)
                    .getSingleResult();
            System.out.println("personaId: " + personaId.getNombre());
        }catch (NoResultException ex){
            System.out.println("No hay persona con ese RFC");
        }
        return personaId;
    }
    
}
