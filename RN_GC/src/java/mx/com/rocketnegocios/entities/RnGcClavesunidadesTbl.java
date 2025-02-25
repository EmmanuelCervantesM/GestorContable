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
import javax.persistence.Lob;
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
@Table(name = "rn_gc_clavesunidades_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcClavesunidadesTbl.findAll", query = "SELECT r FROM RnGcClavesunidadesTbl r")
    , @NamedQuery(name = "RnGcClavesunidadesTbl.findByCreadoPor", query = "SELECT r FROM RnGcClavesunidadesTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcClavesunidadesTbl.findByFechaCreacion", query = "SELECT r FROM RnGcClavesunidadesTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcClavesunidadesTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcClavesunidadesTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcClavesunidadesTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcClavesunidadesTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcClavesunidadesTbl.findById", query = "SELECT r FROM RnGcClavesunidadesTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcClavesunidadesTbl.findByCClaveUnidadcol", query = "SELECT r FROM RnGcClavesunidadesTbl r WHERE r.cClaveUnidadcol = :cClaveUnidadcol")
    , @NamedQuery(name = "RnGcClavesunidadesTbl.findByNombre", query = "SELECT r FROM RnGcClavesunidadesTbl r WHERE r.nombre = :nombre")
    , @NamedQuery(name = "RnGcClavesunidadesTbl.findByFechaInicioVigencia", query = "SELECT r FROM RnGcClavesunidadesTbl r WHERE r.fechaInicioVigencia = :fechaInicioVigencia")
    , @NamedQuery(name = "RnGcClavesunidadesTbl.findByFehaFinVigencia", query = "SELECT r FROM RnGcClavesunidadesTbl r WHERE r.fehaFinVigencia = :fehaFinVigencia")
    , @NamedQuery(name = "RnGcClavesunidadesTbl.findBySimbolo", query = "SELECT r FROM RnGcClavesunidadesTbl r WHERE r.simbolo = :simbolo")})
public class RnGcClavesunidadesTbl implements Serializable {

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
    @Size(min = 1, max = 3)
    @Column(name = "c_ClaveUnidadcol")
    private String cClaveUnidadcol;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Nombre")
    private String nombre;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "Descripcion")
    private String descripcion;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "Nota")
    private String nota;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaInicioVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioVigencia;
    @Column(name = "fehaFinVigencia")
    @Temporal(TemporalType.DATE)
    private Date fehaFinVigencia;
    @Size(max = 30)
    @Column(name = "Simbolo")
    private String simbolo;
    @JoinColumn(name = "cfdis_Id", referencedColumnName = "Id")
    @ManyToOne
    private RnGcCfdisTbl cfdisId;

    public RnGcClavesunidadesTbl() {
    }

    public RnGcClavesunidadesTbl(Integer id) {
        this.id = id;
    }

    public RnGcClavesunidadesTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion, String cClaveUnidadcol, String nombre, Date fechaInicioVigencia) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
        this.cClaveUnidadcol = cClaveUnidadcol;
        this.nombre = nombre;
        this.fechaInicioVigencia = fechaInicioVigencia;
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

    public String getCClaveUnidadcol() {
        return cClaveUnidadcol;
    }

    public void setCClaveUnidadcol(String cClaveUnidadcol) {
        this.cClaveUnidadcol = cClaveUnidadcol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Date getFechaInicioVigencia() {
        return fechaInicioVigencia;
    }

    public void setFechaInicioVigencia(Date fechaInicioVigencia) {
        this.fechaInicioVigencia = fechaInicioVigencia;
    }

    public Date getFehaFinVigencia() {
        return fehaFinVigencia;
    }

    public void setFehaFinVigencia(Date fehaFinVigencia) {
        this.fehaFinVigencia = fehaFinVigencia;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
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
        if (!(object instanceof RnGcClavesunidadesTbl)) {
            return false;
        }
        RnGcClavesunidadesTbl other = (RnGcClavesunidadesTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcClavesunidadesTbl[ id=" + id + " ]";
    }
    
}
