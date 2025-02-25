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
 * @author Developer1
 */
@Entity
@Table(name = "rn_gc_menus_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcMenusTbl.findAll", query = "SELECT r FROM RnGcMenusTbl r")
    , @NamedQuery(name = "RnGcMenusTbl.findByCreadoPor", query = "SELECT r FROM RnGcMenusTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcMenusTbl.findByFechaCreacion", query = "SELECT r FROM RnGcMenusTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcMenusTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcMenusTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcMenusTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcMenusTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcMenusTbl.findById", query = "SELECT r FROM RnGcMenusTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcMenusTbl.findByMenuNombre", query = "SELECT r FROM RnGcMenusTbl r WHERE r.menuNombre = :menuNombre")
    , @NamedQuery(name = "RnGcMenusTbl.findByDescripcion", query = "SELECT r FROM RnGcMenusTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcMenusTbl.findByEstado", query = "SELECT r FROM RnGcMenusTbl r WHERE r.estado = :estado")})
public class RnGcMenusTbl implements Serializable {

    private static final long serialVersionUID = 1L;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "menuNombre")
    private String menuNombre;
    @Size(max = 45)
    @Column(name = "Descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Estado")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "menuId")
    private Collection<RnGcMenuslineasTbl> rnGcMenuslineasTblCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "menuId")
    private Collection<RnGcPerfilesTbl> rnGcPerfilesTblCollection;

    public RnGcMenusTbl() {
    }

    public RnGcMenusTbl(Integer id) {
        this.id = id;
    }

    public RnGcMenusTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion, String menuNombre, String estado) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
        this.menuNombre = menuNombre;
        this.estado = estado;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuNombre() {
        return menuNombre;
    }

    public void setMenuNombre(String menuNombre) {
        this.menuNombre = menuNombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<RnGcMenuslineasTbl> getRnGcMenuslineasTblCollection() {
        return rnGcMenuslineasTblCollection;
    }

    public void setRnGcMenuslineasTblCollection(Collection<RnGcMenuslineasTbl> rnGcMenuslineasTblCollection) {
        this.rnGcMenuslineasTblCollection = rnGcMenuslineasTblCollection;
    }

    @XmlTransient
    public Collection<RnGcPerfilesTbl> getRnGcPerfilesTblCollection() {
        return rnGcPerfilesTblCollection;
    }

    public void setRnGcPerfilesTblCollection(Collection<RnGcPerfilesTbl> rnGcPerfilesTblCollection) {
        this.rnGcPerfilesTblCollection = rnGcPerfilesTblCollection;
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
        if (!(object instanceof RnGcMenusTbl)) {
            return false;
        }
        RnGcMenusTbl other = (RnGcMenusTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcMenusTbl[ id=" + id + " ]";
    }
    
}
