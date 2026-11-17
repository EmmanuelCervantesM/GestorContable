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
 * CTR-12: caché de notas del DOF (oficio + fecha de publicación -> edición y código
 * de nota), para armar la liga directa de cada etapa de la lista negra.
 */
@Entity
@Table(name = "rn_gc_listaNegraDof_tbl")
@NamedQueries({
    @NamedQuery(name = "RnGcListaNegraDofTbl.findByOficioFecha", query = "SELECT r FROM RnGcListaNegraDofTbl r WHERE r.oficio = :oficio AND r.fechaPublicacion = :fechaPublicacion"),
    @NamedQuery(name = "RnGcListaNegraDofTbl.findByFecha", query = "SELECT r FROM RnGcListaNegraDofTbl r WHERE r.fechaPublicacion = :fechaPublicacion")})
public class RnGcListaNegraDofTbl implements Serializable {

    private static final long serialVersionUID = 1L;

    /** El oficio se encontró en el índice del DOF de esa fecha. */
    public static final String RESUELTO = "S";
    /** Se leyeron las dos ediciones de esa fecha y el oficio no está. */
    public static final String NO_ENCONTRADO = "N";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "oficio")
    private String oficio;
    @Column(name = "fechaPublicacion")
    @Temporal(TemporalType.DATE)
    private Date fechaPublicacion;
    @Column(name = "edicion")
    private String edicion;
    @Column(name = "codigoNota")
    private String codigoNota;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "resuelto")
    private String resuelto;
    @Column(name = "fechaConsulta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaConsulta;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOficio() {
        return oficio;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public String getCodigoNota() {
        return codigoNota;
    }

    public void setCodigoNota(String codigoNota) {
        this.codigoNota = codigoNota;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResuelto() {
        return resuelto;
    }

    public void setResuelto(String resuelto) {
        this.resuelto = resuelto;
    }

    public Date getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(Date fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RnGcListaNegraDofTbl)) {
            return false;
        }
        RnGcListaNegraDofTbl other = (RnGcListaNegraDofTbl) object;
        return id != null && id.equals(other.id);
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcListaNegraDofTbl[ id=" + id + " ]";
    }
}
