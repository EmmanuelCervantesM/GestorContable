/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LenovoZ40
 */
@Entity
@Table(name = "rn_gc_nom_percepciones_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcNomPercepcionesTbl.findAll", query = "SELECT r FROM RnGcNomPercepcionesTbl r")
    , @NamedQuery(name = "RnGcNomPercepcionesTbl.findById", query = "SELECT r FROM RnGcNomPercepcionesTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcNomPercepcionesTbl.findByCreadoPor", query = "SELECT r FROM RnGcNomPercepcionesTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcNomPercepcionesTbl.findByFechaCreacion", query = "SELECT r FROM RnGcNomPercepcionesTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcNomPercepcionesTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcNomPercepcionesTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcNomPercepcionesTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcNomPercepcionesTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcNomPercepcionesTbl.findByVersion", query = "SELECT r FROM RnGcNomPercepcionesTbl r WHERE r.version = :version")
    , @NamedQuery(name = "RnGcNomPercepcionesTbl.findByRevision", query = "SELECT r FROM RnGcNomPercepcionesTbl r WHERE r.revision = :revision")
    , @NamedQuery(name = "RnGcNomPercepcionesTbl.findByCveTipoPercepcion", query = "SELECT r FROM RnGcNomPercepcionesTbl r WHERE r.cveTipoPercepcion = :cveTipoPercepcion")
    , @NamedQuery(name = "RnGcNomPercepcionesTbl.findByDescripcion", query = "SELECT r FROM RnGcNomPercepcionesTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcNomPercepcionesTbl.findByFechaInicioVigencia", query = "SELECT r FROM RnGcNomPercepcionesTbl r WHERE r.fechaInicioVigencia = :fechaInicioVigencia")
    , @NamedQuery(name = "RnGcNomPercepcionesTbl.findByFechaFinVigencia", query = "SELECT r FROM RnGcNomPercepcionesTbl r WHERE r.fechaFinVigencia = :fechaFinVigencia")})
public class RnGcNomPercepcionesTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "version")
    private String version;
    @Basic(optional = false)
    @NotNull
    @Column(name = "revision")
    private int revision;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "cve_TipoPercepcion")
    private String cveTipoPercepcion;
    @Size(max = 240)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaInicioVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioVigencia;
    @Column(name = "fechaFinVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaFinVigencia;

    public RnGcNomPercepcionesTbl() {
    }

    public RnGcNomPercepcionesTbl(Integer id) {
        this.id = id;
    }

    public RnGcNomPercepcionesTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion, String version, int revision, String cveTipoPercepcion, Date fechaInicioVigencia) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
        this.version = version;
        this.revision = revision;
        this.cveTipoPercepcion = cveTipoPercepcion;
        this.fechaInicioVigencia = fechaInicioVigencia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public String getCveTipoPercepcion() {
        return cveTipoPercepcion;
    }

    public void setCveTipoPercepcion(String cveTipoPercepcion) {
        this.cveTipoPercepcion = cveTipoPercepcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicioVigencia() {
        return fechaInicioVigencia;
    }

    public void setFechaInicioVigencia(Date fechaInicioVigencia) {
        this.fechaInicioVigencia = fechaInicioVigencia;
    }

    public Date getFechaFinVigencia() {
        return fechaFinVigencia;
    }

    public void setFechaFinVigencia(Date fechaFinVigencia) {
        this.fechaFinVigencia = fechaFinVigencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RnGcNomPercepcionesTbl)) {
            return false;
        }
        RnGcNomPercepcionesTbl other = (RnGcNomPercepcionesTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return cveTipoPercepcion.toString();
    }
    
    /*@Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcNomPercepcionesTbl[ id=" + id + " ]";
    }//*/
    
}
