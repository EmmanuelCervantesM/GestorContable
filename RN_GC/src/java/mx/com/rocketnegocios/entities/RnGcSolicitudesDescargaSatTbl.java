package mx.com.rocketnegocios.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * CTR-06: solicitud de Descarga Masiva de CFDI presentada al SAT. Solo guarda
 * el seguimiento (IdSolicitud, estado, paquetes); los ZIP se piden al SAT al
 * momento de descargar y no se almacenan.
 */
@Entity
@Table(name = "rn_gc_solicitudes_descarga_sat_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcSolicitudesDescargaSatTbl.findAll", query = "SELECT r FROM RnGcSolicitudesDescargaSatTbl r")
    , @NamedQuery(name = "RnGcSolicitudesDescargaSatTbl.findByUsuarioId", query = "SELECT r FROM RnGcSolicitudesDescargaSatTbl r WHERE r.usuariosId = :usuariosId AND (r.estadoSolicitud IS NULL OR r.estadoSolicitud <> 6) ORDER BY r.id DESC")
    , @NamedQuery(name = "RnGcSolicitudesDescargaSatTbl.findPendientesByUsuarioId", query = "SELECT r FROM RnGcSolicitudesDescargaSatTbl r WHERE r.usuariosId = :usuariosId AND r.estadoSolicitud IN (1, 2) ORDER BY r.id")
    , @NamedQuery(name = "RnGcSolicitudesDescargaSatTbl.findAvisosByUsuarioId", query = "SELECT r FROM RnGcSolicitudesDescargaSatTbl r WHERE r.usuariosId = :usuariosId AND r.estadoSolicitud IN (3, 4, 5, 6) AND r.avisoVisto = false ORDER BY r.id")
    , @NamedQuery(name = "RnGcSolicitudesDescargaSatTbl.marcarAvisosVistos", query = "UPDATE RnGcSolicitudesDescargaSatTbl r SET r.avisoVisto = true WHERE r.usuariosId = :usuariosId AND r.estadoSolicitud IN (3, 4, 5, 6) AND r.avisoVisto = false")
    , @NamedQuery(name = "RnGcSolicitudesDescargaSatTbl.countPendientesOAvisosByUsuarioId", query = "SELECT COUNT(r) FROM RnGcSolicitudesDescargaSatTbl r WHERE r.usuariosId.id = :usuarioId AND (r.estadoSolicitud IN (1, 2) OR (r.estadoSolicitud IN (3, 4, 5, 6) AND r.avisoVisto = false))")
    , @NamedQuery(name = "RnGcSolicitudesDescargaSatTbl.countPendientesByUsuarioId", query = "SELECT COUNT(r) FROM RnGcSolicitudesDescargaSatTbl r WHERE r.usuariosId.id = :usuarioId AND r.estadoSolicitud IN (1, 2)")
    , @NamedQuery(name = "RnGcSolicitudesDescargaSatTbl.marcarAvisosVistosPorId", query = "UPDATE RnGcSolicitudesDescargaSatTbl r SET r.avisoVisto = true WHERE r.id IN :ids")
    , @NamedQuery(name = "RnGcSolicitudesDescargaSatTbl.marcarVencidas", query = "UPDATE RnGcSolicitudesDescargaSatTbl r SET r.estadoSolicitud = 6, r.avisoVisto = true, r.ultimaFechaActualizacion = :ahora WHERE r.usuariosId = :usuariosId AND r.estadoSolicitud = 3 AND r.fechaSolicitud < :limite")})
public class RnGcSolicitudesDescargaSatTbl implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int ESTADO_ACEPTADA = 1;
    public static final int ESTADO_EN_PROCESO = 2;
    public static final int ESTADO_TERMINADA = 3;
    public static final int ESTADO_ERROR = 4;
    public static final int ESTADO_RECHAZADA = 5;
    public static final int ESTADO_VENCIDA = 6;

    /**
     * El SAT conserva los paquetes ~72 h. Se cuentan desde la fecha de
     * solicitud (no desde que termino) para dejar margen: pasado este plazo
     * una solicitud Terminada pasa a Vencida y deja de mostrarse.
     */
    public static final int HORAS_VIGENCIA_PAQUETES = 72;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @JoinColumn(name = "usuarios_Id", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private RnGcUsuariosTbl usuariosId;
    /** EMITIDOS o RECIBIDOS. */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaInicial")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaFinal")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinal;
    /** Todos, Vigente o Cancelado (valor enviado al SAT). */
    @Size(max = 10)
    @Column(name = "estadoComprobante")
    private String estadoComprobante;
    @Size(max = 50)
    @Column(name = "idSolicitud")
    private String idSolicitud;
    /** EstadoSolicitud del SAT: 1 Aceptada ... 6 Vencida. */
    @Column(name = "estadoSolicitud")
    private Integer estadoSolicitud;
    @Size(max = 10)
    @Column(name = "codEstatus")
    private String codEstatus;
    @Column(name = "codigoEstadoSolicitud")
    private Integer codigoEstadoSolicitud;
    @Size(max = 500)
    @Column(name = "mensaje")
    private String mensaje;
    @Column(name = "numeroCfdis")
    private Integer numeroCfdis;
    /** Ids de paquete separados por coma. */
    @Lob
    @Column(name = "idsPaquetes")
    private String idsPaquetes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaSolicitud")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSolicitud;
    @Column(name = "fechaUltimaVerificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUltimaVerificacion;
    @Column(name = "fechaDescarga")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDescarga;
    /** El usuario ya vio el aviso de estado final (se marca al abrir la pantalla). */
    @Basic(optional = false)
    @Column(name = "avisoVisto")
    private boolean avisoVisto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creadoPor")
    private int creadoPor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaCreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ultimaActualizacionPor")
    private int ultimaActualizacionPor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ultimaFechaActualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaFechaActualizacion;

    public RnGcSolicitudesDescargaSatTbl() {
    }

    public RnGcSolicitudesDescargaSatTbl(Integer id) {
        this.id = id;
    }

    /** Paquetes como lista (vacia si aun no hay). */
    public List<String> getListaPaquetes() {
        if (idsPaquetes == null || idsPaquetes.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(idsPaquetes.trim().split("\\s*,\\s*")));
    }

    public boolean isPendiente() {
        return estadoSolicitud != null
                && (estadoSolicitud == ESTADO_ACEPTADA || estadoSolicitud == ESTADO_EN_PROCESO);
    }

    /**
     * Causa de la respuesta del SAT segun su CodigoEstadoSolicitud. Al rechazar,
     * el SAT suele devolver "Solicitud Aceptada" en Mensaje y la causa real solo
     * viene en este codigo. Null si el codigo no indica un problema.
     */
    public static String descripcionCodigoEstado(Integer codigo) {
        if (codigo == null) {
            return null;
        }
        switch (codigo) {
            case 5002:
                return "se agotaron las solicitudes que el SAT permite para ese mismo periodo y filtros";
            case 5003:
                return "el periodo excede el máximo de CFDI que el SAT entrega en una solicitud; divídelo en periodos más cortos";
            case 5004:
                return "no se encontraron CFDI en ese periodo";
            case 5005:
                return "ya existe una solicitud igual en el SAT";
            case 5011:
                return "se alcanzó el límite diario de descargas del SAT";
            case 404:
                return "error no controlado del SAT; intenta de nuevo más tarde";
            default:
                return null;
        }
    }

    /**
     * Texto para la columna "Mensaje SAT". El SAT devuelve "Solicitud Aceptada"
     * en casi todas sus respuestas, asi que se muestra solo lo que aporta: la
     * causa por codigo si la hay, un texto claro mientras esta en proceso, nada
     * si ya termino, y el mensaje original en los demas casos (p. ej. errores).
     */
    public String getMensajeSat() {
        String causa = descripcionCodigoEstado(codigoEstadoSolicitud);
        if (causa != null) {
            return "Código " + codigoEstadoSolicitud + ": " + causa;
        }
        if (isPendiente()) {
            return "El SAT está preparando la descarga.";
        }
        if (estadoSolicitud != null && estadoSolicitud == ESTADO_TERMINADA) {
            return null;
        }
        return mensaje;
    }

    public String getEstadoSolicitudTexto() {
        if (estadoSolicitud == null) {
            return "Sin estado";
        }
        switch (estadoSolicitud) {
            case ESTADO_ACEPTADA:
                return "Aceptada";
            case ESTADO_EN_PROCESO:
                return "En proceso";
            case ESTADO_TERMINADA:
                return "Terminada";
            case ESTADO_ERROR:
                return "Error";
            case ESTADO_RECHAZADA:
                return "Rechazada";
            case ESTADO_VENCIDA:
                return "Vencida";
            default:
                return "Desconocido (" + estadoSolicitud + ")";
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RnGcUsuariosTbl getUsuariosId() {
        return usuariosId;
    }

    public void setUsuariosId(RnGcUsuariosTbl usuariosId) {
        this.usuariosId = usuariosId;
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

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Integer getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(Integer estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public String getCodEstatus() {
        return codEstatus;
    }

    public void setCodEstatus(String codEstatus) {
        this.codEstatus = codEstatus;
    }

    public Integer getCodigoEstadoSolicitud() {
        return codigoEstadoSolicitud;
    }

    public void setCodigoEstadoSolicitud(Integer codigoEstadoSolicitud) {
        this.codigoEstadoSolicitud = codigoEstadoSolicitud;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        // La columna es de 500; los mensajes del SAT/excepciones pueden ser mas largos.
        this.mensaje = mensaje != null && mensaje.length() > 500 ? mensaje.substring(0, 500) : mensaje;
    }

    public Integer getNumeroCfdis() {
        return numeroCfdis;
    }

    public void setNumeroCfdis(Integer numeroCfdis) {
        this.numeroCfdis = numeroCfdis;
    }

    public String getIdsPaquetes() {
        return idsPaquetes;
    }

    public void setIdsPaquetes(String idsPaquetes) {
        this.idsPaquetes = idsPaquetes;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Date getFechaUltimaVerificacion() {
        return fechaUltimaVerificacion;
    }

    public void setFechaUltimaVerificacion(Date fechaUltimaVerificacion) {
        this.fechaUltimaVerificacion = fechaUltimaVerificacion;
    }

    public Date getFechaDescarga() {
        return fechaDescarga;
    }

    public void setFechaDescarga(Date fechaDescarga) {
        this.fechaDescarga = fechaDescarga;
    }

    public boolean isAvisoVisto() {
        return avisoVisto;
    }

    public void setAvisoVisto(boolean avisoVisto) {
        this.avisoVisto = avisoVisto;
    }

    public int getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(int creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getUltimaActualizacionPor() {
        return ultimaActualizacionPor;
    }

    public void setUltimaActualizacionPor(int ultimaActualizacionPor) {
        this.ultimaActualizacionPor = ultimaActualizacionPor;
    }

    public Date getUltimaFechaActualizacion() {
        return ultimaFechaActualizacion;
    }

    public void setUltimaFechaActualizacion(Date ultimaFechaActualizacion) {
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RnGcSolicitudesDescargaSatTbl)) {
            return false;
        }
        RnGcSolicitudesDescargaSatTbl other = (RnGcSolicitudesDescargaSatTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcSolicitudesDescargaSatTbl[ id=" + id + " ]";
    }
}
