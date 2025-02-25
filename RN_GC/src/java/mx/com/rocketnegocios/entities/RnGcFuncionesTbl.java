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
@Table(name = "rn_gc_funciones_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcFuncionesTbl.findAll", query = "SELECT r FROM RnGcFuncionesTbl r")
    , @NamedQuery(name = "RnGcFuncionesTbl.findByCreadoPor", query = "SELECT r FROM RnGcFuncionesTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcFuncionesTbl.findByFechaCreacion", query = "SELECT r FROM RnGcFuncionesTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcFuncionesTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcFuncionesTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcFuncionesTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcFuncionesTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcFuncionesTbl.findById", query = "SELECT r FROM RnGcFuncionesTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcFuncionesTbl.findByFuncionClave", query = "SELECT r FROM RnGcFuncionesTbl r WHERE r.funcionClave = :funcionClave")
    , @NamedQuery(name = "RnGcFuncionesTbl.findByFuncionNombre", query = "SELECT r FROM RnGcFuncionesTbl r WHERE r.funcionNombre = :funcionNombre")
    , @NamedQuery(name = "RnGcFuncionesTbl.findByTipo", query = "SELECT r FROM RnGcFuncionesTbl r WHERE r.tipo = :tipo")
    , @NamedQuery(name = "RnGcFuncionesTbl.findByDescripcion", query = "SELECT r FROM RnGcFuncionesTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcFuncionesTbl.findByEstado", query = "SELECT r FROM RnGcFuncionesTbl r WHERE r.estado = :estado")})
public class RnGcFuncionesTbl implements Serializable {

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
    @Size(min = 1, max = 15)
    @Column(name = "funcionClave")
    private String funcionClave;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "funcionNombre")
    private String funcionNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "urlFuncion")
    private String urlFuncion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "iconoFuncion")
    private String iconoFuncion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "Tipo")
    private String tipo;
    @Size(max = 45)
    @Column(name = "Descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "Estado")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionesId")
    private Collection<RnGcMenuslineasTbl> rnGcMenuslineasTblCollection;

    public RnGcFuncionesTbl() {
    }

    public RnGcFuncionesTbl(Integer id) {
        this.id = id;
    }

    public RnGcFuncionesTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion, String funcionClave, String funcionNombre, String urlFuncion, String iconoFuncion, String tipo, String estado) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
        this.funcionClave = funcionClave;
        this.funcionNombre = funcionNombre;
        this.urlFuncion = urlFuncion;
        this.iconoFuncion = iconoFuncion;
        this.tipo = tipo;
        this.estado = estado;
    }

    public String getIconoFuncion() {
        return iconoFuncion;
    }

    public void setIconoFuncion(String iconoFuncion) {
        this.iconoFuncion = iconoFuncion;
    }

    public String getUrlFuncion() {
        return urlFuncion;
    }

    public void setUrlFuncion(String urlFuncion) {
        this.urlFuncion = urlFuncion;
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

    public String getFuncionClave() {
        return funcionClave;
    }

    public void setFuncionClave(String funcionClave) {
        this.funcionClave = funcionClave;
    }

    public String getFuncionNombre() {
        return funcionNombre;
    }

    public void setFuncionNombre(String funcionNombre) {
        this.funcionNombre = funcionNombre;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RnGcFuncionesTbl)) {
            return false;
        }
        RnGcFuncionesTbl other = (RnGcFuncionesTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcFuncionesTbl[ id=" + id + " ]";
    }

}
