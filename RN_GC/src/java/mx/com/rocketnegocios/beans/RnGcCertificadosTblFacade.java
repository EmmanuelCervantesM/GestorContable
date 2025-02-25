/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcCertificadosTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcCertificadosTblFacade extends AbstractFacade<RnGcCertificadosTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcCertificadosTblFacade() {
        super(RnGcCertificadosTbl.class);
    }
    
    public List<RnGcCertificadosTbl> obtenerCertificadosDeUsuario(RnGcUsuariosTbl usuarioId) {
        List<RnGcCertificadosTbl> listaCertificados = null;
        try {
            listaCertificados = em.createNamedQuery("RnGcCertificadosTbl.findByUsuarioId", RnGcCertificadosTbl.class)
                    .setParameter("usuariosId", usuarioId)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("El usuario no tiene certificados.");
        }
        return listaCertificados;
    }
    
    public List<RnGcCertificadosTbl> obtenerCertificadosActivosDeUsuario(RnGcUsuariosTbl usuarioId) {
        List<RnGcCertificadosTbl> listaCertificados = null;
        try {
            listaCertificados = em.createNamedQuery("RnGcCertificadosTbl.findByActivoUsuarioId", RnGcCertificadosTbl.class)
                    .setParameter("usuariosId", usuarioId)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("El usuario no tiene certificados.");
        }
        return listaCertificados;
    }
    
    public List<RnGcCertificadosTbl> obtenerCreadoPor(int creadoPor) {
        List<RnGcCertificadosTbl> listaCertificados = null;
        try {
            listaCertificados = em.createNamedQuery("RnGcCertificadosTbl.findByCreadoPor", RnGcCertificadosTbl.class)
                    .setParameter("creadoPor", creadoPor)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return listaCertificados;
    }
    
    public List<RnGcCertificadosTbl> certificadosActivos() {
        List<RnGcCertificadosTbl> listaActivos = null;
        try {
            listaActivos = em.createNamedQuery("RnGcCertificadosTbl.findByEstado", RnGcCertificadosTbl.class)
                    .setParameter("estado", "Activo")
                    .getResultList();
        } catch(NoResultException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return listaActivos;
    }
}
