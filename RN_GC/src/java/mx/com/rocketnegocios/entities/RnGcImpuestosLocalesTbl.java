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
@Table(name = "rn_gc_impuestos_locales_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcImpuestosLocalesTbl.findAll", query = "SELECT r FROM RnGcImpuestosLocalesTbl r")
    , @NamedQuery(name = "RnGcImpuestosLocalesTbl.findById", query = "SELECT r FROM RnGcImpuestosLocalesTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcImpuestosLocalesTbl.findByNombreImpuesto", query = "SELECT r FROM RnGcImpuestosLocalesTbl r WHERE r.nombreImpuesto = :nombreImpuesto")
    , @NamedQuery(name = "RnGcImpuestosLocalesTbl.findByTasaCuota", query = "SELECT r FROM RnGcImpuestosLocalesTbl r WHERE r.tasaCuota = :tasaCuota")
    , @NamedQuery(name = "RnGcImpuestosLocalesTbl.findByCreadoPor", query = "SELECT r FROM RnGcImpuestosLocalesTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcImpuestosLocalesTbl.findByFechaCreacion", query = "SELECT r FROM RnGcImpuestosLocalesTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcImpuestosLocalesTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcImpuestosLocalesTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcImpuestosLocalesTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcImpuestosLocalesTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcImpuestosLocalesTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "nombreImpuesto")
    private String nombreImpuesto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tasaCuota")
    private Double tasaCuota;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "impuestosLocalesId")
    private Collection<RnGcImpuestosCfdisTbl> rnGcImpuestosCfdisTblCollection;
    @JoinColumn(name = "tipofactor_Id", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private RnGcTipofactorTbl tipofactorId;
    @JoinColumn(name = "tipoimpuesto_Id", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private RnGcTipoimpuestoTbl tipoimpuestoId;

    public RnGcImpuestosLocalesTbl() {
    }

    public RnGcImpuestosLocalesTbl(Integer id) {
        this.id = id;
    }

    public RnGcImpuestosLocalesTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreImpuesto() {
        return nombreImpuesto;
    }

    public void setNombreImpuesto(String nombreImpuesto) {
        this.nombreImpuesto = nombreImpuesto;
    }

    public Double getTasaCuota() {
        if(tasaCuota == null) {
            this.tasaCuota = 0.0;
        }
        return tasaCuota;
    }

    public void setTasaCuota(Double tasaCuota) {
        this.tasaCuota = tasaCuota;
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

    @XmlTransient
    public Collection<RnGcImpuestosCfdisTbl> getRnGcImpuestosCfdisTblCollection() {
        return rnGcImpuestosCfdisTblCollection;
    }

    public void setRnGcImpuestosCfdisTblCollection(Collection<RnGcImpuestosCfdisTbl> rnGcImpuestosCfdisTblCollection) {
        this.rnGcImpuestosCfdisTblCollection = rnGcImpuestosCfdisTblCollection;
    }

    public RnGcTipofactorTbl getTipofactorId() {
        return tipofactorId;
    }

    public void setTipofactorId(RnGcTipofactorTbl tipofactorId) {
        this.tipofactorId = tipofactorId;
    }

    public RnGcTipoimpuestoTbl getTipoimpuestoId() {
        if(tipoimpuestoId == null) {
            this.tipoimpuestoId = new RnGcTipoimpuestoTbl();
        }
        return tipoimpuestoId;
    }

    public void setTipoimpuestoId(RnGcTipoimpuestoTbl tipoimpuestoId) {
        this.tipoimpuestoId = tipoimpuestoId;
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
        if (!(object instanceof RnGcImpuestosLocalesTbl)) {
            return false;
        }
        RnGcImpuestosLocalesTbl other = (RnGcImpuestosLocalesTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcImpuestosLocalesTbl[ id=" + id + " ]";
    }
    
}
