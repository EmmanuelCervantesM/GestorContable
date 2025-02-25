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
@Table(name = "rn_gc_transacciones_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcTransaccionesTbl.findAll", query = "SELECT r FROM RnGcTransaccionesTbl r")
    , @NamedQuery(name = "RnGcTransaccionesTbl.findById", query = "SELECT r FROM RnGcTransaccionesTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcTransaccionesTbl.findByUuid", query = "SELECT r FROM RnGcTransaccionesTbl r WHERE r.uuid = :uuid")
    , @NamedQuery(name = "RnGcTransaccionesTbl.findByRfcEmisor", query = "SELECT r FROM RnGcTransaccionesTbl r WHERE r.rfcEmisor = :rfcEmisor")
    , @NamedQuery(name = "RnGcTransaccionesTbl.findByNombreEmisor", query = "SELECT r FROM RnGcTransaccionesTbl r WHERE r.nombreEmisor = :nombreEmisor")
    , @NamedQuery(name = "RnGcTransaccionesTbl.findByRfcReceptor", query = "SELECT r FROM RnGcTransaccionesTbl r WHERE r.rfcReceptor = :rfcReceptor")
    , @NamedQuery(name = "RnGcTransaccionesTbl.findByNombreReceptor", query = "SELECT r FROM RnGcTransaccionesTbl r WHERE r.nombreReceptor = :nombreReceptor")
    , @NamedQuery(name = "RnGcTransaccionesTbl.findByFolio", query = "SELECT r FROM RnGcTransaccionesTbl r WHERE r.folio = :folio")
    , @NamedQuery(name = "RnGcTransaccionesTbl.findBySerie", query = "SELECT r FROM RnGcTransaccionesTbl r WHERE r.serie = :serie")
    , @NamedQuery(name = "RnGcTransaccionesTbl.findByLugarExpedicion", query = "SELECT r FROM RnGcTransaccionesTbl r WHERE r.lugarExpedicion = :lugarExpedicion")
    , @NamedQuery(name = "RnGcTransaccionesTbl.findByFechaExpedicion", query = "SELECT r FROM RnGcTransaccionesTbl r WHERE r.fechaExpedicion = :fechaExpedicion")
    , @NamedQuery(name = "RnGcTransaccionesTbl.findByTipoComprobante", query = "SELECT r FROM RnGcTransaccionesTbl r WHERE r.tipoComprobante = :tipoComprobante")
    , @NamedQuery(name = "RnGcTransaccionesTbl.findByClaveRegimenFiscal", query = "SELECT r FROM RnGcTransaccionesTbl r WHERE r.claveRegimenFiscal = :claveRegimenFiscal")
    , @NamedQuery(name = "RnGcTransaccionesTbl.findByUsoCfdi", query = "SELECT r FROM RnGcTransaccionesTbl r WHERE r.usoCfdi = :usoCfdi")
    , @NamedQuery(name = "RnGcTransaccionesTbl.findByTipoRelacion", query = "SELECT r FROM RnGcTransaccionesTbl r WHERE r.tipoRelacion = :tipoRelacion")
    , @NamedQuery(name = "RnGcTransaccionesTbl.findByFormaPago", query = "SELECT r FROM RnGcTransaccionesTbl r WHERE r.formaPago = :formaPago")
    , @NamedQuery(name = "RnGcTransaccionesTbl.findByMetodoPago", query = "SELECT r FROM RnGcTransaccionesTbl r WHERE r.metodoPago = :metodoPago")
    , @NamedQuery(name = "RnGcTransaccionesTbl.findByCondicionPago", query = "SELECT r FROM RnGcTransaccionesTbl r WHERE r.condicionPago = :condicionPago")
    , @NamedQuery(name = "RnGcTransaccionesTbl.findByMoneda", query = "SELECT r FROM RnGcTransaccionesTbl r WHERE r.moneda = :moneda")
    , @NamedQuery(name = "RnGcTransaccionesTbl.findByImporte", query = "SELECT r FROM RnGcTransaccionesTbl r WHERE r.importe = :importe")
    , @NamedQuery(name = "RnGcTransaccionesTbl.findByRngccfdistblId", query = "SELECT r FROM RnGcTransaccionesTbl r WHERE r.rngccfdistblId = :rngccfdistblId")
    , @NamedQuery(name = "RnGcTransaccionesTbl.findByCreadoPor", query = "SELECT r FROM RnGcTransaccionesTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcTransaccionesTbl.findByFechaCreacion", query = "SELECT r FROM RnGcTransaccionesTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcTransaccionesTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcTransaccionesTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcTransaccionesTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcTransaccionesTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcTransaccionesTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "uuid")
    private String uuid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "rfcEmisor")
    private String rfcEmisor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
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
    @Size(min = 1, max = 45)
    @Column(name = "usoCfdi")
    private String usoCfdi;
    @Size(max = 45)
    @Column(name = "tipoRelacion")
    private String tipoRelacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
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
    @Size(min = 1, max = 45)
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rngctransaccionestblId1")
    private Collection<RnGcRecibosTbl> rnGcRecibosTblCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transaccionesId")
    private Collection<RnGcTrxlineasTbl> rnGcTrxlineasTblCollection;
    @JoinColumn(name = "rn_gc_personas_tbl_Id", referencedColumnName = "Id")
    @ManyToOne
    private RnGcPersonasTbl rngcpersonastblId;

    public RnGcTransaccionesTbl() {
    }

    public RnGcTransaccionesTbl(Integer id) {
        this.id = id;
    }

    public RnGcTransaccionesTbl(Integer id, String uuid, String rfcEmisor, String nombreEmisor, String rfcReceptor, String nombreReceptor, int lugarExpedicion, Date fechaExpedicion, String tipoComprobante, String usoCfdi, String formaPago, String metodoPago, String moneda, float importe, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
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
    public Collection<RnGcRecibosTbl> getRnGcRecibosTblCollection() {
        return rnGcRecibosTblCollection;
    }

    public void setRnGcRecibosTblCollection(Collection<RnGcRecibosTbl> rnGcRecibosTblCollection) {
        this.rnGcRecibosTblCollection = rnGcRecibosTblCollection;
    }

    @XmlTransient
    public Collection<RnGcTrxlineasTbl> getRnGcTrxlineasTblCollection() {
        return rnGcTrxlineasTblCollection;
    }

    public void setRnGcTrxlineasTblCollection(Collection<RnGcTrxlineasTbl> rnGcTrxlineasTblCollection) {
        this.rnGcTrxlineasTblCollection = rnGcTrxlineasTblCollection;
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
        if (!(object instanceof RnGcTransaccionesTbl)) {
            return false;
        }
        RnGcTransaccionesTbl other = (RnGcTransaccionesTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcTransaccionesTbl[ id=" + id + " ]";
    }
    
}
