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
@Table(name = "rn_gc_catalogo_cuentas_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcCatalogoCuentasTbl.findAll", query = "SELECT r FROM RnGcCatalogoCuentasTbl r")
    , @NamedQuery(name = "RnGcCatalogoCuentasTbl.findById", query = "SELECT r FROM RnGcCatalogoCuentasTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcCatalogoCuentasTbl.findByNumeroCuenta", query = "SELECT r FROM RnGcCatalogoCuentasTbl r WHERE r.numeroCuenta = :numeroCuenta")
    , @NamedQuery(name = "RnGcCatalogoCuentasTbl.findByNumeroCuenta2", query = "SELECT r FROM RnGcCatalogoCuentasTbl r WHERE r.numeroCuenta = :numeroCuenta and r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCatalogoCuentasTbl.findByDescripcionCuenta", query = "SELECT r FROM RnGcCatalogoCuentasTbl r WHERE r.descripcionCuenta = :descripcionCuenta")
    , @NamedQuery(name = "RnGcCatalogoCuentasTbl.findBySubCuenta", query = "SELECT r FROM RnGcCatalogoCuentasTbl r WHERE r.subCuenta = :subCuenta")
    , @NamedQuery(name = "RnGcCatalogoCuentasTbl.findByNaturaleza", query = "SELECT r FROM RnGcCatalogoCuentasTbl r WHERE r.naturaleza = :naturaleza")
    , @NamedQuery(name = "RnGcCatalogoCuentasTbl.findByVersion", query = "SELECT r FROM RnGcCatalogoCuentasTbl r WHERE r.version = :version")
    , @NamedQuery(name = "RnGcCatalogoCuentasTbl.findByRfc", query = "SELECT r FROM RnGcCatalogoCuentasTbl r WHERE r.rfc = :rfc")
    , @NamedQuery(name = "RnGcCatalogoCuentasTbl.findByInicioVigencia", query = "SELECT r FROM RnGcCatalogoCuentasTbl r WHERE r.inicioVigencia = :inicioVigencia")
    , @NamedQuery(name = "RnGcCatalogoCuentasTbl.findByAdicional1", query = "SELECT r FROM RnGcCatalogoCuentasTbl r WHERE r.adicional1 = :adicional1")
    , @NamedQuery(name = "RnGcCatalogoCuentasTbl.findByAdicional2", query = "SELECT r FROM RnGcCatalogoCuentasTbl r WHERE r.adicional2 = :adicional2")
    , @NamedQuery(name = "RnGcCatalogoCuentasTbl.findByMonedaId", query = "SELECT r FROM RnGcCatalogoCuentasTbl r WHERE r.monedaId = :monedaId")
    , @NamedQuery(name = "RnGcCatalogoCuentasTbl.findByTipo", query = "SELECT r FROM RnGcCatalogoCuentasTbl r WHERE r.tipo = :tipo")
    , @NamedQuery(name = "RnGcCatalogoCuentasTbl.findBySubtipo", query = "SELECT r FROM RnGcCatalogoCuentasTbl r WHERE r.subtipo = :subtipo")
    , @NamedQuery(name = "RnGcCatalogoCuentasTbl.findByAdicional6", query = "SELECT r FROM RnGcCatalogoCuentasTbl r WHERE r.adicional6 = :adicional6")
    , @NamedQuery(name = "RnGcCatalogoCuentasTbl.findByCreadoPor", query = "SELECT r FROM RnGcCatalogoCuentasTbl r WHERE r.creadoPor = :creadoPor order by r.codigoAgrupadorSatId.codigoAgrupador, r.numeroCuenta asc")
    , @NamedQuery(name = "RnGcCatalogoCuentasTbl.findByCreadoPorCodigoAgrupador", query = "SELECT r FROM RnGcCatalogoCuentasTbl r WHERE r.creadoPor = :creadoPor and r.codigoAgrupadorSatId = :codigoAgrupador order by r.adicional2, r.numeroCuenta asc")
    , @NamedQuery(name = "RnGcCatalogoCuentasTbl.findByFechaCreacion", query = "SELECT r FROM RnGcCatalogoCuentasTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcCatalogoCuentasTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcCatalogoCuentasTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcCatalogoCuentasTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcCatalogoCuentasTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcCatalogoCuentasTbl.findByDesCuenta", query = "SELECT r FROM RnGcCatalogoCuentasTbl r WHERE r.descripcionCuenta = :descripcionCuenta and r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCatalogoCuentasTbl.findByRfcUser", query = "SELECT r FROM RnGcCatalogoCuentasTbl r WHERE r.rfc = :rfc and r.creadoPor = :creadoPor and r.adicional1 = :diot")})
public class RnGcCatalogoCuentasTbl implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catalogoCuentasId")
    private Collection<RnGcPolizaLineasTbl> rnGcPolizaLineasTblCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "numeroCuenta")
    private String numeroCuenta;
    @Basic(optional = false)
    @Column(name = "descripcionCuenta")
    private String descripcionCuenta;
    @Column(name = "subCuenta")
    private double subCuenta;
    @Basic(optional = false)
    @Column(name = "naturaleza")
    private String naturaleza;
    @Size(max = 45)
    @Column(name = "version")
    private String version;
    @Basic(optional = false)
    @Column(name = "rfc")
    private String rfc;
    @Basic(optional = false)
    @Column(name = "inicioVigencia")
    @Temporal(TemporalType.DATE)
    private Date inicioVigencia;
    @Column(name = "adicional1")
    private boolean adicional1;
    @Size(max = 45)
    @Column(name = "adicional2")
    private String adicional2;
    @JoinColumn(name = "monedaId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RnGcMonedasTbl monedaId;
    @Size(max = 45)
    @Column(name = "tipo")
    private String tipo;
    @Size(max = 45)
    @Column(name = "subtipo")
    private String subtipo;
    @Size(max = 45)
    @Column(name = "adicional6")
    private String adicional6;
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
    @JoinColumn(name = "codigo_agrupador_sat_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RnGcCodigoAgrupadorSatTbl codigoAgrupadorSatId;

    public RnGcCatalogoCuentasTbl() {
    }

    public RnGcCatalogoCuentasTbl(Integer id) {
        this.id = id;
    }

    public RnGcCatalogoCuentasTbl(Integer id, String numeroCuenta, String descripcionCuenta, String naturaleza, String rfc, Date inicioVigencia, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.descripcionCuenta = descripcionCuenta;
        this.naturaleza = naturaleza;
        this.rfc = rfc;
        this.inicioVigencia = inicioVigencia;
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

    public double getSubCuenta() {
        return subCuenta;
    }

    public void setSubCuenta(double subCuenta) {
        this.subCuenta = subCuenta;
    }

    public String getNaturaleza() {
        return naturaleza;
    }

    public void setNaturaleza(String naturaleza) {
        this.naturaleza = naturaleza;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Date getInicioVigencia() {
        return inicioVigencia;
    }

    public void setInicioVigencia(Date inicioVigencia) {
        this.inicioVigencia = inicioVigencia;
    }

    public boolean getAdicional1() {
        return adicional1;
    }

    public void setAdicional1(boolean adicional1) {
        this.adicional1 = adicional1;
    }

    public String getAdicional2() {
        return adicional2;
    }

    public void setAdicional2(String adicional2) {
        this.adicional2 = adicional2;
    }

    public RnGcMonedasTbl getMonedaId() {
        return monedaId;
    }

    public void setMonedaId(RnGcMonedasTbl monedaId) {
        this.monedaId = monedaId;
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

    public void setSubtipo(String subtipo) {
        this.subtipo = subtipo;
    }

    public String getAdicional6() {
        return adicional6;
    }

    public void setAdicional6(String adicional6) {
        this.adicional6 = adicional6;
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

    public RnGcCodigoAgrupadorSatTbl getCodigoAgrupadorSatId() {
        return codigoAgrupadorSatId;
    }

    public void setCodigoAgrupadorSatId(RnGcCodigoAgrupadorSatTbl codigoAgrupadorSatId) {
        this.codigoAgrupadorSatId = codigoAgrupadorSatId;
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
        if (!(object instanceof RnGcCatalogoCuentasTbl)) {
            return false;
        }
        RnGcCatalogoCuentasTbl other = (RnGcCatalogoCuentasTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
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
        return rnGcPolizaLineasTblCollection;
    }

    public void setRnGcPolizaLineasTblCollection(Collection<RnGcPolizaLineasTbl> rnGcPolizaLineasTblCollection) {
        this.rnGcPolizaLineasTblCollection = rnGcPolizaLineasTblCollection;
    }

}
