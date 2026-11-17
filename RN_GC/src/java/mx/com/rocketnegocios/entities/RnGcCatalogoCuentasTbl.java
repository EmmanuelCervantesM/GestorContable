package mx.com.rocketnegocios.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "rn_gc_catalogo_cuentas_tbl")
@NamedQueries({
    @NamedQuery(
        name = "RnGcCatalogoCuentasTbl.findAll",
        query = "SELECT c FROM RnGcCatalogoCuentasTbl c"
    ),
    @NamedQuery(
        name = "RnGcCatalogoCuentasTbl.findByCreadoPor",
        query = "SELECT c FROM RnGcCatalogoCuentasTbl c WHERE c.creadoPor = :creadoPor"
    ),
    @NamedQuery(
        name = "RnGcCatalogoCuentasTbl.findByNumeroCuenta2",
        query = "SELECT c FROM RnGcCatalogoCuentasTbl c WHERE c.numeroCuenta = :numeroCuenta AND c.creadoPor = :creadoPor"
    ),
    @NamedQuery(
        name = "RnGcCatalogoCuentasTbl.findByDesCuenta",
        query = "SELECT c FROM RnGcCatalogoCuentasTbl c WHERE c.descripcionCuenta = :descripcionCuenta AND c.creadoPor = :creadoPor"
    ),
    @NamedQuery(
        name = "RnGcCatalogoCuentasTbl.findByRfcUser",
        query = "SELECT c FROM RnGcCatalogoCuentasTbl c WHERE c.rfc = :rfc AND c.creadoPor = :creadoPor AND c.adicional1 = :diot"
    )
})
public class RnGcCatalogoCuentasTbl implements Serializable {

    private static final long serialVersionUID = 1L;

    // ===== PK =====
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    // ===== CAMPOS PRINCIPALES =====

    @Size(max = 45)
    @Column(name = "numeroCuenta")
    private String numeroCuenta;

    @Size(max = 255)
    @Column(name = "descripcionCuenta")
    private String descripcionCuenta;

    @Size(max = 45)
    @Column(name = "naturaleza")
    private String naturaleza;

    @Size(max = 45)
    @Column(name = "tipo")
    private String tipo;

    @Size(max = 45)
    @Column(name = "subtipo")
    private String subtipo;

    @Column(name = "subCuenta")
    private double subCuenta;
    
    @Column(name = "saldoInicial")
    private BigDecimal saldoInicial;

    @Column(name = "saldoActual")
    private BigDecimal saldoActual;

    @Column(name = "monedaId")
    private Integer moneda;

    @Size(max = 13)
    @Column(name = "rfc")
    private String rfc;

    @Column(name = "adicional1", length = 5) // "TRUE"/"FALSE"
    private String adicional1;

    @Column(name = "adicional2")
    private String adicional2;
    
    @Column(name = "adicional6")
    private String adicional6;

    @Column(name = "id_periodo")
    private Integer idPeriodo;

    @Column(name = "creadoPor")
    private Integer creadoPor;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "inicioVigencia")
    private Date inicioVigencia;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaCreacion")
    private Date fechaCreacion;

    @Column(name = "ultimaActualizacionPor")
    private Integer ultimaActualizacionPor;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ultimaFechaActualizacion")
    private Date ultimaFechaActualizacion;
    @JoinColumn(name = "codigo_agrupador_sat_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    
    private RnGcCodigoAgrupadorSatTbl codigoAgrupadorSatId;
    private Collection<RnGcPolizaLineasTbl> rnGcPolizaLineasTblCollection;

    // ===== CONSTRUCTORES =====

    public RnGcCatalogoCuentasTbl() {
    }

    public RnGcCatalogoCuentasTbl(Integer id) {
        this.id = id;
    }

    // ===== GETTERS / SETTERS =====

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getDescripcionCuenta() {
        return descripcionCuenta;
    }

    public void setDescripcionCuenta(String descripcionCuenta) {
        this.descripcionCuenta = descripcionCuenta;
    }

    public String getNaturaleza() {
        return naturaleza;
    }

    public void setNaturaleza(String naturaleza) {
        this.naturaleza = naturaleza;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSubtipo() {
        return subtipo;
    }

    public double getSubCuenta() {
        return subCuenta;
    }

    public void setSubCuenta(double subCuenta) {
        this.subCuenta = subCuenta;
    }

    public void setSubCuenta(int subCuenta) {
        this.subCuenta = subCuenta;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public BigDecimal getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(BigDecimal saldoActual) {
        this.saldoActual = saldoActual;
    }

    public void setSubtipo(String subtipo) {
        this.subtipo = subtipo;
    }

    public Integer getMoneda() {
        return moneda;
    }

    public void setMoneda(Integer moneda) {
        this.moneda = moneda;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
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
    
    public String getAdicional6() {
        return adicional6;
    }

    public void setAdicional6(String adicional2) {
        this.adicional6 = adicional6;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
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

    public Date getInicioVigencia() {
        return inicioVigencia;
    }

    public void setInicioVigencia(Date inicioVigencia) {
        this.inicioVigencia = inicioVigencia;
    }

    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RnGcCatalogoCuentasTbl)) {
            return false;
        }
        RnGcCatalogoCuentasTbl other = (RnGcCatalogoCuentasTbl) object;
        if ((this.id == null && other.id != null)
                || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcCatalogoCuentasTbl[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<RnGcPolizaLineasTbl> getRnGcPolizaLineasTblCollection() {
        return (Collection<RnGcPolizaLineasTbl>) rnGcPolizaLineasTblCollection;
}

    public void setRnGcPolizaLineasTblCollection(Collection<RnGcPolizaLineasTbl> rnGcPolizaLineasTblCollection) {
        this.rnGcPolizaLineasTblCollection = rnGcPolizaLineasTblCollection;
    }

    public RnGcCodigoAgrupadorSatTbl getCodigoAgrupadorSatId() {
        return codigoAgrupadorSatId;
    }

    public void setCodigoAgrupadorSatId(RnGcCodigoAgrupadorSatTbl codigoAgrupadorSatId) {
        this.codigoAgrupadorSatId = codigoAgrupadorSatId;
    }
    
    @PrePersist
    public void prePersist() {
        Date ahora = new Date();
        if (fechaCreacion == null) fechaCreacion = ahora;
        if (inicioVigencia == null) inicioVigencia = ahora;
        if (adicional1 == null) adicional1 = "FALSE"; 
    }

    @PreUpdate
    public void preUpdate() {
        if (ultimaFechaActualizacion == null) ultimaFechaActualizacion = new Date();
    }

   
}
