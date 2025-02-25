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

/**
 *
 * @author maxoc
 */
@Entity
@Table(name = "rn_gc_cp_carta_porte_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcCpCartaPorteTbl.findAll", query = "SELECT r FROM RnGcCpCartaPorteTbl r")
    , @NamedQuery(name = "RnGcCpCartaPorteTbl.findById", query = "SELECT r FROM RnGcCpCartaPorteTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcCpCartaPorteTbl.findByClave", query = "SELECT r FROM RnGcCpCartaPorteTbl r WHERE r.clave = :clave")
    , @NamedQuery(name = "RnGcCpCartaPorteTbl.findByOrigen", query = "SELECT r FROM RnGcCpCartaPorteTbl r WHERE r.origen = :origen")
    , @NamedQuery(name = "RnGcCpCartaPorteTbl.findByDestino", query = "SELECT r FROM RnGcCpCartaPorteTbl r WHERE r.destino = :destino")
    , @NamedQuery(name = "RnGcCpCartaPorteTbl.findByTransporteInter", query = "SELECT r FROM RnGcCpCartaPorteTbl r WHERE r.transporteInter = :transporteInter")
    , @NamedQuery(name = "RnGcCpCartaPorteTbl.findByTotalDistancia", query = "SELECT r FROM RnGcCpCartaPorteTbl r WHERE r.totalDistancia = :totalDistancia")
    , @NamedQuery(name = "RnGcCpCartaPorteTbl.findByFechaSalida", query = "SELECT r FROM RnGcCpCartaPorteTbl r WHERE r.fechaSalida = :fechaSalida")
    , @NamedQuery(name = "RnGcCpCartaPorteTbl.findByFechaLlegada", query = "SELECT r FROM RnGcCpCartaPorteTbl r WHERE r.fechaLlegada = :fechaLlegada")
    , @NamedQuery(name = "RnGcCpCartaPorteTbl.findByPesoBrutoTotal", query = "SELECT r FROM RnGcCpCartaPorteTbl r WHERE r.pesoBrutoTotal = :pesoBrutoTotal")
    , @NamedQuery(name = "RnGcCpCartaPorteTbl.findByUnidadPeso", query = "SELECT r FROM RnGcCpCartaPorteTbl r WHERE r.unidadPeso = :unidadPeso")
    , @NamedQuery(name = "RnGcCpCartaPorteTbl.findByTotalMercancia", query = "SELECT r FROM RnGcCpCartaPorteTbl r WHERE r.totalMercancia = :totalMercancia")
    , @NamedQuery(name = "RnGcCpCartaPorteTbl.findByTipoCartaPorte", query = "SELECT r FROM RnGcCpCartaPorteTbl r WHERE r.tipoCartaPorte = :tipoCartaPorte")
    , @NamedQuery(name = "RnGcCpCartaPorteTbl.findByCreadoPor", query = "SELECT r FROM RnGcCpCartaPorteTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCpCartaPorteTbl.findByFechaCreacion", query = "SELECT r FROM RnGcCpCartaPorteTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcCpCartaPorteTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcCpCartaPorteTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcCpCartaPorteTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcCpCartaPorteTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcCpCartaPorteTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 10)
    @Column(name = "clave")
    private String clave;
    @Size(max = 10)
    @Column(name = "origen")
    private String origen;
    @Size(max = 10)
    @Column(name = "destino")
    private String destino;
    @Size(max = 150)
    @Column(name = "transporteInter")
    private String transporteInter;
    @Size(max = 50)
    @Column(name = "totalDistancia")
    private String totalDistancia;
    @Column(name = "fechaSalida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSalida;
    @Column(name = "fechaLlegada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLlegada;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pesoBrutoTotal")
    private Double pesoBrutoTotal;
    @Size(max = 50)
    @Column(name = "unidadPeso")
    private String unidadPeso;
    @Column(name = "totalMercancia")
    private Double totalMercancia;
    @Size(max = 45)
    @Column(name = "tipoCartaPorte")
    private String tipoCartaPorte;
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
    @JoinColumn(name = "unidadId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcCpUnidadesTbl unidadId;
    @JoinColumn(name = "conductorId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcCpConductoresTbl conductorId;
    @JoinColumn(name = "dirConductorId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcDireccionesTbl dirConductorId;
    @JoinColumn(name = "clienteProveedorId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcPersonasTbl clienteProveedorId;
    @JoinColumn(name = "dirUsuarioId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcDireccionesUsuariosTbl dirUsuarioId;
    @JoinColumn(name = "cfdisId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcCfdisTbl cfdisId;
    @Size(max = 100)
    @Column(name = "nombreRemitente")
    private String nombreRemitente;
    @Size(max = 100)
    @Column(name = "nombreDestinatario")
    private String nombreDestinatario;
    @Size(max = 15)
    @Column(name = "rfcRemitente")
    private String rfcRemitente;
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

    public String getNombreRemitente() {
        return nombreRemitente;
    }

    public void setNombreRemitente(String nombreRemitente) {
        this.nombreRemitente = nombreRemitente;
    }

    public String getNombreDestinatario() {
        return nombreDestinatario;
    }

    public void setNombreDestinatario(String nombreDestinatario) {
        this.nombreDestinatario = nombreDestinatario;
    }

    public String getRfcRemitente() {
        return rfcRemitente;
    }

    public void setRfcRemitente(String rfcRemitente) {
        this.rfcRemitente = rfcRemitente;
    }

    public String getRfcDestinatario() {
        return rfcDestinatario;
    }

    public void setRfcDestinatario(String rfcDestinatario) {
        this.rfcDestinatario = rfcDestinatario;
    }

    public RnGcDireccionesUsuariosTbl getDirUsuarioId() {
        return dirUsuarioId;
    }

    public void setDirUsuarioId(RnGcDireccionesUsuariosTbl dirUsuarioId) {
        this.dirUsuarioId = dirUsuarioId;
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

    public RnGcCpCartaPorteTbl() {
    }

    public RnGcCpCartaPorteTbl(Integer id) {
        this.id = id;
    }

    public RnGcCpCartaPorteTbl(Integer id, Date fechaCreacion) {
        this.id = id;
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

    public String getTransporteInter() {
        return transporteInter;
    }

    public void setTransporteInter(String transporteInter) {
        this.transporteInter = transporteInter;
    }

    public String getTotalDistancia() {
        return totalDistancia;
    }

    public void setTotalDistancia(String totalDistancia) {
        this.totalDistancia = totalDistancia;
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

    public Double getPesoBrutoTotal() {
        return pesoBrutoTotal;
    }

    public void setPesoBrutoTotal(Double pesoBrutoTotal) {
        this.pesoBrutoTotal = pesoBrutoTotal;
    }

    public String getUnidadPeso() {
        return unidadPeso;
    }

    public void setUnidadPeso(String unidadPeso) {
        this.unidadPeso = unidadPeso;
    }

    public Double getTotalMercancia() {
        return totalMercancia;
    }

    public void setTotalMercancia(Double totalMercancia) {
        this.totalMercancia = totalMercancia;
    }

    public String getTipoCartaPorte() {
        return tipoCartaPorte;
    }

    public void setTipoCartaPorte(String tipoCartaPorte) {
        this.tipoCartaPorte = tipoCartaPorte;
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

    public RnGcCpUnidadesTbl getUnidadId() {
        return unidadId;
    }

    public void setUnidadId(RnGcCpUnidadesTbl unidadId) {
        this.unidadId = unidadId;
    }

    public RnGcCpConductoresTbl getConductorId() {
        return conductorId;
    }

    public void setConductorId(RnGcCpConductoresTbl conductorId) {
        this.conductorId = conductorId;
    }

    public RnGcDireccionesTbl getDirConductorId() {
        return dirConductorId;
    }

    public void setDirConductorId(RnGcDireccionesTbl dirConductorId) {
        this.dirConductorId = dirConductorId;
    }

    public RnGcPersonasTbl getClienteProveedorId() {
        return clienteProveedorId;
    }

    public void setClienteProveedorId(RnGcPersonasTbl clienteProveedorId) {
        this.clienteProveedorId = clienteProveedorId;
    }

    public RnGcDireccionesUsuariosTbl getDirClienteProveedorId() {
        return dirUsuarioId;
    }

    public void setDirClienteProveedorId(RnGcDireccionesUsuariosTbl dirUsuarioId) {
        this.dirUsuarioId = dirUsuarioId;
    }

    public RnGcCfdisTbl getCfdisId() {
        return cfdisId;
    }

    public void setCfdisId(RnGcCfdisTbl cfdisId) {
        this.cfdisId = cfdisId;
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
        if (!(object instanceof RnGcCpCartaPorteTbl)) {
            return false;
        }
        RnGcCpCartaPorteTbl other = (RnGcCpCartaPorteTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcCpCartaPorteTbl[ id=" + id + " ]";
    }
    
}
