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
@Table(name = "rn_gc_listaNegra_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcListaNegraTbl.findAll", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.estado = 'A'")
    , @NamedQuery(name = "RnGcListaNegraTbl.findById", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcListaNegraTbl.findByTipoLista", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.idTipoLista = :idTipoLista and r.estado = 'A'")
    , @NamedQuery(name = "RnGcListaNegraTbl.findByRfc", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.rfc = :rfc and r.estado = 'A'")
    , @NamedQuery(name = "RnGcListaNegraTbl.findByNombreContribuyente", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.nombreContribuyente = :nombreContribuyente AND r.estado = 'A'")
    , @NamedQuery(name = "RnGcListaNegraTbl.findBySituacionContribuyente", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.situacionContribuyente = :situacionContribuyente")
    , @NamedQuery(name = "RnGcListaNegraTbl.findByNumeroFechaOficioGlobalPresuncionSAT", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.numeroFechaOficioGlobalPresuncionSAT = :numeroFechaOficioGlobalPresuncionSAT")
    , @NamedQuery(name = "RnGcListaNegraTbl.findByPublicacionSATpresuntos", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.publicacionSATpresuntos = :publicacionSATpresuntos")
    , @NamedQuery(name = "RnGcListaNegraTbl.findByNumeroFechaOficioGlobalPresuncionDOF", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.numeroFechaOficioGlobalPresuncionDOF = :numeroFechaOficioGlobalPresuncionDOF")
    , @NamedQuery(name = "RnGcListaNegraTbl.findByPublicacionDOFpresuntos", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.publicacionDOFpresuntos = :publicacionDOFpresuntos")
    , @NamedQuery(name = "RnGcListaNegraTbl.findByNumeroFechaOficioGlobalDesvirtuadoSAT", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.numeroFechaOficioGlobalDesvirtuadoSAT = :numeroFechaOficioGlobalDesvirtuadoSAT")
    , @NamedQuery(name = "RnGcListaNegraTbl.findByPublicacionSATdesvirtuados", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.publicacionSATdesvirtuados = :publicacionSATdesvirtuados")
    , @NamedQuery(name = "RnGcListaNegraTbl.findByNumeroFechaOficioGlobalDesvirtuadoDOF", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.numeroFechaOficioGlobalDesvirtuadoDOF = :numeroFechaOficioGlobalDesvirtuadoDOF")
    , @NamedQuery(name = "RnGcListaNegraTbl.findByPublicacionDOFdesvirtuados", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.publicacionDOFdesvirtuados = :publicacionDOFdesvirtuados")
    , @NamedQuery(name = "RnGcListaNegraTbl.findByNumeroFechaOficioGlobalDefinitivoSAT", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.numeroFechaOficioGlobalDefinitivoSAT = :numeroFechaOficioGlobalDefinitivoSAT")
    , @NamedQuery(name = "RnGcListaNegraTbl.findByPublicacionSATdefinitivos", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.publicacionSATdefinitivos = :publicacionSATdefinitivos")
    , @NamedQuery(name = "RnGcListaNegraTbl.findByNumeroFechaOficioGlobalDefinitivoDOF", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.numeroFechaOficioGlobalDefinitivoDOF = :numeroFechaOficioGlobalDefinitivoDOF")
    , @NamedQuery(name = "RnGcListaNegraTbl.findByPublicacionDOFdefinitivos", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.publicacionDOFdefinitivos = :publicacionDOFdefinitivos")
    , @NamedQuery(name = "RnGcListaNegraTbl.findByNumeroFechaOficioGlobalSentenciaSAT", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.numeroFechaOficioGlobalSentenciaSAT = :numeroFechaOficioGlobalSentenciaSAT")
    , @NamedQuery(name = "RnGcListaNegraTbl.findByPublicacionSATsentencia", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.publicacionSATsentencia = :publicacionSATsentencia")
    , @NamedQuery(name = "RnGcListaNegraTbl.findByNumeroFechaOficioGlobalSentenciaDOF", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.numeroFechaOficioGlobalSentenciaDOF = :numeroFechaOficioGlobalSentenciaDOF")
    , @NamedQuery(name = "RnGcListaNegraTbl.findByPublicacionDOFsentencia", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.publicacionDOFsentencia = :publicacionDOFsentencia")
    , @NamedQuery(name = "RnGcListaNegraTbl.findByCreadoPor", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcListaNegraTbl.findByFechaCreacion", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcListaNegraTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcListaNegraTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcListaNegraTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcListaNegraTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 13)
    @Column(name = "RFC")
    private String rfc;
    @Column(name = "nombreContribuyente")
    private String nombreContribuyente;
    @Size(max = 50)
    @Column(name = "situacionContribuyente")
    private String situacionContribuyente;
    @Size(max = 200)
    @Column(name = "numeroFechaOficioGlobalPresuncionSAT")
    private String numeroFechaOficioGlobalPresuncionSAT;
    @Column(name = "publicacionSATpresuntos")
    private String publicacionSATpresuntos;
    @Size(max = 200)
    @Column(name = "numeroFechaOficioGlobalPresuncionDOF")
    private String numeroFechaOficioGlobalPresuncionDOF;
    @Column(name = "publicacionDOFpresuntos")
    private String publicacionDOFpresuntos;
    @Size(max = 200)
    @Column(name = "numeroFechaOficioGlobalDesvirtuadoSAT")
    private String numeroFechaOficioGlobalDesvirtuadoSAT;
    @Column(name = "publicacionSATdesvirtuados")
    private String publicacionSATdesvirtuados;
    @Size(max = 200)
    @Column(name = "numeroFechaOficioGlobalDesvirtuadoDOF")
    private String numeroFechaOficioGlobalDesvirtuadoDOF;
    @Column(name = "publicacionDOFdesvirtuados")
    private String publicacionDOFdesvirtuados;
    @Size(max = 200)
    @Column(name = "numeroFechaOficioGlobalDefinitivoSAT")
    private String numeroFechaOficioGlobalDefinitivoSAT;
    @Column(name = "publicacionSATdefinitivos")
    private String publicacionSATdefinitivos;
    @Size(max = 200)
    @Column(name = "numeroFechaOficioGlobalDefinitivoDOF")
    private String numeroFechaOficioGlobalDefinitivoDOF;
    @Column(name = "publicacionDOFdefinitivos")
    private String publicacionDOFdefinitivos;
    @Size(max = 200)
    @Column(name = "numeroFechaOficioGlobalSentenciaSAT")
    private String numeroFechaOficioGlobalSentenciaSAT;
    @Column(name = "publicacionSATsentencia")
    private String publicacionSATsentencia;
    @Size(max = 200)
    @Column(name = "numeroFechaOficioGlobalSentenciaDOF")
    private String numeroFechaOficioGlobalSentenciaDOF;
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
    @JoinColumn(name = "idTipoLista", referencedColumnName = "id")
    @ManyToOne
    private RnGcTipoListaNegraTbl idTipoLista;
    @Column(name = "estado")
    private String estado;
    @NotNull

    public RnGcListaNegraTbl() {
    }

    public RnGcListaNegraTbl(Integer id) {
        this.id = id;
    }

    public RnGcListaNegraTbl(Integer id, Date fechaCreacion) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
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

    public RnGcTipoListaNegraTbl getIdTipoLista() {
        return idTipoLista;
    }

    public void setIdTipoLista(RnGcTipoListaNegraTbl idTipoLista) {
        this.idTipoLista = idTipoLista;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
        if (!(object instanceof RnGcListaNegraTbl)) {
            return false;
        }
        RnGcListaNegraTbl other = (RnGcListaNegraTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcListaNegraTbl[ id=" + id + " ]";
    }
    
}
