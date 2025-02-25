package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcListaNegraTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcListaNegraTblFacade;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import mx.com.rocketnegocios.beans.RnGcPersonasTblFacade;
import mx.com.rocketnegocios.beans.RnGcTipoListaNegraTblFacade;
import mx.com.rocketnegocios.entities.RnGcPersonasTbl;
import mx.com.rocketnegocios.entities.RnGcTipoListaNegraTbl;
import mx.com.rocketnegocios.entities.personaListaNegra;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

@Named("rnGcListaNegraTblController")
@SessionScoped
public class RnGcListaNegraTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcListaNegraTblFacade ejbFacade;

    @EJB
    private RnGcTipoListaNegraTblFacade tipoListaNegraFacade;

    @EJB
    private RnGcPersonasTblFacade personasFacade;

    private List<RnGcListaNegraTbl> items = null;
    private RnGcListaNegraTbl selected;
    private RnGcTipoListaNegraTbl tipoListaNegra;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private List<personaListaNegra> listaPersonaLN = null;
    private personaListaNegra personaLN = new personaListaNegra();
    private String nombre;
    private String rfc;
    private List<RnGcListaNegraTbl> busqeudaManual = null;
    private RnGcListaNegraTbl selectedManual;

    public RnGcListaNegraTblController() {
    }

    public RnGcListaNegraTbl getSelectedManual() {
        return selectedManual;
    }

    public void setSelectedManual(RnGcListaNegraTbl selectedManual) {
        this.selectedManual = selectedManual;
    }

    public String getNombre() {
        return nombre;
    }

    public List<RnGcListaNegraTbl> getBusqeudaManual() {
        if(busqeudaManual == null)
            busqeudaManual = new ArrayList<>();
        return busqeudaManual;
    }

    public void setBusqeudaManual(List<RnGcListaNegraTbl> busqeudaManual) {
        this.busqeudaManual = busqeudaManual;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public RnGcListaNegraTbl getSelected() {
        return selected;
    }

    public personaListaNegra getPersonaLN() {
        return personaLN;
    }

    public void setPersonaLN(personaListaNegra personaLN) {
        this.personaLN = personaLN;
    }

    public List<personaListaNegra> getListaPersonaLN() {
        List<RnGcPersonasTbl> listaPersonas = new ArrayList<>();
        List<RnGcListaNegraTbl> listaListaNegra = new ArrayList<>();
        List<RnGcListaNegraTbl> listaNegra = new ArrayList<>();
        personaListaNegra personaLN = new personaListaNegra();
        listaPersonaLN = new ArrayList<>();

        listaPersonas = personasFacade.obtenerCreadoPor(usuarioFirmado.obtenerIdUsuario());
        for (RnGcPersonasTbl persona : listaPersonas) {
            listaNegra = getFacade().obtenerXRfc(persona.getRfc());
            if (listaNegra != null && !listaNegra.isEmpty()) {
                listaListaNegra.add(listaNegra.get(0));
                personaLN.setPersona(persona);
                personaLN.setListaNegra(listaNegra.get(0));
                listaPersonaLN.add(personaLN);
                listaNegra = new ArrayList<>();
                personaLN = new personaListaNegra();
            }else{
                listaNegra = new ArrayList<>();
                personaLN = new personaListaNegra();
            }
        }
        return listaPersonaLN;
    }

    public void setListaPersonaLN(List<personaListaNegra> listaPersonaLN) {
        this.listaPersonaLN = listaPersonaLN;
    }

    public void setSelected(RnGcListaNegraTbl selected) {
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

    private RnGcListaNegraTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcListaNegraTbl prepareCreate() {
        selected = new RnGcListaNegraTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcListaNegraTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcListaNegraTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcListaNegraTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcListaNegraTbl> getItems() {
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

    public RnGcListaNegraTbl getRnGcListaNegraTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcListaNegraTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcListaNegraTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcListaNegraTbl.class)
    public static class RnGcListaNegraTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcListaNegraTblController controller = (RnGcListaNegraTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcListaNegraTblController");
            return controller.getRnGcListaNegraTbl(getKey(value));
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
            if (object instanceof RnGcListaNegraTbl) {
                RnGcListaNegraTbl o = (RnGcListaNegraTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcListaNegraTbl.class.getName()});
                return null;
            }
        }
    }

    public void leerPlantilla(FileUploadEvent event) {
        tipoListaNegra = new RnGcTipoListaNegraTbl();
        List<RnGcTipoListaNegraTbl> listaTipoListaNegra = new ArrayList<>();
        crearTipoListaNegra(event);
        System.out.println("tipoListaNegra2: " + tipoListaNegra.getId() + " | " + tipoListaNegra.getTipoLista() + " | " + tipoListaNegra.getFechaPublicacion() + " | " + tipoListaNegra.getFolioPublicacion()
                + " | " + tipoListaNegra.getCreadoPor() + " | " + tipoListaNegra.getFechaCreacion() + " | " + tipoListaNegra.getUltimaActualizacionPor() + " | " + tipoListaNegra.getUltimaFechaActualizacion());
        listaTipoListaNegra = tipoListaNegraFacade.obtenerXFolio(tipoListaNegra.getFolioPublicacion());
        if (listaTipoListaNegra != null && !listaTipoListaNegra.isEmpty()) {
            System.out.println("La lista con el folio publicacion " + tipoListaNegra.getFolioPublicacion() + " ya se encuentra registrada");
            RequestContext.getCurrentInstance().execute("PF('RnGcListaNegraTblDocDialog').hide();");
            JsfUtil.addErrorMessage("La lista con el folio publicacion " + tipoListaNegra.getFolioPublicacion() + " ya se encuentra registrada");
        } else {
            tipoListaNegra = tipoListaNegraFacade.refreshFromDB(tipoListaNegra);
            crearListaNegra(event, tipoListaNegra);
            RequestContext.getCurrentInstance().execute("PF('RnGcListaNegraTblDocDialog').hide();");
            JsfUtil.addSuccessMessage("La lista con el folio publicacion " + tipoListaNegra.getFolioPublicacion() + " se registro con exito");
            selected = null;
        }
    }

    public void crearListaNegra(FileUploadEvent event, RnGcTipoListaNegraTbl tipoLN) {
        try {
            int i = 1;
            XSSFWorkbook excel = new XSSFWorkbook(event.getFile().getInputstream());
            XSSFSheet sheet = excel.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row;
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                Cell cell;
                if (row.getRowNum() >= 5) {
                    prepareCreate();
                    while (cellIterator.hasNext()) {
                        cell = cellIterator.next();
                        if (cell.getColumnIndex() == 1) {
                            selected.setRfc(cell.toString());
                        }
                        if (cell.getColumnIndex() == 2) {
                            selected.setNombreContribuyente(cell.toString());
                        }
                        if (cell.getColumnIndex() == 3) {
                            selected.setSituacionContribuyente(cell.toString());
                        }
                        if (cell.getColumnIndex() == 4) {
                            selected.setNumeroFechaOficioGlobalPresuncionSAT(cell.toString());
                        }
                        if (cell.getColumnIndex() == 5) {
                            selected.setPublicacionSATpresuntos(cell.toString());
                        }
                        if (cell.getColumnIndex() == 6) {
                            selected.setNumeroFechaOficioGlobalPresuncionDOF(cell.toString());
                        }
                        if (cell.getColumnIndex() == 7) {
                            selected.setPublicacionDOFpresuntos(cell.toString());
                        }
                        if (cell.getColumnIndex() == 8) {
                            selected.setPublicacionSATdesvirtuados(cell.toString());
                        }
                        if (cell.getColumnIndex() == 9) {
                            selected.setNumeroFechaOficioGlobalDesvirtuadoSAT(cell.toString());
                        }
                        if (cell.getColumnIndex() == 10) {
                            selected.setPublicacionDOFdesvirtuados(cell.toString());
                        }
                        if (cell.getColumnIndex() == 11) {
                            selected.setNumeroFechaOficioGlobalDefinitivoSAT(cell.toString());
                        }
                        if (cell.getColumnIndex() == 12) {
                            selected.setPublicacionSATdefinitivos(cell.toString());
                        }
                        if (cell.getColumnIndex() == 13) {
                            selected.setPublicacionDOFdefinitivos(cell.toString());
                        }
                        if (cell.getColumnIndex() == 14) {
                            selected.setNumeroFechaOficioGlobalSentenciaSAT(cell.toString());
                        }
                        if (cell.getColumnIndex() == 15) {
                            selected.setPublicacionSATsentencia(cell.toString());
                        }
                        if (cell.getColumnIndex() == 16) {
                            selected.setNumeroFechaOficioGlobalSentenciaDOF(cell.toString());
                        }
                        if (cell.getColumnIndex() == 17) {
                            selected.setPublicacionDOFsentencia(cell.toString());
                        }
                    }
                    //System.out.println("LN: " + i + " | " + selected.getRfc() + " | " + selected.getNombreContribuyente() + " | " + selected.getSituacionContribuyente());
                    selected.setIdTipoLista(tipoLN);
                    setEmbeddableKeys();
                    getFacade().edit(selected);
                    i++;
                }
            }
        } catch (Exception ex) {
            System.out.println("Error en la lectura de la plantilla: " + ex.getLocalizedMessage());
        }
    }

    public void crearTipoListaNegra(FileUploadEvent event) {
        try {
            XSSFWorkbook excel = new XSSFWorkbook(event.getFile().getInputstream());
            XSSFSheet sheet = excel.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row;
            tipoListaNegra.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
            tipoListaNegra.setFechaCreacion(new Date());
            tipoListaNegra.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
            tipoListaNegra.setUltimaFechaActualizacion(new Date());
            tipoListaNegra.setEstado("A");
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                Cell cell;
                prepareCreate();
                while (cellIterator.hasNext()) {
                    cell = cellIterator.next();
                    if (cell.getColumnIndex() == 1 && cell.getRowIndex() == 0) {
                        tipoListaNegra.setTipoLista(cell.toString());
                    }
                    if (cell.getColumnIndex() == 1 && cell.getRowIndex() == 1) {
                        tipoListaNegra.setFechaPublicacion(cell.getDateCellValue());
                    }
                    if (cell.getColumnIndex() == 1 && cell.getRowIndex() == 2) {
                        tipoListaNegra.setFolioPublicacion(cell.toString());
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Error en la lectura de la plantilla: " + ex.getLocalizedMessage());
        }
    }

    public List<RnGcListaNegraTbl> busqueda() {
        //System.out.println("Validaciones: " + (rfc == null) + " | " + rfc.isEmpty() + " | " + (nombre == null) + " | " + (nombre.isEmpty()));
        if(rfc != null && !rfc.isEmpty()){
            System.out.println("Busqueda por RFC");
            busqeudaManual = getFacade().obtenerXRfc(rfc);
            rfc = "";
        }else if(nombre != null && !nombre.isEmpty()){
            System.out.println("Busqueda por Nombre");
            busqeudaManual = getFacade().obtenerXNombre(nombre);
            nombre = "";
        }else if(rfc.isEmpty() && nombre.isEmpty()){
            busqeudaManual = new ArrayList<>();
        }
        return busqeudaManual;
    }

}
