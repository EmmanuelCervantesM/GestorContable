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
@Table(name = "rn_gc_impuestos_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcImpuestosTbl.findAll", query = "SELECT r FROM RnGcImpuestosTbl r")
    , @NamedQuery(name = "RnGcImpuestosTbl.findByCreadoPor", query = "SELECT r FROM RnGcImpuestosTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcImpuestosTbl.findByFechaCreacion", query = "SELECT r FROM RnGcImpuestosTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcImpuestosTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcImpuestosTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcImpuestosTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcImpuestosTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcImpuestosTbl.findById", query = "SELECT r FROM RnGcImpuestosTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcImpuestosTbl.findByCImpuesto", query = "SELECT r FROM RnGcImpuestosTbl r WHERE r.cImpuesto = :cImpuesto")
    , @NamedQuery(name = "RnGcImpuestosTbl.findByDescripcion", query = "SELECT r FROM RnGcImpuestosTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcImpuestosTbl.findByRetencion", query = "SELECT r FROM RnGcImpuestosTbl r WHERE r.retencion = :retencion")
    , @NamedQuery(name = "RnGcImpuestosTbl.findByTraslado", query = "SELECT r FROM RnGcImpuestosTbl r WHERE r.traslado = :traslado")
    , @NamedQuery(name = "RnGcImpuestosTbl.findByLocalFederal", query = "SELECT r FROM RnGcImpuestosTbl r WHERE r.localFederal = :localFederal")})
public class RnGcImpuestosTbl implements Serializable {

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
    @Size(min = 1, max = 10)
    @Column(name = "c_Impuesto")
    private String cImpuesto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "Descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "Retencion")
    private String retencion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "traslado")
    private String traslado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "localFederal")
    private String localFederal;
    @JoinColumn(name = "cfdis_Id", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private RnGcCfdisTbl cfdisId;

    public RnGcImpuestosTbl() {
    }

    public RnGcImpuestosTbl(Integer id) {
        this.id = id;
    }

    public RnGcImpuestosTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion, String cImpuesto, String descripcion, String retencion, String traslado, String localFederal) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
        this.cImpuesto = cImpuesto;
        this.descripcion = descripcion;
        this.retencion = retencion;
        this.traslado = traslado;
        this.localFederal = localFederal;
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

    public String getCImpuesto() {
        return cImpuesto;
    }

    public void setCImpuesto(String cImpuesto) {
        this.cImpuesto = cImpuesto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRetencion() {
        return retencion;
    }

    public void setRetencion(String retencion) {
        this.retencion = retencion;
    }

    public String getTraslado() {
        return traslado;
    }

    public void setTraslado(String traslado) {
        this.traslado = traslado;
    }

    public String getLocalFederal() {
        return localFederal;
    }

    public void setLocalFederal(String localFederal) {
        this.localFederal = localFederal;
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
        if (!(object instanceof RnGcImpuestosTbl)) {
            return false;
        }
        RnGcImpuestosTbl other = (RnGcImpuestosTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcImpuestosTbl[ id=" + id + " ]";
    }
    
}
