package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcPersonasTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcPersonasTblFacade;

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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;

@Named("rnGcPersonasTblController")
@SessionScoped
public class RnGcPersonasTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcPersonasTblFacade ejbFacade;
    private List<RnGcPersonasTbl> items = null;
    private RnGcPersonasTbl selected;
    private RnGcUsuariosTbl usuarioId;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();

    public RnGcPersonasTblController() {
    }

    public RnGcPersonasTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcPersonasTbl selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        selected.setUltimaFechaActualizacion(new Date());
    }

    protected void initializeEmbeddableKey() {
        selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        selected.setFechaCreacion(new Date());
    }

    RnGcPersonasTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcPersonasTbl prepareCreate() {
        selected = new RnGcPersonasTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcPersonasTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcPersonasTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcPersonasTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcPersonasTbl> getItems() {
        /*if (items == null) {
            items = getFacade().findAll();
        }*/
        items = getFacade().findAll();
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
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

    public RnGcPersonasTbl getRnGcPersonasTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcPersonasTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcPersonasTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcPersonasTbl.class)
    public static class RnGcPersonasTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcPersonasTblController controller = (RnGcPersonasTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcPersonasTblController");
            return controller.getRnGcPersonasTbl(getKey(value));
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
            if (object instanceof RnGcPersonasTbl) {
                RnGcPersonasTbl o = (RnGcPersonasTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcPersonasTbl.class.getName()});
                return null;
            }
        }

    }

    public List<RnGcPersonasTbl> itemCreadoPor() {
        if (usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")) {
            items = getFacade().findAll();
        } else {
            items = getFacade().obtenerCreadoPor(usuarioFirmado.obtenerIdUsuario());
        }
        return items;
    }

}
