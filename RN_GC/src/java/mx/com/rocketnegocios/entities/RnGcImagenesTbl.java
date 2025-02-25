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
import javax.persistence.Lob;
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
 * @author jhony
 */
@Entity
@Table(name = "rn_gc_imagenes_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcImagenesTbl.findAll", query = "SELECT r FROM RnGcImagenesTbl r"),
    @NamedQuery(name = "RnGcImagenesTbl.findById", query = "SELECT r FROM RnGcImagenesTbl r WHERE r.id = :id"),
    @NamedQuery(name = "RnGcImagenesTbl.findByRfc", query = "SELECT r FROM RnGcImagenesTbl r WHERE r.rfc = :rfc"),
    @NamedQuery(name = "RnGcImagenesTbl.findByNombreImagen", query = "SELECT r FROM RnGcImagenesTbl r WHERE r.nombreImagen = :nombreImagen"),
    @NamedQuery(name = "RnGcImagenesTbl.findByCreadoPor", query = "SELECT r FROM RnGcImagenesTbl r WHERE r.creadoPor = :creadoPor"),
    @NamedQuery(name = "RnGcImagenesTbl.findByFechaCreacion", query = "SELECT r FROM RnGcImagenesTbl r WHERE r.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "RnGcImagenesTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcImagenesTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor"),
    @NamedQuery(name = "RnGcImagenesTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcImagenesTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")})
public class RnGcImagenesTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "RFC")
    private String rfc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombreImagen")
    private String nombreImagen;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "Foto")
    private byte[] foto;
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

    public RnGcImagenesTbl() {
    }

    public RnGcImagenesTbl(Integer id) {
        this.id = id;
    }

    public RnGcImagenesTbl(Integer id, String rfc, String nombreImagen, byte[] foto, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.rfc = rfc;
        this.nombreImagen = nombreImagen;
        this.foto = foto;
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

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombreImagen() {
        return nombreImagen;
    }

    public void setNombreImagen(String nombreImagen) {
        this.nombreImagen = nombreImagen;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RnGcImagenesTbl)) {
            return false;
        }
        RnGcImagenesTbl other = (RnGcImagenesTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcImagenesTbl[ id=" + id + " ]";
    }
    
}
