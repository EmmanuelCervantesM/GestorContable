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
 * @author Developer1
 */
@Entity
@Table(name = "rn_gc_personas_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcPersonasTbl.findAll", query = "SELECT r FROM RnGcPersonasTbl r")
    , @NamedQuery(name = "RnGcPersonasTbl.findById", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcPersonasTbl.findByNombre", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.nombre = :nombre")
    , @NamedQuery(name = "RnGcPersonasTbl.findByNombreFiscal", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.nombreFiscal = :nombreFiscal")
    , @NamedQuery(name = "RnGcPersonasTbl.findByRfc", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.rfc = :rfc")
    , @NamedQuery(name = "RnGcPersonasTbl.findByTipo", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.tipo = :tipo")
    , @NamedQuery(name = "RnGcPersonasTbl.findByPais", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.pais = :pais")
    , @NamedQuery(name = "RnGcPersonasTbl.findByEstado", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.estado = :estado")
    , @NamedQuery(name = "RnGcPersonasTbl.findByCiudad", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.ciudad = :ciudad")
    , @NamedQuery(name = "RnGcPersonasTbl.findByLocalidad", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.localidad = :localidad")
    , @NamedQuery(name = "RnGcPersonasTbl.findByCodigoPostal", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.codigoPostal = :codigoPostal")
    , @NamedQuery(name = "RnGcPersonasTbl.findByDomicilio", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.domicilio = :domicilio")
    , @NamedQuery(name = "RnGcPersonasTbl.findByNoExt", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.noExt = :noExt")
    , @NamedQuery(name = "RnGcPersonasTbl.findByNoInt", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.noInt = :noInt")
    , @NamedQuery(name = "RnGcPersonasTbl.findByTelefono", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.telefono = :telefono")
    , @NamedQuery(name = "RnGcPersonasTbl.findByEmail", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.email = :email")
    , @NamedQuery(name = "RnGcPersonasTbl.findByEmail2", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.email2 = :email2")
    , @NamedQuery(name = "RnGcPersonasTbl.findByEmail3", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.email3 = :email3")
    , @NamedQuery(name = "RnGcPersonasTbl.findByTipoPersona", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.tipoPersona = :tipoPersona")
    , @NamedQuery(name = "RnGcPersonasTbl.findByEdad", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.edad = :edad")
    , @NamedQuery(name = "RnGcPersonasTbl.findByCreadoPor", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcPersonasTbl.findByCreadoPorNombre", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.creadoPor = :creadoPor and r.rfc = :rfc")
    , @NamedQuery(name = "RnGcPersonasTbl.findByFechaCreacion", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcPersonasTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcPersonasTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcPersonasTbl.findByUsuarioId", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.usuarioId = :usuarioId")
    , @NamedQuery(name = "RnGcPersonasTbl.findByNombreAndRfc", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.nombre = :nombre AND r.rfc = :rfc")
    , @NamedQuery(name = "RnGcPersonasTbl.findByRfcreadoPor", query = "SELECT r FROM RnGcPersonasTbl r WHERE r.rfc = :rfc AND r.creadoPor = :creadoPor")})
public class RnGcPersonasTbl implements Serializable {

    @JoinColumn(name = "usocfdiId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcCatalogosusosTbl usocfdiId;
    @Column(name = "tipoPersonaSAT")
    private String tipoPersonaSat;
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    private String nombre;
    private String nombreFiscal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String rfc;
    @Size(max = 45)
    private String tipo;
    @Size(max = 45)
    private String pais;
    @Size(max = 45)
    private String estado;
    @Size(max = 45)
    private String ciudad;
    @Size(max = 80)
    private String localidad;
    private String codigoPostal;
    @Size(max = 75)
    private String domicilio;
    @Size(max = 45)
    private String noExt;
    @Size(max = 45)
    private String noInt;
    @Size(max = 45)
    private String telefono;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    private String email;
    @Size(max = 45)
    private String email2;
    @Size(max = 45)
    private String email3;
    @Size(min = 1, max = 45)
    private String tipoPersona;
    private Integer edad;
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
    @JoinColumn(name = "usuarioId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcUsuariosTbl usuarioId;
    @JoinColumn(name = "tipoPersona_Id", referencedColumnName = "Id")
    @ManyToOne
    private RnGcTipospersonasTbl tipoPersonaId;
    @OneToMany(mappedBy = "personaId")
    private Collection<RnGcUsuariosTbl> rnGcUsuariosTblCollection;
    @JoinColumn(name = "regimenFiscalId", referencedColumnName = "Id")
    @ManyToOne
    private RnGcRegimenfiscalTbl regimenFiscalId;

    public RnGcPersonasTbl() {
    }
    
    public RnGcPersonasTbl(Integer id) {
        this.id = id;
    }

    public RnGcPersonasTbl(Integer id, String nombre, String rfc, String tipoPersona, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.nombre = nombre;
        this.rfc = rfc;
        this.tipoPersona = tipoPersona;
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

    public String getNombre() {
        if(nombre == null) {
            this.nombre = "-";
        }
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreFiscal() {
        return nombreFiscal;
    }

    public void setNombreFiscal(String nombreFiscal) {
        this.nombreFiscal = nombreFiscal;
    }

    public String getRfc() {
        if(rfc == null) {
            this.rfc = "-";
        }
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
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

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getEmail3() {
        return email3;
    }

    public void setEmail3(String email3) {
        this.email3 = email3;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
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
    
    public String getcodigoPostal() {
        return codigoPostal;
    }
    
    public void setcodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public RnGcUsuariosTbl getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(RnGcUsuariosTbl usuarioId) {
        this.usuarioId = usuarioId;
    }

    public RnGcTipospersonasTbl getTipoPersonaId() {
        return tipoPersonaId;
    }

    public void setTipoPersonaId(RnGcTipospersonasTbl tipoPersonaId) {
        this.tipoPersonaId = tipoPersonaId;
    }

    public String getTipoPersonaSat() {
        return tipoPersonaSat;
    }

    public void setTipoPersonaSat(String tipoPersonaSat) {
        this.tipoPersonaSat = tipoPersonaSat;
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
        if (!(object instanceof RnGcPersonasTbl)) {
            return false;
        }
        RnGcPersonasTbl other = (RnGcPersonasTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcPersonasTbl[ id=" + id + " ]";
    }

    public Collection<RnGcUsuariosTbl> getRnGcUsuariosTblCollection() {
        return rnGcUsuariosTblCollection;
    }

    public void setRnGcUsuariosTblCollection(Collection<RnGcUsuariosTbl> rnGcUsuariosTblCollection) {
        this.rnGcUsuariosTblCollection = rnGcUsuariosTblCollection;
    }

    public RnGcRegimenfiscalTbl getRegimenFiscalId() {
        return regimenFiscalId;
    }

    public void setRegimenFiscalId(RnGcRegimenfiscalTbl regimenFiscalId) {
        this.regimenFiscalId = regimenFiscalId;
    }

    public RnGcCatalogosusosTbl getUsocfdiId() {
        return usocfdiId;
    }

    public void setUsocfdiId(RnGcCatalogosusosTbl usocfdiId) {
        this.usocfdiId = usocfdiId;
    }
    
}
