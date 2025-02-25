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
 * @author mmiva
 */
@Entity
@Table(name = "rn_gc_claveprodserv_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcClaveprodservTbl.findAll", query = "SELECT r FROM RnGcClaveprodservTbl r")
    , @NamedQuery(name = "RnGcClaveprodservTbl.findById", query = "SELECT r FROM RnGcClaveprodservTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcClaveprodservTbl.findByCreadoPor", query = "SELECT r FROM RnGcClaveprodservTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcClaveprodservTbl.findByFechaCreacion", query = "SELECT r FROM RnGcClaveprodservTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcClaveprodservTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcClaveprodservTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcClaveprodservTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcClaveprodservTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcClaveprodservTbl.findByCClaveProdServ", query = "SELECT r FROM RnGcClaveprodservTbl r WHERE r.cClaveProdServ = :cClaveProdServ")
    , @NamedQuery(name = "RnGcClaveprodservTbl.findByDescripcion", query = "SELECT r FROM RnGcClaveprodservTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcClaveprodservTbl.findByIncluirIvaTrasladado", query = "SELECT r FROM RnGcClaveprodservTbl r WHERE r.incluirIvaTrasladado = :incluirIvaTrasladado")
    , @NamedQuery(name = "RnGcClaveprodservTbl.findByIncluirIEPSTrasladado", query = "SELECT r FROM RnGcClaveprodservTbl r WHERE r.incluirIEPSTrasladado = :incluirIEPSTrasladado")
    , @NamedQuery(name = "RnGcClaveprodservTbl.findByComplementoIncluir", query = "SELECT r FROM RnGcClaveprodservTbl r WHERE r.complementoIncluir = :complementoIncluir")
    , @NamedQuery(name = "RnGcClaveprodservTbl.findByFechaInicioVigencia", query = "SELECT r FROM RnGcClaveprodservTbl r WHERE r.fechaInicioVigencia = :fechaInicioVigencia")
    , @NamedQuery(name = "RnGcClaveprodservTbl.findByFechaFinVigencia", query = "SELECT r FROM RnGcClaveprodservTbl r WHERE r.fechaFinVigencia = :fechaFinVigencia")
    , @NamedQuery(name = "RnGcClaveprodservTbl.findByVersionCFDI", query = "SELECT r FROM RnGcClaveprodservTbl r WHERE r.versionCFDI = :versionCFDI")
    , @NamedQuery(name = "RnGcClaveprodservTbl.findByVersionCatalogo", query = "SELECT r FROM RnGcClaveprodservTbl r WHERE r.versionCatalogo = :versionCatalogo")
    , @NamedQuery(name = "RnGcClaveprodservTbl.findByEstimuloFranjaFronteriza", query = "SELECT r FROM RnGcClaveprodservTbl r WHERE r.estimuloFranjaFronteriza = :estimuloFranjaFronteriza")
    , @NamedQuery(name = "RnGcClaveprodservTbl.findByPalabrasSimilares", query = "SELECT r FROM RnGcClaveprodservTbl r WHERE r.palabrasSimilares = :palabrasSimilares")})
public class RnGcClaveprodservTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "c_ClaveProdServ")
    private String cClaveProdServ;
    @Size(max = 45)
    @Column(name = "Descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "incluir_IvaTrasladado")
    private String incluirIvaTrasladado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "incluir_IEPSTrasladado")
    private String incluirIEPSTrasladado;
    @Size(max = 10)
    @Column(name = "complementoIncluir")
    private String complementoIncluir;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaInicioVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioVigencia;
    @Column(name = "fechaFinVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaFinVigencia;
    @Size(max = 4)
    @Column(name = "versionCFDI")
    private String versionCFDI;
    @Size(max = 4)
    @Column(name = "versionCatalogo")
    private String versionCatalogo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estimuloFranjaFronteriza")
    private boolean estimuloFranjaFronteriza;
    @Size(max = 250)
    @Column(name = "palabrasSimilares")
    private String palabrasSimilares;

    public RnGcClaveprodservTbl() {
    }

    public RnGcClaveprodservTbl(Integer id) {
        this.id = id;
    }

    public RnGcClaveprodservTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion, String cClaveProdServ, String incluirIvaTrasladado, String incluirIEPSTrasladado, Date fechaInicioVigencia, boolean estimuloFranjaFronteriza) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
        this.cClaveProdServ = cClaveProdServ;
        this.incluirIvaTrasladado = incluirIvaTrasladado;
        this.incluirIEPSTrasladado = incluirIEPSTrasladado;
        this.fechaInicioVigencia = fechaInicioVigencia;
        this.estimuloFranjaFronteriza = estimuloFranjaFronteriza;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCClaveProdServ() {
        return cClaveProdServ;
    }

    public void setCClaveProdServ(String cClaveProdServ) {
        this.cClaveProdServ = cClaveProdServ;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIncluirIvaTrasladado() {
        return incluirIvaTrasladado;
    }

    public void setIncluirIvaTrasladado(String incluirIvaTrasladado) {
        this.incluirIvaTrasladado = incluirIvaTrasladado;
    }

    public String getIncluirIEPSTrasladado() {
        return incluirIEPSTrasladado;
    }

    public void setIncluirIEPSTrasladado(String incluirIEPSTrasladado) {
        this.incluirIEPSTrasladado = incluirIEPSTrasladado;
    }

    public String getComplementoIncluir() {
        return complementoIncluir;
    }

    public void setComplementoIncluir(String complementoIncluir) {
        this.complementoIncluir = complementoIncluir;
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

    public String getVersionCFDI() {
        return versionCFDI;
    }

    public void setVersionCFDI(String versionCFDI) {
        this.versionCFDI = versionCFDI;
    }

    public String getVersionCatalogo() {
        return versionCatalogo;
    }

    public void setVersionCatalogo(String versionCatalogo) {
        this.versionCatalogo = versionCatalogo;
    }

    public boolean getEstimuloFranjaFronteriza() {
        return estimuloFranjaFronteriza;
    }

    public void setEstimuloFranjaFronteriza(boolean estimuloFranjaFronteriza) {
        this.estimuloFranjaFronteriza = estimuloFranjaFronteriza;
    }

    public String getPalabrasSimilares() {
        return palabrasSimilares;
    }

    public void setPalabrasSimilares(String palabrasSimilares) {
        this.palabrasSimilares = palabrasSimilares;
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
        if (!(object instanceof RnGcClaveprodservTbl)) {
            return false;
        }
        RnGcClaveprodservTbl other = (RnGcClaveprodservTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcClaveprodservTbl[ id=" + id + " ]";
    }
    
}
