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
import mx.com.rocketnegocios.entities.RnGcNomTiponominaTbl;

/**
 *
 * @author LenovoZ40
 */
@Stateless
public class RnGcNomTiponominaTblFacade extends AbstractFacade<RnGcNomTiponominaTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcNomTiponominaTblFacade() {
        super(RnGcNomTiponominaTbl.class);
    }
    
    public RnGcNomTiponominaTbl refreshFromDB(RnGcNomTiponominaTbl tipoNomina){
        RnGcNomTiponominaTbl tipoNominaLocal = null;
        tipoNominaLocal = em.merge(tipoNomina);
        return tipoNominaLocal;
    }
    
    public RnGcNomTiponominaTbl obtenerXClave(String clave){
        RnGcNomTiponominaTbl tipoNomina = new RnGcNomTiponominaTbl();
        try{
            tipoNomina = em.createNamedQuery("RnGcNomTiponominaTbl.findByCveTipoNomina", RnGcNomTiponominaTbl.class)
                    .setParameter("cveTipoNomina", clave)
                    .getSingleResult();
        }catch(NoResultException ex){
            System.out.println("No encontro tipo nomina");
        }
        return tipoNomina;
    }
    
}
