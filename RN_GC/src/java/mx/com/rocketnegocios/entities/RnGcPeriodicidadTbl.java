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
 * @author mmiva
 */
@Entity
@Table(name = "rn_gc_periodicidad_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcPeriodicidadTbl.findAll", query = "SELECT r FROM RnGcPeriodicidadTbl r")
    , @NamedQuery(name = "RnGcPeriodicidadTbl.findById", query = "SELECT r FROM RnGcPeriodicidadTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcPeriodicidadTbl.findByCPeriodicidad", query = "SELECT r FROM RnGcPeriodicidadTbl r WHERE r.cPeriodicidad = :cPeriodicidad")
    , @NamedQuery(name = "RnGcPeriodicidadTbl.findByDescripcion", query = "SELECT r FROM RnGcPeriodicidadTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcPeriodicidadTbl.findByFechaInicioVigencia", query = "SELECT r FROM RnGcPeriodicidadTbl r WHERE r.fechaInicioVigencia = :fechaInicioVigencia")
    , @NamedQuery(name = "RnGcPeriodicidadTbl.findByFechaFinVigencia", query = "SELECT r FROM RnGcPeriodicidadTbl r WHERE r.fechaFinVigencia = :fechaFinVigencia")
    , @NamedQuery(name = "RnGcPeriodicidadTbl.findByVersionCFDI", query = "SELECT r FROM RnGcPeriodicidadTbl r WHERE r.versionCFDI = :versionCFDI")
    , @NamedQuery(name = "RnGcPeriodicidadTbl.findByVersioCatalogo", query = "SELECT r FROM RnGcPeriodicidadTbl r WHERE r.versioCatalogo = :versioCatalogo")
    , @NamedQuery(name = "RnGcPeriodicidadTbl.findByCreadoPor", query = "SELECT r FROM RnGcPeriodicidadTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcPeriodicidadTbl.findByFechaCreacion", query = "SELECT r FROM RnGcPeriodicidadTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcPeriodicidadTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcPeriodicidadTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcPeriodicidadTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcPeriodicidadTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcPeriodicidadTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "c_Periodicidad")
    private String cPeriodicidad;
    @Size(max = 45)
    @Column(name = "Descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaInicioVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioVigencia;
    @Column(name = "fechaFinVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaFinVigencia;
    @Size(max = 4)
    @Column(name = "versionCFDI")
    private String versionCFDI;
    @Size(max = 4)
    @Column(name = "versioCatalogo")
    private String versioCatalogo;
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

    public RnGcPeriodicidadTbl() {
    }

    public RnGcPeriodicidadTbl(Integer id) {
        this.id = id;
    }

    public RnGcPeriodicidadTbl(Integer id, String cPeriodicidad, Date fechaInicioVigencia, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.cPeriodicidad = cPeriodicidad;
        this.fechaInicioVigencia = fechaInicioVigencia;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCPeriodicidad() {
        return cPeriodicidad;
    }

    public void setCPeriodicidad(String cPeriodicidad) {
        this.cPeriodicidad = cPeriodicidad;
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

    public String getVersionCFDI() {
        return versionCFDI;
    }

    public void setVersionCFDI(String versionCFDI) {
        this.versionCFDI = versionCFDI;
    }

    public String getVersioCatalogo() {
        return versioCatalogo;
    }

    public void setVersioCatalogo(String versioCatalogo) {
        this.versioCatalogo = versioCatalogo;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RnGcPeriodicidadTbl)) {
            return false;
        }
        RnGcPeriodicidadTbl other = (RnGcPeriodicidadTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcPeriodicidadTbl[ id=" + id + " ]";
    }
    
}
