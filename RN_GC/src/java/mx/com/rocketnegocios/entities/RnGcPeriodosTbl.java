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
@Table(name = "rn_gc_periodos_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcPeriodosTbl.findAll", query = "SELECT r FROM RnGcPeriodosTbl r")
    , @NamedQuery(name = "RnGcPeriodosTbl.findById", query = "SELECT r FROM RnGcPeriodosTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcPeriodosTbl.findByPeriodoId", query = "SELECT r FROM RnGcPeriodosTbl r WHERE r.usuariosId = :usuario order by r.periodoId desc")
    , @NamedQuery(name = "RnGcPeriodosTbl.findByFechaInicioPeriodo", query = "SELECT r FROM RnGcPeriodosTbl r WHERE r.fechaInicioPeriodo = :fechaInicioPeriodo")
    , @NamedQuery(name = "RnGcPeriodosTbl.findByFechaFinPeriodo", query = "SELECT r FROM RnGcPeriodosTbl r WHERE r.fechaFinPeriodo = :fechaFinPeriodo")
    , @NamedQuery(name = "RnGcPeriodosTbl.findByCreadoPor", query = "SELECT r FROM RnGcPeriodosTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcPeriodosTbl.findByFechaCreacion", query = "SELECT r FROM RnGcPeriodosTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcPeriodosTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcPeriodosTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcPeriodosTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcPeriodosTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcPeriodosTbl implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "año")
    private Integer año;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mes")
    private String mes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estatus")
    private String estatus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipoPeriodo")
    private String tipoPeriodo;
    @Column(name = "libroContableId")
    private Integer libroContableId;
    @JoinColumn(name = "usuarioId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcUsuariosTbl usuariosId;
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "periodoId")
    private int periodoId;
    @Column(name = "fechaInicioPeriodo")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioPeriodo;
    @Size(min = 1, max = 45)
    @Column(name = "fechaFinPeriodo")
    private String fechaFinPeriodo;
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
    @OneToMany(mappedBy = "periodosId")
    private Collection<RnGcCfdisTbl> rnGcCfdisTblCollection;
    @JoinColumn(name = "cfdis_Id", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private RnGcCfdisTbl cfdisId;

    public RnGcPeriodosTbl() {
    }

    public RnGcPeriodosTbl(Integer id) {
        this.id = id;
    }

    public RnGcPeriodosTbl(Integer id, int periodoId, Date fechaInicioPeriodo, String fechaFinPeriodo, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.periodoId = periodoId;
        this.fechaInicioPeriodo = fechaInicioPeriodo;
        this.fechaFinPeriodo = fechaFinPeriodo;
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

    public int getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(int periodoId) {
        this.periodoId = periodoId;
    }

    public Date getFechaInicioPeriodo() {
        return fechaInicioPeriodo;
    }

    public void setFechaInicioPeriodo(Date fechaInicioPeriodo) {
        this.fechaInicioPeriodo = fechaInicioPeriodo;
    }

    public String getFechaFinPeriodo() {
        return fechaFinPeriodo;
    }

    public void setFechaFinPeriodo(String fechaFinPeriodo) {
        this.fechaFinPeriodo = fechaFinPeriodo;
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

    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getTipoPeriodo() {
        return tipoPeriodo;
    }

    public void setTipoPeriodo(String tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }

    public Integer getLibroContableId() {
        return libroContableId;
    }

    public void setLibroContableId(Integer libroContableId) {
        this.libroContableId = libroContableId;
    }

    public RnGcUsuariosTbl getUsuariosId() {
        return usuariosId;
    }

    public void setUsuariosId(RnGcUsuariosTbl usuariosId) {
        this.usuariosId = usuariosId;
    }

    @XmlTransient
    public Collection<RnGcCfdisTbl> getRnGcCfdisTblCollection() {
        return rnGcCfdisTblCollection;
    }

    public void setRnGcCfdisTblCollection(Collection<RnGcCfdisTbl> rnGcCfdisTblCollection) {
        this.rnGcCfdisTblCollection = rnGcCfdisTblCollection;
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
        if (!(object instanceof RnGcPeriodosTbl)) {
            return false;
        }
        RnGcPeriodosTbl other = (RnGcPeriodosTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcPeriodosTbl[ id=" + id + " ]";
    }
    
}
