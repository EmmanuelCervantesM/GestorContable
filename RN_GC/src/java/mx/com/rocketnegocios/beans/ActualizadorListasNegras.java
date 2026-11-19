package mx.com.rocketnegocios.beans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import mx.com.rocketnegocios.entities.RnGcListaNegraRevisionTbl;
import mx.com.rocketnegocios.util.listanegra.DescargaSat;
import mx.com.rocketnegocios.util.listanegra.ListaNegraCsv;
import mx.com.rocketnegocios.util.listanegra.TipoListaNegra;

/**
 * CTR-12 (S13): actualización automática de las listas negras y cruce con los CFDI.
 * <ul>
 * <li>DOF (49 Bis X): cada hora revisa los días pendientes, del más reciente al más antiguo,
 * desde el 01/01/2026 (entrada en vigor de la fracción X). Un día queda cerrado cuando se
 * revisó sin error después de que terminó; los días con error se reintentan hasta
 * {@link #MAX_INTENTOS} veces.</li>
 * <li>CSV del SAT (69-B y 69-B Bis): una vez al día compara el ETag y solo baja y carga el
 * archivo si cambió. La carga manual sigue disponible.</li>
 * <li>CFDI: cada hora cruza los CFDI creados o modificados desde la pasada anterior; la
 * primera vez revisa todo el histórico.</li>
 * </ul>
 * Cada paso corre en su propia transacción (los métodos del servicio), así un día o un
 * archivo que falla no deshace lo demás. Las cargas quedan con creadoPor = 0 (sistema).
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Lock(LockType.WRITE)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ActualizadorListasNegras {

    private static final Logger LOG = Logger.getLogger(ActualizadorListasNegras.class.getName());

    public static final int USUARIO_SISTEMA = 0;
    static final int MAX_INTENTOS = 5;
    static final int DIAS_POR_PASADA = 15;
    /** Un listado nuevo con menos de esta fracción de los registros vigentes no se carga. */
    static final double MINIMO_REGISTROS = 0.5;

    @EJB
    private ListaNegraService servicio;

    @Schedule(minute = "15", hour = "*", persistent = false)
    public void revisarDof() {
        Date hoy = inicioDelDia(new Date());
        Date inicio = inicioDof();
        Map<String, RnGcListaNegraRevisionTbl> revisiones = servicio.revisionesDof(inicio);
        SimpleDateFormat clave = new SimpleDateFormat("yyyy-MM-dd");
        List<Date> pendientes = new ArrayList<>();
        Calendar dia = Calendar.getInstance();
        dia.setTime(hoy);
        while (!dia.getTime().before(inicio) && pendientes.size() < DIAS_POR_PASADA) {
            if (pendiente(revisiones.get(clave.format(dia.getTime())), dia.getTime())) {
                pendientes.add(dia.getTime());
            }
            dia.add(Calendar.DAY_OF_MONTH, -1);
        }
        for (Date fecha : pendientes) {
            try {
                ListaNegraService.Resumen49BisX r = servicio.importar49BisX(fecha, USUARIO_SISTEMA);
                List<String> partes = new ArrayList<>(r.getCargados());
                partes.addAll(r.getOmitidos());
                partes.addAll(r.getErrores());
                servicio.registrarRevision(RnGcListaNegraRevisionTbl.FUENTE_DOF, fecha,
                        r.getErrores().isEmpty() ? RnGcListaNegraRevisionTbl.OK : RnGcListaNegraRevisionTbl.ERROR, null,
                        partes.isEmpty() ? "Sin oficios de 49 Bis X." : String.join(" ", partes)
                        + (r.getAvisos() > 0 ? " Avisos: " + r.getAvisos() + "." : ""));
            } catch (Exception ex) {
                LOG.log(Level.WARNING, "CTR-12: falló la revisión del DOF del " + clave.format(fecha), ex);
                registrarError(RnGcListaNegraRevisionTbl.FUENTE_DOF, fecha, ex);
            }
        }
    }

    @Schedule(minute = "30", hour = "6", persistent = false)
    public void revisarCsvSat() {
        for (TipoListaNegra tipo : TipoListaNegra.values()) {
            if (tipo.getUrlCsv() != null) {
                revisarCsv(tipo);
            }
        }
    }

    private void revisarCsv(TipoListaNegra tipo) {
        String fuente = RnGcListaNegraRevisionTbl.fuenteCsv(tipo.getEtiqueta());
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            DescargaSat.Cabecera cab = DescargaSat.cabecera(tipo.getUrlCsv());
            String publicado = cab.getUltimaModificacion() != null ? f.format(cab.getUltimaModificacion()) : "sin fecha";
            RnGcListaNegraRevisionTbl previa = servicio.revision(fuente);
            if (previa != null && RnGcListaNegraRevisionTbl.OK.equals(previa.getEstado())
                    && cab.getEtag() != null && cab.getEtag().equals(previa.getMarca())) {
                servicio.registrarRevision(fuente, null, RnGcListaNegraRevisionTbl.OK, null,
                        "Sin cambios; el archivo del SAT es el publicado el " + publicado + ".");
                return;
            }
            ListaNegraCsv.Resultado lectura = ListaNegraCsv.leer(DescargaSat.bajar(tipo.getUrlCsv()), tipo);
            if (lectura.getError() != null) {
                servicio.registrarRevision(fuente, null, RnGcListaNegraRevisionTbl.ERROR, null, lectura.getError());
                return;
            }
            long vigentes = servicio.contarActivos(tipo);
            if (lectura.getFilas().size() < vigentes * MINIMO_REGISTROS) {
                // Un archivo cortado daría de baja a casi toda la lista; mejor esperar al siguiente.
                servicio.registrarRevision(fuente, null, RnGcListaNegraRevisionTbl.ERROR, null,
                        "El archivo publicado el " + publicado + " trae " + lectura.getFilas().size()
                        + " RFC y hay " + vigentes + " vigentes; no se cargó.");
                return;
            }
            Date fechaPublicacion = cab.getUltimaModificacion() != null ? cab.getUltimaModificacion() : new Date();
            ListaNegraService.Resumen r = servicio.importar(tipo, lectura.getFilas(), USUARIO_SISTEMA,
                    "SAT " + tipo.getEtiqueta() + " " + publicado, fechaPublicacion, true);
            servicio.registrarRevision(fuente, null, RnGcListaNegraRevisionTbl.OK, cab.getEtag(),
                    "Cargado el archivo publicado el " + publicado + ": " + lectura.getFilas().size() + " RFC ("
                    + r.getNuevos() + " nuevos, " + r.getActualizados() + " actualizados, " + r.getSinCambios()
                    + " sin cambios, " + r.getCambiosEstado() + " con cambio de estado, " + r.getBajas()
                    + " ya no aparecen). " + lectura.getErrores().size() + " registros con error. Avisos: " + r.getAvisos() + ".");
        } catch (Exception ex) {
            LOG.log(Level.WARNING, "CTR-12: falló la revisión del CSV " + tipo.getEtiqueta(), ex);
            registrarError(fuente, null, ex);
        }
    }

    @Schedule(minute = "45", hour = "*", persistent = false)
    public void cruzarCfdiNuevos() {
        Date inicio = new Date();
        try {
            RnGcListaNegraRevisionTbl previa = servicio.revision(RnGcListaNegraRevisionTbl.FUENTE_CFDI);
            Date desde = previa != null && previa.getMarca() != null ? new Date(Long.parseLong(previa.getMarca())) : new Date(0);
            int avisos = servicio.cruzarCfdiDesde(desde);
            servicio.registrarRevision(RnGcListaNegraRevisionTbl.FUENTE_CFDI, null, RnGcListaNegraRevisionTbl.OK,
                    String.valueOf(inicio.getTime()), (desde.getTime() == 0 ? "Histórico completo" : "CFDI desde "
                    + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(desde)) + ". Avisos: " + avisos + ".");
        } catch (Exception ex) {
            LOG.log(Level.WARNING, "CTR-12: falló el cruce de listas negras con CFDI", ex);
            registrarError(RnGcListaNegraRevisionTbl.FUENTE_CFDI, null, ex);
        }
    }

    /** Botón "Revisar ahora" del administrador: corre todo en segundo plano. */
    @Asynchronous
    public void revisarTodo() {
        revisarCsvSat();
        revisarDof();
        cruzarCfdiNuevos();
    }

    private void registrarError(String fuente, Date fecha, Exception ex) {
        try {
            servicio.registrarRevision(fuente, fecha, RnGcListaNegraRevisionTbl.ERROR, null,
                    ex.getClass().getSimpleName() + ": " + ex.getMessage());
        } catch (Exception otra) {
            LOG.log(Level.SEVERE, "CTR-12: no se pudo guardar la revisión de " + fuente, otra);
        }
    }

    /**
     * Un día está pendiente si nunca se revisó, si falló y le quedan intentos, o si la última
     * revisión sin error fue antes de que terminara el día (aún podía salir la edición vespertina).
     */
    static boolean pendiente(RnGcListaNegraRevisionTbl r, Date dia) {
        if (r == null) {
            return true;
        }
        if (RnGcListaNegraRevisionTbl.ERROR.equals(r.getEstado())) {
            return r.getIntentos() < MAX_INTENTOS;
        }
        Calendar fin = Calendar.getInstance();
        fin.setTime(dia);
        fin.add(Calendar.DAY_OF_MONTH, 1);
        return r.getFechaRevision().before(fin.getTime());
    }

    static Date inicioDof() {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2026, Calendar.JANUARY, 1);
        return cal.getTime();
    }

    private static Date inicioDelDia(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
