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
import mx.com.rocketnegocios.entities.RnGcPersonasTbl;

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

    public RnGcArchivosTbl obtenerArchivoPorCfdiId(int cfdiId) {

        try {
            List<RnGcArchivosTbl> lista = em.createNamedQuery(
                    "RnGcArchivosTbl.findByCfdiIdValue",
                    RnGcArchivosTbl.class)
                    .setParameter("cfdiId", cfdiId)
                    .getResultList();

            return lista.isEmpty() ? null : lista.get(0);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Nuevo metodo para que no vean todos las pantallas 
    public List<RnGcArchivosTbl> obtenerCreadoPor(int creadoPor){
        List<RnGcArchivosTbl> lista = null; 
        try {
            lista = em.createNamedQuery("RnGcArchivosTbl.findByCreadoPor", RnGcArchivosTbl.class).setParameter("creadoPor", creadoPor).getResultList();
        } catch (NoResultException ex) {
            System.out.println("No hay archivos para ese usuario :)");
        }
        return lista;
    }
    //Este método consulta y devuelve las personas de la tabla RnGcPersonasTbl cuyo campo creadoPor coincide con alguno de los identificadores incluidos en la lista ids
    public List<RnGcArchivosTbl> obtenerCreadoPorLista(List<Integer>ids){
        List<RnGcArchivosTbl> lista =null;
        try {
            lista = em.createQuery("SELECT r FROM RnGcArchivosTbl r WHERE r.creadoPor IN:ids", RnGcArchivosTbl.class).setParameter("ids", ids).getResultList();
        } catch (NoResultException ex){
            System.out.println("No hay archivos para ese grupo de usuarios"); 

        }
        return lista;
    }
}
