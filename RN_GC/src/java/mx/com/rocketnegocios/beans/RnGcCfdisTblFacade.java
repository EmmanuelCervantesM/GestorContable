/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcArchivosTbl;
import mx.com.rocketnegocios.entities.RnGcCfdisTbl;

@Stateless
public class RnGcCfdisTblFacade extends AbstractFacade<RnGcCfdisTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcCfdisTblFacade() {
        super(RnGcCfdisTbl.class);
    }

    public RnGcCfdisTbl refreshFromDB(RnGcCfdisTbl cfdiId) {
        RnGcCfdisTbl cfdilocal = null;
        cfdilocal = em.merge(cfdiId);
        return cfdilocal;
    }

    public List<RnGcCfdisTbl> obtenerTipoComprobante(String tipoComprobante) {
        List<RnGcCfdisTbl> tipoComp = null;
        try {
            tipoComp = em.createNamedQuery("RnGcCfdisTbl.findByTipoComprobante", RnGcCfdisTbl.class)
                    .setParameter("tipoComprobante", tipoComprobante)
                    .getResultList();
            System.out.println("tipoComp1: " + tipoComp);
        } catch (NoResultException ex) {
            System.out.println("No encontro tipoComprobante");
        }
        return tipoComp;
    }//*/

    public RnGcCfdisTbl obtenerPorUuid(String Uuid) {
        RnGcCfdisTbl cfdi = null;
        try {
            cfdi = em.createNamedQuery("RnGcCfdisTbl.findByUuid", RnGcCfdisTbl.class)
                    .setParameter("uuid", Uuid)
                    .getSingleResult();
            System.out.println("cfdi:" + cfdi);
        } catch (NoResultException ex) {
            System.out.println("No encontro tipoComprobante");
        }
        return cfdi;
    }

    public RnGcArchivosTbl obtenerArchivoId(RnGcCfdisTbl cfdisId) {
        RnGcArchivosTbl archivos = null;
        try {
            archivos = em.createNamedQuery("RnGcArchivosTbl.findByrngccfdistblId", RnGcArchivosTbl.class)
                    .setParameter("cfdiId", cfdisId)
                    .getSingleResult();
            System.out.println("listaArchivos: " + archivos);
        } catch (NoResultException ex) {
            System.out.println("No se encontraron Archivos con cfdisId");
        }
        return archivos;
    }

    public List<RnGcCfdisTbl> obtenerCreadoPor(Integer creadoPor) {
        List<RnGcCfdisTbl> listaCfdis = null;
        try {
            listaCfdis = em.createNamedQuery("RnGcCfdisTbl.findByCreadoPor", RnGcCfdisTbl.class)
                    .setParameter("creadoPor", creadoPor)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No hay datos");
        }
        return listaCfdis;
    }

    public List<RnGcCfdisTbl> tipoComprobanteCreadoPor(String TipoComprobante, Integer creadoPor) {
        List<RnGcCfdisTbl> listaCfdis = null;
        try {
            listaCfdis = em.createNamedQuery("RnGcCfdisTbl.findTipoComprobanteCreadoPor", RnGcCfdisTbl.class)
                    .setParameter("tipoComprobante", TipoComprobante)
                    .setParameter("creadoPor", creadoPor)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No hay datos");
        }

        return listaCfdis;
    }

    public List<RnGcCfdisTbl> obtenerEmitidos(String Rfc) {
        List<RnGcCfdisTbl> listaEmitidos = null;
        try {
            listaEmitidos = em.createNamedQuery("RnGcCfdisTbl.findByRfcEmisorUUID", RnGcCfdisTbl.class)
                    .setParameter("rfcEmisor", Rfc)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No hay datos: " + ex);
        }
        return listaEmitidos;
    }
    
    public List<RnGcCfdisTbl> obtenerPPD(String Rfc) {
        List<RnGcCfdisTbl> listaEmitidos = null;
        try {
            listaEmitidos = em.createNamedQuery("RnGcCfdisTbl.findByRfcEmisorPPD", RnGcCfdisTbl.class)
                    .setParameter("rfcEmisor", Rfc)
                    .setParameter("metodoPago", "PPD")
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No hay datos: " + ex);
        }
        return listaEmitidos;
    }
    
    public List<RnGcCfdisTbl> obtenerPPDCliente(String Rfc) {
        List<RnGcCfdisTbl> listaEmitidos = null;
        try {
            listaEmitidos = em.createNamedQuery("RnGcCfdisTbl.findByRfcReceptorPPD", RnGcCfdisTbl.class)
                    .setParameter("rfcReceptor", Rfc)
                    .setParameter("metodoPago", "PPD")
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No hay datos: " + ex);
        }
        return listaEmitidos;
    }

    public List<RnGcCfdisTbl> obtenerRecibidos(String Rfc) {
        List<RnGcCfdisTbl> listaRecibidos = null;
        try {
            listaRecibidos = em.createNamedQuery("RnGcCfdisTbl.findByRfcReceptor", RnGcCfdisTbl.class)
                    .setParameter("rfcReceptor", Rfc)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No hay datos; " + ex);
        }
        return listaRecibidos;
    }

    public List<RnGcCfdisTbl> obtenerCfdisParaComplementos(String rfcReceptor) {
        List<RnGcCfdisTbl> listaCfdis = null;
        try {
            listaCfdis = em.createNamedQuery("RnGcCfdisTbl.findByCfdisComplementos", RnGcCfdisTbl.class)
                    .setParameter("rfcReceptor", rfcReceptor)
                    .getResultList();
            System.out.println("listaCfdisParaComplementos1: " + listaCfdis);
        } catch (NoResultException ex) {
            System.out.println("Error1: " + ex.getMessage());
        }
        return listaCfdis;
    }

    public List<RnGcCfdisTbl> obtenerRfcEmisorCreadoPorUsuario(String rfcEmisor) {
        System.out.println("Entro a obtenerRfcEmisorCreadoPorUsuario Facade: " + rfcEmisor);
        List<RnGcCfdisTbl> listaCfdis = null;
        try {
            listaCfdis = em.createNamedQuery("RnGcCfdisTbl.findByRfcEmisor", RnGcCfdisTbl.class)
                    .setParameter("rfcEmisor", rfcEmisor)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No hay datos");
        }
        System.out.println("El tamaño de la lista del RFC es: " + listaCfdis.size());
        return listaCfdis;
    }

    public List<RnGcCfdisTbl> obtenerRfcReceptorCreadoPorUsuario(String rfcReceptor) {
        System.out.println("Entro a obtenerRfcReceptorCreadoPorUsuario Facade: " + rfcReceptor);
        List<RnGcCfdisTbl> listaCfdisRecibidos = null;
        try {
            listaCfdisRecibidos = em.createNamedQuery("RnGcCfdisTbl.findByRfcReceptor", RnGcCfdisTbl.class)
                    .setParameter("rfcReceptor", rfcReceptor)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No hay datos");
        }
        System.out.println("El tamaño de la lista del RFC es: " + listaCfdisRecibidos.size());
        return listaCfdisRecibidos;
    }

    public List<RnGcCfdisTbl> obtenerEmitidosParaMisReportes(String Rfc, int creadoPor) {
        System.out.println("Entro a obtenerEmitidosParaMisReportes Facade: " + Rfc + " Quien creo el rfc emisor " + creadoPor);
        List<RnGcCfdisTbl> listaEmitidosParaMisReportes = null;
        try {
            listaEmitidosParaMisReportes = em.createNamedQuery("RnGcCfdisTbl.findByRfcEmisorCreadoPor", RnGcCfdisTbl.class)
                    .setParameter("rfcEmisor", Rfc)
                    .setParameter("creadoPor", creadoPor)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No hay datos: " + ex);
        }
        System.out.println("El tamaño de la lista es: " + listaEmitidosParaMisReportes.size());
        return listaEmitidosParaMisReportes;
    }

    public List<RnGcCfdisTbl> obtenerRecibidosParaMisReportes(String Rfc, int creadoPor) {
        System.out.println("Entro a obtenerRecibidosParaMisReportes Facade: " + Rfc + " Quien creo el rfc receptor " + creadoPor);
        List<RnGcCfdisTbl> listaRecibidosParaMisReportes = null;
        try {
            listaRecibidosParaMisReportes = em.createNamedQuery("RnGcCfdisTbl.findByRfcReceptorCreadoPor", RnGcCfdisTbl.class)
                    .setParameter("rfcReceptor", Rfc)
                    .setParameter("creadoPor", creadoPor)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No hay datos; " + ex);
        }
        System.out.println("El tamaño de la lista es: " + listaRecibidosParaMisReportes.size());
        return listaRecibidosParaMisReportes;
    }
    
    public List<RnGcCfdisTbl> obtenerXRFCReceptorFecha(String Rfc, Date fInicial, Date fFinal) {
        List<RnGcCfdisTbl> listaRecibidos = null;
        try {
            listaRecibidos = em.createNamedQuery("RnGcCfdisTbl.findByRfcReceptorFecha", RnGcCfdisTbl.class)
                    .setParameter("rfcReceptor", Rfc)
                    .setParameter("fInicial", fInicial)
                    .setParameter("fFinal", fFinal)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No hay datos en obtenerFacturasXRFCFecha; " + ex);
        }
        return listaRecibidos;
    }
    
    public List<RnGcCfdisTbl> obtenerXRFCReceptorGuardado(String Rfc, int creadoPor) {
        List<RnGcCfdisTbl> lista = null;
        try {
            lista = em.createNamedQuery("RnGcCfdisTbl.findByRfcReceptorCreadoPorGuardado", RnGcCfdisTbl.class)
                    .setParameter("rfcReceptor", Rfc)
                    .setParameter("creadoPor", creadoPor)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No hay datos; " + ex);
        }
        System.out.println("El tamaño de la lista es: " + lista.size());
        return lista;
    }
    
    public List<RnGcCfdisTbl> obtenerXRFCReceptorPlantilla(String Rfc, int creadoPor) {
        List<RnGcCfdisTbl> lista = null;
        try {
            lista = em.createNamedQuery("RnGcCfdisTbl.findByRfcReceptorCreadoPorPlantilla", RnGcCfdisTbl.class)
                    .setParameter("rfcReceptor", Rfc)
                    .setParameter("creadoPor", creadoPor)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No hay datos; " + ex);
        }
        System.out.println("El tamaño de la lista es: " + lista.size());
        return lista;
    }
}
