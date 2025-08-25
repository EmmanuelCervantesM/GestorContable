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

/**
 *
 * @author Developer1
 */
@Entity
@Table(name = "rn_gc_timbres_total_tbl")
@NamedQueries({
    @NamedQuery(name = "RnGcTimbresTotalTbl.findAll", query = "SELECT r FROM RnGcTimbresTotalTbl r")
    ,
    @NamedQuery(name = "RnGcTimbresTotalTbl.findById", query = "SELECT r FROM RnGcTimbresTotalTbl r WHERE r.id = :id")
    ,
    @NamedQuery(name = "RnGcTimbresTotalTbl.findByUsuarioId", query = "SELECT r FROM RnGcTimbresTotalTbl r WHERE r.usuarioId = :usuarioId")
    ,
    @NamedQuery(name = "RnGcTimbresTotalTbl.sumTimbresTotales",
            query = "SELECT SUM(r.timbresTotal) FROM RnGcTimbresTotalTbl r WHERE r.usuarioId = :usuarioId")
})
public class RnGcTimbresTotalTbl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;

    @NotNull
    @Column(name = "timbres_total")
    private int timbresTotal;

    // Auditoría
    @Basic(optional = false)
    @NotNull
    @Column(name = "creadoPor")
    private int creadoPor;

    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaCreacion")
    private Date fechaCreacion;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ultimaActualizacionPor")
    private int ultimaActualizacionPor;

    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ultimaFechaActualizacion")
    private Date ultimaFechaActualizacion;

    @NotNull
    @JoinColumn(name = "usuarios_Id", referencedColumnName = "Id")
    @ManyToOne
    private RnGcUsuariosTbl usuarioId;

    // Constructores
    public RnGcTimbresTotalTbl() {
    }

    public RnGcTimbresTotalTbl(Integer id, int timbresTotal, int timbresUsados, int timbresRestantes,
            int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion) {
        this.id = id;
        this.timbresTotal = timbresTotal;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RnGcUsuariosTbl getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(RnGcUsuariosTbl usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getTimbresTotal() {
        return timbresTotal;
    }

    public void setTimbresTotal(int timbresTotal) {
        this.timbresTotal = timbresTotal;
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

}
