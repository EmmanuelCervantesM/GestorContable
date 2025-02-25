/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.entities;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Developer1
 */
@Entity
@Table(name = "rn_gc_direcciones_usuarios_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcDireccionesUsuariosTbl.findAll", query = "SELECT r FROM RnGcDireccionesUsuariosTbl r")
    , @NamedQuery(name = "RnGcDireccionesUsuariosTbl.findById", query = "SELECT r FROM RnGcDireccionesUsuariosTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcDireccionesUsuariosTbl.findByTipo", query = "SELECT r FROM RnGcDireccionesUsuariosTbl r WHERE r.tipo = :tipo")
    , @NamedQuery(name = "RnGcDireccionesUsuariosTbl.findByPais", query = "SELECT r FROM RnGcDireccionesUsuariosTbl r WHERE r.pais = :pais")
    , @NamedQuery(name = "RnGcDireccionesUsuariosTbl.findByEstado", query = "SELECT r FROM RnGcDireccionesUsuariosTbl r WHERE r.estado = :estado")
    , @NamedQuery(name = "RnGcDireccionesUsuariosTbl.findByMunicipio", query = "SELECT r FROM RnGcDireccionesUsuariosTbl r WHERE r.municipio = :municipio")
    , @NamedQuery(name = "RnGcDireccionesUsuariosTbl.findByColonia", query = "SELECT r FROM RnGcDireccionesUsuariosTbl r WHERE r.colonia = :colonia")
    , @NamedQuery(name = "RnGcDireccionesUsuariosTbl.findByCodigoPostal", query = "SELECT r FROM RnGcDireccionesUsuariosTbl r WHERE r.codigoPostal = :codigoPostal")
    , @NamedQuery(name = "RnGcDireccionesUsuariosTbl.findByNombreCalle", query = "SELECT r FROM RnGcDireccionesUsuariosTbl r WHERE r.nombreCalle = :nombreCalle")
    , @NamedQuery(name = "RnGcDireccionesUsuariosTbl.findByNoExt", query = "SELECT r FROM RnGcDireccionesUsuariosTbl r WHERE r.noExt = :noExt")
    , @NamedQuery(name = "RnGcDireccionesUsuariosTbl.findByNoInt", query = "SELECT r FROM RnGcDireccionesUsuariosTbl r WHERE r.noInt = :noInt")
    , @NamedQuery(name = "RnGcDireccionesUsuariosTbl.findByCreadoPor", query = "SELECT r FROM RnGcDireccionesUsuariosTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcDireccionesUsuariosTbl.findByFechaCreacion", query = "SELECT r FROM RnGcDireccionesUsuariosTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcDireccionesUsuariosTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcDireccionesUsuariosTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcDireccionesUsuariosTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcDireccionesUsuariosTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcDireccionesUsuariosTbl.findByUsuarioId", query = "SELECT r FROM RnGcDireccionesUsuariosTbl r WHERE r.usuarioId = :usuarioId")})
public class RnGcDireccionesUsuariosTbl implements Serializable {

    @OneToMany(mappedBy = "dirUsuarioId")
    private Collection<RnGcCpCartaPorteTbl> rnGcCpCartaPorteTblCollection1;
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "tipo")
    private String tipo;
    @Size(max = 45)
    @Column(name = "pais")
    private String pais;
    @Size(max = 45)
    @Column(name = "estado")
    private String estado;
    @Size(max = 45)
    @Column(name = "municipio")
    private String municipio;
    @Size(max = 45)
    @Column(name = "colonia")
    private String colonia;
    @Column(name = "codigoPostal")
    private Integer codigoPostal;
    @Size(max = 45)
    @Column(name = "nombreCalle")
    private String nombreCalle;
    @Size(max = 45)
    @Column(name = "noExt")
    private String noExt;
    @Size(max = 45)
    @Column(name = "noInt")
    private String noInt;
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
    @JoinColumn(name = "usuarioId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcUsuariosTbl usuarioId;
    @Column(name = "clavePais")
    private String clavePais;
    @Column(name = "claveEstado")
    private String claveEstado;
    @Column(name = "claveMunicipio")
    private String claveMunicipio;
    @Column(name = "claveLocalidad")
    private String claveLocalidad;
    @Column(name = "claveColonia")
    private String claveColonia;

    public RnGcDireccionesUsuariosTbl() {
    }

    public RnGcDireccionesUsuariosTbl(Integer id) {
        this.id = id;
    }

    public RnGcDireccionesUsuariosTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getNombreCalle() {
        return nombreCalle;
    }

    public void setNombreCalle(String nombreCalle) {
        this.nombreCalle = nombreCalle;
    }

    public String getNoExt() {
        return noExt;
    }

    public void setNoExt(String noExt) {
        this.noExt = noExt;
    }

    public String getNoInt() {
        return noInt;
    }

    public void setNoInt(String noInt) {
        this.noInt = noInt;
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

    public RnGcUsuariosTbl getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(RnGcUsuariosTbl usuarioId) {
        this.usuarioId = usuarioId;
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

    public String getClaveColonia() {
        return claveColonia;
    }

    public void setClaveColonia(String claveColonia) {
        this.claveColonia = claveColonia;
    }
    
    @XmlTransient
    public Collection<RnGcCpCartaPorteTbl> getRnGcCpCartaPorteTblCollection1() {
        return rnGcCpCartaPorteTblCollection1;
    }

    public void setRnGcCpCartaPorteTblCollection1(Collection<RnGcCpCartaPorteTbl> rnGcCpCartaPorteTblCollection1) {
        this.rnGcCpCartaPorteTblCollection1 = rnGcCpCartaPorteTblCollection1;
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
        if (!(object instanceof RnGcDireccionesUsuariosTbl)) {
            return false;
        }
        RnGcDireccionesUsuariosTbl other = (RnGcDireccionesUsuariosTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcDireccionesUsuariosTbl[ id=" + id + " ]";
    }
    
}
