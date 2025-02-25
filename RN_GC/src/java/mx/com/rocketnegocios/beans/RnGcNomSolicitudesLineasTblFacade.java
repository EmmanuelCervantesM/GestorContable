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
import mx.com.rocketnegocios.entities.RnGcNomSolicitudesLineasTbl;

/**
 *
 * @author LenovoZ40
 */
@Stateless
public class RnGcNomSolicitudesLineasTblFacade extends AbstractFacade<RnGcNomSolicitudesLineasTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcNomSolicitudesLineasTblFacade() {
        super(RnGcNomSolicitudesLineasTbl.class);
    }
    
    public RnGcNomSolicitudesLineasTbl refreshFromDB(RnGcNomSolicitudesLineasTbl soliLineas){
        RnGcNomSolicitudesLineasTbl soliLineasLocal = null;
        soliLineasLocal = em.merge(soliLineas);
        return soliLineasLocal;
    }
    
    public List<RnGcNomSolicitudesLineasTbl> obtenerPercepciones(Integer soliTrabajador){
        List<RnGcNomSolicitudesLineasTbl> listaPercepciones = null;
        try{
            listaPercepciones = em.createNamedQuery("RnGcNomSolicitudesLineasTbl.obtenerPercepciones", RnGcNomSolicitudesLineasTbl.class)
                    .setParameter("solicitudTrabajadorId", soliTrabajador)
                    .getResultList();
        }catch(NoResultException ex){
            System.out.println("No se encontraron percepciones");
        }
        return listaPercepciones;
    }
    
    public List<RnGcNomSolicitudesLineasTbl> obtenerDeducciones(Integer soliTrabajador){
        List<RnGcNomSolicitudesLineasTbl> listaDeducciones = null;
        try{
            listaDeducciones = em.createNamedQuery("RnGcNomSolicitudesLineasTbl.obtenerDeducciones", RnGcNomSolicitudesLineasTbl.class)
                    .setParameter("solicitudTrabajadorId", soliTrabajador)
                    .getResultList();
        }catch(NoResultException ex){
            System.out.println("No se encontraron deducciones");
        }
        return listaDeducciones;
    }
    
    public List<RnGcNomSolicitudesLineasTbl> obtenerXSoliTrabajador(Integer soliTrabajadorUno, Integer soliTrabajadorDos){
        List<RnGcNomSolicitudesLineasTbl> listaLineas = null;
        try{
            listaLineas = em.createNamedQuery("RnGcNomSolicitudesLineasTbl.findBySoliTrabajador", RnGcNomSolicitudesLineasTbl.class)
                    .setParameter("soliTrabajadorUno", soliTrabajadorUno)
                    .setParameter("soliTrabajadorDos", soliTrabajadorDos)
                    .getResultList();
        }catch(NoResultException ex){
            System.err.println("No se encontraron lineas");
        }
        return listaLineas;
    }
    
}
