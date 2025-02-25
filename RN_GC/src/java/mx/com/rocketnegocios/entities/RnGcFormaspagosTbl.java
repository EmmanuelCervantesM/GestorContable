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
@Table(name = "rn_gc_formaspagos_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcFormaspagosTbl.findAll", query = "SELECT r FROM RnGcFormaspagosTbl r")
    , @NamedQuery(name = "RnGcFormaspagosTbl.findByCreadoPor", query = "SELECT r FROM RnGcFormaspagosTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcFormaspagosTbl.findByFechaCreacion", query = "SELECT r FROM RnGcFormaspagosTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcFormaspagosTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcFormaspagosTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcFormaspagosTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcFormaspagosTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcFormaspagosTbl.findById", query = "SELECT r FROM RnGcFormaspagosTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcFormaspagosTbl.findByCFormaPago", query = "SELECT r FROM RnGcFormaspagosTbl r WHERE r.cFormaPago = :cFormaPago")
    , @NamedQuery(name = "RnGcFormaspagosTbl.findByDescripcion", query = "SELECT r FROM RnGcFormaspagosTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcFormaspagosTbl.findByBancarizado", query = "SELECT r FROM RnGcFormaspagosTbl r WHERE r.bancarizado = :bancarizado")
    , @NamedQuery(name = "RnGcFormaspagosTbl.findByNumOperacion", query = "SELECT r FROM RnGcFormaspagosTbl r WHERE r.numOperacion = :numOperacion")
    , @NamedQuery(name = "RnGcFormaspagosTbl.findByRFCEmisorOrdenante", query = "SELECT r FROM RnGcFormaspagosTbl r WHERE r.rFCEmisorOrdenante = :rFCEmisorOrdenante")
    , @NamedQuery(name = "RnGcFormaspagosTbl.findByCuentaOrdenante", query = "SELECT r FROM RnGcFormaspagosTbl r WHERE r.cuentaOrdenante = :cuentaOrdenante")
    , @NamedQuery(name = "RnGcFormaspagosTbl.findByPatronCuentaOrdenante", query = "SELECT r FROM RnGcFormaspagosTbl r WHERE r.patronCuentaOrdenante = :patronCuentaOrdenante")
    , @NamedQuery(name = "RnGcFormaspagosTbl.findByRFCEmisorBenficiario", query = "SELECT r FROM RnGcFormaspagosTbl r WHERE r.rFCEmisorBenficiario = :rFCEmisorBenficiario")
    , @NamedQuery(name = "RnGcFormaspagosTbl.findByCuentaBeneficiario", query = "SELECT r FROM RnGcFormaspagosTbl r WHERE r.cuentaBeneficiario = :cuentaBeneficiario")
    , @NamedQuery(name = "RnGcFormaspagosTbl.findByPatronCuentaBeneficiario", query = "SELECT r FROM RnGcFormaspagosTbl r WHERE r.patronCuentaBeneficiario = :patronCuentaBeneficiario")
    , @NamedQuery(name = "RnGcFormaspagosTbl.findByTipoCadenaPago", query = "SELECT r FROM RnGcFormaspagosTbl r WHERE r.tipoCadenaPago = :tipoCadenaPago")
    , @NamedQuery(name = "RnGcFormaspagosTbl.findByBancoEmisorCuentaOrdenante", query = "SELECT r FROM RnGcFormaspagosTbl r WHERE r.bancoEmisorCuentaOrdenante = :bancoEmisorCuentaOrdenante")
    , @NamedQuery(name = "RnGcFormaspagosTbl.findByFechaInicioVigencia", query = "SELECT r FROM RnGcFormaspagosTbl r WHERE r.fechaInicioVigencia = :fechaInicioVigencia")
    , @NamedQuery(name = "RnGcFormaspagosTbl.findByFechaFinVigencia", query = "SELECT r FROM RnGcFormaspagosTbl r WHERE r.fechaFinVigencia = :fechaFinVigencia")})
public class RnGcFormaspagosTbl implements Serializable {

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
    @Size(min = 1, max = 10)
    @Column(name = "c_FormaPago")
    private String cFormaPago;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 35)
    @Column(name = "Descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "Bancarizado")
    private String bancarizado;
    @Size(max = 10)
    @Column(name = "NumOperacion")
    private String numOperacion;
    @Size(max = 13)
    @Column(name = "RFC_EmisorOrdenante")
    private String rFCEmisorOrdenante;
    @Size(max = 30)
    @Column(name = "CuentaOrdenante")
    private String cuentaOrdenante;
    @Size(max = 60)
    @Column(name = "patronCuentaOrdenante")
    private String patronCuentaOrdenante;
    @Size(max = 13)
    @Column(name = "RFC_EmisorBenficiario")
    private String rFCEmisorBenficiario;
    @Size(max = 30)
    @Column(name = "CuentaBeneficiario")
    private String cuentaBeneficiario;
    @Size(max = 60)
    @Column(name = "patronCuentaBeneficiario")
    private String patronCuentaBeneficiario;
    @Size(max = 45)
    @Column(name = "tipoCadenaPago")
    private String tipoCadenaPago;
    @Size(max = 45)
    @Column(name = "bancoEmisorCuentaOrdenante")
    private String bancoEmisorCuentaOrdenante;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaInicioVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioVigencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaFinVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaFinVigencia;
    @JoinColumn(name = "cfdis_Id", referencedColumnName = "Id")
    @ManyToOne
    private RnGcCfdisTbl cfdisId;

    public RnGcFormaspagosTbl() {
    }

    public RnGcFormaspagosTbl(Integer id) {
        this.id = id;
    }

    public RnGcFormaspagosTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion, String cFormaPago, String descripcion, String bancarizado, Date fechaInicioVigencia, Date fechaFinVigencia) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
        this.cFormaPago = cFormaPago;
        this.descripcion = descripcion;
        this.bancarizado = bancarizado;
        this.fechaInicioVigencia = fechaInicioVigencia;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCFormaPago() {
        return cFormaPago;
    }

    public void setCFormaPago(String cFormaPago) {
        this.cFormaPago = cFormaPago;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getBancarizado() {
        return bancarizado;
    }

    public void setBancarizado(String bancarizado) {
        this.bancarizado = bancarizado;
    }

    public String getNumOperacion() {
        return numOperacion;
    }

    public void setNumOperacion(String numOperacion) {
        this.numOperacion = numOperacion;
    }

    public String getRFCEmisorOrdenante() {
        return rFCEmisorOrdenante;
    }

    public void setRFCEmisorOrdenante(String rFCEmisorOrdenante) {
        this.rFCEmisorOrdenante = rFCEmisorOrdenante;
    }

    public String getCuentaOrdenante() {
        return cuentaOrdenante;
    }

    public void setCuentaOrdenante(String cuentaOrdenante) {
        this.cuentaOrdenante = cuentaOrdenante;
    }

    public String getPatronCuentaOrdenante() {
        return patronCuentaOrdenante;
    }

    public void setPatronCuentaOrdenante(String patronCuentaOrdenante) {
        this.patronCuentaOrdenante = patronCuentaOrdenante;
    }

    public String getRFCEmisorBenficiario() {
        return rFCEmisorBenficiario;
    }

    public void setRFCEmisorBenficiario(String rFCEmisorBenficiario) {
        this.rFCEmisorBenficiario = rFCEmisorBenficiario;
    }

    public String getCuentaBeneficiario() {
        return cuentaBeneficiario;
    }

    public void setCuentaBeneficiario(String cuentaBeneficiario) {
        this.cuentaBeneficiario = cuentaBeneficiario;
    }

    public String getPatronCuentaBeneficiario() {
        return patronCuentaBeneficiario;
    }

    public void setPatronCuentaBeneficiario(String patronCuentaBeneficiario) {
        this.patronCuentaBeneficiario = patronCuentaBeneficiario;
    }

    public String getTipoCadenaPago() {
        return tipoCadenaPago;
    }

    public void setTipoCadenaPago(String tipoCadenaPago) {
        this.tipoCadenaPago = tipoCadenaPago;
    }

    public String getBancoEmisorCuentaOrdenante() {
        return bancoEmisorCuentaOrdenante;
    }

    public void setBancoEmisorCuentaOrdenante(String bancoEmisorCuentaOrdenante) {
        this.bancoEmisorCuentaOrdenante = bancoEmisorCuentaOrdenante;
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

    public RnGcCfdisTbl getCfdisId() {
        return cfdisId;
    }

    public void setCfdisId(RnGcCfdisTbl cfdisId) {
        this.cfdisId = cfdisId;
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
        if (!(object instanceof RnGcFormaspagosTbl)) {
            return false;
        }
        RnGcFormaspagosTbl other = (RnGcFormaspagosTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcFormaspagosTbl[ id=" + id + " ]";
    }
    
}
