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
@Table(name = "rn_gc_facturas_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcFacturasTbl.findAll", query = "SELECT r FROM RnGcFacturasTbl r")
    , @NamedQuery(name = "RnGcFacturasTbl.findById", query = "SELECT r FROM RnGcFacturasTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcFacturasTbl.findByUuid", query = "SELECT r FROM RnGcFacturasTbl r WHERE r.uuid = :uuid")
    , @NamedQuery(name = "RnGcFacturasTbl.findByRfcEmisor", query = "SELECT r FROM RnGcFacturasTbl r WHERE r.rfcEmisor = :rfcEmisor")
    , @NamedQuery(name = "RnGcFacturasTbl.findByNombreEmisor", query = "SELECT r FROM RnGcFacturasTbl r WHERE r.nombreEmisor = :nombreEmisor")
    , @NamedQuery(name = "RnGcFacturasTbl.findByRfcReceptor", query = "SELECT r FROM RnGcFacturasTbl r WHERE r.rfcReceptor = :rfcReceptor")
    , @NamedQuery(name = "RnGcFacturasTbl.findByNombreReceptor", query = "SELECT r FROM RnGcFacturasTbl r WHERE r.nombreReceptor = :nombreReceptor")
    , @NamedQuery(name = "RnGcFacturasTbl.findByFolio", query = "SELECT r FROM RnGcFacturasTbl r WHERE r.folio = :folio")
    , @NamedQuery(name = "RnGcFacturasTbl.findBySerie", query = "SELECT r FROM RnGcFacturasTbl r WHERE r.serie = :serie")
    , @NamedQuery(name = "RnGcFacturasTbl.findByLugarExpedicion", query = "SELECT r FROM RnGcFacturasTbl r WHERE r.lugarExpedicion = :lugarExpedicion")
    , @NamedQuery(name = "RnGcFacturasTbl.findByFechaExpedicion", query = "SELECT r FROM RnGcFacturasTbl r WHERE r.fechaExpedicion = :fechaExpedicion")
    , @NamedQuery(name = "RnGcFacturasTbl.findByTipoComprobante", query = "SELECT r FROM RnGcFacturasTbl r WHERE r.tipoComprobante = :tipoComprobante")
    , @NamedQuery(name = "RnGcFacturasTbl.findByClaveRegimenFiscal", query = "SELECT r FROM RnGcFacturasTbl r WHERE r.claveRegimenFiscal = :claveRegimenFiscal")
    , @NamedQuery(name = "RnGcFacturasTbl.findByUsoCfdi", query = "SELECT r FROM RnGcFacturasTbl r WHERE r.usoCfdi = :usoCfdi")
    , @NamedQuery(name = "RnGcFacturasTbl.findByTipoRelacion", query = "SELECT r FROM RnGcFacturasTbl r WHERE r.tipoRelacion = :tipoRelacion")
    , @NamedQuery(name = "RnGcFacturasTbl.findByFormaPago", query = "SELECT r FROM RnGcFacturasTbl r WHERE r.formaPago = :formaPago")
    , @NamedQuery(name = "RnGcFacturasTbl.findByMetodoPago", query = "SELECT r FROM RnGcFacturasTbl r WHERE r.metodoPago = :metodoPago")
    , @NamedQuery(name = "RnGcFacturasTbl.findByCondicionPago", query = "SELECT r FROM RnGcFacturasTbl r WHERE r.condicionPago = :condicionPago")
    , @NamedQuery(name = "RnGcFacturasTbl.findByMoneda", query = "SELECT r FROM RnGcFacturasTbl r WHERE r.moneda = :moneda")
    , @NamedQuery(name = "RnGcFacturasTbl.findByImporte", query = "SELECT r FROM RnGcFacturasTbl r WHERE r.importe = :importe")
    , @NamedQuery(name = "RnGcFacturasTbl.findByRngccfdistblId", query = "SELECT r FROM RnGcFacturasTbl r WHERE r.rngccfdistblId = :rngccfdistblId")
    , @NamedQuery(name = "RnGcFacturasTbl.findByCreadoPor", query = "SELECT r FROM RnGcFacturasTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcFacturasTbl.findByFechaCreacion", query = "SELECT r FROM RnGcFacturasTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcFacturasTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcFacturasTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcFacturasTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcFacturasTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcFacturasTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "uuid")
    private String uuid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "rfcEmisor")
    private String rfcEmisor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombreEmisor")
    private String nombreEmisor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "rfcReceptor")
    private String rfcReceptor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombreReceptor")
    private String nombreReceptor;
    @Size(max = 15)
    @Column(name = "folio")
    private String folio;
    @Size(max = 15)
    @Column(name = "serie")
    private String serie;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lugarExpedicion")
    private int lugarExpedicion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaExpedicion")
    @Temporal(TemporalType.DATE)
    private Date fechaExpedicion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "tipoComprobante")
    private String tipoComprobante;
    @Size(max = 45)
    @Column(name = "claveRegimenFiscal")
    private String claveRegimenFiscal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "usoCfdi")
    private String usoCfdi;
    @Size(max = 45)
    @Column(name = "tipoRelacion")
    private String tipoRelacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "formaPago")
    private String formaPago;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "metodoPago")
    private String metodoPago;
    @Size(max = 45)
    @Column(name = "condicionPago")
    private String condicionPago;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "moneda")
    private String moneda;
    @Basic(optional = false)
    @NotNull
    @Column(name = "importe")
    private float importe;
    @Column(name = "rn_gc_cfdis_tbl_Id")
    private Integer rngccfdistblId;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rngcfacturasId")
    private Collection<RnGcFacturaslineasTbl> rnGcFacturaslineasTblCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facturasId")
    private Collection<RnGcPagosTbl> rnGcPagosTblCollection;
    @JoinColumn(name = "rn_gc_personas_tbl_Id", referencedColumnName = "Id")
    @ManyToOne
    private RnGcPersonasTbl rngcpersonastblId;

    public RnGcFacturasTbl() {
    }

    public RnGcFacturasTbl(Integer id) {
        this.id = id;
    }

    public RnGcFacturasTbl(Integer id, String uuid, String rfcEmisor, String nombreEmisor, String rfcReceptor, String nombreReceptor, int lugarExpedicion, Date fechaExpedicion, String tipoComprobante, String usoCfdi, String formaPago, String metodoPago, String moneda, float importe, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.uuid = uuid;
        this.rfcEmisor = rfcEmisor;
        this.nombreEmisor = nombreEmisor;
        this.rfcReceptor = rfcReceptor;
        this.nombreReceptor = nombreReceptor;
        this.lugarExpedicion = lugarExpedicion;
        this.fechaExpedicion = fechaExpedicion;
        this.tipoComprobante = tipoComprobante;
        this.usoCfdi = usoCfdi;
        this.formaPago = formaPago;
        this.metodoPago = metodoPago;
        this.moneda = moneda;
        this.importe = importe;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRfcEmisor() {
        return rfcEmisor;
    }

    public void setRfcEmisor(String rfcEmisor) {
        this.rfcEmisor = rfcEmisor;
    }

    public String getNombreEmisor() {
        return nombreEmisor;
    }

    public void setNombreEmisor(String nombreEmisor) {
        this.nombreEmisor = nombreEmisor;
    }

    public String getRfcReceptor() {
        return rfcReceptor;
    }

    public void setRfcReceptor(String rfcReceptor) {
        this.rfcReceptor = rfcReceptor;
    }

    public String getNombreReceptor() {
        return nombreReceptor;
    }

    public void setNombreReceptor(String nombreReceptor) {
        this.nombreReceptor = nombreReceptor;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getLugarExpedicion() {
        return lugarExpedicion;
    }

    public void setLugarExpedicion(int lugarExpedicion) {
        this.lugarExpedicion = lugarExpedicion;
    }

    public Date getFechaExpedicion() {
        return fechaExpedicion;
    }

    public void setFechaExpedicion(Date fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public String getClaveRegimenFiscal() {
        return claveRegimenFiscal;
    }

    public void setClaveRegimenFiscal(String claveRegimenFiscal) {
        this.claveRegimenFiscal = claveRegimenFiscal;
    }

    public String getUsoCfdi() {
        return usoCfdi;
    }

    public void setUsoCfdi(String usoCfdi) {
        this.usoCfdi = usoCfdi;
    }

    public String getTipoRelacion() {
        return tipoRelacion;
    }

    public void setTipoRelacion(String tipoRelacion) {
        this.tipoRelacion = tipoRelacion;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getCondicionPago() {
        return condicionPago;
    }

    public void setCondicionPago(String condicionPago) {
        this.condicionPago = condicionPago;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

    public Integer getRngccfdistblId() {
        return rngccfdistblId;
    }

    public void setRngccfdistblId(Integer rngccfdistblId) {
        this.rngccfdistblId = rngccfdistblId;
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
    public Collection<RnGcFacturaslineasTbl> getRnGcFacturaslineasTblCollection() {
        return rnGcFacturaslineasTblCollection;
    }

    public void setRnGcFacturaslineasTblCollection(Collection<RnGcFacturaslineasTbl> rnGcFacturaslineasTblCollection) {
        this.rnGcFacturaslineasTblCollection = rnGcFacturaslineasTblCollection;
    }

    @XmlTransient
    public Collection<RnGcPagosTbl> getRnGcPagosTblCollection() {
        return rnGcPagosTblCollection;
    }

    public void setRnGcPagosTblCollection(Collection<RnGcPagosTbl> rnGcPagosTblCollection) {
        this.rnGcPagosTblCollection = rnGcPagosTblCollection;
    }

    public RnGcPersonasTbl getRngcpersonastblId() {
        return rngcpersonastblId;
    }

    public void setRngcpersonastblId(RnGcPersonasTbl rngcpersonastblId) {
        this.rngcpersonastblId = rngcpersonastblId;
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
        if (!(object instanceof RnGcFacturasTbl)) {
            return false;
        }
        RnGcFacturasTbl other = (RnGcFacturasTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcFacturasTbl[ id=" + id + " ]";
    }
    
}
