/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
    
    public List<RnGcPeriodosTbl> obtenerPeriodoIdDesc(RnGcUsuariosTbl usuario){
        List<RnGcPeriodosTbl> lista = new ArrayList<>();
        try{
            lista = em.createNamedQuery("RnGcPeriodosTbl.findByPeriodoId", RnGcPeriodosTbl.class)
                    .setParameter("usuario", usuario)
                    .getResultList();
        }catch (Exception ex) {
            System.out.println("Error obtenerPeriodoIdDesc: " + ex.getLocalizedMessage());
        }
        return lista;
    }
}
