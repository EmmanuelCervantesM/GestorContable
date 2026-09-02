package mx.com.rocketnegocios.web;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.primefaces.model.DefaultStreamedContent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import mx.com.rocketnegocios.beans.RnGcCatalogoCuentasTblFacade;
import mx.com.rocketnegocios.beans.RnGcImagenesTblFacade;
import mx.com.rocketnegocios.beans.RnGcTipoPolizaFacade;
import mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade;
import mx.com.rocketnegocios.entities.AuxiliarCuentasRow;
import mx.com.rocketnegocios.entities.BalanceGeneralRow;
import mx.com.rocketnegocios.entities.EstadoResultadoRow;
import mx.com.rocketnegocios.entities.LibroDiarioRow;
import mx.com.rocketnegocios.entities.LibroMayorRow;
import mx.com.rocketnegocios.entities.RnGcCatalogoCuentasTbl;
import mx.com.rocketnegocios.entities.RnGcImagenesTbl;
import mx.com.rocketnegocios.entities.RnGcTipoPoliza;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;

// Jasper
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Workbook;
import static org.apache.poi.util.IOUtils.toByteArray;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// PrimeFaces
import org.primefaces.model.StreamedContent;
import org.primefaces.model.DefaultStreamedContent;

@Named("rnGcTipoPolizaController")
@SessionScoped
@ViewScoped
public class RnGcTipoPolizaController implements Serializable {

    private static final long serialVersionUID = 1L;

    private String reporteSeleccionado = "";
    private String tituloReporte = "";
    private String rfc = "";
    private String nombreEmpresa = "";
    private String tipoCuenta;           
    private Boolean cuentaConMovimientos; 
    
    @EJB
    private RnGcImagenesTblFacade ejbFacadeImagen;
    @EJB
    private RnGcUsuariosTblFacade usuarioFacade;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();

    @EJB
    private RnGcCatalogoCuentasTblFacade ejbFacade;
    
    @EJB
    private RnGcTipoPolizaFacade rnGcTipoPolizaFacade;

    // ======== Filtros ========
    private Date fechaDesde;
    private Date fechaHasta;

    // ======== Multi selección de cuentas ========
    private List<RnGcCatalogoCuentasTbl> listaCuentas = new ArrayList<>();
    private RnGcImagenesTbl rnGcImagenesTbl = new RnGcImagenesTbl();
    // ======== Descargas ========
    private StreamedContent reportePdf;
    private StreamedContent reporteExcel;

    @PostConstruct
    public void init() {
        recargarCuentas();
    }

    public void recargarCuentas() {
        try {
            RnGcUsuariosTbl usuario = getUsuarioLogueado();
            rfc = usuario.getRfc();
            nombreEmpresa = usuario.getNombreCompleto();
            if (usuario == null) {
                listaCuentas = new ArrayList<>();
                return;
            }

            listaCuentas = ejbFacade.obtenerListaCuentas(usuario);

        } catch (Exception e) {
            listaCuentas = new ArrayList<>();
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error cargando cuentas", e.getMessage()));
        }
    }
    
    public List<RnGcTipoPoliza> obtenerTipoPolizaPorUsuario() {
       RnGcUsuariosTbl usuarioLogueado = getUsuarioLogueado();

        if (usuarioLogueado != null) {
            return rnGcTipoPolizaFacade.obtenerListaPolizas(usuarioLogueado);
        }

        return new ArrayList<>();
    }

    // =========================================================
    // Validación / Preparación (tu método igual)
    // =========================================================
    public void validarFiltrosYPreparar() {
        FacesContext fc = FacesContext.getCurrentInstance();

        if (fechaDesde == null || fechaHasta == null) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Faltan fechas", "Debes seleccionar 'Desde' y 'Hasta'."));
            fc.validationFailed();
            return;
        }

        if (fechaDesde.after(fechaHasta)) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Rango inválido", "La fecha 'Desde' no puede ser mayor a 'Hasta'."));
            fc.validationFailed();
            return;
        }

        if (reporteSeleccionado == null || reporteSeleccionado.trim().isEmpty()) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Reporte no seleccionado", "Selecciona un reporte antes de continuar."));
            fc.validationFailed();
            return;
        }

        reportePdf = null;
        reporteExcel = null;
    }

    // =========================================================
    // Helpers
    // =========================================================
    private String buildFileName(String ext) {
        String rep = (tituloReporte != null && !tituloReporte.trim().isEmpty())
                ? tituloReporte.replace(" ", "_")
                : "Reporte";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return rep + "_" + sdf.format(new Date()) + "." + ext;
    }

    // =========================================================
    public void generarPdf() {
        
        Date fechaDesde = this.fechaDesde;
        Date fechaHasta = this.fechaHasta;
        String tipoCuenta = this.tipoCuenta;
        boolean soloConMovimientos = Boolean.TRUE.equals(this.cuentaConMovimientos);

        System.out.println("=== generarPdf() ===");
        System.out.println("reporteSeleccionado: " + reporteSeleccionado);
        System.out.println("fechaDesde: " + fechaDesde);
        System.out.println("fechaHasta: " + fechaHasta);
        System.out.println("tipoCuenta: " + tipoCuenta);
        System.out.println("soloConMovimientos: " + soloConMovimientos);
        
        if ("ER".equals(reporteSeleccionado)) {
            verPdfEstadoResultado(fechaDesde,fechaHasta,tipoCuenta,soloConMovimientos);
        } else if ("BG".equals(reporteSeleccionado)) {
            verPdfBalanceGeneral(fechaDesde,fechaHasta,tipoCuenta,soloConMovimientos);
        } else if ("AC".equals(reporteSeleccionado)) {
            verPdfAuxiliarCuentas(fechaDesde,fechaHasta,tipoCuenta,soloConMovimientos);     
        } else if ("LM".equals(reporteSeleccionado)) {
            verPdfLibroMayor(fechaDesde,fechaHasta,tipoCuenta,soloConMovimientos);  
        } else if ("LD".equals(reporteSeleccionado)) {
            verPdfLibroDiario(fechaDesde,fechaHasta,tipoCuenta,soloConMovimientos); 
        }else {
            throw new IllegalArgumentException("No hay reporte seleccionado");
        }
    }
    
    public void generarExcel() {
        
        Date fechaDesde = this.fechaDesde;
        Date fechaHasta = this.fechaHasta;
        String tipoCuenta = this.tipoCuenta;
        boolean soloConMovimientos = Boolean.TRUE.equals(this.cuentaConMovimientos);

        System.out.println("=== generarExcel() ===");
        System.out.println("reporteSeleccionado: " + reporteSeleccionado);
        System.out.println("fechaDesde: " + fechaDesde);
        System.out.println("fechaHasta: " + fechaHasta);
        System.out.println("tipoCuenta: " + tipoCuenta);
        System.out.println("soloConMovimientos: " + soloConMovimientos);
        
        if ("ER".equals(reporteSeleccionado)) {
            verExcelEstadoResultado(fechaDesde,fechaHasta,tipoCuenta,soloConMovimientos);
        } else if ("BG".equals(reporteSeleccionado)) {
            verExcelBalanceGeneral(fechaDesde,fechaHasta,tipoCuenta,soloConMovimientos);
        } else if ("AC".equals(reporteSeleccionado)) {
            verExcelAuxiliarCuentas(fechaDesde,fechaHasta,tipoCuenta,soloConMovimientos);
        } else if ("LM".equals(reporteSeleccionado)) {
            verExcelLibroMayor(fechaDesde,fechaHasta,tipoCuenta,soloConMovimientos);
        } else if ("LD".equals(reporteSeleccionado)) {
            verExcelLibroDiario(fechaDesde,fechaHasta,tipoCuenta,soloConMovimientos); 
        }else {
            throw new IllegalArgumentException("No hay reporte seleccionado");
        }
    }
    
    public void verPdfEstadoResultado(Date fechaDesde, Date fechaHasta, String tipoCuenta, boolean soloConMovimientos) {
        
        DefaultStreamedContent img = this.byteToImage(rfc);
        
        RnGcUsuariosTbl usuarioLogueado = getUsuarioLogueado();
        
        Integer idUsuario = usuarioLogueado.getId();
        
        List<EstadoResultadoRow> data = new ArrayList<>();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("es", "MX"));

        FacesContext fc = FacesContext.getCurrentInstance();
        String periodo = fechaDesde.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(fmt)
                + " - " +
                fechaHasta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(fmt);

        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("P_EMPRESA", nombreEmpresa);
            parametros.put("P_PERIODO", periodo);
            //parametros.put("P_SUCURSALES", "TODAS");
            parametros.put("P_FECHA_HORA", new Date());
            
            if("ACUMULADAS".equalsIgnoreCase(tipoCuenta)){
                data = ejbFacade.reporteEstadoResultadoPadre(fechaDesde, fechaHasta,idUsuario);
                
                if(soloConMovimientos){
                    data.removeIf(r -> r.getImporteEstadoResultado() == null
                        || r.getImporteEstadoResultado().compareTo(BigDecimal.ZERO) == 0);
                }
            } else if ("DETALLE".equalsIgnoreCase(tipoCuenta)){
                
                 data = ejbFacade.reporteEstadoResultadoPadre(fechaDesde, fechaHasta,idUsuario);
                
            }

            try (InputStream logo = img.getStream();
                 InputStream jrxml = fc.getExternalContext()
                        .getResourceAsStream("/resources/Reports/estado_resultado.jrxml")) {

                if (jrxml == null) throw new IllegalStateException("No se encontró JRXML /resources/Reports/estado_resultado.jrxml");
                if (logo == null) throw new IllegalStateException("No se encontró LOGO");

                parametros.put("P_LOGO", logo);

                JasperReport report = JasperCompileManager.compileReport(jrxml);

                JRDataSource ds = new JRBeanCollectionDataSource(data);

                JasperPrint jp = JasperFillManager.fillReport(report, parametros, ds);

                byte[] pdf = JasperExportManager.exportReportToPdf(jp);

                String nombre = "EstadoResultado_" + new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf";

                this.reportePdf = new DefaultStreamedContent(
                        new ByteArrayInputStream(pdf),
                        "application/pdf",
                        nombre
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            Throwable root = e;
            while (root.getCause() != null) root = root.getCause();
            String msg = (root.getMessage() != null) ? root.getMessage() : root.toString();
            JsfUtil.addErrorMessage("Ocurrió un error al generar el PDF: " + msg);
        }
    } 

    public void verExcelEstadoResultado(Date fechaDesde, Date fechaHasta, String tipoCuenta, boolean soloConMovimientos) {

        RnGcUsuariosTbl usuarioLogueado = getUsuarioLogueado();
        Integer idUsuario = usuarioLogueado.getId();

        List<EstadoResultadoRow> data = new ArrayList<>();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("es", "MX"));

        String periodo = fechaDesde.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(fmt)
                + " - " +
                fechaHasta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(fmt);

        try {

            if ("ACUMULADAS".equalsIgnoreCase(tipoCuenta)) {

                data = ejbFacade.reporteEstadoResultadoPadre(fechaDesde, fechaHasta, idUsuario);

                if (soloConMovimientos) {
                    data.removeIf(r -> r.getImporteEstadoResultado() == null
                            || r.getImporteEstadoResultado().compareTo(BigDecimal.ZERO) == 0);
                }

            } else if ("DETALLE".equalsIgnoreCase(tipoCuenta)) {

                data = ejbFacade.reporteEstadoResultadoPadre(fechaDesde, fechaHasta, idUsuario);
            }

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Estado de Resultados");

            CreationHelper creationHelper = workbook.getCreationHelper();

            Font fontTitulo = workbook.createFont();
            fontTitulo.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fontTitulo.setFontHeightInPoints((short) 16);

            CellStyle styleTitulo = workbook.createCellStyle();
            styleTitulo.setFont(fontTitulo);

            Font fontSubtitulo = workbook.createFont();
            fontSubtitulo.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fontSubtitulo.setFontHeightInPoints((short) 12);

            CellStyle styleSubtitulo = workbook.createCellStyle();
            styleSubtitulo.setFont(fontSubtitulo);

            Font fontHeader = workbook.createFont();
            fontHeader.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fontHeader.setColor(Font.COLOR_NORMAL);

            CellStyle styleHeader = workbook.createCellStyle();
            styleHeader.setFont(fontHeader);
            styleHeader.setAlignment(CellStyle.ALIGN_CENTER);
            styleHeader.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            styleHeader.setBorderBottom(CellStyle.BORDER_THIN);
            styleHeader.setBorderTop(CellStyle.BORDER_THIN);
            styleHeader.setBorderLeft(CellStyle.BORDER_THIN);
            styleHeader.setBorderRight(CellStyle.BORDER_THIN);

            CellStyle styleNormal = workbook.createCellStyle();
            styleNormal.setBorderBottom(CellStyle.BORDER_THIN);
            styleNormal.setBorderTop(CellStyle.BORDER_THIN);
            styleNormal.setBorderLeft(CellStyle.BORDER_THIN);
            styleNormal.setBorderRight(CellStyle.BORDER_THIN);

            Font fontParent = workbook.createFont();
            fontParent.setBoldweight(Font.BOLDWEIGHT_BOLD);

            CellStyle styleParent = workbook.createCellStyle();
            styleParent.setFont(fontParent);
            styleParent.setBorderBottom(CellStyle.BORDER_THIN);
            styleParent.setBorderTop(CellStyle.BORDER_THIN);
            styleParent.setBorderLeft(CellStyle.BORDER_THIN);
            styleParent.setBorderRight(CellStyle.BORDER_THIN);

            CellStyle styleMoney = workbook.createCellStyle();
            styleMoney.cloneStyleFrom(styleNormal);
            styleMoney.setDataFormat(creationHelper.createDataFormat().getFormat("$ #,##0.00;-$ #,##0.00"));
            styleMoney.setAlignment(CellStyle.ALIGN_RIGHT);

            CellStyle styleMoneyParent = workbook.createCellStyle();
            styleMoneyParent.cloneStyleFrom(styleParent);
            styleMoneyParent.setDataFormat(creationHelper.createDataFormat().getFormat("$ #,##0.00;-$ #,##0.00"));
            styleMoneyParent.setAlignment(CellStyle.ALIGN_RIGHT);

            int rowIndex = 0;

            Row rowEmpresa = sheet.createRow(rowIndex++);
            Cell cellEmpresa = rowEmpresa.createCell(0);
            cellEmpresa.setCellValue(nombreEmpresa);
            cellEmpresa.setCellStyle(styleTitulo);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

            Row rowTitulo = sheet.createRow(rowIndex++);
            Cell cellTitulo = rowTitulo.createCell(0);
            cellTitulo.setCellValue("ESTADO DE RESULTADOS");
            cellTitulo.setCellStyle(styleSubtitulo);
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 3));

            rowIndex++;

            Row rowPeriodo = sheet.createRow(rowIndex++);
            Cell cellPeriodoLabel = rowPeriodo.createCell(0);
            cellPeriodoLabel.setCellValue("Periodo:");
            cellPeriodoLabel.setCellStyle(styleSubtitulo);

            Cell cellPeriodo = rowPeriodo.createCell(1);
            cellPeriodo.setCellValue(periodo);

            Row rowFecha = sheet.createRow(rowIndex++);
            Cell cellFechaLabel = rowFecha.createCell(0);
            cellFechaLabel.setCellValue("Fecha generación:");
            cellFechaLabel.setCellStyle(styleSubtitulo);

            Cell cellFecha = rowFecha.createCell(1);
            cellFecha.setCellValue(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

            rowIndex++;

            Row header = sheet.createRow(rowIndex++);

            Cell h0 = header.createCell(0);
            h0.setCellValue("Cuenta");
            h0.setCellStyle(styleHeader);

            Cell h1 = header.createCell(1);
            h1.setCellValue("Descripción");
            h1.setCellStyle(styleHeader);

            Cell h2 = header.createCell(2);
            h2.setCellValue("Saldo del periodo");
            h2.setCellStyle(styleHeader);

            Cell h3 = header.createCell(3);
            h3.setCellValue("Acumulado");
            h3.setCellStyle(styleHeader);

            for (EstadoResultadoRow item : data) {

                Row row = sheet.createRow(rowIndex++);

                String cuenta = item.getNumeroCuenta() != null ? item.getNumeroCuenta() : "";
                String descripcion = item.getDescripcionCuenta() != null ? item.getDescripcionCuenta() : "";

                BigDecimal importe = item.getImporteEstadoResultado() != null
                        ? item.getImporteEstadoResultado()
                        : BigDecimal.ZERO;

                boolean esPadreOFormula =
                        cuenta.trim().isEmpty()
                                || "400".equals(cuenta)
                                || "500".equals(cuenta)
                                || "600".equals(cuenta);

                CellStyle styleTexto = esPadreOFormula ? styleParent : styleNormal;
                CellStyle styleImporte = esPadreOFormula ? styleMoneyParent : styleMoney;

                Cell c0 = row.createCell(0);
                c0.setCellValue(cuenta);
                c0.setCellStyle(styleTexto);

                Cell c1 = row.createCell(1);
                c1.setCellValue(descripcion);
                c1.setCellStyle(styleTexto);

                Cell c2 = row.createCell(2);
                c2.setCellValue(importe.doubleValue());
                c2.setCellStyle(styleImporte);

                Cell c3 = row.createCell(3);
                c3.setCellValue(importe.doubleValue());
                c3.setCellStyle(styleImporte);
            }

            sheet.setColumnWidth(0, 18 * 256);
            sheet.setColumnWidth(1, 45 * 256);
            sheet.setColumnWidth(2, 22 * 256);
            sheet.setColumnWidth(3, 22 * 256);

            sheet.createFreezePane(0, 7);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            workbook.write(bos);

            String nombre = "EstadoResultado_"
                    + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())
                    + ".xlsx";

            this.reporteExcel = new DefaultStreamedContent(
                    new ByteArrayInputStream(bos.toByteArray()),
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    nombre
            );

        } catch (Exception e) {
            e.printStackTrace();

            Throwable root = e;
            while (root.getCause() != null) {
                root = root.getCause();
            }

            String msg = root.getMessage() != null ? root.getMessage() : root.toString();
            JsfUtil.addErrorMessage("Ocurrió un error al generar el Excel: " + msg);
        }
    }
 
    public void verPdfBalanceGeneral(Date fechaDesde, Date fechaHasta, String tipoCuenta, boolean soloConMovimientos) {
        
        DefaultStreamedContent img = this.byteToImage(rfc);
        
        RnGcUsuariosTbl usuarioLogueado = getUsuarioLogueado();
        
        Integer idUsuario = usuarioLogueado.getId();
        
        List<BalanceGeneralRow> data = new ArrayList<>();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("es", "MX"));

        FacesContext fc = FacesContext.getCurrentInstance();
        String periodo = fechaDesde.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(fmt)
                + " - " +
                fechaHasta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(fmt);

        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("P_EMPRESA", nombreEmpresa);
            parametros.put("P_PERIODO", periodo);
            //parametros.put("P_SUCURSALES", "TODAS");
            parametros.put("P_FECHA_HORA", new Date());
            
            if("ACUMULADAS".equalsIgnoreCase(tipoCuenta)){
                data = ejbFacade.reporteBalanceGeneralPadreHijos(fechaDesde, fechaHasta,idUsuario);
                
                if (soloConMovimientos) {
                    data.removeIf(r ->
                            esCeroONulo(r.getSaldoActivo())
                            && esCeroONulo(r.getSaldoPasivo())
                    );
    }
            } else if ("DETALLE".equalsIgnoreCase(tipoCuenta)){
                
                 data = ejbFacade.reporteBalanceGeneralPadreHijos(fechaDesde, fechaHasta,idUsuario);
                
            }

            try (InputStream logo = img.getStream();
                 InputStream jrxml = fc.getExternalContext()
                        .getResourceAsStream("/resources/Reports/balance_general.jrxml")) {

                if (jrxml == null) throw new IllegalStateException("No se encontró JRXML /resources/Reports/balance_general.jrxml");
                if (logo == null) throw new IllegalStateException("No se encontró LOGO");

               parametros.put("P_LOGO", logo);

                byte[] jrxmlBytes = toByteArray(jrxml);

                String jrxmlContenido = new String(jrxmlBytes, "UTF-8");

                // Limpieza para versiones de Jasper que no soportan textAdjust
                jrxmlContenido = jrxmlContenido.replaceAll("\\s+textAdjust=\"[^\"]*\"", "");

                JasperReport report = JasperCompileManager.compileReport(
                        new ByteArrayInputStream(jrxmlContenido.getBytes("UTF-8"))
                );

                JRDataSource ds = new JRBeanCollectionDataSource(data);

                JasperPrint jp = JasperFillManager.fillReport(report, parametros, ds);

                byte[] pdf = JasperExportManager.exportReportToPdf(jp);

                String nombre = "BalanceGeneral_" + new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf";

                this.reportePdf = new DefaultStreamedContent(
                        new ByteArrayInputStream(pdf),
                        "application/pdf",
                        nombre
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            Throwable root = e;
            while (root.getCause() != null) root = root.getCause();
            String msg = (root.getMessage() != null) ? root.getMessage() : root.toString();
            JsfUtil.addErrorMessage("Ocurrió un error al generar el PDF: " + msg);
        }
    } 
    
    public void verExcelBalanceGeneral(Date fechaDesde, Date fechaHasta, String tipoCuenta, boolean soloConMovimientos) {

        RnGcUsuariosTbl usuarioLogueado = getUsuarioLogueado();
        Integer idUsuario = usuarioLogueado.getId();

        List<BalanceGeneralRow> data = new ArrayList<>();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("es", "MX"));

        String periodo = fechaDesde.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(fmt)
                + " - " +
                fechaHasta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(fmt);

        try {

            if ("ACUMULADAS".equalsIgnoreCase(tipoCuenta)) {

                data = ejbFacade.reporteBalanceGeneralPadreHijos(fechaDesde, fechaHasta, idUsuario);

                if (soloConMovimientos) {
                    data.removeIf(r ->
                            esCeroONulo(r.getSaldoActivo())
                            && esCeroONulo(r.getSaldoPasivo())
                    );
                }

            } else if ("DETALLE".equalsIgnoreCase(tipoCuenta)) {

                data = ejbFacade.reporteBalanceGeneralPadreHijos(fechaDesde, fechaHasta, idUsuario);
            }

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Balance General");

            DataFormat dataFormat = workbook.createDataFormat();

            Font fontTitulo = workbook.createFont();
            fontTitulo.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fontTitulo.setFontHeightInPoints((short) 16);

            CellStyle styleTitulo = workbook.createCellStyle();
            styleTitulo.setFont(fontTitulo);

            Font fontSubtitulo = workbook.createFont();
            fontSubtitulo.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fontSubtitulo.setFontHeightInPoints((short) 12);
            fontSubtitulo.setColor(IndexedColors.DARK_BLUE.getIndex());

            CellStyle styleSubtitulo = workbook.createCellStyle();
            styleSubtitulo.setFont(fontSubtitulo);

            Font fontInfoLabel = workbook.createFont();
            fontInfoLabel.setBoldweight(Font.BOLDWEIGHT_BOLD);

            CellStyle styleInfoLabel = workbook.createCellStyle();
            styleInfoLabel.setFont(fontInfoLabel);

            Font fontHeader = workbook.createFont();
            fontHeader.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fontHeader.setColor(IndexedColors.WHITE.getIndex());

            CellStyle styleSectionHeader = workbook.createCellStyle();
            styleSectionHeader.setFont(fontHeader);
            styleSectionHeader.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            styleSectionHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
            styleSectionHeader.setAlignment(CellStyle.ALIGN_CENTER);
            styleSectionHeader.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            styleSectionHeader.setBorderBottom(CellStyle.BORDER_THIN);
            styleSectionHeader.setBorderTop(CellStyle.BORDER_THIN);
            styleSectionHeader.setBorderLeft(CellStyle.BORDER_THIN);
            styleSectionHeader.setBorderRight(CellStyle.BORDER_THIN);

            CellStyle styleHeader = workbook.createCellStyle();
            styleHeader.cloneStyleFrom(styleSectionHeader);

            CellStyle styleNormal = workbook.createCellStyle();
            styleNormal.setBorderBottom(CellStyle.BORDER_THIN);
            styleNormal.setBorderTop(CellStyle.BORDER_THIN);
            styleNormal.setBorderLeft(CellStyle.BORDER_THIN);
            styleNormal.setBorderRight(CellStyle.BORDER_THIN);
            styleNormal.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

            Font fontParent = workbook.createFont();
            fontParent.setBoldweight(Font.BOLDWEIGHT_BOLD);

            CellStyle styleParent = workbook.createCellStyle();
            styleParent.setFont(fontParent);
            styleParent.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
            styleParent.setFillPattern(CellStyle.SOLID_FOREGROUND);
            styleParent.setBorderBottom(CellStyle.BORDER_THIN);
            styleParent.setBorderTop(CellStyle.BORDER_THIN);
            styleParent.setBorderLeft(CellStyle.BORDER_THIN);
            styleParent.setBorderRight(CellStyle.BORDER_THIN);
            styleParent.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

            CellStyle styleMoney = workbook.createCellStyle();
            styleMoney.cloneStyleFrom(styleNormal);
            styleMoney.setDataFormat(dataFormat.getFormat("$ #,##0.00;-$ #,##0.00"));
            styleMoney.setAlignment(CellStyle.ALIGN_RIGHT);

            CellStyle styleMoneyParent = workbook.createCellStyle();
            styleMoneyParent.cloneStyleFrom(styleParent);
            styleMoneyParent.setDataFormat(dataFormat.getFormat("$ #,##0.00;-$ #,##0.00"));
            styleMoneyParent.setAlignment(CellStyle.ALIGN_RIGHT);

            Font fontTotal = workbook.createFont();
            fontTotal.setBoldweight(Font.BOLDWEIGHT_BOLD);

            CellStyle styleTotalLabel = workbook.createCellStyle();
            styleTotalLabel.setFont(fontTotal);
            styleTotalLabel.setBorderBottom(CellStyle.BORDER_THIN);
            styleTotalLabel.setBorderTop(CellStyle.BORDER_THIN);
            styleTotalLabel.setBorderLeft(CellStyle.BORDER_THIN);
            styleTotalLabel.setBorderRight(CellStyle.BORDER_THIN);
            styleTotalLabel.setAlignment(CellStyle.ALIGN_RIGHT);

            CellStyle styleTotalMoney = workbook.createCellStyle();
            styleTotalMoney.setFont(fontTotal);
            styleTotalMoney.setBorderBottom(CellStyle.BORDER_THIN);
            styleTotalMoney.setBorderTop(CellStyle.BORDER_THIN);
            styleTotalMoney.setBorderLeft(CellStyle.BORDER_THIN);
            styleTotalMoney.setBorderRight(CellStyle.BORDER_THIN);
            styleTotalMoney.setAlignment(CellStyle.ALIGN_RIGHT);
            styleTotalMoney.setDataFormat(dataFormat.getFormat("$ #,##0.00;-$ #,##0.00"));

            int rowIndex = 0;

            Row rowEmpresa = sheet.createRow(rowIndex++);
            Cell cellEmpresa = rowEmpresa.createCell(0);
            cellEmpresa.setCellValue(nombreEmpresa);
            cellEmpresa.setCellStyle(styleTitulo);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

            Row rowTitulo = sheet.createRow(rowIndex++);
            Cell cellTitulo = rowTitulo.createCell(0);
            cellTitulo.setCellValue("BALANCE GENERAL");
            cellTitulo.setCellStyle(styleSubtitulo);
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));

            rowIndex++;

            Row rowPeriodo = sheet.createRow(rowIndex++);
            Cell cellPeriodoLabel = rowPeriodo.createCell(0);
            cellPeriodoLabel.setCellValue("Periodo:");
            cellPeriodoLabel.setCellStyle(styleInfoLabel);

            Cell cellPeriodo = rowPeriodo.createCell(1);
            cellPeriodo.setCellValue(periodo);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 1, 3));

            Row rowFecha = sheet.createRow(rowIndex++);
            Cell cellFechaLabel = rowFecha.createCell(0);
            cellFechaLabel.setCellValue("Fecha generación:");
            cellFechaLabel.setCellStyle(styleInfoLabel);

            Cell cellFecha = rowFecha.createCell(1);
            cellFecha.setCellValue(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
            sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 1, 3));

            rowIndex++;

            Row rowSecciones = sheet.createRow(rowIndex++);

            Cell activoTitle = rowSecciones.createCell(0);
            activoTitle.setCellValue("ACTIVO");
            activoTitle.setCellStyle(styleSectionHeader);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 0, 2));

            Cell pasivoTitle = rowSecciones.createCell(4);
            pasivoTitle.setCellValue("PASIVO + CAPITAL");
            pasivoTitle.setCellStyle(styleSectionHeader);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 4, 6));

            Row header = sheet.createRow(rowIndex++);

            crearCelda(header, 0, "Cuenta", styleHeader);
            crearCelda(header, 1, "Descripción", styleHeader);
            crearCelda(header, 2, "Saldo", styleHeader);

            crearCelda(header, 4, "Cuenta", styleHeader);
            crearCelda(header, 5, "Descripción", styleHeader);
            crearCelda(header, 6, "Saldo", styleHeader);

            BigDecimal totalActivo = BigDecimal.ZERO;
            BigDecimal totalPasivoCapital = BigDecimal.ZERO;

            for (BalanceGeneralRow item : data) {

                String cuentaActivo = item.getCuentaActivo() != null ? item.getCuentaActivo() : "";
                BigDecimal saldoActivo = item.getSaldoActivo() != null ? item.getSaldoActivo() : BigDecimal.ZERO;

                String cuentaPasivo = item.getCuentaPasivo() != null ? item.getCuentaPasivo() : "";
                BigDecimal saldoPasivo = item.getSaldoPasivo() != null ? item.getSaldoPasivo() : BigDecimal.ZERO;

                if ("100".equals(cuentaActivo.trim())) {
                    totalActivo = saldoActivo;
                }

                if ("200".equals(cuentaPasivo.trim()) || "300".equals(cuentaPasivo.trim())) {
                    totalPasivoCapital = totalPasivoCapital.add(saldoPasivo);
                }
            }

            for (BalanceGeneralRow item : data) {

                Row row = sheet.createRow(rowIndex++);

                String cuentaActivo = item.getCuentaActivo() != null ? item.getCuentaActivo() : "";
                String descripcionActivo = item.getDescripcionActivo() != null ? item.getDescripcionActivo() : "";
                BigDecimal saldoActivo = item.getSaldoActivo() != null ? item.getSaldoActivo() : BigDecimal.ZERO;

                String cuentaPasivo = item.getCuentaPasivo() != null ? item.getCuentaPasivo() : "";
                String descripcionPasivo = item.getDescripcionPasivo() != null ? item.getDescripcionPasivo() : "";
                BigDecimal saldoPasivo = item.getSaldoPasivo() != null ? item.getSaldoPasivo() : BigDecimal.ZERO;

                boolean activoTitulo = esTituloActivoBalance(cuentaActivo);
                boolean pasivoTitulo = esTituloPasivoBalance(cuentaPasivo);

                CellStyle styleActivoTexto = activoTitulo ? styleParent : styleNormal;
                CellStyle styleActivoMoney = activoTitulo ? styleMoneyParent : styleMoney;

                CellStyle stylePasivoTexto = pasivoTitulo ? styleParent : styleNormal;
                CellStyle stylePasivoMoney = pasivoTitulo ? styleMoneyParent : styleMoney;

                crearCelda(row, 0, cuentaActivo, styleActivoTexto);
                crearCelda(row, 1, descripcionActivo, styleActivoTexto);
                crearCeldaMoney(row, 2, saldoActivo, styleActivoMoney);

                crearCelda(row, 3, "", styleNormal);

                crearCelda(row, 4, cuentaPasivo, stylePasivoTexto);
                crearCelda(row, 5, descripcionPasivo, stylePasivoTexto);
                crearCeldaMoney(row, 6, saldoPasivo, stylePasivoMoney);
            }

            rowIndex++;

            Row rowTotal = sheet.createRow(rowIndex++);

            crearCelda(rowTotal, 0, "", styleTotalLabel);
            crearCelda(rowTotal, 1, "TOTAL ACTIVO:", styleTotalLabel);
            crearCeldaMoney(rowTotal, 2, totalActivo, styleTotalMoney);

            crearCelda(rowTotal, 3, "", styleNormal);

            crearCelda(rowTotal, 4, "", styleTotalLabel);
            crearCelda(rowTotal, 5, "TOTAL PASIVO + CAPITAL:", styleTotalLabel);
            crearCeldaMoney(rowTotal, 6, totalPasivoCapital, styleTotalMoney);

            sheet.setColumnWidth(0, 13 * 256);
            sheet.setColumnWidth(1, 34 * 256);
            sheet.setColumnWidth(2, 18 * 256);

            sheet.setColumnWidth(3, 3 * 256);

            sheet.setColumnWidth(4, 13 * 256);
            sheet.setColumnWidth(5, 34 * 256);
            sheet.setColumnWidth(6, 18 * 256);

            sheet.createFreezePane(0, 7);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);

            String nombre = "BalanceGeneral_"
                    + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())
                    + ".xlsx";

            this.reporteExcel = new DefaultStreamedContent(
                    new ByteArrayInputStream(bos.toByteArray()),
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    nombre
            );

        } catch (Exception e) {
            e.printStackTrace();

            Throwable root = e;
            while (root.getCause() != null) {
                root = root.getCause();
            }

            String msg = root.getMessage() != null ? root.getMessage() : root.toString();
            JsfUtil.addErrorMessage("Ocurrió un error al generar el Excel Balance General: " + msg);
        }
    }

    public void verPdfAuxiliarCuentas(Date fechaDesde, Date fechaHasta, String tipoCuenta, boolean soloConMovimientos) {
        
        DefaultStreamedContent img = this.byteToImage(rfc);
        
        RnGcUsuariosTbl usuarioLogueado = getUsuarioLogueado();
        
        Integer idUsuario = usuarioLogueado.getId();
        
        List<AuxiliarCuentasRow> data = new ArrayList<>();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("es", "MX"));

        FacesContext fc = FacesContext.getCurrentInstance();
        String periodo = fechaDesde.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(fmt)
                + " - " +
                fechaHasta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(fmt);

        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("P_EMPRESA", nombreEmpresa);
            parametros.put("P_PERIODO", periodo);
            //parametros.put("P_SUCURSALES", "TODAS");
            parametros.put("P_FECHA_HORA", new Date());
            
            if("ACUMULADAS".equalsIgnoreCase(tipoCuenta)){
                data = ejbFacade.reporteAuxiliarCuentasPadreHijos(fechaDesde, fechaHasta,idUsuario);
                
                if(soloConMovimientos){
                    data.removeIf(r -> r.getImporteEstadoResultado() == null
                        || r.getImporteEstadoResultado().compareTo(BigDecimal.ZERO) == 0);
                }
            } else if ("DETALLE".equalsIgnoreCase(tipoCuenta)){
                
                 data = ejbFacade.reporteAuxiliarCuentasPadreHijos(fechaDesde, fechaHasta,idUsuario);
                
            }

            try (InputStream logo = img.getStream();
                 InputStream jrxml = fc.getExternalContext()
                        .getResourceAsStream("/resources/Reports/auxiliar_cuentas.jrxml")) {

                if (jrxml == null) throw new IllegalStateException("No se encontró JRXML /resources/Reports/auxiliar_cuentas.jrxml");
                if (logo == null) throw new IllegalStateException("No se encontró LOGO");

                parametros.put("P_LOGO", logo);

                JasperReport report = JasperCompileManager.compileReport(jrxml);

                JRDataSource ds = new JRBeanCollectionDataSource(data);

                JasperPrint jp = JasperFillManager.fillReport(report, parametros, ds);

                byte[] pdf = JasperExportManager.exportReportToPdf(jp);

                String nombre = "AuxiliarCuentas_" + new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf";

                this.reportePdf = new DefaultStreamedContent(
                        new ByteArrayInputStream(pdf),
                        "application/pdf",
                        nombre
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            Throwable root = e;
            while (root.getCause() != null) root = root.getCause();
            String msg = (root.getMessage() != null) ? root.getMessage() : root.toString();
            JsfUtil.addErrorMessage("Ocurrió un error al generar el PDF: " + msg);
        }
    }
    
    public void verExcelAuxiliarCuentas(Date fechaDesde, Date fechaHasta, String tipoCuenta, boolean soloConMovimientos) {

        RnGcUsuariosTbl usuarioLogueado = getUsuarioLogueado();
        Integer idUsuario = usuarioLogueado.getId();

        List<AuxiliarCuentasRow> data = new ArrayList<>();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("es", "MX"));

        String periodo = fechaDesde.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(fmt)
                + " - " +
                fechaHasta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(fmt);

        try {

            if ("ACUMULADAS".equalsIgnoreCase(tipoCuenta)) {

                data = ejbFacade.reporteAuxiliarCuentasPadreHijos(fechaDesde, fechaHasta, idUsuario);

                if (soloConMovimientos) {
                    data.removeIf(r -> r.getImporteEstadoResultado() == null
                            || r.getImporteEstadoResultado().compareTo(BigDecimal.ZERO) == 0);
                }

            } else if ("DETALLE".equalsIgnoreCase(tipoCuenta)) {

                data = ejbFacade.reporteAuxiliarCuentasPadreHijos(fechaDesde, fechaHasta, idUsuario);
            }

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Auxiliar de Cuentas");

            DataFormat dataFormat = workbook.createDataFormat();

            Font fontTitulo = workbook.createFont();
            fontTitulo.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fontTitulo.setFontHeightInPoints((short) 16);

            CellStyle styleTitulo = workbook.createCellStyle();
            styleTitulo.setFont(fontTitulo);

            Font fontSubtitulo = workbook.createFont();
            fontSubtitulo.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fontSubtitulo.setFontHeightInPoints((short) 12);
            fontSubtitulo.setColor(IndexedColors.DARK_BLUE.getIndex());

            CellStyle styleSubtitulo = workbook.createCellStyle();
            styleSubtitulo.setFont(fontSubtitulo);

            Font fontInfoLabel = workbook.createFont();
            fontInfoLabel.setBoldweight(Font.BOLDWEIGHT_BOLD);

            CellStyle styleInfoLabel = workbook.createCellStyle();
            styleInfoLabel.setFont(fontInfoLabel);

            Font fontHeader = workbook.createFont();
            fontHeader.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fontHeader.setColor(IndexedColors.WHITE.getIndex());

            CellStyle styleHeader = workbook.createCellStyle();
            styleHeader.setFont(fontHeader);
            styleHeader.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            styleHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
            styleHeader.setAlignment(CellStyle.ALIGN_CENTER);
            styleHeader.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            styleHeader.setBorderBottom(CellStyle.BORDER_THIN);
            styleHeader.setBorderTop(CellStyle.BORDER_THIN);
            styleHeader.setBorderLeft(CellStyle.BORDER_THIN);
            styleHeader.setBorderRight(CellStyle.BORDER_THIN);

            CellStyle styleNormal = workbook.createCellStyle();
            styleNormal.setBorderBottom(CellStyle.BORDER_THIN);
            styleNormal.setBorderTop(CellStyle.BORDER_THIN);
            styleNormal.setBorderLeft(CellStyle.BORDER_THIN);
            styleNormal.setBorderRight(CellStyle.BORDER_THIN);
            styleNormal.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

            CellStyle styleNormalCenter = workbook.createCellStyle();
            styleNormalCenter.cloneStyleFrom(styleNormal);
            styleNormalCenter.setAlignment(CellStyle.ALIGN_CENTER);

            Font fontParent = workbook.createFont();
            fontParent.setBoldweight(Font.BOLDWEIGHT_BOLD);

            CellStyle styleParent = workbook.createCellStyle();
            styleParent.setFont(fontParent);
            styleParent.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            styleParent.setFillPattern(CellStyle.SOLID_FOREGROUND);
            styleParent.setBorderBottom(CellStyle.BORDER_THIN);
            styleParent.setBorderTop(CellStyle.BORDER_THIN);
            styleParent.setBorderLeft(CellStyle.BORDER_THIN);
            styleParent.setBorderRight(CellStyle.BORDER_THIN);
            styleParent.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

            CellStyle styleParentCenter = workbook.createCellStyle();
            styleParentCenter.cloneStyleFrom(styleParent);
            styleParentCenter.setAlignment(CellStyle.ALIGN_CENTER);

            CellStyle styleMoney = workbook.createCellStyle();
            styleMoney.cloneStyleFrom(styleNormal);
            styleMoney.setDataFormat(dataFormat.getFormat("$ #,##0.00;-$ #,##0.00"));
            styleMoney.setAlignment(CellStyle.ALIGN_RIGHT);

            CellStyle styleMoneyParent = workbook.createCellStyle();
            styleMoneyParent.cloneStyleFrom(styleParent);
            styleMoneyParent.setDataFormat(dataFormat.getFormat("$ #,##0.00;-$ #,##0.00"));
            styleMoneyParent.setAlignment(CellStyle.ALIGN_RIGHT);

            Font fontTotal = workbook.createFont();
            fontTotal.setBoldweight(Font.BOLDWEIGHT_BOLD);

            CellStyle styleTotalLabel = workbook.createCellStyle();
            styleTotalLabel.setFont(fontTotal);
            styleTotalLabel.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            styleTotalLabel.setFillPattern(CellStyle.SOLID_FOREGROUND);
            styleTotalLabel.setBorderBottom(CellStyle.BORDER_THIN);
            styleTotalLabel.setBorderTop(CellStyle.BORDER_THIN);
            styleTotalLabel.setBorderLeft(CellStyle.BORDER_THIN);
            styleTotalLabel.setBorderRight(CellStyle.BORDER_THIN);

            CellStyle styleTotalMoney = workbook.createCellStyle();
            styleTotalMoney.setFont(fontTotal);
            styleTotalMoney.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            styleTotalMoney.setFillPattern(CellStyle.SOLID_FOREGROUND);
            styleTotalMoney.setBorderBottom(CellStyle.BORDER_THIN);
            styleTotalMoney.setBorderTop(CellStyle.BORDER_THIN);
            styleTotalMoney.setBorderLeft(CellStyle.BORDER_THIN);
            styleTotalMoney.setBorderRight(CellStyle.BORDER_THIN);
            styleTotalMoney.setAlignment(CellStyle.ALIGN_RIGHT);
            styleTotalMoney.setDataFormat(dataFormat.getFormat("$ #,##0.00;-$ #,##0.00"));

            int rowIndex = 0;

            Row rowEmpresa = sheet.createRow(rowIndex++);
            Cell cellEmpresa = rowEmpresa.createCell(0);
            cellEmpresa.setCellValue(nombreEmpresa);
            cellEmpresa.setCellStyle(styleTitulo);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

            Row rowTitulo = sheet.createRow(rowIndex++);
            Cell cellTitulo = rowTitulo.createCell(0);
            cellTitulo.setCellValue("AUXILIAR DE CUENTAS");
            cellTitulo.setCellStyle(styleSubtitulo);
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));

            rowIndex++;

            Row rowPeriodo = sheet.createRow(rowIndex++);
            Cell cellPeriodoLabel = rowPeriodo.createCell(0);
            cellPeriodoLabel.setCellValue("Periodo:");
            cellPeriodoLabel.setCellStyle(styleInfoLabel);

            Cell cellPeriodo = rowPeriodo.createCell(1);
            cellPeriodo.setCellValue(periodo);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 1, 3));

            Row rowFecha = sheet.createRow(rowIndex++);
            Cell cellFechaLabel = rowFecha.createCell(0);
            cellFechaLabel.setCellValue("Fecha generación:");
            cellFechaLabel.setCellStyle(styleInfoLabel);

            Cell cellFecha = rowFecha.createCell(1);
            cellFecha.setCellValue(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
            sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 1, 3));

            rowIndex++;

            Row header = sheet.createRow(rowIndex++);

            crearCelda(header, 0, "Fecha", styleHeader);
            crearCelda(header, 1, "Periodo", styleHeader);
            crearCelda(header, 2, "Tipo póliza", styleHeader);
            crearCelda(header, 3, "No. póliza", styleHeader);
            crearCelda(header, 4, "Cuenta", styleHeader);
            crearCelda(header, 5, "Descripción", styleHeader);
            crearCelda(header, 6, "Cargo", styleHeader);
            crearCelda(header, 7, "Abono", styleHeader);
            crearCelda(header, 8, "Saldo", styleHeader);

            BigDecimal totalCargo = BigDecimal.ZERO;
            BigDecimal totalAbono = BigDecimal.ZERO;
            BigDecimal totalSaldo = BigDecimal.ZERO;

            for (AuxiliarCuentasRow item : data) {

                String fecha = item.getFechaCreacion() != null ? item.getFechaCreacion() : "";
                String periodoRow = item.getPeriodo() != null ? item.getPeriodo() : "";
                String tipoPoliza = item.getTipoPoliza() != null ? item.getTipoPoliza() : "";
                String numeroPoliza = item.getNumeroPoliza() != null ? item.getNumeroPoliza() : "";
                String cuenta = item.getNumeroCuenta() != null ? item.getNumeroCuenta() : "";
                String descripcion = item.getDescripcionCuenta() != null ? item.getDescripcionCuenta() : "";

                BigDecimal cargo = item.getCargo() != null ? item.getCargo() : BigDecimal.ZERO;
                BigDecimal abono = item.getAbono() != null ? item.getAbono() : BigDecimal.ZERO;
                BigDecimal saldo = item.getImporteEstadoResultado() != null
                        ? item.getImporteEstadoResultado()
                        : BigDecimal.ZERO;

                boolean esPadre = cuenta != null && !cuenta.trim().isEmpty() && !cuenta.contains("-");

                Row row = sheet.createRow(rowIndex++);

                CellStyle styleTexto = esPadre ? styleParent : styleNormal;
                CellStyle styleTextoCenter = esPadre ? styleParentCenter : styleNormalCenter;
                CellStyle styleImporte = esPadre ? styleMoneyParent : styleMoney;

                crearCelda(row, 0, fecha, styleTextoCenter);
                crearCelda(row, 1, periodoRow, styleTextoCenter);
                crearCelda(row, 2, tipoPoliza, styleTextoCenter);
                crearCelda(row, 3, numeroPoliza, styleTextoCenter);
                crearCelda(row, 4, cuenta, styleTexto);
                crearCelda(row, 5, descripcion, styleTexto);

                /*
                 * Igual que en el PDF:
                 * - En filas padre no mostramos cargo ni abono.
                 * - En filas hijas sí mostramos cargo y abono.
                 * - En ambos casos mostramos saldo.
                 */
                if (esPadre) {
                    crearCelda(row, 6, "", styleImporte);
                    crearCelda(row, 7, "", styleImporte);
                } else {
                    crearCeldaMoney(row, 6, cargo, styleImporte);
                    crearCeldaMoney(row, 7, abono, styleImporte);

                    totalCargo = totalCargo.add(cargo);
                    totalAbono = totalAbono.add(abono);
                }

                crearCeldaMoney(row, 8, saldo, styleImporte);

                totalSaldo = totalSaldo.add(saldo);

                /*
                 * Totales visibles:
                 * cargo/abono solo de filas detalle para evitar duplicar padres + hijas.
                 * saldo se suma como viene en la lógica del PDF.
                 */
                if (!esPadre) {
                    totalCargo = totalCargo.add(cargo);
                    totalAbono = totalAbono.add(abono);
                }

                totalSaldo = totalSaldo.add(saldo);
            }

            rowIndex++;

            Row rowTotal = sheet.createRow(rowIndex++);

            crearCelda(rowTotal, 0, "", styleTotalLabel);
            crearCelda(rowTotal, 1, "", styleTotalLabel);
            crearCelda(rowTotal, 2, "TOTALES", styleTotalLabel);
            crearCeldaMoney(rowTotal, 3, totalCargo, styleTotalMoney);
            crearCeldaMoney(rowTotal, 4, totalAbono, styleTotalMoney);
            crearCeldaMoney(rowTotal, 5, totalSaldo, styleTotalMoney);

            sheet.setColumnWidth(0, 14 * 256);
            sheet.setColumnWidth(1, 18 * 256);
            sheet.setColumnWidth(2, 48 * 256);
            sheet.setColumnWidth(3, 18 * 256);
            sheet.setColumnWidth(4, 18 * 256);
            sheet.setColumnWidth(5, 18 * 256);

            sheet.createFreezePane(0, 7);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);

            String nombre = "AuxiliarCuentas_"
                    + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())
                    + ".xlsx";

            this.reporteExcel = new DefaultStreamedContent(
                    new ByteArrayInputStream(bos.toByteArray()),
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    nombre
            );

        } catch (Exception e) {
            e.printStackTrace();

            Throwable root = e;
            while (root.getCause() != null) {
                root = root.getCause();
            }

            String msg = root.getMessage() != null ? root.getMessage() : root.toString();
            JsfUtil.addErrorMessage("Ocurrió un error al generar el Excel Auxiliar de Cuentas: " + msg);
        }
    }
    
    public void verPdfLibroMayor(Date fechaDesde, Date fechaHasta, String tipoCuenta, boolean soloConMovimientos) {

        DefaultStreamedContent img = this.byteToImage(rfc);
        
        RnGcUsuariosTbl usuarioLogueado = getUsuarioLogueado();
        
        Integer idUsuario = usuarioLogueado.getId();

        List<LibroMayorRow> data = new ArrayList<>();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("es", "MX"));

        FacesContext fc = FacesContext.getCurrentInstance();

        String periodo = fechaDesde.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(fmt)
                + " - " +
                fechaHasta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(fmt);

        try {

            data = ejbFacade.reporteLibroMayor(fechaDesde, fechaHasta, idUsuario);

            if (soloConMovimientos) {
                data = filtrarLibroMayorSinMovimientos(data);
            }

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("P_EMPRESA", nombreEmpresa);
            parametros.put("P_PERIODO", periodo);
            parametros.put("P_FECHA_HORA", new Date());

            try (InputStream logo = img.getStream();
                 InputStream jrxml = fc.getExternalContext()
                        .getResourceAsStream("/resources/Reports/libro_mayor.jrxml")) {

                if (jrxml == null) {
                    throw new IllegalStateException("No se encontró JRXML /resources/Reports/libro_mayor.jrxml");
                }

                if (logo == null) {
                    throw new IllegalStateException("No se encontró LOGO");
                }

                parametros.put("P_LOGO", logo);

                JasperReport report = JasperCompileManager.compileReport(jrxml);

                JRDataSource ds = new JRBeanCollectionDataSource(data);

                JasperPrint jp = JasperFillManager.fillReport(report, parametros, ds);

                byte[] pdf = JasperExportManager.exportReportToPdf(jp);

                String nombre = "LibroMayor_"
                        + new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())
                        + ".pdf";

                this.reportePdf = new DefaultStreamedContent(
                        new ByteArrayInputStream(pdf),
                        "application/pdf",
                        nombre
                );
            }

        } catch (Exception e) {
            e.printStackTrace();

            Throwable root = e;
            while (root.getCause() != null) {
                root = root.getCause();
            }

            String msg = root.getMessage() != null ? root.getMessage() : root.toString();
            JsfUtil.addErrorMessage("Ocurrió un error al generar el PDF Libro Mayor: " + msg);
        }
    }
    
    public void verExcelLibroMayor(Date fechaDesde, Date fechaHasta, String tipoCuenta, boolean soloConMovimientos) {

        RnGcUsuariosTbl usuarioLogueado = getUsuarioLogueado();
        Integer idUsuario = usuarioLogueado.getId();

        List<LibroMayorRow> data = new ArrayList<>();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("es", "MX"));

        String periodo = fechaDesde.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(fmt)
                + " - " +
                fechaHasta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(fmt);

        try {

            data = ejbFacade.reporteLibroMayor(fechaDesde, fechaHasta, idUsuario);

            if (soloConMovimientos) {
                data = filtrarLibroMayorSinMovimientos(data);
            }

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Libro Mayor");

            DataFormat dataFormat = workbook.createDataFormat();

            Font fontTitulo = workbook.createFont();
            fontTitulo.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fontTitulo.setFontHeightInPoints((short) 16);

            CellStyle styleTitulo = workbook.createCellStyle();
            styleTitulo.setFont(fontTitulo);

            Font fontSubtitulo = workbook.createFont();
            fontSubtitulo.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fontSubtitulo.setFontHeightInPoints((short) 12);
            fontSubtitulo.setColor(IndexedColors.DARK_BLUE.getIndex());

            CellStyle styleSubtitulo = workbook.createCellStyle();
            styleSubtitulo.setFont(fontSubtitulo);

            Font fontInfoLabel = workbook.createFont();
            fontInfoLabel.setBoldweight(Font.BOLDWEIGHT_BOLD);

            CellStyle styleInfoLabel = workbook.createCellStyle();
            styleInfoLabel.setFont(fontInfoLabel);

            Font fontHeader = workbook.createFont();
            fontHeader.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fontHeader.setColor(IndexedColors.WHITE.getIndex());

            CellStyle styleHeader = workbook.createCellStyle();
            styleHeader.setFont(fontHeader);
            styleHeader.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            styleHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
            styleHeader.setAlignment(CellStyle.ALIGN_CENTER);
            styleHeader.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            styleHeader.setBorderBottom(CellStyle.BORDER_THIN);
            styleHeader.setBorderTop(CellStyle.BORDER_THIN);
            styleHeader.setBorderLeft(CellStyle.BORDER_THIN);
            styleHeader.setBorderRight(CellStyle.BORDER_THIN);

            CellStyle styleNormal = workbook.createCellStyle();
            styleNormal.setBorderBottom(CellStyle.BORDER_THIN);
            styleNormal.setBorderTop(CellStyle.BORDER_THIN);
            styleNormal.setBorderLeft(CellStyle.BORDER_THIN);
            styleNormal.setBorderRight(CellStyle.BORDER_THIN);
            styleNormal.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

            CellStyle styleNormalCenter = workbook.createCellStyle();
            styleNormalCenter.cloneStyleFrom(styleNormal);
            styleNormalCenter.setAlignment(CellStyle.ALIGN_CENTER);

            Font fontCuenta = workbook.createFont();
            fontCuenta.setBoldweight(Font.BOLDWEIGHT_BOLD);

            CellStyle styleCuenta = workbook.createCellStyle();
            styleCuenta.setFont(fontCuenta);
            styleCuenta.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
            styleCuenta.setFillPattern(CellStyle.SOLID_FOREGROUND);
            styleCuenta.setBorderBottom(CellStyle.BORDER_THIN);
            styleCuenta.setBorderTop(CellStyle.BORDER_THIN);
            styleCuenta.setBorderLeft(CellStyle.BORDER_THIN);
            styleCuenta.setBorderRight(CellStyle.BORDER_THIN);
            styleCuenta.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

            CellStyle styleCuentaMoney = workbook.createCellStyle();
            styleCuentaMoney.cloneStyleFrom(styleCuenta);
            styleCuentaMoney.setDataFormat(dataFormat.getFormat("$ #,##0.00;-$ #,##0.00"));
            styleCuentaMoney.setAlignment(CellStyle.ALIGN_RIGHT);

            CellStyle styleMoney = workbook.createCellStyle();
            styleMoney.cloneStyleFrom(styleNormal);
            styleMoney.setDataFormat(dataFormat.getFormat("$ #,##0.00;-$ #,##0.00"));
            styleMoney.setAlignment(CellStyle.ALIGN_RIGHT);

            Font fontTotal = workbook.createFont();
            fontTotal.setBoldweight(Font.BOLDWEIGHT_BOLD);

            CellStyle styleTotal = workbook.createCellStyle();
            styleTotal.setFont(fontTotal);
            styleTotal.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            styleTotal.setFillPattern(CellStyle.SOLID_FOREGROUND);
            styleTotal.setBorderBottom(CellStyle.BORDER_THIN);
            styleTotal.setBorderTop(CellStyle.BORDER_THIN);
            styleTotal.setBorderLeft(CellStyle.BORDER_THIN);
            styleTotal.setBorderRight(CellStyle.BORDER_THIN);
            styleTotal.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

            CellStyle styleTotalMoney = workbook.createCellStyle();
            styleTotalMoney.cloneStyleFrom(styleTotal);
            styleTotalMoney.setDataFormat(dataFormat.getFormat("$ #,##0.00;-$ #,##0.00"));
            styleTotalMoney.setAlignment(CellStyle.ALIGN_RIGHT);

            int rowIndex = 0;

            Row rowEmpresa = sheet.createRow(rowIndex++);
            Cell cellEmpresa = rowEmpresa.createCell(0);
            cellEmpresa.setCellValue(nombreEmpresa);
            cellEmpresa.setCellStyle(styleTitulo);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

            Row rowTitulo = sheet.createRow(rowIndex++);
            Cell cellTitulo = rowTitulo.createCell(0);
            cellTitulo.setCellValue("LIBRO MAYOR DE CUENTAS");
            cellTitulo.setCellStyle(styleSubtitulo);
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 4));

            rowIndex++;

            Row rowPeriodo = sheet.createRow(rowIndex++);
            Cell cellPeriodoLabel = rowPeriodo.createCell(0);
            cellPeriodoLabel.setCellValue("Periodo:");
            cellPeriodoLabel.setCellStyle(styleInfoLabel);

            Cell cellPeriodo = rowPeriodo.createCell(1);
            cellPeriodo.setCellValue(periodo);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 1, 3));

            Row rowFecha = sheet.createRow(rowIndex++);
            Cell cellFechaLabel = rowFecha.createCell(0);
            cellFechaLabel.setCellValue("Fecha generación:");
            cellFechaLabel.setCellStyle(styleInfoLabel);

            Cell cellFecha = rowFecha.createCell(1);
            cellFecha.setCellValue(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
            sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 1, 3));

            rowIndex++;

            Row header = sheet.createRow(rowIndex++);

            crearCelda(header, 0, "Mes", styleHeader);
            crearCelda(header, 1, "Cuenta / Descripción", styleHeader);
            crearCelda(header, 2, "Cargos", styleHeader);
            crearCelda(header, 3, "Abonos", styleHeader);
            crearCelda(header, 4, "Saldo", styleHeader);

            for (LibroMayorRow item : data) {

                Row row = sheet.createRow(rowIndex++);

                String cuenta = item.getNumeroCuenta() != null ? item.getNumeroCuenta() : "";
                String descripcion = item.getDescripcionCuenta() != null ? item.getDescripcionCuenta() : "";
                String mes = item.getMes() != null ? item.getMes() : "";

                BigDecimal cargo = item.getCargo() != null ? item.getCargo() : BigDecimal.ZERO;
                BigDecimal abono = item.getAbono() != null ? item.getAbono() : BigDecimal.ZERO;
                BigDecimal saldo = item.getSaldo() != null ? item.getSaldo() : BigDecimal.ZERO;

                Integer tipoFila = item.getTipoFila() != null ? item.getTipoFila() : 1;

                if (tipoFila == 0) {

                    /*
                     * Encabezado de cuenta:
                     * Cuenta - Descripción
                     * En saldo se muestra el saldo inicial.
                     */
                    crearCelda(row, 0, "", styleCuenta);

                    Cell cellCuenta = row.createCell(1);
                    cellCuenta.setCellValue(cuenta + " - " + descripcion);
                    cellCuenta.setCellStyle(styleCuenta);

                    crearCelda(row, 2, "", styleCuenta);
                    crearCelda(row, 3, "", styleCuenta);
                    crearCeldaMoney(row, 4, saldo, styleCuentaMoney);

                } else if (tipoFila == 2) {

                    /*
                     * Totales por cuenta.
                     */
                    crearCelda(row, 0, "Totales", styleTotal);
                    crearCelda(row, 1, "", styleTotal);
                    crearCeldaMoney(row, 2, cargo, styleTotalMoney);
                    crearCeldaMoney(row, 3, abono, styleTotalMoney);
                    crearCeldaMoney(row, 4, saldo, styleTotalMoney);

                } else {

                    /*
                     * Filas de meses.
                     */
                    crearCelda(row, 0, mes, styleNormalCenter);
                    crearCelda(row, 1, "", styleNormal);
                    crearCeldaMoney(row, 2, cargo, styleMoney);
                    crearCeldaMoney(row, 3, abono, styleMoney);
                    crearCeldaMoney(row, 4, saldo, styleMoney);
                }
            }

            sheet.setColumnWidth(0, 18 * 256);
            sheet.setColumnWidth(1, 48 * 256);
            sheet.setColumnWidth(2, 18 * 256);
            sheet.setColumnWidth(3, 18 * 256);
            sheet.setColumnWidth(4, 18 * 256);

            sheet.createFreezePane(0, 7);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);

            String nombre = "LibroMayor_"
                    + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())
                    + ".xlsx";

            this.reporteExcel = new DefaultStreamedContent(
                    new ByteArrayInputStream(bos.toByteArray()),
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    nombre
            );

        } catch (Exception e) {
            e.printStackTrace();

            Throwable root = e;
            while (root.getCause() != null) {
                root = root.getCause();
            }

            String msg = root.getMessage() != null ? root.getMessage() : root.toString();
            JsfUtil.addErrorMessage("Ocurrió un error al generar el Excel Libro Mayor: " + msg);
        }
    }
    
    public void verPdfLibroDiario(Date fechaDesde, Date fechaHasta, String tipoCuenta, boolean soloConMovimientos) {

        DefaultStreamedContent img = this.byteToImage(rfc);

        RnGcUsuariosTbl usuarioLogueado = getUsuarioLogueado();

        Integer idUsuario = usuarioLogueado.getId();

        List<LibroDiarioRow> data = new ArrayList<>();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("es", "MX"));

        FacesContext fc = FacesContext.getCurrentInstance();

        String periodo = fechaDesde.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(fmt)
                + " - " +
                fechaHasta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(fmt);

        try {

            data = ejbFacade.reporteLibroDiario(fechaDesde, fechaHasta, idUsuario);

            if (soloConMovimientos) {
                data = filtrarLibroDiarioSinMovimientos(data);
            }

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("P_EMPRESA", nombreEmpresa);
            parametros.put("P_PERIODO", periodo);
            parametros.put("P_FECHA_HORA", new Date());

            try (InputStream logo = img.getStream();
                 InputStream jrxml = fc.getExternalContext()
                        .getResourceAsStream("/resources/Reports/libro_diario.jrxml")) {

                if (jrxml == null) {
                    throw new IllegalStateException("No se encontró JRXML /resources/Reports/libro_diario.jrxml");
                }

                if (logo == null) {
                    throw new IllegalStateException("No se encontró LOGO");
                }

                parametros.put("P_LOGO", logo);

                JasperReport report = JasperCompileManager.compileReport(jrxml);

                JRDataSource ds = new JRBeanCollectionDataSource(data);

                JasperPrint jp = JasperFillManager.fillReport(report, parametros, ds);

                byte[] pdf = JasperExportManager.exportReportToPdf(jp);

                String nombre = "LibroDiario_"
                        + new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())
                        + ".pdf";

                this.reportePdf = new DefaultStreamedContent(
                        new ByteArrayInputStream(pdf),
                        "application/pdf",
                        nombre
                );
            }

        } catch (Exception e) {
            e.printStackTrace();

            Throwable root = e;
            while (root.getCause() != null) {
                root = root.getCause();
            }

            String msg = root.getMessage() != null ? root.getMessage() : root.toString();
            JsfUtil.addErrorMessage("Ocurrió un error al generar el PDF Libro Diario: " + msg);
        }
    }
    
    public void verExcelLibroDiario(Date fechaDesde, Date fechaHasta, String tipoCuenta, boolean soloConMovimientos) {

        RnGcUsuariosTbl usuarioLogueado = getUsuarioLogueado();
        Integer idUsuario = usuarioLogueado.getId();

        List<LibroDiarioRow> data = new ArrayList<>();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("es", "MX"));

        String periodo = fechaDesde.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(fmt)
                + " - " +
                fechaHasta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(fmt);

        try {

            data = ejbFacade.reporteLibroDiario(fechaDesde, fechaHasta, idUsuario);

            if (soloConMovimientos) {
                data = filtrarLibroDiarioSinMovimientos(data);
            }

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Libro Diario");

            DataFormat dataFormat = workbook.createDataFormat();

            Font fontTitulo = workbook.createFont();
            fontTitulo.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fontTitulo.setFontHeightInPoints((short) 16);

            CellStyle styleTitulo = workbook.createCellStyle();
            styleTitulo.setFont(fontTitulo);

            Font fontSubtitulo = workbook.createFont();
            fontSubtitulo.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fontSubtitulo.setFontHeightInPoints((short) 12);
            fontSubtitulo.setColor(IndexedColors.DARK_BLUE.getIndex());

            CellStyle styleSubtitulo = workbook.createCellStyle();
            styleSubtitulo.setFont(fontSubtitulo);

            Font fontInfoLabel = workbook.createFont();
            fontInfoLabel.setBoldweight(Font.BOLDWEIGHT_BOLD);

            CellStyle styleInfoLabel = workbook.createCellStyle();
            styleInfoLabel.setFont(fontInfoLabel);

            Font fontHeader = workbook.createFont();
            fontHeader.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fontHeader.setColor(IndexedColors.WHITE.getIndex());

            CellStyle styleHeader = workbook.createCellStyle();
            styleHeader.setFont(fontHeader);
            styleHeader.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            styleHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
            styleHeader.setAlignment(CellStyle.ALIGN_CENTER);
            styleHeader.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            styleHeader.setBorderBottom(CellStyle.BORDER_THIN);
            styleHeader.setBorderTop(CellStyle.BORDER_THIN);
            styleHeader.setBorderLeft(CellStyle.BORDER_THIN);
            styleHeader.setBorderRight(CellStyle.BORDER_THIN);

            CellStyle styleNormal = workbook.createCellStyle();
            styleNormal.setBorderBottom(CellStyle.BORDER_THIN);
            styleNormal.setBorderTop(CellStyle.BORDER_THIN);
            styleNormal.setBorderLeft(CellStyle.BORDER_THIN);
            styleNormal.setBorderRight(CellStyle.BORDER_THIN);
            styleNormal.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

            CellStyle styleNormalCenter = workbook.createCellStyle();
            styleNormalCenter.cloneStyleFrom(styleNormal);
            styleNormalCenter.setAlignment(CellStyle.ALIGN_CENTER);

            CellStyle styleMoney = workbook.createCellStyle();
            styleMoney.cloneStyleFrom(styleNormal);
            styleMoney.setDataFormat(dataFormat.getFormat("$ #,##0.00;-$ #,##0.00"));
            styleMoney.setAlignment(CellStyle.ALIGN_RIGHT);

            Font fontPoliza = workbook.createFont();
            fontPoliza.setBoldweight(Font.BOLDWEIGHT_BOLD);

            CellStyle stylePoliza = workbook.createCellStyle();
            stylePoliza.setFont(fontPoliza);
            stylePoliza.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
            stylePoliza.setFillPattern(CellStyle.SOLID_FOREGROUND);
            stylePoliza.setBorderBottom(CellStyle.BORDER_THIN);
            stylePoliza.setBorderTop(CellStyle.BORDER_THIN);
            stylePoliza.setBorderLeft(CellStyle.BORDER_THIN);
            stylePoliza.setBorderRight(CellStyle.BORDER_THIN);
            stylePoliza.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

            Font fontTotal = workbook.createFont();
            fontTotal.setBoldweight(Font.BOLDWEIGHT_BOLD);

            CellStyle styleTotal = workbook.createCellStyle();
            styleTotal.setFont(fontTotal);
            styleTotal.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            styleTotal.setFillPattern(CellStyle.SOLID_FOREGROUND);
            styleTotal.setBorderBottom(CellStyle.BORDER_THIN);
            styleTotal.setBorderTop(CellStyle.BORDER_THIN);
            styleTotal.setBorderLeft(CellStyle.BORDER_THIN);
            styleTotal.setBorderRight(CellStyle.BORDER_THIN);
            styleTotal.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

            CellStyle styleTotalRight = workbook.createCellStyle();
            styleTotalRight.cloneStyleFrom(styleTotal);
            styleTotalRight.setAlignment(CellStyle.ALIGN_RIGHT);

            CellStyle styleTotalMoney = workbook.createCellStyle();
            styleTotalMoney.cloneStyleFrom(styleTotal);
            styleTotalMoney.setDataFormat(dataFormat.getFormat("$ #,##0.00;-$ #,##0.00"));
            styleTotalMoney.setAlignment(CellStyle.ALIGN_RIGHT);

            int rowIndex = 0;

            Row rowEmpresa = sheet.createRow(rowIndex++);
            Cell cellEmpresa = rowEmpresa.createCell(0);
            cellEmpresa.setCellValue(nombreEmpresa);
            cellEmpresa.setCellStyle(styleTitulo);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

            Row rowTitulo = sheet.createRow(rowIndex++);
            Cell cellTitulo = rowTitulo.createCell(0);
            cellTitulo.setCellValue("LIBRO DIARIO DE PÓLIZAS");
            cellTitulo.setCellStyle(styleSubtitulo);
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));

            rowIndex++;

            Row rowPeriodo = sheet.createRow(rowIndex++);
            Cell cellPeriodoLabel = rowPeriodo.createCell(0);
            cellPeriodoLabel.setCellValue("Periodo:");
            cellPeriodoLabel.setCellStyle(styleInfoLabel);

            Cell cellPeriodo = rowPeriodo.createCell(1);
            cellPeriodo.setCellValue(periodo);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 1, 3));

            Row rowFecha = sheet.createRow(rowIndex++);
            Cell cellFechaLabel = rowFecha.createCell(0);
            cellFechaLabel.setCellValue("Fecha generación:");
            cellFechaLabel.setCellStyle(styleInfoLabel);

            Cell cellFecha = rowFecha.createCell(1);
            cellFecha.setCellValue(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
            sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 1, 3));

            rowIndex++;

            Row header = sheet.createRow(rowIndex++);

            crearCelda(header, 0, "Sucursal", styleHeader);
            crearCelda(header, 1, "Tipo Pol.", styleHeader);
            crearCelda(header, 2, "Cuenta", styleHeader);
            crearCelda(header, 3, "Descripción cuenta", styleHeader);
            crearCelda(header, 4, "Cargo", styleHeader);
            crearCelda(header, 5, "Abono", styleHeader);

            BigDecimal totalGeneralCargo = BigDecimal.ZERO;
            BigDecimal totalGeneralAbono = BigDecimal.ZERO;

            for (LibroDiarioRow item : data) {

                Row row = sheet.createRow(rowIndex++);

                String sucursal = item.getSucursal() != null ? item.getSucursal() : "";
                String tipoPoliza = item.getTipoPoliza() != null ? item.getTipoPoliza() : "";
                String fechaPoliza = item.getFechaPoliza() != null ? item.getFechaPoliza() : "";
                String numeroPoliza = item.getNumeroPoliza() != null ? item.getNumeroPoliza() : "";
                String numeroCuenta = item.getNumeroCuenta() != null ? item.getNumeroCuenta() : "";
                String descripcionCuenta = item.getDescripcionCuenta() != null ? item.getDescripcionCuenta() : "";

                BigDecimal cargo = item.getCargo() != null ? item.getCargo() : BigDecimal.ZERO;
                BigDecimal abono = item.getAbono() != null ? item.getAbono() : BigDecimal.ZERO;

                Integer tipoFila = item.getTipoFila() != null ? item.getTipoFila() : 1;

                if (tipoFila == 0) {

                    String textoPoliza = tipoPoliza
                            + " Fecha:"
                            + fechaPoliza
                            + " No. Póliza "
                            + numeroPoliza;

                    crearCelda(row, 0, textoPoliza, stylePoliza);
                    crearCelda(row, 1, "", stylePoliza);
                    crearCelda(row, 2, "", stylePoliza);
                    crearCelda(row, 3, "", stylePoliza);
                    crearCelda(row, 4, "", stylePoliza);
                    crearCelda(row, 5, "", stylePoliza);

                    sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 0, 3));

                } else if (tipoFila == 2) {

                    crearCelda(row, 0, "", styleTotal);
                    crearCelda(row, 1, "", styleTotal);
                    crearCelda(row, 2, "", styleTotal);
                    crearCelda(row, 3, "TOTAL PÓLIZA:", styleTotalRight);
                    crearCeldaMoney(row, 4, cargo, styleTotalMoney);
                    crearCeldaMoney(row, 5, abono, styleTotalMoney);

                    totalGeneralCargo = totalGeneralCargo.add(cargo);
                    totalGeneralAbono = totalGeneralAbono.add(abono);

                } else {

                    crearCelda(row, 0, sucursal, styleNormal);
                    crearCelda(row, 1, tipoPoliza, styleNormalCenter);
                    crearCelda(row, 2, numeroCuenta, styleNormal);
                    crearCelda(row, 3, descripcionCuenta, styleNormal);
                    crearCeldaMoney(row, 4, cargo, styleMoney);
                    crearCeldaMoney(row, 5, abono, styleMoney);
                }
            }

            rowIndex++;

            Row rowTotalGeneral = sheet.createRow(rowIndex++);

            crearCelda(rowTotalGeneral, 0, "", styleTotal);
            crearCelda(rowTotalGeneral, 1, "", styleTotal);
            crearCelda(rowTotalGeneral, 2, "", styleTotal);
            crearCelda(rowTotalGeneral, 3, "TOTAL GENERAL:", styleTotalRight);
            crearCeldaMoney(rowTotalGeneral, 4, totalGeneralCargo, styleTotalMoney);
            crearCeldaMoney(rowTotalGeneral, 5, totalGeneralAbono, styleTotalMoney);

            sheet.setColumnWidth(0, 16 * 256);
            sheet.setColumnWidth(1, 14 * 256);
            sheet.setColumnWidth(2, 18 * 256);
            sheet.setColumnWidth(3, 50 * 256);
            sheet.setColumnWidth(4, 18 * 256);
            sheet.setColumnWidth(5, 18 * 256);

            sheet.createFreezePane(0, 7);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);

            String nombre = "LibroDiario_"
                    + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())
                    + ".xlsx";

            this.reporteExcel = new DefaultStreamedContent(
                    new ByteArrayInputStream(bos.toByteArray()),
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    nombre
            );

        } catch (Exception e) {
            e.printStackTrace();

            Throwable root = e;
            while (root.getCause() != null) {
                root = root.getCause();
            }

            String msg = root.getMessage() != null ? root.getMessage() : root.toString();
            JsfUtil.addErrorMessage("Ocurrió un error al generar el Excel Libro Diario: " + msg);
        }
    }
    
    
    // =========================================================
    // AJUSTA ESTO A TU PROYECTO (cómo obtienes el usuario logueado)
    // =========================================================
    private RnGcUsuariosTbl getUsuarioLogueado() {
        Integer uid = (usuarioFirmado != null) ? usuarioFirmado.obtenerIdUsuario() : null;
        RnGcUsuariosTbl user = (uid != null) ? usuarioFacade.obtenerUsuarioPorId(uid) : null;
        return user; 
    }
    
    public DefaultStreamedContent byteToImage(String RFC) {
        System.out.println("Obtener imagen");
        System.out.println("RFC:" + RFC);
        ByteArrayInputStream img;
        if (!RFC.isEmpty()) {
            rnGcImagenesTbl = ejbFacadeImagen.obtenerImagenPorRFC(RFC);
            if (rnGcImagenesTbl != null) {
                System.out.println("Obteniendo Imagen  del RFC");
                System.out.println(rnGcImagenesTbl.getNombreImagen());
                
                byte[] imgBytes = rnGcImagenesTbl.getFoto();
                img = new ByteArrayInputStream(imgBytes);
            } else {
                System.out.println("El objeto es nulo");
                rnGcImagenesTbl = ejbFacadeImagen.obtenerImagenPorRFC("ADMINISTRADOR");
                byte[] imgBytes = rnGcImagenesTbl.getFoto();
                img = new ByteArrayInputStream(imgBytes);
            }
        } else {
            rnGcImagenesTbl = ejbFacadeImagen.obtenerImagenPorRFC("ADMINISTRADOR");
            byte[] imgBytes = rnGcImagenesTbl.getFoto();
            img = new ByteArrayInputStream(imgBytes);
        }
        return new DefaultStreamedContent(img, "image/png");
    }
    
    private List<LibroMayorRow> filtrarLibroMayorSinMovimientos(List<LibroMayorRow> data) {

        if (data == null || data.isEmpty()) {
            return new ArrayList<>();
        }

        Map<String, Boolean> cuentasConMovimiento = new HashMap<>();

        for (LibroMayorRow row : data) {

            String cuenta = row.getNumeroCuenta() != null ? row.getNumeroCuenta() : "";

            BigDecimal cargo = row.getCargo() != null ? row.getCargo() : BigDecimal.ZERO;
            BigDecimal abono = row.getAbono() != null ? row.getAbono() : BigDecimal.ZERO;
            BigDecimal saldo = row.getSaldo() != null ? row.getSaldo() : BigDecimal.ZERO;

            boolean tieneImporte =
                    cargo.compareTo(BigDecimal.ZERO) != 0
                    || abono.compareTo(BigDecimal.ZERO) != 0
                    || saldo.compareTo(BigDecimal.ZERO) != 0;

            if (tieneImporte) {
                cuentasConMovimiento.put(cuenta, true);
            }
        }

        List<LibroMayorRow> filtrado = new ArrayList<>();

        for (LibroMayorRow row : data) {
            String cuenta = row.getNumeroCuenta() != null ? row.getNumeroCuenta() : "";

            if (cuentasConMovimiento.containsKey(cuenta)) {
                filtrado.add(row);
            }
        }

        return filtrado;
    }
    
    private List<LibroDiarioRow> filtrarLibroDiarioSinMovimientos(List<LibroDiarioRow> data) {

        if (data == null || data.isEmpty()) {
            return new ArrayList<>();
        }

        Map<String, Boolean> polizasConMovimiento = new HashMap<>();

        for (LibroDiarioRow row : data) {

            String poliza = row.getNumeroPoliza() != null ? row.getNumeroPoliza() : "";

            BigDecimal cargo = row.getCargo() != null ? row.getCargo() : BigDecimal.ZERO;
            BigDecimal abono = row.getAbono() != null ? row.getAbono() : BigDecimal.ZERO;

            boolean tieneMovimiento =
                    cargo.compareTo(BigDecimal.ZERO) != 0
                    || abono.compareTo(BigDecimal.ZERO) != 0;

            if (tieneMovimiento) {
                polizasConMovimiento.put(poliza, true);
            }
        }

        List<LibroDiarioRow> filtrado = new ArrayList<>();

        for (LibroDiarioRow row : data) {

            String poliza = row.getNumeroPoliza() != null ? row.getNumeroPoliza() : "";

            if (polizasConMovimiento.containsKey(poliza)) {
                filtrado.add(row);
            }
        }

        return filtrado;
    }

    private void crearCelda(Row row, int column, String value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value != null ? value : "");
        cell.setCellStyle(style);
    }

    private void crearCeldaMoney(Row row, int column, BigDecimal value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value != null ? value.doubleValue() : BigDecimal.ZERO.doubleValue());
        cell.setCellStyle(style);
    }

    private boolean esTituloActivoBalance(String cuenta) {
        if (cuenta == null) {
            return false;
        }

        String c = cuenta.trim();

        return "100".equals(c)
                || "100.01".equals(c)
                || "100.02".equals(c);
    }

    private boolean esTituloPasivoBalance(String cuenta) {
        if (cuenta == null) {
            return false;
        }

        String c = cuenta.trim();

        return "200".equals(c)
                || "200.01".equals(c)
                || "200.02".equals(c)
                || "300".equals(c);
    }

    private boolean esCeroONulo(BigDecimal value) {
        return value == null || value.compareTo(BigDecimal.ZERO) == 0;
    }


    // =========================================================
    // GETTERS / SETTERS
    // =========================================================
    public List<RnGcCatalogoCuentasTbl> getListaCuentas() { return listaCuentas; }
    public void setListaCuentas(List<RnGcCatalogoCuentasTbl> listaCuentas) { this.listaCuentas = listaCuentas; }

    public String getReporteSeleccionado() { return reporteSeleccionado; }
    public void setReporteSeleccionado(String reporteSeleccionado) { this.reporteSeleccionado = reporteSeleccionado; }

    public String getTituloReporte() { return tituloReporte; }
    public void setTituloReporte(String tituloReporte) { this.tituloReporte = tituloReporte; }

    public Date getFechaDesde() { return fechaDesde; }
    public void setFechaDesde(Date fechaDesde) { this.fechaDesde = fechaDesde; }

    public Date getFechaHasta() { return fechaHasta; }
    public void setFechaHasta(Date fechaHasta) { this.fechaHasta = fechaHasta; }

    public StreamedContent getReportePdf() { return reportePdf; }
    public void setReportePdf(StreamedContent reportePdf) { this.reportePdf = reportePdf; }

    public StreamedContent getReporteExcel() { return reporteExcel; }
    public void setReporteExcel(StreamedContent reporteExcel) { this.reporteExcel = reporteExcel; }
    
    public String getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(String tipoCuenta) { this.tipoCuenta = tipoCuenta; }

    public Boolean getCuentaConMovimientos() { return cuentaConMovimientos; }
    public void setCuentaConMovimientos(Boolean cuentaConMovimientos) {
        this.cuentaConMovimientos = cuentaConMovimientos;
    }

    public String getTipoCuentaLabel() {
        if ("ACUMULADAS".equals(tipoCuenta)) return "Cuentas acumuladas";
        if ("DETALLE".equals(tipoCuenta)) return "Cuentas a detalle";
        return "";
    }
   
}
