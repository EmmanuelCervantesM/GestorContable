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
@Table(name = "rn_gc_tiposcfdis_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcTiposcfdisTbl.findAll", query = "SELECT r FROM RnGcTiposcfdisTbl r")
    , @NamedQuery(name = "RnGcTiposcfdisTbl.findByCreadoPor", query = "SELECT r FROM RnGcTiposcfdisTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcTiposcfdisTbl.findByFechaCreacion", query = "SELECT r FROM RnGcTiposcfdisTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcTiposcfdisTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcTiposcfdisTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcTiposcfdisTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcTiposcfdisTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcTiposcfdisTbl.findById", query = "SELECT r FROM RnGcTiposcfdisTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcTiposcfdisTbl.findByDescripcion", query = "SELECT r FROM RnGcTiposcfdisTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcTiposcfdisTbl.findByValorMaximo", query = "SELECT r FROM RnGcTiposcfdisTbl r WHERE r.valorMaximo = :valorMaximo")
    , @NamedQuery(name = "RnGcTiposcfdisTbl.findByFechaInicioVigencia", query = "SELECT r FROM RnGcTiposcfdisTbl r WHERE r.fechaInicioVigencia = :fechaInicioVigencia")
    , @NamedQuery(name = "RnGcTiposcfdisTbl.findByFechaFinVigencia", query = "SELECT r FROM RnGcTiposcfdisTbl r WHERE r.fechaFinVigencia = :fechaFinVigencia")
    , @NamedQuery(name = "RnGcTiposcfdisTbl.findByClavetipoComprobante", query = "SELECT r FROM RnGcTiposcfdisTbl r WHERE r.clavetipoComprobante = :clavetipoComprobante")})
public class RnGcTiposcfdisTbl implements Serializable {

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
    @Size(min = 1, max = 8)
    @Column(name = "Descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ValorMaximo")
    private int valorMaximo;
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
    @Size(min = 1, max = 1)
    @Column(name = "clavetipoComprobante")
    private String clavetipoComprobante;
    @JoinColumn(name = "cfdi_Id", referencedColumnName = "Id")
    @ManyToOne
    private RnGcCfdisTbl cfdiId;

    public RnGcTiposcfdisTbl() {
    }

    public RnGcTiposcfdisTbl(Integer id) {
        this.id = id;
    }

    public RnGcTiposcfdisTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion, String descripcion, int valorMaximo, Date fechaInicioVigencia, String clavetipoComprobante) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
        this.descripcion = descripcion;
        this.valorMaximo = valorMaximo;
        this.fechaInicioVigencia = fechaInicioVigencia;
        this.clavetipoComprobante = clavetipoComprobante;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(int valorMaximo) {
        this.valorMaximo = valorMaximo;
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

    public String getClavetipoComprobante() {
        return clavetipoComprobante;
    }

    public void setClavetipoComprobante(String clavetipoComprobante) {
        this.clavetipoComprobante = clavetipoComprobante;
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
        if (!(object instanceof RnGcTiposcfdisTbl)) {
            return false;
        }
        RnGcTiposcfdisTbl other = (RnGcTiposcfdisTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcTiposcfdisTbl[ id=" + id + " ]";
    }
    
}
