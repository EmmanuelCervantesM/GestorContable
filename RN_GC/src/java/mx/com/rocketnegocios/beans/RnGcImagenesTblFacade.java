/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcImagenesTbl;

/**
 *
 * @author jhony
 */
@Stateless
public class RnGcImagenesTblFacade extends AbstractFacade<RnGcImagenesTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcImagenesTblFacade() {
        super(RnGcImagenesTbl.class);
    }
    
    public RnGcImagenesTbl obtenerImagenPorRFC(String RFC){
        RnGcImagenesTbl imagen = null;
        try {
            imagen = em.createNamedQuery("RnGcImagenesTbl.findByRfc",RnGcImagenesTbl.class)
                    .setParameter("rfc", RFC)
                    .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("No se encontro imagenId utilizando RFC");
        }
        return imagen;
    }
    
}
