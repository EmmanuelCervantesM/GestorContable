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
 * @author maxoc
 */
@Entity
@Table(name = "rn_gc_cp_origendestino_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcCpOrigendestinoTbl.findAll", query = "SELECT r FROM RnGcCpOrigendestinoTbl r")
    , @NamedQuery(name = "RnGcCpOrigendestinoTbl.findById", query = "SELECT r FROM RnGcCpOrigendestinoTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcCpOrigendestinoTbl.findByOrigen", query = "SELECT r FROM RnGcCpOrigendestinoTbl r WHERE r.origen = :origen")
    , @NamedQuery(name = "RnGcCpOrigendestinoTbl.findByDestino", query = "SELECT r FROM RnGcCpOrigendestinoTbl r WHERE r.destino = :destino")
    , @NamedQuery(name = "RnGcCpOrigendestinoTbl.findByFechaSalida", query = "SELECT r FROM RnGcCpOrigendestinoTbl r WHERE r.fechaSalida = :fechaSalida")
    , @NamedQuery(name = "RnGcCpOrigendestinoTbl.findByFechaLlegada", query = "SELECT r FROM RnGcCpOrigendestinoTbl r WHERE r.fechaLlegada = :fechaLlegada")
    , @NamedQuery(name = "RnGcCpOrigendestinoTbl.findByCreadoPor", query = "SELECT r FROM RnGcCpOrigendestinoTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCpOrigendestinoTbl.findByFechaCreacion", query = "SELECT r FROM RnGcCpOrigendestinoTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcCpOrigendestinoTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcCpOrigendestinoTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcCpOrigendestinoTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcCpOrigendestinoTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcCpOrigendestinoTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 10)
    @Column(name = "origen")
    private String origen;
    @Size(max = 10)
    @Column(name = "destino")
    private String destino;
    @Column(name = "fechaSalida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSalida;
    @Column(name = "fechaLlegada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLlegada;
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
    @Column(name = "distancia")
    private double distancia;
    @JoinColumn(name = "dirClienteProveedorId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcDireccionesTbl dirClienteProveedorId;
    @JoinColumn(name = "cartaPorteId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcCpCartaPorteTbl cartaPorteId;
    @Size(max = 100)
    @Column(name = "nombreDestinatario")
    private String nombreDestinatario;
    @Size(max = 15)
    @Column(name = "rfcDestinatario")
    private String rfcDestinatario;
    @Size(max = 10)
    @Column(name = "clavePais")
    private String clavePais;
    @Size(max = 10)
    @Column(name = "claveEstado")
    private String claveEstado;
    @Size(max = 10)
    @Column(name = "claveMunicipio")
    private String claveMunicipio;
    @Size(max = 10)
    @Column(name = "claveColonia")
    private String claveColonia;
    @Size(max = 10)
    @Column(name = "codPostal")
    private String codPostal;
    @Size(max = 100)
    @Column(name = "calle")
    private String calle;
    @Size(max = 10)
    @Column(name = "noExt")
    private String noExt;
    @Size(max = 10)
    @Column(name = "noInt")
    private String noInt;
    @OneToMany(mappedBy = "destinoId")
    private Collection<RnGcCpProductosDestinosTbl> rnGcCpProductosDestinosTblCollection;

    public RnGcCpOrigendestinoTbl() {
    }

    public RnGcCpOrigendestinoTbl(Integer id) {
        this.id = id;
    }

    public RnGcCpOrigendestinoTbl(Integer id, Date fechaCreacion) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Date getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(Date fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
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

    public double getDistancia() {
        return distancia;
    }

    public RnGcDireccionesTbl getDirClienteProveedorId() {
        return dirClienteProveedorId;
    }

    public void setDirClienteProveedorId(RnGcDireccionesTbl dirClienteProveedorId) {
        this.dirClienteProveedorId = dirClienteProveedorId;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public RnGcCpCartaPorteTbl getCartaPorteId() {
        return cartaPorteId;
    }

    public void setCartaPorteId(RnGcCpCartaPorteTbl cartaPorteId) {
        this.cartaPorteId = cartaPorteId;
    }

    public String getNombreDestinatario() {
        return nombreDestinatario;
    }

    public void setNombreDestinatario(String nombreDestinatario) {
        this.nombreDestinatario = nombreDestinatario;
    }

    public String getRfcDestinatario() {
        return rfcDestinatario;
    }

    public void setRfcDestinatario(String rfcDestinatario) {
        this.rfcDestinatario = rfcDestinatario;
    }

    public String getClavePais() {
        return clavePais;
    }

    public void setClavePais(String clavePais) {
        this.clavePais = clavePais;
    }

    public String getClaveEstado() {
        return claveEstado;
    }

    public void setClaveEstado(String claveEstado) {
        this.claveEstado = claveEstado;
    }

    public String getClaveMunicipio() {
        return claveMunicipio;
    }

    public void setClaveMunicipio(String claveMunicipio) {
        this.claveMunicipio = claveMunicipio;
    }

    public String getClaveColonia() {
        return claveColonia;
    }

    public void setClaveColonia(String claveColonia) {
        this.claveColonia = claveColonia;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNoExt() {
        return noExt;
    }

    public void setNoExt(String noExt) {
        this.noExt = noExt;
    }

    public String getNoInt() {
        return noInt;
    }

    public void setNoInt(String noInt) {
        this.noInt = noInt;
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
        if (!(object instanceof RnGcCpOrigendestinoTbl)) {
            return false;
        }
        RnGcCpOrigendestinoTbl other = (RnGcCpOrigendestinoTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcCpOrigendestinoTbl[ id=" + id + " ]";
    }
    
}
