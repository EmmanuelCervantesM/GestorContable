package mx.com.rocketnegocios.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
 * Trabajo en segundo plano para generar la Opinión de cumplimiento o la
 * Constancia de situación fiscal desde el portal del SAT (Selenium headless).
 * El PDF NO se guarda en BD: queda en disco ({@link #rutaArchivo}) solo hasta
 * {@link #fechaExpira}; despues se borra y el trabajo pasa a EXPIRADO.
 *
 * Los trabajos solicitados por el mismo usuario dentro de la ventana de
 * agrupacion comparten {@link #lote} y se notifican con un solo aviso.
 */
@Entity
@Table(name = "rn_gc_documentos_sat_job_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcDocumentosSatJobTbl.findAll", query = "SELECT r FROM RnGcDocumentosSatJobTbl r")
    , @NamedQuery(name = "RnGcDocumentosSatJobTbl.findActivosByUsuarioId", query = "SELECT r FROM RnGcDocumentosSatJobTbl r WHERE r.usuariosId.id = :usuarioId AND r.estado IN ('PENDIENTE', 'EN_PROCESO') ORDER BY r.id")
    , @NamedQuery(name = "RnGcDocumentosSatJobTbl.findActivosByUsuarioIdTipo", query = "SELECT r FROM RnGcDocumentosSatJobTbl r WHERE r.usuariosId.id = :usuarioId AND r.tipo = :tipo AND r.estado IN ('PENDIENTE', 'EN_PROCESO') ORDER BY r.id DESC")
    , @NamedQuery(name = "RnGcDocumentosSatJobTbl.findUltimosByUsuarioIdTipo", query = "SELECT r FROM RnGcDocumentosSatJobTbl r WHERE r.usuariosId.id = :usuarioId AND r.tipo = :tipo ORDER BY r.id DESC")
    , @NamedQuery(name = "RnGcDocumentosSatJobTbl.findAvisosByUsuarioId", query = "SELECT r FROM RnGcDocumentosSatJobTbl r WHERE r.usuariosId.id = :usuarioId AND r.estado IN ('LISTO', 'ERROR') AND r.avisoVisto = false ORDER BY r.id")
    , @NamedQuery(name = "RnGcDocumentosSatJobTbl.countActivosOAvisosByUsuarioId", query = "SELECT COUNT(r) FROM RnGcDocumentosSatJobTbl r WHERE r.usuariosId.id = :usuarioId AND (r.estado IN ('PENDIENTE', 'EN_PROCESO') OR (r.estado IN ('LISTO', 'ERROR') AND r.avisoVisto = false))")
    , @NamedQuery(name = "RnGcDocumentosSatJobTbl.findLotesPendientesByUsuarioIdDesde", query = "SELECT r FROM RnGcDocumentosSatJobTbl r WHERE r.usuariosId.id = :usuarioId AND r.estado = 'PENDIENTE' AND r.fechaSolicitud > :desde ORDER BY r.id DESC")
    , @NamedQuery(name = "RnGcDocumentosSatJobTbl.findPendientesByLote", query = "SELECT r FROM RnGcDocumentosSatJobTbl r WHERE r.lote = :lote AND r.estado = 'PENDIENTE' ORDER BY r.id")
    , @NamedQuery(name = "RnGcDocumentosSatJobTbl.findByLote", query = "SELECT r FROM RnGcDocumentosSatJobTbl r WHERE r.lote = :lote ORDER BY r.id")
    , @NamedQuery(name = "RnGcDocumentosSatJobTbl.findListosExpirados", query = "SELECT r FROM RnGcDocumentosSatJobTbl r WHERE r.estado = 'LISTO' AND r.fechaExpira < :ahora ORDER BY r.id")
    , @NamedQuery(name = "RnGcDocumentosSatJobTbl.marcarInterrumpidos", query = "UPDATE RnGcDocumentosSatJobTbl r SET r.estado = 'ERROR', r.mensaje = :mensaje, r.fechaFin = :ahora, r.ultimaFechaActualizacion = :ahora WHERE r.estado IN ('PENDIENTE', 'EN_PROCESO')")
    , @NamedQuery(name = "RnGcDocumentosSatJobTbl.marcarAvisosVistos", query = "UPDATE RnGcDocumentosSatJobTbl r SET r.avisoVisto = true WHERE r.id IN :ids")})
public class RnGcDocumentosSatJobTbl implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String TIPO_OPINION = "OPINION";
    public static final String TIPO_CONSTANCIA = "CONSTANCIA";

    public static final String ESTADO_PENDIENTE = "PENDIENTE";
    public static final String ESTADO_EN_PROCESO = "EN_PROCESO";
    public static final String ESTADO_LISTO = "LISTO";
    public static final String ESTADO_ERROR = "ERROR";
    public static final String ESTADO_EXPIRADO = "EXPIRADO";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @JoinColumn(name = "usuarios_Id", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private RnGcUsuariosTbl usuariosId;
    /** OPINION o CONSTANCIA. */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "tipo")
    private String tipo;
    /** UUID que agrupa los trabajos solicitados dentro de la misma ventana. */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "lote")
    private String lote;
    /** PENDIENTE, EN_PROCESO, LISTO, ERROR o EXPIRADO. */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "estado")
    private String estado;
    @Size(max = 500)
    @Column(name = "mensaje")
    private String mensaje;
    @Size(max = 500)
    @Column(name = "rutaArchivo")
    private String rutaArchivo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaSolicitud")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSolicitud;
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "fechaFin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Column(name = "fechaExpira")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaExpira;
    /** El usuario ya recibio el aviso (growl) del resultado. */
    @Basic(optional = false)
    @Column(name = "avisoVisto")
    private boolean avisoVisto;
    /** Reservado para el aviso por correo (pendiente, ver NotificadorCorreoDocumentosSat). */
    @Basic(optional = false)
    @Column(name = "correoEnviado")
    private boolean correoEnviado;
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

    public RnGcDocumentosSatJobTbl() {
    }

    public RnGcDocumentosSatJobTbl(Integer id) {
        this.id = id;
    }

    public boolean isActivo() {
        return ESTADO_PENDIENTE.equals(estado) || ESTADO_EN_PROCESO.equals(estado);
    }

    public boolean isListo() {
        return ESTADO_LISTO.equals(estado);
    }

    /** Nombre del documento para mensajes al usuario. */
    public String getNombreDocumento() {
        return nombreDocumento(tipo);
    }

    public static String nombreDocumento(String tipo) {
        return TIPO_OPINION.equals(tipo) ? "Opinión de cumplimiento" : "Constancia de situación fiscal";
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

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        // La columna es de 500; los mensajes de excepciones pueden ser mas largos.
        this.mensaje = mensaje != null && mensaje.length() > 500 ? mensaje.substring(0, 500) : mensaje;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaExpira() {
        return fechaExpira;
    }

    public void setFechaExpira(Date fechaExpira) {
        this.fechaExpira = fechaExpira;
    }

    public boolean isAvisoVisto() {
        return avisoVisto;
    }

    public void setAvisoVisto(boolean avisoVisto) {
        this.avisoVisto = avisoVisto;
    }

    public boolean isCorreoEnviado() {
        return correoEnviado;
    }

    public void setCorreoEnviado(boolean correoEnviado) {
        this.correoEnviado = correoEnviado;
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
        if (!(object instanceof RnGcDocumentosSatJobTbl)) {
            return false;
        }
        RnGcDocumentosSatJobTbl other = (RnGcDocumentosSatJobTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcDocumentosSatJobTbl[ id=" + id + " ]";
    }
}
