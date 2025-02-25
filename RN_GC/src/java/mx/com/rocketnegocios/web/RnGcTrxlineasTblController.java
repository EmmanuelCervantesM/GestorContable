package mx.com.rocketnegocios.web;

import java.io.File;
import java.io.IOException;
import mx.com.rocketnegocios.entities.RnGcTrxlineasTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcTrxlineasTblFacade;

import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import mx.com.rocketnegocios.beans.RnGcTransaccionesTblFacade;
import mx.com.rocketnegocios.entities.RnGcTransaccionesTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;

@Named("rnGcTrxlineasTblController")
@SessionScoped
public class RnGcTrxlineasTblController implements Serializable {

    @EJB
    private RnGcTransaccionesTblFacade rnGcTransaccionesTblFacade;

    @EJB
    private mx.com.rocketnegocios.beans.RnGcTrxlineasTblFacade ejbFacade;
    private List<RnGcTrxlineasTbl> items = null;
    private RnGcTrxlineasTbl selected;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();

    private RnGcTransaccionesTblController transaccionesController;
    private List<RnGcTrxlineasTbl> itemsTrxLineas = null;

    public RnGcTrxlineasTblController() {
    }

    public RnGcTrxlineasTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcTrxlineasTbl selected) {
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

    private RnGcTrxlineasTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcTrxlineasTbl prepareCreate() {
        selected = new RnGcTrxlineasTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcTrxlineasTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcTrxlineasTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcTrxlineasTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcTrxlineasTbl> getItems() {
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
                Logger.getLogger(selected.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public RnGcTrxlineasTbl getRnGcTrxlineasTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcTrxlineasTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcTrxlineasTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcTrxlineasTbl.class)
    public static class RnGcTrxlineasTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcTrxlineasTblController controller = (RnGcTrxlineasTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcTrxlineasTblController");
            return controller.getRnGcTrxlineasTbl(getKey(value));
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
            if (object instanceof RnGcTrxlineasTbl) {
                RnGcTrxlineasTbl o = (RnGcTrxlineasTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcTrxlineasTbl.class.getName()});
                return null;
            }
        }

    }

    public RnGcTransaccionesTbl getRnGcTransaccionesTbl(java.lang.Integer id) {
        return rnGcTransaccionesTblFacade.find(id);
    }

    public void prepareTrxLines(RnGcTransaccionesTbl transaccionId) {
        if (transaccionId != null) {
            itemsTrxLineas = getFacade().obtenerTrxLineas(transaccionId);
        }
    }

    public List<RnGcTrxlineasTbl> getItemsTrxLineas() {
        return itemsTrxLineas;
    }

    public void setItemsTrxLineas(List<RnGcTrxlineasTbl> itemsTrxLineas) {
        this.itemsTrxLineas = itemsTrxLineas;
    }

    public static String[] getFiles(String dir_path) {
        String[] arr_res = null;
        File xml = new File(dir_path);
        if (xml.isDirectory()) {
            List<String> res = new ArrayList<>();
            File[] arr_content = xml.listFiles();
            for (int i = 0; i < arr_content.length; i++) {
                if (arr_content[i].isFile()) {
                    res.add(arr_content[i].toString());
                }
            }
            arr_res = res.toArray(new String[0]);
        } else {
            System.err.println("Directorio no válido");
        }
        return arr_res;
    }    
}
