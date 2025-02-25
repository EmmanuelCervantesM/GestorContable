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
import mx.com.rocketnegocios.entities.RnGcMenusTbl;
import mx.com.rocketnegocios.entities.RnGcMenuslineasTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcMenuslineasTblFacade extends AbstractFacade<RnGcMenuslineasTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcMenuslineasTblFacade() {
        super(RnGcMenuslineasTbl.class);
    }

    public List<RnGcMenuslineasTbl> obtenerFuncionesPorMenu(RnGcMenusTbl menuId) {
        List<RnGcMenuslineasTbl> listaFuncionesMenu = null;
        try {
            listaFuncionesMenu = em.createNamedQuery("RnGcMenuslineasTbl.findByMenuId", RnGcMenuslineasTbl.class)
                    .setParameter("menuId", menuId)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No se encontro listaFuncionesMenu con menuId");
        }
        return listaFuncionesMenu;
    }
}
