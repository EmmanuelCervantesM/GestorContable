package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcPerfilesTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcPerfilesTblFacade;

import java.io.Serializable;
import java.util.ArrayList;
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
import mx.com.rocketnegocios.beans.RnGcUsuariosPerfilesTblFacade;
import mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;

@Named("rnGcPerfilesTblController")
@SessionScoped
public class RnGcPerfilesTblController implements Serializable {

    @EJB
    private RnGcUsuariosPerfilesTblFacade usuarioPerfilFacade;
    @EJB
    private RnGcUsuariosTblFacade usuarioFacade;
    @EJB
    private RnGcPerfilesTblFacade perfilFacade;
    @EJB
    private mx.com.rocketnegocios.beans.RnGcPerfilesTblFacade ejbFacade;
    private List<RnGcPerfilesTbl> items = null;
    private RnGcPerfilesTbl selected;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private RnGcUsuariosTbl usuarioId;
    private ArrayList<String> listaTiposPerfiles = new ArrayList<>();
    private List<RnGcPerfilesTbl> listaPerfil = null;

    public List<RnGcPerfilesTbl> cargarPerfiles() {
        listaPerfil = null;
        usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());

        System.out.println("Entro a cargarPerfiles");
        List<RnGcPerfilesTbl> tipoPerfil = new ArrayList<>();
        System.out.println("El tipo de usuario es: " + usuarioId.getTipoUsuario());
        if(usuarioId.getTipoUsuario() != null){
            tipoPerfil = perfilFacade.listaTiposPerfilesPorUsuario2(usuarioId.getTipoUsuario());
            listaPerfil = tipoPerfil;
        }
        System.out.println("lista Perfil -------" + listaPerfil);
        return listaPerfil;
    }

    public RnGcPerfilesTblController() {
    }

    public RnGcPerfilesTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcPerfilesTbl selected) {
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

    private RnGcPerfilesTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcPerfilesTbl prepareCreate() {
        selected = new RnGcPerfilesTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcPerfilesTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcPerfilesTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcPerfilesTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcPerfilesTbl> getItems() {
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

    public RnGcPerfilesTbl getRnGcPerfilesTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcPerfilesTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcPerfilesTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcPerfilesTbl.class)
    public static class RnGcPerfilesTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcPerfilesTblController controller = (RnGcPerfilesTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcPerfilesTblController");
            return controller.getRnGcPerfilesTbl(getKey(value));
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
            if (object instanceof RnGcPerfilesTbl) {
                RnGcPerfilesTbl o = (RnGcPerfilesTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcPerfilesTbl.class.getName()});
                return null;
            }
        }

    }

    public List<RnGcPerfilesTbl> obtenerPerfilesPorUsuario() {
        List<RnGcPerfilesTbl> itemsUsuario = new ArrayList<>();
        if (usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")) {
            itemsUsuario = getFacade().findAll();
        } else if (usuarioFirmado.perfilUsuario().contains("DESPACHO")) {
            items = getFacade().findAll();
            for (int i = 1; i < items.size(); i++) {
                itemsUsuario.add(items.get(i));
            }
        }
        return itemsUsuario;
    }

}
