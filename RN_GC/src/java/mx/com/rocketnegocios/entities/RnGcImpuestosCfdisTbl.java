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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Developer1
 */
@Entity
@Table(name = "rn_gc_impuestos_cfdis_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcImpuestosCfdisTbl.findAll", query = "SELECT r FROM RnGcImpuestosCfdisTbl r")
    , @NamedQuery(name = "RnGcImpuestosCfdisTbl.findById", query = "SELECT r FROM RnGcImpuestosCfdisTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcImpuestosCfdisTbl.findByCreadoPor", query = "SELECT r FROM RnGcImpuestosCfdisTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcImpuestosCfdisTbl.findByFechaCreacion", query = "SELECT r FROM RnGcImpuestosCfdisTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcImpuestosCfdisTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcImpuestosCfdisTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcImpuestosCfdisTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcImpuestosCfdisTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcImpuestosCfdisTbl.findByCfdiLinea", query = "SELECT r FROM RnGcImpuestosCfdisTbl r WHERE r.cfdisLineasId = :cfdisLineasId")})
public class RnGcImpuestosCfdisTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
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
    @Column(name = "importe")
    private Double importe;
    @JoinColumn(name = "cfdisLineas_Id", referencedColumnName = "Id")
    @ManyToOne(optional = true)
    private RnGcCfdisLineasTbl cfdisLineasId;
    @JoinColumn(name = "impuestosLocales_Id", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private RnGcImpuestosLocalesTbl impuestosLocalesId;

    public RnGcImpuestosCfdisTbl() {
    }

    public RnGcImpuestosCfdisTbl(Integer id) {
        this.id = id;
    }

    public RnGcImpuestosCfdisTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
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

    public RnGcCfdisLineasTbl getCfdisLineasId() {
        return cfdisLineasId;
    }

    public void setCfdisLineasId(RnGcCfdisLineasTbl cfdisLineasId) {
        this.cfdisLineasId = cfdisLineasId;
    }

    public RnGcImpuestosLocalesTbl getImpuestosLocalesId() {
        if(impuestosLocalesId == null) {
            this.impuestosLocalesId = new RnGcImpuestosLocalesTbl();
        }
        return impuestosLocalesId;
    }

    public void setImpuestosLocalesId(RnGcImpuestosLocalesTbl impuestosLocalesId) {
        this.impuestosLocalesId = impuestosLocalesId;
    }

    public Double getImporte() {
        if(importe == null) {
            this.importe = 0.0;
        }
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
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
        if (!(object instanceof RnGcImpuestosCfdisTbl)) {
            return false;
        }
        RnGcImpuestosCfdisTbl other = (RnGcImpuestosCfdisTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcImpuestosCfdisTbl[ id=" + id + " ]";
    }
    
}
