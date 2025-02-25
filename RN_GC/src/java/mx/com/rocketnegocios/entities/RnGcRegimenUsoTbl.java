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
 * @author maxoc
 */
@Entity
@Table(name = "rn_gc_regimen_uso_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcRegimenUsoTbl.findAll", query = "SELECT r FROM RnGcRegimenUsoTbl r")
    , @NamedQuery(name = "RnGcRegimenUsoTbl.findById", query = "SELECT r FROM RnGcRegimenUsoTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcRegimenUsoTbl.findByFisica", query = "SELECT r FROM RnGcRegimenUsoTbl r WHERE r.fisica = :fisica")
    , @NamedQuery(name = "RnGcRegimenUsoTbl.findByMoral", query = "SELECT r FROM RnGcRegimenUsoTbl r WHERE r.moral = :moral")
    , @NamedQuery(name = "RnGcRegimenUsoTbl.findByCreadoPor", query = "SELECT r FROM RnGcRegimenUsoTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcRegimenUsoTbl.findByFechaCreacion", query = "SELECT r FROM RnGcRegimenUsoTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcRegimenUsoTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcRegimenUsoTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcRegimenUsoTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcRegimenUsoTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcRegimenUsoTbl.findByRegimenFisico", query = "SELECT r FROM RnGcRegimenUsoTbl r WHERE r.fisica = 'Si' and r.regimenId = :regimen")
    , @NamedQuery(name = "RnGcRegimenUsoTbl.findByRegimenMoral", query = "SELECT r FROM RnGcRegimenUsoTbl r WHERE r.moral = 'Si' and r.regimenId = :regimen")
    , @NamedQuery(name = "RnGcRegimenUsoTbl.findByRegimenUso", query = "SELECT r FROM RnGcRegimenUsoTbl r WHERE r.regimenId = :regimen and r.usocfdiId = :uso")})
public class RnGcRegimenUsoTbl implements Serializable {

    @JoinColumn(name = "regimenfiscalId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcRegimenfiscalTbl regimenId;
    @JoinColumn(name = "usocfdiId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcCatalogosusosTbl usocfdiId;
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "fisica")
    private String fisica;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "moral")
    private String moral;
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

    public RnGcRegimenUsoTbl() {
    }

    public RnGcRegimenUsoTbl(Integer id) {
        this.id = id;
    }

    public RnGcRegimenUsoTbl(Integer id, String fisica, String moral, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.fisica = fisica;
        this.moral = moral;
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

    public String getFisica() {
        return fisica;
    }

    public void setFisica(String fisica) {
        this.fisica = fisica;
    }

    public String getMoral() {
        return moral;
    }

    public void setMoral(String moral) {
        this.moral = moral;
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

    public RnGcRegimenfiscalTbl getRegimenId() {
        return regimenId;
    }

    public void setRegimenId(RnGcRegimenfiscalTbl regimenId) {
        this.regimenId = regimenId;
    }

    public RnGcCatalogosusosTbl getUsocfdiId() {
        return usocfdiId;
    }

    public void setUsocfdiId(RnGcCatalogosusosTbl usocfdiId) {
        this.usocfdiId = usocfdiId;
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
        if (!(object instanceof RnGcRegimenUsoTbl)) {
            return false;
        }
        RnGcRegimenUsoTbl other = (RnGcRegimenUsoTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcRegimenUsoTbl[ id=" + id + " ]";
    }
    
}
