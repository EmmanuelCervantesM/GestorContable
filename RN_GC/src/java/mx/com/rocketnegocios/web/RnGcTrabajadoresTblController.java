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
import mx.com.rocketnegocios.entities.RnGcNomTiporegimencontratacionTbl;
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
        } else if ("09".equals(tipoPersona)) {
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
        if (usuarioFirmado.perfilUsuario().equals("ADMINISTRADOR")) {
            items = getFacade().findAll();
        } else {
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

    private static final int MAX_MENSAJES_RECHAZO = 20;

    public void leerPlantilla(FileUploadEvent event) throws IOException {
        System.out.println("Archivo recibido: " + event.getFile().getFileName());

        List<String> rechazos = new ArrayList<>();
        int insertados = 0;

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(event.getFile().getInputstream());

            XSSFSheet sheet = workbook.getSheet("Trabajadores"); // nombre de la hoja

            if (sheet == null) {
                System.out.println("No se encontró una hoja llamada 'Trabajadores'.");
                JsfUtil.addErrorMessage("El archivo no contiene una hoja llamada 'Trabajadores'.");
                return;
            }

            //int firstRow = sheet.getFirstRowNum();
            int firstRow = 5; // Encabezado en la fila 6 de Excel; los datos inician en la fila 7
            int lastRow = sheet.getLastRowNum();

            for (int i = firstRow + 1; i <= lastRow; i++) { // omitir encabezado
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                int filaExcel = i + 1;

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
                String email = getCellValue(row.getCell(18)).trim();
                String observaciones = getCellValue(row.getCell(19)).trim();
                String puesto = getCellValue(row.getCell(20)).trim();

                // Filas sin ningun dato (formato o validaciones de la plantilla): se ignoran sin mensaje
                if ((sysAsim + nombre + apellidoPaterno + apellidoMaterno + curp + rfc + regimenFiscal + nss
                        + fechaInicioLaboral + salarioBase + salarioDiarioIntegrado + entidadFederativa + codigoPostal
                        + regimenContratacion + riesgoPuesto + tipoContrato + tipoJornada + sindicalizado + email
                        + observaciones + puesto).isEmpty()) {
                    continue;
                }

                System.out.println("Fila " + filaExcel + " - Valores:");
                System.out.println(
                        "Tipo: " + sysAsim + ", "
                        + "Nombre: " + nombre + ", "
                        + "Apellido Paterno: " + apellidoPaterno + ", "
                        + "Apellido Materno: " + apellidoMaterno + ", "
                        + "CURP: " + curp + ", "
                        + "RFC: " + rfc + ", "
                        + "Régimen Fiscal: " + regimenFiscal + ", "
                        + "NSS: " + nss + ", "
                        + "Fecha Inicio: " + fechaInicioLaboral + ", "
                        + "Salario Base: " + salarioBase + ", "
                        + "SDI: " + salarioDiarioIntegrado + ", "
                        + "Entidad: " + entidadFederativa + ", "
                        + "CP: " + codigoPostal + ", "
                        + "Régimen Contratación: " + regimenContratacion + ", "
                        + "Riesgo Puesto: " + riesgoPuesto + ", "
                        + "Tipo Contrato: " + tipoContrato + ", "
                        + "Tipo Jornada: " + tipoJornada + ", "
                        + "Sindicalizado: " + sindicalizado + ", "
                        + "Email: " + email + ", "
                        + "Observaciones: " + observaciones + ", "
                        + "Puesto: " + puesto
                );

                if (!sysAsim.equalsIgnoreCase("Salariado") && !sysAsim.equalsIgnoreCase("Asimilado")) {
                    rechazarFila(rechazos, filaExcel, "el tipo de trabajador debe ser 'Salariado' o 'Asimilado' (valor: '" + sysAsim + "').");
                    continue;
                }

                // Validación de campos obligatorios
                List<String> faltantes = new ArrayList<>();
                validarObligatorio(faltantes, nombre, "Nombre");
                validarObligatorio(faltantes, apellidoPaterno, "Apellido paterno");
                validarObligatorio(faltantes, apellidoMaterno, "Apellido materno");
                validarLongitud(faltantes, curp, 18, "CURP");
                validarLongitud(faltantes, rfc, 13, "RFC");
                validarObligatorio(faltantes, regimenFiscal, "Régimen fiscal");
                validarObligatorio(faltantes, entidadFederativa, "Entidad federativa");
                validarObligatorio(faltantes, codigoPostal, "Código postal");
                validarObligatorio(faltantes, regimenContratacion, "Régimen de contratación");
                validarObligatorio(faltantes, tipoContrato, "Tipo de contrato");

                if (sysAsim.equalsIgnoreCase("Salariado")) {
                    validarLongitud(faltantes, nss, 11, "NSS");
                    validarObligatorio(faltantes, fechaInicioLaboral, "Fecha inicio laboral");
                    validarObligatorio(faltantes, salarioBase, "Salario base");
                    validarObligatorio(faltantes, salarioDiarioIntegrado, "Salario diario integrado");
                    validarObligatorio(faltantes, riesgoPuesto, "Riesgo del puesto");
                    validarObligatorio(faltantes, tipoJornada, "Tipo de jornada");
                    if (!(sindicalizado.equalsIgnoreCase("si") || sindicalizado.equalsIgnoreCase("no"))) {
                        faltantes.add("Sindicalizado (Si/No)");
                    }
                    validarObligatorio(faltantes, puesto, "Puesto");
                }

                if (!faltantes.isEmpty()) {
                    rechazarFila(rechazos, filaExcel, "trabajador " + sysAsim.toLowerCase() + " con campos obligatorios faltantes o inválidos: " + String.join(", ", faltantes) + ".");
                    continue;
                }

                // Validación existencia en base de datos (solo se comparan CURP, RFC y NSS con valor)
                String campoDuplicado = ejbFacade.obtenerCampoDuplicado(curp, rfc, nss, usuarioFirmado.obtenerIdUsuario());
                if (campoDuplicado != null) {
                    rechazarFila(rechazos, filaExcel, "ya existe un trabajador con el mismo " + campoDuplicado + ".");
                    continue; // Salta inserción
                }

                // Valicar que los catalogos exista para Salariado
                if (sysAsim.equalsIgnoreCase("Salariado")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.setLenient(false); // Para que no acepte fechas inválidas como 2023/02/30

                    Date fechaInicioLaboralDate = null;
                    try {
                        fechaInicioLaboralDate = sdf.parse(fechaInicioLaboral);
                    } catch (ParseException e) {
                        rechazarFila(rechazos, filaExcel, "fecha inicio laboral inválida '" + fechaInicioLaboral + "' (formato esperado aaaa-mm-dd).");
                        continue; // Sigue con la siguiente fila
                    }

                    RnGcNomTiporegimenTbl regimenFiscalTbl = ejbFacadeRegimen.obternerRegimenByDescripcion(regimenFiscal);
                    // Validar si el resultado o la clave son nulas
                    if (regimenFiscalTbl == null || regimenFiscalTbl.getCveTipoRegimen() == null) {
                        rechazarFila(rechazos, filaExcel, "régimen fiscal no válido '" + regimenFiscal + "'.");
                        continue; // pasa a la siguiente fila del archivo
                    }

                    // Aquí ya puedes usar el valor de cveTipoRegimen
                    String claveRegimen = regimenFiscalTbl.getCveTipoRegimen();
                    System.out.println("Clave de régimen obtenida: " + claveRegimen);

                    RnGcNomEstadosTbl entidadFederativaTbl = ejbFacadeEstado.obtenerByNombre(entidadFederativa);
                    // Validar si el resultado o la clave son nulas
                    if (entidadFederativaTbl == null || entidadFederativaTbl.getNombre() == null) {
                        rechazarFila(rechazos, filaExcel, "entidad federativa no válida '" + entidadFederativa + "'.");
                        continue; // pasa a la siguiente fila del archivo
                    }

                    RnGcNomRiesgopuestoTbl riesgoPuestoTbl = ejbFacadeRiesgoPuesto.obtenerByDescripcion(riesgoPuesto);
                    // Validar si el resultado o la clave son nulas
                    if (riesgoPuestoTbl == null || riesgoPuestoTbl.getCveRiesgoPuesto() == null) {
                        rechazarFila(rechazos, filaExcel, "riesgo del puesto no válido '" + riesgoPuesto + "'.");
                        continue; // pasa a la siguiente fila del archivo
                    }

                    RnGcNomTipocontratoTbl tipoContratoTbl = ejbFacadeTipoContrato.obtenerByDescripcion(tipoContrato);
                    // Validar si el resultado o la clave son nulas
                    if (tipoContratoTbl == null || tipoContratoTbl.getCveTipoContrato() == null) {
                        rechazarFila(rechazos, filaExcel, "tipo de contrato no válido '" + tipoContrato + "'.");
                        continue; // pasa a la siguiente fila del archivo
                    }

                    RnGcNomTipojornadaTbl tipoJornadaTbl = ejbFacadeJornada.obtenerByDescripcion(tipoJornada);
                    // Validar si el resultado o la clave son nulas
                    if (tipoJornadaTbl == null || tipoJornadaTbl.getCveTipoJornada() == null) {
                        rechazarFila(rechazos, filaExcel, "tipo de jornada no válido '" + tipoJornada + "'.");
                        continue; // pasa a la siguiente fila del archivo
                    }

                    RnGcNomTiporegimencontratacionTbl regimenContratacionTbl = ejbFacadeTipoContratacion.obternerRegimenContratacionByDescripcion(regimenContratacion);
                    // Validar si el resultado o la clave son nulas
                    if (regimenContratacionTbl == null || regimenContratacionTbl.getCveTipoRegimenContratacion() == null) {
                        rechazarFila(rechazos, filaExcel, "régimen de contratación no válido '" + regimenContratacion + "'.");
                        continue; // pasa a la siguiente fila del archivo
                    }

                    RnGcTrabajadoresTbl nuevoTrabajador = new RnGcTrabajadoresTbl();
                    nuevoTrabajador.setTipoEmpleado("Salariado");
                    nuevoTrabajador.setNombre(nombre.toUpperCase());
                    nuevoTrabajador.setApPaterno(apellidoPaterno.toUpperCase());
                    nuevoTrabajador.setApMaterno(apellidoMaterno.toUpperCase());
                    nuevoTrabajador.setCurp(curp.toUpperCase());
                    nuevoTrabajador.setRfc(rfc.toUpperCase());
                    nuevoTrabajador.setTipoPersona(regimenFiscalTbl.getCveTipoRegimen());
                    nuevoTrabajador.setRegimenFicalId(regimenFiscalTbl);
                    nuevoTrabajador.setNss(nss.toUpperCase());
                    nuevoTrabajador.setFechaInicio(fechaInicioLaboralDate);
                    nuevoTrabajador.setSalarioBase(salarioBase);
                    nuevoTrabajador.setSdi(salarioDiarioIntegrado);
                    nuevoTrabajador.setEstadoId(entidadFederativaTbl.getId());
                    nuevoTrabajador.setEntidadFederativaId(entidadFederativaTbl);
                    nuevoTrabajador.setCodigoPostal(codigoPostal);
                    nuevoTrabajador.setRegimenContratacionId(regimenContratacionTbl);
                    nuevoTrabajador.setRiesgoPuestoTblId(riesgoPuestoTbl);
                    nuevoTrabajador.setTipoContratoId(tipoContratoTbl.getId());
                    nuevoTrabajador.setTipoJornadaTblId(tipoJornadaTbl);
                    nuevoTrabajador.setSindicalizado(
                            "si".equalsIgnoreCase(sindicalizado) ? "S" : "N"
                    );
                    nuevoTrabajador.setPuesto(puesto);
                    nuevoTrabajador.setEmail1(email.toLowerCase());
                    nuevoTrabajador.setObservaciones(observaciones);
                    nuevoTrabajador.setNoTrabajador(ejbFacade.obtenerSiguienteNoTrabajador(usuarioFirmado.obtenerIdUsuario()));

                    // Persistir en la base de datos
                    Date now = new Date();
                    nuevoTrabajador.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
                    nuevoTrabajador.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                    nuevoTrabajador.setFechaCreacion(now);
                    nuevoTrabajador.setUltimaFechaActualizacion(now);

                    String nombreCompleto = nombre.toUpperCase() + " " + apellidoPaterno.toUpperCase() + " " + apellidoMaterno.toUpperCase();
                    nuevoTrabajador.setNombreCompleto(nombreCompleto);

                    if (insertarTrabajador(nuevoTrabajador, filaExcel, rechazos)) {
                        insertados++;
                    }
                }

                // Valicar que los catalogos exista para Asimilado
                if ("Asimilado".equalsIgnoreCase(sysAsim)) {
                    System.out.println("Entro a Asimilado");

                    // Parsear fecha de inicio laboral solo si tiene valor
                    Date fechaInicioLaboralDate = null;
                    if (!fechaInicioLaboral.isEmpty()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        sdf.setLenient(false); // No aceptar fechas inválidas
                        try {
                            fechaInicioLaboralDate = sdf.parse(fechaInicioLaboral);
                        } catch (ParseException e) {
                            rechazarFila(rechazos, filaExcel, "fecha inicio laboral inválida '" + fechaInicioLaboral + "' (formato esperado aaaa-mm-dd).");
                            continue; // pasa a la siguiente fila
                        }
                    }

                    // Obtener entidades obligatorias
                    RnGcNomTiporegimenTbl regimenFiscalTbl = ejbFacadeRegimen.obternerRegimenByDescripcion(regimenFiscal);
                    RnGcNomTiporegimencontratacionTbl regimenContratacionTbl = ejbFacadeTipoContratacion.obternerRegimenContratacionByDescripcion(regimenContratacion);

                    // Validar obligatorios (entidades)
                    if (regimenFiscalTbl == null || regimenFiscalTbl.getCveTipoRegimen() == null) {
                        rechazarFila(rechazos, filaExcel, "régimen fiscal no válido '" + regimenFiscal + "'.");
                        continue;
                    }
                    if (regimenContratacionTbl == null || regimenContratacionTbl.getCveTipoRegimenContratacion() == null) {
                        rechazarFila(rechazos, filaExcel, "régimen de contratación no válido '" + regimenContratacion + "'.");
                        continue;
                    }

                    // Obtener entidades opcionales si tienen valor
                    RnGcNomEstadosTbl entidadFederativaTbl = null;
                    if (!entidadFederativa.isEmpty()) {
                        entidadFederativaTbl = ejbFacadeEstado.obtenerByNombre(entidadFederativa);
                        if (entidadFederativaTbl == null) {
                            System.out.println("Estado no encontrado, se dejará null para la fila " + filaExcel);
                        }
                    }

                    RnGcNomRiesgopuestoTbl riesgoPuestoTbl = null;
                    if (!riesgoPuesto.isEmpty()) {
                        riesgoPuestoTbl = ejbFacadeRiesgoPuesto.obtenerByDescripcion(riesgoPuesto);
                        if (riesgoPuestoTbl == null) {
                            System.out.println("Riesgo de puesto no encontrado, se dejará null para la fila " + filaExcel);
                        }
                    }

                    RnGcNomTipocontratoTbl tipoContratoTbl = null;
                    if (!tipoContrato.isEmpty()) {
                        tipoContratoTbl = ejbFacadeTipoContrato.obtenerByDescripcion(tipoContrato);
                        if (tipoContratoTbl == null) {
                            System.out.println("Tipo de contrato no encontrado, se dejará null para la fila " + filaExcel);
                        }
                    }

                    RnGcNomTipojornadaTbl tipoJornadaTbl = null;
                    if (!tipoJornada.isEmpty()) {
                        tipoJornadaTbl = ejbFacadeJornada.obtenerByDescripcion(tipoJornada);
                        if (tipoJornadaTbl == null) {
                            System.out.println("Tipo de jornada no encontrado, se dejará null para la fila " + filaExcel);
                        }
                    }

                    // Crear el trabajador
                    RnGcTrabajadoresTbl nuevoTrabajador = new RnGcTrabajadoresTbl();
                    nuevoTrabajador.setTipoEmpleado("Asimilado");
                    nuevoTrabajador.setNombre(nombre.toUpperCase());
                    nuevoTrabajador.setApPaterno(apellidoPaterno.toUpperCase());
                    nuevoTrabajador.setApMaterno(apellidoMaterno.toUpperCase());
                    nuevoTrabajador.setCurp(curp.toUpperCase());
                    nuevoTrabajador.setRfc(rfc.toUpperCase());
                    nuevoTrabajador.setTipoPersona(regimenFiscalTbl.getCveTipoRegimen());
                    nuevoTrabajador.setRegimenFicalId(regimenFiscalTbl);
                    nuevoTrabajador.setNss(nss.toUpperCase());
                    nuevoTrabajador.setFechaInicio(fechaInicioLaboralDate);
                    nuevoTrabajador.setSalarioBase(salarioBase);
                    nuevoTrabajador.setSdi(salarioDiarioIntegrado);
                    nuevoTrabajador.setEstadoId(entidadFederativaTbl != null ? entidadFederativaTbl.getId() : null);
                    nuevoTrabajador.setEntidadFederativaId(entidadFederativaTbl);
                    nuevoTrabajador.setCodigoPostal(codigoPostal);
                    nuevoTrabajador.setRegimenContratacionId(regimenContratacionTbl);
                    nuevoTrabajador.setRiesgoPuestoTblId(riesgoPuestoTbl);
                    nuevoTrabajador.setTipoContratoId(tipoContratoTbl != null ? tipoContratoTbl.getId() : null);
                    nuevoTrabajador.setTipoJornadaTblId(tipoJornadaTbl);
                    nuevoTrabajador.setSindicalizado("si".equalsIgnoreCase(sindicalizado) ? "S" : "N");
                    nuevoTrabajador.setPuesto(puesto.isEmpty() ? null : puesto);
                    nuevoTrabajador.setEmail1(email.toLowerCase());
                    nuevoTrabajador.setObservaciones(observaciones);
                    nuevoTrabajador.setNoTrabajador(ejbFacade.obtenerSiguienteNoTrabajador(usuarioFirmado.obtenerIdUsuario()));

                    // Fechas y usuario
                    Date now = new Date();
                    nuevoTrabajador.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
                    nuevoTrabajador.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                    nuevoTrabajador.setFechaCreacion(now);
                    nuevoTrabajador.setUltimaFechaActualizacion(now);

                    // Nombre completo
                    String nombreCompleto = nombre.toUpperCase() + " " + apellidoPaterno.toUpperCase() + " " + apellidoMaterno.toUpperCase();
                    nuevoTrabajador.setNombreCompleto(nombreCompleto);

                    if (insertarTrabajador(nuevoTrabajador, filaExcel, rechazos)) {
                        insertados++;
                    }
                }

            }
        } catch (Exception e) {
            System.err.println("Error al leer la plantilla: " + e.getMessage());
            e.printStackTrace();
            JsfUtil.addErrorMessage("Error al leer la plantilla: " + e.getMessage());
        }

        mostrarResumenCarga(insertados, rechazos);
    }

    private boolean insertarTrabajador(RnGcTrabajadoresTbl nuevoTrabajador, int filaExcel, List<String> rechazos) {
        try {
            System.out.println("Trabajador : " + nuevoTrabajador);
            getFacade().create(nuevoTrabajador);
            System.out.println("ID generado: " + nuevoTrabajador.getId());
            System.out.println("Trabajador insertado exitosamente en la fila " + filaExcel);
            return true;
        } catch (Exception ex) {
            System.err.println("Error al insertar trabajador en la fila " + filaExcel + ": " + ex.getMessage());
            rechazarFila(rechazos, filaExcel, "error al guardar el trabajador (" + ex.getMessage() + ").");
            return false;
        }
    }

    private void rechazarFila(List<String> rechazos, int filaExcel, String motivo) {
        String mensaje = "Fila " + filaExcel + ": " + motivo;
        System.out.println(mensaje);
        rechazos.add(mensaje);
    }

    private void validarObligatorio(List<String> faltantes, String valor, String campo) {
        if (valor == null || valor.isEmpty()) {
            faltantes.add(campo);
        }
    }

    private void validarLongitud(List<String> faltantes, String valor, int longitud, String campo) {
        if (valor == null || valor.length() != longitud) {
            faltantes.add(campo + " (" + longitud + " caracteres)");
        }
    }

    private void mostrarResumenCarga(int insertados, List<String> rechazos) {
        String resumen = "Carga de trabajadores terminada: " + insertados + " insertados, " + rechazos.size() + " rechazados.";
        System.out.println(resumen);
        if (rechazos.isEmpty()) {
            JsfUtil.addSuccessMessage(resumen);
            return;
        }
        JsfUtil.addWarningMessage(resumen);
        int mostrados = Math.min(rechazos.size(), MAX_MENSAJES_RECHAZO);
        for (int k = 0; k < mostrados; k++) {
            JsfUtil.addErrorMessage(rechazos.get(k));
        }
        if (rechazos.size() > MAX_MENSAJES_RECHAZO) {
            JsfUtil.addErrorMessage("... y " + (rechazos.size() - MAX_MENSAJES_RECHAZO) + " filas rechazadas más.");
        }
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue().trim();

            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                }
                // Convertir a entero si es un número sin decimales
                double numericValue = cell.getNumericCellValue();
                if (numericValue == Math.floor(numericValue)) {
                    return String.valueOf((long) numericValue); // entero sin .0
                } else {
                    return String.valueOf(numericValue); // mantiene decimales si existen
                }

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

    public List<String> obtenerNominaCreado(Integer nominaId) {
        List<String> nominaTrabajador = new ArrayList<>();
        List<RnGcNomNominasTbl> nomina = new ArrayList<>();
        RnGcNomNominasTblFacade nominaFacade = new RnGcNomNominasTblFacade();
        if (nominaId != null) {
            nomina = nominaFacade.obtenerNominaPorId(nominaId);
            for (RnGcNomNominasTbl nomina1 : nomina) {
                nominaTrabajador.add(nomina1.getNombreNomina());
            }
        }
        return nominaTrabajador;
    }

    public void descargaPlantilla() throws IOException {
        System.out.println("descargarPlantilla");
        File plantilla = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/Trabajadores_Formulario.xlsm"));
        System.out.println("plantilla: " + plantilla);
        byte[] aux = FileUtils.readFileToByteArray(plantilla);
        System.out.println("aux: " + aux);
        InputStream streamPlantilla = new ByteArrayInputStream(aux);
        System.out.println("streamPlantilla: " + streamPlantilla);
        downLoadFile = new DefaultStreamedContent(streamPlantilla,
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                "plantilla_trabajadores_" + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()).concat(".xlsm"));
    }

}
