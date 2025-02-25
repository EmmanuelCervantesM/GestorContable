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
import mx.com.rocketnegocios.entities.RnGcCfdisLineasTbl;
import mx.com.rocketnegocios.entities.RnGcCfdisTbl;

@Stateless
public class RnGcCfdisLineasTblFacade extends AbstractFacade<RnGcCfdisLineasTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcCfdisLineasTblFacade() {
        super(RnGcCfdisLineasTbl.class);
    }

    public RnGcCfdisLineasTbl refreshFromDB(RnGcCfdisLineasTbl cfdiLineaId) {
        RnGcCfdisLineasTbl trxlocal = null;
        trxlocal = em.merge(cfdiLineaId);
        return trxlocal;
    }
    
    public List<RnGcCfdisLineasTbl> obtenerCfdisLineas(RnGcCfdisTbl cfdisId) {
        List<RnGcCfdisLineasTbl> listaCfdisLineas = null;
          System.out.println("Entro a  obtenerCfdisLineas con el cfdisId: "+cfdisId.getId() );
        try {
            listaCfdisLineas = em.createNamedQuery("RnGcCfdisLineasTbl.findByCfdis_Id", RnGcCfdisLineasTbl.class)
                    .setParameter("cfdisId", cfdisId)
                    .getResultList();
        }catch(NoResultException ex) {
            System.out.println("No se encontro CfdisLineas con cfdiId");
        }
          System.out.println("El tamaño de las lineas es: "+listaCfdisLineas.size() );
        return listaCfdisLineas;
    }
    
    public List<RnGcArchivosTbl> obtenerDocs(RnGcCfdisTbl cfdisId) {
        List<RnGcArchivosTbl> listaArchivos = null;
        try {
            listaArchivos = em.createNamedQuery("RnGcArchivosTbl.findByrngccfdistblId", RnGcArchivosTbl.class)
                    .setParameter("cfdiId", cfdisId)
                    .getResultList();
            System.out.println("listaArchivos: " + listaArchivos);
        }catch(NoResultException ex){
            System.out.println("No se encontro Archivos con cfdisId");
        }
        return listaArchivos;
    }

}
