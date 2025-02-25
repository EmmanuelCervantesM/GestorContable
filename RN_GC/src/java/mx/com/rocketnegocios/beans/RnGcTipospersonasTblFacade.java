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
import mx.com.rocketnegocios.entities.RnGcTipospersonasTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcTipospersonasTblFacade extends AbstractFacade<RnGcTipospersonasTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcTipospersonasTblFacade() {
        super(RnGcTipospersonasTbl.class);
    }

    public RnGcTipospersonasTbl obtenerTipoPersona(String TipoPersonaStr) {
        RnGcTipospersonasTbl tipoPersonaId = null;
        try {
            tipoPersonaId = em.createNamedQuery("RnGcTipospersonasTbl.findByTipoPersona", RnGcTipospersonasTbl.class)
                    .setParameter("tipoPersona", TipoPersonaStr)
                    .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("No encontro tipoPersona");
        }
        return tipoPersonaId;
    }

}
