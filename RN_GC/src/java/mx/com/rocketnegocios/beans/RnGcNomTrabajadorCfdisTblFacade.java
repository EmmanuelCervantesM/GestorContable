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
import mx.com.rocketnegocios.entities.RnGcNomSolicitudTrabajadorTbl;
import mx.com.rocketnegocios.entities.RnGcNomTrabajadorCfdisTbl;

/**
 *
 * @author LenovoZ40
 */
@Stateless
public class RnGcNomTrabajadorCfdisTblFacade extends AbstractFacade<RnGcNomTrabajadorCfdisTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcNomTrabajadorCfdisTblFacade() {
        super(RnGcNomTrabajadorCfdisTbl.class);
    }
    
    public RnGcNomTrabajadorCfdisTbl refreshFromDB(RnGcNomTrabajadorCfdisTbl trabajadorCfdi){
        RnGcNomTrabajadorCfdisTbl trabajadorCfdiLocal = null;
        trabajadorCfdiLocal = em.merge(trabajadorCfdi);
        return trabajadorCfdiLocal;
    }
    
    public RnGcNomTrabajadorCfdisTbl obtenerXSoliTrabajdor(RnGcNomSolicitudTrabajadorTbl soliTrabajador){
        RnGcNomTrabajadorCfdisTbl trabajadorCfdi = new RnGcNomTrabajadorCfdisTbl();
        try{
            trabajadorCfdi = em.createNamedQuery("RnGcNomTrabajadorCfdisTbl.findBysolicitudTrabajdorId", RnGcNomTrabajadorCfdisTbl.class)
                    .setParameter("solicitudTrabajdorId", soliTrabajador)
                    .getSingleResult();
        }catch(NoResultException ex){
            System.out.println("No se encontraron cfdi del trabajador");
        }
        return trabajadorCfdi;
    }
    
}
