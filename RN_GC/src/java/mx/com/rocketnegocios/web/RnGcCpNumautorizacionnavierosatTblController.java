package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcCpNumautorizacionnavierosatTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcCpNumautorizacionnavierosatTblFacade;

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

@Named("rnGcCpNumautorizacionnavierosatTblController")
@SessionScoped
public class RnGcCpNumautorizacionnavierosatTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcCpNumautorizacionnavierosatTblFacade ejbFacade;
    private List<RnGcCpNumautorizacionnavierosatTbl> items = null;
    private RnGcCpNumautorizacionnavierosatTbl selected;

    public RnGcCpNumautorizacionnavierosatTblController() {
    }

    public RnGcCpNumautorizacionnavierosatTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcCpNumautorizacionnavierosatTbl selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private RnGcCpNumautorizacionnavierosatTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcCpNumautorizacionnavierosatTbl prepareCreate() {
        selected = new RnGcCpNumautorizacionnavierosatTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCpNumautorizacionnavierosatTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCpNumautorizacionnavierosatTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcCpNumautorizacionnavierosatTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcCpNumautorizacionnavierosatTbl> getItems() {
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

    public RnGcCpNumautorizacionnavierosatTbl getRnGcCpNumautorizacionnavierosatTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcCpNumautorizacionnavierosatTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcCpNumautorizacionnavierosatTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcCpNumautorizacionnavierosatTbl.class)
    public static class RnGcCpNumautorizacionnavierosatTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcCpNumautorizacionnavierosatTblController controller = (RnGcCpNumautorizacionnavierosatTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcCpNumautorizacionnavierosatTblController");
            return controller.getRnGcCpNumautorizacionnavierosatTbl(getKey(value));
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
            if (object instanceof RnGcCpNumautorizacionnavierosatTbl) {
                RnGcCpNumautorizacionnavierosatTbl o = (RnGcCpNumautorizacionnavierosatTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcCpNumautorizacionnavierosatTbl.class.getName()});
                return null;
            }
        }

    }

}
