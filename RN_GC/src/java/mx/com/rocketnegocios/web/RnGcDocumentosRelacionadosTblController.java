package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcDocumentosRelacionadosTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcDocumentosRelacionadosTblFacade;

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

@Named("rnGcDocumentosRelacionadosTblController")
@SessionScoped
public class RnGcDocumentosRelacionadosTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcDocumentosRelacionadosTblFacade ejbFacade;
    private List<RnGcDocumentosRelacionadosTbl> items = null;
    private RnGcDocumentosRelacionadosTbl selected;

    public RnGcDocumentosRelacionadosTblController() {
    }

    public RnGcDocumentosRelacionadosTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcDocumentosRelacionadosTbl selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private RnGcDocumentosRelacionadosTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcDocumentosRelacionadosTbl prepareCreate() {
        selected = new RnGcDocumentosRelacionadosTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcDocumentosRelacionadosTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcDocumentosRelacionadosTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcDocumentosRelacionadosTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcDocumentosRelacionadosTbl> getItems() {
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

    public RnGcDocumentosRelacionadosTbl getRnGcDocumentosRelacionadosTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcDocumentosRelacionadosTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcDocumentosRelacionadosTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcDocumentosRelacionadosTbl.class)
    public static class RnGcDocumentosRelacionadosTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcDocumentosRelacionadosTblController controller = (RnGcDocumentosRelacionadosTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcDocumentosRelacionadosTblController");
            return controller.getRnGcDocumentosRelacionadosTbl(getKey(value));
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
            if (object instanceof RnGcDocumentosRelacionadosTbl) {
                RnGcDocumentosRelacionadosTbl o = (RnGcDocumentosRelacionadosTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcDocumentosRelacionadosTbl.class.getName()});
                return null;
            }
        }

    }

}
