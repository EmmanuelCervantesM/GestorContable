/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcPeriodosTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcPeriodosTblFacade extends AbstractFacade<RnGcPeriodosTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() { 
        return em; 
    }

    public RnGcPeriodosTblFacade() { 
        super(RnGcPeriodosTbl.class); 
    }
    
    @Override
    public void create(RnGcPeriodosTbl entity) {
        em.persist(entity);    
    }

    public void flush() { em.flush(); }

    public List<RnGcPeriodosTbl> obtenerPeriodoIdDesc(RnGcUsuariosTbl usuario){
        return em.createQuery(
                "SELECT p FROM RnGcPeriodosTbl p " +
                "WHERE p.usuariosId = :usuario " + 
                "ORDER BY p.periodoId DESC", 
                RnGcPeriodosTbl.class)
            .setParameter("usuario", usuario)
            .setMaxResults(1)
            .getResultList();
    }
    
    public RnGcPeriodosTbl findActivo(RnGcUsuariosTbl usuario) {
        List<RnGcPeriodosTbl> l = em.createNamedQuery(
                "RnGcPeriodosTbl.findActivoByUsuario", RnGcPeriodosTbl.class)
            .setParameter("usuario", usuario)
            .setMaxResults(1)
            .getResultList();
        return l.isEmpty() ? null : l.get(0);
    }

    public RnGcPeriodosTbl findAnterior(RnGcPeriodosTbl periodoActual,
                                        RnGcUsuariosTbl usuario) {
        if (periodoActual == null) {
            return null;
        }

        List<RnGcPeriodosTbl> lista = em.createQuery(
                "SELECT p FROM RnGcPeriodosTbl p " +
                "WHERE p.usuariosId = :usr " +
                "  AND p.fechaInicioPeriodo < :fi " +
                "ORDER BY p.fechaInicioPeriodo DESC",
                RnGcPeriodosTbl.class)
            .setParameter("usr", usuario)
            .setParameter("fi", periodoActual.getFechaInicioPeriodo())
            .setMaxResults(1)
            .getResultList();

        return lista.isEmpty() ? null : lista.get(0);
    }

    public int siguienteConsecutivoPeriodo(RnGcUsuariosTbl usuario) {
        Integer ultimo = null;
        try {
            ultimo = em.createQuery(
                    "SELECT MAX(p.periodoId) FROM RnGcPeriodosTbl p " +
                    "WHERE p.usuariosId = :usuario",   // <-- ajusta este nombre
                    Integer.class)
                .setParameter("usuario", usuario)
                .getSingleResult();
        } catch (NoResultException ignore) {}
        return (ultimo == null ? 1 : ultimo + 1);
    }
    
    public RnGcPeriodosTbl obtenerPeriodoActivo(RnGcUsuariosTbl usuario) {
        List<RnGcPeriodosTbl> l = em.createQuery(
                "SELECT p FROM RnGcPeriodosTbl p " +
                "WHERE p.usuariosId = :usuario AND p.estatus = 'A' " +
                "ORDER BY p.periodoId DESC", RnGcPeriodosTbl.class)
            .setParameter("usuario", usuario)  // <-- ajusta el nombre del campo si difiere
            .setMaxResults(1)
            .getResultList();
        return l.isEmpty() ? null : l.get(0);
    }

    public boolean existeSolapadoActivo(RnGcUsuariosTbl usuario, Date ini, Date fin) {
        Long c = em.createQuery(
                "SELECT COUNT(p) FROM RnGcPeriodosTbl p " +
                "WHERE p.usuariosId = :usuario AND p.estatus = 'A' " +
                "AND p.fechaInicioPeriodo <= :fin AND p.fechaFinPeriodo >= :ini", Long.class)
            .setParameter("usuario", usuario)
            .setParameter("ini", ini)
            .setParameter("fin", fin)
            .getSingleResult();
        return c != null && c > 0;
    }

    public void forceFlushWithQuery() {
        em.createQuery("SELECT COUNT(p) FROM RnGcPeriodosTbl p", Long.class)
          .getSingleResult();
    }

    /**
     * Si prefieres flush directo.
     */
    public void flushSafely() {
        em.flush();
    }
    
    public boolean existeAbierto(RnGcUsuariosTbl u) {
        Long c = em.createQuery(
            "select count(p) from RnGcPeriodosTbl p where p.usuariosId = :u and p.estatus = 'A'", Long.class)
            .setParameter("u", u)
            .getSingleResult();
        return c != null && c > 0;
    }
    
public RnGcPeriodosTbl buscarUltimoPeriodoPorUsuario(RnGcUsuariosTbl usuario){
    List<RnGcPeriodosTbl> res = em.createQuery(
        "SELECT r FROM RnGcPeriodosTbl r " +
        "WHERE r.usuariosId = :u " +
        "ORDER BY r.fechaFinPeriodo DESC", RnGcPeriodosTbl.class)
        .setParameter("u", usuario)
        .setMaxResults(1)
        .getResultList();
    return res.isEmpty() ? null : res.get(0);
}

    public List<RnGcPeriodosTbl> findByUsuario(RnGcUsuariosTbl user) {
        return em.createQuery(
                "SELECT p FROM RnGcPeriodosTbl p WHERE p.usuariosId = :u",
                RnGcPeriodosTbl.class)
            .setParameter("u", user)
            .getResultList();
    }

    public RnGcPeriodosTbl obtenerPeriodoActivo(Integer idUsuario) {
        List<RnGcPeriodosTbl> l = em.createQuery(
                "SELECT p FROM RnGcPeriodosTbl p WHERE p.usuariosId.id = :id AND p.estatus = 'A' ORDER BY p.periodoId DESC",
                RnGcPeriodosTbl.class)
            .setParameter("id", idUsuario)
            .setMaxResults(1)
            .getResultList();
        return l.isEmpty() ? null : l.get(0);
    }


}
