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
@Table(name = "rn_gc_cp_seguros_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcCpSegurosTbl.findAll", query = "SELECT r FROM RnGcCpSegurosTbl r")
    , @NamedQuery(name = "RnGcCpSegurosTbl.findById", query = "SELECT r FROM RnGcCpSegurosTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcCpSegurosTbl.findByNombreAseguradora", query = "SELECT r FROM RnGcCpSegurosTbl r WHERE r.nombreAseguradora = :nombreAseguradora")
    , @NamedQuery(name = "RnGcCpSegurosTbl.findByPoliza", query = "SELECT r FROM RnGcCpSegurosTbl r WHERE r.poliza = :poliza")
    , @NamedQuery(name = "RnGcCpSegurosTbl.findByPrimaSeguro", query = "SELECT r FROM RnGcCpSegurosTbl r WHERE r.primaSeguro = :primaSeguro")
    , @NamedQuery(name = "RnGcCpSegurosTbl.findByFechaInicioVigencia", query = "SELECT r FROM RnGcCpSegurosTbl r WHERE r.fechaInicioVigencia = :fechaInicioVigencia")
    , @NamedQuery(name = "RnGcCpSegurosTbl.findByFechaFinVigencia", query = "SELECT r FROM RnGcCpSegurosTbl r WHERE r.fechaFinVigencia = :fechaFinVigencia")
    , @NamedQuery(name = "RnGcCpSegurosTbl.findByTipoSeguro", query = "SELECT r FROM RnGcCpSegurosTbl r WHERE r.tipoSeguro = :tipoSeguro")
    , @NamedQuery(name = "RnGcCpSegurosTbl.findByCreadoPor", query = "SELECT r FROM RnGcCpSegurosTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCpSegurosTbl.findByFechaCreacion", query = "SELECT r FROM RnGcCpSegurosTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcCpSegurosTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcCpSegurosTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcCpSegurosTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcCpSegurosTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcCpSegurosTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 150)
    @Column(name = "nombreAseguradora")
    private String nombreAseguradora;
    @Size(max = 50)
    @Column(name = "poliza")
    private String poliza;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "primaSeguro")
    private Double primaSeguro;
    @Column(name = "fechaInicioVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioVigencia;
    @Column(name = "fechaFinVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaFinVigencia;
    @Size(max = 45)
    @Column(name = "tipoSeguro")
    private String tipoSeguro;
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
    @OneToMany(mappedBy = "seguroId")
    private Collection<RnGcCpUnidadesTbl> rnGcCpUnidadesTblCollection;

    public RnGcCpSegurosTbl() {
    }

    public RnGcCpSegurosTbl(Integer id) {
        this.id = id;
    }

    public RnGcCpSegurosTbl(Integer id, Date fechaCreacion) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreAseguradora() {
        return nombreAseguradora;
    }

    public void setNombreAseguradora(String nombreAseguradora) {
        this.nombreAseguradora = nombreAseguradora;
    }

    public String getPoliza() {
        return poliza;
    }

    public void setPoliza(String poliza) {
        this.poliza = poliza;
    }

    public Double getPrimaSeguro() {
        return primaSeguro;
    }

    public void setPrimaSeguro(Double primaSeguro) {
        this.primaSeguro = primaSeguro;
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

    public String getTipoSeguro() {
        return tipoSeguro;
    }

    public void setTipoSeguro(String tipoSeguro) {
        this.tipoSeguro = tipoSeguro;
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
    public Collection<RnGcCpUnidadesTbl> getRnGcCpUnidadesTblCollection() {
        return rnGcCpUnidadesTblCollection;
    }

    public void setRnGcCpUnidadesTblCollection(Collection<RnGcCpUnidadesTbl> rnGcCpUnidadesTblCollection) {
        this.rnGcCpUnidadesTblCollection = rnGcCpUnidadesTblCollection;
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
        if (!(object instanceof RnGcCpSegurosTbl)) {
            return false;
        }
        RnGcCpSegurosTbl other = (RnGcCpSegurosTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcCpSegurosTbl[ id=" + id + " ]";
    }
    
}
