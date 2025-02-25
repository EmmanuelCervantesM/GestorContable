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
 * @author LenovoZ40
 */
@Entity
@Table(name = "rn_gc_tipoListaNegra_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcTipoListaNegraTbl.findAll", query = "SELECT r FROM RnGcTipoListaNegraTbl r where r.estado = 'A'")
    , @NamedQuery(name = "RnGcTipoListaNegraTbl.findById", query = "SELECT r FROM RnGcTipoListaNegraTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcTipoListaNegraTbl.findByTipoLista", query = "SELECT r FROM RnGcTipoListaNegraTbl r WHERE r.tipoLista = :tipoLista")
    , @NamedQuery(name = "RnGcTipoListaNegraTbl.findByFechaPublicacion", query = "SELECT r FROM RnGcTipoListaNegraTbl r WHERE r.fechaPublicacion = :fechaPublicacion")
    , @NamedQuery(name = "RnGcTipoListaNegraTbl.findByFolioPublicacion", query = "SELECT r FROM RnGcTipoListaNegraTbl r WHERE r.folioPublicacion = :folioPublicacion")
    , @NamedQuery(name = "RnGcTipoListaNegraTbl.findByFolioPublicacionCreadoPor", query = "SELECT r FROM RnGcTipoListaNegraTbl r WHERE r.folioPublicacion = :folioPublicacion")
    , @NamedQuery(name = "RnGcTipoListaNegraTbl.findByCreadoPor", query = "SELECT r FROM RnGcTipoListaNegraTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcTipoListaNegraTbl.findByFechaCreacion", query = "SELECT r FROM RnGcTipoListaNegraTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcTipoListaNegraTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcTipoListaNegraTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcTipoListaNegraTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcTipoListaNegraTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcTipoListaNegraTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 30)
    @Column(name = "tipoLista")
    private String tipoLista;
    @Column(name = "fechaPublicacion")
    @Temporal(TemporalType.DATE)
    private Date fechaPublicacion;
    @Size(max = 50)
    @Column(name = "folioPublicacion")
    private String folioPublicacion;
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
    @OneToMany(mappedBy = "idTipoLista")
    private Collection<RnGcListaNegraTbl> rnGcListaNegraTblCollection;
    @Column(name = "estado")
    private String estado;
    @NotNull

    public RnGcTipoListaNegraTbl() {
    }

    public RnGcTipoListaNegraTbl(Integer id) {
        this.id = id;
    }

    public RnGcTipoListaNegraTbl(Integer id, Date fechaCreacion) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoLista() {
        return tipoLista;
    }

    public void setTipoLista(String tipoLista) {
        this.tipoLista = tipoLista;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getFolioPublicacion() {
        return folioPublicacion;
    }

    public void setFolioPublicacion(String folioPublicacion) {
        this.folioPublicacion = folioPublicacion;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<RnGcListaNegraTbl> getRnGcListaNegraTblCollection() {
        return rnGcListaNegraTblCollection;
    }

    public void setRnGcListaNegraTblCollection(Collection<RnGcListaNegraTbl> rnGcListaNegraTblCollection) {
        this.rnGcListaNegraTblCollection = rnGcListaNegraTblCollection;
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
        if (!(object instanceof RnGcTipoListaNegraTbl)) {
            return false;
        }
        RnGcTipoListaNegraTbl other = (RnGcTipoListaNegraTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcTipoListaNegraTbl[ id=" + id + " ]";
    }
    
}
