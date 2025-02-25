package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcCpPaissatTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcCpPaissatTblFacade;

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

@Named("rnGcCpPaissatTblController")
@SessionScoped
public class RnGcCpPaissatTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcCpPaissatTblFacade ejbFacade;
    private List<RnGcCpPaissatTbl> items = null;
    private RnGcCpPaissatTbl selected;

    public RnGcCpPaissatTblController() {
    }

    public RnGcCpPaissatTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcCpPaissatTbl selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private RnGcCpPaissatTblFacade getFacade() {
        return ejbFacade;
    }

    public List<RnGcCpPaissatTbl> obtenerPais() {
        return getFacade().obtenerXTipo("pais");
    }
    
    public List<RnGcCpPaissatTbl> obtenerEstados() {
        return getFacade().obtenerXPais("mex");
    }
    
    public List<RnGcCpPaissatTbl> obtenerMunicipios(String estado) {
        return getFacade().obtenerXEstado(estado);
    }
    
    public RnGcCpPaissatTbl prepareCreate() {
        selected = new RnGcCpPaissatTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCpPaissatTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCpPaissatTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcCpPaissatTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcCpPaissatTbl> getItems() {
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

    public RnGcCpPaissatTbl getRnGcCpPaissatTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcCpPaissatTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcCpPaissatTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcCpPaissatTbl.class)
    public static class RnGcCpPaissatTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcCpPaissatTblController controller = (RnGcCpPaissatTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcCpPaissatTblController");
            return controller.getRnGcCpPaissatTbl(getKey(value));
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
            if (object instanceof RnGcCpPaissatTbl) {
                RnGcCpPaissatTbl o = (RnGcCpPaissatTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcCpPaissatTbl.class.getName()});
                return null;
            }
        }

    }

}
