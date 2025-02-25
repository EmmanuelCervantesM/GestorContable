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
 * @author Consultor
 */
@Entity
@Table(name = "rn_gc_tipo_poliza")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcTipoPoliza.findAll", query = "SELECT r FROM RnGcTipoPoliza r")
    , @NamedQuery(name = "RnGcTipoPoliza.findById", query = "SELECT r FROM RnGcTipoPoliza r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcTipoPoliza.findByTipoPoliza", query = "SELECT r FROM RnGcTipoPoliza r WHERE r.tipoPoliza = :tipoPoliza")
    , @NamedQuery(name = "RnGcTipoPoliza.findByDescripcion", query = "SELECT r FROM RnGcTipoPoliza r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcTipoPoliza.findByNumeroSecuencia", query = "SELECT r FROM RnGcTipoPoliza r WHERE r.numeroSecuencia = :numeroSecuencia")
    , @NamedQuery(name = "RnGcTipoPoliza.findByFechaFinSecuencia", query = "SELECT r FROM RnGcTipoPoliza r WHERE r.fechaFinSecuencia = :fechaFinSecuencia")
    , @NamedQuery(name = "RnGcTipoPoliza.findByEstatus", query = "SELECT r FROM RnGcTipoPoliza r WHERE r.estatus = :estatus")
    , @NamedQuery(name = "RnGcTipoPoliza.findByAdicional1", query = "SELECT r FROM RnGcTipoPoliza r WHERE r.adicional1 = :adicional1")
    , @NamedQuery(name = "RnGcTipoPoliza.findByAdicional2", query = "SELECT r FROM RnGcTipoPoliza r WHERE r.adicional2 = :adicional2")
    , @NamedQuery(name = "RnGcTipoPoliza.findByCreadoPor", query = "SELECT r FROM RnGcTipoPoliza r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcTipoPoliza.findByFechaCreacion", query = "SELECT r FROM RnGcTipoPoliza r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcTipoPoliza.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcTipoPoliza r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcTipoPoliza.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcTipoPoliza r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcTipoPoliza implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tipoPoliza")
    private String tipoPoliza;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numeroSecuencia")
    private int numeroSecuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaFinSecuencia")
    @Temporal(TemporalType.DATE)
    private Date fechaFinSecuencia;
    @Size(max = 45)
    @Column(name = "estatus")
    private String estatus;
    @Size(max = 45)
    @Column(name = "adicional1")
    private String adicional1;
    @Size(max = 45)
    @Column(name = "adicional2")
    private String adicional2;
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
    @OneToMany(mappedBy = "tipoPolizaId")
    private Collection<RnGcPolizaHeaderTbl> rnGcPolizaHeaderTblCollection;

    public RnGcTipoPoliza() {
    }

    public RnGcTipoPoliza(Integer id) {
        this.id = id;
    }

    public RnGcTipoPoliza(Integer id, String tipoPoliza, int numeroSecuencia, Date fechaFinSecuencia, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.tipoPoliza = tipoPoliza;
        this.numeroSecuencia = numeroSecuencia;
        this.fechaFinSecuencia = fechaFinSecuencia;
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

    public String getTipoPoliza() {
        return tipoPoliza;
    }

    public void setTipoPoliza(String tipoPoliza) {
        this.tipoPoliza = tipoPoliza;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNumeroSecuencia() {
        return numeroSecuencia;
    }

    public void setNumeroSecuencia(int numeroSecuencia) {
        this.numeroSecuencia = numeroSecuencia;
    }

    public Date getFechaFinSecuencia() {
        return fechaFinSecuencia;
    }

    public void setFechaFinSecuencia(Date fechaFinSecuencia) {
        this.fechaFinSecuencia = fechaFinSecuencia;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getAdicional1() {
        return adicional1;
    }

    public void setAdicional1(String adicional1) {
        this.adicional1 = adicional1;
    }

    public String getAdicional2() {
        return adicional2;
    }

    public void setAdicional2(String adicional2) {
        this.adicional2 = adicional2;
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
    public Collection<RnGcPolizaHeaderTbl> getRnGcPolizaHeaderTblCollection() {
        return rnGcPolizaHeaderTblCollection;
    }

    public void setRnGcPolizaHeaderTblCollection(Collection<RnGcPolizaHeaderTbl> rnGcPolizaHeaderTblCollection) {
        this.rnGcPolizaHeaderTblCollection = rnGcPolizaHeaderTblCollection;
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
        if (!(object instanceof RnGcTipoPoliza)) {
            return false;
        }
        RnGcTipoPoliza other = (RnGcTipoPoliza) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcTipoPoliza[ id=" + id + " ]";
    }
    
}
