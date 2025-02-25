package mx.com.rocketnegocios.web;

import java.io.File;
import java.io.IOException;
import mx.com.rocketnegocios.entities.RnGcFacturaslineasTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcFacturaslineasTblFacade;

import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import mx.com.rocketnegocios.beans.RnGcFacturasTblFacade;
import mx.com.rocketnegocios.entities.RnGcFacturasTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

@Named("rnGcFacturaslineasTblController")
@SessionScoped
public class RnGcFacturaslineasTblController implements Serializable {

    @EJB
    private RnGcFacturasTblFacade rnGcFacturasTblFacade;

    @EJB
    private mx.com.rocketnegocios.beans.RnGcFacturaslineasTblFacade ejbFacade;
    private List<RnGcFacturaslineasTbl> items = null;
    private RnGcFacturaslineasTbl selected;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();

    //private RnGcFacturasTblController facturasController;
    private List<RnGcFacturaslineasTbl> itemsFactLineas = null;

    public RnGcFacturaslineasTblController() {
    }

    public RnGcFacturaslineasTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcFacturaslineasTbl selected) {
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

    private RnGcFacturaslineasTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcFacturaslineasTbl prepareCreate() {
        selected = new RnGcFacturaslineasTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcFacturaslineasTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcFacturaslineasTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcFacturaslineasTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcFacturaslineasTbl> getItems() {
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

    public RnGcFacturaslineasTbl getRnGcFacturaslineasTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcFacturaslineasTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcFacturaslineasTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcFacturaslineasTbl.class)
    public static class RnGcFacturaslineasTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcFacturaslineasTblController controller = (RnGcFacturaslineasTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcFacturaslineasTblController");
            return controller.getRnGcFacturaslineasTbl(getKey(value));
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
            if (object instanceof RnGcFacturaslineasTbl) {
                RnGcFacturaslineasTbl o = (RnGcFacturaslineasTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcFacturaslineasTbl.class.getName()});
                return null;
            }
        }

    }   

    public void mover() throws IOException {
        String pathorigen = "C:\\Users\\Developer1\\Documents\\NetBeansProjects\\RN_AdmonContable_Repo\\RN_GC\\web\\CFDIs";
        String pathdestino = "C:\\Users\\Developer1\\Documents\\NetBeansProjects\\RN_AdmonContable_Repo\\RN_GC\\web\\xml_procesados";
        System.out.println("<------ Leyendo archivos de: " + pathorigen + "------>");
        java.nio.file.Path destino = FileSystems.getDefault().getPath(pathdestino);
        String[] archivo = getFiles(pathorigen);
        try {
            for (int i = 0; i < archivo.length; i++) {
                System.out.println("Archivo No." + (i + 1) + ": " + archivo[i]);
                java.nio.file.Path origen = FileSystems.getDefault().getPath(archivo[i]);
                Files.move(origen, destino.resolve(origen.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            System.err.println("Error: " + ex);
        }
    }

    public RnGcFacturasTbl getRnGcFacturasTbl(java.lang.Integer id) {
        return rnGcFacturasTblFacade.find(id);
    }

    public void prepareFactLines(RnGcFacturasTbl facturaId) {
        if (facturaId != null) {
            itemsFactLineas = getFacade().obtenerFactLineas(facturaId);
        }
    }

    public List<RnGcFacturaslineasTbl> getItemsFactLineas() {
        return itemsFactLineas;
    }

    public void setItemsFactLineas(List<RnGcFacturaslineasTbl> itemsFactLineas) {
        this.itemsFactLineas = itemsFactLineas;
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
            System.out.println("Directorio no valido");
        }
        return arr_res;
    }

}
