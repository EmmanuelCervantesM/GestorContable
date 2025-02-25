package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcCfdisTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcCfdisTblFacade;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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

@Named("rnGcCfdis_facturasTblController")
@SessionScoped
public class RnGcCfdis_facturasTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcCfdisTblFacade ejbFacade;
    
    @EJB
    private mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade usuariosFacade;
    
    private List<RnGcCfdisTbl> items = null;
    private List<RnGcCfdisTbl> itemsFacturas = null;
    private RnGcCfdisTbl selected;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private List<RnGcCfdisTbl> itemsEmitidos = null;
    private RnGcUsuariosTbl usuarioId;
    private List<RnGcCfdisTbl> filteredItems;

    public RnGcCfdis_facturasTblController() {
    }

    public RnGcCfdisTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcCfdisTbl selected) {
        this.selected = selected;
    }

    public List<RnGcCfdisTbl> getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(List<RnGcCfdisTbl> filteredItems) {
        this.filteredItems = filteredItems;
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
            itemsFacturas = null;
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
            itemsFacturas = null;
        }
    }

    public List<RnGcCfdisTbl> getItems() {
        //if (items == null) {
        items = getFacade().findAll();
        //}
        return items;
    }

    public List<RnGcCfdisTbl> getItemsFacturas() {
        if (itemsFacturas == null) {
            itemsFacturas = getFacade().obtenerTipoComprobante("E");
        }
        return itemsFacturas;
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
    
    public List<RnGcCfdisTbl> egresosCreadoPor() {
        if(usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")){
            itemsFacturas = getFacade().obtenerTipoComprobante("E");
        }else {
            itemsFacturas = getFacade().tipoComprobanteCreadoPor("E", usuarioFirmado.obtenerIdUsuario());
        }
        return itemsFacturas;
    }
    
    public boolean filterByFecha(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.isEmpty()) {
            return true;
        }
        if (value == null) {
            return false;
        }

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Date filterDate = (Date) value;
        Date dateFrom;
        Date dateTo;

        try {
            String fromPart = filterText.substring(0, filterText.indexOf("-"));
            String toPart = filterText.substring(filterText.indexOf("-") + 1);
            dateFrom = fromPart.isEmpty() ? null : df.parse(fromPart);
            dateTo = toPart.isEmpty() ? null : df.parse(toPart);
        } catch (ParseException ex) {
            Logger.getLogger(RnGcCfdisTblController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return (dateFrom == null || filterDate.after(dateFrom)) && (dateTo == null || filterDate.before(dateTo));
    }

    public List<RnGcCfdisTbl> obtenerEmitidos() {
        if (itemsEmitidos == null) {
            try {
                usuarioId = usuariosFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
                if (usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")) {
                    itemsEmitidos = getFacade().findAll();
                } else {
                    itemsEmitidos = getFacade().obtenerEmitidos(usuarioId.getRfc());
                }
            } catch (ClassFormatError ex) {
                ex.printStackTrace();
            }
        }
        return itemsEmitidos;
    }
}
