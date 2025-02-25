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

/**
 *
 * @author maxoc
 */
@Entity
@Table(name = "rn_gc_cp_productos_destinos_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcCpProductosDestinosTbl.findAll", query = "SELECT r FROM RnGcCpProductosDestinosTbl r")
    , @NamedQuery(name = "RnGcCpProductosDestinosTbl.findById", query = "SELECT r FROM RnGcCpProductosDestinosTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcCpProductosDestinosTbl.findByProductoId", query = "SELECT r FROM RnGcCpProductosDestinosTbl r WHERE r.productoId = :productoId")
    , @NamedQuery(name = "RnGcCpProductosDestinosTbl.findByDestinoId", query = "SELECT r FROM RnGcCpProductosDestinosTbl r WHERE r.destinoId = :destinoId")
    , @NamedQuery(name = "RnGcCpProductosDestinosTbl.findByCreadoPor", query = "SELECT r FROM RnGcCpProductosDestinosTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCpProductosDestinosTbl.findByFechaCreacion", query = "SELECT r FROM RnGcCpProductosDestinosTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcCpProductosDestinosTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcCpProductosDestinosTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcCpProductosDestinosTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcCpProductosDestinosTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcCpProductosDestinosTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
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
    @JoinColumn(name = "productoId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcProductserviciosTbl productoId;
    @JoinColumn(name = "destinoId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcCpOrigendestinoTbl destinoId;

    public RnGcCpProductosDestinosTbl() {
    }

    public RnGcCpProductosDestinosTbl(Integer id) {
        this.id = id;
    }

    public RnGcCpProductosDestinosTbl(Integer id, Date fechaCreacion) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public RnGcProductserviciosTbl getProductoId() {
        return productoId;
    }

    public void setProductoId(RnGcProductserviciosTbl productoId) {
        this.productoId = productoId;
    }

    public RnGcCpOrigendestinoTbl getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(RnGcCpOrigendestinoTbl destinoId) {
        this.destinoId = destinoId;
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
        if (!(object instanceof RnGcCpProductosDestinosTbl)) {
            return false;
        }
        RnGcCpProductosDestinosTbl other = (RnGcCpProductosDestinosTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcCpCartaPorteTbl[ id=" + id + " ]";
    }
    
}
