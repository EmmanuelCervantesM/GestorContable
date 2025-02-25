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
@Table(name = "rn_gc_facturaslineas_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcFacturaslineasTbl.findAll", query = "SELECT r FROM RnGcFacturaslineasTbl r")
    , @NamedQuery(name = "RnGcFacturaslineasTbl.findById", query = "SELECT r FROM RnGcFacturaslineasTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcFacturaslineasTbl.findByClaveProdServ", query = "SELECT r FROM RnGcFacturaslineasTbl r WHERE r.claveProdServ = :claveProdServ")
    , @NamedQuery(name = "RnGcFacturaslineasTbl.findByNoIdentificacion", query = "SELECT r FROM RnGcFacturaslineasTbl r WHERE r.noIdentificacion = :noIdentificacion")
    , @NamedQuery(name = "RnGcFacturaslineasTbl.findByCantidad", query = "SELECT r FROM RnGcFacturaslineasTbl r WHERE r.cantidad = :cantidad")
    , @NamedQuery(name = "RnGcFacturaslineasTbl.findByClaveUnidad", query = "SELECT r FROM RnGcFacturaslineasTbl r WHERE r.claveUnidad = :claveUnidad")
    , @NamedQuery(name = "RnGcFacturaslineasTbl.findByUnidad", query = "SELECT r FROM RnGcFacturaslineasTbl r WHERE r.unidad = :unidad")
    , @NamedQuery(name = "RnGcFacturaslineasTbl.findByDescripcion", query = "SELECT r FROM RnGcFacturaslineasTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcFacturaslineasTbl.findByValorUnit", query = "SELECT r FROM RnGcFacturaslineasTbl r WHERE r.valorUnit = :valorUnit")
    , @NamedQuery(name = "RnGcFacturaslineasTbl.findByImporte", query = "SELECT r FROM RnGcFacturaslineasTbl r WHERE r.importe = :importe")
    , @NamedQuery(name = "RnGcFacturaslineasTbl.findByBase", query = "SELECT r FROM RnGcFacturaslineasTbl r WHERE r.base = :base")
    , @NamedQuery(name = "RnGcFacturaslineasTbl.findByImpuesto", query = "SELECT r FROM RnGcFacturaslineasTbl r WHERE r.impuesto = :impuesto")
    , @NamedQuery(name = "RnGcFacturaslineasTbl.findByTipoFactor", query = "SELECT r FROM RnGcFacturaslineasTbl r WHERE r.tipoFactor = :tipoFactor")
    , @NamedQuery(name = "RnGcFacturaslineasTbl.findByTipoTasa", query = "SELECT r FROM RnGcFacturaslineasTbl r WHERE r.tipoTasa = :tipoTasa")
    , @NamedQuery(name = "RnGcFacturaslineasTbl.findByImporteImpuesto", query = "SELECT r FROM RnGcFacturaslineasTbl r WHERE r.importeImpuesto = :importeImpuesto")
    , @NamedQuery(name = "RnGcFacturaslineasTbl.findByCreadoPor", query = "SELECT r FROM RnGcFacturaslineasTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcFacturaslineasTbl.findByFechaCreacion", query = "SELECT r FROM RnGcFacturaslineasTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcFacturaslineasTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcFacturaslineasTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcFacturaslineasTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcFacturaslineasTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcFacturaslineasTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "claveProdServ")
    private int claveProdServ;
    @Size(max = 25)
    @Column(name = "NoIdentificacion")
    private String noIdentificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Cantidad")
    private float cantidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "claveUnidad")
    private String claveUnidad;
    @Size(max = 45)
    @Column(name = "Unidad")
    private String unidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "Descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ValorUnit")
    private float valorUnit;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Importe")
    private float importe;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Base")
    private float base;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Impuesto")
    private int impuesto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "TipoFactor")
    private String tipoFactor;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TipoTasa")
    private Float tipoTasa;
    @Size(max = 45)
    @Column(name = "importeImpuesto")
    private String importeImpuesto;
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
    @JoinColumn(name = "rn_gc_facturas_Id", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private RnGcFacturasTbl rngcfacturasId;

    public RnGcFacturaslineasTbl() {
    }

    public RnGcFacturaslineasTbl(Integer id) {
        this.id = id;
    }

    public RnGcFacturaslineasTbl(Integer id, int claveProdServ, float cantidad, String claveUnidad, String descripcion, float valorUnit, float importe, float base, int impuesto, String tipoFactor, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.claveProdServ = claveProdServ;
        this.cantidad = cantidad;
        this.claveUnidad = claveUnidad;
        this.descripcion = descripcion;
        this.valorUnit = valorUnit;
        this.importe = importe;
        this.base = base;
        this.impuesto = impuesto;
        this.tipoFactor = tipoFactor;
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

    public int getClaveProdServ() {
        return claveProdServ;
    }

    public void setClaveProdServ(int claveProdServ) {
        this.claveProdServ = claveProdServ;
    }

    public String getNoIdentificacion() {
        return noIdentificacion;
    }

    public void setNoIdentificacion(String noIdentificacion) {
        this.noIdentificacion = noIdentificacion;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public String getClaveUnidad() {
        return claveUnidad;
    }

    public void setClaveUnidad(String claveUnidad) {
        this.claveUnidad = claveUnidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getValorUnit() {
        return valorUnit;
    }

    public void setValorUnit(float valorUnit) {
        this.valorUnit = valorUnit;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

    public float getBase() {
        return base;
    }

    public void setBase(float base) {
        this.base = base;
    }

    public int getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(int impuesto) {
        this.impuesto = impuesto;
    }

    public String getTipoFactor() {
        return tipoFactor;
    }

    public void setTipoFactor(String tipoFactor) {
        this.tipoFactor = tipoFactor;
    }

    public Float getTipoTasa() {
        return tipoTasa;
    }

    public void setTipoTasa(Float tipoTasa) {
        this.tipoTasa = tipoTasa;
    }

    public String getImporteImpuesto() {
        return importeImpuesto;
    }

    public void setImporteImpuesto(String importeImpuesto) {
        this.importeImpuesto = importeImpuesto;
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

    public RnGcFacturasTbl getRngcfacturasId() {
        return rngcfacturasId;
    }

    public void setRngcfacturasId(RnGcFacturasTbl rngcfacturasId) {
        this.rngcfacturasId = rngcfacturasId;
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
        if (!(object instanceof RnGcFacturaslineasTbl)) {
            return false;
        }
        RnGcFacturaslineasTbl other = (RnGcFacturaslineasTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcFacturaslineasTbl[ id=" + id + " ]";
    }
    
}
