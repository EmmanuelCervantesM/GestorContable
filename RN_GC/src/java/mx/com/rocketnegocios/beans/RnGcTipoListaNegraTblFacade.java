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
import mx.com.rocketnegocios.entities.RnGcTipoListaNegraTbl;

/**
 *
 * @author LenovoZ40
 */
@Stateless
public class RnGcTipoListaNegraTblFacade extends AbstractFacade<RnGcTipoListaNegraTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcTipoListaNegraTblFacade() {
        super(RnGcTipoListaNegraTbl.class);
    }
    
    public RnGcTipoListaNegraTbl refreshFromDB(RnGcTipoListaNegraTbl tipoLN){
        RnGcTipoListaNegraTbl tipoLNLocal = null;
        tipoLNLocal = em.merge(tipoLN);
        return tipoLNLocal;
    }
    
    public List<RnGcTipoListaNegraTbl> obtenerActivos(){
        List<RnGcTipoListaNegraTbl> tipoListaNegraActivos = null;
        try{
            tipoListaNegraActivos = em.createNamedQuery("RnGcTipoListaNegraTbl.findAll", RnGcTipoListaNegraTbl.class)
                    .getResultList();
        }catch(NoResultException ex){
            System.out.println("No se encontrarons resultado activos");
        }
        return tipoListaNegraActivos;
    }
    
    public List<RnGcTipoListaNegraTbl> obtenerXFolio(String folio){
        List<RnGcTipoListaNegraTbl> tipoListaNegraActivos = null;
        try{
            tipoListaNegraActivos = em.createNamedQuery("RnGcTipoListaNegraTbl.findByFolioPublicacionCreadoPor", RnGcTipoListaNegraTbl.class)
                    .setParameter("folioPublicacion", folio)
                    .getResultList();
        }catch(NoResultException ex){
            System.out.println("No se ecnontraron resultados con el folio: " + folio);
        }
        return tipoListaNegraActivos;
    }
    
}
