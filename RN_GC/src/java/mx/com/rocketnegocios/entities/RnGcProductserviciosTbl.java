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
@Table(name = "rn_gc_productservicios_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcProductserviciosTbl.findAll", query = "SELECT r FROM RnGcProductserviciosTbl r")
    , @NamedQuery(name = "RnGcProductserviciosTbl.findById", query = "SELECT r FROM RnGcProductserviciosTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcProductserviciosTbl.findByTipoProdServ", query = "SELECT r FROM RnGcProductserviciosTbl r WHERE r.tipoProdServ = :tipoProdServ")
    , @NamedQuery(name = "RnGcProductserviciosTbl.findByClaveProductServ", query = "SELECT r FROM RnGcProductserviciosTbl r WHERE r.claveProductServ = :claveProductServ")
    , @NamedQuery(name = "RnGcProductserviciosTbl.findByNoIdentificacion", query = "SELECT r FROM RnGcProductserviciosTbl r WHERE r.noIdentificacion = :noIdentificacion")
    , @NamedQuery(name = "RnGcProductserviciosTbl.findByClaveUnidad", query = "SELECT r FROM RnGcProductserviciosTbl r WHERE r.claveUnidad = :claveUnidad")
    , @NamedQuery(name = "RnGcProductserviciosTbl.findByUnidad", query = "SELECT r FROM RnGcProductserviciosTbl r WHERE r.unidad = :unidad")
    , @NamedQuery(name = "RnGcProductserviciosTbl.findByDescripcion", query = "SELECT r FROM RnGcProductserviciosTbl r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcProductserviciosTbl.findByValorunitario", query = "SELECT r FROM RnGcProductserviciosTbl r WHERE r.valorunitario = :valorunitario")
    , @NamedQuery(name = "RnGcProductserviciosTbl.findByTipoImpuesto", query = "SELECT r FROM RnGcProductserviciosTbl r WHERE r.tipoImpuesto = :tipoImpuesto")
    , @NamedQuery(name = "RnGcProductserviciosTbl.findByImpuesto", query = "SELECT r FROM RnGcProductserviciosTbl r WHERE r.impuesto = :impuesto")
    , @NamedQuery(name = "RnGcProductserviciosTbl.findByTipofactor", query = "SELECT r FROM RnGcProductserviciosTbl r WHERE r.tipofactor = :tipofactor")
    , @NamedQuery(name = "RnGcProductserviciosTbl.findByTipoTasa", query = "SELECT r FROM RnGcProductserviciosTbl r WHERE r.tipoTasa = :tipoTasa")
    , @NamedQuery(name = "RnGcProductserviciosTbl.findByFechaInicioVigencia", query = "SELECT r FROM RnGcProductserviciosTbl r WHERE r.fechaInicioVigencia = :fechaInicioVigencia")
    , @NamedQuery(name = "RnGcProductserviciosTbl.findByCreadoPor", query = "SELECT r FROM RnGcProductserviciosTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcProductserviciosTbl.findByCreadoPorDescripcion", query = "SELECT r FROM RnGcProductserviciosTbl r WHERE r.creadoPor = :creadoPor and r.descripcion = :descripcion")
    , @NamedQuery(name = "RnGcProductserviciosTbl.findByFechaCreacion", query = "SELECT r FROM RnGcProductserviciosTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcProductserviciosTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcProductserviciosTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcProductserviciosTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcProductserviciosTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcProductserviciosTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tipoProdServ")
    private String tipoProdServ;
    @Basic(optional = false)
    @NotNull
    @Column(name = "claveProductServ")
    private String claveProductServ;
    @Size(max = 45)
    @Column(name = "noIdentificacion")
    private String noIdentificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "claveUnidad")
    private String claveUnidad;
    @Size(max = 45)
    @Column(name = "unidad")
    private String unidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "Descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valorunitario")
    private double valorunitario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tipoImpuesto")
    private String tipoImpuesto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "impuesto")
    private String impuesto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tipofactor")
    private String tipofactor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipoTasa")
    private double tipoTasa;
    @Column(name = "fechaInicioVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioVigencia;
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
    @JoinColumn(name = "cfdis_Id", referencedColumnName = "Id")
    @ManyToOne
    private RnGcCfdisTbl cfdisId;
    @Column(name = "tipoImpuesto2")
    private String tipoImpuesto2;
    @Column(name = "impuesto2")
    private String impuesto2;
    @Column(name = "tipoFactor2")
    private String tipoFactor2;
    @Column(name = "tipoTasa2")
    private Double tipoTasa2;
    @Column(name = "tipoImpuesto3")
    private String tipoImpuesto3;
    @Column(name = "impuesto3")
    private String impuesto3;
    @Column(name = "tipoFactor3")
    private String tipoFactor3;
    @Column(name = "tipoTasa3")
    private Double tipoTasa3;
    @Column(name = "tipoImpuesto4")
    private String tipoImpuesto4;
    @Column(name = "impuesto4")
    private String impuesto4;
    @Column(name = "tipoFactor4")
    private String tipoFactor4;
    @Column(name = "tipoTasa4")
    private Double tipoTasa4;
    @Column(name = "peso")
    private double peso;
    @Column(name = "peligroso")
    private String peligroso;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoId")
    private Collection<RnGcCfdisLineasTbl> rnGcCfdisLineasTblCollection;
    @JoinColumn(name = "objetoImpId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcObjetoimpuestoTbl objetoImpId;
    @OneToMany(mappedBy = "productoId")
    private Collection<RnGcCpProductosDestinosTbl> rnGcCpProductosDestinosTblCollection;

    public RnGcProductserviciosTbl() {
    }

    public RnGcProductserviciosTbl(Integer id) {
        this.id = id;
    }

    public RnGcProductserviciosTbl(Integer id, String tipoProdServ, String claveProductServ, String claveUnidad, String descripcion, double valorunitario, String tipoImpuesto, String impuesto, String tipofactor, double tipoTasa, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.tipoProdServ = tipoProdServ;
        this.claveProductServ = claveProductServ;
        this.claveUnidad = claveUnidad;
        this.descripcion = descripcion;
        this.valorunitario = valorunitario;
        this.tipoImpuesto = tipoImpuesto;
        this.impuesto = impuesto;
        this.tipofactor = tipofactor;
        this.tipoTasa = tipoTasa;
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

    public String getTipoProdServ() {
        return tipoProdServ;
    }

    public void setTipoProdServ(String tipoProdServ) {
        this.tipoProdServ = tipoProdServ;
    }

    public String getClaveProductServ() {
        if(claveProductServ == null) {
            this.claveProductServ = "-";
        }
        return claveProductServ;
    }

    public void setClaveProductServ(String claveProductServ) {
        this.claveProductServ = claveProductServ;
    }

    public String getNoIdentificacion() {
        return noIdentificacion;
    }

    public void setNoIdentificacion(String noIdentificacion) {
        this.noIdentificacion = noIdentificacion;
    }

    public String getClaveUnidad() {
        if(claveUnidad == null) {
            this.claveUnidad = "-";
        }
        return claveUnidad;
    }

    public void setClaveUnidad(String claveUnidad) {
        this.claveUnidad = claveUnidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getDescripcion() {
        if(descripcion == null) {
            this.descripcion = "-";
        }
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getValorunitario() {
        if(valorunitario < 0.0) {
            this.valorunitario = 0.0;
        }
        return valorunitario;
    }

    public void setValorunitario(double valorunitario) {
        this.valorunitario = valorunitario;
    }

    public String getTipoImpuesto() {
        if(tipoImpuesto == null) {
            this.tipoImpuesto = "-";
        }
        return tipoImpuesto;
    }

    public void setTipoImpuesto(String tipoImpuesto) {
        this.tipoImpuesto = tipoImpuesto;
    }

    public String getImpuesto() {
        if(impuesto == null) {
            this.impuesto = "-";
        }
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public String getTipofactor() {
        if(tipofactor == null) {
            this.tipofactor = "-";
        }
        return tipofactor;
    }

    public void setTipofactor(String tipofactor) {
        this.tipofactor = tipofactor;
    }

    public double getTipoTasa() {
        return tipoTasa;
    }

    public void setTipoTasa(double tipoTasa) {
        this.tipoTasa = tipoTasa;
    }

    public Date getFechaInicioVigencia() {
        return fechaInicioVigencia;
    }

    public void setFechaInicioVigencia(Date fechaInicioVigencia) {
        this.fechaInicioVigencia = fechaInicioVigencia;
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

    public RnGcCfdisTbl getCfdisId() {
        return cfdisId;
    }

    public void setCfdisId(RnGcCfdisTbl cfdisId) {
        this.cfdisId = cfdisId;
    }

    public String getTipoImpuesto2() {
        return tipoImpuesto2;
    }

    public void setTipoImpuesto2(String tipoImpuesto2) {
        this.tipoImpuesto2 = tipoImpuesto2;
    }

    public String getImpuesto2() {
        return impuesto2;
    }

    public void setImpuesto2(String impuesto2) {
        this.impuesto2 = impuesto2;
    }

    public String getTipoFactor2() {
        return tipoFactor2;
    }

    public void setTipoFactor2(String tipoFactor2) {
        this.tipoFactor2 = tipoFactor2;
    }

    public Double getTipoTasa2() {
        if(tipoTasa2 == null) {
            this.tipoTasa2 = 0.0;
        }
        return tipoTasa2;
    }

    public void setTipoTasa2(Double tipoTasa2) {
        this.tipoTasa2 = tipoTasa2;
    }

    public String getTipoImpuesto3() {
        return tipoImpuesto3;
    }

    public void setTipoImpuesto3(String tipoImpuesto3) {
        this.tipoImpuesto3 = tipoImpuesto3;
    }

    public String getImpuesto3() {
        return impuesto3;
    }

    public void setImpuesto3(String impuesto3) {
        this.impuesto3 = impuesto3;
    }

    public String getTipoFactor3() {
        return tipoFactor3;
    }

    public void setTipoFactor3(String tipoFactor3) {
        this.tipoFactor3 = tipoFactor3;
    }

    public Double getTipoTasa3() {
        if(tipoTasa3 == null) {
            this.tipoTasa3 = 0.0;
        }
        return tipoTasa3;
    }

    public void setTipoTasa3(Double tipoTasa3) {
        this.tipoTasa3 = tipoTasa3;
    }

    public String getTipoImpuesto4() {
        return tipoImpuesto4;
    }

    public void setTipoImpuesto4(String tipoImpuesto4) {
        this.tipoImpuesto4 = tipoImpuesto4;
    }

    public String getImpuesto4() {
        return impuesto4;
    }

    public void setImpuesto4(String impuesto4) {
        this.impuesto4 = impuesto4;
    }

    public String getTipoFactor4() {
        return tipoFactor4;
    }

    public void setTipoFactor4(String tipoFactor4) {
        this.tipoFactor4 = tipoFactor4;
    }

    public Double getTipoTasa4() {
        if(tipoTasa4 == null) {
            this.tipoTasa4 = 0.0;
        }
        return tipoTasa4;
    }

    public void setTipoTasa4(Double tipoTasa4) {
        this.tipoTasa4 = tipoTasa4;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getPeligroso() {
        return peligroso;
    }

    public void setPeligroso(String peligroso) {
        this.peligroso = peligroso;
    }
    
    @XmlTransient
    public Collection<RnGcCfdisLineasTbl> getRnGcCfdisLineasTblCollection() {
        return rnGcCfdisLineasTblCollection;
    }

    public void setRnGcCfdisLineasTblCollection(Collection<RnGcCfdisLineasTbl> rnGcCfdisLineasTblCollection) {
        this.rnGcCfdisLineasTblCollection = rnGcCfdisLineasTblCollection;
    }

    public RnGcObjetoimpuestoTbl getObjetoImpId() {
        return objetoImpId;
    }

    public void setObjetoImpId(RnGcObjetoimpuestoTbl objetoImpId) {
        this.objetoImpId = objetoImpId;
    }
    
    @XmlTransient
    public Collection<RnGcCpProductosDestinosTbl> getRnGcCpProductosDestinosTblCollection() {
        return rnGcCpProductosDestinosTblCollection;
    }

    public void setRnGcCpProductosDestinosTblCollection(Collection<RnGcCpProductosDestinosTbl> rnGcCpProductosDestinosTblCollection) {
        this.rnGcCpProductosDestinosTblCollection = rnGcCpProductosDestinosTblCollection;
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
        if (!(object instanceof RnGcProductserviciosTbl)) {
            return false;
        }
        RnGcProductserviciosTbl other = (RnGcProductserviciosTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcProductserviciosTbl[ id=" + id + " ]";
    }
    
}
