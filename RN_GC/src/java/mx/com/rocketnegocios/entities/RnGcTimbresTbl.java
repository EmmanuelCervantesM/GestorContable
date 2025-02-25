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
@Table(name = "rn_gc_timbres_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcTimbresTbl.findAll", query = "SELECT r FROM RnGcTimbresTbl r")
    , @NamedQuery(name = "RnGcTimbresTbl.findById", query = "SELECT r FROM RnGcTimbresTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcTimbresTbl.findByProveedor", query = "SELECT r FROM RnGcTimbresTbl r WHERE r.proveedor = :proveedor")
    , @NamedQuery(name = "RnGcTimbresTbl.findByTimbresTotal", query = "SELECT r FROM RnGcTimbresTbl r WHERE r.timbresTotal = :timbresTotal")
    , @NamedQuery(name = "RnGcTimbresTbl.findByTimbresRestantes", query = "SELECT r FROM RnGcTimbresTbl r WHERE r.timbresRestantes = :timbresRestantes")
    , @NamedQuery(name = "RnGcTimbresTbl.findByFechaInicio", query = "SELECT r FROM RnGcTimbresTbl r WHERE r.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "RnGcTimbresTbl.findByFechaFin", query = "SELECT r FROM RnGcTimbresTbl r WHERE r.fechaFin = :fechaFin")
    , @NamedQuery(name = "RnGcTimbresTbl.findByCreadoPor", query = "SELECT r FROM RnGcTimbresTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcTimbresTbl.findByFechaCreacion", query = "SELECT r FROM RnGcTimbresTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcTimbresTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcTimbresTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcTimbresTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcTimbresTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcTimbresTbl.findByUsuarioId", query = "SELECT r FROM RnGcTimbresTbl r WHERE r.usuarioId = :usuarioId")
    , @NamedQuery(name = "RnGcTimbresTbl.findByProveedorUsuario", query = "SELECT r FROM RnGcTimbresTbl r WHERE r.proveedor = :proveedor AND r.usuarioId = :usuarioId AND r.estado = 'Activo' and r.timbresRestantes > 0")
    , @NamedQuery(name = "RnGcTimbresTbl.SUMTimbresActivos", query = "SELECT SUM(r.timbresRestantes) FROM RnGcTimbresTbl r WHERE r.proveedor = :proveedor AND r.usuarioId = :usuarioId AND r.estado = 'Activo'")
    , @NamedQuery(name = "RnGcTimbresTbl.SUMTimbresTotal", query = "SELECT SUM(r.timbresTotal) FROM RnGcTimbresTbl r WHERE r.proveedor = :proveedor AND r.usuarioId = :usuarioId AND r.estado = 'Activo'")
    , @NamedQuery(name = "RnGcTimbresTbl.findByUsuarioEstado", query = "SELECT r FROM RnGcTimbresTbl r WHERE r.usuarioId = :usuarioId AND r.estado = 'Activo'") })
public class RnGcTimbresTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Size(min = 1, max = 50)
    @Column(name = "proveedor")
    private String proveedor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timbresTotal")
    private int timbresTotal;
    @Column(name = "timbresRestantes")
    private int timbresRestantes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timbresUsados")
    private int timbresUsados;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fechaFin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
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
    @JoinColumn(name = "usuarios_Id", referencedColumnName = "Id")
    @ManyToOne
    private RnGcUsuariosTbl usuarioId;

    public RnGcTimbresTbl() {
    }

    public RnGcTimbresTbl(Integer id) {
        this.id = id;
    }

    public RnGcTimbresTbl(Integer id, String proveedor, int timbresTotal, int timbresRestantes, Date fechaInicio, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.proveedor = proveedor;
        this.timbresTotal = timbresTotal;
        this.timbresRestantes = timbresRestantes;
        this.fechaInicio = fechaInicio;
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

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public int getTimbresTotal() {
        return timbresTotal;
    }

    public void setTimbresTotal(int timbresTotal) {
        this.timbresTotal = timbresTotal;
    }

    public int getTimbresRestantes() {
        return timbresRestantes;
    }

    public void setTimbresRestantes(int timbresRestantes) {
        this.timbresRestantes = timbresRestantes;
    }

    public int getTimbresUsados() {
        return timbresUsados;
    }

    public void setTimbresUsados(int timbresUsados) {
        this.timbresUsados = timbresUsados;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaInicio() {
        if(fechaInicio == null) {
            this.fechaInicio = new Date();
        }
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
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

    public RnGcUsuariosTbl getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(RnGcUsuariosTbl usuarioId) {
        this.usuarioId = usuarioId;
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
        if (!(object instanceof RnGcTimbresTbl)) {
            return false;
        }
        RnGcTimbresTbl other = (RnGcTimbresTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcTimbresTbl[ id=" + id + " ]";
    }
    
}
