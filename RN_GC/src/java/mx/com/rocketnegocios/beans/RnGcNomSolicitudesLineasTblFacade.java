/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import javax.annotation.security.PermitAll;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcNomSolicitudesLineasTbl;

/**
 *
 * @author LenovoZ40
 */
@Stateless
@PermitAll
public class RnGcNomSolicitudesLineasTblFacade extends AbstractFacade<RnGcNomSolicitudesLineasTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcNomSolicitudesLineasTblFacade() {
        super(RnGcNomSolicitudesLineasTbl.class);
    }

    public RnGcNomSolicitudesLineasTbl refreshFromDB(RnGcNomSolicitudesLineasTbl soliLineas) {
        RnGcNomSolicitudesLineasTbl soliLineasLocal = null;
        soliLineasLocal = em.merge(soliLineas);
        return soliLineasLocal;
    }

    public List<RnGcNomSolicitudesLineasTbl> obtenerPercepciones(Integer soliTrabajador) {
        List<RnGcNomSolicitudesLineasTbl> listaPercepciones = null;
        try {
            listaPercepciones = em.createNamedQuery("RnGcNomSolicitudesLineasTbl.obtenerPercepciones", RnGcNomSolicitudesLineasTbl.class)
                    .setParameter("solicitudTrabajadorId", soliTrabajador)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No se encontraron percepciones");
        }
        return listaPercepciones;
    }

    public List<RnGcNomSolicitudesLineasTbl> obtenerPercepcionesConTipoRegistro(Integer soliTrabajador) {
        List<RnGcNomSolicitudesLineasTbl> listaPercepciones = null;
        try {
            listaPercepciones = em.createNamedQuery("RnGcNomSolicitudesLineasTbl.obtenerPercepcionesTipoRegistro", RnGcNomSolicitudesLineasTbl.class)
                    .setParameter("solicitudTrabajadorId", soliTrabajador)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No se encontraron percepciones");
        }
        return listaPercepciones;
    }

    public List<RnGcNomSolicitudesLineasTbl> obtenerDeduccionesConTipoRegistro(Integer soliTrabajador) {
        List<RnGcNomSolicitudesLineasTbl> listaDeducciones = null;
        try {
            listaDeducciones = em.createNamedQuery("RnGcNomSolicitudesLineasTbl.obtenerDeduccionesTipoRegistro", RnGcNomSolicitudesLineasTbl.class)
                    .setParameter("solicitudTrabajadorId", soliTrabajador)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No se encontraron percepciones");
        }
        return listaDeducciones;
    }

    public List<RnGcNomSolicitudesLineasTbl> obtenerOtrosPagosConTipoRegistro(Integer soliTrabajador) {
        List<RnGcNomSolicitudesLineasTbl> listaOtrosPagos = null;
        try {
            listaOtrosPagos = em.createNamedQuery("RnGcNomSolicitudesLineasTbl.obtenerOtrosPagosTipoRegistro", RnGcNomSolicitudesLineasTbl.class)
                    .setParameter("solicitudTrabajadorId", soliTrabajador)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No se encontraron percepciones");
        }
        return listaOtrosPagos;
    }

    public List<RnGcNomSolicitudesLineasTbl> obtenerIncapacidadConTipoRegistro(Integer soliTrabajador) {
        List<RnGcNomSolicitudesLineasTbl> listaIncapacidad = null;
        try {
            listaIncapacidad = em.createNamedQuery("RnGcNomSolicitudesLineasTbl.obtenerIncapacidadTipoRegistro", RnGcNomSolicitudesLineasTbl.class)
                    .setParameter("solicitudTrabajadorId", soliTrabajador)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No se encontraron percepciones");
        }
        return listaIncapacidad;
    }

    public List<RnGcNomSolicitudesLineasTbl> obtenerDeducciones(Integer soliTrabajador) {
        List<RnGcNomSolicitudesLineasTbl> listaDeducciones = null;
        try {
            listaDeducciones = em.createNamedQuery("RnGcNomSolicitudesLineasTbl.obtenerDeducciones", RnGcNomSolicitudesLineasTbl.class)
                    .setParameter("solicitudTrabajadorId", soliTrabajador)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No se encontraron deducciones");
        }
        return listaDeducciones;
    }

    public List<RnGcNomSolicitudesLineasTbl> obtenerIncapacidad(Integer soliTrabajador) {
        List<RnGcNomSolicitudesLineasTbl> listaIncapacidad = null;
        try {
            listaIncapacidad = em.createNamedQuery("RnGcNomSolicitudesLineasTbl.obtenerIncapacidad", RnGcNomSolicitudesLineasTbl.class)
                    .setParameter("solicitudTrabajadorId", soliTrabajador)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No se encontraron deducciones");
        }
        return listaIncapacidad;
    }

    public List<RnGcNomSolicitudesLineasTbl> obtenerOtrosPagos(Integer soliTrabajador) {
        List<RnGcNomSolicitudesLineasTbl> listaOtrosPagos = null;
        try {
            listaOtrosPagos = em.createNamedQuery("RnGcNomSolicitudesLineasTbl.obtenerIncapacidad", RnGcNomSolicitudesLineasTbl.class)
                    .setParameter("solicitudTrabajadorId", soliTrabajador)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No se encontraron deducciones");
        }
        return listaOtrosPagos;
    }

    public List<RnGcNomSolicitudesLineasTbl> obtenerXSoliTrabajador(Integer soliTrabajadorUno, Integer soliTrabajadorDos) {
        List<RnGcNomSolicitudesLineasTbl> listaLineas = null;
        try {
            listaLineas = em.createNamedQuery("RnGcNomSolicitudesLineasTbl.findBySoliTrabajador", RnGcNomSolicitudesLineasTbl.class)
                    .setParameter("soliTrabajadorUno", soliTrabajadorUno)
                    .setParameter("soliTrabajadorDos", soliTrabajadorDos)
                    .getResultList();
        } catch (NoResultException ex) {
            System.err.println("No se encontraron lineas");
        }
        return listaLineas;
    }

    public List<RnGcNomSolicitudesLineasTbl> obtenerXTrabajadorId(Integer solicitudTrabajadorId) {
        List<RnGcNomSolicitudesLineasTbl> listaLineas = new ArrayList<>();
        try {
            listaLineas = em.createNamedQuery("RnGcNomSolicitudesLineasTbl.findBySolicitudTrabajadorId", RnGcNomSolicitudesLineasTbl.class)
                    .setParameter("solicitudTrabajadorId", solicitudTrabajadorId)
                    .getResultList();
        } catch (NoResultException ex) {
            System.err.println("No se encontraron líneas para el trabajador con ID: " + solicitudTrabajadorId);
        }
        return listaLineas;
    }

    public BigDecimal obtenerTotalPercepciones(Integer soliTrabajadorId) {
        try {
            BigDecimal total = em.createNamedQuery("RnGcNomSolicitudesLineasTbl.obtenerTotalPercepciones", BigDecimal.class)
                    .setParameter("solicitudTrabajadorId", soliTrabajadorId)
                    .getSingleResult();

            // Si viene null, devuelvo 0.00
            if (total == null) {
                return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
            }
            // Devuelvo con 2 decimales
            return total.setScale(2, RoundingMode.HALF_UP);

        } catch (NoResultException ex) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
    }
    
    public BigDecimal obtenerTotalDeducciones(Integer soliTrabajadorId) {
        try {
            BigDecimal total = em.createNamedQuery("RnGcNomSolicitudesLineasTbl.obtenerTotalDeducciones", BigDecimal.class)
                    .setParameter("solicitudTrabajadorId", soliTrabajadorId)
                    .getSingleResult();

            // Si viene null, devuelvo 0.00
            if (total == null) {
                return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
            }
            // Devuelvo con 2 decimales
            return total.setScale(2, RoundingMode.HALF_UP);

        } catch (NoResultException ex) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
    }
    
    public BigDecimal obtenerTotalOtrosPagos(Integer soliTrabajadorId) {
        try {
            BigDecimal total = em.createNamedQuery("RnGcNomSolicitudesLineasTbl.obtenerTotalOtrosPagos", BigDecimal.class)
                    .setParameter("solicitudTrabajadorId", soliTrabajadorId)
                    .getSingleResult();

            // Si viene null, devuelvo 0.00
            if (total == null) {
                return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
            }
            // Devuelvo con 2 decimales
            return total.setScale(2, RoundingMode.HALF_UP);

        } catch (NoResultException ex) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
    }
    
     public BigDecimal obtenerTotalIncapacidad(Integer soliTrabajadorId) {
        try {
            BigDecimal total = em.createNamedQuery("RnGcNomSolicitudesLineasTbl.obtenerTotalIncapacidad", BigDecimal.class)
                    .setParameter("solicitudTrabajadorId", soliTrabajadorId)
                    .getSingleResult();

            // Si viene null, devuelvo 0.00
            if (total == null) {
                return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
            }
            // Devuelvo con 2 decimales
            return total.setScale(2, RoundingMode.HALF_UP);

        } catch (NoResultException ex) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
    }

}
