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
import mx.com.rocketnegocios.entities.RnGcCpPaissatTbl;

/**
 *
 * @author maxoc
 */
@Stateless
public class RnGcCpPaissatTblFacade extends AbstractFacade<RnGcCpPaissatTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcCpPaissatTblFacade() {
        super(RnGcCpPaissatTbl.class);
    }
    
    public List<RnGcCpPaissatTbl> obtenerXTipo(String tipo){
        List<RnGcCpPaissatTbl> lista = null;
        try {
            lista = em.createNamedQuery("RnGcCpPaissatTbl.findByTipo", RnGcCpPaissatTbl.class)
                    .setParameter("tipo", tipo)
                    .getResultList();
        }catch (NoResultException ex){
            System.out.println("Error en obtener por tipo");
        }
        return lista;
    }
    
    public List<RnGcCpPaissatTbl> obtenerXPais(String pais){
        List<RnGcCpPaissatTbl> lista = null;
        try {
            lista = em.createNamedQuery("RnGcCpPaissatTbl.findByEstadoPais", RnGcCpPaissatTbl.class)
                    .setParameter("tipo", "estado")
                    .setParameter("clavePais", pais)
                    .getResultList();
        }catch (NoResultException ex){
            System.out.println("Error en obtener por pais");
        }
        return lista;
    }
    
    public List<RnGcCpPaissatTbl> obtenerXEstado(String estado){
        List<RnGcCpPaissatTbl> lista = null;
        try {
            lista = em.createNamedQuery("RnGcCpPaissatTbl.findByEstado", RnGcCpPaissatTbl.class)
                    .setParameter("tipo", "municipio")
                    .setParameter("claveEstado", estado)
                    .getResultList();
        }catch (NoResultException ex){
            System.out.println("Error en obtener por pais");
        }
        return lista;
    }
}
