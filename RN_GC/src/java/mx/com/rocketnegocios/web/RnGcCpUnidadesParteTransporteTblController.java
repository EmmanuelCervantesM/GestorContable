package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcCpUnidadesParteTransporteTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcCpUnidadesParteTransporteTblFacade;

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

@Named("rnGcCpUnidadesParteTransporteTblController")
@SessionScoped
public class RnGcCpUnidadesParteTransporteTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcCpUnidadesParteTransporteTblFacade ejbFacade;
    private List<RnGcCpUnidadesParteTransporteTbl> items = null;
    private RnGcCpUnidadesParteTransporteTbl selected;

    public RnGcCpUnidadesParteTransporteTblController() {
    }

    public RnGcCpUnidadesParteTransporteTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcCpUnidadesParteTransporteTbl selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private RnGcCpUnidadesParteTransporteTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcCpUnidadesParteTransporteTbl prepareCreate() {
        selected = new RnGcCpUnidadesParteTransporteTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCpUnidadesParteTransporteTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCpUnidadesParteTransporteTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcCpUnidadesParteTransporteTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcCpUnidadesParteTransporteTbl> getItems() {
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

    public RnGcCpUnidadesParteTransporteTbl getRnGcCpUnidadesParteTransporteTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcCpUnidadesParteTransporteTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcCpUnidadesParteTransporteTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcCpUnidadesParteTransporteTbl.class)
    public static class RnGcCpUnidadesParteTransporteTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcCpUnidadesParteTransporteTblController controller = (RnGcCpUnidadesParteTransporteTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcCpUnidadesParteTransporteTblController");
            return controller.getRnGcCpUnidadesParteTransporteTbl(getKey(value));
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
            if (object instanceof RnGcCpUnidadesParteTransporteTbl) {
                RnGcCpUnidadesParteTransporteTbl o = (RnGcCpUnidadesParteTransporteTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcCpUnidadesParteTransporteTbl.class.getName()});
                return null;
            }
        }

    }

}
