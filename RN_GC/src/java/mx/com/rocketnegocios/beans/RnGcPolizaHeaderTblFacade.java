/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import static com.itextpdf.kernel.pdf.PdfName.T;
import jakarta.annotation.security.PermitAll;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import mx.com.rocketnegocios.entities.RnGcPolizaHeaderTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import org.apache.poi.ss.formula.functions.T;

/**
 *
 * @author Consultor
 */
@Stateless
@PermitAll
public class RnGcPolizaHeaderTblFacade extends AbstractFacade<RnGcPolizaHeaderTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcPolizaHeaderTblFacade() {
        super(RnGcPolizaHeaderTbl.class);
    }
    
    public RnGcPolizaHeaderTbl refreshFromDB(RnGcPolizaHeaderTbl poliza) {
        RnGcPolizaHeaderTbl polizaH = null;
        polizaH = em.merge(poliza);
        return polizaH;
    }
    
     public List<RnGcPolizaHeaderTbl> obtenerListaPolizas(RnGcUsuariosTbl usuarioId) {
        List<RnGcPolizaHeaderTbl> polizas = null;
        try {
            polizas = em.createNamedQuery("RnGcPolizaHeaderTbl.findByCreadoPor", RnGcPolizaHeaderTbl.class)
                    .setParameter("creadoPor", usuarioId.getId())
                    .getResultList();
            System.out.println("El tamaño de la lista de polizas es: " + polizas.size() + " para el usuario: " + usuarioId.getNombreCompleto());
        } catch (NoResultException ex) {
            System.out.println("No hay lista de polizas");
        }
        return polizas;
    }
     
    public void create(T entity) {
        try {
            em.persist(entity);
            em.flush(); // fuerza el INSERT y las validaciones aquí
        } catch (ConstraintViolationException ex) {
            for (ConstraintViolation<?> cv : ex.getConstraintViolations()) {
                System.out.println("VALIDATION ERROR: " 
                    + cv.getRootBeanClass().getSimpleName() + "." 
                    + cv.getPropertyPath() + " " 
                    + cv.getMessage() + " (valor=" + cv.getInvalidValue() + ")");
            }
            throw ex;
        }
    }
    
     public Integer insertPolizaHeaderNativo(RnGcPolizaHeaderTbl header) {

        String sql = "INSERT INTO rn_gc_poliza_header_tbl ("
                + "numeroPoliza, "
                + "tipoPoliza, "
                + "fecha, "
                + "concepto, "
                + "receptor, "
                + "rfcReceptor, "
                + "estatus, "
                + "vigente, "
                + "adicional1, "
                + "adicional2, "
                + "creadoPor, "
                + "fechaCreacion, "
                + "ultimaActualizacionPor, "
                + "ultimaFechaActualizacion, "
                + "tipoCambio, "
                + "tipoMoneda, "
                + "tipoPolizaId, "
                + "periodoId, "
                + "cfdiId"
                + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        Query q = em.createNativeQuery(sql);

        q.setParameter(1,  header.getNumeroPoliza());
        q.setParameter(2,  header.getTipoPoliza());
        q.setParameter(3,  header.getFecha());
        q.setParameter(4,  header.getConcepto());
        q.setParameter(5,  header.getReceptor());
        q.setParameter(6,  header.getRfcReceptor());
        q.setParameter(7,  header.getEstatus());
        q.setParameter(8,  header.getVigente());
        q.setParameter(9,  header.getAdicional1());
        q.setParameter(10, header.getAdicional2());
        q.setParameter(11, header.getCreadoPor());
        q.setParameter(12, header.getFechaCreacion());
        q.setParameter(13, header.getUltimaActualizacionPor());
        q.setParameter(14, header.getUltimaFechaActualizacion());
        q.setParameter(15, header.getTipoCambio());
        q.setParameter(16, header.getTipoMoneda() != null ? header.getTipoMoneda().getId() : null);
        q.setParameter(17, header.getTipoPolizaId() != null ? header.getTipoPolizaId().getId() : null);
        q.setParameter(18, header.getPeriodoId() != null ? header.getPeriodoId().getId() : null);
        q.setParameter(19, header.getCfdiId() != null ? header.getCfdiId().getId() : null);

        q.executeUpdate();

        // Obtener el ID generado (MySQL)
        Integer idGenerado = ((Number) em
                .createNativeQuery("SELECT LAST_INSERT_ID()")
                .getSingleResult())
                .intValue();

        return idGenerado;
    }

    @PermitAll
    public int obtenerConsecutivoPorUsuario(Integer creadoPor) {
        try {
            Long total = em.createNamedQuery("RnGcPolizaHeaderTbl.countByCreadoPor", Long.class)
                    .setParameter("creadoPor", creadoPor)
                    .getSingleResult();

            System.out.println("Total de líneas para usuario " + creadoPor + ": " + total);

            return total.intValue() + 1;

        } catch (Exception e) {
            System.out.println("Error al obtener consecutivo por usuario: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
}
