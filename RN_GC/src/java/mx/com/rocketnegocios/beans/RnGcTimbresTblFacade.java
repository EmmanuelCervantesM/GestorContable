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
import mx.com.rocketnegocios.entities.RnGcTimbresTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;

/**
 *
 * @author Developer1
 */
@Stateless
public class RnGcTimbresTblFacade extends AbstractFacade<RnGcTimbresTbl> {

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RnGcTimbresTblFacade() {
        super(RnGcTimbresTbl.class);
    }

    public RnGcTimbresTbl refreshFromDB(RnGcTimbresTbl timbre) {
        RnGcTimbresTbl timbreLocal = null;
        timbreLocal = em.merge(timbre);
        return timbreLocal;
    }

    public List<RnGcTimbresTbl> obtenerPorUsuario(RnGcUsuariosTbl usuarioId) {
        List<RnGcTimbresTbl> listaTimbres = null;
        try {
            listaTimbres = em.createNamedQuery("RnGcTimbresTbl.findByUsuarioId", RnGcTimbresTbl.class)
                    .setParameter("usuarioId", usuarioId)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return listaTimbres;
    }

    public List<RnGcTimbresTbl> obtenerTimbre(String proveedor, RnGcUsuariosTbl usuario) {
        List<RnGcTimbresTbl> itemsTimbre = null;
        try {
            itemsTimbre = em.createNamedQuery("RnGcTimbresTbl.findByProveedorUsuario", RnGcTimbresTbl.class)
                    .setParameter("proveedor", proveedor)
                    .setParameter("usuarioId", usuario)
                    .getResultList();
            //System.out.println("timbreF: " + itemsTimbre);
        } catch (NoResultException ex) {
            System.out.println("Error obtenerTimbre: " + ex.getMessage());
        }
        return itemsTimbre;
    }

    public long obtenerTotalTimbresActivosXUsuario(String proveedor, RnGcUsuariosTbl usuario) {
        Long totaltimbres = 0L;
        try {
            totaltimbres = em.createNamedQuery("RnGcTimbresTbl.SUMTimbresActivos", long.class)
                    .setParameter("proveedor", proveedor)
                    .setParameter("usuarioId", usuario)
                    .getSingleResult();
            //System.out.println("totaltimbres: " + totaltimbres);
        } catch (NoResultException ex) {
            System.out.println("Error en obtenerTotalTimbresActivosXUsuario: " + ex.getMessage());
        }
        return totaltimbres;
    }

    public long obtenerTotalTimbresTotalesXUsuario(String proveedor, RnGcUsuariosTbl usuario) {
        Long totaltimbres = 0L;
        try {
            totaltimbres = em.createNamedQuery("RnGcTimbresTbl.SUMTimbresTotal", long.class)
                    .setParameter("proveedor", proveedor)
                    .setParameter("usuarioId", usuario)
                    .getSingleResult();
            //System.out.println("totaltimbres: " + totaltimbres);
        } catch (NoResultException ex) {
            System.out.println("Error en obtenerTotalTimbresTotalesXUsuario: " + ex.getMessage());
        }
        return totaltimbres;
    }

    public List<RnGcTimbresTbl> listaTimbresUsuario(RnGcUsuariosTbl usuario) {
        List<RnGcTimbresTbl> listaTimbres = null;
        try {
            listaTimbres = em.createNamedQuery("RnGcTimbresTbl.findByUsuarioEstado", RnGcTimbresTbl.class)
                    .setParameter("usuarioId", usuario)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return listaTimbres;
    }

    /**
     * CTR-03: lazy-check de vigencia de lotes de timbres, mismo patron que
     * RnGcCertificadosTblFacade.actualizarSiVencido() para certificados. Un
     * lote con fechaFin ya vencida pasa a Inactivo la primera vez que se
     * consulta, para que deje de contar como disponible en cualquier
     * pantalla que filtre por estado='Activo'.
     */
    public void actualizarTimbresVencidos(RnGcUsuariosTbl usuario) {
        try {
            em.createNamedQuery("RnGcTimbresTbl.marcarVencidosInactivos")
                    .setParameter("usuarioId", usuario)
                    .executeUpdate();
        } catch (Exception ex) {
            System.out.println("Error en actualizarTimbresVencidos: " + ex.getMessage());
        }
    }

    /**
     * CTR-03: total real de timbres disponibles (Activo + con saldo + dentro
     * de vigencia por fecha). Reemplaza mostrar la lista de lotes por un
     * unico numero.
     */
    public long obtenerTotalTimbresVigentesXUsuario(RnGcUsuariosTbl usuario) {
        if (usuario == null) {
            return 0L;
        }
        actualizarTimbresVencidos(usuario);
        Long total = 0L;
        try {
            total = em.createNamedQuery("RnGcTimbresTbl.SUMTimbresVigentesByUsuario", Long.class)
                    .setParameter("usuarioId", usuario)
                    .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("Error en obtenerTotalTimbresVigentesXUsuario: " + ex.getMessage());
        }
        return total != null ? total : 0L;
    }

    public long obtenerTotalTimbresXUsuario(RnGcUsuariosTbl usuario) {
        if (usuario == null) {
            return 0L;
        }

        Long totaltimbres = 0L;
        try {
            totaltimbres = em.createNamedQuery("RnGcTimbresTbl.findTotalTimbresByUsuario", Long.class)
                    .setParameter("usuarioId", usuario)
                    .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("Error en obtenerTotalTimbresXUsuario: " + ex.getMessage());
        }
        return totaltimbres != null ? totaltimbres : 0L;
    }

}
