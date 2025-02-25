package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcCpMaterialpeligrososatTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcCpMaterialpeligrososatTblFacade;

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

@Named("rnGcCpMaterialpeligrososatTblController")
@SessionScoped
public class RnGcCpMaterialpeligrososatTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcCpMaterialpeligrososatTblFacade ejbFacade;
    private List<RnGcCpMaterialpeligrososatTbl> items = null;
    private RnGcCpMaterialpeligrososatTbl selected;

    public RnGcCpMaterialpeligrososatTblController() {
    }

    public RnGcCpMaterialpeligrososatTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcCpMaterialpeligrososatTbl selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private RnGcCpMaterialpeligrososatTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcCpMaterialpeligrososatTbl prepareCreate() {
        selected = new RnGcCpMaterialpeligrososatTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCpMaterialpeligrososatTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCpMaterialpeligrososatTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcCpMaterialpeligrososatTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcCpMaterialpeligrososatTbl> getItems() {
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

    public RnGcCpMaterialpeligrososatTbl getRnGcCpMaterialpeligrososatTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcCpMaterialpeligrososatTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcCpMaterialpeligrososatTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcCpMaterialpeligrososatTbl.class)
    public static class RnGcCpMaterialpeligrososatTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcCpMaterialpeligrososatTblController controller = (RnGcCpMaterialpeligrososatTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcCpMaterialpeligrososatTblController");
            return controller.getRnGcCpMaterialpeligrososatTbl(getKey(value));
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
            if (object instanceof RnGcCpMaterialpeligrososatTbl) {
                RnGcCpMaterialpeligrososatTbl o = (RnGcCpMaterialpeligrososatTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcCpMaterialpeligrososatTbl.class.getName()});
                return null;
            }
        }

    }

}
