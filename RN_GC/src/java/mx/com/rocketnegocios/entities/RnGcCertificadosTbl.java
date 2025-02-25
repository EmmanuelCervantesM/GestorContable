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
import javax.persistence.Lob;
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
@Table(name = "rn_gc_certificados_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcCertificadosTbl.findAll", query = "SELECT r FROM RnGcCertificadosTbl r")
    , @NamedQuery(name = "RnGcCertificadosTbl.findById", query = "SELECT r FROM RnGcCertificadosTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcCertificadosTbl.findByContraseniaLlavePrivada", query = "SELECT r FROM RnGcCertificadosTbl r WHERE r.contraseniaLlavePrivada = :contraseniaLlavePrivada")
    , @NamedQuery(name = "RnGcCertificadosTbl.findByNumeroCertificado", query = "SELECT r FROM RnGcCertificadosTbl r WHERE r.numeroCertificado = :numeroCertificado")
    , @NamedQuery(name = "RnGcCertificadosTbl.findByCreadoPor", query = "SELECT r FROM RnGcCertificadosTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCertificadosTbl.findByFechaCreacion", query = "SELECT r FROM RnGcCertificadosTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcCertificadosTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcCertificadosTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcCertificadosTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcCertificadosTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcCertificadosTbl.findByUsuarioId", query = "SELECT r FROM RnGcCertificadosTbl r WHERE r.usuariosId = :usuariosId")
    , @NamedQuery(name = "RnGcCertificadosTbl.findByActivoUsuarioId", query = "SELECT r FROM RnGcCertificadosTbl r WHERE r.usuariosId = :usuariosId AND r.estado = 'Activo' AND r.fechaVencimiento >= CURRENT_TIMESTAMP order by r.id desc")
    , @NamedQuery(name = "RnGcCertificadosTbl.findByEstado", query = "SELECT r FROM RnGcCertificadosTbl r WHERE r.estado = :estado") })
public class RnGcCertificadosTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Lob
    private byte[] llavePrivada;
    @Basic(optional = false)
    @NotNull
    @Lob
    private byte[] certificadoSelloDigital;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    private String contraseniaLlavePrivada;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    private String numeroCertificado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String estado;
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
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimiento;
    //@OneToMany(mappedBy = "certificadosId")
    //private Collection<RnGcCfdisTbl> rnGcCfdisTblCollection;    
    @JoinColumn(name = "usuarios_Id", referencedColumnName = "Id")
    @ManyToOne
    private RnGcUsuariosTbl usuariosId;
    @Column(name = "nombreCertificado")
    private String nombreCertificado;

    public RnGcCertificadosTbl() {
    }

    public RnGcCertificadosTbl(Integer id) {
        this.id = id;
    }

    public RnGcCertificadosTbl(Integer id, byte[] llavePrivada, byte[] certificadoSelloDigital, String contraseniaLlavePrivada, String numeroCertificado, String estado, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion, Date fechaVencimiento) {
        this.id = id;
        this.llavePrivada = llavePrivada;
        this.certificadoSelloDigital = certificadoSelloDigital;
        this.contraseniaLlavePrivada = contraseniaLlavePrivada;
        this.numeroCertificado = numeroCertificado;
        this.estado = estado;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
        this.fechaVencimiento = fechaVencimiento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getLlavePrivada() {
        return llavePrivada;
    }

    public void setLlavePrivada(byte[] llavePrivada) {
        this.llavePrivada = llavePrivada;
    }

    public byte[] getCertificadoSelloDigital() {
        return certificadoSelloDigital;
    }

    public void setCertificadoSelloDigital(byte[] certificadoSelloDigital) {
        this.certificadoSelloDigital = certificadoSelloDigital;
    }

    public String getContraseniaLlavePrivada() {
        return contraseniaLlavePrivada;
    }

    public void setContraseniaLlavePrivada(String contraseniaLlavePrivada) {
        this.contraseniaLlavePrivada = contraseniaLlavePrivada;
    }

    public String getNumeroCertificado() {
        return numeroCertificado;
    }

    public void setNumeroCertificado(String numeroCertificado) {
        this.numeroCertificado = numeroCertificado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public RnGcUsuariosTbl getUsuariosId() {
        return usuariosId;
    }

    public void setUsuariosId(RnGcUsuariosTbl usuariosId) {
        this.usuariosId = usuariosId;
    }

    public String getNombreCertificado() {
        return nombreCertificado;
    }

    public void setNombreCertificado(String nombreCertificado) {
        this.nombreCertificado = nombreCertificado;
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
        if (!(object instanceof RnGcCertificadosTbl)) {
            return false;
        }
        RnGcCertificadosTbl other = (RnGcCertificadosTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcCertificadosTbl[ id=" + id + " ]";
    }
}