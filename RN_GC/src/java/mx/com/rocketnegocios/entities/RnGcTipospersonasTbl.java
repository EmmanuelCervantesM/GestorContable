/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Developer1
 */
@Entity
@Table(name = "rn_gc_tipospersonas_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcTipospersonasTbl.findAll", query = "SELECT r FROM RnGcTipospersonasTbl r")
    , @NamedQuery(name = "RnGcTipospersonasTbl.findById", query = "SELECT r FROM RnGcTipospersonasTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcTipospersonasTbl.findByTipoPersona", query = "SELECT r FROM RnGcTipospersonasTbl r WHERE r.tipoPersona = :tipoPersona")
    , @NamedQuery(name = "RnGcTipospersonasTbl.findByCreadoPor", query = "SELECT r FROM RnGcTipospersonasTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcTipospersonasTbl.findByFechaCreacion", query = "SELECT r FROM RnGcTipospersonasTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcTipospersonasTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcTipospersonasTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcTipospersonasTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcTipospersonasTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcTipospersonasTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tipoPersona")
    private String tipoPersona;
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
    @OneToMany(mappedBy = "tipoPersonaId")
    private Collection<RnGcPersonasTbl> rnGcPersonasTblCollection;

    public RnGcTipospersonasTbl() {
    }

    public RnGcTipospersonasTbl(Integer id) {
        this.id = id;
    }

    public RnGcTipospersonasTbl(Integer id, String tipoPersona, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.tipoPersona = tipoPersona;
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

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
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

    @XmlTransient
    public Collection<RnGcPersonasTbl> getRnGcPersonasTblCollection() {
        return rnGcPersonasTblCollection;
    }

    public void setRnGcPersonasTblCollection(Collection<RnGcPersonasTbl> rnGcPersonasTblCollection) {
        this.rnGcPersonasTblCollection = rnGcPersonasTblCollection;
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
        if (!(object instanceof RnGcTipospersonasTbl)) {
            return false;
        }
        RnGcTipospersonasTbl other = (RnGcTipospersonasTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcTipospersonasTbl[ id=" + id + " ]";
    }
    
}
