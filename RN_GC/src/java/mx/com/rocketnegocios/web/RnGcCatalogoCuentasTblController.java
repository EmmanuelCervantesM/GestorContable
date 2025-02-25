package mx.com.rocketnegocios.web;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import mx.com.rocketnegocios.entities.RnGcCatalogoCuentasTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcCatalogoCuentasTblFacade;

import java.io.Serializable;
import java.io.StringWriter;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import mx.com.rocketnegocios.entities.ListaCuentas;
import mx.com.rocketnegocios.entities.RnGcCodigoAgrupadorSatTbl;
import mx.com.rocketnegocios.entities.RnGcMonedasTbl;
import mx.com.rocketnegocios.entities.RnGcPolizaLineasTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.export.SimpleXlsxExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

@Named("rnGcCatalogoCuentasTblController")
@SessionScoped
public class RnGcCatalogoCuentasTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcMonedasTblFacade monedaFacade;
    
    @EJB
    private mx.com.rocketnegocios.beans.RnGcCatalogoCuentasTblFacade ejbFacade;
    private List<RnGcCatalogoCuentasTbl> items = null;
    private RnGcCatalogoCuentasTbl selected;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private UploadedFile file;
    private StreamedContent downLoadFile, downLoadFileP, downLoadFile1, downLoadFile2, downLoadFile3;
    private byte[] xml = null;
    private List<RnGcPolizaLineasTbl> itemsPolizaLineas = null;
    private List<String> rfcSeleccionado;
    private List<String> listaRfc;
    private boolean valid = true;
    private String motivo = "", nCuenta="",dCuenta="",nivel="",codigo="",natur="",tipo="",subtipo="",rfc="";
    private String motivoVal = "", ndCuentas="",rfcs="";

    public String getMotivoVal() {
        return motivoVal;
    }

    public void setMotivoVal(String motivoVal) {
        this.motivoVal = motivoVal;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public List<String> getListaRfc() {
        return listaRfc;
    }

    public void setListaRfc(List<String> listaRfc) {
        this.listaRfc = listaRfc;
    }

    public List<String> getRfcSeleccionado() {
        return rfcSeleccionado;
    }

    public void setRfcSeleccionado(List<String> rfcSeleccionado) {
        this.rfcSeleccionado = rfcSeleccionado;
    }

    @EJB
    private mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade usuarioFacade;
    private RnGcUsuariosTbl usuarioId = null;

    @EJB
    private mx.com.rocketnegocios.beans.RnGcCodigoAgrupadorSatTblFacade codigoAgrupadorFacade;

    List<RnGcCatalogoCuentasTbl> listaCuentasSinRegistrar = new ArrayList<>();

    public List<RnGcCatalogoCuentasTbl> getListaCuentasSinRegistrar() {
        return listaCuentasSinRegistrar;
    }

    public void setListaCuentasSinRegistrar(List<RnGcCatalogoCuentasTbl> listaCuentasSinRegistrar) {
        this.listaCuentasSinRegistrar = listaCuentasSinRegistrar;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public RnGcCatalogoCuentasTblController() {
    }

    public RnGcCatalogoCuentasTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcCatalogoCuentasTbl selected) {
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
    
    private RnGcCatalogoCuentasTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcCatalogoCuentasTbl prepareCreate() {
        //System.out.println("**** Entro a preparar para crear ***");
        selected = new RnGcCatalogoCuentasTbl();
        initializeEmbeddableKey();
        return selected;
    }
    
    public int contarCaracteres(String cadena) {
        char caracter = '-';
        int posicion, contador = 0;
        int nivel = 0;
        //se busca la primera vez que aparece
        posicion = cadena.indexOf(caracter);
        while (posicion != -1) { //mientras se encuentre el caracter
            contador++;           //se cuenta
            //se sigue buscando a partir de la posición siguiente a la encontrada                                 
            posicion = cadena.indexOf(caracter, posicion + 1);
        }
        if (contador == 0)
        {nivel = 1;}
        if (contador == 1)
        {nivel = 2;}
        if (contador == 2)
        {nivel = 3;}
        if (contador == 3)
        {nivel = 4;}
        if (contador == 4)
        {nivel = 5;}
        return nivel;
    }
    
    public void create() {
        if(validarCuenta().equals("si")){
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCatalogoCuentasTblCreated"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
        } 
    }

    public void update() {
        if (selected.getNumeroCuenta() == null || selected.getDescripcionCuenta() == null || selected.getAdicional2() == null 
                ||selected.getCodigoAgrupadorSatId() == null || selected.getTipo() == null || selected.getSubtipo() == null || selected.getNaturaleza() == null) {
            RequestContext.getCurrentInstance().execute("PF('CuentasDlgErrorDialogo').show();");
        }else{
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCatalogoCuentasTblUpdated"));
            RequestContext.getCurrentInstance().execute("PF('RnGcCatalogoCuentasTblEditDialog').hide();");
        }
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcCatalogoCuentasTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcCatalogoCuentasTbl> getItems() {
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

    public RnGcCatalogoCuentasTbl getRnGcCatalogoCuentasTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcCatalogoCuentasTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcCatalogoCuentasTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcCatalogoCuentasTbl.class)
    public static class RnGcCatalogoCuentasTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcCatalogoCuentasTblController controller = (RnGcCatalogoCuentasTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcCatalogoCuentasTblController");
            return controller.getRnGcCatalogoCuentasTbl(getKey(value));
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
            if (object instanceof RnGcCatalogoCuentasTbl) {
                RnGcCatalogoCuentasTbl o = (RnGcCatalogoCuentasTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcCatalogoCuentasTbl.class.getName()});
                return null;
            }
        }

    }

    public List<String> completarTexto(String consulta){
        usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        List<String> cuentasLista = new ArrayList<>();
        List<RnGcCatalogoCuentasTbl> cuentas = ejbFacade.obtenerCuentasCreadoPor(usuarioId.getId());
        for(RnGcCatalogoCuentasTbl cuenta : cuentas){
            cuentasLista.add(cuenta.getNumeroCuenta());
        }
        return cuentasLista.stream().filter(t -> t.startsWith(consulta)).collect(Collectors.toList());
    }
    
    public void mostrarNivel(){
        if (selected.getNumeroCuenta() != null) {
            System.out.println("el numero de Cuenta ingresado es: " + selected.getNumeroCuenta());
            List<RnGcCatalogoCuentasTbl> cuenta = new ArrayList<>();
            usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            cuenta = ejbFacade.obtenerListadeNumerosCuentas(selected.getNumeroCuenta(), usuarioId);
            if(!cuenta.isEmpty()){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Número de cuenta existente"));
                System.out.println("Número de cuenta existente");
                selected.setNumeroCuenta("");
            }else{
                System.out.println("Número de cuenta correcta");
                int nivel = contarCaracteres(String.valueOf(selected.getNumeroCuenta()));
                String niv = String.valueOf(nivel);
                selected.setAdicional2(niv);
            }
        }
    }
    
    public void validarDescripcion(){
        if (selected.getDescripcionCuenta() != null) {
            System.out.println("La descripcion de Cuenta ingresado es: " + selected.getDescripcionCuenta());
            List<RnGcCatalogoCuentasTbl> cuenta = new ArrayList<>();
            usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            cuenta = ejbFacade.obtenerListadeCuentasDescripcion(selected.getDescripcionCuenta(), usuarioId);
            if(!cuenta.isEmpty()){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Descripción de cuenta existente"));
                System.out.println("Descripción de cuenta existente");
                selected.setDescripcionCuenta("");
            }
        }
    }
    
    public void validarRFC(){
        if (selected.getRfc() != null) {
            System.out.println("El RFC de Cuenta ingresado es: " + selected.getRfc());
            List<RnGcCatalogoCuentasTbl> cuenta = new ArrayList<>();
            usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            cuenta = ejbFacade.obtenerListadeCuentasRFC(selected.getRfc(), usuarioId, true);
            if(!cuenta.isEmpty() && selected.getAdicional1()){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "RFC existente"));
                System.out.println("RFC existente");
                selected.setRfc("");
            }
        }
    }
    
    public void ingresarTipos(){
        String codigo = selected.getCodigoAgrupadorSatId().getCodigoAgrupador();
        System.out.print(codigo);
        int cod;
        String[] parts = codigo.split("\\.");
        /*char caracter = '.';
        int posicion, contador = 0;
        posicion = codigo.indexOf(caracter);
        while (posicion != -1) {
            contador++;                               
            posicion = codigo.indexOf(caracter, posicion + 1);
        }
        if (contador == 0)
            cod = Integer.parseInt(codigo);
        else{
            System.out.print(parts[0]);
            cod = Integer.parseInt(parts[0]);
        }*/
        cod = Integer.parseInt(parts[0]);
        System.out.print(cod);
        if(cod > 99 && cod < 200){
            selected.setTipo("1");
            selected.setNaturaleza("D");
            if(cod == 100 && parts.length > 1){
                if(Integer.parseInt(parts[1]) == 01)
                    selected.setSubtipo("1");
                if(Integer.parseInt(parts[1]) == 02)
                    selected.setSubtipo("2");
            }
            if(cod > 100 && cod < 151)
                selected.setSubtipo("1");
            if(cod > 150 && cod < 200)
                selected.setSubtipo("2");
        }
        if(cod > 199 && cod < 300){
            selected.setTipo("2");
            selected.setNaturaleza("A");
            if(cod == 200 && parts.length > 1){
                if(Integer.parseInt(parts[1]) == 01)
                    selected.setSubtipo("1");
                if(Integer.parseInt(parts[1]) == 02)
                    selected.setSubtipo("2");
            }
            if(cod > 200 && cod < 251)
                selected.setSubtipo("1");
            if(cod > 250 && cod < 300)
                selected.setSubtipo("2");
        }
        if(cod > 299 && cod < 400){
            selected.setTipo("3");
            selected.setSubtipo("3");
            selected.setNaturaleza("A");
        }
        if(cod > 399 && cod < 500){
            selected.setTipo("4");
            selected.setSubtipo("3");
            selected.setNaturaleza("A");
        }
        if(cod > 499 && cod < 600){
            selected.setTipo("5");
            selected.setSubtipo("3");
            selected.setNaturaleza("D");
        }
        if(cod > 599 && cod < 700){
            selected.setTipo("6");
            selected.setSubtipo("3");
            selected.setNaturaleza("D");
        }
        if(cod > 699 && cod < 800){
            selected.setTipo("7");
            selected.setSubtipo("3");
        }
        if(cod > 799 && cod < 900){
            selected.setTipo("8");
            selected.setSubtipo("3");
        }
    }
    
    public void validacionCancelar() {
        this.valid = false;
    }
    
    public RnGcCatalogoCuentasTbl preparaCrear(RnGcCatalogoCuentasTbl catalogo) {
        System.out.println("**** Entro a preparar para crear ***");
        selected = new RnGcCatalogoCuentasTbl();
        System.out.println("Catalogo de Cuenta Rfc" + catalogo.getRfc());
        selected.setRfc(catalogo.getRfc());
        initializeEmbeddableKey();
        // selected.setRfc(rfcReceptor);
        return selected;
    }

    public void exportarExcel(ActionEvent actionEvent) throws JRException, IOException, NamingException, SQLException {
        System.out.println("Entro a exportar el catalogo de cuentas en excel");
        List<RnGcCatalogoCuentasTbl> listaCuentas = new ArrayList<>();
        List<ListaCuentas> lista = new ArrayList<>();
        String diot;
        listaCuentas = obtenerCuentasPorUsuario();
        for(RnGcCatalogoCuentasTbl cuenta : listaCuentas){
            ListaCuentas cuent = new ListaCuentas();
            cuent.setCodigo(cuenta.getCodigoAgrupadorSatId().getCodigoAgrupador());
            cuent.setnCuenta(cuenta.getNumeroCuenta());
            cuent.setdCuenta(cuenta.getDescripcionCuenta());
            cuent.setNivel(cuenta.getAdicional2());
            if(cuenta.getTipo().equals("1")){
                cuent.setTipo("Activo");    
                if(cuenta.getSubtipo().equals("1"))
                    cuent.setSubtipo("Activo a corto plazo");
                if(cuenta.getSubtipo().equals("2"))
                    cuent.setSubtipo("Activo a largo plazo");
            }
            if(cuenta.getTipo().equals("2")){
                cuent.setTipo("Pasivo");
                if(cuenta.getSubtipo().equals("1"))
                    cuent.setSubtipo("Pasivo a corto plazo");
                if(cuenta.getSubtipo().equals("2"))
                    cuent.setSubtipo("Pasivo a largo plazo");
            }
            if(cuenta.getTipo().equals("3")){
                cuent.setTipo("Capital");
                cuent.setSubtipo("Resultados");
            }
            if(cuenta.getTipo().equals("4")){
                cuent.setTipo("Ingresos");
                cuent.setSubtipo("Resultados");
            }
            if(cuenta.getTipo().equals("5")){
                cuent.setTipo("Costos");
                cuent.setSubtipo("Resultados");
            }
            if(cuenta.getTipo().equals("6")){
                cuent.setTipo("Gastos");
                cuent.setSubtipo("Resultados");
            }
            if(cuenta.getTipo().equals("7")){
                cuent.setTipo("Resultado integral de financiamiento");
                cuent.setSubtipo("Resultados");
            }
            if(cuenta.getTipo().equals("8")){
                cuent.setTipo("Cuentas de orden");
                cuent.setSubtipo("Resultados");
            }
            if(cuenta.getNaturaleza().equals("A"))
                cuent.setNaturaleza("Acreedora");
            if(cuenta.getNaturaleza().equals("D"))
                cuent.setNaturaleza("Deudora");
            if(cuenta.getMonedaId() != null)
                cuent.setMoneda(cuenta.getMonedaId().getCMoneda());
            else
                cuent.setMoneda("");
            if(cuenta.getAdicional1())
                diot = "DIOT";
            else
                diot = "";
            cuent.setDiot(diot);
            if(cuenta.getRfc() != null)
                cuent.setRfc(cuenta.getRfc());
            else
                cuent.setRfc("");
            cuent.setVigencia(cuenta.getInicioVigencia());
            lista.add(cuent);
        }
        usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("listaCuentas", lista);
        parametros.put("nombre", usuarioId.getNombreCompleto());
        parametros.put("rfc", usuarioId.getRfc());
        parametros.put("fecha", new Date());
        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Reports/Catálogo de Cuentas 1.jasper"));
        //Llena el reporte
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JREmptyDataSource());
        System.out.println("Llena el reporte");
        //Imprime Reporte de Promedios Semestrales
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; fileName=Catalogo de Cuentas.xls");
        ServletOutputStream stream = response.getOutputStream();

        JRXlsExporter xlsExporter = new JRXlsExporter();

        xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(stream));
        SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
        SimpleXlsExporterConfiguration xlsExporterConfiguration = new SimpleXlsExporterConfiguration();
        xlsReportConfiguration.setOnePagePerSheet(false);
        xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
        xlsReportConfiguration.setDetectCellType(true);
        xlsReportConfiguration.setWhitePageBackground(false);
        xlsExporter.setConfiguration(xlsReportConfiguration);
        xlsExporter.exportReport();

        System.out.println("Realizo exportManager");
        stream.flush();
        stream.close();

        FacesContext.getCurrentInstance().responseComplete();
        System.out.println("responseComplete");
    }

    public void exportarPdf(){
        System.out.println("Entro a exportar el catálogo de cuentas en pdf");
        try {
        byte[] archExpor = null;
        List<RnGcCatalogoCuentasTbl> listaCuentas = new ArrayList<>();
        List<ListaCuentas> lista = new ArrayList<>();
        listaCuentas = obtenerCuentasPorUsuario();
        for(RnGcCatalogoCuentasTbl cuenta : listaCuentas){
            ListaCuentas cuent = new ListaCuentas();
            cuent.setnCuenta(cuenta.getNumeroCuenta());
            cuent.setdCuenta(cuenta.getDescripcionCuenta());
            cuent.setNivel(cuenta.getAdicional2());
            if(cuenta.getTipo().equals("1")){
                cuent.setTipo("Activo");    
                if(cuenta.getSubtipo().equals("1"))
                    cuent.setSubtipo("Activo a corto plazo");
                if(cuenta.getSubtipo().equals("2"))
                    cuent.setSubtipo("Activo a largo plazo");
            }
            if(cuenta.getTipo().equals("2")){
                cuent.setTipo("Pasivo");
                if(cuenta.getSubtipo().equals("1"))
                    cuent.setSubtipo("Pasivo a corto plazo");
                if(cuenta.getSubtipo().equals("2"))
                    cuent.setSubtipo("Pasivo a largo plazo");
            }
            if(cuenta.getTipo().equals("3")){
                cuent.setTipo("Capital");
                cuent.setSubtipo("Resultados");
            }
            if(cuenta.getTipo().equals("4")){
                cuent.setTipo("Ingresos");
                cuent.setSubtipo("Resultados");
            }
            if(cuenta.getTipo().equals("5")){
                cuent.setTipo("Costos");
                cuent.setSubtipo("Resultados");
            }
            if(cuenta.getTipo().equals("6")){
                cuent.setTipo("Gastos");
                cuent.setSubtipo("Resultados");
            }
            if(cuenta.getTipo().equals("7")){
                cuent.setTipo("Resultado integral de financiamiento");
                cuent.setSubtipo("Resultados");
            }
            if(cuenta.getTipo().equals("8")){
                cuent.setTipo("Cuentas de orden");
                cuent.setSubtipo("Resultados");
            }
            if(cuenta.getNaturaleza().equals("A"))
                cuent.setNaturaleza("Acreedora");
            if(cuenta.getNaturaleza().equals("D"))
                cuent.setNaturaleza("Deudora");
            if(cuenta.getMonedaId() != null)
                cuent.setMoneda(cuenta.getMonedaId().getCMoneda());
            else
                cuent.setMoneda("");
            lista.add(cuent);
        }
        usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("listaCuentas", lista);
        parametros.put("nombre", usuarioId.getNombreCompleto());
        parametros.put("rfc", usuarioId.getRfc());
        System.out.println("parametros: " + parametros.toString());
        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Reports/Catálogo de Cuentas.jasper"));
        //Llena el reporte
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JREmptyDataSource());
        System.out.println("Llena el reporte");
        archExpor = JasperExportManager.exportReportToPdf(jasperPrint);
        InputStream streamPdf = new ByteArrayInputStream(archExpor);
        downLoadFile = new DefaultStreamedContent(streamPdf, "document/pdf", "Catálogo de Cuentas.pdf" );
        /*HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; fileName=Catálogo de Cuentas.pdf");
        ServletOutputStream stream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
        System.out.println("Realizo exportManager");
        stream.flush();
        stream.close();
        try {
            if (!con.isClosed()) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(RnGcCatalogoCuentasTblController.class.getName()).log(Level.SEVERE, null, ex);
        }

        FacesContext.getCurrentInstance().responseComplete();
        System.out.println("responseComplete");*/
        }catch (Exception ex) {
            System.out.println("Error descargarFormato: " + ex.getLocalizedMessage());
            JsfUtil.addErrorMessage("Ocurrio un error dutante la descarga del formato.");
            ex.printStackTrace();
        }
    }
    
    
    public StreamedContent getDownLoadFile() throws IOException {
        return downLoadFile;
    }
    public StreamedContent getDownLoadFileP() throws IOException {
        return downLoadFileP;
    }
    public StreamedContent getDownLoadFile1() throws IOException {
        catalogoCuentasXML();
        return downLoadFile1;
    }
    /*public StreamedContent getDownLoadFile2() throws IOException {
        balanzaComprobacionXML();
        return downLoadFile2;
    }
    public StreamedContent getDownLoadFile3() throws IOException {
        //polizasXML();
        return downLoadFile3;
    }*/
    
    public void catalogoCuentasXML() throws IOException
    {
        Date fecha = new Date();
        List<RnGcCatalogoCuentasTbl> lista = null;
        lista = obtenerCuentasPorUsuario();
        usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        String rfcs, anios, mess;
        
        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            //DOMImplementation implementation = docBuilder.getDOMImplementation();
            
            //Document doc = implementation.createDocument(null,"Cuentas",null);
            Document doc = docBuilder.newDocument();
            doc.setXmlVersion("1.0");
            
            Element raiz = doc.createElement("catalogocuentas:Catalogo");
            Attr xmlxsi = doc.createAttribute("xmlns:xsi");
            xmlxsi.setValue("http://www.w3.org/2001/XMLSchema-instance");
            Attr xmlcuentas = doc.createAttribute("xmlns:catalogocuentas");
            xmlcuentas.setValue("http://www.sat.gob.mx/esquemas/ContabilidadE/1_3/CatalogoCuentas");
            Attr xsischema = doc.createAttribute("xsi:schemaLocation");
            xsischema.setValue("http://www.sat.gob.mx/esquemas/ContabilidadE/1_3/CatalogoCuentas http://www.sat.gob.mx/esquemas/ContabilidadE/1_3/CatalogoCuentas/CatalogoCuentas_1_3.xsd");
            Attr rfc = doc.createAttribute("RFC");
            rfc.setValue(String.valueOf(usuarioId.getRfc()));
            Attr version = doc.createAttribute("Version");
            version.setValue("1.3");
            Attr anio = doc.createAttribute("Anio");
            anio.setValue(String.valueOf(fecha.getYear()));//Se toma desde la pantalla, es el periodo
            Attr mes = doc.createAttribute("Mes");
            mes.setValue(String.valueOf(fecha.getMonth()));//Se toma desde la pantalla, es el periodo
            raiz.setAttributeNode(xsischema);
            raiz.setAttributeNode(version);
            raiz.setAttributeNode(rfc);
            raiz.setAttributeNode(mes);
            raiz.setAttributeNode(anio);
            raiz.setAttributeNode(xmlcuentas);
            raiz.setAttributeNode(xmlxsi);
            
            for (int i = 0; i < lista.size(); i++)
            {               
                Element cuenta = doc.createElement("catalogocuentas:Ctas");
                raiz.appendChild(cuenta);
                
                /*Attr naturaleza = doc.createAttribute("Natur");
                naturaleza.setValue(String.valueOf(lista.get(i).getNaturaleza()));
                cuenta.setAttributeNode(naturaleza);
                Attr des = doc.createAttribute("Des");
                des.setValue(String.valueOf(lista.get(i).getDescripcionCuenta()));
                cuenta.setAttributeNode(des);
                Attr NumCta = doc.createAttribute("NumCta");
                NumCta.setValue(String.valueOf(lista.get(i).getNumeroCuenta()));
                cuenta.setAttributeNode(NumCta);
                Attr CodAgrup = doc.createAttribute("CodAgrup");
                CodAgrup.setValue(String.valueOf(lista.get(i).getCodigoAgrupadorSatId().getCodigoAgrupador()));
                cuenta.setAttributeNode(CodAgrup);
                Attr SubCtaDe = doc.createAttribute("SubCtaDe");
                SubCtaDe.setValue(String.valueOf(lista.get(i).getSubCuenta()));
                cuenta.setAttributeNode(SubCtaDe);
                */
                //El nivel lo elige el usuario, desde la pantalla, se muestran desde el nivel 1 hasta el nivel que se elige
                
                //if (lista.get(i).getAdicional2() != null && !lista.get(i).getAdicional2().isEmpty())
                cuenta.setAttribute("Nivel", String.valueOf(lista.get(i).getAdicional2()));
                cuenta.setAttribute("Natur", String.valueOf(lista.get(i).getNaturaleza()));
                cuenta.setAttribute("Desc", String.valueOf(lista.get(i).getDescripcionCuenta()));
                cuenta.setAttribute("NumCta", String.valueOf(lista.get(i).getNumeroCuenta()));
                cuenta.setAttribute("CodAgrup", String.valueOf(lista.get(i).getCodigoAgrupadorSatId().getCodigoAgrupador()));
                cuenta.setAttribute("SubCtaDe", String.valueOf(lista.get(i).getSubCuenta()));
            }
            
            //doc.getDocumentElement().appendChild(raiz);
            doc.appendChild(raiz);

            StringWriter writer = new StringWriter();
            Source source = new DOMSource(doc);
            Result result = new StreamResult(writer);
            
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
            
            xml = writer.getBuffer().toString().getBytes();
            InputStream streamPlantilla = new ByteArrayInputStream(xml);
            
            rfcs = String.valueOf(usuarioId.getRfc());
            anios = String.valueOf(fecha.getYear());
            mess = String.valueOf(fecha.getMonth());
            downLoadFile1 = new DefaultStreamedContent(streamPlantilla, "document/xml", rfcs+anios+mess+"CT.xml");
            
            System.out.println("Fichero creado correctamente");
        }
        catch(ParserConfigurationException | TransformerException ex)
        {
            System.out.print(ex.getMessage());
        }
    }
    
    //public void balanzaComprobacionXML() throws IOException
    /*{
        List<RnGcCatalogoCuentasTbl> lista = null;
        lista = obtenerCuentasPorUsuario();
        List<RnGcPolizaLineasTbl> lista1 = null;
        lista1 = getItemsPolizaLineas();
        String rfcs, anios, mess;
        
        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Document doc = docBuilder.newDocument();
            doc.setXmlVersion("1.0");
            
            Element raiz = doc.createElement("BCE:Balanza");
            Attr xmlxsi = doc.createAttribute("xmlns:xsi");
            xmlxsi.setValue("http://www.w3.org/2001/XMLSchema-instance");
            Attr xmlcuentas = doc.createAttribute("xmlns:BCE");
            xmlcuentas.setValue("http://www.sat.gob.mx/esquemas/ContabilidadE/1_3/BalanzaComprobacion");
            Attr xsischema = doc.createAttribute("xsi:schemaLocation");
            xsischema.setValue("http://www.sat.gob.mx/esquemas/ContabilidadE/1_3/BalanzaComprobacion http://www.sat.gob.mx/esquemas/ContabilidadE/1_3/BalanzaComprobacion/BalanzaComprobacion_1_3.xsd");
            Attr rfc = doc.createAttribute("RFC");
            rfc.setValue(String.valueOf(usuarioId.getRfc()));
            Attr version = doc.createAttribute("Version");
            version.setValue("1.3");
            Attr anio = doc.createAttribute("Anio");
            anio.setValue("2020");//Se toma desde la pantalla, es el periodo
            Attr mes = doc.createAttribute("Mes");
            mes.setValue("10");//Se toma desde la pantalla, es el periodo
            Attr tipoEnvio = doc.createAttribute("TipoEnvio");//lo elige el usuario desde la pantalla
            tipoEnvio.setValue("N");//puede ser N(normal) o C(complementaria)
            raiz.setAttributeNode(anio);
            raiz.setAttributeNode(mes);
            raiz.setAttributeNode(rfc);
            raiz.setAttributeNode(version);
            raiz.setAttributeNode(tipoEnvio);
            raiz.setAttributeNode(xsischema);
            raiz.setAttributeNode(xmlcuentas);
            raiz.setAttributeNode(xmlxsi);
            
            for (int i = 0; i < lista.size(); i++)
            {               
                Element cuenta = doc.createElement("BCE:Ctas");
                raiz.appendChild(cuenta);
                
                cuenta.setAttribute("NumCta", String.valueOf(lista1.get(i).getCatalogoCuentasId().getNumeroCuenta()));
                cuenta.setAttribute("SaldoIni", "0");
                cuenta.setAttribute("Debe", String.valueOf(lista1.get(i).getCargo()));
                cuenta.setAttribute("Haber", String.valueOf(lista1.get(i).getAbono()));
                cuenta.setAttribute("SaldoFin", ""+(0+lista1.get(i).getCargo()-lista1.get(i).getAbono()));
            }
            
            doc.appendChild(raiz);

            StringWriter writer = new StringWriter();
            Source source = new DOMSource(doc);
            Result result = new StreamResult(writer);
            
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
            
            xml = writer.getBuffer().toString().getBytes();
            InputStream streamPlantilla = new ByteArrayInputStream(xml);
            
            rfcs = String.valueOf(usuarioId.getRfc());
            anios = "2020";
            mess = "10";
            downLoadFile = new DefaultStreamedContent(streamPlantilla, "document/xml", rfcs+anios+mess+"BN.xml");
            
            System.out.println("Fichero creado correctamente");
        }
        catch(ParserConfigurationException | TransformerException ex)
        {
            System.out.print(ex.getMessage());
        }
    }*/

    public void decargarPlantilla1() throws IOException{
        System.out.print("------------------- Entro a plantilla 1 *******************");
        File archivo = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/Plantilla_Catalogo_Cuentas.xlsx"));
        byte[] archExpor = Files.readAllBytes(archivo.toPath());
        InputStream stream = new ByteArrayInputStream(archExpor);
        System.out.print("camino "+ archivo.toPath() );
        downLoadFileP = new DefaultStreamedContent(stream, "application/vnd.ms-excel", "Plantilla Catalogo Cuentas.xlsx" );
    }

    //Carga masiva de cuentas por excel
    public void descargarPlantilla(ActionEvent actionEvent) throws JRException, IOException, NamingException, SQLException {
        System.out.println("Entro a descargar plantilla en  excel");
        Connection con = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:app/GC_Produccion");
            con = ds.getConnection();
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(RnGcCatalogoCuentasTbl.class.getName()).log(Level.SEVERE, null, ex);
        }
        Map<String, Object> parametros = new HashMap<String, Object>();
        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Reports/CatalogoDeCuentasPlantilla.jasper"));
        //Llena el reporte
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, con);
        System.out.println("Llena el reporte");
        //Imprime Reporte de Promedios Semestrales
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; fileName=Plantilla.xlsx");
        ServletOutputStream stream = response.getOutputStream();

        JRXlsxExporter xlsExporter = new JRXlsxExporter();

        xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(stream));
        SimpleXlsxReportConfiguration xlsReportConfiguration = new SimpleXlsxReportConfiguration();
        SimpleXlsxExporterConfiguration xlsExporterConfiguration = new SimpleXlsxExporterConfiguration();
        xlsReportConfiguration.setOnePagePerSheet(false);
        xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
        //xlsReportConfiguration.setDetectCellType(true);
        xlsReportConfiguration.setWhitePageBackground(false);
        xlsExporter.setConfiguration(xlsReportConfiguration);
        xlsExporter.exportReport();

        System.out.println("Realizo exportManager");
        stream.flush();
        stream.close();
        try {
            if (!con.isClosed()) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(RnGcCatalogoCuentasTblController.class.getName()).log(Level.SEVERE, null, ex);
        }

        FacesContext.getCurrentInstance().responseComplete();
        System.out.println("responseComplete");
    }

    public void addMessage() {
        String summary = selected.getAdicional1() ? "DIOT Seleccionado" : "DIOT Deseleccionado";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }

    public void leerPlantilla(FileUploadEvent event) throws FileNotFoundException, IOException {
        System.out.println("Entro a leer plantilla");
        if (file != null) {
            FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

        try {
            XSSFWorkbook libro = new XSSFWorkbook(event.getFile().getInputstream());
            XSSFSheet hoja = libro.getSheetAt(0);
            Iterator<Row> filas = hoja.iterator();
            Iterator<Cell> celdas;
            Row fila;
            Cell celda;
            while (filas.hasNext()) {
                fila = filas.next();
                celdas = fila.cellIterator();
                System.out.println("La fila es: " + fila.getRowNum());
                while (celdas.hasNext()) {
                    celda = celdas.next();
                    System.out.println("Columna 1 valor: " + celda.getStringCellValue());
                    /*  switch (celda.getColumnIndex()) {
                        case 1:
                            System.out.println("Columna 1 valor: " + celda.getStringCellValue());
                            
                        case 2:
                            System.out.println("Columna 1 valor: " + celda.getStringCellValue());
                           
                        case 3:
                            System.out.println("Columna 1 valor: " + celda.getStringCellValue());
                            
                        case 4:
                            System.out.println("Columna 1 valor: " + celda.getStringCellValue());
                            
                        case 5:
                            System.out.println("Columna 1 valor: " + celda.getStringCellValue());
                            
                        default:
                            System.out.println("default");

                    }*/

                }
                System.out.println("termino  de leer excel");

            }

        } catch (Exception e) {
            e.getMessage();
        }//*/
    }

    public String validarCuenta() {
        String validacion = "";
        if (selected.getNumeroCuenta() == null || selected.getDescripcionCuenta() == null || selected.getAdicional2() == null 
                ||selected.getCodigoAgrupadorSatId() == null || selected.getTipo() == null || selected.getSubtipo() == null || selected.getNaturaleza() == null) {
            RequestContext.getCurrentInstance().execute("PF('CuentasDlgErrorDialogo').show();");
            validacion = "no";
            /*System.out.println("el numero de Cuenta ingresado es: " + selected.getNumeroCuenta());
            List<RnGcCatalogoCuentasTbl> cuenta = new ArrayList<>();
            usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            cuenta = ejbFacade.obtenerListadeNumerosCuentas(selected.getNumeroCuenta(), usuarioId);
            if(!cuenta.isEmpty()){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "La cuenta"+ selected.getNumeroCuenta() +" ya existe con el usuario" + usuarioId.getNombreCompleto()));
            }*/
        }else{
            if (selected.getInicioVigencia() == null) {
                selected.setInicioVigencia(new Date());
            }
            RequestContext.getCurrentInstance().execute("PF('RnGcCatalogoCuentasTblCreateDialog').hide();");
            validacion = "si";
        }
        return validacion;
    }

    public void leerxls(FileUploadEvent event) throws FileNotFoundException, IOException {
        System.out.println("**************************** Entro a leer plantilla ********************************");
        listaCuentasSinRegistrar= new ArrayList<>();
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            XSSFWorkbook excel = new XSSFWorkbook(event.getFile().getInputstream());
            XSSFSheet sheet = excel.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row;
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                Cell cell;
                String linea1 = "";
                //System.out.println("La fila es: " + row.getRowNum());
                if (row.getRowNum() > 15) {
                    prepareCreate();
                    while (cellIterator.hasNext()) {
                        cell = cellIterator.next();
                        if (cell.getColumnIndex() == 1) {
                            //System.out.println("Columna 1 valor: " + cell.toString());
                            if(cell.getCellType() != Cell.CELL_TYPE_BLANK){
                                if(cell.toString().contains(".")){
                                    String[] parts = cell.toString().split("\\.");
                                    //System.out.println("parts: " + parts.length);
                                    if(Integer.parseInt(parts[1]) == 0){
                                        //System.out.println("cuenta " + parts[0]);
                                        selected.setNumeroCuenta(parts[0]);
                                    }else{
                                        //System.out.println("cuenta 1" + cell.toString());
                                        selected.setNumeroCuenta(cell.toString());
                                    }
                                }else{
                                    selected.setNumeroCuenta(cell.toString());
                                }
                                
                            }else{
                                selected.setNumeroCuenta(null);
                                nCuenta=" número de cuenta,";
                            }
                        }

                        if (cell.getColumnIndex() == 2) {
                            //System.out.println("Columna 2 valor: " + cell.toString());
                            if(cell.getCellType() != Cell.CELL_TYPE_BLANK){
                                selected.setDescripcionCuenta(cell.toString());
                            }else{
                                selected.setDescripcionCuenta(null);
                                dCuenta=" descripción de cuenta,";
                            }
                        }

                        if (cell.getColumnIndex() == 3) {
                            //System.out.println("Columna 3 valor: " + cell.toString());
                            if(cell.getCellType() != Cell.CELL_TYPE_BLANK){
                                int val = (int) cell.getNumericCellValue();
                                selected.setAdicional2(String.valueOf(val));
                            }else{
                                selected.setAdicional2(null);
                                nivel = " nivel,";
                            }
                        }
                        
                        if (cell.getColumnIndex() == 4) {
                            //System.out.println("Columna 4 valor: " + cell.toString());
                            if(cell.getCellType() != Cell.CELL_TYPE_BLANK){
                                //System.out.println("colum " + cell.toString());
                                String[] parts = cell.toString().split("\\.");
                                //System.out.println("parts " + parts[0]);
                                if(Integer.parseInt(parts[1]) == 0){
                                    //System.out.println("codigo " + parts[0]);
                                    obtenerCodigoAgrupador(parts[0]);
                                }else{
                                    //System.out.println("codigo 1" + cell.toString());
                                    obtenerCodigoAgrupador(cell.toString());
                                }
                            }else{
                                selected.setCodigoAgrupadorSatId(null);
                                codigo=" codigo agrupador,";
                                //System.out.println("Colum: " + selected.getCodigoAgrupadorSatId());
                            }
                        }

                        if (cell.getColumnIndex() == 5) {
                            //System.out.println("Columna 5 valor: " + cell.toString());
                            if(cell.getCellType() != Cell.CELL_TYPE_BLANK){
                                //System.out.println("Columna: " + cell.toString());
                                int val = (int) cell.getNumericCellValue();
                                selected.setTipo(String.valueOf(val));
                            }else{
                                selected.setTipo(null);
                                tipo=" tipo,";
                                //System.out.println("Colum: " + selected.getTipo());
                            }
                        }
                        
                        if (cell.getColumnIndex() == 6) {
                            //System.out.println("Columna 6 valor: " + cell.toString());
                            if(cell.getCellType() != Cell.CELL_TYPE_BLANK){
                                //System.out.println("Columna: " + cell.toString());
                                int val = (int) cell.getNumericCellValue();
                                selected.setSubtipo(String.valueOf(val));
                            }else{
                                selected.setSubtipo(null);
                                subtipo=" subtipo,";
                                //System.out.println("Colum: " + selected.getSubtipo());
                            }
                        }
                        
                        if (cell.getColumnIndex() == 7) {
                            //System.out.println("Columna 7 valor: " + cell.toString());
                            if(cell.getCellType() != Cell.CELL_TYPE_BLANK){
                                if ("A".equals(cell.toString()) || "D".equals(cell.toString())) {
                                    selected.setNaturaleza(cell.toString());
                                    //System.out.println("naturaleza: " + selected.getNaturaleza());
                                }else if ("Acreedora".equals(cell.toString())) {
                                    selected.setNaturaleza("A");
                                    //System.out.println("naturaleza: " + selected.getNaturaleza());
                                }else if ("Deudora".equals(cell.toString())) {
                                    selected.setNaturaleza("D");
                                    //System.out.println("naturaleza: " + selected.getNaturaleza());
                                }else{
                                    selected.setNaturaleza("");
                                    //System.out.println("naturaleza: " + selected.getNaturaleza());
                                }
                            }else{
                                selected.setNaturaleza(null);
                                natur = " naturaleza,";
                                //System.out.println("Colum: " + selected.getNaturaleza());
                            }
                        }
                        
                        if (cell.getColumnIndex() == 8) {
                            //System.out.println("Columna 8 valor: " + cell.toString());
                            obtenerMoneda(cell.toString());
                        }
                        
                        if (cell.getColumnIndex() == 9) {
                            //System.out.println("Columna 9 valor: " + cell.toString());
                            if ("DIOT".equals(cell.toString().toUpperCase())) {
                                selected.setAdicional1(Boolean.parseBoolean("TRUE"));
                            } else {
                                selected.setAdicional1(Boolean.parseBoolean("FALSE"));
                            }
                        }
                        
                        if (cell.getColumnIndex() == 10) {
                            //System.out.println("Columna 10 valor: " + cell.toString());
                            selected.setRfc(cell.toString());
                        }
                        
                        if (cell.getColumnIndex() == 11) {
                            //System.out.println("Columna 11 valor: " + cell.toString());
                            //  String fecha= cell.toString();
                            //  Date fechafinal = formatoFecha.parse(fecha);
                            //System.out.println("FechaFinal: " + cell.getDateCellValue());
                            selected.setInicioVigencia(cell.getDateCellValue());
                            if (selected.getInicioVigencia() == null) {
                                selected.setInicioVigencia(new Date());
                            }
                        }

                    }

                    /*System.out.println("Objeto: " + selected.getNumeroCuenta() + "||" + selected.getDescripcionCuenta()
                            + "||" + selected.getNaturaleza() + "||" + selected.getInicioVigencia()
                            + "||" + selected.getCodigoAgrupadorSatId() + "||" + selected.getAdicional1());*/
                    try {

                        selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                        selected.setUltimaFechaActualizacion(new Date());
                        if (!validarRegistroParaInsertar()) {
                            ejbFacade.crea(selected);
                            //System.out.println("Creo registro");
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro(s) cargado(s)"));
                        }

                    } catch (Exception e) {
                        System.out.println("Error al crear el registro");
                    }

                    selected = null;
                }

            }
            //System.out.println("Tamaño de la lista de cuentas que no se registraron: " + listaCuentasSinRegistrar.size());
            obtenerCuentasPorUsuario();
            if(!listaCuentasSinRegistrar.isEmpty()){
                motivo = nCuenta+dCuenta+nivel+codigo+natur+tipo+subtipo+rfc;
                motivoVal = ndCuentas+rfcs;
                obtenerCuentasNoRegistradas();
                RequestContext.getCurrentInstance().execute("PF('CuentasDialog').show();"); 
                JsfUtil.addSuccessMessage("Archivo Cargado Correctamente");
                System.out.println("Archivo cargado correctamente");
                nCuenta="";dCuenta="";nivel="";codigo="";natur="";tipo="";subtipo="";rfc="";
                ndCuentas="";rfcs="";
            }
            
        } catch (Exception e) {
            System.out.println("error al crear cuenta: " + e.getMessage());
            JsfUtil.addErrorMessage("Ocurrio un error al cargar las cuentas.");
            e.printStackTrace();
        }//*/
    }
    
    public List<RnGcCatalogoCuentasTbl> obtenerCuentasNoRegistradas(){
        List<RnGcCatalogoCuentasTbl> lista = listaCuentasSinRegistrar;
        if(lista != null){
            for(int i = 0; i < lista.size(); i++)
                System.out.println("lista sin registrar: " + lista.get(i).getNumeroCuenta() + " " + lista.get(i).getDescripcionCuenta()); 
        }
        return lista;
    }

    public void obtenerCodigoAgrupador(String codigoAgrupador) {
        System.out.println("Entro a obtener codigo agrupador del SAT");
        RnGcCodigoAgrupadorSatTbl codigoSAT = new RnGcCodigoAgrupadorSatTbl();
        if (codigoAgrupador != null) {
            String[] parts = codigoAgrupador.split("\\.");
            //System.out.println("parts " + parts[0]);
            if(parts.length == 1){
                //System.out.println("codigo " + parts[0]);
                codigoAgrupador = parts[0];
                //System.out.println("---------");
            }
            //System.out.println("codigoAgrupador " + codigoAgrupador);
            codigoSAT = codigoAgrupadorFacade.getCodigoAgrupador(codigoAgrupador);
            selected.setCodigoAgrupadorSatId(codigoSAT);
        }
    }
    
    public void obtenerMoneda(String moneda) {
        System.out.println("Entro a obtener moneda");
        RnGcMonedasTbl monedaId = new RnGcMonedasTbl();
        if (moneda != null) {
            monedaId = monedaFacade.obtenerMonedas(moneda);
            selected.setMonedaId(monedaId);
            System.out.println("La moneda es " + monedaId.getCMoneda());
        }
    }

    public List<RnGcCatalogoCuentasTbl> obtenerCuentasPorUsuario() {
        usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        //System.out.println("Entró a obtenerPolizasPorUsuario con el usuario: " + usuarioId.getNombreCompleto());
        List<RnGcCatalogoCuentasTbl> listaCuentasPorUsuario = new ArrayList<>();
        listaCuentasPorUsuario = null;
        listaCuentasPorUsuario = ejbFacade.obtenerListaCuentas(usuarioId);
        //System.out.println("El tamaño de la listacuentasPorUsuario es: " + listaCuentasPorUsuario.size());
        return listaCuentasPorUsuario;

    }

    public boolean validarRegistroParaInsertar() {
        //System.out.println("Entro a validar el registro que se va a insertar en el catalogo de cuentas");
        usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        boolean var = false;
        if(selected.getNumeroCuenta() == null && selected.getDescripcionCuenta() == null && selected.getAdicional2() == null 
                && selected.getCodigoAgrupadorSatId() == null && selected.getTipo() == null && selected.getSubtipo() == null
                && selected.getNaturaleza() == null){
            //System.out.println("No hay registro");
            nCuenta="";dCuenta="";nivel="";codigo="";natur="";tipo="";subtipo="";rfc="";
            var = true;
        }else{
          if (selected.getNumeroCuenta() != null) {
            if (selected.getDescripcionCuenta() != null) {
                if (selected.getAdicional2() != null) {
                    if (selected.getCodigoAgrupadorSatId() != null) {
                        if (selected.getTipo() != null) {
                            if (selected.getSubtipo() != null) {
                                if (selected.getNaturaleza() != null) {
                                    List<RnGcCatalogoCuentasTbl> listaCuentas = new ArrayList<>();
                                    List<RnGcCatalogoCuentasTbl> cuenta = new ArrayList<>();
                                    List<RnGcCatalogoCuentasTbl> cuenta1 = new ArrayList<>();
                                    cuenta1 = ejbFacade.obtenerListadeCuentasDescripcion(selected.getDescripcionCuenta(), usuarioId);
                                    listaCuentas = ejbFacade.obtenerListadeNumerosCuentas(selected.getNumeroCuenta(), usuarioId );
                                    if (listaCuentas.isEmpty() && cuenta1.isEmpty()) {
                                        if(selected.getRfc() == null && !selected.getAdicional1()){
                                            selected.setRfc("");
                                            var = false;
                                        }else if(selected.getRfc() != null && !selected.getAdicional1()){
                                            var = false;
                                        }else if(selected.getRfc() != null && selected.getAdicional1()){
                                            cuenta = ejbFacade.obtenerListadeCuentasRFC(selected.getRfc(), usuarioId, true);
                                            if(!cuenta.isEmpty()){
                                                var = true;
                                                //System.out.println("El RFC ya existe");
                                                listaCuentasSinRegistrar.add(selected);
                                                rfcs=" rfc,";
                                            }else
                                                var = false;
                                        }else if(selected.getRfc() == null && selected.getAdicional1()){
                                            System.out.println("El RFC es obligatorio porque llenaron el campo DIOT");
                                            listaCuentasSinRegistrar.add(selected);
                                            rfc=" rfc,";
                                            var = true;
                                        }
                                    }else{
                                        //System.out.println("Ya hay un registro con ese numero de cuenta o con esa descripcion de cuenta");
                                        listaCuentasSinRegistrar.add(selected);
                                        ndCuentas = " número o descripción de cuenta,";
                                        var = true;
                                    }
                                }else{
                                    System.out.println("El campo naturaleza es obligatorio");
                                    listaCuentasSinRegistrar.add(selected);
                                    var = true;
                                }
                            }else{
                                System.out.println("El campo subtipo es obligatorio");
                                listaCuentasSinRegistrar.add(selected);
                                var = true;
                            }
                        }else{
                            System.out.println("El campo tipo es obligatorio");
                            listaCuentasSinRegistrar.add(selected);
                            var = true;
                        }
                    }else{
                        System.out.println("El campo codigo agrupador es obligatorio");
                        listaCuentasSinRegistrar.add(selected);
                        var = true;
                    }
                }else{
                    System.out.println("El campo nivel es obligatorio");
                    listaCuentasSinRegistrar.add(selected);
                    var = true;
                }
            }else{
                System.out.println("El campo descripcion de cuenta es obligatorio");
                listaCuentasSinRegistrar.add(selected);
                var = true;
            }
        }else{
            System.out.println("El campo numero de cuenta es obligatorio");
            listaCuentasSinRegistrar.add(selected);
            var = true;
        }
        }
        return var;
    }

}
