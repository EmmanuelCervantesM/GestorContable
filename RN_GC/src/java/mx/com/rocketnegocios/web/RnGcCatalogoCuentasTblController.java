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
import java.math.BigDecimal;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
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
import javax.faces.view.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import mx.com.rocketnegocios.beans.RnGcCodigoAgrupadorSatTblFacade;
import mx.com.rocketnegocios.beans.RnGcMonedasTblFacade;
import mx.com.rocketnegocios.beans.RnGcPeriodosTblFacade;
import mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade;
import mx.com.rocketnegocios.entities.ListaCuentas;
import mx.com.rocketnegocios.entities.RnGcCodigoAgrupadorSatTbl;
import mx.com.rocketnegocios.entities.RnGcMonedasTbl;
import mx.com.rocketnegocios.entities.RnGcPeriodosTbl;
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
import org.apache.poi.ss.usermodel.FormulaEvaluator;
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
    
    private static final long serialVersionUID = 1L;
    
    @PersistenceContext
    private EntityManager em;
    
    @EJB
    private RnGcMonedasTblFacade monedaFacade;
    
    @EJB
    private RnGcCatalogoCuentasTblFacade ejbFacade;
    
    @EJB
    private RnGcMonedasTblFacade ejbMonedas;
    
    @EJB
    private RnGcCodigoAgrupadorSatTblFacade ejbCodigoAgrupadorSat;
    
    @EJB 
    private RnGcPeriodosTblFacade periodosFacade;
    
    @EJB 
    private RnGcUsuariosTblFacade usuariosFacade;
    
    @EJB
    private RnGcCatalogoCuentasTblFacade catalogoCuentasFacade;

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
    private Date periodoActivoInicio;   
    private Date periodoActivoFin;      
    private String periodoActivoLabel; 
    private List<RnGcCatalogoCuentasTbl> prevalidas;
    private List<RnGcCatalogoCuentasTbl> rechazadas;
    private List<Object> rechazadasDetalladas; 
    private int totalLeidas, totPrevalidas, totRechazadas;
    public List<RnGcCatalogoCuentasTbl> getPrevalidas() { return prevalidas; }
    public List<RnGcCatalogoCuentasTbl> getRechazadas() { return rechazadas; }
    public int getTotalLeidas() { return totalLeidas; }
    public int getTotPrevalidas() { return totPrevalidas; }
    public int getTotRechazadas() { return totRechazadas; }
    private Set<String> codigosMonedaValidos = new HashSet<>();
    private static final String REQ_CUENTA      = "cuenta";
    private static final String REQ_DESCRIPCION = "descripción";
    private static final String REQ_TIPO        = "tipo";
    private static final String REQ_SUBTIPO     = "subtipo";
    private static final String REQ_AGRUPADOR   = "agrupador";
    private static final String REQ_MONEDA      = "moneda";
    private static final String REQ_DIOT        = "diot";
    private static final String REQ_RFC         = "rfc";
    private static final String REQ_INICIAL         = "saldo";
    private Map<String, Integer> mapaMonedas = new HashMap<>();
    private Map<String, Integer> idMonedaPorCodigo;
    private Map<String, Integer> idAgrupadorPorCodigo = new HashMap<>();
    private Set<String> codigosAgrupadorValidos = new HashSet<>();
    private boolean agrupadoresCargados = false;
    private RnGcPeriodosTbl periodoActivo;
    private Integer idPeriodoActivo;
    private static final Set<String> CONCEPTOS_DEUDORA = new HashSet<>(Arrays.asList(
        "ACTIVO", "COSTO", "GASTO"
    ));
    private static final Set<String> CONCEPTOS_ACREEDORA = new HashSet<>(Arrays.asList(
        "PASIVO", "CAPITAL", "INGRESO"
    ));
    private String normaliza(String s) {
        return (s == null) ? null : s.trim().toUpperCase(Locale.ROOT);
    }
    
    @EJB
    private RnGcPeriodosTblFacade rnGcPeriodosTblFacade;

    public RnGcPeriodosTbl getPeriodoActivo() {
        if (periodoActivo == null) {
            Integer idUsuario = null;
            try {
                idUsuario = usuarioFirmado != null ? usuarioFirmado.obtenerIdUsuario() : null;
            } catch (Exception e) {
            }
            if (idUsuario != null) {
                periodoActivo = rnGcPeriodosTblFacade.obtenerPeriodoActivo(idUsuario);
            }
        }
        return periodoActivo;
    }

    public String getPeriodoActivoDescripcion() {
        RnGcPeriodosTbl p = getPeriodoActivo();
        if (p == null) {
            return "Sin periodo activo";
        }

        String mesStr = p.getMes();
        int mesNum = Integer.parseInt(mesStr);
        String nombreMes = nombreMesEnEsp(mesNum);

        return nombreMes + " " + p.getAnio();
    }

    private String nombreMesEnEsp(int mes) {
        switch (mes) {
            case 1:  return "ENERO";
            case 2:  return "FEBRERO";
            case 3:  return "MARZO";
            case 4:  return "ABRIL";
            case 5:  return "MAYO";
            case 6:  return "JUNIO";
            case 7:  return "JULIO";
            case 8:  return "AGOSTO";
            case 9:  return "SEPTIEMBRE";
            case 10: return "OCTUBRE";
            case 11: return "NOVIEMBRE";
            case 12: return "DICIEMBRE";
            default: return "";
        }
    }

    public Date getMinFechaPeriodo() {
        RnGcPeriodosTbl p = getPeriodoActivo();
        if (p == null) {
            return null;
        }

        int mes = Integer.parseInt(p.getMes());
        int anio = p.getAnio();

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(anio, mes - 1, 1);
        return cal.getTime();
    }

    public Date getMaxFechaPeriodo() {
        RnGcPeriodosTbl p = getPeriodoActivo();
        if (p == null) {
            return null;
        }

        int mes = Integer.parseInt(p.getMes());
        int anio = p.getAnio();

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(anio, mes - 1, 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    
    @PostConstruct
    public void init() {
        cargarPeriodoActivo();  
        idMonedaPorCodigo = new HashMap<String, Integer>();
        codigosMonedaValidos = new HashSet<String>();

        try {
            cargarMonedasValidas();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(">>> Error cargando monedas válidas en init(): " + e.getMessage());
        }
    }
    
    public void prepararCargaMasiva() {
        RequestContext rc = RequestContext.getCurrentInstance();

        try {
            Integer uid = (usuarioFirmado != null) ? usuarioFirmado.obtenerIdUsuario() : null;
            RnGcUsuariosTbl user = (uid != null) ? usuariosFacade.obtenerUsuarioPorId(uid) : null;
            RnGcPeriodosTbl p   = (user != null) ? periodosFacade.findActivo(user) : null;

            if (p == null) {
                
                JsfUtil.addErrorMessage("Se debe activar un periodo");
                rc.addCallbackParam("periodoActivo", false);
                return;
            }

            rc.addCallbackParam("periodoActivo", true);

        } catch (Exception e) {
            JsfUtil.addErrorMessage("Ocurrió un error al validar el periodo activo.");
            rc.addCallbackParam("periodoActivo", false);
        }
    }

    public void cargarPeriodoActivo() {
        try {
            Integer uid = (usuarioFirmado != null) ? usuarioFirmado.obtenerIdUsuario() : null;
            RnGcUsuariosTbl user = (uid != null) ? usuariosFacade.obtenerUsuarioPorId(uid) : null;

            RnGcPeriodosTbl p = (user != null) ? periodosFacade.findActivo(user) : null;
            if (p != null) {
                this.periodoActivo = p;
                this.idPeriodoActivo = p.getId();

                this.periodoActivoInicio = primerDiaMes(p.getFechaInicioPeriodo());
                this.periodoActivoFin    = ultimoDiaMes(p.getFechaInicioPeriodo());

                java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("MMMM yyyy", new java.util.Locale("es","MX"));
                this.periodoActivoLabel = df.format(p.getFechaInicioPeriodo()).toUpperCase(new java.util.Locale("es","MX"));
            } else {
                 this.periodoActivo = null;
                this.idPeriodoActivo = null;
                this.periodoActivoInicio = null;
                this.periodoActivoFin    = null;
                this.periodoActivoLabel  = "Se debe activar un periodo";
            }
        } catch (Exception e) {
            this.periodoActivoInicio = null;
            this.periodoActivoFin    = null;
            this.periodoActivoLabel  = "Se debe activar un periodo";
        }
    }

    private Date primerDiaMes(Date d) {
        if (d == null) return null;
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(d);
        c.set(java.util.Calendar.DAY_OF_MONTH, 1);
        resetHora(c);
        return c.getTime();
    }
    private Date ultimoDiaMes(Date d) {
        if (d == null) return null;
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(d);
        c.set(java.util.Calendar.DAY_OF_MONTH, c.getActualMaximum(java.util.Calendar.DAY_OF_MONTH));
        // último día a 23:59:59.999 para permitir seleccionarlo
        c.set(java.util.Calendar.HOUR_OF_DAY, 23);
        c.set(java.util.Calendar.MINUTE, 59);
        c.set(java.util.Calendar.SECOND, 59);
        c.set(java.util.Calendar.MILLISECOND, 999);
        return c.getTime();
    }
    private void resetHora(java.util.Calendar c) {
        c.set(java.util.Calendar.HOUR_OF_DAY, 0);
        c.set(java.util.Calendar.MINUTE, 0);
        c.set(java.util.Calendar.SECOND, 0);
        c.set(java.util.Calendar.MILLISECOND, 0);
    }

    public Date getPeriodoActivoInicio() { return periodoActivoInicio; }
    public Date getPeriodoActivoFin()    { return periodoActivoFin; }
    public String getPeriodoActivoLabel(){ return periodoActivoLabel; }

    public void refrescarPeriodoActivo() {
        cargarPeriodoActivo();
    }

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

    public void prepareCreate() {
        RequestContext rc = RequestContext.getCurrentInstance();

        try {
            Integer uid = (usuarioFirmado != null) ? usuarioFirmado.obtenerIdUsuario() : null;
            RnGcUsuariosTbl user = (uid != null) ? usuariosFacade.obtenerUsuarioPorId(uid) : null;
            RnGcPeriodosTbl p   = (user != null) ? periodosFacade.findActivo(user) : null;

            if (p == null) {
                // NO hay periodo activo
                JsfUtil.addErrorMessage("Se debe activar un periodo");
                rc.addCallbackParam("periodoActivo", false);
                return;
            }

            // SÍ hay periodo activo: preparas el selected
            selected = new RnGcCatalogoCuentasTbl();
            initializeEmbeddableKey();

            // Usas el periodoActivoInicio si ya lo traes calculado en @PostConstruct
            if (periodoActivoInicio != null) {
                selected.setInicioVigencia(periodoActivoInicio);
            } else if (p.getFechaInicioPeriodo() != null) {
                selected.setInicioVigencia(p.getFechaInicioPeriodo());
            }

            rc.addCallbackParam("periodoActivo", true);

        } catch (Exception e) {
            JsfUtil.addErrorMessage("Ocurrió un error al validar el periodo activo.");
            rc.addCallbackParam("periodoActivo", false);
        }
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
    
    public void moverRechazadasAValidas() {
        try {
            // aquí metes la lógica para mover de listaRechazadas a listaValidas
            // por ejemplo:
            // catalogoService.moverRechazadasAValidas(usuarioFirmado, periodoActual);

            JsfUtil.addSuccessMessage("Las cuentas rechazadas fueron movidas a válidas correctamente.");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Ocurrió un error al mover las cuentas rechazadas a válidas.");
            // log.error("error...", e);
        }
    }

    public void revalidarRechazadas() {
        try {
            // lógica para revalidar las rechazadas
            // catalogoService.revalidarRechazadas(usuarioFirmado, periodoActual);

            JsfUtil.addSuccessMessage("Las cuentas rechazadas fueron revalidadas correctamente.");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Ocurrió un error al revalidar las cuentas rechazadas.");
            // log.error("error...", e);
        }
    }

        // Normaliza mínimamente (quita espacios extremos)
     private static String safeTrim(String s) {
         return s == null ? "" : s.trim();
     }

     private static String canonicalCodeKey(String raw) {
         if (raw == null) return null;
         raw = raw.trim();
         String digits = raw.replaceAll("\\D", "");
         return digits.isEmpty() ? raw : digits;
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

    
    // ====== NORMALIZACIÓN ======
    private static String norm(String s) {
        if (s == null) return "";
        s = s.trim()
             .replace('á','a').replace('Á','A')
             .replace('é','e').replace('É','E')
             .replace('í','i').replace('Í','I')
             .replace('ó','o').replace('Ó','O')
             .replace('ú','u').replace('Ú','U')
             .replace('ü','u').replace('Ü','U')
             .replace('ñ','n').replace('Ñ','N');
        s = s.replaceAll("\\s+", " "); // colapsa espacios (incluye NBSP)
        return s.toLowerCase();
    }


    // ====== REGLAS DE NATURALEZA (ajústalas) ======
    private static class NaturaRules {
        final Map<String,String> exact = new HashMap<>();
        final List<Map.Entry<String,String>> prefixes = new ArrayList<>();
    }
    private static NaturaRules buildNaturalezaRules() {
        NaturaRules rules = new NaturaRules();
        // Exactos ejemplo:
        // rules.exact.put("101", "D");

        // Prefijos (ajusta a tu operación / SAT)
        rules.prefixes.add(new AbstractMap.SimpleEntry<>("1", "D"));
        rules.prefixes.add(new AbstractMap.SimpleEntry<>("2", "A"));
        rules.prefixes.add(new AbstractMap.SimpleEntry<>("3", "A"));
        rules.prefixes.add(new AbstractMap.SimpleEntry<>("4", "A"));
        rules.prefixes.add(new AbstractMap.SimpleEntry<>("5", "D"));
        rules.prefixes.add(new AbstractMap.SimpleEntry<>("6", "D"));
        rules.prefixes.add(new AbstractMap.SimpleEntry<>("7", "D"));
        rules.prefixes.add(new AbstractMap.SimpleEntry<>("8", "D"));
        rules.prefixes.add(new AbstractMap.SimpleEntry<>("9", "D"));
        // prefijo más largo gana
        java.util.Collections.sort(rules.prefixes, new Comparator<Map.Entry<String,String>>() {
            public int compare(Map.Entry<String,String> a, Map.Entry<String,String> b) {
                return Integer.compare(b.getKey().length(), a.getKey().length());
            }
        });
        return rules;
    }
    private static String resolveNaturaleza(String codeKey, NaturaRules rules) {
        if (codeKey == null || codeKey.isEmpty() || rules == null) return null;
        String ex = rules.exact.get(codeKey);
        if (ex != null) return ex;
        for (Map.Entry<String,String> e : rules.prefixes) {
            if (codeKey.startsWith(e.getKey())) return e.getValue();
        }
        return null;
    }


    // Lee 1 vez el catálogo desde Excel y llena el mapa
    private Map<String,Integer> loadCatalogNivelFromExcel(java.io.InputStream in) throws IOException {
        Map<String,Integer> map = new HashMap<>();
        org.apache.poi.xssf.usermodel.XSSFWorkbook wb = null;
        try {
            wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook(in);
            XSSFSheet sh = wb.getSheetAt(0);
            DataFormatter fmt = new DataFormatter();

            // Detectar cabeceras del catálogo
            List<String> dups = new ArrayList<>();
            AbstractMap.SimpleEntry<Integer, Map<Integer,String>> hi = findHeaderRow(sh, fmt, dups);
            if (hi == null) throw new IOException("Catálogo: no se encontró fila de cabeceras.");
            Map<Integer,String> header = hi.getValue();

            // Columnas candidatas del catálogo:
            Integer colCodigo = null, colNivel = null;
            // "Código agrupador" suele venir con NBSP, por eso usamos norma general
            for (Map.Entry<Integer,String> e : header.entrySet()) {
                String h = norm(e.getValue());
                if (colCodigo == null && (h.contains("codigo") && h.contains("agrupador"))) colCodigo = e.getKey();
                if (colNivel == null && (h.equals("nivel") || h.contains("nivel"))) colNivel = e.getKey();
            }
            if (colCodigo == null || colNivel == null)
                throw new IOException("Catálogo: faltan columnas 'Código agrupador' y/o 'Nivel'.");

            // Recorrer filas de datos del catálogo
            for (int r = hi.getKey() + 1; r <= sh.getLastRowNum(); r++) {
                Row row = sh.getRow(r);
                if (row == null) continue;
                String codigoRaw = fmt.formatCellValue(row.getCell(colCodigo)).trim();
                String nivelRaw  = fmt.formatCellValue(row.getCell(colNivel)).trim();
                if (codigoRaw.isEmpty() || nivelRaw.isEmpty()) continue;

                String key = canonicalCodeKey(codigoRaw); // “101.0” -> “101”
                // parse nivel (acepta "3", "3.0")
                Integer nivel;
                try {
                    if (nivelRaw.matches("\\d+")) nivel = Integer.valueOf(nivelRaw);
                    else nivel = Integer.valueOf((int) Double.parseDouble(nivelRaw.replace(",", ".")));
                } catch (Exception ex) {
                    continue;
                }
                if (!key.isEmpty() && !map.containsKey(key)) {
                    map.put(key, nivel);
                }
            }
        } finally {
            if (in != null) try { in.close(); } catch (Exception ignore) {}
        }
        return map;
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
            //selected.setMonedaId(monedaId);
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
    
    // === NUEVO: selección múltiple de la tabla de válidas ===
    private List<RnGcCatalogoCuentasTbl> selectedPrevalidas = new ArrayList<>();

    public List<RnGcCatalogoCuentasTbl> getSelectedPrevalidas() {
        return selectedPrevalidas;
    }
    public void setSelectedPrevalidas(List<RnGcCatalogoCuentasTbl> selectedPrevalidas) {
        this.selectedPrevalidas = selectedPrevalidas;
    }

    private boolean editarRechazadas; 
    private List<RnGcCatalogoCuentasTbl> selectedRechazadas;       // seleccionadas en tabla rechazadas
    public void toggleEditarRechazadas() { this.editarRechazadas = !this.editarRechazadas; }

    // === (Opcional) habilitar/deshabilitar edición por celda ===
    private boolean editar = false;
    public boolean isEditar() { return editar; }
    public void setEditar(boolean editar) { this.editar = editar; }
    public void toggleEditar() { this.editar = !this.editar; }

    // Eliminar seleccionadas en válidas (solo memoria)
    public void eliminarSeleccionPrevalidas() {
        if (selectedPrevalidas != null && !selectedPrevalidas.isEmpty()) {
            prevalidas.removeAll(new ArrayList<>(selectedPrevalidas));
            selectedPrevalidas.clear();
            totPrevalidas = prevalidas.size();
            JsfUtil.addSuccessMessage("Registros válidos eliminados de la lista temporal.");
        }
    }

    // Eliminar seleccionadas en rechazadas (solo memoria)
    public void eliminarSeleccionRechazadas() {
        if (selectedRechazadas != null && !selectedRechazadas.isEmpty()) {
            rechazadas.removeAll(new ArrayList<>(selectedRechazadas));
            selectedRechazadas.clear();
            totRechazadas = rechazadas.size();
            JsfUtil.addSuccessMessage("Registros rechazados eliminados de la lista temporal.");
        }
    }

    // Confirmar (guardar en BD) lo válido (puedes guardar todos o sólo seleccionados)
    public void guardarCorrecto() {
        List<RnGcCatalogoCuentasTbl> aGuardar =
            (selectedPrevalidas != null && !selectedPrevalidas.isEmpty())
                ? new ArrayList<>(selectedPrevalidas)
                : new ArrayList<>(prevalidas);

        if (aGuardar.isEmpty()) {
            JsfUtil.addErrorMessage("No hay registros válidos para guardar.");
            return;
        }
        
        Integer uid = (usuarioFirmado != null) ? usuarioFirmado.obtenerIdUsuario() : null;
        RnGcUsuariosTbl user = (uid != null) ? usuariosFacade.obtenerUsuarioPorId(uid) : null;
        RnGcPeriodosTbl p = (user != null) ? periodosFacade.findActivo(user) : null;

        // Asegura que tengas un usuario válido
        Integer userId = null;
        try { userId = usuarioFirmado != null ? usuarioFirmado.obtenerIdUsuario() : null; } catch (Exception ignore) {}
        if (userId == null) {
            // fallback seguro (ajústalo a tu contexto)
            userId = 0; // o lanza error si prefieres
        }

        Date ahora = new Date();
        int ok = 0, fail = 0;
        for (RnGcCatalogoCuentasTbl c : aGuardar) {
            try {
                if (c.getFechaCreacion() == null) c.setFechaCreacion(ahora);
                if (c.getInicioVigencia() == null) c.setInicioVigencia(ahora);

                // campos obligatorios de auditoría:
                if (c.getCreadoPor() == null) c.setCreadoPor(userId);
                c.setUltimaActualizacionPor(userId);
                c.setIdPeriodo(p.getPeriodoId());
                c.setUltimaFechaActualizacion(ahora);
                
                 // ===== S A L D O S  =====
                // Si viene null del Excel, lo normalizamos a 0
                if (c.getSaldoInicial() == null) {
                    c.setSaldoInicial(BigDecimal.ZERO);
                }

                // Si saldo_actual viene null, lo inicializamos igual al saldo_inicial
                if (c.getSaldoActual() == null) {
                    c.setSaldoActual(c.getSaldoInicial());
                }

                // si usas DIOT como String, asegúrate de no enviar nulls inesperados
                if (c.getAdicional1() == null) c.setAdicional1("FALSE");

                ejbFacade.crea(c);
                ok++;
            } catch (Exception ex) {
                fail++;
                // opcional: loguea 'ex' con más detalle
            }
        }

        prevalidas.removeAll(aGuardar);
        if (selectedPrevalidas != null) selectedPrevalidas.clear();
        totPrevalidas = prevalidas.size();

        JsfUtil.addSuccessMessage("Guardados: " + ok + " | Fallidos: " + fail);
    }

    // Cancelar (limpia y cierra)
    public void cancelarCarga() {
        if (prevalidas != null) prevalidas.clear();
        if (rechazadas != null) rechazadas.clear();
        if (selectedPrevalidas != null) selectedPrevalidas.clear();
        if (selectedRechazadas != null) selectedRechazadas.clear();
        totPrevalidas = 0;
        totRechazadas = 0;
        // puedes resetear contadores / flags extra
        this.editar = false;
        this.editarRechazadas = false;
        JsfUtil.addSuccessMessage("Proceso cancelado y datos temporales descartados.");
    }
    

    private boolean diotEsTrueEstricto(String s) {
        if (s == null) return false;
        String v = s.trim().toLowerCase();
        return v.equals("true") || v.equals("si") || v.equals("sí") || v.equals("1");
    }
    private String parseDiot(String s) {
        return diotEsTrueEstricto(s) ? "TRUE" : "FALSE";
    }
    private int inferNivelDesdeAgrupador(String agr) {
        // Ejemplo: regresa dígitos iniciales o longitud, ajusta a tu regla real
        String onlyDigits = agr.replaceAll("\\D", "");
        if (onlyDigits.isEmpty()) return -1;
        return Math.min(onlyDigits.length(), 9);
    }
    private String resolveNaturalezaDesdeAgrupador(String agr) {
        // Ejemplo: pon tu mapeo real; por ahora “D”/“A” según heurística simple
        return agr != null && agr.startsWith("1") ? "D" : "A";
    }
    private RnGcCatalogoCuentasTbl copiaLigera(RnGcCatalogoCuentasTbl c) {
        RnGcCatalogoCuentasTbl x = new RnGcCatalogoCuentasTbl();
        x.setNumeroCuenta(c.getNumeroCuenta());
        x.setDescripcionCuenta(c.getDescripcionCuenta());
        x.setTipo(c.getTipo());
        x.setSubtipo(c.getSubtipo());
        x.setAdicional2(c.getAdicional2());
        x.setNaturaleza(c.getNaturaleza());
        x.setRfc(c.getRfc());
        x.setMoneda(c.getMoneda());
        x.setAdicional1(c.getAdicional1());
        x.setSaldoInicial(c.getSaldoInicial());
        x.setSaldoActual(c.getSaldoActual());
        return x;
    }
    private AbstractMap.SimpleEntry<Integer, Map<Integer,String>> findHeaderRow(
            XSSFSheet sheet, DataFormatter fmt, List<String> duplicadas) {
        // Busca cabecera en primeras 16 filas (ajusta)
        for (int r=0; r<=Math.min(sheet.getLastRowNum(), 15); r++) {
            Row row = sheet.getRow(r);
            if (row == null) continue;
            Map<Integer,String> idx2name = new LinkedHashMap<>();
            Set<String> seen = new HashSet<>();
            boolean ok = false;
            for (int c=0; c<row.getLastCellNum(); c++) {
                String val = fmt.formatCellValue(row.getCell(c)).trim().toLowerCase();
                if (!val.isEmpty()) {
                    ok = true;
                    if (!seen.add(val)) duplicadas.add(val);
                    idx2name.put(c, val);
                }
            }
            if (ok) return new AbstractMap.SimpleEntry<>(r, idx2name);
        }
        return null;
    }
    private Integer findColIndex(Map<Integer,String> headerByIndex, String keyLike) {
        if (headerByIndex == null) return null;
        String k = keyLike.toLowerCase();
        for (Map.Entry<Integer,String> e: headerByIndex.entrySet()) {
            String h = e.getValue();
            if (h.contains(k)) return e.getKey();
        }
        return null;
    }
    // true = YA EXISTE/NO insertar; false = OK para insertar en preview
    private boolean validarRegistroParaInsertarPreview(RnGcCatalogoCuentasTbl cand) {
        // Implementación real tuya. Aquí un ejemplo contra número de cuenta:
        if (cand.getNumeroCuenta() == null) return true;
        Long cnt = em.createQuery(
                "select count(c) from RnGcCatalogoCuentasTbl c where c.numeroCuenta = :n",
                Long.class)
            .setParameter("n", cand.getNumeroCuenta())
            .getSingleResult();
        return cnt != null && cnt > 0;
    }
    
    private String normalizaDiot(String raw) {
        if (raw == null) return "FALSE";
        String v = raw.trim().toUpperCase();

        if ("TRUE".equals(v) || "1".equals(v) || "SI".equals(v) || "SÍ".equals(v) || "X".equals(v)) {
            return "TRUE";
        }
        if ("FALSE".equals(v) || "0".equals(v) || "NO".equals(v) || v.isEmpty()) {
            return "FALSE";
        }
        // Si viene algo raro, lo tratamos como FALSE (o podrías agregar error si quieres)
        return "FALSE";
    }

    private boolean esRfcValido(String rfc) {
        if (rfc == null) return false;
        String v = rfc.trim().toUpperCase();
        // RFC (simplificado): PM o PF con homoclave
        return v.matches("^[A-ZÑ&]{3,4}[0-9]{6}[A-Z0-9]{3}$");
    }
    
    private boolean rfcYaRegistradoEnCatalogo(String rfc) {
        try {
            return catalogoCuentasFacade.existeRfcEnCatalogo(rfc);
        } catch (Exception e) {
            // si quieres, logueas y por seguridad consideras que sí está
            // LOG.error("Error al validar RFC en catálogo", e);
            return true;
        }
    }
    
    private void cargarMonedasValidas() {

        if (idMonedaPorCodigo == null) {
            idMonedaPorCodigo = new HashMap<String, Integer>();
        }
        if (codigosMonedaValidos == null) {
            codigosMonedaValidos = new HashSet<String>();
        }

        idMonedaPorCodigo.clear();
        codigosMonedaValidos.clear();

        // Si el EJB no se inyectó, evita NPE y avisa en log
        if (ejbMonedas == null) {
            System.out.println(">>> ejbMonedas es NULL, no se inyectó RnGcMonedasTblFacade");
            return;
        }

        List<RnGcMonedasTbl> lista = ejbMonedas.findAll();
        if (lista == null || lista.isEmpty()) {
            System.out.println(">>> No se encontraron registros en rn_gc_monedas_tbl");
            return;
        }

        for (RnGcMonedasTbl m : lista) {
            if (m == null) {
                continue;
            }

            String codigo = m.getCMoneda(); 
            Integer id    = m.getId();    

            if (codigo == null || id == null) {
                continue;
            }

            codigo = codigo.trim().toUpperCase();
            if (codigo.isEmpty()) {
                continue;
            }

            codigosMonedaValidos.add(codigo);
            idMonedaPorCodigo.put(codigo, id);
        }

        System.out.println(">>> Monedas válidas cargadas: " + codigosMonedaValidos);
    }
    
    private void cargarAgrupadoresValidos() {
        if (agrupadoresCargados) return;

        idAgrupadorPorCodigo.clear();
        codigosAgrupadorValidos.clear();

        List<RnGcCodigoAgrupadorSatTbl> lista = ejbCodigoAgrupadorSat.findAll();
        for (RnGcCodigoAgrupadorSatTbl a : lista) {
            if (a.getCodigoAgrupador() != null) {
                String cod = normalizaCodigoAgrupador(a.getCodigoAgrupador());
                codigosAgrupadorValidos.add(cod);
                idAgrupadorPorCodigo.put(cod, a.getId());
            }
        }

        agrupadoresCargados = true;
        System.out.println(">>> Agrupadores SAT válidos cargados: " + codigosAgrupadorValidos.size());
    }
    
    private String normalizaCodigoAgrupador(String s) {
        if (s == null) return null;
        s = s.trim().toUpperCase();
        s = s.replaceAll("[^0-9A-Z\\.\\-]", "");
        return s.isEmpty() ? null : s;
    }
    
    private String extraerCodigoMoneda(String celda) {
        if (celda == null) return null;
        String t = celda.trim().toUpperCase();
        if (t.isEmpty()) return null;

        // si viene "MXN Pesos" -> nos quedamos con "MXN"
        int space = t.indexOf(' ');
        if (space > 0) {
            t = t.substring(0, space);
        }

        // si viene algo tipo "Pesos (MXN)" -> sacamos lo que va entre paréntesis
        int par = t.indexOf('(');
        if (par >= 0) {
            int fin = t.indexOf(')', par + 1);
            if (fin > par + 1) {
                t = t.substring(par + 1, fin).trim();
            }
        }

        return t.isEmpty() ? null : t;
    }
    
    private String normalizaNaturaleza(String s){
        if (s == null) return null;
        s = s.trim().toUpperCase();

        // unifica variantes comunes
        if (s.equals("DEUDORA") || s.equals("D")) return "D";
        if (s.equals("ACREEDORA") || s.equals("A")) return "A";

        return s.isEmpty() ? null : s;
    }

    private String calcularNaturalezaPorCodigoAgrupador(String codigoAgrupadorTxt, List<String> errs) {
        Integer code = extraeCodigoBase(codigoAgrupadorTxt);
        if (code == null) {
            errs.add("Código agrupador SAT inválido para calcular naturaleza: " + codigoAgrupadorTxt);
            return null;
        }

        // Reglas no ambiguas
        if (code >= 100 && code <= 199) return "D"; // Activo
        if (code >= 200 && code <= 499) return "A"; // Pasivo/Capital/Ingreso
        if (code >= 500 && code <= 699) return "D"; // Costo/Gasto

        // Rangos ambiguos en tu tabla
        if ((code >= 700 && code <= 799) || (code >= 800 && code <= 899)) {
            errs.add("Naturaleza requerida manualmente para código agrupador " + code +
                     " (700–899 es ambiguo sin concepto).");
            return null;
        }

        errs.add("Código agrupador fuera de rango esperado para naturaleza: " + code);
        return null;
    }

    private Integer extraeCodigoBase(String codigoTxt) {
        if (codigoTxt == null) return null;
        String t = codigoTxt.trim();
        if (t.isEmpty()) return null;

        // deja números y puntos (por si viene 101.01, 200-01, etc.)
        t = t.replaceAll("[^0-9.]", "");
        if (t.isEmpty()) return null;

        try {
            double d = Double.parseDouble(t);
            return (int) Math.floor(d);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    private Integer calcularNivelCuenta(String numeroCuenta) {
        if (numeroCuenta == null || numeroCuenta.trim().isEmpty()) {
            return null;
        }
        return numeroCuenta.trim().split("-").length;
    }
   
    public void validarSaldosJerarquia() {

        if (prevalidas == null || prevalidas.isEmpty()) {
            return;
        }

        if (rechazadas == null) {
            rechazadas = new ArrayList<>();
        }

        // Normalizar saldos nulos a 0
        for (RnGcCatalogoCuentasTbl cta : prevalidas) {
            if (cta.getSaldoInicial() == null) {
                cta.setSaldoInicial(BigDecimal.ZERO);
            }
        }

        List<RnGcCatalogoCuentasTbl> padresRechazados = new ArrayList<>();

        for (RnGcCatalogoCuentasTbl padre : prevalidas) {
            String numPadre = padre.getNumeroCuenta();
            if (numPadre == null || numPadre.isEmpty()) {
                continue;
            }

            // Sólo consideramos como "padres" a las cuentas sin guiones: 101, 102, 103...
            if (numPadre.contains("-")) {
                continue;
            }

            BigDecimal sumaHijos = BigDecimal.ZERO;
            boolean tieneHijos = false;

            for (RnGcCatalogoCuentasTbl posibleHijo : prevalidas) {
                String numHijo = posibleHijo.getNumeroCuenta();
                if (numHijo == null) {
                    continue;
                }

                // Hijo si empieza con "padre-"
                if (numHijo.startsWith(numPadre + "-")) {
                    tieneHijos = true;
                    if (posibleHijo.getSaldoInicial() != null) {
                        sumaHijos = sumaHijos.add(posibleHijo.getSaldoInicial());
                    }
                }
            }

            if (!tieneHijos) {
                continue; // no es padre en esta estructura, no se valida
            }

            BigDecimal saldoPadre = padre.getSaldoInicial() == null
                    ? BigDecimal.ZERO
                    : padre.getSaldoInicial();

            // CASO 1: Padre sin saldo pero hijos sí tienen → completar saldo padre
            if (saldoPadre.compareTo(BigDecimal.ZERO) == 0
                    && sumaHijos.compareTo(BigDecimal.ZERO) > 0) {

                padre.setSaldoInicial(sumaHijos);
                padre.setSaldoActual(sumaHijos); // si quieres arrancar saldo_actual igual

            // CASO 2: Padre con saldo que NO cuadra con la suma de sus hijos → rechazar
            } else if (saldoPadre.compareTo(sumaHijos) != 0) {

                padresRechazados.add(padre);
            }
        }

        // Mover padres no válidos de prevalidas → rechazadas
        if (!padresRechazados.isEmpty()) {
            prevalidas.removeAll(padresRechazados);
            rechazadas.addAll(padresRechazados);
        }
    }


    public void leerxls(FileUploadEvent event) throws IOException {

        prevalidas  = new ArrayList<>();
        rechazadas  = new ArrayList<>();
        rechazadasDetalladas = new ArrayList<>();
        totalLeidas = 0; totPrevalidas = 0; totRechazadas = 0;

        InputStream in = null;
        XSSFWorkbook excel = null;

        try {
            org.primefaces.model.UploadedFile uf = event.getFile();
            in = uf.getInputstream();
            excel = new XSSFWorkbook(in);

            DataFormatter fmt = new DataFormatter();
            XSSFSheet sheet = excel.getSheetAt(0);

            // 1) Cabeceras
            List<String> cabDup = new ArrayList<>();
            AbstractMap.SimpleEntry<Integer, Map<Integer,String>> headerInfo =
                    findHeaderRow(sheet, fmt, cabDup);

            if (headerInfo == null) {
                JsfUtil.addErrorMessage("No se encontró fila de cabeceras en las primeras 16 filas.");
                return;
            }
            if (!cabDup.isEmpty()) {
                JsfUtil.addErrorMessage("Hay cabeceras repetidas: " + String.join(", ", cabDup));
                return;
            }

            final int headerRowIdx = headerInfo.getKey();
            final Map<Integer,String> headerByIndex = headerInfo.getValue();

            // 2) Índices de columnas requeridas
            Integer colCuenta    = findColIndex(headerByIndex, REQ_CUENTA);
            Integer colDesc      = findColIndex(headerByIndex, REQ_DESCRIPCION);
            Integer colTipo      = findColIndex(headerByIndex, REQ_TIPO);
            Integer colSubtipo   = findColIndex(headerByIndex, REQ_SUBTIPO);
            Integer colAgrupador = findColIndex(headerByIndex, REQ_AGRUPADOR);
            Integer colMoneda    = findColIndex(headerByIndex, REQ_MONEDA);
            Integer colDiot      = findColIndex(headerByIndex, REQ_DIOT);
            Integer colRfc       = findColIndex(headerByIndex, REQ_RFC);
            Integer colSaldo     = findColIndex(headerByIndex, REQ_INICIAL);

            List<String> faltantes = new ArrayList<>();
            if (colCuenta == null)    faltantes.add("cuenta");
            if (colDesc == null)      faltantes.add("descripción");
            if (colTipo == null)      faltantes.add("tipo");
            if (colSubtipo == null)   faltantes.add("subtipo");
            if (colAgrupador == null) faltantes.add("agrupador SAT");
            if (colMoneda == null)    faltantes.add("moneda");
            if (colDiot == null)      faltantes.add("DIOT");
            if (colRfc == null)       faltantes.add("RFC");
            if (colSaldo == null)     faltantes.add("Saldo");
            if (!faltantes.isEmpty()) {
                JsfUtil.addErrorMessage("Faltan columnas requeridas: " + String.join(", ", faltantes));
                return;
            }

            cargarAgrupadoresValidos();
            cargarMonedasValidas();
            if (idMonedaPorCodigo == null || idMonedaPorCodigo.isEmpty()) {
                JsfUtil.addErrorMessage("No se pudieron cargar las monedas válidas del catálogo.");
                return;
            }

            Map<String, List<Integer>> mapaDuplicadas = new HashMap<>();

            class RowError implements Serializable {
                private static final long serialVersionUID = 1L;
                int fila;
                String cuenta;
                List<String> errs = new ArrayList<>();
            }
            class CandWithErrors {
                int fila;
                RnGcCatalogoCuentasTbl cand;
                String cuentaLimpia;
                RowError re = new RowError();
            }

            List<CandWithErrors> temporales = new ArrayList<>();

            // ====== MAPAS PARA VALIDACION PADRE/HJOS ======
            final Map<String, BigDecimal> sumaSaldoPorCuentaPadre = new HashMap<>();
            final Map<String, CandWithErrors> porCuenta = new HashMap<>(); // opcional
            final Set<String> cuentasMarcadasPorSaldo = new HashSet<>();

            // 4) Lectura
            int firstDataRow = headerRowIdx + 1;
            int lastRow = sheet.getLastRowNum();
            totalLeidas = Math.max(0, lastRow - headerRowIdx);

            for (int r = firstDataRow; r <= lastRow; r++) {

                Row row = sheet.getRow(r);
                if (row == null) continue;

                CandWithErrors cwe = new CandWithErrors();
                cwe.fila = r + 1;
                cwe.cand = new RnGcCatalogoCuentasTbl();
                cwe.re.fila = cwe.fila;

                // ===== CUENTA =====
                String cuentaRaw = fmt.formatCellValue(row.getCell(colCuenta));
                if (cuentaRaw != null) {
                    String raw = cuentaRaw.trim();
                    int dot = raw.indexOf('.'); if (dot > 0) raw = raw.substring(0, dot).trim();
                    int sp  = raw.indexOf(' '); if (sp  > 0) raw = raw.substring(0, sp ).trim();
                    raw = raw.isEmpty()? null : raw;

                    cwe.cand.setNumeroCuenta(raw);
                    cwe.cuentaLimpia = raw;
                    cwe.re.cuenta = raw;
                }

                if (cwe.cand.getNumeroCuenta() == null) {
                    cwe.cand.setAdicional1("Número de cuenta requerido");
                } else {
                    mapaDuplicadas.computeIfAbsent(cwe.cand.getNumeroCuenta(), k -> new ArrayList<>())
                                  .add(cwe.fila);
                    porCuenta.put(cwe.cand.getNumeroCuenta(), cwe);
                }

                // ===== DESCRIPCIÓN =====
                String desc = fmt.formatCellValue(row.getCell(colDesc)).trim();
                cwe.cand.setDescripcionCuenta(desc.isEmpty()? null : desc);
                if (cwe.cand.getDescripcionCuenta() == null) {
                    cwe.cand.setAdicional1("Descripción requerida");
                }

                // ===== TIPO / SUBTIPO =====
                String tipoTxt    = fmt.formatCellValue(row.getCell(colTipo)).trim().replaceAll("[^0-9-]","");
                String subtipoTxt = fmt.formatCellValue(row.getCell(colSubtipo)).trim().replaceAll("[^0-9-]","");
                cwe.cand.setTipo(   tipoTxt.isEmpty()? null : tipoTxt);
                cwe.cand.setSubtipo(subtipoTxt.isEmpty()? null : subtipoTxt);
                if (cwe.cand.getTipo() == null)    cwe.cand.setAdicional1("Tipo requerido");
                if (cwe.cand.getSubtipo() == null) cwe.cand.setAdicional1("Subtipo requerido");

                // ===== AGRUPADOR SAT =====
                String agrupTxtCelda = fmt.formatCellValue(row.getCell(colAgrupador)).trim();
                String codAgr = normalizaCodigoAgrupador(agrupTxtCelda);
                Integer idAgr = idAgrupadorPorCodigo.get(codAgr);

                if (idAgr == null) {
                    cwe.cand.setAdicional1("Agrupador SAT inválido: " + codAgr);
                } else {
                    RnGcCodigoAgrupadorSatTbl agr = ejbCodigoAgrupadorSat.find(idAgr);
                    cwe.cand.setCodigoAgrupadorSatId(agr);
                }

                // ===== SALDO INICIAL =====
                BigDecimal saldoInicial = null;
                if (colSaldo != null) {
                    String saldoTxt = fmt.formatCellValue(row.getCell(colSaldo)).trim();
                    if (!saldoTxt.isEmpty()) {
                        saldoTxt = saldoTxt.replace(",", "");
                        try {
                            saldoInicial = new BigDecimal(saldoTxt);
                        } catch (NumberFormatException ex) {
                            cwe.cand.setAdicional1("Saldo inicial inválido: " + saldoTxt);
                        }
                    }
                }
                cwe.cand.setSaldoInicial(saldoInicial);
                cwe.cand.setSaldoActual(saldoInicial);

                // ===== Acumular subcuentas => padre =====
                acumularSaldoSoloPadreInmediato(sumaSaldoPorCuentaPadre, cwe.cuentaLimpia, saldoInicial);

                // ===== MONEDA =====
                String monedaTxtCelda = fmt.formatCellValue(row.getCell(colMoneda)).trim();
                String codigoMoneda = extraerCodigoMoneda(monedaTxtCelda);
                Integer idMon = (codigoMoneda != null) ? idMonedaPorCodigo.get(codigoMoneda) : null;

                if (idMon == null) {
                    cwe.cand.setAdicional1("Moneda inválida o no registrada en catálogo: " + monedaTxtCelda);
                } else {
                    cwe.cand.setMoneda(idMon);
                }

                // ===== NATURALEZA =====
                String nat = calcularNaturalezaPorCodigoAgrupador(agrupTxtCelda, cwe.re.errs);
                cwe.cand.setNaturaleza(nat);

                // ===== PERIODO =====
                if (idPeriodoActivo == null) {
                    cwe.cand.setAdicional1("No hay periodo activo para registrar");
                } else {
                    cwe.cand.setIdPeriodo(idPeriodoActivo);
                }

                // ===== NIVEL =====
                Integer nivel = calcularNivelCuenta(cwe.cand.getNumeroCuenta());
                cwe.cand.setAdicional2(nivel != null ? nivel.toString() : null);

                // ===== DIOT / RFC =====
                String diotTxt  = fmt.formatCellValue(row.getCell(colDiot)).trim();
                String diotNorm = normalizaDiot(diotTxt);
                cwe.cand.setAdicional1(diotNorm);

                String rfcTxt   = fmt.formatCellValue(row.getCell(colRfc)).trim();
                cwe.cand.setRfc(rfcTxt);

                if ("TRUE".equals(diotNorm)) {
                    if (rfcTxt == null || rfcTxt.trim().isEmpty()) {
                        cwe.cand.setAdicional1("RFC requerido porque DIOT=TRUE");
                    } else if (!esRfcValido(rfcTxt)) {
                        cwe.cand.setAdicional1("RFC inválido (formato incorrecto)");
                    } else if (rfcYaRegistradoEnCatalogo(rfcTxt)) {
                        cwe.cand.setAdicional1("RFC ya registrado en catálogo de cuentas");
                    }
                }

                if (cwe.cand.getInicioVigencia() == null) {
                    cwe.cand.setInicioVigencia(new Date());
                }

                temporales.add(cwe);
            }

            // ==========================================================
            // 5) VALIDAR PADRE vs SUMA SUBCUENTAS (y marcar padre + hijos)
            // ==========================================================
            for (CandWithErrors cwe : temporales) {
            String cuenta = cwe.cuentaLimpia;
            if (cuenta == null) continue;

            BigDecimal sumaHijosDirectos = sumaSaldoPorCuentaPadre.get(cuenta);
            if (sumaHijosDirectos == null) continue; // no tiene hijos directos

            BigDecimal saldoCuenta = cwe.cand.getSaldoInicial();
            if (saldoCuenta == null) saldoCuenta = BigDecimal.ZERO;

            System.out.println("VALIDANDO CUENTA: " + cuenta
                    + " | saldoCuenta=" + saldoCuenta
                    + " | sumaHijosDirectos=" + sumaHijosDirectos);

            if (saldoCuenta.compareTo(sumaHijosDirectos) != 0) {

                // error para la cuenta padre/intermedia
                cwe.cand.setAdicional1("Saldo inconsistente. Cuenta=" + saldoCuenta
                                + ", suma hijos directos=" + sumaHijosDirectos);

                cuentasMarcadasPorSaldo.add(cuenta);

                // marcar hijos directos
                for (CandWithErrors hijo : temporales) {
                    if (hijo.cuentaLimpia == null) continue;

                    String padreDeHijo = obtenerCuentaPadre(hijo.cuentaLimpia);
                    if (cuenta.equals(padreDeHijo)) {
                        cwe.cand.setAdicional1("Cuenta pertenece a padre " + cuenta + " con saldo inconsistente.");
                        cuentasMarcadasPorSaldo.add(hijo.cuentaLimpia);
                    }
                }
            }
        }
            // =========================
            // 6) Marcar duplicadas
            // =========================
            Set<Integer> filasConDup = new HashSet<>();
            for (Map.Entry<String, List<Integer>> e : mapaDuplicadas.entrySet()) {
                if (e.getValue().size() > 1) filasConDup.addAll(e.getValue());
            }
            if (!filasConDup.isEmpty()) {
                for (CandWithErrors cwe : temporales) {
                    if (cwe.cuentaLimpia != null && filasConDup.contains(cwe.fila)) {
                        cwe.cand.setAdicional1("Cuenta duplicada en archivo (filas: " + mapaDuplicadas.get(cwe.cuentaLimpia) + ")");
                    }
                }
            }

            // =========================
            // 7) Partición final
            // =========================
            for (CandWithErrors cwe : temporales) {

                // Si quedó marcada por regla de saldo padre/hijos, se va a rechazadas directo
                if (cwe.cuentaLimpia != null && cuentasMarcadasPorSaldo.contains(cwe.cuentaLimpia)) {
                    rechazadas.add(copiaLigera(cwe.cand));
                    rechazadasDetalladas.add(cwe.re);
                    continue;
                }

                if (cwe.re.errs.isEmpty()) {
                    boolean yaExiste = validarRegistroParaInsertarPreview(cwe.cand);
                    if (!yaExiste) {
                        prevalidas.add(cwe.cand);
                    } else {
                        rechazadas.add(copiaLigera(cwe.cand));
                        RowError re = new RowError();
                        re.fila = cwe.fila;
                        re.cuenta = cwe.cand.getNumeroCuenta();
                        re.errs = java.util.Collections.singletonList("Ya existe en sistema / regla negocio");
                        rechazadasDetalladas.add(re);
                    }
                } else {
                    rechazadas.add(copiaLigera(cwe.cand));
                    rechazadasDetalladas.add(cwe.re);
                }
            }

            totPrevalidas = prevalidas.size();
            totRechazadas = rechazadas.size();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Prevalidación",
                            "Leídas: " + totalLeidas + " | Válidas: " + totPrevalidas + " | Rechazadas: " + totRechazadas));

        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Ocurrió un error al prevalidar el archivo.");
        } finally {
            if (excel != null) try { excel.close(); } catch (Exception ignore) {}
            if (in != null)    try { in.close(); } catch (Exception ignore) {}
        }
    }

    private void acumularSaldoPorCuentaPadre(Map<String, BigDecimal> mapa,
                                             String numeroCuenta,
                                             BigDecimal saldoInicial) {
        if (numeroCuenta == null || saldoInicial == null) return;

        String padre = obtenerCuentaPadre(numeroCuenta);
        if (padre == null) return; // no es subcuenta

        mapa.merge(padre, saldoInicial, BigDecimal::add);
    }

    private String obtenerCuentaPadre(String cuenta) {
        if (cuenta == null || cuenta.trim().isEmpty()) {
            return null;
        }

        int idx = cuenta.lastIndexOf("-");
        if (idx == -1) {
            return null;
        }

        return cuenta.substring(0, idx);
    }
    

    private void acumularSaldoSoloPadreInmediato(Map<String, BigDecimal> sumaPorPadre,
                                             String cuenta,
                                             BigDecimal saldo) {
        if (cuenta == null || cuenta.trim().isEmpty() || saldo == null) {
            return;
        }

        String padre = obtenerCuentaPadre(cuenta);
        if (padre == null || padre.trim().isEmpty()) {
            return;
        }

        BigDecimal actual = sumaPorPadre.get(padre);
        if (actual == null) {
            actual = BigDecimal.ZERO;
        }

        sumaPorPadre.put(padre, actual.add(saldo));
    }

}
