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
@Table(name = "rn_gc_trxlineas_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcTrxlineasTbl.findAll", query = "SELECT r FROM RnGcTrxlineasTbl r")
    , @NamedQuery(name = "RnGcTrxlineasTbl.findById", query = "SELECT r FROM RnGcTrxlineasTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcTrxlineasTbl.findByClaveProdServ", query = "SELECT r FROM RnGcTrxlineasTbl r WHERE r.claveProdServ = :claveProdServ")
    , @NamedQuery(name = "RnGcTrxlineasTbl.findByNoIdentificacion", query = "SELECT r FROM RnGcTrxlineasTbl r WHERE r.noIdentificacion = :noIdentificacion")
    , @NamedQuery(name = "RnGcTrxlineasTbl.findByCantidad", query = "SELECT r FROM RnGcTrxlineasTbl r WHERE r.cantidad = :cantidad")
    , @NamedQuery(name = "RnGcTrxlineasTbl.findByClaveUnidad", query = "SELECT r FROM RnGcTrxlineasTbl r WHERE r.claveUnidad = :claveUnidad")
    , @NamedQuery(name = "RnGcTrxlineasTbl.findByUnidad", query = "SELECT r FROM RnGcTrxlineasTbl r WHERE r.unidad = :unidad")
    , @NamedQuery(name = "RnGcTrxlineasTbl.findByDescripcion", query = "SELECT r FROM RnGcTrxlineasTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcTrxlineasTbl.findByValorUnit", query = "SELECT r FROM RnGcTrxlineasTbl r WHERE r.valorUnit = :valorUnit")
    , @NamedQuery(name = "RnGcTrxlineasTbl.findByImporte", query = "SELECT r FROM RnGcTrxlineasTbl r WHERE r.importe = :importe")
    , @NamedQuery(name = "RnGcTrxlineasTbl.findByBase", query = "SELECT r FROM RnGcTrxlineasTbl r WHERE r.base = :base")
    , @NamedQuery(name = "RnGcTrxlineasTbl.findByImpuesto", query = "SELECT r FROM RnGcTrxlineasTbl r WHERE r.impuesto = :impuesto")
    , @NamedQuery(name = "RnGcTrxlineasTbl.findByTipoFactor", query = "SELECT r FROM RnGcTrxlineasTbl r WHERE r.tipoFactor = :tipoFactor")
    , @NamedQuery(name = "RnGcTrxlineasTbl.findByTipoTasa", query = "SELECT r FROM RnGcTrxlineasTbl r WHERE r.tipoTasa = :tipoTasa")
    , @NamedQuery(name = "RnGcTrxlineasTbl.findByImporteImpuesto", query = "SELECT r FROM RnGcTrxlineasTbl r WHERE r.importeImpuesto = :importeImpuesto")
    , @NamedQuery(name = "RnGcTrxlineasTbl.findByCreadoPor", query = "SELECT r FROM RnGcTrxlineasTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcTrxlineasTbl.findByFechaCreacion", query = "SELECT r FROM RnGcTrxlineasTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcTrxlineasTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcTrxlineasTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcTrxlineasTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcTrxlineasTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcTrxlineasTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ClaveProdServ")
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
    @Size(min = 1, max = 15)
    @Column(name = "ClaveUnidad")
    private String claveUnidad;
    @Size(max = 25)
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
    @JoinColumn(name = "transaccionesId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private RnGcTransaccionesTbl transaccionesId;

    public RnGcTrxlineasTbl() {
    }

    public RnGcTrxlineasTbl(Integer id) {
        this.id = id;
    }

    public RnGcTrxlineasTbl(Integer id, int claveProdServ, float cantidad, String claveUnidad, String descripcion, float valorUnit, float importe, float base, int impuesto, String tipoFactor, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
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

    public RnGcTransaccionesTbl getTransaccionesId() {
        return transaccionesId;
    }

    public void setTransaccionesId(RnGcTransaccionesTbl transaccionesId) {
        this.transaccionesId = transaccionesId;
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
        if (!(object instanceof RnGcTrxlineasTbl)) {
            return false;
        }
        RnGcTrxlineasTbl other = (RnGcTrxlineasTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcTrxlineasTbl[ id=" + id + " ]";
    }
    
}
