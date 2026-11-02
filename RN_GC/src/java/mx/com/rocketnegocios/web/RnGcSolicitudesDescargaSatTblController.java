package mx.com.rocketnegocios.web;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import mx.com.rocketnegocios.beans.RnGcSolicitudesDescargaSatTblFacade;
import mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade;
import mx.com.rocketnegocios.beans.VerificadorDescargaSat;
import mx.com.rocketnegocios.entities.RnGcSolicitudesDescargaSatTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.SatDescargaMasiva.DescargaMasivaSatCliente;
import mx.com.rocketnegocios.util.SatDescargaMasiva.DescargaMasivaSatException;
import mx.com.rocketnegocios.util.SatDescargaMasiva.ResultadoSolicitudSat;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * CTR-06 (fase 1): solicitud manual de Descarga Masiva de CFDI al SAT,
 * seguimiento de solicitudes y descarga de los paquetes ZIP bajo demanda.
 * Los XML no se cargan al sistema en esta fase.
 */
@Named("rnGcSolicitudesDescargaSatTblController")
@SessionScoped
public class RnGcSolicitudesDescargaSatTblController implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(RnGcSolicitudesDescargaSatTblController.class.getName());

    /** Ejercicios anteriores consultables ademas del actual (5 + actual). */
    private static final int EJERCICIOS_ANTERIORES = 25;

    public static final String URL_PANTALLA = "/serv/rnGcSolicitudesDescargaSatTbl/descargaSat";

    @EJB
    private RnGcSolicitudesDescargaSatTblFacade solicitudesFacade;

    @EJB
    private RnGcUsuariosTblFacade usuariosFacade;

    @EJB
    private VerificadorDescargaSat verificador;

    private final UsuarioFirmado usuarioFirmado = new UsuarioFirmado();

    private String tipo = DescargaMasivaSatCliente.TIPO_EMITIDOS;
    // Emitidos
    private Date fechaInicial;
    private Date fechaFinal;
    private String estadoComprobante = DescargaMasivaSatCliente.ESTADO_TODOS;
    // Recibidos
    private Integer anio;
    private Integer mes;
    private Integer dia;

    private List<RnGcSolicitudesDescargaSatTbl> solicitudes;

    /**
     * Al abrir la pantalla: refresca la lista y da por vistos los avisos del
     * banner.
     */
    public void alCargarPagina() {
        if (FacesContext.getCurrentInstance().isPostback()) {
            return;
        }
        RnGcUsuariosTbl usuario = obtenerUsuario();
        solicitudesFacade.marcarAvisosVistos(usuario);
        solicitudes = solicitudesFacade.obtenerSolicitudesDeUsuario(usuario);
    }

    public void solicitarDescarga() {
        RnGcUsuariosTbl usuario = obtenerUsuario();
        Date[] periodo = calcularPeriodo();
        if (periodo == null) {
            return;
        }
        boolean recibidos = DescargaMasivaSatCliente.TIPO_RECIBIDOS.equals(tipo);
        String estado = recibidos ? DescargaMasivaSatCliente.ESTADO_VIGENTE : estadoComprobante;
        try {
            DescargaMasivaSatCliente cliente = verificador.crearCliente(usuario);
            ResultadoSolicitudSat resultado = recibidos
                    ? cliente.solicitarRecibidos(periodo[0], periodo[1])
                    : cliente.solicitarEmitidos(periodo[0], periodo[1], estado);

            Date ahora = new Date();
            int usuarioId = usuario.getId();
            RnGcSolicitudesDescargaSatTbl solicitud = new RnGcSolicitudesDescargaSatTbl();
            solicitud.setUsuariosId(usuario);
            solicitud.setTipo(
                    recibidos ? DescargaMasivaSatCliente.TIPO_RECIBIDOS : DescargaMasivaSatCliente.TIPO_EMITIDOS);
            solicitud.setFechaInicial(periodo[0]);
            solicitud.setFechaFinal(periodo[1]);
            solicitud.setEstadoComprobante(estado);
            solicitud.setIdSolicitud(resultado.getIdSolicitud());
            solicitud.setEstadoSolicitud(RnGcSolicitudesDescargaSatTbl.ESTADO_ACEPTADA);
            solicitud.setCodEstatus(resultado.getCodEstatus());
            solicitud.setMensaje(resultado.getMensaje());
            solicitud.setFechaSolicitud(ahora);
            solicitud.setAvisoVisto(false);
            solicitud.setCreadoPor(usuarioId);
            solicitud.setFechaCreacion(ahora);
            solicitud.setUltimaActualizacionPor(usuarioId);
            solicitud.setUltimaFechaActualizacion(ahora);
            try {
                solicitudesFacade.create(solicitud);
            } catch (RuntimeException ex) {
                // La solicitud ya existe en el SAT: se muestra el IdSolicitud para no perderlo.
                LOG.log(Level.SEVERE,
                        "CTR-06: solicitud " + resultado.getIdSolicitud() + " aceptada por el SAT pero no guardada",
                        ex);
                mensaje(FacesMessage.SEVERITY_ERROR,
                        "La solicitud fue aceptada por el SAT (Id " + resultado.getIdSolicitud()
                                + ") pero no se pudo guardar. Reporta este Id al administrador.");
                return;
            }
            mensaje(FacesMessage.SEVERITY_INFO, "Solicitud enviada al SAT (Id " + resultado.getIdSolicitud()
                    + "). El SAT puede tardar de minutos a horas en prepararla; te avisaremos en cuanto esté lista.");
            solicitudes = solicitudesFacade.obtenerSolicitudesDeUsuario(usuario);
        } catch (DescargaMasivaSatException ex) {
            mensaje(FacesMessage.SEVERITY_ERROR, ex.getMessage());
        }
    }

    /**
     * Boton "Actualizar estado": verifica ahora con el SAT las solicitudes
     * pendientes.
     */
    public void actualizarEstado() {
        RnGcUsuariosTbl usuario = obtenerUsuario();
        int consultadas = verificador.verificarPendientes(usuario.getId(), true);
        solicitudesFacade.marcarAvisosVistos(usuario);
        solicitudes = solicitudesFacade.obtenerSolicitudesDeUsuario(usuario);
        mensaje(FacesMessage.SEVERITY_INFO, consultadas == 0
                ? "No hay solicitudes pendientes por verificar."
                : "Se verificaron " + consultadas + " solicitud(es) con el SAT.");
    }

    /**
     * Descarga un paquete directamente del SAT (no se guarda en BD). Se
     * valida que la solicitud pertenezca al usuario en sesion.
     */
    public StreamedContent descargarPaquete(RnGcSolicitudesDescargaSatTbl fila, String idPaquete) {
        RnGcUsuariosTbl usuario = obtenerUsuario();
        // La pantalla pudo quedar abierta mas alla del plazo de vigencia.
        solicitudesFacade.marcarVencidas(usuario);
        RnGcSolicitudesDescargaSatTbl solicitud = fila == null ? null : solicitudesFacade.find(fila.getId());
        if (solicitud == null || solicitud.getUsuariosId() == null
                || !solicitud.getUsuariosId().getId().equals(usuario.getId())
                || !solicitud.getListaPaquetes().contains(idPaquete)) {
            mensaje(FacesMessage.SEVERITY_ERROR, "Paquete no disponible.");
            return null;
        }
        if (solicitud.getEstadoSolicitud() == null
                || solicitud.getEstadoSolicitud() != RnGcSolicitudesDescargaSatTbl.ESTADO_TERMINADA) {
            mensaje(FacesMessage.SEVERITY_ERROR, "El paquete ya venció en el SAT (se conserva aprox. "
                    + RnGcSolicitudesDescargaSatTbl.HORAS_VIGENCIA_PAQUETES + " horas). Solicita la descarga de nuevo.");
            return null;
        }
        try {
            byte[] zip = verificador.crearCliente(usuario).descargarPaquete(idPaquete);
            Date ahora = new Date();
            solicitud.setFechaDescarga(ahora);
            solicitud.setUltimaActualizacionPor(usuario.getId());
            solicitud.setUltimaFechaActualizacion(ahora);
            solicitudesFacade.edit(solicitud);
            return new DefaultStreamedContent(new ByteArrayInputStream(zip), "application/zip", idPaquete + ".zip");
        } catch (DescargaMasivaSatException ex) {
            mensaje(FacesMessage.SEVERITY_ERROR, ex.getMessage());
            return null;
        }
    }

    // ------------------------------------------------------------------
    // Banner CTR-03 (admin.xhtml)
    // ------------------------------------------------------------------

    public List<RnGcSolicitudesDescargaSatTbl> obtenerAvisosDescargaSat() {
        FacesContext contexto = FacesContext.getCurrentInstance();
        // Se consulta una sola vez por request: el dialog evalua la lista varias veces.
        @SuppressWarnings("unchecked")
        List<RnGcSolicitudesDescargaSatTbl> avisos = (List<RnGcSolicitudesDescargaSatTbl>) contexto.getAttributes()
                .get("ctr06Avisos");
        if (avisos == null) {
            avisos = solicitudesFacade.obtenerAvisosDeUsuario(obtenerUsuario());
            contexto.getAttributes().put("ctr06Avisos", avisos);
            // Se dan por vistos al mostrarse en el banner para que el aviso global no los repita.
            List<Integer> ids = new ArrayList<>();
            for (RnGcSolicitudesDescargaSatTbl aviso : avisos) {
                ids.add(aviso.getId());
            }
            solicitudesFacade.marcarAvisosVistos(ids);
        }
        return avisos;
    }

    public boolean isMostrarAlertaDescargaSat() {
        return !obtenerAvisosDescargaSat().isEmpty();
    }

    public String mensajeAviso(RnGcSolicitudesDescargaSatTbl solicitud) {
        return textoAviso(solicitud);
    }

    /** Texto del aviso de una solicitud en estado final (banner y aviso global). */
    public static String textoAviso(RnGcSolicitudesDescargaSatTbl solicitud) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String periodo = (DescargaMasivaSatCliente.TIPO_RECIBIDOS.equals(solicitud.getTipo()) ? "recibidos"
                : "emitidos")
                + " del " + formato.format(solicitud.getFechaInicial()) + " al "
                + formato.format(solicitud.getFechaFinal());
        if (solicitud.getEstadoSolicitud() == RnGcSolicitudesDescargaSatTbl.ESTADO_TERMINADA) {
            return "Tu descarga de CFDI " + periodo + " está lista (" + solicitud.getNumeroCfdis() + " CFDI).";
        }
        String causa = RnGcSolicitudesDescargaSatTbl.descripcionCodigoEstado(solicitud.getCodigoEstadoSolicitud());
        if (causa != null) {
            return "Tu descarga de CFDI " + periodo + " terminó con estado " + solicitud.getEstadoSolicitudTexto()
                    + ". Respuesta del SAT (código " + solicitud.getCodigoEstadoSolicitud() + "): " + causa + ".";
        }
        return "Tu descarga de CFDI " + periodo + " terminó con estado " + solicitud.getEstadoSolicitudTexto()
                + (solicitud.getMensaje() == null ? "." : ". Mensaje del SAT: " + solicitud.getMensaje());
    }

    // ------------------------------------------------------------------
    // Validaciones
    // ------------------------------------------------------------------

    /** @return {inicio, fin} o null si hay errores (ya agregados como mensaje). */
    private Date[] calcularPeriodo() {
        Date inicio;
        Date fin;
        Date limite = finDeHoy();
        if (DescargaMasivaSatCliente.TIPO_RECIBIDOS.equals(tipo)) {
            if (anio == null || mes == null) {
                mensaje(FacesMessage.SEVERITY_WARN, "Selecciona año y mes.");
                return null;
            }
            Calendar calendario = Calendar.getInstance();
            calendario.clear();
            calendario.setLenient(false);
            try {
                calendario.set(anio, mes - 1, dia == null ? 1 : dia, 0, 0, 0);
                inicio = calendario.getTime();
            } catch (IllegalArgumentException ex) {
                mensaje(FacesMessage.SEVERITY_WARN, "La fecha seleccionada no existe.");
                return null;
            }
            calendario.setLenient(true);
            if (dia == null) {
                calendario.add(Calendar.MONTH, 1);
            } else {
                calendario.add(Calendar.DAY_OF_MONTH, 1);
            }
            calendario.add(Calendar.SECOND, -1);
            fin = calendario.getTime();
            if (inicio.after(limite)) {
                mensaje(FacesMessage.SEVERITY_WARN, "Solo se pueden solicitar CFDI hasta el día de hoy.");
                return null;
            }
            // Mes en curso: se corta al final del dia de hoy.
            if (fin.after(limite)) {
                fin = limite;
            }
        } else {
            if (fechaInicial == null || fechaFinal == null) {
                mensaje(FacesMessage.SEVERITY_WARN, "Captura fecha inicial y fecha final.");
                return null;
            }
            // Dias completos: la inicial desde 00:00:00 y la final hasta 23:59:59.
            inicio = inicioDelDia(fechaInicial);
            fin = finDelDia(fechaFinal);
            if (fin.after(limite)) {
                mensaje(FacesMessage.SEVERITY_WARN, "La fecha final solo puede ser hasta el día de hoy.");
                return null;
            }
        }
        // El SAT (v1.5) exige inicio estrictamente menor al fin y trabaja a segundos.
        if (fin.getTime() - inicio.getTime() < 1000) {
            mensaje(FacesMessage.SEVERITY_WARN, "La fecha inicial debe ser anterior a la fecha final.");
            return null;
        }
        if (inicio.before(fechaMinimaConsultable())) {
            mensaje(FacesMessage.SEVERITY_WARN, "Solo se pueden consultar CFDI desde el "
                    + new SimpleDateFormat("dd/MM/yyyy").format(fechaMinimaConsultable())
                    + " (ejercicio actual y " + EJERCICIOS_ANTERIORES + " anteriores).");
            return null;
        }
        return new Date[] { inicio, fin };
    }

    /** 1 de enero del ejercicio actual menos 5. */
    private static Date fechaMinimaConsultable() {
        Calendar calendario = Calendar.getInstance();
        int anioMinimo = calendario.get(Calendar.YEAR) - EJERCICIOS_ANTERIORES;
        calendario.clear();
        calendario.set(anioMinimo, Calendar.JANUARY, 1, 0, 0, 0);
        return calendario.getTime();
    }

    /**
     * Ultimo segundo del dia de hoy. El SAT rechaza una FechaFinal posterior
     * al dia en curso con "301 ... Fecha final invalida" (verificado en vivo).
     */
    private static Date inicioDelDia(Date fecha) {
        return Date.from(fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private static Date finDelDia(Date fecha) {
        return Date.from(fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                .atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());
    }

    private static Date finDeHoy() {
        return Date.from(LocalDate.now().atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());
    }

    private RnGcUsuariosTbl obtenerUsuario() {
        return usuariosFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
    }

    private static void mensaje(FacesMessage.Severity severidad, String texto) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidad, texto, null));
    }

    // ------------------------------------------------------------------
    // Opciones de la pantalla
    // ------------------------------------------------------------------

    public boolean isRecibidos() {
        return DescargaMasivaSatCliente.TIPO_RECIBIDOS.equals(tipo);
    }

    /** Ejercicios consultables: actual y 5 anteriores. */
    public List<Integer> getAniosDisponibles() {
        int actual = Calendar.getInstance().get(Calendar.YEAR);
        List<Integer> anios = new ArrayList<>();
        for (int a = actual; a >= actual - EJERCICIOS_ANTERIORES; a--) {
            anios.add(a);
        }
        return anios;
    }

    /** Meses del año seleccionado hasta el mes en curso. */
    public List<Integer> getMesesDisponibles() {
        List<Integer> meses = new ArrayList<>();
        LocalDate hoy = LocalDate.now();
        int ultimo = anio != null && anio == hoy.getYear() ? hoy.getMonthValue() : 12;
        for (int m = 1; m <= ultimo; m++) {
            meses.add(m);
        }
        return meses;
    }

    /** Dias reales del mes seleccionado hasta hoy (vacio sin año/mes). */
    public List<Integer> getDiasDisponibles() {
        List<Integer> dias = new ArrayList<>();
        if (anio == null || mes == null) {
            return dias;
        }
        LocalDate hoy = LocalDate.now();
        YearMonth periodo = YearMonth.of(anio, mes);
        int ultimo = periodo.equals(YearMonth.from(hoy)) ? hoy.getDayOfMonth() : periodo.lengthOfMonth();
        for (int d = 1; d <= ultimo; d++) {
            dias.add(d);
        }
        return dias;
    }

    /** Al cambiar año o mes se descarta el dia si ya no existe en ese mes. */
    public void alCambiarPeriodo() {
        if (mes != null && !getMesesDisponibles().contains(mes)) {
            mes = null;
        }
        if (dia != null && !getDiasDisponibles().contains(dia)) {
            dia = null;
        }
    }

    public Date getFechaMinima() {
        return fechaMinimaConsultable();
    }

    public Date getFechaMaxima() {
        return finDeHoy();
    }

    /**
     * f:convertDateTime usa UTC por defecto; se muestra en la zona del servidor,
     * igual que p:calendar.
     */
    public TimeZone getZonaHoraria() {
        return TimeZone.getDefault();
    }

    /** Lo invoca el aviso global (templateRN) cuando una solicitud cambia de estado. */
    public void recargarSolicitudes() {
        solicitudes = solicitudesFacade.obtenerSolicitudesDeUsuario(obtenerUsuario());
    }

    public List<RnGcSolicitudesDescargaSatTbl> getSolicitudes() {
        if (solicitudes == null) {
            solicitudes = solicitudesFacade.obtenerSolicitudesDeUsuario(obtenerUsuario());
        }
        return solicitudes;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getEstadoComprobante() {
        return estadoComprobante;
    }

    public void setEstadoComprobante(String estadoComprobante) {
        this.estadoComprobante = estadoComprobante;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }
}
