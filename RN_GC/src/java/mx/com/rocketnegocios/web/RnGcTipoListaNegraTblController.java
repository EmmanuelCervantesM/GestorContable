package mx.com.rocketnegocios.web;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import mx.com.rocketnegocios.entities.RnGcTipoListaNegraTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcTipoListaNegraTblFacade;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import mx.com.rocketnegocios.beans.RnGcListaNegraTblFacade;
import mx.com.rocketnegocios.entities.RnGcListaNegraTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named("rnGcTipoListaNegraTblController")
@SessionScoped
public class RnGcTipoListaNegraTblController implements Serializable {
    
    @EJB
    private RnGcListaNegraTblFacade listaNegraFacade;

    @EJB
    private mx.com.rocketnegocios.beans.RnGcTipoListaNegraTblFacade ejbFacade;
    private List<RnGcTipoListaNegraTbl> items = null;
    private RnGcTipoListaNegraTbl selected;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private List<RnGcListaNegraTbl> listaNegra = null;
    private RnGcListaNegraTbl itemListaNegra;
    private StreamedContent downLoadFile;

    public RnGcTipoListaNegraTblController() {
    }

    public StreamedContent getDownLoadFile() {
        return downLoadFile;
    }

    public void setDownLoadFile(StreamedContent downLoadFile) {
        this.downLoadFile = downLoadFile;
    }

    public RnGcTipoListaNegraTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcTipoListaNegraTbl selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        selected.setUltimaFechaActualizacion(new Date());
    }

    protected void initializeEmbeddableKey() {
        selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        selected.setFechaCreacion(new Date());
        selected.setEstado("A");
    }

    private RnGcTipoListaNegraTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcTipoListaNegraTbl prepareCreate() {
        selected = new RnGcTipoListaNegraTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcTipoListaNegraTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcTipoListaNegraTblUpdated"));
    }

    public void deleteListaNegra(RnGcTipoListaNegraTbl tipoListaNegra){
        try{
            List<RnGcListaNegraTbl> itemsListaNegra = new ArrayList<>();
            itemsListaNegra = listaNegraFacade.obtenerXTipoListaNegra(tipoListaNegra);
            for(RnGcListaNegraTbl listaNegra : itemsListaNegra){
                listaNegraFacade.remove(listaNegra);
            }
            getFacade().remove(tipoListaNegra);
            JsfUtil.addSuccessMessage("Datos Eliminados Correctamente");
            selected = null;
        }catch(EJBException ex){
            System.out.println("Error en la eliminacion: " + ex.getLocalizedMessage());
            JsfUtil.addSuccessMessage("Ocurrio un error durante la eliminaciòn");
        }
        
        
    }
    
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcTipoListaNegraTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcTipoListaNegraTbl> getItems() {
        items = getFacade().obtenerActivos();
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

    public RnGcTipoListaNegraTbl getRnGcTipoListaNegraTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcTipoListaNegraTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcTipoListaNegraTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcTipoListaNegraTbl.class)
    public static class RnGcTipoListaNegraTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcTipoListaNegraTblController controller = (RnGcTipoListaNegraTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcTipoListaNegraTblController");
            return controller.getRnGcTipoListaNegraTbl(getKey(value));
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
            if (object instanceof RnGcTipoListaNegraTbl) {
                RnGcTipoListaNegraTbl o = (RnGcTipoListaNegraTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcTipoListaNegraTbl.class.getName()});
                return null;
            }
        }
    }

    public List<RnGcListaNegraTbl> getListaNegra() {
        return listaNegra;
    }

    public RnGcListaNegraTbl getItemListaNegra() {
        return itemListaNegra;
    }

    public void setItemListaNegra(RnGcListaNegraTbl itemListaNegra) {
        this.itemListaNegra = itemListaNegra;
    }
    
    public void prepareListaNegra(RnGcTipoListaNegraTbl tipoListaNegra){
        this.listaNegra = new ArrayList<>();
        this.listaNegra = listaNegraFacade.obtenerXTipoListaNegra(tipoListaNegra);
        itemListaNegra = null;
    }
    
    public RnGcListaNegraTbl prepareItemListaNegra(){
        itemListaNegra = new RnGcListaNegraTbl();
        itemListaNegra.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        itemListaNegra.setFechaCreacion(new Date());
        itemListaNegra.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        itemListaNegra.setUltimaFechaActualizacion(new Date());
        itemListaNegra.setIdTipoLista(selected);
        itemListaNegra.setEstado("A");
        System.out.println("listaNegra: " + itemListaNegra.getIdTipoLista());
        return itemListaNegra;
    }
    
    public void deleteListaNegra(){
        System.out.println("deleteListaNegra: " + itemListaNegra);
        listaNegraFacade.remove(itemListaNegra);
        itemListaNegra = null;
        JsfUtil.addSuccessMessage("Eliminado Correctamente");
        prepareListaNegra(selected);
    }
    
    public void updateListaNegra(){
        itemListaNegra.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        itemListaNegra.setUltimaFechaActualizacion(new Date());
        listaNegraFacade.edit(itemListaNegra);
        JsfUtil.addSuccessMessage("Actualizado Correctamente");
        prepareListaNegra(selected);
    }
    
    public void createListaNegra(){
        itemListaNegra.setRfc(itemListaNegra.getRfc().toUpperCase());
        itemListaNegra.setNombreContribuyente(iniMayusculas(itemListaNegra.getNombreContribuyente()));
        listaNegraFacade.edit(itemListaNegra);
        JsfUtil.addSuccessMessage("Creado Correctamente");
        prepareListaNegra(selected);
    }
    
    public String iniMayusculas(String contri) {
        String vacias[] = {"De", "Y", "Con", "Del", "El", "En", "Es", "No", "Para", "Pero", "Por", "Que", "Se", "Si", "Sin", "Solo", "Tan", "Te", "Tu"};
        contri = contri.toLowerCase();
        char[] caracteres = contri.toCharArray();
        caracteres[0] = Character.toUpperCase(caracteres[0]);
        for (int i = 0; i < contri.length() - 2; i++) {
            if (caracteres[i] == ' ' || caracteres[i] == '.' || caracteres[i] == ',') {
                caracteres[i + 1] = Character.toUpperCase(caracteres[i + 1]);
            }
        }
        
        String persona = new String(caracteres);
        String[] parts = persona.split(" ");
        List<String> nombreFinal = new ArrayList<>();
        String nombreFin = "";
        
        for(String nombre : parts){
            nombreFinal.add(nombre);
        }
        
        for(int i = 0; i < nombreFinal.size(); i++){
            for(String nombre : vacias){
                if(nombreFinal.get(i).equals(nombre))
                    nombreFinal.set(i, nombre.toLowerCase());
            }
        }
        
        for(String nombre : nombreFinal){
            nombreFin = nombreFin + nombre + " ";
        }
        System.out.println(nombreFin.substring(0, nombreFin.length()-1));
        
        return nombreFin.substring(0, nombreFin.length()-1);
    }
    
    public void descargarPlantilla(){
        try{
            File plantilla = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/Plantilla_Listas_Negras_Conta_Arc.xlsx"));
            byte[] aux = FileUtils.readFileToByteArray(plantilla);
            InputStream streamPlantilla = new ByteArrayInputStream(aux);
            downLoadFile = new DefaultStreamedContent(streamPlantilla, 
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    "Plantilla_Listas_Negras_Conta_Arc_" + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) + ".xlsx");
        }catch(Exception ex){
            ex.printStackTrace();
            JsfUtil.addErrorMessage("Ocurrio un eror al descargar la plantilla");
        }
    }

}
