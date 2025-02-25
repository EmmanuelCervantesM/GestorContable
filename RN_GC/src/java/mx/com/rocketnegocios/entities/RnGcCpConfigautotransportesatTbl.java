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
 * @author maxoc
 */
@Entity
@Table(name = "rn_gc_cp_configautotransportesat_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcCpConfigautotransportesatTbl.findAll", query = "SELECT r FROM RnGcCpConfigautotransportesatTbl r")
    , @NamedQuery(name = "RnGcCpConfigautotransportesatTbl.findById", query = "SELECT r FROM RnGcCpConfigautotransportesatTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcCpConfigautotransportesatTbl.findByClave", query = "SELECT r FROM RnGcCpConfigautotransportesatTbl r WHERE r.clave = :clave")
    , @NamedQuery(name = "RnGcCpConfigautotransportesatTbl.findByDescripcion", query = "SELECT r FROM RnGcCpConfigautotransportesatTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcCpConfigautotransportesatTbl.findByNumeroEjes", query = "SELECT r FROM RnGcCpConfigautotransportesatTbl r WHERE r.numeroEjes = :numeroEjes")
    , @NamedQuery(name = "RnGcCpConfigautotransportesatTbl.findByNumeroLlantas", query = "SELECT r FROM RnGcCpConfigautotransportesatTbl r WHERE r.numeroLlantas = :numeroLlantas")
    , @NamedQuery(name = "RnGcCpConfigautotransportesatTbl.findByRemolque", query = "SELECT r FROM RnGcCpConfigautotransportesatTbl r WHERE r.remolque = :remolque")
    , @NamedQuery(name = "RnGcCpConfigautotransportesatTbl.findByFechaInicioVigencia", query = "SELECT r FROM RnGcCpConfigautotransportesatTbl r WHERE r.fechaInicioVigencia = :fechaInicioVigencia")
    , @NamedQuery(name = "RnGcCpConfigautotransportesatTbl.findByFechaFin", query = "SELECT r FROM RnGcCpConfigautotransportesatTbl r WHERE r.fechaFin = :fechaFin")
    , @NamedQuery(name = "RnGcCpConfigautotransportesatTbl.findByCreadoPor", query = "SELECT r FROM RnGcCpConfigautotransportesatTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCpConfigautotransportesatTbl.findByFechaCreacion", query = "SELECT r FROM RnGcCpConfigautotransportesatTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcCpConfigautotransportesatTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcCpConfigautotransportesatTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcCpConfigautotransportesatTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcCpConfigautotransportesatTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcCpConfigautotransportesatTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 10)
    @Column(name = "clave")
    private String clave;
    @Size(max = 200)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 2)
    @Column(name = "numeroEjes")
    private String numeroEjes;
    @Size(max = 10)
    @Column(name = "numeroLlantas")
    private String numeroLlantas;
    @Size(max = 5)
    @Column(name = "remolque")
    private String remolque;
    @Column(name = "fechaInicioVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioVigencia;
    @Column(name = "fechaFin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Column(name = "creadoPor")
    private Integer creadoPor;
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
    @OneToMany(mappedBy = "configVehiculoId")
    private Collection<RnGcCpUnidadesTbl> rnGcCpUnidadesTblCollection1;

    public RnGcCpConfigautotransportesatTbl() {
    }

    public RnGcCpConfigautotransportesatTbl(Integer id) {
        this.id = id;
    }

    public RnGcCpConfigautotransportesatTbl(Integer id, Date fechaCreacion) {
        this.id = id;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNumeroEjes() {
        return numeroEjes;
    }

    public void setNumeroEjes(String numeroEjes) {
        this.numeroEjes = numeroEjes;
    }

    public String getNumeroLlantas() {
        return numeroLlantas;
    }

    public void setNumeroLlantas(String numeroLlantas) {
        this.numeroLlantas = numeroLlantas;
    }

    public String getRemolque() {
        return remolque;
    }

    public void setRemolque(String remolque) {
        this.remolque = remolque;
    }

    public Date getFechaInicioVigencia() {
        return fechaInicioVigencia;
    }

    public void setFechaInicioVigencia(Date fechaInicioVigencia) {
        this.fechaInicioVigencia = fechaInicioVigencia;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Integer creadoPor) {
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

    @XmlTransient
    public Collection<RnGcCpUnidadesTbl> getRnGcCpUnidadesTblCollection1() {
        return rnGcCpUnidadesTblCollection1;
    }

    public void setRnGcCpUnidadesTblCollection1(Collection<RnGcCpUnidadesTbl> rnGcCpUnidadesTblCollection1) {
        this.rnGcCpUnidadesTblCollection1 = rnGcCpUnidadesTblCollection1;
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
        if (!(object instanceof RnGcCpConfigautotransportesatTbl)) {
            return false;
        }
        RnGcCpConfigautotransportesatTbl other = (RnGcCpConfigautotransportesatTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcCpConfigautotransportesatTbl[ id=" + id + " ]";
    }
    
}
