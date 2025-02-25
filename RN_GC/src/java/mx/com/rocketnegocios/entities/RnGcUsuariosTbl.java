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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
 * @author Developer1
 */
@Entity
@Table(name = "rn_gc_usuarios_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcUsuariosTbl.findAll", query = "SELECT r FROM RnGcUsuariosTbl r")
    , @NamedQuery(name = "RnGcUsuariosTbl.findById", query = "SELECT r FROM RnGcUsuariosTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcUsuariosTbl.findByUsuarioClave", query = "SELECT r FROM RnGcUsuariosTbl r WHERE r.usuarioClave = :usuarioClave")
    , @NamedQuery(name = "RnGcUsuariosTbl.findByRfc", query = "SELECT r FROM RnGcUsuariosTbl r WHERE r.rfc = :rfc")
    , @NamedQuery(name = "RnGcUsuariosTbl.findByNombreCompleto", query = "SELECT r FROM RnGcUsuariosTbl r WHERE r.nombreCompleto = :nombreCompleto")
    , @NamedQuery(name = "RnGcUsuariosTbl.findByTelefono", query = "SELECT r FROM RnGcUsuariosTbl r WHERE r.telefono = :telefono")
    , @NamedQuery(name = "RnGcUsuariosTbl.findByEmail", query = "SELECT r FROM RnGcUsuariosTbl r WHERE r.email = :email")
    , @NamedQuery(name = "RnGcUsuariosTbl.findByCodigoPostal", query = "SELECT r FROM RnGcUsuariosTbl r WHERE r.codigoPostal = :codigoPostal")
    , @NamedQuery(name = "RnGcUsuariosTbl.findByFechaAlta", query = "SELECT r FROM RnGcUsuariosTbl r WHERE r.fechaAlta = :fechaAlta")
    , @NamedQuery(name = "RnGcUsuariosTbl.findByFechaBaja", query = "SELECT r FROM RnGcUsuariosTbl r WHERE r.fechaBaja = :fechaBaja")
    , @NamedQuery(name = "RnGcUsuariosTbl.findByEstado", query = "SELECT r FROM RnGcUsuariosTbl r WHERE r.estado = :estado")
    , @NamedQuery(name = "RnGcUsuariosTbl.findByContrasenia", query = "SELECT r FROM RnGcUsuariosTbl r WHERE r.contrasenia = :contrasenia")
    , @NamedQuery(name = "RnGcUsuariosTbl.findByFechaContrasenia", query = "SELECT r FROM RnGcUsuariosTbl r WHERE r.fechaContrasenia = :fechaContrasenia")
    , @NamedQuery(name = "RnGcUsuariosTbl.findByNoIntentos", query = "SELECT r FROM RnGcUsuariosTbl r WHERE r.noIntentos = :noIntentos")
    , @NamedQuery(name = "RnGcUsuariosTbl.findByClienteId", query = "SELECT r FROM RnGcUsuariosTbl r WHERE r.clienteId = :clienteId")
    , @NamedQuery(name = "RnGcUsuariosTbl.findByProveedorId", query = "SELECT r FROM RnGcUsuariosTbl r WHERE r.proveedorId = :proveedorId")
    , @NamedQuery(name = "RnGcUsuariosTbl.findByContraseniaLlave", query = "SELECT r FROM RnGcUsuariosTbl r WHERE r.contraseniaLlave = :contraseniaLlave")
    , @NamedQuery(name = "RnGcUsuariosTbl.findByCreadoPor", query = "SELECT r FROM RnGcUsuariosTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcUsuariosTbl.findByFechaCreacion", query = "SELECT r FROM RnGcUsuariosTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcUsuariosTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcUsuariosTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcUsuariosTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcUsuariosTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcUsuariosTbl.findByNoUsuarios", query = "SELECT r FROM RnGcUsuariosTbl r WHERE r.noUsuarios = :noUsuarios AND r.usuarioId = :usuarioId AND r.estado = 'Activo'")
    , @NamedQuery(name = "RnGcUsuariosTbl.findByUsuarioId", query = "SELECT r FROM RnGcUsuariosTbl r WHERE r.usuarioId = :usuarioId")})
public class RnGcUsuariosTbl implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    private String passwordEmail;
    
    @JoinColumn(name = "regimenId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcRegimenfiscalTbl regimenId;
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    private String usuarioClave;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    private String rfc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    private String nombreCompleto;
    @Size(max = 30)
    private String telefono;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    private String email;
    @Basic(optional = false)
    @NotNull
    private int codigoPostal;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;
    @Temporal(TemporalType.DATE)
    private Date fechaBaja;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    private String tipoUsuario;
    @Basic(optional = false)
    @NotNull
    private int noUsuarios;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    private String contrasenia;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fechaContrasenia;
    @Basic(optional = false)
    @NotNull
    private int noIntentos;
    @Size(max = 20)
    private String clienteId;
    @Size(max = 20)
    private String proveedorId;
    @Lob
    private byte[] certificadoSello;
    @Lob
    private byte[] llavePrivada;
    @Size(max = 45)
    private String contraseniaLlave;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuariosId")
    private Collection<RnGcCertificadosTbl> rnGcCertificadosTblCollection;
    @OneToMany(mappedBy = "usuarioId")
    private Collection<RnGcPersonasTbl> rnGcPersonasTblCollection;
    @OneToMany(mappedBy = "usuarioId")
    private Collection<RnGcUsuariosTbl> rnGcUsuariosTblCollection;
    @JoinColumn(name = "Usuario_Id", referencedColumnName = "Id")
    @ManyToOne
    private RnGcUsuariosTbl usuarioId;
    @JoinColumn(name = "personaId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcPersonasTbl personaId;

    public RnGcUsuariosTbl() {
    }

    public RnGcUsuariosTbl(Integer id) {
        this.id = id;
    }

    public RnGcUsuariosTbl(Integer id, String usuarioClave, String tipoUsuario, int noUsuarios, String rfc, String nombreCompleto, int codigoPostal, Date fechaAlta, String estado, String contrasenia, Date fechaContrasenia, int noIntentos, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.usuarioClave = usuarioClave;
        this.tipoUsuario = tipoUsuario;
        this.noUsuarios = noUsuarios;
        this.rfc = rfc;
        this.nombreCompleto = nombreCompleto;
        this.codigoPostal = codigoPostal;
        this.fechaAlta = fechaAlta;
        this.estado = estado;
        this.contrasenia = contrasenia;
        this.fechaContrasenia = fechaContrasenia;
        this.noIntentos = noIntentos;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
    }

    public int getNoUsuarios() {
        return noUsuarios;
    }

    public void setNoUsuarios(int noUsuarios) {
        this.noUsuarios = noUsuarios;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuarioClave() {
        return usuarioClave;
    }

    public void setUsuarioClave(String usuarioClave) {
        this.usuarioClave = usuarioClave;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Date getFechaContrasenia() {
        return fechaContrasenia;
    }

    public void setFechaContrasenia(Date fechaContrasenia) {
        this.fechaContrasenia = fechaContrasenia;
    }

    public int getNoIntentos() {
        return noIntentos;
    }

    public void setNoIntentos(int noIntentos) {
        this.noIntentos = noIntentos;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(String proveedorId) {
        this.proveedorId = proveedorId;
    }

    public byte[] getCertificadoSello() {
        return certificadoSello;
    }

    public void setCertificadoSello(byte[] certificadoSello) {
        this.certificadoSello = certificadoSello;
    }

    public byte[] getLlavePrivada() {
        return llavePrivada;
    }

    public void setLlavePrivada(byte[] llavePrivada) {
        this.llavePrivada = llavePrivada;
    }

    public String getContraseniaLlave() {
        return contraseniaLlave;
    }

    public void setContraseniaLlave(String contraseniaLlave) {
        this.contraseniaLlave = contraseniaLlave;
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

    public RnGcUsuariosTbl getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(RnGcUsuariosTbl usuarioId) {
        this.usuarioId = usuarioId;
    }

    public RnGcPersonasTbl getPersonaId() {
        return personaId;
    }

    public void setPersonaId(RnGcPersonasTbl personaId) {
        this.personaId = personaId;
    }

    public RnGcRegimenfiscalTbl getRegimenId() {
        return regimenId;
    }

    public void setRegimenId(RnGcRegimenfiscalTbl regimenId) {
        this.regimenId = regimenId;
    }

    public String getPasswordEmail() {
        return passwordEmail;
    }

    public void setPasswordEmail(String passwordEmail) {
        this.passwordEmail = passwordEmail;
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
        if (!(object instanceof RnGcUsuariosTbl)) {
            return false;
        }
        RnGcUsuariosTbl other = (RnGcUsuariosTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcUsuariosTbl[ id=" + id + " ]";
    }

}
