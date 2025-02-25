/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcCodigoAgrupadorSatTbl;

/**
 *
 * @author Consultor
 */
@Stateless
public class RnGcCodigoAgrupadorSatTblFacade extends AbstractFacade<RnGcCodigoAgrupadorSatTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcCodigoAgrupadorSatTblFacade() {
        super(RnGcCodigoAgrupadorSatTbl.class);
    }
    
       public RnGcCodigoAgrupadorSatTbl getCodigoAgrupador(String codigoAgrupador) {
           System.out.println("Entro al Facade a obtener el codigo agrupador");
        RnGcCodigoAgrupadorSatTbl codigoAgrupadorSat = null;
        try {
            codigoAgrupadorSat = em.createNamedQuery("RnGcCodigoAgrupadorSatTbl.findByCodigoAgrupador", RnGcCodigoAgrupadorSatTbl.class)
                    .setParameter("codigoAgrupador", codigoAgrupador)
                    .getSingleResult();
            System.out.println("El Codigo Agrupador SAT encontrado es: " + codigoAgrupadorSat.getCodigoAgrupador());
        } catch (NoResultException ex) {
            System.out.println("No hay codigo Agrupador Sat con ese codigo");
        }
        return codigoAgrupadorSat;
    }
}
