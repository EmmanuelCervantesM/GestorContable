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
@Table(name = "rn_gc_tipolistanegra_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcTipolistanegraTbl_1.findAll", query = "SELECT r FROM RnGcTipolistanegraTbl_1 r")
    , @NamedQuery(name = "RnGcTipolistanegraTbl_1.findById", query = "SELECT r FROM RnGcTipolistanegraTbl_1 r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcTipolistanegraTbl_1.findByTipoLista", query = "SELECT r FROM RnGcTipolistanegraTbl_1 r WHERE r.tipoLista = :tipoLista")
    , @NamedQuery(name = "RnGcTipolistanegraTbl_1.findByFechaPublicacion", query = "SELECT r FROM RnGcTipolistanegraTbl_1 r WHERE r.fechaPublicacion = :fechaPublicacion")
    , @NamedQuery(name = "RnGcTipolistanegraTbl_1.findByFolioPublicacion", query = "SELECT r FROM RnGcTipolistanegraTbl_1 r WHERE r.folioPublicacion = :folioPublicacion")
    , @NamedQuery(name = "RnGcTipolistanegraTbl_1.findByCreadoPor", query = "SELECT r FROM RnGcTipolistanegraTbl_1 r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcTipolistanegraTbl_1.findByFechaCreacion", query = "SELECT r FROM RnGcTipolistanegraTbl_1 r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcTipolistanegraTbl_1.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcTipolistanegraTbl_1 r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcTipolistanegraTbl_1.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcTipolistanegraTbl_1 r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcTipolistanegraTbl_1.findByEstado", query = "SELECT r FROM RnGcTipolistanegraTbl_1 r WHERE r.estado = :estado")})
public class RnGcTipolistanegraTbl_1 implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "estado")
    private String estado;
    @OneToMany(mappedBy = "idTipoLista")
    private Collection<RnGcListanegraTbl_1> rnGcListanegraTblCollection;

    public RnGcTipolistanegraTbl_1() {
    }

    public RnGcTipolistanegraTbl_1(Integer id) {
        this.id = id;
    }

    public RnGcTipolistanegraTbl_1(Integer id, Date fechaCreacion, String estado) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
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
    public Collection<RnGcListanegraTbl_1> getRnGcListanegraTblCollection() {
        return rnGcListanegraTblCollection;
    }

    public void setRnGcListanegraTblCollection(Collection<RnGcListanegraTbl_1> rnGcListanegraTblCollection) {
        this.rnGcListanegraTblCollection = rnGcListanegraTblCollection;
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
        if (!(object instanceof RnGcTipolistanegraTbl_1)) {
            return false;
        }
        RnGcTipolistanegraTbl_1 other = (RnGcTipolistanegraTbl_1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcTipolistanegraTbl_1[ id=" + id + " ]";
    }
    
}
