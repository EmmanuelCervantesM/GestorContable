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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author LenovoZ40
 */
@Entity
@Table(name = "rn_gc_nom_solicitudes_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcNomSolicitudesTbl.findAll", query = "SELECT r FROM RnGcNomSolicitudesTbl r")
    , @NamedQuery(name = "RnGcNomSolicitudesTbl.findById", query = "SELECT r FROM RnGcNomSolicitudesTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcNomSolicitudesTbl.findByCreadoPor", query = "SELECT r FROM RnGcNomSolicitudesTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcNomSolicitudesTbl.findByFechaCreacion", query = "SELECT r FROM RnGcNomSolicitudesTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcNomSolicitudesTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcNomSolicitudesTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcNomSolicitudesTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcNomSolicitudesTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcNomSolicitudesTbl.findByNominaId", query = "SELECT r FROM RnGcNomSolicitudesTbl r WHERE r.nominaId = :nominaId")
    , @NamedQuery(name = "RnGcNomSolicitudesTbl.findByPatronId", query = "SELECT r FROM RnGcNomSolicitudesTbl r WHERE r.patronId = :patronId")
    , @NamedQuery(name = "RnGcNomSolicitudesTbl.findByRegistroPatronal", query = "SELECT r FROM RnGcNomSolicitudesTbl r WHERE r.registroPatronal = :registroPatronal")
    , @NamedQuery(name = "RnGcNomSolicitudesTbl.findByNombreSolicitud", query = "SELECT r FROM RnGcNomSolicitudesTbl r WHERE r.nombreSolicitud = :nombreSolicitud")
    , @NamedQuery(name = "RnGcNomSolicitudesTbl.findByEstatus", query = "SELECT r FROM RnGcNomSolicitudesTbl r WHERE r.estatus = :estatus")})
public class RnGcNomSolicitudesTbl implements Serializable {

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
    @Column(name = "nominaId")
    private Integer nominaId;
    @Column(name = "patronId")
    private Integer patronId;
    @Size(max = 45)
    @Column(name = "registroPatronal")
    private String registroPatronal;
    @Size(max = 45)
    @Column(name = "nombreSolicitud")
    private String nombreSolicitud;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "estatus")
    private String estatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "solicitudId")
    private Collection<RnGcNomSolicitudTrabajadorTbl> rnGcNomSolicitudTrabajadorTblCollection;
    @JoinColumn(name = "periodoNominaId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RnGcNomPeriodonominaTbl periodoNominaId;

    public RnGcNomSolicitudesTbl() {
    }

    public RnGcNomSolicitudesTbl(Integer id) {
        this.id = id;
    }

    public RnGcNomSolicitudesTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion, String estatus) {
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

    public Integer getNominaId() {
        return nominaId;
    }

    public void setNominaId(Integer nominaId) {
        this.nominaId = nominaId;
    }

    public Integer getPatronId() {
        return patronId;
    }

    public void setPatronId(Integer patronId) {
        this.patronId = patronId;
    }

    public String getRegistroPatronal() {
        return registroPatronal;
    }

    public void setRegistroPatronal(String registroPatronal) {
        this.registroPatronal = registroPatronal;
    }

    public String getNombreSolicitud() {
        return nombreSolicitud;
    }

    public void setNombreSolicitud(String nombreSolicitud) {
        this.nombreSolicitud = nombreSolicitud;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public RnGcNomPeriodonominaTbl getPeriodoNominaId() {
        return periodoNominaId;
    }

    public void setPeriodoNominaId(RnGcNomPeriodonominaTbl periodoNominaId) {
        this.periodoNominaId = periodoNominaId;
    }

    @XmlTransient
    public Collection<RnGcNomSolicitudTrabajadorTbl> getRnGcNomSolicitudTrabajadorTblCollection() {
        return rnGcNomSolicitudTrabajadorTblCollection;
    }

    public void setRnGcNomSolicitudTrabajadorTblCollection(Collection<RnGcNomSolicitudTrabajadorTbl> rnGcNomSolicitudTrabajadorTblCollection) {
        this.rnGcNomSolicitudTrabajadorTblCollection = rnGcNomSolicitudTrabajadorTblCollection;
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
        if (!(object instanceof RnGcNomSolicitudesTbl)) {
            return false;
        }
        RnGcNomSolicitudesTbl other = (RnGcNomSolicitudesTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcNomSolicitudesTbl[ id=" + id + " ]";
    }
    
}
