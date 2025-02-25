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
@Table(name = "rn_gc_catalogosconceptos_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcCatalogosconceptosTbl.findAll", query = "SELECT r FROM RnGcCatalogosconceptosTbl r")
    , @NamedQuery(name = "RnGcCatalogosconceptosTbl.findById", query = "SELECT r FROM RnGcCatalogosconceptosTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcCatalogosconceptosTbl.findByCantidad", query = "SELECT r FROM RnGcCatalogosconceptosTbl r WHERE r.cantidad = :cantidad")
    , @NamedQuery(name = "RnGcCatalogosconceptosTbl.findByUnidad", query = "SELECT r FROM RnGcCatalogosconceptosTbl r WHERE r.unidad = :unidad")
    , @NamedQuery(name = "RnGcCatalogosconceptosTbl.findByDescripcion", query = "SELECT r FROM RnGcCatalogosconceptosTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcCatalogosconceptosTbl.findByValorUnitario", query = "SELECT r FROM RnGcCatalogosconceptosTbl r WHERE r.valorUnitario = :valorUnitario")
    , @NamedQuery(name = "RnGcCatalogosconceptosTbl.findByCreadoPor", query = "SELECT r FROM RnGcCatalogosconceptosTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCatalogosconceptosTbl.findByFechaCreacion", query = "SELECT r FROM RnGcCatalogosconceptosTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcCatalogosconceptosTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcCatalogosconceptosTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcCatalogosconceptosTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcCatalogosconceptosTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcCatalogosconceptosTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Cantidad")
    private int cantidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Unidad")
    private int unidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valorUnitario")
    private int valorUnitario;
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
    @JoinColumn(name = "cfdis_Id", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private RnGcCfdisTbl cfdisId;

    public RnGcCatalogosconceptosTbl() {
    }

    public RnGcCatalogosconceptosTbl(Integer id) {
        this.id = id;
    }

    public RnGcCatalogosconceptosTbl(Integer id, int cantidad, int unidad, String descripcion, int valorUnitario, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.descripcion = descripcion;
        this.valorUnitario = valorUnitario;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getUnidad() {
        return unidad;
    }

    public void setUnidad(int unidad) {
        this.unidad = unidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(int valorUnitario) {
        this.valorUnitario = valorUnitario;
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
        if (!(object instanceof RnGcCatalogosconceptosTbl)) {
            return false;
        }
        RnGcCatalogosconceptosTbl other = (RnGcCatalogosconceptosTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcCatalogosconceptosTbl[ id=" + id + " ]";
    }
    
}
