package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcCfdisTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcCfdisTblFacade;

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
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;

@Named("rnGcCfdis_transaccionesTblController")
@SessionScoped
public class RnGcCfdis_transaccionesTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcCfdisTblFacade ejbFacade;
    
    @EJB
    private mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade usuariosFacade;
    
    private List<RnGcCfdisTbl> items = null;
    private List<RnGcCfdisTbl> itemsTransacciones = null;
    private RnGcCfdisTbl selected;
    private RnGcUsuariosTbl usuarioId;
    private List<RnGcUsuariosTbl> listaUsuarios;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();

    public RnGcCfdis_transaccionesTblController() {
    }

    public RnGcCfdisTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcCfdisTbl selected) {
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

    private RnGcCfdisTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcCfdisTbl prepareCreate() {
        selected = new RnGcCfdisTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCfdisTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            itemsTransacciones = null;
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCfdisTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcCfdisTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
            itemsTransacciones = null;
        }
    }

    public List<RnGcCfdisTbl> getItems() {
        //if (items == null) {
        items = getFacade().findAll();
        //}
        return items;
    }

    public List<RnGcCfdisTbl> getItemsTransacciones() {
        if (itemsTransacciones == null) {
            itemsTransacciones = getFacade().obtenerTipoComprobante("I");
        }
        return itemsTransacciones;
    }

    public void setItemsTransacciones(List<RnGcCfdisTbl> itemsTransacciones) {
        this.itemsTransacciones = itemsTransacciones;
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

    public RnGcCfdisTbl getRnGcCfdisTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcCfdisTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcCfdisTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcCfdisTbl.class)
    public static class RnGcCfdisTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcCfdisTblController controller = (RnGcCfdisTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcCfdisTblController");
            return controller.getRnGcCfdisTbl(getKey(value));
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
            if (object instanceof RnGcCfdisTbl) {
                RnGcCfdisTbl o = (RnGcCfdisTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcCfdisTbl.class.getName()});
                return null;
            }
        }

    }
    
    public List<RnGcCfdisTbl> ingresosCreadoPor(){
        if(usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")){
            itemsTransacciones = getFacade().obtenerTipoComprobante("I");
        } else {
            itemsTransacciones = getFacade().tipoComprobanteCreadoPor("I", usuarioFirmado.obtenerIdUsuario());
        }
        return itemsTransacciones;
    }

}
