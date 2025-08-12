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
import mx.com.rocketnegocios.entities.RnGcNomPercepcionesTbl;

/**
 *
 * @author LenovoZ40
 */
@Stateless
public class RnGcNomPercepcionesTblFacade extends AbstractFacade<RnGcNomPercepcionesTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcNomPercepcionesTblFacade() {
        super(RnGcNomPercepcionesTbl.class);
    }
    
   public RnGcNomPercepcionesTbl obtenerXClave(String clave) {
    try {
        return em.createNamedQuery("RnGcNomPercepcionesTbl.findByCveTipoPercepcion", RnGcNomPercepcionesTbl.class)
                 .setParameter("cveTipoPercepcion", clave)
                 .getSingleResult();
    } catch (NoResultException ex) {
        System.out.println("No se encontró la percepción con clave: " + clave);
        return null;
    } catch (Exception ex) {
        System.err.println("Error al obtener percepción por clave: " + clave);
        ex.printStackTrace();
        return null;
    }
}
    
}
