package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcCodigospostalesTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcCodigospostalesTblFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import mx.com.rocketnegocios.util.UsuarioFirmado;

@Named("rnGcCodigospostalesTblController")
@SessionScoped
public class RnGcCodigospostalesTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcCodigospostalesTblFacade ejbFacade;
    private List<RnGcCodigospostalesTbl> items = null;
    private RnGcCodigospostalesTbl selected;
    private List<RnGcCodigospostalesTbl> listaCodigos = null;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();

    public RnGcCodigospostalesTblController() {
    }

    public RnGcCodigospostalesTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcCodigospostalesTbl selected) {
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

    private RnGcCodigospostalesTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcCodigospostalesTbl prepareCreate() {
        selected = new RnGcCodigospostalesTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCodigospostalesTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCodigospostalesTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcCodigospostalesTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcCodigospostalesTbl> getItems() {
        items = getFacade().findAll();
        return items;
    }

    public List<RnGcCodigospostalesTbl> busquedaporCodigo(double codigo) {
        int codigoPostal = (int) codigo;
        listaCodigos = getFacade().buscarporCodigo(codigoPostal);

        return listaCodigos;
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

    public RnGcCodigospostalesTbl getRnGcCodigospostalesTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcCodigospostalesTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcCodigospostalesTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public List<String> itemsSinRepetir() {
        List<String> estados = Arrays.asList("AGU", "BCN", "BCS", "CAM", "CHH", "CHP", "COA", "COL",
                "CMX", "DUR", "GRO", "GUA", "HID", "JAL", "MEX", "MIC", "MOR", "NAY", "NLE", "OAX",
                "PUE", "QUE", "ROO", "SIN", "SLP", "SON", "TAB", "TAM", "TLA", "VER", "YUC", "ZAC");
        return estados;
    }

    @FacesConverter(forClass = RnGcCodigospostalesTbl.class)
    public static class RnGcCodigospostalesTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcCodigospostalesTblController controller = (RnGcCodigospostalesTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcCodigospostalesTblController");
            return controller.getRnGcCodigospostalesTbl(getKey(value));
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
            if (object instanceof RnGcCodigospostalesTbl) {
                RnGcCodigospostalesTbl o = (RnGcCodigospostalesTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcCodigospostalesTbl.class.getName()});
                return null;
            }
        }

    }

    public List<RnGcCodigospostalesTbl> obtenerEstadoporCodigo(int codigoPostal) {
        listaCodigos = getFacade().buscarporCodigo(codigoPostal);
        return listaCodigos;
    }

    public List<RnGcCodigospostalesTbl> obetenerCodigosPorEstado(String estado) {
        listaCodigos = getFacade().buscarPorEstado(estado);
        return listaCodigos;
    }

}
