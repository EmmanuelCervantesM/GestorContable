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
import mx.com.rocketnegocios.entities.RnGcCatalogoCuentasTbl;
import mx.com.rocketnegocios.entities.RnGcCodigoAgrupadorSatTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;

/**
 *
 * @author Consultor
 */
@Stateless
public class RnGcCatalogoCuentasTblFacade extends AbstractFacade<RnGcCatalogoCuentasTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcCatalogoCuentasTblFacade() {
        super(RnGcCatalogoCuentasTbl.class);
    }

    public List<RnGcCatalogoCuentasTbl> obtenerListaCatalogoCuentas(String rfc) {
        List<RnGcCatalogoCuentasTbl> listaCatalogoCuentas = null;

        try {
            listaCatalogoCuentas = em.createNamedQuery("RnGcCatalogoCuentasTbl.findByRfc", RnGcCatalogoCuentasTbl.class)
                    .setParameter("rfc", rfc)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No hay cuentas");
        }

        return listaCatalogoCuentas;
    }

    public List<RnGcCatalogoCuentasTbl> obtenerListaCuentas(RnGcUsuariosTbl usuarioId) {
        List<RnGcCatalogoCuentasTbl> cuentas = null;
        try {
            cuentas = em.createNamedQuery("RnGcCatalogoCuentasTbl.findByCreadoPor", RnGcCatalogoCuentasTbl.class)
                    .setParameter("creadoPor", usuarioId.getId())
                    .getResultList();
            //System.out.println("El tamaño de la lista de cuentas es: " + cuentas.size() + " para el usuario: " + usuarioId.getNombreCompleto());
        } catch (NoResultException ex) {
            System.out.println("No hay lista de cuentas");
        }
        return cuentas;
    }

    public List<RnGcCatalogoCuentasTbl> obtenerListadeNumerosCuentas(String numeroCuenta, RnGcUsuariosTbl usuarioId) {
        List<RnGcCatalogoCuentasTbl> listaCatalogoCuentas = null;

        try {
            listaCatalogoCuentas = em.createNamedQuery("RnGcCatalogoCuentasTbl.findByNumeroCuenta2", RnGcCatalogoCuentasTbl.class)
                    .setParameter("numeroCuenta", numeroCuenta)
                    .setParameter("creadoPor", usuarioId.getId())
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No hay cuentas");
        }

        return listaCatalogoCuentas;
    }

    public List<RnGcCatalogoCuentasTbl> obtenerCuentasCreadoPor(Integer usuarioId) {
        List<RnGcCatalogoCuentasTbl> cuentas = null;
        try {
            cuentas = em.createNamedQuery("RnGcCatalogoCuentasTbl.findByCreadoPor", RnGcCatalogoCuentasTbl.class)
                    .setParameter("creadoPor", usuarioId)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No hay lista de cuentas");
        }
        return cuentas;
    }
    
    public List<RnGcCatalogoCuentasTbl> obtenerCuentasCreadoPorCodigoAgrupador(Integer usuarioId, RnGcCodigoAgrupadorSatTbl codigiAgrupador) {
        List<RnGcCatalogoCuentasTbl> cuentas = null;
        try {
            cuentas = em.createNamedQuery("RnGcCatalogoCuentasTbl.findByCreadoPorCodigoAgrupador", RnGcCatalogoCuentasTbl.class)
                    .setParameter("creadoPor", usuarioId)
                    .setParameter("codigoAgrupador", codigiAgrupador)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No hay lista de cuentas");
        }
        return cuentas;
    }
    
    public List<RnGcCatalogoCuentasTbl> obtenerListadeCuentasDescripcion(String desCuenta, RnGcUsuariosTbl usuarioId) {
        List<RnGcCatalogoCuentasTbl> listaCatalogoCuentas = null;

        try {
            listaCatalogoCuentas = em.createNamedQuery("RnGcCatalogoCuentasTbl.findByDesCuenta", RnGcCatalogoCuentasTbl.class)
                    .setParameter("descripcionCuenta", desCuenta)
                    .setParameter("creadoPor", usuarioId.getId())
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No hay cuentas");
        }

        return listaCatalogoCuentas;
    }
    
    public List<RnGcCatalogoCuentasTbl> obtenerListadeCuentasRFC(String rfc, RnGcUsuariosTbl usuarioId, boolean diot) {
        List<RnGcCatalogoCuentasTbl> listaCatalogoCuentas = null;

        try {
            listaCatalogoCuentas = em.createNamedQuery("RnGcCatalogoCuentasTbl.findByRfcUser", RnGcCatalogoCuentasTbl.class)
                    .setParameter("rfc", rfc)
                    .setParameter("creadoPor", usuarioId.getId())
                    .setParameter("diot", diot)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No hay cuentas");
        }

        return listaCatalogoCuentas;
    }
}
