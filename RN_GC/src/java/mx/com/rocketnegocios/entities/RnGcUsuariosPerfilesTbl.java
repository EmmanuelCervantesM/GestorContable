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
@Table(name = "rn_gc_usuarios_perfiles_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcUsuariosPerfilesTbl.findAll", query = "SELECT r FROM RnGcUsuariosPerfilesTbl r")
    , @NamedQuery(name = "RnGcUsuariosPerfilesTbl.findById", query = "SELECT r FROM RnGcUsuariosPerfilesTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcUsuariosPerfilesTbl.findByFechaInicial", query = "SELECT r FROM RnGcUsuariosPerfilesTbl r WHERE r.fechaInicial = :fechaInicial")
    , @NamedQuery(name = "RnGcUsuariosPerfilesTbl.findByFechaFinal", query = "SELECT r FROM RnGcUsuariosPerfilesTbl r WHERE r.fechaFinal = :fechaFinal")
    , @NamedQuery(name = "RnGcUsuariosPerfilesTbl.findByCreadoPor", query = "SELECT r FROM RnGcUsuariosPerfilesTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcUsuariosPerfilesTbl.findByFechaCreacion", query = "SELECT r FROM RnGcUsuariosPerfilesTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcUsuariosPerfilesTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcUsuariosPerfilesTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcUsuariosPerfilesTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcUsuariosPerfilesTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGeUsuarioPerfilesTbl.findByUsuarioId", query = "SELECT r FROM RnGcUsuariosPerfilesTbl r WHERE r.usuariosId = :usuariosId")})
public class RnGcUsuariosPerfilesTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaInicial")
    @Temporal(TemporalType.DATE)
    private Date fechaInicial;
    @Column(name = "fechaFinal")
    @Temporal(TemporalType.DATE)
    private Date fechaFinal;
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
    @JoinColumn(name = "perfilesId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private RnGcPerfilesTbl perfilesId;
    @JoinColumn(name = "usuariosId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private RnGcUsuariosTbl usuariosId;

    public RnGcUsuariosPerfilesTbl() {
    }

    public RnGcUsuariosPerfilesTbl(Integer id) {
        this.id = id;
    }

    public RnGcUsuariosPerfilesTbl(Integer id, Date fechaInicial, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.fechaInicial = fechaInicial;
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

    public Date getFechaInicial() {
        if(fechaInicial == null) {
            this.fechaInicial = new Date();
        }
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
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

    public RnGcPerfilesTbl getPerfilesId() {
        return perfilesId;
    }

    public void setPerfilesId(RnGcPerfilesTbl perfilesId) {
        this.perfilesId = perfilesId;
    }

    public RnGcUsuariosTbl getUsuariosId() {
        return usuariosId;
    }

    public void setUsuariosId(RnGcUsuariosTbl usuariosId) {
        this.usuariosId = usuariosId;
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
        if (!(object instanceof RnGcUsuariosPerfilesTbl)) {
            return false;
        }
        RnGcUsuariosPerfilesTbl other = (RnGcUsuariosPerfilesTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcUsuariosPerfilesTbl[ id=" + id + " ]";
    }
    
}
