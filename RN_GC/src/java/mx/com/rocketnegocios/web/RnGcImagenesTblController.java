package mx.com.rocketnegocios.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import mx.com.rocketnegocios.entities.RnGcImagenesTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcImagenesTblFacade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

@Named("rnGcImagenesTblController")
@SessionScoped
public class RnGcImagenesTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcImagenesTblFacade ejbFacade;
    private List<RnGcImagenesTbl> items = null;
    private RnGcImagenesTbl selected;
    private RnGcImagenesTbl rnGcImagenesTbl = new RnGcImagenesTbl();
    private UploadedFile file;
    private String RFC_Contribuyente = "";
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private int idUsuario = 0;

    public RnGcImagenesTblController() {
    }

    public RnGcImagenesTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcImagenesTbl selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private RnGcImagenesTblFacade getFacade() {
        return ejbFacade;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public RnGcImagenesTbl prepareCreate() {
        selected = new RnGcImagenesTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcImagenesTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcImagenesTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcImagenesTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcImagenesTbl> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        System.out.println("Entro al metodo Persist");
        if (file != null && !RFC_Contribuyente.isEmpty()) {
            setEmbeddableKeys();
            try {
                if (null == persistAction) {
                    rnGcImagenesTbl = new RnGcImagenesTbl();
                } else {
                    switch (persistAction) {
                        case CREATE:
                            try {
                                RnGcImagenesTbl rnGcImagenes = new RnGcImagenesTbl();
                                rnGcImagenes.setNombreImagen(file.getFileName());
                                rnGcImagenes.setRfc(RFC_Contribuyente);
                                rnGcImagenes.setFoto(file.getContents());
                                rnGcImagenes.setCreadoPor(idUsuario);
                                rnGcImagenes.setFechaCreacion(new Date());
                                rnGcImagenes.setUltimaActualizacionPor(idUsuario);
                                rnGcImagenes.setUltimaFechaActualizacion(new Date());
                                ejbFacade.edit(rnGcImagenes);
                                FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "La imagen " + file.getFileName() + " se subió con exito");
                                FacesContext.getCurrentInstance().addMessage(null, mensaje);
                            } catch (Exception e) {
                                FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo subir la imgen" + e.getMessage());
                                FacesContext.getCurrentInstance().addMessage(null, mensaje);
                                System.out.println(e.getMessage());
                            }
                            break;
                        case UPDATE:
                            try {
                                rnGcImagenesTbl.setNombreImagen(file.getFileName());
                                rnGcImagenesTbl.setRfc(RFC_Contribuyente);
                                rnGcImagenesTbl.setFoto(file.getContents());
                                rnGcImagenesTbl.setUltimaActualizacionPor(idUsuario);
                                rnGcImagenesTbl.setUltimaFechaActualizacion(new Date());
                                ejbFacade.edit(rnGcImagenesTbl);
                                FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "La imagen " + file.getFileName() + " se actualizó con exito.");
                                FacesContext.getCurrentInstance().addMessage(null, mensaje);
                            } catch (Exception e) {
                                FacesMessage mensaje = new FacesMessage("No se pudo subir la imgen" + e.getMessage());
                                FacesContext.getCurrentInstance().addMessage(null, mensaje);
                                System.out.println(e.getMessage());
                            }
                            break;
                        default:
                            rnGcImagenesTbl = new RnGcImagenesTbl();
                            break;
                    }
                }
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public RnGcImagenesTbl getRnGcImagenesTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcImagenesTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcImagenesTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcImagenesTbl.class)
    public static class RnGcImagenesTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcImagenesTblController controller = (RnGcImagenesTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcImagenesTblController");
            return controller.getRnGcImagenesTbl(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof RnGcImagenesTbl) {
                RnGcImagenesTbl o = (RnGcImagenesTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcImagenesTbl.class.getName()});
                return null;
            }
        }

    }

    public void subirImagen(String RFC) {
        System.out.println("Subir imagen");
        System.out.println("RFC:" + RFC);

        if (file != null && !RFC.isEmpty()) {
            RFC_Contribuyente = RFC;
            idUsuario = usuarioFirmado.obtenerIdUsuario();
            if (rnGcImagenesTbl.getRfc().equals("ADMINISTRADOR")) {
                persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcImagenesTblCreated"));
            } else {
                persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcImagenesTblUpdated"));
            }
        } else if (file == null) {
            FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El tipo de archivo no es valido");
            FacesContext.getCurrentInstance().addMessage(null, mensaje);
        } else {
            FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se puede obtener el RFC");
            FacesContext.getCurrentInstance().addMessage(null, mensaje);
        }
    }

    public DefaultStreamedContent byteToImage(String RFC) {
        System.out.println("Obtener imagen");
        System.out.println("RFC:" + RFC);
        ByteArrayInputStream img;
        if (!RFC.isEmpty()) {
            rnGcImagenesTbl = ejbFacade.obtenerImagenPorRFC(RFC);
            if (rnGcImagenesTbl != null) {
                System.out.println("Obteniendo Imagen  del RFC");
                System.out.println(rnGcImagenesTbl.getNombreImagen());
                
                byte[] imgBytes = rnGcImagenesTbl.getFoto();
                img = new ByteArrayInputStream(imgBytes);
            } else {
                System.out.println("El objeto es nulo");
                rnGcImagenesTbl = ejbFacade.obtenerImagenPorRFC("ADMINISTRADOR");
                byte[] imgBytes = rnGcImagenesTbl.getFoto();
                img = new ByteArrayInputStream(imgBytes);
            }
        } else {
            rnGcImagenesTbl = ejbFacade.obtenerImagenPorRFC("ADMINISTRADOR");
            byte[] imgBytes = rnGcImagenesTbl.getFoto();
            img = new ByteArrayInputStream(imgBytes);
        }
        return new DefaultStreamedContent(img, "image/png");
    }

}
