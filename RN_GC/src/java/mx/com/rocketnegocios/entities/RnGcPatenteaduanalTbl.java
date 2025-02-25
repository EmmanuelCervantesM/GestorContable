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
 * @author mmiva
 */
@Entity
@Table(name = "rn_gc_patenteaduanal_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcPatenteaduanalTbl.findAll", query = "SELECT r FROM RnGcPatenteaduanalTbl r")
    , @NamedQuery(name = "RnGcPatenteaduanalTbl.findById", query = "SELECT r FROM RnGcPatenteaduanalTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcPatenteaduanalTbl.findByCreadoPor", query = "SELECT r FROM RnGcPatenteaduanalTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcPatenteaduanalTbl.findByFechaCreacion", query = "SELECT r FROM RnGcPatenteaduanalTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcPatenteaduanalTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcPatenteaduanalTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcPatenteaduanalTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcPatenteaduanalTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcPatenteaduanalTbl.findByCPatenteAduanal", query = "SELECT r FROM RnGcPatenteaduanalTbl r WHERE r.cPatenteAduanal = :cPatenteAduanal")
    , @NamedQuery(name = "RnGcPatenteaduanalTbl.findByFechaInicioVigencia", query = "SELECT r FROM RnGcPatenteaduanalTbl r WHERE r.fechaInicioVigencia = :fechaInicioVigencia")
    , @NamedQuery(name = "RnGcPatenteaduanalTbl.findByFechaFinVigencia", query = "SELECT r FROM RnGcPatenteaduanalTbl r WHERE r.fechaFinVigencia = :fechaFinVigencia")
    , @NamedQuery(name = "RnGcPatenteaduanalTbl.findByVersionCFDI", query = "SELECT r FROM RnGcPatenteaduanalTbl r WHERE r.versionCFDI = :versionCFDI")
    , @NamedQuery(name = "RnGcPatenteaduanalTbl.findByVersionCatalogo", query = "SELECT r FROM RnGcPatenteaduanalTbl r WHERE r.versionCatalogo = :versionCatalogo")})
public class RnGcPatenteaduanalTbl implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "c_PatenteAduanal")
    private String cPatenteAduanal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaInicioVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioVigencia;
    @Column(name = "fechaFinVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaFinVigencia;
    @Size(max = 4)
    @Column(name = "versionCFDI")
    private String versionCFDI;
    @Size(max = 4)
    @Column(name = "versionCatalogo")
    private String versionCatalogo;

    public RnGcPatenteaduanalTbl() {
    }

    public RnGcPatenteaduanalTbl(Integer id) {
        this.id = id;
    }

    public RnGcPatenteaduanalTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion, String cPatenteAduanal, Date fechaInicioVigencia) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
        this.cPatenteAduanal = cPatenteAduanal;
        this.fechaInicioVigencia = fechaInicioVigencia;
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

    public String getCPatenteAduanal() {
        return cPatenteAduanal;
    }

    public void setCPatenteAduanal(String cPatenteAduanal) {
        this.cPatenteAduanal = cPatenteAduanal;
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

    public String getVersionCFDI() {
        return versionCFDI;
    }

    public void setVersionCFDI(String versionCFDI) {
        this.versionCFDI = versionCFDI;
    }

    public String getVersionCatalogo() {
        return versionCatalogo;
    }

    public void setVersionCatalogo(String versionCatalogo) {
        this.versionCatalogo = versionCatalogo;
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
        if (!(object instanceof RnGcPatenteaduanalTbl)) {
            return false;
        }
        RnGcPatenteaduanalTbl other = (RnGcPatenteaduanalTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcPatenteaduanalTbl[ id=" + id + " ]";
    }
    
}
