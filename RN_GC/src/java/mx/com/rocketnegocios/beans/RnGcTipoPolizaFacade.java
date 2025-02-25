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
import mx.com.rocketnegocios.entities.RnGcTipoPoliza;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;

/**
 *
 * @author Consultor
 */
@Stateless
public class RnGcTipoPolizaFacade extends AbstractFacade<RnGcTipoPoliza> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcTipoPolizaFacade() {
        super(RnGcTipoPoliza.class);
    }
    
    
    
     public List<RnGcTipoPoliza> obtenerListaPolizas(RnGcUsuariosTbl usuarioId) {
        List<RnGcTipoPoliza> tipoPoliza = null;
        try {
            tipoPoliza = em.createNamedQuery("RnGcTipoPoliza.findByCreadoPor", RnGcTipoPoliza.class)
                    .setParameter("creadoPor", usuarioId.getId())
                    .getResultList();
            System.out.println("El tamaño de la lista de tipos de poliza es: " + tipoPoliza.size() + " para el usuario: " + usuarioId.getNombreCompleto());
        } catch (NoResultException ex) {
            System.out.println("No hay lista de polizas");
        }
        return tipoPoliza;
    }
     
     public RnGcTipoPoliza tipoPoliza(RnGcTipoPoliza id) {
        RnGcTipoPoliza tipoPoliza = null;
        try {
            tipoPoliza = em.createNamedQuery("RnGcTipoPoliza.findById", RnGcTipoPoliza.class)
                    .setParameter("id", id.getId())
                    .getSingleResult();
            System.out.println("La secuencia encontrada es: " + tipoPoliza.getNumeroSecuencia());
        } catch (NoResultException ex) {
            System.out.println("No hay polizas con ese id");
        }
        return tipoPoliza;
    }
}
