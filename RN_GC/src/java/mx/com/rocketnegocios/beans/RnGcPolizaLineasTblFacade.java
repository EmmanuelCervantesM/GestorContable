/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import jakarta.annotation.security.PermitAll;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import mx.com.rocketnegocios.entities.RnGcPolizaHeaderTbl;
import mx.com.rocketnegocios.entities.RnGcPolizaLineasTbl;

/**
 *
 * @author Consultor
 */
@Stateless
public class RnGcPolizaLineasTblFacade extends AbstractFacade<RnGcPolizaLineasTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcPolizaLineasTblFacade() {
        super(RnGcPolizaLineasTbl.class);
    }
    
    public RnGcPolizaLineasTbl refreshFromDB(RnGcPolizaLineasTbl poliza) {
        RnGcPolizaLineasTbl polizaL = null;
        polizaL = em.merge(poliza);
        return polizaL;
    }
    
     public List<RnGcPolizaLineasTbl> obtenerPolizaLineas(RnGcPolizaHeaderTbl polizaHeaderId) {
        List<RnGcPolizaLineasTbl> listaPolizaLineas = null;
        try {
            listaPolizaLineas = em.createNamedQuery("RnGcPolizaLineasTbl.findByPolizaHeaderId", RnGcPolizaLineasTbl.class)
                    .setParameter("polizaHeaderId", polizaHeaderId)
                    .getResultList();
        }catch (NoResultException ex){
            System.out.println("No encontro lista de poliza lineas con polizaHeaderId"+polizaHeaderId.getId());
        }
        return listaPolizaLineas;        
    }
    
    public void insertPolizaLineaNativa(RnGcPolizaLineasTbl linea, int headerId, Integer periodoId) {

        String sql = "INSERT INTO rn_gc_poliza_lineas_tbl ("
                + "sucursal, "
                + "polizaHeaderId, "
                + "catalogoCuentasId, "
                + "cargo, "
                + "abono, "
                + "referencia, "
                + "concepto, "
                + "creadoPor, "
                + "fechaCreacion, "
                + "ultimaActualizacionPor, "
                + "ultimaFechaActualizacion, "
                + "id_periodo"
                + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

        Query q = em.createNativeQuery(sql);

        q.setParameter(1,  linea.getSucursal());
        q.setParameter(2,  headerId);
        q.setParameter(3,  linea.getCatalogoCuentasId().getId());
        q.setParameter(4,  linea.getCargo());
        q.setParameter(5,  linea.getAbono());
        q.setParameter(6,  linea.getReferencia());
        q.setParameter(7,  linea.getConcepto());
        q.setParameter(8,  linea.getCreadoPor());
        q.setParameter(9,  linea.getFechaCreacion());
        q.setParameter(10, linea.getUltimaActualizacionPor());
        q.setParameter(11, linea.getUltimaFechaActualizacion());
        q.setParameter(12, periodoId);

        q.executeUpdate();
    }

    public List<RnGcPolizaLineasTbl> findByPolizaHeader(RnGcPolizaHeaderTbl header) {
        return em.createQuery(
                    "SELECT l FROM RnGcPolizaLineasTbl l WHERE l.polizaHeaderId = :header ORDER BY l.id",
                    RnGcPolizaLineasTbl.class)
                 .setParameter("header", header)
                 .getResultList();
    }

}
