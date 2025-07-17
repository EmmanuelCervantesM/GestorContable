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
import mx.com.rocketnegocios.beans.RnGcNomOrigenrecursoTblFacade;
import mx.com.rocketnegocios.beans.RnGcNomPercepcionesTblFacade;
import mx.com.rocketnegocios.beans.RnGcNomPeriodicidadpagoTblFacade;
import mx.com.rocketnegocios.beans.RnGcNomPeriodonominaTblFacade;
import mx.com.rocketnegocios.beans.RnGcNomSolicitudTrabajadorTblFacade;
import mx.com.rocketnegocios.beans.RnGcNomSolicitudesLineasTblFacade;
import mx.com.rocketnegocios.beans.RnGcNomSolicitudesTblFacade;
import mx.com.rocketnegocios.beans.RnGcNomTipoincapacidadTblFacade;
import mx.com.rocketnegocios.beans.RnGcNomTiponominaTblFacade;
import mx.com.rocketnegocios.beans.RnGcNomTipootropagoTblFacade;
import mx.com.rocketnegocios.beans.RnGcTrabajadoresTblFacade;
import mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade;
import mx.com.rocketnegocios.entities.RnGcNomDeduccionesTbl;
import mx.com.rocketnegocios.entities.RnGcNomOrigenrecursoTbl;
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
import org.apache.poi.ss.usermodel.DateUtil;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import java.text.SimpleDateFormat;
import mx.com.rocketnegocios.entities.RnGcNomTipoincapacidadTbl;

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
    private RnGcNomTipoincapacidadTblFacade incapacidadFacade;

    @EJB
    private RnGcNomTipootropagoTblFacade otroPagoFacade;

    @EJB
    private RnGcNomOrigenrecursoTblFacade origenRecursoFacade;

    @EJB
    private RnGcUsuariosTblFacade usuarioFacade;

    private List<RnGcNomNominasTbl> items = null;
    private RnGcNomNominasTbl selected;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private RnGcNomPeriodicidadpagoTbl periodicidadPago;
    private RnGcNomOrigenrecursoTbl origenRecurso;
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
        origenRecurso = new RnGcNomOrigenrecursoTbl();
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

    public void inicializarSolTrabajador() {
        soliTrabajador = new RnGcNomSolicitudTrabajadorTbl();
        trabajador = new RnGcTrabajadoresTbl();
        soliTrabajador.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        soliTrabajador.setFechaCreacion(new Date());
        soliTrabajador.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        soliTrabajador.setUltimaFechaActualizacion(new Date());
    }

    public void inicializarSolicitudLinea() {
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

    public void totalPercepcionDeduccion(List<RnGcNomSolicitudTrabajadorTbl> listaSolicitudes) {
        for (RnGcNomSolicitudTrabajadorTbl soliTrabajador : listaSolicitudes) {
            List<RnGcNomSolicitudesLineasTbl> percepciones = solicitudesLineasFacade.obtenerPercepciones(soliTrabajador.getId());
            List<RnGcNomSolicitudesLineasTbl> deducciones = solicitudesLineasFacade.obtenerDeducciones(soliTrabajador.getId());
            BigDecimal percepcion = BigDecimal.ZERO;
            BigDecimal deduccion = BigDecimal.ZERO;
            for (RnGcNomSolicitudesLineasTbl percep : percepciones) {
                percepcion = percepcion.add(percep.getTotalGravado()).add(percep.getTotalExento());
            }
            for (RnGcNomSolicitudesLineasTbl deduc : deducciones) {
                deduccion = deduccion.add(deduc.getTotalGravado());
            }
            soliTrabajador.setTotalPercepciones(percepcion);
            soliTrabajador.setTotalDeducciones(deduccion);
            soliTrabajador.setImporteNeto(percepcion.subtract(deduccion));
            soliTrabajadorFacade.edit(soliTrabajador);
        }
    }

    public void PercepDeduc(FileUploadEvent event, List<RnGcNomSolicitudTrabajadorTbl> listaSolicitudes) {
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
                            if (cell.getColumnIndex() == 0) {
                                percepDeduc.setNoEmpleado(cell.getStringCellValue());
                            }
                            if (cell.getColumnIndex() == 1) {
                                percepDeduc.setNombre(cell.getStringCellValue());
                            }
                            if (cell.getColumnIndex() == 2) {
                                percepDeduc.setApPaterno(cell.getStringCellValue());
                            }
                            if (cell.getColumnIndex() == 3) {
                                percepDeduc.setApMaterno(cell.getStringCellValue());
                            }
                            if (cell.getColumnIndex() == 4) {
                                percepDeduc.setTipoPercepcion(cell.getStringCellValue());
                            }
                            if (cell.getColumnIndex() == 5) {
                                percepDeduc.setClavePercepcion(cell.getStringCellValue());
                            }
                            if (cell.getColumnIndex() == 6) {
                                percepDeduc.setConceptoPercepcion(cell.getStringCellValue());
                            }
                            if (cell.getColumnIndex() == 7) {
                                percepDeduc.setImporteGravado(cell.getStringCellValue());
                            }
                            if (cell.getColumnIndex() == 8) {
                                percepDeduc.setImporteExcento(cell.getStringCellValue());
                            }
                            if (cell.getColumnIndex() == 9) {
                                percepDeduc.setTipoDeduccion(cell.getStringCellValue());
                            }
                            if (cell.getColumnIndex() == 10) {
                                percepDeduc.setClaveDeduccion(cell.getStringCellValue());
                            }
                            if (cell.getColumnIndex() == 11) {
                                percepDeduc.setConceptoDeduccion(cell.getStringCellValue());
                            }
                            if (cell.getColumnIndex() == 12) {
                                percepDeduc.setImporte(cell.getStringCellValue());
                            }
                        }
                    }
                    if (percepDeduc.getNombre() != null) {
                        listaPercepDeduc.add(percepDeduc);
                    }
                }
            }
            System.out.println("----------size(): " + listaPercepDeduc.size() + " | listaSolicitudes: " + listaSolicitudes + "----------");
            for (PercepcionesDeducciones perceDeduc : listaPercepDeduc) {
                RnGcNomSolicitudesLineasTbl percepcionesAux = new RnGcNomSolicitudesLineasTbl();
                RnGcNomSolicitudesLineasTbl deduccionesAux = new RnGcNomSolicitudesLineasTbl();
                RnGcNomSolicitudTrabajadorTbl aux = new RnGcNomSolicitudTrabajadorTbl();
                RnGcNomPercepcionesTbl percepcionAux = new RnGcNomPercepcionesTbl();
                RnGcNomDeduccionesTbl deduccionAux = new RnGcNomDeduccionesTbl();
                for (RnGcNomSolicitudTrabajadorTbl solTrabajador : listaSolicitudes) {
                    if (solTrabajador.getTrabajadorId().getNombre().equals(perceDeduc.getNombre())
                            && solTrabajador.getTrabajadorId().getApPaterno().equals(perceDeduc.getApPaterno())) {
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

                if (perceDeduc.getTipoDeduccion() != null) {
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

            // Validar el archivo Excel, si hay errores retorna la lista de errores
            List<String> errores = validarCampos(event);
            if (!errores.isEmpty()) {
                for (String err : errores) {
                    JsfUtil.addErrorMessage(err);
                }
                return; // Detener ejecución si hay errores
            }

            guardarPlantilla(event);
            /*  
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
       
             */
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public void PercepDeducNewVersion(FileUploadEvent event, List<RnGcNomSolicitudTrabajadorTbl> listaSolicitudes) {
        System.out.println("----- Inicio PercepDeduc -----");
        try {
            XSSFWorkbook excel = new XSSFWorkbook(event.getFile().getInputstream());
            XSSFSheet sheet = excel.getSheetAt(0);

            // Procesar percepciones y deducciones de columnas A-M (índice 0-12)
            for (int fila = 16; fila <= sheet.getLastRowNum(); fila++) {
                Row row = sheet.getRow(fila);
                if (row == null || esCeldaVacia(row.getCell(0))) {
                    continue; // Saltar filas vacías
                }

                String noEmpleado = getValorCelda(row.getCell(0));
                System.out.println("No de trabajador en la " + (fila + 1) + ": " + noEmpleado);
                RnGcNomSolicitudTrabajadorTbl trabajadorSol = listaSolicitudes.stream()
                        .filter(t -> t.getTrabajadorId().getNoTrabajador().equalsIgnoreCase(noEmpleado))
                        .findFirst()
                        .orElse(null);

                if (trabajadorSol == null) {
                    System.out.println("❌ No se encontró trabajador para fila " + (fila + 1) + ": " + noEmpleado);
                    continue;
                }

                // === PERCEPCIÓN ===
                String clavePercepcionRaw = getValorCelda(row.getCell(5)).trim();
                String clavePercepcion;

                if (clavePercepcionRaw.matches("\\d+(\\.0+)?")) {
                    // Si es un número como 3 o 3.0 o 003.00
                    Double valor = Double.parseDouble(clavePercepcionRaw);
                    clavePercepcion = String.format("%03d", valor.intValue());
                } else {
                    // Si es un código alfanumérico u otra cosa
                    clavePercepcion = clavePercepcionRaw;
                }

                System.out.println("✅ Percepción clave : " + clavePercepcion);
                if (!clavePercepcion.isEmpty()) {
                    try {
                        RnGcNomPercepcionesTbl percepcionAux = percepcionFacade.obtenerXClave(clavePercepcion);

                        if (percepcionAux != null) {
                            System.out.println("✅ Percepción encontrada: " + percepcionAux);
                        } else {
                            System.out.println("⚠️ No se encontró percepción con clave: " + clavePercepcion);
                        }
                        RnGcNomSolicitudesLineasTbl percepcionLinea = new RnGcNomSolicitudesLineasTbl();

                        percepcionLinea.setSolicitudTrabajadorId(trabajadorSol.getId());
                        percepcionLinea.setPercepcionId(percepcionAux);
                        percepcionLinea.setTotalGravado(new BigDecimal(getValorCelda(row.getCell(7))));
                        percepcionLinea.setTotalExento(new BigDecimal(getValorCelda(row.getCell(8))));
                        percepcionLinea.setTipoClave(clavePercepcion);
                        percepcionLinea.setTipoConcepto(getValorCelda(row.getCell(6)));
                        percepcionLinea.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
                        percepcionLinea.setFechaCreacion(new Date());
                        percepcionLinea.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                        percepcionLinea.setUltimaFechaActualizacion(new Date());

                        solicitudesLineasFacade.refreshFromDB(percepcionLinea);
                        System.out.println("✅ Percepción guardada para No. de trabajador" + noEmpleado);
                    } catch (Exception e) {
                        System.out.println("⚠️ Error al guardar percepción fila " + (fila + 1) + ": " + e.getMessage());
                    }
                }

                // === DEDUCCIÓN NORMAL ===
                String claveDeduccionRaw = getValorCelda(row.getCell(10)).trim();
                String claveDeduccion;

                if (claveDeduccionRaw.matches("\\d+(\\.0+)?")) {
                    // Si es un número como 3 o 3.0 o 003.00
                    Double valor = Double.parseDouble(clavePercepcionRaw);
                    claveDeduccion = String.format("%03d", valor.intValue());
                } else {
                    // Si es un código alfanumérico u otra cosa
                    claveDeduccion = claveDeduccionRaw;
                }

                System.out.println("✅ Deducción clave : " + claveDeduccion);
                if (!claveDeduccion.isEmpty()) {
                    try {
                        RnGcNomDeduccionesTbl deduccionAux = deduccionFacade.obtenerXClave(claveDeduccion);
                        RnGcNomSolicitudesLineasTbl deduccionLinea = new RnGcNomSolicitudesLineasTbl();

                        deduccionLinea.setSolicitudTrabajadorId(trabajadorSol.getId());
                        deduccionLinea.setDeduccionId(deduccionAux);
                        deduccionLinea.setTotalGravado(new BigDecimal(getValorCelda(row.getCell(12))));
                        deduccionLinea.setTipoClave(claveDeduccion);
                        deduccionLinea.setTipoConcepto(getValorCelda(row.getCell(11)));
                        deduccionLinea.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
                        deduccionLinea.setFechaCreacion(new Date());
                        deduccionLinea.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                        deduccionLinea.setUltimaFechaActualizacion(new Date());

                        solicitudesLineasFacade.refreshFromDB(deduccionLinea);
                        System.out.println("✅ Deducción guardada para No. de trabajador" + noEmpleado);
                    } catch (Exception e) {
                        System.out.println("⚠️ Error al guardar deducción fila " + (fila + 1) + ": " + e.getMessage());
                    }
                }

                // === DEDUCCIÓN ADICIONAL (O-R / columnas 14-18) ===
                if (!esCeldaVacia(row.getCell(14))) {
                    try {

                        String claveDeduccionAdicionalRaw = getValorCelda(row.getCell(16)).trim();
                        String claveDeduccionAdicional;

                        if (claveDeduccionAdicionalRaw.matches("\\d+(\\.0+)?")) {
                            // Si es un número como 3 o 3.0 o 003.00
                            Double valor = Double.parseDouble(claveDeduccionAdicionalRaw);
                            claveDeduccionAdicional = String.format("%03d", valor.intValue());
                        } else {
                            // Si es un código alfanumérico u otra cosa
                            claveDeduccionAdicional = claveDeduccionAdicionalRaw;
                        }

                        System.out.println("✅ Deducción clave : " + claveDeduccionAdicional);
                        if (!claveDeduccionAdicional.isEmpty()) {
                            RnGcNomDeduccionesTbl deduccionExtra = deduccionFacade.obtenerXClave(claveDeduccionAdicional);
                            RnGcNomSolicitudesLineasTbl deduccionLineaExtra = new RnGcNomSolicitudesLineasTbl();

                            deduccionLineaExtra.setSolicitudTrabajadorId(trabajadorSol.getId());
                            deduccionLineaExtra.setDeduccionId(deduccionExtra);
                            deduccionLineaExtra.setTotalGravado(new BigDecimal(getValorCelda(row.getCell(18))));
                            deduccionLineaExtra.setTipoClave("ADICIONAL");
                            deduccionLineaExtra.setTipoConcepto(getValorCelda(row.getCell(17)));
                            deduccionLineaExtra.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
                            deduccionLineaExtra.setFechaCreacion(new Date());
                            deduccionLineaExtra.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                            deduccionLineaExtra.setUltimaFechaActualizacion(new Date());

                            solicitudesLineasFacade.refreshFromDB(deduccionLineaExtra);
                            System.out.println("✅ Deducción adicional guardada para No. de trabajador" + noEmpleado);
                        }
                    } catch (Exception e) {
                        System.out.println("⚠️ Error al guardar deducción adicional fila " + (fila + 1) + ": " + e.getMessage());
                    }
                }

                // === TIPO DE INCAPACIDAD (U-X / columnas 14-18) ===
                if (!esCeldaVacia(row.getCell(20))) {
                    try {

                        String claveTipoIncapacidadRaw = getValorCelda(row.getCell(20)).trim();
                        String claveTipoIncapacidad;

                        if (claveTipoIncapacidadRaw.matches("\\d+(\\.0+)?")) {
                            // Si es un número como 3 o 3.0 o 003.00
                            Double valor = Double.parseDouble(claveTipoIncapacidadRaw);
                            claveTipoIncapacidad = String.format("%02d", valor.intValue());
                        } else {
                            // Si es un código alfanumérico u otra cosa
                            claveTipoIncapacidad = claveTipoIncapacidadRaw;
                        }

                        System.out.println("✅ Incapacidad clave : " + claveTipoIncapacidad);
                        if (!claveTipoIncapacidad.isEmpty()) {
                            RnGcNomTipoincapacidadTbl tipoIncapacidad = incapacidadFacade.obtenerXClave(claveTipoIncapacidad);
                            RnGcNomSolicitudesLineasTbl tipoIncapacidadExtra = new RnGcNomSolicitudesLineasTbl();

                            tipoIncapacidadExtra.setSolicitudTrabajadorId(trabajadorSol.getId());
                            tipoIncapacidadExtra.setTipoIncapacidadId(tipoIncapacidad);
                            tipoIncapacidadExtra.setTotalGravado(new BigDecimal(getValorCelda(row.getCell(23))));
                            tipoIncapacidadExtra.setTipoClave("INCAPACIDAD");
                            String valorCelda = getValorCelda(row.getCell(21));
                            if (valorCelda != null && !valorCelda.trim().isEmpty()) {
                                try {
                                    tipoIncapacidadExtra.setDiasIncapacidad(Integer.parseInt(valorCelda.trim()));
                                } catch (NumberFormatException e) {
                                    System.out.println("❌ Error al convertir a entero: " + valorCelda);
                                    tipoIncapacidadExtra.setDiasIncapacidad(0); // o maneja como prefieras
                                }
                            }
                            tipoIncapacidadExtra.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
                            tipoIncapacidadExtra.setFechaCreacion(new Date());
                            tipoIncapacidadExtra.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                            tipoIncapacidadExtra.setUltimaFechaActualizacion(new Date());

                            solicitudesLineasFacade.refreshFromDB(tipoIncapacidadExtra);
                            System.out.println("✅ Deducción adicional guardada para No. de trabajador" + noEmpleado);
                        }
                    } catch (Exception e) {
                        System.out.println("⚠️ Error al guardar deducción adicional fila " + (fila + 1) + ": " + e.getMessage());
                    }
                }
            }

            totalPercepcionDeduccion(listaSolicitudes);

        } catch (Exception e) {
            System.out.println("❌ Error general en PercepDeduc: " + e.getMessage());
        }
    }

    private void guardarPlantilla(FileUploadEvent event) {
        try {
            XSSFWorkbook excel = new XSSFWorkbook(event.getFile().getInputstream());
            XSSFSheet sheet = excel.getSheetAt(0);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar fechaActual = Calendar.getInstance();

            // Obtener datos generales de encabezado
            Row row;
            Date fechaPago = null;
            for (int fila = 1; fila <= 11; fila++) {
                row = sheet.getRow(fila);
                if (row != null) {
                    Cell cell = row.getCell(3);
                    if (cell != null && !esCeldaVacia(cell)) {
                        switch (fila) {
                            case 1: // D2
                                // // El RFC patronal podría estar aquí, pero no lo estás usando
                                //solicitud.setRegistroPatronal(cell.getStringCellValue());
                                break;
                            case 2: // D3
                                solicitud.setRegistroPatronal(cell.getStringCellValue());
                                break;
                            case 3: // D4
                                String nombreNomina = cell.getStringCellValue();
                                selected.setNombreNomina(nombreNomina);
                                //periodoNomina.setNombrePeriodo(nombreNomina);
                                solicitud.setNombreSolicitud(nombreNomina);
                                break;
                            case 5: // D6
                                tipoNomina = tipoNominaFacade.obtenerXDescripcion(cell.getStringCellValue());
                                selected.setTipoNominaId(tipoNomina);
                                break;
                            case 6: // 7

                                fechaPago = sdf.parse(obtenerValorCeldaComoTexto(cell));
                                break;
                            case 7: // D8

                                String valorFechaInicial = obtenerValorCeldaComoTexto(cell);
                                System.out.println("📅 Fecha leída de celda: " + valorFechaInicial);

                                periodoNomina.setFechaInicio(sdf.parse(valorFechaInicial));
                                break;
                            case 8: // D9
                                String valorFechaFinal = obtenerValorCeldaComoTexto(cell);
                                System.out.println("📅 Fecha leída de celda: " + valorFechaFinal);

                                periodoNomina.setFechaFin(sdf.parse(valorFechaFinal));
                                break;
                            case 9: // D10
                                // Días pagados ya se calculan después con fechas, puedes usar si lo necesitas
                                break;
                            case 10: // D11
                                periodicidadPago = periodicidadPagoFacade.obtenerXDescripcion(cell.getStringCellValue());
                                // Imprimir información del objeto obtenido
                                if (periodicidadPago != null) {
                                    System.out.println("✅ PeriodicidadPago encontrada:");
                                    System.out.println(" - ID: " + periodicidadPago.getId());
                                    System.out.println(" - Descripción: " + periodicidadPago.getDescripcion()); // ajusta según tu entidad
                                } else {
                                    System.out.println("⚠️ No se encontró ninguna periodicidadPago con descripción: " + cell.getStringCellValue());
                                }

                                selected.setPeriodicidadPagoId(periodicidadPago);
                                break;
                            case 11: // D12
                                if (cell.getStringCellValue() != null) {
                                    origenRecurso = origenRecursoFacade.obtenerXDescripcion(cell.getStringCellValue());
                                    selected.setOrigenRecursoId(origenRecurso);
                                    break;
                                }
                        }
                    }
                }
            }

            // Guardar Nomina, Periodo y Solicitud
            selected.setEsConfidencial("S");
            selected.setPatronId(usuarioFirmado.obtenerIdUsuario());
            selected = getFacade().refreshFromDB(selected);

// Verificar si fue persistido correctamente
            if (selected != null && selected.getId() != null) {
                System.out.println("✅ Nómina guardada correctamente. ID: " + selected.getId());
            } else {
                System.out.println("⚠️ Nómina no fue guardada correctamente o el ID es nulo.");
            }

            System.out.println("Generando periodo");

            SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");

            String nombrePeriodo = "Periodo "
                    + sdfDate.format(periodoNomina.getFechaInicio())
                    + " - "
                    + sdfDate.format(periodoNomina.getFechaFin());
            System.out.println("Nombre del período generado: " + nombrePeriodo);
            periodoNomina.setNombrePeriodo(nombrePeriodo);

            periodoNomina.setAnioPeriodo(String.valueOf(fechaActual.get(Calendar.YEAR)));
            periodoNomina.setNumMesPeriodo(fechaActual.get(Calendar.MONTH) + 1);
            System.out.println("📅 Año del periodo asignado: " + periodoNomina.getAnioPeriodo());
            System.out.println("📆 Mes del periodo asignado: " + periodoNomina.getNumMesPeriodo());
            periodoNomina.setEstatus("A");
            periodoNomina.setNominaId(selected);
            periodoNomina = periodoNominaFacade.refreshFromDB(periodoNomina);

            if (periodoNomina != null && periodoNomina.getId() != null) {
                System.out.println("✅ Periodo de nómina guardado correctamente. ID: " + periodoNomina.getId());
                System.out.println("🗓️ Fechas del periodo: " + periodoNomina.getFechaInicio() + " al " + periodoNomina.getFechaFin());
                System.out.println("📅 Año: " + periodoNomina.getAnioPeriodo() + ", Mes: " + periodoNomina.getNumMesPeriodo());
            } else {
                System.out.println("❌ Error: No se guardó el periodo de nómina correctamente.");
            }

            solicitud.setPatronId(usuarioFirmado.obtenerIdUsuario());
            solicitud.setEstatus("A");
            solicitud.setNominaId(selected.getId());
            solicitud.setPeriodoNominaId(periodoNomina);

            solicitud.setRegistroPatronal(solicitud.getRegistroPatronal());
            solicitud.setNombreSolicitud(nombrePeriodo);

            solicitud = solicitudFacade.refreshFromDB(solicitud);

            if (solicitud != null && solicitud.getId() != null) {
                System.out.println("✅ Solicitud guardada correctamente. ID: " + solicitud.getId());
                System.out.println("🔗 Asociada a nómina ID: " + solicitud.getNominaId());
                System.out.println("📅 Periodo asociado ID: " + (solicitud.getPeriodoNominaId() != null ? solicitud.getPeriodoNominaId().getId() : "N/A"));
            } else {
                System.out.println("❌ Error: No se guardó la solicitud correctamente.");
            }

            // Leer y guardar trabajadores desde fila 17
            int filaActual = 16;
            listaSoliTrabajadores = new ArrayList<>();
            Long diasPagados = ((periodoNomina.getFechaFin().getTime() - periodoNomina.getFechaInicio().getTime()) / (60 * 60 * 24 * 1000)) + 1;

            while (true) {
                Row fila = sheet.getRow(filaActual);
                if (fila == null || esCeldaVacia(fila.getCell(0))) {
                    break;
                }

                try {
                    Cell clave = fila.getCell(0);
                    if (!esCeldaVacia(clave)) {
                        String noTrabajador = String.valueOf(clave.getStringCellValue());
                        inicializarSolTrabajador();
                        trabajador = trabajadorFacade.obtenerPorNoTrabajador(noTrabajador, usuarioFirmado.obtenerIdUsuario());
                        if (trabajador != null) {
                            System.out.println("✅ Trabajador encontrado:");
                            System.out.println(" - ID: " + trabajador.getId());
                            System.out.println(" - Nombre: " + trabajador.getNombre());
                            System.out.println(" - Apellido paterno: " + trabajador.getApPaterno());
                            System.out.println(" - Apellido materno: " + trabajador.getApMaterno());
                            System.out.println(" - No. Trabajador: " + trabajador.getNoTrabajador());
                        } else {
                            System.out.println("❌ -- No se encontró el trabajador con número: " + noTrabajador);
                        }
                        soliTrabajador.setTrabajadorId(trabajador);
                        soliTrabajador.setSolicitudId(solicitud);
                        soliTrabajador.setDiasPagados(diasPagados.intValue());
                        soliTrabajador.setFechaPago(fechaPago);
                        soliTrabajador.setSdi(new BigDecimal(trabajador.getSdi()));
                        soliTrabajador.setEstatus("A");
                        soliTrabajador = soliTrabajadorFacade.refreshFromDB(soliTrabajador);

                        listaSoliTrabajadores.add(soliTrabajador);
                    }
                } catch (Exception e) {
                    System.out.println("Error al procesar trabajador en fila " + (filaActual + 1) + ": " + e.getMessage());
                }
                filaActual++;
            }

            // Procesar percepciones y deducciones
            PercepDeducNewVersion(event, listaSoliTrabajadores);

            System.out.println("Nomina: " + selected.getId() + " | Periodo: " + periodoNomina.getId() + " | Solicitud: " + solicitud.getId());

        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Error al procesar plantilla: " + ex.getMessage());
        }
    }

    private String obtenerClaveFormateada(Cell cell, int largo) {
        String clave = "";
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            int claveNum = (int) cell.getNumericCellValue();
            clave = String.format("%0" + largo + "d", claveNum);
        } else {
            clave = cell.getStringCellValue().trim();
            if (clave.matches("\\d+")) {
                clave = String.format("%0" + largo + "d", Integer.parseInt(clave));
            }
        }
        return clave;
    }

    private List<String> validarCampos(FileUploadEvent event) {

        Integer usuarioId = usuarioFirmado.obtenerIdUsuario();
        List<String> errores = new ArrayList<>();
        try {
            XSSFWorkbook excel = new XSSFWorkbook(event.getFile().getInputstream());
            XSSFSheet sheet = excel.getSheetAt(0);

            // 1. VALIDAR ENCABEZADO (D2, D3, D4, D6, D7, D8, D9, D10, D11)
            int[] filasCabecera = {1, 2, 3, 5, 6, 7, 8, 9, 10}; // índice base 0
            for (int fila : filasCabecera) {
                Row row = sheet.getRow(fila);
                Cell cell = (row != null) ? row.getCell(3) : null;
                if (cell == null || cell.toString().trim().isEmpty()) {
                    errores.add("La celda D" + (fila + 1) + " no debe estar vacía.");
                }

                // Validar que D7, D8, D9 sean fechas
                if (fila == 6 || fila == 7 || fila == 8) {
                    if (!esFecha(cell)) {
                        errores.add("La celda D" + (fila + 1) + " debe contener una fecha.");
                        System.out.println("Valor en D" + (fila + 1) + ": " + obtenerValorCeldaComoTexto(cell));
                    }
                }
            }

            // Validar que la celda D6 tenga un valor válido del catálogo de tipo de nómina
            Row filaTipoNomina = sheet.getRow(5); // fila 6 (índice 5)
            if (filaTipoNomina != null) {
                Cell celdaTipoNomina = filaTipoNomina.getCell(3); // columna D
                if (!esCeldaVacia(celdaTipoNomina)) {
                    String valorTipoNomina = celdaTipoNomina.getStringCellValue().trim();
                    if (tipoNominaFacade.obtenerXDescripcion(valorTipoNomina) == null) {
                        errores.add("El valor '" + valorTipoNomina + "' en la celda D6 no existe en el catálogo de tipos de nómina.");
                        return errores; // Cortar validación aquí si no existe
                    }
                } else {
                    errores.add("La celda D6 está vacía.");
                    return errores;
                }
            }

            // Validar que la celda D10 tenga un valor válido del catálogo de periodicidad de pago
            Row filaPeriodicidadPago = sheet.getRow(10); // fila 11 (índice 10)
            if (filaPeriodicidadPago != null) {
                Cell celdaPeriodicidadPago = filaPeriodicidadPago.getCell(3); // columna D
                if (!esCeldaVacia(celdaPeriodicidadPago)) {
                    String valorPeriodicidadPago = celdaPeriodicidadPago.getStringCellValue().trim();
                    if (periodicidadPagoFacade.obtenerXDescripcion(valorPeriodicidadPago) == null) {
                        errores.add("El valor '" + valorPeriodicidadPago + "' en la celda D10 no existe en el catálogo de tipos de nómina.");
                        return errores; // Cortar validación aquí si no existe
                    }
                } else {
                    errores.add("La celda D10 está vacía.");
                    return errores;
                }
            }

            // Validar que la celda D11 tenga un valor válido del catálogo de origen de recurso
            Row filaOrigenRecurso = sheet.getRow(11); // fila 11 (índice 10)
            if (filaOrigenRecurso != null) {
                Cell celdaOrigenRecurso = filaOrigenRecurso.getCell(3); // columna D
                if (!esCeldaVacia(celdaOrigenRecurso)) {
                    String valorOrigenRecurso = celdaOrigenRecurso.getStringCellValue().trim();
                    if (origenRecursoFacade.obtenerXDescripcion(valorOrigenRecurso) == null) {
                        errores.add("El valor '" + valorOrigenRecurso + "' en la celda D11 no existe en el catálogo de tipos de nómina.");
                        return errores; // Cortar validación aquí si no existe
                    }
                } else {
                    errores.add("La celda D11 está vacía.");
                    return errores;
                }
            }

            // 2. VALIDAR DATOS DE EMPLEADOS desde fila 17 (índice 16)
            int filaActual = 16;
            while (true) {
                Row row = sheet.getRow(filaActual);
                if (row == null || esCeldaVacia(row.getCell(0))) {
                    break;
                }

                validarCeldaNoVacia(row, 0, filaActual, "No. Empleado", errores);
                validarCeldaNoVacia(row, 5, filaActual, "Clave percepción", errores);
                validarCeldaNoVacia(row, 6, filaActual, "Concepto percepción", errores);
                validarCeldaNoVacia(row, 7, filaActual, "Importe gravado", errores);
                validarCeldaNoVacia(row, 8, filaActual, "Importe exento", errores);
                validarCeldaNoVacia(row, 10, filaActual, "Clave deduccion", errores);
                validarCeldaNoVacia(row, 11, filaActual, "Concepto deducción", errores);
                validarCeldaNoVacia(row, 12, filaActual, "Importe deducción", errores);

                // Validar tipo numérico en columnas A, H, I, L
                validarEsNumero(row, 7, filaActual, "Importe gravado", errores);
                validarEsNumero(row, 8, filaActual, "Importe exento", errores);

                // Valisar que No. Trabajador exista
                validarNoEmpleadoExistente(row, 0, filaActual, usuarioId, errores);

                // VALIDAR que la clave de percepción exista
                Cell celdaClavePercepcion = row.getCell(5); // Columna F (índice 5)
                if (!esCeldaVacia(celdaClavePercepcion)) {
                    try {
                        String clave = "";

                        if (celdaClavePercepcion.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            int claveNum = (int) celdaClavePercepcion.getNumericCellValue();
                            clave = String.format("%03d", claveNum); // convierte 1 → "001", 53 → "053"
                            System.out.println("Consultando en catálogo: clave de percepción (num) = " + clave);
                        } else {
                            clave = celdaClavePercepcion.getStringCellValue().trim();
                            if (clave.matches("\\d+")) {
                                clave = String.format("%03d", Integer.parseInt(clave)); // también aplica si es texto como "4"
                            }
                            System.out.println("Consultando en catálogo: clave de percepción (str) = " + clave);
                        }

                        if (percepcionFacade.obtenerXClave(clave) == null) {
                            errores.add("Fila " + (filaActual + 1) + ": La clave de percepción '" + clave + "' no existe en el catálogo.");
                            return errores;
                        }
                    } catch (Exception e) {
                        errores.add("Fila " + (filaActual + 1) + ": Error al validar la clave de percepción. Verifica que sea un valor válido.");
                        return errores;
                    }
                }

// VALIDAR que la clave de decuccion exista
                Cell celdaClaveDeduccion = row.getCell(10); // Columna F (índice 5)
                if (!esCeldaVacia(celdaClaveDeduccion)) {
                    try {
                        String clave = "";

                        if (celdaClaveDeduccion.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            int claveNum = (int) celdaClaveDeduccion.getNumericCellValue();
                            clave = String.format("%03d", claveNum); // convierte 1 → "001", 53 → "053"
                            System.out.println("Consultando en catálogo: clave de percepción (num) = " + clave);
                        } else {
                            clave = celdaClaveDeduccion.getStringCellValue().trim();
                            if (clave.matches("\\d+")) {
                                clave = String.format("%03d", Integer.parseInt(clave)); // también aplica si es texto como "4"
                            }
                            System.out.println("Consultando en catálogo: clave de deduccion (str) = " + clave);
                        }

                        if (deduccionFacade.obtenerXClave(clave) == null) {
                            errores.add("Fila " + (filaActual + 1) + ": La clave de deducción '" + clave + "' no existe en el catálogo.");
                            return errores;
                        }
                    } catch (Exception e) {
                        errores.add("Fila " + (filaActual + 1) + ": Error al validar la clave de deducción. Verifica que sea un valor válido.");
                        return errores;
                    }
                }

                filaActual++;
            }

            // 3. VALIDAR DEDUCCIONES si columna O (índice 14) tiene datos
            filaActual = 16;
            while (true) {
                Row row = sheet.getRow(filaActual);
                if (row == null || esCeldaVacia(row.getCell(14))) {
                    break;
                }

                validarCeldaNoVacia(row, 14, filaActual, "No de Empleado", errores);
                validarCeldaNoVacia(row, 16, filaActual, "Clave SAT deducción", errores);
                validarCeldaNoVacia(row, 17, filaActual, "Concepto SAT deducción", errores);
                validarCeldaNoVacia(row, 18, filaActual, "Importe de deducción", errores);

                // Validar tipo numérico en columnas O, S
                validarEsNumero(row, 14, filaActual, "No de Empleado", errores);
                validarEsNumero(row, 16, filaActual, "Clave SAT deducción", errores);
                validarEsNumero(row, 18, filaActual, "Importe de deducción", errores);

                // Valisar que No. Trabajador exista
                validarNoEmpleadoExistente(row, 14, filaActual, usuarioId, errores);

                // VALIDAR que la clave de otro pago
                Cell celdaClaveOtroPago = row.getCell(10); // Columna F (índice 5)
                if (!esCeldaVacia(celdaClaveOtroPago)) {
                    try {
                        String clave = "";

                        if (celdaClaveOtroPago.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            int claveNum = (int) celdaClaveOtroPago.getNumericCellValue();
                            clave = String.format("%03d", claveNum); // convierte 1 → "001", 53 → "053"
                            System.out.println("Consultando en catálogo: clave de otro pago (num) = " + clave);
                        } else {
                            clave = celdaClaveOtroPago.getStringCellValue().trim();
                            if (clave.matches("\\d+")) {
                                clave = String.format("%03d", Integer.parseInt(clave)); // también aplica si es texto como "4"
                            }
                            System.out.println("Consultando en catálogo: clave de otro pago (str) = " + clave);
                        }

                        if (otroPagoFacade.obtenerXClave(clave) == null) {
                            errores.add("Fila " + (filaActual + 1) + ": La clave de otro pago '" + clave + "' no existe en el catálogo.");
                            return errores;
                        }
                    } catch (Exception e) {
                        errores.add("Fila " + (filaActual + 1) + ": Error al validar la clave de otro pago. Verifica que sea un valor válido.");
                        return errores;
                    }
                }

                filaActual++;
            }

            // 4. VALIDAR INCAPACIDAD si columna U (índice 20) tiene datos
            filaActual = 16;
            while (true) {
                Row row = sheet.getRow(filaActual);
                if (row == null || esCeldaVacia(row.getCell(20))) {
                    break;
                }

                validarCeldaNoVacia(row, 20, filaActual, "No de Empleado", errores);
                validarCeldaNoVacia(row, 21, filaActual, "No de días", errores);
                validarCeldaNoVacia(row, 22, filaActual, "Tipo", errores);
                validarCeldaNoVacia(row, 23, filaActual, "Importe", errores);

                // Validar tipo numérico en columnas U, V, X
                validarEsNumero(row, 20, filaActual, "No de Empleado", errores);
                validarEsNumero(row, 21, filaActual, "No de días", errores);
                validarEsNumero(row, 23, filaActual, "Importe", errores);

                // Valisar que No. Trabajador exista
                validarNoEmpleadoExistente(row, 20, filaActual, usuarioId, errores);

                // VALIDAR que la clave de incapacidad
                Cell celdaClaveIncapacidad = row.getCell(10); // Columna F (índice 5)
                if (!esCeldaVacia(celdaClaveIncapacidad)) {
                    try {
                        String clave = "";

                        if (celdaClaveIncapacidad.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            int claveNum = (int) celdaClaveIncapacidad.getNumericCellValue();
                            clave = String.format("%02d", claveNum); // convierte 1 → "001", 53 → "053"
                            System.out.println("Consultando en catálogo: clave de incapacidad (num) = " + clave);
                        } else {
                            clave = celdaClaveIncapacidad.getStringCellValue().trim();
                            if (clave.matches("\\d+")) {
                                clave = String.format("%02d", Integer.parseInt(clave)); // también aplica si es texto como "4"
                            }
                            System.out.println("Consultando en catálogo: clave de incapacidad (str) = " + clave);
                        }

                        if (incapacidadFacade.obtenerXClave(clave) == null) {
                            errores.add("Fila " + (filaActual + 1) + ": La clave de incapacidad '" + clave + "' no existe en el catálogo.");
                            return errores;
                        }
                    } catch (Exception e) {
                        errores.add("Fila " + (filaActual + 1) + ": Error al validar la clave de incapacidad. Verifica que sea un valor válido.");
                        return errores;
                    }
                }

                filaActual++;
            }

        } catch (Exception ex) {
            errores.add("Error al leer el archivo Excel: " + ex.getMessage());
        }
        return errores;
    }

    private boolean validarNoEmpleadoExistente(Row row, Integer col, int filaActual, Integer usuarioId, List<String> errores) {
        Cell celdaNoEmpleado = row.getCell(col); // Columna A

        if (!esCeldaVacia(celdaNoEmpleado)) {
            try {
                int noEmpleado;

                if (celdaNoEmpleado.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    noEmpleado = (int) celdaNoEmpleado.getNumericCellValue();
                } else {
                    String valorTexto = celdaNoEmpleado.getStringCellValue().trim();
                    noEmpleado = Integer.parseInt(valorTexto);
                }

                String noEmpleadoString = String.valueOf(noEmpleado);
                System.out.println("Fila " + (filaActual + 1) + ": No. Empleado leído = " + noEmpleadoString);
                System.out.println("Consultando en catálogo: número de empleado = " + noEmpleadoString);

                if (trabajadorFacade.obtenerPorNoTrabajador(noEmpleadoString, usuarioId) == null) {
                    errores.add("Fila " + (filaActual + 1) + ": El número de empleado '" + noEmpleadoString + "' no existe en el catálogo.");
                    return false;
                }

            } catch (Exception e) {
                errores.add("Fila " + (filaActual + 1) + ": El número de empleado debe ser numérico.");
                return false;
            }
        }
        return true;
    }

    private void validarCeldaNoVacia(Row row, int colIndex, int filaIndex, String campo, List<String> errores) {
        Cell cell = row.getCell(colIndex);
        if (cell == null || cell.toString().trim().isEmpty()) {
            errores.add("Fila " + (filaIndex + 1) + ", columna " + (colIndex + 1) + " (" + campo + ") está vacía.");
        }
    }

    private String obtenerValorCeldaComoTexto(Cell cell) {
        if (cell == null) {
            return "(vacía)";
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case Cell.CELL_TYPE_BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            case Cell.CELL_TYPE_BLANK:
                return "(en blanco)";
            default:
                return "(tipo desconocido)";
        }
    }

    private boolean esCeldaVacia(Cell cell) {
        return cell == null || cell.toString().trim().isEmpty();
    }

    private boolean esNumero(Cell cell) {
        if (cell == null) {
            return false;
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                return !DateUtil.isCellDateFormatted(cell); // Asegura que no sea fecha
            case Cell.CELL_TYPE_STRING:
                try {
                    Double.parseDouble(cell.getStringCellValue().trim());
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            default:
                return false;
        }
    }

    private String getValorCelda(Cell cell) {
        if (cell == null) {
            return "";
        }
        try {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    return cell.getStringCellValue().trim();
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return new SimpleDateFormat("dd/MM/yyyy").format(cell.getDateCellValue());
                    } else {
                        return String.valueOf(cell.getNumericCellValue());
                    }
                case Cell.CELL_TYPE_BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case Cell.CELL_TYPE_FORMULA:
                    return cell.getCellFormula();
                case Cell.CELL_TYPE_BLANK:
                    return "";
                default:
                    return "";
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error al obtener valor de celda: " + e.getMessage());
            return "";
        }
    }

    private boolean esFecha(Cell cell) {
        if (cell == null) {
            return false;
        }

        try {
            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                return DateUtil.isCellDateFormatted(cell);
            } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                String valor = cell.getStringCellValue().trim();
                if (valor.isEmpty()) {
                    return false;
                }

                // Intenta parsear manualmente
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(false); // Validación estricta
                sdf.parse(valor);
                return true;
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    private void validarEsNumero(Row row, int colIndex, int filaIndex, String campo, List<String> errores) {
        Cell cell = row.getCell(colIndex);
        if (!esNumero(cell)) {
            errores.add("Fila " + (filaIndex + 1) + ", columna " + (colIndex + 1) + " (" + campo + ") debe ser numérico.");
        }
    }
    ///

    //////
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

    public void descargaPlantilla() {
        try {
            File plantilla = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/Plantilla_Nomina.xlsx"));
            byte[] aux = FileUtils.readFileToByteArray(plantilla);
            InputStream streamPlantilla = new ByteArrayInputStream(aux);
            downLoadFile = new DefaultStreamedContent(streamPlantilla,
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    "plantilla_nomina_" + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()).concat(".xlsx"));
        } catch (Exception ex) {
            System.err.println("Ocurrio un error al descargar la plantilla de nomina");
        }
    }

    public void eliminarDatos() {
        //List<>
        RnGcNomPeriodonominaTbl periodoNomina = new RnGcNomPeriodonominaTbl();
        RnGcNomSolicitudesTbl solicitud = new RnGcNomSolicitudesTbl();
        List<RnGcNomSolicitudTrabajadorTbl> solioTrabajador = new ArrayList<>();
        List<RnGcNomSolicitudesLineasTbl> solicitudLineas = new ArrayList<>();

        periodoNomina = periodoNominaFacade.obtenerXNomina(selected);
        solicitud = solicitudFacade.obtenerXNomina(selected.getId());
        solioTrabajador = soliTrabajadorFacade.obtenerXSolicitud(solicitud);
        if (solioTrabajador != null && solioTrabajador.size() > 0) {
            solicitudLineas = solicitudesLineasFacade.obtenerXSoliTrabajador(solioTrabajador.get(0).getId(), solioTrabajador.get(solioTrabajador.size() - 1).getId());
        }
        System.out.println("solicitudLineas: " + solicitudLineas);
        System.out.println("solioTrabajador: " + solioTrabajador);
        System.out.println("solicitud: " + solicitud);
        System.out.println("periodoNomina: " + periodoNomina);
        System.out.println("selected: " + selected);

        for (RnGcNomSolicitudesLineasTbl soliLineas : solicitudLineas) {
            solicitudesLineasFacade.remove(soliLineas);
        }
        for (RnGcNomSolicitudTrabajadorTbl soliTrabajador : solioTrabajador) {
            soliTrabajadorFacade.remove(soliTrabajador);
        }
        solicitudFacade.remove(solicitud);
        periodoNominaFacade.remove(periodoNomina);
        getFacade().remove(selected);
        selected = null;
    }

}
