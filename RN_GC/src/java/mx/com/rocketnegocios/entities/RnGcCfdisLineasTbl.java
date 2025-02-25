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
@Table(name = "rn_gc_cfdis_lineas_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcCfdisLineasTbl.findAll", query = "SELECT r FROM RnGcCfdisLineasTbl r")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findById", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByClaveProdServ", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.claveProdServ = :claveProdServ")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByNoIdentificacion", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.noIdentificacion = :noIdentificacion")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByCantidad", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.cantidad = :cantidad")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByClaveUnidad", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.claveUnidad = :claveUnidad")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByUnidad", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.unidad = :unidad")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByDescripcion", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByValorUnit", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.valorUnit = :valorUnit")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByImporte", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.importe = :importe")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByBase", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.base = :base")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByTipoImpuesto", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.tipoImpuesto = :tipoImpuesto")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByImpuesto", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.impuesto = :impuesto")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByTipoFactor", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.tipoFactor = :tipoFactor")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByTipoTasa", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.tipoTasa = :tipoTasa")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByTipoImpuesto2", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.tipoImpuesto2 = :tipoImpuesto2")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByImpuesto2", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.impuesto2 = :impuesto2")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByTipoTasa2", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.tipoTasa2 = :tipoTasa2")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByTipoFactor2", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.tipoFactor2 = :tipoFactor2")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByTipoImpuesto3", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.tipoImpuesto3 = :tipoImpuesto3")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByImpuesto3", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.impuesto3 = :impuesto3")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByTipoFactor3", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.tipoFactor3 = :tipoFactor3")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByTipoTasa3", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.tipoTasa3 = :tipoTasa3")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByTipoImpuesto4", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.tipoImpuesto4 = :tipoImpuesto4")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByImpuesto4", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.impuesto4 = :impuesto4")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByTipoFactor4", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.tipoFactor4 = :tipoFactor4")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByTipoTasa4", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.tipoTasa4 = :tipoTasa4")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByImporteimpuesto", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.importeimpuesto = :importeimpuesto")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByImporteImpuesto2", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.importeImpuesto2 = :importeImpuesto2")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByImporteImpuesto3", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.importeImpuesto3 = :importeImpuesto3")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByImporteImpuesto4", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.importeImpuesto4 = :importeImpuesto4")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByCreadoPor", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByFechaCreacion", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcCfdisLineasTbl.findByCfdis_Id", query = "SELECT r FROM RnGcCfdisLineasTbl r WHERE r.cfdisId = :cfdisId")})
public class RnGcCfdisLineasTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    private String claveProdServ;
    @Size(max = 25)
    private String noIdentificacion;
    @Basic(optional = false)
    @NotNull
    private double cantidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    private String claveUnidad;
    @Size(max = 45)
    private String unidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    private double valorUnit;
    @Basic(optional = false)
    @NotNull
    private double importe;
    @Basic(optional = false)
    @NotNull
    private double base;
    @Size(max = 45)
    private String tipoImpuesto;
    private String impuesto;
    @Size(min = 1, max = 15)
    private String tipoFactor;
    private double tipoTasa;
    @Size(max = 45)
    private String tipoImpuesto2;
    private String impuesto2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double tipoTasa2;
    @Size(max = 45)
    private String tipoFactor2;
    @Size(max = 45)
    private String tipoImpuesto3;
    private String impuesto3;
    @Size(max = 45)
    private String tipoFactor3;
    private Double tipoTasa3;
    @Size(max = 45)
    private String tipoImpuesto4;
    private String impuesto4;
    @Size(max = 45)
    private String tipoFactor4;
    private Double tipoTasa4;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaFechaActualizacion;
    private Double importeimpuesto;
    private Double importeImpuesto2;
    private Double importeImpuesto3;
    private Double importeImpuesto4;
    private Double descuento;
    @Basic(optional = false)
    @NotNull
    private int creadoPor;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    private int ultimaActualizacionPor;
    @JoinColumn(name = "cfdis_Id", referencedColumnName = "Id")
    @ManyToOne(optional = true)
    private RnGcCfdisTbl cfdisId;
    @Column(name = "noPedimento")
    private String noPedimento;
    @Column(name = "noCuentaPredial")
    private String noCuentaPredial;
    @Column(name = "peso")
    private double pesoUnitario;
    @JoinColumn(name = "productoId", referencedColumnName = "Id")
    @ManyToOne(optional = true)
    private RnGcProductserviciosTbl productoId;
    @Column(name = "peligroso")
    private String peligroso;

    public RnGcCfdisLineasTbl() {
    }

    public RnGcCfdisLineasTbl(Integer id) {
        this.id = id;
    }

    public RnGcCfdisLineasTbl(Integer id, String claveProdServ, double cantidad, String claveUnidad, String descripcion, double valorUnit, double importe, double base, Date ultimaFechaActualizacion, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor) {
        this.id = id;
        this.claveProdServ = claveProdServ;
        this.cantidad = cantidad;
        this.claveUnidad = claveUnidad;
        this.descripcion = descripcion;
        this.valorUnit = valorUnit;
        this.importe = importe;
        this.base = base;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClaveProdServ() {
        if(claveProdServ == null) {
            this.claveProdServ = "-";
        }
        return claveProdServ;
    }

    public void setClaveProdServ(String claveProdServ) {
        this.claveProdServ = claveProdServ;
    }

    public String getNoIdentificacion() {
        return noIdentificacion;
    }

    public void setNoIdentificacion(String noIdentificacion) {
        this.noIdentificacion = noIdentificacion;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getClaveUnidad() {
        if(claveUnidad == null) {
            this.claveUnidad = "-";
        }
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
        if(descripcion == null) {
            this.descripcion ="-";
        }
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getValorUnit() {
        return valorUnit;
    }

    public void setValorUnit(double valorUnit) {
        this.valorUnit = valorUnit;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getBase() {
        return base;
    }

    public void setBase(double base) {
        this.base = base;
    }

    public Double getDescuento() {
        if(descuento == null) {
            this.descuento = 0.0;
        }
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public String getTipoImpuesto() {
        return tipoImpuesto;
    }

    public void setTipoImpuesto(String tipoImpuesto) {
        this.tipoImpuesto = tipoImpuesto;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public String getTipoFactor() {
        return tipoFactor;
    }

    public void setTipoFactor(String tipoFactor) {
        this.tipoFactor = tipoFactor;
    }

    public Double getTipoTasa() {
        return tipoTasa;
    }

    public void setTipoTasa(Double tipoTasa) {
        this.tipoTasa = tipoTasa;
    }

    public String getTipoImpuesto2() {
        return tipoImpuesto2;
    }

    public void setTipoImpuesto2(String tipoImpuesto2) {
        this.tipoImpuesto2 = tipoImpuesto2;
    }

    public String getImpuesto2() {
        return impuesto2;
    }

    public void setImpuesto2(String impuesto2) {
        this.impuesto2 = impuesto2;
    }

    public Double getTipoTasa2() {
        return tipoTasa2;
    }

    public void setTipoTasa2(Double tipoTasa2) {
        this.tipoTasa2 = tipoTasa2;
    }

    public String getTipoFactor2() {
        return tipoFactor2;
    }

    public void setTipoFactor2(String tipoFactor2) {
        this.tipoFactor2 = tipoFactor2;
    }

    public String getTipoImpuesto3() {
        return tipoImpuesto3;
    }

    public void setTipoImpuesto3(String tipoImpuesto3) {
        this.tipoImpuesto3 = tipoImpuesto3;
    }

    public String getImpuesto3() {
        return impuesto3;
    }

    public void setImpuesto3(String impuesto3) {
        this.impuesto3 = impuesto3;
    }

    public String getTipoFactor3() {
        return tipoFactor3;
    }

    public void setTipoFactor3(String tipoFactor3) {
        this.tipoFactor3 = tipoFactor3;
    }

    public Double getTipoTasa3() {
        return tipoTasa3;
    }

    public void setTipoTasa3(Double tipoTasa3) {
        this.tipoTasa3 = tipoTasa3;
    }

    public String getTipoImpuesto4() {
        return tipoImpuesto4;
    }

    public void setTipoImpuesto4(String tipoImpuesto4) {
        this.tipoImpuesto4 = tipoImpuesto4;
    }

    public String getImpuesto4() {
        return impuesto4;
    }

    public void setImpuesto4(String impuesto4) {
        this.impuesto4 = impuesto4;
    }

    public String getTipoFactor4() {
        return tipoFactor4;
    }

    public void setTipoFactor4(String tipoFactor4) {
        this.tipoFactor4 = tipoFactor4;
    }

    public Double getTipoTasa4() {
        return tipoTasa4;
    }

    public void setTipoTasa4(Double tipoTasa4) {
        this.tipoTasa4 = tipoTasa4;
    }

    public Date getUltimaFechaActualizacion() {
        return ultimaFechaActualizacion;
    }

    public void setUltimaFechaActualizacion(Date ultimaFechaActualizacion) {
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
    }

    public Double getImporteimpuesto() {
        if(importeimpuesto == null) {
            this.importeimpuesto = 0.0;
        }
        return importeimpuesto;
    }

    public void setImporteimpuesto(Double importeimpuesto) {
        this.importeimpuesto = importeimpuesto;
    }

    public Double getImporteImpuesto2() {
        if(importeImpuesto2 == null) {
            this.importeImpuesto2 = 0.0;
        }
        return importeImpuesto2;
    }

    public void setImporteImpuesto2(Double importeImpuesto2) {
        this.importeImpuesto2 = importeImpuesto2;
    }

    public Double getImporteImpuesto3() {
        if(importeImpuesto3 == null) {
            this.importeImpuesto3 = 0.0;
        }
        return importeImpuesto3;
    }

    public void setImporteImpuesto3(Double importeImpuesto3) {
        this.importeImpuesto3 = importeImpuesto3;
    }

    public Double getImporteImpuesto4() {
        if(importeImpuesto4 == null) {
            this.importeImpuesto4 = 0.0;
        }
        return importeImpuesto4;
    }

    public void setImporteImpuesto4(Double importeImpuesto4) {
        this.importeImpuesto4 = importeImpuesto4;
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

    public RnGcCfdisTbl getCfdisId() {
        return cfdisId;
    }

    public void setCfdisId(RnGcCfdisTbl cfdisId) {
        this.cfdisId = cfdisId;
    }

    public String getNoCuentaPredial() {
        return noCuentaPredial;
    }

    public void setNoCuentaPredial(String noCuentaPredial) {
        this.noCuentaPredial = noCuentaPredial;
    }

    public String getNoPedimento() {
        return noPedimento;
    }

    public void setNoPedimento(String noPedimento) {
        this.noPedimento = noPedimento;
    }
    
    public double getPesoUnitario() {
        return pesoUnitario;
    }

    public void setPesoUnitario(double pesoUnitario) {
        this.pesoUnitario = pesoUnitario;
    }

    public RnGcProductserviciosTbl getProductoId() {
        return productoId;
    }

    public void setProductoId(RnGcProductserviciosTbl productoId) {
        this.productoId = productoId;
    }

    public String getPeligroso() {
        return peligroso;
    }

    public void setPeligroso(String peligroso) {
        this.peligroso = peligroso;
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
        if (!(object instanceof RnGcCfdisLineasTbl)) {
            return false;
        }
        RnGcCfdisLineasTbl other = (RnGcCfdisLineasTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcCfdisLineasTbl[ id=" + id + " ]";
    }

}
