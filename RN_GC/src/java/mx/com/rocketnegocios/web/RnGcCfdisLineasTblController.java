package mx.com.rocketnegocios.web;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.sefactura.ResultadoCancelacion;
import com.sefactura.ConsultaCancelacion;
import com.sefactura.SolCancelacion;
import com.sefactura.pac.client.RespuestaTimbrado;
import com.sefactura.pac.client.Sefactura;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import mx.com.rocketnegocios.entities.RnGcCfdisLineasTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcCfdisLineasTblFacade;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.NoResultException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
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
import javax.xml.transform.stream.StreamSource;
import mx.com.rocketnegocios.beans.RnGcArchivosTblFacade;
import mx.com.rocketnegocios.beans.RnGcCatalogosusosTblFacade;
import mx.com.rocketnegocios.beans.RnGcCertificadosTblFacade;
import mx.com.rocketnegocios.beans.RnGcCfdisTblFacade;
import mx.com.rocketnegocios.beans.RnGcDocumentosRelacionadosTblFacade;
import mx.com.rocketnegocios.beans.RnGcFirmasTblFacade;
import mx.com.rocketnegocios.beans.RnGcImagenesTblFacade;
import mx.com.rocketnegocios.beans.RnGcPagosRelacionadosTblFacade;
import mx.com.rocketnegocios.beans.RnGcPersonasTblFacade;
import mx.com.rocketnegocios.beans.RnGcRegimenfiscalTblFacade;
import mx.com.rocketnegocios.beans.RnGcTimbresTblFacade;
import mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade;
import mx.com.rocketnegocios.entities.RnGcArchivosTbl;
import mx.com.rocketnegocios.entities.RnGcCatalogosusosTbl;
import mx.com.rocketnegocios.entities.RnGcCertificadosTbl;
import mx.com.rocketnegocios.entities.RnGcCfdisTbl;
import mx.com.rocketnegocios.entities.RnGcDocumentosRelacionadosTbl;
import mx.com.rocketnegocios.entities.RnGcFirmasTbl;
import mx.com.rocketnegocios.entities.RnGcImagenesTbl;
import mx.com.rocketnegocios.entities.RnGcPagosRelacionadosTbl;
import mx.com.rocketnegocios.entities.RnGcPersonasTbl;
import mx.com.rocketnegocios.entities.RnGcProductserviciosTbl;
import mx.com.rocketnegocios.entities.RnGcRegimenfiscalTbl;
import mx.com.rocketnegocios.entities.RnGcTimbresTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.io.FileUtils;
import org.apache.commons.ssl.PKCS8Key;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.pdf.PdfPage;
//import javafx.scene.text.TextAlignment;
import org.apache.poi.ss.usermodel.Color;



@Named("rnGcCfdisLineasTblController")
@SessionScoped
public class RnGcCfdisLineasTblController implements Serializable {

    @EJB
    private RnGcArchivosTblFacade archivosFacade;

    @EJB
    private mx.com.rocketnegocios.beans.RnGcCfdisLineasTblFacade ejbFacade;

    @EJB
    private RnGcCfdisTblFacade cfdisFacade;

    @EJB
    private RnGcPersonasTblFacade personaFacade;

    @EJB
    private RnGcUsuariosTblFacade usuarioFacade;

    @EJB
    private RnGcTimbresTblFacade timbresFacade;

    @EJB
    private RnGcCertificadosTblFacade certificadosFacade;

    @EJB
    private RnGcFirmasTblFacade firmasFacade;
    
    @EJB
    private RnGcUsuariosTblFacade usuariosFacade;

    @EJB
    private RnGcImagenesTblFacade imagenesFacade;
    
    @EJB
    private RnGcCatalogosusosTblFacade usosFacade;
    
    @EJB
    private RnGcRegimenfiscalTblFacade regimenFacade;
    
    @EJB
    private RnGcDocumentosRelacionadosTblFacade documentosRelacionadosFacade;

    @EJB
    private RnGcPagosRelacionadosTblFacade pagosRelacionadosFacade;

    private List<RnGcCfdisLineasTbl> items = null;
    private RnGcCfdisLineasTbl selected;
    private final UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private List<RnGcArchivosTbl> itemsDocs = null;
    private StreamedContent downLoadFile;
    private List<RnGcCfdisLineasTbl> itemsLineas = null;
    private RnGcCfdisLineasTbl lineasConceptos;
    private RnGcCfdisTbl cfdiId;
    private List<RnGcCfdisTbl> listaCfdis;
    private RnGcArchivosTbl archivo;
    private List<RnGcCfdisTbl> filteredEmitidos;
    private List<RnGcCfdisTbl> filteredRecibidos;
    private RnGcPersonasTbl persona;
    private List<RnGcCfdisTbl> recibidos;
    private List<RnGcCfdisTbl> emitidos;
    private RnGcUsuariosTbl usuario;
    private List<RnGcTimbresTbl> timbres;
    private RnGcCertificadosTbl certificados;
    private RnGcCfdisTbl cfdiUuid;
    private RnGcCfdisTbl cfdi2;
    private Double saldoPagar = 0.0;
    private int parcialidad = 0;
    private Double monto = 0.0;
    private Double montoTotal = 0.0;
    private Double saldoIinsoluto = 0.0;
    private List<RnGcCfdisTbl> listaCfdisRelacionados;
    private RnGcCfdisTbl cfdiRelacionado;
    private RnGcFirmasTbl firmas;
    private RnGcProductserviciosTbl producServicio2;
    private RnGcProductserviciosTbl producServicio;
    private RnGcDocumentosRelacionadosTbl cfdisRelacionados;
    private List<RnGcDocumentosRelacionadosTbl> listaDocumentosRelacionados;
    private RnGcPagosRelacionadosTbl pagosRelacionados;
    private List<RnGcPagosRelacionadosTbl> listaPagosRelacionados;
    private String motivo = "", uuidRelacionado = "";
    private Date ini, fin;
    private List<RnGcCfdisTbl> listaCfdisSustitucion;
    private RnGcCfdisTbl cfdiSustitucion;
    private StreamedContent downLoadFileC;

    public void setDownLoadFileC(StreamedContent downLoadFileC) {
        this.downLoadFileC = downLoadFileC;
    }

    public StreamedContent getDownLoadFileC() {
        return downLoadFileC;
    }

    public String getUuidRelacionado() {
        return uuidRelacionado;
    }

    public void setUuidRelacionado(String uuidRelacionado) {
        this.uuidRelacionado = uuidRelacionado;
    }

    
    
    public RnGcCfdisTbl getCfdiSustitucion() {
        if(cfdiSustitucion == null)
            cfdiSustitucion = new RnGcCfdisTbl();
        return cfdiSustitucion;
    }

    public void setCfdiSustitucion(RnGcCfdisTbl cfdiSustitucion) {
        this.cfdiSustitucion = cfdiSustitucion;
    }

    public List<RnGcCfdisTbl> getListaCfdisSustitucion() {
        return listaCfdisSustitucion;
    }

    public void setListaCfdisSustitucion(List<RnGcCfdisTbl> listaCfdisSustitucion) {
        this.listaCfdisSustitucion = listaCfdisSustitucion;
    }

    public Date getIni() {
        return ini;
    }

    public void setIni(Date ini) {
        this.ini = ini;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Double getSaldoPagar() {
        return saldoPagar;
    }

    public void setSaldoPagar(Double saldoPagar) {
        this.saldoPagar = saldoPagar;
    }

    public List<RnGcPagosRelacionadosTbl> getListaPagosRelacionados() {
        if (listaPagosRelacionados == null) {
            this.listaPagosRelacionados = new ArrayList<>();
        }
        return listaPagosRelacionados;
    }

    public void setListaPagosRelacionados(List<RnGcPagosRelacionadosTbl> listaPagosRelacionados) {
        this.listaPagosRelacionados = listaPagosRelacionados;
    }

    public RnGcPagosRelacionadosTbl getPagosRelacionados() {
        if (pagosRelacionados == null) {
            this.pagosRelacionados = new RnGcPagosRelacionadosTbl();
        }
        return pagosRelacionados;
    }

    public void setPagosRelacionados(RnGcPagosRelacionadosTbl pagosRelacionados) {
        this.pagosRelacionados = pagosRelacionados;
    }

    public RnGcProductserviciosTbl getProducServicio() {
        if (producServicio == null) {
            this.producServicio = new RnGcProductserviciosTbl();
        }
        return producServicio;
    }

    public void setProducServicio(RnGcProductserviciosTbl producServicio) {
        this.producServicio = producServicio;
    }

    public RnGcProductserviciosTbl getProducServicio2() {
        if (producServicio2 == null) {
            this.producServicio2 = new RnGcProductserviciosTbl();
        }
        return producServicio2;
    }

    public void setProducServicio2(RnGcProductserviciosTbl producServicio2) {
        this.producServicio2 = producServicio2;
    }

    public RnGcCfdisTbl getCfdiRelacionado() {
        if (cfdiRelacionado == null) {
            this.cfdiRelacionado = new RnGcCfdisTbl();
        }
        return cfdiRelacionado;
    }

    public void setCfdiRelacionado(RnGcCfdisTbl cfdiRelacionado) {
        this.cfdiRelacionado = cfdiRelacionado;
    }

    public List<RnGcCfdisTbl> getListaCfdis() {
        if (listaCfdis == null) {
            this.listaCfdis = new ArrayList<>();
        }
        return listaCfdis;
    }

    public void setListaCfdis(List<RnGcCfdisTbl> listaCfdis) {
        this.listaCfdis = listaCfdis;
    }

    public List<RnGcCfdisTbl> getListaCfdisRelacionados() {
        if (listaCfdisRelacionados == null) {
            this.listaCfdisRelacionados = new ArrayList<>();
        }
        return listaCfdisRelacionados;
    }

    public void setListaCfdisRelacionados(List<RnGcCfdisTbl> listaCfdisRelacionados) {
        this.listaCfdisRelacionados = listaCfdisRelacionados;
    }

    public RnGcFirmasTbl getFirmas() {
        return firmas;
    }

    public void setFirmas(RnGcFirmasTbl firmas) {
        this.firmas = firmas;
    }

    public int getParcialidad() {
        return parcialidad;
    }

    public void setParcialidad(int parcialidad) {
        this.parcialidad = parcialidad;
    }

    public RnGcCfdisLineasTblController() {
    }

    public RnGcCfdisLineasTbl getSelected() {
        if (selected == null) {
            selected = new RnGcCfdisLineasTbl();
        }
        return selected;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Double getSaldoIinsoluto() {
        return saldoIinsoluto;
    }

    public void setSaldoIinsoluto(Double saldoIinsoluto) {
        this.saldoIinsoluto = saldoIinsoluto;
    }

    public RnGcCfdisTbl getCfdiUuid() {
        if (cfdiUuid == null) {
            this.cfdiUuid = new RnGcCfdisTbl();
        }
        return cfdiUuid;
    }

    public void setCfdiUuid(RnGcCfdisTbl cfdiUuid) {
        this.cfdiUuid = cfdiUuid;
    }

    public RnGcCfdisTbl getCfdi2() {
        if (cfdi2 == null) {
            this.cfdi2 = new RnGcCfdisTbl();
        }
        return cfdi2;
    }

    public void setCfdi2(RnGcCfdisTbl cfdi2) {
        this.cfdi2 = cfdi2;
    }

    public RnGcArchivosTbl getArchivo() {
        if (archivo == null) {
            this.archivo = new RnGcArchivosTbl();
        }
        return archivo;
    }

    public RnGcUsuariosTbl getUsuario() {
        if (usuario == null) {
            this.usuario = new RnGcUsuariosTbl();
        }
        return usuario;
    }

    public RnGcCertificadosTbl getCertificados() {
        if (certificados == null) {
            this.certificados = new RnGcCertificadosTbl();
        }
        return certificados;
    }

    public void setCertificados(RnGcCertificadosTbl certificados) {
        this.certificados = certificados;
    }

    public void setUsuario(RnGcUsuariosTbl usuario) {
        this.usuario = usuario;
    }

    public void setArchivo(RnGcArchivosTbl archivo) {
        this.archivo = archivo;
    }

    public RnGcPersonasTbl getPersona() {
        if (persona == null) {
            this.persona = new RnGcPersonasTbl();
        }
        return persona;
    }

    public void setPersona(RnGcPersonasTbl persona) {
        this.persona = persona;
    }

    public RnGcCfdisLineasTbl getLineasConceptos() {
        if (lineasConceptos == null) {
            this.lineasConceptos = new RnGcCfdisLineasTbl();
        }
        return lineasConceptos;
    }

    public List<RnGcCfdisTbl> getFilteredEmitidos() {
        return filteredEmitidos;
    }

    public void setFilteredEmitidos(List<RnGcCfdisTbl> filteredEmitidos) {
        this.filteredEmitidos = filteredEmitidos;
    }

    public List<RnGcCfdisTbl> getFilteredRecibidos() {
        return filteredRecibidos;
    }

    public List<RnGcCfdisTbl> getEmitidos() {
        if (recibidos == null) {
            this.recibidos = new ArrayList<>();
        }
        return emitidos;
    }

    public void setEmitidos(List<RnGcCfdisTbl> emitidos) {
        this.emitidos = emitidos;
    }

    public List<RnGcCfdisTbl> getRecibidos() {
        if (emitidos == null) {
            this.emitidos = new ArrayList<>();
        }
        return recibidos;
    }

    public void setRecibidos(List<RnGcCfdisTbl> recibidos) {
        this.recibidos = recibidos;
    }

    public void setFilteredRecibidos(List<RnGcCfdisTbl> filteredRecibidos) {
        this.filteredRecibidos = filteredRecibidos;
    }

    public void setLineasConceptos(RnGcCfdisLineasTbl lineasConceptos) {
        this.lineasConceptos = lineasConceptos;
    }

    public RnGcCfdisTbl getCfdiId() {
        if (cfdiId == null) {
            this.cfdiId = new RnGcCfdisTbl();
        }
        return cfdiId;
    }

    public void setCfdiId(RnGcCfdisTbl cfdiId) {
        this.cfdiId = cfdiId;
    }

    public void setSelected(RnGcCfdisLineasTbl selected) {
        this.selected = selected;
    }

    public RnGcDocumentosRelacionadosTbl getCfdisRelacionados() {
        if (cfdisRelacionados == null) {
            this.cfdisRelacionados = new RnGcDocumentosRelacionadosTbl();
        }
        return cfdisRelacionados;
    }

    public void setCfdisRelacionados(RnGcDocumentosRelacionadosTbl cfdisRelacionados) {
        this.cfdisRelacionados = cfdisRelacionados;
    }

    protected void setEmbeddableKeys() {
        selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        selected.setUltimaFechaActualizacion(new Date());
    }

    protected void embeddable() {
        archivo = new RnGcArchivosTbl();
        archivo.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        archivo.setFechaCreacion(new Date());
        archivo.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        archivo.setUltimaFechaActualizacion(new Date());
    }

    protected void initializeEmbeddableKey() {
        selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        selected.setFechaCreacion(new Date());
    }

    private RnGcCfdisLineasTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcCfdisLineasTbl prepareCreate() {
        System.out.println("PrepareCreate");
        selected = new RnGcCfdisLineasTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void prepareCfdi2() {
        cfdi2 = new RnGcCfdisTbl();
    }

    public void actualizarDatos() throws ParseException {
        if (cfdiId.getSerie() == null || !cfdiId.getSerie().isEmpty()) {
            cfdiId.setFolio(null);
        }
        cfdiId.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        cfdiId.setUltimaFechaActualizacion(new Date());
        cfdiId = cfdisFacade.refreshFromDB(cfdiId);
        System.out.println("fechaExpedicion: " + cfdiId.getFechaExpedicion());
        listaCfdisRelacionados = new ArrayList<>();
        cfdisRelacionados = new RnGcDocumentosRelacionadosTbl();
        JsfUtil.addSuccessMessage("Datos Actualizados");
    }

    public void create() {
        System.out.println("Probando");
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCfdisLineasTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        System.out.println("update()");
        persistLinea(PersistAction.UPDATE);
    }

    public void destroy() {
        System.out.println("destroy()");
        persistLinea(PersistAction.DELETE);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
        System.out.println("Item Eliminado");
    }

    private void persistLinea(PersistAction persistAction) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != persistAction.DELETE) {
                    JsfUtil.addSuccessMessage("Concepto actualizado");
                    System.out.println("cfdiId: " + cfdiId);
                    selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                    selected.setUltimaFechaActualizacion(new Date());
                    getFacade().edit(selected);
                } else {
                    itemsLineas.remove(selected);
                    getFacade().remove(selected);
                    JsfUtil.addSuccessMessage("Concepto eliminado");
                }
            } catch (EJBException ex) {
                JsfUtil.addErrorMessage("Error durante el proceso");
            }
        } else {
            System.out.println("selected vacio");
        }
    }

    public List<RnGcCfdisLineasTbl> getItems() {
        items = getFacade().findAll();
        return items;
    }

    public void facturar(RnGcCfdisTbl cfdis) throws Exception {
        try {
            itemsLineas = getFacade().obtenerCfdisLineas(cfdis);
            embeddable();
            usuario = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            timbres = timbresFacade.obtenerTimbre(cfdiId.getProveedorTimbres(), usuario);
            if (timbres != null && !timbres.isEmpty()) {
                if (timbres.get(0).getTimbresRestantes() > 0) {
                    timbres.get(0).setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                    timbres.get(0).setUltimaFechaActualizacion(new Date());
                    timbres.get(0).setTimbresUsados(timbres.get(0).getTimbresUsados() + 1);
                    timbres.get(0).setTimbresRestantes(timbres.get(0).getTimbresRestantes() - 1);
                    timbres.get(0).setTimbresTotal(timbres.get(0).getTimbresRestantes());
                    if (crearXML()) {
                        timbresFacade.edit(timbres.get(0));
                        cfdiId = cfdisFacade.refreshFromDB(cfdiId);
                        archivo.setCfdiId(cfdiId);
                        archivosFacade.edit(archivo);
                        persona = personaFacade.obtenerporRfcCreadoPor(cfdiId.getRfcReceptor(), usuarioFirmado.obtenerIdUsuario());
                        if (crearFirmas()) {
                            firmas.setCfdiId(cfdiId);
                            firmasFacade.edit(firmas);
                        }
                        enviarCorreo(persona, archivo, cfdiId);
                        JsfUtil.addSuccessMessage(cfdiId.getRespuestaTimbrado());
                        System.out.println("getFacade().edit(selected)");
                    } else {
                        cfdiId = cfdisFacade.refreshFromDB(cfdiId);
                        JsfUtil.addSuccessMessage(cfdiId.getRespuestaTimbrado());
                    }
                } else {
                    JsfUtil.addErrorMessage("El número de timbres asignados se ha terminado");
                }
            } else {
                JsfUtil.addErrorMessage("Los timbres que tenia asignados se han terminado");
            }
        } catch (NullPointerException ex) {
            JsfUtil.addErrorMessage("No se escogio alguna factura");
        }
        actualizarDatos();
        System.out.println("cfdiId: " + cfdiId.getUuid() + " | " + cfdiId.getRespuestaTimbrado() + " | " + cfdiId + " | " + emitidos);
    }

    private void persist(PersistAction persistAction, String successMessage) {
        System.out.println("persist | " + persistAction);
        try {
            if (persistAction != PersistAction.DELETE) {
                embeddable();
                System.out.println("cfdiId1: " + cfdiId.getFechaExpedicion() + " | " + cfdiId.getRfcReceptor()
                        + " | " + cfdiId.getNombreReceptor());
                cfdiId.setImporte(calcularTotal());
                cfdisFacade.edit(cfdiId);
                if (crearXML()) {
                    cfdisFacade.edit(cfdiId);
                    archivo.setCfdiId(cfdiId);
                    archivosFacade.edit(archivo);
                    System.out.println("cfdiId2: " + cfdiId.getFechaExpedicion() + " | " + cfdiId.getRfcReceptor()
                            + " | " + cfdiId.getNombreReceptor());
                    System.out.println("archivo: " + archivo.getArchivoPdf() + " | " + archivo.getArchivoXml()
                            + " | " + archivo.getArchivoQR() + " | " + archivo.getCfdiId());
                    System.out.println("getFacade().edit(selected)");
                    JsfUtil.addSuccessMessage(cfdiId.getRespuestaTimbrado());
                    cfdiId = null;
                    archivo = null;
                    selected = null;
                    itemsLineas.clear();
                } else {
                    cfdisFacade.edit(cfdiId);
                    System.out.println("cfdiId3: " + cfdiId.getFechaExpedicion() + " | " + cfdiId.getRfcReceptor()
                            + " | " + cfdiId.getNombreReceptor());
                    JsfUtil.addSuccessMessage(cfdiId.getRespuestaTimbrado());
                    cfdiId = null;
                    selected = null;
                    itemsLineas.clear();
                }
            } else {
                getFacade().remove(selected);
                System.out.println("getFacade().remove(selected)");
            }
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

    public RnGcCfdisLineasTbl getRnGcCfdisLineasTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcCfdisLineasTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcCfdisLineasTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcCfdisLineasTbl.class)
    public static class RnGcCfdisLineasTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcCfdisLineasTblController controller = (RnGcCfdisLineasTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcCfdisLineasTblController");
            return controller.getRnGcCfdisLineasTbl(getKey(value));
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
            if (object instanceof RnGcCfdisLineasTbl) {
                RnGcCfdisLineasTbl o = (RnGcCfdisLineasTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcCfdisLineasTbl.class.getName()});
                return null;
            }
        }
    }

    public void prepareLineas(RnGcCfdisTbl cfdiId) {
        System.out.println("Cfdi: " + cfdiId.getUsoCfdi() + " | " + cfdiId.getMoneda() + " | " + cfdiId.getImporte() + " | " + cfdiId);
        if (cfdiId != null) {
            itemsLineas = getFacade().obtenerCfdisLineas(cfdiId);
            firmas = firmasFacade.obtenerFirmasPorCfdi(cfdiId);
            //obtenerDocumentosRelacionados(cfdiId);
            //sobtenerPagosRelacionados(cfdiId);
        }
    }

    public void preparedocs(RnGcCfdisTbl cfdiId) {
        if (cfdiId != null) {
            itemsDocs = getFacade().obtenerDocs(cfdiId);
            for (int i = 0; i < itemsDocs.size(); i++) {
                byte[] pdf = itemsDocs.get(i).getArchivoPdf();
                InputStream streamPdf = new ByteArrayInputStream(pdf);
                System.out.println("namepdf: " + streamPdf);
                downLoadFile = new DefaultStreamedContent(streamPdf, "Document/pdf", streamPdf.toString().concat(".pdf"));
                System.out.println("Archivo Descargado");
            }
        }
    }

    public List<RnGcCfdisLineasTbl> getItemsLineas() {
        return itemsLineas;
    }

    public void setItemsLineas(List<RnGcCfdisLineasTbl> itemsLineas) {
        this.itemsLineas = itemsLineas;
    }

    public List<RnGcArchivosTbl> getItemsDocs() {
        return itemsDocs;
    }

    public void setItemsDocs(List<RnGcArchivosTbl> itemsDocs) {
        this.itemsDocs = itemsDocs;
    }

    public StreamedContent getDownLoadFile() {
        return downLoadFile;
    }

    public void setDownLoadFile(StreamedContent downLoadFile) {
        this.downLoadFile = downLoadFile;
    }

    public String calcularImporte(Double cantidad, Double valorUnit) {
        System.out.println("Datos: " + cantidad + " | " + valorUnit);
        Double importe = 0.0;
        if (cantidad > 0.0 && valorUnit > 0.0) {
            importe = cantidad * valorUnit;
        }
        selected.setBase(importe);
        selected.setImporte(importe);
        System.out.println("calcularImporte: " + importe);
        return new DecimalFormat("0.00").format(importe);
    }

    public String calcularImporteImpuesto(String porcent, Double cantidad, Double valorUnit) {
        Double importeImpuesto = 0.0;
        Double importe = 0.0;
        if (!porcent.isEmpty()) {
            if (Double.parseDouble(porcent) > 1.0) {
                importe = Double.parseDouble(calcularImporte(cantidad, valorUnit));
                importeImpuesto = importe * (Double.parseDouble(porcent) / 100);
            } else {
                importe = Double.parseDouble(calcularImporte(cantidad, valorUnit));
                importeImpuesto = importe * Double.parseDouble(porcent);
            }
        }
        return new DecimalFormat("0.00").format(importeImpuesto);
    }

    public String calcularSubtotal() {
        Double subTotal = 0.0;
        if (itemsLineas != null) {
            for (int i = 0; i < itemsLineas.size(); i++) {
                System.out.println("itemsLineas: " + itemsLineas.get(i).getImporte() + " | " + itemsLineas.size());
                subTotal += itemsLineas.get(i).getImporte();
            }
        }
        return new DecimalFormat("0.00").format(subTotal);
    }

    public Double calcularTotal() {
        Double total = 0.0;
        total = Double.parseDouble(calcularSubtotal()) + Double.parseDouble(trasladadoISR()) + Double.parseDouble(trasladadoIva()) + Double.parseDouble(trasladadoIEPS()) - Double.parseDouble(retenidosISR()) - Double.parseDouble(retenidosIva()) - Double.parseDouble(retenidosIEPS()) - Double.parseDouble(calcularDescuento());
        cfdiId.setImporte(total);
        cfdiId.setSaldoInsoluto(total);
        cfdiId.setSaldoPagado(total);
        cfdiId.setSaldoPagar(0.0);
        return total;
    }

    public String trasladadoIva() {
        Double trasladosIva = 0.0;
        if (itemsLineas != null) {
            for (int i = 0; i < itemsLineas.size(); i++) {
                if (itemsLineas.get(i).getImpuesto().equals("002") && itemsLineas.get(i).getTipoImpuesto().equals("Traslado")) {
                    trasladosIva += itemsLineas.get(i).getImporteimpuesto();
                }
                if (itemsLineas.get(i).getImpuesto2() != null && itemsLineas.get(i).getImpuesto2().equals("002") && itemsLineas.get(i).getImpuesto2() != null && itemsLineas.get(i).getTipoImpuesto2().equals("Traslado")) {
                    trasladosIva += itemsLineas.get(i).getImporteImpuesto2();
                }
                if (itemsLineas.get(i).getImpuesto3().equals("002") && itemsLineas.get(i).getTipoImpuesto3().equals("Traslado")) {
                    trasladosIva += itemsLineas.get(i).getImporteImpuesto3();
                }
                if (itemsLineas.get(i).getImpuesto4().equals("002") && itemsLineas.get(i).getTipoImpuesto4().equals("Traslado")) {
                    trasladosIva += itemsLineas.get(i).getImporteImpuesto4();
                }
            }
        }
        return new DecimalFormat("0.00").format(trasladosIva);
    }

    public String trasladadoISR() {
        Double trasladosIsr = 0.0;
        if (itemsLineas != null) {
            for (int i = 0; i < itemsLineas.size(); i++) {
                if (itemsLineas.get(i).getImpuesto().equals("001") && itemsLineas.get(i).getTipoImpuesto().equals("Traslado")) {
                    trasladosIsr += itemsLineas.get(i).getImporteimpuesto();
                }
                if (itemsLineas.get(i).getImpuesto2() != null && itemsLineas.get(i).getImpuesto2().equals("001") && itemsLineas.get(i).getTipoImpuesto2().equals("Traslado")) {
                    trasladosIsr += itemsLineas.get(i).getImporteImpuesto2();
                }
                if (itemsLineas.get(i).getImpuesto3().equals("001") && itemsLineas.get(i).getTipoImpuesto3().equals("Traslado")) {
                    trasladosIsr += itemsLineas.get(i).getImporteImpuesto3();
                }
                if (itemsLineas.get(i).getImpuesto4().equals("001") && itemsLineas.get(i).getTipoImpuesto4().equals("Traslado")) {
                    trasladosIsr += itemsLineas.get(i).getImporteImpuesto4();
                }
            }
        }
        return new DecimalFormat("0.00").format(trasladosIsr);
    }

    public String trasladadoIEPS() {
        Double trasladadosIEPS = 0.0;
        if (itemsLineas != null) {
            for (int i = 0; i < itemsLineas.size(); i++) {
                if (itemsLineas.get(i).getImpuesto().equals("003") && itemsLineas.get(i).getTipoImpuesto().equals("Traslado")) {
                    trasladadosIEPS += itemsLineas.get(i).getImporteimpuesto();
                }
                if (itemsLineas.get(i).getImpuesto2() != null && itemsLineas.get(i).getImpuesto2().equals("003") && itemsLineas.get(i).getTipoImpuesto2().equals("Traslado")) {
                    trasladadosIEPS += itemsLineas.get(i).getImporteImpuesto2();
                }
                if (itemsLineas.get(i).getImpuesto3().equals("003") && itemsLineas.get(i).getTipoImpuesto3().equals("Traslado")) {
                    trasladadosIEPS += itemsLineas.get(i).getImporteImpuesto3();
                }
                if (itemsLineas.get(i).getImpuesto4().equals("003") && itemsLineas.get(i).getTipoImpuesto4().equals("Traslado")) {
                    trasladadosIEPS += itemsLineas.get(i).getImporteImpuesto4();
                }
            }
        }
        return new DecimalFormat("0.00").format(trasladadosIEPS);
    }

    public String retenidosIva() {
        Double retenidosIva = 0.0;
        if (itemsLineas != null) {
            for (int i = 0; i < itemsLineas.size(); i++) {
                if (itemsLineas.get(i).getImpuesto().equals("002") && itemsLineas.get(i).getTipoImpuesto().equals("Retención")) {
                    retenidosIva += itemsLineas.get(i).getImporteimpuesto();
                }
                if (itemsLineas.get(i).getImpuesto2() != null && itemsLineas.get(i).getImpuesto2().equals("002") && itemsLineas.get(i).getTipoImpuesto2().equals("Retención")) {
                    retenidosIva += itemsLineas.get(i).getImporteImpuesto2();
                }
                if (itemsLineas.get(i).getImpuesto3().equals("002") && itemsLineas.get(i).getTipoImpuesto3().equals("Retención")) {
                    retenidosIva += itemsLineas.get(i).getImporteImpuesto3();
                }
                if (itemsLineas.get(i).getImpuesto4().equals("002") && itemsLineas.get(i).getTipoImpuesto4().equals("Retención")) {
                    retenidosIva += itemsLineas.get(i).getImporteImpuesto4();
                }
            }
        }
        return new DecimalFormat("0.00").format(retenidosIva);
    }

    public String retenidosISR() {
        Double retenidosIsr = 0.0;
        if (itemsLineas != null) {
            for (int i = 0; i < itemsLineas.size(); i++) {
                if (itemsLineas.get(i).getImpuesto().equals("001") && itemsLineas.get(i).getTipoImpuesto().equals("Retención")) {
                    retenidosIsr += itemsLineas.get(i).getImporteimpuesto();
                }
                if (itemsLineas.get(i).getImpuesto2() != null && itemsLineas.get(i).getImpuesto2().equals("001") && itemsLineas.get(i).getTipoImpuesto2().equals("Retención")) {
                    retenidosIsr += itemsLineas.get(i).getImporteImpuesto2();
                }
                if (itemsLineas.get(i).getImpuesto3().equals("001") && itemsLineas.get(i).getTipoImpuesto3().equals("Retención")) {
                    retenidosIsr += itemsLineas.get(i).getImporteImpuesto3();
                }
                if (itemsLineas.get(i).getImpuesto4().equals("001") && itemsLineas.get(i).getTipoImpuesto4().equals("Retención")) {
                    retenidosIsr += itemsLineas.get(i).getImporteImpuesto4();
                }
            }
        }
        return new DecimalFormat("0.00").format(retenidosIsr);
    }

    public String retenidosIEPS() {
        Double retenidosIEPS = 0.0;
        if (itemsLineas != null) {
            for (int i = 0; i < itemsLineas.size(); i++) {
                if (itemsLineas.get(i).getImpuesto().equals("003") && itemsLineas.get(i).getTipoImpuesto().equals("Retención")) {
                    retenidosIEPS += itemsLineas.get(i).getImporteimpuesto();
                }
                if (itemsLineas.get(i).getImpuesto2() != null && itemsLineas.get(i).getImpuesto2().equals("003") && itemsLineas.get(i).getTipoImpuesto2().equals("Retención")) {
                    retenidosIEPS += itemsLineas.get(i).getImporteImpuesto2();
                }
                if (itemsLineas.get(i).getImpuesto3().equals("003") && itemsLineas.get(i).getTipoImpuesto3().equals("Retención")) {
                    retenidosIEPS += itemsLineas.get(i).getImporteImpuesto3();
                }
                if (itemsLineas.get(i).getImpuesto4().equals("003") && itemsLineas.get(i).getTipoImpuesto4().equals("Retención")) {
                    retenidosIEPS += itemsLineas.get(i).getImporteImpuesto4();
                }
            }
        }
        return new DecimalFormat("0.00").format(retenidosIEPS);
    }

    public boolean crearXML() throws Exception {
        String cadOrig = "";
        boolean valor = false;
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            //Raiz
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("cfdi:Comprobante");
            doc.appendChild(rootElement);
            //Atributos Raiz
            Attr version = doc.createAttribute("Version");
            version.setValue("3.3");
            rootElement.setAttributeNode(version);
            //System.out.println(cfdisId.getSerie());
            if (cfdiId.getSerie() != null && !cfdiId.getSerie().isEmpty()) {
                Attr serie = doc.createAttribute("Serie");
                serie.setValue(cfdiId.getSerie());
                rootElement.setAttributeNode(serie);
            }
            if (cfdiId.getFolio() != null) {
                Attr folio1 = doc.createAttribute("Folio");
                folio1.setValue(String.valueOf(cfdiId.getFolio()));
                rootElement.setAttributeNode(folio1);
            }//*/
            Attr fecha = doc.createAttribute("Fecha");
            fecha.setValue(new SimpleDateFormat("YYYY-MM-dd'T'hh:mm:ss").format(cfdiId.getFechaExpedicion()));
            rootElement.setAttributeNode(fecha);
            Attr sello = doc.createAttribute("Sello");
            sello.setValue("");
            rootElement.setAttributeNode(sello);
            Attr formaPago = doc.createAttribute("FormaPago");
            formaPago.setValue(cfdiId.getFormaPago());
            rootElement.setAttributeNode(formaPago);
            Attr noCertificado = doc.createAttribute("NoCertificado");
            noCertificado.setValue(cfdiId.getCertificados_Id().getNumeroCertificado());
            rootElement.setAttributeNode(noCertificado);
            Attr certificado1 = doc.createAttribute("Certificado");
            certificado1.setValue("");
            rootElement.setAttributeNode(certificado1);
            if (!cfdiId.getCondicionPago().isEmpty()) {
                Attr condicionPago = doc.createAttribute("CondicionesDePago");
                condicionPago.setValue(cfdiId.getCondicionPago());
                rootElement.setAttributeNode(condicionPago);
            }
            Attr subTotal = doc.createAttribute("SubTotal");
            subTotal.setValue(calcularSubtotal());
            rootElement.setAttributeNode(subTotal);
            Attr moneda = doc.createAttribute("Moneda");
            moneda.setValue(cfdiId.getMoneda());
            rootElement.setAttributeNode(moneda);
            if (cfdiId.getMoneda().equals("MXN") || cfdiId.getMoneda().equals("XXX")) {
                Attr tipoCambio = doc.createAttribute("TipoCambio");
                tipoCambio.setValue("1");
                rootElement.setAttributeNode(tipoCambio);
                cfdiId.setTipoCambio(1.0);
            } else {
                Attr tipoCambio = doc.createAttribute("TipoCambio");
                tipoCambio.setValue(String.valueOf(cfdiId.getTipoCambio()));
                rootElement.setAttributeNode(tipoCambio);
            }
            Attr total = doc.createAttribute("Total");
            total.setValue(new DecimalFormat("0.00").format(calcularTotal()));
            rootElement.setAttributeNode(total);
            Attr tipoCfdi = doc.createAttribute("TipoDeComprobante");
            tipoCfdi.setValue(cfdiId.getTipoComprobante());
            rootElement.setAttributeNode(tipoCfdi);
            Attr metodoPago = doc.createAttribute("MetodoPago");
            metodoPago.setValue(cfdiId.getMetodoPago());
            rootElement.setAttributeNode(metodoPago);
            Attr lugarExp = doc.createAttribute("LugarExpedicion");
            lugarExp.setValue(String.valueOf(cfdiId.getLugarExpedicion()));
            rootElement.setAttributeNode(lugarExp);

            Attr cfdi = doc.createAttribute("xmlns:cfdi");
            cfdi.setValue("http://www.sat.gob.mx/cfd/3");
            rootElement.setAttributeNode(cfdi);
            Attr xsi = doc.createAttribute("xmlns:xsi");
            xsi.setValue("http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttributeNode(xsi);
            Attr esquema = doc.createAttribute("xsi:schemaLocation");
            esquema.setValue("http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd");
            rootElement.setAttributeNode(esquema);
            //Nodo CfdiRelacionados
            if (cfdiId.getTipoRelacion() != null) {
                Element relacionados = doc.createElement("cfdi:CfdiRelacionados");
                rootElement.appendChild(relacionados);
                //Attributos CfdiRelacionados
                Attr tipoRelacion = doc.createAttribute("TipoRelacion");
                tipoRelacion.setValue(cfdiId.getTipoRelacion());
                relacionados.setAttributeNode(tipoRelacion);
                if (cfdiId.getUuidRelacionado() != null) {
                    Element relacionado = doc.createElement("cfdi:CfdiRelacionado");//NodoCfdiRelacionado
                    relacionados.appendChild(relacionado);
                    Attr uuid = doc.createAttribute("UUID");
                    uuid.setValue(cfdiId.getUuidRelacionado());
                    relacionado.setAttributeNode(uuid);
                }
            }
            //Nodo emisor
            Element emisor = doc.createElement("cfdi:Emisor");
            rootElement.appendChild(emisor);
            //Atributos Emisor
            Attr rfcE = doc.createAttribute("Rfc");
            rfcE.setValue(cfdiId.getRfcEmisor());
            emisor.setAttributeNode(rfcE);
            Attr nombreE = doc.createAttribute("Nombre");
            nombreE.setValue(cfdiId.getNombreEmisor());
            emisor.setAttributeNode(nombreE);
            Attr regimen = doc.createAttribute("RegimenFiscal");
            regimen.setValue(cfdiId.getClaveRegimenFiscal());
            emisor.setAttributeNode(regimen);
            //nodo receptor
            Element receptor = doc.createElement("cfdi:Receptor");
            rootElement.appendChild(receptor);
            //Atributos Receptor
            Attr rfcR = doc.createAttribute("Rfc");
            rfcR.setValue(cfdiId.getRfcReceptor());
            receptor.setAttributeNode(rfcR);
            Attr nombreR = doc.createAttribute("Nombre");
            nombreR.setValue(cfdiId.getNombreReceptor());
            receptor.setAttributeNode(nombreR);
            Attr uso = doc.createAttribute("UsoCFDI");
            uso.setValue(cfdiId.getUsoCfdi());
            receptor.setAttributeNode(uso);
            for (int i = 0; i < itemsLineas.size(); i++) {
                //Nodo conceptos
                Element conceptos = doc.createElement("cfdi:Conceptos");
                rootElement.appendChild(conceptos);
                //Atributos conceptos
                Element concepto = doc.createElement("cfdi:Concepto");
                conceptos.appendChild(concepto);
                Attr claveProd = doc.createAttribute("ClaveProdServ");
                claveProd.setValue(String.valueOf(itemsLineas.get(i).getClaveProdServ()));
                concepto.setAttributeNode(claveProd);
                if (!itemsLineas.get(i).getNoIdentificacion().isEmpty()) {
                    Attr noIdenti = doc.createAttribute("NoIdentificacion");
                    noIdenti.setValue(itemsLineas.get(i).getNoIdentificacion());
                    concepto.setAttributeNode(noIdenti);
                }
                Attr cantidad = doc.createAttribute("Cantidad");
                cantidad.setValue(new DecimalFormat("0").format(itemsLineas.get(i).getCantidad()));
                concepto.setAttributeNode(cantidad);
                Attr claveUnidad = doc.createAttribute("ClaveUnidad");
                claveUnidad.setValue(itemsLineas.get(i).getClaveUnidad());
                concepto.setAttributeNode(claveUnidad);
                if (!itemsLineas.get(i).getUnidad().isEmpty()) {
                    Attr unidad = doc.createAttribute("Unidad");
                    unidad.setValue(itemsLineas.get(i).getUnidad());
                    concepto.setAttributeNode(unidad);
                }
                Attr descripcion = doc.createAttribute("Descripcion");
                descripcion.setValue(itemsLineas.get(i).getDescripcion());
                concepto.setAttributeNode(descripcion);
                Attr valorUnit = doc.createAttribute("ValorUnitario");
                valorUnit.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getValorUnit()));
                concepto.setAttributeNode(valorUnit);
                Attr importe = doc.createAttribute("Importe");
                importe.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getImporte()));
                concepto.setAttributeNode(importe);
                //nodo impuestos
                Element impuestos = doc.createElement("cfdi:Impuestos");
                concepto.appendChild(impuestos);
                if (itemsLineas.get(i).getTipoImpuesto().equals("Traslado") || itemsLineas.get(i).getTipoImpuesto2().equals("Traslado")
                        || itemsLineas.get(i).getTipoImpuesto3().equals("Traslado") || itemsLineas.get(i).getTipoImpuesto4().equals("Traslado")) {
                    //nodo traslados
                    Element traslados = doc.createElement("cfdi:Traslados");
                    impuestos.appendChild(traslados);
                    if (itemsLineas.get(i).getTipoImpuesto().equals("Traslado")) {
                        System.out.println("1T");
                        //nodo traslado
                        Element traslado = doc.createElement("cfdi:Traslado");
                        traslados.appendChild(traslado);
                        //atributos traslado
                        Attr base1 = doc.createAttribute("Base");
                        base1.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getBase()));
                        traslado.setAttributeNode(base1);
                        Attr impuesto1 = doc.createAttribute("Impuesto");
                        impuesto1.setValue(String.valueOf(itemsLineas.get(i).getImpuesto()));
                        traslado.setAttributeNode(impuesto1);
                        Attr factor1 = doc.createAttribute("TipoFactor");
                        factor1.setValue(itemsLineas.get(i).getTipoFactor());
                        traslado.setAttributeNode(factor1);
                        Attr tasa1 = doc.createAttribute("TasaOCuota");
                        tasa1.setValue("0" + new DecimalFormat(".000000").format(itemsLineas.get(i).getTipoTasa() / 100));
                        traslado.setAttributeNode(tasa1);
                        Attr importeImpuesto1 = doc.createAttribute("Importe");
                        importeImpuesto1.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getImporteimpuesto()));
                        traslado.setAttributeNode(importeImpuesto1);
                    }
                    if (itemsLineas.get(i).getTipoImpuesto2().equals("Traslado")) {
                        System.out.println("2T");
                        //nodo traslado
                        Element traslado = doc.createElement("cfdi:Traslado");
                        traslados.appendChild(traslado);
                        //atributos traslado
                        Attr base2 = doc.createAttribute("Base");                       //Base
                        base2.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getBase()));
                        traslado.setAttributeNode(base2);
                        Attr impuesto2 = doc.createAttribute("Impuesto");            //Impuesto
                        impuesto2.setValue(String.valueOf(itemsLineas.get(i).getImpuesto2()));
                        traslado.setAttributeNode(impuesto2);
                        Attr factor2 = doc.createAttribute("TipoFactor");            //TipoFactor
                        factor2.setValue(itemsLineas.get(i).getTipoFactor2());
                        traslado.setAttributeNode(factor2);
                        Attr tasa2 = doc.createAttribute("TasaOCuota");             //TasaOCuota
                        tasa2.setValue("0" + new DecimalFormat(".000000").format(itemsLineas.get(i).getTipoTasa2() / 100));
                        traslado.setAttributeNode(tasa2);
                        Attr importeImpuesto2 = doc.createAttribute("Importe");     //ImporteImpuesto
                        importeImpuesto2.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getImporteImpuesto2()));
                        traslado.setAttributeNode(importeImpuesto2);
                    }
                    if (itemsLineas.get(i).getTipoImpuesto3().equals("Traslado")) {
                        System.out.println("3T");
                        //nodo traslado
                        Element traslado = doc.createElement("cfdi:Traslado");
                        traslados.appendChild(traslado);
                        //atributos traslado
                        Attr base3 = doc.createAttribute("Base");                       //Base
                        base3.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getBase()));
                        traslado.setAttributeNode(base3);
                        Attr impuesto3 = doc.createAttribute("Impuesto");            //Impuesto
                        impuesto3.setValue(String.valueOf(itemsLineas.get(i).getImpuesto3()));
                        traslado.setAttributeNode(impuesto3);
                        Attr factor3 = doc.createAttribute("TipoFactor");            //TipoFactor
                        factor3.setValue(itemsLineas.get(i).getTipoFactor3());
                        traslado.setAttributeNode(factor3);
                        Attr tasa3 = doc.createAttribute("TasaOCuota");             //TasaOCuota
                        tasa3.setValue("0" + new DecimalFormat(".000000").format(itemsLineas.get(i).getTipoTasa3() / 100));
                        traslado.setAttributeNode(tasa3);
                        Attr importeImpuesto3 = doc.createAttribute("Importe");     //ImporteImpuesto
                        importeImpuesto3.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getImporteImpuesto3()));
                        traslado.setAttributeNode(importeImpuesto3);
                    }
                    if (itemsLineas.get(i).getTipoImpuesto4().equals("Traslado")) {
                        System.out.println("4T");
                        //nodo traslado
                        Element traslado = doc.createElement("cfdi:Traslado");
                        traslados.appendChild(traslado);
                        //atributos traslado
                        Attr base4 = doc.createAttribute("Base");                       //Base
                        base4.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getBase()));
                        traslado.setAttributeNode(base4);
                        Attr impuesto4 = doc.createAttribute("Impuesto");            //Impuesto
                        impuesto4.setValue(String.valueOf(itemsLineas.get(i).getImpuesto4()));
                        traslado.setAttributeNode(impuesto4);
                        Attr factor4 = doc.createAttribute("TipoFactor");            //TipoFactor
                        factor4.setValue(itemsLineas.get(i).getTipoFactor4());
                        traslado.setAttributeNode(factor4);
                        Attr tasa4 = doc.createAttribute("TasaOCuota");             //TasaOCuota
                        tasa4.setValue("0" + new DecimalFormat(".000000").format(itemsLineas.get(i).getTipoTasa4() / 100));
                        traslado.setAttributeNode(tasa4);
                        Attr importeImpuesto4 = doc.createAttribute("Importe");     //ImporteImpuesto
                        importeImpuesto4.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getImporteImpuesto4()));
                        traslado.setAttributeNode(importeImpuesto4);
                    }
                }
                if (itemsLineas.get(i).getTipoImpuesto().equals("Retención") || itemsLineas.get(i).getTipoImpuesto2().equals("Retención")
                        || itemsLineas.get(i).getTipoImpuesto3().equals("Retención") || itemsLineas.get(i).getTipoImpuesto4().equals("Retención")) {
                    //nodo retenciones
                    Element retenciones = doc.createElement("cfdi:Retenciones");
                    impuestos.appendChild(retenciones);
                    if (itemsLineas.get(i).getTipoImpuesto().equals("Retención")) {
                        System.out.println("1R");
                        //nodo retencion
                        Element retencion = doc.createElement("cfdi:Retencion");
                        retenciones.appendChild(retencion);
                        //atributos traslado
                        Attr baseR1 = doc.createAttribute("Base");                       //Base
                        baseR1.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getBase()));
                        retencion.setAttributeNode(baseR1);
                        Attr impuestoR1 = doc.createAttribute("Impuesto");            //Impuesto
                        impuestoR1.setValue(String.valueOf(itemsLineas.get(i).getImpuesto()));
                        retencion.setAttributeNode(impuestoR1);
                        Attr factorR1 = doc.createAttribute("TipoFactor");            //TipoFactor
                        factorR1.setValue(itemsLineas.get(i).getTipoFactor());
                        retencion.setAttributeNode(factorR1);
                        Attr tasaR1 = doc.createAttribute("TasaOCuota");             //TasaOCuota
                        tasaR1.setValue("0" + new DecimalFormat(".000000").format(itemsLineas.get(i).getTipoTasa() / 100));
                        retencion.setAttributeNode(tasaR1);
                        Attr importeR1 = doc.createAttribute("Importe");               //Importe
                        importeR1.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getImporteimpuesto()));
                        retencion.setAttributeNode(importeR1);
                    }
                    if (itemsLineas.get(i).getTipoImpuesto2().equals("Retención")) {
                        System.out.println("2R");
                        //nodo retencion
                        Element retencion = doc.createElement("cfdi:Retencion");
                        retenciones.appendChild(retencion);
                        //atributos traslado
                        Attr baseR2 = doc.createAttribute("Base");                       //Base
                        baseR2.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getBase()));
                        retencion.setAttributeNode(baseR2);
                        Attr impuestoR2 = doc.createAttribute("Impuesto");            //Impuesto
                        impuestoR2.setValue(String.valueOf(itemsLineas.get(i).getImpuesto2()));
                        retencion.setAttributeNode(impuestoR2);
                        Attr factorR2 = doc.createAttribute("TipoFactor");            //TipoFactor
                        factorR2.setValue(itemsLineas.get(i).getTipoFactor2());
                        retencion.setAttributeNode(factorR2);
                        Attr tasaR2 = doc.createAttribute("TasaOCuota");             //TasaOCuota
                        tasaR2.setValue("0" + new DecimalFormat(".000000").format(itemsLineas.get(i).getTipoTasa2() / 100));
                        retencion.setAttributeNode(tasaR2);
                        Attr importeR2 = doc.createAttribute("Importe");               //Importe
                        importeR2.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getImporteImpuesto2()));
                        retencion.setAttributeNode(importeR2);
                    }
                    if (itemsLineas.get(i).getTipoImpuesto3().equals("Retención")) {
                        System.out.println("3R");
                        //nodo retencion
                        Element retencion = doc.createElement("cfdi:Retencion");
                        retenciones.appendChild(retencion);
                        //atributos traslado
                        Attr baseR3 = doc.createAttribute("Base");                       //Base
                        baseR3.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getBase()));
                        retencion.setAttributeNode(baseR3);
                        Attr impuestoR3 = doc.createAttribute("Impuesto");            //Impuesto
                        impuestoR3.setValue(String.valueOf(itemsLineas.get(i).getImpuesto3()));
                        retencion.setAttributeNode(impuestoR3);
                        Attr factorR3 = doc.createAttribute("TipoFactor");            //TipoFactor
                        factorR3.setValue(itemsLineas.get(i).getTipoFactor3());
                        retencion.setAttributeNode(factorR3);
                        Attr tasaR3 = doc.createAttribute("TasaOCuota");             //TasaOCuota
                        tasaR3.setValue("0" + new DecimalFormat(".000000").format(itemsLineas.get(i).getTipoTasa3() / 100));
                        retencion.setAttributeNode(tasaR3);
                        Attr importeR3 = doc.createAttribute("Importe");               //Importe
                        importeR3.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getImporteImpuesto3()));
                        retencion.setAttributeNode(importeR3);
                    }
                    if (itemsLineas.get(i).getTipoImpuesto4().equalsIgnoreCase("Retención")) {
                        //nodo retencion
                        Element retencion = doc.createElement("cfdi:Retencion");
                        retenciones.appendChild(retencion);
                        //atributos traslado
                        Attr baseR4 = doc.createAttribute("Base");                       //Base
                        baseR4.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getBase()));
                        retencion.setAttributeNode(baseR4);
                        Attr impuestoR4 = doc.createAttribute("Impuesto");            //Impuesto
                        impuestoR4.setValue(String.valueOf(itemsLineas.get(i).getImpuesto4()));
                        retencion.setAttributeNode(impuestoR4);
                        Attr factorR4 = doc.createAttribute("TipoFactor");            //TipoFactor
                        factorR4.setValue(itemsLineas.get(i).getTipoFactor4());
                        retencion.setAttributeNode(factorR4);
                        Attr tasaR4 = doc.createAttribute("TasaOCuota");             //TasaOCuota
                        tasaR4.setValue("0" + new DecimalFormat(".000000").format(itemsLineas.get(i).getTipoTasa4() / 100));
                        retencion.setAttributeNode(tasaR4);
                        Attr importeR4 = doc.createAttribute("Importe");               //Importe
                        importeR4.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getImporteImpuesto4()));
                        retencion.setAttributeNode(importeR4);
                    }
                }
            }
            //nodo impuestos
            Element impuestos = doc.createElement("cfdi:Impuestos");
            rootElement.appendChild(impuestos);
            if (Double.parseDouble(retenidosISR()) > 0.0 || Double.parseDouble(retenidosIva()) > 0.0 || Double.parseDouble(retenidosIEPS()) > 0.0) {
                //Atributos impuestos
                Attr totalRetenidos = doc.createAttribute("TotalImpuestosRetenidos");
                totalRetenidos.setValue(new DecimalFormat("0.00").format(Double.parseDouble(retenidosISR()) + Double.parseDouble(retenidosIva()) + Double.parseDouble(retenidosIEPS())));
                impuestos.setAttributeNode(totalRetenidos);
                //nodo retenciones
                Element retenciones = doc.createElement("cfdi:Retenciones");
                impuestos.appendChild(retenciones);
                for (int i = 0; i < itemsLineas.size(); i++) {
                    if (itemsLineas.get(i).getTipoImpuesto().equals("Retención")) {
                        //nodo retencion
                        Element retencion1 = doc.createElement("cfdi:Retencion");
                        retenciones.appendChild(retencion1);
                        //Atributos
                        Attr impuesto = doc.createAttribute("Impuesto");
                        impuesto.setValue(String.valueOf(itemsLineas.get(i).getImpuesto()));
                        retencion1.setAttributeNode(impuesto);
                        Attr importe = doc.createAttribute("Importe");
                        importe.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getImporteimpuesto()));
                        retencion1.setAttributeNode(importe);
                    }
                    if (itemsLineas.get(i).getTipoImpuesto2().equals("Retención")) {
                        //nodo retencion
                        Element retencion2 = doc.createElement("cfdi:Retencion");
                        retenciones.appendChild(retencion2);
                        //Atributos
                        Attr impuesto2 = doc.createAttribute("Impuesto");
                        impuesto2.setValue(String.valueOf(itemsLineas.get(i).getImpuesto2()));
                        retencion2.setAttributeNode(impuesto2);
                        Attr importe2 = doc.createAttribute("Importe");
                        importe2.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getImporteImpuesto2()));
                        retencion2.setAttributeNode(importe2);
                    }
                    if (itemsLineas.get(i).getTipoImpuesto3().equals("Retención")) {
                        //nodo retencion
                        Element retencion3 = doc.createElement("cfdi:Retencion");
                        retenciones.appendChild(retencion3);
                        //Atributos
                        Attr impuesto3 = doc.createAttribute("Impuesto");
                        impuesto3.setValue(String.valueOf(itemsLineas.get(i).getImpuesto3()));
                        retencion3.setAttributeNode(impuesto3);
                        Attr importe3 = doc.createAttribute("Importe");
                        importe3.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getImporteImpuesto3()));
                        retencion3.setAttributeNode(importe3);
                    }
                    if (itemsLineas.get(i).getTipoImpuesto4().equals("Retención")) {
                        //nodo retencion
                        Element retencion4 = doc.createElement("cfdi:Retencion");
                        retenciones.appendChild(retencion4);
                        //Atributos retencion
                        Attr impuesto4 = doc.createAttribute("Impuesto");
                        impuesto4.setValue(String.valueOf(itemsLineas.get(i).getImpuesto4()));
                        retencion4.setAttributeNode(impuesto4);
                        Attr importe4 = doc.createAttribute("Importe");
                        importe4.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getImporteImpuesto4()));
                        retencion4.setAttributeNode(importe4);
                    }
                }
            }
            if (Double.parseDouble(trasladadoISR()) > 0.0 || Double.parseDouble(trasladadoIva()) > 0.0 || Double.parseDouble(trasladadoIEPS()) > 0.0) {
                //Atributos impuestos
                Attr totalTrasladado = doc.createAttribute("TotalImpuestosTrasladados");
                totalTrasladado.setValue(new DecimalFormat("0.00").format(Double.parseDouble(trasladadoISR()) + Double.parseDouble(trasladadoIva()) + Double.parseDouble(trasladadoIEPS())));
                impuestos.setAttributeNode(totalTrasladado);
                //nodo traslados
                Element traslados = doc.createElement("cfdi:Traslados");
                impuestos.appendChild(traslados);
                for (int i = 0; i < itemsLineas.size(); i++) {
                    if (itemsLineas.get(i).getTipoImpuesto().equals("Traslado")) {
                        //nodo traslado
                        Element traslado1 = doc.createElement("cfdi:Traslado");
                        traslados.appendChild(traslado1);
                        //Atributos traslado
                        Attr impuesto = doc.createAttribute("Impuesto");
                        impuesto.setValue(String.valueOf(itemsLineas.get(i).getImpuesto()));
                        traslado1.setAttributeNode(impuesto);
                        Attr factor = doc.createAttribute("TipoFactor");
                        factor.setValue(itemsLineas.get(i).getTipoFactor());
                        traslado1.setAttributeNode(factor);
                        Attr tasa = doc.createAttribute("TasaOCuota");
                        tasa.setValue("0" + new DecimalFormat(".000000").format(itemsLineas.get(i).getTipoTasa() / 100));
                        traslado1.setAttributeNode(tasa);
                        Attr importe = doc.createAttribute("Importe");
                        importe.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getImporteimpuesto()));
                        traslado1.setAttributeNode(importe);
                    }
                    if (itemsLineas.get(i).getTipoImpuesto2().equals("Traslado")) {
                        //nodo traslado
                        Element traslado2 = doc.createElement("cfdi:Traslado");
                        traslados.appendChild(traslado2);
                        //Atributos traslado
                        Attr impuesto2 = doc.createAttribute("Impuesto");
                        impuesto2.setValue(String.valueOf(itemsLineas.get(i).getImpuesto2()));
                        traslado2.setAttributeNode(impuesto2);
                        Attr factor2 = doc.createAttribute("TipoFactor");
                        factor2.setValue(itemsLineas.get(i).getTipoFactor2());
                        traslado2.setAttributeNode(factor2);
                        Attr tasa2 = doc.createAttribute("TasaOCuota");
                        tasa2.setValue("0" + new DecimalFormat(".000000").format(itemsLineas.get(i).getTipoTasa2() / 100));
                        traslado2.setAttributeNode(tasa2);
                        Attr importe2 = doc.createAttribute("Importe");
                        importe2.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getImporteImpuesto2()));
                        traslado2.setAttributeNode(importe2);
                    }
                    if (itemsLineas.get(i).getTipoImpuesto3().equals("Traslado")) {
                        //nodo traslado
                        Element traslado3 = doc.createElement("cfdi:Traslado");
                        traslados.appendChild(traslado3);
                        //Atributos traslado
                        Attr impuesto3 = doc.createAttribute("Impuesto");
                        impuesto3.setValue(String.valueOf(itemsLineas.get(i).getImpuesto3()));
                        traslado3.setAttributeNode(impuesto3);
                        Attr factor3 = doc.createAttribute("TipoFactor");
                        factor3.setValue(itemsLineas.get(i).getTipoFactor3());
                        traslado3.setAttributeNode(factor3);
                        Attr tasa3 = doc.createAttribute("TasaOCuota");
                        tasa3.setValue("0" + new DecimalFormat(".000000").format(itemsLineas.get(i).getTipoTasa3() / 100));
                        traslado3.setAttributeNode(tasa3);
                        Attr importe3 = doc.createAttribute("Importe");
                        importe3.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getImporteImpuesto3()));
                        traslado3.setAttributeNode(importe3);
                    }
                    if (itemsLineas.get(i).getTipoImpuesto4().equals("Traslado")) {
                        //nodo traslado
                        Element traslado4 = doc.createElement("cfdi:Traslado");
                        traslados.appendChild(traslado4);
                        //Atributos traslado
                        Attr impuesto4 = doc.createAttribute("Impuesto");
                        impuesto4.setValue(String.valueOf(itemsLineas.get(i).getImpuesto4()));
                        traslado4.setAttributeNode(impuesto4);
                        Attr factor4 = doc.createAttribute("TipoFactor");
                        factor4.setValue(itemsLineas.get(i).getTipoFactor4());
                        traslado4.setAttributeNode(factor4);
                        Attr tasa4 = doc.createAttribute("TasaOCuota");
                        tasa4.setValue("0" + new DecimalFormat(".000000").format(itemsLineas.get(i).getTipoTasa4() / 100));
                        traslado4.setAttributeNode(tasa4);
                        Attr importe4 = doc.createAttribute("Importe");
                        importe4.setValue(new DecimalFormat("0.00").format(itemsLineas.get(i).getImporteImpuesto4()));
                        traslado4.setAttributeNode(importe4);
                    }
                }
            }
            System.out.println("Probando");
            File xslt = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/cadenaoriginal_3_3.xslt"));            //Inicio calcula cadena original
            StreamSource sourceXSL = new StreamSource(xslt);
            System.out.println("Probando2");

            DOMSource source = new DOMSource(doc);
            //File tempFile = File.createTempFile("cfdi" + n + ".xml", null);
            File tempFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/xmlTemp.xml"));
            FileWriter writer = new FileWriter(tempFile);
            System.out.println("Probando3");
            StreamResult sourceXml = new StreamResult(writer);
            TransformerFactory trasnformerFactory = TransformerFactory.newInstance();
            Transformer trasnformer = trasnformerFactory.newTransformer();
            trasnformer.transform(source, sourceXml);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();   //CadenaOrignal
            StreamResult cadenaOriginal = new StreamResult(baos);
            StreamSource sourceXml2 = new StreamSource(tempFile);
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer trasnformer2 = tFactory.newTransformer(sourceXSL);
            trasnformer2.transform(sourceXml2, cadenaOriginal);
            cadOrig = baos.toString();                                            //CadenaOriginal
            leerCfdi(tempFile);
            crearSello(cadOrig);
            if (modificarXml(cadOrig, tempFile)) {
                valor = true;
            } else {
                valor = false;
            }
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException te) {
            te.printStackTrace();
        } catch (NullPointerException ex) {
            JsfUtil.addErrorMessage("Error al ingresar datos");               //*/
        }
        System.out.println("valor: " + valor);
        return valor;
    }

    public String leerCfdi(File xml) {
        File archivo1 = xml;
        FileReader fr = null;
        BufferedReader br = null;
        String linea = " ";
        try {
            fr = new FileReader(archivo1);
            br = new BufferedReader(fr);

            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
                cfdiId.setXmlTrama(linea);
            }
        } catch (IOException ex) {
            ex.getMessage();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.getMessage();
            }
        }
        return linea;
    }

    public String crearSello(String xml) throws Exception {
        PKCS8Key pkcs8 = new PKCS8Key(cfdiId.getCertificados_Id().getLlavePrivada(), cfdiId.getCertificados_Id().getContraseniaLlavePrivada().toCharArray());
        KeyFactory privateKeyFact = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec pkcs8Encoded = new PKCS8EncodedKeySpec(pkcs8.getDecryptedBytes());
        PrivateKey privateKey = privateKeyFact.generatePrivate(pkcs8Encoded);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        byte[] cadenaOriginalArray = xml.getBytes();
        signature.update(cadenaOriginalArray);
        String firma = new String(Base64.getEncoder().encode(signature.sign()));
        System.out.println("firma: " + firma);
        return firma;
    }

    public String crearCertificado() throws Exception {
        String certificadoB64 = new String(Base64.getEncoder().encode(cfdiId.getCertificados_Id().getCertificadoSelloDigital()));
        System.out.println("certificado: " + certificadoB64);
        return certificadoB64;
    }

    public boolean modificarXml(String xml, File xmlAc) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = docFactory.newDocumentBuilder();
        Document doc = builder.parse(xmlAc);
        boolean valorModifica = false;
        File tempFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/xmlTemp.xml"));
        NodeList items = doc.getElementsByTagName("cfdi:Comprobante");
        for (int i = 0; i < items.getLength(); i++) {
            Element element = (Element) items.item(i);
            element.setAttribute("Sello", crearSello(xml));
            element.setAttribute("Certificado", crearCertificado());
        }
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result output = new StreamResult(tempFile);
        Source input = new DOMSource(doc);
        transformer.transform(input, output);
        if (timbra(tempFile.getPath(), xml)) {
            valorModifica = true;
        } else {
            valorModifica = false;
        }
        return valorModifica;
    }

    public boolean timbra(String nombre, String cadOriginal) {
        System.out.println("nombre: " + nombre + " | cadOriginal: " + cadOriginal);
        boolean valorTimbra = false;
        try {
            FileInputStream fis = new FileInputStream(new java.io.File(nombre));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int cuantos = 0;
            byte[] bytes = new byte[10000];
            File xmltimbrado = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/xmlTimbrado.xml"));
            while ((cuantos = fis.read(bytes, 0, bytes.length)) >= 0) {
                baos.write(bytes, 0, cuantos);
            }
            bytes = baos.toByteArray();
            baos.close();
            String xml = new String(bytes, "UTF-8");
            Sefactura sf = new Sefactura("http://www.jonima.com.mx:3014", "VICA840114RZ4", "VICA840114RZ4");
            System.out.println("XML: " + xml);
            RespuestaTimbrado rt = sf.timbrado(xml);
            if (rt.getResultado() != null && rt.getResultado().length() > 0) {
                System.out.println("Error al generar timbrado: " + rt.getResultado());
                cfdiId.setRespuestaTimbrado(rt.getResultado());
                valorTimbra = false;
            } else {
                String selloSAT = " ";
                String noCertSAT = " ";
                String fechaTimbrado = " ";
                String Uuid = " ";
                String selloCFDI = " ";
                String xmlTimbrado = rt.getXml();
                cfdiId.setXmlTrama(xmlTimbrado);
                FileOutputStream fos = new FileOutputStream(xmltimbrado);
                fos.write(xmlTimbrado.getBytes("UTF-8"));
                fos.close();
                byte[] codQR = Base64.getDecoder().decode(rt.getCadenaCodigo());
                archivo.setArchivoQR(codQR);
                byte[] xmlTimbradoB = new byte[(int) xmltimbrado.length()];
                fis = new FileInputStream(xmltimbrado);
                fis.read(xmlTimbradoB);
                fis.close();
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();//obtener datos de XML Timbbrado para crear PDF
                DocumentBuilder builder = docFactory.newDocumentBuilder();
                Document doc = builder.parse(xmltimbrado);
                NodeList items = doc.getElementsByTagName("tfd:TimbreFiscalDigital");
                for (int i = 0; i < items.getLength(); i++) {
                    Element element = (Element) items.item(0);
                    selloSAT = element.getAttribute("SelloSAT");
                    noCertSAT = element.getAttribute("NoCertificadoSAT");
                    fechaTimbrado = element.getAttribute("FechaTimbrado");
                    Uuid = element.getAttribute("UUID");
                    selloCFDI = element.getAttribute("SelloCFD");
                    cfdiId.setUuid(element.getAttribute("UUID"));
                }
                archivo.setArchivoXml(xmlTimbradoB);
                leerCfdi(xmltimbrado);
                cfdiId.setRespuestaTimbrado("Timbrado de forma correcta");
                System.out.println(noCertSAT + " | " + fechaTimbrado + " | " + Uuid + " | " + selloCFDI + " | " + selloSAT);
                crearPDF(selloSAT, noCertSAT, fechaTimbrado, Uuid, selloCFDI, codQR, cadOriginal);
                //crearArchivo(xmltimbrado);
                valorTimbra = true;
                System.out.println("Ya termine ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valorTimbra;
    }

    public void crearPDF(String selloSAT, String noCertSAT, String fechaTimbrado, String Uuid, String selloCFDI, byte[] codigoQR, String cadenaOrig) throws JRException, IOException, ParseException {
        System.out.println("Creacion de PDF");
        FileOutputStream fos = new FileOutputStream(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/qr.png")));
        fos.write(codigoQR);
        fos.close();
        String imagenqr = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRealPath("/resources/images/qr.png");
        String imagenLogo = FacesContext.getCurrentInstance().
                getExternalContext().
                getRealPath("/resources/images/logoAdminContable.png");
        //Crear Map para setear los valores de la factura
        Map<String, Object> parametros = new HashMap<String, Object>();
        //Parametros
        parametros.put("RFC_Emisor", cfdiId.getRfcEmisor());
        parametros.put("Nombre_Emisor", cfdiId.getNombreEmisor());
        parametros.put("RFC_Receptor", cfdiId.getRfcReceptor());
        parametros.put("Nombre_Receptor", cfdiId.getNombreReceptor());
        parametros.put("Uso_CFDI", cfdiId.getUsoCfdi());
        parametros.put("Folio_Fiscal", cfdiId.getFolio());
        parametros.put("NoSerie_CSD", cfdiId.getSerie());
        parametros.put("CodigoPostal", cfdiId.getLugarExpedicion());
        parametros.put("FechaHora_Emision", new SimpleDateFormat("YYYY-MM-dd'T'hh:mm:ss").format(cfdiId.getFechaExpedicion()));
        parametros.put("EfectoComprobante", cfdiId.getTipoComprobante());
        if (cfdiId.getUsoCfdi() != null) {
            switch (cfdiId.getUsoCfdi()) {

                case "G01":
                    parametros.put("Uso_CFDI", "Adquisición de mercancias");
                    break;
                case "G02":
                    parametros.put("Uso_CFDI", "Devoluciones, descuentos o bonificaciones");
                    break;
                case "G03":
                    parametros.put("Uso_CFDI", "Gastos en general");
                    break;
                case "I01":
                    parametros.put("Uso_CFDI", "Contrucciones");
                    break;
                case "I02":
                    parametros.put("Uso_CFDI", "Mobiliario y equipo de oficina por inversiones");
                    break;
                case "I03":
                    parametros.put("Uso_CFDI", "Equipo de transporte");
                    break;
                case "I04":
                    parametros.put("Uso_CFDI", "Equipo de computo y accesorios");
                    break;
                case "I05":
                    parametros.put("Uso_CFDI", "Dados, troqueles, moldes, matrices y herramiental");
                    break;
                case "I06":
                    parametros.put("Uso_CFDI", "Comunicaciones telefónicas");
                    break;
                case "I07":
                    parametros.put("Uso_CFDI", "Comunicaciones Satelitales");
                    break;
                case "I08":
                    parametros.put("Uso_CFDI", "Otra Maquinaria y equipo");
                    break;
                case "D01":
                    parametros.put("Uso_CFDI", "Honorarios médicos, dentales y gastos hospitalarios");
                    break;
                case "D02":
                    parametros.put("Uso_CFDI", "Gastos médicos por incapacidad o discapacidad");
                    break;
                case "D03":
                    parametros.put("Uso_CFDI", "GastosFunerales");
                    break;
                case "D04":
                    parametros.put("Uso_CFDI", "Donativos");
                    break;
                case "D05":
                    parametros.put("Uso_CFDI", "Interese reales efectivamente pagados por créditos hipotecarios (casa habitación)");
                    break;
                case "D06":
                    parametros.put("Uso_CFDI", "Aportaciones voluntarias al SAR");
                    break;
                case "D07":
                    parametros.put("Uso_CFDI", "Primas por seguros de gastos médicos");
                    break;
                case "D08":
                    parametros.put("Uso_CFDI", "Gastos de trasnportación escolar obligatoria");
                    break;
                case "D09":
                    parametros.put("Uso_CFDI", "Depósitos en cuentas para el ahorro, primas que tengan como base planes de pensiones");
                    break;
                case "D10":
                    parametros.put("Uso_CFDI", "Pagos por servicios educativos (colegiaturas)");
                    break;
                case "P01":
                    parametros.put("Uso_CFDI", "Por definir");
                    break;
                default:
                    parametros.put("Uso_CFDI", "-");
                    break;
            }
        }
        if (cfdiId.getClaveRegimenFiscal() != null) {
            switch (cfdiId.getClaveRegimenFiscal()) {

                case "601":
                    parametros.put("RegimenFiscal", "601 - General de Ley Personas Morales");
                    break;
                case "603":
                    parametros.put("RegimenFiscal", "603 - Personas Morales con Fines no Lucrativos");
                    break;
                case "605":
                    parametros.put("RegimenFiscal", "605 - Sueldo y Salarios e Ingresos Asimilados a Salarios");
                    break;
                case "606":
                    parametros.put("RegimenFiscal", "606 - Arrendamiento");
                    break;
                case "608":
                    parametros.put("RegimenFiscal", "608 - Demás Ingresos");
                    break;
                case "609":
                    parametros.put("RegimenFiscal", "609 - onsolidación");
                    break;
                case "610":
                    parametros.put("RegimenFiscal", "610 - Residentes en el Extranjero sin Establecimiento Permanente en México");
                    break;
                case "611":
                    parametros.put("RegimenFiscal", "611 - Ingresos por Dividendos (socios y accionistas)");
                    break;
                case "612":
                    parametros.put("RegimenFiscal", "612 - Personas Físicas con Actividades Empresariales y Profesionales");
                    break;
                case "614":
                    parametros.put("RegimenFiscal", "614 - Ingresos por Intereses");
                    break;
                case "616":
                    parametros.put("RegimenFiscal", "616 - Sin Obligaciones Fiscales");
                    break;
                case "620":
                    parametros.put("RegimenFiscal", "620 - Sociedades Cooperativas de Producción que Optan por Diferir sus Ingresos");
                    break;
                case "621":
                    parametros.put("RegimenFiscal", "621 - Incorporación Fiscal");
                    break;
                case "622":
                    parametros.put("RegimenFiscal", "622 - Actividades Agricolas, Ganaderas, Silvícolas y Pesqueras");
                    break;
                case "623":
                    parametros.put("RegimenFiscal", "623 - Opcional para Grupos de Sociedades");
                    break;
                case "624":
                    parametros.put("RegimenFiscal", "624 - Coordinados");
                    break;
                case "628":
                    parametros.put("RegimenFiscal", "628 - Hidrocarburos");
                    break;
                case "607":
                    parametros.put("RegimenFiscal", "607 - Régimen de Enajenación o Adquisicion de Bienes");
                    break;
                case "629":
                    parametros.put("RegimenFiscal", "629 - De los Regímenes Fiscales Preferentes y de las Empresas Multinacionales");
                    break;
                case "630":
                    parametros.put("RegimenFiscal", "630 - Enajenación de Acciones en Bolsa de Valores");
                    break;
                case "615":
                    parametros.put("RegimenFiscal", "615 - Régimen de los Ingresos por Obtencion de Premios");
                    break;
                default:
                    parametros.put("RegimenFiscal", "-");
                    break;
            }
        }
        if (cfdiId.getMoneda().equals("MXN") || cfdiId.getMoneda().equals("XXX")) {
            parametros.put("tipoCambio", 1);
        } else {
            parametros.put("tipoCambio", cfdiId.getTipoCambio());
        }
        parametros.put("Moneda", cfdiId.getMoneda());
        System.out.println("itemsLineas: " + itemsLineas);
        parametros.put("conceptos", itemsLineas);
        parametros.put("Subtotal", new DecimalFormat("0.00").format(Double.parseDouble(calcularSubtotal())));
        parametros.put("Total", new DecimalFormat("0.00").format(calcularTotal()));
        parametros.put("ImpuestosRetenidos", new DecimalFormat("0.00").format(Double.parseDouble(retenidosIEPS()) + Double.parseDouble(retenidosISR()) + Double.parseDouble(retenidosIva())));
        parametros.put("ImpuestosTrasladados", new DecimalFormat("0.00").format(Double.parseDouble(trasladadoIEPS()) + Double.parseDouble(trasladadoISR()) + Double.parseDouble(trasladadoIva())));
        parametros.put("totalDescuento", new DecimalFormat("0.00").format(Double.parseDouble(calcularDescuento())));
        parametros.put("Logo", imagenLogo);
        parametros.put("QR", imagenqr);

        if (cfdiId.getFormaPago() != null) {
            switch (cfdiId.getFormaPago()) {

                case "01":
                    parametros.put("FormaPago", "01 - Efectivo");
                    break;
                case "02":
                    parametros.put("FormaPago", "02 - Cheque Nominativo");
                    break;
                case "03":
                    parametros.put("FormaPago", "03 - Trasnferencia electrónica de fondos");
                    break;
                case "04":
                    parametros.put("FormaPago", "04 - Tarjeta de crédito");
                    break;
                case "05":
                    parametros.put("FormaPago", "05 - Monedero electrónico");
                    break;
                case "06":
                    parametros.put("FormaPago", "Dinero electrónico");
                    break;
                case "08":
                    parametros.put("FormaPago", "Vales de despensa");
                    break;
                case "12":
                    parametros.put("FormaPago", "Dación en pago");
                    break;
                case "13":
                    parametros.put("FormaPago", "Pago por subrogación");
                    break;
                case "14":
                    parametros.put("FormaPago", "14 - Pago por consignación");
                    break;
                case "15":
                    parametros.put("FormaPago", "15 - Condonación");
                    break;
                case "17":
                    parametros.put("FormaPago", "Compensación");
                    break;
                case "23":
                    parametros.put("FormaPago", "Novación");
                    break;
                case "24":
                    parametros.put("FormaPago", "Confución");
                    break;
                case "25":
                    parametros.put("FormaPago", "Remisión de deuda");
                    break;
                case "26":
                    parametros.put("FormaPago", "Prescripción");
                    break;
                case "27":
                    parametros.put("FormaPago", "A satisfacción del acreedor");
                    break;
                case "28":
                    parametros.put("FormaPago", "Trajeta de débito");
                    break;
                case "29":
                    parametros.put("FormaPago", "Tarjeta de servicios");
                    break;
                case "30":
                    parametros.put("FormaPago", "Aplicación de anticipos");
                    break;
                case "31":
                    parametros.put("FormaPago", "Intermediario de pagos");
                    break;
                case "99":
                    parametros.put("FormaPago", "Por definir");
                    break;
                default:
                    parametros.put("FormaPago", " ");
                    break;
            }
        }
        if (cfdiId.getMetodoPago() != null) {
            if (cfdiId.getMetodoPago().equals("PUE")) {
                parametros.put("MetodoPago", "PUE - Pago en una sola exhibición");
            }
            if (cfdiId.getMetodoPago().equals("PPD")) {
                parametros.put("MetodoPago", "PPD - Pago en parcialidades o diferido");
            }
        }
        //String selloSAT, String noCertSAT, String fechaTimbrado, String Uuid, String selloCFDI, byte[] codigoQR, String cadenaOrig
        parametros.put("sello_CFDI", selloCFDI);
        parametros.put("sello_SAT", selloSAT);
        parametros.put("cadenaOriginal", cadenaOrig);
        parametros.put("noCertificadoSAT", noCertSAT);
        parametros.put("fechaCertificacion", fechaTimbrado);
        parametros.put("UUID", Uuid);

        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Reports/FacturaPrevia2.jasper"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JREmptyDataSource());
        //Imprime PDF
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; fileName=Factura_" + cfdiId.getRfcEmisor() + new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss").format(cfdiId.getFechaExpedicion()) + ".pdf");

        ServletOutputStream stream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
        System.out.println("Se realizo la descarga del PDF");
        byte[] facturaPDF = JasperExportManager.exportReportToPdf(jasperPrint);
        archivo.setArchivoPdf(facturaPDF);//*/
        stream.flush();
        stream.close();
        //*/
        FacesContext.getCurrentInstance().responseComplete();
        System.out.println("PDF guardado");
    }

    public String calcularDescuento() {
        Double descuento = 0.0;
        if (itemsLineas != null) {
            for (int i = 0; i < itemsLineas.size(); i++) {
                if (itemsLineas.get(i).getDescuento() != null) {
                    descuento += itemsLineas.get(i).getDescuento();
                }
            }
        }
        return new DecimalFormat("0.00").format(descuento);
    }

    public void descargaXml(RnGcCfdisTbl cfdisId) {
        System.out.println("cfdisId: " + cfdisId);
        if (cfdisId != null) {
            try {
                archivo = new RnGcArchivosTbl();
                archivo = archivosFacade.obtenerArchivo(cfdisId);
                byte[] xml = archivo.getArchivoXml();
                InputStream streamXml = new ByteArrayInputStream(xml);
                downLoadFile = new DefaultStreamedContent(streamXml, "document/xml", cfdisId.getUuid().concat(".xml"));
                JsfUtil.addErrorMessage("Archivo XML descargado");
                System.out.println("Archivo: " + downLoadFile.getName());
                archivo = new RnGcArchivosTbl();
            } catch (NullPointerException ex) {
                JsfUtil.addErrorMessage("Error al intentar descargar archivo XML. No se encontro archivo.");
                System.out.println("Error al descargar XML: " + ex);
                archivo = new RnGcArchivosTbl();
            }
        }
    }

    public void descargaPdf(RnGcCfdisTbl cfdisId) {
        System.out.println("cfdisId: " + cfdisId);
        if (cfdisId != null) {
            try {
                archivo = archivosFacade.obtenerArchivo(cfdisId);
                byte[] pdf = archivo.getArchivoPdf();
                InputStream streamPdf = new ByteArrayInputStream(pdf);
                downLoadFile = new DefaultStreamedContent(streamPdf, "document/pdf", cfdisId.getSerie() + "-" + cfdisId.getFolio() + "-" + cfdisId.getNombreReceptor() +".pdf");
                JsfUtil.addErrorMessage("Archivo PDF descargado");
                System.out.println("Archivo: " + downLoadFile.getName());
                archivo = new RnGcArchivosTbl();
            } catch (NullPointerException ex) {
                JsfUtil.addErrorMessage("Error al intentar descargar archivo PDF. No se encontro archivo.");
                System.out.println("Error al descargar PDF: " + ex);
                archivo = new RnGcArchivosTbl();
            }
        }
    }
    
    
     public byte[] obtenerImagen() {
        RnGcUsuariosTbl usuarioId = usuariosFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        RnGcImagenesTbl imagenId = imagenesFacade.obtenerImagenPorRFC(usuarioId.getRfc());
        if (imagenId == null) {
            imagenId = imagenesFacade.obtenerImagenPorRFC("ADMINISTRADOR");
        }
        System.out.println("Imagen: " + imagenId.getNombreImagen() + imagenId.getRfc());
        return imagenId.getFoto();
    }
    
    public void descargaCancelado(RnGcCfdisTbl cfdisId) {
    try {
            System.out.println("ENTRO ACUSE CANCELACION");
            FileOutputStream fos = new FileOutputStream(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/responsivo.png")));
            fos.write(obtenerImagen());
            fos.close();
            String imagenLogo = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getRealPath("/resources/images/responsivo.png");//*/
            String imagenqr = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getRealPath("/resources/images/qr.png");
            //Crea el Map para setear los valores de la factura
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("RFC_Emisor", cfdisId.getRfcEmisor());
            parametros.put("Nombre_Emisor", cfdisId.getNombreEmisor());
            parametros.put("RFC_Receptor", cfdisId.getRfcReceptor());
            parametros.put("Nombre_Receptor", cfdisId.getNombreReceptor());
            parametros.put("Uso_CFDI", cfdisId.getUsoCfdi());
            parametros.put("Folio_Fiscal", cfdisId.getFolio());
            parametros.put("NoSerie_CSD", cfdisId.getSerie());
            parametros.put("CodigoPostal", cfdisId.getLugarExpedicion());
            parametros.put("FechaHora_Emision", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(cfdisId.getFechaExpedicion()));
            parametros.put("FechaCancelacion", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(cfdisId.getUltimaFechaActualizacion()));
            parametros.put("QR", imagenqr);
            parametros.put("Logo", imagenLogo);
            parametros.put("Uuid", cfdisId.getUuid());

            if (cfdisId.getTipoComprobante() != null) {
                switch (cfdisId.getTipoComprobante()) {
                    case "I":
                        parametros.put("EfectoComprobante", "I - Ingreso");
                        break;
                    case "E":
                        parametros.put("EfectoComprobante", "E - Egreso");
                        break;
                    case "T":
                        parametros.put("EfectoComprobante", "T - Traslado");
                        break;
                    case "P":
                        parametros.put("EfectoComprobante", "P - Pago");
                        break;
                }
            }
            List<RnGcCatalogosusosTbl> listaUsos = usosFacade.findAll();
            if (cfdisId.getUsoCfdi() != null) {
                for(RnGcCatalogosusosTbl us : listaUsos){
                    if(cfdisId.getUsoCfdi().equals(us.getCUsoCFDI()))
                        parametros.put("Uso_CFDI", us.getCUsoCFDI() + " - " + us.getDescripcion());
                }
            }
            List<RnGcRegimenfiscalTbl> listaRegimen = regimenFacade.findAll();
            if (cfdisId.getClaveRegimenFiscal() != null) {
                for(RnGcRegimenfiscalTbl reg : listaRegimen){
                    if(cfdisId.getClaveRegimenFiscal().equals(String.valueOf(reg.getClaveRegimenFiscal())))
                        parametros.put("RegimenFiscal", reg.getClaveRegimenFiscal() + " - " + reg.getDescripcion());
                }
            }
            parametros.put("Moneda", cfdisId.getMoneda());
            if (cfdisId.getMoneda() != null) {
                if (cfdisId.getMoneda().equals("MXN") || cfdisId.getMoneda().equals("XXX")) {
                    parametros.put("tipoCambio", 1);
                } else {
                    parametros.put("tipoCambio", cfdisId.getTipoCambio());
                }
            }
            
            if (cfdisId.getFormaPago() != null) {
                switch (cfdisId.getFormaPago()) {

                    case "01":
                        parametros.put("FormaPago", "01 - Efectivo");
                        break;
                    case "02":
                        parametros.put("FormaPago", "02 - Cheque Nominativo");
                        break;
                    case "03":
                        parametros.put("FormaPago", "03 - Transferencia electrónica de fondos");
                        break;
                    case "04":
                        parametros.put("FormaPago", "04 - Tarjeta de crédito");
                        break;
                    case "05":
                        parametros.put("FormaPago", "05 - Monedero electrónico");
                        break;
                    case "06":
                        parametros.put("FormaPago", "06 - Dinero electrónico");
                        break;
                    case "08":
                        parametros.put("FormaPago", "08 - Vales de despensa");
                        break;
                    case "12":
                        parametros.put("FormaPago", "12 - Dación en pago");
                        break;
                    case "13":
                        parametros.put("FormaPago", "13 - Pago por subrogación");
                        break;
                    case "14":
                        parametros.put("FormaPago", "14 - Pago por consignación");
                        break;
                    case "15":
                        parametros.put("FormaPago", "15 - Condonación");
                        break;
                    case "17":
                        parametros.put("FormaPago", "17 - Compensación");
                        break;
                    case "23":
                        parametros.put("FormaPago", "23 - Novación");
                        break;
                    case "24":
                        parametros.put("FormaPago", "24 - Confución");
                        break;
                    case "25":
                        parametros.put("FormaPago", "25 - Remisión de deuda");
                        break;
                    case "26":
                        parametros.put("FormaPago", "26 - Prescripción");
                        break;
                    case "27":
                        parametros.put("FormaPago", "27 - A satisfacción del acreedor");
                        break;
                    case "28":
                        parametros.put("FormaPago", "28 - Trajeta de débito");
                        break;
                    case "29":
                        parametros.put("FormaPago", "29 - Tarjeta de servicios");
                        break;
                    case "30":
                        parametros.put("FormaPago", "30 - Aplicación de anticipos");
                        break;
                    case "31":
                        parametros.put("FormaPago", "31 - Intermediario de pagos");
                        break;
                    case "99":
                        parametros.put("FormaPago", "99 - Por definir");
                        break;
                    default:
                        parametros.put("FormaPago", " ");
                        break;
                }
            }

            if (cfdisId.getMetodoPago() != null) {
                if (cfdisId.getMetodoPago().equals("PUE")) {
                    parametros.put("MetodoPago", "PUE - Pago en una sola exhibición");
                }
                if (cfdisId.getMetodoPago().equals("PPD")) {
                    parametros.put("MetodoPago", "PPD - Pago en parcialidades o diferido");
                }
            }
            if (cfdisId.getTexto()!= null && !cfdisId.getTexto().isEmpty()) {
                parametros.put("texto", cfdisId.getTexto());
            }
            parametros.put("importeLetra", cfdisId.getImporteLetra());
            System.out.println("llego hasta aqui");
            // Antes de crear el archivo PDF
            System.out.println("Antes de crear el archivo PDF");

            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Reports/AcuseCancelacion.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JREmptyDataSource());

            // Después de crear el archivo PDF
            System.out.println("Después de crear el archivo PDF");

            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);
            InputStream streamPdf = new ByteArrayInputStream(pdf);

            // Antes de establecer la DefaultStreamedContent
            System.out.println("Antes de establecer la DefaultStreamedContent");

            downLoadFileC = new DefaultStreamedContent(streamPdf, "document/pdf", "AcuseCancelacion-" +cfdisId.getSerie() + "-" + cfdisId.getFolio() + ".pdf");

            // Después de establecer la DefaultStreamedContent
            System.out.println("Después de establecer la DefaultStreamedContent");
    }  catch (Exception ex) {
    System.out.println("Ocurrió un error al descargar el Acuse de Cancelación");
    ex.printStackTrace(); // Agrega esta línea para imprimir la traza de la excepción
    JsfUtil.addErrorMessage("Ocurrió un error al descargar el Acuse de Cancelación");
}

}

    public String importeLetra() {
        NumeroALetra numLetra = new NumeroALetra();
        String importeLetra = String.valueOf(new DecimalFormat("0.00").format(calcularTotal()));
        cfdiId.setImporteLetra(numLetra.Convertir(importeLetra, false));
        return numLetra.Convertir(importeLetra, false);
    }

    public void enviarCorreo(RnGcPersonasTbl persona, RnGcArchivosTbl archivoId, RnGcCfdisTbl cfdiId) {
        final String username = "emmanuel@eefn.com.mx";
        final String password = "f1&jbV94";
        Properties props = new Properties();
        props.put("mail.smtp.host", "eefn.com.mx");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "*");
        System.out.println("props: " + props);
        //Session session = Session.getDefaultInstance(props, null);
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        MimeMessage message = new MimeMessage(session);
        System.out.println("Persona E-mails: " + persona.getEmail() + " | " + persona.getEmail2());
        try {
            if (persona.getEmail().contains("@") && persona.getEmail() != null && persona.getEmail2() != "-") {
                List<String> correos = new ArrayList<>();
                correos.add(persona.getEmail());
                if (persona.getEmail2().contains("@") && persona.getEmail2() != null && persona.getEmail2() != "-") {
                    correos.add(persona.getEmail2());
                }
                InternetAddress[] destinatarios = new InternetAddress[correos.size()];
                for (int i = 0; i < correos.size(); i++) {
                    destinatarios[i] = new InternetAddress(correos.get(i));
                }
                session.getProperties().put("mail.smtp.strattls.enable", "true");
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setText("Factura timbrada correctamente");
                //FileOutputStream fos = new FileOutputStream(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/qr.png")));
                FileOutputStream fos = new FileOutputStream(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/archivoPdf.pdf")));
                fos.write(archivoId.getArchivoPdf());
                fos.close();
                MimeBodyPart mimeArchivoPdf = new MimeBodyPart();
                mimeArchivoPdf.attachFile(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/archivoPdf.pdf")));
                MimeBodyPart mimeArchivoXml = new MimeBodyPart();
                mimeArchivoXml.attachFile(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/xmlTimbrado.xml")));

                MimeMultipart multipart = new MimeMultipart();
                multipart.addBodyPart(mimeBodyPart);
                multipart.addBodyPart(mimeArchivoPdf);
                multipart.addBodyPart(mimeArchivoXml);
                message.setFrom(new InternetAddress(username, cfdiId.getNombreEmisor()));
                message.setSubject(cfdiId.getNombreEmisor());
                message.addRecipients(Message.RecipientType.TO, destinatarios);
                message.setContent(multipart);
                //Enviar Mensaje
                Transport transport = session.getTransport("smtp");
                transport.connect(username, password);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                System.out.println("Correo enviado");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Archivos enviados al correo " + persona.getEmail()));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("El correo " + persona.getEmail() + " no es válido"));
            }
        } catch (MessagingException | UnsupportedEncodingException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error al enviar correo"));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error al enviar mensaje"));
        }
    }

    public List<RnGcCfdisTbl> obtenerEmitidos() {
        try {
            usuario = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            if (usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")) {
                emitidos = cfdisFacade.findAll();
            } else {
                emitidos = cfdisFacade.obtenerEmitidos(usuario.getRfc());
            }
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }
        return emitidos;
    }

    public List<RnGcCfdisTbl> obtenerRecibidos() {
        try {
            usuario = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            if (usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")) {
                recibidos = cfdisFacade.findAll();
            } else {
                recibidos = cfdisFacade.obtenerRecibidos(usuario.getRfc());
            }
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }
        return recibidos;
    }

    public void cancelarFactura(RnGcCfdisTbl cfdi) throws Exception {
        Sefactura sf = new Sefactura("http://www.jonima.com.mx:3014", "VICA840114RZ4", "VICA840114RZ4");
        SolCancelacion cancelacion = new SolCancelacion();
        System.out.println("UUID: " + cfdi.getUuid());
        System.out.println("Key: " + Base64.getEncoder().encodeToString(cfdi.getCertificados_Id().getLlavePrivada()));
        System.out.println("Certificado: " + Base64.getEncoder().encodeToString(cfdi.getCertificados_Id().getCertificadoSelloDigital()));
        System.out.println("Pass: " + Base64.getEncoder().encodeToString(cfdi.getCertificados_Id().getContraseniaLlavePrivada().getBytes()));
        String respuestaCancelar = sf.cancela(cfdi.getUuid(), Base64.getEncoder().encodeToString(cfdi.getCertificados_Id().getLlavePrivada()),
                Base64.getEncoder().encodeToString(cfdi.getCertificados_Id().getCertificadoSelloDigital()),
                Base64.getEncoder().encodeToString(cfdi.getCertificados_Id().getContraseniaLlavePrivada().getBytes()));
    }

    public void buscarCfdi() {
        if (cfdiUuid != null) {
            cfdi2 = cfdiUuid;
        }
        cfdiUuid = null;
    }

    public Double calcularsaldoInsoluto(Double saldoPagado, Double saldoAnterior) {
        monto = cfdiId.getMontoPago();
        Double cantidadPagada = 0.0;
        if (saldoPagado > 0.0) {
            System.out.println(saldoAnterior + " | " + saldoPagado);
            montoTotal += saldoPagado;
            if (montoTotal <= monto) {
                saldoIinsoluto = saldoAnterior - saldoPagado;
                if (cfdi2.getSaldoPagar() != null) {
                    cantidadPagada = cfdi2.getSaldoPagar() + saldoPagar;
                }
                cfdi2.setNumeroParcialidad(parcialidad);
                cfdi2.setSaldoPagar(cantidadPagada);
                cfdi2.setCantidadPagada(saldoPagar);
            }
        }
        System.out.println("Insoluto: " + cfdi2.getSaldoInsoluto());
        return saldoIinsoluto;
    }

    public void eliminarRelacionados() {
        System.out.println("listaCfdisRelacionados: " + listaCfdisRelacionados.size() + " | " + cfdiRelacionado);
        listaCfdisRelacionados.remove(cfdiRelacionado);
        cfdiRelacionado = new RnGcCfdisTbl();
        System.out.println("listaCfdisRelacionados2: " + listaCfdisRelacionados.size() + " | " + cfdiRelacionado);
    }

    public void eliminarDocsRelacionados() {
        System.out.println("listaCfdis: " + listaCfdis.size() + " | " + cfdiUuid);
        listaCfdis.remove(cfdiUuid);
        cfdiUuid = new RnGcCfdisTbl();
        System.out.println("listaCfdis2: " + listaCfdis.size() + " | " + cfdiUuid);
    }

    public void agregarRelacionados() {
        System.out.println("agregarRelacionados");
        if (cfdiRelacionado != null) {
            listaCfdisRelacionados.add(cfdiRelacionado);
            cfdiRelacionado = new RnGcCfdisTbl();
        } else {
            JsfUtil.addErrorMessage("No se pudo agregar el CFDI");
        }
    }

    public boolean crearFirmas() {
        boolean valor = false;
        if (firmas.getCargo1() != null && firmas.getNombre1() != null) {
            firmas.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
            firmas.setFechaCreacion(new Date());
            firmas.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
            firmas.setUltimaFechaActualizacion(new Date());
            valor = true;
        }
        if (firmas.getCargo2() != null && firmas.getNombre2() != null) {
            firmas.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
            firmas.setFechaCreacion(new Date());
            firmas.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
            firmas.setUltimaFechaActualizacion(new Date());
            valor = true;
        }
        if (firmas.getCargo3() != null && firmas.getNombre3() != null) {
            firmas.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
            firmas.setFechaCreacion(new Date());
            firmas.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
            firmas.setUltimaFechaActualizacion(new Date());
            valor = true;
        }
        if (firmas.getCargo4() != null && firmas.getNombre4() != null) {
            firmas.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
            firmas.setFechaCreacion(new Date());
            firmas.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
            firmas.setUltimaFechaActualizacion(new Date());
            valor = true;
        }
        if (firmas.getCargo5() != null && firmas.getNombre5() != null) {
            firmas.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
            firmas.setFechaCreacion(new Date());
            firmas.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
            firmas.setUltimaFechaActualizacion(new Date());
            valor = true;
        }
        System.out.println("Valor: " + valor);
        return valor;
    }

    public void buscarProdServ() {
        if (producServicio2 != null) {
            producServicio = producServicio2;
            selected.setClaveProdServ(producServicio.getClaveProductServ());
            selected.setNoIdentificacion(producServicio.getClaveProductServ());
            selected.setClaveUnidad(producServicio.getClaveUnidad());
            selected.setUnidad(producServicio.getUnidad());
            selected.setDescripcion(producServicio.getDescripcion());
            selected.setValorUnit(producServicio.getValorunitario());
            selected.setTipoImpuesto(producServicio.getTipoImpuesto());
            selected.setImpuesto(producServicio.getImpuesto());
            selected.setTipoFactor(producServicio.getTipofactor());
            selected.setTipoTasa(producServicio.getTipoTasa());
            if (producServicio.getTipoImpuesto2() != null) {
                selected.setTipoImpuesto2(producServicio.getTipoImpuesto2());
                selected.setImpuesto2(producServicio.getImpuesto2());
                selected.setTipoFactor2(producServicio.getTipoFactor2());
                selected.setTipoTasa2(producServicio.getTipoTasa2());
            }
            if (producServicio.getTipoImpuesto3() != null) {
                selected.setTipoImpuesto3(producServicio.getTipoImpuesto3());
                selected.setImpuesto3(producServicio.getImpuesto3());
                selected.setTipoFactor3(producServicio.getTipoFactor3());
                selected.setTipoTasa3(producServicio.getTipoTasa3());
            }
            if (producServicio.getTipoImpuesto4() != null) {
                selected.setTipoImpuesto4(producServicio.getTipoImpuesto4());
                selected.setImpuesto4(producServicio.getImpuesto4());
                selected.setTipoFactor4(producServicio.getTipoFactor4());
                selected.setTipoTasa4(producServicio.getTipoTasa4());
            }
        }
        producServicio2 = new RnGcProductserviciosTbl();
    }

    public List<RnGcCfdisLineasTbl> agregarLinea() {
        System.out.println("CfdiId: " + cfdiId);
        selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        selected.setFechaCreacion(new Date());
        selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        selected.setUltimaFechaActualizacion(new Date());
        selected.setCfdisId(cfdiId);
        itemsLineas.add(selected);
        getFacade().edit(selected);
        selected = new RnGcCfdisLineasTbl();
        return itemsLineas;
    }

    public List<RnGcCfdisTbl> obtenerDocumentosRelacionados(RnGcCfdisTbl cfdiId) {
        listaDocumentosRelacionados = documentosRelacionadosFacade.obtenerDocumentos(cfdiId);
        listaCfdisRelacionados = new ArrayList<>();
        if (listaDocumentosRelacionados.size() > 0) {
            for (int i = 0; i < listaDocumentosRelacionados.size(); i++) {
                RnGcCfdisTbl cfdi1 = cfdisFacade.obtenerPorUuid(listaDocumentosRelacionados.get(i).getIdDocumento());
                listaCfdisRelacionados.add(cfdi1);
            }
        }
        return listaCfdisRelacionados;
    }

    public List<RnGcCfdisTbl> obtenerPagosRelacionados(RnGcCfdisTbl cfdiId) {
        listaPagosRelacionados = pagosRelacionadosFacade.obtenerPagosRelacionado(cfdiId);
        listaCfdis = new ArrayList<>();
        if (listaPagosRelacionados.size() > 0) {
            for (int i = 0; i < listaPagosRelacionados.size(); i++) {
                RnGcCfdisTbl cfdi1 = cfdisFacade.obtenerPorUuid(listaPagosRelacionados.get(i).getIdDocumento());
                listaCfdis.add(cfdi1);
            }
        }
        return listaCfdis;
    }

    public void agregarFactura() {
        System.out.println("agregarFactura");
        if (cfdi2 != null) {
            System.out.println("calculo: " + montoTotal + " | " + monto);
            if (montoTotal <= monto) {
                System.out.println("calculo2: " + montoTotal + " | " + monto);
                System.out.println(cfdi2.getSaldoPagar() + " | " + cfdi2.getSaldoPagado() + " | " + cfdi2.getSaldoInsoluto() + " | " + cfdi2.getCantidadPagada());
                cfdi2.setSaldoInsoluto(cfdi2.getSaldoPagado() - cfdi2.getCantidadPagada());
                listaCfdis.add(cfdi2);
            } else {
                montoTotal -= saldoPagar;
                System.out.println("montoTotal: " + montoTotal);
                JsfUtil.addErrorMessage("No se agrego la factura se ha excedido el monto de pago");
            }
        } else {
            JsfUtil.addErrorMessage("No se agrego la factura");
        }
        saldoPagar = 0.0;
        parcialidad = 0;
        saldoIinsoluto = 0.0;
        cfdi2 = new RnGcCfdisTbl();
        System.out.println("Fin agregarFactura");
    }
    
    public void actualizarEstatus() throws IOException, Exception{
        File cert = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/certificado.cer"));
        FileUtils.writeByteArrayToFile(cert, certificados.getCertificadoSelloDigital());

        File key = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/llavePrivada.key"));
        FileUtils.writeByteArrayToFile(key, certificados.getLlavePrivada());
        //Sefactura sf = new Sefactura("http://www.jonima.com.mx:3014", "VICA840114RZ4", "VICA840114RZ4"); //Desarrollo
        Sefactura sf = new Sefactura("https://www.sefactura.com.mx", "AFC060520V16", "AFC060520V16"); //Produccion
        
        ResultadoCancelacion resCancel = sf.consultaCancelacion(cfdiId.getRfcEmisor(), cfdiId.getRfcReceptor(), cfdiId.getUuid(), cfdiId.getImporte(), motivo, "AFC060520V16", "AFC060520V16");
        String respuesta = resCancel.getEstado();
        //String respuesta = sf.cancelaAcuse(cfdiId.getUuid(), key.getPath(), cert.getPath(), certificados.getContraseniaLlavePrivada());
        System.out.println("respuesta -: codigo estatus: " + resCancel.getCodigoEstatus() + " | estatus: " + resCancel.getEstado() + " | estatusCancel: " + resCancel.getEstatusCancelacion());
        if(respuesta.contains("Cancelado")){
            System.out.println("CFDI " + cfdiId.getUuid() + " actualizado");
            cfdiId.setRespuestaTimbrado("UUID con resultado: 801 - En proceso - UUID CANCELADO CORRECTAMENTE en el UUID: " + cfdiId.getUuid());
            cfdiId.setEstatus("Cancelado");
            cfdiId.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
            cfdiId.setUltimaFechaActualizacion(new Date());
            cfdisFacade.edit(cfdiId);
            JsfUtil.addSuccessMessage("Estatus: " + cfdiId.getUuid() + " se encuentra cancelado");
        }else{
            JsfUtil.addSuccessMessage("Estatus: " + respuesta);
        }
    }

    public void cancelarComplemento() {
        try {
            System.out.println("DatosFactura: " + cfdiId);
            if (cfdiId != null) {
                System.out.println("tipoComprobante: " + cfdiId.getTipoComprobante());
                if (cfdiId.getTipoComprobante().equals("P")) {
                    usuario = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
                    List<RnGcTimbresTbl> timbres = new ArrayList<>();
                    timbres = timbresFacade.obtenerTimbre("Proveedor", usuario);
                    if (timbres != null && !timbres.isEmpty()) {
                        System.out.println("Timbres: " + timbres.get(0).getTimbresRestantes());

                        File cert = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/certificado.cer"));
                        FileUtils.writeByteArrayToFile(cert, certificados.getCertificadoSelloDigital());

                        File key = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/llavePrivada.key"));
                        FileUtils.writeByteArrayToFile(key, certificados.getLlavePrivada());

                        //Sefactura sf = new Sefactura("http://www.jonima.com.mx:3014", "VICA840114RZ4", "VICA840114RZ4"); //Desarrollo
                        Sefactura sf = new Sefactura("https://www.sefactura.com.mx", "AFC060520V16", "AFC060520V16"); //Produccion
                        
                        System.out.println("dato 1 :" + cfdiId.getUuid());
                        System.out.println("dato 2: " + motivo );
                        String respuesta;
                        ////respuesta = "UUID CANCELADO CORRECTAMENTE"; ////////emmanuel
                        if(motivo.equals(01)){
                            System.out.println("|"+cfdiId.getUuid()+"|"+ motivo+"|" + uuidRelacionado +"|");
                            // Se espera la siguiente estructura en el atributo uuids: |UUID|Motivo|FolioSustitucion|
                        respuesta = sf.cancelacion40(cfdiId.getUuid(), motivo, uuidRelacionado, key.getPath(), cert.getPath(), certificados.getContraseniaLlavePrivada(),cfdiId.getRfcEmisor(),cfdiId.getRfcReceptor(),cfdiId.getImporte());
                        }else{
                            System.out.println("|"+cfdiId.getUuid()+"|"+motivo+"|"+"|");
                            // Se espera la siguiente estructura en el atributo uuids: |UUID|Motivo||
                            //respuesta = sf.cancela("|"+cfdiId.getUuid()+"|"+motivo+"|"+"|", key.getPath(), cert.getPath(), certificados.getContraseniaLlavePrivada());
                        respuesta = sf.cancelacion40(cfdiId.getUuid(), motivo, "", key.getPath(), cert.getPath(), certificados.getContraseniaLlavePrivada(),cfdiId.getRfcEmisor(),cfdiId.getRfcReceptor(),cfdiId.getImporte());
                        }
                        System.out.println("respuesta: " + respuesta);
                        if (respuesta.contains("UUID CANCELADO CORRECTAMENTE")) {
                            cfdiId.setRespuestaTimbrado(respuesta);
                            cfdiId.setEstatus("Cancelado");
                            cfdiId.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                            cfdiId.setUltimaFechaActualizacion(new Date());
                            cfdisFacade.edit(cfdiId);
                            
                            
                            descargaCancelado2(cfdiId);

                            listaPagosRelacionados = pagosRelacionadosFacade.obtenerPagosRelacionado(cfdiId);
                            if (listaPagosRelacionados != null && !listaPagosRelacionados.isEmpty()) {
                                for (RnGcPagosRelacionadosTbl pagoRelacionado : listaPagosRelacionados) {
                                    RnGcCfdisTbl cfdi = new RnGcCfdisTbl();
                                    cfdi = cfdisFacade.obtenerPorUuid(pagoRelacionado.getIdDocumento());
                                    cfdi.setSaldoPagado(cfdi.getSaldoPagado() - (pagoRelacionado.getImporteSaldoAnterior() - pagoRelacionado.getImporteInsoluto()));
                                    cfdi.setSaldoInsoluto(cfdi.getSaldoInsoluto() + (pagoRelacionado.getImporteSaldoAnterior() - pagoRelacionado.getImporteInsoluto()));
                                    cfdi.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                                    cfdi.setUltimaFechaActualizacion(new Date());
                                    cfdi = cfdisFacade.refreshFromDB(cfdi);
                                }
                            } else {
                                System.out.println("Else de lista pago relacionado 1");
                                cfdiId = new RnGcCfdisTbl();
                                certificados = new RnGcCertificadosTbl();
                            }
                            timbres.get(0).setTimbresRestantes(timbres.get(0).getTimbresRestantes() - 1);
                            timbres.get(0).setTimbresUsados(timbres.get(0).getTimbresUsados() + 1);
                            timbres.get(0).setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                            timbres.get(0).setUltimaFechaActualizacion(new Date());
                            timbresFacade.edit(timbres.get(0));
                            System.out.println("El CFDI con el UUID " + cfdiId.getUuid() + " fue cancelado correctamente");
                            JsfUtil.addSuccessMessage("El CFDI con el UUID " + cfdiId.getUuid() + " fue cancelado correctamente");
                            
                            cfdiId = new RnGcCfdisTbl();
                            certificados = new RnGcCertificadosTbl();
                        } else if (respuesta.contains("En proceso")) {
                            cfdiId.setRespuestaTimbrado(respuesta);
                            cfdiId.setEstatus("En proceso");
                            cfdiId.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                            cfdiId.setUltimaFechaActualizacion(new Date());
                            cfdisFacade.edit(cfdiId);

                            listaPagosRelacionados = pagosRelacionadosFacade.obtenerPagosRelacionado(cfdiId);
                            if (listaPagosRelacionados != null && !listaPagosRelacionados.isEmpty()) {
                                for (RnGcPagosRelacionadosTbl pagoRelacionado : listaPagosRelacionados) {
                                    RnGcCfdisTbl cfdi = new RnGcCfdisTbl();
                                    cfdi = cfdisFacade.obtenerPorUuid(pagoRelacionado.getIdDocumento());
                                    cfdi.setSaldoPagado(cfdi.getSaldoPagado() - (pagoRelacionado.getImporteSaldoAnterior() - pagoRelacionado.getImporteInsoluto()));
                                    cfdi.setSaldoInsoluto(cfdi.getSaldoInsoluto() + (pagoRelacionado.getImporteSaldoAnterior() - pagoRelacionado.getImporteInsoluto()));
                                    cfdi.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                                    cfdi.setUltimaFechaActualizacion(new Date());
                                    cfdi = cfdisFacade.refreshFromDB(cfdi);
                                }
                            } else {
                                System.out.println("Else de lista pago relacionado - en proceso -");
                                cfdiId = new RnGcCfdisTbl();
                                certificados = new RnGcCertificadosTbl();
                            }
                            timbres.get(0).setTimbresRestantes(timbres.get(0).getTimbresRestantes() - 1);
                            timbres.get(0).setTimbresUsados(timbres.get(0).getTimbresUsados() + 1);
                            timbres.get(0).setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                            timbres.get(0).setUltimaFechaActualizacion(new Date());
                            timbresFacade.edit(timbres.get(0));
                            System.out.println("El CFDI con el UUID " + cfdiId.getUuid() + " esta en proceso de cancelación");
                            JsfUtil.addSuccessMessage("El CFDI con el UUID " + cfdiId.getUuid() + " esta en proceso de cancelación");
                            cfdiId = new RnGcCfdisTbl();
                            certificados = new RnGcCertificadosTbl();
                        }else{
                            System.out.println("El CFDI con el UUID " + cfdiId.getUuid() + " no se pudo cancelar");
                            JsfUtil.addErrorMessage("El CFDI con el UUID " + cfdiId.getUuid() + " no se pudo cancelar");
                            cfdiId = new RnGcCfdisTbl();
                            certificados = new RnGcCertificadosTbl();
                        }
                    } else {
                        System.out.println("No tienes timbres restantes");
                        JsfUtil.addErrorMessage("No tienes timbres restantes para realizar la operación");
                        cfdiId = new RnGcCfdisTbl();
                        certificados = new RnGcCertificadosTbl();
                    }
                } else {
                    usuario = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
                    List<RnGcTimbresTbl> timbres = new ArrayList<>();
                    timbres = timbresFacade.obtenerTimbre("Proveedor", usuario);
                    if (timbres != null && !timbres.isEmpty()) {
                        File cert = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/certificado.cer"));
                        FileUtils.writeByteArrayToFile(cert, certificados.getCertificadoSelloDigital());
                        
                        File key = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/llavePrivada.key"));
                        FileUtils.writeByteArrayToFile(key, certificados.getLlavePrivada());
                        
                        //Sefactura sf = new Sefactura("http://www.jonima.com.mx:3014", "VICA840114RZ4", "VICA840114RZ4"); //Desarrollo
                        Sefactura sf = new Sefactura("https://www.sefactura.com.mx", "AFC060520V16", "AFC060520V16"); //Produccion

                        System.out.println("dato 1: " + cfdiId.getUuid());
                        System.out.println("dato 2: " + motivo );
                        String respuesta;
 //                       respuesta ="UUID CANCELADO CORRECTAMENTE";
                        if(motivo.equals("01")){
                            System.out.println("--------- Cancelacion40 ---------");
                            System.out.println("--------- con sustitucion ---------");
                            System.out.println("|"+cfdiId.getUuid()+"|"+ motivo+"|" + uuidRelacionado +"|");
                            // Se espera la siguiente estructura en el atributo uuids: |UUID|Motivo|FolioSustitucion|
                        respuesta = sf.cancelacion40(cfdiId.getUuid(), motivo, uuidRelacionado, key.getPath(), cert.getPath(), certificados.getContraseniaLlavePrivada(),cfdiId.getRfcEmisor(),cfdiId.getRfcReceptor(),cfdiId.getImporte());
                        }else{
                            System.out.println("--------- Cancelacion40 ---------");
                            System.out.println("|"+cfdiId.getUuid()+"|"+motivo+"|"+"|");
                            //Se espera la siguiente estructura en el atributo uuids: |UUID|Motivo||
                            //respuesta = sf.cancela("|"+cfdiId.getUuid()+"|"+motivo+"|"+"|", key.getPath(), cert.getPath(), certificados.getContraseniaLlavePrivada());
                        respuesta = sf.cancelacion40(cfdiId.getUuid(), motivo, "", key.getPath(), cert.getPath(), certificados.getContraseniaLlavePrivada(),cfdiId.getRfcEmisor(),cfdiId.getRfcReceptor(),cfdiId.getImporte());
                        }
                        System.out.println("respuesta: " + respuesta);
                        if(respuesta.contains("UUID CANCELADO CORRECTAMENTE")){
                            cfdiId.setRespuestaTimbrado(respuesta);
                            cfdiId.setEstatus("Cancelado");
                            cfdiId.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                            cfdiId.setUltimaFechaActualizacion(new Date());
                            cfdisFacade.edit(cfdiId);

                            timbres.get(0).setTimbresRestantes(timbres.get(0).getTimbresRestantes() - 1);
                            timbres.get(0).setTimbresUsados(timbres.get(0).getTimbresUsados() + 1);
                            timbres.get(0).setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                            timbres.get(0).setUltimaFechaActualizacion(new Date());
                            timbresFacade.edit(timbres.get(0));
                            System.out.println("El CFDI con el UUID " + cfdiId.getUuid() + " fue cancelado correctamente");
                            JsfUtil.addSuccessMessage("El CFDI con el UUID " + cfdiId.getUuid() + " fue cancelado correctamente");
                        }else if(respuesta.contains("En proceso")){
                            cfdiId.setRespuestaTimbrado(respuesta);
                            cfdiId.setEstatus("En proceso");
                            cfdiId.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                            cfdiId.setUltimaFechaActualizacion(new Date());
                            cfdisFacade.edit(cfdiId);

                            timbres.get(0).setTimbresRestantes(timbres.get(0).getTimbresRestantes() - 1);
                            timbres.get(0).setTimbresUsados(timbres.get(0).getTimbresUsados() + 1);
                            timbres.get(0).setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                            timbres.get(0).setUltimaFechaActualizacion(new Date());
                            timbresFacade.edit(timbres.get(0));
                            System.out.println("El CFDI con el UUID " + cfdiId.getUuid() + " esta en proceso de cancelación");
                            JsfUtil.addSuccessMessage("El CFDI con el UUID " + cfdiId.getUuid() + " esta en proceso de cancelación");
                        }else{
                            System.out.println("El CFDI con el UUID " + cfdiId.getUuid() + " no se pudo cancelar");
                            JsfUtil.addErrorMessage("El CFDI con el UUID " + cfdiId.getUuid() + " no se pudo cancelar");
                        }
                        cfdiSustitucion = new RnGcCfdisTbl();
                        cfdiId = new RnGcCfdisTbl();
                        certificados = new RnGcCertificadosTbl();
                    } else {
                        System.out.println("No tienes timbres restantes");
                        JsfUtil.addErrorMessage("No tienes timbres restantes para realizar la operación");
                        cfdiId = new RnGcCfdisTbl();
                        certificados = new RnGcCertificadosTbl();
                    }
                }
            } else {
                System.out.println("No se selecciono CFDI");
                JsfUtil.addErrorMessage("No se selecciono CFDI");
            }
        } catch (Exception ex) {
            System.out.println("Error durante la cancelacion del timbre: " + ex.getLocalizedMessage());
            JsfUtil.addErrorMessage("Error durante la cancelacion del timbre");
            cfdiId = new RnGcCfdisTbl();
            certificados = new RnGcCertificadosTbl();
        }
    }

    public String parseDate(Date fecha1, String formato) {
        String fecha = "";
        fecha = new SimpleDateFormat(formato).format(fecha1);
        return fecha;
    }

    public boolean estadoCFDI(RnGcCfdisTbl cfdi) {
        boolean valor = true;
        if (cfdi != null && cfdi.getRespuestaTimbrado() != null
                && !cfdi.getRespuestaTimbrado().contains("UUID con resultado: 801 - En proceso - UUID CANCELADO CORRECTAMENTE en el UUID: ")
                && !cfdi.getRespuestaTimbrado().contains("3004 - 202 - UUID previamente cancelado en el UUID: ")) {
            valor = false;
        }
        return valor;
    }

    public RnGcPersonasTbl buscarPersona(RnGcCfdisTbl cfdi) {
        System.out.println("cfdi: " + cfdi);
        if (cfdi != null) {
            RnGcPersonasTbl persona = new RnGcPersonasTbl();
            persona = personaFacade.obtenerporRfcCreadoPor(cfdi.getRfcReceptor(), usuarioFirmado.obtenerIdUsuario());
        } else {
            JsfUtil.addErrorMessage("No se ha escogido un CFDI");
        }
        System.out.println("buscarPersona: " + persona);
        return persona;
    }
    
    public void buscarFacturasRFCFecha(){
        cfdiSustitucion = new RnGcCfdisTbl();
        listaCfdisSustitucion = new ArrayList();
        List<RnGcCfdisTbl> lista = cfdisFacade.obtenerXRFCReceptorFecha(cfdiId.getRfcReceptor(), ini, fin);
        if(lista != null && !lista.isEmpty()){
            listaCfdisSustitucion = lista;
        }else
            JsfUtil.addErrorMessage("No se encontraron facturas");
    }
    
    public void limpiarListaSustitucion(){
        listaCfdisSustitucion = new ArrayList();
        uuidRelacionado = cfdiSustitucion.getUuid();
    }



public void descargaCancelado2(RnGcCfdisTbl cfdisId) {
    if (cfdisId != null) {
        try {
            archivo = archivosFacade.obtenerArchivo(cfdisId);
            byte[] pdfBytes = archivo.getArchivoPdf();

            try (ByteArrayOutputStream modifiedPdfStream = new ByteArrayOutputStream()) {
                // Crear un nuevo documento PDF con iText
                PdfWriter writer = new PdfWriter(modifiedPdfStream);
                PdfDocument pdfDocument = new PdfDocument(new PdfReader(new ByteArrayInputStream(pdfBytes)), writer);

                // Obtener la primera página del documento existente
                PdfPage firstPage = pdfDocument.getFirstPage();

                // Configurar la apariencia de la marca de agua
                float fontSize = 90;
                float transparency = 0.8f;
                String watermarkText = "CANCELADO";

                // Configurar fuente y color
                PdfFont font = PdfFontFactory.createFont();
                DeviceRgb redColor = new DeviceRgb(255, 0, 0);  // RGB para rojo

                // Crear un lienzo para agregar la marca de agua en la misma página
                PdfCanvas canvas = new PdfCanvas(firstPage.newContentStreamAfter(), firstPage.getResources(), pdfDocument);
                canvas.setFontAndSize(font, fontSize);

                // Ajustar la transformación de la matriz para poner el texto en diagonal
                float x = 100;
                float y = 400;
                float angle = (float) (Math.PI / 4); // Ángulo en radianes (45 grados)

                // Agregar marca de agua con color rojo en diagonal
                canvas.saveState();
                canvas.setExtGState(new PdfExtGState().setFillOpacity(transparency));
                canvas.setFillColor(redColor);

                // Ajustar la transformación de la matriz para poner el texto en diagonal
                canvas.concatMatrix(Math.cos(angle), Math.sin(angle), -Math.sin(angle), Math.cos(angle), x, y);

                canvas.beginText().setFontAndSize(font, fontSize);
                canvas.showText(watermarkText);
                canvas.endText();

                canvas.restoreState();

                // Cerrar el documento iText
                pdfDocument.close();

                // Crear un nuevo InputStream desde el array de bytes modificado
                InputStream streamPdf = new ByteArrayInputStream(modifiedPdfStream.toByteArray());

                // Crear el archivo descargable
                downLoadFileC = new DefaultStreamedContent(streamPdf, "application/pdf", "archivo_con_marca_de_agua.pdf");

               // JsfUtil.addSuccessMessage("Archivo PDF descargado con marca de agua");
                archivo = new RnGcArchivosTbl();
            } catch (IOException ex) {
                handleException(ex, "Error al agregar marca de agua.");
            }
        } catch (NullPointerException ex) {
            handleException(ex, "No se encontró archivo.");
        } catch (Exception ex) {
            handleException(ex, "Error inesperado al intentar descargar archivo PDF: " + ex.getMessage());
        }
    }
}




// Método para manejar excepciones y agregar mensajes de error
private void handleException(Exception ex, String errorMessage) {
    JsfUtil.addErrorMessage("Error al intentar descargar archivo PDF. " + errorMessage);
    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al descargar PDF", ex);
    archivo = new RnGcArchivosTbl();
}
}