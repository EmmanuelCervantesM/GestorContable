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
@Table(name = "rn_gc_bancos_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcBancosTbl.findAll", query = "SELECT r FROM RnGcBancosTbl r")
    , @NamedQuery(name = "RnGcBancosTbl.findById", query = "SELECT r FROM RnGcBancosTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcBancosTbl.findByBancoId", query = "SELECT r FROM RnGcBancosTbl r WHERE r.bancoId = :bancoId")
    , @NamedQuery(name = "RnGcBancosTbl.findByNombre", query = "SELECT r FROM RnGcBancosTbl r WHERE r.nombre = :nombre")
    , @NamedQuery(name = "RnGcBancosTbl.findByTipoBanco", query = "SELECT r FROM RnGcBancosTbl r WHERE r.tipoBanco = :tipoBanco")
    , @NamedQuery(name = "RnGcBancosTbl.findByCreadoPor", query = "SELECT r FROM RnGcBancosTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcBancosTbl.findByFechaCreacion", query = "SELECT r FROM RnGcBancosTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcBancosTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcBancosTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcBancosTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcBancosTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcBancosTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bancoId")
    private int bancoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "TipoBanco")
    private String tipoBanco;
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
    @JoinColumn(name = "CuentasBancarias_Id", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private RnGcCuentasbancariasTbl cuentasBancariasId;
    @JoinColumn(name = "personas_Id", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private RnGcPersonasTbl personasId;

    public RnGcBancosTbl() {
    }

    public RnGcBancosTbl(Integer id) {
        this.id = id;
    }

    public RnGcBancosTbl(Integer id, int bancoId, String nombre, String tipoBanco, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.bancoId = bancoId;
        this.nombre = nombre;
        this.tipoBanco = tipoBanco;
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

    public int getBancoId() {
        return bancoId;
    }

    public void setBancoId(int bancoId) {
        this.bancoId = bancoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoBanco() {
        return tipoBanco;
    }

    public void setTipoBanco(String tipoBanco) {
        this.tipoBanco = tipoBanco;
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

    public RnGcCuentasbancariasTbl getCuentasBancariasId() {
        return cuentasBancariasId;
    }

    public void setCuentasBancariasId(RnGcCuentasbancariasTbl cuentasBancariasId) {
        this.cuentasBancariasId = cuentasBancariasId;
    }

    public RnGcPersonasTbl getPersonasId() {
        return personasId;
    }

    public void setPersonasId(RnGcPersonasTbl personasId) {
        this.personasId = personasId;
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
        if (!(object instanceof RnGcBancosTbl)) {
            return false;
        }
        RnGcBancosTbl other = (RnGcBancosTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcBancosTbl[ id=" + id + " ]";
    }
    
}
