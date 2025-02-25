/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author LenovoZ40
 */
@Entity
@Table(name = "rn_gc_nom_solicitud_trabajador_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcNomSolicitudTrabajadorTbl.findAll", query = "SELECT r FROM RnGcNomSolicitudTrabajadorTbl r")
    , @NamedQuery(name = "RnGcNomSolicitudTrabajadorTbl.findById", query = "SELECT r FROM RnGcNomSolicitudTrabajadorTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcNomSolicitudTrabajadorTbl.findByCreadoPor", query = "SELECT r FROM RnGcNomSolicitudTrabajadorTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcNomSolicitudTrabajadorTbl.findByFechaCreacion", query = "SELECT r FROM RnGcNomSolicitudTrabajadorTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcNomSolicitudTrabajadorTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcNomSolicitudTrabajadorTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcNomSolicitudTrabajadorTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcNomSolicitudTrabajadorTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcNomSolicitudTrabajadorTbl.findByDiasPagados", query = "SELECT r FROM RnGcNomSolicitudTrabajadorTbl r WHERE r.diasPagados = :diasPagados")
    , @NamedQuery(name = "RnGcNomSolicitudTrabajadorTbl.findByFechaPago", query = "SELECT r FROM RnGcNomSolicitudTrabajadorTbl r WHERE r.fechaPago = :fechaPago")
    , @NamedQuery(name = "RnGcNomSolicitudTrabajadorTbl.findBySdi", query = "SELECT r FROM RnGcNomSolicitudTrabajadorTbl r WHERE r.sdi = :sdi")
    , @NamedQuery(name = "RnGcNomSolicitudTrabajadorTbl.findBySolicitudId", query = "SELECT r FROM RnGcNomSolicitudTrabajadorTbl r WHERE r.solicitudId = :solicitudId")
    , @NamedQuery(name = "RnGcNomSolicitudTrabajadorTbl.findByTotalPercepciones", query = "SELECT r FROM RnGcNomSolicitudTrabajadorTbl r WHERE r.totalPercepciones = :totalPercepciones")
    , @NamedQuery(name = "RnGcNomSolicitudTrabajadorTbl.findByTotalDeducciones", query = "SELECT r FROM RnGcNomSolicitudTrabajadorTbl r WHERE r.totalDeducciones = :totalDeducciones")
    , @NamedQuery(name = "RnGcNomSolicitudTrabajadorTbl.findByImporteNeto", query = "SELECT r FROM RnGcNomSolicitudTrabajadorTbl r WHERE r.importeNeto = :importeNeto")
    , @NamedQuery(name = "RnGcNomSolicitudTrabajadorTbl.findByEstatus", query = "SELECT r FROM RnGcNomSolicitudTrabajadorTbl r WHERE r.estatus = :estatus")})
public class RnGcNomSolicitudTrabajadorTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
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
    @Column(name = "diasPagados")
    private Integer diasPagados;
    @Column(name = "fechaPago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SDI")
    private BigDecimal sdi;
    @Column(name = "totalPercepciones")
    private BigDecimal totalPercepciones;
    @Column(name = "totalDeducciones")
    private BigDecimal totalDeducciones;
    @Column(name = "importeNeto")
    private BigDecimal importeNeto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "estatus")
    private String estatus;
    @JoinColumn(name = "solicitudId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RnGcNomSolicitudesTbl solicitudId;
    @JoinColumn(name = "trabajadorId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RnGcTrabajadoresTbl trabajadorId;

    public RnGcNomSolicitudTrabajadorTbl() {
    }

    public RnGcNomSolicitudTrabajadorTbl(Integer id) {
        this.id = id;
    }

    public RnGcNomSolicitudTrabajadorTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion, String estatus) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
        this.estatus = estatus;
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

    public Integer getDiasPagados() {
        return diasPagados;
    }

    public void setDiasPagados(Integer diasPagados) {
        this.diasPagados = diasPagados;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public BigDecimal getSdi() {
        return sdi;
    }

    public void setSdi(BigDecimal sdi) {
        this.sdi = sdi;
    }

    public BigDecimal getTotalPercepciones() {
        return totalPercepciones;
    }

    public void setTotalPercepciones(BigDecimal totalPercepciones) {
        this.totalPercepciones = totalPercepciones;
    }

    public BigDecimal getTotalDeducciones() {
        return totalDeducciones;
    }

    public void setTotalDeducciones(BigDecimal totalDeducciones) {
        this.totalDeducciones = totalDeducciones;
    }

    public BigDecimal getImporteNeto() {
        return importeNeto;
    }

    public void setImporteNeto(BigDecimal importeNeto) {
        this.importeNeto = importeNeto;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public RnGcNomSolicitudesTbl getSolicitudId() {
        return solicitudId;
    }

    public void setSolicitudId(RnGcNomSolicitudesTbl solicitudId) {
        this.solicitudId = solicitudId;
    }

    public RnGcTrabajadoresTbl getTrabajadorId() {
        return trabajadorId;
    }

    public void setTrabajadorId(RnGcTrabajadoresTbl trabajadorId) {
        this.trabajadorId = trabajadorId;
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
        if (!(object instanceof RnGcNomSolicitudTrabajadorTbl)) {
            return false;
        }
        RnGcNomSolicitudTrabajadorTbl other = (RnGcNomSolicitudTrabajadorTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcNomSolicitudTrabajadorTbl[ id=" + id + " ]";
    }
    
}
