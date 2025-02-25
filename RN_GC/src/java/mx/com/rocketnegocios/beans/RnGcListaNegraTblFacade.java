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
import mx.com.rocketnegocios.entities.RnGcListaNegraTbl;
import mx.com.rocketnegocios.entities.RnGcTipoListaNegraTbl;

/**
 *
 * @author LenovoZ40
 */
@Stateless
public class RnGcListaNegraTblFacade extends AbstractFacade<RnGcListaNegraTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcListaNegraTblFacade() {
        super(RnGcListaNegraTbl.class);
    }
    
    public List<RnGcListaNegraTbl> obtenerXTipoListaNegra(RnGcTipoListaNegraTbl tipoListaNegra){
        List<RnGcListaNegraTbl> listaNegra = new ArrayList<>();
        try{
            listaNegra = em.createNamedQuery("RnGcListaNegraTbl.findByTipoLista", RnGcListaNegraTbl.class)
                    .setParameter("idTipoLista", tipoListaNegra)
                    .getResultList();
        }catch(NoResultException ex){
            System.out.println("No hay datos con el tipo lista negra: " + tipoListaNegra);
        }
        return listaNegra;
    }
    
    public List<RnGcListaNegraTbl> obtenerActivos(){
        List<RnGcListaNegraTbl> listaNegra = new ArrayList<>();
        try{
            listaNegra = em.createNamedQuery("RnGcListaNegraTbl.findAll", RnGcListaNegraTbl.class)
                    .getResultList();
        }catch(NoResultException ex){
            System.out.println("No se encontraron datos activos");
        }
        return listaNegra;
    }
    
    public List<RnGcListaNegraTbl> obtenerXRfc(String rfc){
        List<RnGcListaNegraTbl> listaNegra = new ArrayList<>();
        try{
            listaNegra = em.createNamedQuery("RnGcListaNegraTbl.findByRfc", RnGcListaNegraTbl.class)
                    .setParameter("rfc", rfc)
                    .getResultList();
        }catch(NoResultException ex){
            System.out.println("No se encontraron datos activos");
        }
        return listaNegra;
    }
    
    public List<RnGcListaNegraTbl> obtenerXNombre(String nombre){
        List<RnGcListaNegraTbl> listaNegra = new ArrayList<>();
        try{
            listaNegra = em.createNamedQuery("RnGcListaNegraTbl.findByNombreContribuyente", RnGcListaNegraTbl.class)
                    .setParameter("nombreContribuyente", nombre)
                    .getResultList();
        }catch(NoResultException ex){
            System.out.println("No se encontraron datos activos");
        }
        return listaNegra;
    }
    
}
