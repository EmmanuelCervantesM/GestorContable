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
import mx.com.rocketnegocios.entities.RnGcCodigospostalesTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcCodigospostalesTblFacade extends AbstractFacade<RnGcCodigospostalesTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcCodigospostalesTblFacade() {
        super(RnGcCodigospostalesTbl.class);
    }
    
    public List<RnGcCodigospostalesTbl> buscarporCodigo(int codigoPostal){
        List<RnGcCodigospostalesTbl> listaCodigos = null;
        
        try{
            listaCodigos = em.createNamedQuery("RnGcCodigospostalesTbl.findByCCodigoPostal", RnGcCodigospostalesTbl.class)
                    .setParameter("cCodigoPostal", codigoPostal)
                    .getResultList();
            System.out.println("listaCodigos: " + listaCodigos);
        } catch(NoResultException ex){
            System.out.println("No hay codigos postales");
        }
        return listaCodigos;
    }
    
    public List<RnGcCodigospostalesTbl> buscarPorEstado(String estado) {
        List <RnGcCodigospostalesTbl> listaCodigos = null;
        //System.out.println("estado: " + estado);
        try {
            listaCodigos = em.createNamedQuery("RnGcCodigospostalesTbl.findByCEstado", RnGcCodigospostalesTbl.class)
                    .setParameter("cEstado", estado)
                    .getResultList();
            System.out.println(listaCodigos.size());
        } catch (NoResultException ex) {
            System.out.println("No hay codigos " + ex);
        }
        return listaCodigos;
    }
    
    public String buscarInicioPorEstado(String estado) {
        String codigo = null;
        try {
            codigo = em.createNativeQuery("select c_CodigoPostal from rn_gc_codigospostales_tbl where c_Estado = ? and c_CodigoPostal != 0 limit 1")
                    .setParameter(1, estado)
                    .getSingleResult()
                    .toString();
        } catch (NoResultException ex) {
            System.out.println("No hay codigos " + ex);
        }
        return codigo;
    }
    
    public String buscarFinPorEstado(String estado) {
        String codigo = null;
        try {
            codigo = em.createNativeQuery("select c_CodigoPostal from rn_gc_codigospostales_tbl where c_Estado = ? order by id desc limit 1")
                    .setParameter(1, estado)
                    .getSingleResult()
                    .toString();
        } catch (NoResultException ex) {
            System.out.println("No hay codigos " + ex);
        }
        return codigo;
    }
    
}
