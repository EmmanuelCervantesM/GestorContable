/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.entities;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author EBIW1
 */
@Entity
@Table(name = "rn_gc_complementos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcComplementos.findAll", query = "SELECT r FROM RnGcComplementos r")
    , @NamedQuery(name = "RnGcComplementos.findByPkId", query = "SELECT r FROM RnGcComplementos r WHERE r.pkId = :pkId")
    , @NamedQuery(name = "RnGcComplementos.findByComplementoSeleccionado", query = "SELECT r FROM RnGcComplementos r WHERE r.complementoSeleccionado = :complementoSeleccionado")
    , @NamedQuery(name = "RnGcComplementos.findByNombreAlumno", query = "SELECT r FROM RnGcComplementos r WHERE r.nombreAlumno = :nombreAlumno")
    , @NamedQuery(name = "RnGcComplementos.findByCurp", query = "SELECT r FROM RnGcComplementos r WHERE r.curp = :curp")
    , @NamedQuery(name = "RnGcComplementos.findByNivelEducativo", query = "SELECT r FROM RnGcComplementos r WHERE r.nivelEducativo = :nivelEducativo")
    , @NamedQuery(name = "RnGcComplementos.findByAutRVOE", query = "SELECT r FROM RnGcComplementos r WHERE r.autRVOE = :autRVOE")
    , @NamedQuery(name = "RnGcComplementos.findByRfcPago", query = "SELECT r FROM RnGcComplementos r WHERE r.rfcPago = :rfcPago")
    , @NamedQuery(name = "RnGcComplementos.findByComplementoEscuela", query = "SELECT r FROM RnGcComplementos r WHERE r.complementoEscuela = :complementoEscuela")})
public class RnGcComplementos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id")
    private Integer pkId;
    @Size(max = 255)
    @Column(name = "complementoSeleccionado")
    private String complementoSeleccionado;
    @Size(max = 255)
    @Column(name = "nombreAlumno")
    private String nombreAlumno;
    @Size(max = 18)
    @Column(name = "curp")
    private String curp;
    @Size(max = 50)
    @Column(name = "nivelEducativo")
    private String nivelEducativo;
    @Size(max = 50)
    @Column(name = "autRVOE")
    private String autRVOE;
    @Size(max = 13)
    @Column(name = "rfcPago")
    private String rfcPago;
    @Column(name = "complementoEscuela")
    private Boolean complementoEscuela;
    @JoinColumn(name = "cfdi_Id", referencedColumnName = "Id")
    @ManyToOne
    private RnGcCfdisTbl cfdiId;

    public RnGcComplementos() {
    }

    public RnGcComplementos(Integer pkId) {
        this.pkId = pkId;
    }

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public String getComplementoSeleccionado() {
        return complementoSeleccionado;
    }

    public void setComplementoSeleccionado(String complementoSeleccionado) {
        this.complementoSeleccionado = complementoSeleccionado;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNivelEducativo() {
        return nivelEducativo;
    }

    public void setNivelEducativo(String nivelEducativo) {
        this.nivelEducativo = nivelEducativo;
    }

    public String getAutRVOE() {
        return autRVOE;
    }

    public void setAutRVOE(String autRVOE) {
        this.autRVOE = autRVOE;
    }

    public String getRfcPago() {
        return rfcPago;
    }

    public void setRfcPago(String rfcPago) {
        this.rfcPago = rfcPago;
    }

    public Boolean getComplementoEscuela() {
        return complementoEscuela;
    }

    public void setComplementoEscuela(Boolean complementoEscuela) {
        this.complementoEscuela = complementoEscuela;
    }

    public RnGcCfdisTbl getCfdiId() {
        return cfdiId;
    }

    public void setCfdiId(RnGcCfdisTbl cfdiId) {
        this.cfdiId = cfdiId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkId != null ? pkId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RnGcComplementos)) {
            return false;
        }
        RnGcComplementos other = (RnGcComplementos) object;
        if ((this.pkId == null && other.pkId != null) || (this.pkId != null && !this.pkId.equals(other.pkId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcComplementos[ pkId=" + pkId + " ]";
    }
    
}
