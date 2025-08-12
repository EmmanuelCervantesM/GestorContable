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
import javax.persistence.FetchType;
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
 * @author LenovoZ40
 */
@Entity
@Table(name = "rn_gc_trabajadores_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcTrabajadoresTbl.findAll", query = "SELECT r FROM RnGcTrabajadoresTbl r")
    , @NamedQuery(name = "RnGcTrabajadoresTbl.findById", query = "SELECT r FROM RnGcTrabajadoresTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcTrabajadoresTbl.findByCreadoPor", query = "SELECT r FROM RnGcTrabajadoresTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcTrabajadoresTbl.findByCreadoPorYNombre", query = "SELECT r FROM RnGcTrabajadoresTbl r WHERE r.creadoPor = :creadoPor and r.nombre = :nombre")
    , @NamedQuery(name = "RnGcTrabajadoresTbl.findByFechaCreacion", query = "SELECT r FROM RnGcTrabajadoresTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcTrabajadoresTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcTrabajadoresTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcTrabajadoresTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcTrabajadoresTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcTrabajadoresTbl.findByApPaterno", query = "SELECT r FROM RnGcTrabajadoresTbl r WHERE r.apPaterno = :apPaterno")
    , @NamedQuery(name = "RnGcTrabajadoresTbl.findByApMaterno", query = "SELECT r FROM RnGcTrabajadoresTbl r WHERE r.apMaterno = :apMaterno")
    , @NamedQuery(name = "RnGcTrabajadoresTbl.findByNombre", query = "SELECT r FROM RnGcTrabajadoresTbl r WHERE r.nombre = :nombre")
    , @NamedQuery(name = "RnGcTrabajadoresTbl.findByNombreCompleto", query = "SELECT r FROM RnGcTrabajadoresTbl r WHERE r.nombreCompleto = :nombreCompleto")
    , @NamedQuery(name = "RnGcTrabajadoresTbl.findByCurp", query = "SELECT r FROM RnGcTrabajadoresTbl r WHERE r.curp = :curp")
    , @NamedQuery(name = "RnGcTrabajadoresTbl.findByRfc", query = "SELECT r FROM RnGcTrabajadoresTbl r WHERE r.rfc = :rfc")
    , @NamedQuery(name = "RnGcTrabajadoresTbl.findByNss", query = "SELECT r FROM RnGcTrabajadoresTbl r WHERE r.nss = :nss")
    , @NamedQuery(name = "RnGcTrabajadoresTbl.findBySdi", query = "SELECT r FROM RnGcTrabajadoresTbl r WHERE r.sdi = :sdi")
    , @NamedQuery(name = "RnGcTrabajadoresTbl.findByTipoPersona", query = "SELECT r FROM RnGcTrabajadoresTbl r WHERE r.tipoPersona = :tipoPersona")
    , @NamedQuery(name = "RnGcTrabajadoresTbl.findByNominaId", query = "SELECT r FROM RnGcTrabajadoresTbl r WHERE r.nominaId = :nominaId")
    , @NamedQuery(name = "RnGcTrabajadoresTbl.countTrabajador", query = "SELECT COUNT(r) FROM RnGcTrabajadoresTbl r WHERE r.curp = :curp or r.rfc = :rfc or r.nss = :nss")
    , @NamedQuery(name = "RnGcTrabajadoresTbl.findTrabajador", query = "SELECT r FROM RnGcTrabajadoresTbl r WHERE r.curp = :curp OR r.rfc = :rfc OR r.nss = :nss")
    , @NamedQuery(name = "RnGcTrabajadoresTbl.findByNoTrabajador", query = "SELECT r FROM RnGcTrabajadoresTbl r WHERE r.noTrabajador = :noTrabajador AND r.creadoPor = :creadoPor"),})
public class RnGcTrabajadoresTbl implements Serializable {

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
    @Column(name = "apPaterno")
    private String apPaterno;
    @Size(max = 45)
    @Column(name = "apMaterno")
    private String apMaterno;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 135)
    @Column(name = "nombreCompleto")
    private String nombreCompleto;
    @Size(max = 45)
    @Column(name = "CURP")
    private String curp;
    @Size(max = 15)
    @Column(name = "RFC")
    private String rfc;
    @Size(max = 15)
    @Column(name = "NSS")
    private String nss;
    @Size(max = 45)
    @Column(name = "SDI")
    private String sdi;
    @Size(max = 45)
    @Column(name = "tipoPersona")
    private String tipoPersona;
    @Column(name = "nominaId")
    private Integer nominaId;
    @Column(name = "noTrabajador")
    private String noTrabajador;
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "banco")
    private String banco;
    @Column(name = "cuentaBancaria")
    private String cuentaBancaria;
    @Column(name = "salarioBase")
    private String salarioBase;
    @Column(name = "tipoContratoId")
    private Integer tipoContratoId;
    @Column(name = "estadoId")
    private Integer estadoId;
    @Column(name = "email1")
    private String email1;
    @Size(max = 5)
    @Column(name = "codigoPostal")
    private String codigoPostal;
    @Size(max = 250)
    @Column(name = "observaciones")
    private String observaciones;
    
    @Size(max = 10)
    @Column(name = "tipoEmpleado")
    private String tipoEmpleado;
    
    @Size(max = 1)
    @Column(name = "sindicalizado")
    private String sindicalizado;
    
    @ManyToOne
    @JoinColumn(name = "tipoJornadaTblId", referencedColumnName = "id")
    private RnGcNomTipojornadaTbl  tipoJornadaTblId;
    
    @ManyToOne
    @JoinColumn(name = "riesgoPuestoTblId", referencedColumnName = "id")
    private RnGcNomRiesgopuestoTbl riesgoPuestoTblId;
    
    @ManyToOne
    @JoinColumn(name = "regimenContratacionId", referencedColumnName = "id")
    private RnGcNomTiporegimencontratacionTbl regimenContratacionId;

    @ManyToOne
    @JoinColumn(name = "entidadFederativaId", referencedColumnName = "id")
    private RnGcNomEstadosTbl entidadFederativaId;
    

    
    public RnGcTrabajadoresTbl() {
    }

    public RnGcTrabajadoresTbl(Integer id) {
        this.id = id;
    }

    public RnGcTrabajadoresTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
    }

    public RnGcNomTiporegimencontratacionTbl getRegimenContratacionId() {
        return regimenContratacionId;
    }

    public void setEntidadFederativaId(RnGcNomEstadosTbl entidadFederativaId) {
        this.entidadFederativaId = entidadFederativaId;
    }
    
    public RnGcNomEstadosTbl getEntidadFederativaId() {
        return entidadFederativaId;
    }
    

    
    public void setRegimenContratacionId(RnGcNomTiporegimencontratacionTbl regimenContratacionId) {
        this.regimenContratacionId = regimenContratacionId;
    }
    
    public RnGcNomRiesgopuestoTbl getRiesgoPuestoTblId() {
        return riesgoPuestoTblId;
    }

    public void setRiesgoPuestoTblId(RnGcNomRiesgopuestoTbl riesgoPuestoTblId) {
        this.riesgoPuestoTblId = riesgoPuestoTblId;
    }
    
    public RnGcNomTipojornadaTbl getTipoJornadaTblId() {
        return tipoJornadaTblId;
    }

    public void setTipoJornadaTblId(RnGcNomTipojornadaTbl tipoJornadaTblId) {
        this.tipoJornadaTblId = tipoJornadaTblId;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
     public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
    
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public String getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(String tipoEmpleado) {
        if (tipoEmpleado == null) {
        this.tipoEmpleado = "Salariado";
    } else {
        this.tipoEmpleado = tipoEmpleado;
    }
    }
    
        public String getSindicalizado() {
        return sindicalizado;
    }

    public void setSindicalizado(String sindicalizado) {
        this.sindicalizado = sindicalizado;
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

    public String getApPaterno() {
        if(apPaterno == null)
            this.apPaterno = "";
        return apPaterno;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApMaterno() {
        if(apMaterno == null)
            this.apMaterno = "";
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public String getNombre() {
        if(nombre == null)
            this.nombre = "";
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCurp() {
        if(curp == null)
            this.curp = "";
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getRfc() {
        if(rfc == null)
            this.rfc = "";
        return rfc;
    }
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNss() {
        if(nss == null)
            this.nss = "";
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getSdi() {
        if(sdi == null)
            this.sdi = "";
        return sdi;
    }

    public void setSdi(String sdi) {
        this.sdi = sdi;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public Integer getNominaId() {
        return nominaId;
    }

    public void setNominaId(Integer nominaId) {
        this.nominaId = nominaId;
    }

    public String getNoTrabajador() {
        return noTrabajador;
    }

    public void setNoTrabajador(String noTrabajador) {
        this.noTrabajador = noTrabajador;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(String cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    public String getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(String salarioBase) {
        this.salarioBase = salarioBase;
    }

    public Integer getTipoContratoId() {
        return tipoContratoId;
    }

    public void setTipoContratoId(Integer tipoContratoId) {
        this.tipoContratoId = tipoContratoId;
    }

    public Integer getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Integer estadoId) {
        this.estadoId = estadoId;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
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
        if (!(object instanceof RnGcTrabajadoresTbl)) {
            return false;
        }
        RnGcTrabajadoresTbl other = (RnGcTrabajadoresTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcTrabajadoresTbl[ id=" + id + " ]";
    }
    
}
