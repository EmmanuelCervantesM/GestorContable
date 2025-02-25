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
import mx.com.rocketnegocios.entities.RnGcCpColoniasatTbl;

/**
 *
 * @author maxoc
 */
@Stateless
public class RnGcCpColoniasatTblFacade extends AbstractFacade<RnGcCpColoniasatTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcCpColoniasatTblFacade() {
        super(RnGcCpColoniasatTbl.class);
    }
    
    public List<RnGcCpColoniasatTbl> obtenerXcodigo(String codigo){
        List<RnGcCpColoniasatTbl> cod = null;
        try {
            cod = em.createNamedQuery("RnGcCpColoniasatTbl.findByCodigoPostal", RnGcCpColoniasatTbl.class)
                    .setParameter("codigoPostal", codigo)
                    .getResultList();
            
        }catch (NoResultException ex){
            System.out.println("Error en obtener por codigo postal");
        }
        return cod;
    }
    
    public List<RnGcCpColoniasatTbl> obtenerXcpFI(String inicial, String finals){
        List<RnGcCpColoniasatTbl> lista = null;
        try {
            lista = em.createNamedQuery("RnGcCpColoniasatTbl.findByCodigoPostalFI", RnGcCpColoniasatTbl.class)
                    .setParameter("inicial", inicial)
                    .setParameter("finals", finals)
                    .getResultList();
        }catch (NoResultException ex){
            System.out.println("Error en obtener por codigo postal");
        }
        return lista;
    }
    
}
