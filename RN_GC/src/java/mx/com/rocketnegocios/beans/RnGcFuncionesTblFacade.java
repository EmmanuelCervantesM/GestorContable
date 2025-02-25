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
import mx.com.rocketnegocios.entities.RnGcFuncionesTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcFuncionesTblFacade extends AbstractFacade<RnGcFuncionesTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcFuncionesTblFacade() {
        super(RnGcFuncionesTbl.class);
    }

    public List<RnGcFuncionesTbl> obtenerFunciones(RnGcFuncionesTbl funcionesId) {
        List<RnGcFuncionesTbl> listaFunciones = null;
        System.out.println("*********** Entro a obtener menuLineas con el id de funciones: " + funcionesId);
        try {
            listaFunciones = em.createNamedQuery("RnGcFuncionesTbl.findById", RnGcFuncionesTbl.class)
                    .setParameter("id", funcionesId)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No se encontro listaFunciones con funcionesId");
        }
        return listaFunciones;
    }

}
