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
 * @author maxoc
 */
@Entity
@Table(name = "rn_gc_cp_tipolicencia_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcCpTipolicenciaTbl.findAll", query = "SELECT r FROM RnGcCpTipolicenciaTbl r")
    , @NamedQuery(name = "RnGcCpTipolicenciaTbl.findById", query = "SELECT r FROM RnGcCpTipolicenciaTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcCpTipolicenciaTbl.findByTipo", query = "SELECT r FROM RnGcCpTipolicenciaTbl r WHERE r.tipo = :tipo")
    , @NamedQuery(name = "RnGcCpTipolicenciaTbl.findByDescripcion", query = "SELECT r FROM RnGcCpTipolicenciaTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcCpTipolicenciaTbl.findByCreadoPor", query = "SELECT r FROM RnGcCpTipolicenciaTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCpTipolicenciaTbl.findByFechaCreacion", query = "SELECT r FROM RnGcCpTipolicenciaTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcCpTipolicenciaTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcCpTipolicenciaTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcCpTipolicenciaTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcCpTipolicenciaTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcCpTipolicenciaTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tipo")
    private String tipo;
    @Size(max = 150)
    @Column(name = "descripcion")
    private String descripcion;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoLicenciaId")
    private Collection<RnGcCpConductoresTbl> rnGcCpConductoresTblCollection;

    public RnGcCpTipolicenciaTbl() {
    }

    public RnGcCpTipolicenciaTbl(Integer id) {
        this.id = id;
    }

    public RnGcCpTipolicenciaTbl(Integer id, String tipo, int creadoPor, Date fechaCreacion) {
        this.id = id;
        this.tipo = tipo;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    @XmlTransient
    public Collection<RnGcCpConductoresTbl> getRnGcCpConductoresTblCollection() {
        return rnGcCpConductoresTblCollection;
    }

    public void setRnGcCpConductoresTblCollection(Collection<RnGcCpConductoresTbl> rnGcCpConductoresTblCollection) {
        this.rnGcCpConductoresTblCollection = rnGcCpConductoresTblCollection;
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
        if (!(object instanceof RnGcCpTipolicenciaTbl)) {
            return false;
        }
        RnGcCpTipolicenciaTbl other = (RnGcCpTipolicenciaTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcCpTipolicenciaTbl[ id=" + id + " ]";
    }
    
}
