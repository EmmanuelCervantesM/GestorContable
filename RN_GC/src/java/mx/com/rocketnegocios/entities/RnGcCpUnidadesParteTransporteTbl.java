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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author maxoc
 */
@Entity
@Table(name = "rn_gc_cp_unidades_parte_transporte_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcCpUnidadesParteTransporteTbl.findAll", query = "SELECT r FROM RnGcCpUnidadesParteTransporteTbl r")
    , @NamedQuery(name = "RnGcCpUnidadesParteTransporteTbl.findById", query = "SELECT r FROM RnGcCpUnidadesParteTransporteTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcCpUnidadesParteTransporteTbl.findByCreadoPor", query = "SELECT r FROM RnGcCpUnidadesParteTransporteTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCpUnidadesParteTransporteTbl.findByFechaCreacion", query = "SELECT r FROM RnGcCpUnidadesParteTransporteTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcCpUnidadesParteTransporteTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcCpUnidadesParteTransporteTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcCpUnidadesParteTransporteTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcCpUnidadesParteTransporteTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcCpUnidadesParteTransporteTbl.findByUnidadId", query = "SELECT r FROM RnGcCpUnidadesParteTransporteTbl r WHERE r.unidadId = :unidadId")})
public class RnGcCpUnidadesParteTransporteTbl implements Serializable {

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
    @Column(name = "ultimaActualizacionPor")
    private Integer ultimaActualizacionPor;
    @Column(name = "ultimaFechaActualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaFechaActualizacion;
    @JoinColumn(name = "unidadId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcCpUnidadesTbl unidadId;
    @JoinColumn(name = "parteTransId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcCpPartetransportesatTbl parteTransId;

    public RnGcCpUnidadesParteTransporteTbl() {
    }

    public RnGcCpUnidadesParteTransporteTbl(Integer id) {
        this.id = id;
    }

    public RnGcCpUnidadesParteTransporteTbl(Integer id, int creadoPor, Date fechaCreacion) {
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

    public RnGcCpUnidadesTbl getUnidadId() {
        return unidadId;
    }

    public void setUnidadId(RnGcCpUnidadesTbl unidadId) {
        this.unidadId = unidadId;
    }

    public RnGcCpPartetransportesatTbl getParteTransId() {
        return parteTransId;
    }

    public void setParteTransId(RnGcCpPartetransportesatTbl parteTransId) {
        this.parteTransId = parteTransId;
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
        if (!(object instanceof RnGcCpUnidadesParteTransporteTbl)) {
            return false;
        }
        RnGcCpUnidadesParteTransporteTbl other = (RnGcCpUnidadesParteTransporteTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcCpUnidadesParteTransporteTbl[ id=" + id + " ]";
    }
    
}
