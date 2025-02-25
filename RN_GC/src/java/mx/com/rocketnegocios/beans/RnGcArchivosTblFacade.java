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
import mx.com.rocketnegocios.entities.RnGcArchivosTbl;
import mx.com.rocketnegocios.entities.RnGcCfdisTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcArchivosTblFacade extends AbstractFacade<RnGcArchivosTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcArchivosTblFacade() {
        super(RnGcArchivosTbl.class);
    }

    public RnGcArchivosTbl refreshFromDB(RnGcArchivosTbl archivoId) {
        RnGcArchivosTbl archivolocal = null;
        archivolocal = em.merge(archivoId);
        return archivolocal;
    }
    
    public List<RnGcArchivosTbl> obtenerArchivos(RnGcCfdisTbl cfdisId) {
        List<RnGcArchivosTbl> listaArchivos = null;
        System.out.println("cfdisId: " + cfdisId);
        try {
            listaArchivos = em.createNamedQuery("RnGcArchivosTbl.findByrngccfdistblId", RnGcArchivosTbl.class)
                    .setParameter("cfdiId", cfdisId)
                    .getResultList();
            System.out.println("listaArchivos: " + listaArchivos);
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }
        return listaArchivos;
    }
    
    public RnGcArchivosTbl obtenerArchivo(RnGcCfdisTbl cfdisId) {
        RnGcArchivosTbl listaArchivos = null;
        System.out.println("cfdisId: " + cfdisId);
        try {
            listaArchivos = em.createNamedQuery("RnGcArchivosTbl.findByrngccfdistblId", RnGcArchivosTbl.class)
                    .setParameter("cfdiId", cfdisId)
                    .getSingleResult();
            System.out.println("listaArchivos: " + listaArchivos);
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }
        return listaArchivos;
    }

}
