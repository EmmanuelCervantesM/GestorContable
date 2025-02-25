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
 * @author maxoc
 */
@Entity
@Table(name = "rn_gc_cp_paissat_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcCpPaissatTbl.findAll", query = "SELECT r FROM RnGcCpPaissatTbl r")
    , @NamedQuery(name = "RnGcCpPaissatTbl.findById", query = "SELECT r FROM RnGcCpPaissatTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcCpPaissatTbl.findByDescripcion", query = "SELECT r FROM RnGcCpPaissatTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcCpPaissatTbl.findByClavePais", query = "SELECT r FROM RnGcCpPaissatTbl r WHERE r.clavePais = :clavePais")
    , @NamedQuery(name = "RnGcCpPaissatTbl.findByClaveEstado", query = "SELECT r FROM RnGcCpPaissatTbl r WHERE r.claveEstado = :claveEstado")
    , @NamedQuery(name = "RnGcCpPaissatTbl.findByClaveMunicipio", query = "SELECT r FROM RnGcCpPaissatTbl r WHERE r.claveMunicipio = :claveMunicipio")
    , @NamedQuery(name = "RnGcCpPaissatTbl.findByClaveLocalidad", query = "SELECT r FROM RnGcCpPaissatTbl r WHERE r.claveLocalidad = :claveLocalidad")
    , @NamedQuery(name = "RnGcCpPaissatTbl.findByTipo", query = "SELECT r FROM RnGcCpPaissatTbl r WHERE r.tipo = :tipo")
    , @NamedQuery(name = "RnGcCpPaissatTbl.findByEstadoPais", query = "SELECT r FROM RnGcCpPaissatTbl r WHERE r.tipo = :tipo and r.clavePais = :clavePais")
    , @NamedQuery(name = "RnGcCpPaissatTbl.findByEstado", query = "SELECT r FROM RnGcCpPaissatTbl r WHERE r.tipo = :tipo and r.claveEstado = :claveEstado")
    , @NamedQuery(name = "RnGcCpPaissatTbl.findByFechaInicioVigencia", query = "SELECT r FROM RnGcCpPaissatTbl r WHERE r.fechaInicioVigencia = :fechaInicioVigencia")
    , @NamedQuery(name = "RnGcCpPaissatTbl.findByFechaFinVigencia", query = "SELECT r FROM RnGcCpPaissatTbl r WHERE r.fechaFinVigencia = :fechaFinVigencia")
    , @NamedQuery(name = "RnGcCpPaissatTbl.findByCreadoPor", query = "SELECT r FROM RnGcCpPaissatTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCpPaissatTbl.findByFechaCreacion", query = "SELECT r FROM RnGcCpPaissatTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcCpPaissatTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcCpPaissatTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcCpPaissatTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcCpPaissatTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcCpPaissatTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Size(max = 150)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 5)
    @Column(name = "clavePais")
    private String clavePais;
    @Size(max = 5)
    @Column(name = "claveEstado")
    private String claveEstado;
    @Size(max = 5)
    @Column(name = "claveMunicipio")
    private String claveMunicipio;
    @Size(max = 5)
    @Column(name = "claveLocalidad")
    private String claveLocalidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaInicioVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioVigencia;
    @Column(name = "fechaFinVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaFinVigencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creadoPor")
    private int creadoPor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaCreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "ultimaActualizacionPor")
    private Integer ultimaActualizacionPor;
    @Column(name = "ultimaFechaActualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaFechaActualizacion;

    public RnGcCpPaissatTbl() {
    }

    public RnGcCpPaissatTbl(Integer id) {
        this.id = id;
    }

    public RnGcCpPaissatTbl(Integer id, String tipo, Date fechaInicioVigencia, int creadoPor, Date fechaCreacion) {
        this.id = id;
        this.tipo = tipo;
        this.fechaInicioVigencia = fechaInicioVigencia;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getClavePais() {
        return clavePais;
    }

    public void setClavePais(String clavePais) {
        this.clavePais = clavePais;
    }

    public String getClaveEstado() {
        return claveEstado;
    }

    public void setClaveEstado(String claveEstado) {
        this.claveEstado = claveEstado;
    }

    public String getClaveMunicipio() {
        return claveMunicipio;
    }

    public void setClaveMunicipio(String claveMunicipio) {
        this.claveMunicipio = claveMunicipio;
    }

    public String getClaveLocalidad() {
        return claveLocalidad;
    }

    public void setClaveLocalidad(String claveLocalidad) {
        this.claveLocalidad = claveLocalidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public Integer getUltimaActualizacionPor() {
        return ultimaActualizacionPor;
    }

    public void setUltimaActualizacionPor(Integer ultimaActualizacionPor) {
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
        if (!(object instanceof RnGcCpPaissatTbl)) {
            return false;
        }
        RnGcCpPaissatTbl other = (RnGcCpPaissatTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcCpPaissatTbl[ id=" + id + " ]";
    }
    
}
