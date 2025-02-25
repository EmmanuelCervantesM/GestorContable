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
import mx.com.rocketnegocios.entities.RnGcNomSolicitudesTbl;

/**
 *
 * @author LenovoZ40
 */
@Stateless
public class RnGcNomSolicitudesTblFacade extends AbstractFacade<RnGcNomSolicitudesTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcNomSolicitudesTblFacade() {
        super(RnGcNomSolicitudesTbl.class);
    }
    
    public RnGcNomSolicitudesTbl refreshFromDB(RnGcNomSolicitudesTbl solicitud){
        RnGcNomSolicitudesTbl solicitudLocal = null;
        solicitudLocal = em.merge(solicitud);
        return solicitudLocal;
    }
    
    public RnGcNomSolicitudesTbl obtenerXNomina(Integer nominaId){
        RnGcNomSolicitudesTbl solicitud = null;
        try{
            solicitud = em.createNamedQuery("RnGcNomSolicitudesTbl.findByNominaId", RnGcNomSolicitudesTbl.class)
                    .setParameter("nominaId", nominaId)
                    .getSingleResult();
        }catch(NoResultException ex){
            System.out.println("No se encontro la soliciutd");
        }
        return solicitud;
    }
    
}
