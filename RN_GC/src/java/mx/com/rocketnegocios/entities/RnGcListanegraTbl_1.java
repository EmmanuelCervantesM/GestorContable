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
 * @author maxoc
 */
@Entity
@Table(name = "rn_gc_listanegra_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcListanegraTbl_1.findAll", query = "SELECT r FROM RnGcListanegraTbl_1 r")
    , @NamedQuery(name = "RnGcListanegraTbl_1.findById", query = "SELECT r FROM RnGcListanegraTbl_1 r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcListanegraTbl_1.findByRfc", query = "SELECT r FROM RnGcListanegraTbl_1 r WHERE r.rfc = :rfc")
    , @NamedQuery(name = "RnGcListanegraTbl_1.findBySituacionContribuyente", query = "SELECT r FROM RnGcListanegraTbl_1 r WHERE r.situacionContribuyente = :situacionContribuyente")
    , @NamedQuery(name = "RnGcListanegraTbl_1.findByNumeroFechaOficioGlobalPresuncionSAT", query = "SELECT r FROM RnGcListanegraTbl_1 r WHERE r.numeroFechaOficioGlobalPresuncionSAT = :numeroFechaOficioGlobalPresuncionSAT")
    , @NamedQuery(name = "RnGcListanegraTbl_1.findByPublicacionSATpresuntos", query = "SELECT r FROM RnGcListanegraTbl_1 r WHERE r.publicacionSATpresuntos = :publicacionSATpresuntos")
    , @NamedQuery(name = "RnGcListanegraTbl_1.findByNumeroFechaOficioGlobalPresuncionDOF", query = "SELECT r FROM RnGcListanegraTbl_1 r WHERE r.numeroFechaOficioGlobalPresuncionDOF = :numeroFechaOficioGlobalPresuncionDOF")
    , @NamedQuery(name = "RnGcListanegraTbl_1.findByPublicacionDOFpresuntos", query = "SELECT r FROM RnGcListanegraTbl_1 r WHERE r.publicacionDOFpresuntos = :publicacionDOFpresuntos")
    , @NamedQuery(name = "RnGcListanegraTbl_1.findByNumeroFechaOficioGlobalDesvirtuadoSAT", query = "SELECT r FROM RnGcListanegraTbl_1 r WHERE r.numeroFechaOficioGlobalDesvirtuadoSAT = :numeroFechaOficioGlobalDesvirtuadoSAT")
    , @NamedQuery(name = "RnGcListanegraTbl_1.findByPublicacionSATdesvirtuados", query = "SELECT r FROM RnGcListanegraTbl_1 r WHERE r.publicacionSATdesvirtuados = :publicacionSATdesvirtuados")
    , @NamedQuery(name = "RnGcListanegraTbl_1.findByNumeroFechaOficioGlobalDesvirtuadoDOF", query = "SELECT r FROM RnGcListanegraTbl_1 r WHERE r.numeroFechaOficioGlobalDesvirtuadoDOF = :numeroFechaOficioGlobalDesvirtuadoDOF")
    , @NamedQuery(name = "RnGcListanegraTbl_1.findByPublicacionDOFdesvirtuados", query = "SELECT r FROM RnGcListanegraTbl_1 r WHERE r.publicacionDOFdesvirtuados = :publicacionDOFdesvirtuados")
    , @NamedQuery(name = "RnGcListanegraTbl_1.findByNumeroFechaOficioGlobalDefinitivoSAT", query = "SELECT r FROM RnGcListanegraTbl_1 r WHERE r.numeroFechaOficioGlobalDefinitivoSAT = :numeroFechaOficioGlobalDefinitivoSAT")
    , @NamedQuery(name = "RnGcListanegraTbl_1.findByPublicacionSATdefinitivos", query = "SELECT r FROM RnGcListanegraTbl_1 r WHERE r.publicacionSATdefinitivos = :publicacionSATdefinitivos")
    , @NamedQuery(name = "RnGcListanegraTbl_1.findByNumeroFechaOficioGlobalDefinitivoDOF", query = "SELECT r FROM RnGcListanegraTbl_1 r WHERE r.numeroFechaOficioGlobalDefinitivoDOF = :numeroFechaOficioGlobalDefinitivoDOF")
    , @NamedQuery(name = "RnGcListanegraTbl_1.findByPublicacionDOFdefinitivos", query = "SELECT r FROM RnGcListanegraTbl_1 r WHERE r.publicacionDOFdefinitivos = :publicacionDOFdefinitivos")
    , @NamedQuery(name = "RnGcListanegraTbl_1.findByNumeroFechaOficioGlobalSentenciaSAT", query = "SELECT r FROM RnGcListanegraTbl_1 r WHERE r.numeroFechaOficioGlobalSentenciaSAT = :numeroFechaOficioGlobalSentenciaSAT")
    , @NamedQuery(name = "RnGcListanegraTbl_1.findByPublicacionSATsentencia", query = "SELECT r FROM RnGcListanegraTbl_1 r WHERE r.publicacionSATsentencia = :publicacionSATsentencia")
    , @NamedQuery(name = "RnGcListanegraTbl_1.findByNumeroFechaOficioGlobalSentenciaDOF", query = "SELECT r FROM RnGcListanegraTbl_1 r WHERE r.numeroFechaOficioGlobalSentenciaDOF = :numeroFechaOficioGlobalSentenciaDOF")
    , @NamedQuery(name = "RnGcListanegraTbl_1.findByPublicacionDOFsentencia", query = "SELECT r FROM RnGcListanegraTbl_1 r WHERE r.publicacionDOFsentencia = :publicacionDOFsentencia")
    , @NamedQuery(name = "RnGcListanegraTbl_1.findByCreadoPor", query = "SELECT r FROM RnGcListanegraTbl_1 r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcListanegraTbl_1.findByFechaCreacion", query = "SELECT r FROM RnGcListanegraTbl_1 r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcListanegraTbl_1.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcListanegraTbl_1 r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcListanegraTbl_1.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcListanegraTbl_1 r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcListanegraTbl_1.findByEstado", query = "SELECT r FROM RnGcListanegraTbl_1 r WHERE r.estado = :estado")})
public class RnGcListanegraTbl_1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 13)
    @Column(name = "RFC")
    private String rfc;
    @Lob
    @Size(max = 65535)
    @Column(name = "nombreContribuyente")
    private String nombreContribuyente;
    @Size(max = 50)
    @Column(name = "situacionContribuyente")
    private String situacionContribuyente;
    @Size(max = 200)
    @Column(name = "numeroFechaOficioGlobalPresuncionSAT")
    private String numeroFechaOficioGlobalPresuncionSAT;
    @Size(max = 200)
    @Column(name = "publicacionSATpresuntos")
    private String publicacionSATpresuntos;
    @Size(max = 200)
    @Column(name = "numeroFechaOficioGlobalPresuncionDOF")
    private String numeroFechaOficioGlobalPresuncionDOF;
    @Size(max = 200)
    @Column(name = "publicacionDOFpresuntos")
    private String publicacionDOFpresuntos;
    @Size(max = 200)
    @Column(name = "numeroFechaOficioGlobalDesvirtuadoSAT")
    private String numeroFechaOficioGlobalDesvirtuadoSAT;
    @Size(max = 200)
    @Column(name = "publicacionSATdesvirtuados")
    private String publicacionSATdesvirtuados;
    @Size(max = 200)
    @Column(name = "numeroFechaOficioGlobalDesvirtuadoDOF")
    private String numeroFechaOficioGlobalDesvirtuadoDOF;
    @Size(max = 200)
    @Column(name = "publicacionDOFdesvirtuados")
    private String publicacionDOFdesvirtuados;
    @Size(max = 200)
    @Column(name = "numeroFechaOficioGlobalDefinitivoSAT")
    private String numeroFechaOficioGlobalDefinitivoSAT;
    @Size(max = 200)
    @Column(name = "publicacionSATdefinitivos")
    private String publicacionSATdefinitivos;
    @Size(max = 200)
    @Column(name = "numeroFechaOficioGlobalDefinitivoDOF")
    private String numeroFechaOficioGlobalDefinitivoDOF;
    @Size(max = 200)
    @Column(name = "publicacionDOFdefinitivos")
    private String publicacionDOFdefinitivos;
    @Size(max = 200)
    @Column(name = "numeroFechaOficioGlobalSentenciaSAT")
    private String numeroFechaOficioGlobalSentenciaSAT;
    @Size(max = 200)
    @Column(name = "publicacionSATsentencia")
    private String publicacionSATsentencia;
    @Size(max = 200)
    @Column(name = "numeroFechaOficioGlobalSentenciaDOF")
    private String numeroFechaOficioGlobalSentenciaDOF;
    @Size(max = 200)
    @Column(name = "publicacionDOFsentencia")
    private String publicacionDOFsentencia;
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "idTipoLista", referencedColumnName = "id")
    @ManyToOne
    private RnGcTipolistanegraTbl_1 idTipoLista;

    public RnGcListanegraTbl_1() {
    }

    public RnGcListanegraTbl_1(Integer id) {
        this.id = id;
    }

    public RnGcListanegraTbl_1(Integer id, Date fechaCreacion, String estado) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombreContribuyente() {
        return nombreContribuyente;
    }

    public void setNombreContribuyente(String nombreContribuyente) {
        this.nombreContribuyente = nombreContribuyente;
    }

    public String getSituacionContribuyente() {
        return situacionContribuyente;
    }

    public void setSituacionContribuyente(String situacionContribuyente) {
        this.situacionContribuyente = situacionContribuyente;
    }

    public String getNumeroFechaOficioGlobalPresuncionSAT() {
        return numeroFechaOficioGlobalPresuncionSAT;
    }

    public void setNumeroFechaOficioGlobalPresuncionSAT(String numeroFechaOficioGlobalPresuncionSAT) {
        this.numeroFechaOficioGlobalPresuncionSAT = numeroFechaOficioGlobalPresuncionSAT;
    }

    public String getPublicacionSATpresuntos() {
        return publicacionSATpresuntos;
    }

    public void setPublicacionSATpresuntos(String publicacionSATpresuntos) {
        this.publicacionSATpresuntos = publicacionSATpresuntos;
    }

    public String getNumeroFechaOficioGlobalPresuncionDOF() {
        return numeroFechaOficioGlobalPresuncionDOF;
    }

    public void setNumeroFechaOficioGlobalPresuncionDOF(String numeroFechaOficioGlobalPresuncionDOF) {
        this.numeroFechaOficioGlobalPresuncionDOF = numeroFechaOficioGlobalPresuncionDOF;
    }

    public String getPublicacionDOFpresuntos() {
        return publicacionDOFpresuntos;
    }

    public void setPublicacionDOFpresuntos(String publicacionDOFpresuntos) {
        this.publicacionDOFpresuntos = publicacionDOFpresuntos;
    }

    public String getNumeroFechaOficioGlobalDesvirtuadoSAT() {
        return numeroFechaOficioGlobalDesvirtuadoSAT;
    }

    public void setNumeroFechaOficioGlobalDesvirtuadoSAT(String numeroFechaOficioGlobalDesvirtuadoSAT) {
        this.numeroFechaOficioGlobalDesvirtuadoSAT = numeroFechaOficioGlobalDesvirtuadoSAT;
    }

    public String getPublicacionSATdesvirtuados() {
        return publicacionSATdesvirtuados;
    }

    public void setPublicacionSATdesvirtuados(String publicacionSATdesvirtuados) {
        this.publicacionSATdesvirtuados = publicacionSATdesvirtuados;
    }

    public String getNumeroFechaOficioGlobalDesvirtuadoDOF() {
        return numeroFechaOficioGlobalDesvirtuadoDOF;
    }

    public void setNumeroFechaOficioGlobalDesvirtuadoDOF(String numeroFechaOficioGlobalDesvirtuadoDOF) {
        this.numeroFechaOficioGlobalDesvirtuadoDOF = numeroFechaOficioGlobalDesvirtuadoDOF;
    }

    public String getPublicacionDOFdesvirtuados() {
        return publicacionDOFdesvirtuados;
    }

    public void setPublicacionDOFdesvirtuados(String publicacionDOFdesvirtuados) {
        this.publicacionDOFdesvirtuados = publicacionDOFdesvirtuados;
    }

    public String getNumeroFechaOficioGlobalDefinitivoSAT() {
        return numeroFechaOficioGlobalDefinitivoSAT;
    }

    public void setNumeroFechaOficioGlobalDefinitivoSAT(String numeroFechaOficioGlobalDefinitivoSAT) {
        this.numeroFechaOficioGlobalDefinitivoSAT = numeroFechaOficioGlobalDefinitivoSAT;
    }

    public String getPublicacionSATdefinitivos() {
        return publicacionSATdefinitivos;
    }

    public void setPublicacionSATdefinitivos(String publicacionSATdefinitivos) {
        this.publicacionSATdefinitivos = publicacionSATdefinitivos;
    }

    public String getNumeroFechaOficioGlobalDefinitivoDOF() {
        return numeroFechaOficioGlobalDefinitivoDOF;
    }

    public void setNumeroFechaOficioGlobalDefinitivoDOF(String numeroFechaOficioGlobalDefinitivoDOF) {
        this.numeroFechaOficioGlobalDefinitivoDOF = numeroFechaOficioGlobalDefinitivoDOF;
    }

    public String getPublicacionDOFdefinitivos() {
        return publicacionDOFdefinitivos;
    }

    public void setPublicacionDOFdefinitivos(String publicacionDOFdefinitivos) {
        this.publicacionDOFdefinitivos = publicacionDOFdefinitivos;
    }

    public String getNumeroFechaOficioGlobalSentenciaSAT() {
        return numeroFechaOficioGlobalSentenciaSAT;
    }

    public void setNumeroFechaOficioGlobalSentenciaSAT(String numeroFechaOficioGlobalSentenciaSAT) {
        this.numeroFechaOficioGlobalSentenciaSAT = numeroFechaOficioGlobalSentenciaSAT;
    }

    public String getPublicacionSATsentencia() {
        return publicacionSATsentencia;
    }

    public void setPublicacionSATsentencia(String publicacionSATsentencia) {
        this.publicacionSATsentencia = publicacionSATsentencia;
    }

    public String getNumeroFechaOficioGlobalSentenciaDOF() {
        return numeroFechaOficioGlobalSentenciaDOF;
    }

    public void setNumeroFechaOficioGlobalSentenciaDOF(String numeroFechaOficioGlobalSentenciaDOF) {
        this.numeroFechaOficioGlobalSentenciaDOF = numeroFechaOficioGlobalSentenciaDOF;
    }

    public String getPublicacionDOFsentencia() {
        return publicacionDOFsentencia;
    }

    public void setPublicacionDOFsentencia(String publicacionDOFsentencia) {
        this.publicacionDOFsentencia = publicacionDOFsentencia;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public RnGcTipolistanegraTbl_1 getIdTipoLista() {
        return idTipoLista;
    }

    public void setIdTipoLista(RnGcTipolistanegraTbl_1 idTipoLista) {
        this.idTipoLista = idTipoLista;
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
        if (!(object instanceof RnGcListanegraTbl_1)) {
            return false;
        }
        RnGcListanegraTbl_1 other = (RnGcListanegraTbl_1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcListanegraTbl_1[ id=" + id + " ]";
    }
    
}
