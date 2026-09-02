/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcTimbresTotalTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;

/**
 *
 * @author Joaquin
 */
@Stateless
public class RnGcTimbresTotalesTblFacade extends AbstractFacade<RnGcTimbresTotalTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcTimbresTotalesTblFacade() {
        super(RnGcTimbresTotalTbl.class);
    }

    public RnGcTimbresTotalTbl refreshFromDB(RnGcTimbresTotalTbl timbre) {
        RnGcTimbresTotalTbl timbreLocal = null;
        timbreLocal = em.merge(timbre);
        return timbreLocal;
    }

    public RnGcTimbresTotalTbl createAndReturn(RnGcTimbresTotalTbl timbre) {
        if (timbre.getUsuarioId() == null) {
            throw new IllegalArgumentException("El campo usuarioId no puede ser null");
        }
        if (timbre.getFechaCreacion() == null) {
            timbre.setFechaCreacion(new Date());
        }
        if (timbre.getUltimaFechaActualizacion() == null) {
            timbre.setUltimaFechaActualizacion(new Date());
        }
        em.persist(timbre);
        em.flush(); // fuerza la inserción y genera ID
        return timbre;
    }

    public List<RnGcTimbresTotalTbl> obtenerPorUsuario(RnGcUsuariosTbl usuarioId) {
        List<RnGcTimbresTotalTbl> totalTimbres = null;
        try {
            System.out.print("Se esta ejecuntado la funcion en la base de dato");
            totalTimbres = em.createNamedQuery("RnGcTimbresTotalTbl.findByUsuarioId", RnGcTimbresTotalTbl.class)
                    .setParameter("usuarioId", usuarioId)
                    .getResultList();
            System.out.print("Se encontro: " + totalTimbres.size());
        } catch (NoResultException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return totalTimbres;
    }

    public int obtenerTimbreAdministrador(Integer usuarioId) {
        int totalTimbre = 0;
        try {
            // Buscar el objeto Usuario por su ID
            RnGcUsuariosTbl usuario = em.find(RnGcUsuariosTbl.class, usuarioId);

            if (usuario != null) {
                BigDecimal  result = (BigDecimal ) em.createNamedQuery("RnGcTimbresTotalTbl.sumTimbresTotales")
                        //.setParameter("usuarioId", usuario) // ahora sí pasamos la entidad
                        .getSingleResult();
                if (result != null) {
                    totalTimbre = result.intValue();
                }
            }
        } catch (NoResultException ex) {
            System.out.println("Error obtenerTimbre: " + ex.getMessage());
        }
        return totalTimbre;
    }

    public RnGcTimbresTotalTbl obtenerTimbreAdministradorAll(Integer usuarioId) {
        RnGcTimbresTotalTbl timbreTotal = null;
        try {
            // Buscar el objeto Usuario por su ID
            RnGcUsuariosTbl usuario = em.find(RnGcUsuariosTbl.class, usuarioId);

            if (usuario != null) {
                // Ejecutar la NamedQuery que busca por el usuario
                timbreTotal = em.createNamedQuery("RnGcTimbresTotalTbl.findByUsuarioId", RnGcTimbresTotalTbl.class)
                        .setParameter("usuarioId", usuario) // pasamos la entidad
                        .getSingleResult();
            }
        } catch (NoResultException ex) {
            System.out.println("Error obtenerTimbre: " + ex.getMessage());
        }
        return timbreTotal;
    }

}
