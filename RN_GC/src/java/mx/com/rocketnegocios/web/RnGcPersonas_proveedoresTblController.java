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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import mx.com.rocketnegocios.beans.RnGcTipospersonasTblFacade;
import mx.com.rocketnegocios.entities.RnGcTipospersonasTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;

@ManagedBean(name = "rnGcPersonas_proveedoresTblController")
@SessionScoped
public class RnGcPersonas_proveedoresTblController implements Serializable {

    @EJB
    private RnGcTipospersonasTblFacade rnGcTipospersonasTblFacade;
    
    @EJB
    private mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade usuarioFacade;
        
    @EJB
    private mx.com.rocketnegocios.beans.RnGcPersonasTblFacade ejbFacade;
    private List<RnGcPersonasTbl> items = null;
    private List<RnGcPersonasTbl> itemsProveedor = null;
    private RnGcPersonasTbl selected;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();

    public RnGcPersonas_proveedoresTblController() {
    }

    public RnGcPersonasTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcPersonasTbl selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        RnGcTipospersonasTbl tipoPersonaId = rnGcTipospersonasTblFacade.obtenerTipoPersona("Proveedor");        
        selected.setTipoPersonaId(tipoPersonaId);
        selected.setTipoPersona("Proveedor");
        selected.setUsuarioId(usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario()));
        selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        selected.setUltimaFechaActualizacion(new Date());     
    }

    protected void initializeEmbeddableKey() {
        selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        selected.setFechaCreacion(new Date());
    }

    private RnGcPersonasTblFacade getFacade() {
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
            itemsProveedor = null;
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
            itemsProveedor = null;
        }
    }

    public List<RnGcPersonasTbl> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public List<RnGcPersonasTbl> getItemsProveedor() {
        if(itemsProveedor == null) {
            itemsProveedor = getFacade().obtenerPersonasPorTipo("Proveedor");
        }
        return itemsProveedor;
    }

    public void setItemsProveedor(List<RnGcPersonasTbl> itemsProveedor) {
        this.itemsProveedor = itemsProveedor;
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
            return controller.getFacade().find(getKey(value));
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
            
}
