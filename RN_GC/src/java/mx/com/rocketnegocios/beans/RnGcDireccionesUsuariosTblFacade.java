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
import mx.com.rocketnegocios.entities.RnGcDireccionesUsuariosTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcDireccionesUsuariosTblFacade extends AbstractFacade<RnGcDireccionesUsuariosTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcDireccionesUsuariosTblFacade() {
        super(RnGcDireccionesUsuariosTbl.class);
    }
    
    public List<RnGcDireccionesUsuariosTbl> obtenerDireccionesporUsuario(RnGcUsuariosTbl usuarioId){
        List <RnGcDireccionesUsuariosTbl> listaDireccionesUsuarios = null;
        try {
            listaDireccionesUsuarios = em.createNamedQuery("RnGcDireccionesUsuariosTbl.findByUsuarioId", RnGcDireccionesUsuariosTbl.class)
                    .setParameter("usuarioId", usuarioId)
                    .getResultList();
        }catch(NoResultException ex){
            System.out.println("No hay direcciones con ese usuario");
        }
        return listaDireccionesUsuarios;
    }
    
}
