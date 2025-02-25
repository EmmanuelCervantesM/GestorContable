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
 * @author maxoc
 */
@Entity
@Table(name = "rn_gc_cp_contenedorsat_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcCpContenedorsatTbl.findAll", query = "SELECT r FROM RnGcCpContenedorsatTbl r")
    , @NamedQuery(name = "RnGcCpContenedorsatTbl.findById", query = "SELECT r FROM RnGcCpContenedorsatTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcCpContenedorsatTbl.findByClave", query = "SELECT r FROM RnGcCpContenedorsatTbl r WHERE r.clave = :clave")
    , @NamedQuery(name = "RnGcCpContenedorsatTbl.findByTipoContenedor", query = "SELECT r FROM RnGcCpContenedorsatTbl r WHERE r.tipoContenedor = :tipoContenedor")
    , @NamedQuery(name = "RnGcCpContenedorsatTbl.findByDescripcion", query = "SELECT r FROM RnGcCpContenedorsatTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcCpContenedorsatTbl.findByFechaInicioVigencia", query = "SELECT r FROM RnGcCpContenedorsatTbl r WHERE r.fechaInicioVigencia = :fechaInicioVigencia")
    , @NamedQuery(name = "RnGcCpContenedorsatTbl.findByFechaFinVigencia", query = "SELECT r FROM RnGcCpContenedorsatTbl r WHERE r.fechaFinVigencia = :fechaFinVigencia")
    , @NamedQuery(name = "RnGcCpContenedorsatTbl.findByCreadoPor", query = "SELECT r FROM RnGcCpContenedorsatTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCpContenedorsatTbl.findByFechaCreacion", query = "SELECT r FROM RnGcCpContenedorsatTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcCpContenedorsatTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcCpContenedorsatTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcCpContenedorsatTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcCpContenedorsatTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcCpContenedorsatTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Size(max = 10)
    @Column(name = "clave")
    private String clave;
    @Size(max = 15)
    @Column(name = "tipoContenedor")
    private String tipoContenedor;
    @Size(max = 45)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fechaInicioVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioVigencia;
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
    @Column(name = "ultimaActualizacionPor")
    private Integer ultimaActualizacionPor;
    @Column(name = "ultimaFechaActualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaFechaActualizacion;

    public RnGcCpContenedorsatTbl() {
    }

    public RnGcCpContenedorsatTbl(Integer id) {
        this.id = id;
    }

    public RnGcCpContenedorsatTbl(Integer id, int creadoPor, Date fechaCreacion) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getTipoContenedor() {
        return tipoContenedor;
    }

    public void setTipoContenedor(String tipoContenedor) {
        this.tipoContenedor = tipoContenedor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RnGcCpContenedorsatTbl)) {
            return false;
        }
        RnGcCpContenedorsatTbl other = (RnGcCpContenedorsatTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcCpContenedorsatTbl[ id=" + id + " ]";
    }
    
}
