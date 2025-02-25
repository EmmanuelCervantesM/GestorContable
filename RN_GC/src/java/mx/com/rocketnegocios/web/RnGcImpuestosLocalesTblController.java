package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcImpuestosLocalesTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcImpuestosLocalesTblFacade;

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
import mx.com.rocketnegocios.util.UsuarioFirmado;

@Named("rnGcImpuestosLocalesTblController")
@SessionScoped
public class RnGcImpuestosLocalesTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcImpuestosLocalesTblFacade ejbFacade;
    private List<RnGcImpuestosLocalesTbl> items = null;
    private RnGcImpuestosLocalesTbl selected;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();

    public RnGcImpuestosLocalesTblController() {
    }

    public RnGcImpuestosLocalesTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcImpuestosLocalesTbl selected) {
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

    private RnGcImpuestosLocalesTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcImpuestosLocalesTbl prepareCreate() {
        selected = new RnGcImpuestosLocalesTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcImpuestosLocalesTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcImpuestosLocalesTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcImpuestosLocalesTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcImpuestosLocalesTbl> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
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

    public RnGcImpuestosLocalesTbl getRnGcImpuestosLocalesTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcImpuestosLocalesTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcImpuestosLocalesTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcImpuestosLocalesTbl.class)
    public static class RnGcImpuestosLocalesTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcImpuestosLocalesTblController controller = (RnGcImpuestosLocalesTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcImpuestosLocalesTblController");
            return controller.getRnGcImpuestosLocalesTbl(getKey(value));
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
            if (object instanceof RnGcImpuestosLocalesTbl) {
                RnGcImpuestosLocalesTbl o = (RnGcImpuestosLocalesTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcImpuestosLocalesTbl.class.getName()});
                return null;
            }
        }

    }
    
    public List<RnGcImpuestosLocalesTbl> obtenerImpLocalesXUsuario() {
        items = getFacade().obtenerImpLocalesXUsuario(usuarioFirmado.obtenerIdUsuario());
        return items;
    }

}
