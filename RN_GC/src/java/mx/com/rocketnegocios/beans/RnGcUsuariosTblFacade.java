package mx.com.rocketnegocios.beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;

@Stateless
public class RnGcUsuariosTblFacade extends AbstractFacade<RnGcUsuariosTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcUsuariosTblFacade() {
        super(RnGcUsuariosTbl.class);
    }

    public RnGcUsuariosTbl refreshFromDB(RnGcUsuariosTbl usuarioId) {
        RnGcUsuariosTbl usuariolocal = null;
        usuariolocal = em.merge(usuarioId);
        return usuariolocal;
    }

    public RnGcUsuariosTbl obtenerUsuarioPorClave(String usuarioClave) {
        RnGcUsuariosTbl usuarioId = null;
        System.out.println("usuarioClave: " + usuarioClave);
        try {
            usuarioId = em.createNamedQuery("RnGcUsuariosTbl.findByUsuarioClave", RnGcUsuariosTbl.class)
                    .setParameter("usuarioClave", usuarioClave)
                    .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("No se encontro usuarioId utilizando usuarioClave");
        }
        return usuarioId;
    }

    public RnGcUsuariosTbl obtenerUsuarioId(String NombreCompleto) {
        RnGcUsuariosTbl usuarioId = null;
        System.out.println("obtenerUsuarioId");
        try {
            usuarioId = em.createNamedQuery("RnGcUsuariosTbl.findByNombreCompleto", RnGcUsuariosTbl.class)
                    .setParameter("nombreCompleto", NombreCompleto)
                    .getSingleResult();
            System.out.println("obtenerUsuarioId: " + usuarioId);
        } catch (NoResultException ex) {
            System.out.println("No hay usuarioId");
        }
        return usuarioId;
    }

    public RnGcUsuariosTbl obtenerUsuarioPorId(int Id) {
        RnGcUsuariosTbl usuarioId = new RnGcUsuariosTbl();
        try {
            usuarioId = em.createNamedQuery("RnGcUsuariosTbl.findById", RnGcUsuariosTbl.class)
                    .setParameter("id", Id)
                    .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("No hay Usuario");
        }
        return usuarioId;
    }

    public List<RnGcUsuariosTbl> obtenerPorUsuario(RnGcUsuariosTbl usuarioId) {
        List<RnGcUsuariosTbl> listaUsuarios = null;

        try {
            listaUsuarios = em.createNamedQuery("RnGcUsuariosTbl.findByUsuarioId", RnGcUsuariosTbl.class)
                    .setParameter("usuarioId", usuarioId)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No hay usuarios");
        }

        return listaUsuarios;
    }

    public List<RnGcUsuariosTbl> buscaCreadoPor(int creadoPor) {
        List<RnGcUsuariosTbl> listaUsuarios = null;
        try {
            listaUsuarios = em.createNamedQuery("RnGcUsuariosTbl.findByCreadoPor", RnGcUsuariosTbl.class)
                    .setParameter("creadoPor", creadoPor)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No hay items creados");
        }
        return listaUsuarios;
    }

    public List<RnGcUsuariosTbl> obtenerPorRfcClave(String rfc, String usuarioClave) {
        List<RnGcUsuariosTbl> listaUsuarioId = null;
        try {
            listaUsuarioId = em.createNamedQuery("RnGcUsuariosTbl.findByRfcClave", RnGcUsuariosTbl.class)
                    .setParameter("rfc", rfc)
                    .setParameter("usuarioClave", usuarioClave)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No se encontro usuario con el RFC: " + rfc);
        }
        return listaUsuarioId;
    }

    public List<RnGcUsuariosTbl> obtenerListaUsuarioPorId(int Id) {
        List<RnGcUsuariosTbl> usuarioId = null;
        try {
            usuarioId = em.createNamedQuery("RnGcUsuariosTbl.findById", RnGcUsuariosTbl.class)
                    .setParameter("id", Id)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No hay Usuario");
        }
        return usuarioId;
    }

    public List<RnGcUsuariosTbl> obtenerListaUsuario(RnGcUsuariosTbl usuarioId) {
        List<RnGcUsuariosTbl> usuario = null;
        try {
            usuario = em.createNamedQuery("RnGcUsuariosTbl.findByUsuarioId", RnGcUsuariosTbl.class)
                    .setParameter("usuarioId", usuarioId)
                    .getResultList();
            //System.out.println("El tamaño de la lista de usuarios es: " + usuario.size() + " para el usuario: " + usuarioId.getNombreCompleto());
        } catch (NoResultException ex) {
            System.out.println("No hay Usuario");
        }
        return usuario;
    }

    public RnGcUsuariosTbl obtenerUsuario(RnGcUsuariosTbl usuarioId) {
        RnGcUsuariosTbl usuario = null;
        try {
            usuario = em.createNamedQuery("RnGcUsuariosTbl.findByUsuarioId", RnGcUsuariosTbl.class)
                    .setParameter("usuarioId", usuarioId)
                    .getSingleResult();
            System.out.println("usuario: " + usuario);
        } catch (NoResultException ex) {
            System.out.println("No se encontro Usuarios con usuarioId: " + ex.getLocalizedMessage() + " | " + ex.getMessage() + " | " + ex.toString());
            ex.printStackTrace();
        }
        return usuario;
    }

    public RnGcUsuariosTbl obtenerRfcUsuario(String rfc) {
        RnGcUsuariosTbl usuarioRfc = null;
        try {
            usuarioRfc = em.createNamedQuery("RnGcUsuariosTbl.findByRfc", RnGcUsuariosTbl.class)
                    .setParameter("rfc", rfc)
                    .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("No se encontro usuario con el RFC: " + rfc);
        }
        return usuarioRfc;
    }

    public RnGcUsuariosTbl obtenerNoUsuariosPorUsuario(int noUsuarios, RnGcUsuariosTbl usuario) {
        RnGcUsuariosTbl numUsuarios = null;
        try {
            numUsuarios = em.createNamedQuery("RnGcUsuariosTbl.findByNoUsuarios", RnGcUsuariosTbl.class)
                    .setParameter("noUsuarios", noUsuarios)
                    .setParameter("usuarioId", usuario)
                    .getSingleResult();
            System.out.println("El número de usuarios es: " + numUsuarios);
        } catch (NoResultException ex) {
            System.out.println("No se encontro ningún usuario: " + noUsuarios);
        }
        return numUsuarios;
    }
    
    public List<RnGcUsuariosTbl> buscarTodo() {
        List<RnGcUsuariosTbl> usuario = null;
        try {
            usuario = em.createNamedQuery("RnGcUsuariosTbl.findAll", RnGcUsuariosTbl.class)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No hay Usuario");
        }
        return usuario;
    }
}
