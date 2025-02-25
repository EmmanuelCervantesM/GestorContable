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
import mx.com.rocketnegocios.entities.RnGcCpUnidadesParteTransporteTbl;
import mx.com.rocketnegocios.entities.RnGcCpUnidadesTbl;

/**
 *
 * @author maxoc
 */
@Stateless
public class RnGcCpUnidadesParteTransporteTblFacade extends AbstractFacade<RnGcCpUnidadesParteTransporteTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcCpUnidadesParteTransporteTblFacade() {
        super(RnGcCpUnidadesParteTransporteTbl.class);
    }
    
    public List<RnGcCpUnidadesParteTransporteTbl> obtenerXunidad(RnGcCpUnidadesTbl unidad){
        List<RnGcCpUnidadesParteTransporteTbl> lista = null;
        try {
            lista = em.createNamedQuery("RnGcCpUnidadesParteTransporteTbl.findByUnidadId", RnGcCpUnidadesParteTransporteTbl.class)
                    .setParameter("unidadId", unidad)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("Error obtenerXunidad: " + ex.getLocalizedMessage());
        }
        return lista;
    }
}
