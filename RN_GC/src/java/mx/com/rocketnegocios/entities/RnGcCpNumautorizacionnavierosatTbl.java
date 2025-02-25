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
 * @author maxoc
 */
@Entity
@Table(name = "rn_gc_cp_numautorizacionnavierosat_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcCpNumautorizacionnavierosatTbl.findAll", query = "SELECT r FROM RnGcCpNumautorizacionnavierosatTbl r")
    , @NamedQuery(name = "RnGcCpNumautorizacionnavierosatTbl.findById", query = "SELECT r FROM RnGcCpNumautorizacionnavierosatTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcCpNumautorizacionnavierosatTbl.findByNumAutorizacion", query = "SELECT r FROM RnGcCpNumautorizacionnavierosatTbl r WHERE r.numAutorizacion = :numAutorizacion")
    , @NamedQuery(name = "RnGcCpNumautorizacionnavierosatTbl.findByFechaInicioVigencia", query = "SELECT r FROM RnGcCpNumautorizacionnavierosatTbl r WHERE r.fechaInicioVigencia = :fechaInicioVigencia")
    , @NamedQuery(name = "RnGcCpNumautorizacionnavierosatTbl.findByFechaFinVigencia", query = "SELECT r FROM RnGcCpNumautorizacionnavierosatTbl r WHERE r.fechaFinVigencia = :fechaFinVigencia")
    , @NamedQuery(name = "RnGcCpNumautorizacionnavierosatTbl.findByCreadoPor", query = "SELECT r FROM RnGcCpNumautorizacionnavierosatTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCpNumautorizacionnavierosatTbl.findByFechaCreacion", query = "SELECT r FROM RnGcCpNumautorizacionnavierosatTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcCpNumautorizacionnavierosatTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcCpNumautorizacionnavierosatTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcCpNumautorizacionnavierosatTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcCpNumautorizacionnavierosatTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcCpNumautorizacionnavierosatTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "numAutorizacion")
    private String numAutorizacion;
    @Column(name = "fechaInicioVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioVigencia;
    @Column(name = "fechaFinVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaFinVigencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creadoPor")
    private int creadoPor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaCreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "ultimaActualizacionPor")
    private Integer ultimaActualizacionPor;
    @Column(name = "ultimaFechaActualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaFechaActualizacion;

    public RnGcCpNumautorizacionnavierosatTbl() {
    }

    public RnGcCpNumautorizacionnavierosatTbl(Integer id) {
        this.id = id;
    }

    public RnGcCpNumautorizacionnavierosatTbl(Integer id, int creadoPor, Date fechaCreacion) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumAutorizacion() {
        return numAutorizacion;
    }

    public void setNumAutorizacion(String numAutorizacion) {
        this.numAutorizacion = numAutorizacion;
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

    public Integer getUltimaActualizacionPor() {
        return ultimaActualizacionPor;
    }

    public void setUltimaActualizacionPor(Integer ultimaActualizacionPor) {
        this.ultimaActualizacionPor = ultimaActualizacionPor;
    }

    public Date getUltimaFechaActualizacion() {
        return ultimaFechaActualizacion;
    }

    public void setUltimaFechaActualizacion(Date ultimaFechaActualizacion) {
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
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
        if (!(object instanceof RnGcCpNumautorizacionnavierosatTbl)) {
            return false;
        }
        RnGcCpNumautorizacionnavierosatTbl other = (RnGcCpNumautorizacionnavierosatTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcCpNumautorizacionnavierosatTbl[ id=" + id + " ]";
    }
    
}
