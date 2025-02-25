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
@Table(name = "rn_gc_tiporelacion_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcTiporelacionTbl.findAll", query = "SELECT r FROM RnGcTiporelacionTbl r")
    , @NamedQuery(name = "RnGcTiporelacionTbl.findByCreadoPor", query = "SELECT r FROM RnGcTiporelacionTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcTiporelacionTbl.findByFechaCreacion", query = "SELECT r FROM RnGcTiporelacionTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcTiporelacionTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcTiporelacionTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcTiporelacionTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcTiporelacionTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcTiporelacionTbl.findById", query = "SELECT r FROM RnGcTiporelacionTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcTiporelacionTbl.findByClaveTipoRelacion", query = "SELECT r FROM RnGcTiporelacionTbl r WHERE r.claveTipoRelacion = :claveTipoRelacion")
    , @NamedQuery(name = "RnGcTiporelacionTbl.findByDescripcion", query = "SELECT r FROM RnGcTiporelacionTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcTiporelacionTbl.findByInicioVigencia", query = "SELECT r FROM RnGcTiporelacionTbl r WHERE r.inicioVigencia = :inicioVigencia")
    , @NamedQuery(name = "RnGcTiporelacionTbl.findByFinVigencia", query = "SELECT r FROM RnGcTiporelacionTbl r WHERE r.finVigencia = :finVigencia")})
public class RnGcTiporelacionTbl implements Serializable {

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
    @Column(name = "claveTipoRelacion")
    private String claveTipoRelacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "inicioVigencia")
    @Temporal(TemporalType.DATE)
    private Date inicioVigencia;
    @Column(name = "finVigencia")
    @Temporal(TemporalType.DATE)
    private Date finVigencia;
    @JoinColumn(name = "cfdi_Id", referencedColumnName = "Id")
    @ManyToOne
    private RnGcCfdisTbl cfdiId;

    public RnGcTiporelacionTbl() {
    }

    public RnGcTiporelacionTbl(Integer id) {
        this.id = id;
    }

    public RnGcTiporelacionTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion, String claveTipoRelacion, String descripcion) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
        this.claveTipoRelacion = claveTipoRelacion;
        this.descripcion = descripcion;
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

    public String getClaveTipoRelacion() {
        return claveTipoRelacion;
    }

    public void setClaveTipoRelacion(String claveTipoRelacion) {
        this.claveTipoRelacion = claveTipoRelacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getInicioVigencia() {
        return inicioVigencia;
    }

    public void setInicioVigencia(Date inicioVigencia) {
        this.inicioVigencia = inicioVigencia;
    }

    public Date getFinVigencia() {
        return finVigencia;
    }

    public void setFinVigencia(Date finVigencia) {
        this.finVigencia = finVigencia;
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
        if (!(object instanceof RnGcTiporelacionTbl)) {
            return false;
        }
        RnGcTiporelacionTbl other = (RnGcTiporelacionTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcTiporelacionTbl[ id=" + id + " ]";
    }
    
}
