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
 * @author Developer1
 */
@Entity
@Table(name = "rn_gc_menuslineas_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcMenuslineasTbl.findAll", query = "SELECT r FROM RnGcMenuslineasTbl r")
    , @NamedQuery(name = "RnGcMenuslineasTbl.findByCreadoPor", query = "SELECT r FROM RnGcMenuslineasTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcMenuslineasTbl.findByFechaCreacion", query = "SELECT r FROM RnGcMenuslineasTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcMenuslineasTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcMenuslineasTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcMenuslineasTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcMenuslineasTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcMenuslineasTbl.findById", query = "SELECT r FROM RnGcMenuslineasTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcMenuslineasTbl.findByMenuId", query = "SELECT r FROM RnGcMenuslineasTbl r WHERE r.menuId = :menuId")
    , @NamedQuery(name = "RnGcMenuslineasTbl.findByFuncionesId", query = "SELECT r FROM RnGcMenuslineasTbl r WHERE r.funcionesId = :funcionesId")
    , @NamedQuery(name = "RnGcMenuslineasTbl.findByFuncionesId2", query = "SELECT r FROM RnGcMenuslineasTbl r WHERE r.menuId = :menuId AND r.funcionesId = :funcionesId")
    , @NamedQuery(name = "RnGcMenuslineasTbl.findByNoSecuencia", query = "SELECT r FROM RnGcMenuslineasTbl r WHERE r.noSecuencia = :noSecuencia")
    , @NamedQuery(name = "RnGcMenuslineasTbl.findByAdicional1", query = "SELECT r FROM RnGcMenuslineasTbl r WHERE r.adicional1 = :adicional1")
    , @NamedQuery(name = "RnGcMenuslineasTbl.findByAdicional2", query = "SELECT r FROM RnGcMenuslineasTbl r WHERE r.adicional2 = :adicional2")
    , @NamedQuery(name = "RnGcMenuslineasTbl.findByAdicional3", query = "SELECT r FROM RnGcMenuslineasTbl r WHERE r.adicional3 = :adicional3")
    , @NamedQuery(name = "RnGcMenuslineasTbl.findByAdicional4", query = "SELECT r FROM RnGcMenuslineasTbl r WHERE r.adicional4 = :adicional4")
    , @NamedQuery(name = "RnGcMenuslineasTbl.findByAdicional5", query = "SELECT r FROM RnGcMenuslineasTbl r WHERE r.adicional5 = :adicional5")})
public class RnGcMenuslineasTbl implements Serializable {

    private static final long serialVersionUID = 1L;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "noSecuencia")
    private int noSecuencia;
    @Size(max = 45)
    @Column(name = "Adicional1")
    private String adicional1;
    @Size(max = 45)
    @Column(name = "Adicional2")
    private String adicional2;
    @Size(max = 45)
    @Column(name = "Adicional3")
    private String adicional3;
    @Size(max = 45)
    @Column(name = "Adicional4")
    private String adicional4;
    @Size(max = 45)
    @Column(name = "Adicional5")
    private String adicional5;
    @JoinColumn(name = "Funciones_Id", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private RnGcFuncionesTbl funcionesId;
    @JoinColumn(name = "Menu_Id", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private RnGcMenusTbl menuId;

    public RnGcMenuslineasTbl() {
    }

    public RnGcMenuslineasTbl(Integer id) {
        this.id = id;
    }

    public RnGcMenuslineasTbl(Integer id, int creadoPor, Date fechaCreacion, int ultimaActualizacionPor, Date ultimaFechaActualizacion, int noSecuencia) {
        this.id = id;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.ultimaActualizacionPor = ultimaActualizacionPor;
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
        this.noSecuencia = noSecuencia;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNoSecuencia() {
        return noSecuencia;
    }

    public void setNoSecuencia(int noSecuencia) {
        this.noSecuencia = noSecuencia;
    }

    public String getAdicional1() {
        return adicional1;
    }

    public void setAdicional1(String adicional1) {
        this.adicional1 = adicional1;
    }

    public String getAdicional2() {
        return adicional2;
    }

    public void setAdicional2(String adicional2) {
        this.adicional2 = adicional2;
    }

    public String getAdicional3() {
        return adicional3;
    }

    public void setAdicional3(String adicional3) {
        this.adicional3 = adicional3;
    }

    public String getAdicional4() {
        return adicional4;
    }

    public void setAdicional4(String adicional4) {
        this.adicional4 = adicional4;
    }

    public String getAdicional5() {
        return adicional5;
    }

    public void setAdicional5(String adicional5) {
        this.adicional5 = adicional5;
    }

    public RnGcFuncionesTbl getFuncionesId() {
        return funcionesId;
    }

    public void setFuncionesId(RnGcFuncionesTbl funcionesId) {
        this.funcionesId = funcionesId;
    }

    public RnGcMenusTbl getMenuId() {
        return menuId;
    }

    public void setMenuId(RnGcMenusTbl menuId) {
        this.menuId = menuId;
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
        if (!(object instanceof RnGcMenuslineasTbl)) {
            return false;
        }
        RnGcMenuslineasTbl other = (RnGcMenuslineasTbl) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcMenuslineasTbl[ id=" + id + " ]";
    }

}
