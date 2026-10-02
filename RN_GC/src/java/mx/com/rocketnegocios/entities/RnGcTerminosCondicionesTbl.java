package mx.com.rocketnegocios.entities;

import java.io.Serializable;
import java.util.Date; 
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "rn_gc_terminos_condiciones_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcTerminosCondicionesTbl.findAll", query = "SELECT r FROM RnGcTerminosCondicionesTbl r"),
    @NamedQuery(name = "RnGcTerminosCondicionesTbl.findVigente", query = "SELECT r FROM RnGcTerminosCondicionesTbl r WHERE r.vigente ='S'")
})
public class RnGcTerminosCondicionesTbl implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Basic (optional = false)
    private Integer id;

    @Basic(optional = false)
    @NotNull 
    @Size(max =20)
    private String version; 

    @Lob 
    @Basic(optional = false)
    @NotNull
    private String contenido;

    @Basic(optional = false)
    @NotNull 
    @Size(min =1, max =1)
    private String vigente;
    
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPublicacion;

    @Basic (optional = false)
    @NotNull
    private int creadoPor;

    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    public RnGcTerminosCondicionesTbl() {}
    public RnGcTerminosCondicionesTbl(Integer id) {this.id = id;}

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    public String getVersion() {return version;}
    public void setVersion(String version) {this.version = version;}
    public String getVigente() {return vigente;}
    public void setVigente(String vigente) {this.vigente = vigente;}
    public Date getFechaPublicacion() {return fechaPublicacion;}
    public void setFechaPublicacion(Date fechaPublicacion) {this.fechaPublicacion = fechaPublicacion;}
    public int getCreadoPor() {return creadoPor;}
    public void setCreadoPor(int creadoPor) {this.creadoPor = creadoPor;}
    public Date getFechaCreacion() {return fechaCreacion;}
    public void setFechaCreacion(Date fechaCreacion) {this.fechaCreacion = fechaCreacion;}
    public String getContenido() {return contenido;}
    public void setContenido(String contenido) {this.contenido = contenido;}
    
    @Override
    public int hashCode(){
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    @Override 
    public boolean equals(Object object){
        if (!(object instanceof RnGcTerminosCondicionesTbl)) {
            return false;
        }
        RnGcTerminosCondicionesTbl other = (RnGcTerminosCondicionesTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcTerminosCondicionesTbl[ id=" + id + " ]";
    }


}

