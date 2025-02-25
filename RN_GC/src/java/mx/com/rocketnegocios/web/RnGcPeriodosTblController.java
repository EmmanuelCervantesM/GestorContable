package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcPeriodosTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcPeriodosTblFacade;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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

@Named("rnGcPeriodosTblController")
@SessionScoped
public class RnGcPeriodosTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade usuarioFacade;
    
    @EJB
    private mx.com.rocketnegocios.beans.RnGcPeriodosTblFacade ejbFacade;
    private List<RnGcPeriodosTbl> items = null;
    private RnGcPeriodosTbl selected;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private Date año;

    public RnGcPeriodosTblController() {
    }

    public RnGcPeriodosTblFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(RnGcPeriodosTblFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public UsuarioFirmado getUsuarioFirmado() {
        return usuarioFirmado;
    }

    public void setUsuarioFirmado(UsuarioFirmado usuarioFirmado) {
        this.usuarioFirmado = usuarioFirmado;
    }

    public Date getAño() {
        return año;
    }

    public void setAño(Date año) {
        this.año = año;
    }

    public RnGcPeriodosTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcPeriodosTbl selected) {
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

    private RnGcPeriodosTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcPeriodosTbl prepareCreate() {
        selected = new RnGcPeriodosTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        RnGcUsuariosTbl user = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        List<RnGcPeriodosTbl> lista = getFacade().obtenerPeriodoIdDesc(user);
        if (lista != null && !lista.isEmpty()) {
            selected.setPeriodoId(lista.get(0).getPeriodoId() + 1);
        }else{
            selected.setPeriodoId(1);
        }
        String fech = new SimpleDateFormat("yyyy").format(año.getTime());
        selected.setAño(Integer.parseInt(fech));
        selected.setUsuariosId(user);
        selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        selected.setFechaCreacion(new Date());
        selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        selected.setUltimaFechaActualizacion(new Date());
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcPeriodosTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        selected.setUltimaFechaActualizacion(new Date());
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcPeriodosTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcPeriodosTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcPeriodosTbl> getItems() {
        if (items == null) {
            RnGcUsuariosTbl user = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            items = getFacade().obtenerPeriodoIdDesc(user);
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

    public RnGcPeriodosTbl getRnGcPeriodosTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcPeriodosTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcPeriodosTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    public List<RnGcPeriodosTbl> obtenerPeriodoUsuario() {
        RnGcUsuariosTbl user = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        List<RnGcPeriodosTbl> lista = getFacade().obtenerPeriodoIdDesc(user);
        return lista;
    }

    @FacesConverter(forClass = RnGcPeriodosTbl.class)
    public static class RnGcPeriodosTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcPeriodosTblController controller = (RnGcPeriodosTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcPeriodosTblController");
            return controller.getRnGcPeriodosTbl(getKey(value));
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
            if (object instanceof RnGcPeriodosTbl) {
                RnGcPeriodosTbl o = (RnGcPeriodosTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcPeriodosTbl.class.getName()});
                return null;
            }
        }

    }

}
