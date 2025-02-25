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
 * @author Consultor
 */
@Entity
@Table(name = "rn_gc_codigo_agrupador_sat_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcCodigoAgrupadorSatTbl.findAll", query = "SELECT r FROM RnGcCodigoAgrupadorSatTbl r")
    , @NamedQuery(name = "RnGcCodigoAgrupadorSatTbl.findById", query = "SELECT r FROM RnGcCodigoAgrupadorSatTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcCodigoAgrupadorSatTbl.findByNivel", query = "SELECT r FROM RnGcCodigoAgrupadorSatTbl r WHERE r.nivel = :nivel")
    , @NamedQuery(name = "RnGcCodigoAgrupadorSatTbl.findByCodigoAgrupador", query = "SELECT r FROM RnGcCodigoAgrupadorSatTbl r WHERE r.codigoAgrupador = :codigoAgrupador")
    , @NamedQuery(name = "RnGcCodigoAgrupadorSatTbl.findByNombreCuenta", query = "SELECT r FROM RnGcCodigoAgrupadorSatTbl r WHERE r.nombreCuenta = :nombreCuenta")
    , @NamedQuery(name = "RnGcCodigoAgrupadorSatTbl.findByAdicional1", query = "SELECT r FROM RnGcCodigoAgrupadorSatTbl r WHERE r.adicional1 = :adicional1")
    , @NamedQuery(name = "RnGcCodigoAgrupadorSatTbl.findByAdicional2", query = "SELECT r FROM RnGcCodigoAgrupadorSatTbl r WHERE r.adicional2 = :adicional2")
    , @NamedQuery(name = "RnGcCodigoAgrupadorSatTbl.findByAdicional3", query = "SELECT r FROM RnGcCodigoAgrupadorSatTbl r WHERE r.adicional3 = :adicional3")
    , @NamedQuery(name = "RnGcCodigoAgrupadorSatTbl.findByCreadoPor", query = "SELECT r FROM RnGcCodigoAgrupadorSatTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCodigoAgrupadorSatTbl.findByFechaCreacion", query = "SELECT r FROM RnGcCodigoAgrupadorSatTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcCodigoAgrupadorSatTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcCodigoAgrupadorSatTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcCodigoAgrupadorSatTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcCodigoAgrupadorSatTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcCodigoAgrupadorSatTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nivel")
    private String nivel;
    @Basic(optional = true)
    @NotNull
    @Size(max = 45)
    @Column(name = "codigoAgrupador")
    private String codigoAgrupador;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "nombreCuenta")
    private String nombreCuenta;
    @Size(max = 45)
    @Column(name = "adicional1")
    private String adicional1;
    @Size(max = 45)
    @Column(name = "adicional2")
    private String adicional2;
    @Size(max = 45)
    @Column(name = "adicional3")
    private String adicional3;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoAgrupadorSatId")
    private Collection<RnGcCatalogoCuentasTbl> rnGcCatalogoCuentasTblCollection;
    @OneToMany(mappedBy = "codigoAgrupadorSatId")
    private Collection<RnGcCodigoAgrupadorSatTbl> rnGcCodigoAgrupadorSatTblCollection;
    @JoinColumn(name = "codigo_agrupador_sat_id", referencedColumnName = "id")
    @ManyToOne
    private RnGcCodigoAgrupadorSatTbl codigoAgrupadorSatId;

    public RnGcCodigoAgrupadorSatTbl() {
    }

    public RnGcCodigoAgrupadorSatTbl(Integer id) {
        this.id = id;
    }

    public RnGcCodigoAgrupadorSatTbl(Integer id, String nivel, String codigoAgrupador, String nombreCuenta, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.nivel = nivel;
        this.codigoAgrupador = codigoAgrupador;
        this.nombreCuenta = nombreCuenta;
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

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getCodigoAgrupador() {
        return codigoAgrupador;
    }

    public void setCodigoAgrupador(String codigoAgrupador) {
        this.codigoAgrupador = codigoAgrupador;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public String getAdicional1() {
        return adicional1;
    }

    public void setAdicional1(String adicional1) {
        this.adicional1 = adicional1;
    }

    public String getAdicional2() {
        return adicional2;
    }

    public void setAdicional2(String adicional2) {
        this.adicional2 = adicional2;
    }

    public String getAdicional3() {
        return adicional3;
    }

    public void setAdicional3(String adicional3) {
        this.adicional3 = adicional3;
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

    @XmlTransient
    public Collection<RnGcCatalogoCuentasTbl> getRnGcCatalogoCuentasTblCollection() {
        return rnGcCatalogoCuentasTblCollection;
    }

    public void setRnGcCatalogoCuentasTblCollection(Collection<RnGcCatalogoCuentasTbl> rnGcCatalogoCuentasTblCollection) {
        this.rnGcCatalogoCuentasTblCollection = rnGcCatalogoCuentasTblCollection;
    }

    @XmlTransient
    public Collection<RnGcCodigoAgrupadorSatTbl> getRnGcCodigoAgrupadorSatTblCollection() {
        return rnGcCodigoAgrupadorSatTblCollection;
    }

    public void setRnGcCodigoAgrupadorSatTblCollection(Collection<RnGcCodigoAgrupadorSatTbl> rnGcCodigoAgrupadorSatTblCollection) {
        this.rnGcCodigoAgrupadorSatTblCollection = rnGcCodigoAgrupadorSatTblCollection;
    }

    public RnGcCodigoAgrupadorSatTbl getCodigoAgrupadorSatId() {
        return codigoAgrupadorSatId;
    }

    public void setCodigoAgrupadorSatId(RnGcCodigoAgrupadorSatTbl codigoAgrupadorSatId) {
        this.codigoAgrupadorSatId = codigoAgrupadorSatId;
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
        if (!(object instanceof RnGcCodigoAgrupadorSatTbl)) {
            return false;
        }
        RnGcCodigoAgrupadorSatTbl other = (RnGcCodigoAgrupadorSatTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcCodigoAgrupadorSatTbl[ id=" + id + " ]";
    }
    
}
