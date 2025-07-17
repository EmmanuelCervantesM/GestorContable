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
import mx.com.rocketnegocios.entities.RnGcNomTipojornadaTbl;

/**
 *
 * @author LenovoZ40
 */
@Stateless
public class RnGcNomTipojornadaTblFacade extends AbstractFacade<RnGcNomTipojornadaTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcNomTipojornadaTblFacade() {
        super(RnGcNomTipojornadaTbl.class);
    }
    
    public RnGcNomTipojornadaTbl obtenerByDescripcion(String descripcion){
        RnGcNomTipojornadaTbl tipoJornada = null;
        try{
            tipoJornada = em.createNamedQuery("RnGcNomTipojornadaTbl.findByDescripcion", RnGcNomTipojornadaTbl.class)
                    .setParameter("descripcion", descripcion)
                    .getSingleResult();
        }catch(NoResultException ex){
            System.out.println("No se encontraron obtenerByDescripcion");
        }
        return tipoJornada;
    }
    
}
