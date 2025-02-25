/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.entities;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Developer1
 */
@Entity
@Table(name = "rn_gc_tasacuota_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcTasacuotaTbl.findAll", query = "SELECT r FROM RnGcTasacuotaTbl r")
    , @NamedQuery(name = "RnGcTasacuotaTbl.findById", query = "SELECT r FROM RnGcTasacuotaTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcTasacuotaTbl.findByRangoofijo", query = "SELECT r FROM RnGcTasacuotaTbl r WHERE r.rangoofijo = :rangoofijo")
    , @NamedQuery(name = "RnGcTasacuotaTbl.findByValorMaximo", query = "SELECT r FROM RnGcTasacuotaTbl r WHERE r.valorMaximo = :valorMaximo")
    , @NamedQuery(name = "RnGcTasacuotaTbl.findByImpuesto", query = "SELECT r FROM RnGcTasacuotaTbl r WHERE r.impuesto = :impuesto")
    , @NamedQuery(name = "RnGcTasacuotaTbl.findByFactor", query = "SELECT r FROM RnGcTasacuotaTbl r WHERE r.factor = :factor")
    , @NamedQuery(name = "RnGcTasacuotaTbl.findByTraslado", query = "SELECT r FROM RnGcTasacuotaTbl r WHERE r.traslado = :traslado")
    , @NamedQuery(name = "RnGcTasacuotaTbl.findByRetencion", query = "SELECT r FROM RnGcTasacuotaTbl r WHERE r.retencion = :retencion")
    , @NamedQuery(name = "RnGcTasacuotaTbl.findByFechaInicioVigencia", query = "SELECT r FROM RnGcTasacuotaTbl r WHERE r.fechaInicioVigencia = :fechaInicioVigencia")
    , @NamedQuery(name = "RnGcTasacuotaTbl.findByFechaFinVigencia", query = "SELECT r FROM RnGcTasacuotaTbl r WHERE r.fechaFinVigencia = :fechaFinVigencia")
    , @NamedQuery(name = "RnGcTasacuotaTbl.findByCreadoPor", query = "SELECT r FROM RnGcTasacuotaTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcTasacuotaTbl.findByFechaCreacion", query = "SELECT r FROM RnGcTasacuotaTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcTasacuotaTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcTasacuotaTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcTasacuotaTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcTasacuotaTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcTasaCuotaTbl.findByTipoFactorImpuesto", query = "SELECT r FROM RnGcTasacuotaTbl r WHERE r.traslado = :traslado AND r.retencion = :retencion AND r.factor = :factor AND r.impuesto = :impuesto")})
public class RnGcTasacuotaTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "rangoofijo")
    private String rangoofijo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valorMaximo")
    private double valorMaximo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "impuesto")
    private String impuesto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "factor")
    private String factor;
    @Size(max = 45)
    @Column(name = "traslado")
    private String traslado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "retencion")
    private String retencion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaInicioVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioVigencia;
    @Size(max = 45)
    @Column(name = "fechaFinVigencia")
    private String fechaFinVigencia;
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
    @JoinColumn(name = "cfdi_Id", referencedColumnName = "Id")
    @ManyToOne
    private RnGcCfdisTbl cfdiId;

    public RnGcTasacuotaTbl() {
    }

    public RnGcTasacuotaTbl(Integer id) {
        this.id = id;
    }

    public RnGcTasacuotaTbl(Integer id, String rangoofijo, double valorMaximo, String impuesto, String factor, String retencion, Date fechaInicioVigencia, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.rangoofijo = rangoofijo;
        this.valorMaximo = valorMaximo;
        this.impuesto = impuesto;
        this.factor = factor;
        this.retencion = retencion;
        this.fechaInicioVigencia = fechaInicioVigencia;
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

    public String getRangoofijo() {
        return rangoofijo;
    }

    public void setRangoofijo(String rangoofijo) {
        this.rangoofijo = rangoofijo;
    }

    public double getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(double valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public String getTraslado() {
        return traslado;
    }

    public void setTraslado(String traslado) {
        this.traslado = traslado;
    }

    public String getRetencion() {
        return retencion;
    }

    public void setRetencion(String retencion) {
        this.retencion = retencion;
    }

    public Date getFechaInicioVigencia() {
        return fechaInicioVigencia;
    }

    public void setFechaInicioVigencia(Date fechaInicioVigencia) {
        this.fechaInicioVigencia = fechaInicioVigencia;
    }

    public String getFechaFinVigencia() {
        return fechaFinVigencia;
    }

    public void setFechaFinVigencia(String fechaFinVigencia) {
        this.fechaFinVigencia = fechaFinVigencia;
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
        if (!(object instanceof RnGcTasacuotaTbl)) {
            return false;
        }
        RnGcTasacuotaTbl other = (RnGcTasacuotaTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcTasacuotaTbl[ id=" + id + " ]";
    }
    
}
