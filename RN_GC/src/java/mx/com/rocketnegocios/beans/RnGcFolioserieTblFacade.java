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
import mx.com.rocketnegocios.entities.RnGcCertificadosTbl;
import mx.com.rocketnegocios.entities.RnGcFolioserieTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcFolioserieTblFacade extends AbstractFacade<RnGcFolioserieTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcFolioserieTblFacade() {
        super(RnGcFolioserieTbl.class);
    }
    
    public List<RnGcFolioserieTbl> serieporUsuario(RnGcUsuariosTbl usuarioId){
        List<RnGcFolioserieTbl> listaFolios = null;
        try {
            listaFolios = em.createNamedQuery("RnGcFolioserieTbl.findByUsuarioEstado", RnGcFolioserieTbl.class)
                    .setParameter("usuariosId", usuarioId)
                    .setParameter("estado", "Activo")
                    .getResultList();
            System.out.println("listaFolios: " + listaFolios);
        } catch (NoResultException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return listaFolios;
    }
    
    public List<RnGcFolioserieTbl> folioPorUsuarioSerie(RnGcUsuariosTbl usuarioId, String serie){
        List<RnGcFolioserieTbl> folio = null;
        try {
            folio = em.createNamedQuery("RnGcFolioserieTbl.findByUsuarioSerie", RnGcFolioserieTbl.class)
                    .setParameter("usuariosId", usuarioId)
                    .setParameter("serie", serie)
                    .getResultList();
            System.out.println("folio: " + folio);
        } catch (NoResultException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return folio;
    }
     public List<RnGcFolioserieTbl> folioPorUsuarioSerieYCertificado(RnGcUsuariosTbl usuarioId, String serie, RnGcCertificadosTbl certificadoId){
        List<RnGcFolioserieTbl> folio = null;
        try {
            folio = em.createNamedQuery("RnGcFolioserieTbl.findByUsuarioSerieYCertificado", RnGcFolioserieTbl.class)
                    .setParameter("usuariosId", usuarioId)
                    .setParameter("serie", serie)
                    .setParameter("certificadoId", certificadoId)
                    .getResultList();
            System.out.println("folio: " + folio);
        } catch (NoResultException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return folio;
    }
    
    public List<RnGcFolioserieTbl> foliosPorCertificados(RnGcCertificadosTbl certificadoId) {
        List<RnGcFolioserieTbl> listaFolioSerie = null;
        try {
            listaFolioSerie = em.createNamedQuery("RnGcFolioserieTbl.findByCertificado", RnGcFolioserieTbl.class)
                    .setParameter("certificadosId", certificadoId)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return listaFolioSerie;
    }
    
    public List<RnGcFolioserieTbl> folioPorUsuarioSerieCert(RnGcUsuariosTbl usuarioId, String serie, RnGcCertificadosTbl certificadoId){
        List<RnGcFolioserieTbl> folio = null;
        try {
            folio = em.createNamedQuery("RnGcFolioserieTbl.findByUsuarioSerieCert", RnGcFolioserieTbl.class)
                    .setParameter("usuariosId", usuarioId)
                    .setParameter("serie", serie)
                    .setParameter("certificadoId", certificadoId)
                    .getResultList();
            System.out.println("folio: " + folio);
        } catch (NoResultException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return folio;
    }
}
