/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
@Table(name = "rn_gc_firmas_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcFirmasTbl.findAll", query = "SELECT r FROM RnGcFirmasTbl r")
    , @NamedQuery(name = "RnGcFirmasTbl.findById", query = "SELECT r FROM RnGcFirmasTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcFirmasTbl.findByCargo1", query = "SELECT r FROM RnGcFirmasTbl r WHERE r.cargo1 = :cargo1")
    , @NamedQuery(name = "RnGcFirmasTbl.findByNombre1", query = "SELECT r FROM RnGcFirmasTbl r WHERE r.nombre1 = :nombre1")
    , @NamedQuery(name = "RnGcFirmasTbl.findByCargo2", query = "SELECT r FROM RnGcFirmasTbl r WHERE r.cargo2 = :cargo2")
    , @NamedQuery(name = "RnGcFirmasTbl.findByNombre2", query = "SELECT r FROM RnGcFirmasTbl r WHERE r.nombre2 = :nombre2")
    , @NamedQuery(name = "RnGcFirmasTbl.findByCargo3", query = "SELECT r FROM RnGcFirmasTbl r WHERE r.cargo3 = :cargo3")
    , @NamedQuery(name = "RnGcFirmasTbl.findByNombre3", query = "SELECT r FROM RnGcFirmasTbl r WHERE r.nombre3 = :nombre3")
    , @NamedQuery(name = "RnGcFirmasTbl.findByCargo4", query = "SELECT r FROM RnGcFirmasTbl r WHERE r.cargo4 = :cargo4")
    , @NamedQuery(name = "RnGcFirmasTbl.findByNombre4", query = "SELECT r FROM RnGcFirmasTbl r WHERE r.nombre4 = :nombre4")
    , @NamedQuery(name = "RnGcFirmasTbl.findByCargo5", query = "SELECT r FROM RnGcFirmasTbl r WHERE r.cargo5 = :cargo5")
    , @NamedQuery(name = "RnGcFirmasTbl.findByNombre5", query = "SELECT r FROM RnGcFirmasTbl r WHERE r.nombre5 = :nombre5")
    , @NamedQuery(name = "RnGcFirmasTbl.findByCreadoPor", query = "SELECT r FROM RnGcFirmasTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcFirmasTbl.findByFechaCreacion", query = "SELECT r FROM RnGcFirmasTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcFirmasTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcFirmasTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcFirmasTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcFirmasTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcFirmasTbl.findByCfdiId", query = "SELECT r FROM RnGcFirmasTbl r WHERE r.cfdiId = :cfdiId")})
public class RnGcFirmasTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Size(max = 100)
    private String cargo1;
    @Size(max = 100)
    private String nombre1;
    @Size(max = 100)
    private String cargo2;
    @Size(max = 100)
    private String nombre2;
    @Size(max = 100)
    private String cargo3;
    @Size(max = 100)
    private String nombre3;
    @Size(max = 100)
    private String cargo4;
    @Size(max = 100)
    private String nombre4;
    @Size(max = 100)
    private String cargo5;
    @Size(max = 100)
    private String nombre5;
    @Basic(optional = false)
    @NotNull
    private int creadoPor;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    private int ultimaActualizacionPor;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaFechaActualizacion;
    @JoinColumn(name = "cfdis_Id", referencedColumnName = "Id")
    @ManyToOne
    private RnGcCfdisTbl cfdiId;

    public RnGcFirmasTbl() {
    }

    public RnGcFirmasTbl(Integer id) {
        this.id = id;
    }

    public RnGcFirmasTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
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

    public String getCargo1() {
        return cargo1;
    }

    public void setCargo1(String cargo1) {
        this.cargo1 = cargo1;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getCargo2() {
        return cargo2;
    }

    public void setCargo2(String cargo2) {
        this.cargo2 = cargo2;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getCargo3() {
        return cargo3;
    }

    public void setCargo3(String cargo3) {
        this.cargo3 = cargo3;
    }

    public String getNombre3() {
        return nombre3;
    }

    public void setNombre3(String nombre3) {
        this.nombre3 = nombre3;
    }

    public String getCargo4() {
        return cargo4;
    }

    public void setCargo4(String cargo4) {
        this.cargo4 = cargo4;
    }

    public String getNombre4() {
        return nombre4;
    }

    public void setNombre4(String nombre4) {
        this.nombre4 = nombre4;
    }

    public String getCargo5() {
        return cargo5;
    }

    public void setCargo5(String cargo5) {
        this.cargo5 = cargo5;
    }

    public String getNombre5() {
        return nombre5;
    }

    public void setNombre5(String nombre5) {
        this.nombre5 = nombre5;
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
        if (!(object instanceof RnGcFirmasTbl)) {
            return false;
        }
        RnGcFirmasTbl other = (RnGcFirmasTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcFirmasTbl[ id=" + id + " ]";
    }
    
}
