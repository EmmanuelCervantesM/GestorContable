/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
@Table(name = "rn_gc_pagos_relacionados_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcPagosRelacionadosTbl.findAll", query = "SELECT r FROM RnGcPagosRelacionadosTbl r")
    , @NamedQuery(name = "RnGcPagosRelacionadosTbl.findById", query = "SELECT r FROM RnGcPagosRelacionadosTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcPagosRelacionadosTbl.findByIdDocumento", query = "SELECT r FROM RnGcPagosRelacionadosTbl r WHERE r.idDocumento = :idDocumento")
    , @NamedQuery(name = "RnGcPagosRelacionadosTbl.findBySerie", query = "SELECT r FROM RnGcPagosRelacionadosTbl r WHERE r.serie = :serie")
    , @NamedQuery(name = "RnGcPagosRelacionadosTbl.findByFolio", query = "SELECT r FROM RnGcPagosRelacionadosTbl r WHERE r.folio = :folio")
    , @NamedQuery(name = "RnGcPagosRelacionadosTbl.findByMoneda", query = "SELECT r FROM RnGcPagosRelacionadosTbl r WHERE r.moneda = :moneda")
    , @NamedQuery(name = "RnGcPagosRelacionadosTbl.findByTipoCambio", query = "SELECT r FROM RnGcPagosRelacionadosTbl r WHERE r.tipoCambio = :tipoCambio")
    , @NamedQuery(name = "RnGcPagosRelacionadosTbl.findByMetodoPago", query = "SELECT r FROM RnGcPagosRelacionadosTbl r WHERE r.metodoPago = :metodoPago")
    , @NamedQuery(name = "RnGcPagosRelacionadosTbl.findByNumeroParcialidad", query = "SELECT r FROM RnGcPagosRelacionadosTbl r WHERE r.numeroParcialidad = :numeroParcialidad")
    , @NamedQuery(name = "RnGcPagosRelacionadosTbl.findByImporteSaldoAnterior", query = "SELECT r FROM RnGcPagosRelacionadosTbl r WHERE r.importeSaldoAnterior = :importeSaldoAnterior")
    , @NamedQuery(name = "RnGcPagosRelacionadosTbl.findByImportePagado", query = "SELECT r FROM RnGcPagosRelacionadosTbl r WHERE r.importePagado = :importePagado")
    , @NamedQuery(name = "RnGcPagosRelacionadosTbl.findByImporteInsoluto", query = "SELECT r FROM RnGcPagosRelacionadosTbl r WHERE r.importeInsoluto = :importeInsoluto")
    , @NamedQuery(name = "RnGcPagosRelacionadosTbl.findByCreadoPor", query = "SELECT r FROM RnGcPagosRelacionadosTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcPagosRelacionadosTbl.findByFechaCreacion", query = "SELECT r FROM RnGcPagosRelacionadosTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcPagosRelacionadosTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcPagosRelacionadosTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcPagosRelacionadosTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcPagosRelacionadosTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcPagosRelacionadosTbl.findByCfdisId", query = "SELECT r FROM RnGcPagosRelacionadosTbl r WHERE r.cfdisId = :cfdisId")})
public class RnGcPagosRelacionadosTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Size(max = 45)
    private String idDocumento;
    @Size(max = 45)
    private String serie;
    @Size(max = 45)
    private String folio;
    @Size(max = 45)
    private String moneda;
    private Double tipoCambio;
    @Size(max = 45)
    private String metodoPago;
    private Integer numeroParcialidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double importeSaldoAnterior;
    private Double importePagado;
    private Double importeInsoluto;
    private Double cantidadPagada;
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
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaFechaActualizacion;
    @JoinColumn(name = "cfdis_Id", referencedColumnName = "Id")
    @ManyToOne
    private RnGcCfdisTbl cfdisId;

    public RnGcPagosRelacionadosTbl() {
    }

    public RnGcPagosRelacionadosTbl(Integer id) {
        this.id = id;
    }

    public RnGcPagosRelacionadosTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
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

    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Double getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(Double tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Integer getNumeroParcialidad() {
        return numeroParcialidad;
    }

    public void setNumeroParcialidad(Integer numeroParcialidad) {
        this.numeroParcialidad = numeroParcialidad;
    }

    public Double getImporteSaldoAnterior() {
        return importeSaldoAnterior;
    }

    public void setImporteSaldoAnterior(Double importeSaldoAnterior) {
        this.importeSaldoAnterior = importeSaldoAnterior;
    }

    public Double getImportePagado() {
        return importePagado;
    }

    public void setImportePagado(Double importePagado) {
        this.importePagado = importePagado;
    }

    public Double getImporteInsoluto() {
        return importeInsoluto;
    }

    public void setImporteInsoluto(Double importeInsoluto) {
        this.importeInsoluto = importeInsoluto;
    }

    public Double getCantidadPagada() {
        return cantidadPagada;
    }

    public void setCantidadPagada(Double cantidadPagada) {
        this.cantidadPagada = cantidadPagada;
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

    public RnGcCfdisTbl getCfdisId() {
        return cfdisId;
    }

    public void setCfdisId(RnGcCfdisTbl cfdisId) {
        this.cfdisId = cfdisId;
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
        if (!(object instanceof RnGcPagosRelacionadosTbl)) {
            return false;
        }
        RnGcPagosRelacionadosTbl other = (RnGcPagosRelacionadosTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcPagosRelacionadosTbl[ id=" + id + " ]";
    }
    
}
