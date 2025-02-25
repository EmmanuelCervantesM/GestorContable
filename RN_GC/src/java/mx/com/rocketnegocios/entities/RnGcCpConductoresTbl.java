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
 * @author maxoc
 */
@Entity
@Table(name = "rn_gc_cp_conductores_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcCpConductoresTbl.findAll", query = "SELECT r FROM RnGcCpConductoresTbl r")
    , @NamedQuery(name = "RnGcCpConductoresTbl.findById", query = "SELECT r FROM RnGcCpConductoresTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcCpConductoresTbl.findByLicencia", query = "SELECT r FROM RnGcCpConductoresTbl r WHERE r.licencia = :licencia")
    , @NamedQuery(name = "RnGcCpConductoresTbl.findByNombre", query = "SELECT r FROM RnGcCpConductoresTbl r WHERE r.nombre = :nombre")
    , @NamedQuery(name = "RnGcCpConductoresTbl.findByApellidoPaterno", query = "SELECT r FROM RnGcCpConductoresTbl r WHERE r.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "RnGcCpConductoresTbl.findByApellidoMaterno", query = "SELECT r FROM RnGcCpConductoresTbl r WHERE r.apellidoMaterno = :apellidoMaterno")
    , @NamedQuery(name = "RnGcCpConductoresTbl.findByTelefono", query = "SELECT r FROM RnGcCpConductoresTbl r WHERE r.telefono = :telefono")
    , @NamedQuery(name = "RnGcCpConductoresTbl.findByEstado", query = "SELECT r FROM RnGcCpConductoresTbl r WHERE r.estado = :estado")
    , @NamedQuery(name = "RnGcCpConductoresTbl.findByCreadoPor", query = "SELECT r FROM RnGcCpConductoresTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCpConductoresTbl.findByFechaCreacion", query = "SELECT r FROM RnGcCpConductoresTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcCpConductoresTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcCpConductoresTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcCpConductoresTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcCpConductoresTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcCpConductoresTbl.findByRfc", query = "SELECT r FROM RnGcCpConductoresTbl r WHERE r.rfc = :rfc")
    , @NamedQuery(name = "RnGcCpConductoresTbl.findByClaveFigura", query = "SELECT r FROM RnGcCpConductoresTbl r WHERE r.claveFigura = :claveFigura")
    , @NamedQuery(name = "RnGcCpConductoresTbl.findByNumeroLicencia", query = "SELECT r FROM RnGcCpConductoresTbl r WHERE r.numeroLicencia = :numeroLicencia")})
public class RnGcCpConductoresTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "licencia")
    private String licencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "apellidoPaterno")
    private String apellidoPaterno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "apellidoMaterno")
    private String apellidoMaterno;
    @Size(max = 10)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 5)
    @Column(name = "estado")
    private String estado;
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
    @Size(max = 15)
    @Column(name = "rfc")
    private String rfc;
    @Size(max = 2)
    @Column(name = "claveFigura")
    private String claveFigura;
    @Size(max = 25)
    @Column(name = "numeroLicencia")
    private String numeroLicencia;
    @JoinColumn(name = "tipoLicenciaId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private RnGcCpTipolicenciaTbl tipoLicenciaId;
    @OneToMany(mappedBy = "conductorId")
    private Collection<RnGcCpCartaPorteTbl> rnGcCpCartaPorteTblCollection;
    @OneToMany( mappedBy = "conductorId")
    private Collection<RnGcDireccionesTbl> rnGcDireccionesTblCollection;

    public RnGcCpConductoresTbl() {
    }

    public RnGcCpConductoresTbl(Integer id) {
        this.id = id;
    }

    public RnGcCpConductoresTbl(Integer id, String licencia, String nombre, String apellidoPaterno, String apellidoMaterno, int creadoPor, Date fechaCreacion) {
        this.id = id;
        this.licencia = licencia;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getClaveFigura() {
        return claveFigura;
    }

    public void setClaveFigura(String claveFigura) {
        this.claveFigura = claveFigura;
    }

    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }

    public RnGcCpTipolicenciaTbl getTipoLicenciaId() {
        return tipoLicenciaId;
    }

    public void setTipoLicenciaId(RnGcCpTipolicenciaTbl tipoLicenciaId) {
        this.tipoLicenciaId = tipoLicenciaId;
    }

    @XmlTransient
    public Collection<RnGcCpCartaPorteTbl> getRnGcCpCartaPorteTblCollection() {
        return rnGcCpCartaPorteTblCollection;
    }

    public void setRnGcCpCartaPorteTblCollection(Collection<RnGcCpCartaPorteTbl> rnGcCpCartaPorteTblCollection) {
        this.rnGcCpCartaPorteTblCollection = rnGcCpCartaPorteTblCollection;
    }

    @XmlTransient
    public Collection<RnGcDireccionesTbl> getRnGcDireccionesTblCollection() {
        return rnGcDireccionesTblCollection;
    }

    public void setRnGcDireccionesTblCollection(Collection<RnGcDireccionesTbl> rnGcDireccionesTblCollection) {
        this.rnGcDireccionesTblCollection = rnGcDireccionesTblCollection;
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
        if (!(object instanceof RnGcCpConductoresTbl)) {
            return false;
        }
        RnGcCpConductoresTbl other = (RnGcCpConductoresTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcCpConductoresTbl[ id=" + id + " ]";
    }
    
}
