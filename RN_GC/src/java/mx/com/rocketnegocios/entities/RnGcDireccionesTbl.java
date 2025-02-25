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
import javax.persistence.CascadeType;
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
@Table(name = "rn_gc_direcciones_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcDireccionesTbl.findAll", query = "SELECT r FROM RnGcDireccionesTbl r")
    , @NamedQuery(name = "RnGcDireccionesTbl.findById", query = "SELECT r FROM RnGcDireccionesTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcDireccionesTbl.findByTipo", query = "SELECT r FROM RnGcDireccionesTbl r WHERE r.tipo = :tipo")
    , @NamedQuery(name = "RnGcDireccionesTbl.findByPais", query = "SELECT r FROM RnGcDireccionesTbl r WHERE r.pais = :pais")
    , @NamedQuery(name = "RnGcDireccionesTbl.findByEstado", query = "SELECT r FROM RnGcDireccionesTbl r WHERE r.estado = :estado")
    , @NamedQuery(name = "RnGcDireccionesTbl.findByMunicipio", query = "SELECT r FROM RnGcDireccionesTbl r WHERE r.municipio = :municipio")
    , @NamedQuery(name = "RnGcDireccionesTbl.findByColonia", query = "SELECT r FROM RnGcDireccionesTbl r WHERE r.colonia = :colonia")
    , @NamedQuery(name = "RnGcDireccionesTbl.findByCodigoPostal", query = "SELECT r FROM RnGcDireccionesTbl r WHERE r.codigoPostal = :codigoPostal")
    , @NamedQuery(name = "RnGcDireccionesTbl.findByNombreCalle", query = "SELECT r FROM RnGcDireccionesTbl r WHERE r.nombreCalle = :nombreCalle")
    , @NamedQuery(name = "RnGcDireccionesTbl.findByNumeroInterior", query = "SELECT r FROM RnGcDireccionesTbl r WHERE r.numeroInterior = :numeroInterior")
    , @NamedQuery(name = "RnGcDireccionesTbl.findByNumeroExterior", query = "SELECT r FROM RnGcDireccionesTbl r WHERE r.numeroExterior = :numeroExterior")
    , @NamedQuery(name = "RnGcDireccionesTbl.findByCreadoPor", query = "SELECT r FROM RnGcDireccionesTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcDireccionesTbl.findByFechaCreacion", query = "SELECT r FROM RnGcDireccionesTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcDireccionesTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcDireccionesTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcDireccionesTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcDireccionesTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcDireccionesTbl.findByPersonaId", query = "SELECT r FROM RnGcDireccionesTbl r WHERE r.personasId = :personasId")
    , @NamedQuery(name = "RnGcDireccionesTbl.findByConductorId", query = "SELECT r FROM RnGcDireccionesTbl r WHERE r.conductorId = :conductorId")})
public class RnGcDireccionesTbl implements Serializable {

    @OneToMany(mappedBy = "dirConductorId")
    private Collection<RnGcCpCartaPorteTbl> rnGcCpCartaPorteTblCollection;
    @OneToMany(mappedBy = "dirClienteProveedorId")
    private Collection<RnGcCpOrigendestinoTbl> rnGcCpOrigendestinoTblCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "tipo")
    private String tipo;
    @Size(max = 75)
    @Column(name = "Pais")
    private String pais;
    @Size(max = 75)
    @Column(name = "Estado")
    private String estado;
    @Column(name = "municipio")
    private String municipio;
    @Size(max = 45)
    @Column(name = "colonia")
    private String colonia;
    @Column(name = "codigoPostal")
    private String codigoPostal;
    @Size(max = 100)
    @Column(name = "nombreCalle")
    private String nombreCalle;
    @Size(max = 25)
    @Column(name = "numeroInterior")
    private String numeroInterior;
    @Size(max = 25)
    @Column(name = "numeroExterior")
    private String numeroExterior;
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
    @JoinColumn(name = "personasId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcPersonasTbl personasId;
    @JoinColumn(name = "conductorId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcCpConductoresTbl conductorId;
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

    public RnGcDireccionesTbl() {
    }

    public RnGcDireccionesTbl(Integer id) {
        this.id = id;
    }

    public RnGcDireccionesTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
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

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getNombreCalle() {
        return nombreCalle;
    }

    public void setNombreCalle(String nombreCalle) {
        this.nombreCalle = nombreCalle;
    }

    public String getNumeroInterior() {
        return numeroInterior;
    }

    public void setNumeroInterior(String numeroInterior) {
        this.numeroInterior = numeroInterior;
    }

    public String getNumeroExterior() {
        return numeroExterior;
    }

    public void setNumeroExterior(String numeroExterior) {
        this.numeroExterior = numeroExterior;
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

    public RnGcPersonasTbl getPersonasId() {
        return personasId;
    }

    public void setPersonasId(RnGcPersonasTbl personasId) {
        this.personasId = personasId;
    }

    public RnGcCpConductoresTbl getConductorId() {
        return conductorId;
    }

    public void setConductorId(RnGcCpConductoresTbl conductorId) {
        this.conductorId = conductorId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RnGcDireccionesTbl)) {
            return false;
        }
        RnGcDireccionesTbl other = (RnGcDireccionesTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcDireccionesTbl[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<RnGcCpCartaPorteTbl> getRnGcCpCartaPorteTblCollection() {
        return rnGcCpCartaPorteTblCollection;
    }

    public void setRnGcCpCartaPorteTblCollection(Collection<RnGcCpCartaPorteTbl> rnGcCpCartaPorteTblCollection) {
        this.rnGcCpCartaPorteTblCollection = rnGcCpCartaPorteTblCollection;
    }
    
    public Collection<RnGcCpOrigendestinoTbl> getRnGcCpOrigendestinoTblCollection() {
        return rnGcCpOrigendestinoTblCollection;
    }

    public void setRnGcCpOrigendestinoTblCollection(Collection<RnGcCpOrigendestinoTbl> rnGcCpOrigendestinoTblCollection) {
        this.rnGcCpOrigendestinoTblCollection = rnGcCpOrigendestinoTblCollection;
    }
    
}
