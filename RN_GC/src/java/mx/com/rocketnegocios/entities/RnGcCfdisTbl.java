/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.entities;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.time.*;
import java.util.TimeZone;
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
@Table(name = "rn_gc_cfdis_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcCfdisTbl.findAll", query = "SELECT r FROM RnGcCfdisTbl r")
    , @NamedQuery(name = "RnGcCfdisTbl.findById", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcCfdisTbl.findByUuid", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.uuid = :uuid")
    , @NamedQuery(name = "RnGcCfdisTbl.findByRfcEmisorPPD", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.rfcEmisor = :rfcEmisor and r.uuid is not null and r.metodoPago = :metodoPago and r.saldoInsoluto > 0")
    , @NamedQuery(name = "RnGcCfdisTbl.findByRfcEmisorUUID", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.rfcEmisor = :rfcEmisor and r.uuid is not null")
    , @NamedQuery(name = "RnGcCfdisTbl.findByRfcEmisor", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.rfcEmisor = :rfcEmisor")
    , @NamedQuery(name = "RnGcCfdisTbl.findByNombreEmisor", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.nombreEmisor = :nombreEmisor")
    , @NamedQuery(name = "RnGcCfdisTbl.findByRfcReceptor", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.rfcReceptor = :rfcReceptor")
    , @NamedQuery(name = "RnGcCfdisTbl.findByNombreReceptor", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.nombreReceptor = :nombreReceptor")
    , @NamedQuery(name = "RnGcCfdisTbl.findByRfcEmisorCreadoPor", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.rfcEmisor = :rfcEmisor AND r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCfdisTbl.findByRfcReceptorCreadoPor", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.rfcReceptor = :rfcReceptor AND r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCfdisTbl.findByFolio", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.folio = :folio")
    , @NamedQuery(name = "RnGcCfdisTbl.findBySerie", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.serie = :serie")
    , @NamedQuery(name = "RnGcCfdisTbl.findByLugarExpedicion", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.lugarExpedicion = :lugarExpedicion")
    , @NamedQuery(name = "RnGcCfdisTbl.findByFechaExpedicion", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.fechaExpedicion = :fechaExpedicion")
    , @NamedQuery(name = "RnGcCfdisTbl.findByTipoComprobante", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.tipoComprobante = :tipoComprobante")
    , @NamedQuery(name = "RnGcCfdisTbl.findByClaveRegimenFiscal", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.claveRegimenFiscal = :claveRegimenFiscal")
    , @NamedQuery(name = "RnGcCfdisTbl.findByUsoCfdi", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.usoCfdi = :usoCfdi")
    , @NamedQuery(name = "RnGcCfdisTbl.findByTipoRelacion", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.tipoRelacion = :tipoRelacion")
    , @NamedQuery(name = "RnGcCfdisTbl.findByFormaPago", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.formaPago = :formaPago")
    , @NamedQuery(name = "RnGcCfdisTbl.findByMetodoPago", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.metodoPago = :metodoPago")
    , @NamedQuery(name = "RnGcCfdisTbl.findByCondicionPago", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.condicionPago = :condicionPago")
    , @NamedQuery(name = "RnGcCfdisTbl.findByMoneda", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.moneda = :moneda")
    , @NamedQuery(name = "RnGcCfdisTbl.findByImporte", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.importe = :importe")
    , @NamedQuery(name = "RnGcCfdisTbl.findByCreadoPor", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCfdisTbl.findByFechaCreacion", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcCfdisTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcCfdisTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcCfdisTbl.findTipoComprobanteCreadoPor", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.tipoComprobante = :tipoComprobante AND r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCfdisTbl.findByCfdisComplementos", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.rfcReceptor = :rfcReceptor AND r.saldoInsoluto != 0 AND r.uuid != 'null' AND r.tipoComprobante != 'P' and r.estatus != 'Cancelado'")
    , @NamedQuery(name = "RnGcCfdisTbl.findByRfcReceptorFecha", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.rfcReceptor = :rfcReceptor and r.fechaExpedicion between :fInicial and :fFinal and r.estatus not in ('Cancelado')")
    , @NamedQuery(name = "RnGcCfdisTbl.findByRfcReceptorPPD", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.rfcReceptor = :rfcReceptor and r.uuid is not null and r.metodoPago = :metodoPago and r.saldoInsoluto > 0")
    , @NamedQuery(name = "RnGcCfdisTbl.findByRfcReceptorCreadoPorGuardado", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.rfcReceptor = :rfcReceptor AND r.creadoPor = :creadoPor AND r.estatus = 'Guardado'")
    , @NamedQuery(name = "RnGcCfdisTbl.findByRfcReceptorCreadoPorPlantilla", query = "SELECT r FROM RnGcCfdisTbl r WHERE r.rfcReceptor = :rfcReceptor AND r.creadoPor = :creadoPor AND r.estatus = 'Timbrado'")})
public class RnGcCfdisTbl implements Serializable {

    @Column(name = "texto")
    private String texto;
    @Size(max = 50)
    @Column(name = "estatus")
    private String estatus;
    @JoinColumn(name = "exportacionId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcExportacionTbl exportacionId;
    @JoinColumn(name = "personaId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcPersonasTbl personaId;
    @Column(name = "subtotal")
    private double subtotal;
    @Column(name = "ivaTotal")
    private double ivaTotal;
    
    @OneToMany(mappedBy = "cfdiId")
    private Collection<RnGcPolizaHeaderTbl> rnGcPolizaHeaderTblCollection;

    @Column(name = "montoPago")
    private Double montoPago;
    @Column(name = "numeroParcialidad")
    private Integer numeroParcialidad;
    @OneToMany(mappedBy = "cfdisId")
    private Collection<RnGcPagosRelacionadosTbl> rnGcPagosRelacionadosTblCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "uuid")
    private String uuid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "rfcEmisor")
    private String rfcEmisor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "nombreEmisor")
    private String nombreEmisor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "rfcReceptor")
    private String rfcReceptor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "nombreReceptor")
    private String nombreReceptor;
    @Column(name = "folio")
    private Integer folio;
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
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaExpedicion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "tipoComprobante")
    private String tipoComprobante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "claveRegimenFiscal")
    private String claveRegimenFiscal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "usoCfdi")
    private String usoCfdi;
    @Size(max = 80)
    @Column(name = "tipoRelacion")
    private String tipoRelacion;
    @Size(min = 1, max = 45)
    @Column(name = "formaPago")
    private String formaPago;
    @Size(min = 1, max = 45)
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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "importe")
    private Double importe;
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
    @OneToMany(mappedBy = "cfdiId")
    /*private Collection<RnGcPaisesTbl> rnGcPaisesTblCollection;
    @OneToMany(mappedBy = "cfdiId")*/
    private Collection<RnGcTiporelacionTbl> rnGcTiporelacionTblCollection;
    @OneToMany(mappedBy = "cfdisId")
    private Collection<RnGcClavesunidadesTbl> rnGcClavesunidadesTblCollection;
    @JoinColumn(name = "periodos_Id", referencedColumnName = "Id")
    @ManyToOne
    private RnGcPeriodosTbl periodosId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cfdisId")
    private Collection<RnGcPeriodosTbl> rnGcPeriodosTblCollection;
    @OneToMany(mappedBy = "cfdiId")
    private Collection<RnGcTasacuotaTbl> rnGcTasacuotaTblCollection;
    @OneToMany(mappedBy = "cfdiId")
    private Collection<RnGcCodigospostalesTbl> rnGcCodigospostalesTblCollection;
    @OneToMany(mappedBy = "cfdiId")
    private Collection<RnGcMonedasTbl> rnGcMonedasTblCollection;
    @OneToMany(mappedBy = "cfdiId")
    private Collection<RnGcArchivosTbl> rnGcArchivosTblCollection;
    @OneToMany(mappedBy = "cfdisId")
    private Collection<RnGcAduanasTbl> rnGcAduanasTblCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cfdisId")
    private Collection<RnGcMetodospagosTbl> rnGcMetodospagosTblCollection;
    @OneToMany(mappedBy = "cfdiId")
    private Collection<RnGcTiposcfdisTbl> rnGcTiposcfdisTblCollection;
    @OneToMany(mappedBy = "cfdiId")
    private Collection<RnGcRegimenfiscalTbl> rnGcRegimenfiscalTblCollection;
    @OneToMany(mappedBy = "cfdisId")
    private Collection<RnGcFormaspagosTbl> rnGcFormaspagosTblCollection;
    @OneToMany(mappedBy = "cfdisId")
    private Collection<RnGcTipofactorTbl> rnGcTipofactorTblCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cfdisId")
    private Collection<RnGcImpuestosTbl> rnGcImpuestosTblCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cfdisId")
    private Collection<RnGcCatalogosconceptosTbl> rnGcCatalogosconceptosTblCollection;
    @OneToMany(mappedBy = "cfdisId")
    private Collection<RnGcProductserviciosTbl> rnGcProductserviciosTblCollection;
    @OneToMany(mappedBy = "cfdiId")
    private Collection<RnGcCatalogosusosTbl> rnGcCatalogosusosTblCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cfdisId")
    private Collection<RnGcCfdisLineasTbl> rnGcCfdisLineasTblCollection;
    @JoinColumn(name = "certificados_Id", referencedColumnName = "Id")
    @ManyToOne
    private RnGcCertificadosTbl certificados_Id;
    @Column(name = "xmlTrama")
    private String xmlTrama;
    @Size(max = 250)
    @Column(name = "respuestaTimbrado")
    private String respuestaTimbrado;
    @Column(name = "tipoCambio")
    private Double tipoCambio;
    @Column(name = "proveedorTimbres")
    private String proveedorTimbres;
    @Column(name = "uuidRelacionado")
    private String uuidRelacionado;
    @Column(name = "saldoPagado")
    private Double saldoPagado;
    @Column(name = "saldoInsoluto")
    private Double saldoInsoluto;
    @Column(name = "saldoPagar")
    private Double saldoPagar;
    @Column(name = "importeLetra")
    private String importeLetra;
    @Column(name = "fechaPago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPago;
    @Column(name = "formaPagoP")
    private String formaPagoP;
    @Column(name = "monedaP")
    private String monedaP;
    @Column(name = "tipoCambioP")
    private String tipoCambioP;
    @Column(name = "numeroOperacion")
    private String numeroOperacion;
    @Column(name = "rfcEmisorCuentaOrigen")
    private String rfcEmisorCuentaOrigen;
    @Column(name = "nombreBanco")
    private String nombreBanco;
    @Column(name = "cuentaOrdenante")
    private String cuentaOrdenante;
    @Column(name = "rfcEmisorCuentaBeneficiaria")
    private String rfcEmisorCuentaBeneficiaria;
    @Column(name = "cuentaBeneficiaria")
    private String cuentaBeneficiaria;
    @Column(name = "tipoCadenaPago")
    private String tipoCadenaPago;
    @Column(name = "certificadoPago")
    private String certificadoPago;
    @Column(name = "cadenaPago")
    private String cadenaPago;
    @Column(name = "selloPago")
    private String selloPago;
    @Column(name = "cantidadPagada")
    private Double cantidadPagada;

    public RnGcCfdisTbl() {
    }

    public RnGcCfdisTbl(Integer id) {
        this.id = id;
    }

    public RnGcCfdisTbl(Integer id, String rfcEmisor, String nombreEmisor, String rfcReceptor, String nombreReceptor, int lugarExpedicion, Date fechaExpedicion, String tipoComprobante, String claveRegimenFiscal, String usoCfdi, String formaPago, String metodoPago, String moneda, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion, RnGcCertificadosTbl certificados_Id, String xmlTrama, String respuestaTimbrado) {
        this.id = id;
        this.rfcEmisor = rfcEmisor;
        this.nombreEmisor = nombreEmisor;
        this.rfcReceptor = rfcReceptor;
        this.nombreReceptor = nombreReceptor;
        this.lugarExpedicion = lugarExpedicion;
        this.fechaExpedicion = fechaExpedicion;
        this.tipoComprobante = tipoComprobante;
        this.claveRegimenFiscal = claveRegimenFiscal;
        this.usoCfdi = usoCfdi;
        this.formaPago = formaPago;
        this.metodoPago = metodoPago;
        this.moneda = moneda;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
        this.certificados_Id = certificados_Id;
        this.xmlTrama = xmlTrama;
        this.respuestaTimbrado = respuestaTimbrado;
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

    public Integer getFolio() {
        return folio;
    }

    public void setFolio(Integer folio) {
        this.folio = folio;
    }

    public String getSerie() {
        if(serie == null){
            this.serie = "-";
        }
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getLugarExpedicion() {
        if(lugarExpedicion == 0)
            this.lugarExpedicion = 0;
        return lugarExpedicion;
    }

    public void setLugarExpedicion(int lugarExpedicion) {
        this.lugarExpedicion = lugarExpedicion;
    }

    public Date getFechaExpedicion() throws ParseException {
        if (fechaExpedicion == null) {
            /*String DATE_FORMAT = "dd-MM-yyyy hh:mm:ss";
            TimeZone timeZone = TimeZone.getTimeZone("America/Mexico_City");
            SimpleDateFormat formatterWithTimeZone = new SimpleDateFormat(DATE_FORMAT);
            formatterWithTimeZone.setTimeZone(timeZone);
            String sDate = formatterWithTimeZone.format(new Date());
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
            Date dateWithTimeZone = formatter.parse(sDate);
            this.fechaExpedicion = dateWithTimeZone;*/
            fechaExpedicion = new Date();
        }
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
        if(claveRegimenFiscal == null)
            this.claveRegimenFiscal = "-";
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

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
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

    public Double getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(Double tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public String getProveedorTimbres() {
        return proveedorTimbres;
    }

    public void setProveedorTimbres(String proveedorTimbres) {
        this.proveedorTimbres = proveedorTimbres;
    }

    public String getUuidRelacionado() {
        return uuidRelacionado;
    }

    public void setUuidRelacionado(String uuidRelacionado) {
        this.uuidRelacionado = uuidRelacionado;
    }

    public Double getSaldoPagar() {
        return saldoPagar;
    }

    public void setSaldoPagar(Double saldoPagar) {
        this.saldoPagar = saldoPagar;
    }

    public String getImporteLetra() {
        return importeLetra;
    }

    public void setImporteLetra(String importeLetra) {
        this.importeLetra = importeLetra;
    }

    public Double getCantidadPagada() {
        if(cantidadPagada == null)
            this.cantidadPagada = 0.0;
        return cantidadPagada;
    }

    public void setCantidadPagada(Double cantidadPagada) {
        this.cantidadPagada = cantidadPagada;
    }

    /*@XmlTransient
    public Collection<RnGcPaisesTbl> getRnGcPaisesTblCollection() {
        return rnGcPaisesTblCollection;
    }

    public void setRnGcPaisesTblCollection(Collection<RnGcPaisesTbl> rnGcPaisesTblCollection) {
        this.rnGcPaisesTblCollection = rnGcPaisesTblCollection;
    }*/

    @XmlTransient
    public Collection<RnGcTiporelacionTbl> getRnGcTiporelacionTblCollection() {
        return rnGcTiporelacionTblCollection;
    }

    public void setRnGcTiporelacionTblCollection(Collection<RnGcTiporelacionTbl> rnGcTiporelacionTblCollection) {
        this.rnGcTiporelacionTblCollection = rnGcTiporelacionTblCollection;
    }

    @XmlTransient
    public Collection<RnGcClavesunidadesTbl> getRnGcClavesunidadesTblCollection() {
        return rnGcClavesunidadesTblCollection;
    }

    public void setRnGcClavesunidadesTblCollection(Collection<RnGcClavesunidadesTbl> rnGcClavesunidadesTblCollection) {
        this.rnGcClavesunidadesTblCollection = rnGcClavesunidadesTblCollection;
    }

    public RnGcPeriodosTbl getPeriodosId() {
        return periodosId;
    }

    public void setPeriodosId(RnGcPeriodosTbl periodosId) {
        this.periodosId = periodosId;
    }

    public RnGcCertificadosTbl getCertificados_Id() {
        if(certificados_Id == null)
            this.certificados_Id = new RnGcCertificadosTbl();
        return certificados_Id;
    }

    public void setCertificados_Id(RnGcCertificadosTbl certificados_Id) {
        this.certificados_Id = certificados_Id;
    }

    public String getXmlTrama() {
        return xmlTrama;
    }

    public void setXmlTrama(String xmlTrama) {
        this.xmlTrama = xmlTrama;
    }

    public String getRespuestaTimbrado() {
        return respuestaTimbrado;
    }

    public void setRespuestaTimbrado(String respuestaTimbrado) {
        this.respuestaTimbrado = respuestaTimbrado;
    }

    public Double getSaldoPagado() {
        if (saldoPagado == null) {
            this.saldoPagado = 0.0;
        }
        return saldoPagado;
    }

    public void setSaldoPagado(Double saldoPagado) {
        this.saldoPagado = saldoPagado;
    }

    public Double getSaldoInsoluto() {
        if (saldoInsoluto == null) {
            this.saldoInsoluto = 0.0;
        }
        return saldoInsoluto;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getFormaPagoP() {
        return formaPagoP;
    }

    public void setFormaPagoP(String formaPagoP) {
        this.formaPagoP = formaPagoP;
    }

    public String getMonedaP() {
        return monedaP;
    }

    public void setMonedaP(String monedaP) {
        this.monedaP = monedaP;
    }

    public String getTipoCambioP() {
        return tipoCambioP;
    }

    public void setTipoCambioP(String tipoCambioP) {
        this.tipoCambioP = tipoCambioP;
    }

    public String getNumeroOperacion() {
        return numeroOperacion;
    }

    public void setNumeroOperacion(String numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }

    public String getRfcEmisorCuentaOrigen() {
        return rfcEmisorCuentaOrigen;
    }

    public void setRfcEmisorCuentaOrigen(String rfcEmisorCuentaOrigen) {
        this.rfcEmisorCuentaOrigen = rfcEmisorCuentaOrigen;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public String getCuentaOrdenante() {
        return cuentaOrdenante;
    }

    public void setCuentaOrdenante(String cuentaOrdenante) {
        this.cuentaOrdenante = cuentaOrdenante;
    }

    public String getRfcEmisorCuentaBeneficiaria() {
        return rfcEmisorCuentaBeneficiaria;
    }

    public void setRfcEmisorCuentaBeneficiaria(String rfcEmisorCuentaBeneficiaria) {
        this.rfcEmisorCuentaBeneficiaria = rfcEmisorCuentaBeneficiaria;
    }

    public String getTipoCadenaPago() {
        return tipoCadenaPago;
    }

    public void setTipoCadenaPago(String tipoCadenaPago) {
        this.tipoCadenaPago = tipoCadenaPago;
    }

    public String getCertificadoPago() {
        return certificadoPago;
    }

    public void setCertificadoPago(String certificadoPago) {
        this.certificadoPago = certificadoPago;
    }

    public String getCuentaBeneficiaria() {
        return cuentaBeneficiaria;
    }

    public void setCuentaBeneficiaria(String cuentaBeneficiaria) {
        this.cuentaBeneficiaria = cuentaBeneficiaria;
    }

    public String getCadenaPago() {
        return cadenaPago;
    }

    public void setCadenaPago(String cadenaPago) {
        this.cadenaPago = cadenaPago;
    }

    public String getSelloPago() {
        return selloPago;
    }

    public void setSelloPago(String selloPago) {
        this.selloPago = selloPago;
    }

    public void setSaldoInsoluto(Double saldoInsoluto) {
        this.saldoInsoluto = saldoInsoluto;
    }

    @XmlTransient
    public Collection<RnGcPeriodosTbl> getRnGcPeriodosTblCollection() {
        return rnGcPeriodosTblCollection;
    }

    public void setRnGcPeriodosTblCollection(Collection<RnGcPeriodosTbl> rnGcPeriodosTblCollection) {
        this.rnGcPeriodosTblCollection = rnGcPeriodosTblCollection;
    }

    @XmlTransient
    public Collection<RnGcTasacuotaTbl> getRnGcTasacuotaTblCollection() {
        return rnGcTasacuotaTblCollection;
    }

    public void setRnGcTasacuotaTblCollection(Collection<RnGcTasacuotaTbl> rnGcTasacuotaTblCollection) {
        this.rnGcTasacuotaTblCollection = rnGcTasacuotaTblCollection;
    }

    @XmlTransient
    public Collection<RnGcCodigospostalesTbl> getRnGcCodigospostalesTblCollection() {
        return rnGcCodigospostalesTblCollection;
    }

    public void setRnGcCodigospostalesTblCollection(Collection<RnGcCodigospostalesTbl> rnGcCodigospostalesTblCollection) {
        this.rnGcCodigospostalesTblCollection = rnGcCodigospostalesTblCollection;
    }

    @XmlTransient
    public Collection<RnGcMonedasTbl> getRnGcMonedasTblCollection() {
        return rnGcMonedasTblCollection;
    }

    public void setRnGcMonedasTblCollection(Collection<RnGcMonedasTbl> rnGcMonedasTblCollection) {
        this.rnGcMonedasTblCollection = rnGcMonedasTblCollection;
    }

    @XmlTransient
    public Collection<RnGcArchivosTbl> getRnGcArchivosTblCollection() {
        return rnGcArchivosTblCollection;
    }

    public void setRnGcArchivosTblCollection(Collection<RnGcArchivosTbl> rnGcArchivosTblCollection) {
        this.rnGcArchivosTblCollection = rnGcArchivosTblCollection;
    }

    @XmlTransient
    public Collection<RnGcAduanasTbl> getRnGcAduanasTblCollection() {
        return rnGcAduanasTblCollection;
    }

    public void setRnGcAduanasTblCollection(Collection<RnGcAduanasTbl> rnGcAduanasTblCollection) {
        this.rnGcAduanasTblCollection = rnGcAduanasTblCollection;
    }

    @XmlTransient
    public Collection<RnGcMetodospagosTbl> getRnGcMetodospagosTblCollection() {
        return rnGcMetodospagosTblCollection;
    }

    public void setRnGcMetodospagosTblCollection(Collection<RnGcMetodospagosTbl> rnGcMetodospagosTblCollection) {
        this.rnGcMetodospagosTblCollection = rnGcMetodospagosTblCollection;
    }

    @XmlTransient
    public Collection<RnGcTiposcfdisTbl> getRnGcTiposcfdisTblCollection() {
        return rnGcTiposcfdisTblCollection;
    }

    public void setRnGcTiposcfdisTblCollection(Collection<RnGcTiposcfdisTbl> rnGcTiposcfdisTblCollection) {
        this.rnGcTiposcfdisTblCollection = rnGcTiposcfdisTblCollection;
    }

    @XmlTransient
    public Collection<RnGcRegimenfiscalTbl> getRnGcRegimenfiscalTblCollection() {
        return rnGcRegimenfiscalTblCollection;
    }

    public void setRnGcRegimenfiscalTblCollection(Collection<RnGcRegimenfiscalTbl> rnGcRegimenfiscalTblCollection) {
        this.rnGcRegimenfiscalTblCollection = rnGcRegimenfiscalTblCollection;
    }

    @XmlTransient
    public Collection<RnGcFormaspagosTbl> getRnGcFormaspagosTblCollection() {
        return rnGcFormaspagosTblCollection;
    }

    public void setRnGcFormaspagosTblCollection(Collection<RnGcFormaspagosTbl> rnGcFormaspagosTblCollection) {
        this.rnGcFormaspagosTblCollection = rnGcFormaspagosTblCollection;
    }

    @XmlTransient
    public Collection<RnGcTipofactorTbl> getRnGcTipofactorTblCollection() {
        return rnGcTipofactorTblCollection;
    }

    public void setRnGcTipofactorTblCollection(Collection<RnGcTipofactorTbl> rnGcTipofactorTblCollection) {
        this.rnGcTipofactorTblCollection = rnGcTipofactorTblCollection;
    }

    @XmlTransient
    public Collection<RnGcImpuestosTbl> getRnGcImpuestosTblCollection() {
        return rnGcImpuestosTblCollection;
    }

    public void setRnGcImpuestosTblCollection(Collection<RnGcImpuestosTbl> rnGcImpuestosTblCollection) {
        this.rnGcImpuestosTblCollection = rnGcImpuestosTblCollection;
    }

    @XmlTransient
    public Collection<RnGcCatalogosconceptosTbl> getRnGcCatalogosconceptosTblCollection() {
        return rnGcCatalogosconceptosTblCollection;
    }

    public void setRnGcCatalogosconceptosTblCollection(Collection<RnGcCatalogosconceptosTbl> rnGcCatalogosconceptosTblCollection) {
        this.rnGcCatalogosconceptosTblCollection = rnGcCatalogosconceptosTblCollection;
    }

    @XmlTransient
    public Collection<RnGcProductserviciosTbl> getRnGcProductserviciosTblCollection() {
        return rnGcProductserviciosTblCollection;
    }

    public void setRnGcProductserviciosTblCollection(Collection<RnGcProductserviciosTbl> rnGcProductserviciosTblCollection) {
        this.rnGcProductserviciosTblCollection = rnGcProductserviciosTblCollection;
    }

    @XmlTransient
    public Collection<RnGcCatalogosusosTbl> getRnGcCatalogosusosTblCollection() {
        return rnGcCatalogosusosTblCollection;
    }

    public void setRnGcCatalogosusosTblCollection(Collection<RnGcCatalogosusosTbl> rnGcCatalogosusosTblCollection) {
        this.rnGcCatalogosusosTblCollection = rnGcCatalogosusosTblCollection;
    }

    @XmlTransient
    public Collection<RnGcCfdisLineasTbl> getRnGcCfdisLineasTblCollection() {
        return rnGcCfdisLineasTblCollection;
    }

    public void setRnGcCfdisLineasTblCollection(Collection<RnGcCfdisLineasTbl> rnGcCfdisLineasTblCollection) {
        this.rnGcCfdisLineasTblCollection = rnGcCfdisLineasTblCollection;
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
        if (!(object instanceof RnGcCfdisTbl)) {
            return false;
        }
        RnGcCfdisTbl other = (RnGcCfdisTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcCfdisTbl[ id=" + id + " ]";
    }

    public Double getMontoPago() {
        return montoPago;
    }

    public void setMontoPago(Double montoPago) {
        this.montoPago = montoPago;
    }

    public Integer getNumeroParcialidad() {
        return numeroParcialidad;
    }

    public void setNumeroParcialidad(Integer numeroParcialidad) {
        this.numeroParcialidad = numeroParcialidad;
    }

    @XmlTransient
    public Collection<RnGcPagosRelacionadosTbl> getRnGcPagosRelacionadosTblCollection() {
        return rnGcPagosRelacionadosTblCollection;
    }

    public void setRnGcPagosRelacionadosTblCollection(Collection<RnGcPagosRelacionadosTbl> rnGcPagosRelacionadosTblCollection) {
        this.rnGcPagosRelacionadosTblCollection = rnGcPagosRelacionadosTblCollection;
    }

    @XmlTransient
    public Collection<RnGcPolizaHeaderTbl> getRnGcPolizaHeaderTblCollection() {
        return rnGcPolizaHeaderTblCollection;
    }

    public void setRnGcPolizaHeaderTblCollection(Collection<RnGcPolizaHeaderTbl> rnGcPolizaHeaderTblCollection) {
        this.rnGcPolizaHeaderTblCollection = rnGcPolizaHeaderTblCollection;
    }

    public RnGcPersonasTbl getPersonaId() {
        return personaId;
    }

    public void setPersonaId(RnGcPersonasTbl personaId) {
        this.personaId = personaId;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIvaTotal() {
        return ivaTotal;
    }

    public void setIvaTotal(double ivaTotal) {
        this.ivaTotal = ivaTotal;
    }

    public RnGcExportacionTbl getExportacionId() {
        return exportacionId;
    }

    public void setExportacionId(RnGcExportacionTbl exportacionId) {
        this.exportacionId = exportacionId;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

}
