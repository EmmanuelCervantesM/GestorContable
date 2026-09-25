/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcCertificadosTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcCertificadosTblFacade extends AbstractFacade<RnGcCertificadosTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcCertificadosTblFacade() {
        super(RnGcCertificadosTbl.class);
    }

    @Override
    public RnGcCertificadosTbl find(Object id) {
        return actualizarSiVencido(super.find(id));
    }

    @Override
    public List<RnGcCertificadosTbl> findAll() {
        return actualizarSiVencidos(super.findAll());
    }

    public List<RnGcCertificadosTbl> obtenerCertificadosDeUsuario(RnGcUsuariosTbl usuarioId) {
        List<RnGcCertificadosTbl> listaCertificados = null;
        try {
            listaCertificados = em.createNamedQuery("RnGcCertificadosTbl.findByUsuarioId", RnGcCertificadosTbl.class)
                    .setParameter("usuariosId", usuarioId)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("El usuario no tiene certificados.");
        }
        return actualizarSiVencidos(listaCertificados);
    }

    public List<RnGcCertificadosTbl> obtenerCertificadosActivosDeUsuario(RnGcUsuariosTbl usuarioId) {
        List<RnGcCertificadosTbl> listaCertificados = null;
        try {
            listaCertificados = em.createNamedQuery("RnGcCertificadosTbl.findByActivoUsuarioId", RnGcCertificadosTbl.class)
                    .setParameter("usuariosId", usuarioId)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("El usuario no tiene certificados.");
        }
        return actualizarSiVencidos(listaCertificados);
    }

    /**
     * CTR-13 punto 7: certificados que deben ofrecerse al timbrar. Solo CSD
     * vigentes y Activos; un FIEL nunca debe listarse aqui.
     */
    public List<RnGcCertificadosTbl> obtenerCertificadosCsdActivosDeUsuario(RnGcUsuariosTbl usuarioId) {
        List<RnGcCertificadosTbl> listaCertificados = null;
        try {
            listaCertificados = em.createNamedQuery("RnGcCertificadosTbl.findByActivoCsdUsuarioId", RnGcCertificadosTbl.class)
                    .setParameter("usuariosId", usuarioId)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("El usuario no tiene certificados CSD activos.");
        }
        return actualizarSiVencidos(listaCertificados);
    }

    /**
     * CTR-13 punto 3: FIEL vigente (Activo) del usuario, si existe, para
     * reemplazarlo automaticamente al subir una FIEL nueva.
     */
    public RnGcCertificadosTbl obtenerFielVigente(RnGcUsuariosTbl usuarioId) {
        try {
            List<RnGcCertificadosTbl> lista = em.createNamedQuery("RnGcCertificadosTbl.findByFielVigenteUsuarioId", RnGcCertificadosTbl.class)
                    .setParameter("usuariosId", usuarioId)
                    .getResultList();
            if (lista != null && !lista.isEmpty()) {
                return lista.get(0);
            }
        } catch (NoResultException ex) {
            System.out.println("El usuario no tiene FIEL vigente.");
        }
        return null;
    }

    /**
     * CTR-13.1: unicidad GLOBAL de numeroCertificado (en todo el sistema, no
     * solo del usuario que lo sube).
     *
     * @param idAExcluir id del certificado que se esta editando (para no
     * chocar contra si mismo); {@code null} en alta.
     */
    public boolean existeNumeroCertificado(String numeroCertificado, Integer idAExcluir) {
        if (numeroCertificado == null || numeroCertificado.trim().isEmpty()) {
            return false;
        }
        try {
            List<RnGcCertificadosTbl> coincidencias = em.createNamedQuery("RnGcCertificadosTbl.findByNumeroCertificado", RnGcCertificadosTbl.class)
                    .setParameter("numeroCertificado", numeroCertificado)
                    .getResultList();
            for (RnGcCertificadosTbl coincidencia : coincidencias) {
                if (idAExcluir == null || !idAExcluir.equals(coincidencia.getId())) {
                    return true;
                }
            }
        } catch (NoResultException ex) {
            return false;
        }
        return false;
    }

    public List<RnGcCertificadosTbl> obtenerCreadoPor(int creadoPor) {
        List<RnGcCertificadosTbl> listaCertificados = null;
        try {
            listaCertificados = em.createNamedQuery("RnGcCertificadosTbl.findByCreadoPor", RnGcCertificadosTbl.class)
                    .setParameter("creadoPor", creadoPor)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return actualizarSiVencidos(listaCertificados);
    }

    public List<RnGcCertificadosTbl> certificadosActivos() {
        List<RnGcCertificadosTbl> listaActivos = null;
        try {
            listaActivos = em.createNamedQuery("RnGcCertificadosTbl.findByEstado", RnGcCertificadosTbl.class)
                    .setParameter("estado", "Activo")
                    .getResultList();
        } catch(NoResultException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return actualizarSiVencidos(listaActivos);
    }

    /**
     * CTR-13 punto 7 (rama ADMINISTRADOR): mismos certificados que
     * certificadosActivos(), pero solo CSD; un FIEL nunca debe listarse aqui.
     */
    public List<RnGcCertificadosTbl> certificadosCsdActivos() {
        List<RnGcCertificadosTbl> lista = certificadosActivos();
        if (lista == null) {
            return lista;
        }
        List<RnGcCertificadosTbl> soloCsd = new java.util.ArrayList<>();
        for (RnGcCertificadosTbl cert : lista) {
            if ("CSD".equals(cert.getTipo())) {
                soloCsd.add(cert);
            }
        }
        return soloCsd;
    }

    /**
     * CTR-13 punto 6: lazy-check de vencimiento. No hay job en background; el
     * paso a Inactivo ocurre al leer/listar el certificado.
     */
    private List<RnGcCertificadosTbl> actualizarSiVencidos(List<RnGcCertificadosTbl> certificados) {
        if (certificados != null) {
            for (RnGcCertificadosTbl certificado : certificados) {
                actualizarSiVencido(certificado);
            }
        }
        return certificados;
    }

    private RnGcCertificadosTbl actualizarSiVencido(RnGcCertificadosTbl certificado) {
        if (certificado != null
                && certificado.getFechaVencimiento() != null
                && certificado.getFechaVencimiento().before(new Date())
                && !"Inactivo".equals(certificado.getEstado())) {
            certificado.setEstado("Inactivo");
            edit(certificado);
        }
        return certificado;
    }

    /**
     * CTR-03: certificados Activos cuya fechaVencimiento cae dentro de los
     * proximos diasAviso dias (pero que todavia no vencieron: el lazy-check
     * de actualizarSiVencido ya los hubiera pasado a Inactivo). Usado para
     * el aviso "tu sello/firma esta por vencer".
     */
    public List<RnGcCertificadosTbl> obtenerCertificadosPorVencer(RnGcUsuariosTbl usuarioId, int diasAviso) {
        List<RnGcCertificadosTbl> activos = obtenerCertificadosActivosDeUsuario(usuarioId);
        List<RnGcCertificadosTbl> porVencer = new ArrayList<>();
        if (activos == null) {
            return porVencer;
        }
        Date ahora = new Date();
        Calendar limite = Calendar.getInstance();
        limite.add(Calendar.DAY_OF_MONTH, diasAviso);
        Date fechaLimite = limite.getTime();
        for (RnGcCertificadosTbl certificado : activos) {
            // obtenerCertificadosActivosDeUsuario() ya corrio el lazy-check: un
            // certificado que vencio JUSTO ahora sigue en esta lista (el objeto
            // ya quedo Inactivo en BD/memoria, pero la referencia no se filtra
            // solita). Sin el chequeo "after(ahora)" ese certificado ya vencido
            // se colaba como si estuviera "por vencer" con fecha pasada.
            if (certificado.getFechaVencimiento() != null
                    && certificado.getFechaVencimiento().after(ahora)
                    && !certificado.getFechaVencimiento().after(fechaLimite)) {
                porVencer.add(certificado);
            }
        }
        return porVencer;
    }
}
