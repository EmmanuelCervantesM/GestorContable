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
import mx.com.rocketnegocios.entities.RnGcCatalogosusosTbl;
import mx.com.rocketnegocios.entities.RnGcRegimenUsoTbl;
import mx.com.rocketnegocios.entities.RnGcRegimenfiscalTbl;

/**
 *
 * @author maxoc
 */
@Stateless
public class RnGcRegimenUsoTblFacade extends AbstractFacade<RnGcRegimenUsoTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcRegimenUsoTblFacade() {
        super(RnGcRegimenUsoTbl.class);
    }
    
    public List<RnGcRegimenUsoTbl> obtenerUsoXRegimen(RnGcRegimenfiscalTbl regimen, String tipo){
        List<RnGcRegimenUsoTbl> lista = null;
        String valor = "";
        try {
            if(tipo.equals("Fisica"))
                valor = "RnGcRegimenUsoTbl.findByRegimenFisico";
            else
                valor = "RnGcRegimenUsoTbl.findByRegimenMoral";
            lista = em.createNamedQuery(valor, RnGcRegimenUsoTbl.class)
                    .setParameter("regimen",regimen)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("Error obtenerUsoXRegimen: " + ex.getLocalizedMessage());
        }
        return lista;
    }
    
    public List<RnGcRegimenUsoTbl> obtenerXUsoRegimen(RnGcRegimenfiscalTbl regimen, RnGcCatalogosusosTbl uso){
        List<RnGcRegimenUsoTbl> lista = null;
        try {
            lista = em.createNamedQuery("RnGcRegimenUsoTbl.findByRegimenUso", RnGcRegimenUsoTbl.class)
                    .setParameter("regimen",regimen)
                    .setParameter("uso",uso)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("Error obtenerXUsoRegimen: " + ex.getLocalizedMessage());
        }
        return lista;
    }
}
