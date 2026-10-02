package mx.com.rocketnegocios.web;

import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import mx.com.rocketnegocios.beans.RnGcTerminosCondicionesTblFacade;
import mx.com.rocketnegocios.entities.RnGcTerminosCondicionesTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;

//controlador para los temrinos y condiciones y que el adminsitrador pueda modificarlos y publicar nuevas versiones.
@Named("rnGcTerminosCondicionesTblController")
@SessionScoped
public class RnGcTerminosCondicionesTblController implements Serializable {

    @EJB
    private RnGcTerminosCondicionesTblFacade rnGcTerminosCondicionesTblFacade;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private String version;
    private String contenido;
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public boolean isEsAdministrador() {
        String listaPerfiles = String.valueOf(
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listaPerfiles"));
        return listaPerfiles != null && listaPerfiles.contains("ADMINISTRADOR");
    }

    // Carga el formulario para que no se empieze desde 0 
    public void cargarVigente() {
        RnGcTerminosCondicionesTbl vigente = rnGcTerminosCondicionesTblFacade.obtenerVigente();
        if (vigente != null) {
            version = vigente.getVersion();
            contenido = vigente.getContenido();
        }
    }

    public void publicarNuevaVersion() {
        if (!isEsAdministrador()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Solo un administrador puede publicar los Terminos y Condiciones"));
            return;
        }
        if (version == null || version.isEmpty() || contenido == null || contenido.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Completa la version y el contenido", ""));
            return;
        }

        try {
            rnGcTerminosCondicionesTblFacade.marcarNoVigentes();
            RnGcTerminosCondicionesTbl nuevo = new RnGcTerminosCondicionesTbl();
            nuevo.setVersion(version);
            nuevo.setContenido(contenido);
            nuevo.setVigente("S");
            nuevo.setFechaPublicacion(new Date());
            nuevo.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
            nuevo.setFechaCreacion(new Date());
            rnGcTerminosCondicionesTblFacade.create(nuevo);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Nueva version de Terminos y Condiciones publicada"));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo publicar: " + e.getMessage()));
        }
    }
}
