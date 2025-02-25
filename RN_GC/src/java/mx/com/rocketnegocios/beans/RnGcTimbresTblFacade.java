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
import mx.com.rocketnegocios.entities.RnGcTimbresTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcTimbresTblFacade extends AbstractFacade<RnGcTimbresTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcTimbresTblFacade() {
        super(RnGcTimbresTbl.class);
    }
    
    public RnGcTimbresTbl refreshFromDB(RnGcTimbresTbl timbre){
        RnGcTimbresTbl timbreLocal = null;
        timbreLocal = em.merge(timbre);
        return timbreLocal;
    }

    public List<RnGcTimbresTbl> obtenerPorUsuario(RnGcUsuariosTbl usuarioId) {
        List<RnGcTimbresTbl> listaTimbres = null;
        try {
            listaTimbres = em.createNamedQuery("RnGcTimbresTbl.findByUsuarioId", RnGcTimbresTbl.class)
                    .setParameter("usuarioId", usuarioId)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return listaTimbres;
    }

    public List<RnGcTimbresTbl> obtenerTimbre(String proveedor, RnGcUsuariosTbl usuario) {
        List<RnGcTimbresTbl> itemsTimbre = null;
        try {
            itemsTimbre = em.createNamedQuery("RnGcTimbresTbl.findByProveedorUsuario", RnGcTimbresTbl.class)
                    .setParameter("proveedor", proveedor)
                    .setParameter("usuarioId", usuario)
                    .getResultList();
            //System.out.println("timbreF: " + itemsTimbre);
        } catch (NoResultException ex) {
            System.out.println("Error obtenerTimbre: " + ex.getMessage());
        }
        return itemsTimbre;
    }
    
    public long obtenerTotalTimbresActivosXUsuario(String proveedor, RnGcUsuariosTbl usuario) {
        Long totaltimbres = 0L;
        try {
            totaltimbres = em.createNamedQuery("RnGcTimbresTbl.SUMTimbresActivos", long.class)
                    .setParameter("proveedor", proveedor)
                    .setParameter("usuarioId", usuario)
                    .getSingleResult();
            //System.out.println("totaltimbres: " + totaltimbres);
        } catch (NoResultException ex) {
            System.out.println("Error en obtenerTotalTimbresActivosXUsuario: " + ex.getMessage());
        }
        return totaltimbres;
    }
    
    public long obtenerTotalTimbresTotalesXUsuario(String proveedor, RnGcUsuariosTbl usuario) {
        Long totaltimbres = 0L;
        try {
            totaltimbres = em.createNamedQuery("RnGcTimbresTbl.SUMTimbresTotal", long.class)
                    .setParameter("proveedor", proveedor)
                    .setParameter("usuarioId", usuario)
                    .getSingleResult();
            //System.out.println("totaltimbres: " + totaltimbres);
        } catch (NoResultException ex) {
            System.out.println("Error en obtenerTotalTimbresTotalesXUsuario: " + ex.getMessage());
        }
        return totaltimbres;
    }
    
    public List<RnGcTimbresTbl> listaTimbresUsuario(RnGcUsuariosTbl usuario) {
        List<RnGcTimbresTbl> listaTimbres = null;
        try {
            listaTimbres = em.createNamedQuery("RnGcTimbresTbl.findByUsuarioEstado", RnGcTimbresTbl.class)
                    .setParameter("usuarioId", usuario)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return listaTimbres;
    }
    

}
