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
 * @author Developer1
 */
@Entity
@Table(name = "rn_gc_codigospostales_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcCodigospostalesTbl.findAll", query = "SELECT r FROM RnGcCodigospostalesTbl r")
    , @NamedQuery(name = "RnGcCodigospostalesTbl.findByCreadoPor", query = "SELECT r FROM RnGcCodigospostalesTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCodigospostalesTbl.findByFechaCreacion", query = "SELECT r FROM RnGcCodigospostalesTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcCodigospostalesTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcCodigospostalesTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcCodigospostalesTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcCodigospostalesTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcCodigospostalesTbl.findById", query = "SELECT r FROM RnGcCodigospostalesTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcCodigospostalesTbl.findByCCodigoPostal", query = "SELECT r FROM RnGcCodigospostalesTbl r WHERE r.cCodigoPostal = :cCodigoPostal")
    , @NamedQuery(name = "RnGcCodigospostalesTbl.findByCEstado", query = "SELECT r FROM RnGcCodigospostalesTbl r WHERE r.cEstado = :cEstado")
    , @NamedQuery(name = "RnGcCodigospostalesTbl.findByCMunicipio", query = "SELECT r FROM RnGcCodigospostalesTbl r WHERE r.cMunicipio = :cMunicipio")
    , @NamedQuery(name = "RnGcCodigospostalesTbl.findByCLocalidad", query = "SELECT r FROM RnGcCodigospostalesTbl r WHERE r.cLocalidad = :cLocalidad")})
public class RnGcCodigospostalesTbl implements Serializable {

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
    @Column(name = "c_CodigoPostal")
    private String cCodigoPostal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "c_Estado")
    private String cEstado;
    @Size(max = 80)
    @Column(name = "c_Municipio")
    private String cMunicipio;
    @Size(max = 80)
    @Column(name = "c_Localidad")
    private String cLocalidad;
    @JoinColumn(name = "cfdi_Id", referencedColumnName = "Id")
    @ManyToOne
    private RnGcCfdisTbl cfdiId;

    public RnGcCodigospostalesTbl() {
    }

    public RnGcCodigospostalesTbl(Integer id) {
        this.id = id;
    }

    public RnGcCodigospostalesTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion, String cCodigoPostal, String cEstado) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
        this.cCodigoPostal = cCodigoPostal;
        this.cEstado = cEstado;
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

    public String getCCodigoPostal() {
        return cCodigoPostal;
    }

    public void setCCodigoPostal(String cCodigoPostal) {
        this.cCodigoPostal = cCodigoPostal;
    }

    public String getCEstado() {
        return cEstado;
    }

    public void setCEstado(String cEstado) {
        this.cEstado = cEstado;
    }

    public String getCMunicipio() {
        return cMunicipio;
    }

    public void setCMunicipio(String cMunicipio) {
        this.cMunicipio = cMunicipio;
    }

    public String getCLocalidad() {
        return cLocalidad;
    }

    public void setCLocalidad(String cLocalidad) {
        this.cLocalidad = cLocalidad;
    }

    public RnGcCfdisTbl getCfdiId() {
        return cfdiId;
    }

    public void setCfdiId(RnGcCfdisTbl cfdiId) {
        this.cfdiId = cfdiId;
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
        if (!(object instanceof RnGcCodigospostalesTbl)) {
            return false;
        }
        RnGcCodigospostalesTbl other = (RnGcCodigospostalesTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcCodigospostalesTbl[ id=" + id + " ]";
    }
    
}
