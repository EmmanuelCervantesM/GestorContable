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
@Table(name = "rn_gc_recibos_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcRecibosTbl.findAll", query = "SELECT r FROM RnGcRecibosTbl r")
    , @NamedQuery(name = "RnGcRecibosTbl.findById", query = "SELECT r FROM RnGcRecibosTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcRecibosTbl.findByCreadoPor", query = "SELECT r FROM RnGcRecibosTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcRecibosTbl.findByFechaCreacion", query = "SELECT r FROM RnGcRecibosTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcRecibosTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcRecibosTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcRecibosTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcRecibosTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcRecibosTbl.findByTransaccionesId", query = "SELECT r FROM RnGcRecibosTbl r WHERE r.transaccionesId = :transaccionesId")
    , @NamedQuery(name = "RnGcRecibosTbl.findByTransaccionesPeriodoId", query = "SELECT r FROM RnGcRecibosTbl r WHERE r.transaccionesPeriodoId = :transaccionesPeriodoId")
    , @NamedQuery(name = "RnGcRecibosTbl.findByTransaccionesPersonaId", query = "SELECT r FROM RnGcRecibosTbl r WHERE r.transaccionesPersonaId = :transaccionesPersonaId")
    , @NamedQuery(name = "RnGcRecibosTbl.findByRngctransaccionestblId", query = "SELECT r FROM RnGcRecibosTbl r WHERE r.rngctransaccionestblId = :rngctransaccionestblId")
    , @NamedQuery(name = "RnGcRecibosTbl.findByNoRecibo", query = "SELECT r FROM RnGcRecibosTbl r WHERE r.noRecibo = :noRecibo")})
public class RnGcRecibosTbl implements Serializable {

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
    @Column(name = "transacciones_Id")
    private int transaccionesId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "transacciones_Periodo_Id")
    private int transaccionesPeriodoId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "transacciones_Persona_Id")
    private int transaccionesPersonaId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rn_gc_transacciones_tbl_Id")
    private int rngctransaccionestblId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "noRecibo")
    private String noRecibo;
    @JoinColumn(name = "rn_gc_transacciones_tbl_Id1", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private RnGcTransaccionesTbl rngctransaccionestblId1;

    public RnGcRecibosTbl() {
    }

    public RnGcRecibosTbl(Integer id) {
        this.id = id;
    }

    public RnGcRecibosTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion, int transaccionesId, int transaccionesPeriodoId, int transaccionesPersonaId, int rngctransaccionestblId, String noRecibo) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
        this.transaccionesId = transaccionesId;
        this.transaccionesPeriodoId = transaccionesPeriodoId;
        this.transaccionesPersonaId = transaccionesPersonaId;
        this.rngctransaccionestblId = rngctransaccionestblId;
        this.noRecibo = noRecibo;
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

    public int getTransaccionesId() {
        return transaccionesId;
    }

    public void setTransaccionesId(int transaccionesId) {
        this.transaccionesId = transaccionesId;
    }

    public int getTransaccionesPeriodoId() {
        return transaccionesPeriodoId;
    }

    public void setTransaccionesPeriodoId(int transaccionesPeriodoId) {
        this.transaccionesPeriodoId = transaccionesPeriodoId;
    }

    public int getTransaccionesPersonaId() {
        return transaccionesPersonaId;
    }

    public void setTransaccionesPersonaId(int transaccionesPersonaId) {
        this.transaccionesPersonaId = transaccionesPersonaId;
    }

    public int getRngctransaccionestblId() {
        return rngctransaccionestblId;
    }

    public void setRngctransaccionestblId(int rngctransaccionestblId) {
        this.rngctransaccionestblId = rngctransaccionestblId;
    }

    public String getNoRecibo() {
        return noRecibo;
    }

    public void setNoRecibo(String noRecibo) {
        this.noRecibo = noRecibo;
    }

    public RnGcTransaccionesTbl getRngctransaccionestblId1() {
        return rngctransaccionestblId1;
    }

    public void setRngctransaccionestblId1(RnGcTransaccionesTbl rngctransaccionestblId1) {
        this.rngctransaccionestblId1 = rngctransaccionestblId1;
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
        if (!(object instanceof RnGcRecibosTbl)) {
            return false;
        }
        RnGcRecibosTbl other = (RnGcRecibosTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcRecibosTbl[ id=" + id + " ]";
    }
    
}
