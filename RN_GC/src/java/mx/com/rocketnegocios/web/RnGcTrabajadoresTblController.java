package mx.com.rocketnegocios.web;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import mx.com.rocketnegocios.entities.RnGcTrabajadoresTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcTrabajadoresTblFacade;

import java.io.Serializable;
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
import mx.com.rocketnegocios.beans.RnGcNomEstadosTblFacade;
import mx.com.rocketnegocios.beans.RnGcNomNominasTblFacade;
import mx.com.rocketnegocios.beans.RnGcNomTipocontratoTblFacade;
import mx.com.rocketnegocios.entities.RnGcNomEstadosTbl;
import mx.com.rocketnegocios.entities.RnGcNomNominasTbl;
import mx.com.rocketnegocios.entities.RnGcNomTipocontratoTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.DefaultStreamedContent;

@Named("rnGcTrabajadoresTblController")
@SessionScoped
public class RnGcTrabajadoresTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade usuarioFacade;
    
    @EJB
    private RnGcNomTipocontratoTblFacade tipoContratoFacade;
    
    @EJB
    private RnGcNomEstadosTblFacade estadoFacade;
    
    @EJB
    private mx.com.rocketnegocios.beans.RnGcTrabajadoresTblFacade ejbFacade;
    private List<RnGcTrabajadoresTbl> items = null;
    private RnGcTrabajadoresTbl selected;
    private UploadedFile file;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private StreamedContent downLoadFile;
    private RnGcNomTipocontratoTbl tipoContrato;
    private RnGcNomEstadosTbl estado;

    public RnGcTrabajadoresTblController() {
    }

    public RnGcTrabajadoresTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcTrabajadoresTbl selected) {
        this.selected = selected;
    }

    public StreamedContent getDownLoadFile() {
        return downLoadFile;
    }

    public void setDownLoadFile(StreamedContent downLoadFile) {
        this.downLoadFile = downLoadFile;
    }

    protected void setEmbeddableKeys() {
        selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        selected.setUltimaFechaActualizacion(new Date());
    }

    protected void initializeEmbeddableKey() {
        selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        selected.setFechaCreacion(new Date());
    }

    private RnGcTrabajadoresTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcTrabajadoresTbl prepareCreate() {
        selected = new RnGcTrabajadoresTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcTrabajadoresTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcTrabajadoresTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcTrabajadoresTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcTrabajadoresTbl> getItems() {
        if(usuarioFirmado.perfilUsuario().equals("ADMINISTRADOR")){
            items = getFacade().findAll();
        }else{
            items = getFacade().trabajadoresCreadoPor(usuarioFirmado.obtenerIdUsuario());
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    selected.setNombreCompleto(selected.getNombre() + " " + selected.getApPaterno() + " " + selected.getApMaterno());
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

    public RnGcTrabajadoresTbl getRnGcTrabajadoresTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcTrabajadoresTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcTrabajadoresTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcTrabajadoresTbl.class)
    public static class RnGcTrabajadoresTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcTrabajadoresTblController controller = (RnGcTrabajadoresTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcTrabajadoresTblController");
            return controller.getRnGcTrabajadoresTbl(getKey(value));
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
            if (object instanceof RnGcTrabajadoresTbl) {
                RnGcTrabajadoresTbl o = (RnGcTrabajadoresTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcTrabajadoresTbl.class.getName()});
                return null;
            }
        }
    }
    
    public void leerPlantilla(FileUploadEvent event) throws FileNotFoundException, IOException{
        System.out.println("event: " + event.getFile().getFileName() + " | " + event.getFile().getContentType());
        try {
            XSSFWorkbook excel = new XSSFWorkbook(event.getFile().getInputstream());
            XSSFSheet sheet = excel.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Row row;
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
		Iterator<Cell> cellIterator = row.cellIterator();
		Cell cell;
                if(row.getRowNum() > 3){
                    prepareCreate();
                    while (cellIterator.hasNext()) {
                    cell = cellIterator.next();
                        if(cell.getRowIndex() > 3){
                            String datos = cell.getStringCellValue() + ",";      
                            String [] parts = datos.split(",");
                            for(String dato : parts){
                                if(cell.getColumnIndex() == 0)
                                    selected.setNoTrabajador(dato);
                                if(cell.getColumnIndex() == 1)
                                    selected.setFechaInicio(sdf.parse(dato));
                                if(cell.getColumnIndex() == 2)
                                    selected.setTipoPersona(dato);
                                if(cell.getColumnIndex() == 4){
                                    tipoContrato = new RnGcNomTipocontratoTbl();
                                    tipoContrato = tipoContratoFacade.obtenerXClave(dato);
                                    selected.setTipoContratoId(tipoContrato.getId());
                                }
                                if(cell.getColumnIndex() == 10)
                                    selected.setBanco(dato);
                                if(cell.getColumnIndex() == 11)
                                    selected.setCuentaBancaria(dato);
                                if(cell.getColumnIndex() == 12){
                                    estado = new RnGcNomEstadosTbl();
                                    estado = estadoFacade.obtenerXClave(dato);
                                    selected.setEstadoId(estado.getId());
                                }
                                if(cell.getColumnIndex() == 13)
                                    selected.setNombre(dato);
                                if(cell.getColumnIndex() == 14)
                                    selected.setApPaterno(dato);
                                if(cell.getColumnIndex() == 15)
                                    selected.setApMaterno(dato);
                                if(cell.getColumnIndex() == 16)
                                    selected.setCurp(dato);
                                if(cell.getColumnIndex() == 17)
                                    selected.setRfc(dato);
                                if(cell.getColumnIndex() == 18)
                                    selected.setNss(dato);
                                if(cell.getColumnIndex() == 19)
                                    selected.setSalarioBase(dato);
                                if(cell.getColumnIndex() == 20)
                                    selected.setSdi(dato);
                                if(cell.getColumnIndex() == 21)
                                    selected.setEmail1(dato);
                           }
                        }
                    }
                setEmbeddableKeys();
                selected.setNombreCompleto(selected.getNombre() + " " + selected.getApPaterno() + " " + selected.getApMaterno());
                getFacade().edit(selected);
		System.out.println();
                }
            }
            selected = new RnGcTrabajadoresTbl();
        }catch (Exception e) {
            e.getMessage();
	}//*/
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public List<String> obtenerNominaCreado(Integer nominaId){
        List<String> nominaTrabajador = new ArrayList<>();
        List<RnGcNomNominasTbl> nomina = new ArrayList<>();
        RnGcNomNominasTblFacade nominaFacade = new RnGcNomNominasTblFacade();
        if(nominaId != null){
            nomina = nominaFacade.obtenerNominaPorId(nominaId);
            for(RnGcNomNominasTbl nomina1 : nomina){
                nominaTrabajador.add(nomina1.getNombreNomina());
            }
        }
        return nominaTrabajador;
    }
    
    public void deacargaPlantilla() throws IOException{
        System.out.println("descargarPlantilla");
        File plantilla = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/Trabajadores.xlsx"));
        System.out.println("plantilla: " + plantilla);
        byte[] aux = FileUtils.readFileToByteArray(plantilla);
        System.out.println("aux: " + aux);
        InputStream streamPlantilla = new ByteArrayInputStream(aux);
        System.out.println("streamPlantilla: " + streamPlantilla);
        downLoadFile = new DefaultStreamedContent(streamPlantilla,
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                "plantilla_trabajadores_" + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()).concat(".xlsx"));
    }

}
