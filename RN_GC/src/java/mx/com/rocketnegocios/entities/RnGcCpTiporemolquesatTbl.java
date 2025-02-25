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
 * @author maxoc
 */
@Entity
@Table(name = "rn_gc_cp_tiporemolquesat_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcCpTiporemolquesatTbl.findAll", query = "SELECT r FROM RnGcCpTiporemolquesatTbl r")
    , @NamedQuery(name = "RnGcCpTiporemolquesatTbl.findById", query = "SELECT r FROM RnGcCpTiporemolquesatTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcCpTiporemolquesatTbl.findByClave", query = "SELECT r FROM RnGcCpTiporemolquesatTbl r WHERE r.clave = :clave")
    , @NamedQuery(name = "RnGcCpTiporemolquesatTbl.findByDescripcion", query = "SELECT r FROM RnGcCpTiporemolquesatTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcCpTiporemolquesatTbl.findByFechaInicioVigencia", query = "SELECT r FROM RnGcCpTiporemolquesatTbl r WHERE r.fechaInicioVigencia = :fechaInicioVigencia")
    , @NamedQuery(name = "RnGcCpTiporemolquesatTbl.findByFechaFin", query = "SELECT r FROM RnGcCpTiporemolquesatTbl r WHERE r.fechaFin = :fechaFin")
    , @NamedQuery(name = "RnGcCpTiporemolquesatTbl.findByCreadoPor", query = "SELECT r FROM RnGcCpTiporemolquesatTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCpTiporemolquesatTbl.findByFechaCreacion", query = "SELECT r FROM RnGcCpTiporemolquesatTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcCpTiporemolquesatTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcCpTiporemolquesatTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcCpTiporemolquesatTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcCpTiporemolquesatTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcCpTiporemolquesatTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 10)
    @Column(name = "clave")
    private String clave;
    @Size(max = 150)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fechaInicioVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioVigencia;
    @Column(name = "fechaFin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Column(name = "creadoPor")
    private Integer creadoPor;
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
    @OneToMany(mappedBy = "tipoRemolqueId")
    private Collection<RnGcCpUnidadesTbl> rnGcCpUnidadesTblCollection;

    public RnGcCpTiporemolquesatTbl() {
    }

    public RnGcCpTiporemolquesatTbl(Integer id) {
        this.id = id;
    }

    public RnGcCpTiporemolquesatTbl(Integer id, Date fechaCreacion) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
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

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Integer creadoPor) {
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
    public Collection<RnGcCpUnidadesTbl> getRnGcCpUnidadesTblCollection() {
        return rnGcCpUnidadesTblCollection;
    }

    public void setRnGcCpUnidadesTblCollection(Collection<RnGcCpUnidadesTbl> rnGcCpUnidadesTblCollection) {
        this.rnGcCpUnidadesTblCollection = rnGcCpUnidadesTblCollection;
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
        if (!(object instanceof RnGcCpTiporemolquesatTbl)) {
            return false;
        }
        RnGcCpTiporemolquesatTbl other = (RnGcCpTiporemolquesatTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcCpTiporemolquesatTbl[ id=" + id + " ]";
    }
    
}
