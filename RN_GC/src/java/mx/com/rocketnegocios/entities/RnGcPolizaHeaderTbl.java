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
 * @author Consultor
 */
@Entity
@Table(name = "rn_gc_poliza_header_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcPolizaHeaderTbl.findAll", query = "SELECT r FROM RnGcPolizaHeaderTbl r")
    , @NamedQuery(name = "RnGcPolizaHeaderTbl.findById", query = "SELECT r FROM RnGcPolizaHeaderTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcPolizaHeaderTbl.findByNumeroPoliza", query = "SELECT r FROM RnGcPolizaHeaderTbl r WHERE r.numeroPoliza = :numeroPoliza")
    , @NamedQuery(name = "RnGcPolizaHeaderTbl.findByTipoPoliza", query = "SELECT r FROM RnGcPolizaHeaderTbl r WHERE r.tipoPoliza = :tipoPoliza")
    , @NamedQuery(name = "RnGcPolizaHeaderTbl.findByFecha", query = "SELECT r FROM RnGcPolizaHeaderTbl r WHERE r.fecha = :fecha")
    , @NamedQuery(name = "RnGcPolizaHeaderTbl.findByConcepto", query = "SELECT r FROM RnGcPolizaHeaderTbl r WHERE r.concepto = :concepto")
    , @NamedQuery(name = "RnGcPolizaHeaderTbl.findByReceptor", query = "SELECT r FROM RnGcPolizaHeaderTbl r WHERE r.receptor = :receptor")
    , @NamedQuery(name = "RnGcPolizaHeaderTbl.findByRfcReceptor", query = "SELECT r FROM RnGcPolizaHeaderTbl r WHERE r.rfcReceptor = :rfcReceptor")
    , @NamedQuery(name = "RnGcPolizaHeaderTbl.findByCreadoPor", query = "SELECT r FROM RnGcPolizaHeaderTbl r WHERE r.creadoPor = :creadoPor order by r.id desc")
    , @NamedQuery(name = "RnGcPolizaHeaderTbl.findByFechaCreacion", query = "SELECT r FROM RnGcPolizaHeaderTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcPolizaHeaderTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcPolizaHeaderTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcPolizaHeaderTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcPolizaHeaderTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcPolizaHeaderTbl implements Serializable {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tipoCambio")
    private Double tipoCambio;
    @JoinColumn(name = "tipoMoneda", referencedColumnName = "Id")
    @ManyToOne
    private RnGcMonedasTbl tipoMoneda;

    @JoinColumn(name = "tipoPolizaId", referencedColumnName = "id")
    @ManyToOne
    private RnGcTipoPoliza tipoPolizaId;
    @JoinColumn(name = "periodoId", referencedColumnName = "id")
    @ManyToOne
    private RnGcPeriodosTbl periodoId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numeroPoliza")
    private int numeroPoliza;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tipoPoliza")
    private String tipoPoliza;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 500)
    @Column(name = "concepto")
    private String concepto;
    @Size(max = 200)
    @Column(name = "receptor")
    private String receptor;
    @Size(max = 13)
    @Column(name = "rfcReceptor")
    private String rfcReceptor;
    @Size(max = 45)
    @Column(name = "estatus")
    private String estatus;
    @Size(max = 45)
    @Column(name = "vigente")
    private String vigente;
    @Size(max = 45)
    @Column(name = "adicional1")
    private String adicional1;
    @Size(max = 45)
    @Column(name = "adicional2")
    private String adicional2;
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
    @JoinColumn(name = "cfdiId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcCfdisTbl cfdiId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "polizaHeaderId")
    private Collection<RnGcPolizaLineasTbl> rnGcPolizaLineasTblCollection;

    public RnGcPolizaHeaderTbl() {
    }

    public RnGcPolizaHeaderTbl(Integer id) {
        this.id = id;
    }

    public RnGcPolizaHeaderTbl(Integer id, int numeroPoliza, String tipoPoliza, Date fecha, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.numeroPoliza = numeroPoliza;
        this.tipoPoliza = tipoPoliza;
        this.fecha = fecha;
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

    public int getNumeroPoliza() {
        return numeroPoliza;
    }

    public void setNumeroPoliza(int numeroPoliza) {
        this.numeroPoliza = numeroPoliza;
    }

    public String getTipoPoliza() {
        return tipoPoliza;
    }

    public void setTipoPoliza(String tipoPoliza) {
        this.tipoPoliza = tipoPoliza;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getRfcReceptor() {
        return rfcReceptor;
    }

    public void setRfcReceptor(String rfcReceptor) {
        this.rfcReceptor = rfcReceptor;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getVigente() {
        return vigente;
    }

    public void setVigente(String vigente) {
        this.vigente = vigente;
    }

    public String getAdicional1() {
        return adicional1;
    }

    public void setAdicional1(String adicional1) {
        this.adicional1 = adicional1;
    }

    public String getAdicional2() {
        return adicional2;
    }

    public void setAdicional2(String adicional2) {
        this.adicional2 = adicional2;
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

    @XmlTransient
    public Collection<RnGcPolizaLineasTbl> getRnGcPolizaLineasTblCollection() {
        return rnGcPolizaLineasTblCollection;
    }

    public void setRnGcPolizaLineasTblCollection(Collection<RnGcPolizaLineasTbl> rnGcPolizaLineasTblCollection) {
        this.rnGcPolizaLineasTblCollection = rnGcPolizaLineasTblCollection;
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
        if (!(object instanceof RnGcPolizaHeaderTbl)) {
            return false;
        }
        RnGcPolizaHeaderTbl other = (RnGcPolizaHeaderTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcPolizaHeaderTbl[ id=" + id + " ]";
    }

    public RnGcTipoPoliza getTipoPolizaId() {
        return tipoPolizaId;
    }

    public void setTipoPolizaId(RnGcTipoPoliza tipoPolizaId) {
        this.tipoPolizaId = tipoPolizaId;
    }

    public Double getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(Double tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public RnGcMonedasTbl getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(RnGcMonedasTbl tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public RnGcPeriodosTbl getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(RnGcPeriodosTbl periodoId) {
        this.periodoId = periodoId;
    }

}
