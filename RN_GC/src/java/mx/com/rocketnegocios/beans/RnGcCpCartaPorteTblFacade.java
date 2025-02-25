/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcCpCartaPorteTbl;

/**
 *
 * @author maxoc
 */
@Stateless
public class RnGcCpCartaPorteTblFacade extends AbstractFacade<RnGcCpCartaPorteTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcCpCartaPorteTblFacade() {
        super(RnGcCpCartaPorteTbl.class);
    }
    
    public RnGcCpCartaPorteTbl refreshFromDB(RnGcCpCartaPorteTbl cartaPorte) {
        RnGcCpCartaPorteTbl cartaPorteLocal = null;
        cartaPorteLocal = em.merge(cartaPorte);
        return cartaPorteLocal;
    }
    
    public List<RnGcCpCartaPorteTbl> obtenerXDesc() {
        List<RnGcCpCartaPorteTbl> lista = null;
        try {
            lista = em.createNativeQuery("select * from rn_gc_cp_carta_porte_tbl order by id desc", RnGcCpCartaPorteTbl.class)
                    .getResultList();
        } catch (Exception ex) {
            System.out.println("Error obtenerXDesc: " + ex.getLocalizedMessage());
        }
        return lista;
    }
}
