package mx.com.rocketnegocios.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * CTR-12: bitácora de la actualización automática de listas negras. Un renglón por día del
 * DOF revisado, uno por CSV del SAT (con su ETag) y uno por la pasada de CFDI.
 */
@Entity
@Table(name = "rn_gc_listaNegraRevision_tbl")
@NamedQueries({
    @NamedQuery(name = "RnGcListaNegraRevisionTbl.findByFuenteDesde", query = "SELECT r FROM RnGcListaNegraRevisionTbl r WHERE r.fuente = :fuente AND r.fecha >= :desde"),
    @NamedQuery(name = "RnGcListaNegraRevisionTbl.findByFuenteSinFecha", query = "SELECT r FROM RnGcListaNegraRevisionTbl r WHERE r.fuente = :fuente AND r.fecha IS NULL ORDER BY r.id"),
    @NamedQuery(name = "RnGcListaNegraRevisionTbl.findByFuenteFecha", query = "SELECT r FROM RnGcListaNegraRevisionTbl r WHERE r.fuente = :fuente AND r.fecha = :fecha ORDER BY r.id"),
    @NamedQuery(name = "RnGcListaNegraRevisionTbl.findRecientes", query = "SELECT r FROM RnGcListaNegraRevisionTbl r ORDER BY r.fechaRevision DESC, r.id DESC")})
public class RnGcListaNegraRevisionTbl implements Serializable {

    public static final String FUENTE_DOF = "DOF";
    public static final String FUENTE_CFDI = "CFDI";
    public static final String OK = "OK";
    public static final String ERROR = "ERROR";

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fuente")
    private String fuente;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "estado")
    private String estado;
    @Column(name = "intentos")
    private int intentos;
    @Column(name = "marca")
    private String marca;
    @Column(name = "mensaje")
    private String mensaje;
    @Column(name = "fechaRevision")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRevision;

    /** Fuente de un CSV del SAT, p. ej. "CSV 69-B". */
    public static String fuenteCsv(String etiquetaLista) {
        return "CSV " + etiquetaLista;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(Date fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RnGcListaNegraRevisionTbl)) {
            return false;
        }
        RnGcListaNegraRevisionTbl other = (RnGcListaNegraRevisionTbl) object;
        return id != null && id.equals(other.id);
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcListaNegraRevisionTbl[ id=" + id + " ]";
    }
}
