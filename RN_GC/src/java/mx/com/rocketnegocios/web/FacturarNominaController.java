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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import mx.com.rocketnegocios.beans.RnGcCfdisLineasTblFacade;
import mx.com.rocketnegocios.beans.RnGcCfdisTblFacade;
import mx.com.rocketnegocios.beans.RnGcDireccionesUsuariosTblFacade;
import mx.com.rocketnegocios.beans.RnGcPersonasTblFacade;
import mx.com.rocketnegocios.beans.RnGcTipospersonasTblFacade;
import mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade;
import mx.com.rocketnegocios.entities.RnGcCfdisLineasTbl;
import mx.com.rocketnegocios.entities.RnGcCfdisTbl;
import mx.com.rocketnegocios.entities.RnGcDireccionesUsuariosTbl;
import mx.com.rocketnegocios.entities.RnGcPersonasTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import mx.com.rocketnegocios.web.util.JsfUtil;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import mx.com.rocketnegocios.beans.RnGcArchivosTblFacade;
import mx.com.rocketnegocios.beans.RnGcCertificadosTblFacade;
import mx.com.rocketnegocios.entities.RnGcArchivosTbl;
import mx.com.rocketnegocios.entities.RnGcCertificadosTbl;
import org.apache.commons.ssl.PKCS8Key;
import org.w3c.dom.NodeList;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;
import mx.com.rocketnegocios.beans.RnGcDocumentosRelacionadosTblFacade;
import mx.com.rocketnegocios.beans.RnGcFirmasTblFacade;
import mx.com.rocketnegocios.beans.RnGcFolioserieTblFacade;
import mx.com.rocketnegocios.beans.RnGcImagenesTblFacade;
import mx.com.rocketnegocios.beans.RnGcImpuestosCfdisTblFacade;
import mx.com.rocketnegocios.beans.RnGcImpuestosLocalesTblFacade;
import mx.com.rocketnegocios.beans.RnGcPagosRelacionadosTblFacade;
import mx.com.rocketnegocios.beans.RnGcProductserviciosTblFacade;
import mx.com.rocketnegocios.beans.RnGcTimbresTblFacade;
import mx.com.rocketnegocios.beans.RnGcTrabajadoresTblFacade;
import mx.com.rocketnegocios.entities.RnGcDocumentosRelacionadosTbl;
import mx.com.rocketnegocios.entities.RnGcFirmasTbl;
import mx.com.rocketnegocios.entities.RnGcFolioserieTbl;
import mx.com.rocketnegocios.entities.RnGcImagenesTbl;
import mx.com.rocketnegocios.entities.RnGcImpuestosCfdisTbl;
import mx.com.rocketnegocios.entities.RnGcImpuestosLocalesTbl;
import mx.com.rocketnegocios.entities.RnGcPagosRelacionadosTbl;
import mx.com.rocketnegocios.entities.RnGcProductserviciosTbl;
import mx.com.rocketnegocios.entities.RnGcTimbresTbl;
import mx.com.rocketnegocios.entities.RnGcTrabajadoresTbl;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@SessionScoped
@Named("facturarNominaContoller")
public class FacturarNominaController implements Serializable {

    private static final String ORIGINAL
            = "ÁáÉéÍíÓóÚúÑñÜü";
    private static final String REPLACEMENT
            = "AaEeIiOoUuNnUu";

    public FacturarNominaController() {
        /*this.listaUsuarios = null;
        if (cfdisId == null) {
            cfdisId = new RnGcCfdisTbl();
        }

        if (lineas == null) {
            lineas = new RnGcCfdisLineasTbl();
        }

        if (personas == null) {
            personas = new RnGcPersonasTbl();
        }*/
    }

    @EJB
    private RnGcCfdisTblFacade cfdisFacade;

    @EJB
    private RnGcCfdisLineasTblFacade lineasCfdisFacade;

    @EJB
    private RnGcUsuariosTblFacade usuariosFacade;

    @EJB
    private RnGcTipospersonasTblFacade tiposPersonasFacade;

    @EJB
    private RnGcPersonasTblFacade personasFacade;

    @EJB
    private RnGcDireccionesUsuariosTblFacade dirUsuarioFacade;

    @EJB
    private RnGcArchivosTblFacade archivosFacade;

    @EJB
    private RnGcCertificadosTblFacade certificadosTbl;

    @EJB
    private RnGcFolioserieTblFacade folioSerieFacade;

    @EJB
    private RnGcProductserviciosTblFacade producServicioFacade;

    @EJB
    private RnGcTimbresTblFacade timbresFacade;

    @EJB
    private RnGcFirmasTblFacade firmasFacade;

    @EJB
    private RnGcImpuestosLocalesTblFacade impLocalesFacade;

    @EJB
    private RnGcImpuestosCfdisTblFacade impCfdisFacade;

    @EJB
    private RnGcDocumentosRelacionadosTblFacade docsRelacionadosFacade;

    @EJB
    private RnGcPagosRelacionadosTblFacade pagorelacionadoFacade;

    @EJB
    private RnGcImagenesTblFacade imagenesFacade;

    @EJB
    private RnGcTrabajadoresTblFacade trabajadoresFacade;

    private List<RnGcCfdisLineasTbl> cfdisLineas = new ArrayList<>();
    private RnGcCfdisTbl cfdisId;
    private RnGcCfdisLineasTbl lineas;
    private RnGcCfdisLineasTbl selectedLinea;
    private RnGcCfdisTbl complementoPago;
    private RnGcCfdisTbl complementoPago2;
    private RnGcPersonasTbl personas;
    private RnGcPersonasTbl personas2;
    private RnGcPersonasTbl personas3;
    private final UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private RnGcUsuariosTbl usuario = new RnGcUsuariosTbl();
    private List<RnGcUsuariosTbl> listaUsuarios;
    private List<RnGcDireccionesUsuariosTbl> listaDirUsuario;
    private List<RnGcCfdisLineasTbl> itemsFactura = null;
    private RnGcArchivosTbl archivo = new RnGcArchivosTbl();
    private RnGcCertificadosTbl certificado;
    private List<RnGcFolioserieTbl> itemsFolio;
    private RnGcFolioserieTbl folio;
    private RnGcProductserviciosTbl producServicio;
    private RnGcProductserviciosTbl producServicio2;
    private List<RnGcProductserviciosTbl> listaProductosServicios;
    private List<RnGcPersonasTbl> listaPersonas;
    private List<RnGcTimbresTbl> timbres;
    private List<RnGcTimbresTbl> listaTimbres;
    private RnGcCfdisLineasTbl lineasSelected;
    private List<RnGcCfdisTbl> listaCfdis = null;
    private List<RnGcCfdisTbl> listaCfdisSeleccion = null;
    private RnGcCfdisTbl cfdiUuid = null;
    private List<RnGcCfdisTbl> listaCfdisRelacionados;
    private List<RnGcCfdisTbl> listaComplemntosPago;
    private RnGcCfdisTbl cfdiRelacionado;
    private RnGcCfdisTbl cfdi2;
    private Double saldoPagar = 0.0;
    private Double monto = 0.0;
    private Double montoTotal = 0.0;
    private int parcialidad = 0;
    private Double saldoIinsoluto = 0.0;
    private RnGcFirmasTbl firmas;
    private List<RnGcImpuestosLocalesTbl> listaImpLocales;
    private RnGcImpuestosLocalesTbl impLocales;
    private RnGcImpuestosLocalesTbl impLocales2;
    private RnGcImpuestosCfdisTbl impuestosCfdis;
    private List<RnGcImpuestosCfdisTbl> listaImpuestosCfdis = null;
    private List<RnGcImpuestosCfdisTbl> listaImpuestosCfdisXLinea;
    private RnGcDocumentosRelacionadosTbl docsRelacionados;
    private RnGcPagosRelacionadosTbl pagoRelacionado;
    private List<RnGcPagosRelacionadosTbl> listaPagosRelacionado;
    private RnGcTrabajadoresTbl trabajador = new RnGcTrabajadoresTbl();
    private RnGcTrabajadoresTbl trabajador2 = new RnGcTrabajadoresTbl();
    private RnGcTrabajadoresTbl trabajador3 = new RnGcTrabajadoresTbl();
    private List<RnGcTrabajadoresTbl> listaTrabajadores;
    private List<RnGcTrabajadoresTbl> listaTrabajadoresAux;
    private StreamedContent downLoadFile;

    public Double getSaldoIinsoluto() {
        return saldoIinsoluto;
    }

    public void setSaldoIinsoluto(Double saldoIinsoluto) {
        this.saldoIinsoluto = saldoIinsoluto;
    }

    public RnGcCfdisTbl getComplementoPago() {
        return complementoPago;
    }

    public void setComplementoPago(RnGcCfdisTbl complementoPago) {
        this.complementoPago = complementoPago;
    }

    public RnGcCfdisTbl getComplementoPago2() {
        if (complementoPago2 == null) {
            this.complementoPago2 = new RnGcCfdisTbl();
        }
        return complementoPago2;
    }

    public void setComplementoPago2(RnGcCfdisTbl complementoPago2) {
        this.complementoPago2 = complementoPago2;
    }

    public List<RnGcCfdisTbl> getListaCfdisSeleccion() {
        if (listaCfdisSeleccion == null) {
            this.listaCfdisSeleccion = new ArrayList<>();
        }
        return listaCfdisSeleccion;
    }

    public void setListaCfdisSeleccion(List<RnGcCfdisTbl> listaCfdisSeleccion) {
        this.listaCfdisSeleccion = listaCfdisSeleccion;
    }

    public RnGcCfdisLineasTbl getSelectedLinea() {
        if (selectedLinea == null) {
            this.selectedLinea = new RnGcCfdisLineasTbl();
        }
        return selectedLinea;
    }

    public void setSelectedLinea(RnGcCfdisLineasTbl selectedLinea) {
        this.selectedLinea = selectedLinea;
    }

    public List<RnGcImpuestosCfdisTbl> getListaImpuestosCfdisXLinea() {
        if (listaImpuestosCfdisXLinea == null) {
            this.listaImpuestosCfdisXLinea = new ArrayList<>();
        }
        return listaImpuestosCfdisXLinea;
    }

    public void setListaImpuestosCfdisXLinea(List<RnGcImpuestosCfdisTbl> listaImpuestosCfdisXLinea) {
        this.listaImpuestosCfdisXLinea = listaImpuestosCfdisXLinea;
    }

    public RnGcDocumentosRelacionadosTbl getDocsRelacionados() {
        if (docsRelacionados == null) {
            this.docsRelacionados = new RnGcDocumentosRelacionadosTbl();
        }
        return docsRelacionados;
    }

    public void setDocsRelacionados(RnGcDocumentosRelacionadosTbl docsRelacionados) {
        this.docsRelacionados = docsRelacionados;
    }

    public List<RnGcImpuestosCfdisTbl> getListaImpuestosCfdis() {
        if (listaImpuestosCfdis == null) {
            this.listaImpuestosCfdis = new ArrayList<>();
        }
        return listaImpuestosCfdis;
    }

    public void setListaImpuestosCfdis(List<RnGcImpuestosCfdisTbl> listaImpuestosCfdis) {
        this.listaImpuestosCfdis = listaImpuestosCfdis;
    }

    public RnGcImpuestosCfdisTbl getImpuestosCfdis() {
        if (impuestosCfdis == null) {
            this.impuestosCfdis = new RnGcImpuestosCfdisTbl();
        }
        return impuestosCfdis;
    }

    public void setImpuestosCfdis(RnGcImpuestosCfdisTbl impuestosCfdis) {
        this.impuestosCfdis = impuestosCfdis;
    }

    public RnGcImpuestosLocalesTbl getImpLocales2() {
        if (impLocales2 == null) {
            this.impLocales2 = new RnGcImpuestosLocalesTbl();
        }
        return impLocales2;
    }

    public void setImpLocales2(RnGcImpuestosLocalesTbl impLocales2) {
        this.impLocales2 = impLocales2;
    }

    public RnGcImpuestosLocalesTbl getImpLocales() {
        if (impLocales == null) {
            this.impLocales = new RnGcImpuestosLocalesTbl();
        }
        return impLocales;
    }

    public void setImpLocales(RnGcImpuestosLocalesTbl impLocales) {
        this.impLocales = impLocales;
    }

    public List<RnGcImpuestosLocalesTbl> getListaImpLocales() {
        if (listaImpLocales == null) {
            this.listaImpLocales = new ArrayList<>();
        }
        return listaImpLocales;
    }

    public void setListaImpLocales(List<RnGcImpuestosLocalesTbl> listaImpLocales) {
        this.listaImpLocales = listaImpLocales;
    }

    public RnGcCfdisTbl getCfdisId() {
        if (cfdisId == null) {
            this.cfdisId = new RnGcCfdisTbl();
        }
        return cfdisId;
    }

    public RnGcFirmasTbl getFirmas() {
        if (firmas == null) {
            this.firmas = new RnGcFirmasTbl();
        }
        return firmas;
    }

    public void setFirmas(RnGcFirmasTbl firmas) {
        this.firmas = firmas;
    }

    public Double getSaldoPagar() {
        return saldoPagar;
    }

    public void setSaldoPagar(Double saldoPagar) {
        this.saldoPagar = saldoPagar;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public int getParcialidad() {
        return parcialidad;
    }

    public void setParcialidad(int parcialidad) {
        this.parcialidad = parcialidad;
    }

    public List<RnGcPersonasTbl> getListaPersonas() {
        if (listaPersonas == null) {
            this.listaPersonas = new ArrayList<>();
        }
        return listaPersonas;
    }

    public void setListaPersonas(List<RnGcPersonasTbl> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    public RnGcCfdisLineasTbl getLineasSelected() {
        if (lineasSelected == null) {
            lineasSelected = new RnGcCfdisLineasTbl();
        }
        return lineasSelected;
    }

    public void setLineasSelected(RnGcCfdisLineasTbl lineasSelected) {
        this.lineasSelected = lineasSelected;
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

    public List<RnGcCfdisTbl> getListaComplemntosPago() {
        if (listaComplemntosPago == null) {
            this.listaComplemntosPago = new ArrayList<>();
        }
        System.out.println("listaComplemntosPago: " + listaComplemntosPago);
        return listaComplemntosPago;
    }

    public void setListaComplemntosPago(List<RnGcCfdisTbl> listaComplemntosPago) {
        this.listaComplemntosPago = listaComplemntosPago;
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

    public RnGcCfdisLineasTblFacade getLineasCfdisFacade() {
        return lineasCfdisFacade;
    }

    public List<RnGcFolioserieTbl> getItemsFolio() {
        if (itemsFolio == null) {
            this.itemsFolio = new ArrayList<>();
        }
        return itemsFolio;
    }

    public void setItemsFolio(List<RnGcFolioserieTbl> itemsFolio) {
        this.itemsFolio = itemsFolio;
    }

    public RnGcFolioserieTbl getFolio() {
        if (folio == null) {
            this.folio = new RnGcFolioserieTbl();
        }
        return folio;
    }

    public void setFolio(RnGcFolioserieTbl folio) {
        this.folio = folio;
    }

    public void setLineasCfdisFacade(RnGcCfdisLineasTblFacade lineasCfdisFacade) {
        this.lineasCfdisFacade = lineasCfdisFacade;
    }

    public void setCfdisId(RnGcCfdisTbl cfdisId) {
        this.cfdisId = cfdisId;
    }

    public RnGcCfdisLineasTbl getLineas() {
        if (lineas == null) {
            this.lineas = new RnGcCfdisLineasTbl();
        }
        return lineas;
    }

    public void setLineas(RnGcCfdisLineasTbl lineas) {
        this.lineas = lineas;
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

    public RnGcArchivosTbl getArchivo() {
        if (archivo == null) {
            this.archivo = new RnGcArchivosTbl();
        }
        return archivo;
    }

    public RnGcArchivosTblFacade getArchivosFacade() {
        return archivosFacade;
    }

    public void setArchivosFacade(RnGcArchivosTblFacade archivosFacade) {
        this.archivosFacade = archivosFacade;
    }

    public void setArchivo(RnGcArchivosTbl archivo) {
        this.archivo = archivo;
    }

    public RnGcCertificadosTbl getCertificado() {
        return certificado;
    }

    public void setCertificado(RnGcCertificadosTbl certificado) {
        this.certificado = certificado;
    }

    public RnGcPersonasTbl getPersonas() {
        if (personas == null) {
            this.personas = new RnGcPersonasTbl();
        }
        return personas;
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

    public void setPersonas(RnGcPersonasTbl personas) {
        this.personas = personas;
    }

    public RnGcPersonasTbl getPersonas2() {
        return personas2;
    }

    public void setPersonas2(RnGcPersonasTbl personas2) {
        this.personas2 = personas2;
    }

    public RnGcPersonasTbl getPersonas3() {
        return personas3;
    }

    public void setPersonas3(RnGcPersonasTbl personas3) {
        this.personas3 = personas3;
    }

    public List<RnGcCfdisLineasTbl> getCfdisLineas() {
        if (cfdisLineas == null) {
            this.cfdisLineas = new ArrayList<>();
        }
        return cfdisLineas;
    }

    public void setCfdisLineas(List<RnGcCfdisLineasTbl> cfdisLineas) {
        this.cfdisLineas = cfdisLineas;
    }

    public List<RnGcUsuariosTbl> getListaUsuarios() {
        if (listaUsuarios == null) {
            this.listaUsuarios = new ArrayList<>();
        }
        return listaUsuarios;
    }

    public void setListaUsuarios(List<RnGcUsuariosTbl> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public void createFactura() throws Exception {
        System.out.println("createFactura");
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCfdisTblCreated"));
    }

    private void persist(PersistAction persistAction, String successMessage) throws Exception {
        if (cfdisId != null && personas != null) {
            setEmbeddableKeysExist();
            try {
                if (persistAction != PersistAction.DELETE) {
                    usuario = usuariosFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
                    System.out.println("Usuario: " + usuario + " | " + usuarioFirmado.obtenerIdUsuario()
                            + " | " + cfdisId.getProveedorTimbres());
                    timbres = timbresFacade.obtenerTimbre("Proveedor", usuario);
                    System.out.println("Timbres: " + timbres);
                    if (timbres != null && !timbres.isEmpty()) {
                        if (timbres.get(0).getTimbresRestantes() > 0) {
                            timbres.get(0).setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                            timbres.get(0).setUltimaFechaActualizacion(new Date());
                            if (crearXML()) {
                                try {
                                    System.out.println("Timbrado Correctamente");
                                    cfdisId = cfdisFacade.refreshFromDB(cfdisId);
                                    persistComplemento(persistAction.UPDATE, "PPD Actualizado");
                                    archivo.setCfdiId(cfdisId);
                                    archivo = archivosFacade.refreshFromDB(archivo);
                                    if (listaCfdisRelacionados != null && listaCfdisRelacionados.size() > 0) {
                                        for (int i = 0; i < listaCfdisRelacionados.size(); i++) {
                                            docsRelacionados.setIdDocumento(listaCfdisRelacionados.get(i).getUuid());
                                            docsRelacionados.setSerie(listaCfdisRelacionados.get(i).getSerie());
                                            docsRelacionados.setFolio(String.valueOf(listaCfdisRelacionados.get(i).getFolio()));
                                            docsRelacionados.setMoneda(listaCfdisRelacionados.get(i).getMoneda());
                                            docsRelacionados.setTipoCambio(listaCfdisRelacionados.get(i).getTipoCambio());
                                            docsRelacionados.setMetodoPago(listaCfdisRelacionados.get(i).getMetodoPago());
                                            docsRelacionados.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
                                            docsRelacionados.setFechaCreacion(new Date());
                                            docsRelacionados.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                                            docsRelacionados.setUltimaFechaActualizacion(new Date());
                                            docsRelacionados.setCfdisId(cfdisId);
                                            docsRelacionadosFacade.edit(docsRelacionados);
                                            docsRelacionados = new RnGcDocumentosRelacionadosTbl();
                                        }
                                    }
                                    if (crearFirmas()) {
                                        firmas.setCfdiId(cfdisId);
                                        firmasFacade.edit(firmas);
                                    }
                                    saldoPagar = 0.0;
                                    parcialidad = 0;
                                    saldoIinsoluto = 0.0;
                                    if (cfdisId.getSerie() != null && cfdisId.getFolio() != null) {
                                        folio = obtenerFolioPorUsuarioSerie(cfdisId.getSerie()).get(0);
                                        folio.setFolio(folio.getFolio() + 1);
                                        folio.setUltimaFechaActualizacion(new Date());
                                        folioSerieFacade.edit(folio);
                                        itemsFolio.clear();
                                    }
                                    if (cfdisId.getTipoComprobante().equals("I") || cfdisId.getTipoComprobante().equals("E")
                                            || cfdisId.getTipoComprobante().equals("T")) {
                                        for (int z = 0; z < cfdisLineas.size(); z++) {
                                            if (!obtenerProductos(cfdisLineas.get(z))) {
                                                producServicioFacade.edit(producServicio);
                                            }
                                        }
                                    }
                                    for (int i = 0; i < cfdisLineas.size(); i++) {
                                        cfdisLineas.get(i).setCfdisId(cfdisId);
                                        lineasCfdisFacade.edit(cfdisLineas.get(i));
                                    }
                                    timbres.get(0).setTimbresUsados(timbres.get(0).getTimbresUsados() + 1);
                                    timbres.get(0).setTimbresRestantes(timbres.get(0).getTimbresRestantes() - 1);
                                    timbresFacade.edit(timbres.get(0));
                                    enviarCorreo(archivo);
                                    producServicio = new RnGcProductserviciosTbl();
                                    lineas = new RnGcCfdisLineasTbl();
                                    personas = new RnGcPersonasTbl();
                                    firmas = new RnGcFirmasTbl();
                                    cfdisLineas = new ArrayList<>();
                                    itemsFactura = new ArrayList<>();
                                    cfdisId = new RnGcCfdisTbl();
                                    listaCfdisRelacionados = new ArrayList<>();;
                                    listaCfdis = new ArrayList<>();
                                    listaImpuestosCfdis = new ArrayList<>();
                                    listaImpuestosCfdisXLinea = new ArrayList<>();
                                    timbres = new ArrayList<>();
                                    JsfUtil.addErrorMessage("Timbrado correctamente");
                                } catch (Exception ex) {
                                    producServicio = new RnGcProductserviciosTbl();
                                    lineas = new RnGcCfdisLineasTbl();
                                    personas = new RnGcPersonasTbl();
                                    firmas = new RnGcFirmasTbl();
                                    cfdisLineas = new ArrayList<>();
                                    itemsFactura = new ArrayList<>();
                                    cfdisId.setFolio(null);
                                    cfdisId = cfdisFacade.refreshFromDB(cfdisId);
                                    cfdisId = new RnGcCfdisTbl();
                                    listaCfdisRelacionados = new ArrayList<>();
                                    listaCfdis = new ArrayList<>();
                                    listaImpuestosCfdis = new ArrayList<>();
                                    listaImpuestosCfdisXLinea = new ArrayList<>();
                                    JsfUtil.addErrorMessage("Error al intentar el timbrado");
                                }
                            } else {
                                try {
                                    JsfUtil.addErrorMessage("Error al intentar el timbrado");
                                    System.out.println(cfdisId.getRespuestaTimbrado());
                                    cfdisId.setFolio(null);
                                    cfdisId = cfdisFacade.refreshFromDB(cfdisId);
                                    if (cfdisId.getSerie() != null && cfdisId.getFolio() != null) {
                                        folio = obtenerFolioPorUsuarioSerie(cfdisId.getSerie()).get(0);
                                        folio.setFolio(folio.getFolio());
                                        folio.setUltimaFechaActualizacion(new Date());
                                        folioSerieFacade.edit(folio);
                                        itemsFolio.clear();
                                    }
                                    for (int i = 0; i < cfdisLineas.size(); i++) {
                                        cfdisLineas.get(i).setCfdisId(cfdisId);
                                        lineasCfdisFacade.edit(cfdisLineas.get(i));
                                    }
                                    if (cfdisId.getTipoComprobante().equals("I") || cfdisId.getTipoComprobante().equals("E")
                                            || cfdisId.getTipoComprobante().equals("T")) {
                                        for (int z = 0; z < cfdisLineas.size(); z++) {
                                            if (!obtenerProductos(cfdisLineas.get(z))) {
                                                producServicioFacade.edit(producServicio);
                                            }
                                        }
                                    }
                                    if (listaCfdisRelacionados != null && listaCfdisRelacionados.size() > 0) {
                                        for (int i = 0; i < listaCfdisRelacionados.size(); i++) {
                                            docsRelacionados.setIdDocumento(listaCfdisRelacionados.get(i).getUuid());
                                            docsRelacionados.setSerie(listaCfdisRelacionados.get(i).getSerie());
                                            docsRelacionados.setFolio(String.valueOf(listaCfdisRelacionados.get(i).getFolio()));
                                            docsRelacionados.setMoneda(listaCfdisRelacionados.get(i).getMoneda());
                                            docsRelacionados.setTipoCambio(listaCfdisRelacionados.get(i).getTipoCambio());
                                            docsRelacionados.setMetodoPago(listaCfdisRelacionados.get(i).getMetodoPago());
                                            docsRelacionados.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
                                            docsRelacionados.setFechaCreacion(new Date());
                                            docsRelacionados.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                                            docsRelacionados.setUltimaFechaActualizacion(new Date());
                                            docsRelacionados.setCfdisId(cfdisId);
                                            docsRelacionadosFacade.edit(docsRelacionados);
                                            docsRelacionados = new RnGcDocumentosRelacionadosTbl();
                                        }
                                    }
                                    if (crearFirmas()) {
                                        firmas.setCfdiId(cfdisId);
                                        firmasFacade.edit(firmas);
                                    }
                                    timbres.get(0).setTimbresUsados(timbres.get(0).getTimbresUsados() + 1);
                                    timbres.get(0).setTimbresRestantes(timbres.get(0).getTimbresRestantes() - 1);
                                    timbresFacade.edit(timbres.get(0));
                                    producServicio = new RnGcProductserviciosTbl();
                                    lineas = new RnGcCfdisLineasTbl();
                                    personas = new RnGcPersonasTbl();
                                    firmas = new RnGcFirmasTbl();
                                    cfdisLineas = new ArrayList<>();
                                    itemsFactura = new ArrayList<>();
                                    cfdisId = new RnGcCfdisTbl();
                                    listaCfdisRelacionados = new ArrayList<>();;
                                    listaCfdis = new ArrayList<>();
                                    listaImpuestosCfdis = new ArrayList<>();
                                    listaImpuestosCfdisXLinea = new ArrayList<>();
                                    timbres = new ArrayList<>();
                                } catch (Exception ex) {
                                    JsfUtil.addErrorMessage("Error al intentar el timbrado de la factura");
                                    producServicio = new RnGcProductserviciosTbl();
                                    lineas = new RnGcCfdisLineasTbl();
                                    personas = new RnGcPersonasTbl();
                                    firmas = new RnGcFirmasTbl();
                                    cfdisLineas = new ArrayList<>();
                                    itemsFactura = new ArrayList<>();
                                    cfdisId = new RnGcCfdisTbl();
                                    listaCfdisRelacionados = new ArrayList<>();;
                                    listaCfdis = new ArrayList<>();
                                    listaImpuestosCfdis = new ArrayList<>();
                                    listaImpuestosCfdisXLinea = new ArrayList<>();
                                }
                            }
                        } else {
                            JsfUtil.addErrorMessage("Los timbres que tenia asignados se han terminado");
                            producServicio = new RnGcProductserviciosTbl();
                            lineas = new RnGcCfdisLineasTbl();
                            personas = new RnGcPersonasTbl();
                            firmas = new RnGcFirmasTbl();
                            cfdisLineas = new ArrayList<>();
                            itemsFactura = new ArrayList<>();
                            cfdisId = new RnGcCfdisTbl();
                            listaCfdisRelacionados = new ArrayList<>();;
                            listaCfdis = new ArrayList<>();
                        }
                    } else {
                        JsfUtil.addErrorMessage("Los timbres que tenia asignados se han terminado");
                    }
                } else {
                    cfdisFacade.remove(cfdisId);
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured") + "adasdasdasd");
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    protected void setEmbeddableKeysExist() {
        System.out.println("setEmbeddableKeysExist()");
        cfdisId.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        cfdisId.setFechaCreacion(new Date());
        cfdisId.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        cfdisId.setUltimaFechaActualizacion(new Date());
        cfdisId.setRfcReceptor(personas.getRfc());
        cfdisId.setNombreReceptor(personas.getNombre());
        cfdisId.setCantidadPagada(0.0);
        archivo.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        archivo.setFechaCreacion(new Date());
        archivo.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        archivo.setUltimaFechaActualizacion(new Date());
    }

    public List<RnGcCfdisLineasTbl> getItemsFactura() {
        if (itemsFactura == null) {
            this.itemsFactura = new ArrayList<>();
        }
        if (itemsFactura != null) {
            if (cfdisId.getTipoComprobante() != null) {
                if (!cfdisId.getTipoComprobante().equals("N")) {
                    itemsFactura = cfdisLineas;
                }
            }
        }
        if (cfdisId.getTipoComprobante() != null) {
            if (cfdisId.getTipoComprobante().equals("N")) {
                this.itemsFactura = new ArrayList<>(1);
                this.cfdisLineas = new ArrayList<>(1);
                lineas.setId((int) (Math.random() * 999999999));
                lineas.setClaveProdServ("84111505");
                lineas.setCantidad(1.0);
                lineas.setClaveUnidad("ACT");
                lineas.setDescripcion("Pago de nómina");
                lineas.setValorUnit(0.0);
                lineas.setImporte(0.0);
                lineas.setBase(0.0);
                lineas.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
                lineas.setFechaCreacion(new Date());
                lineas.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                lineas.setUltimaFechaActualizacion(new Date());
                cfdisLineas.add(lineas);
                itemsFactura = cfdisLineas;
            }
        }
        //lineas = new RnGcCfdisLineasTbl();
        return itemsFactura;
    }

    public List<RnGcCfdisLineasTbl> agregarLinea() {
        System.out.println("agregarLinea: ");
        lineas.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        lineas.setFechaCreacion(new Date());
        lineas.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        lineas.setUltimaFechaActualizacion(new Date());
        lineas.setId((int) (Math.random() * 999999999));
        if (!obtenerProductos(lineas)) {
            producServicioFacade.edit(producServicio);
        }
        System.out.println("lineas: " + lineas.getId() + " | " + lineas);
        cfdisLineas.add(lineas);
        lineas = new RnGcCfdisLineasTbl();
        selectedLinea = null;
        producServicio = new RnGcProductserviciosTbl();
        System.out.println("lineas: " + lineas);
        return cfdisLineas;
    }

    public RnGcCfdisLineasTbl prepareCreateItems() {
        System.out.println("prepareCreateItems: " + lineas);
        lineas = new RnGcCfdisLineasTbl();
        System.out.println("prepareCreateItems2: " + lineas);
        return lineas;
    }

    public void iniciarLineas() {
        lineas = new RnGcCfdisLineasTbl();
    }

    @SuppressWarnings("empty-statement")
    public boolean obtenerClientes(String rfc) {
        List<RnGcPersonasTbl> listaPersonas = personasFacade.obtenerCreadoPor(usuarioFirmado.obtenerIdUsuario());;
        boolean valor = false;
        for (int i = 0; i < listaPersonas.size(); i++) {
            if (listaPersonas.get(i).getRfc().equals(rfc)) {
                System.out.println("La persona ya existe");
                valor = true;
                break;
            } else {
                System.out.println("La persona no esta registrada");
                valor = false;
            }
        }
        return valor;
    }

    public boolean obtenerProductos(RnGcCfdisLineasTbl cfdisLinea) {
        List<RnGcProductserviciosTbl> listaProductos = producServicioFacade.obtenerPorUsuario(usuarioFirmado.obtenerIdUsuario());
        producServicio = new RnGcProductserviciosTbl();
        boolean valor = false;
        if (listaProductos.size() > 0) {
            for (int j = 0; j < listaProductos.size(); j++) {
                if (listaProductos.get(j).getDescripcion().equals(cfdisLinea.getDescripcion())) {
                    valor = true;
                    break;
                } else {
                    producServicio.setTipoProdServ("Producto/Servicio");
                    producServicio.setClaveProductServ(cfdisLinea.getClaveProdServ());
                    producServicio.setNoIdentificacion(cfdisLinea.getNoIdentificacion());
                    producServicio.setClaveUnidad(cfdisLinea.getClaveUnidad());
                    producServicio.setUnidad(cfdisLinea.getUnidad());
                    producServicio.setDescripcion(cfdisLinea.getDescripcion());
                    producServicio.setValorunitario(cfdisLinea.getValorUnit());
                    producServicio.setTipoImpuesto(cfdisLinea.getTipoImpuesto());
                    producServicio.setImpuesto(String.valueOf(cfdisLinea.getImpuesto()));
                    producServicio.setTipofactor(cfdisLinea.getTipoFactor());
                    producServicio.setTipoTasa(cfdisLinea.getTipoTasa());
                    producServicio.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
                    producServicio.setFechaCreacion(new Date());
                    producServicio.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                    producServicio.setUltimaFechaActualizacion(new Date());
                    producServicio.setTipoImpuesto2(cfdisLinea.getTipoImpuesto2());//--------2
                    producServicio.setImpuesto2(cfdisLinea.getImpuesto2());
                    producServicio.setTipoFactor2(cfdisLinea.getTipoFactor2());
                    producServicio.setTipoTasa2(cfdisLinea.getTipoTasa2());
                    producServicio.setTipoImpuesto3(cfdisLinea.getTipoImpuesto3());//--------3
                    producServicio.setImpuesto3(cfdisLinea.getImpuesto3());
                    producServicio.setTipoFactor3(cfdisLinea.getTipoFactor3());
                    producServicio.setTipoTasa3(cfdisLinea.getTipoTasa3());
                    producServicio.setTipoImpuesto4(cfdisLinea.getTipoImpuesto4());//--------4
                    producServicio.setImpuesto4(cfdisLinea.getImpuesto4());
                    producServicio.setTipoFactor4(cfdisLinea.getTipoFactor4());
                    producServicio.setTipoTasa4(cfdisLinea.getTipoTasa4());
                }
            }
        } else {
            producServicio.setTipoProdServ("Producto/Servicio");
            producServicio.setClaveProductServ(cfdisLinea.getClaveProdServ());
            producServicio.setNoIdentificacion(cfdisLinea.getNoIdentificacion());
            producServicio.setClaveUnidad(cfdisLinea.getClaveUnidad());
            producServicio.setUnidad(cfdisLinea.getUnidad());
            producServicio.setDescripcion(cfdisLinea.getDescripcion());
            producServicio.setValorunitario(cfdisLinea.getValorUnit());
            producServicio.setTipoImpuesto(cfdisLinea.getTipoImpuesto());
            producServicio.setImpuesto(String.valueOf(cfdisLinea.getImpuesto()));
            producServicio.setTipofactor(cfdisLinea.getTipoFactor());
            producServicio.setTipoTasa(cfdisLinea.getTipoTasa());
            producServicio.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
            producServicio.setFechaCreacion(new Date());
            producServicio.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
            producServicio.setUltimaFechaActualizacion(new Date());
            producServicio.setTipoImpuesto2(cfdisLinea.getTipoImpuesto2());//--------2
            producServicio.setImpuesto2(cfdisLinea.getImpuesto2());
            producServicio.setTipoFactor2(cfdisLinea.getTipoFactor2());
            producServicio.setTipoTasa3(cfdisLinea.getTipoTasa2());
            producServicio.setTipoImpuesto3(cfdisLinea.getTipoImpuesto3());//--------3
            producServicio.setImpuesto3(cfdisLinea.getImpuesto3());
            producServicio.setTipoFactor3(cfdisLinea.getTipoFactor3());
            producServicio.setTipoTasa3(cfdisLinea.getTipoTasa3());
            producServicio.setTipoImpuesto4(cfdisLinea.getTipoImpuesto4());//--------4
            producServicio.setImpuesto4(cfdisLinea.getImpuesto4());
            producServicio.setTipoFactor4(cfdisLinea.getTipoFactor4());
            producServicio.setTipoTasa4(cfdisLinea.getTipoTasa4());
        }
        return valor;
    }

    public List<RnGcUsuariosTbl> obtenerUsuario() {
        listaUsuarios = usuariosFacade.obtenerListaUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        return listaUsuarios;
    }

    public int obtenerIdUsuario() {
        listaUsuarios = usuariosFacade.obtenerListaUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        return listaUsuarios.get(0).getId();
    }

    public String obtenerRFCUsuario() {
        System.out.println("id:" + usuarioFirmado.obtenerIdUsuario());
        listaUsuarios = usuariosFacade.obtenerListaUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        return listaUsuarios.get(0).getRfc();
    }

    public String obtenerNombreCompletoUsuario() {
        listaUsuarios = usuariosFacade.obtenerListaUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        return listaUsuarios.get(0).getNombreCompleto();
    }

    public List<String> codigosPostalesUsuario() {
        List<String> listaCodigos = new ArrayList<>();
        usuario = usuariosFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        listaDirUsuario = dirUsuarioFacade.obtenerDireccionesporUsuario(usuario);
        listaCodigos.add(usuario.getCodigoPostal() + " - " + usuario.getProveedorId());
        for (int i = 0; i < listaDirUsuario.size(); i++) {
            listaCodigos.add(listaDirUsuario.get(i).getCodigoPostal() + " - " + listaDirUsuario.get(i).getTipo());
        }
        return listaCodigos;
    }

    public String calcularImporte(Double cantidad, Double valorUnit) {
        Double importe = 0.0;
        if (cantidad != null && valorUnit != null) {
            importe = cantidad * valorUnit;
            if (lineas != null) {
                System.out.println("linea importe");
                lineas.setImporte(importe);
            }
            if (selectedLinea != null) {
                System.out.println("selectedLinea importe");
                selectedLinea.setImporte(importe);
            }
        }
        return new DecimalFormat("0.00").format(importe);
    }

    public String calcularBase(Double cantidad, Double valorUnit, Double descuento) {
        Double base = 0.0;
        if (cantidad != null && valorUnit != null && descuento != null) {
            base = (cantidad * valorUnit) - descuento;
            if (lineas != null) {
                System.out.println("linea base");
                lineas.setBase(base);
            }
            if (selectedLinea != null) {
                System.out.println("selectedLinea base");
                selectedLinea.setBase(base);
            }
        }
        return new DecimalFormat("0.00").format(base);
    }

    public String calcularImporteImpuesto(String porcent, Double cantidad, String valorUnit, Double descuento) {
        System.out.println("Datos: " + porcent + " | " + cantidad + " | " + valorUnit + " | " + descuento);
        Double importeImpuesto = 0.0;
        Double importe = 0.0;
        Double base = 0.0;
        if (!porcent.isEmpty()) {
            if (Double.parseDouble(porcent) > 1.0) {
                importe = Double.parseDouble(calcularImporte(cantidad, Double.parseDouble(valorUnit)));
                base = Double.parseDouble(calcularBase(cantidad, Double.parseDouble(valorUnit), descuento));
                importeImpuesto = base * (Double.parseDouble(porcent) / 100);
            } else {
                importe = Double.parseDouble(calcularImporte(cantidad, Double.parseDouble(valorUnit)));
                base = Double.parseDouble(calcularBase(cantidad, Double.parseDouble(valorUnit), descuento));
                importeImpuesto = base * Double.parseDouble(porcent);
            }
        }
        System.out.println("importeImpuesto: " + importeImpuesto);
        return new DecimalFormat("0.00").format(importeImpuesto);
    }

    public String calcularSubtotal() {
        Double subTotal = 0.0;
        if (cfdisLineas != null) {
            for (int i = 0; i < cfdisLineas.size(); i++) {
                subTotal += cfdisLineas.get(i).getImporte();
            }
        }
        return new DecimalFormat("0.00").format(subTotal);
    }

    public String trasladadoIva() {
        Double trasladosIva = 0.0;
        if (cfdisLineas != null) {
            for (int i = 0; i < cfdisLineas.size(); i++) {
                if (cfdisLineas.get(i).getImpuesto() != null && cfdisLineas.get(i).getImpuesto().equals("002") && cfdisLineas.get(i).getTipoImpuesto().equals("Traslado")) {
                    trasladosIva += cfdisLineas.get(i).getImporteimpuesto();
                }
                if (cfdisLineas.get(i).getImpuesto2() != null && cfdisLineas.get(i).getImpuesto2().equals("002") && cfdisLineas.get(i).getTipoImpuesto2().equals("Traslado")) {
                    trasladosIva += cfdisLineas.get(i).getImporteImpuesto2();
                }
                if (cfdisLineas.get(i).getImpuesto3() != null && cfdisLineas.get(i).getImpuesto3().equals("002") && cfdisLineas.get(i).getTipoImpuesto3().equals("Traslado")) {
                    trasladosIva += cfdisLineas.get(i).getImporteImpuesto3();
                }
                if (cfdisLineas.get(i).getImpuesto4() != null && cfdisLineas.get(i).getImpuesto4().equals("002") && cfdisLineas.get(i).getTipoImpuesto4().equals("Traslado")) {
                    trasladosIva += cfdisLineas.get(i).getImporteImpuesto4();
                }
            }
        }
        return new DecimalFormat("0.00").format(trasladosIva);
    }

    public String trasladadoISR() {
        Double trasladosIsr = 0.0;
        if (cfdisLineas != null) {
            for (int i = 0; i < cfdisLineas.size(); i++) {
                if (cfdisLineas.get(i).getImpuesto() != null && cfdisLineas.get(i).getImpuesto().equals("001") && cfdisLineas.get(i).getTipoImpuesto().equals("Traslado")) {
                    trasladosIsr += cfdisLineas.get(i).getImporteimpuesto();
                }
                if (cfdisLineas.get(i).getImpuesto2() != null && cfdisLineas.get(i).getImpuesto2().equals("001") && cfdisLineas.get(i).getTipoImpuesto2().equals("Traslado")) {
                    trasladosIsr += cfdisLineas.get(i).getImporteImpuesto2();
                }
                if (cfdisLineas.get(i).getImpuesto3() != null && cfdisLineas.get(i).getImpuesto3().equals("001") && cfdisLineas.get(i).getTipoImpuesto3().equals("Traslado")) {
                    trasladosIsr += cfdisLineas.get(i).getImporteImpuesto3();
                }
                if (cfdisLineas.get(i).getImpuesto4() != null && cfdisLineas.get(i).getImpuesto4().equals("001") && cfdisLineas.get(i).getTipoImpuesto4().equals("Traslado")) {
                    trasladosIsr += cfdisLineas.get(i).getImporteImpuesto4();
                }
            }
        }
        return new DecimalFormat("0.00").format(trasladosIsr);
    }

    public String trasladadoIEPS() {
        Double trasladadosIEPS = 0.0;
        if (cfdisLineas != null) {
            for (int i = 0; i < cfdisLineas.size(); i++) {
                if (cfdisLineas.get(i).getImpuesto() != null && cfdisLineas.get(i).getImpuesto().equals("003") && cfdisLineas.get(i).getTipoImpuesto().equals("Traslado")) {
                    trasladadosIEPS += cfdisLineas.get(i).getImporteimpuesto();
                }
                if (cfdisLineas.get(i).getImpuesto2() != null && cfdisLineas.get(i).getImpuesto2().equals("003") && cfdisLineas.get(i).getTipoImpuesto2().equals("Traslado")) {
                    trasladadosIEPS += cfdisLineas.get(i).getImporteImpuesto2();
                }
                if (cfdisLineas.get(i).getImpuesto3() != null && cfdisLineas.get(i).getImpuesto3().equals("003") && cfdisLineas.get(i).getTipoImpuesto3().equals("Traslado")) {
                    trasladadosIEPS += cfdisLineas.get(i).getImporteImpuesto3();
                }
                if (cfdisLineas.get(i).getImpuesto4() != null && cfdisLineas.get(i).getImpuesto4().equals("003") && cfdisLineas.get(i).getTipoImpuesto4().equals("Traslado")) {
                    trasladadosIEPS += cfdisLineas.get(i).getImporteImpuesto4();
                }
            }
        }
        return new DecimalFormat("0.00").format(trasladadosIEPS);
    }

    public String retenidosIva() {
        Double retenidosIva = 0.0;
        if (cfdisLineas != null) {
            for (int i = 0; i < cfdisLineas.size(); i++) {
                if (cfdisLineas.get(i).getImpuesto() != null && cfdisLineas.get(i).getImpuesto().equals("002") && cfdisLineas.get(i).getTipoImpuesto().equals("Retención")) {
                    retenidosIva += cfdisLineas.get(i).getImporteimpuesto();
                }
                if (cfdisLineas.get(i).getImpuesto2() != null && cfdisLineas.get(i).getImpuesto2().equals("002") && cfdisLineas.get(i).getTipoImpuesto2().equals("Retención")) {
                    retenidosIva += cfdisLineas.get(i).getImporteImpuesto2();
                }
                if (cfdisLineas.get(i).getImpuesto3() != null && cfdisLineas.get(i).getImpuesto3().equals("002") && cfdisLineas.get(i).getTipoImpuesto3().equals("Retención")) {
                    retenidosIva += cfdisLineas.get(i).getImporteImpuesto3();
                }
                if (cfdisLineas.get(i).getImpuesto4() != null && cfdisLineas.get(i).getImpuesto4().equals("002") && cfdisLineas.get(i).getTipoImpuesto4().equals("Retención")) {
                    retenidosIva += cfdisLineas.get(i).getImporteImpuesto4();
                }
            }
        }
        return new DecimalFormat("0.00").format(retenidosIva);
    }

    public String retenidosISR() {
        Double retenidosIsr = 0.0;
        if (cfdisLineas != null) {
            for (int i = 0; i < cfdisLineas.size(); i++) {
                if (cfdisLineas.get(i).getImpuesto() != null && cfdisLineas.get(i).getImpuesto().equals("001") && cfdisLineas.get(i).getTipoImpuesto().equals("Retención")) {
                    retenidosIsr += cfdisLineas.get(i).getImporteimpuesto();
                }
                if (cfdisLineas.get(i).getImpuesto2() != null && cfdisLineas.get(i).getImpuesto2().equals("001") && cfdisLineas.get(i).getTipoImpuesto2().equals("Retención")) {
                    retenidosIsr += cfdisLineas.get(i).getImporteImpuesto2();
                }
                if (cfdisLineas.get(i).getImpuesto3() != null && cfdisLineas.get(i).getImpuesto3().equals("001") && cfdisLineas.get(i).getTipoImpuesto3().equals("Retención")) {
                    retenidosIsr += cfdisLineas.get(i).getImporteImpuesto3();
                }
                if (cfdisLineas.get(i).getImpuesto4() != null && cfdisLineas.get(i).getImpuesto4().equals("001") && cfdisLineas.get(i).getTipoImpuesto4().equals("Retención")) {
                    retenidosIsr += cfdisLineas.get(i).getImporteImpuesto4();
                }
            }
        }
        return new DecimalFormat("0.00").format(retenidosIsr);
    }

    public String retenidosIEPS() {
        Double retenidosIEPS = 0.0;
        if (cfdisLineas != null) {
            for (int i = 0; i < cfdisLineas.size(); i++) {
                if (cfdisLineas.get(i).getImpuesto() != null && cfdisLineas.get(i).getImpuesto().equals("003") && cfdisLineas.get(i).getTipoImpuesto().equals("Retención")) {
                    retenidosIEPS += cfdisLineas.get(i).getImporteimpuesto();
                }
                if (cfdisLineas.get(i).getImpuesto2() != null && cfdisLineas.get(i).getImpuesto2().equals("003") && cfdisLineas.get(i).getTipoImpuesto2().equals("Retención")) {
                    retenidosIEPS += cfdisLineas.get(i).getImporteImpuesto2();
                }
                if (cfdisLineas.get(i).getImpuesto3() != null && cfdisLineas.get(i).getImpuesto3().equals("003") && cfdisLineas.get(i).getTipoImpuesto3().equals("Retención")) {
                    retenidosIEPS += cfdisLineas.get(i).getImporteImpuesto3();
                }
                if (cfdisLineas.get(i).getImpuesto4() != null && cfdisLineas.get(i).getImpuesto4().equals("003") && cfdisLineas.get(i).getTipoImpuesto4().equals("Retención")) {
                    retenidosIEPS += cfdisLineas.get(i).getImporteImpuesto4();
                }
            }
        }
        return new DecimalFormat("0.00").format(retenidosIEPS);
    }

    public String retenidosLocales() {
        Double retenidoLocal = 0.0;
        if (listaImpuestosCfdis != null) {
            for (int i = 0; i < listaImpuestosCfdis.size(); i++) {
                if (listaImpuestosCfdis.get(i).getImpuestosLocalesId().getTipoimpuestoId().getTipoImpuesto().equals("Retención")) {
                    retenidoLocal += listaImpuestosCfdis.get(i).getImporte();
                }
            }
        }
        return new DecimalFormat("0.00").format(retenidoLocal);
    }

    public String trasladosLocales() {
        Double trasladoLocal = 0.0;
        if (listaImpuestosCfdis != null) {
            for (int i = 0; i < listaImpuestosCfdis.size(); i++) {
                if (listaImpuestosCfdis.get(i).getImpuestosLocalesId().getTipoimpuestoId().getTipoImpuesto().equals("Traslado")) {
                    trasladoLocal += listaImpuestosCfdis.get(i).getImporte();
                }
            }
        }
        return new DecimalFormat("0.00").format(trasladoLocal);
    }

    public String calcularDescuento() {
        Double descuento = 0.0;
        if (cfdisLineas != null) {
            for (int i = 0; i < cfdisLineas.size(); i++) {
                if (cfdisLineas.get(i).getDescuento() != null) {
                    descuento += cfdisLineas.get(i).getDescuento();
                }
            }
        }
        return new DecimalFormat("0.00").format(descuento);
    }

    public Double calcularTotal() {
        Double total = 0.0;
        total = Double.parseDouble(calcularSubtotal()) + Double.parseDouble(trasladadoISR()) + Double.parseDouble(trasladadoIva()) + Double.parseDouble(trasladadoIEPS()) + Double.parseDouble(trasladosLocales()) - Double.parseDouble(retenidosISR()) - Double.parseDouble(retenidosIva()) - Double.parseDouble(retenidosIEPS()) - Double.parseDouble(calcularDescuento()) - Double.parseDouble(retenidosLocales());
        cfdisId.setImporte(total);
        cfdisId.setSaldoInsoluto(total);
        cfdisId.setSaldoPagado(total);
        cfdisId.setSaldoPagar(0.0);
        System.out.println("Total: " + total);
        return total;
    }

    public String importeLetra() {
        NumeroALetra numLetra = new NumeroALetra();
        String importeLetra = String.valueOf(new DecimalFormat("0.00").format(calcularTotal()));
        cfdisId.setImporteLetra(numLetra.Convertir(importeLetra, false));
        return numLetra.Convertir(importeLetra, false);
    }

    public boolean crearXML() throws Exception {
        System.out.println("crearXML");
        String cadOrig = "";
        boolean valor = false;
        try {
            System.out.println("Probando");
            File xslt = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/cadenaoriginal_3_3.xslt"));            //Inicio calcula cadena original
            StreamSource sourceXSL = new StreamSource(xslt);
            System.out.println("Probando2");
            if (cfdisId.getTipoComprobante().equals("I") || cfdisId.getTipoComprobante().equals("E")) {
                valor = crearXmlIngEgre();
            }
            if (cfdisId.getTipoComprobante().equals("P")) {
                valor = crearXmlPago();
            }

        } catch (NullPointerException ex) {
            System.out.println("Error3: " + ex.getMessage());
            JsfUtil.addErrorMessage("Error al ingresar datos");               //*/
        }
        System.out.println("valor1: " + valor);
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
                System.out.println("linea: " + linea);
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
        return linea;
    }

    public String crearCertificado() throws Exception {
        String certificadoB64 = new String(Base64.getEncoder().encode(cfdisId.getCertificados_Id().getCertificadoSelloDigital()));
        System.out.println("certificado: " + certificadoB64);
        return certificadoB64;
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
        System.out.println("firma: " + firma);
        return firma;
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
            System.out.println("1: " + element.getAttribute("Sello"));
            System.out.println("1: " + element.getAttribute("Certificado"));
            //if (element.getAttribute("Sello").isEmpty()) {
            element.setAttribute("Sello", crearSello(xml));
            //}
            //if (element.getAttribute("Certificado").isEmpty()) {
            element.setAttribute("Certificado", crearCertificado());
            //}
            System.out.println("2: " + element.getAttribute("Sello"));
            System.out.println("2: " + element.getAttribute("Certificado"));
        }
        leerCfdi(xmlAc);
        leerCfdi(tempFile);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result output = new StreamResult(tempFile);
        Source input = new DOMSource(doc);
        transformer.transform(input, output);
        if (timbra(tempFile.getPath(), xml)) {
            valorModifica = true;
        }
        return valorModifica;
    }

    public boolean timbra(String nombre, String cadOriginal) {
        System.out.println("nombre: " + nombre + " | cadOriginal: " + cadOriginal);
        leerCfdi(new File(nombre));
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
            //Produccion https://www.sefactura.com.mx AFC060520V16 AFC060520V16
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
                System.out.println("ProbandoT");
                FileOutputStream fos = new FileOutputStream(xmltimbrado);
                fos.write(xmlTimbrado.getBytes("UTF-8"));
                fos.close();
                byte[] codQR = Base64.getDecoder().decode(rt.getCadenaCodigo());
                System.out.println("ProbandoT2");
                archivo.setArchivoQR(codQR);
                byte[] xmlTimbradoB = new byte[(int) xmltimbrado.length()];
                fis = new FileInputStream(xmltimbrado);
                fis.read(xmlTimbradoB);
                fis.close();
                System.out.println("ProbandoT3");
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();//obtener datos de XML Timbrado para crear PDF
                DocumentBuilder builder = docFactory.newDocumentBuilder();
                Document doc = builder.parse(xmltimbrado);
                NodeList items = doc.getElementsByTagName("tfd:TimbreFiscalDigital");
                System.out.println("ProbandoT4");
                for (int i = 0; i < items.getLength(); i++) {
                    Element element = (Element) items.item(0);
                    selloSAT = element.getAttribute("SelloSAT");
                    noCertSAT = element.getAttribute("NoCertificadoSAT");
                    fechaTimbrado = element.getAttribute("FechaTimbrado");
                    Uuid = element.getAttribute("UUID");
                    selloCFDI = element.getAttribute("SelloCFD");
                    rfcProvCertif = element.getAttribute("RfcProvCertif");
                    System.out.println("Probando5");
                }
                cfdisId.setUuid(Uuid);
                archivo.setArchivoXml(xmlTimbradoB);
                cfdisId.setRespuestaTimbrado("Timbrado de forma correcta");
                System.out.println(noCertSAT + " | " + fechaTimbrado + " | " + Uuid + " | " + selloCFDI + " | " + selloSAT + " | " + rfcProvCertif);
                crearPDF(selloSAT, noCertSAT, fechaTimbrado, Uuid, selloCFDI, codQR, cadOriginal, rfcProvCertif);
                System.out.println("ProbandoT6");
                //crearArchivo(xmltimbrado);
                valorTimbra = true;
                System.out.println("Ya termine ");
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getLocalizedMessage());
        }
        return valorTimbra;
    }

    public void crearArchivo(File xmlTimbrado) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = docFactory.newDocumentBuilder();
            Document doc = builder.parse(xmlTimbrado);
            NodeList items = doc.getElementsByTagName("tfd:TimbreFiscalDigital");
            for (int i = 0; i < items.getLength(); i++) {
                Element element = (Element) items.item(i);
                String selloSAT = element.getAttribute("SelloSAT");
                String noCertSAT = element.getAttribute("NoCertificadoSAT");
                String fechaTimbrado = element.getAttribute("FechaTimbrado");
                String Uuid = element.getAttribute("UUID");
                String selloCFDI = element.getAttribute("SelloCFD");
                cfdisId.setUuid(element.getAttribute("UUID"));
            }
        } catch (Exception ex) {
            System.out.println("Error al esribir");
        }
    }

    public void vistaPreviaPDF(ActionEvent actionEvent) throws Exception {
        verPDF(actionEvent);
    }

    public void verPDF(ActionEvent actionEvent) throws Exception {
        System.out.println("ENTRO A VISUALIZAR LA FACURA PREVIA IngreEgre");
        System.out.println("RFCEmisor: " + cfdisId.getRfcEmisor());
        System.out.println("UsoCFDI: " + cfdisId.getUsoCfdi());
        System.out.println("RegimenFiscal: " + cfdisId.getClaveRegimenFiscal());
        FileOutputStream fos = new FileOutputStream(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/responsivo.png")));
        fos.write(obtenerImagen());
        fos.close();
        RnGcUsuariosTbl emisor = new RnGcUsuariosTbl();
        emisor = usuariosFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        cfdisId.setRfcEmisor(emisor.getRfc());
        cfdisId.setNombreEmisor(emisor.getNombreCompleto());
        String imagenLogo = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRealPath("/resources/images/responsivo.png");//*/
        String imagenqr = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRealPath("/resources/images/qr.png");
        //Crea el Map para setear los valores de la factura
        //HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        //response.addHeader("Content-disposition", "attachment; fileName=Factura_" + cfdisId.getRfcEmisor() + new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss").format(cfdisId.getFechaExpedicion()) + ".pdf");
        //response.setHeader("Content-Disposition", "attachment;filename=Factura_Nómina_" + cfdisId.getRfcEmisor() + "_" + new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss").format(cfdisId.getFechaExpedicion()) + ".zip");
        //response.setContentType("application/zip");

        byte[] pdf = null;
        List<byte[]> pdfs = new ArrayList<>();
        for (RnGcTrabajadoresTbl trabajador : listaTrabajadores) {
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("RFC_Emisor", cfdisId.getRfcEmisor());
            parametros.put("Nombre_Emisor", cfdisId.getNombreEmisor());
            parametros.put("RFC_Receptor", trabajador.getRfc());
            parametros.put("Nombre_Receptor", trabajador.getNombreCompleto());
            parametros.put("Uso_CFDI", cfdisId.getUsoCfdi());
            //parametros.put("Folio_Fiscal", cfdisId.getFolio());
            parametros.put("NoSerie_CSD", cfdisId.getSerie());
            parametros.put("CodigoPostal", cfdisId.getLugarExpedicion());
            parametros.put("FechaHora_Emision", new SimpleDateFormat("YYYY-MM-dd'T'hh:mm:ss").format(cfdisId.getFechaExpedicion()));
            parametros.put("QR", imagenqr);
            parametros.put("Logo", imagenLogo);

            parametros.put("EfectoComprobante", "N - Nómina");
            parametros.put("Uso_CFDI", "P01 - Por definir");

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
            parametros.put("Moneda", cfdisId.getMoneda());
            if (cfdisId.getMoneda() != null) {
                if (cfdisId.getMoneda().equals("MXN") || cfdisId.getMoneda().equals("XXX")) {
                    parametros.put("tipoCambio", 1);
                } else {
                    parametros.put("tipoCambio", cfdisId.getTipoCambio());
                }
            }
            System.out.println("ListaConceptossss: " + cfdisLineas);
            parametros.put("conceptos", cfdisLineas);
            parametros.put("CFDIsRelacionados", listaCfdisRelacionados);
            parametros.put("Subtotal", Double.parseDouble(calcularSubtotal()));
            parametros.put("Total", calcularTotal());
            parametros.put("ImpuestosRetenidos", Double.parseDouble(new DecimalFormat("0.00").format(Double.parseDouble(retenidosIEPS()) + Double.parseDouble(retenidosISR()) + Double.parseDouble(retenidosIva()))));
            parametros.put("ImpuestosTrasladados", Double.parseDouble(new DecimalFormat("0.00").format(Double.parseDouble(trasladadoIEPS()) + Double.parseDouble(trasladadoISR()) + Double.parseDouble(trasladadoIva()))));
            parametros.put("totalDescuento", Double.parseDouble(new DecimalFormat("0.00").format(Double.parseDouble(calcularDescuento()))));

            parametros.put("FormaPago", "99 - Por definir");
            parametros.put("MetodoPago", "PUE - Pago en una sola exhibición");

            if (firmas.getNombre1() != null && firmas.getCargo1() != null) {
                parametros.put("nombre1", firmas.getNombre1());
                parametros.put("cargo1", firmas.getCargo1());
            }
            if (firmas.getNombre2() != null && firmas.getCargo2() != null) {
                parametros.put("nombre2", firmas.getNombre2());
                parametros.put("cargo2", firmas.getCargo2());
            }
            if (firmas.getNombre3() != null && firmas.getCargo3() != null) {
                parametros.put("nombre3", firmas.getNombre3());
                parametros.put("cargo3", firmas.getCargo3());
            }
            if (firmas.getNombre4() != null && firmas.getCargo4() != null) {
                parametros.put("nombre4", firmas.getNombre4());
                parametros.put("cargo4", firmas.getCargo4());
            }
            if (firmas.getNombre5() != null && firmas.getCargo5() != null) {
                parametros.put("nombre5", firmas.getNombre5());
                parametros.put("cargo5", firmas.getCargo5());
            }
            parametros.put("importeLetra", cfdisId.getImporteLetra());
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Reports/previo1.3.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JREmptyDataSource());

            pdfs.add(JasperExportManager.exportReportToPdf(jasperPrint));

        }

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        //response.addHeader("Content-disposition", "attachment; fileName=Factura_" + cfdisId.getRfcEmisor() + new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss").format(cfdisId.getFechaExpedicion()) + ".pdf");
        response.setHeader("Content-Disposition", "attachment;filename=Factura_Nómina_" + cfdisId.getRfcEmisor() + "_" + new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss").format(cfdisId.getFechaExpedicion()) + ".zip");
        response.setContentType("application/zip");

        ServletOutputStream baos = response.getOutputStream();
        ZipOutputStream zout = new ZipOutputStream(baos);
        System.out.println("pdfs1: " + pdfs.size() + " | " + pdfs.toString());
        for (int j = 0; j < pdfs.size(); j++) {
            System.out.println("pdfs2: " + pdfs.get(j) + " | " + " | " + j);
            ZipEntry entry = new ZipEntry("VistaPreviaNomina_" + "_" + (j + 1) + "_" + new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(new Date()) + ".pdf");
            zout.putNextEntry(entry);
            zout.write(pdfs.get(j));
            zout.closeEntry();
        }
        zout.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();
        return;
        //*/
        //FacesContext.getCurrentInstance().responseComplete();
        //FacesContext.getCurrentInstance().responseComplete();
    }

    public StreamedContent getDownLoadFile() {
        return downLoadFile;
    }

    public void setDownLoadFile(StreamedContent downLoadFile) {
        this.downLoadFile = downLoadFile;
    }

    public void crearPDF(String selloSAT, String noCertSAT, String fechaTimbrado, String Uuid, String selloCFDI, byte[] codigoQR, String cadenaOrig, String rfcProvCertif) throws JRException, IOException, ParseException {
        System.out.println("Creacion de PDF");
        FileOutputStream fos = new FileOutputStream(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/qr.png")));
        fos.write(codigoQR);
        fos.close();
        fos = new FileOutputStream(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/responsivo.png")));
        fos.write(obtenerImagen());
        fos.close();
        String imagenqr = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRealPath("/resources/images/qr.png");
        String imagenLogo = FacesContext.getCurrentInstance().
                getExternalContext().
                getRealPath("/resources/images/responsivo.png");
        //Crear Map para setear los valores de la factura
        Map<String, Object> parametros = new HashMap<String, Object>();
        //Parametros
        parametros.put("Logo", imagenLogo);
        parametros.put("RFC_Emisor", cfdisId.getRfcEmisor());
        parametros.put("Nombre_Emisor", cfdisId.getNombreEmisor());
        parametros.put("RFC_Receptor", personas.getRfc());
        parametros.put("Nombre_Receptor", personas.getNombre());
        parametros.put("Uso_CFDI", cfdisId.getUsoCfdi());
        parametros.put("Folio_Fiscal", cfdisId.getFolio());
        parametros.put("NoSerie_CSD", cfdisId.getSerie());
        parametros.put("CodigoPostal", cfdisId.getLugarExpedicion());
        parametros.put("FechaHora_Emision", new SimpleDateFormat("YYYY-MM-dd'T'hh:mm:ss").format(cfdisId.getFechaExpedicion()));
        parametros.put("importeLetra", cfdisId.getImporteLetra());
        parametros.put("rfcProvCertif", rfcProvCertif);

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

        if (cfdisId.getUsoCfdi() != null) {
            switch (cfdisId.getUsoCfdi()) {

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
        if (cfdisId.getMoneda().equals("MXN") || cfdisId.getMoneda().equals("XXX")) {
            parametros.put("tipoCambio", 1);
        } else {
            parametros.put("tipoCambio", cfdisId.getTipoCambio());
        }
        parametros.put("Moneda", cfdisId.getMoneda());
        parametros.put("conceptos", cfdisLineas);
        parametros.put("CFDIsRelacionados", listaCfdisRelacionados);
        parametros.put("Subtotal", Double.parseDouble(calcularSubtotal()));
        parametros.put("Total", calcularTotal());
        parametros.put("ImpuestosRetenidos", Double.parseDouble(new DecimalFormat("0.00").format(Double.parseDouble(retenidosIEPS()) + Double.parseDouble(retenidosISR()) + Double.parseDouble(retenidosIva()))));
        parametros.put("ImpuestosTrasladados", Double.parseDouble(new DecimalFormat("0.00").format(Double.parseDouble(trasladadoIEPS()) + Double.parseDouble(trasladadoISR()) + Double.parseDouble(trasladadoIva()))));
        parametros.put("totalDescuento", Double.parseDouble(new DecimalFormat("0.00").format(Double.parseDouble(calcularDescuento()))));
        parametros.put("QR", imagenqr);

        if (cfdisId.getFormaPago() != null) {
            switch (cfdisId.getFormaPago()) {

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
        if (cfdisId.getMetodoPago() != null) {
            if (cfdisId.getMetodoPago().equals("PUE")) {
                parametros.put("MetodoPago", "PUE - Pago en una sola exhibición");
            }
            if (cfdisId.getMetodoPago().equals("PPD")) {
                parametros.put("MetodoPago", "PPD - Pago en parcialidades o diferido");
            }
        }
        if (firmas.getNombre1() != null && firmas.getCargo1() != null) {
            parametros.put("nombre1", firmas.getNombre1());
            parametros.put("cargo1", firmas.getCargo1());
        }
        if (firmas.getNombre2() != null && firmas.getCargo2() != null) {
            parametros.put("nombre2", firmas.getNombre2());
            parametros.put("cargo2", firmas.getCargo2());
        }
        if (firmas.getNombre3() != null && firmas.getCargo3() != null) {
            parametros.put("nombre3", firmas.getNombre3());
            parametros.put("cargo3", firmas.getCargo3());
        }
        if (firmas.getNombre4() != null && firmas.getCargo4() != null) {
            parametros.put("nombre4", firmas.getNombre4());
            parametros.put("cargo4", firmas.getCargo4());
        }
        if (firmas.getNombre5() != null && firmas.getCargo5() != null) {
            parametros.put("nombre5", firmas.getNombre5());
            parametros.put("cargo5", firmas.getCargo5());
        }
        //String selloSAT, String noCertSAT, String fechaTimbrado, String Uuid, String selloCFDI, byte[] codigoQR, String cadenaOrig
        parametros.put("sello_CFDI", selloCFDI);
        parametros.put("sello_SAT", selloSAT);
        parametros.put("cadenaOriginal", cadenaOrig);
        parametros.put("noCertificadoSAT", noCertSAT);
        parametros.put("fechaCertificacion", fechaTimbrado);
        parametros.put("Uuid", Uuid);

        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Reports/previo1.3.jasper"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JREmptyDataSource());
        //Imprime PDF
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        //response.addHeader("Content-disposition", "attachment; fileName=Factura_" + cfdisId.getRfcEmisor() + new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss").format(cfdisId.getFechaExpedicion()) + ".pdf");
        response.setHeader("Content-Disposition", "attachment;filename=Factura_" + cfdisId.getRfcEmisor() + "_" + new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss").format(cfdisId.getFechaExpedicion()) + ".zip");
        response.setContentType("application/zip");

        ServletOutputStream out = response.getOutputStream();
        ZipOutputStream zout = new ZipOutputStream(out);

        ServletOutputStream stream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
        System.out.println("Se realizo la descarga del PDF");
        byte[] facturaPDF = JasperExportManager.exportReportToPdf(jasperPrint);
        archivo.setArchivoPdf(facturaPDF);//*/

        ZipEntry zip = new ZipEntry("Factura_" + cfdisId.getRfcEmisor() + new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss").format(cfdisId.getFechaExpedicion()) + ".pdf");
        zout.putNextEntry(zip);
        zout.write(facturaPDF);
        zout.closeEntry();

        ZipEntry zip2 = new ZipEntry("Factura_" + cfdisId.getRfcEmisor() + new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss").format(cfdisId.getFechaExpedicion()) + ".xml");
        byte[] xml = archivo.getArchivoXml();
        zout.putNextEntry(zip2);
        zout.write(xml);
        zout.closeEntry();

        zout.close();
        stream.flush();
        stream.close();
        System.out.println("PDF guardado");
    }

    public void updateLinea() {
        System.out.println("update()");
        System.out.println(selectedLinea.getClaveProdServ() + " | " + selectedLinea.getNoIdentificacion()
                + " | " + selectedLinea.getCantidad() + " | " + selectedLinea.getClaveUnidad()
                + " | " + selectedLinea.getUnidad() + " | " + selectedLinea.getDescripcion()
                + " | " + selectedLinea.getValorUnit() + " | " + selectedLinea.getImporte()
                + " | " + selectedLinea.getBase() + " | " + selectedLinea.getImpuesto()
                + " | " + selectedLinea.getImpuesto2() + " | " + selectedLinea.getImpuesto3()
                + " | " + selectedLinea.getImpuesto4() + " | " + selectedLinea.getTipoFactor()
                + " | " + selectedLinea.getTipoFactor2() + " | " + selectedLinea.getTipoFactor3()
                + " | " + selectedLinea.getTipoFactor4() + " | " + selectedLinea.getTipoTasa()
                + " | " + selectedLinea.getTipoTasa2() + " | " + selectedLinea.getTipoTasa3()
                + " | " + selectedLinea.getTipoTasa4() + " | " + selectedLinea.getImporteimpuesto()
                + " | " + selectedLinea.getImporteImpuesto2() + " | " + selectedLinea.getImporteImpuesto3()
                + " | " + selectedLinea.getImporteImpuesto4() + " | " + selectedLinea.getCreadoPor()
                + " | " + selectedLinea.getFechaCreacion() + " | " + selectedLinea.getUltimaActualizacionPor()
                + " | " + selectedLinea.getUltimaFechaActualizacion());
        persistLinea(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCfdisLineasTblUpdated"));
    }

    public void eliminarConcepto() {
        System.out.println("destroy()");
        System.out.println(selectedLinea.getClaveProdServ() + " | " + selectedLinea.getNoIdentificacion()
                + " | " + selectedLinea.getCantidad() + " | " + selectedLinea.getClaveUnidad()
                + " | " + selectedLinea.getUnidad() + " | " + selectedLinea.getDescripcion()
                + " | " + selectedLinea.getValorUnit() + " | " + selectedLinea.getImporte()
                + " | " + selectedLinea.getBase() + " | " + selectedLinea.getImpuesto()
                + " | " + selectedLinea.getImpuesto2() + " | " + selectedLinea.getImpuesto3()
                + " | " + selectedLinea.getImpuesto4() + " | " + selectedLinea.getTipoFactor()
                + " | " + selectedLinea.getTipoFactor2() + " | " + selectedLinea.getTipoFactor3()
                + " | " + selectedLinea.getTipoFactor4() + " | " + selectedLinea.getTipoTasa()
                + " | " + selectedLinea.getTipoTasa2() + " | " + selectedLinea.getTipoTasa3()
                + " | " + selectedLinea.getTipoTasa4() + " | " + selectedLinea.getImporteimpuesto()
                + " | " + selectedLinea.getImporteImpuesto2() + " | " + selectedLinea.getImporteImpuesto3()
                + " | " + selectedLinea.getImporteImpuesto4() + " | " + selectedLinea.getCreadoPor()
                + " | " + selectedLinea.getFechaCreacion() + " | " + selectedLinea.getUltimaActualizacionPor()
                + " | " + selectedLinea.getUltimaFechaActualizacion());
        persistLinea(PersistAction.DELETE, "Concepto Eliminado");
    }

    public void persistLinea(PersistAction persistAction, String mensage) {
        System.out.println("persist: " + persistAction + " | " + selectedLinea.getDescripcion());
        if (selectedLinea != null) {
            try {
                if (persistAction != PersistAction.DELETE) {
                    getLineasCfdisFacade().edit(selectedLinea);
                    selectedLinea = new RnGcCfdisLineasTbl();
                    JsfUtil.addSuccessMessage(mensage);
                    System.out.println("getFacade().edit(lineas) | " + cfdisLineas.size());
                } else {
                    if (impLocalLinea(selectedLinea)) {
                        JsfUtil.addSuccessMessage("Debes borrar el impuesto local que tiene relacionado el concepto");
                    } else {
                        cfdisLineas.remove(selectedLinea);
                        selectedLinea = new RnGcCfdisLineasTbl();
                        JsfUtil.addSuccessMessage(mensage);
                        System.out.println("getFacade().remove(lineas) | " + cfdisLineas.size());
                    }
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
    }

    public void imprimirSaldos() {
        System.out.println("tipoComprobante: " + cfdisId.getTipoComprobante().equals("I") + " | " + cfdisId.getTipoComprobante().equals("E")
                + " | " + cfdisId.getTipoComprobante().equals("T"));
    }

    public void persistComplemento(PersistAction persistAction, String mensage) {
        System.out.println("persist: " + persistAction + " | " + listaCfdis);
        if (listaCfdis != null) {
            try {
                if (persistAction != PersistAction.DELETE) {
                    RnGcCfdisTbl cfdiPPD = new RnGcCfdisTbl();
                    for (int i = 0; i < listaCfdis.size(); i++) {
                        cfdiPPD = listaCfdis.get(i);
                        cfdiPPD.setSaldoPagar(0.0);
                        cfdiPPD.setMontoPago(0.0);
                        cfdiPPD.setSaldoPagado(cfdiPPD.getSaldoInsoluto());
                        cfdisFacade.edit(cfdiPPD);
                        cfdiPPD = new RnGcCfdisTbl();
                    }
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
    }

    public boolean impLocalLinea(RnGcCfdisLineasTbl linea) {
        boolean valor = false;
        if (listaImpuestosCfdis != null) {
            for (int i = 0; i < listaImpuestosCfdis.size(); i++) {
                if (listaImpuestosCfdis.get(i).getCfdisLineasId().equals(linea)) {
                    valor = true;
                    break;
                }
            }
        }
        return valor;
    }

    public List<RnGcFolioserieTbl> obtenerFolioPorUsuarioSerie(String serie) {
        System.out.println("Serie: " + serie);
        if (!serie.isEmpty()) {
            usuario = usuariosFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            itemsFolio = folioSerieFacade.folioPorUsuarioSerie(usuario, serie);
        } else {
            itemsFolio = null;
        }
        return itemsFolio;
    }

    public void buscarCfdi() {
        if (cfdiUuid != null) {
            cfdi2 = cfdiUuid;
        }
        cfdiUuid = null;
    }

    public void buscarCliente() {
        if (personas2 != null) {
            trabajador = trabajador2; //RFC
        } else {
            if (personas3 != null) { //Nombre
                trabajador = trabajador3;
            }
        }
        if (!personas.getNombre().equals("-") && !personas.getRfc().equals("-")) {
            if (usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")) {
                JsfUtil.addSuccessMessage("El cliente " + personas.getRfc() + " se ha aactualizado");
            } else {
                if (!obtenerClientes(personas.getRfc().toUpperCase())) {
                    personas.setRfc(personas.getRfc().toUpperCase());
                    personas.setNombre(iniMayusculas(personas.getNombre()));
                    personas.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
                    personas.setTipoPersona("Cliente");
                    personas.setFechaCreacion(new Date());
                    personas.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                    personas.setUltimaFechaActualizacion(new Date());
                    personas.setTipoPersonaId(tiposPersonasFacade.obtenerTipoPersona("Cliente"));
                    personas.setUsuarioId(usuariosFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario()));
                    personas.setNoInt(personas.getNoInt());
                    personasFacade.edit(personas);
                    JsfUtil.addSuccessMessage("El cliente " + personas.getRfc() + " se ha guardado");
                } else {
                    JsfUtil.addSuccessMessage("El cliente " + personas.getRfc() + " se ha actualizado");
                }
            }
        } else {
            JsfUtil.addSuccessMessage("No se ha ingresado datos del receptor");
        }
        System.out.println("listaUsuarios: " + listaUsuarios);
        List<RnGcUsuariosTbl> usuario = usuariosFacade.obtenerListaUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        cfdisId.setRfcEmisor(usuario.get(0).getRfc());
        cfdisId.setNombreEmisor(usuario.get(0).getNombreCompleto());
        JsfUtil.addSuccessMessage("El emisor es: " + usuario.get(0).getRfc());
        personas2 = null;
        personas3 = null;
    }

    public void buscarTrabajadores() {
        if (trabajador2 != null) {
            trabajador = trabajador2; //RFC
        } else {
            if (trabajador3 != null) { //Nombre
                trabajador = trabajador3;
            }
        }
        System.out.println("trabajador: " + trabajador + " | " + trabajador2 + " | " + trabajador3);
        trabajador2 = null;
        trabajador3 = null;
    }

    public void guardarCliente(RnGcPersonasTbl personaId) {
        listaPersonas = personasFacade.obtenerCreadoPor(usuarioFirmado.obtenerIdUsuario());
        for (int i = 0; i < listaPersonas.size(); i++) {
            if (listaPersonas.get(i).getRfc().equals(personaId.getRfc())) {
                System.out.println("El cliente ya existe");
                break;
            } else {

                personasFacade.edit(personaId);
                System.out.println("Cliente " + personaId + " guardado");
            }
        }
    }

    public void buscarProdServ() {
        if (producServicio2 != null) {
            producServicio = producServicio2;
            lineas.setClaveProdServ(producServicio.getClaveProductServ());
            lineas.setNoIdentificacion(producServicio.getNoIdentificacion());
            lineas.setClaveUnidad(producServicio.getClaveUnidad());
            lineas.setUnidad(producServicio.getUnidad());
            lineas.setDescripcion(producServicio.getDescripcion());
            lineas.setValorUnit(producServicio.getValorunitario());
            lineas.setTipoImpuesto(producServicio.getTipoImpuesto());
            lineas.setImpuesto(producServicio.getImpuesto());
            lineas.setTipoFactor(producServicio.getTipofactor());
            lineas.setTipoTasa(producServicio.getTipoTasa());
            System.out.println(lineas.getClaveProdServ() + " | " + lineas.getNoIdentificacion() + " | " + lineas.getClaveUnidad()
                    + " | " + lineas.getUnidad() + " | " + lineas.getDescripcion() + " | " + lineas.getValorUnit()
                    + " | " + lineas.getTipoImpuesto() + " | " + lineas.getImpuesto() + " | " + lineas.getTipoFactor()
                    + " | " + lineas.getTipoTasa());
            if (producServicio2.getTipoImpuesto2() != null) {
                lineas.setTipoImpuesto2(producServicio2.getTipoImpuesto2());
                lineas.setImpuesto2(producServicio2.getImpuesto2());
                lineas.setTipoFactor2(producServicio2.getTipoFactor2());
                lineas.setTipoTasa2(producServicio2.getTipoTasa2());
                System.out.println(lineas.getTipoImpuesto2() + " | " + lineas.getImpuesto2()
                        + " | " + lineas.getTipoFactor2() + " | " + lineas.getTipoTasa2());
            }
            if (producServicio2.getTipoImpuesto3() != null) {
                lineas.setTipoImpuesto3(producServicio2.getTipoImpuesto3());
                lineas.setImpuesto3(producServicio2.getImpuesto3());
                lineas.setTipoFactor3(producServicio2.getTipoFactor3());
                lineas.setTipoTasa3(producServicio2.getTipoTasa3());
                System.out.println(lineas.getTipoImpuesto3() + " | " + lineas.getImpuesto3()
                        + " | " + lineas.getTipoFactor3() + " | " + lineas.getTipoTasa3());
            }
            if (producServicio2.getTipoImpuesto4() != null) {
                lineas.setTipoImpuesto4(producServicio2.getTipoImpuesto4());
                lineas.setImpuesto4(producServicio2.getImpuesto4());
                lineas.setTipoFactor4(producServicio2.getTipoFactor4());
                lineas.setTipoTasa4(producServicio2.getTipoTasa4());
                System.out.println(lineas.getTipoImpuesto4() + " | " + lineas.getImpuesto4()
                        + " | " + lineas.getTipoFactor4() + " | " + lineas.getTipoTasa4());
            }
        }
        producServicio2 = null;
    }

    public void enviarCorreo(RnGcArchivosTbl archivoId) {
        final String username = "conta-arc@rocketnegocios.com.mx";
        final String password = "ga1mJ7$4";
        Properties props = new Properties();
        props.put("mail.smtp.host", "rocketnegocios.com.mx");
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
        System.out.println("Persona E-mails: " + personas.getEmail() + " | " + personas.getEmail2());
        try {
            if (personas.getEmail().contains("@") && personas.getEmail() != null) {
                List<String> correos = new ArrayList<>();
                correos.add(personas.getEmail());
                if (personas.getEmail2() != null && personas.getEmail2().contains("@") && personas.getEmail2() != "-") {
                    correos.add(personas.getEmail2());
                }
                InternetAddress[] destinatarios = new InternetAddress[correos.size()];
                for (int i = 0; i < correos.size(); i++) {
                    destinatarios[i] = new InternetAddress(correos.get(i));
                }
                session.getProperties().put("mail.smtp.strattls.enable", "true");
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setText("Factura timbrada correctamente: " + archivoId.getArchivoPdf());
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
                message.setFrom(new InternetAddress(username, cfdisId.getNombreEmisor()));
                message.setSubject(cfdisId.getNombreEmisor());
                message.addRecipients(Message.RecipientType.TO, destinatarios);
                message.setContent(multipart);
                //Enviar Mensaje
                Transport transport = session.getTransport("smtp");
                transport.connect(username, password);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Archivos enviados al correo " + personas.getEmail()));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("El correo " + personas.getEmail() + " no es válido"));
            }
        } catch (MessagingException | UnsupportedEncodingException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error al enviar correo"));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error al enviar mensaje"));
        }
    }

    public List<RnGcTimbresTbl> obtenerTimbres() {
        usuario = usuariosFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        listaTimbres = timbresFacade.listaTimbresUsuario(usuario);
        System.out.println("listaTimbres: " + listaTimbres);
        return listaTimbres;
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

    public List<RnGcCfdisTbl> getListaCfdis() {
        if (listaCfdis == null) {
            this.listaCfdis = new ArrayList<>();
        }
        return listaCfdis;
    }

    public void setListaCfdis(List<RnGcCfdisTbl> listaCfdis) {
        this.listaCfdis = listaCfdis;
    }

    public void agregarFactura() {
        System.out.println("agregarFactura");
        if (cfdi2 != null) {
            System.out.println("calculo: " + montoTotal + " | " + monto);
            if (montoTotal <= monto) {
                System.out.println("calculo2: " + montoTotal + " | " + monto);
                System.out.println(cfdi2.getSaldoPagar() + " | " + cfdi2.getSaldoPagado() + " | " + cfdi2.getSaldoInsoluto() + " | " + cfdi2.getCantidadPagada());
                cfdi2.setSaldoInsoluto(cfdi2.getSaldoPagado() - cfdi2.getCantidadPagada());
                cfdi2.setId((int) (Math.random() * 999999999));

                listaCfdis.add(cfdi2);
            } else {
                montoTotal -= saldoPagar;
                System.out.println("montoTotal: " + montoTotal);
                JsfUtil.addErrorMessage("No se agrego la factura se ha excedido el monto de pago");
            }
        } else {
            JsfUtil.addErrorMessage("no se pudo agregar la factura");
        }
        saldoPagar = 0.0;
        parcialidad = 0;
        saldoIinsoluto = 0.0;
        cfdi2 = new RnGcCfdisTbl();
        System.out.println("Fina agregarFactura");
    }

    public List<RnGcCfdisTbl> prepareCfdi2() {
        System.out.println("prepareCfdis2: " + cfdi2 + " | " + cfdisId);
        cfdi2 = new RnGcCfdisTbl();
        listaCfdisSeleccion = cfdisFacade.obtenerCfdisParaComplementos(cfdisId.getRfcReceptor());
        System.out.println("prepareCfdis22: " + cfdi2 + " | " + cfdisId + " | " + listaCfdisSeleccion);
        return listaCfdisSeleccion;
    }

    public List<RnGcCfdisTbl> prepararListaCfdi2() {
        cfdi2 = new RnGcCfdisTbl();
        listaCfdisSeleccion = cfdisFacade.obtenerCfdisParaComplementos(cfdisId.getRfcReceptor());
        System.out.println("prepararListaCfdi2 Fin");
        return listaCfdisSeleccion;
    }

    public void eliminarDocsRelacionados() {
        System.out.println("listaCfdis: " + listaCfdis.size() + " | " + cfdiUuid);
        listaCfdis.remove(cfdiUuid);
        cfdiUuid = new RnGcCfdisTbl();
        System.out.println("listaCfdis2: " + listaCfdis.size() + " | " + cfdiUuid);
    }

    public void eliminarRelacionados() {
        System.out.println("listaCfdisRelacionados: " + listaCfdisRelacionados.size() + " | " + cfdiRelacionado);
        listaCfdisRelacionados.remove(cfdiRelacionado);
        cfdiRelacionado = new RnGcCfdisTbl();
        System.out.println("listaCfdisRelacionados2: " + listaCfdisRelacionados.size() + " | " + cfdiRelacionado);
    }

    public void eliminarComplemento() {
        System.out.println("listaComplemntosPago: " + listaCfdis.size() + " | " + cfdiRelacionado);
        listaCfdis.remove(cfdiRelacionado);
        cfdiRelacionado = new RnGcCfdisTbl();
        System.out.println("listaCfdisRelacionados2: " + listaCfdis.size() + " | " + cfdiRelacionado);
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

    public void agregarComplementoPago() {
        System.out.println("agregarComplementoPago1: " + cfdiRelacionado.getUuid());
        if (cfdiRelacionado != null) {
            listaCfdis.add(cfdiRelacionado);
            cfdiRelacionado = new RnGcCfdisTbl();
            System.out.println("agregarComplementoPago2: " + cfdiRelacionado);
        } else {
            JsfUtil.addErrorMessage("No se pudo agregar el CFDI");
        }
    }

    public void prepareRelacionado() {
        cfdiRelacionado = new RnGcCfdisTbl();
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Valor Cambiado", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public Double saldosInsolutos(Double salgoPagado1) {
        Double saldoInsoluto = 0.0;
        if (salgoPagado1 != null) {
            for (int i = 0; i < listaCfdis.size(); i++) {
                System.out.println("listaCfdis: " + listaCfdis.get(i).getSaldoPagar());
            }
        }
        return saldoInsoluto;
    }

    public Double calcularSaldoInsoluto(Double saldoPagado) {
        Double saldoInsoluto = 0.0;
        if (saldoPagado != null) {
            saldoInsoluto = cfdiRelacionado.getSaldoInsoluto() - cfdiRelacionado.getSaldoPagar();
        } else {
            saldoInsoluto = cfdiRelacionado.getSaldoInsoluto();
        }
        System.out.println("saldoInsoluto: " + saldoInsoluto);
        return saldoInsoluto;
    }

    public boolean crearXmlPago() throws Exception {
        System.out.println("crearXmlPago: " + cfdisLineas.size() + " | " + itemsFactura.size());
        String cadOrig = "";
        boolean valor = false;
        File tempFile = null;
        try {
            System.out.println("crearXML2");
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
            Attr certificado1 = doc.createAttribute("Certificado");
            certificado1.setValue("");
            rootElement.setAttributeNode(certificado1);
            if (cfdisId.getCondicionPago() != null) {
                Attr condicionPago = doc.createAttribute("CondicionesDePago");
                condicionPago.setValue(cfdisId.getCondicionPago());
                rootElement.setAttributeNode(condicionPago);
            }
            Attr subTotal = doc.createAttribute("SubTotal");
            subTotal.setValue("0");
            rootElement.setAttributeNode(subTotal);
            Attr moneda = doc.createAttribute("Moneda");
            moneda.setValue(cfdisId.getMoneda());
            rootElement.setAttributeNode(moneda);
            Attr total = doc.createAttribute("Total");
            total.setValue("0");
            rootElement.setAttributeNode(total);
            Attr tipoCfdi = doc.createAttribute("TipoDeComprobante");
            tipoCfdi.setValue(cfdisId.getTipoComprobante());
            rootElement.setAttributeNode(tipoCfdi);
            Attr lugarExp = doc.createAttribute("LugarExpedicion");
            lugarExp.setValue(String.valueOf(cfdisId.getLugarExpedicion()));
            rootElement.setAttributeNode(lugarExp);

            Attr cfdi = doc.createAttribute("xmlns:cfdi");
            cfdi.setValue("http://www.sat.gob.mx/cfd/3");
            rootElement.setAttributeNode(cfdi);
            Attr pago = doc.createAttribute("xmlns:pago10");
            pago.setValue("http://www.sat.gob.mx/Pagos");
            rootElement.setAttributeNode(pago);
            Attr impLocal = doc.createAttribute("xmlns:implocal");
            impLocal.setValue("http://www.sat.gob.mx/implocal");
            rootElement.setAttributeNode(impLocal);
            Attr xsi = doc.createAttribute("xmlns:xsi");
            xsi.setValue("http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttributeNode(xsi);
            Attr esquema = doc.createAttribute("xsi:schemaLocation");
            esquema.setValue("http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd http://www.sat.gob.mx/implocal http://www.sat.gob.mx/sitio_internet/cfd/implocal/implocal.xsd http://www.sat.gob.mx/Pagos http://www.sat.gob.mx/sitio_internet/cfd/Pagos/Pagos10.xsd");
            rootElement.setAttributeNode(esquema);
            //Nodo CfdiRelacionados
            if (cfdisId.getTipoRelacion() != null && listaCfdisRelacionados.size() > 0) {
                Element relacionados = doc.createElement("cfdi:CfdiRelacionados");
                rootElement.appendChild(relacionados);
                //Attributos CfdiRelacionados
                Attr tipoRelacion = doc.createAttribute("TipoRelacion");
                tipoRelacion.setValue(cfdisId.getTipoRelacion());
                relacionados.setAttributeNode(tipoRelacion);
                for (int i = 0; i < listaCfdisRelacionados.size(); i++) {
                    Element relacionado = doc.createElement("cfdi:CfdiRelacionado");//NodoCfdiRelacionado
                    relacionados.appendChild(relacionado);
                    Attr uuid = doc.createAttribute("UUID");
                    uuid.setValue(listaCfdisRelacionados.get(i).getUuid());
                    relacionado.setAttributeNode(uuid);
                }
            }
            //Nodo emisor
            Element emisor = doc.createElement("cfdi:Emisor");
            rootElement.appendChild(emisor);
            //Atributos Emisor
            Attr rfcE = doc.createAttribute("Rfc");
            rfcE.setValue(cfdisId.getRfcEmisor());
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
            rfcR.setValue(personas.getRfc());
            receptor.setAttributeNode(rfcR);
            Attr nombreR = doc.createAttribute("Nombre");
            nombreR.setValue(stripAccents(personas.getNombre()));
            receptor.setAttributeNode(nombreR);
            Attr uso = doc.createAttribute("UsoCFDI");
            uso.setValue(cfdisId.getUsoCfdi());
            receptor.setAttributeNode(uso);
            System.out.println("Probando1.0");
            System.out.println("Probando2.0");
            //Nodo conceptos
            Element conceptos = doc.createElement("cfdi:Conceptos");
            rootElement.appendChild(conceptos);
            //Atributos conceptos
            Element concepto = doc.createElement("cfdi:Concepto");
            conceptos.appendChild(concepto);
            Attr claveProd = doc.createAttribute("ClaveProdServ");
            claveProd.setValue("84111506");
            concepto.setAttributeNode(claveProd);
            System.out.println("Probando3.0");
            Attr cantidad = doc.createAttribute("Cantidad");
            cantidad.setValue("1");
            concepto.setAttributeNode(cantidad);
            Attr claveUnidad = doc.createAttribute("ClaveUnidad");
            claveUnidad.setValue("ACT");
            concepto.setAttributeNode(claveUnidad);
            System.out.println("Probando4.0");
            Attr descripcion = doc.createAttribute("Descripcion");
            descripcion.setValue("Pago");
            concepto.setAttributeNode(descripcion);
            Attr valorUnit = doc.createAttribute("ValorUnitario");
            valorUnit.setValue("0");
            concepto.setAttributeNode(valorUnit);
            Attr importe = doc.createAttribute("Importe");
            importe.setValue("0");
            concepto.setAttributeNode(importe);
            System.out.println("Probando5.0");
            //Nodo Complemento
            Element complemento = doc.createElement("cfdi:Complemento");
            rootElement.appendChild(complemento);
            //Nodo pagos
            Element pagos = doc.createElement("pago10:Pagos");
            complemento.appendChild(pagos);
            //Atributo Pagos
            Attr versionP = doc.createAttribute("Version");
            versionP.setValue("1.0");
            pagos.setAttributeNode(versionP);
            //Nodo pago
            Element pago1 = doc.createElement("pago10:Pago");
            pagos.appendChild(pago1);
            //Atributos pago
            Attr fechaPago = doc.createAttribute("FechaPago");
            fechaPago.setValue(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(cfdisId.getFechaPago()));
            pago1.setAttributeNode(fechaPago);
            Attr formaPagoP = doc.createAttribute("FormaDePagoP");
            formaPagoP.setValue(cfdisId.getFormaPagoP());
            pago1.setAttributeNode(formaPagoP);
            Attr monedaP = doc.createAttribute("MonedaP");
            monedaP.setValue(cfdisId.getMonedaP());
            pago1.setAttributeNode(monedaP);
            Attr monto1 = doc.createAttribute("Monto");
            monto1.setValue(String.valueOf(cfdisId.getMontoPago()));
            pago1.setAttributeNode(monto1);
            System.out.println("Probando6.0");
            for (int a = 0; a < listaCfdis.size(); a++) {
                //Nodo DocRelacionado
                Element docRelacionado = doc.createElement("pago10:DoctoRelacionado");
                pago1.appendChild(docRelacionado);
                //Atributo DocRelacionados
                if (listaCfdis.get(a).getFolio() != null) {
                    Attr folioP = doc.createAttribute("Folio"); //---------------
                    folioP.setValue(String.valueOf(listaCfdis.get(a).getFolio()));
                    docRelacionado.setAttributeNode(folioP);
                }
                System.out.println("Probando7.0");
                Attr idDoc = doc.createAttribute("IdDocumento"); //-------------
                idDoc.setValue(listaCfdis.get(a).getUuid());
                docRelacionado.setAttributeNode(idDoc);
                Attr impSaldoAnterior = doc.createAttribute("ImpSaldoAnt"); //-------------
                impSaldoAnterior.setValue(new DecimalFormat("0.00").format(listaCfdis.get(a).getSaldoInsoluto() + listaCfdis.get(a).getSaldoPagar()));
                docRelacionado.setAttributeNode(impSaldoAnterior);
                Attr impPagado = doc.createAttribute("ImpPagado"); //-------------
                impPagado.setValue(new DecimalFormat("0.00").format(listaCfdis.get(a).getSaldoPagar()));
                docRelacionado.setAttributeNode(impPagado);
                Attr saldoInsoluto = doc.createAttribute("ImpSaldoInsoluto"); //-------------
                saldoInsoluto.setValue(new DecimalFormat("0.00").format(listaCfdis.get(a).getSaldoInsoluto()));
                docRelacionado.setAttributeNode(saldoInsoluto);
                Attr metodoPagoP = doc.createAttribute("MetodoDePagoDR"); //-------------
                metodoPagoP.setValue(listaCfdis.get(a).getMetodoPago());
                docRelacionado.setAttributeNode(metodoPagoP);
                System.out.println("Probando8.0");
                Attr moneda2 = doc.createAttribute("MonedaDR"); //-------------
                moneda2.setValue(listaCfdis.get(a).getMoneda());
                docRelacionado.setAttributeNode(moneda2);
                Attr numeroParcialidad = doc.createAttribute("NumParcialidad"); //-------------
                numeroParcialidad.setValue(String.valueOf(listaCfdis.get(a).getNumeroParcialidad()));
                docRelacionado.setAttributeNode(numeroParcialidad);
                if (listaCfdis.get(a).getSerie() != null) {
                    Attr serieP = doc.createAttribute("Serie"); //-------------
                    serieP.setValue(listaCfdis.get(a).getSerie());
                    docRelacionado.setAttributeNode(serieP);
                }
            }
            System.out.println("Probando9.0");
            System.out.println("crearXML3");
            System.out.println("Probando");
            File xslt = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/cadenaoriginal_3_3.xslt"));            //Inicio calcula cadena original
            StreamSource sourceXSL = new StreamSource(xslt);
            System.out.println("Probando2");

            DOMSource source = new DOMSource(doc);
            //tempFile = new File("C:\\Users\\Developer1\\Desktop\\XMLs\\probandoXML.xml");
            tempFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/xmlTemp.xml"));
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
            crearSello(cadOrig);
            crearCertificado();
            leerCfdi(tempFile);
            if (modificarXml(cadOrig, tempFile)) {
                valor = true;
            } else {
                valor = false;
            }//*/
        } catch (NullPointerException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (TransformerException ex2) {
            System.out.println("Error2: " + ex2.getMessage());
        }
        return valor;
    }

    public boolean crearXmlIngEgre() {
        File tempFile = null;
        System.out.println("crearXmlIngEgre");
        String cadOrig = "";
        boolean valor = false;
        try {
            System.out.println("crearXML2");
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
            Attr formaPago = doc.createAttribute("FormaPago");
            formaPago.setValue(cfdisId.getFormaPago());
            rootElement.setAttributeNode(formaPago);
            Attr noCertificado = doc.createAttribute("NoCertificado");
            noCertificado.setValue(cfdisId.getCertificados_Id().getNumeroCertificado());
            rootElement.setAttributeNode(noCertificado);
            Attr certificado1 = doc.createAttribute("Certificado");
            certificado1.setValue("");
            rootElement.setAttributeNode(certificado1);
            if (Double.parseDouble(calcularDescuento()) > 0.0) {
                Attr descuentoRoot = doc.createAttribute("Descuento");
                descuentoRoot.setValue(calcularDescuento());
                rootElement.setAttributeNode(descuentoRoot);
            }
            if (!cfdisId.getCondicionPago().isEmpty()) {
                Attr condicionPago = doc.createAttribute("CondicionesDePago");
                condicionPago.setValue(cfdisId.getCondicionPago());
                rootElement.setAttributeNode(condicionPago);
            }
            Attr subTotal = doc.createAttribute("SubTotal");
            subTotal.setValue(calcularSubtotal());
            rootElement.setAttributeNode(subTotal);
            Attr moneda = doc.createAttribute("Moneda");
            moneda.setValue(cfdisId.getMoneda());
            rootElement.setAttributeNode(moneda);
            if (cfdisId.getMoneda().equals("MXN") || cfdisId.getMoneda().equals("XXX")) {
                Attr tipoCambio = doc.createAttribute("TipoCambio");
                tipoCambio.setValue("1");
                rootElement.setAttributeNode(tipoCambio);
                cfdisId.setTipoCambio(1.0);
            } else {
                Attr tipoCambio = doc.createAttribute("TipoCambio");
                tipoCambio.setValue(String.valueOf(cfdisId.getTipoCambio()));
                rootElement.setAttributeNode(tipoCambio);
            }
            Attr total = doc.createAttribute("Total");
            total.setValue(new DecimalFormat("0.00").format(calcularTotal()));
            rootElement.setAttributeNode(total);
            Attr tipoCfdi = doc.createAttribute("TipoDeComprobante");
            tipoCfdi.setValue(cfdisId.getTipoComprobante());
            rootElement.setAttributeNode(tipoCfdi);
            Attr metodoPago = doc.createAttribute("MetodoPago");
            metodoPago.setValue(cfdisId.getMetodoPago());
            rootElement.setAttributeNode(metodoPago);
            Attr lugarExp = doc.createAttribute("LugarExpedicion");
            lugarExp.setValue(String.valueOf(cfdisId.getLugarExpedicion()));
            rootElement.setAttributeNode(lugarExp);

            Attr cfdi = doc.createAttribute("xmlns:cfdi");
            cfdi.setValue("http://www.sat.gob.mx/cfd/3");
            rootElement.setAttributeNode(cfdi);
            Attr impLocal = doc.createAttribute("xmlns:implocal");
            impLocal.setValue("http://www.sat.gob.mx/implocal");
            rootElement.setAttributeNode(impLocal);
            Attr pago = doc.createAttribute("xmlns:pago10");
            pago.setValue("http://www.sat.gob.mx/Pagos");
            rootElement.setAttributeNode(pago);
            Attr xsi = doc.createAttribute("xmlns:xsi");
            xsi.setValue("http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttributeNode(xsi);
            Attr esquema = doc.createAttribute("xsi:schemaLocation");
            esquema.setValue("http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd http://www.sat.gob.mx/implocal http://www.sat.gob.mx/sitio_internet/cfd/implocal/implocal.xsd http://www.sat.gob.mx/Pagos http://www.sat.gob.mx/sitio_internet/cfd/Pagos/Pagos10.xsd");
            rootElement.setAttributeNode(esquema);
            //Nodo CfdiRelacionados
            if (cfdisId.getTipoRelacion() != null && listaCfdisRelacionados.size() > 0) {
                Element relacionados = doc.createElement("cfdi:CfdiRelacionados");
                rootElement.appendChild(relacionados);
                //Attributos CfdiRelacionados
                Attr tipoRelacion = doc.createAttribute("TipoRelacion");
                tipoRelacion.setValue(cfdisId.getTipoRelacion());
                relacionados.setAttributeNode(tipoRelacion);
                for (int i = 0; i < listaCfdisRelacionados.size(); i++) {
                    Element relacionado = doc.createElement("cfdi:CfdiRelacionado");//NodoCfdiRelacionado
                    relacionados.appendChild(relacionado);
                    Attr uuid = doc.createAttribute("UUID");
                    uuid.setValue(listaCfdisRelacionados.get(i).getUuid());
                    relacionado.setAttributeNode(uuid);
                }
            }
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
            rfcR.setValue(personas.getRfc());
            receptor.setAttributeNode(rfcR);
            Attr nombreR = doc.createAttribute("Nombre");
            nombreR.setValue(stripAccents(personas.getNombre()));
            receptor.setAttributeNode(nombreR);
            Attr uso = doc.createAttribute("UsoCFDI");
            uso.setValue(cfdisId.getUsoCfdi());
            receptor.setAttributeNode(uso);
            System.out.println("crearXML3");
            //Nodo conceptos
            Element conceptos = doc.createElement("cfdi:Conceptos");
            rootElement.appendChild(conceptos);
            for (int i = 0; i < cfdisLineas.size(); i++) {
                //Atributos conceptos
                Element concepto = doc.createElement("cfdi:Concepto");
                conceptos.appendChild(concepto);
                Attr claveProd = doc.createAttribute("ClaveProdServ");
                claveProd.setValue(String.valueOf(cfdisLineas.get(i).getClaveProdServ()));
                concepto.setAttributeNode(claveProd);
                if (cfdisLineas.get(i).getNoIdentificacion() != null && !cfdisLineas.get(i).getNoIdentificacion().isEmpty()) {
                    Attr noIdenti = doc.createAttribute("NoIdentificacion");
                    noIdenti.setValue(cfdisLineas.get(i).getNoIdentificacion());
                    concepto.setAttributeNode(noIdenti);
                }
                Attr cantidad = doc.createAttribute("Cantidad");
                cantidad.setValue(new DecimalFormat("0").format(cfdisLineas.get(i).getCantidad()));
                concepto.setAttributeNode(cantidad);
                Attr claveUnidad = doc.createAttribute("ClaveUnidad");
                claveUnidad.setValue(cfdisLineas.get(i).getClaveUnidad());
                concepto.setAttributeNode(claveUnidad);
                if (cfdisLineas.get(i).getUnidad() != null && !cfdisLineas.get(i).getUnidad().isEmpty()) {
                    Attr unidad = doc.createAttribute("Unidad");
                    unidad.setValue(cfdisLineas.get(i).getUnidad());
                    concepto.setAttributeNode(unidad);
                }
                Attr descripcion = doc.createAttribute("Descripcion");
                descripcion.setValue(stripAccents(cfdisLineas.get(i).getDescripcion()));
                concepto.setAttributeNode(descripcion);
                Attr valorUnit = doc.createAttribute("ValorUnitario");
                valorUnit.setValue(new DecimalFormat("0.00").format(cfdisLineas.get(i).getValorUnit()));
                concepto.setAttributeNode(valorUnit);
                Attr importe = doc.createAttribute("Importe");
                importe.setValue(new DecimalFormat("0.00").format(cfdisLineas.get(i).getImporte()));
                concepto.setAttributeNode(importe);
                if (cfdisLineas.get(i).getDescuento() != null && cfdisLineas.get(i).getDescuento() > 0.0) {
                    Attr descuento = doc.createAttribute("Descuento");
                    descuento.setValue(new DecimalFormat("0.00").format(cfdisLineas.get(i).getDescuento()));
                    concepto.setAttributeNode(descuento);
                }
                //nodo impuestos
                Element impuestos = doc.createElement("cfdi:Impuestos");
                concepto.appendChild(impuestos);
                if (cfdisLineas.get(i).getTipoImpuesto().equals("Traslado") || cfdisLineas.get(i).getTipoImpuesto2().equals("Traslado")
                        || cfdisLineas.get(i).getTipoImpuesto3().equals("Traslado") || cfdisLineas.get(i).getTipoImpuesto4().equals("Traslado")) {
                    //nodo traslados
                    Element traslados = doc.createElement("cfdi:Traslados");
                    impuestos.appendChild(traslados);
                    if (cfdisLineas.get(i).getTipoImpuesto() != null && cfdisLineas.get(i).getTipoImpuesto().equals("Traslado")) {
                        System.out.println("1T");
                        //nodo traslado
                        Element traslado = doc.createElement("cfdi:Traslado");
                        traslados.appendChild(traslado);
                        //atributos traslado
                        Attr base1 = doc.createAttribute("Base");
                        base1.setValue(new DecimalFormat("0.00").format(cfdisLineas.get(i).getBase()));
                        traslado.setAttributeNode(base1);
                        Attr impuesto1 = doc.createAttribute("Impuesto");
                        impuesto1.setValue(String.valueOf(cfdisLineas.get(i).getImpuesto()));
                        traslado.setAttributeNode(impuesto1);
                        Attr factor1 = doc.createAttribute("TipoFactor");
                        factor1.setValue(cfdisLineas.get(i).getTipoFactor());
                        traslado.setAttributeNode(factor1);
                        Attr importeImpuesto1 = doc.createAttribute("Importe");
                        importeImpuesto1.setValue(new DecimalFormat("0.00").format(cfdisLineas.get(i).getImporteimpuesto()));
                        traslado.setAttributeNode(importeImpuesto1);
                        if (cfdisLineas.get(i).getTipoTasa() > 1.0) {
                            Attr tasa1 = doc.createAttribute("TasaOCuota");
                            tasa1.setValue("0" + new DecimalFormat(".000000").format(cfdisLineas.get(i).getTipoTasa() / 100));
                            traslado.setAttributeNode(tasa1);
                        } else {
                            Attr tasa1 = doc.createAttribute("TasaOCuota");
                            tasa1.setValue("0" + new DecimalFormat(".000000").format(cfdisLineas.get(i).getTipoTasa()));
                            traslado.setAttributeNode(tasa1);
                        }
                    }
                    System.out.println("dato1");
                    if (cfdisLineas.get(i).getTipoImpuesto2() != null && cfdisLineas.get(i).getTipoImpuesto2().equals("Traslado")) {
                        System.out.println("2T");
                        //nodo traslado
                        Element traslado = doc.createElement("cfdi:Traslado");
                        traslados.appendChild(traslado);
                        //atributos traslado
                        Attr base2 = doc.createAttribute("Base");                       //Base
                        base2.setValue(new DecimalFormat("0.00").format(cfdisLineas.get(i).getBase()));
                        traslado.setAttributeNode(base2);
                        Attr impuesto2 = doc.createAttribute("Impuesto");            //Impuesto
                        impuesto2.setValue(String.valueOf(cfdisLineas.get(i).getImpuesto2()));
                        traslado.setAttributeNode(impuesto2);
                        Attr factor2 = doc.createAttribute("TipoFactor");            //TipoFactor
                        factor2.setValue(cfdisLineas.get(i).getTipoFactor2());
                        traslado.setAttributeNode(factor2);
                        if (cfdisLineas.get(i).getTipoTasa2() > 1.0) {
                            Attr tasa2 = doc.createAttribute("TasaOCuota");             //TasaOCuota
                            tasa2.setValue("0" + new DecimalFormat(".000000").format(cfdisLineas.get(i).getTipoTasa2() / 100));
                            traslado.setAttributeNode(tasa2);
                        } else {
                            Attr tasa2 = doc.createAttribute("TasaOCuota");             //TasaOCuota
                            tasa2.setValue("0" + new DecimalFormat(".000000").format(cfdisLineas.get(i).getTipoTasa2()));
                            traslado.setAttributeNode(tasa2);
                        }
                        Attr importeImpuesto2 = doc.createAttribute("Importe");     //ImporteImpuesto
                        importeImpuesto2.setValue(new DecimalFormat("0.00").format(cfdisLineas.get(i).getImporteImpuesto2()));
                        traslado.setAttributeNode(importeImpuesto2);
                    }
                    System.out.println("dato2");
                    if (cfdisLineas.get(i).getTipoImpuesto3() != null && cfdisLineas.get(i).getTipoImpuesto3().equals("Traslado")) {
                        System.out.println("3T");
                        //nodo traslado
                        Element traslado = doc.createElement("cfdi:Traslado");
                        traslados.appendChild(traslado);
                        //atributos traslado
                        Attr base3 = doc.createAttribute("Base");                       //Base
                        base3.setValue(new DecimalFormat("0.00").format(cfdisLineas.get(i).getBase()));
                        traslado.setAttributeNode(base3);
                        Attr impuesto3 = doc.createAttribute("Impuesto");            //Impuesto
                        impuesto3.setValue(String.valueOf(cfdisLineas.get(i).getImpuesto3()));
                        traslado.setAttributeNode(impuesto3);
                        Attr factor3 = doc.createAttribute("TipoFactor");            //TipoFactor
                        factor3.setValue(cfdisLineas.get(i).getTipoFactor3());
                        traslado.setAttributeNode(factor3);
                        if (cfdisLineas.get(i).getTipoTasa3() > 1.0) {
                            Attr tasa3 = doc.createAttribute("TasaOCuota");             //TasaOCuota
                            tasa3.setValue("0" + new DecimalFormat(".000000").format(cfdisLineas.get(i).getTipoTasa3() / 100));
                            traslado.setAttributeNode(tasa3);
                        } else {
                            Attr tasa3 = doc.createAttribute("TasaOCuota");             //TasaOCuota
                            tasa3.setValue("0" + new DecimalFormat(".000000").format(cfdisLineas.get(i).getTipoTasa3()));
                            traslado.setAttributeNode(tasa3);
                        }
                        Attr importeImpuesto3 = doc.createAttribute("Importe");     //ImporteImpuesto
                        importeImpuesto3.setValue(new DecimalFormat("0.00").format(cfdisLineas.get(i).getImporteImpuesto3()));
                        traslado.setAttributeNode(importeImpuesto3);
                    }
                    System.out.println("dato3");
                    if (cfdisLineas.get(i).getTipoImpuesto4() != null && cfdisLineas.get(i).getTipoImpuesto4().equals("Traslado")) {
                        System.out.println("4T");
                        //nodo traslado
                        Element traslado = doc.createElement("cfdi:Traslado");
                        traslados.appendChild(traslado);
                        //atributos traslado
                        Attr base4 = doc.createAttribute("Base");                       //Base
                        base4.setValue(new DecimalFormat("0.00").format(cfdisLineas.get(i).getBase()));
                        traslado.setAttributeNode(base4);
                        Attr impuesto4 = doc.createAttribute("Impuesto");            //Impuesto
                        impuesto4.setValue(String.valueOf(cfdisLineas.get(i).getImpuesto4()));
                        traslado.setAttributeNode(impuesto4);
                        Attr factor4 = doc.createAttribute("TipoFactor");            //TipoFactor
                        factor4.setValue(cfdisLineas.get(i).getTipoFactor4());
                        traslado.setAttributeNode(factor4);
                        if (cfdisLineas.get(i).getTipoTasa4() > 1.0) {
                            Attr tasa4 = doc.createAttribute("TasaOCuota");             //TasaOCuota
                            tasa4.setValue("0" + new DecimalFormat(".000000").format(cfdisLineas.get(i).getTipoTasa4() / 100));
                            traslado.setAttributeNode(tasa4);
                        } else {
                            Attr tasa4 = doc.createAttribute("TasaOCuota");             //TasaOCuota
                            tasa4.setValue("0" + new DecimalFormat(".000000").format(cfdisLineas.get(i).getTipoTasa4()));
                            traslado.setAttributeNode(tasa4);
                        }
                        Attr importeImpuesto4 = doc.createAttribute("Importe");     //ImporteImpuesto
                        importeImpuesto4.setValue(new DecimalFormat("0.00").format(cfdisLineas.get(i).getImporteImpuesto4()));
                        traslado.setAttributeNode(importeImpuesto4);
                    }
                    System.out.println("dato4");
                }
                if (((cfdisLineas.get(i).getTipoImpuesto() != null) && cfdisLineas.get(i).getTipoImpuesto().equals("Retención"))
                        || ((cfdisLineas.get(i).getTipoImpuesto3() != null) && cfdisLineas.get(i).getTipoImpuesto3().equals("Retención"))
                        || ((cfdisLineas.get(i).getTipoImpuesto2() != null) && cfdisLineas.get(i).getTipoImpuesto2().equals("Retención"))
                        || ((cfdisLineas.get(i).getTipoImpuesto4() != null) && cfdisLineas.get(i).getTipoImpuesto4().equals("Retención"))) {
                    //nodo retenciones
                    Element retenciones = doc.createElement("cfdi:Retenciones");
                    impuestos.appendChild(retenciones);
                    if (cfdisLineas.get(i).getTipoImpuesto() != null && cfdisLineas.get(i).getTipoImpuesto().equals("Retención")) {
                        System.out.println("1R");
                        //nodo retencion
                        Element retencion = doc.createElement("cfdi:Retencion");
                        retenciones.appendChild(retencion);
                        //atributos traslado
                        Attr baseR1 = doc.createAttribute("Base");                       //Base
                        baseR1.setValue(new DecimalFormat("0.00").format(cfdisLineas.get(i).getBase()));
                        retencion.setAttributeNode(baseR1);
                        Attr impuestoR1 = doc.createAttribute("Impuesto");            //Impuesto
                        impuestoR1.setValue(String.valueOf(cfdisLineas.get(i).getImpuesto()));
                        retencion.setAttributeNode(impuestoR1);
                        Attr factorR1 = doc.createAttribute("TipoFactor");            //TipoFactor
                        factorR1.setValue(cfdisLineas.get(i).getTipoFactor());
                        retencion.setAttributeNode(factorR1);
                        if (cfdisLineas.get(i).getTipoTasa() > 1.0) {
                            Attr tasaR1 = doc.createAttribute("TasaOCuota");             //TasaOCuota
                            tasaR1.setValue("0" + new DecimalFormat(".000000").format(cfdisLineas.get(i).getTipoTasa() / 100));
                            retencion.setAttributeNode(tasaR1);
                        } else {
                            Attr tasaR1 = doc.createAttribute("TasaOCuota");             //TasaOCuota
                            tasaR1.setValue("0" + new DecimalFormat(".000000").format(cfdisLineas.get(i).getTipoTasa()));
                            retencion.setAttributeNode(tasaR1);
                        }
                        Attr importeR1 = doc.createAttribute("Importe");               //Importe
                        importeR1.setValue(new DecimalFormat("0.00").format(cfdisLineas.get(i).getImporteimpuesto()));
                        retencion.setAttributeNode(importeR1);
                    }
                    System.out.println("dato5");
                    if (cfdisLineas.get(i).getTipoImpuesto2() != null && cfdisLineas.get(i).getTipoImpuesto2().equals("Retención")) {
                        System.out.println("2R");
                        //nodo retencion
                        Element retencion = doc.createElement("cfdi:Retencion");
                        retenciones.appendChild(retencion);
                        //atributos traslado
                        Attr baseR2 = doc.createAttribute("Base");                       //Base
                        baseR2.setValue(new DecimalFormat("0.00").format(cfdisLineas.get(i).getBase()));
                        retencion.setAttributeNode(baseR2);
                        Attr impuestoR2 = doc.createAttribute("Impuesto");            //Impuesto
                        impuestoR2.setValue(String.valueOf(cfdisLineas.get(i).getImpuesto2()));
                        retencion.setAttributeNode(impuestoR2);
                        Attr factorR2 = doc.createAttribute("TipoFactor");            //TipoFactor
                        factorR2.setValue(cfdisLineas.get(i).getTipoFactor2());
                        retencion.setAttributeNode(factorR2);
                        if (cfdisLineas.get(i).getTipoTasa2() > 1.0) {
                            Attr tasaR2 = doc.createAttribute("TasaOCuota");             //TasaOCuota
                            tasaR2.setValue("0" + new DecimalFormat(".000000").format(cfdisLineas.get(i).getTipoTasa2() / 100));
                            retencion.setAttributeNode(tasaR2);
                        } else {
                            Attr tasaR2 = doc.createAttribute("TasaOCuota");             //TasaOCuota
                            tasaR2.setValue("0" + new DecimalFormat(".000000").format(cfdisLineas.get(i).getTipoTasa2()));
                            retencion.setAttributeNode(tasaR2);
                        }
                        Attr importeR2 = doc.createAttribute("Importe");               //Importe
                        importeR2.setValue(new DecimalFormat("0.00").format(cfdisLineas.get(i).getImporteImpuesto2()));
                        retencion.setAttributeNode(importeR2);
                    }
                    System.out.println("dato6");
                    if (cfdisLineas.get(i).getTipoImpuesto3() != null && cfdisLineas.get(i).getTipoImpuesto3().equals("Retención")) {
                        System.out.println("3R");
                        //nodo retencion
                        Element retencion = doc.createElement("cfdi:Retencion");
                        retenciones.appendChild(retencion);
                        //atributos traslado
                        Attr baseR3 = doc.createAttribute("Base");                       //Base
                        baseR3.setValue(new DecimalFormat("0.00").format(cfdisLineas.get(i).getBase()));
                        retencion.setAttributeNode(baseR3);
                        Attr impuestoR3 = doc.createAttribute("Impuesto");            //Impuesto
                        impuestoR3.setValue(String.valueOf(cfdisLineas.get(i).getImpuesto3()));
                        retencion.setAttributeNode(impuestoR3);
                        Attr factorR3 = doc.createAttribute("TipoFactor");            //TipoFactor
                        factorR3.setValue(cfdisLineas.get(i).getTipoFactor3());
                        retencion.setAttributeNode(factorR3);
                        if (cfdisLineas.get(i).getTipoTasa3() > 1.0) {
                            Attr tasaR3 = doc.createAttribute("TasaOCuota");             //TasaOCuota
                            tasaR3.setValue("0" + new DecimalFormat(".000000").format(cfdisLineas.get(i).getTipoTasa3() / 100));
                            retencion.setAttributeNode(tasaR3);
                        } else {
                            Attr tasaR3 = doc.createAttribute("TasaOCuota");             //TasaOCuota
                            tasaR3.setValue("0" + new DecimalFormat(".000000").format(cfdisLineas.get(i).getTipoTasa3()));
                            retencion.setAttributeNode(tasaR3);
                        }
                        Attr importeR3 = doc.createAttribute("Importe");               //Importe
                        importeR3.setValue(new DecimalFormat("0.00").format(cfdisLineas.get(i).getImporteImpuesto3()));
                        retencion.setAttributeNode(importeR3);
                    }
                    System.out.println("dato7");
                    if (cfdisLineas.get(i).getTipoImpuesto4() != null && cfdisLineas.get(i).getTipoImpuesto4().equalsIgnoreCase("Retención")) {
                        System.out.println("4R");
                        //nodo retencion
                        Element retencion = doc.createElement("cfdi:Retencion");
                        retenciones.appendChild(retencion);
                        //atributos traslado
                        Attr baseR4 = doc.createAttribute("Base");                       //Base
                        baseR4.setValue(new DecimalFormat("0.00").format(cfdisLineas.get(i).getBase()));
                        retencion.setAttributeNode(baseR4);
                        Attr impuestoR4 = doc.createAttribute("Impuesto");            //Impuesto
                        impuestoR4.setValue(String.valueOf(cfdisLineas.get(i).getImpuesto4()));
                        retencion.setAttributeNode(impuestoR4);
                        Attr factorR4 = doc.createAttribute("TipoFactor");            //TipoFactor
                        factorR4.setValue(cfdisLineas.get(i).getTipoFactor4());
                        retencion.setAttributeNode(factorR4);
                        if (cfdisLineas.get(i).getTipoTasa4() > 1.0) {
                            Attr tasaR4 = doc.createAttribute("TasaOCuota");             //TasaOCuota
                            tasaR4.setValue("0" + new DecimalFormat(".000000").format(cfdisLineas.get(i).getTipoTasa4() / 100));
                            retencion.setAttributeNode(tasaR4);
                        } else {
                            Attr tasaR4 = doc.createAttribute("TasaOCuota");             //TasaOCuota
                            tasaR4.setValue("0" + new DecimalFormat(".000000").format(cfdisLineas.get(i).getTipoTasa4()));
                            retencion.setAttributeNode(tasaR4);
                        }
                        Attr importeR4 = doc.createAttribute("Importe");               //Importe
                        importeR4.setValue(new DecimalFormat("0.00").format(cfdisLineas.get(i).getImporteImpuesto4()));
                        retencion.setAttributeNode(importeR4);
                    }
                    System.out.println("dato8");
                }
            }
            //nodo impuestos
            System.out.println("crearXML4");
            Element impuestos = doc.createElement("cfdi:Impuestos");
            rootElement.appendChild(impuestos);
            if (Double.parseDouble(retenidosISR()) > 0.0 || Double.parseDouble(retenidosIva()) > 0.0 || Double.parseDouble(retenidosIEPS()) > 0.0) {
                //Atributos impuestos
                System.out.println("Retenidos");
                Attr totalRetenidos = doc.createAttribute("TotalImpuestosRetenidos");
                totalRetenidos.setValue(new DecimalFormat("0.00").format(Double.parseDouble(retenidosISR()) + Double.parseDouble(retenidosIva()) + Double.parseDouble(retenidosIEPS())));
                impuestos.setAttributeNode(totalRetenidos);
                //nodo retenciones
                Element retenciones = doc.createElement("cfdi:Retenciones");
                impuestos.appendChild(retenciones);
                //nodo retencion
                if (Double.parseDouble(retenidosISR()) > 0.0) {
                    Element retencionIsr = doc.createElement("cfdi:Retencion");
                    retenciones.appendChild(retencionIsr);
                    //atributos retencion
                    Attr impuestoIsr = doc.createAttribute("Impuesto");
                    impuestoIsr.setValue("001");
                    retencionIsr.setAttributeNode(impuestoIsr);
                    Attr importeIsr = doc.createAttribute("Importe");
                    importeIsr.setValue(retenidosISR());
                    retencionIsr.setAttributeNode(importeIsr);
                }
                if (Double.parseDouble(retenidosIva()) > 0.0) {
                    Element retencionIsr = doc.createElement("cfdi:Retencion");
                    retenciones.appendChild(retencionIsr);
                    //atributos retencion
                    Attr impuestoIsr = doc.createAttribute("Impuesto");
                    impuestoIsr.setValue("002");
                    retencionIsr.setAttributeNode(impuestoIsr);
                    Attr importeIsr = doc.createAttribute("Importe");
                    importeIsr.setValue(retenidosIva());
                    retencionIsr.setAttributeNode(importeIsr);
                }
                if (Double.parseDouble(retenidosIEPS()) > 0.0) {
                    Element retencionIsr = doc.createElement("cfdi:Retencion");
                    retenciones.appendChild(retencionIsr);
                    //atributos retencion
                    Attr impuestoIsr = doc.createAttribute("Impuesto");
                    impuestoIsr.setValue("003");
                    retencionIsr.setAttributeNode(impuestoIsr);
                    Attr importeIsr = doc.createAttribute("Importe");
                    importeIsr.setValue(retenidosIEPS());
                    retencionIsr.setAttributeNode(importeIsr);
                }
            }
            System.out.println("Retenidos2");
            if (Double.parseDouble(trasladadoISR()) > 0.0 || Double.parseDouble(trasladadoIva()) > 0.0 || Double.parseDouble(trasladadoIEPS()) > 0.0) {
                //Atributos impuestos
                System.out.println("Trasladados");
                Attr totalTrasladado = doc.createAttribute("TotalImpuestosTrasladados");
                totalTrasladado.setValue(new DecimalFormat("0.00").format(Double.parseDouble(trasladadoISR()) + Double.parseDouble(trasladadoIva()) + Double.parseDouble(trasladadoIEPS())));
                impuestos.setAttributeNode(totalTrasladado);
                //nodo traslados
                Element traslados = doc.createElement("cfdi:Traslados");
                impuestos.appendChild(traslados);
                if (iva16() > 0.0) {
                    //nodo traslado
                    Element traslado16 = doc.createElement("cfdi:Traslado");
                    traslados.appendChild(traslado16);
                    //atributos traslado
                    Attr importe16 = doc.createAttribute("Importe");
                    importe16.setValue(new DecimalFormat("0.00").format(iva16()));
                    traslado16.setAttributeNode(importe16);
                    Attr impuesto16 = doc.createAttribute("Impuesto");
                    impuesto16.setValue("002");
                    traslado16.setAttributeNode(impuesto16);
                    Attr tasa16 = doc.createAttribute("TasaOCuota");
                    tasa16.setValue("0.160000");
                    traslado16.setAttributeNode(tasa16);
                    Attr factor16 = doc.createAttribute("TipoFactor");
                    factor16.setValue("Tasa");
                    traslado16.setAttributeNode(factor16);
                }
                if (iva8() > 0.0) {
                    //nodo traslado
                    Element traslado8 = doc.createElement("cfdi:Traslado");
                    traslados.appendChild(traslado8);
                    //atributos traslado
                    Attr importe8 = doc.createAttribute("Importe");
                    importe8.setValue(new DecimalFormat("0.00").format(iva8()));
                    traslado8.setAttributeNode(importe8);
                    Attr impuesto8 = doc.createAttribute("Impuesto");
                    impuesto8.setValue("002");
                    traslado8.setAttributeNode(impuesto8);
                    Attr tasa8 = doc.createAttribute("TasaOCuota");
                    tasa8.setValue("0.160000");
                    traslado8.setAttributeNode(tasa8);
                    Attr factor8 = doc.createAttribute("TipoFactor");
                    factor8.setValue("Tasa");
                    traslado8.setAttributeNode(factor8);
                }
                if (iva0()) {
                    //nodo traslado
                    Element traslado0 = doc.createElement("cfdi:Traslado");
                    traslados.appendChild(traslado0);
                    //atributos traslado
                    Attr importe0 = doc.createAttribute("Importe");
                    importe0.setValue(new DecimalFormat("0.00").format(iva8()));
                    traslado0.setAttributeNode(importe0);
                    Attr impuesto0 = doc.createAttribute("Impuesto");
                    impuesto0.setValue("002");
                    traslado0.setAttributeNode(impuesto0);
                    Attr tasa0 = doc.createAttribute("TasaOCuota");
                    tasa0.setValue("0.160000");
                    traslado0.setAttributeNode(tasa0);
                    Attr factor0 = doc.createAttribute("TipoFactor");
                    factor0.setValue("Tasa");
                    traslado0.setAttributeNode(factor0);
                }
            }
            //Complemento
            if (listaImpuestosCfdis != null) {
                Element complemento = doc.createElement("cfdi:Complemento");
                rootElement.appendChild(complemento);
                //Nodo impuestos locales
                Element impuestosLocales = doc.createElement("implocal:ImpuestosLocales");
                complemento.appendChild(impuestosLocales);
                //Atributo impuestos locales
                Attr versionLocal = doc.createAttribute("version");
                versionLocal.setValue("1.0");
                impuestosLocales.setAttributeNode(versionLocal);
                Attr totalRetenciones = doc.createAttribute("TotaldeRetenciones");
                totalRetenciones.setValue(retenidosLocales());
                impuestosLocales.setAttributeNode(totalRetenciones);
                Attr totalTraslados = doc.createAttribute("TotaldeTraslados");
                totalTraslados.setValue(trasladosLocales());
                impuestosLocales.setAttributeNode(totalTraslados);
                if (Double.parseDouble(retenidosLocales()) > 0.0) {
                    for (int i = 0; i < listaImpuestosCfdis.size(); i++) {
                        if (listaImpuestosCfdis.get(i).getImpuestosLocalesId().getTipoimpuestoId().getTipoImpuesto().equals("Retención")) {
                            //Nodo RetencionesLocales
                            Element retencionLocal = doc.createElement("implocal:RetencionesLocales");
                            impuestosLocales.appendChild(retencionLocal);
                            //Atributos RetencionesLocales
                            Attr retenidoLocal = doc.createAttribute("ImpLocRetenido");
                            retenidoLocal.setValue(listaImpuestosCfdis.get(i).getImpuestosLocalesId().getNombreImpuesto());
                            retencionLocal.setAttributeNode(retenidoLocal);
                            Attr tasaRetencion = doc.createAttribute("TasadeRetencion");
                            tasaRetencion.setValue(new DecimalFormat("0.00").format(listaImpuestosCfdis.get(i).getImpuestosLocalesId().getTasaCuota()));
                            retencionLocal.setAttributeNode(tasaRetencion);
                            Attr importeRetenido = doc.createAttribute("Importe");
                            importeRetenido.setValue(new DecimalFormat("0.00").format(listaImpuestosCfdis.get(i).getImporte()));
                            retencionLocal.setAttributeNode(importeRetenido);
                        }
                    }
                }
                if (Double.parseDouble(trasladosLocales()) > 0.0) {
                    for (int i = 0; i < listaImpuestosCfdis.size(); i++) {
                        if (listaImpuestosCfdis.get(i).getImpuestosLocalesId().getTipoimpuestoId().getTipoImpuesto().equals("Traslado")) {
                            //Nodo TrasladosLocales
                            Element trasladadoLocal = doc.createElement("implocal:TrasladosLocales");
                            impuestosLocales.appendChild(trasladadoLocal);
                            //Atributos TrasladosLocales
                            Attr trasladoLocal = doc.createAttribute("ImpLocTrasladado");
                            trasladoLocal.setValue(listaImpuestosCfdis.get(i).getImpuestosLocalesId().getNombreImpuesto());
                            trasladadoLocal.setAttributeNode(trasladoLocal);
                            Attr tasaTraslado = doc.createAttribute("TasadeTraslado");
                            tasaTraslado.setValue(new DecimalFormat("0.00").format(listaImpuestosCfdis.get(i).getImpuestosLocalesId().getTasaCuota()));
                            trasladadoLocal.setAttributeNode(tasaTraslado);
                            Attr importeTraslado = doc.createAttribute("Importe");
                            importeTraslado.setValue(new DecimalFormat("0.00").format(listaImpuestosCfdis.get(i).getImporte()));
                            trasladadoLocal.setAttributeNode(importeTraslado);
                        }
                    }
                }
            }
            System.out.println("Trasladados2");
            System.out.println("Probando");
            File xslt = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/cadenaoriginal_3_3.xslt"));            //Inicio calcula cadena original
            StreamSource sourceXSL = new StreamSource(xslt);
            System.out.println("Probando2");

            DOMSource source = new DOMSource(doc);
            System.out.println("Probando3");
            tempFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/xmlTemp.xml"));
            //tempFile = new File("C:\\Users\\Developer1\\Desktop\\Nueva carpeta\\NuevoPrueba.xml");
            System.out.println("Probando3.1");
            FileWriter writer = new FileWriter(tempFile);
            System.out.println("Probando3.2");
            StreamResult sourceXml = new StreamResult(writer);
            TransformerFactory trasnformerFactory = TransformerFactory.newInstance();
            Transformer trasnformer = trasnformerFactory.newTransformer();
            trasnformer.transform(source, sourceXml);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();           //CadenaOrignal
            StreamResult cadenaOriginal = new StreamResult(baos);
            StreamSource sourceXml2 = new StreamSource(tempFile);
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer trasnformer2 = tFactory.newTransformer(sourceXSL);
            trasnformer2.transform(sourceXml2, cadenaOriginal);
            cadOrig = baos.toString();                                          //CadenaOriginal
            crearSello(cadOrig);
            crearCertificado();
            leerCfdi(tempFile);
            if (modificarXml(cadOrig, tempFile)) {
                valor = true;
            } else {
                valor = false;
            }//*/
            System.out.println("Probando4");
        } catch (Exception ex) {
            System.out.println("ErrorIngEgre: " + ex.getMessage() + " | " + ex.getLocalizedMessage());
        }
        return valor;
    }

    public void verConcepto() {
        System.out.println(lineas.getCantidad() + " | " + lineas.getClaveProdServ()
                + " | " + lineas.getNoIdentificacion() + " | " + lineas.getClaveUnidad()
                + " | " + lineas.getUnidad() + " | " + lineas.getDescripcion()
                + " | " + lineas.getValorUnit() + " | " + lineas.getTipoImpuesto()
                + " | " + lineas.getImpuesto() + " | " + lineas.getTipoFactor()
                + " | " + lineas.getTipoTasa() + " | " + lineas.getImporteimpuesto()
                + " | " + lineas.getDescuento());
    }

    public void verDatos() {
        if (iva16() > 0.0) {
            System.out.println("Total IVA16: " + iva16());
        }
        if (iva8() > 0.0) {
            System.out.println("Total IVA8: " + iva8());
        }
        if (iva0()) {
            System.out.println("IVA0: " + iva0());
        }
    }

    public Double iva16() {
        Double iva16 = 0.0;
        for (int i = 0; i < cfdisLineas.size(); i++) {
            if (cfdisLineas.get(i).getTipoTasa() != null && cfdisLineas.get(i).getTipoTasa() > 1.0) {
                if (cfdisLineas.get(i).getTipoImpuesto() != null && cfdisLineas.get(i).getTipoImpuesto().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto() != null && cfdisLineas.get(i).getImpuesto().equals("002")
                        && cfdisLineas.get(i).getTipoFactor() != null && cfdisLineas.get(i).getTipoFactor().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa() != null && cfdisLineas.get(i).getTipoTasa().equals(16.0)) {
                    iva16 += cfdisLineas.get(i).getImporteimpuesto();
                }
            } else {
                if (cfdisLineas.get(i).getTipoImpuesto() != null && cfdisLineas.get(i).getTipoImpuesto().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto() != null && cfdisLineas.get(i).getImpuesto().equals("002")
                        && cfdisLineas.get(i).getTipoFactor() != null && cfdisLineas.get(i).getTipoFactor().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa() != null && cfdisLineas.get(i).getTipoTasa().equals(0.16)) {
                    iva16 += cfdisLineas.get(i).getImporteimpuesto();
                }
            }
            if (cfdisLineas.get(i).getTipoTasa2() != null && cfdisLineas.get(i).getTipoTasa2() > 1.0) {
                if (cfdisLineas.get(i).getTipoImpuesto2() != null && cfdisLineas.get(i).getTipoImpuesto2().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto2() != null && cfdisLineas.get(i).getImpuesto2().equals("002")
                        && cfdisLineas.get(i).getTipoFactor2() != null && cfdisLineas.get(i).getTipoFactor2().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa2() != null && cfdisLineas.get(i).getTipoTasa2().equals(16.0)) {
                    iva16 += cfdisLineas.get(i).getImporteImpuesto2();
                }
            } else {
                if (cfdisLineas.get(i).getTipoImpuesto2() != null && cfdisLineas.get(i).getTipoImpuesto2().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto2() != null && cfdisLineas.get(i).getImpuesto2().equals("002")
                        && cfdisLineas.get(i).getTipoFactor2() != null && cfdisLineas.get(i).getTipoFactor2().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa2() != null && cfdisLineas.get(i).getTipoTasa2().equals(0.16)) {
                    iva16 += cfdisLineas.get(i).getImporteImpuesto2();
                }
            }
            if (cfdisLineas.get(i).getTipoTasa3() != null && cfdisLineas.get(i).getTipoTasa3() > 1.0) {
                if (cfdisLineas.get(i).getTipoImpuesto3() != null && cfdisLineas.get(i).getTipoImpuesto3().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto3() != null && cfdisLineas.get(i).getImpuesto3().equals("002")
                        && cfdisLineas.get(i).getTipoFactor3() != null && cfdisLineas.get(i).getTipoFactor3().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa3() != null && cfdisLineas.get(i).getTipoTasa3().equals(16.0)) {
                    iva16 += cfdisLineas.get(i).getImporteImpuesto3();
                }
            } else {
                if (cfdisLineas.get(i).getTipoImpuesto3() != null && cfdisLineas.get(i).getTipoImpuesto3().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto3() != null && cfdisLineas.get(i).getImpuesto3().equals("002")
                        && cfdisLineas.get(i).getTipoFactor3() != null && cfdisLineas.get(i).getTipoFactor3().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa3() != null && cfdisLineas.get(i).getTipoTasa3().equals(0.16)) {
                    iva16 += cfdisLineas.get(i).getImporteImpuesto3();
                }
            }
            if (cfdisLineas.get(i).getTipoTasa4() != null && cfdisLineas.get(i).getTipoTasa4() > 1.0) {
                if (cfdisLineas.get(i).getTipoImpuesto4() != null && cfdisLineas.get(i).getTipoImpuesto4().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto4() != null && cfdisLineas.get(i).getImpuesto4().equals("002")
                        && cfdisLineas.get(i).getTipoFactor4() != null && cfdisLineas.get(i).getTipoFactor4().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa4() != null && cfdisLineas.get(i).getTipoTasa4().equals(16.0)) {
                    iva16 += cfdisLineas.get(i).getImporteImpuesto4();
                }
            } else {
                if (cfdisLineas.get(i).getTipoImpuesto4() != null && cfdisLineas.get(i).getTipoImpuesto4().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto4() != null && cfdisLineas.get(i).getImpuesto4().equals("002")
                        && cfdisLineas.get(i).getTipoFactor4() != null && cfdisLineas.get(i).getTipoFactor4().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa4() != null && cfdisLineas.get(i).getTipoTasa4().equals(0.16)) {
                    iva16 += cfdisLineas.get(i).getImporteImpuesto4();
                }
            }
        }
        return iva16;
    }

    public boolean iva0() {
        Double iva0 = 0.0;
        boolean valor = false;
        for (int i = 0; i < cfdisLineas.size(); i++) {
            if (cfdisLineas.get(i).getTipoTasa() != null && cfdisLineas.get(i).getTipoTasa() > 1.0) {
                if (cfdisLineas.get(i).getTipoImpuesto() != null && cfdisLineas.get(i).getTipoImpuesto().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto() != null && cfdisLineas.get(i).getImpuesto().equals("002")
                        && cfdisLineas.get(i).getTipoFactor() != null && cfdisLineas.get(i).getTipoFactor().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa() != null && cfdisLineas.get(i).getTipoTasa().equals(0.0)) {
                    iva0 += cfdisLineas.get(i).getImporteimpuesto();
                    valor = true;
                }
            } else {
                if (cfdisLineas.get(i).getTipoImpuesto() != null && cfdisLineas.get(i).getTipoImpuesto().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto() != null && cfdisLineas.get(i).getImpuesto().equals("002")
                        && cfdisLineas.get(i).getTipoFactor() != null && cfdisLineas.get(i).getTipoFactor().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa() != null && cfdisLineas.get(i).getTipoTasa().equals(0.0)) {
                    iva0 += cfdisLineas.get(i).getImporteimpuesto();
                    valor = true;
                }
            }
            if (cfdisLineas.get(i).getTipoTasa2() != null && cfdisLineas.get(i).getTipoTasa2() > 1.0) {
                if (cfdisLineas.get(i).getTipoImpuesto2() != null && cfdisLineas.get(i).getTipoImpuesto2().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto2() != null && cfdisLineas.get(i).getImpuesto2().equals("002")
                        && cfdisLineas.get(i).getTipoFactor2() != null && cfdisLineas.get(i).getTipoFactor2().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa2() != null && cfdisLineas.get(i).getTipoTasa2().equals(0.0)) {
                    iva0 += cfdisLineas.get(i).getImporteImpuesto2();
                    valor = true;
                }
            } else {
                if (cfdisLineas.get(i).getTipoImpuesto2() != null && cfdisLineas.get(i).getTipoImpuesto2().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto2() != null && cfdisLineas.get(i).getImpuesto2().equals("002")
                        && cfdisLineas.get(i).getTipoFactor2() != null && cfdisLineas.get(i).getTipoFactor2().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa2() != null && cfdisLineas.get(i).getTipoTasa2().equals(0.0)) {
                    iva0 += cfdisLineas.get(i).getImporteImpuesto2();
                    valor = true;
                }
            }
            if (cfdisLineas.get(i).getTipoTasa3() != null && cfdisLineas.get(i).getTipoTasa3() > 1.0) {
                if (cfdisLineas.get(i).getTipoImpuesto3() != null && cfdisLineas.get(i).getTipoImpuesto3().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto3() != null && cfdisLineas.get(i).getImpuesto3().equals("002")
                        && cfdisLineas.get(i).getTipoFactor3() != null && cfdisLineas.get(i).getTipoFactor3().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa3() != null && cfdisLineas.get(i).getTipoTasa3().equals(0.0)) {
                    iva0 += cfdisLineas.get(i).getImporteImpuesto3();
                    valor = true;
                }
            } else {
                if (cfdisLineas.get(i).getTipoImpuesto3() != null && cfdisLineas.get(i).getTipoImpuesto3().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto3() != null && cfdisLineas.get(i).getImpuesto3().equals("002")
                        && cfdisLineas.get(i).getTipoFactor3() != null && cfdisLineas.get(i).getTipoFactor3().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa3() != null && cfdisLineas.get(i).getTipoTasa3().equals(0.0)) {
                    iva0 += cfdisLineas.get(i).getImporteImpuesto3();
                    valor = true;
                }
            }
            if (cfdisLineas.get(i).getTipoTasa4() != null && cfdisLineas.get(i).getTipoTasa4() > 1.0) {
                if (cfdisLineas.get(i).getTipoImpuesto4() != null && cfdisLineas.get(i).getTipoImpuesto4().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto4() != null && cfdisLineas.get(i).getImpuesto4().equals("002")
                        && cfdisLineas.get(i).getTipoFactor4() != null && cfdisLineas.get(i).getTipoFactor4().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa4() != null && cfdisLineas.get(i).getTipoTasa4().equals(0.0)) {
                    iva0 += cfdisLineas.get(i).getImporteImpuesto4();
                    valor = true;
                }
            } else {
                if (cfdisLineas.get(i).getTipoImpuesto4() != null && cfdisLineas.get(i).getTipoImpuesto4().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto4() != null && cfdisLineas.get(i).getImpuesto4().equals("002")
                        && cfdisLineas.get(i).getTipoFactor4() != null && cfdisLineas.get(i).getTipoFactor4().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa4() != null && cfdisLineas.get(i).getTipoTasa4().equals(0.0)) {
                    iva0 += cfdisLineas.get(i).getImporteImpuesto4();
                    valor = true;
                }
            }
        }
        return valor;
    }

    public Double iva8() {
        Double iva8 = 0.0;
        for (int i = 0; i < cfdisLineas.size(); i++) {
            if (cfdisLineas.get(i).getTipoTasa() != null && cfdisLineas.get(i).getTipoTasa() > 1.0) {
                if (cfdisLineas.get(i).getTipoImpuesto() != null && cfdisLineas.get(i).getTipoImpuesto().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto() != null && cfdisLineas.get(i).getImpuesto().equals("002")
                        && cfdisLineas.get(i).getTipoFactor() != null && cfdisLineas.get(i).getTipoFactor().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa() != null && cfdisLineas.get(i).getTipoTasa().equals(8.0)) {
                    iva8 += cfdisLineas.get(i).getImporteimpuesto();
                }
            } else {
                if (cfdisLineas.get(i).getTipoImpuesto() != null && cfdisLineas.get(i).getTipoImpuesto().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto() != null && cfdisLineas.get(i).getImpuesto().equals("002")
                        && cfdisLineas.get(i).getTipoFactor() != null && cfdisLineas.get(i).getTipoFactor().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa() != null && cfdisLineas.get(i).getTipoTasa().equals(0.08)) {
                    iva8 += cfdisLineas.get(i).getImporteimpuesto();
                }
            }
            if (cfdisLineas.get(i).getTipoTasa2() != null && cfdisLineas.get(i).getTipoTasa2() > 1.0) {
                if (cfdisLineas.get(i).getTipoImpuesto2() != null && cfdisLineas.get(i).getTipoImpuesto2().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto2() != null && cfdisLineas.get(i).getImpuesto2().equals("002")
                        && cfdisLineas.get(i).getTipoFactor2() != null && cfdisLineas.get(i).getTipoFactor2().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa2() != null && cfdisLineas.get(i).getTipoTasa2().equals(8.0)) {
                    iva8 += cfdisLineas.get(i).getImporteImpuesto2();
                }
            } else {
                if (cfdisLineas.get(i).getTipoImpuesto2() != null && cfdisLineas.get(i).getTipoImpuesto2().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto2() != null && cfdisLineas.get(i).getImpuesto2().equals("002")
                        && cfdisLineas.get(i).getTipoFactor2() != null && cfdisLineas.get(i).getTipoFactor2().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa2() != null && cfdisLineas.get(i).getTipoTasa2().equals(0.08)) {
                    iva8 += cfdisLineas.get(i).getImporteImpuesto2();
                }
            }
            if (cfdisLineas.get(i).getTipoTasa3() != null && cfdisLineas.get(i).getTipoTasa3() > 1.0) {
                if (cfdisLineas.get(i).getTipoImpuesto3() != null && cfdisLineas.get(i).getTipoImpuesto3().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto3() != null && cfdisLineas.get(i).getImpuesto3().equals("002")
                        && cfdisLineas.get(i).getTipoFactor3() != null && cfdisLineas.get(i).getTipoFactor3().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa3() != null && cfdisLineas.get(i).getTipoTasa3().equals(8.0)) {
                    iva8 += cfdisLineas.get(i).getImporteImpuesto3();
                }
            } else {
                if (cfdisLineas.get(i).getTipoImpuesto3() != null && cfdisLineas.get(i).getTipoImpuesto3().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto3() != null && cfdisLineas.get(i).getImpuesto3().equals("002")
                        && cfdisLineas.get(i).getTipoFactor3() != null && cfdisLineas.get(i).getTipoFactor3().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa3() != null && cfdisLineas.get(i).getTipoTasa3().equals(0.08)) {
                    iva8 += cfdisLineas.get(i).getImporteImpuesto3();
                }
            }
            if (cfdisLineas.get(i).getTipoTasa4() != null && cfdisLineas.get(i).getTipoTasa4() > 1.0) {
                if (cfdisLineas.get(i).getTipoImpuesto4() != null && cfdisLineas.get(i).getTipoImpuesto4().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto4() != null && cfdisLineas.get(i).getImpuesto4().equals("002")
                        && cfdisLineas.get(i).getTipoFactor4() != null && cfdisLineas.get(i).getTipoFactor4().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa4() != null && cfdisLineas.get(i).getTipoTasa4().equals(8.0)) {
                    iva8 += cfdisLineas.get(i).getImporteImpuesto4();
                }
            } else {
                if (cfdisLineas.get(i).getTipoImpuesto4() != null && cfdisLineas.get(i).getTipoImpuesto4().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto4() != null && cfdisLineas.get(i).getImpuesto4().equals("002")
                        && cfdisLineas.get(i).getTipoFactor4() != null && cfdisLineas.get(i).getTipoFactor4().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa4() != null && cfdisLineas.get(i).getTipoTasa4().equals(0.08)) {
                    iva8 += cfdisLineas.get(i).getImporteImpuesto4();
                }
            }
        }
        return iva8;
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

    public Double calcularsaldoInsoluto(Double saldoPagado, Double saldoAnterior) {
        monto = cfdisId.getMontoPago();
        Double cantidadPagada = 0.0;
        if (saldoPagado > 0.0) {
            System.out.println(saldoAnterior + " | " + saldoPagado);
            montoTotal += saldoPagado;
            if (monto != null) {
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
        }
        System.out.println("Insoluto: " + cfdi2.getSaldoInsoluto());
        System.out.println("listaCfdisSeleccion: " + (listaCfdisSeleccion != null) + " | " + listaCfdisSeleccion + " | " + personas.getRfc());
        listaCfdisSeleccion = cfdisFacade.obtenerCfdisParaComplementos(personas.getRfc());
        System.out.println("listaCfdisSeleccion2: " + (listaCfdisSeleccion != null) + " | " + listaCfdisSeleccion + " | " + personas.getRfc());
        return saldoIinsoluto;
    }

    public void listaCfdis1() {
        if (listaCfdis != null) {
            for (int i = 0; i < listaCfdis.size(); i++) {
                System.out.println("PRUEBA: " + listaCfdis.get(i).getSaldoPagar() + " | " + listaCfdis.get(i).getSaldoPagado() + " | " + listaCfdis.get(i).getSaldoInsoluto() + " | " + listaCfdis.get(i).getCantidadPagada());
            }
        }
        if (cfdisLineas != null) {
            for (int a = 0; a < cfdisLineas.size(); a++) {
                System.out.println("cfdisLineas: " + cfdisLineas.get(a).getClaveProdServ());
            }
        }
        crearFirmas();
        System.out.println("Firmas: " + firmas.getCargo1() + " | " + firmas.getNombre1() + " | " + firmas.getCargo2() + " | " + firmas.getNombre2()
                + " | " + firmas.getCargo3() + " | " + firmas.getNombre3() + " | " + firmas.getCargo4() + " | " + firmas.getNombre4()
                + " | " + firmas.getCargo5() + " | " + firmas.getNombre5() + " | " + firmas.getCreadoPor() + " | " + firmas.getFechaCreacion()
                + " | " + firmas.getUltimaActualizacionPor() + " | " + firmas.getUltimaFechaActualizacion());
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
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

    public void prepareImpLocal() {
        this.listaImpuestosCfdisXLinea = new ArrayList<>();
        impLocales = new RnGcImpuestosLocalesTbl();
        impLocales2 = new RnGcImpuestosLocalesTbl();
        impuestosCfdis = new RnGcImpuestosCfdisTbl();
        if (listaImpuestosCfdis != null) {
            System.out.println("if");
            for (int i = 0; i < listaImpuestosCfdis.size(); i++) {
                if (listaImpuestosCfdis.get(i).getCfdisLineasId().equals(selectedLinea));
                this.listaImpuestosCfdisXLinea.add(listaImpuestosCfdis.get(i));
            }
        }
    }

    public void buscarImpLocal() {
        if (impLocales != null) {
            impLocales2 = impLocales;
        }
        impLocales = new RnGcImpuestosLocalesTbl();
    }

    public Double importeImpuestoCfdis(Double tasaCuota) {
        Double importeImpuestoCfdis = 0.0;
        if (tasaCuota != null) {
            if (tasaCuota < 1.0) {
                importeImpuestoCfdis = (Double.parseDouble(calcularSubtotal()) * tasaCuota);
            } else {
                importeImpuestoCfdis = (Double.parseDouble(calcularSubtotal()) * (tasaCuota / 100));
            }
        }
        return importeImpuestoCfdis;
    }

    public Double importeImpuestoCfdis2(RnGcCfdisLineasTbl linea, Double tasaCuota) {
        Double importeImpuestoCfdis = 0.0;
        Double importe = 0.0;
        if (impuestosCfdis == null) {
            this.impuestosCfdis = new RnGcImpuestosCfdisTbl();
        }
        importe = linea.getImporte();
        System.out.println("importe: " + importe);
        if (tasaCuota != null) {
            if (tasaCuota > 1.0) {
                importeImpuestoCfdis = importe * (tasaCuota / 100);
                impuestosCfdis.setImporte(importeImpuestoCfdis);
                impuestosCfdis.setCfdisLineasId(linea);
            } else {
                importeImpuestoCfdis = importe * tasaCuota;
                impuestosCfdis.setImporte(importeImpuestoCfdis);
                impuestosCfdis.setCfdisLineasId(linea);
            }
        }
        System.out.println("importeImpuestoCfdis: " + importeImpuestoCfdis);
        return importeImpuestoCfdis;
    }

    public void agregarImpuestosCfdis() {
        if (!obtenerImpuestosLocales()) {
            impLocales2.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
            impLocales2.setFechaCreacion(new Date());
            impLocales2.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
            impLocales2.setUltimaFechaActualizacion(new Date());
            impLocales2.setId((int) (Math.random() * 999999999));
            impLocalesFacade.edit(impLocales2);
        }
        if (listaImpuestosCfdis == null) {
            this.listaImpuestosCfdis = new ArrayList<>();
        }
        impuestosCfdis.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        impuestosCfdis.setFechaCreacion(new Date());
        impuestosCfdis.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        impuestosCfdis.setUltimaFechaActualizacion(new Date());
        impuestosCfdis.setImpuestosLocalesId(impLocales2);
        impuestosCfdis.setId((int) (Math.random() * 999999999));
        listaImpuestosCfdis.add(impuestosCfdis);
        listaImpuestosCfdisXLinea.add(impuestosCfdis);
        System.out.println("impuestosCfdis: " + impuestosCfdis.getImporte() + " | " + impuestosCfdis.getImpuestosLocalesId()
                + " | " + impuestosCfdis.getCreadoPor() + " | " + impuestosCfdis.getFechaCreacion()
                + " | " + impuestosCfdis.getUltimaActualizacionPor() + " | " + impuestosCfdis.getUltimaFechaActualizacion()
                + " | " + impuestosCfdis.getCfdisLineasId().getBase());
        impuestosCfdis = new RnGcImpuestosCfdisTbl();
        impLocales2 = new RnGcImpuestosLocalesTbl();
        calcularTotal();
    }

    public boolean obtenerImpuestosLocales() {
        boolean valor = false;
        listaImpLocales = impLocalesFacade.obtenerImpLocalesXUsuario(usuarioFirmado.obtenerIdUsuario());
        if (listaImpLocales.size() > 0) {
            for (int i = 0; i < listaImpLocales.size(); i++) {
                if (impLocales2.getNombreImpuesto().equals(listaImpLocales.get(i).getNombreImpuesto())) {
                    valor = true;
                    break;
                }
            }
        }
        return valor;
    }

    public void editarImpCfdis() {
        System.out.println("impuestosCfdis: " + impuestosCfdis.getImpuestosLocalesId() + " | " + impuestosCfdis.getImporte() + " | " + impuestosCfdis.getCfdisLineasId()
                + " | " + impuestosCfdis.getCfdisLineasId().getBase());
        impuestosCfdis.setImporte(impuestosCfdis.getImporte());
        listaImpuestosCfdisXLinea.remove(impuestosCfdis);
        listaImpuestosCfdis.remove(impuestosCfdis);
        listaImpuestosCfdisXLinea.add(impuestosCfdis);
        listaImpuestosCfdis.add(impuestosCfdis);
        System.out.println("impuestosCfdis2: " + impuestosCfdis.getImpuestosLocalesId() + " | " + impuestosCfdis.getImporte() + " | " + impuestosCfdis.getCfdisLineasId()
                + " | " + impuestosCfdis.getCfdisLineasId().getBase());
        calcularTotal();
    }

    public void eliminarImpCfdis() {
        System.out.println("listaImpuestosCfdis: " + listaImpuestosCfdis.size() + " | " + listaImpuestosCfdisXLinea.size());
        listaImpuestosCfdis.remove(impuestosCfdis);
        listaImpuestosCfdisXLinea.remove(impuestosCfdis);
        System.out.println("listaImpuestosCfdis2: " + listaImpuestosCfdis.size() + " | " + listaImpuestosCfdisXLinea.size());
    }

    public void guardarRegistros() {
        System.out.println("guardarRegistros");
        cfdisId.setRfcReceptor(personas.getRfc());
        cfdisId.setNombreReceptor(personas.getNombre());
        cfdisId.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        cfdisId.setFechaCreacion(new Date());
        cfdisId.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        cfdisId.setUltimaFechaActualizacion(new Date());
        cfdisId = cfdisFacade.refreshFromDB(cfdisId);
        System.out.println("cfdisId: " + cfdisId);
        cfdisId = cfdisFacade.refreshFromDB(cfdisId);
        for (int i = 0; i < cfdisLineas.size(); i++) {
            cfdisLineas.get(i).setCfdisId(cfdisId);
            lineasCfdisFacade.edit(cfdisLineas.get(i));
            System.out.println("cfdisLineas: " + cfdisLineas.get(i).getId());
        }
        /*cfdisId.setRfcReceptor(personas.getRfc());
        cfdisId.setNombreReceptor(personas.getNombre());
        cfdisId.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        cfdisId.setFechaCreacion(new Date());
        cfdisId.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        cfdisId.setUltimaFechaActualizacion(new Date());
        cfdisId = cfdisFacade.refreshFromDB(cfdisId);
        System.out.println("cfdisId: " + cfdisId);
        for (int i = 0; i < cfdisLineas.size(); i++) {
            cfdisLineas.get(i).setCfdisId(cfdisId);
            lineas = lineasCfdisFacade.refreshFromDB(cfdisLineas.get(i));
            for (int j = 0; j < listaImpuestosCfdis.size(); j++) {
                if (listaImpuestosCfdis.get(j).getCfdisLineasId().getDescripcion().equals(lineas.getDescripcion())) {
                    RnGcImpuestosCfdisTbl get = listaImpuestosCfdis.get(j);
                    get.setCfdisLineasId(lineas);
                    get = impCfdisFacade.refreshFromDB(get);
                    System.out.println("impuestosCfdis: " + impuestosCfdis.getCfdisLineasId() + " | " + impuestosCfdis + " | " + listaImpuestosCfdis.size());
                    get = new RnGcImpuestosCfdisTbl();
                }
            }
            System.out.println("lineas: " + lineas);
            lineas = new RnGcCfdisLineasTbl();
        }
        cfdisId = new RnGcCfdisTbl();
        /*for(int i = 0; i < listaImpuestosCfdis.size(); i++) {
            RnGcImpuestosCfdisTbl get = listaImpuestosCfdis.get(i);
            impuestosCfdis = impCfdisFacade.refreshFromDB(get);
            System.out.println("impuestosCfdis: " + impuestosCfdis.getCfdisLineasId() + " | " + impuestosCfdis + " | " + listaImpuestosCfdis.size());
        }
        impuestosCfdis = new RnGcImpuestosCfdisTbl();*/
    }

    public List<RnGcImpuestosCfdisTbl> listaImpCfdis() {
        System.out.println("selectedLinea: " + selectedLinea.getId());
        for (int i = 0; i < listaImpuestosCfdis.size(); i++) {
            if (listaImpuestosCfdis.get(i).getCfdisLineasId().getId().equals(selectedLinea.getId())) {
                listaImpuestosCfdisXLinea.add(listaImpuestosCfdis.get(i));
            }
        }
        return listaImpuestosCfdisXLinea;
    }

    public void selectedLineaImpCfdiLinea(RnGcCfdisLineasTbl selected) {
        System.out.println("selected: " + selected);
    }

    public List<RnGcImpuestosCfdisTbl> prepareImpuestoCfdis(RnGcCfdisLineasTbl lineaId) {
        System.out.println("selectedLineas: " + lineaId + " | " + listaImpuestosCfdisXLinea.size());
        this.listaImpuestosCfdisXLinea = new ArrayList<>();
        System.out.println("listaImpuestosCfdisXLinea: " + listaImpuestosCfdisXLinea.size());
        for (int i = 0; i < listaImpuestosCfdis.size(); i++) {
            if (listaImpuestosCfdis.get(i).getCfdisLineasId().equals(selectedLinea)) {
                System.out.println("listaImpuestosCfdis: " + listaImpuestosCfdis.get(i) + " | " + listaImpuestosCfdis.get(i).getCfdisLineasId());
                listaImpuestosCfdisXLinea.add(listaImpuestosCfdis.get(i));
            }
        }
        return listaImpuestosCfdisXLinea;
    }

    public void iniciarListaImpLocal() {
        System.out.println("iniciarListaImpLocal");
        listaImpuestosCfdisXLinea = new ArrayList<>();
    }

    public List<RnGcCfdisTbl> listaCfdisAgregados() {
        listaCfdis = listaCfdisSeleccion;
        System.out.println("listaCfdis: " + listaCfdis + " | " + listaCfdisSeleccion);
        return listaCfdis;
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
        System.out.println("Str: " + new String(array));
        return new String(array);
    }

    public String iniMayusculas(String nombre) {
        char[] caracteres = null;
        if (nombre != null) {
            nombre = nombre.toLowerCase();
            caracteres = nombre.toCharArray();
            caracteres[0] = Character.toUpperCase(caracteres[0]);
            for (int i = 0; i < nombre.length() - 2; i++) {
                if (caracteres[i] == ' ' || caracteres[i] == '.' || caracteres[i] == ',') {
                    caracteres[i + 1] = Character.toUpperCase(caracteres[i + 1]);
                }
            }
        }
        return new String(caracteres);
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

    public RnGcTrabajadoresTbl getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(RnGcTrabajadoresTbl trabajador) {
        this.trabajador = trabajador;
    }

    public RnGcTrabajadoresTbl getTrabajador2() {
        return trabajador2;
    }

    public void setTrabajador2(RnGcTrabajadoresTbl trabajador2) {
        this.trabajador2 = trabajador2;
    }

    public RnGcTrabajadoresTbl getTrabajador3() {
        return trabajador3;
    }

    public void setTrabajador3(RnGcTrabajadoresTbl trabajador3) {
        this.trabajador3 = trabajador3;
    }

    public List<RnGcTrabajadoresTbl> getListaTrabajadores() {
        if (listaTrabajadores == null) {
            this.listaTrabajadores = new ArrayList<>();
        }
        return listaTrabajadores;
    }

    public void setListaTrabajadores(List<RnGcTrabajadoresTbl> listaTrabajadores) {
        this.listaTrabajadores = listaTrabajadores;
    }

    public List<RnGcTrabajadoresTbl> getListaTrabajadoresAux() {
        if (listaTrabajadoresAux == null) {
            this.listaTrabajadoresAux = new ArrayList<>();
        }
        return listaTrabajadoresAux;
    }

    public void setListaTrabajadoresAux(List<RnGcTrabajadoresTbl> listaTrabajadoresAux) {
        this.listaTrabajadoresAux = listaTrabajadoresAux;
    }

    public void agregarTrabajadores() {
        System.out.println("listasTrabajadores1: " + listaTrabajadoresAux + " | " + listaTrabajadores);
        listaTrabajadores = listaTrabajadoresAux;
        listaTrabajadoresAux = new ArrayList<>();
        System.out.println("listasTrabajadores2: " + listaTrabajadoresAux + " | " + listaTrabajadores);
    }

}
