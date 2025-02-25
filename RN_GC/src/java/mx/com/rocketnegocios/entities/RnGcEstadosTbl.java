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
 * @author mmiva
 */
@Entity
@Table(name = "rn_gc_estados_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcEstadosTbl.findAll", query = "SELECT r FROM RnGcEstadosTbl r")
    , @NamedQuery(name = "RnGcEstadosTbl.findById", query = "SELECT r FROM RnGcEstadosTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcEstadosTbl.findByCreadoPor", query = "SELECT r FROM RnGcEstadosTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcEstadosTbl.findByFechaCreacion", query = "SELECT r FROM RnGcEstadosTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcEstadosTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcEstadosTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcEstadosTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcEstadosTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcEstadosTbl.findByCEstado", query = "SELECT r FROM RnGcEstadosTbl r WHERE r.cEstado = :cEstado")
    , @NamedQuery(name = "RnGcEstadosTbl.findByCPais", query = "SELECT r FROM RnGcEstadosTbl r WHERE r.cPais = :cPais")
    , @NamedQuery(name = "RnGcEstadosTbl.findByNombreEstado", query = "SELECT r FROM RnGcEstadosTbl r WHERE r.nombreEstado = :nombreEstado")
    , @NamedQuery(name = "RnGcEstadosTbl.findByFechaInicioVigencia", query = "SELECT r FROM RnGcEstadosTbl r WHERE r.fechaInicioVigencia = :fechaInicioVigencia")
    , @NamedQuery(name = "RnGcEstadosTbl.findByFechaFinVigencia", query = "SELECT r FROM RnGcEstadosTbl r WHERE r.fechaFinVigencia = :fechaFinVigencia")
    , @NamedQuery(name = "RnGcEstadosTbl.findByVersionCFDI", query = "SELECT r FROM RnGcEstadosTbl r WHERE r.versionCFDI = :versionCFDI")
    , @NamedQuery(name = "RnGcEstadosTbl.findByVersionCatalogo", query = "SELECT r FROM RnGcEstadosTbl r WHERE r.versionCatalogo = :versionCatalogo")})
public class RnGcEstadosTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
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
    @Size(min = 1, max = 3)
    @Column(name = "c_Estado")
    private String cEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "c_Pais")
    private String cPais;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombreEstado")
    private String nombreEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaInicioVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioVigencia;
    @Column(name = "fechaFinVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaFinVigencia;
    @Size(max = 4)
    @Column(name = "versionCFDI")
    private String versionCFDI;
    @Size(max = 4)
    @Column(name = "versionCatalogo")
    private String versionCatalogo;
    @JoinColumn(name = "paisId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private RnGcPaisesTbl paisId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoId")
    private Collection<RnGcLocalidadesTbl> rnGcLocalidadesTblCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoId")
    private Collection<RnGcMunicipiosTbl> rnGcMunicipiosTblCollection;

    public RnGcEstadosTbl() {
    }

    public RnGcEstadosTbl(Integer id) {
        this.id = id;
    }

    public RnGcEstadosTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion, String cEstado, String cPais, String nombreEstado, Date fechaInicioVigencia) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
        this.cEstado = cEstado;
        this.cPais = cPais;
        this.nombreEstado = nombreEstado;
        this.fechaInicioVigencia = fechaInicioVigencia;
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

    public String getCEstado() {
        return cEstado;
    }

    public void setCEstado(String cEstado) {
        this.cEstado = cEstado;
    }

    public String getCPais() {
        return cPais;
    }

    public void setCPais(String cPais) {
        this.cPais = cPais;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public Date getFechaInicioVigencia() {
        return fechaInicioVigencia;
    }

    public void setFechaInicioVigencia(Date fechaInicioVigencia) {
        this.fechaInicioVigencia = fechaInicioVigencia;
    }

    public Date getFechaFinVigencia() {
        return fechaFinVigencia;
    }

    public void setFechaFinVigencia(Date fechaFinVigencia) {
        this.fechaFinVigencia = fechaFinVigencia;
    }

    public String getVersionCFDI() {
        return versionCFDI;
    }

    public void setVersionCFDI(String versionCFDI) {
        this.versionCFDI = versionCFDI;
    }

    public String getVersionCatalogo() {
        return versionCatalogo;
    }

    public void setVersionCatalogo(String versionCatalogo) {
        this.versionCatalogo = versionCatalogo;
    }

    public RnGcPaisesTbl getPaisId() {
        return paisId;
    }

    public void setPaisId(RnGcPaisesTbl paisId) {
        this.paisId = paisId;
    }

    @XmlTransient
    public Collection<RnGcLocalidadesTbl> getRnGcLocalidadesTblCollection() {
        return rnGcLocalidadesTblCollection;
    }

    public void setRnGcLocalidadesTblCollection(Collection<RnGcLocalidadesTbl> rnGcLocalidadesTblCollection) {
        this.rnGcLocalidadesTblCollection = rnGcLocalidadesTblCollection;
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
        if (!(object instanceof RnGcEstadosTbl)) {
            return false;
        }
        RnGcEstadosTbl other = (RnGcEstadosTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcEstadosTbl[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<RnGcMunicipiosTbl> getRnGcMunicipiosTblCollection() {
        return rnGcMunicipiosTblCollection;
    }

    public void setRnGcMunicipiosTblCollection(Collection<RnGcMunicipiosTbl> rnGcMunicipiosTblCollection) {
        this.rnGcMunicipiosTblCollection = rnGcMunicipiosTblCollection;
    }
    
}
