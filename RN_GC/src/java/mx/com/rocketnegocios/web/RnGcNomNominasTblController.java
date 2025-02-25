package mx.com.rocketnegocios.web;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import mx.com.rocketnegocios.entities.RnGcNomNominasTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcNomNominasTblFacade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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
import mx.com.rocketnegocios.beans.RnGcNomDeduccionesTblFacade;
import mx.com.rocketnegocios.beans.RnGcNomPercepcionesTblFacade;
import mx.com.rocketnegocios.beans.RnGcNomPeriodicidadpagoTblFacade;
import mx.com.rocketnegocios.beans.RnGcNomPeriodonominaTblFacade;
import mx.com.rocketnegocios.beans.RnGcNomSolicitudTrabajadorTblFacade;
import mx.com.rocketnegocios.beans.RnGcNomSolicitudesLineasTblFacade;
import mx.com.rocketnegocios.beans.RnGcNomSolicitudesTblFacade;
import mx.com.rocketnegocios.beans.RnGcNomTiponominaTblFacade;
import mx.com.rocketnegocios.beans.RnGcTrabajadoresTblFacade;
import mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade;
import mx.com.rocketnegocios.entities.RnGcNomDeduccionesTbl;
import mx.com.rocketnegocios.entities.RnGcNomPercepcionesTbl;
import mx.com.rocketnegocios.entities.RnGcNomPeriodicidadpagoTbl;
import mx.com.rocketnegocios.entities.RnGcNomPeriodonominaTbl;
import mx.com.rocketnegocios.entities.RnGcNomSolicitudTrabajadorTbl;
import mx.com.rocketnegocios.entities.RnGcNomSolicitudesLineasTbl;
import mx.com.rocketnegocios.entities.RnGcNomSolicitudesTbl;
import mx.com.rocketnegocios.entities.RnGcNomTiponominaTbl;
import mx.com.rocketnegocios.entities.RnGcTrabajadoresTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import mx.com.rocketnegocios.web.PercepcionesDeducciones;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named("rnGcNomNominasTblController")
@SessionScoped
public class RnGcNomNominasTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcNomNominasTblFacade ejbFacade;

    @EJB
    private RnGcNomPeriodicidadpagoTblFacade periodicidadPagoFacade;

    @EJB
    private RnGcNomTiponominaTblFacade tipoNominaFacade;

    @EJB
    private RnGcNomPeriodonominaTblFacade periodoNominaFacade;

    @EJB
    private RnGcNomSolicitudesTblFacade solicitudFacade;

    @EJB
    private RnGcNomSolicitudTrabajadorTblFacade soliTrabajadorFacade;
    
    @EJB
    private RnGcTrabajadoresTblFacade trabajadorFacade;
    
    @EJB
    private RnGcNomSolicitudesLineasTblFacade solicitudesLineasFacade;
    
    @EJB
    private RnGcNomPercepcionesTblFacade percepcionFacade;
    
    @EJB
    private RnGcNomDeduccionesTblFacade deduccionFacade;
    
    @EJB
    private RnGcUsuariosTblFacade usuarioFacade;

    private List<RnGcNomNominasTbl> items = null;
    private RnGcNomNominasTbl selected;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private RnGcNomPeriodicidadpagoTbl periodicidadPago;
    private RnGcNomTiponominaTbl tipoNomina;
    private RnGcNomPeriodonominaTbl periodoNomina;
    private RnGcNomSolicitudesTbl solicitud;
    private RnGcNomSolicitudTrabajadorTbl soliTrabajador;
    private RnGcTrabajadoresTbl trabajador;
    private List<RnGcNomSolicitudTrabajadorTbl> listaSoliTrabajadores;
    private RnGcNomSolicitudesLineasTbl solicitudesLineasPercepcion;
    private RnGcNomSolicitudesLineasTbl solicitudesLineasDeduccion;
    private RnGcNomPercepcionesTbl percepcion;
    private RnGcNomDeduccionesTbl deduccion;
    private StreamedContent downLoadFile;

    public RnGcNomNominasTblController() {
    }

    public RnGcNomNominasTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcNomNominasTbl selected) {
        this.selected = selected;
    }

    public StreamedContent getDownLoadFile() {
        return downLoadFile;
    }

    public void setDownLoadFile(StreamedContent downLoadFile) {
        this.downLoadFile = downLoadFile;
    }

    protected void setEmbeddableKeys() {
        selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        selected.setUltimaFechaActualizacion(new Date());
    }

    protected void initializeEmbeddableKey() {
        selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        selected.setFechaCreacion(new Date());
    }

    private RnGcNomNominasTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcNomNominasTbl prepareCreate() {
        selected = new RnGcNomNominasTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcNomNominasTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcNomNominasTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcNomNominasTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcNomNominasTbl> getItems() {
        if (usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")) {
            items = getFacade().findAll();
        } else {
            items = getFacade().obtenerNominaCreadoPor(usuarioFirmado.obtenerIdUsuario());
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

    public RnGcNomNominasTbl getRnGcNomNominasTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcNomNominasTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcNomNominasTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcNomNominasTbl.class)
    public static class RnGcNomNominasTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcNomNominasTblController controller = (RnGcNomNominasTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcNomNominasTblController");
            return controller.getRnGcNomNominasTbl(getKey(value));
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
            if (object instanceof RnGcNomNominasTbl) {
                RnGcNomNominasTbl o = (RnGcNomNominasTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcNomNominasTbl.class.getName()});
                return null;
            }
        }

    }

    public List<RnGcNomNominasTbl> obtenerNominaCreadoPor() {
        List<RnGcNomNominasTbl> listaNominas = new ArrayList<>();
        if (usuarioFirmado.perfilUsuario().equals("ADMINISTRADOR")) {
            listaNominas = getFacade().findAll();
        } else {
            listaNominas = getFacade().obtenerNominaCreadoPor(usuarioFirmado.obtenerIdUsuario());
        }
        return listaNominas;
    }

    public void inicializar() {
        selected = new RnGcNomNominasTbl();
        periodoNomina = new RnGcNomPeriodonominaTbl();
        solicitud = new RnGcNomSolicitudesTbl();
        periodicidadPago = new RnGcNomPeriodicidadpagoTbl();
        selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        selected.setFechaCreacion(new Date());
        selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        selected.setUltimaFechaActualizacion(new Date());
        periodoNomina.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        periodoNomina.setFechaCreacion(new Date());
        periodoNomina.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        periodoNomina.setUltimaFechaActualizacion(new Date());
        solicitud.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        solicitud.setFechaCreacion(new Date());
        solicitud.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        solicitud.setUltimaFechaActualizacion(new Date());
    }
    
    public void inicializarSolTrabajador(){
        soliTrabajador = new RnGcNomSolicitudTrabajadorTbl();
        trabajador = new RnGcTrabajadoresTbl();
        soliTrabajador.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        soliTrabajador.setFechaCreacion(new Date());
        soliTrabajador.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        soliTrabajador.setUltimaFechaActualizacion(new Date());
    }
    
    public void inicializarSolicitudLinea(){
        solicitudesLineasPercepcion = new RnGcNomSolicitudesLineasTbl();
        solicitudesLineasDeduccion = new RnGcNomSolicitudesLineasTbl();
        solicitudesLineasPercepcion.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        solicitudesLineasPercepcion.setFechaCreacion(new Date());
        solicitudesLineasPercepcion.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        solicitudesLineasPercepcion.setUltimaFechaActualizacion(new Date());
        solicitudesLineasDeduccion.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        solicitudesLineasDeduccion.setFechaCreacion(new Date());
        solicitudesLineasDeduccion.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        solicitudesLineasDeduccion.setUltimaFechaActualizacion(new Date());
    }
    
    public void totalPercepcionDeduccion(List<RnGcNomSolicitudTrabajadorTbl> listaSolicitudes){
        for(RnGcNomSolicitudTrabajadorTbl soliTrabajador : listaSolicitudes){
            List<RnGcNomSolicitudesLineasTbl> percepciones = solicitudesLineasFacade.obtenerPercepciones(soliTrabajador.getId());
            List<RnGcNomSolicitudesLineasTbl> deducciones = solicitudesLineasFacade.obtenerDeducciones(soliTrabajador.getId());
            BigDecimal percepcion = BigDecimal.ZERO;
            BigDecimal deduccion = BigDecimal.ZERO;
            for(RnGcNomSolicitudesLineasTbl percep : percepciones){
                percepcion = percepcion.add(percep.getTotalGravado()).add(percep.getTotalExento());
            }
            for(RnGcNomSolicitudesLineasTbl deduc : deducciones){
                deduccion = deduccion.add(deduc.getTotalGravado());
            }
            soliTrabajador.setTotalPercepciones(percepcion);
            soliTrabajador.setTotalDeducciones(deduccion);
            soliTrabajador.setImporteNeto(percepcion.subtract(deduccion));
            soliTrabajadorFacade.edit(soliTrabajador);
        }
    }

    public void PercepDeduc(FileUploadEvent event, List<RnGcNomSolicitudTrabajadorTbl> listaSolicitudes){
        PercepcionesDeducciones percepDeduc = new PercepcionesDeducciones();
        List<PercepcionesDeducciones> listaPercepDeduc = new ArrayList<>();
        System.out.println("----- Inicio PercepDeduc -----");
        try {
            XSSFWorkbook excel = new XSSFWorkbook(event.getFile().getInputstream());
            XSSFSheet sheet = excel.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row;
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                Cell cell;
                if (row.getRowNum() >= 14) {
                    percepDeduc = new PercepcionesDeducciones();
                    while (cellIterator.hasNext()) {
                        cell = cellIterator.next();
                        if (!cell.getStringCellValue().isEmpty()) {
                            if(cell.getColumnIndex() == 0)
                                percepDeduc.setNoEmpleado(cell.getStringCellValue());
                            if(cell.getColumnIndex() == 1)
                                percepDeduc.setNombre(cell.getStringCellValue());
                            if(cell.getColumnIndex() == 2)
                                percepDeduc.setApPaterno(cell.getStringCellValue());
                            if(cell.getColumnIndex() == 3)
                                percepDeduc.setApMaterno(cell.getStringCellValue());
                            if(cell.getColumnIndex() == 4)
                                percepDeduc.setTipoPercepcion(cell.getStringCellValue());
                            if(cell.getColumnIndex() == 5)
                                percepDeduc.setClavePercepcion(cell.getStringCellValue());
                            if(cell.getColumnIndex() == 6)
                                percepDeduc.setConceptoPercepcion(cell.getStringCellValue());
                            if(cell.getColumnIndex() == 7)
                                percepDeduc.setImporteGravado(cell.getStringCellValue());
                            if(cell.getColumnIndex() == 8)
                                percepDeduc.setImporteExcento(cell.getStringCellValue());
                            if(cell.getColumnIndex() == 9)
                                percepDeduc.setTipoDeduccion(cell.getStringCellValue());
                            if(cell.getColumnIndex() == 10)
                                percepDeduc.setClaveDeduccion(cell.getStringCellValue());
                            if(cell.getColumnIndex() == 11)
                                percepDeduc.setConceptoDeduccion(cell.getStringCellValue());
                            if(cell.getColumnIndex() == 12)
                                percepDeduc.setImporte(cell.getStringCellValue());
                        }
                    }
                    if(percepDeduc.getNombre() != null)
                        listaPercepDeduc.add(percepDeduc);
                }
            }
            System.out.println("----------size(): " + listaPercepDeduc.size() + " | listaSolicitudes: " + listaSolicitudes + "----------");
            for(PercepcionesDeducciones perceDeduc : listaPercepDeduc){
                RnGcNomSolicitudesLineasTbl percepcionesAux = new RnGcNomSolicitudesLineasTbl();
                RnGcNomSolicitudesLineasTbl deduccionesAux = new RnGcNomSolicitudesLineasTbl();
                RnGcNomSolicitudTrabajadorTbl aux = new RnGcNomSolicitudTrabajadorTbl();
                RnGcNomPercepcionesTbl percepcionAux = new RnGcNomPercepcionesTbl();
                RnGcNomDeduccionesTbl deduccionAux = new RnGcNomDeduccionesTbl();
                for(RnGcNomSolicitudTrabajadorTbl solTrabajador : listaSolicitudes){
                    if(solTrabajador.getTrabajadorId().getNombre().equals(perceDeduc.getNombre()) &&
                            solTrabajador.getTrabajadorId().getApPaterno().equals(perceDeduc.getApPaterno())){
                        aux = solTrabajador;
                    }
                }
                System.out.println("---------- Percepcion ----------");
                percepcionesAux.setSolicitudTrabajadorId(aux.getId());
                percepcionAux = percepcionFacade.obtenerXClave(perceDeduc.getClavePercepcion());
                percepcionesAux.setPercepcionId(percepcionAux);
                percepcionesAux.setTotalGravado(new BigDecimal(perceDeduc.getImporteGravado()));
                percepcionesAux.setTotalExento(new BigDecimal(perceDeduc.getImporteExcento()));
                percepcionesAux.setTipoClave(perceDeduc.getTipoPercepcion());
                percepcionesAux.setTipoConcepto(perceDeduc.getConceptoPercepcion());
                percepcionesAux.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
                percepcionesAux.setFechaCreacion(new Date());
                percepcionesAux.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                percepcionesAux.setUltimaFechaActualizacion(new Date());
                percepcionesAux = solicitudesLineasFacade.refreshFromDB(percepcionesAux);
                
                if(perceDeduc.getTipoDeduccion() != null){
                    System.out.println("---------- Deduccion ----------");
                    deduccionesAux.setSolicitudTrabajadorId(aux.getId());
                    deduccionAux = deduccionFacade.obtenerXClave(perceDeduc.getClaveDeduccion());
                    deduccionesAux.setDeduccionId(deduccionAux);
                    deduccionesAux.setTotalGravado(new BigDecimal(perceDeduc.getImporte()));
                    deduccionesAux.setTipoClave(perceDeduc.getTipoDeduccion());
                    deduccionesAux.setTipoConcepto(perceDeduc.getConceptoDeduccion());
                    deduccionesAux.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
                    deduccionesAux.setFechaCreacion(new Date());
                    deduccionesAux.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                    deduccionesAux.setUltimaFechaActualizacion(new Date());
                    deduccionesAux = solicitudesLineasFacade.refreshFromDB(deduccionesAux);
                }
            }
            totalPercepcionDeduccion(listaSolicitudes);
        } catch (Exception e) {
            e.getMessage();
        }
    }
    

    public void leerPlantilla(FileUploadEvent event) {
        try {
            inicializar();
            XSSFWorkbook excel = new XSSFWorkbook(event.getFile().getInputstream());
            XSSFSheet sheet = excel.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar fecha = Calendar.getInstance();
            Row row;
            List<String> nombres = new ArrayList<>();
            Date fechaPago = null;
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                Cell cell;
                while (cellIterator.hasNext()) {
                    cell = cellIterator.next();
                    if (cell.getColumnIndex() == 2 && cell.getRowIndex() == 1)
                        solicitud.setRegistroPatronal(cell.getStringCellValue());
                    if (cell.getColumnIndex() == 2 && cell.getRowIndex() == 2) {
                        selected.setNombreNomina(cell.getStringCellValue());
                        periodoNomina.setNombrePeriodo(cell.getStringCellValue());
                        solicitud.setNombreSolicitud(cell.getStringCellValue());
                    }
                    if (cell.getColumnIndex() == 2 && cell.getRowIndex() == 5)
                        fechaPago = sdf.parse(cell.getStringCellValue());
                    if (cell.getColumnIndex() == 2 && cell.getRowIndex() == 4) {
                        tipoNomina = tipoNominaFacade.obtenerXClave(cell.getStringCellValue());
                        selected.setTipoNominaId(tipoNomina);
                    }
                    if (cell.getColumnIndex() == 2 && cell.getRowIndex() == 6) 
                        periodoNomina.setFechaInicio(sdf.parse(cell.getStringCellValue()));
                    if (cell.getColumnIndex() == 2 && cell.getRowIndex() == 7) 
                        periodoNomina.setFechaFin(sdf.parse(cell.getStringCellValue()));
                    if (cell.getColumnIndex() == 2 && cell.getRowIndex() == 9) {
                        periodicidadPago = periodicidadPagoFacade.obtenerXDescr(cell.getStringCellValue());
                        selected.setPeriodicidadPagoId(periodicidadPago);//
                    }
                    if (cell.getColumnIndex() == 1 && cell.getRowIndex() >= 14 && !cell.getStringCellValue().isEmpty()){
                        nombres.add(cell.getStringCellValue());
                    }
                }
            }
            listaSoliTrabajadores = new ArrayList<>();
            selected.setEsConfidencial("S");
            selected.setPatronId(usuarioFirmado.obtenerIdUsuario());
            selected = getFacade().refreshFromDB(selected);
            periodoNomina.setAnioPeriodo(String.valueOf(fecha.get(Calendar.YEAR)));
            periodoNomina.setNumMesPeriodo(fecha.get(Calendar.MONTH) + 1);
            periodoNomina.setEstatus("A");
            periodoNomina.setNominaId(selected);
            periodoNomina = periodoNominaFacade.refreshFromDB(periodoNomina);
            solicitud.setNominaId(selected.getId());
            solicitud.setPeriodoNominaId(periodoNomina);
            solicitud.setPatronId(usuarioFirmado.obtenerIdUsuario());
            solicitud.setEstatus("A");
            solicitud = solicitudFacade.refreshFromDB(solicitud);
            nombres = nombres.stream().distinct().collect(Collectors.toList());
            Long diasPagados = ((periodoNomina.getFechaFin().getTime()-periodoNomina.getFechaInicio().getTime())/(60*60*24*1000))+1;            
            for(String nombre : nombres){
                inicializarSolTrabajador();
                trabajador = trabajadorFacade.obtenerCreadoPorYNombre(nombre, usuarioFirmado.obtenerIdUsuario());
                soliTrabajador.setTrabajadorId(trabajador);
                soliTrabajador.setSolicitudId(solicitud);
                soliTrabajador.setDiasPagados(diasPagados.intValue());
                soliTrabajador.setFechaPago(fechaPago);
                soliTrabajador.setSdi(new BigDecimal(trabajador.getSdi()));
                soliTrabajador.setEstatus("A");
                soliTrabajador = soliTrabajadorFacade.refreshFromDB(soliTrabajador);
                listaSoliTrabajadores.add(soliTrabajador);
            }
            System.out.println("listaSoliTrabajadores: " + listaSoliTrabajadores);
            PercepDeduc(event, listaSoliTrabajadores);
            System.out.println("Nomina: " + selected.getId() + " | Periodo: " + periodoNomina.getId() + " | Solicitud:" + solicitud.getId());
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
    
    public String buscarUsuarioPorId(int Id) {
        System.out.println("controller: " + Id);
        RnGcUsuariosTbl usuarioId = usuarioFacade.obtenerUsuarioPorId(Id);
        if (usuarioId != null) {
            System.out.println("!= null");
            return usuarioId.getNombreCompleto();
        } else {
            System.out.println("== null");
            return "No tiene registrado un patron";
        }
    }
    
    public void descargaPlantilla(){
        try{
            File plantilla = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/Plantilla_Nomina.xlsx"));
            byte[] aux = FileUtils.readFileToByteArray(plantilla);
            InputStream streamPlantilla = new ByteArrayInputStream(aux);
            downLoadFile = new DefaultStreamedContent(streamPlantilla,
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                "plantilla_nomina_" + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()).concat(".xlsx"));
        }catch(Exception ex){
            System.err.println("Ocurrio un error al descargar la plantilla de nomina");
        }
    }
    
    public void eliminarDatos(){
        //List<>
        RnGcNomPeriodonominaTbl periodoNomina = new RnGcNomPeriodonominaTbl();
        RnGcNomSolicitudesTbl solicitud = new RnGcNomSolicitudesTbl();
        List<RnGcNomSolicitudTrabajadorTbl> solioTrabajador = new ArrayList<>();
        List<RnGcNomSolicitudesLineasTbl> solicitudLineas = new ArrayList<>();
        
        periodoNomina = periodoNominaFacade.obtenerXNomina(selected);
        solicitud = solicitudFacade.obtenerXNomina(selected.getId());
        solioTrabajador = soliTrabajadorFacade.obtenerXSolicitud(solicitud);
        if(solioTrabajador != null && solioTrabajador.size()>0){
            solicitudLineas = solicitudesLineasFacade.obtenerXSoliTrabajador(solioTrabajador.get(0).getId(), solioTrabajador.get(solioTrabajador.size()-1).getId());
        }
        System.out.println("solicitudLineas: " + solicitudLineas);
        System.out.println("solioTrabajador: " + solioTrabajador);
        System.out.println("solicitud: " + solicitud);
        System.out.println("periodoNomina: " + periodoNomina);
        System.out.println("selected: " + selected);
        
        for(RnGcNomSolicitudesLineasTbl soliLineas : solicitudLineas){
            solicitudesLineasFacade.remove(soliLineas);
        }
        for(RnGcNomSolicitudTrabajadorTbl soliTrabajador : solioTrabajador){
            soliTrabajadorFacade.remove(soliTrabajador);
        }
        solicitudFacade.remove(solicitud);
        periodoNominaFacade.remove(periodoNomina);
        getFacade().remove(selected);
        selected = null;
    }

}
