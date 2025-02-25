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
@Table(name = "rn_gc_catalogosusos_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcCatalogosusosTbl.findAll", query = "SELECT r FROM RnGcCatalogosusosTbl r")
    , @NamedQuery(name = "RnGcCatalogosusosTbl.findById", query = "SELECT r FROM RnGcCatalogosusosTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcCatalogosusosTbl.findByCUsoCFDI", query = "SELECT r FROM RnGcCatalogosusosTbl r WHERE r.cUsoCFDI = :cUsoCFDI")
    , @NamedQuery(name = "RnGcCatalogosusosTbl.findByDescripcion", query = "SELECT r FROM RnGcCatalogosusosTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcCatalogosusosTbl.findByAplicacion", query = "SELECT r FROM RnGcCatalogosusosTbl r WHERE r.aplicacion = :aplicacion")
    , @NamedQuery(name = "RnGcCatalogosusosTbl.findByFechaInicioVigencia", query = "SELECT r FROM RnGcCatalogosusosTbl r WHERE r.fechaInicioVigencia = :fechaInicioVigencia")
    , @NamedQuery(name = "RnGcCatalogosusosTbl.findByFechaFinVigencia", query = "SELECT r FROM RnGcCatalogosusosTbl r WHERE r.fechaFinVigencia = :fechaFinVigencia")
    , @NamedQuery(name = "RnGcCatalogosusosTbl.findByCreadoPor", query = "SELECT r FROM RnGcCatalogosusosTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCatalogosusosTbl.findByFechaCreacion", query = "SELECT r FROM RnGcCatalogosusosTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcCatalogosusosTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcCatalogosusosTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcCatalogosusosTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcCatalogosusosTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcCatalogosusosTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "c_UsoCFDI")
    private String cUsoCFDI;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 90)
    @Column(name = "Descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "Aplicacion")
    private String aplicacion;
    @Basic(optional = false)
    @NotNull
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "ultimaActualizacionPor")
    private int ultimaActualizacionPor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ultimaFechaActualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaFechaActualizacion;
    @JoinColumn(name = "cfdi_Id", referencedColumnName = "Id")
    @ManyToOne
    private RnGcCfdisTbl cfdiId;

    public RnGcCatalogosusosTbl() {
    }

    public RnGcCatalogosusosTbl(Integer id) {
        this.id = id;
    }

    public RnGcCatalogosusosTbl(Integer id, String cUsoCFDI, String descripcion, String aplicacion, Date fechaInicioVigencia, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.cUsoCFDI = cUsoCFDI;
        this.descripcion = descripcion;
        this.aplicacion = aplicacion;
        this.fechaInicioVigencia = fechaInicioVigencia;
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

    public String getCUsoCFDI() {
        return cUsoCFDI;
    }

    public void setCUsoCFDI(String cUsoCFDI) {
        this.cUsoCFDI = cUsoCFDI;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
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

    public RnGcCfdisTbl getCfdiId() {
        return cfdiId;
    }

    public void setCfdiId(RnGcCfdisTbl cfdiId) {
        this.cfdiId = cfdiId;
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
        if (!(object instanceof RnGcCatalogosusosTbl)) {
            return false;
        }
        RnGcCatalogosusosTbl other = (RnGcCatalogosusosTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcCatalogosusosTbl[ id=" + id + " ]";
    }
    
}
