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
 *
 * @author mmiva
 */
@Entity
@Table(name = "rn_gc_localidades_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcLocalidadesTbl.findAll", query = "SELECT r FROM RnGcLocalidadesTbl r")
    , @NamedQuery(name = "RnGcLocalidadesTbl.findById", query = "SELECT r FROM RnGcLocalidadesTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcLocalidadesTbl.findByCreadoPor", query = "SELECT r FROM RnGcLocalidadesTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcLocalidadesTbl.findByFechaCreacion", query = "SELECT r FROM RnGcLocalidadesTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcLocalidadesTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcLocalidadesTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcLocalidadesTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcLocalidadesTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcLocalidadesTbl.findByCLocalidad", query = "SELECT r FROM RnGcLocalidadesTbl r WHERE r.cLocalidad = :cLocalidad")
    , @NamedQuery(name = "RnGcLocalidadesTbl.findByCEstado", query = "SELECT r FROM RnGcLocalidadesTbl r WHERE r.cEstado = :cEstado")
    , @NamedQuery(name = "RnGcLocalidadesTbl.findByDescripcion", query = "SELECT r FROM RnGcLocalidadesTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcLocalidadesTbl.findByFechaInicioVigencia", query = "SELECT r FROM RnGcLocalidadesTbl r WHERE r.fechaInicioVigencia = :fechaInicioVigencia")
    , @NamedQuery(name = "RnGcLocalidadesTbl.findByFechaFinVigencia", query = "SELECT r FROM RnGcLocalidadesTbl r WHERE r.fechaFinVigencia = :fechaFinVigencia")
    , @NamedQuery(name = "RnGcLocalidadesTbl.findByVersionCFDI", query = "SELECT r FROM RnGcLocalidadesTbl r WHERE r.versionCFDI = :versionCFDI")
    , @NamedQuery(name = "RnGcLocalidadesTbl.findByVersionCatalogo", query = "SELECT r FROM RnGcLocalidadesTbl r WHERE r.versionCatalogo = :versionCatalogo")})
public class RnGcLocalidadesTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
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
    @Size(min = 1, max = 3)
    @Column(name = "c_Localidad")
    private String cLocalidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "c_Estado")
    private String cEstado;
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
    @Column(name = "versionCatalogo")
    private String versionCatalogo;
    @JoinColumn(name = "estadoId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private RnGcEstadosTbl estadoId;

    public RnGcLocalidadesTbl() {
    }

    public RnGcLocalidadesTbl(Integer id) {
        this.id = id;
    }

    public RnGcLocalidadesTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion, String cLocalidad, String cEstado, Date fechaInicioVigencia) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
        this.cLocalidad = cLocalidad;
        this.cEstado = cEstado;
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

    public String getCLocalidad() {
        return cLocalidad;
    }

    public void setCLocalidad(String cLocalidad) {
        this.cLocalidad = cLocalidad;
    }

    public String getCEstado() {
        return cEstado;
    }

    public void setCEstado(String cEstado) {
        this.cEstado = cEstado;
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

    public String getVersionCatalogo() {
        return versionCatalogo;
    }

    public void setVersionCatalogo(String versionCatalogo) {
        this.versionCatalogo = versionCatalogo;
    }

    public RnGcEstadosTbl getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(RnGcEstadosTbl estadoId) {
        this.estadoId = estadoId;
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
        if (!(object instanceof RnGcLocalidadesTbl)) {
            return false;
        }
        RnGcLocalidadesTbl other = (RnGcLocalidadesTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcLocalidadesTbl[ id=" + id + " ]";
    }
    
}
