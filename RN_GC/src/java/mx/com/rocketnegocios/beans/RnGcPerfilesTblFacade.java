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
import mx.com.rocketnegocios.entities.RnGcPerfilesTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcPerfilesTblFacade extends AbstractFacade<RnGcPerfilesTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcPerfilesTblFacade() {
        super(RnGcPerfilesTbl.class);
    }

    public List<RnGcPerfilesTbl> listaTiposPerfilesPorUsuario2(String tipoPer) {
        List<RnGcPerfilesTbl> tipoPerfil = null;
        System.out.println("Entro al facade de perfiles Co tipo de usurio: " + tipoPerfil);
        try {
            if (tipoPer.equals("AG")) {
                System.out.println("Entro a AG");
                tipoPerfil = em.createNamedQuery("RnGcPerfilesTbl.findAll", RnGcPerfilesTbl.class)
                        //.setParameter("listaperfiles", tipoPer)
                        .getResultList();
            } else if (tipoPer.equals("A")) {
                tipoPerfil = em.createNamedQuery("RnGcPerfilesTbl.findBytipoPerfilA", RnGcPerfilesTbl.class)
                        //.setParameter("listaperfiles", tipoPer)
                        .getResultList();
            } else if (tipoPer.equals("AD")) {
                tipoPerfil = em.createNamedQuery("RnGcPerfilesTbl.findBytipoPerfilAD", RnGcPerfilesTbl.class)
                        //.setParameter("listaperfiles", tipoPer)
                        .getResultList();
            } else if (tipoPer.equals("AC")) {
                tipoPerfil = em.createNamedQuery("RnGcPerfilesTbl.findBytipoPerfilAC", RnGcPerfilesTbl.class)
                        // .setParameter("listaperfiles", tipoPer)
                        .getResultList();
            }

        } catch (NoResultException ex) {
            System.out.println("No hay ningún tipo de perfil");
        }
//        System.out.println("lista tipo perfil 2:  " + tipoPerfil.size());
        return tipoPerfil;
    }
    
    public List<RnGcPerfilesTbl> obtenerXTipoUsuario(String tipoUsuario) {
        List<RnGcPerfilesTbl> perfiles = null;
        try{
            perfiles = em.createNamedQuery("RnGcPerfilesTbl.findByTipoUsuario", RnGcPerfilesTbl.class)
                    .setParameter("tipoUsuario", tipoUsuario)
                    .getResultList();
        }catch (NoResultException ex) {
            System.out.println("No hay ningún tipo de perfil");
        }
        return perfiles;
    }

}
