package mx.com.rocketnegocios.entities;

import java.io.Serializable;
import java.util.Date; 
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity 
@Table (name = "rn_gc_aceptacion_terminos_tbl")
@XmlRootElement 
@NamedQueries ({
    @NamedQuery(name = "RnGcAceptacionTerminosTbl.findAll", query = "SELECT r FROM RnGcAceptacionTerminosTbl r"),
    @NamedQuery(name = "RnGcAceptacionTerminosTbl.findUltimaPorUsuario", query = "SELECT r FROM RnGcAceptacionTerminosTbl r WHERE r.usuarioId.id = :idUsuario ORDER BY r.fechaHora DESC")
})
public class RnGcAceptacionTerminosTbl implements Serializable {
    private static final long serialVersionUID =1L; 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic (optional = false)
    private Integer id;
    
    @JoinColumn (name= "usuarioId", referencedColumnName ="Id")
    @ManyToOne (optional = false)
    @NotNull 
    private RnGcUsuariosTbl usuarioId;

    @Basic (optional = false)
    @NotNull 
    @Size ( max=20)
    private String versionTerminos; 
    
    @Basic(optional = false)
    @NotNull
    @Size ( max=20)
    private String versionPrivacidad;

    @Basic (optional =false)
    @NotNull 
    @Size (min=1, max=20)
    private String estado;

    @Basic (optional = false)
    @NotNull 
    @Temporal (TemporalType.TIMESTAMP)
    private Date fechaHora;

    public RnGcAceptacionTerminosTbl(){ }
    public RnGcAceptacionTerminosTbl (Integer id){this.id = id;}

    public Integer getId(){return id;}
    public void setId(Integer id){this.id = id;}
    public RnGcUsuariosTbl getUsuarioId() {return usuarioId;}
    public void setUsuarioId(RnGcUsuariosTbl usuarioId){this.usuarioId = usuarioId;}
    public String getVersionTerminos(){return versionTerminos;}
    public void setVersionTerminos(String versionTerminos){this.versionTerminos = versionTerminos;}
    public String getVersionPrivacidad(){return versionPrivacidad;}
    public void setVersionPrivacidad(String versionPrivacidad){this.versionPrivacidad = versionPrivacidad;}
    public String getEstado(){return estado;}
    public void setEstado(String estado){this.estado = estado;}
    public Date getFechaHora(){return fechaHora;}
    public void setFechaHora(Date fechaHora){this.fechaHora = fechaHora;}

    @Override 
    public int hashCode(){ int hash = 0; hash += (id != null ? id.hashCode() : 0); return hash;}

    @Override 
    public boolean equals(Object object){
        if (!(object instanceof RnGcAceptacionTerminosTbl)) {return false;}
        RnGcAceptacionTerminosTbl other = (RnGcAceptacionTerminosTbl) object;
        return !((this.id == null && other.id !=null) || (this.id !=null && !this.id.equals(other.id)));
    }
    @Override 
    public String toString(){return "mx.com.rocketnegocios.entities.RnGcAceptacionTerminosTbl[ id=" + id + " ]";}
}
