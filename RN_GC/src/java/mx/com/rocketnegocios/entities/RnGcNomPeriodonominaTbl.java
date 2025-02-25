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
 * @author LenovoZ40
 */
@Entity
@Table(name = "rn_gc_nom_periodonomina_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcNomPeriodonominaTbl.findAll", query = "SELECT r FROM RnGcNomPeriodonominaTbl r")
    , @NamedQuery(name = "RnGcNomPeriodonominaTbl.findById", query = "SELECT r FROM RnGcNomPeriodonominaTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcNomPeriodonominaTbl.findByCreadoPor", query = "SELECT r FROM RnGcNomPeriodonominaTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcNomPeriodonominaTbl.findByFechaCreacion", query = "SELECT r FROM RnGcNomPeriodonominaTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcNomPeriodonominaTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcNomPeriodonominaTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcNomPeriodonominaTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcNomPeriodonominaTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcNomPeriodonominaTbl.findByNombrePeriodo", query = "SELECT r FROM RnGcNomPeriodonominaTbl r WHERE r.nombrePeriodo = :nombrePeriodo")
    , @NamedQuery(name = "RnGcNomPeriodonominaTbl.findByFechaInicio", query = "SELECT r FROM RnGcNomPeriodonominaTbl r WHERE r.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "RnGcNomPeriodonominaTbl.findByFechaFin", query = "SELECT r FROM RnGcNomPeriodonominaTbl r WHERE r.fechaFin = :fechaFin")
    , @NamedQuery(name = "RnGcNomPeriodonominaTbl.findByAnioPeriodo", query = "SELECT r FROM RnGcNomPeriodonominaTbl r WHERE r.anioPeriodo = :anioPeriodo")
    , @NamedQuery(name = "RnGcNomPeriodonominaTbl.findByMesPeriodo", query = "SELECT r FROM RnGcNomPeriodonominaTbl r WHERE r.mesPeriodo = :mesPeriodo")
    , @NamedQuery(name = "RnGcNomPeriodonominaTbl.findByNumMesPeriodo", query = "SELECT r FROM RnGcNomPeriodonominaTbl r WHERE r.numMesPeriodo = :numMesPeriodo")
    , @NamedQuery(name = "RnGcNomPeriodonominaTbl.findByEstatus", query = "SELECT r FROM RnGcNomPeriodonominaTbl r WHERE r.estatus = :estatus")
    , @NamedQuery(name = "RnGcNomPeriodonominaTbl.findByNomina", query = "SELECT r FROM RnGcNomPeriodonominaTbl r WHERE r.nominaId = :nominaId")})
public class RnGcNomPeriodonominaTbl implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombrePeriodo")
    private String nombrePeriodo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaFin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "anioPeriodo")
    private String anioPeriodo;
    @Size(max = 45)
    @Column(name = "mesPeriodo")
    private String mesPeriodo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numMesPeriodo")
    private long numMesPeriodo;
    @Size(max = 1)
    @Column(name = "estatus")
    private String estatus;
    @JoinColumn(name = "nominaId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RnGcNomNominasTbl nominaId;

    public RnGcNomPeriodonominaTbl() {
    }

    public RnGcNomPeriodonominaTbl(Integer id) {
        this.id = id;
    }

    public RnGcNomPeriodonominaTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion, String nombrePeriodo, Date fechaInicio, Date fechaFin, String anioPeriodo, long numMesPeriodo) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
        this.nombrePeriodo = nombrePeriodo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.anioPeriodo = anioPeriodo;
        this.numMesPeriodo = numMesPeriodo;
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

    public String getNombrePeriodo() {
        return nombrePeriodo;
    }

    public void setNombrePeriodo(String nombrePeriodo) {
        this.nombrePeriodo = nombrePeriodo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getAnioPeriodo() {
        return anioPeriodo;
    }

    public void setAnioPeriodo(String anioPeriodo) {
        this.anioPeriodo = anioPeriodo;
    }

    public String getMesPeriodo() {
        return mesPeriodo;
    }

    public void setMesPeriodo(String mesPeriodo) {
        this.mesPeriodo = mesPeriodo;
    }

    public long getNumMesPeriodo() {
        return numMesPeriodo;
    }

    public void setNumMesPeriodo(long numMesPeriodo) {
        this.numMesPeriodo = numMesPeriodo;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public RnGcNomNominasTbl getNominaId() {
        return nominaId;
    }

    public void setNominaId(RnGcNomNominasTbl nominaId) {
        this.nominaId = nominaId;
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
        if (!(object instanceof RnGcNomPeriodonominaTbl)) {
            return false;
        }
        RnGcNomPeriodonominaTbl other = (RnGcNomPeriodonominaTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcNomPeriodonominaTbl[ id=" + id + " ]";
    }
    
}
