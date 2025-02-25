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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "rn_gc_tipofactor_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcTipofactorTbl.findAll", query = "SELECT r FROM RnGcTipofactorTbl r")
    , @NamedQuery(name = "RnGcTipofactorTbl.findById", query = "SELECT r FROM RnGcTipofactorTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcTipofactorTbl.findByCtipoFactor", query = "SELECT r FROM RnGcTipofactorTbl r WHERE r.ctipoFactor = :ctipoFactor")
    , @NamedQuery(name = "RnGcTipofactorTbl.findByFechaInicioVigencia", query = "SELECT r FROM RnGcTipofactorTbl r WHERE r.fechaInicioVigencia = :fechaInicioVigencia")
    , @NamedQuery(name = "RnGcTipofactorTbl.findByFechaFinVigencia", query = "SELECT r FROM RnGcTipofactorTbl r WHERE r.fechaFinVigencia = :fechaFinVigencia")
    , @NamedQuery(name = "RnGcTipofactorTbl.findByCreadoPor", query = "SELECT r FROM RnGcTipofactorTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcTipofactorTbl.findByFechaCreacion", query = "SELECT r FROM RnGcTipofactorTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcTipofactorTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcTipofactorTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcTipofactorTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcTipofactorTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcTipofactorTbl implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipofactorId")
    private Collection<RnGcImpuestosLocalesTbl> rnGcImpuestosLocalesTblCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "c_tipoFactor")
    private String ctipoFactor;
    @Basic(optional = false)
    @NotNull
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
    @ManyToOne
    private RnGcCfdisTbl cfdisId;

    public RnGcTipofactorTbl() {
    }

    public RnGcTipofactorTbl(Integer id) {
        this.id = id;
    }

    public RnGcTipofactorTbl(Integer id, String ctipoFactor, Date fechaInicioVigencia, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.ctipoFactor = ctipoFactor;
        this.fechaInicioVigencia = fechaInicioVigencia;
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

    public String getCtipoFactor() {
        return ctipoFactor;
    }

    public void setCtipoFactor(String ctipoFactor) {
        this.ctipoFactor = ctipoFactor;
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
        if (!(object instanceof RnGcTipofactorTbl)) {
            return false;
        }
        RnGcTipofactorTbl other = (RnGcTipofactorTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcTipofactorTbl[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<RnGcImpuestosLocalesTbl> getRnGcImpuestosLocalesTblCollection() {
        return rnGcImpuestosLocalesTblCollection;
    }

    public void setRnGcImpuestosLocalesTblCollection(Collection<RnGcImpuestosLocalesTbl> rnGcImpuestosLocalesTblCollection) {
        this.rnGcImpuestosLocalesTblCollection = rnGcImpuestosLocalesTblCollection;
    }
    
}
