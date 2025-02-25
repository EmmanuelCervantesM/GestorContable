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
@Table(name = "rn_gc_metodospagos_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcMetodospagosTbl.findAll", query = "SELECT r FROM RnGcMetodospagosTbl r")
    , @NamedQuery(name = "RnGcMetodospagosTbl.findByCreadoPor", query = "SELECT r FROM RnGcMetodospagosTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcMetodospagosTbl.findByFechaCreacion", query = "SELECT r FROM RnGcMetodospagosTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcMetodospagosTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcMetodospagosTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcMetodospagosTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcMetodospagosTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcMetodospagosTbl.findById", query = "SELECT r FROM RnGcMetodospagosTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcMetodospagosTbl.findByCMetodoPago", query = "SELECT r FROM RnGcMetodospagosTbl r WHERE r.cMetodoPago = :cMetodoPago")
    , @NamedQuery(name = "RnGcMetodospagosTbl.findByDescripcion", query = "SELECT r FROM RnGcMetodospagosTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcMetodospagosTbl.findByFechaInicioVigencia", query = "SELECT r FROM RnGcMetodospagosTbl r WHERE r.fechaInicioVigencia = :fechaInicioVigencia")
    , @NamedQuery(name = "RnGcMetodospagosTbl.findByFechaFinVigencia", query = "SELECT r FROM RnGcMetodospagosTbl r WHERE r.fechaFinVigencia = :fechaFinVigencia")})
public class RnGcMetodospagosTbl implements Serializable {

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
    @Size(min = 1, max = 3)
    @Column(name = "c_MetodoPago")
    private String cMetodoPago;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 35)
    @Column(name = "Descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaInicioVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioVigencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaFinVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaFinVigencia;
    @JoinColumn(name = "cfdis_Id", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private RnGcCfdisTbl cfdisId;

    public RnGcMetodospagosTbl() {
    }

    public RnGcMetodospagosTbl(Integer id) {
        this.id = id;
    }

    public RnGcMetodospagosTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion, String cMetodoPago, String descripcion, Date fechaInicioVigencia, Date fechaFinVigencia) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
        this.cMetodoPago = cMetodoPago;
        this.descripcion = descripcion;
        this.fechaInicioVigencia = fechaInicioVigencia;
        this.fechaFinVigencia = fechaFinVigencia;
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

    public String getCMetodoPago() {
        return cMetodoPago;
    }

    public void setCMetodoPago(String cMetodoPago) {
        this.cMetodoPago = cMetodoPago;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicioVigencia() {
        return fechaInicioVigencia;
    }

    public void setFechaInicioVigencia(Date fechaInicioVigencia) {
        this.fechaInicioVigencia = fechaInicioVigencia;
    }

    public Date getFechaFinVigencia() {
        return fechaFinVigencia;
    }

    public void setFechaFinVigencia(Date fechaFinVigencia) {
        this.fechaFinVigencia = fechaFinVigencia;
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
        if (!(object instanceof RnGcMetodospagosTbl)) {
            return false;
        }
        RnGcMetodospagosTbl other = (RnGcMetodospagosTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcMetodospagosTbl[ id=" + id + " ]";
    }
    
}
