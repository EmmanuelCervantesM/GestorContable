package mx.com.rocketnegocios.web;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import mx.com.rocketnegocios.beans.RnGcDocumentosLegalesTblFacade;
import mx.com.rocketnegocios.entities.RnGcDocumentosLegalesTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

//controlador de la pantalla de administracion sube nuevas versiones de
@Named("rnGcDocumentosLegalesTblController")
@SessionScoped
public class RnGcDocumentosLegalesTblController implements Serializable {

    @EJB
    private RnGcDocumentosLegalesTblFacade ejbFacade;

    private UploadedFile file;
    private String tipoDocumento; // temrinos o privacidad 
    private String version;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();

    public UploadedFile getFile() { return file; }
    public void setFile(UploadedFile file) { this.file = file; }
    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    //cehcar que rol tiene si es administrador o otro usuario 
    public boolean isEsAdministrador() {
        String listaPerfiles = String.valueOf(
                FacesContext.getCurrentInstance()
                        .getExternalContext().getSessionMap().get("listaPerfiles"));
        return listaPerfiles != null && listaPerfiles.contains("ADMINISTRADOR");
    }

    //surbir el PDF al servidor 
    public void subirDocumento() {
        if (!isEsAdministrador()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Solo un administrador puede subir este documento"));
            return;
        }
        if (tipoDocumento == null || tipoDocumento.isEmpty() ||version == null || version.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Selecciona el tipo de documento y/o ls version"));
            return;
        }
        if (!esPdfValido(file)){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El archivo debe ser un PDF válido (.pdf)"));
            return; 
        }
        if (file.getContents().length >10_000_000){ //los MB que permite subir osea 10MB 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error","El pdf no debe superar los 10MB :)"));
            return;
        }
        try {
            ejbFacade.marcarComoNoVigentes(tipoDocumento);
            RnGcDocumentosLegalesTbl doc = new RnGcDocumentosLegalesTbl();
            doc.setTipoDocumento(tipoDocumento);
            doc.setVersion(version);
            doc.setArchivoPdf(file.getContents());
            doc.setNombreArchivo(file.getFileName());
            doc.setVigente("S");
            doc.setFechaPublicacion(new Date());
            doc.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
            doc.setFechaCreacion(new Date());
            ejbFacade.create(doc);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Documento " + tipoDocumento + " versión " + version + " publicado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo subir el documento: " + e.getMessage()));
        }
    }
    // Validar el archivo subido sea realmente un PDF mediante los primeros Bytes del documento 
    private boolean esPdfValido (UploadedFile file){
        if (file == null || file.getFileName() == null){
            return false;
        }
        if (!file.getFileName().toLowerCase().endsWith(".pdf")){
            return false;
        }
        byte[] contenido = file.getContents();
        if(contenido == null || contenido.length <5){
            return false;
        }
        String firma = new String (contenido, 0,5 , java.nio.charset.StandardCharsets.US_ASCII);
        return firma.equals("%PDF-");
    }

    //Convierte el PDF vigente en Byte 
    public DefaultStreamedContent verDocumento(String tipoDocumento) {
        RnGcDocumentosLegalesTbl doc = ejbFacade.obtenerVigentePorTipo(tipoDocumento);
        if (doc == null || doc.getArchivoPdf() == null) {
            return null;
        }
        return new DefaultStreamedContent(new ByteArrayInputStream(doc.getArchivoPdf()), "application/pdf", doc.getNombreArchivo());
    }
}
