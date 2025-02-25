package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcDireccionesTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcDireccionesTblFacade;

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
import mx.com.rocketnegocios.beans.RnGcPersonasTblFacade;
import mx.com.rocketnegocios.entities.RnGcPersonasTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;

@Named("rnGcDireccionesTblController")
@SessionScoped
public class RnGcDireccionesTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcDireccionesTblFacade ejbFacade;

    @EJB
    private RnGcPersonasTblFacade personasFacade;

    private List<RnGcDireccionesTbl> items = null;
    private RnGcDireccionesTbl selected;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();

    private List<RnGcDireccionesTbl> itemsDirecciones = null;
    private RnGcPersonasTbl personaId = null;
    private List<RnGcPersonasTbl> listaPersona = null;

    public RnGcDireccionesTblController() {
    }

    public RnGcDireccionesTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcDireccionesTbl selected) {
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

    private RnGcDireccionesTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcDireccionesTbl prepareCreate() {
        System.out.println("prepareCreate() Direcciones");
        selected = new RnGcDireccionesTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        System.out.println("create() Direcciones");
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcDireccionesTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcDireccionesTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcDireccionesTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcDireccionesTbl> getItems() {
        /*if (items == null) {
            items = getFacade().findAll();
        }*/
        items = getFacade().findAll();
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

    public RnGcDireccionesTbl getRnGcDireccionesTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcDireccionesTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcDireccionesTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcDireccionesTbl.class)
    public static class RnGcDireccionesTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcDireccionesTblController controller = (RnGcDireccionesTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcDireccionesTblController");
            return controller.getRnGcDireccionesTbl(getKey(value));
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
            if (object instanceof RnGcDireccionesTbl) {
                RnGcDireccionesTbl o = (RnGcDireccionesTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcDireccionesTbl.class.getName()});
                return null;
            }
        }

    }

    public List<RnGcDireccionesTbl> getItemsPersonas() {
        this.itemsDirecciones = getFacade().obtenerDireccionesPorPersona(personaId);
        System.out.println("itemsDirecciones: " + itemsDirecciones);
        return itemsDirecciones;
    }

    public void prepararItemsPersona(RnGcPersonasTbl personaId) {
        this.personaId = personaId;
        System.out.println("personaId: " + personaId);
    }

    public String obtenerSucursales(Integer Id) {
        System.out.println("Id: " + Id);
        personaId = personasFacade.obtenerPersona(Id);
        itemsDirecciones = getFacade().obtenerDireccionesPorPersona(personaId);
        if (itemsDirecciones.size() > 0) {
            return "El cliente " + personaId.getNombre() + " tiene " + itemsDirecciones.size() + " sucursal registradas";
        } else {
            return "El cliente " + personaId.getNombre() + " no tiene sucursales registradas";
        }
    }

    public List<RnGcDireccionesTbl> getItemsSelectOne(RnGcPersonasTbl personaId) {
        return getFacade().obtenerDireccionesPorPersona(personaId);
    }
}
