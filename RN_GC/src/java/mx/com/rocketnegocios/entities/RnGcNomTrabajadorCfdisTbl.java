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
 * @author LenovoZ40
 */
@Entity
@Table(name = "rn_gc_nom_trabajador_cfdis_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcNomTrabajadorCfdisTbl.findAll", query = "SELECT r FROM RnGcNomTrabajadorCfdisTbl r")
    , @NamedQuery(name = "RnGcNomTrabajadorCfdisTbl.findById", query = "SELECT r FROM RnGcNomTrabajadorCfdisTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcNomTrabajadorCfdisTbl.findBysolicitudTrabajdorId", query = "SELECT r FROM RnGcNomTrabajadorCfdisTbl r WHERE r.solicitudTrabajdorId = :solicitudTrabajdorId")
    , @NamedQuery(name = "RnGcNomTrabajadorCfdisTbl.findByCreadoPor", query = "SELECT r FROM RnGcNomTrabajadorCfdisTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcNomTrabajadorCfdisTbl.findByFechaCreacion", query = "SELECT r FROM RnGcNomTrabajadorCfdisTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcNomTrabajadorCfdisTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcNomTrabajadorCfdisTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcNomTrabajadorCfdisTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcNomTrabajadorCfdisTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcNomTrabajadorCfdisTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
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
    @JoinColumn(name = "solicitudTrabajadorId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RnGcNomSolicitudTrabajadorTbl solicitudTrabajdorId;
    @JoinColumn(name = "cfdiId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private RnGcCfdisTbl cfdiId;

    public RnGcNomTrabajadorCfdisTbl() {
    }

    public RnGcNomTrabajadorCfdisTbl(Integer id) {
        this.id = id;
    }

    public RnGcNomTrabajadorCfdisTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
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

    public RnGcNomSolicitudTrabajadorTbl getSolicitudTrabajdorId() {
        return solicitudTrabajdorId;
    }

    public void setSolicitudTrabajdorId(RnGcNomSolicitudTrabajadorTbl solicitudTrabajdorId) {
        this.solicitudTrabajdorId = solicitudTrabajdorId;
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
        if (!(object instanceof RnGcNomTrabajadorCfdisTbl)) {
            return false;
        }
        RnGcNomTrabajadorCfdisTbl other = (RnGcNomTrabajadorCfdisTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcNomTrabajadorCfdisTbl[ id=" + id + " ]";
    }
    
}
