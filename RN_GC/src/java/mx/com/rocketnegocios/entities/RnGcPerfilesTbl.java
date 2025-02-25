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
@Table(name = "rn_gc_perfiles_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcPerfilesTbl.findAll", query = "SELECT r FROM RnGcPerfilesTbl r")
    , @NamedQuery(name = "RnGcPerfilesTbl.findByCreadoPor", query = "SELECT r FROM RnGcPerfilesTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcPerfilesTbl.findByFechaCreacion", query = "SELECT r FROM RnGcPerfilesTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcPerfilesTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcPerfilesTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcPerfilesTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcPerfilesTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcPerfilesTbl.findById", query = "SELECT r FROM RnGcPerfilesTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcPerfilesTbl.findByPerfilNombre", query = "SELECT r FROM RnGcPerfilesTbl r WHERE r.perfilNombre = :perfilNombre")
    , @NamedQuery(name = "RnGcPerfilesTbl.findBytipoPerfilAGA", query = "SELECT r FROM RnGcPerfilesTbl r WHERE r.tipoPerfil IN ('A', 'AD', 'AC','DE','CE')")
    , @NamedQuery(name = "RnGcPerfilesTbl.findBytipoPerfilA", query = "SELECT r FROM RnGcPerfilesTbl r WHERE r.tipoPerfil IN ('A', 'AG', 'AD', 'AC','DE','CE')")
    , @NamedQuery(name = "RnGcPerfilesTbl.findBytipoPerfilAD", query = "SELECT r FROM RnGcPerfilesTbl r WHERE r.tipoPerfil IN ('AD', 'AC','DE','CE')")
    , @NamedQuery(name = "RnGcPerfilesTbl.findBytipoPerfilAC", query = "SELECT r FROM RnGcPerfilesTbl r WHERE r.tipoPerfil IN ('AC','CE')")
    , @NamedQuery(name = "RnGcPerfilesTbl.findByDescripcion", query = "SELECT r FROM RnGcPerfilesTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcPerfilesTbl.findByFechaInicial", query = "SELECT r FROM RnGcPerfilesTbl r WHERE r.fechaInicial = :fechaInicial")
    , @NamedQuery(name = "RnGcPerfilesTbl.findByFechaFinal", query = "SELECT r FROM RnGcPerfilesTbl r WHERE r.fechaFinal = :fechaFinal")
    , @NamedQuery(name = "RnGcPerfilesTbl.findByTipoUsuario", query = "SELECT r FROM RnGcPerfilesTbl r WHERE r.tipoPerfil = :tipoUsuario")})
public class RnGcPerfilesTbl implements Serializable {

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
    @Column(name = "PerfilNombre")
    private String perfilNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "Estado")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "tipoPerfil")
    private String tipoPerfil;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaInicial")
    @Temporal(TemporalType.DATE)
    private Date fechaInicial;
    @Column(name = "fechaFinal")
    @Temporal(TemporalType.DATE)
    private Date fechaFinal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfilesId")
    private Collection<RnGcUsuariosPerfilesTbl> rnGcUsuariosPerfilesTblCollection;
    @JoinColumn(name = "Menu_Id", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private RnGcMenusTbl menuId;

    public RnGcPerfilesTbl() {
    }

    public RnGcPerfilesTbl(Integer id) {
        this.id = id;
    }

    public RnGcPerfilesTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion, String perfilNombre, String descripcion, String tipoPerfil, String estado, Date fechaInicial) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
        this.perfilNombre = perfilNombre;
        this.descripcion = descripcion;
        this.tipoPerfil = tipoPerfil;
        this.estado = estado;
        this.fechaInicial = fechaInicial;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoPerfil() {
        return tipoPerfil;
    }

    public void setTipoPerfil(String tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
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

    public String getPerfilNombre() {
        return perfilNombre;
    }

    public void setPerfilNombre(String perfilNombre) {
        this.perfilNombre = perfilNombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    @XmlTransient
    public Collection<RnGcUsuariosPerfilesTbl> getRnGcUsuariosPerfilesTblCollection() {
        return rnGcUsuariosPerfilesTblCollection;
    }

    public void setRnGcUsuariosPerfilesTblCollection(Collection<RnGcUsuariosPerfilesTbl> rnGcUsuariosPerfilesTblCollection) {
        this.rnGcUsuariosPerfilesTblCollection = rnGcUsuariosPerfilesTblCollection;
    }

    public RnGcMenusTbl getMenuId() {
        return menuId;
    }

    public void setMenuId(RnGcMenusTbl menuId) {
        this.menuId = menuId;
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
        if (!(object instanceof RnGcPerfilesTbl)) {
            return false;
        }
        RnGcPerfilesTbl other = (RnGcPerfilesTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcPerfilesTbl[ id=" + id + " ]";
    }

}
