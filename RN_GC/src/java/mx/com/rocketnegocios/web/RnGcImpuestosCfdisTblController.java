package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcImpuestosCfdisTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcImpuestosCfdisTblFacade;

import java.io.Serializable;
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

@Named("rnGcImpuestosCfdisTblController")
@SessionScoped
public class RnGcImpuestosCfdisTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcImpuestosCfdisTblFacade ejbFacade;
    private List<RnGcImpuestosCfdisTbl> items = null;
    private RnGcImpuestosCfdisTbl selected;

    public RnGcImpuestosCfdisTblController() {
    }

    public RnGcImpuestosCfdisTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcImpuestosCfdisTbl selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private RnGcImpuestosCfdisTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcImpuestosCfdisTbl prepareCreate() {
        selected = new RnGcImpuestosCfdisTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcImpuestosCfdisTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcImpuestosCfdisTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcImpuestosCfdisTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcImpuestosCfdisTbl> getItems() {
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

    public RnGcImpuestosCfdisTbl getRnGcImpuestosCfdisTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcImpuestosCfdisTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcImpuestosCfdisTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcImpuestosCfdisTbl.class)
    public static class RnGcImpuestosCfdisTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcImpuestosCfdisTblController controller = (RnGcImpuestosCfdisTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcImpuestosCfdisTblController");
            return controller.getRnGcImpuestosCfdisTbl(getKey(value));
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
            if (object instanceof RnGcImpuestosCfdisTbl) {
                RnGcImpuestosCfdisTbl o = (RnGcImpuestosCfdisTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcImpuestosCfdisTbl.class.getName()});
                return null;
            }
        }

    }

}
