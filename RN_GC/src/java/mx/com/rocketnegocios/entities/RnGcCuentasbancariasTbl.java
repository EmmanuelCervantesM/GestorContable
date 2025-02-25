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
@Table(name = "rn_gc_cuentasbancarias_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcCuentasbancariasTbl.findAll", query = "SELECT r FROM RnGcCuentasbancariasTbl r")
    , @NamedQuery(name = "RnGcCuentasbancariasTbl.findById", query = "SELECT r FROM RnGcCuentasbancariasTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcCuentasbancariasTbl.findByCuentaBancariaId", query = "SELECT r FROM RnGcCuentasbancariasTbl r WHERE r.cuentaBancariaId = :cuentaBancariaId")
    , @NamedQuery(name = "RnGcCuentasbancariasTbl.findByBanconombre", query = "SELECT r FROM RnGcCuentasbancariasTbl r WHERE r.banconombre = :banconombre")
    , @NamedQuery(name = "RnGcCuentasbancariasTbl.findByNoCuentaBancaria", query = "SELECT r FROM RnGcCuentasbancariasTbl r WHERE r.noCuentaBancaria = :noCuentaBancaria")
    , @NamedQuery(name = "RnGcCuentasbancariasTbl.findByPais", query = "SELECT r FROM RnGcCuentasbancariasTbl r WHERE r.pais = :pais")
    , @NamedQuery(name = "RnGcCuentasbancariasTbl.findByFechaInicioVigencia", query = "SELECT r FROM RnGcCuentasbancariasTbl r WHERE r.fechaInicioVigencia = :fechaInicioVigencia")
    , @NamedQuery(name = "RnGcCuentasbancariasTbl.findByFechaFinVigencia", query = "SELECT r FROM RnGcCuentasbancariasTbl r WHERE r.fechaFinVigencia = :fechaFinVigencia")
    , @NamedQuery(name = "RnGcCuentasbancariasTbl.findByCreadoPor", query = "SELECT r FROM RnGcCuentasbancariasTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCuentasbancariasTbl.findByFechaCreacion", query = "SELECT r FROM RnGcCuentasbancariasTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcCuentasbancariasTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcCuentasbancariasTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcCuentasbancariasTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcCuentasbancariasTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcCuentasbancariasTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cuentaBancariaId")
    private int cuentaBancariaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "Banconombre")
    private String banconombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "noCuentaBancaria")
    private int noCuentaBancaria;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Pais")
    private String pais;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuentasBancariasId")
    private Collection<RnGcBancosTbl> rnGcBancosTblCollection;

    public RnGcCuentasbancariasTbl() {
    }

    public RnGcCuentasbancariasTbl(Integer id) {
        this.id = id;
    }

    public RnGcCuentasbancariasTbl(Integer id, int cuentaBancariaId, String banconombre, int noCuentaBancaria, String pais, Date fechaInicioVigencia, Date fechaFinVigencia, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.cuentaBancariaId = cuentaBancariaId;
        this.banconombre = banconombre;
        this.noCuentaBancaria = noCuentaBancaria;
        this.pais = pais;
        this.fechaInicioVigencia = fechaInicioVigencia;
        this.fechaFinVigencia = fechaFinVigencia;
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

    public int getCuentaBancariaId() {
        return cuentaBancariaId;
    }

    public void setCuentaBancariaId(int cuentaBancariaId) {
        this.cuentaBancariaId = cuentaBancariaId;
    }

    public String getBanconombre() {
        return banconombre;
    }

    public void setBanconombre(String banconombre) {
        this.banconombre = banconombre;
    }

    public int getNoCuentaBancaria() {
        return noCuentaBancaria;
    }

    public void setNoCuentaBancaria(int noCuentaBancaria) {
        this.noCuentaBancaria = noCuentaBancaria;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
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
    public Collection<RnGcBancosTbl> getRnGcBancosTblCollection() {
        return rnGcBancosTblCollection;
    }

    public void setRnGcBancosTblCollection(Collection<RnGcBancosTbl> rnGcBancosTblCollection) {
        this.rnGcBancosTblCollection = rnGcBancosTblCollection;
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
        if (!(object instanceof RnGcCuentasbancariasTbl)) {
            return false;
        }
        RnGcCuentasbancariasTbl other = (RnGcCuentasbancariasTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcCuentasbancariasTbl[ id=" + id + " ]";
    }
    
}
