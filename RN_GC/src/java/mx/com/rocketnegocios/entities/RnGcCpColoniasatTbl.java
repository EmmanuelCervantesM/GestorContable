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
@Table(name = "rn_gc_cp_coloniasat_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcCpColoniasatTbl.findAll", query = "SELECT r FROM RnGcCpColoniasatTbl r")
    , @NamedQuery(name = "RnGcCpColoniasatTbl.findById", query = "SELECT r FROM RnGcCpColoniasatTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcCpColoniasatTbl.findByDescripcion", query = "SELECT r FROM RnGcCpColoniasatTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcCpColoniasatTbl.findByClavePais", query = "SELECT r FROM RnGcCpColoniasatTbl r WHERE r.clavePais = :clavePais")
    , @NamedQuery(name = "RnGcCpColoniasatTbl.findByClaveColonia", query = "SELECT r FROM RnGcCpColoniasatTbl r WHERE r.claveColonia = :claveColonia")
    , @NamedQuery(name = "RnGcCpColoniasatTbl.findByCodigoPostal", query = "SELECT r FROM RnGcCpColoniasatTbl r WHERE r.codigoPostal = :codigoPostal")
    , @NamedQuery(name = "RnGcCpColoniasatTbl.findByCodigoPostalFI", query = "SELECT r FROM RnGcCpColoniasatTbl r WHERE r.codigoPostal <= :finals and r.codigoPostal >= :inicial")
    , @NamedQuery(name = "RnGcCpColoniasatTbl.findByTipo", query = "SELECT r FROM RnGcCpColoniasatTbl r WHERE r.tipo = :tipo")
    , @NamedQuery(name = "RnGcCpColoniasatTbl.findByFechaInicio", query = "SELECT r FROM RnGcCpColoniasatTbl r WHERE r.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "RnGcCpColoniasatTbl.findByFechaFin", query = "SELECT r FROM RnGcCpColoniasatTbl r WHERE r.fechaFin = :fechaFin")
    , @NamedQuery(name = "RnGcCpColoniasatTbl.findByCreadoPor", query = "SELECT r FROM RnGcCpColoniasatTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCpColoniasatTbl.findByFechaCreacion", query = "SELECT r FROM RnGcCpColoniasatTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcCpColoniasatTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcCpColoniasatTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcCpColoniasatTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcCpColoniasatTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcCpColoniasatTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 150)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 5)
    @Column(name = "clavePais")
    private String clavePais;
    @Size(max = 5)
    @Column(name = "claveColonia")
    private String claveColonia;
    @Size(max = 10)
    @Column(name = "codigoPostal")
    private String codigoPostal;
    @Size(max = 20)
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fechaFin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Column(name = "creadoPor")
    private Integer creadoPor;
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

    public RnGcCpColoniasatTbl() {
    }

    public RnGcCpColoniasatTbl(Integer id) {
        this.id = id;
    }

    public RnGcCpColoniasatTbl(Integer id, Date fechaCreacion) {
        this.id = id;
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

    public String getClaveColonia() {
        return claveColonia;
    }

    public void setClaveColonia(String claveColonia) {
        this.claveColonia = claveColonia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public Integer getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Integer creadoPor) {
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
        if (!(object instanceof RnGcCpColoniasatTbl)) {
            return false;
        }
        RnGcCpColoniasatTbl other = (RnGcCpColoniasatTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcCpColoniasatTbl[ id=" + id + " ]";
    }
    
}
