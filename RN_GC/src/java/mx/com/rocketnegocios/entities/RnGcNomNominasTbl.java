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
@Table(name = "rn_gc_nom_nominas_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcNomNominasTbl.findAll", query = "SELECT r FROM RnGcNomNominasTbl r")
    , @NamedQuery(name = "RnGcNomNominasTbl.findById", query = "SELECT r FROM RnGcNomNominasTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcNomNominasTbl.findByCreadoPor", query = "SELECT r FROM RnGcNomNominasTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcNomNominasTbl.findByFechaCreacion", query = "SELECT r FROM RnGcNomNominasTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcNomNominasTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcNomNominasTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcNomNominasTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcNomNominasTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcNomNominasTbl.findByNombreNomina", query = "SELECT r FROM RnGcNomNominasTbl r WHERE r.nombreNomina = :nombreNomina")
    , @NamedQuery(name = "RnGcNomNominasTbl.findByEsConfidencial", query = "SELECT r FROM RnGcNomNominasTbl r WHERE r.esConfidencial = :esConfidencial")
    , @NamedQuery(name = "RnGcNomNominasTbl.findByPatronId", query = "SELECT r FROM RnGcNomNominasTbl r WHERE r.patronId = :patronId")})
public class RnGcNomNominasTbl implements Serializable {

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
    @Size(max = 45)
    @Column(name = "nombreNomina")
    private String nombreNomina;
    @Size(max = 1)
    @Column(name = "esConfidencial")
    private String esConfidencial;
    @Column(name = "patronId")
    private Integer patronId;
    @JoinColumn(name = "periodicidadPagoId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RnGcNomPeriodicidadpagoTbl periodicidadPagoId;
    @JoinColumn(name = "origenRecursoId", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private RnGcNomOrigenrecursoTbl origenRecursoId;
    @JoinColumn(name = "tipoNominaId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RnGcNomTiponominaTbl tipoNominaId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nominaId")
    private Collection<RnGcNomPeriodonominaTbl> rnGcNomPeriodonominaTblCollection;

    public RnGcNomNominasTbl() {
    }

    public RnGcNomNominasTbl(Integer id) {
        this.id = id;
    }

    public RnGcNomNominasTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
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

    public String getNombreNomina() {
        return nombreNomina;
    }

    public void setNombreNomina(String nombreNomina) {
        this.nombreNomina = nombreNomina;
    }

    public String getEsConfidencial() {
        return esConfidencial;
    }

    public void setEsConfidencial(String esConfidencial) {
        this.esConfidencial = esConfidencial;
    }

    public Integer getPatronId() {
        return patronId;
    }

    public void setPatronId(Integer patronId) {
        this.patronId = patronId;
    }

    public RnGcNomPeriodicidadpagoTbl getPeriodicidadPagoId() {
        return periodicidadPagoId;
    }

    public void setPeriodicidadPagoId(RnGcNomPeriodicidadpagoTbl periodicidadPagoId) {
        this.periodicidadPagoId = periodicidadPagoId;
    }

    public RnGcNomOrigenrecursoTbl getOrigenRecursoId() {
        return origenRecursoId;
    }

    public void setOrigenRecursoId(RnGcNomOrigenrecursoTbl origenRecursoId) {
        this.origenRecursoId = origenRecursoId;
    }
    
    public RnGcNomTiponominaTbl getTipoNominaId() {
        return tipoNominaId;
    }

    public void setTipoNominaId(RnGcNomTiponominaTbl tipoNominaId) {
        this.tipoNominaId = tipoNominaId;
    }

    @XmlTransient
    public Collection<RnGcNomPeriodonominaTbl> getRnGcNomPeriodonominaTblCollection() {
        return rnGcNomPeriodonominaTblCollection;
    }

    public void setRnGcNomPeriodonominaTblCollection(Collection<RnGcNomPeriodonominaTbl> rnGcNomPeriodonominaTblCollection) {
        this.rnGcNomPeriodonominaTblCollection = rnGcNomPeriodonominaTblCollection;
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
        if (!(object instanceof RnGcNomNominasTbl)) {
            return false;
        }
        RnGcNomNominasTbl other = (RnGcNomNominasTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcNomNominasTbl[ id=" + id + " ]";
    }
    
}
