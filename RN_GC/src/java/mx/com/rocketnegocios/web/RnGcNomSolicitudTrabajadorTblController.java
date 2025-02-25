package mx.com.rocketnegocios.web;

import com.sefactura.pac.client.RespuestaTimbrado;
import com.sefactura.pac.client.Sefactura;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import mx.com.rocketnegocios.entities.RnGcNomSolicitudTrabajadorTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcNomSolicitudTrabajadorTblFacade;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
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
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import mx.com.rocketnegocios.beans.RnGcArchivosTblFacade;
import mx.com.rocketnegocios.beans.RnGcCfdisTblFacade;
import mx.com.rocketnegocios.beans.RnGcFolioserieTblFacade;
import mx.com.rocketnegocios.beans.RnGcImagenesTblFacade;
import mx.com.rocketnegocios.beans.RnGcNomEstadosTblFacade;
import mx.com.rocketnegocios.beans.RnGcNomNominasTblFacade;
import mx.com.rocketnegocios.beans.RnGcNomPeriodonominaTblFacade;
import mx.com.rocketnegocios.beans.RnGcNomSolicitudesLineasTblFacade;
import mx.com.rocketnegocios.beans.RnGcNomSolicitudesTblFacade;
import mx.com.rocketnegocios.beans.RnGcNomTipocontratoTblFacade;
import mx.com.rocketnegocios.beans.RnGcNomTrabajadorCfdisTblFacade;
import mx.com.rocketnegocios.beans.RnGcTimbresTblFacade;
import mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade;
import mx.com.rocketnegocios.entities.RnGcArchivosTbl;
import mx.com.rocketnegocios.entities.RnGcCertificadosTbl;
import mx.com.rocketnegocios.entities.RnGcCfdisTbl;
import mx.com.rocketnegocios.entities.RnGcFolioserieTbl;
import mx.com.rocketnegocios.entities.RnGcImagenesTbl;
import mx.com.rocketnegocios.entities.RnGcNomEstadosTbl;
import mx.com.rocketnegocios.entities.RnGcNomNominasTbl;
import mx.com.rocketnegocios.entities.RnGcNomSolicitudesLineasTbl;
import mx.com.rocketnegocios.entities.RnGcNomSolicitudesTbl;
import mx.com.rocketnegocios.entities.RnGcNomTipocontratoTbl;
import mx.com.rocketnegocios.entities.RnGcNomTrabajadorCfdisTbl;
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
import org.apache.poi.openxml4j.util.ZipInputStreamZipEntrySource;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

@Named("rnGcNomSolicitudTrabajadorTblController")
@SessionScoped
public class RnGcNomSolicitudTrabajadorTblController implements Serializable {

    private static final String ORIGINAL
            = "ÁáÉéÍíÓóÚúÑñÜü";
    private static final String REPLACEMENT
            = "AaEeIiOoUuNnUu";

    @EJB
    private mx.com.rocketnegocios.beans.RnGcNomSolicitudTrabajadorTblFacade ejbFacade;

    @EJB
    private RnGcNomSolicitudesTblFacade solicitudFacade;

    @EJB
    private RnGcCfdisTblFacade cfdisFacade;

    @EJB
    private RnGcUsuariosTblFacade usuarioFacade;

    @EJB
    private RnGcNomNominasTblFacade nominaFacade;

    @EJB
    private RnGcNomSolicitudesLineasTblFacade solicitudLineasFacade;

    @EJB
    private RnGcNomTipocontratoTblFacade tipoContratoFacade;

    @EJB
    private RnGcNomEstadosTblFacade estadoFacade;

    @EJB
    private RnGcArchivosTblFacade archivoFacade;

    @EJB
    private RnGcImagenesTblFacade imagenesFacade;

    @EJB
    private RnGcNomTrabajadorCfdisTblFacade trabajadorCfdiFacade;

    @EJB
    private RnGcTimbresTblFacade timbresFacade;

    @EJB
    private RnGcNomPeriodonominaTblFacade periodoNominaFacade;

    @EJB
    private RnGcFolioserieTblFacade folioSerieFacade;

    private List<RnGcNomSolicitudTrabajadorTbl> items = null;
    private RnGcNomSolicitudTrabajadorTbl selected;
    private RnGcNomSolicitudesTbl solicitud;
    private List<RnGcNomSolicitudTrabajadorTbl> listaSolicitudesTrabajador;
    private List<RnGcNomSolicitudTrabajadorTbl> seleccionados;
    private RnGcCfdisTbl cfdisId;
    private RnGcCfdisTbl cfdisId2;
    private RnGcUsuariosTbl usuario;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private RnGcNomNominasTbl nomina;
    private List<RnGcNomSolicitudesLineasTbl> listaPercepciones;
    private List<RnGcNomSolicitudesLineasTbl> listaDeducciones;
    private RnGcNomTipocontratoTbl tipoContrato;
    private RnGcNomEstadosTbl estado;
    private RnGcArchivosTbl archivo = new RnGcArchivosTbl();
    private RnGcNomTrabajadorCfdisTbl trabajadorCfdi;
    private List<RnGcTimbresTbl> timbre;
    private StreamedContent downLoadFile;
    private List<RnGcFolioserieTbl> folioSerie;

    public RnGcNomSolicitudTrabajadorTblController() {
    }

    public RnGcNomSolicitudTrabajadorTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcNomSolicitudTrabajadorTbl selected) {
        this.selected = selected;
    }

    public RnGcCfdisTbl getCfdisId() {
        if (cfdisId == null) {
            this.cfdisId = new RnGcCfdisTbl();
        }
        return cfdisId;
    }

    public void setCfdisId(RnGcCfdisTbl cfdisId) {
        this.cfdisId = cfdisId;
    }

    public RnGcCfdisTbl getCfdisId2() {
        if (cfdisId2 == null) {
            this.cfdisId2 = new RnGcCfdisTbl();
        }
        return cfdisId2;
    }

    public void setCfdisId2(RnGcCfdisTbl cfdisId2) {
        this.cfdisId2 = cfdisId2;
    }

    public StreamedContent getDownLoadFile() {
        return downLoadFile;
    }

    public void setDownLoadFile(StreamedContent downLoadFile) {
        this.downLoadFile = downLoadFile;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public List<RnGcNomSolicitudTrabajadorTbl> getSeleccionados() {
        return seleccionados;
    }

    public void setSeleccionados(List<RnGcNomSolicitudTrabajadorTbl> seleccionados) {
        this.seleccionados = seleccionados;
    }

    private RnGcNomSolicitudTrabajadorTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcNomSolicitudTrabajadorTbl prepareCreate() {
        selected = new RnGcNomSolicitudTrabajadorTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcNomSolicitudTrabajadorTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcNomSolicitudTrabajadorTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcNomSolicitudTrabajadorTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcNomSolicitudTrabajadorTbl> getItems() {
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

    public RnGcNomSolicitudTrabajadorTbl getRnGcNomSolicitudTrabajadorTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcNomSolicitudTrabajadorTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcNomSolicitudTrabajadorTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcNomSolicitudTrabajadorTbl.class)
    public static class RnGcNomSolicitudTrabajadorTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcNomSolicitudTrabajadorTblController controller = (RnGcNomSolicitudTrabajadorTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcNomSolicitudTrabajadorTblController");
            return controller.getRnGcNomSolicitudTrabajadorTbl(getKey(value));
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
            if (object instanceof RnGcNomSolicitudTrabajadorTbl) {
                RnGcNomSolicitudTrabajadorTbl o = (RnGcNomSolicitudTrabajadorTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcNomSolicitudTrabajadorTbl.class.getName()});
                return null;
            }
        }
    }

    public List<RnGcNomSolicitudTrabajadorTbl> getListaSolicitudesTrabajador() {
        return listaSolicitudesTrabajador;
    }

    public void setListaSolicitudesTrabajador(List<RnGcNomSolicitudTrabajadorTbl> listaSolicitudesTrabajador) {
        this.listaSolicitudesTrabajador = listaSolicitudesTrabajador;
    }

    public void obtenerSolicitudesTrabajador(RnGcNomNominasTbl nominaId) {
        solicitud = new RnGcNomSolicitudesTbl();
        trabajadorCfdi = new RnGcNomTrabajadorCfdisTbl();
        cfdisId = new RnGcCfdisTbl();
        cfdisId2 = new RnGcCfdisTbl();
        archivo = new RnGcArchivosTbl();
        timbre = new ArrayList<>();
        folioSerie = new ArrayList<>();
        solicitud = solicitudFacade.obtenerXNomina(nominaId.getId());
        listaSolicitudesTrabajador = new ArrayList<>();
        listaSolicitudesTrabajador = getFacade().obtenerXSolicitud(solicitud);
        cfdisId.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        cfdisId.setFechaCreacion(new Date());
        cfdisId.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        cfdisId.setUltimaFechaActualizacion(new Date());
        cfdisId.setTipoComprobante("N");
        cfdisId.setUsoCfdi("P01");
        cfdisId.setMoneda("MXN");
        cfdisId.setFormaPago("99");
        cfdisId.setMetodoPago("PUE");
        usuario = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        cfdisId.setNombreEmisor(usuario.getNombreCompleto());
        cfdisId.setRfcEmisor(usuario.getRfc());
        archivo.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        archivo.setFechaCreacion(new Date());
        archivo.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        archivo.setUltimaFechaActualizacion(new Date());
        trabajadorCfdi.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        trabajadorCfdi.setFechaCreacion(new Date());
        trabajadorCfdi.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        trabajadorCfdi.setUltimaFechaActualizacion(new Date());
        timbre = timbresFacade.obtenerTimbre("Proveedor", usuario);
        seleccionados = new ArrayList<>();
    }

    public void inicializarDatos() {
        cfdisId = new RnGcCfdisTbl();
        archivo = new RnGcArchivosTbl();
        folioSerie = new ArrayList<>();
        trabajadorCfdi = new RnGcNomTrabajadorCfdisTbl();
        cfdisId.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        cfdisId.setFechaCreacion(new Date());
        cfdisId.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        cfdisId.setUltimaFechaActualizacion(new Date());
        cfdisId.setTipoComprobante("N");
        cfdisId.setUsoCfdi("P01");
        cfdisId.setMoneda("MXN");
        cfdisId.setFormaPago("99");
        cfdisId.setMetodoPago("PUE");
        usuario = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        cfdisId.setNombreEmisor(usuario.getNombreCompleto());
        cfdisId.setRfcEmisor(usuario.getRfc());
        archivo.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        archivo.setFechaCreacion(new Date());
        archivo.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        archivo.setUltimaFechaActualizacion(new Date());
        trabajadorCfdi.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        trabajadorCfdi.setFechaCreacion(new Date());
        trabajadorCfdi.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        trabajadorCfdi.setUltimaFechaActualizacion(new Date());
        seleccionados = new ArrayList<>();
    }

    public void obtenerSeleccionados() throws ParseException {
        if (timbre != null && !timbre.isEmpty()) {
            Integer folio1 = 0;
            for (RnGcNomSolicitudTrabajadorTbl soliTrabajador : seleccionados) {
                if(cfdisId2.getSerie() != null)
                    cfdisId.setSerie(cfdisId2.getSerie());
                if(cfdisId2.getFolio() != null)
                    cfdisId.setFolio(cfdisId2.getFolio()+folio1);
                folio1 = folio1 + 1;
                cfdisId.setClaveRegimenFiscal(cfdisId2.getClaveRegimenFiscal());
                cfdisId.setCertificados_Id(cfdisId2.getCertificados_Id());
                cfdisId.setFechaExpedicion(cfdisId2.getFechaExpedicion());
                cfdisId.setLugarExpedicion(cfdisId2.getLugarExpedicion());
                cfdisId.setCondicionPago(cfdisId2.getCondicionPago());
                if (crearXML(soliTrabajador)) {
                    timbre.get(0).setTimbresRestantes(timbre.get(0).getTimbresTotal());
                    timbre.get(0).setTimbresUsados(timbre.get(0).getTimbresUsados() + 1);
                    timbresFacade.edit(timbre.get(0));
                    if (cfdisId.getFolio() != null && cfdisId.getSerie() != null) {
                        RnGcFolioserieTbl folioSerieLocal = new RnGcFolioserieTbl();
                        folioSerieLocal = obtenerFolioPorUsuarioSerie(cfdisId.getSerie()).get(0);
                        if (folioSerieLocal != null && folioSerieLocal.getFolio() != null) {
                            folioSerieLocal.setFolio(folioSerieLocal.getFolio() + 1);
                            folioSerieLocal.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                            folioSerieLocal.setUltimaFechaActualizacion(new Date());
                            folioSerieFacade.edit(folioSerieLocal);
                        }
                    }
                    if (cfdisId.getUuid() != null) {
                        cfdisId = cfdisFacade.refreshFromDB(cfdisId);
                        trabajadorCfdi.setSolicitudTrabajdorId(soliTrabajador);
                        trabajadorCfdi.setCfdiId(cfdisId);
                        trabajadorCfdi = trabajadorCfdiFacade.refreshFromDB(trabajadorCfdi);
                        if (archivo.getArchivoPdf() != null && archivo.getArchivoXml() != null && archivo.getArchivoQR() != null) {
                            archivo.setCfdiId(cfdisId);
                            archivo = archivoFacade.refreshFromDB(archivo);
                            enviarCorreo2(soliTrabajador, archivo);
                        }
                        inicializarDatos();
                    }
                } else {
                    timbre.get(0).setTimbresRestantes(timbre.get(0).getTimbresTotal());
                    timbre.get(0).setTimbresUsados(timbre.get(0).getTimbresUsados() + 1);
                    timbresFacade.edit(timbre.get(0));
                    inicializarDatos();
                    JsfUtil.addErrorMessage("Ocurrio un eror durante el timbrado");
                }
                JsfUtil.addSuccessMessage("Facturado de forma correcta");
            }
        } else {
            inicializarDatos();
            JsfUtil.addSuccessMessage("Los timbres asignados a este usuario se han terminado");
        }
    }

    public List<RnGcFolioserieTbl> obtenerFolioPorUsuarioSerie(String serie) {
        if (!serie.isEmpty()) {
            RnGcUsuariosTbl usuario1 = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            folioSerie = folioSerieFacade.folioPorUsuarioSerie(usuario1, serie);
        } else {
            folioSerie = null;
        }
        return folioSerie;
    }

    public String totalGravado(List<RnGcNomSolicitudesLineasTbl> Percepciones) {
        BigDecimal totalGravado = BigDecimal.ZERO;
        for (RnGcNomSolicitudesLineasTbl percepcion : Percepciones) {
            totalGravado = totalGravado.add(percepcion.getTotalGravado());
        }
        return String.valueOf(totalGravado.doubleValue());
    }

    public String totalExento(List<RnGcNomSolicitudesLineasTbl> Percepciones) {
        BigDecimal totalExento = BigDecimal.ZERO;
        for (RnGcNomSolicitudesLineasTbl percepcion : Percepciones) {
            totalExento = totalExento.add(percepcion.getTotalExento());
        }
        return String.valueOf(totalExento.doubleValue());
    }

    public String totalOtraDeduccion(List<RnGcNomSolicitudesLineasTbl> Deducciones) {
        BigDecimal totalOtraDeduccion = BigDecimal.ZERO;
        for (RnGcNomSolicitudesLineasTbl deduccion : Deducciones) {
            if (deduccion.getDeduccionId().getId() != 26) {
                totalOtraDeduccion = totalOtraDeduccion.add(deduccion.getTotalGravado());
            }
        }
        return String.valueOf(totalOtraDeduccion.doubleValue());
    }

    public String totalRetenido(List<RnGcNomSolicitudesLineasTbl> Deducciones) {
        BigDecimal totalRetenido = BigDecimal.ZERO;
        for (RnGcNomSolicitudesLineasTbl deduccion : Deducciones) {
            if (deduccion.getDeduccionId().getId() == 26) {
                totalRetenido = totalRetenido.add(deduccion.getTotalGravado());
            }
        }
        return String.valueOf(totalRetenido.doubleValue());
    }

    public boolean crearXML(RnGcNomSolicitudTrabajadorTbl solicitudTrabajador) {
        String cadOrig = "";
        boolean valor = false;
        File tempFile = null;
        try {
            tipoContrato = new RnGcNomTipocontratoTbl();
            estado = new RnGcNomEstadosTbl();
            listaPercepciones = new ArrayList<>();
            listaDeducciones = new ArrayList<>();
            tipoContrato = tipoContratoFacade.obtenerXId(solicitudTrabajador.getTrabajadorId().getTipoContratoId());
            estado = estadoFacade.obtenerXId(solicitudTrabajador.getTrabajadorId().getEstadoId());
            listaPercepciones = solicitudLineasFacade.obtenerPercepciones(solicitudTrabajador.getId());
            listaDeducciones = solicitudLineasFacade.obtenerDeducciones(solicitudTrabajador.getId());
            cfdisId.setImporte(solicitudTrabajador.getImporteNeto().doubleValue());
            cfdisId.setSaldoPagado(solicitudTrabajador.getImporteNeto().doubleValue());
            cfdisId.setSaldoInsoluto(solicitudTrabajador.getImporteNeto().doubleValue());
            cfdisId.setRfcReceptor(solicitudTrabajador.getTrabajadorId().getRfc());
            cfdisId.setNombreReceptor(solicitudTrabajador.getTrabajadorId().getNombreCompleto());

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
            if (cfdisId.getSerie() != null) {
                Attr serie = doc.createAttribute("Serie");
                serie.setValue(cfdisId.getSerie());
                rootElement.setAttributeNode(serie);
            }
            if (cfdisId.getFolio() != null) {
                Attr folio1 = doc.createAttribute("Folio");
                folio1.setValue(String.valueOf(cfdisId.getFolio()));
                rootElement.setAttributeNode(folio1);
            }//*/
            Attr fecha = doc.createAttribute("Fecha");
            fecha.setValue(new SimpleDateFormat("YYYY-MM-dd'T'hh:mm:ss").format(cfdisId.getFechaExpedicion()));
            rootElement.setAttributeNode(fecha);
            Attr sello = doc.createAttribute("Sello");
            sello.setValue("");
            rootElement.setAttributeNode(sello);
            Attr noCertificado = doc.createAttribute("NoCertificado");
            noCertificado.setValue(cfdisId.getCertificados_Id().getNumeroCertificado());
            rootElement.setAttributeNode(noCertificado);
            Attr formaPago = doc.createAttribute("FormaPago");
            formaPago.setValue(cfdisId.getFormaPago());
            rootElement.setAttributeNode(formaPago);
            Attr metodoPago = doc.createAttribute("MetodoPago");
            metodoPago.setValue(cfdisId.getMetodoPago());
            rootElement.setAttributeNode(metodoPago);
            Attr certificado1 = doc.createAttribute("Certificado");
            certificado1.setValue("");
            rootElement.setAttributeNode(certificado1);
            if (cfdisId.getCondicionPago() != null && !cfdisId.getCondicionPago().isEmpty()) {
                Attr condicionPago = doc.createAttribute("CondicionesDePago");
                condicionPago.setValue(cfdisId.getCondicionPago());
                rootElement.setAttributeNode(condicionPago);
            }
            Attr subTotal = doc.createAttribute("SubTotal");
            subTotal.setValue(String.valueOf(solicitudTrabajador.getTotalPercepciones().doubleValue()));
            rootElement.setAttributeNode(subTotal);
            Attr moneda = doc.createAttribute("Moneda");
            moneda.setValue(cfdisId.getMoneda());
            rootElement.setAttributeNode(moneda);
            Attr total = doc.createAttribute("Total");
            cfdisId.setImporteLetra(importeLetra(solicitudTrabajador.getImporteNeto()));
            total.setValue(String.valueOf(solicitudTrabajador.getImporteNeto().doubleValue()));
            rootElement.setAttributeNode(total);
            Attr descuentoTotal = doc.createAttribute("Descuento");
            descuentoTotal.setValue(String.valueOf(solicitudTrabajador.getTotalDeducciones().doubleValue()));
            rootElement.setAttributeNode(descuentoTotal);
            Attr tipoCfdi = doc.createAttribute("TipoDeComprobante");
            tipoCfdi.setValue(cfdisId.getTipoComprobante());
            rootElement.setAttributeNode(tipoCfdi);
            Attr lugarExp = doc.createAttribute("LugarExpedicion");
            lugarExp.setValue(String.valueOf(cfdisId.getLugarExpedicion()));
            rootElement.setAttributeNode(lugarExp);
            Attr cfdi = doc.createAttribute("xmlns:cfdi");
            cfdi.setValue("http://www.sat.gob.mx/cfd/3");
            rootElement.setAttributeNode(cfdi);
            Attr impLocal = doc.createAttribute("xmlns:nomina12");
            impLocal.setValue("http://www.sat.gob.mx/nomina12");
            rootElement.setAttributeNode(impLocal);
            Attr xsi = doc.createAttribute("xmlns:xsi");
            xsi.setValue("http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttributeNode(xsi);
            Attr esquema = doc.createAttribute("xsi:schemaLocation");
            esquema.setValue("http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd http://www.sat.gob.mx/nomina12 http://www.sat.gob.mx/sitio_internet/cfd/nomina/nomina12.xsd");
            rootElement.setAttributeNode(esquema);
            //Nodo emisor
            Element emisor = doc.createElement("cfdi:Emisor");
            rootElement.appendChild(emisor);
            //Atributos Emisor
            Attr rfcE = doc.createAttribute("Rfc");
            rfcE.setValue(stripAccents(cfdisId.getRfcEmisor()));
            emisor.setAttributeNode(rfcE);
            Attr nombreE = doc.createAttribute("Nombre");
            nombreE.setValue(stripAccents(cfdisId.getNombreEmisor()));
            emisor.setAttributeNode(nombreE);
            Attr regimen = doc.createAttribute("RegimenFiscal");
            regimen.setValue(cfdisId.getClaveRegimenFiscal());
            emisor.setAttributeNode(regimen);
            //nodo receptor
            Element receptor = doc.createElement("cfdi:Receptor");
            rootElement.appendChild(receptor);
            //Atributos Receptor
            Attr rfcR = doc.createAttribute("Rfc");
            rfcR.setValue(solicitudTrabajador.getTrabajadorId().getRfc());
            receptor.setAttributeNode(rfcR);
            Attr nombreR = doc.createAttribute("Nombre");
            nombreR.setValue(stripAccents(solicitudTrabajador.getTrabajadorId().getNombreCompleto()));
            receptor.setAttributeNode(nombreR);
            Attr uso = doc.createAttribute("UsoCFDI");
            uso.setValue(cfdisId.getUsoCfdi());
            receptor.setAttributeNode(uso);
            //Nodo conceptos
            Element conceptos = doc.createElement("cfdi:Conceptos");
            rootElement.appendChild(conceptos);
            //Atributos conceptos
            Element concepto = doc.createElement("cfdi:Concepto");
            conceptos.appendChild(concepto);
            Attr claveProd = doc.createAttribute("ClaveProdServ");
            claveProd.setValue("84111505");
            concepto.setAttributeNode(claveProd);
            Attr cantidad = doc.createAttribute("Cantidad");
            cantidad.setValue("1");
            concepto.setAttributeNode(cantidad);
            Attr claveUnidad = doc.createAttribute("ClaveUnidad");
            claveUnidad.setValue("ACT");
            concepto.setAttributeNode(claveUnidad);
            Attr descripcion = doc.createAttribute("Descripcion");
            descripcion.setValue("Pago de nómina");
            concepto.setAttributeNode(descripcion);
            Attr valorUnit = doc.createAttribute("ValorUnitario");
            valorUnit.setValue(String.valueOf(solicitudTrabajador.getTotalPercepciones().doubleValue()));
            concepto.setAttributeNode(valorUnit);
            Attr importe = doc.createAttribute("Importe");
            importe.setValue(String.valueOf(solicitudTrabajador.getTotalPercepciones().doubleValue()));
            concepto.setAttributeNode(importe);
            Attr descuento = doc.createAttribute("Descuento");
            descuento.setValue(String.valueOf(solicitudTrabajador.getTotalDeducciones().doubleValue()));
            concepto.setAttributeNode(descuento);
            //Nodo Complemento
            Element complemento = doc.createElement("cfdi:Complemento");
            rootElement.appendChild(complemento);
            Element pagos = doc.createElement("nomina12:Nomina");
            complemento.appendChild(pagos);
            Attr versionP = doc.createAttribute("Version");
            versionP.setValue("1.2");
            pagos.setAttributeNode(versionP);
            Attr tipoNomina = doc.createAttribute("TipoNomina");
            tipoNomina.setValue(solicitudTrabajador.getSolicitudId().getPeriodoNominaId().getNominaId().getTipoNominaId().getCveTipoNomina());
            //tipoNomina.setValue(nomina.getTipoNominaId().getCveTipoNomina());
            pagos.setAttributeNode(tipoNomina);
            Attr fechaPago = doc.createAttribute("FechaPago");
            fechaPago.setValue(new SimpleDateFormat("YYYY-MM-dd").format(solicitudTrabajador.getFechaPago()));
            pagos.setAttributeNode(fechaPago);
            Attr fechaInicialPago = doc.createAttribute("FechaInicialPago");
            fechaInicialPago.setValue(new SimpleDateFormat("YYYY-MM-dd").format(solicitudTrabajador.getSolicitudId().getPeriodoNominaId().getFechaInicio()));
            pagos.setAttributeNode(fechaInicialPago);
            Attr fechaFinalPago = doc.createAttribute("FechaFinalPago");
            fechaFinalPago.setValue(new SimpleDateFormat("YYYY-MM-dd").format(solicitudTrabajador.getSolicitudId().getPeriodoNominaId().getFechaFin()));
            pagos.setAttributeNode(fechaFinalPago);
            Attr diasPagados = doc.createAttribute("NumDiasPagados");
            diasPagados.setValue(solicitudTrabajador.getDiasPagados().toString());
            pagos.setAttributeNode(diasPagados);
            Attr totalPercepcion = doc.createAttribute("TotalPercepciones");
            totalPercepcion.setValue(String.valueOf(solicitudTrabajador.getTotalPercepciones().doubleValue()));
            pagos.setAttributeNode(totalPercepcion);
            Attr totalDeduccion = doc.createAttribute("TotalDeducciones");
            totalDeduccion.setValue(String.valueOf(solicitudTrabajador.getTotalDeducciones().doubleValue()));
            pagos.setAttributeNode(totalDeduccion);
            Element emisorNom = doc.createElement("nomina12:Emisor");
            pagos.appendChild(emisorNom);
            Attr rfcPatron = doc.createAttribute("RfcPatronOrigen");
            rfcPatron.setValue(usuario.getRfc());
            emisorNom.setAttributeNode(rfcPatron);

            Element receptorNom = doc.createElement("nomina12:Receptor");
            pagos.appendChild(receptorNom);
            Attr banco = doc.createAttribute("Banco");
            banco.setValue(solicitudTrabajador.getTrabajadorId().getBanco());
            receptorNom.setAttributeNode(banco);
            Attr curp = doc.createAttribute("Curp");
            curp.setValue(solicitudTrabajador.getTrabajadorId().getCurp());
            receptorNom.setAttributeNode(curp);
            Attr noEmpleado = doc.createAttribute("NumEmpleado");
            noEmpleado.setValue(solicitudTrabajador.getTrabajadorId().getNoTrabajador());
            receptorNom.setAttributeNode(noEmpleado);
            Attr periodicidadPago = doc.createAttribute("PeriodicidadPago");
            periodicidadPago.setValue(solicitudTrabajador.getSolicitudId().getPeriodoNominaId().getNominaId().getPeriodicidadPagoId().getCvePeriodicidadPago());
            //periodicidadPago.setValue(nomina.getPeriodicidadPagoId().getCvePeriodicidadPago());
            receptorNom.setAttributeNode(periodicidadPago);
            Attr tipoContra = doc.createAttribute("TipoContrato");
            tipoContra.setValue(tipoContrato.getCveTipoContrato());
            receptorNom.setAttributeNode(tipoContra);
            Attr est = doc.createAttribute("ClaveEntFed");
            est.setValue(estado.getCveEstado());
            receptorNom.setAttributeNode(est);
            Attr tipoRegi = doc.createAttribute("TipoRegimen");
            tipoRegi.setValue(solicitudTrabajador.getTrabajadorId().getTipoPersona());
            receptorNom.setAttributeNode(tipoRegi);
            if (solicitudTrabajador.getTrabajadorId().getCuentaBancaria() != null && !solicitudTrabajador.getTrabajadorId().getCuentaBancaria().isEmpty()) {
                Attr cuentaBancaria = doc.createAttribute("CuentaBancaria");
                cuentaBancaria.setValue(solicitudTrabajador.getTrabajadorId().getCuentaBancaria());
                receptorNom.setAttributeNode(cuentaBancaria);
            }
            Element percepciones = doc.createElement("nomina12:Percepciones");
            pagos.appendChild(percepciones);
            Attr totalSueldo = doc.createAttribute("TotalSueldos");
            totalSueldo.setValue(String.valueOf(solicitudTrabajador.getTotalPercepciones().doubleValue()));
            percepciones.setAttributeNode(totalSueldo);
            Attr totalGravado = doc.createAttribute("TotalGravado");
            totalGravado.setValue(totalGravado(listaPercepciones));
            percepciones.setAttributeNode(totalGravado);
            Attr TotalExento = doc.createAttribute("TotalExento");
            TotalExento.setValue(totalExento(listaPercepciones));
            percepciones.setAttributeNode(TotalExento);
            for (RnGcNomSolicitudesLineasTbl percepcion : listaPercepciones) {
                Element percep = doc.createElement("nomina12:Percepcion");
                percepciones.appendChild(percep);
                Attr tipoPercepcion = doc.createAttribute("TipoPercepcion");
                tipoPercepcion.setValue(stripAccents(percepcion.getPercepcionId().getCveTipoPercepcion()));
                percep.setAttributeNode(tipoPercepcion);
                Attr clave = doc.createAttribute("Clave");
                clave.setValue(stripAccents(percepcion.getTipoClave()));
                percep.setAttributeNode(clave);
                Attr concep = doc.createAttribute("Concepto");
                concep.setValue(stripAccents(percepcion.getPercepcionId().getDescripcion()));
                percep.setAttributeNode(concep);
                Attr imporExcento = doc.createAttribute("ImporteExento");
                imporExcento.setValue(String.valueOf(percepcion.getTotalExento().doubleValue()));
                percep.setAttributeNode(imporExcento);
                Attr imporGravado = doc.createAttribute("ImporteGravado");
                imporGravado.setValue(String.valueOf(percepcion.getTotalGravado().doubleValue()));
                percep.setAttributeNode(imporGravado);
            }
            Element deducciones = doc.createElement("nomina12:Deducciones");
            pagos.appendChild(deducciones);
            Attr totOtraDeduc = doc.createAttribute("TotalOtrasDeducciones");
            totOtraDeduc.setValue(totalOtraDeduccion(listaDeducciones));
            deducciones.setAttributeNode(totOtraDeduc);
            Double retenidos = null;
            retenidos = Double.parseDouble(totalRetenido(listaDeducciones));
            if(retenidos != null && retenidos > 0.0){
                Attr totalRetenido = doc.createAttribute("TotalImpuestosRetenidos");
                totalRetenido.setValue(retenidos.toString());
                deducciones.setAttributeNode(totalRetenido);
            }
            for (RnGcNomSolicitudesLineasTbl deduccion : listaDeducciones) {
                Element deduc = doc.createElement("nomina12:Deduccion");
                deducciones.appendChild(deduc);
                Attr tipoDeduccion = doc.createAttribute("TipoDeduccion");
                tipoDeduccion.setValue(stripAccents(deduccion.getDeduccionId().getCveTipoDeduccion()));
                deduc.setAttributeNode(tipoDeduccion);
                Attr concep = doc.createAttribute("Concepto");
                concep.setValue(stripAccents(deduccion.getTipoConcepto()));
                deduc.setAttributeNode(concep);
                Attr importeDeduc = doc.createAttribute("Importe");
                importeDeduc.setValue(String.valueOf(deduccion.getTotalGravado().doubleValue()));
                deduc.setAttributeNode(importeDeduc);
                Attr clave = doc.createAttribute("Clave");
                clave.setValue(stripAccents(deduccion.getTipoClave()));
                deduc.setAttributeNode(clave);
            }

            File xslt = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/cadenaoriginal_3_3.xslt"));
            tempFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/xmlTemp.xml"));//Inicio calcula cadena original
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(tempFile);
            transformer.transform(domSource, streamResult);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            StreamResult cadenaOriginal = new StreamResult(bos);

            StreamSource sourceXML = new StreamSource(tempFile);
            StreamSource sourceXSL = new StreamSource(xslt);
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer2 = tFactory.newTransformer(sourceXSL);
            transformer2.transform(sourceXML, cadenaOriginal);

            String selloCompl = crearSello(bos.toString());
            String cadenaOriginalCompl = bos.toString();
            String certificado = crearCertificado();

            valor = modificarXml(selloCompl, certificado, cadenaOriginalCompl, tempFile, solicitudTrabajador);
        } catch (Exception ex) {
            System.err.println("Ocurrio un error en la creacion del XML: " + ex.getLocalizedMessage());
        }
        System.out.println("valorCrearXML: " + valor);
        return valor;
    }

    public boolean modificarXml(String sello, String certificado, String cadenaOriginal, File xmlAc, RnGcNomSolicitudTrabajadorTbl soliTrabajador) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = docFactory.newDocumentBuilder();
        Document doc = builder.parse(xmlAc);
        boolean valorModifica = true;
        File tempFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/xmlTemp.xml"));
        NodeList items = doc.getElementsByTagName("cfdi:Comprobante");
        for (int i = 0; i < items.getLength(); i++) {
            Element element = (Element) items.item(i);
            element.setAttribute("Sello", sello);
            element.setAttribute("Certificado", certificado);
        }
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result output = new StreamResult(tempFile);
        Source input = new DOMSource(doc);
        transformer.transform(input, output);
        if (timbra(tempFile.getPath(), cadenaOriginal, soliTrabajador)) {
            valorModifica = true;
        }
        return valorModifica;
    }

    public boolean timbra(String nombre, String cadOriginal, RnGcNomSolicitudTrabajadorTbl soliTrabajador) {
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
            //Sefactura sf = new Sefactura(" http://www.jonima.com.mx:3014", "VICA840114RZ4", "VICA840114RZ4"); //Desarrollo
            Sefactura sf = new Sefactura("https://www.sefactura.com.mx", "AFC060520V16", "AFC060520V16"); //Produccion
            RespuestaTimbrado rt = sf.timbrado(xml);
            System.out.println("xmlTimbrado: " + rt.getXml());
            System.out.println("resultado: " + rt.getResultado());
            if (rt.getResultado() != null && rt.getResultado().length() > 0) {
                System.out.println("Error al generar timbrado: " + rt.getResultado());
                cfdisId.setRespuestaTimbrado(rt.getResultado());
            } else {
                String selloSAT = " ";
                String noCertSAT = " ";
                String fechaTimbrado = " ";
                String Uuid = " ";
                String selloCFDI = " ";
                String xmlTimbrado = rt.getXml();
                String rfcProvCertif = " ";
                cfdisId.setXmlTrama(xmlTimbrado);
                FileOutputStream fos = new FileOutputStream(xmltimbrado);
                fos.write(xmlTimbrado.getBytes("UTF-8"));
                fos.close();
                byte[] codQR = Base64.getDecoder().decode(rt.getCadenaCodigo());
                archivo.setArchivoQR(codQR);
                byte[] xmlTimbradoB = new byte[(int) xmltimbrado.length()];
                fis = new FileInputStream(xmltimbrado);
                fis.read(xmlTimbradoB);
                fis.close();
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();//obtener datos de XML Timbrado para  PDF
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
                    rfcProvCertif = element.getAttribute("RfcProvCertif");
                }
                cfdisId.setUuid(Uuid);
                archivo.setArchivoXml(xmlTimbradoB);
                cfdisId.setRespuestaTimbrado("Timbrado de forma correcta");
                System.out.println(noCertSAT + " | " + fechaTimbrado + " | " + Uuid + " | " + selloCFDI + " | " + selloSAT + " | " + rfcProvCertif);
                crearPDF(selloSAT, noCertSAT, fechaTimbrado, Uuid, selloCFDI, codQR, cadOriginal, rfcProvCertif, soliTrabajador);
                //crearArchivo(xmltimbrado);
                valorTimbra = true;
                System.out.println("Ya termine ");
            }
        } catch (Exception ex) {
            System.err.println("Ocurrio un error en el timbrado: " + ex.getLocalizedMessage());
        }
        return valorTimbra;
    }

    public String crearSello(String xml) throws Exception {
        PKCS8Key pkcs8 = new PKCS8Key(cfdisId.getCertificados_Id().getLlavePrivada(), cfdisId.getCertificados_Id().getContraseniaLlavePrivada().toCharArray());
        KeyFactory privateKeyFact = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec pkcs8Encoded = new PKCS8EncodedKeySpec(pkcs8.getDecryptedBytes());
        PrivateKey privateKey = privateKeyFact.generatePrivate(pkcs8Encoded);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        byte[] cadenaOriginalArray = xml.getBytes();
        signature.update(cadenaOriginalArray);
        String firma = new String(Base64.getEncoder().encode(signature.sign()));
        System.out.println("Sello: " + firma);
        return firma;
    }

    public String crearCertificado() {
        String certificado = new String(Base64.getEncoder().encode(cfdisId.getCertificados_Id().getCertificadoSelloDigital()));
        System.out.println("Certificado: " + certificado);
        return certificado;
    }

    public String leerCfdi(File xml) {
        File archivo1 = xml;
        FileReader fr = null;
        BufferedReader br = null;
        String linea = " ";
        String contenido = null;
        try {
            fr = new FileReader(archivo1);
            br = new BufferedReader(fr);

            while ((linea = br.readLine()) != null) {
                System.out.println("linea: " + linea);
                contenido = linea;
                cfdisId.setXmlTrama(linea);
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
        return contenido;
    }

    public static String stripAccents(String str) {
        if (str == null) {
            return null;
        }
        char[] array = str.toCharArray();
        for (int index = 0; index < array.length; index++) {
            int pos = ORIGINAL.indexOf(array[index]);
            if (pos > -1) {
                array[index] = REPLACEMENT.charAt(pos);
            }
        }
        return new String(array);
    }

    public void crearPDF(String selloSAT, String noCertSAT, String fechaTimbrado, String Uuid, String selloCFDI, byte[] codigoQR, String cadenaOrig, String rfcProvCertif, RnGcNomSolicitudTrabajadorTbl soliTrsbajador) throws JRException, IOException, ParseException {
        FileOutputStream fos = new FileOutputStream(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/qr.png")));
        fos.write(codigoQR);
        fos.close();
        fos = new FileOutputStream(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/responsivo.png")));
        fos.write(obtenerImagen());
        fos.close();
        String imagenLogo = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRealPath("/resources/images/responsivo.png");//*/
        String imagenqr = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRealPath("/resources/images/qr.png");
        tipoContrato = new RnGcNomTipocontratoTbl();
        estado = new RnGcNomEstadosTbl();
        listaPercepciones = new ArrayList<>();
        listaDeducciones = new ArrayList<>();
        tipoContrato = tipoContratoFacade.obtenerXId(soliTrsbajador.getTrabajadorId().getTipoContratoId());
        estado = estadoFacade.obtenerXId(soliTrsbajador.getTrabajadorId().getEstadoId());
        listaPercepciones = solicitudLineasFacade.obtenerPercepciones(soliTrsbajador.getId());
        listaDeducciones = solicitudLineasFacade.obtenerDeducciones(soliTrsbajador.getId());
        cfdisId.setImporte(soliTrsbajador.getImporteNeto().doubleValue());
        cfdisId.setSaldoPagado(soliTrsbajador.getImporteNeto().doubleValue());
        cfdisId.setSaldoInsoluto(soliTrsbajador.getImporteNeto().doubleValue());
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("Nombre_Emisor", cfdisId.getNombreEmisor());
        parametros.put("RFC_Emisor", cfdisId.getRfcEmisor());
        parametros.put("Nombre_Receptor", soliTrsbajador.getTrabajadorId().getNombreCompleto());
        parametros.put("RFC_Receptor", soliTrsbajador.getTrabajadorId().getRfc());
        parametros.put("RegimenFiscal", cfdisId.getClaveRegimenFiscal());
        parametros.put("NoSerie_CSD", cfdisId.getSerie());
        parametros.put("Folio_Fiscal", cfdisId.getFolio());
        parametros.put("CodigoPostal", cfdisId.getLugarExpedicion());
        parametros.put("FechaHora_Emision", new SimpleDateFormat("YYYY-MM-dd'T'hh:mm:ss").format(cfdisId.getFechaExpedicion()));
        parametros.put("QR", imagenqr);
        parametros.put("Logo", imagenLogo);

        if (cfdisId.getClaveRegimenFiscal() != null) {
            switch (cfdisId.getClaveRegimenFiscal()) {
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
        parametros.put("EfectoComprobante", "N - Nomina");
        parametros.put("Uso_CFDI", "P01 - Por definir");
        parametros.put("FormaPago", "99 - Por definir");
        parametros.put("MetodoPago", "PUE - Pago en una sola exhibicion");
        parametros.put("moneda", "MXN - Peso Mexicano");
        parametros.put("listaPercepciones", listaPercepciones);
        parametros.put("listaDeducciones", listaDeducciones);
        parametros.put("valorUnitario", soliTrsbajador.getTotalPercepciones());
        parametros.put("importe", soliTrsbajador.getTotalPercepciones());
        parametros.put("descuento", soliTrsbajador.getTotalDeducciones());
        parametros.put("Subtotal", soliTrsbajador.getTotalPercepciones());
        parametros.put("Total", soliTrsbajador.getImporteNeto());
        parametros.put("CURP_Receptor", soliTrsbajador.getTrabajadorId().getCurp());
        parametros.put("NSS_Receptor", soliTrsbajador.getTrabajadorId().getNss());
        parametros.put("RiesgoPuesto", soliTrsbajador.getTrabajadorId().getFechaInicio());
        parametros.put("tipoContrato", tipoContrato.getCveTipoContrato() + " - " + tipoContrato.getDescripcion());
        parametros.put("tipoRegimen", soliTrsbajador.getTrabajadorId().getTipoPersona());
        parametros.put("banco", soliTrsbajador.getTrabajadorId().getBanco());
        parametros.put("cuentaBancaria", soliTrsbajador.getTrabajadorId().getCuentaBancaria());
        parametros.put("salarioBase", Double.valueOf(soliTrsbajador.getTrabajadorId().getSalarioBase()));
        parametros.put("salarioDiario", Double.valueOf(soliTrsbajador.getTrabajadorId().getSdi()));
        parametros.put("claveFederativa", estado.getCveEstado());
        parametros.put("N_empleado", soliTrsbajador.getTrabajadorId().getNoTrabajador());
        parametros.put("importeLetra", importeLetra(soliTrsbajador.getImporteNeto()));
        parametros.put("tipoNomina", soliTrsbajador.getSolicitudId().getPeriodoNominaId().getNominaId().getTipoNominaId().getCveTipoNomina() + " - " + soliTrsbajador.getSolicitudId().getPeriodoNominaId().getNominaId().getTipoNominaId().getDescripcion());
        parametros.put("fechaInicialPago", solicitud.getPeriodoNominaId().getFechaInicio());
        parametros.put("diasPagados", soliTrsbajador.getDiasPagados());
        parametros.put("fechaPago", soliTrsbajador.getFechaPago());
        parametros.put("fechaFinalPago", solicitud.getPeriodoNominaId().getFechaFin());
        parametros.put("registroPatronal", solicitud.getRegistroPatronal());
        parametros.put("fechaInicioLaboral", soliTrsbajador.getTrabajadorId().getFechaInicio());
        parametros.put("totalPercepciones", String.valueOf(soliTrsbajador.getTotalPercepciones().doubleValue()));
        parametros.put("totalDeducciones", String.valueOf(soliTrsbajador.getTotalDeducciones().doubleValue()));
        parametros.put("importeNeto", String.valueOf(soliTrsbajador.getImporteNeto().doubleValue()));
        parametros.put("Uuid", Uuid);
        parametros.put("rfcProvCertif", rfcProvCertif);
        parametros.put("noCertificadoSAT", noCertSAT);
        parametros.put("fechaCertificacion", fechaTimbrado);
        parametros.put("cadenaOriginal", cadenaOrig);
        parametros.put("sello_CFDI", selloCFDI);
        parametros.put("sello_SAT", selloSAT);

        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Reports/complemento_nomina.jasper"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JREmptyDataSource());
        byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);
        archivo.setArchivoPdf(pdf);
    }

    public void descargarVistaPrevia() {
        try {
            if (seleccionados != null && !seleccionados.isEmpty() && seleccionados.size() > 0) {
                cfdisId.setClaveRegimenFiscal(cfdisId2.getClaveRegimenFiscal());
                cfdisId.setLugarExpedicion(cfdisId2.getLugarExpedicion());
                FileOutputStream fos = new FileOutputStream(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/responsivo.png")));
                fos.write(obtenerImagen());
                fos.close();
                String imagenLogo = FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .getRealPath("/resources/images/responsivo.png");//*/
                String imagenqr = FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .getRealPath("/resources/images/qr.png");
                List<byte[]> pdfs = new ArrayList<>();
                for (RnGcNomSolicitudTrabajadorTbl soliTrabajador : seleccionados) {
                    nomina = new RnGcNomNominasTbl();
                    tipoContrato = new RnGcNomTipocontratoTbl();
                    estado = new RnGcNomEstadosTbl();
                    listaPercepciones = new ArrayList<>();
                    listaDeducciones = new ArrayList<>();
                    nomina = nominaFacade.obtenerNominaPorIdUnico(soliTrabajador.getSolicitudId().getNominaId());
                    tipoContrato = tipoContratoFacade.obtenerXId(soliTrabajador.getTrabajadorId().getTipoContratoId());
                    estado = estadoFacade.obtenerXId(soliTrabajador.getTrabajadorId().getEstadoId());
                    listaPercepciones = solicitudLineasFacade.obtenerPercepciones(soliTrabajador.getId());
                    listaDeducciones = solicitudLineasFacade.obtenerDeducciones(soliTrabajador.getId());
                    cfdisId.setImporte(soliTrabajador.getImporteNeto().doubleValue());
                    cfdisId.setSaldoPagado(soliTrabajador.getImporteNeto().doubleValue());
                    cfdisId.setSaldoInsoluto(soliTrabajador.getImporteNeto().doubleValue());
                    Map<String, Object> parametros = new HashMap<String, Object>();
                    parametros.put("Nombre_Emisor", cfdisId.getNombreEmisor());
                    parametros.put("RFC_Emisor", cfdisId.getRfcEmisor());
                    parametros.put("Nombre_Receptor", soliTrabajador.getTrabajadorId().getNombreCompleto());
                    parametros.put("RFC_Receptor", soliTrabajador.getTrabajadorId().getRfc());
                    parametros.put("RegimenFiscal", cfdisId.getClaveRegimenFiscal());
                    parametros.put("NoSerie_CSD", cfdisId.getSerie());
                    parametros.put("Folio_Fiscal", cfdisId.getFolio());
                    parametros.put("CodigoPostal", cfdisId.getLugarExpedicion());
                    parametros.put("FechaHora_Emision", new SimpleDateFormat("YYYY-MM-dd'T'hh:mm:ss").format(cfdisId.getFechaExpedicion()));
                    parametros.put("QR", imagenqr);
                    parametros.put("Logo", imagenLogo);

                    if (cfdisId.getClaveRegimenFiscal() != null) {
                        switch (cfdisId.getClaveRegimenFiscal()) {
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
                    parametros.put("EfectoComprobante", "N - Nomina");
                    parametros.put("Uso_CFDI", "P01 - Por definir");
                    parametros.put("FormaPago", "99 - Por definir");
                    parametros.put("MetodoPago", "PUE - Pago en una sola exhibicion");
                    parametros.put("moneda", "MXN - Peso Mexicano");
                    parametros.put("listaPercepciones", listaPercepciones);
                    parametros.put("listaDeducciones", listaDeducciones);
                    parametros.put("valorUnitario", soliTrabajador.getTotalPercepciones());
                    parametros.put("importe", soliTrabajador.getTotalPercepciones());
                    parametros.put("descuento", soliTrabajador.getTotalDeducciones());
                    parametros.put("Subtotal", soliTrabajador.getTotalPercepciones());
                    parametros.put("Total", soliTrabajador.getImporteNeto());
                    parametros.put("CURP_Receptor", soliTrabajador.getTrabajadorId().getCurp());
                    parametros.put("NSS_Receptor", soliTrabajador.getTrabajadorId().getNss());
                    parametros.put("RiesgoPuesto", soliTrabajador.getTrabajadorId().getFechaInicio());
                    parametros.put("tipoContrato", tipoContrato.getCveTipoContrato() + " - " + tipoContrato.getDescripcion());
                    parametros.put("tipoRegimen", soliTrabajador.getTrabajadorId().getTipoPersona());
                    parametros.put("banco", soliTrabajador.getTrabajadorId().getBanco());
                    parametros.put("cuentaBancaria", soliTrabajador.getTrabajadorId().getCuentaBancaria());
                    parametros.put("salarioBase", Double.valueOf(soliTrabajador.getTrabajadorId().getSalarioBase()));
                    parametros.put("salarioDiario", Double.valueOf(soliTrabajador.getTrabajadorId().getSdi()));
                    parametros.put("claveFederativa", estado.getCveEstado());
                    parametros.put("N_empleado", soliTrabajador.getTrabajadorId().getNoTrabajador());
                    parametros.put("importeLetra", importeLetra(soliTrabajador.getImporteNeto()));
                    parametros.put("tipoNomina", nomina.getTipoNominaId().getCveTipoNomina() + " - " + nomina.getTipoNominaId().getDescripcion());
                    parametros.put("fechaInicialPago", solicitud.getPeriodoNominaId().getFechaInicio());
                    parametros.put("diasPagados", soliTrabajador.getDiasPagados());
                    parametros.put("fechaPago", soliTrabajador.getFechaPago());
                    parametros.put("fechaFinalPago", solicitud.getPeriodoNominaId().getFechaFin());
                    parametros.put("registroPatronal", solicitud.getRegistroPatronal());
                    parametros.put("fechaInicioLaboral", soliTrabajador.getTrabajadorId().getFechaInicio());
                    parametros.put("totalPercepciones", String.valueOf(soliTrabajador.getTotalPercepciones().doubleValue()));
                    parametros.put("totalDeducciones", String.valueOf(soliTrabajador.getTotalDeducciones().doubleValue()));
                    parametros.put("importeNeto", String.valueOf(soliTrabajador.getImporteNeto().doubleValue()));

                    File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Reports/complemento_nomina.jasper"));
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JREmptyDataSource());
                    pdfs.add(JasperExportManager.exportReportToPdf(jasperPrint));
                }

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ZipOutputStream zout = new ZipOutputStream(bos);

                for (int i = 0; i < pdfs.size(); i++) {
                    System.out.println("pdfs2: " + pdfs.get(i) + " | " + new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(new Date()) + " | " + (i + 1));
                    ZipEntry entry = new ZipEntry("VistaPreviaNomina_" + seleccionados.get(i).getTrabajadorId().getNombre() + "_" + new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(new Date()) + ".pdf");
                    zout.putNextEntry(entry);
                    zout.write(pdfs.get(i));
                    zout.closeEntry();
                }
                zout.finish();
                zout.flush();
                zout.close();
                System.out.println("Descarga1");
                ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
                InputStream streamPdf = bis;
                downLoadFile = new DefaultStreamedContent(streamPdf, "application/zip", "Factura_Nomina_" + new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(new Date()) + ".zip");
                System.out.println("Descarga2");
            } else {
                JsfUtil.addSuccessMessage("No se seleccionaron trabajadores");
            }
        } catch (Exception ex) {
            System.out.println("Ocurrio un error en la descarga del archivo zip");
        }
    }

    public byte[] obtenerImagen() {
        RnGcUsuariosTbl usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        RnGcImagenesTbl imagenId = imagenesFacade.obtenerImagenPorRFC(usuarioId.getRfc());
        if (imagenId == null) {
            imagenId = imagenesFacade.obtenerImagenPorRFC("ADMINISTRADOR");
        }
        System.out.println("Imagen: " + imagenId.getNombreImagen() + imagenId.getRfc());
        return imagenId.getFoto();
    }

    public String importeLetra(BigDecimal importeNeto) {
        NumeroALetra numLetra = new NumeroALetra();
        String importeLetra = String.valueOf(new DecimalFormat("0.00").format(importeNeto.doubleValue()));
        cfdisId.setImporteLetra(numLetra.Convertir(importeLetra, false));
        return numLetra.Convertir(importeLetra, false);
    }

    public void enviarCorreo2(RnGcNomSolicitudTrabajadorTbl solicitudTrabajador, RnGcArchivosTbl archivo) {
        if (solicitudTrabajador.getTrabajadorId().getEmail1() != null && !solicitudTrabajador.getTrabajadorId().getEmail1().isEmpty()
                && solicitudTrabajador.getTrabajadorId().getEmail1().contains("@")) {
            final String username = "conta-arc@rocketnegocios.com.mx";
            final String password = "ga1mJ7$4";
            Properties props = new Properties();
            props.put("mail.smtp.host", "rocketnegocios.com.mx");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.ssl.trust", "*");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            MimeMessage message = new MimeMessage(session);
            try {
                session.getProperties().put("mail.smtp.strattls.enable", "true");

                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setText(solicitudTrabajador.getSolicitudId().getPeriodoNominaId().getNominaId().getNombreNomina());

                FileOutputStream fos = new FileOutputStream(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/archivoPdf.pdf")));
                fos.write(archivo.getArchivoPdf());
                fos.close();
                MimeBodyPart mimeArchivoPdf = new MimeBodyPart();
                mimeArchivoPdf.attachFile(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/archivoPdf.pdf")));

                fos = new FileOutputStream(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/xmlTimbrado.xml")));
                fos.write(archivo.getArchivoXml());
                fos.close();
                MimeBodyPart mimeArchivoXml = new MimeBodyPart();
                mimeArchivoXml.attachFile(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/xmlTimbrado.xml")));

                fos = new FileOutputStream(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/qr.png")));
                fos.write(archivo.getArchivoQR());
                fos.close();
                MimeBodyPart mimeArchivoQR = new MimeBodyPart();
                mimeArchivoQR.attachFile(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/qr.png")));

                MimeMultipart multipart = new MimeMultipart();
                multipart.addBodyPart(mimeBodyPart);
                multipart.addBodyPart(mimeArchivoPdf);
                multipart.addBodyPart(mimeArchivoXml);
                multipart.addBodyPart(mimeArchivoQR);

                message.setFrom(new InternetAddress(username, usuario.getNombreCompleto()));
                message.setSubject(solicitudTrabajador.getSolicitudId().getPeriodoNominaId().getNominaId().getNombreNomina());

                message.addRecipients(Message.RecipientType.TO, solicitudTrabajador.getTrabajadorId().getEmail1());
                message.setContent(multipart);

                Transport transport = session.getTransport("smtp");
                transport.connect(username, password);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Archivos enviados al correo " + solicitudTrabajador.getTrabajadorId().getEmail1()));

            } catch (MessagingException | UnsupportedEncodingException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error al enviar correo"));
            } catch (IOException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error al enviar mensaje"));
            }
        }
    }

    public String parseDate(Date fecha1, String formato) {
        return new SimpleDateFormat(formato).format(fecha1);
    }

    public String estatusCfdi(RnGcNomSolicitudTrabajadorTbl solicitudTrabajador) {
        RnGcNomTrabajadorCfdisTbl trabajadorCFDILocal = new RnGcNomTrabajadorCfdisTbl();
        trabajadorCFDILocal = trabajadorCfdiFacade.obtenerXSoliTrabajdor(solicitudTrabajador);
        String estado = null;
        if (trabajadorCFDILocal != null && trabajadorCFDILocal.getCfdiId() != null
                && trabajadorCFDILocal.getCfdiId().getRespuestaTimbrado().equals("Timbrado de forma correcta")) {
            System.out.println("estatusCfdi: " + trabajadorCFDILocal.getCfdiId());
            estado = trabajadorCFDILocal.getCfdiId().getRespuestaTimbrado();
        } else if (trabajadorCFDILocal != null && trabajadorCFDILocal.getCfdiId() != null
                && trabajadorCFDILocal.getCfdiId().getRespuestaTimbrado().contains("UUID CANCELADO CORRECTAMENTE")) {
            estado = "UUID CANCELADO CORRECTAMENTE";
        } else {
            estado = "No Timbrado";
        }
        return estado;
    }

    public void descargarPDF(RnGcNomSolicitudTrabajadorTbl solicitudTrabajador) {
        RnGcNomTrabajadorCfdisTbl trabajadorCFDILocal = new RnGcNomTrabajadorCfdisTbl();
        trabajadorCFDILocal = trabajadorCfdiFacade.obtenerXSoliTrabajdor(solicitudTrabajador);
        System.out.println("DescargarPDF1: " + solicitudTrabajador + " | " + trabajadorCFDILocal);
        if (trabajadorCFDILocal.getCfdiId() != null) {
            RnGcArchivosTbl archivos = new RnGcArchivosTbl();
            archivos = archivoFacade.obtenerArchivo(trabajadorCFDILocal.getCfdiId());
            System.out.println("DescargarPDF2: " + archivos);
            if (archivos != null && archivos.getArchivoPdf() != null) {
                InputStream streamPlantilla = new ByteArrayInputStream(archivos.getArchivoPdf());
                downLoadFile = new DefaultStreamedContent(streamPlantilla, "document/pdf",
                        "Nomina" + solicitudTrabajador.getTrabajadorId().getNombre() + "_" + trabajadorCFDILocal.getCfdiId().getUuid()
                        + "_" + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()).concat(".pdf"));
            }
        }
    }

    public void enviarCorreo(RnGcNomSolicitudTrabajadorTbl solicitudTrabajador) {
        System.out.println("solicitudTrabajadorEnviarCoreo: " + solicitudTrabajador);
        if (solicitudTrabajador != null && solicitudTrabajador.getTrabajadorId().getEmail1() != null
                && solicitudTrabajador.getTrabajadorId().getEmail1().contains("@")) {
            RnGcNomTrabajadorCfdisTbl trabajadorCFDI = new RnGcNomTrabajadorCfdisTbl();
            RnGcArchivosTbl archivos = new RnGcArchivosTbl();
            RnGcUsuariosTbl emisor = new RnGcUsuariosTbl();
            trabajadorCFDI = trabajadorCfdiFacade.obtenerXSoliTrabajdor(solicitudTrabajador);
            archivos = archivoFacade.obtenerArchivo(trabajadorCFDI.getCfdiId());
            emisor = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());

            final String username = "conta-arc@rocketnegocios.com.mx";
            final String password = "ga1mJ7$4";
            Properties props = new Properties();
            props.put("mail.smtp.host", "rocketnegocios.com.mx");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.ssl.trust", "*");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            MimeMessage message = new MimeMessage(session);
            try {
                session.getProperties().put("mail.smtp.strattls.enable", "true");

                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setText(solicitudTrabajador.getSolicitudId().getPeriodoNominaId().getNominaId().getNombreNomina());

                FileOutputStream fos = new FileOutputStream(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/archivoPdf.pdf")));
                fos.write(archivos.getArchivoPdf());
                fos.close();
                MimeBodyPart mimeArchivoPdf = new MimeBodyPart();
                mimeArchivoPdf.attachFile(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/archivoPdf.pdf")));

                fos = new FileOutputStream(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/xmlTimbrado.xml")));
                fos.write(archivos.getArchivoXml());
                fos.close();
                MimeBodyPart mimeArchivoXml = new MimeBodyPart();
                mimeArchivoXml.attachFile(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/xmlTimbrado.xml")));

                fos = new FileOutputStream(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/qr.png")));
                fos.write(archivos.getArchivoQR());
                fos.close();
                MimeBodyPart mimeArchivoQR = new MimeBodyPart();
                mimeArchivoQR.attachFile(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/qr.png")));

                MimeMultipart multipart = new MimeMultipart();
                multipart.addBodyPart(mimeBodyPart);
                multipart.addBodyPart(mimeArchivoPdf);
                multipart.addBodyPart(mimeArchivoXml);
                multipart.addBodyPart(mimeArchivoQR);

                message.setFrom(new InternetAddress(username, emisor.getNombreCompleto()));
                message.setSubject(solicitudTrabajador.getSolicitudId().getPeriodoNominaId().getNominaId().getNombreNomina());

                message.addRecipients(Message.RecipientType.TO, solicitudTrabajador.getTrabajadorId().getEmail1());
                message.setContent(multipart);

                Transport transport = session.getTransport("smtp");
                transport.connect(username, password);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Archivos enviados al correo " + solicitudTrabajador.getTrabajadorId().getEmail1()));

            } catch (MessagingException | UnsupportedEncodingException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error al enviar correo"));
            } catch (IOException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error al enviar mensaje"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("El correo " + solicitudTrabajador.getTrabajadorId().getEmail1() + " no es válido"));
        }
    }

    public void cancelarTimbre(RnGcNomSolicitudTrabajadorTbl solicitudTrabajador) {
        System.out.println("Validacion: " + (solicitudTrabajador != null) + " | " + (cfdisId2.getCertificados_Id() != null)
                + " | " + cfdisId2.getClaveRegimenFiscal() + " | " + (cfdisId2.getLugarExpedicion() > 0));
        if (solicitudTrabajador != null && cfdisId2.getCertificados_Id() != null
                && cfdisId2.getClaveRegimenFiscal() != null && cfdisId2.getLugarExpedicion() > 0) {
            RnGcUsuariosTbl usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            List<RnGcTimbresTbl> timbresId = timbresFacade.obtenerTimbre("Proveedor", usuarioId);
            if (timbresId != null && !timbresId.isEmpty()) {
                RnGcNomTrabajadorCfdisTbl trabajadorCFDI = new RnGcNomTrabajadorCfdisTbl();
                trabajadorCFDI = trabajadorCfdiFacade.obtenerXSoliTrabajdor(solicitudTrabajador);
                RnGcCertificadosTbl certificado = new RnGcCertificadosTbl();
                certificado = cfdisId2.getCertificados_Id();
                RnGcCfdisTbl cfdiCancela = new RnGcCfdisTbl();
                cfdiCancela = trabajadorCFDI.getCfdiId();
                try {
                    File cert = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/certificado.cer"));
                    FileUtils.writeByteArrayToFile(cert, certificado.getCertificadoSelloDigital());
                    System.out.println("cert: " + cert.getPath());

                    File key = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/llavePrivada.key"));
                    FileUtils.writeByteArrayToFile(key, certificado.getLlavePrivada());
                    System.out.println("key: " + key.getPath());

                    //Sefactura sf = new Sefactura("http://www.jonima.com.mx:3014", "VICA840114RZ4", "VICA840114RZ4");
                    Sefactura sf = new Sefactura("https://www.sefactura.com.mx", "AFC060520V16", "AFC060520V16"); //Produccion
                    String respuesta = sf.cancela(trabajadorCFDI.getCfdiId().getUuid(), key.getPath(), cert.getPath(), certificado.getContraseniaLlavePrivada());
                    cfdiCancela.setRespuestaTimbrado(respuesta);
                    cfdiCancela.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                    cfdiCancela.setUltimaFechaActualizacion(new Date());
                    cfdisFacade.edit(cfdiCancela);
                    seleccionados = new ArrayList<>();
                    System.out.println("Respuesta Cancelacion: " + respuesta);
                    timbresId.get(0).setTimbresRestantes(timbresId.get(0).getTimbresRestantes() - 1);
                    timbresId.get(0).setTimbresUsados(timbresId.get(0).getTimbresUsados() + 1);
                    timbresFacade.edit(timbresId.get(0));
                    JsfUtil.addSuccessMessage("Se realizo la cancelación de manera correcta");
                } catch (Exception ex) {
                    System.out.println("Error durante la cancelacion del timbre: " + ex.getLocalizedMessage());
                }
            } else {
                JsfUtil.addErrorMessage("Se han terminado los timbres que tenias asignados");
            }
        } else {
            System.out.println("No se realizo el timbrado. No se escogio algun dato");
            JsfUtil.addErrorMessage("Se esperan los siguientes datos: Clave Regimen Fiscal, Certificado, Lugar Expedicion");
        }

    }

}
