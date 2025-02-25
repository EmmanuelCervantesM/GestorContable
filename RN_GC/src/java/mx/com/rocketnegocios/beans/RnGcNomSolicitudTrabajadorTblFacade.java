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
import mx.com.rocketnegocios.entities.RnGcNomSolicitudTrabajadorTbl;
import mx.com.rocketnegocios.entities.RnGcNomSolicitudesTbl;

/**
 *
 * @author LenovoZ40
 */
@Stateless
public class RnGcNomSolicitudTrabajadorTblFacade extends AbstractFacade<RnGcNomSolicitudTrabajadorTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcNomSolicitudTrabajadorTblFacade() {
        super(RnGcNomSolicitudTrabajadorTbl.class);
    }
    
    public RnGcNomSolicitudTrabajadorTbl refreshFromDB(RnGcNomSolicitudTrabajadorTbl soliTrabajador){
        RnGcNomSolicitudTrabajadorTbl soliTrabajadorLocal = null;
        soliTrabajadorLocal = em.merge(soliTrabajador);
        return soliTrabajadorLocal;
    }
    
    public List<RnGcNomSolicitudTrabajadorTbl> obtenerXSolicitud(RnGcNomSolicitudesTbl solicitudId){
        List<RnGcNomSolicitudTrabajadorTbl> listaSoliTrabajador = null;
        try{
            listaSoliTrabajador = em.createNamedQuery("RnGcNomSolicitudTrabajadorTbl.findBySolicitudId", RnGcNomSolicitudTrabajadorTbl.class)
                    .setParameter("solicitudId", solicitudId)
                    .getResultList();
        }catch(NoResultException ex){
            System.err.println("No se encontraron datos");
        }
        return listaSoliTrabajador;
    }
    
    public RnGcNomSolicitudTrabajadorTbl obtenerXId(Integer solicitudTrabajadorId){
        RnGcNomSolicitudTrabajadorTbl solicitudTrabajador = null;
        try{
            solicitudTrabajador = em.createNamedQuery("RnGcNomSolicitudTrabajadorTbl.findById", RnGcNomSolicitudTrabajadorTbl.class)
                    .setParameter("id", solicitudTrabajadorId)
                    .getSingleResult();
        }catch(NoResultException ex){
            System.err.println("No se encontraron por Id");
        }
        return solicitudTrabajador;
    }
    
}
