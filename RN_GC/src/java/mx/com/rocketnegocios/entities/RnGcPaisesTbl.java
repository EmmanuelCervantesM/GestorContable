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
@Table(name = "rn_gc_paises_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcPaisesTbl.findAll", query = "SELECT r FROM RnGcPaisesTbl r")
    , @NamedQuery(name = "RnGcPaisesTbl.findByCreadoPor", query = "SELECT r FROM RnGcPaisesTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcPaisesTbl.findByFechaCreacion", query = "SELECT r FROM RnGcPaisesTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcPaisesTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcPaisesTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcPaisesTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcPaisesTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcPaisesTbl.findById", query = "SELECT r FROM RnGcPaisesTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcPaisesTbl.findByCPais", query = "SELECT r FROM RnGcPaisesTbl r WHERE r.cPais = :cPais")
    , @NamedQuery(name = "RnGcPaisesTbl.findByDescripcion", query = "SELECT r FROM RnGcPaisesTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcPaisesTbl.findByFormatoCodPostal", query = "SELECT r FROM RnGcPaisesTbl r WHERE r.formatoCodPostal = :formatoCodPostal")
    , @NamedQuery(name = "RnGcPaisesTbl.findByFormatoIdentidadTributaria", query = "SELECT r FROM RnGcPaisesTbl r WHERE r.formatoIdentidadTributaria = :formatoIdentidadTributaria")
    , @NamedQuery(name = "RnGcPaisesTbl.findByValidacionIdentidadTributaria", query = "SELECT r FROM RnGcPaisesTbl r WHERE r.validacionIdentidadTributaria = :validacionIdentidadTributaria")
    , @NamedQuery(name = "RnGcPaisesTbl.findByAgrupaciones", query = "SELECT r FROM RnGcPaisesTbl r WHERE r.agrupaciones = :agrupaciones")})
public class RnGcPaisesTbl implements Serializable {

    private static final long serialVersionUID = 1L;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "c_Pais")
    private String cPais;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Descripcion")
    private String descripcion;
    @Size(max = 40)
    @Column(name = "formatoCodPostal")
    private String formatoCodPostal;
    @Size(max = 85)
    @Column(name = "formatoIdentidadTributaria")
    private String formatoIdentidadTributaria;
    @Size(max = 45)
    @Column(name = "validacionIdentidadTributaria")
    private String validacionIdentidadTributaria;
    @Size(max = 15)
    @Column(name = "Agrupaciones")
    private String agrupaciones;
    @Column(name = "cfdi_Id")
    private Integer cfdiId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paisId")
    private Collection<RnGcEstadosTbl> rnGcEstadosTblCollection;


    public RnGcPaisesTbl() {
    }

    public RnGcPaisesTbl(Integer id) {
        this.id = id;
    }

    public RnGcPaisesTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion, String cPais, String descripcion) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
        this.cPais = cPais;
        this.descripcion = descripcion;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCPais() {
        return cPais;
    }

    public void setCPais(String cPais) {
        this.cPais = cPais;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFormatoCodPostal() {
        return formatoCodPostal;
    }

    public void setFormatoCodPostal(String formatoCodPostal) {
        this.formatoCodPostal = formatoCodPostal;
    }

    public String getFormatoIdentidadTributaria() {
        return formatoIdentidadTributaria;
    }

    public void setFormatoIdentidadTributaria(String formatoIdentidadTributaria) {
        this.formatoIdentidadTributaria = formatoIdentidadTributaria;
    }

    public String getValidacionIdentidadTributaria() {
        return validacionIdentidadTributaria;
    }

    public void setValidacionIdentidadTributaria(String validacionIdentidadTributaria) {
        this.validacionIdentidadTributaria = validacionIdentidadTributaria;
    }

    public String getAgrupaciones() {
        return agrupaciones;
    }

    public void setAgrupaciones(String agrupaciones) {
        this.agrupaciones = agrupaciones;
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
        if (!(object instanceof RnGcPaisesTbl)) {
            return false;
        }
        RnGcPaisesTbl other = (RnGcPaisesTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcPaisesTbl[ id=" + id + " ]";
    }

    public Integer getCfdiId() {
        return cfdiId;
    }

    public void setCfdiId(Integer cfdiId) {
        this.cfdiId = cfdiId;
    }

    @XmlTransient
    public Collection<RnGcEstadosTbl> getRnGcEstadosTblCollection() {
        return rnGcEstadosTblCollection;
    }

    public void setRnGcEstadosTblCollection(Collection<RnGcEstadosTbl> rnGcEstadosTblCollection) {
        this.rnGcEstadosTblCollection = rnGcEstadosTblCollection;
    }
    
}
