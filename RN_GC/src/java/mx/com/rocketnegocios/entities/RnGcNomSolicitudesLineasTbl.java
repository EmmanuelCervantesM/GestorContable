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
@Table(name = "rn_gc_nom_solicitudes_lineas_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcNomSolicitudesLineasTbl.findAll", query = "SELECT r FROM RnGcNomSolicitudesLineasTbl r")
    , @NamedQuery(name = "RnGcNomSolicitudesLineasTbl.findById", query = "SELECT r FROM RnGcNomSolicitudesLineasTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcNomSolicitudesLineasTbl.findByCreadoPor", query = "SELECT r FROM RnGcNomSolicitudesLineasTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcNomSolicitudesLineasTbl.findByFechaCreacion", query = "SELECT r FROM RnGcNomSolicitudesLineasTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcNomSolicitudesLineasTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcNomSolicitudesLineasTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcNomSolicitudesLineasTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcNomSolicitudesLineasTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcNomSolicitudesLineasTbl.findBySolicitudTrabajadorId", query = "SELECT r FROM RnGcNomSolicitudesLineasTbl r WHERE r.solicitudTrabajadorId = :solicitudTrabajadorId")
    , @NamedQuery(name = "RnGcNomSolicitudesLineasTbl.obtenerPercepciones", query = "SELECT r FROM RnGcNomSolicitudesLineasTbl r WHERE r.solicitudTrabajadorId = :solicitudTrabajadorId and r.percepcionId is not null")
    , @NamedQuery(name = "RnGcNomSolicitudesLineasTbl.obtenerDeducciones", query = "SELECT r FROM RnGcNomSolicitudesLineasTbl r WHERE r.solicitudTrabajadorId = :solicitudTrabajadorId and r.deduccionId is not null")
    , @NamedQuery(name = "RnGcNomSolicitudesLineasTbl.findByTotalGravado", query = "SELECT r FROM RnGcNomSolicitudesLineasTbl r WHERE r.totalGravado = :totalGravado")
    , @NamedQuery(name = "RnGcNomSolicitudesLineasTbl.findByTotalExento", query = "SELECT r FROM RnGcNomSolicitudesLineasTbl r WHERE r.totalExento = :totalExento")
    , @NamedQuery(name = "RnGcNomSolicitudesLineasTbl.findByTipoClave", query = "SELECT r FROM RnGcNomSolicitudesLineasTbl r WHERE r.tipoClave = :tipoClave")
    , @NamedQuery(name = "RnGcNomSolicitudesLineasTbl.findByTipoConcepto", query = "SELECT r FROM RnGcNomSolicitudesLineasTbl r WHERE r.tipoConcepto = :tipoConcepto")
    , @NamedQuery(name = "RnGcNomSolicitudesLineasTbl.findBySoliTrabajador", query = "SELECT r FROM RnGcNomSolicitudesLineasTbl r WHERE r.solicitudTrabajadorId between :soliTrabajadorUno and :soliTrabajadorDos")})
public class RnGcNomSolicitudesLineasTbl implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Column(name = "solicitudTrabajadorId")
    private int solicitudTrabajadorId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "totalGravado")
    private BigDecimal totalGravado;
    @Column(name = "totalExento")
    private BigDecimal totalExento;
    @Size(max = 45)
    @Column(name = "tipoClave")
    private String tipoClave;
    @Size(max = 80)
    @Column(name = "tipoConcepto")
    private String tipoConcepto;
    @Column(name = "diasIncapacidad")
    private Integer diasIncapacidad;
    @JoinColumn(name = "percepcionId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RnGcNomPercepcionesTbl percepcionId;
    @JoinColumn(name = "deduccionId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RnGcNomDeduccionesTbl deduccionId;
    @JoinColumn(name = "tipoIncapacidadId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RnGcNomTipoincapacidadTbl tipoIncapacidadId;

    public RnGcNomSolicitudesLineasTbl() {
    }

    public RnGcNomSolicitudesLineasTbl(Integer id) {
        this.id = id;
    }

    public RnGcNomSolicitudesLineasTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion, int solicitudTrabajadorId) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
        this.solicitudTrabajadorId = solicitudTrabajadorId;
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

    public int getSolicitudTrabajadorId() {
        return solicitudTrabajadorId;
    }

    public void setSolicitudTrabajadorId(int solicitudTrabajadorId) {
        this.solicitudTrabajadorId = solicitudTrabajadorId;
    }

    public BigDecimal getTotalGravado() {
        return totalGravado;
    }

    public void setTotalGravado(BigDecimal totalGravado) {
        this.totalGravado = totalGravado;
    }

    public BigDecimal getTotalExento() {
        return totalExento;
    }

    public void setTotalExento(BigDecimal totalExento) {
        this.totalExento = totalExento;
    }

    public String getTipoClave() {
        return tipoClave;
    }

    public void setTipoClave(String tipoClave) {
        this.tipoClave = tipoClave;
    }

    public String getTipoConcepto() {
        return tipoConcepto;
    }

    public void setTipoConcepto(String tipoConcepto) {
        this.tipoConcepto = tipoConcepto;
    }

    public RnGcNomPercepcionesTbl getPercepcionId() {
        return percepcionId;
    }

    public void setPercepcionId(RnGcNomPercepcionesTbl percepcionId) {
        this.percepcionId = percepcionId;
    }

    public RnGcNomDeduccionesTbl getDeduccionId() {
        return deduccionId;
    }

    public void setDeduccionId(RnGcNomDeduccionesTbl deduccionId) {
        this.deduccionId = deduccionId;
    }

    public RnGcNomTipoincapacidadTbl getTipoIncapacidadId() {
        return tipoIncapacidadId;
    }

    public void setTipoIncapacidadId(RnGcNomTipoincapacidadTbl tipoIncapacidadId) {
        this.tipoIncapacidadId = tipoIncapacidadId;
    }
    
    public Integer getDiasIncapacidad() {
        return diasIncapacidad;
    }

    public void setDiasIncapacidad(Integer diasIncapacidad) {
        this.diasIncapacidad = diasIncapacidad;
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
        if (!(object instanceof RnGcNomSolicitudesLineasTbl)) {
            return false;
        }
        RnGcNomSolicitudesLineasTbl other = (RnGcNomSolicitudesLineasTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcNomSolicitudesLineasTbl[ id=" + id + " ]";
    }

}
