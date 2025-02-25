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
import mx.com.rocketnegocios.entities.RnGcNomTipocontratoTbl;

/**
 *
 * @author LenovoZ40
 */
@Stateless
public class RnGcNomTipocontratoTblFacade extends AbstractFacade<RnGcNomTipocontratoTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcNomTipocontratoTblFacade() {
        super(RnGcNomTipocontratoTbl.class);
    }
    
    public RnGcNomTipocontratoTbl obtenerXClave(String claveTipo){
        RnGcNomTipocontratoTbl tipoContrato = null;
        try{
            tipoContrato = em.createNamedQuery("RnGcNomTipocontratoTbl.findByCveTipoContrato", RnGcNomTipocontratoTbl.class)
                    .setParameter("cveTipoContrato", claveTipo)
                    .getSingleResult();
        }catch(NoResultException ex){
            System.out.println("No se encontro tipoContratoXClave");
        }
        return tipoContrato;
    }
    
    public RnGcNomTipocontratoTbl obtenerXId(Integer tipoId){
        RnGcNomTipocontratoTbl tipoContrato = null;
        try{
            tipoContrato = em.createNamedQuery("RnGcNomTipocontratoTbl.findById", RnGcNomTipocontratoTbl.class)
                    .setParameter("id", tipoId)
                    .getSingleResult();
        }catch(NoResultException ex){
            System.out.println("No se encontraron tipoContratoXId");
        }
        return tipoContrato;
    }
    
}
