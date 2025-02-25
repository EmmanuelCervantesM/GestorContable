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
@Table(name = "rn_gc_pagos_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcPagosTbl.findAll", query = "SELECT r FROM RnGcPagosTbl r")
    , @NamedQuery(name = "RnGcPagosTbl.findById", query = "SELECT r FROM RnGcPagosTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcPagosTbl.findByNoPago", query = "SELECT r FROM RnGcPagosTbl r WHERE r.noPago = :noPago")
    , @NamedQuery(name = "RnGcPagosTbl.findByNombreEmisor", query = "SELECT r FROM RnGcPagosTbl r WHERE r.nombreEmisor = :nombreEmisor")
    , @NamedQuery(name = "RnGcPagosTbl.findByRFCEmisor", query = "SELECT r FROM RnGcPagosTbl r WHERE r.rFCEmisor = :rFCEmisor")
    , @NamedQuery(name = "RnGcPagosTbl.findByTipoCopmprobante", query = "SELECT r FROM RnGcPagosTbl r WHERE r.tipoCopmprobante = :tipoCopmprobante")
    , @NamedQuery(name = "RnGcPagosTbl.findByLugarExpedicion", query = "SELECT r FROM RnGcPagosTbl r WHERE r.lugarExpedicion = :lugarExpedicion")
    , @NamedQuery(name = "RnGcPagosTbl.findByFechaExpedicion", query = "SELECT r FROM RnGcPagosTbl r WHERE r.fechaExpedicion = :fechaExpedicion")
    , @NamedQuery(name = "RnGcPagosTbl.findByHoraExpedicion", query = "SELECT r FROM RnGcPagosTbl r WHERE r.horaExpedicion = :horaExpedicion")
    , @NamedQuery(name = "RnGcPagosTbl.findByMoneda", query = "SELECT r FROM RnGcPagosTbl r WHERE r.moneda = :moneda")
    , @NamedQuery(name = "RnGcPagosTbl.findByRFCReceptor", query = "SELECT r FROM RnGcPagosTbl r WHERE r.rFCReceptor = :rFCReceptor")
    , @NamedQuery(name = "RnGcPagosTbl.findByUsoCFDI", query = "SELECT r FROM RnGcPagosTbl r WHERE r.usoCFDI = :usoCFDI")
    , @NamedQuery(name = "RnGcPagosTbl.findByImporte", query = "SELECT r FROM RnGcPagosTbl r WHERE r.importe = :importe")
    , @NamedQuery(name = "RnGcPagosTbl.findByImpuesto", query = "SELECT r FROM RnGcPagosTbl r WHERE r.impuesto = :impuesto")
    , @NamedQuery(name = "RnGcPagosTbl.findBySubtotal", query = "SELECT r FROM RnGcPagosTbl r WHERE r.subtotal = :subtotal")
    , @NamedQuery(name = "RnGcPagosTbl.findByTotalImpuesto", query = "SELECT r FROM RnGcPagosTbl r WHERE r.totalImpuesto = :totalImpuesto")
    , @NamedQuery(name = "RnGcPagosTbl.findByTotal", query = "SELECT r FROM RnGcPagosTbl r WHERE r.total = :total")
    , @NamedQuery(name = "RnGcPagosTbl.findByCreadoPor", query = "SELECT r FROM RnGcPagosTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcPagosTbl.findByFechaCreacion", query = "SELECT r FROM RnGcPagosTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcPagosTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcPagosTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcPagosTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcPagosTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcPagosTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "noPago")
    private int noPago;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombreEmisor")
    private String nombreEmisor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "RFCEmisor")
    private String rFCEmisor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "tipoCopmprobante")
    private String tipoCopmprobante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "lugarExpedicion")
    private String lugarExpedicion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaExpedicion")
    @Temporal(TemporalType.DATE)
    private Date fechaExpedicion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "horaExpedicion")
    @Temporal(TemporalType.TIME)
    private Date horaExpedicion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "Moneda")
    private String moneda;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "RFCReceptor")
    private String rFCReceptor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "usoCFDI")
    private String usoCFDI;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Importe")
    private float importe;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Impuesto")
    private int impuesto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Subtotal")
    private float subtotal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "totalImpuesto")
    private float totalImpuesto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Total")
    private float total;
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
    @JoinColumn(name = "facturas_Id", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private RnGcFacturasTbl facturasId;

    public RnGcPagosTbl() {
    }

    public RnGcPagosTbl(Integer id) {
        this.id = id;
    }

    public RnGcPagosTbl(Integer id, int noPago, String nombreEmisor, String rFCEmisor, String tipoCopmprobante, String lugarExpedicion, Date fechaExpedicion, Date horaExpedicion, String moneda, String rFCReceptor, String usoCFDI, float importe, int impuesto, float subtotal, float totalImpuesto, float total, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.noPago = noPago;
        this.nombreEmisor = nombreEmisor;
        this.rFCEmisor = rFCEmisor;
        this.tipoCopmprobante = tipoCopmprobante;
        this.lugarExpedicion = lugarExpedicion;
        this.fechaExpedicion = fechaExpedicion;
        this.horaExpedicion = horaExpedicion;
        this.moneda = moneda;
        this.rFCReceptor = rFCReceptor;
        this.usoCFDI = usoCFDI;
        this.importe = importe;
        this.impuesto = impuesto;
        this.subtotal = subtotal;
        this.totalImpuesto = totalImpuesto;
        this.total = total;
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

    public int getNoPago() {
        return noPago;
    }

    public void setNoPago(int noPago) {
        this.noPago = noPago;
    }

    public String getNombreEmisor() {
        return nombreEmisor;
    }

    public void setNombreEmisor(String nombreEmisor) {
        this.nombreEmisor = nombreEmisor;
    }

    public String getRFCEmisor() {
        return rFCEmisor;
    }

    public void setRFCEmisor(String rFCEmisor) {
        this.rFCEmisor = rFCEmisor;
    }

    public String getTipoCopmprobante() {
        return tipoCopmprobante;
    }

    public void setTipoCopmprobante(String tipoCopmprobante) {
        this.tipoCopmprobante = tipoCopmprobante;
    }

    public String getLugarExpedicion() {
        return lugarExpedicion;
    }

    public void setLugarExpedicion(String lugarExpedicion) {
        this.lugarExpedicion = lugarExpedicion;
    }

    public Date getFechaExpedicion() {
        return fechaExpedicion;
    }

    public void setFechaExpedicion(Date fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    public Date getHoraExpedicion() {
        return horaExpedicion;
    }

    public void setHoraExpedicion(Date horaExpedicion) {
        this.horaExpedicion = horaExpedicion;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getRFCReceptor() {
        return rFCReceptor;
    }

    public void setRFCReceptor(String rFCReceptor) {
        this.rFCReceptor = rFCReceptor;
    }

    public String getUsoCFDI() {
        return usoCFDI;
    }

    public void setUsoCFDI(String usoCFDI) {
        this.usoCFDI = usoCFDI;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

    public int getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(int impuesto) {
        this.impuesto = impuesto;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getTotalImpuesto() {
        return totalImpuesto;
    }

    public void setTotalImpuesto(float totalImpuesto) {
        this.totalImpuesto = totalImpuesto;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
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

    public RnGcFacturasTbl getFacturasId() {
        return facturasId;
    }

    public void setFacturasId(RnGcFacturasTbl facturasId) {
        this.facturasId = facturasId;
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
        if (!(object instanceof RnGcPagosTbl)) {
            return false;
        }
        RnGcPagosTbl other = (RnGcPagosTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcPagosTbl[ id=" + id + " ]";
    }
    
}
