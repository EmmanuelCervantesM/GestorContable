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
import java.math.BigDecimal;
import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
import mx.com.rocketnegocios.entities.RnGcNomRiesgopuestoTbl;
import mx.com.rocketnegocios.entities.RnGcNomTipocontratoTbl;
import mx.com.rocketnegocios.entities.RnGcNomTipojornadaTbl;
import mx.com.rocketnegocios.entities.RnGcNomTiporegimenTbl;
import mx.com.rocketnegocios.entities.RnGcRegimenfiscalTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
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
    @EJB
    private mx.com.rocketnegocios.beans.RnGcNomTiporegimenTblFacade ejbFacadeRegimen;
    @EJB
    private mx.com.rocketnegocios.beans.RnGcNomEstadosTblFacade ejbFacadeEstado;
    @EJB
    private mx.com.rocketnegocios.beans.RnGcNomRiesgopuestoTblFacade ejbFacadeRiesgoPuesto;
    @EJB
    private mx.com.rocketnegocios.beans.RnGcNomTipocontratoTblFacade ejbFacadeTipoContrato;
    @EJB
    private mx.com.rocketnegocios.beans.RnGcNomTipojornadaTblFacade ejbFacadeJornada;
    @EJB
    private mx.com.rocketnegocios.beans.RnGcNomTiporegimencontratacionTblFacade ejbFacadeTipoContratacion;

    
    private String tipoEmpleadoSeleccionado;
    
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
    
public long getDiasDesdeFechaInicio() {
    if (selected != null && selected.getFechaInicio() != null) {
        LocalDate fechaInicio = selected.getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate hoy = LocalDate.now();
        return ChronoUnit.DAYS.between(fechaInicio, hoy);
    }
    return 0;
}

public String obtenerDescripcionTipoPersona(String tipoPersona) {
    if ("02".equals(tipoPersona)) {
        return tipoPersona + " - Salariado";
    } else if ("03".equals(tipoPersona)) {
        return tipoPersona + " - Asimilado";
    } else {
        return tipoPersona; // Si no es ni 02 ni 03, simplemente devuelve el valor tal cual.
    }
}
    public StreamedContent getDownLoadFile() {
        return downLoadFile;
    }

    public boolean esAsalariado() {
      return "Asalariado".equalsIgnoreCase(this.selected.getTipoEmpleado());
}

public boolean esSalariado() {
    return "Salariado".equalsIgnoreCase(this.selected.getTipoEmpleado());
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
        
        selected.setNoTrabajador(ejbFacade.obtenerSiguienteNoTrabajador(usuarioFirmado.obtenerIdUsuario()));
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcTrabajadoresTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
             getItems();
        }
    }

    // Este método puede ser llamado en el <p:ajax> para acciones adicionales si lo necesitas
public void cambiarTipoEmpleado() {
    if (selected != null && selected.getTipoEmpleado() != null) {
        System.out.println("Tipo de empleado cambiado a: " + selected.getTipoEmpleado());
        // Aquí puedes establecer campos, limpiar valores, etc.
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
                    selected.setNombreCompleto((selected.getNombre() + " " + selected.getApPaterno() + " " + selected.getApMaterno()).toUpperCase());
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

    public void leerPlantilla(FileUploadEvent event) throws IOException {
    System.out.println("Archivo recibido: " + event.getFile().getFileName());

    try {
        XSSFWorkbook workbook = new XSSFWorkbook(event.getFile().getInputstream());
        
        XSSFSheet sheet = workbook.getSheet("Trabajadores"); // nombre de la hoja

        if (sheet == null) {
            System.out.println("No se encontró una hoja llamada 'Trabajadores'.");
            return;
        }
            
        
        //int firstRow = sheet.getFirstRowNum();
        int firstRow = 5; // Fila 7 en Excel
        int lastRow = sheet.getLastRowNum();

        for (int i = firstRow + 1; i <= lastRow; i++) { // omitir encabezado
            Row row = sheet.getRow(i);
            if (row == null) continue;

            String sysAsim = getCellValue(row.getCell(0)).trim();
            String nombre = getCellValue(row.getCell(1)).trim();
            String apellidoPaterno = getCellValue(row.getCell(2)).trim();
            String apellidoMaterno = getCellValue(row.getCell(3)).trim();
            String curp = getCellValue(row.getCell(4)).trim();
            String rfc = getCellValue(row.getCell(5)).trim();
            String regimenFiscal = getCellValue(row.getCell(6)).trim();
            String nss = getCellValue(row.getCell(7)).trim();
            String fechaInicioLaboral = getCellValue(row.getCell(8)).trim();
            String salarioBase = getCellValue(row.getCell(9)).trim();
            String salarioDiarioIntegrado = getCellValue(row.getCell(10)).trim();
            String entidadFederativa = getCellValue(row.getCell(11)).trim();
            String codigoPostal = getCellValue(row.getCell(12)).trim();
            String regimenContratacion = getCellValue(row.getCell(13)).trim();
            String riesgoPuesto = getCellValue(row.getCell(14)).trim();
            String tipoContrato = getCellValue(row.getCell(15)).trim();
            String tipoJornada = getCellValue(row.getCell(16)).trim();
            String sindicalizado = getCellValue(row.getCell(17)).trim();
            String email  = getCellValue(row.getCell(18)).trim();
            String observaciones = getCellValue(row.getCell(19)).trim();
      
           System.out.println("Fila " + (i + 1) + " - Valores:");
            System.out.println(
                "Tipo: " + sysAsim + ", " +
                "Nombre: " + nombre + ", " +
                "Apellido Paterno: " + apellidoPaterno + ", " +
                "Apellido Materno: " + apellidoMaterno + ", " +
                "CURP: " + curp + ", " +
                "RFC: " + rfc + ", " +
                "Régimen Fiscal: " + regimenFiscal + ", " +
                "NSS: " + nss + ", " +
                "Fecha Inicio: " + fechaInicioLaboral + ", " +
                "Salario Base: " + salarioBase + ", " +
                "SDI: " + salarioDiarioIntegrado + ", " +
                "Entidad: " + entidadFederativa + ", " +
                "CP: " + codigoPostal + ", " +
                "Régimen Contratación: " + regimenContratacion + ", " +
                "Riesgo Puesto: " + riesgoPuesto + ", " +
                "Tipo Contrato: " + tipoContrato + ", " +
                "Tipo Jornada: " + tipoJornada + ", " +
                "Sindicalizado: " + sindicalizado + ", " +
                "Email: " + email + ", " +
                "Observaciones: " + observaciones
            ); 
            
            if (sysAsim == null || (!sysAsim.equalsIgnoreCase("Salariado") && !sysAsim.equalsIgnoreCase("Asimilado"))) {
                System.out.println("Trabajador no tiene tipo de trabajador válido");
                continue;
            }
            
            if (sysAsim.equalsIgnoreCase("Salariado")) {
                if (nombre == null || nombre.isEmpty() ||
                    apellidoPaterno == null || apellidoPaterno.isEmpty() ||
                    apellidoMaterno == null || apellidoMaterno.isEmpty() ||
                    curp == null || curp.isEmpty() || curp.length() != 18 ||
                    rfc == null || rfc.isEmpty() || rfc.length() != 13 ||
                    regimenFiscal == null || regimenFiscal.isEmpty() ||
                    nss == null || nss.isEmpty() || nss.length() != 11 ||
                    fechaInicioLaboral == null || fechaInicioLaboral.isEmpty() ||
                    salarioBase == null || salarioBase.isEmpty() ||
                    salarioDiarioIntegrado == null || salarioDiarioIntegrado.isEmpty() ||
                    entidadFederativa == null || entidadFederativa.isEmpty() ||
                    codigoPostal == null || codigoPostal.isEmpty() ||
                    regimenContratacion == null || regimenContratacion.isEmpty() ||
                    riesgoPuesto == null || riesgoPuesto.isEmpty() ||
                    tipoContrato == null || tipoContrato.isEmpty() ||
                    tipoJornada == null || tipoJornada.isEmpty() ||
                    sindicalizado == null || sindicalizado.isEmpty() ||
                    !(sindicalizado.equalsIgnoreCase("si") || sindicalizado.equalsIgnoreCase("no"))) {

                    System.out.println("Trabajador de tipo salariado no cumple con los campos obligatorios");
                    continue;
                }
            }
            
            if (sysAsim.equalsIgnoreCase("Asimilado")) {
                if (nombre == null || nombre.isEmpty() ||
                    apellidoPaterno == null || apellidoPaterno.isEmpty() ||
                    apellidoMaterno == null || apellidoMaterno.isEmpty() ||
                    curp == null || curp.isEmpty() || curp.length() != 18 ||
                    rfc == null || rfc.isEmpty() || rfc.length() != 13 ||
                    regimenFiscal == null || regimenFiscal.isEmpty() ||
                    entidadFederativa == null || entidadFederativa.isEmpty() ||
                    codigoPostal == null || codigoPostal.isEmpty() ||
                    regimenContratacion == null || regimenContratacion.isEmpty() ||
                    tipoContrato == null || tipoContrato.isEmpty()) {

                    System.out.println("Trabajador de tipo asimilado no cumple con los campos obligatorios");
                    continue;
                }
            }
            
            // Validación existencia en base de datos
            long existe = ejbFacade.contarTrabajador(curp, rfc, nss);
            if (existe > 0) {
                System.out.println("Fila " + (i+1) + ": Ya existe trabajador con CURP, RFC o NSS.");
                continue; // Salta inserción
            }
            
            // Valicar que los catalogos exista 
            if (sysAsim.equalsIgnoreCase("Salariado")){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false); // Para que no acepte fechas inválidas como 2023/02/30

                Date fechaInicioLaboralDate = null;
                try {
                    fechaInicioLaboralDate = sdf.parse(fechaInicioLaboral);
                } catch (ParseException e) {
                    System.out.println("Fecha inicio laboral inválida en la fila " + i + ": " + fechaInicioLaboral);
                    continue; // Sigue con la siguiente fila
                }
                
               RnGcNomTiporegimenTbl regimenFiscalTbl =  ejbFacadeRegimen.obternerRegimenByDescripcion(regimenFiscal);
                // Validar si el resultado o la clave son nulas
                if (regimenFiscalTbl == null || regimenFiscalTbl.getCveTipoRegimen() == null) {
                    System.out.println("Regimen fiscal no válido para el trabajador en la fila " + i);
                    continue; // pasa a la siguiente fila del archivo
                }

                // Aquí ya puedes usar el valor de cveTipoRegimen
                String claveRegimen = regimenFiscalTbl.getCveTipoRegimen();
                System.out.println("Clave de régimen obtenida: " + claveRegimen); 
                
                RnGcNomEstadosTbl entidadFederativaTbl =  ejbFacadeEstado.obtenerByNombre(entidadFederativa);
                // Validar si el resultado o la clave son nulas
                if (entidadFederativaTbl == null || entidadFederativaTbl.getNombre() == null) {
                    System.out.println("Estado no válido para el trabajador en la fila " + i);
                    continue; // pasa a la siguiente fila del archivo
                }
                
                RnGcNomRiesgopuestoTbl riesgoPuestoTbl =  ejbFacadeRiesgoPuesto.obtenerByDescripcion(riesgoPuesto);
                // Validar si el resultado o la clave son nulas
                if (riesgoPuestoTbl == null || riesgoPuestoTbl.getCveRiesgoPuesto()== null) {
                    System.out.println("Rueso de puesto no válido para el trabajador en la fila " + i);
                    continue; // pasa a la siguiente fila del archivo
                }
                
                RnGcNomTipocontratoTbl tipoContratoTbl =  ejbFacadeTipoContrato.obtenerByDescripcion(tipoContrato);
                // Validar si el resultado o la clave son nulas
                if (tipoContratoTbl == null || tipoContratoTbl.getCveTipoContrato() == null) {
                    System.out.println("Tipo de contrato no válido para el trabajador en la fila " + i);
                    continue; // pasa a la siguiente fila del archivo
                }
                
                RnGcNomTipojornadaTbl tipoJornadaTbl =  ejbFacadeJornada.obtenerByDescripcion(tipoJornada);
                // Validar si el resultado o la clave son nulas
                if (tipoJornadaTbl == null || tipoJornadaTbl.getCveTipoJornada() == null) {
                    System.out.println("Tipo de Jornada no válido para el trabajador en la fila " + i);
                    continue; // pasa a la siguiente fila del archivo
                }
                
                RnGcTrabajadoresTbl nuevoTrabajador = new RnGcTrabajadoresTbl();
                nuevoTrabajador.setNombre(nombre.toUpperCase());
                nuevoTrabajador.setApPaterno(apellidoPaterno.toUpperCase());
                nuevoTrabajador.setApMaterno(apellidoMaterno.toUpperCase());
                nuevoTrabajador.setCurp(curp.toUpperCase());
                nuevoTrabajador.setRfc(rfc.toUpperCase());
                nuevoTrabajador.setTipoPersona(regimenFiscalTbl.getCveTipoRegimen());
                nuevoTrabajador.setNss(nss.toUpperCase());
                nuevoTrabajador.setFechaInicio(fechaInicioLaboralDate);
                nuevoTrabajador.setSalarioBase(salarioBase);
                nuevoTrabajador.setSdi(salarioDiarioIntegrado);
                nuevoTrabajador.setEstadoId(entidadFederativaTbl.getId());
                //nuevoTrabajador.setCodigoPostal(codigoPostal);
                //nuevoTrabajador.setRegimenContratacion(regimenContratacion);
                //nuevoTrabajador.setRiesgoPuesto(riesgoPuestoTbl); 
                nuevoTrabajador.setTipoContratoId(tipoContratoTbl.getId()); 
                //nuevoTrabajador.setTipoJornada(tipoJornadaTbl);
                //nuevoTrabajador.setSindicalizado(sindicalizado.equalsIgnoreCase("si"));
                nuevoTrabajador.setEmail1(email.toLowerCase());
                //nuevoTrabajador.setObservaciones(observaciones);
                
                // Persistir en la base de datos
                Date now = new Date();
                nuevoTrabajador.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
                nuevoTrabajador.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                nuevoTrabajador.setFechaCreacion(now);
                nuevoTrabajador.setUltimaFechaActualizacion(now);
                
                String nombreCompleto = nombre.toUpperCase() + " " + apellidoPaterno.toUpperCase() + " " + apellidoMaterno.toUpperCase();
                nuevoTrabajador.setNombreCompleto(nombreCompleto);
                
                try {
                    System.out.println("Trabajador : " +  nuevoTrabajador);
                    getFacade().create(nuevoTrabajador);
                    System.out.println("ID generado: " + nuevoTrabajador.getId());
                    System.out.println("Trabajador insertado exitosamente en la fila " + (i + 1));
                } catch (Exception ex) {
                    System.err.println("Error al insertar trabajador en la fila " + (i + 1) + ": " + ex.getMessage());
                }
            }
        }
    } catch (Exception e) {
        System.err.println("Error al leer la plantilla: " + e.getMessage());
        e.printStackTrace();
    }
}

  private String getCellValue(Cell cell) {
    if (cell == null) return "";

    switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            return cell.getStringCellValue();
        case Cell.CELL_TYPE_NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
                return new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
            }
            return String.valueOf(cell.getNumericCellValue());
        case Cell.CELL_TYPE_BOOLEAN:
            return String.valueOf(cell.getBooleanCellValue());
        case Cell.CELL_TYPE_FORMULA:
            return cell.getCellFormula();
        default:
            return "";
    }
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
