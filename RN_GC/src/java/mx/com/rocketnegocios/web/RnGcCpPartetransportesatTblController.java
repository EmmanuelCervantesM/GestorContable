package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcCpPartetransportesatTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcCpPartetransportesatTblFacade;

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

@Named("rnGcCpPartetransportesatTblController")
@SessionScoped
public class RnGcCpPartetransportesatTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcCpPartetransportesatTblFacade ejbFacade;
    private List<RnGcCpPartetransportesatTbl> items = null;
    private RnGcCpPartetransportesatTbl selected;

    public RnGcCpPartetransportesatTblController() {
    }

    public RnGcCpPartetransportesatTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcCpPartetransportesatTbl selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private RnGcCpPartetransportesatTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcCpPartetransportesatTbl prepareCreate() {
        selected = new RnGcCpPartetransportesatTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCpPartetransportesatTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCpPartetransportesatTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcCpPartetransportesatTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcCpPartetransportesatTbl> getItems() {
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

    public RnGcCpPartetransportesatTbl getRnGcCpPartetransportesatTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcCpPartetransportesatTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcCpPartetransportesatTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcCpPartetransportesatTbl.class)
    public static class RnGcCpPartetransportesatTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcCpPartetransportesatTblController controller = (RnGcCpPartetransportesatTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcCpPartetransportesatTblController");
            return controller.getRnGcCpPartetransportesatTbl(getKey(value));
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
            if (object instanceof RnGcCpPartetransportesatTbl) {
                RnGcCpPartetransportesatTbl o = (RnGcCpPartetransportesatTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcCpPartetransportesatTbl.class.getName()});
                return null;
            }
        }

    }

}
