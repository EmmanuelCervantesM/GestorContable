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
import mx.com.rocketnegocios.entities.RnGcImpuestosLocalesTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcImpuestosLocalesTblFacade extends AbstractFacade<RnGcImpuestosLocalesTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcImpuestosLocalesTblFacade() {
        super(RnGcImpuestosLocalesTbl.class);
    }
    
    public List<RnGcImpuestosLocalesTbl> obtenerImpLocalesXUsuario(int creadoPor) {
        List<RnGcImpuestosLocalesTbl> listaImpLocales = null;
        try {
            listaImpLocales = em.createNamedQuery("RnGcImpuestosLocalesTbl.findByCreadoPor", RnGcImpuestosLocalesTbl.class)
                    .setParameter("creadoPor", creadoPor)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No hay datos: " + ex.getMessage());
        }
        return listaImpLocales;
    }
    
}
