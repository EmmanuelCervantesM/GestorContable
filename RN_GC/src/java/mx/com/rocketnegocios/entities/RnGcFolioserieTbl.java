/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
 * @author Developer1
 */
@Entity
@Table(name = "rn_gc_folioserie_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcFolioserieTbl.findAll", query = "SELECT r FROM RnGcFolioserieTbl r")
    , @NamedQuery(name = "RnGcFolioserieTbl.findById", query = "SELECT r FROM RnGcFolioserieTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcFolioserieTbl.findByNombre", query = "SELECT r FROM RnGcFolioserieTbl r WHERE r.nombre = :nombre")
    , @NamedQuery(name = "RnGcFolioserieTbl.findByCreadoPor", query = "SELECT r FROM RnGcFolioserieTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcFolioserieTbl.findByFechaCreacion", query = "SELECT r FROM RnGcFolioserieTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcFolioserieTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcFolioserieTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcFolioserieTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcFolioserieTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcFolioserieTbl.findByUsuarioEstado", query = "SELECT r FROM RnGcFolioserieTbl r WHERE r.usuariosId = :usuariosId AND r.estado = :estado")
    , @NamedQuery(name = "RnGcFolioserieTbl.findByUsuarioSerie", query = "SELECT r FROM RnGcFolioserieTbl r WHERE r.usuariosId = :usuariosId AND r.serie = :serie")
    , @NamedQuery(name = "RnGcFolioserieTbl.findByUsuarioSerieYCertificado", query = "SELECT r FROM RnGcFolioserieTbl r WHERE r.usuariosId = :usuariosId AND r.serie = :serie AND r.certificadosId = :certificadoId")
    , @NamedQuery(name = "RnGcFolioserieTbl.findByUsuarioSerieCert", query = "SELECT r FROM RnGcFolioserieTbl r WHERE r.usuariosId = :usuariosId AND r.serie = :serie AND r.certificadosId = :certificadoId")
    , @NamedQuery(name = "RnGcFolioserieTbl.findByCertificado", query = "SELECT r FROM RnGcFolioserieTbl r WHERE r.certificadosId = :certificadosId")})
public class RnGcFolioserieTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String serie;
    @Basic(optional = false)
    @NotNull
    private Integer folio;
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
    @JoinColumn(name = "usuarios_Id", referencedColumnName = "Id")
    @ManyToOne
    private RnGcUsuariosTbl usuariosId;
    @JoinColumn(name = "certificados_Id", referencedColumnName = "Id")
    @ManyToOne
    private RnGcCertificadosTbl certificadosId;

    public RnGcFolioserieTbl() {
    }

    public RnGcFolioserieTbl(Integer id) {
        this.id = id;
    }

    public RnGcFolioserieTbl(Integer id, String nombre, String serie, Integer folio, String estado, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.nombre = nombre;
        this.serie = serie;
        this.folio = folio;
        this.estado = estado;
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
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Integer getFolio() {
        return folio;
    }

    public void setFolio(Integer folio) {
        this.folio = folio;
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

    public RnGcUsuariosTbl getUsuariosId() {
        return usuariosId;
    }

    public void setUsuariosId(RnGcUsuariosTbl usuariosId) {
        this.usuariosId = usuariosId;
    }

    public RnGcCertificadosTbl getCertificadosId() {
        return certificadosId;
    }

    public void setCertificadosId(RnGcCertificadosTbl certificadosId) {
        this.certificadosId = certificadosId;
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
        if (!(object instanceof RnGcFolioserieTbl)) {
            return false;
        }
        RnGcFolioserieTbl other = (RnGcFolioserieTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcFolioserieTbl[ id=" + id + " ]";
    }   
    
}
