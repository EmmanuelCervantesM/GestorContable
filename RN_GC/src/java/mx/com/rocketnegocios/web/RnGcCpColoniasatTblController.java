package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcCpColoniasatTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcCpColoniasatTblFacade;

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
import mx.com.rocketnegocios.beans.RnGcCodigospostalesTblFacade;

@Named("rnGcCpColoniasatTblController")
@SessionScoped
public class RnGcCpColoniasatTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcCpColoniasatTblFacade ejbFacade;
    @EJB
    private RnGcCodigospostalesTblFacade codigosFacade;
    
    private List<RnGcCpColoniasatTbl> items = null;
    private RnGcCpColoniasatTbl selected;

    public RnGcCpColoniasatTblController() {
    }

    public RnGcCpColoniasatTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcCpColoniasatTbl selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private RnGcCpColoniasatTblFacade getFacade() {
        return ejbFacade;
    }
    
    public List<RnGcCpColoniasatTbl> obtenerXEstado(String estado) {
        String cpInicial = codigosFacade.buscarInicioPorEstado(estado);
        String cpFinal = codigosFacade.buscarFinPorEstado(estado);
        return getFacade().obtenerXcpFI(cpInicial,cpFinal);
    }
    
    public List<RnGcCpColoniasatTbl> obtenerCodigoPostal(String codigo) {
        return getFacade().obtenerXcodigo(codigo);
    }

    public RnGcCpColoniasatTbl prepareCreate() {
        selected = new RnGcCpColoniasatTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCpColoniasatTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCpColoniasatTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcCpColoniasatTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcCpColoniasatTbl> getItems() {
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

    public RnGcCpColoniasatTbl getRnGcCpColoniasatTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcCpColoniasatTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcCpColoniasatTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcCpColoniasatTbl.class)
    public static class RnGcCpColoniasatTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcCpColoniasatTblController controller = (RnGcCpColoniasatTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcCpColoniasatTblController");
            return controller.getRnGcCpColoniasatTbl(getKey(value));
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
            if (object instanceof RnGcCpColoniasatTbl) {
                RnGcCpColoniasatTbl o = (RnGcCpColoniasatTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcCpColoniasatTbl.class.getName()});
                return null;
            }
        }

    }

}
