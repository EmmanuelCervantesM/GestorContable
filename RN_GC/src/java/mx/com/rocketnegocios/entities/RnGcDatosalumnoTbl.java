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
 * @author Developer1
 */
@Entity
@Table(name = "rn_gc_datosalumno_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcDatosalumnoTbl.findAll", query = "SELECT r FROM RnGcDatosalumnoTbl r")
    , @NamedQuery(name = "RnGcDatosalumnoTbl.findById", query = "SELECT r FROM RnGcDatosalumnoTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcDatosalumnoTbl.findByCreadoPor", query = "SELECT r FROM RnGcDatosalumnoTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcDatosalumnoTbl.findByFechaCreacion", query = "SELECT r FROM RnGcDatosalumnoTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcDatosalumnoTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcDatosalumnoTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcDatosalumnoTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcDatosalumnoTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcDatosalumnoTbl.findByPersonaId", query = "SELECT r FROM RnGcDatosalumnoTbl r WHERE r.personasId = :personasId")})
public class RnGcDatosalumnoTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 18)
    @Column(name = "curp")
    private String curp;
    @Size(max = 50)
    @Column(name = "nivelEducativo")
    private String nivelEducativo;
    @Size(max = 100)
    @Column(name = "autRVOE")
    private String autRVOE;
    @Size(max = 13)
    @Column(name = "rfcPago")
    private String rfcPago;
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
    @JoinColumn(name = "personasId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcPersonasTbl personasId;

    public RnGcDatosalumnoTbl() {
    }

    public RnGcDatosalumnoTbl(Integer id) {
        this.id = id;
    }

    public RnGcDatosalumnoTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNivelEducativo() {
        return nivelEducativo;
    }

    public void setNivelEducativo(String nivelEducativo) {
        this.nivelEducativo = nivelEducativo;
    }

    public String getAutRVOE() {
        return autRVOE;
    }

    public void setAutRVOE(String autRVOE) {
        this.autRVOE = autRVOE;
    }

    public String getRfcPago() {
        return rfcPago;
    }

    public void setRfcPago(String rfcPago) {
        this.rfcPago = rfcPago;
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

    public RnGcPersonasTbl getPersonasId() {
        return personasId;
    }

    public void setPersonasId(RnGcPersonasTbl personasId) {
        this.personasId = personasId;
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
        if (!(object instanceof RnGcDatosalumnoTbl)) {
            return false;
        }
        RnGcDatosalumnoTbl other = (RnGcDatosalumnoTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcDatosalumnoTbl[ id=" + id + " ]";
    }

}
