/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.web;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import mx.com.rocketnegocios.beans.RnGcNomTiporegimencontratacionTblFacade;
import mx.com.rocketnegocios.entities.RnGcNomTiporegimencontratacionTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import mx.com.rocketnegocios.web.util.JsfUtil;


/**
 *
 * @author Joaquin
 */
@Named("rnGcNomTiporegimencontratacionTblController")
@SessionScoped
public class RnGcNomTiporegimencontratacionTblController implements Serializable{
    
    @EJB
    private mx.com.rocketnegocios.beans.RnGcNomTiporegimencontratacionTblFacade ejbFacade;
    private List<RnGcNomTiporegimencontratacionTbl> items = null;
    private RnGcNomTiporegimencontratacionTbl selected;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    
     public RnGcNomTiporegimencontratacionTblController() {
    }

    public RnGcNomTiporegimencontratacionTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcNomTiporegimencontratacionTbl selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        selected.setUltimaFechaActualizacion(new Date());
    }

    protected void initializeEmbeddableKey() {
        selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        selected.setFechaCreacion(new Date());
    }

    private RnGcNomTiporegimencontratacionTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcNomTiporegimencontratacionTbl prepareCreate() {
        selected = new RnGcNomTiporegimencontratacionTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcNomTiporegimenTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcNomTiporegimenTblUpdated"));
    }

    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcNomTiporegimenTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcNomTiporegimencontratacionTbl> getItems() {
        items = getFacade().findAll();
        return items;
    }

    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
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

    public RnGcNomTiporegimencontratacionTbl getRnGcNomTiporegimencontratacionTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcNomTiporegimencontratacionTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcNomTiporegimencontratacionTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcNomTiporegimencontratacionTbl.class)
    public static class RnGcNomTiporegimenTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcNomTiporegimencontratacionTblController controller = (RnGcNomTiporegimencontratacionTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcNomTiporegimencontratacionTblController");
            return controller.getRnGcNomTiporegimencontratacionTbl(getKey(value));
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
            if (object instanceof RnGcNomTiporegimencontratacionTbl) {
                RnGcNomTiporegimencontratacionTbl o = (RnGcNomTiporegimencontratacionTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcNomTiporegimencontratacionTbl.class.getName()});
                return null;
            }
        }

    }
}
