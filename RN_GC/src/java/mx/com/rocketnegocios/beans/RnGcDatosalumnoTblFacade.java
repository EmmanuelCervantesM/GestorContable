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
import mx.com.rocketnegocios.entities.RnGcDatosalumnoTbl;
import mx.com.rocketnegocios.entities.RnGcPersonasTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcDatosalumnoTblFacade extends AbstractFacade<RnGcDatosalumnoTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcDatosalumnoTblFacade() {
        super(RnGcDatosalumnoTbl.class);
    }
    
    public List<RnGcDatosalumnoTbl> obtenerAlumnosPorPersona(RnGcPersonasTbl personaId){
        List<RnGcDatosalumnoTbl> listaDirecciones = null;
        
        try {
            listaDirecciones = em.createNamedQuery("RnGcDatosalumnoTbl.findByPersonaId", RnGcDatosalumnoTbl.class)
                    .setParameter("personasId", personaId)
                    .getResultList();
            System.out.println("listadirecciones: " + listaDirecciones);
        }catch (NoResultException ex){
            System.out.println("No hay direcciones para esa persona");
        }
        
        return listaDirecciones;
    }
    
}
