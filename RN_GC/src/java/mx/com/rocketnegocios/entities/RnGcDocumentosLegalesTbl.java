package mx.com.rocketnegocios.entities;

import java.io.Serializable;
import java.util.Date; 
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement; 

@Entity
@Table(name = "rn_gc_documentos_legales_tbl")
@XmlRootElement 
@NamedQueries({
    @NamedQuery(name = "RnGcDocumentosLegalesTbl.findAll", query ="SELECT r FROM RnGcDocumentosLegalesTbl r"),
    @NamedQuery(name = "RnGcDocumentosLegalesTbl.findVigentePorTipo", query = "SELECT r FROM RnGcDocumentosLegalesTbl r WHERE r.tipoDocumento = :tipoDocumento AND r.vigente = 'S'")
})
public class RnGcDocumentosLegalesTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    //identificador único de la entidad, generado automaticamente por la base de datos
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id; 

    // definir los atributos de la entidad, con sus restricciones de validación
    @Basic(optional = false)
    @NotNull
    @Size (min=1, max=20)
    private String tipoDocumento; 

    @Basic(optional = false)
    @NotNull
    @Size (min=1, max=20)
    private String version;

    @Lob 
    @Basic(optional = false)
    @NotNull 
    private byte[] archivoPdf;

    @Basic(optional = false)
    @NotNull 
    @Size (min = 1, max =150)
    private String nombreArchivo; 

    @Basic(optional = false)
    @NotNull
    @Size(min=1, max=1)
    private String vigente; 

    @Basic(optional = false) 
    @NotNull 
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPublicacion;

    @Basic(optional = false)
    @NotNull 
    private int creadoPor;

    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    public RnGcDocumentosLegalesTbl() {}
    public RnGcDocumentosLegalesTbl(Integer id) {this.id = id;}

    public Integer getId() {return id;}
    public void setId(Integer id) { this.id = id;}
    public String getTipoDocumento() {return tipoDocumento;}
    public void setTipoDocumento(String tipoDocumento) {this.tipoDocumento = tipoDocumento;}
    public String getVersion() {return version;}
    public void setVersion(String version) {this.version = version;}
    public byte[] getArchivoPdf() {return archivoPdf;}
    public void setArchivoPdf (byte[] archivoPdf) {this.archivoPdf = archivoPdf;}
    public String getNombreArchivo() {return nombreArchivo;}
    public void setNombreArchivo(String nombreArchivo) {this.nombreArchivo = nombreArchivo;}
    public String getVigente() {return vigente;}
    public void setVigente(String vigente) {this.vigente = vigente;}
    public Date getFechaPublicacion() {return fechaPublicacion;}
    public void setFechaPublicacion(Date fechaPublicacion) {this.fechaPublicacion = fechaPublicacion;}
    public int getCreadoPor() {return creadoPor;}
    public void setCreadoPor(int creadoPor) {this.creadoPor = creadoPor;}
    public Date getFechaCreacion() {return fechaCreacion;}
    public void setFechaCreacion(Date fechaCreacion) {this.fechaCreacion = fechaCreacion;}
    
    @Override 
    public int hashCode() {int hash =0; hash += (id != null ? id.hashCode(): 0); return hash;}
    
    @Override 
    public boolean equals(Object object) {
        if (!(object instanceof RnGcDocumentosLegalesTbl)) return false;
            RnGcDocumentosLegalesTbl other = (RnGcDocumentosLegalesTbl) object; 
            return !((this.id == null && other.id !=null) || (this.id != null && !this.id.equals(other.id)));
        
    }
    @Override
    public String toString() {return "mx.com.rocketnegocios.entities.RnGcDocumentosLegalesTbl[ id=" + id + " ]";}

}
