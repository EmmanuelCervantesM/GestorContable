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
 * @author Consultor
 */
@Entity
@Table(name = "rn_gc_poliza_lineas_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcPolizaLineasTbl.findAll", query = "SELECT r FROM RnGcPolizaLineasTbl r")
    , @NamedQuery(name = "RnGcPolizaLineasTbl.findById", query = "SELECT r FROM RnGcPolizaLineasTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcPolizaLineasTbl.findByPolizaHeaderId", query = "SELECT r FROM RnGcPolizaLineasTbl r WHERE r.polizaHeaderId = :polizaHeaderId")
    , @NamedQuery(name = "RnGcPolizaLineasTbl.findBySucursal", query = "SELECT r FROM RnGcPolizaLineasTbl r WHERE r.sucursal = :sucursal")
    , @NamedQuery(name = "RnGcPolizaLineasTbl.findByCargo", query = "SELECT r FROM RnGcPolizaLineasTbl r WHERE r.cargo = :cargo")
    , @NamedQuery(name = "RnGcPolizaLineasTbl.findByAbono", query = "SELECT r FROM RnGcPolizaLineasTbl r WHERE r.abono = :abono")
    , @NamedQuery(name = "RnGcPolizaLineasTbl.findByReferencia", query = "SELECT r FROM RnGcPolizaLineasTbl r WHERE r.referencia = :referencia")
    , @NamedQuery(name = "RnGcPolizaLineasTbl.findByConcepto", query = "SELECT r FROM RnGcPolizaLineasTbl r WHERE r.concepto = :concepto")
    , @NamedQuery(name = "RnGcPolizaLineasTbl.findByCreadoPor", query = "SELECT r FROM RnGcPolizaLineasTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcPolizaLineasTbl.findByFechaCreacion", query = "SELECT r FROM RnGcPolizaLineasTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcPolizaLineasTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcPolizaLineasTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcPolizaLineasTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcPolizaLineasTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcPolizaLineasTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "sucursal")
    private String sucursal;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cargo")
    private Double cargo;
    @Column(name = "abono")
    private Double abono;
    @Size(max = 500)
    @Column(name = "referencia")
    private String referencia;
    @Size(max = 500)
    @Column(name = "concepto")
    private String concepto;
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
    @JoinColumn(name = "catalogoCuentasId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RnGcCatalogoCuentasTbl catalogoCuentasId;
    @JoinColumn(name = "polizaHeaderId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RnGcPolizaHeaderTbl polizaHeaderId;

    public RnGcPolizaLineasTbl() {
    }

    public RnGcPolizaLineasTbl(Integer id) {
        this.id = id;
    }

    public RnGcPolizaLineasTbl(Integer id, String sucursal, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.sucursal = sucursal;
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

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public Double getCargo() {
        return cargo;
    }

    public void setCargo(Double cargo) {
        this.cargo = cargo;
    }

    public Double getAbono() {
        return abono;
    }

    public void setAbono(Double abono) {
        this.abono = abono;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
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

    public RnGcCatalogoCuentasTbl getCatalogoCuentasId() {
        return catalogoCuentasId;
    }

    public void setCatalogoCuentasId(RnGcCatalogoCuentasTbl catalogoCuentasId) {
        this.catalogoCuentasId = catalogoCuentasId;
    }

    public RnGcPolizaHeaderTbl getPolizaHeaderId() {
        return polizaHeaderId;
    }

    public void setPolizaHeaderId(RnGcPolizaHeaderTbl polizaHeaderId) {
        this.polizaHeaderId = polizaHeaderId;
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
        if (!(object instanceof RnGcPolizaLineasTbl)) {
            return false;
        }
        RnGcPolizaLineasTbl other = (RnGcPolizaLineasTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcPolizaLineasTbl[ id=" + id + " ]";
    }
    
}
