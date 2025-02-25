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
import mx.com.rocketnegocios.entities.RnGcCfdisTbl;
import mx.com.rocketnegocios.entities.RnGcDocumentosRelacionadosTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcDocumentosRelacionadosTblFacade extends AbstractFacade<RnGcDocumentosRelacionadosTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcDocumentosRelacionadosTblFacade() {
        super(RnGcDocumentosRelacionadosTbl.class);
    }
    
    public RnGcDocumentosRelacionadosTbl refreshFromDB(RnGcDocumentosRelacionadosTbl docRel) {
        RnGcDocumentosRelacionadosTbl docRelLocal = null;
        docRelLocal = em.merge(docRel);
        return docRelLocal;
    }
    
    public List<RnGcDocumentosRelacionadosTbl> obtenerDocumentos(RnGcCfdisTbl cfdiId) {
        System.out.println("cfdiId: " + cfdiId);
        List<RnGcDocumentosRelacionadosTbl> listaDocumentos = null;
        try {
            listaDocumentos = em.createNamedQuery("RnGCDocumentosRelacionadosTbl.findByCfdiId", RnGcDocumentosRelacionadosTbl.class)
                    .setParameter("cfdisId", cfdiId)
                    .getResultList();
         } catch (NoResultException ex) {
             System.out.println("Error: " + ex.getMessage());
         }
        System.out.println("listaDocumentos: " + listaDocumentos);
        return listaDocumentos;
    }
    
}
