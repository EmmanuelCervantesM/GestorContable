package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcProductserviciosTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcProductserviciosTblFacade;

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
import mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;

@Named("rnGcProductserviciosTblController")
@SessionScoped
public class RnGcProductserviciosTblController implements Serializable {
    
    @EJB
    private mx.com.rocketnegocios.beans.RnGcProductserviciosTblFacade ejbFacade;
    private mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade usuariosFacade;
    private List<RnGcProductserviciosTbl> items = null;
    private RnGcProductserviciosTbl selected;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private List<RnGcProductserviciosTbl> itemsProdServ = null;
    private RnGcUsuariosTbl usuarioVar;
    
    public RnGcProductserviciosTblController() {
    }
    
    public RnGcProductserviciosTbl getSelected() {
        return selected;
    }
    
    public void setSelected(RnGcProductserviciosTbl selected) {
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
    
    private RnGcProductserviciosTblFacade getFacade() {
        return ejbFacade;
    }
    
    public RnGcProductserviciosTbl prepareCreate() {
        System.out.println("prepareCreate");
        selected = new RnGcProductserviciosTbl();
        initializeEmbeddableKey();
        return selected;
    }
    
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcProductserviciosTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcProductserviciosTblUpdated"));
    }
    
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcProductserviciosTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public List<RnGcProductserviciosTbl> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    
    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            System.out.println(selected.getTipoProdServ() + " | " + selected.getClaveProductServ() + " | "
                    + selected.getNoIdentificacion() + " | " + selected.getClaveUnidad() + " | "
                    + selected.getUnidad() + " | " + selected.getDescripcion() + " | "
                    + selected.getValorunitario() + " | " + selected.getTipoImpuesto() + " | "
                    + selected.getImpuesto() + " | " + selected.getTipofactor() + " | "
                    + selected.getTipoTasa());
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
    
    public RnGcProductserviciosTbl getRnGcProductserviciosTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }
    
    public List<RnGcProductserviciosTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }
    
    public List<RnGcProductserviciosTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    @FacesConverter(forClass = RnGcProductserviciosTbl.class)
    public static class RnGcProductserviciosTblControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcProductserviciosTblController controller = (RnGcProductserviciosTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcProductserviciosTblController");
            return controller.getRnGcProductserviciosTbl(getKey(value));
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
            if (object instanceof RnGcProductserviciosTbl) {
                RnGcProductserviciosTbl o = (RnGcProductserviciosTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcProductserviciosTbl.class.getName()});
                return null;
            }
        }
        
    }

    public RnGcUsuariosTblFacade getUsuariosFacade() {
        return usuariosFacade;
    }

    public void setUsuariosFacade(RnGcUsuariosTblFacade usuariosFacade) {
        this.usuariosFacade = usuariosFacade;
    }    
    
    public List<RnGcProductserviciosTbl> itemscreadoPor(){
        if(usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")){
            itemsProdServ = getFacade().findAll();
        }else{
            itemsProdServ = getFacade().obtenerPorUsuario(usuarioFirmado.obtenerIdUsuario());
        }
        
        return itemsProdServ;
    }
    
}
