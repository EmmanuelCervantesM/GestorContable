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
@Table(name = "rn_gc_cp_derechospasosat_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcCpDerechospasosatTbl.findAll", query = "SELECT r FROM RnGcCpDerechospasosatTbl r")
    , @NamedQuery(name = "RnGcCpDerechospasosatTbl.findById", query = "SELECT r FROM RnGcCpDerechospasosatTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcCpDerechospasosatTbl.findByClave", query = "SELECT r FROM RnGcCpDerechospasosatTbl r WHERE r.clave = :clave")
    , @NamedQuery(name = "RnGcCpDerechospasosatTbl.findByDerechoDePaso", query = "SELECT r FROM RnGcCpDerechospasosatTbl r WHERE r.derechoDePaso = :derechoDePaso")
    , @NamedQuery(name = "RnGcCpDerechospasosatTbl.findByEntre", query = "SELECT r FROM RnGcCpDerechospasosatTbl r WHERE r.entre = :entre")
    , @NamedQuery(name = "RnGcCpDerechospasosatTbl.findByHasta", query = "SELECT r FROM RnGcCpDerechospasosatTbl r WHERE r.hasta = :hasta")
    , @NamedQuery(name = "RnGcCpDerechospasosatTbl.findByOtorgaRecibe", query = "SELECT r FROM RnGcCpDerechospasosatTbl r WHERE r.otorgaRecibe = :otorgaRecibe")
    , @NamedQuery(name = "RnGcCpDerechospasosatTbl.findByConcesionario", query = "SELECT r FROM RnGcCpDerechospasosatTbl r WHERE r.concesionario = :concesionario")
    , @NamedQuery(name = "RnGcCpDerechospasosatTbl.findByFechaInicioVigencia", query = "SELECT r FROM RnGcCpDerechospasosatTbl r WHERE r.fechaInicioVigencia = :fechaInicioVigencia")
    , @NamedQuery(name = "RnGcCpDerechospasosatTbl.findByFechaFinVigencia", query = "SELECT r FROM RnGcCpDerechospasosatTbl r WHERE r.fechaFinVigencia = :fechaFinVigencia")
    , @NamedQuery(name = "RnGcCpDerechospasosatTbl.findByCreadoPor", query = "SELECT r FROM RnGcCpDerechospasosatTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCpDerechospasosatTbl.findByFechaCreacion", query = "SELECT r FROM RnGcCpDerechospasosatTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcCpDerechospasosatTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcCpDerechospasosatTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcCpDerechospasosatTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcCpDerechospasosatTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcCpDerechospasosatTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Size(max = 10)
    @Column(name = "clave")
    private String clave;
    @Size(max = 20)
    @Column(name = "derechoDePaso")
    private String derechoDePaso;
    @Size(max = 150)
    @Column(name = "entre")
    private String entre;
    @Size(max = 150)
    @Column(name = "hasta")
    private String hasta;
    @Size(max = 10)
    @Column(name = "otorgaRecibe")
    private String otorgaRecibe;
    @Size(max = 200)
    @Column(name = "concesionario")
    private String concesionario;
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

    public RnGcCpDerechospasosatTbl() {
    }

    public RnGcCpDerechospasosatTbl(Integer id) {
        this.id = id;
    }

    public RnGcCpDerechospasosatTbl(Integer id, int creadoPor, Date fechaCreacion) {
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

    public String getDerechoDePaso() {
        return derechoDePaso;
    }

    public void setDerechoDePaso(String derechoDePaso) {
        this.derechoDePaso = derechoDePaso;
    }

    public String getEntre() {
        return entre;
    }

    public void setEntre(String entre) {
        this.entre = entre;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    }

    public String getOtorgaRecibe() {
        return otorgaRecibe;
    }

    public void setOtorgaRecibe(String otorgaRecibe) {
        this.otorgaRecibe = otorgaRecibe;
    }

    public String getConcesionario() {
        return concesionario;
    }

    public void setConcesionario(String concesionario) {
        this.concesionario = concesionario;
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
        if (!(object instanceof RnGcCpDerechospasosatTbl)) {
            return false;
        }
        RnGcCpDerechospasosatTbl other = (RnGcCpDerechospasosatTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcCpDerechospasosatTbl[ id=" + id + " ]";
    }
    
}
