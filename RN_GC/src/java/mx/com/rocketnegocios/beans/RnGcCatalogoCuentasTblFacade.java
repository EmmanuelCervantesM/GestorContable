/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import javax.annotation.security.PermitAll;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.persistence.TemporalType;
import static jdk.nashorn.internal.runtime.JSType.toInteger;
import mx.com.rocketnegocios.entities.AuxiliarCuentasRow;
import mx.com.rocketnegocios.entities.BalanceGeneralRow;
import mx.com.rocketnegocios.entities.EstadoResultadoRow;
import mx.com.rocketnegocios.entities.LibroDiarioRow;
import mx.com.rocketnegocios.entities.LibroMayorRow;
import mx.com.rocketnegocios.entities.RnGcCatalogoCuentasTbl;
import mx.com.rocketnegocios.entities.RnGcCodigoAgrupadorSatTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import org.primefaces.model.DefaultStreamedContent;

/**
 *
 * @author Consultor
 */
@Stateless
@PermitAll
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
    
    private DefaultStreamedContent reporteExcel;

    public DefaultStreamedContent getReporteExcel() {
        return reporteExcel;
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
        if (usuarioId == null) {
            return new ArrayList<>();
        }

        List<RnGcCatalogoCuentasTbl> cuentas = new ArrayList<>();

        try {
            String sql =
                "SELECT c.* " +
                "FROM rn_gc_catalogo_cuentas_tbl c " +
                "JOIN ( " +
                "   SELECT p.periodoId " +
                "   FROM rn_gc_periodos_tbl p " +
                "   WHERE p.creadoPor = ? " +
                "   ORDER BY p.fechaInicioPeriodo DESC " +
                "   LIMIT 2 " +
                ") ultimos ON c.id_periodo = ultimos.periodoId " +
                "WHERE c.creadoPor = ?";

            cuentas = em.createNativeQuery(sql, RnGcCatalogoCuentasTbl.class)
                    .setParameter(1, usuarioId)
                    .setParameter(2, usuarioId)
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
    
    public boolean existeRfcEnCatalogo(String rfc) {
        if (rfc == null || rfc.trim().isEmpty()) {
            return false;
        }
        String r = rfc.trim().toUpperCase();
        Long count = em.createQuery(
                "SELECT COUNT(c) FROM RnGcCatalogoCuentasTbl c " +
                "WHERE UPPER(c.rfc) = :rfc", Long.class)
            .setParameter("rfc", r)
            .getSingleResult();
        return count != null && count > 0;
    }
    
    public void aplicarMovimientoCuenta(Integer idCuenta,
                                            BigDecimal cargo,
                                            BigDecimal abono) {

            if (idCuenta == null) {
                return;
            }

            RnGcCatalogoCuentasTbl cuenta = em.find(RnGcCatalogoCuentasTbl.class, idCuenta);
            if (cuenta == null) {
                return;
            }

            BigDecimal saldoActual = cuenta.getSaldoActual();
            if (saldoActual == null) {
                saldoActual = BigDecimal.ZERO;
            }

            BigDecimal cargoBD = (cargo != null) ? cargo : BigDecimal.ZERO;
            BigDecimal abonoBD = (abono != null) ? abono : BigDecimal.ZERO;

            // Regla que tú definiste:
            //  - cargo RESTA
            //  - abono SUMA
            BigDecimal nuevoSaldo = saldoActual
                    .subtract(cargoBD)
                    .add(abonoBD);

            cuenta.setSaldoActual(nuevoSaldo);
            em.merge(cuenta);
        }

    @PermitAll
    public List<EstadoResultadoRow> reporteEstadoResultadoPadre(Date fechaDesde, Date fechaHasta, Integer idUsuario) {
        if (fechaDesde == null || fechaHasta == null || idUsuario == null) {
            return new ArrayList<>();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaHasta);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date fechaHastaMasUno = cal.getTime();

        String sql =
                "WITH mov_periodo AS ( " +
                "    SELECT " +
                "        pl.catalogoCuentasId AS cuentaId, " +
                "        SUM(COALESCE(pl.cargo, 0)) AS totalCargo, " +
                "        SUM(COALESCE(pl.abono, 0)) AS totalAbono " +
                "    FROM rn_gc_db_test.rn_gc_poliza_lineas_tbl pl " +
                "    INNER JOIN rn_gc_db_test.rn_gc_poliza_header_tbl ph " +
                "        ON ph.id = pl.polizaHeaderId " +
                "    WHERE ph.fecha >= ? " +
                "      AND ph.fecha < ? " +
                "      AND ph.creadoPor = ? " +
                "      AND pl.creadoPor = ? " +
                "    GROUP BY pl.catalogoCuentasId " +
                "), " +

                "cuentas AS ( " +
                "    SELECT " +
                "        ct.id, " +
                "        TRIM(ct.numeroCuenta) AS numeroCuenta, " +
                "        ct.descripcionCuenta, " +
                "        COALESCE(mp.totalCargo, 0) AS totalCargo, " +
                "        COALESCE(mp.totalAbono, 0) AS totalAbono, " +
                "        COALESCE(mp.totalAbono - mp.totalCargo, 0) AS importe " +
                "    FROM rn_gc_db_test.rn_gc_catalogo_cuentas_tbl ct " +
                "    LEFT JOIN mov_periodo mp " +
                "        ON mp.cuentaId = ct.id " +
                "    WHERE ct.creadoPor = ? " +
                "      AND ( " +
                "             TRIM(ct.numeroCuenta) LIKE '4%' " +
                "          OR TRIM(ct.numeroCuenta) LIKE '5%' " +
                "          OR TRIM(ct.numeroCuenta) LIKE '6%' " +
                "          OR TRIM(ct.numeroCuenta) LIKE '7%' " +
                "      ) " +
                "), " +

                "padres_fijos AS ( " +
                "    SELECT '400' AS numeroCuenta, 'INGRESOS' AS descripcionDefault, '4' AS prefijo " +
                "    UNION ALL " +
                "    SELECT '500' AS numeroCuenta, 'COSTOS' AS descripcionDefault, '5' AS prefijo " +
                "    UNION ALL " +
                "    SELECT '600' AS numeroCuenta, 'GASTOS' AS descripcionDefault, '6' AS prefijo " +
                "), " +

                "padres AS ( " +
                "    SELECT " +
                "        MAX(c.id) AS id, " +
                "        pf.numeroCuenta, " +
                "        COALESCE(MAX(c.descripcionCuenta), pf.descripcionDefault) AS descripcionCuenta, " +
                "        pf.prefijo " +
                "    FROM padres_fijos pf " +
                "    LEFT JOIN cuentas c " +
                "        ON c.numeroCuenta = pf.numeroCuenta " +
                "    GROUP BY pf.numeroCuenta, pf.descripcionDefault, pf.prefijo " +
                "), " +

                "filas_padre AS ( " +
                "    SELECT " +
                "        p.id, " +
                "        p.numeroCuenta AS cuentaOrden, " +
                "        0 AS ordenFila, " +
                "        p.numeroCuenta, " +
                "        p.descripcionCuenta, " +
                "        CAST(0 AS DECIMAL(18,2)) AS cargo, " +
                "        CAST(0 AS DECIMAL(18,2)) AS abono, " +
                "        COALESCE(SUM(c.importe), 0) AS importeEstadoResultado " +
                "    FROM padres p " +
                "    LEFT JOIN cuentas c " +
                "        ON c.numeroCuenta LIKE CONCAT(p.prefijo, '%') " +
                "    GROUP BY p.id, p.numeroCuenta, p.descripcionCuenta " +
                "), " +

                "filas_hijas AS ( " +
                "    SELECT " +
                "        c.id, " +
                "        p.numeroCuenta AS cuentaOrden, " +
                "        1 AS ordenFila, " +
                "        c.numeroCuenta, " +
                "        CONCAT('   ', c.descripcionCuenta) AS descripcionCuenta, " +
                "        c.totalCargo AS cargo, " +
                "        c.totalAbono AS abono, " +
                "        c.importe AS importeEstadoResultado " +
                "    FROM padres p " +
                "    INNER JOIN cuentas c " +
                "        ON c.numeroCuenta LIKE CONCAT(p.prefijo, '%') " +
                "       AND c.numeroCuenta <> p.numeroCuenta " +
                "), " +

                "formulas AS ( " +
                "    SELECT " +
                "        COALESCE(ABS(SUM(CASE WHEN numeroCuenta LIKE '401%' THEN importe ELSE 0 END)), 0) AS total401, " +
                "        COALESCE(ABS(SUM(CASE WHEN numeroCuenta LIKE '501%' THEN importe ELSE 0 END)), 0) AS total501, " +
                "        COALESCE(ABS(SUM(CASE WHEN numeroCuenta LIKE '601%' THEN importe ELSE 0 END)), 0) AS total601, " +
                "        COALESCE(ABS(SUM(CASE WHEN numeroCuenta LIKE '602%' THEN importe ELSE 0 END)), 0) AS total602, " +
                "        COALESCE(ABS(SUM(CASE WHEN numeroCuenta LIKE '405%' THEN importe ELSE 0 END)), 0) AS otrosIngresos, " +
                "        COALESCE(ABS(SUM(CASE WHEN numeroCuenta LIKE '508%' THEN importe ELSE 0 END)), 0) AS otrosGastos, " +
                "        COALESCE(ABS(SUM(CASE WHEN numeroCuenta LIKE '701%' THEN importe ELSE 0 END)), 0) AS isr " +
                "    FROM cuentas " +
                "), " +

                "calculos AS ( " +
                "    SELECT " +
                "        (total401 - total501) AS utilidadBruta, " +
                "        ((total401 - total501) - (total601 + total602)) AS utilidadOperativa, " +
                "        ((((total401 - total501) - (total601 + total602)) + otrosIngresos - otrosGastos) - isr) AS utilidadNeta " +
                "    FROM formulas " +
                ") " +

                "SELECT " +
                "    t.id, " +
                "    t.numeroCuenta, " +
                "    t.descripcionCuenta, " +
                "    t.cargo, " +
                "    t.abono, " +
                "    t.importeEstadoResultado " +
                "FROM ( " +

                "    SELECT " +
                "        id, cuentaOrden, ordenFila, numeroCuenta, descripcionCuenta, cargo, abono, importeEstadoResultado " +
                "    FROM filas_padre " +

                "    UNION ALL " +

                "    SELECT " +
                "        id, cuentaOrden, ordenFila, numeroCuenta, descripcionCuenta, cargo, abono, importeEstadoResultado " +
                "    FROM filas_hijas " +

                "    UNION ALL " +

                "    SELECT " +
                "        CAST(NULL AS SIGNED) AS id, " +
                "        'ZZ01' AS cuentaOrden, " +
                "        0 AS ordenFila, " +
                "        '' AS numeroCuenta, " +
                "        'UTILIDAD BRUTA' AS descripcionCuenta, " +
                "        CAST(0 AS DECIMAL(18,2)) AS cargo, " +
                "        CAST(0 AS DECIMAL(18,2)) AS abono, " +
                "        utilidadBruta AS importeEstadoResultado " +
                "    FROM calculos " +

                "    UNION ALL " +

                "    SELECT " +
                "        CAST(NULL AS SIGNED) AS id, " +
                "        'ZZ02' AS cuentaOrden, " +
                "        0 AS ordenFila, " +
                "        '' AS numeroCuenta, " +
                "        'UTILIDAD DE OPERACIÓN' AS descripcionCuenta, " +
                "        CAST(0 AS DECIMAL(18,2)) AS cargo, " +
                "        CAST(0 AS DECIMAL(18,2)) AS abono, " +
                "        utilidadOperativa AS importeEstadoResultado " +
                "    FROM calculos " +

                "    UNION ALL " +

                "    SELECT " +
                "        CAST(NULL AS SIGNED) AS id, " +
                "        'ZZ03' AS cuentaOrden, " +
                "        0 AS ordenFila, " +
                "        '' AS numeroCuenta, " +
                "        'UTILIDAD NETA O PERDIDA' AS descripcionCuenta, " +
                "        CAST(0 AS DECIMAL(18,2)) AS cargo, " +
                "        CAST(0 AS DECIMAL(18,2)) AS abono, " +
                "        utilidadNeta AS importeEstadoResultado " +
                "    FROM calculos " +

                ") t " +
                "ORDER BY t.cuentaOrden, t.ordenFila, t.numeroCuenta";

        Query q = em.createNativeQuery(sql);

        int idx = 1;
        q.setParameter(idx++, fechaDesde, TemporalType.TIMESTAMP);
        q.setParameter(idx++, fechaHastaMasUno, TemporalType.TIMESTAMP);
        q.setParameter(idx++, idUsuario); // ph.creadoPor
        q.setParameter(idx++, idUsuario); // pl.creadoPor
        q.setParameter(idx++, idUsuario); // ct.creadoPor en cuentas

        @SuppressWarnings("unchecked")
        List<Object[]> result = q.getResultList();

        List<EstadoResultadoRow> out = new ArrayList<>();

        for (Object[] r : result) {
            Integer id = (r[0] == null) ? null : ((Number) r[0]).intValue();
            String cuenta = r[1] != null ? r[1].toString() : "";
            String desc = r[2] != null ? r[2].toString() : "";
            BigDecimal cargo = toBigDecimal(r[3]);
            BigDecimal abono = toBigDecimal(r[4]);
            BigDecimal importeEstadoResultado = toBigDecimal(r[5]);

            out.add(new EstadoResultadoRow(
                id,
                cuenta,
                desc,
                cargo,
                abono,
                importeEstadoResultado
            ));
        }

        return out;
    }
   
    @PermitAll
    public List<BalanceGeneralRow> reporteBalanceGeneralPadreHijos(Date fechaDesde, Date fechaHasta, Integer idUsuario) {

        if (fechaDesde == null || fechaHasta == null || idUsuario == null) {
            return new ArrayList<>();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaHasta);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date fechaHastaMasUno = cal.getTime();

        String sql =
            "WITH movimientos AS ( " +
            "    SELECT " +
            "        ct.id, " +
            "        TRIM(ct.numeroCuenta) AS numeroCuenta, " +
            "        ct.descripcionCuenta, " +
            "        COALESCE(SUM(CASE WHEN ph.id IS NOT NULL THEN COALESCE(pl.cargo, 0) ELSE 0 END), 0) AS totalCargo, " +
            "        COALESCE(SUM(CASE WHEN ph.id IS NOT NULL THEN COALESCE(pl.abono, 0) ELSE 0 END), 0) AS totalAbono " +
            "    FROM rn_gc_db_test.rn_gc_catalogo_cuentas_tbl ct " +
            "    LEFT JOIN rn_gc_db_test.rn_gc_poliza_lineas_tbl pl " +
            "        ON pl.catalogoCuentasId = ct.id " +
            "    LEFT JOIN rn_gc_db_test.rn_gc_poliza_header_tbl ph " +
            "        ON ph.id = pl.polizaHeaderId " +
            "       AND ph.fecha >= ? " +
            "       AND ph.fecha < ? " +
            "       AND ph.creadoPor = ? " +
            "    WHERE ct.creadoPor = ? " +
            "      AND ( " +

            "             TRIM(ct.numeroCuenta) = '101' OR TRIM(ct.numeroCuenta) LIKE '101-%' " +
            "          OR TRIM(ct.numeroCuenta) = '102' OR TRIM(ct.numeroCuenta) LIKE '102-%' " +
            "          OR TRIM(ct.numeroCuenta) = '105' OR TRIM(ct.numeroCuenta) LIKE '105-%' " +
            "          OR TRIM(ct.numeroCuenta) = '106' OR TRIM(ct.numeroCuenta) LIKE '106-%' " +
            "          OR TRIM(ct.numeroCuenta) = '107' OR TRIM(ct.numeroCuenta) LIKE '107-%' " +
            "          OR TRIM(ct.numeroCuenta) = '109' OR TRIM(ct.numeroCuenta) LIKE '109-%' " +
            "          OR TRIM(ct.numeroCuenta) = '113' OR TRIM(ct.numeroCuenta) LIKE '113-%' " +
            "          OR TRIM(ct.numeroCuenta) = '115' OR TRIM(ct.numeroCuenta) LIKE '115-%' " +
            "          OR TRIM(ct.numeroCuenta) = '120' OR TRIM(ct.numeroCuenta) LIKE '120-%' " +
            "          OR TRIM(ct.numeroCuenta) = '121' OR TRIM(ct.numeroCuenta) LIKE '121-%' " +

            "          OR TRIM(ct.numeroCuenta) = '151' OR TRIM(ct.numeroCuenta) LIKE '151-%' " +
            "          OR TRIM(ct.numeroCuenta) = '152' OR TRIM(ct.numeroCuenta) LIKE '152-%' " +
            "          OR TRIM(ct.numeroCuenta) = '153' OR TRIM(ct.numeroCuenta) LIKE '153-%' " +
            "          OR TRIM(ct.numeroCuenta) = '154' OR TRIM(ct.numeroCuenta) LIKE '154-%' " +
            "          OR TRIM(ct.numeroCuenta) = '155' OR TRIM(ct.numeroCuenta) LIKE '155-%' " +
            "          OR TRIM(ct.numeroCuenta) = '156' OR TRIM(ct.numeroCuenta) LIKE '156-%' " +
            "          OR TRIM(ct.numeroCuenta) = '160' OR TRIM(ct.numeroCuenta) LIKE '160-%' " +
            "          OR TRIM(ct.numeroCuenta) = '171' OR TRIM(ct.numeroCuenta) LIKE '171-%' " +
            "          OR TRIM(ct.numeroCuenta) = '176' OR TRIM(ct.numeroCuenta) LIKE '176-%' " +
            "          OR TRIM(ct.numeroCuenta) = '180' OR TRIM(ct.numeroCuenta) LIKE '180-%' " +

            "          OR TRIM(ct.numeroCuenta) = '201' OR TRIM(ct.numeroCuenta) LIKE '201-%' " +
            "          OR TRIM(ct.numeroCuenta) = '202' OR TRIM(ct.numeroCuenta) LIKE '202-%' " +
            "          OR TRIM(ct.numeroCuenta) = '203' OR TRIM(ct.numeroCuenta) LIKE '203-%' " +
            "          OR TRIM(ct.numeroCuenta) = '205' OR TRIM(ct.numeroCuenta) LIKE '205-%' " +
            "          OR TRIM(ct.numeroCuenta) = '206' OR TRIM(ct.numeroCuenta) LIKE '206-%' " +
            "          OR TRIM(ct.numeroCuenta) = '207' OR TRIM(ct.numeroCuenta) LIKE '207-%' " +
            "          OR TRIM(ct.numeroCuenta) = '213' OR TRIM(ct.numeroCuenta) LIKE '213-%' " +
            "          OR TRIM(ct.numeroCuenta) = '216' OR TRIM(ct.numeroCuenta) LIKE '216-%' " +

            "          OR TRIM(ct.numeroCuenta) = '251' OR TRIM(ct.numeroCuenta) LIKE '251-%' " +
            "          OR TRIM(ct.numeroCuenta) = '252' OR TRIM(ct.numeroCuenta) LIKE '252-%' " +
            "          OR TRIM(ct.numeroCuenta) = '253' OR TRIM(ct.numeroCuenta) LIKE '253-%' " +
            "          OR TRIM(ct.numeroCuenta) = '255' OR TRIM(ct.numeroCuenta) LIKE '255-%' " +
            "          OR TRIM(ct.numeroCuenta) = '256' OR TRIM(ct.numeroCuenta) LIKE '256-%' " +

            "          OR TRIM(ct.numeroCuenta) = '301' OR TRIM(ct.numeroCuenta) LIKE '301-%' " +
            "          OR TRIM(ct.numeroCuenta) = '302' OR TRIM(ct.numeroCuenta) LIKE '302-%' " +
            "          OR TRIM(ct.numeroCuenta) = '303' OR TRIM(ct.numeroCuenta) LIKE '303-%' " +
            "          OR TRIM(ct.numeroCuenta) = '304' OR TRIM(ct.numeroCuenta) LIKE '304-%' " +
            "          OR TRIM(ct.numeroCuenta) = '305' OR TRIM(ct.numeroCuenta) LIKE '305-%' " +
            "          OR TRIM(ct.numeroCuenta) = '306' OR TRIM(ct.numeroCuenta) LIKE '306-%' " +

            "      ) " +
            "    GROUP BY ct.id, TRIM(ct.numeroCuenta), ct.descripcionCuenta " +
            "), " +

            "movimientos_calc AS ( " +
            "    SELECT " +
            "        id, " +
            "        numeroCuenta, " +
            "        descripcionCuenta, " +
            "        totalCargo, " +
            "        totalAbono, " +
            "        CASE " +
            "            WHEN numeroCuenta LIKE '1%' THEN COALESCE(totalCargo - totalAbono, 0) " +
            "            WHEN numeroCuenta LIKE '2%' THEN COALESCE(totalAbono - totalCargo, 0) " +
            "            WHEN numeroCuenta LIKE '3%' THEN COALESCE(totalAbono - totalCargo, 0) " +
            "            ELSE 0 " +
            "        END AS saldoBalance " +
            "    FROM movimientos " +
            "), " +

            "filas_base AS ( " +

            "    SELECT '100' AS numeroCuenta, 'ACTIVO' AS descripcionCuenta, " +
            "           COALESCE(SUM(saldoBalance), 0) AS saldoBalance, " +
            "           'A000' AS orden, 0 AS ordenFila " +
            "    FROM movimientos_calc " +
            "    WHERE numeroCuenta LIKE '1%' " +

            "    UNION ALL " +

            "    SELECT '100.01' AS numeroCuenta, 'ACTIVO CORTO PLAZO' AS descripcionCuenta, " +
            "           COALESCE(SUM(saldoBalance), 0) AS saldoBalance, " +
            "           'A001' AS orden, 0 AS ordenFila " +
            "    FROM movimientos_calc " +
            "    WHERE numeroCuenta = '101' OR numeroCuenta LIKE '101-%' " +
            "       OR numeroCuenta = '102' OR numeroCuenta LIKE '102-%' " +
            "       OR numeroCuenta = '105' OR numeroCuenta LIKE '105-%' " +
            "       OR numeroCuenta = '106' OR numeroCuenta LIKE '106-%' " +
            "       OR numeroCuenta = '107' OR numeroCuenta LIKE '107-%' " +
            "       OR numeroCuenta = '109' OR numeroCuenta LIKE '109-%' " +
            "       OR numeroCuenta = '113' OR numeroCuenta LIKE '113-%' " +
            "       OR numeroCuenta = '115' OR numeroCuenta LIKE '115-%' " +
            "       OR numeroCuenta = '120' OR numeroCuenta LIKE '120-%' " +
            "       OR numeroCuenta = '121' OR numeroCuenta LIKE '121-%' " +

            "    UNION ALL " +

            "    SELECT numeroCuenta, CONCAT('   ', descripcionCuenta), saldoBalance, numeroCuenta AS orden, 1 AS ordenFila " +
            "    FROM movimientos_calc " +
            "    WHERE numeroCuenta = '101' OR numeroCuenta LIKE '101-%' " +
            "       OR numeroCuenta = '102' OR numeroCuenta LIKE '102-%' " +
            "       OR numeroCuenta = '105' OR numeroCuenta LIKE '105-%' " +
            "       OR numeroCuenta = '106' OR numeroCuenta LIKE '106-%' " +
            "       OR numeroCuenta = '107' OR numeroCuenta LIKE '107-%' " +
            "       OR numeroCuenta = '109' OR numeroCuenta LIKE '109-%' " +
            "       OR numeroCuenta = '113' OR numeroCuenta LIKE '113-%' " +
            "       OR numeroCuenta = '115' OR numeroCuenta LIKE '115-%' " +
            "       OR numeroCuenta = '120' OR numeroCuenta LIKE '120-%' " +
            "       OR numeroCuenta = '121' OR numeroCuenta LIKE '121-%' " +

            "    UNION ALL " +

            "    SELECT '100.02' AS numeroCuenta, 'ACTIVO LARGO PLAZO' AS descripcionCuenta, " +
            "           COALESCE(SUM(saldoBalance), 0) AS saldoBalance, " +
            "           'A500' AS orden, 0 AS ordenFila " +
            "    FROM movimientos_calc " +
            "    WHERE numeroCuenta = '151' OR numeroCuenta LIKE '151-%' " +
            "       OR numeroCuenta = '152' OR numeroCuenta LIKE '152-%' " +
            "       OR numeroCuenta = '153' OR numeroCuenta LIKE '153-%' " +
            "       OR numeroCuenta = '154' OR numeroCuenta LIKE '154-%' " +
            "       OR numeroCuenta = '155' OR numeroCuenta LIKE '155-%' " +
            "       OR numeroCuenta = '156' OR numeroCuenta LIKE '156-%' " +
            "       OR numeroCuenta = '160' OR numeroCuenta LIKE '160-%' " +
            "       OR numeroCuenta = '171' OR numeroCuenta LIKE '171-%' " +
            "       OR numeroCuenta = '176' OR numeroCuenta LIKE '176-%' " +
            "       OR numeroCuenta = '180' OR numeroCuenta LIKE '180-%' " +

            "    UNION ALL " +

            "    SELECT numeroCuenta, CONCAT('   ', descripcionCuenta), saldoBalance, numeroCuenta AS orden, 1 AS ordenFila " +
            "    FROM movimientos_calc " +
            "    WHERE numeroCuenta = '151' OR numeroCuenta LIKE '151-%' " +
            "       OR numeroCuenta = '152' OR numeroCuenta LIKE '152-%' " +
            "       OR numeroCuenta = '153' OR numeroCuenta LIKE '153-%' " +
            "       OR numeroCuenta = '154' OR numeroCuenta LIKE '154-%' " +
            "       OR numeroCuenta = '155' OR numeroCuenta LIKE '155-%' " +
            "       OR numeroCuenta = '156' OR numeroCuenta LIKE '156-%' " +
            "       OR numeroCuenta = '160' OR numeroCuenta LIKE '160-%' " +
            "       OR numeroCuenta = '171' OR numeroCuenta LIKE '171-%' " +
            "       OR numeroCuenta = '176' OR numeroCuenta LIKE '176-%' " +
            "       OR numeroCuenta = '180' OR numeroCuenta LIKE '180-%' " +

            "    UNION ALL " +

            "    SELECT '200' AS numeroCuenta, 'PASIVO' AS descripcionCuenta, " +
            "           COALESCE(SUM(saldoBalance), 0) AS saldoBalance, " +
            "           'B000' AS orden, 0 AS ordenFila " +
            "    FROM movimientos_calc " +
            "    WHERE numeroCuenta LIKE '2%' " +

            "    UNION ALL " +

            "    SELECT '200.01', 'PASIVO CORTO PLAZO', COALESCE(SUM(saldoBalance), 0), 'B001', 0 " +
            "    FROM movimientos_calc " +
            "    WHERE numeroCuenta = '201' OR numeroCuenta LIKE '201-%' " +
            "       OR numeroCuenta = '202' OR numeroCuenta LIKE '202-%' " +
            "       OR numeroCuenta = '203' OR numeroCuenta LIKE '203-%' " +
            "       OR numeroCuenta = '205' OR numeroCuenta LIKE '205-%' " +
            "       OR numeroCuenta = '206' OR numeroCuenta LIKE '206-%' " +
            "       OR numeroCuenta = '207' OR numeroCuenta LIKE '207-%' " +
            "       OR numeroCuenta = '213' OR numeroCuenta LIKE '213-%' " +
            "       OR numeroCuenta = '216' OR numeroCuenta LIKE '216-%' " +

            "    UNION ALL " +

            "    SELECT numeroCuenta, CONCAT('   ', descripcionCuenta), saldoBalance, numeroCuenta AS orden, 1 AS ordenFila " +
            "    FROM movimientos_calc " +
            "    WHERE numeroCuenta = '201' OR numeroCuenta LIKE '201-%' " +
            "       OR numeroCuenta = '202' OR numeroCuenta LIKE '202-%' " +
            "       OR numeroCuenta = '203' OR numeroCuenta LIKE '203-%' " +
            "       OR numeroCuenta = '205' OR numeroCuenta LIKE '205-%' " +
            "       OR numeroCuenta = '206' OR numeroCuenta LIKE '206-%' " +
            "       OR numeroCuenta = '207' OR numeroCuenta LIKE '207-%' " +
            "       OR numeroCuenta = '213' OR numeroCuenta LIKE '213-%' " +
            "       OR numeroCuenta = '216' OR numeroCuenta LIKE '216-%' " +

            "    UNION ALL " +

            "    SELECT '200.02', 'PASIVO LARGO PLAZO', COALESCE(SUM(saldoBalance), 0), 'B500', 0 " +
            "    FROM movimientos_calc " +
            "    WHERE numeroCuenta = '251' OR numeroCuenta LIKE '251-%' " +
            "       OR numeroCuenta = '252' OR numeroCuenta LIKE '252-%' " +
            "       OR numeroCuenta = '253' OR numeroCuenta LIKE '253-%' " +
            "       OR numeroCuenta = '255' OR numeroCuenta LIKE '255-%' " +
            "       OR numeroCuenta = '256' OR numeroCuenta LIKE '256-%' " +

            "    UNION ALL " +

            "    SELECT numeroCuenta, CONCAT('   ', descripcionCuenta), saldoBalance, numeroCuenta AS orden, 1 AS ordenFila " +
            "    FROM movimientos_calc " +
            "    WHERE numeroCuenta = '251' OR numeroCuenta LIKE '251-%' " +
            "       OR numeroCuenta = '252' OR numeroCuenta LIKE '252-%' " +
            "       OR numeroCuenta = '253' OR numeroCuenta LIKE '253-%' " +
            "       OR numeroCuenta = '255' OR numeroCuenta LIKE '255-%' " +
            "       OR numeroCuenta = '256' OR numeroCuenta LIKE '256-%' " +

            "    UNION ALL " +

            "    SELECT '300', 'CAPITAL CONTABLE', COALESCE(SUM(saldoBalance), 0), 'C000', 0 " +
            "    FROM movimientos_calc " +
            "    WHERE numeroCuenta = '301' OR numeroCuenta LIKE '301-%' " +
            "       OR numeroCuenta = '302' OR numeroCuenta LIKE '302-%' " +
            "       OR numeroCuenta = '303' OR numeroCuenta LIKE '303-%' " +
            "       OR numeroCuenta = '304' OR numeroCuenta LIKE '304-%' " +
            "       OR numeroCuenta = '305' OR numeroCuenta LIKE '305-%' " +
            "       OR numeroCuenta = '306' OR numeroCuenta LIKE '306-%' " +

            "    UNION ALL " +

            "    SELECT numeroCuenta, CONCAT('   ', descripcionCuenta), saldoBalance, numeroCuenta AS orden, 1 AS ordenFila " +
            "    FROM movimientos_calc " +
            "    WHERE numeroCuenta = '301' OR numeroCuenta LIKE '301-%' " +
            "       OR numeroCuenta = '302' OR numeroCuenta LIKE '302-%' " +
            "       OR numeroCuenta = '303' OR numeroCuenta LIKE '303-%' " +
            "       OR numeroCuenta = '304' OR numeroCuenta LIKE '304-%' " +
            "       OR numeroCuenta = '305' OR numeroCuenta LIKE '305-%' " +
            "       OR numeroCuenta = '306' OR numeroCuenta LIKE '306-%' " +

            ") " +

            "SELECT numeroCuenta, descripcionCuenta, saldoBalance " +
            "FROM filas_base " +
            "ORDER BY orden, ordenFila, numeroCuenta";

        Query q = em.createNativeQuery(sql);

        int idx = 1;
        q.setParameter(idx++, fechaDesde, TemporalType.TIMESTAMP);
        q.setParameter(idx++, fechaHastaMasUno, TemporalType.TIMESTAMP);
        q.setParameter(idx++, idUsuario);
        q.setParameter(idx++, idUsuario);
        q.setParameter(idx++, idUsuario);
        q.setParameter(idx++, idUsuario);

        @SuppressWarnings("unchecked")
        List<Object[]> result = q.getResultList();

        List<Object[]> activos = new ArrayList<>();
        List<Object[]> pasivoCapital = new ArrayList<>();

        for (Object[] r : result) {
            String numeroCuenta = r[0] == null ? "" : r[0].toString();
            String descripcionCuenta = r[1] == null ? "" : r[1].toString();
            BigDecimal saldo = toBigDecimal(r[2]);

            Object[] item = new Object[] {
                numeroCuenta,
                descripcionCuenta,
                saldo
            };

            if (numeroCuenta.startsWith("1")) {
                activos.add(item);
            } else if (numeroCuenta.startsWith("2") || numeroCuenta.startsWith("3")) {
                pasivoCapital.add(item);
            }
        }

        List<BalanceGeneralRow> out = new ArrayList<>();

        int max = Math.max(activos.size(), pasivoCapital.size());

        for (int i = 0; i < max; i++) {
            BalanceGeneralRow row = new BalanceGeneralRow();

            if (i < activos.size()) {
                Object[] activo = activos.get(i);

                row.setCuentaActivo(activo[0] == null ? "" : activo[0].toString());
                row.setDescripcionActivo(activo[1] == null ? "" : activo[1].toString());
                row.setSaldoActivo(toBigDecimal(activo[2]));
            }

            if (i < pasivoCapital.size()) {
                Object[] pasivo = pasivoCapital.get(i);

                row.setCuentaPasivo(pasivo[0] == null ? "" : pasivo[0].toString());
                row.setDescripcionPasivo(pasivo[1] == null ? "" : pasivo[1].toString());
                row.setSaldoPasivo(toBigDecimal(pasivo[2]));
            }

            out.add(row);
        }

        return out;
    }
    
    @PermitAll
    public List<AuxiliarCuentasRow> reporteAuxiliarCuentasPadreHijos(Date fechaDesde, Date fechaHasta, Integer idUsuario) {

        if (fechaDesde == null || fechaHasta == null || idUsuario == null) {
            return new ArrayList<>();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaHasta);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date fechaHastaMasUno = cal.getTime();

        String sql =
                "WITH movimientos AS ( " +
                "    SELECT " +
                "        ct.id, " +
                "        MAX(CASE WHEN ph.id IS NOT NULL THEN pl.fechaCreacion END) AS fechaCreacion, " +

                "        CASE " +
                "            WHEN COUNT(DISTINCT CASE WHEN ph.id IS NOT NULL THEN DATE_FORMAT(ph.fecha, '%Y%m') END) = 1 " +
                "                THEN MAX(CASE WHEN ph.id IS NOT NULL THEN DATE_FORMAT(ph.fecha, '%Y%m') END) " +
                "            WHEN COUNT(DISTINCT CASE WHEN ph.id IS NOT NULL THEN DATE_FORMAT(ph.fecha, '%Y%m') END) > 1 " +
                "                THEN 'VARIOS' " +
                "            ELSE '' " +
                "        END AS periodo, " +

                "        CASE " +
                "            WHEN COUNT(DISTINCT CASE WHEN ph.id IS NOT NULL THEN tp.id END) = 1 " +
                "                THEN MAX(CASE WHEN ph.id IS NOT NULL THEN tp.descripcion END) " +
                "            WHEN COUNT(DISTINCT CASE WHEN ph.id IS NOT NULL THEN tp.id END) > 1 " +
                "                THEN 'VARIOS' " +
                "            ELSE '' " +
                "        END AS tipoPoliza, " +

                "        CASE " +
                "            WHEN COUNT(DISTINCT CASE WHEN ph.id IS NOT NULL THEN ph.id END) = 1 " +
                "                THEN CAST(MAX(CASE WHEN ph.id IS NOT NULL THEN ph.id END) AS CHAR) " +
                "            WHEN COUNT(DISTINCT CASE WHEN ph.id IS NOT NULL THEN ph.id END) > 1 " +
                "                THEN 'VARIAS' " +
                "            ELSE '' " +
                "        END AS numeroPoliza, " +

                "        ct.numeroCuenta, " +
                "        ct.descripcionCuenta, " +
                "        COALESCE(SUM(CASE WHEN ph.id IS NOT NULL THEN COALESCE(pl.cargo, 0) ELSE 0 END), 0) AS totalCargo, " +
                "        COALESCE(SUM(CASE WHEN ph.id IS NOT NULL THEN COALESCE(pl.abono, 0) ELSE 0 END), 0) AS totalAbono " +
                "    FROM rn_gc_db_test.rn_gc_catalogo_cuentas_tbl ct " +
                "    LEFT JOIN rn_gc_db_test.rn_gc_poliza_lineas_tbl pl " +
                "        ON pl.catalogoCuentasId = ct.id " +
                "    LEFT JOIN rn_gc_db_test.rn_gc_poliza_header_tbl ph " +
                "        ON ph.id = pl.polizaHeaderId " +
                "       AND ph.fecha >= ? " +
                "       AND ph.fecha < ? " +
                "       AND ph.creadoPor = ? " +
                "    LEFT JOIN rn_gc_db_test.rn_gc_tipo_poliza tp " +
                "        ON tp.id = ph.tipoPolizaId " +
                "    WHERE ct.creadoPor = ? " +
                "    GROUP BY " +
                "        ct.id, " +
                "        ct.numeroCuenta, " +
                "        ct.descripcionCuenta " +
                "), " +

                "padres AS ( " +
                "    SELECT " +
                "        ct.id, " +
                "        ct.numeroCuenta, " +
                "        ct.descripcionCuenta " +
                "    FROM rn_gc_db_test.rn_gc_catalogo_cuentas_tbl ct " +
                "    WHERE ct.creadoPor = ? " +
                "      AND ct.numeroCuenta NOT LIKE '%-%' " +
                "      AND EXISTS ( " +
                "          SELECT 1 " +
                "          FROM rn_gc_db_test.rn_gc_catalogo_cuentas_tbl h " +
                "          WHERE h.creadoPor = ? " +
                "            AND h.numeroCuenta LIKE CONCAT(ct.numeroCuenta, '-%') " +
                "      ) " +
                ") " +

                "SELECT " +
                "    t.id, " +
                "    DATE_FORMAT(t.fechaCreacion, '%d-%m-%Y') AS fechaCreacion, " +
                "    t.periodo, " +
                "    t.tipoPoliza, " +
                "    t.numeroPoliza, " +
                "    t.numeroCuenta, " +
                "    t.descripcionCuenta, " +
                "    t.cargo, " +
                "    t.abono, " +
                "    t.importeEstadoResultado " +
                "FROM ( " +

                "    SELECT " +
                "        p.id, " +
                "        p.numeroCuenta AS cuentaOrden, " +
                "        0 AS ordenFila, " +
                "        NULL AS fechaCreacion, " +
                "        '' AS periodo, " +
                "        '' AS tipoPoliza, " +
                "        '' AS numeroPoliza, " +
                "        p.numeroCuenta, " +
                "        p.descripcionCuenta, " +
                "        0 AS cargo, " +
                "        0 AS abono, " +
                "        COALESCE(SUM(m.totalAbono - m.totalCargo), 0) AS importeEstadoResultado " +
                "    FROM padres p " +
                "    JOIN movimientos m " +
                "      ON m.numeroCuenta = p.numeroCuenta " +
                "      OR m.numeroCuenta LIKE CONCAT(p.numeroCuenta, '-%') " +
                "    GROUP BY " +
                "        p.id, " +
                "        p.numeroCuenta, " +
                "        p.descripcionCuenta " +

                "    UNION ALL " +

                "    SELECT " +
                "        m.id, " +
                "        p.numeroCuenta AS cuentaOrden, " +
                "        1 AS ordenFila, " +
                "        m.fechaCreacion, " +
                "        m.periodo, " +
                "        m.tipoPoliza, " +
                "        m.numeroPoliza, " +
                "        m.numeroCuenta, " +
                "        CONCAT('   ', m.descripcionCuenta) AS descripcionCuenta, " +
                "        m.totalCargo AS cargo, " +
                "        m.totalAbono AS abono, " +
                "        COALESCE(m.totalAbono - m.totalCargo, 0) AS importeEstadoResultado " +
                "    FROM padres p " +
                "    JOIN movimientos m " +
                "      ON m.numeroCuenta LIKE CONCAT(p.numeroCuenta, '-%') " +

                ") t " +
                "ORDER BY t.cuentaOrden, t.ordenFila, t.numeroCuenta";

        Query q = em.createNativeQuery(sql);

        int idx = 1;
        q.setParameter(idx++, fechaDesde, TemporalType.TIMESTAMP);
        q.setParameter(idx++, fechaHastaMasUno, TemporalType.TIMESTAMP);
        q.setParameter(idx++, idUsuario);
        q.setParameter(idx++, idUsuario);
        q.setParameter(idx++, idUsuario);
        q.setParameter(idx++, idUsuario);

        @SuppressWarnings("unchecked")
        List<Object[]> result = q.getResultList();

        List<AuxiliarCuentasRow> out = new ArrayList<>();

        for (Object[] r : result) {
            AuxiliarCuentasRow row = new AuxiliarCuentasRow();

            String numeroCuenta = r[5] == null ? "" : r[5].toString();
            boolean esPadre = numeroCuenta != null
                    && !numeroCuenta.trim().isEmpty()
                    && !numeroCuenta.contains("-");

            row.setId(r[0] == null ? null : ((Number) r[0]).intValue());

            if (esPadre) {
                row.setFechaCreacion("");
                row.setPeriodo("");
                row.setTipoPoliza("");
                row.setNumeroPoliza("");
            } else {
                row.setFechaCreacion(r[1] == null ? "" : r[1].toString());
                row.setPeriodo(r[2] == null ? "" : r[2].toString());
                row.setTipoPoliza(r[3] == null ? "" : r[3].toString());
                row.setNumeroPoliza(r[4] == null ? "" : r[4].toString());
            }

            row.setNumeroCuenta(numeroCuenta);
            row.setDescripcionCuenta(r[6] == null ? "" : r[6].toString());
            row.setCargo(toBigDecimal(r[7]));
            row.setAbono(toBigDecimal(r[8]));
            row.setImporteEstadoResultado(toBigDecimal(r[9]));

            out.add(row);
        }

        return out;
    }
    
    @PermitAll
    public List<LibroMayorRow> reporteLibroMayor(Date fechaDesde, Date fechaHasta, Integer idUsuario) {

        if (fechaDesde == null || fechaHasta == null || idUsuario == null) {
            return new ArrayList<>();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaHasta);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date fechaHastaMasUno = cal.getTime();

        String sql =
                "WITH meses AS ( " +
                "    SELECT 1 AS mesNumero, 'ENERO' AS mesNombre " +
                "    UNION ALL SELECT 2, 'FEBRERO' " +
                "    UNION ALL SELECT 3, 'MARZO' " +
                "    UNION ALL SELECT 4, 'ABRIL' " +
                "    UNION ALL SELECT 5, 'MAYO' " +
                "    UNION ALL SELECT 6, 'JUNIO' " +
                "    UNION ALL SELECT 7, 'JULIO' " +
                "    UNION ALL SELECT 8, 'AGOSTO' " +
                "    UNION ALL SELECT 9, 'SEPTIEMBRE' " +
                "    UNION ALL SELECT 10, 'OCTUBRE' " +
                "    UNION ALL SELECT 11, 'NOVIEMBRE' " +
                "    UNION ALL SELECT 12, 'DICIEMBRE' " +
                "), " +

                "cuentas AS ( " +
                "    SELECT " +
                "        ct.id, " +
                "        TRIM(ct.numeroCuenta) AS numeroCuenta, " +
                "        ct.descripcionCuenta " +
                "    FROM rn_gc_db_test.rn_gc_catalogo_cuentas_tbl ct " +
                "    WHERE ct.creadoPor = ? " +
                "      AND EXISTS ( " +
                "          SELECT 1 " +
                "          FROM rn_gc_db_test.rn_gc_poliza_lineas_tbl plx " +
                "          INNER JOIN rn_gc_db_test.rn_gc_poliza_header_tbl phx " +
                "              ON phx.id = plx.polizaHeaderId " +
                "          WHERE plx.catalogoCuentasId = ct.id " +
                "            AND phx.fecha < ? " +
                "            AND phx.creadoPor = ? " +
                "            AND plx.creadoPor = ? " +
                "      ) " +
                "), " +

                "saldo_inicial AS ( " +
                "    SELECT " +
                "        c.id AS cuentaId, " +
                "        CASE " +
                "            WHEN c.numeroCuenta LIKE '1%' " +
                "              OR c.numeroCuenta LIKE '5%' " +
                "              OR c.numeroCuenta LIKE '6%' " +
                "              OR c.numeroCuenta LIKE '7%' " +
                "            THEN COALESCE(SUM(COALESCE(pl.cargo, 0) - COALESCE(pl.abono, 0)), 0) " +
                "            ELSE COALESCE(SUM(COALESCE(pl.abono, 0) - COALESCE(pl.cargo, 0)), 0) " +
                "        END AS saldoInicial " +
                "    FROM cuentas c " +
                "    LEFT JOIN rn_gc_db_test.rn_gc_poliza_lineas_tbl pl " +
                "        ON pl.catalogoCuentasId = c.id " +
                "    LEFT JOIN rn_gc_db_test.rn_gc_poliza_header_tbl ph " +
                "        ON ph.id = pl.polizaHeaderId " +
                "       AND ph.fecha < ? " +
                "       AND ph.creadoPor = ? " +
                "    WHERE pl.creadoPor = ? " +
                "       OR pl.creadoPor IS NULL " +
                "    GROUP BY c.id, c.numeroCuenta " +
                "), " +

                "mov_mensual AS ( " +
                "    SELECT " +
                "        c.id AS cuentaId, " +
                "        MONTH(ph.fecha) AS mesNumero, " +
                "        COALESCE(SUM(COALESCE(pl.cargo, 0)), 0) AS cargo, " +
                "        COALESCE(SUM(COALESCE(pl.abono, 0)), 0) AS abono " +
                "    FROM cuentas c " +
                "    INNER JOIN rn_gc_db_test.rn_gc_poliza_lineas_tbl pl " +
                "        ON pl.catalogoCuentasId = c.id " +
                "    INNER JOIN rn_gc_db_test.rn_gc_poliza_header_tbl ph " +
                "        ON ph.id = pl.polizaHeaderId " +
                "    WHERE ph.fecha >= ? " +
                "      AND ph.fecha < ? " +
                "      AND ph.creadoPor = ? " +
                "      AND pl.creadoPor = ? " +
                "    GROUP BY c.id, MONTH(ph.fecha) " +
                ") " +

                "SELECT " +
                "    c.numeroCuenta, " +
                "    c.descripcionCuenta, " +
                "    m.mesNumero, " +
                "    m.mesNombre, " +
                "    COALESCE(si.saldoInicial, 0) AS saldoInicial, " +
                "    COALESCE(mm.cargo, 0) AS cargo, " +
                "    COALESCE(mm.abono, 0) AS abono " +
                "FROM cuentas c " +
                "CROSS JOIN meses m " +
                "LEFT JOIN saldo_inicial si " +
                "    ON si.cuentaId = c.id " +
                "LEFT JOIN mov_mensual mm " +
                "    ON mm.cuentaId = c.id " +
                "   AND mm.mesNumero = m.mesNumero " +
                "WHERE m.mesNumero BETWEEN MONTH(?) AND MONTH(?) " +
                "ORDER BY c.numeroCuenta, m.mesNumero";

        Query q = em.createNativeQuery(sql);

        int idx = 1;

        q.setParameter(idx++, idUsuario);

        q.setParameter(idx++, fechaHastaMasUno, TemporalType.TIMESTAMP);
        q.setParameter(idx++, idUsuario);
        q.setParameter(idx++, idUsuario);

        q.setParameter(idx++, fechaDesde, TemporalType.TIMESTAMP);
        q.setParameter(idx++, idUsuario);
        q.setParameter(idx++, idUsuario);

        q.setParameter(idx++, fechaDesde, TemporalType.TIMESTAMP);
        q.setParameter(idx++, fechaHastaMasUno, TemporalType.TIMESTAMP);
        q.setParameter(idx++, idUsuario);
        q.setParameter(idx++, idUsuario);

        // Estos son los nuevos para limitar los meses visibles
        q.setParameter(idx++, fechaDesde, TemporalType.TIMESTAMP);
        q.setParameter(idx++, fechaHasta, TemporalType.TIMESTAMP);

        @SuppressWarnings("unchecked")
        List<Object[]> result = q.getResultList();

        List<LibroMayorRow> out = new ArrayList<>();

        String cuentaActual = null;
        String descripcionActual = null;

        BigDecimal saldoInicial = BigDecimal.ZERO;
        BigDecimal saldoAcumulado = BigDecimal.ZERO;
        BigDecimal totalCargo = BigDecimal.ZERO;
        BigDecimal totalAbono = BigDecimal.ZERO;

        for (Object[] r : result) {

            String numeroCuenta = r[0] == null ? "" : r[0].toString();
            String descripcionCuenta = r[1] == null ? "" : r[1].toString();
            Integer mesNumero = r[2] == null ? 0 : ((Number) r[2]).intValue();
            String mesNombre = r[3] == null ? "" : r[3].toString();
            BigDecimal saldoInicialRow = toBigDecimal(r[4]);
            BigDecimal cargo = toBigDecimal(r[5]);
            BigDecimal abono = toBigDecimal(r[6]);

            if (cuentaActual == null || !cuentaActual.equals(numeroCuenta)) {

                if (cuentaActual != null) {
                    out.add(new LibroMayorRow(
                            cuentaActual,
                            descripcionActual,
                            "Totales",
                            99,
                            totalCargo,
                            totalAbono,
                            saldoAcumulado,
                            2
                    ));
                }

                cuentaActual = numeroCuenta;
                descripcionActual = descripcionCuenta;
                saldoInicial = saldoInicialRow;
                saldoAcumulado = saldoInicial;
                totalCargo = BigDecimal.ZERO;
                totalAbono = BigDecimal.ZERO;

                out.add(new LibroMayorRow(
                        cuentaActual,
                        descripcionActual,
                        "",
                        0,
                        null,
                        null,
                        saldoInicial,
                        0
                ));
            }

            BigDecimal movimientoMes;

            if (esCuentaDeudoraLibroMayor(numeroCuenta)) {
                movimientoMes = cargo.subtract(abono);
            } else {
                movimientoMes = abono.subtract(cargo);
            }

            saldoAcumulado = saldoAcumulado.add(movimientoMes);
            totalCargo = totalCargo.add(cargo);
            totalAbono = totalAbono.add(abono);

            out.add(new LibroMayorRow(
                    numeroCuenta,
                    descripcionCuenta,
                    mesNombre,
                    mesNumero,
                    cargo,
                    abono,
                    saldoAcumulado,
                    1
            ));
        }

        if (cuentaActual != null) {
            out.add(new LibroMayorRow(
                    cuentaActual,
                    descripcionActual,
                    "Totales",
                    99,
                    totalCargo,
                    totalAbono,
                    saldoAcumulado,
                    2
            ));
        }

        return out;
    }
    
    @PermitAll
    public List<LibroDiarioRow> reporteLibroDiario(Date fechaDesde, Date fechaHasta, Integer idUsuario) {

        if (fechaDesde == null || fechaHasta == null || idUsuario == null) {
            return new ArrayList<>();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaHasta);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date fechaHastaMasUno = cal.getTime();

        String sql =
                "SELECT " +
                "    ph.id AS polizaId, " +
                "    ph.fecha AS fechaPoliza, " +
                "    COALESCE(tp.descripcion, '') AS tipoPoliza, " +
                "    'Matriz' AS sucursal, " +
                "    pl.id AS lineaId, " +
                "    TRIM(ct.numeroCuenta) AS numeroCuenta, " +
                "    ct.descripcionCuenta, " +
                "    COALESCE(pl.cargo, 0) AS cargo, " +
                "    COALESCE(pl.abono, 0) AS abono " +
                "FROM rn_gc_db_test.rn_gc_poliza_header_tbl ph " +
                "INNER JOIN rn_gc_db_test.rn_gc_poliza_lineas_tbl pl " +
                "    ON pl.polizaHeaderId = ph.id " +
                "INNER JOIN rn_gc_db_test.rn_gc_catalogo_cuentas_tbl ct " +
                "    ON ct.id = pl.catalogoCuentasId " +
                "LEFT JOIN rn_gc_db_test.rn_gc_tipo_poliza tp " +
                "    ON tp.id = ph.tipoPolizaId " +
                "WHERE ph.fecha >= ? " +
                "  AND ph.fecha < ? " +
                "  AND ph.creadoPor = ? " +
                "  AND pl.creadoPor = ? " +
                "  AND ct.creadoPor = ? " +
                "ORDER BY ph.fecha, ph.id, pl.id";

        Query q = em.createNativeQuery(sql);

        int idx = 1;
        q.setParameter(idx++, fechaDesde, TemporalType.TIMESTAMP);
        q.setParameter(idx++, fechaHastaMasUno, TemporalType.TIMESTAMP);
        q.setParameter(idx++, idUsuario);
        q.setParameter(idx++, idUsuario);
        q.setParameter(idx++, idUsuario);

        @SuppressWarnings("unchecked")
        List<Object[]> result = q.getResultList();

        List<LibroDiarioRow> out = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        Integer polizaActual = null;
        String fechaActual = "";
        String tipoPolizaActual = "";
        String numeroPolizaActual = "";

        BigDecimal totalCargo = BigDecimal.ZERO;
        BigDecimal totalAbono = BigDecimal.ZERO;

        for (Object[] r : result) {

            Integer polizaId = r[0] == null ? null : ((Number) r[0]).intValue();
            Date fechaPolizaDate = (Date) r[1];
            String fechaPoliza = fechaPolizaDate != null ? sdf.format(fechaPolizaDate) : "";
            String tipoPoliza = r[2] == null ? "" : r[2].toString();
            String sucursal = r[3] == null ? "" : r[3].toString();
            String numeroCuenta = r[5] == null ? "" : r[5].toString();
            String descripcionCuenta = r[6] == null ? "" : r[6].toString();
            BigDecimal cargo = toBigDecimal(r[7]);
            BigDecimal abono = toBigDecimal(r[8]);

            if (polizaActual == null || !polizaActual.equals(polizaId)) {

                if (polizaActual != null) {
                    out.add(new LibroDiarioRow(
                            "",
                            tipoPolizaActual,
                            fechaActual,
                            numeroPolizaActual,
                            "",
                            "",
                            totalCargo,
                            totalAbono,
                            2
                    ));
                }

                polizaActual = polizaId;
                fechaActual = fechaPoliza;
                tipoPolizaActual = tipoPoliza;
                numeroPolizaActual = polizaId != null ? polizaId.toString() : "";

                totalCargo = BigDecimal.ZERO;
                totalAbono = BigDecimal.ZERO;

                out.add(new LibroDiarioRow(
                        "",
                        tipoPolizaActual,
                        fechaActual,
                        numeroPolizaActual,
                        "",
                        "",
                        null,
                        null,
                        0
                ));
            }

            out.add(new LibroDiarioRow(
                    sucursal,
                    tipoPoliza,
                    fechaPoliza,
                    polizaId != null ? polizaId.toString() : "",
                    numeroCuenta,
                    descripcionCuenta,
                    cargo,
                    abono,
                    1
            ));

            totalCargo = totalCargo.add(cargo);
            totalAbono = totalAbono.add(abono);
        }

        if (polizaActual != null) {
            out.add(new LibroDiarioRow(
                    "",
                    tipoPolizaActual,
                    fechaActual,
                    numeroPolizaActual,
                    "",
                    "",
                    totalCargo,
                    totalAbono,
                    2
            ));
        }

        return out;
    }

    private BigDecimal toBigDecimal(Object o) {
        if (o == null) {
            return BigDecimal.ZERO;
        }

        if (o instanceof BigDecimal) {
            return (BigDecimal) o;
        }

        if (o instanceof Number) {
            return BigDecimal.valueOf(((Number) o).doubleValue());
        }

        try {
            return new BigDecimal(o.toString());
        } catch (Exception ex) {
            return BigDecimal.ZERO;
        }
    }

    private boolean esCuentaDeudoraLibroMayor(String numeroCuenta) {
        if (numeroCuenta == null) {
            return true;
        }

    String c = numeroCuenta.trim();

    return c.startsWith("1")
            || c.startsWith("5")
            || c.startsWith("6")
            || c.startsWith("7");
    }
}
