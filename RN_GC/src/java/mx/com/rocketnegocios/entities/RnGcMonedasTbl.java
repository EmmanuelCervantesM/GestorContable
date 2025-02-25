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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Developer1
 */
@Entity
@Table(name = "rn_gc_monedas_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcMonedasTbl.findAll", query = "SELECT r FROM RnGcMonedasTbl r")
    , @NamedQuery(name = "RnGcMonedasTbl.findByCreadoPor", query = "SELECT r FROM RnGcMonedasTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcMonedasTbl.findByFechaCreacion", query = "SELECT r FROM RnGcMonedasTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcMonedasTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcMonedasTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcMonedasTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcMonedasTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcMonedasTbl.findById", query = "SELECT r FROM RnGcMonedasTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcMonedasTbl.findByCMoneda", query = "SELECT r FROM RnGcMonedasTbl r WHERE r.cMoneda = :cMoneda")
    , @NamedQuery(name = "RnGcMonedasTbl.findByDescripcion", query = "SELECT r FROM RnGcMonedasTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcMonedasTbl.findByDecimales", query = "SELECT r FROM RnGcMonedasTbl r WHERE r.decimales = :decimales")
    , @NamedQuery(name = "RnGcMonedasTbl.findByPorcentVariacion", query = "SELECT r FROM RnGcMonedasTbl r WHERE r.porcentVariacion = :porcentVariacion")
    , @NamedQuery(name = "RnGcMonedasTbl.findByFechaInicioVigencia", query = "SELECT r FROM RnGcMonedasTbl r WHERE r.fechaInicioVigencia = :fechaInicioVigencia")
    , @NamedQuery(name = "RnGcMonedasTbl.findByFechaFinVigencia", query = "SELECT r FROM RnGcMonedasTbl r WHERE r.fechaFinVigencia = :fechaFinVigencia")})
public class RnGcMonedasTbl implements Serializable {

    @OneToMany(mappedBy = "tipoMoneda")
    private Collection<RnGcPolizaHeaderTbl> rnGcPolizaHeaderTblCollection;
    @OneToMany(mappedBy = "monedaId")
    private Collection<RnGcCatalogoCuentasTbl> rnGcCatalogoCuentasTblCollection;

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
    @Size(min = 1, max = 3)
    @Column(name = "c_Moneda")
    private String cMoneda;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "Descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Decimales")
    private int decimales;
    @Size(max = 4)
    @Column(name = "PorcentVariacion")
    private String porcentVariacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaInicioVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioVigencia;
    @Column(name = "fechaFinVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaFinVigencia;
    @JoinColumn(name = "cfdi_Id", referencedColumnName = "Id")
    @ManyToOne
    private RnGcCfdisTbl cfdiId;

    public RnGcMonedasTbl() {
    }

    public RnGcMonedasTbl(Integer id) {
        this.id = id;
    }

    public RnGcMonedasTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion, String cMoneda, String descripcion, int decimales, Date fechaInicioVigencia) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
        this.cMoneda = cMoneda;
        this.descripcion = descripcion;
        this.decimales = decimales;
        this.fechaInicioVigencia = fechaInicioVigencia;
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

    public String getCMoneda() {
        return cMoneda;
    }

    public void setCMoneda(String cMoneda) {
        this.cMoneda = cMoneda;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDecimales() {
        return decimales;
    }

    public void setDecimales(int decimales) {
        this.decimales = decimales;
    }

    public String getPorcentVariacion() {
        return porcentVariacion;
    }

    public void setPorcentVariacion(String porcentVariacion) {
        this.porcentVariacion = porcentVariacion;
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

    public RnGcCfdisTbl getCfdiId() {
        return cfdiId;
    }

    public void setCfdiId(RnGcCfdisTbl cfdiId) {
        this.cfdiId = cfdiId;
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
        if (!(object instanceof RnGcMonedasTbl)) {
            return false;
        }
        RnGcMonedasTbl other = (RnGcMonedasTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcMonedasTbl[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<RnGcPolizaHeaderTbl> getRnGcPolizaHeaderTblCollection() {
        return rnGcPolizaHeaderTblCollection;
    }

    public void setRnGcPolizaHeaderTblCollection(Collection<RnGcPolizaHeaderTbl> rnGcPolizaHeaderTblCollection) {
        this.rnGcPolizaHeaderTblCollection = rnGcPolizaHeaderTblCollection;
    }
    
    @XmlTransient
    public Collection<RnGcCatalogoCuentasTbl> getRnGcCatalogoCuentasTblCollection() {
        return rnGcCatalogoCuentasTblCollection;
    }

    public void setRnGcCatalogoCuentasTblCollection(Collection<RnGcCatalogoCuentasTbl> rnGcCatalogoCuentasTblCollection) {
        this.rnGcCatalogoCuentasTblCollection = rnGcCatalogoCuentasTblCollection;
    }
}
