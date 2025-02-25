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
import mx.com.rocketnegocios.entities.RnGcArchivosTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosPerfilesTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcUsuariosPerfilesTblFacade extends AbstractFacade<RnGcUsuariosPerfilesTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcUsuariosPerfilesTblFacade() {
        super(RnGcUsuariosPerfilesTbl.class);
    }

    public RnGcUsuariosPerfilesTbl refreshFromDB(RnGcUsuariosPerfilesTbl usuarioPerfilId) {
        RnGcUsuariosPerfilesTbl usuarioPerfillocal = null;
        usuarioPerfillocal = em.merge(usuarioPerfilId);
        return usuarioPerfillocal;
    }

    public List<RnGcUsuariosPerfilesTbl> obtenerPerfilesPorUsuario(RnGcUsuariosTbl usuarioId) {
        List<RnGcUsuariosPerfilesTbl> listaUsuarioPerfiles = null;
        try {
            listaUsuarioPerfiles = em.createNamedQuery("RnGeUsuarioPerfilesTbl.findByUsuarioId", RnGcUsuariosPerfilesTbl.class)
                    .setParameter("usuariosId", usuarioId)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No se encontro listaUsuariosPerfiles con usuarioId");
        }
        return listaUsuarioPerfiles;
    }

    public RnGcUsuariosPerfilesTbl obtenerPerfilUsuario(RnGcUsuariosTbl usuarioId) {
        RnGcUsuariosPerfilesTbl usuarioPerfiles = null;
        try {
            usuarioPerfiles = em.createNamedQuery("RnGeUsuarioPerfilesTbl.findByUsuarioId", RnGcUsuariosPerfilesTbl.class)
                    .setParameter("usuariosId", usuarioId)
                    .getSingleResult();
            System.out.println("usuarioPerfiles: " + usuarioPerfiles);
        } catch (NoResultException ex) {
            System.out.println("No se encontro UsuariosPerfiles con usuarioId: " + ex.getLocalizedMessage() + " | " + ex.getMessage() + " | " + ex.toString());
            ex.printStackTrace();
        }
        return usuarioPerfiles;
    }

    public RnGcUsuariosPerfilesTbl obtenerUsuario(RnGcUsuariosTbl usuarioId) {
        RnGcUsuariosPerfilesTbl usuarioPerfiles = null;
        try {
            usuarioPerfiles = em.createNamedQuery("RnGeUsuarioPerfilesTbl.findByUsuarioId", RnGcUsuariosPerfilesTbl.class)
                    .setParameter("usuariosId", usuarioId)
                    .getSingleResult();
            System.out.println("usuarioPerfiles: " + usuarioPerfiles);
        } catch (NoResultException ex) {
            System.out.println("No se encontro UsuariosPerfiles con usuarioId: " + ex.getLocalizedMessage() + " | " + ex.getMessage() + " | " + ex.toString());
            ex.printStackTrace();
        }
        return usuarioPerfiles;
    }

}
