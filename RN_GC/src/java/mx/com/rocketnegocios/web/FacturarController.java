package mx.com.rocketnegocios.web;

import com.sefactura.pac.client.RespuestaTimbrado;
import com.sefactura.pac.client.Sefactura;
//import com.sun.javafx.binding.StringFormatter;
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
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
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
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import mx.com.rocketnegocios.beans.RnGcCatalogosusosTblFacade;
import mx.com.rocketnegocios.beans.RnGcComplementosFacade;
import mx.com.rocketnegocios.beans.RnGcCpCartaPorteTblFacade;
import mx.com.rocketnegocios.beans.RnGcCpOrigendestinoTblFacade;
import mx.com.rocketnegocios.beans.RnGcCpProductosDestinosTblFacade;
import mx.com.rocketnegocios.beans.RnGcCpUnidadesParteTransporteTblFacade;
import mx.com.rocketnegocios.beans.RnGcDireccionesTblFacade;
import mx.com.rocketnegocios.beans.RnGcDocumentosRelacionadosTblFacade;
import mx.com.rocketnegocios.beans.RnGcExportacionTblFacade;
import mx.com.rocketnegocios.beans.RnGcFirmasTblFacade;
import mx.com.rocketnegocios.beans.RnGcFolioserieTblFacade;
import mx.com.rocketnegocios.beans.RnGcFormaspagosTblFacade;
import mx.com.rocketnegocios.beans.RnGcImagenesTblFacade;
import mx.com.rocketnegocios.beans.RnGcImpuestosCfdisTblFacade;
import mx.com.rocketnegocios.beans.RnGcImpuestosLocalesTblFacade;
import mx.com.rocketnegocios.beans.RnGcMonedasTblFacade;
import mx.com.rocketnegocios.beans.RnGcObjetoimpuestoTblFacade;
import mx.com.rocketnegocios.beans.RnGcPagosRelacionadosTblFacade;
import mx.com.rocketnegocios.beans.RnGcProductserviciosTblFacade;
import mx.com.rocketnegocios.beans.RnGcRegimenUsoTblFacade;
import mx.com.rocketnegocios.beans.RnGcRegimenfiscalTblFacade;
import mx.com.rocketnegocios.beans.RnGcTimbresTblFacade;
import mx.com.rocketnegocios.beans.RnGcTiporelacionTblFacade;
import mx.com.rocketnegocios.entities.RnGcCatalogosusosTbl;
import mx.com.rocketnegocios.entities.RnGcCpCartaPorteTbl;
import mx.com.rocketnegocios.entities.RnGcCpOrigendestinoTbl;
import mx.com.rocketnegocios.entities.RnGcCpUnidadesParteTransporteTbl;
import mx.com.rocketnegocios.entities.RnGcDireccionesTbl;
import mx.com.rocketnegocios.entities.RnGcDocumentosRelacionadosTbl;
import mx.com.rocketnegocios.entities.RnGcFirmasTbl;
import mx.com.rocketnegocios.entities.RnGcFolioserieTbl;
import mx.com.rocketnegocios.entities.RnGcFormaspagosTbl;
import mx.com.rocketnegocios.entities.RnGcImagenesTbl;
import mx.com.rocketnegocios.entities.RnGcImpuestosCfdisTbl;
import mx.com.rocketnegocios.entities.RnGcImpuestosLocalesTbl;
import mx.com.rocketnegocios.entities.RnGcMonedasTbl;
import mx.com.rocketnegocios.entities.RnGcObjetoimpuestoTbl;
import mx.com.rocketnegocios.entities.RnGcPagosRelacionadosTbl;
import mx.com.rocketnegocios.entities.RnGcProductserviciosTbl;
import mx.com.rocketnegocios.entities.RnGcRegimenUsoTbl;
import mx.com.rocketnegocios.entities.RnGcRegimenfiscalTbl;
import mx.com.rocketnegocios.entities.RnGcTimbresTbl;
import mx.com.rocketnegocios.entities.RnGcTiporelacionTbl;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import mx.com.rocketnegocios.entities.RnGcComplementos;
import mx.com.rocketnegocios.entities.RnGcCpProductosDestinosTbl;
//import sun.misc.BASE64Decoder;

@SessionScoped
@Named("facturarContoller")
public class FacturarController implements Serializable {

    private static final String ORIGINAL
            = "ÁáÉéÍíÓóÚúÑñÜü";
    private static final String REPLACEMENT
            = "AaEeIiOoUuNnUu";

    public FacturarController() {
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
    private RnGcFormaspagosTblFacade formaPagoFacade;
    
    @EJB
    private RnGcDireccionesTblFacade direccionFacade;
    
    @EJB
    private RnGcCpCartaPorteTblFacade porteFacade;
    
    @EJB
    private RnGcCpOrigendestinoTblFacade origenFacade;
    
    @EJB
    private RnGcCpUnidadesParteTransporteTblFacade parteTransporte;
    
    @EJB
    private RnGcRegimenUsoTblFacade regimenUsoFacade;
    
    @EJB
    private RnGcRegimenfiscalTblFacade regimenFacade;
    
    @EJB
    private RnGcCatalogosusosTblFacade usosFacade;
    
    @EJB
    private RnGcExportacionTblFacade exportacionFacade;
    
    @EJB
    private RnGcMonedasTblFacade monedaFacade;
    
    @EJB
    private RnGcTiporelacionTblFacade tipoRelFacade;
    
    @EJB
    private RnGcObjetoimpuestoTblFacade objetoImpFacade;
    
    @EJB
    private RnGcComplementosFacade ComplementosFacade;
    
    @EJB
    private RnGcCpProductosDestinosTblFacade productoDestinoFacade;

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
    private List<RnGcCfdisTbl> listaPPDsSeleccionado;
    private StreamedContent downLoadFile;
    private StreamedContent downLoadFile2;
    private StreamedContent downLoadFile3;
    private StreamedContent downLoadFile4;
    private List<RnGcCfdisTbl> filteredPPD;
    private RnGcFormaspagosTbl formaPago;
    private List<RnGcCfdisTbl> deleteSeleccionado;
    private String tipoComplemento = "CartaPorte";
    private double variable, variable2, variable3, cantidadProd;
    private Date variableFecha, variableFecha2, variableFecha3;
    private RnGcCpCartaPorteTbl cartaPorte;
    private List<RnGcCfdisLineasTbl> listaprod, productosSelec, listaprod1, listaprod2, listaprod3;
    private int nDestinos;
    private String tipoCartaPorte;
    private RnGcProductserviciosTbl productoSelecto;
    private RnGcPersonasTbl direccion0,direccion00,direccion000;
    private RnGcDireccionesTbl direccion1, direccion2, direccion3;
    private boolean dFactura = false, confirmacion = false;
    private RnGcCfdisLineasTbl productoSelec, productoSelec2, productoSelec3;
    private RnGcCpOrigendestinoTbl origenDestino;
    private List<RnGcCpOrigendestinoTbl> destinos;
    private RnGcDireccionesUsuariosTbl direccRemitente;
    private double baseT16 = 0.0, baseT8 = 0.0, baseT0 = 0.0;
    private List<RnGcFormaspagosTbl> formasPago;
    private String prodServicio = "-";
    private boolean complementoEscuela = false;
    private RnGcComplementos complementoE = new RnGcComplementos();

    public void setComplementoEscuela(boolean complementoEscuela) {
        this.complementoEscuela = complementoEscuela;
    }

    public RnGcUsuariosTbl getUsuario() {
        return usuario;
    }

    public void setUsuario(RnGcUsuariosTbl usuario) {
        this.usuario = usuario;
    }

    public String getTipoCartaPorte() {
        return tipoCartaPorte;
    }

    public void setTipoCartaPorte(String tipoCartaPorte) {
        this.tipoCartaPorte = tipoCartaPorte;
    }

    public double getCantidadProd() {
        return cantidadProd;
    }

    public void setCantidadProd(double cantidadProd) {
        this.cantidadProd = cantidadProd;
    }

    public boolean isComplementoEscuela() {
        return complementoEscuela;
    }

    public String getProdServicio() {
        return prodServicio;
    }

    public void setProdServicio(String prodServicio) {
        this.prodServicio = prodServicio;
    }

    public List<RnGcFormaspagosTbl> getFormasPago() {
        return formasPago;
    }

    public void setFormasPago(List<RnGcFormaspagosTbl> formasPago) {
        this.formasPago = formasPago;
    }
    
    public void configCartaPorte(){
        cartaPorte = new RnGcCpCartaPorteTbl();
        listaprod1 = new ArrayList<>();
        listaprod2 = new ArrayList<>();
        listaprod3 = new ArrayList<>();
        direccion1 = new RnGcDireccionesTbl();
        direccion2 = new RnGcDireccionesTbl();
        direccion3 = new RnGcDireccionesTbl();
        direccion0 = new RnGcPersonasTbl();
        direccion00 = new RnGcPersonasTbl();
        direccion000 = new RnGcPersonasTbl();
        cartaPorte.setClienteProveedorId(personas);
        cartaPorte.setNombreRemitente(cfdisId.getNombreEmisor());
        cartaPorte.setNombreDestinatario(personas.getNombre());
        cartaPorte.setRfcRemitente(cfdisId.getRfcEmisor());
        cartaPorte.setRfcDestinatario(personas.getRfc());
        direccion0.setNombre(personas.getNombre());
        direccion0.setRfc(personas.getRfc());
        direccion00.setNombre(personas.getNombre());
        direccion00.setRfc(personas.getRfc());
        direccion000.setNombre(personas.getNombre());
        direccion000.setRfc(personas.getRfc());
        usuario = usuariosFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        cantidadProd = 0;
        productoSelecto = new RnGcProductserviciosTbl();
        tipoCartaPorte = "Ingreso";
        listaprod = new ArrayList<>();
        for(RnGcCfdisLineasTbl list:cfdisLineas){
            listaprod.add(list);
        }
        nDestinos = 1;
    }
    
    public void desconfirmarCartaPorte(){
        confirmacion = false;
        cartaPorte = null;
        listaprod = null;
        listaprod1 = null;
        listaprod2 = null;
        listaprod3 = null;
        direccion1 = null;
        direccion2 = null;
        direccion3 = null;
        direccion0 = null;
        direccion00 = null;
        direccion000 = null;
        nDestinos = 0;
        origenDestino = null;
        destinos = null;
    }
    
    public void confirmarCartaPorte(){
        cartaPorte.setTransporteInter("No");
        double peso = 0;
        double mercancia = 0;
        double distancia = 0;
        for(RnGcCfdisLineasTbl prod : cfdisLineas){
            peso += prod.getPesoUnitario();
            mercancia++;
        }
        cartaPorte.setTotalMercancia(mercancia);
        cartaPorte.setPesoBrutoTotal(peso);
        cartaPorte.setUnidadPeso("KGM");
        Random r = new Random();
        String n = "";
        for (int j = 0; j < 6; j ++){
            int valorDado = r.nextInt(9)+1;
            n = n + valorDado;
        }
        String origen = "OR" + n;
        if(nDestinos > 1){
            if(nDestinos > 2){
                distancia = 0;
                destinos = new ArrayList<>();
                origenDestino = new RnGcCpOrigendestinoTbl();
                origenDestin(origen,direccion1,variableFecha,variable,direccion0);
                destinos.add(origenDestino);
                origenDestino = new RnGcCpOrigendestinoTbl();
                origenDestin(origen,direccion2,variableFecha2,variable2,direccion00);
                destinos.add(origenDestino);
                origenDestino = new RnGcCpOrigendestinoTbl();
                origenDestin(origen,direccion3,variableFecha3,variable3,direccion000);
                destinos.add(origenDestino);
                distancia = variable + variable2 + variable3;
            }else{
                distancia = 0;
                destinos = new ArrayList<>();
                origenDestino = new RnGcCpOrigendestinoTbl();
                origenDestin(origen,direccion1,variableFecha,variable,direccion0);
                destinos.add(origenDestino);
                origenDestino = new RnGcCpOrigendestinoTbl();
                origenDestin(origen,direccion2,variableFecha2,variable2,direccion00);
                destinos.add(origenDestino);
                distancia = variable + variable2;
            }
        }else{
            distancia = 0;
            destinos = new ArrayList<>();
            origenDestino = new RnGcCpOrigendestinoTbl();
            origenDestin(origen,direccion1,variableFecha,variable,direccion0);
            destinos.add(origenDestino);
            distancia = variable;
        }
        cartaPorte.setTotalDistancia(String.valueOf(distancia));
        confirmacion = true;
    }
    
    public void origenDestin(String origen, RnGcDireccionesTbl direccion, Date fecha, Double distancia, RnGcPersonasTbl destinatario){
        Random r = new Random();
        String n = "";
        for (int j = 0; j < 6; j ++){
            int valorDado = r.nextInt(9)+1;
            n = n + valorDado;
        }
        String destino = "DE" + n;
        origenDestino.setDestino(destino);
        origenDestino.setOrigen(origen);
        origenDestino.setFechaLlegada(fecha);
        origenDestino.setDistancia(distancia);
        origenDestino.setRfcDestinatario(destinatario.getRfc());
        origenDestino.setNombreDestinatario(destinatario.getNombre());
        origenDestino.setClavePais(direccion.getClavePais());
        origenDestino.setClaveEstado(direccion.getClaveEstado());
        origenDestino.setClaveMunicipio(direccion.getClaveMunicipio());
        origenDestino.setClaveColonia(direccion.getClaveColonia());
        origenDestino.setCodPostal(direccion.getCodigoPostal());
        origenDestino.setCalle(direccion.getNombreCalle());
        origenDestino.setNoExt(direccion.getNumeroExterior());
        origenDestino.setNoInt(direccion.getNumeroInterior());
        origenDestino.setFechaSalida(cartaPorte.getFechaSalida());
        
    }
    
    public void direccionConductor(){
        RnGcDireccionesTbl direc = direccionFacade.obtenerDireccionesPorConductor(cartaPorte.getConductorId());
        cartaPorte.setDirConductorId(direc);
    }
    
    public void quitarProducto(){
        System.out.print("quitar producto" );
        if(productoSelec != null){
            if(tipoCartaPorte != null && !"Ingreso".equals(tipoCartaPorte))
                listaprod.add(productoSelec);
            listaprod1.remove(productoSelec);
            productoSelec = null;
        }
    }
    
    public void quitarProducto2(){
        System.out.print("quitar producto 2" );
        if(productoSelec2 != null){
            if(tipoCartaPorte != null && !"Ingreso".equals(tipoCartaPorte))
                listaprod.add(productoSelec2);
            listaprod2.remove(productoSelec2);
            productoSelec2 = null;
        }
    }
    
    public void quitarProducto3(){
        System.out.print("quitar producto 3" );
        if(productoSelec3 != null){
            if(tipoCartaPorte != null && !"Ingreso".equals(tipoCartaPorte))
                listaprod.add(productoSelec3);
            listaprod3.remove(productoSelec3);
            productoSelec3 = null;
        }
    }
    
    public void configDestino1(){
        System.out.print("productosSelec: " + productosSelec);
        System.out.print("productoSelecto: " + productoSelecto);
        if(tipoCartaPorte != null && !"Ingreso".equals(tipoCartaPorte)){
            if(productosSelec != null && !productosSelec.isEmpty()){
                for(int i = 0; i < productosSelec.size(); i++){
                    productosSelec.get(i).setImpuesto4("1");
                    listaprod1.add(productosSelec.get(i));
                    listaprod.remove(productosSelec.get(i));
                }
            }
        }else{
            if(productoSelecto != null){
                RnGcCfdisLineasTbl prodLinea = new RnGcCfdisLineasTbl();
                prodLinea.setId((int)(Math.random() * 100) + 1);
                prodLinea.setClaveProdServ(productoSelecto.getClaveProductServ());
                prodLinea.setNoIdentificacion(productoSelecto.getNoIdentificacion());
                prodLinea.setClaveUnidad(productoSelecto.getClaveUnidad());
                prodLinea.setUnidad(productoSelecto.getUnidad());
                prodLinea.setDescripcion(productoSelecto.getDescripcion());
                prodLinea.setPesoUnitario(productoSelecto.getPeso());
                prodLinea.setProductoId(productoSelecto);
                prodLinea.setPeligroso(productoSelecto.getPeligroso());
                prodLinea.setCantidad(cantidadProd);
                prodLinea.setImpuesto4("1");
                listaprod1.add(prodLinea);
            }
            productoSelecto = null;
        }
    }
    
    public void configDestino2(){
        System.out.print("productosSelec: " + productosSelec);
        System.out.print("productoSelecto: " + productoSelecto);
        if(tipoCartaPorte != null && !"Ingreso".equals(tipoCartaPorte))
            if(productosSelec != null && !productosSelec.isEmpty()){
                for(int i = 0; i < productosSelec.size(); i++){
                    productosSelec.get(i).setImpuesto4("2");
                    listaprod2.add(productosSelec.get(i));
                    listaprod.remove(productosSelec.get(i));
                }
            }
        else{
            if(productoSelecto != null){
                RnGcCfdisLineasTbl prodLinea = new RnGcCfdisLineasTbl();
                prodLinea.setId((int)(Math.random() * 100) + 1);
                prodLinea.setClaveProdServ(productoSelecto.getClaveProductServ());
                prodLinea.setNoIdentificacion(productoSelecto.getNoIdentificacion());
                prodLinea.setClaveUnidad(productoSelecto.getClaveUnidad());
                prodLinea.setUnidad(productoSelecto.getUnidad());
                prodLinea.setDescripcion(productoSelecto.getDescripcion());
                prodLinea.setPesoUnitario(productoSelecto.getPeso());
                prodLinea.setProductoId(productoSelecto);
                prodLinea.setPeligroso(productoSelecto.getPeligroso());
                prodLinea.setCantidad(cantidadProd);
                prodLinea.setImpuesto4("2");
                listaprod2.add(prodLinea);
            }
            productoSelecto = null;
        }
    }
    
    public void configDestino3(){
        System.out.print("productosSelec: " + productosSelec);
        System.out.print("productoSelecto: " + productoSelecto);
        if(tipoCartaPorte != null && !"Ingreso".equals(tipoCartaPorte)){
            if(productosSelec != null && !productosSelec.isEmpty()){
                for(int i = 0; i < productosSelec.size(); i++){
                    productosSelec.get(i).setImpuesto4("3");
                    listaprod3.add(productosSelec.get(i));
                    listaprod.remove(productosSelec.get(i));
                }
            }
        }else{
            if(productoSelecto != null){
                RnGcCfdisLineasTbl prodLinea = new RnGcCfdisLineasTbl();
                prodLinea.setId((int)(Math.random() * 100) + 1);
                prodLinea.setClaveProdServ(productoSelecto.getClaveProductServ());
                prodLinea.setNoIdentificacion(productoSelecto.getNoIdentificacion());
                prodLinea.setClaveUnidad(productoSelecto.getClaveUnidad());
                prodLinea.setUnidad(productoSelecto.getUnidad());
                prodLinea.setDescripcion(productoSelecto.getDescripcion());
                prodLinea.setPesoUnitario(productoSelecto.getPeso());
                prodLinea.setProductoId(productoSelecto);
                prodLinea.setPeligroso(productoSelecto.getPeligroso());
                prodLinea.setCantidad(cantidadProd);
                prodLinea.setImpuesto4("3");
                listaprod3.add(prodLinea);
            }
            productoSelecto = null;
        }
    }

    public void direccionFiscal(){
        if(dFactura){
            System.out.print("sin direccion fiscal ");
            dFactura = false;
            direccion1 = new RnGcDireccionesTbl();
        }
        else{
            System.out.print("con direccion fiscal ");
            dFactura = true;
            direccion1 = new RnGcDireccionesTbl();
            direccion1.setPersonasId(personas);
            direccion1.setNombreCalle(personas.getDomicilio());
            direccion1.setNumeroExterior(personas.getNoExt());
            direccion1.setNumeroInterior(personas.getNoInt());
            direccion1.setCodigoPostal(personas.getCodigoPostal());
            direccion1.setColonia(personas.getLocalidad());
            direccion1.setMunicipio(personas.getCiudad());
            direccion1.setEstado(personas.getEstado());
            direccion1.setPais(personas.getPais());
        }
    }
    
    public void elegirTipoCP(){
        
    }
    
    public void produtoDestino(RnGcCpOrigendestinoTbl destino, RnGcProductserviciosTbl producto) {
        RnGcCpProductosDestinosTbl relacion = new RnGcCpProductosDestinosTbl();
        relacion.setProductoId(producto);
        relacion.setDestinoId(destino);
        relacion.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        relacion.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        relacion.setFechaCreacion(new Date());
        relacion.setUltimaFechaActualizacion(new Date());
        //relacion = productoDestinoFacade.refreshFromDB(relacion);
        productoDestinoFacade.edit(relacion);
    }
    
    public boolean isdFactura() {
        return dFactura;
    }

    public void setdFactura(boolean dFactura) {
        this.dFactura = dFactura;
    }

    public RnGcProductserviciosTbl getProductoSelecto() {
        return productoSelecto;
    }

    public void setProductoSelecto(RnGcProductserviciosTbl productoSelecto) {
        this.productoSelecto = productoSelecto;
    }

    public RnGcPersonasTbl getDireccion0() {
        return direccion0;
    }

    public void setDireccion0(RnGcPersonasTbl direccion0) {
        this.direccion0 = direccion0;
    }
    
    public RnGcPersonasTbl getDireccion00() {
        return direccion00;
    }

    public void setDireccion00(RnGcPersonasTbl direccion00) {
        this.direccion00 = direccion00;
    }
    
    public RnGcPersonasTbl getDireccion000() {
        return direccion0;
    }

    public void setDireccion000(RnGcPersonasTbl direccion000) {
        this.direccion0 = direccion000;
    }
    
    public RnGcCfdisLineasTbl getProductoSelec() {
        return productoSelec;
    }

    public void setProductoSelec(RnGcCfdisLineasTbl productoSelec) {
        this.productoSelec = productoSelec;
    }

    public RnGcCfdisLineasTbl getProductoSelec2() {
        return productoSelec2;
    }

    public void setProductoSelec2(RnGcCfdisLineasTbl productoSelec2) {
        this.productoSelec2 = productoSelec2;
    }

    public RnGcCfdisLineasTbl getProductoSelec3() {
        return productoSelec3;
    }

    public void setProductoSelec3(RnGcCfdisLineasTbl productoSelec3) {
        this.productoSelec3 = productoSelec3;
    }

    public RnGcDireccionesTbl getDireccion1() {
        return direccion1;
    }

    public void setDireccion1(RnGcDireccionesTbl direccion1) {
        this.direccion1 = direccion1;
    }

    public RnGcDireccionesTbl getDireccion2() {
        return direccion2;
    }

    public void setDireccion2(RnGcDireccionesTbl direccion2) {
        this.direccion2 = direccion2;
    }

    public RnGcDireccionesTbl getDireccion3() {
        return direccion3;
    }

    public void setDireccion3(RnGcDireccionesTbl direccion3) {
        this.direccion3 = direccion3;
    }

    public int getnDestinos() {
        return nDestinos;
    }

    public void setnDestinos(int nDestinos) {
        this.nDestinos = nDestinos;
    }

    public List<RnGcCfdisLineasTbl> getProductosSelec() {
        return productosSelec;
    }

    public void setProductosSelec(List<RnGcCfdisLineasTbl> productosSelec) {
        this.productosSelec = productosSelec;
    }

    public List<RnGcCfdisLineasTbl> getListaprod() {
        return listaprod;
    }

    public void setListaprod(List<RnGcCfdisLineasTbl> listaprod) {
        this.listaprod = listaprod;
    }
    
    public List<RnGcCfdisLineasTbl> getListaprod1() {
        return listaprod1;
    }

    public void setListaprod1(List<RnGcCfdisLineasTbl> listaprod1) {
        this.listaprod1 = listaprod1;
    }

    public List<RnGcCfdisLineasTbl> getListaprod2() {
        return listaprod2;
    }

    public void setListaprod2(List<RnGcCfdisLineasTbl> listaprod2) {
        this.listaprod2 = listaprod2;
    }

    public List<RnGcCfdisLineasTbl> getListaprod3() {
        return listaprod3;
    }

    public void setListaprod3(List<RnGcCfdisLineasTbl> listaprod3) {
        this.listaprod3 = listaprod3;
    }

    public RnGcCpCartaPorteTbl getCartaPorte() {
        if(cartaPorte == null){
            cartaPorte = new RnGcCpCartaPorteTbl();
        }
        return cartaPorte;
    }

    public void setCartaPorte(RnGcCpCartaPorteTbl cartaPorte) {
        this.cartaPorte = cartaPorte;
    }

    public String getTipoComplemento() {
        return tipoComplemento;
    }

    public void setTipoComplemento(String tipoComplemento) {
        this.tipoComplemento = tipoComplemento;
    }

    public double getVariable() {
        return variable;
    }

    public void setVariable(double variable) {
        this.variable = variable;
    }

    public double getVariable2() {
        return variable2;
    }

    public void setVariable2(double variable2) {
        this.variable2 = variable2;
    }

    public double getVariable3() {
        return variable3;
    }

    public void setVariable3(double variable3) {
        this.variable3 = variable3;
    }

    public Date getVariableFecha() {
        return variableFecha;
    }

    public void setVariableFecha(Date variableFecha) {
        this.variableFecha = variableFecha;
    }

    public Date getVariableFecha2() {
        return variableFecha2;
    }

    public void setVariableFecha2(Date variableFecha2) {
        this.variableFecha2 = variableFecha2;
    }

    public Date getVariableFecha3() {
        return variableFecha3;
    }

    public void setVariableFecha3(Date variableFecha3) {
        this.variableFecha3 = variableFecha3;
    }

    public List<RnGcCfdisTbl> getDeleteSeleccionado() {
        return deleteSeleccionado;
    }

    public void setDeleteSeleccionado(List<RnGcCfdisTbl> deleteSeleccionado) {
        this.deleteSeleccionado = deleteSeleccionado;
    }

    public RnGcFormaspagosTbl getFormaPago() {
        if (formaPago == null) {
            this.formaPago = new RnGcFormaspagosTbl();
        }
        return formaPago;
    }

    public void setFormaPago(RnGcFormaspagosTbl formaPago) {
        this.formaPago = formaPago;
    }

    public StreamedContent getDownLoadFile4() {
        return downLoadFile4;
    }

    public void setDownLoadFile4(StreamedContent downLoadFile4) {
        this.downLoadFile4 = downLoadFile4;
    }

    public StreamedContent getDownLoadFile3() {
        return downLoadFile3;
    }

    public void setDownLoadFile3(StreamedContent downLoadFile3) {
        this.downLoadFile3 = downLoadFile3;
    }

    public List<RnGcCfdisTbl> getFilteredPPD() {
        return filteredPPD;
    }

    public void setFilteredPPD(List<RnGcCfdisTbl> filteredPPD) {
        this.filteredPPD = filteredPPD;
    }

    public StreamedContent getDownLoadFile2() {
        return downLoadFile2;
    }

    public void setDownLoadFile2(StreamedContent downLoadFile2) {
        this.downLoadFile2 = downLoadFile2;
    }

    public StreamedContent getDownLoadFile() {
        return downLoadFile;
    }

    public void setDownLoadFile(StreamedContent downLoadFile) {
        this.downLoadFile = downLoadFile;
    }

    public List<RnGcCfdisTbl> getListaPPDsSeleccionado() {
        return listaPPDsSeleccionado;
    }

    public void setListaPPDsSeleccionado(List<RnGcCfdisTbl> listaPPDsSeleccionado) {
        this.listaPPDsSeleccionado = listaPPDsSeleccionado;
    }

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
        if(personas2 == null)
            personas2 = new RnGcPersonasTbl();
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
    
    public List<RnGcRegimenUsoTbl> usosRegimen(){
        List<RnGcRegimenUsoTbl> lista = new ArrayList<>(), lista1 = new ArrayList<>();
        if(personas != null && personas.getRegimenFiscalId() != null && personas.getTipoPersonaSat() != null){
            lista1 = regimenUsoFacade.obtenerUsoXRegimen(personas.getRegimenFiscalId(), personas.getTipoPersonaSat());
            for(RnGcRegimenUsoTbl valor : lista1)
                if(!valor.getUsocfdiId().getCUsoCFDI().equals("CN01") && !valor.getUsocfdiId().getCUsoCFDI().equals("CP01"))
                    lista.add(valor);
        }
        return lista;
    }
    
    public void iniciarPago(){
        System.out.print("----------------- inicializar pago -------------------------");
        if(cfdisId != null){
            cfdisId.setClaveRegimenFiscal(String.valueOf(listaUsuarios.get(0).getRegimenId().getClaveRegimenFiscal()));
            cfdisId.setLugarExpedicion(listaUsuarios.get(0).getCodigoPostal());
        }
    }
    
    public int calcularParcialidad(){
        int parcial = 0;
        if(cfdiRelacionado != null){
            if(cfdiRelacionado.getNumeroParcialidad() != null)
                cfdiRelacionado.setNumeroParcialidad(cfdiRelacionado.getNumeroParcialidad() + 1);
            else
                cfdiRelacionado.setNumeroParcialidad(1);
            
            parcial = cfdiRelacionado.getNumeroParcialidad();
        }
        return parcial;
    }
    
    public void iniciarFactura(){
        System.out.print("----------------- inicializar factura -------------------------");
        if(cfdisId != null){
            cfdisId.setTipoComprobante("I");
            if(cfdisId.getExportacionId()== null)
                cfdisId.setExportacionId(exportacionFacade.obtenerUnValor());
            
            cfdisId.setClaveRegimenFiscal(String.valueOf(listaUsuarios.get(0).getRegimenId().getClaveRegimenFiscal()));
            cfdisId.setUsoCfdi(String.valueOf(personas.getUsocfdiId().getCUsoCFDI()));
            RnGcMonedasTbl moneda = monedaFacade.obtenerMoneda();
            cfdisId.setMoneda(moneda.getCMoneda());
            cfdisId.setLugarExpedicion(listaUsuarios.get(0).getCodigoPostal());
            cfdisId.setCertificados_Id(certificadosTbl.obtenerCertificadosActivosDeUsuario(listaUsuarios.get(0)).get(0));
        }
    }
    
    public void elegirFormaPago(){
        if(cfdisId.getMetodoPago().equals("PPD")){
            formasPago = new ArrayList<>();
            cfdisId.setFormaPago("99");
            RnGcFormaspagosTbl forma = formaPagoFacade.obtenerFormaPagoXClave("99");
            formasPago.add(forma);
        }else{
            formasPago = new ArrayList<>();
            cfdisId.setFormaPago("");
            formasPago = formaPagoFacade.findAll();
        }
        
    }

    public void crearCartaPorte() throws Exception {
        System.out.println("crearCartaPorte");
        cartaPorte.setCfdisId(cfdisId);
        cartaPorte.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        cartaPorte.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        cartaPorte.setFechaCreacion(new Date());
        cartaPorte.setUltimaFechaActualizacion(new Date());
        List<RnGcCpCartaPorteTbl> listaPorte = porteFacade.obtenerXDesc();
        if (listaPorte != null && !listaPorte.isEmpty()) {
            String nCP = listaPorte.get(0).getClave();
            String[] parts = nCP.split("_");
            Integer sig = Integer.parseInt(parts[parts.length - 1]) + 1;
            cartaPorte.setClave("CCP_" + sig);
        } else {
            cartaPorte.setClave("CCP_1");
        }
        
        cartaPorte = porteFacade.refreshFromDB(cartaPorte);
        //for(RnGcCpOrigendestinoTbl od : destinos){
        for(int i = 0; i < destinos.size(); i++){
            RnGcCpOrigendestinoTbl od = new RnGcCpOrigendestinoTbl();
            od = destinos.get(i);
            od.setCartaPorteId(cartaPorte);
            od.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
            od.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
            od.setFechaCreacion(new Date());
            od.setUltimaFechaActualizacion(new Date());
            //origenFacade.refreshFromDB(destinos.get(i));
            origenFacade.edit(od);
            if (i == 0){
                for(RnGcCfdisLineasTbl prod : listaprod1){
                    produtoDestino(destinos.get(i),prod.getProductoId());
                }
            }
            if (i == 1 && (!listaprod2.isEmpty() && listaprod2 != null)){
                for(RnGcCfdisLineasTbl prod : listaprod2){
                    produtoDestino(destinos.get(i),prod.getProductoId());
                }
            }
            if (i == 2 && (!listaprod3.isEmpty() && listaprod3 != null)){
                for(RnGcCfdisLineasTbl prod : listaprod3){
                    produtoDestino(destinos.get(i),prod.getProductoId());
                }
            }
        }
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
                                    if(cfdisId.getEstatus().equals("Guardado")){
                                        System.out.println("Entro a Guardado");
                                        cfdisFacade.edit(cfdisId);
                                        System.out.println("El cfdi id en Guardado es: "+cfdisId.getId());
                                    }else{
                                        System.out.println("Entro a refresh");
                                        cfdisId = cfdisFacade.refreshFromDB(cfdisId);
                                        System.out.println("El cfdi id en refresh es: "+cfdisId.getId());
                                    }
                                    if ((cfdisId.getTipoComprobante().equals("T") || cfdisId.getTipoComprobante().equals("I")) && confirmacion) {
                                        System.out.println("Entro a carta porte");
                                        crearCartaPorte();
                                    }
                                    System.out.println("cfdisId - Archivo");
                                    archivo.setCfdiId(cfdisId);
                                    System.out.println("El cfdi id despues de la operacion es: "+archivo.getCfdiId());
                                    System.out.println("El archivo id antes de la operacion es: "+archivo.getId());
                                    System.out.println("-------------- // Entro a insertar el archivo // ------------");
                                    archivo = archivosFacade.refreshFromDB(archivo);
                                    System.out.println("-------------- // Fin de insertar el archivo // ------------");
                                    System.out.println("-------------- // El id del archivo es:  // ------------    "+archivo.getId());
                                    
                                    System.out.println("-------------- // Entro a insertar el los datos del complemento escolar // ------------");
                                    complementoE.setCfdiId(cfdisId);
                                    complementoE.setAutRVOE(autRVOE);
                                    complementoE.setCurp(curp);
                                    complementoE.setNivelEducativo(nivelEducativo);
                                    complementoE.setNombreAlumno(nombreAlumno);
                                    complementoE.setRfcPago(rfcPago);
                                    complementoE.setComplementoEscuela(true);
                                    complementoE.setComplementoSeleccionado(complementoSeleccionado);
                                    complementoE = ComplementosFacade.refreshFromDB(complementoE);
                                    System.out.println("-------------- // Fin de insertar el complemento // ------------");
                                    System.out.println("-------------- // El id del complemento es:  // ------------    "+complementoE.getPkId());
                                    
                                    
                                    System.out.println("Documentos Relacionados");
                                    if (listaCfdisRelacionados != null && listaCfdisRelacionados.size() > 0) {
                                        System.out.println("Entro a Documentos Relacionados");
                                        for (int i = 0; i < listaCfdisRelacionados.size(); i++) {
                                            RnGcDocumentosRelacionadosTbl docRelacionados = new RnGcDocumentosRelacionadosTbl();
                                            docRelacionados.setIdDocumento(listaCfdisRelacionados.get(i).getUuid());
                                            docRelacionados.setSerie(listaCfdisRelacionados.get(i).getSerie());
                                            docRelacionados.setFolio(String.valueOf(listaCfdisRelacionados.get(i).getFolio()));
                                            docRelacionados.setMoneda(listaCfdisRelacionados.get(i).getMoneda());
                                            docRelacionados.setMetodoPago(listaCfdisRelacionados.get(i).getMetodoPago());
                                            docRelacionados.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
                                            docRelacionados.setFechaCreacion(new Date());
                                            docRelacionados.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                                            docRelacionados.setUltimaFechaActualizacion(new Date());
                                            docRelacionados.setCfdisId(cfdisId);
                                            docRelacionados = docsRelacionadosFacade.refreshFromDB(docRelacionados);
                                        }
                                    }
                                    
                                    if (crearFirmas()) {
                                        firmas.setCfdiId(cfdisId);
                                        firmasFacade.edit(firmas);
                                        System.out.println("Firmas");
                                    }
                                    System.out.println("Pagos Relacionados1");
                                    if (listaCfdis != null && !listaCfdis.isEmpty()) {
                                        for (RnGcCfdisTbl cfdi : listaCfdis) {
                                            RnGcPagosRelacionadosTbl pagoRelacionadoLocal = new RnGcPagosRelacionadosTbl();
                                            System.out.println("Pagos Relacionados for cfdi");
                                            pagoRelacionadoLocal.setImporteSaldoAnterior(cfdi.getSaldoInsoluto() + cfdi.getCantidadPagada());
                                            cfdi.setSaldoPagado(cfdi.getImporte() - cfdi.getSaldoInsoluto());
                                            cfdi.setSaldoPagar(0.0);
                                            cfdi.setCantidadPagada(0.0);
                                            cfdisFacade.edit(cfdi);
                                            System.out.println("Pagos Relacionados for");
                                            pagoRelacionadoLocal.setIdDocumento(cfdi.getUuid());
                                            if (cfdi.getSerie() != null && !cfdi.getSerie().isEmpty()) {
                                                pagoRelacionadoLocal.setSerie(cfdi.getSerie());
                                            }
                                            if (cfdi.getFolio() != null) {
                                                pagoRelacionadoLocal.setFolio(cfdi.getFolio().toString());
                                            }
                                            pagoRelacionadoLocal.setMoneda(cfdi.getMoneda());
                                            if (cfdi.getTipoCambioP() != null && !cfdi.getTipoCambioP().isEmpty()) {
                                                pagoRelacionadoLocal.setTipoCambio(Double.parseDouble(cfdi.getTipoCambioP()));
                                            }
                                            pagoRelacionadoLocal.setMetodoPago(cfdi.getMetodoPago());
                                            pagoRelacionadoLocal.setNumeroParcialidad(cfdi.getNumeroParcialidad());
                                            pagoRelacionadoLocal.setImportePagado(cfdi.getSaldoPagado());
                                            pagoRelacionadoLocal.setImporteInsoluto(cfdi.getSaldoInsoluto());
                                            pagoRelacionadoLocal.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
                                            pagoRelacionadoLocal.setFechaCreacion(new Date());
                                            pagoRelacionadoLocal.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                                            pagoRelacionadoLocal.setUltimaFechaActualizacion(new Date());
                                            pagoRelacionadoLocal.setCfdisId(cfdisId);
                                            pagorelacionadoFacade.edit(pagoRelacionadoLocal);
                                            System.out.println("Pagos Relacionados for2");
                                        }
                                    }
                                    System.out.println("Pagos Relacionados2");
                                    saldoPagar = 0.0;
                                    parcialidad = 0;
                                    saldoIinsoluto = 0.0;
                                    System.out.println("Folio");
                                    if (cfdisId.getSerie() != null && cfdisId.getFolio() != null) {
                                        folio = obtenerFolioPorUsuarioSerieCert(cfdisId.getSerie(),cfdisId.getCertificados_Id()).get(0);
                                        folio.setFolio(folio.getFolio() + 1);
                                        folio.setUltimaFechaActualizacion(new Date());
                                        folioSerieFacade.edit(folio);
                                        itemsFolio.clear();
                                    }
                                    System.out.println("Lineas");
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
                                        System.out.println("-------------- // Entro a editar linea // ------------ID: " +cfdisLineas.get(i).getId()+ " || fechaCreacion: "+cfdisLineas.get(i).getFechaCreacion()+ " || UltimafechaActualizacion:"+cfdisLineas.get(i).getUltimaFechaActualizacion() );
                                        cfdisLineas.get(i).setFechaCreacion(new Date());
                                        cfdisLineas.get(i).setUltimaFechaActualizacion(new Date());
                                        lineasCfdisFacade.edit(cfdisLineas.get(i));     
                                    }
                                    System.out.println("Lineas2");
                                    timbres.get(0).setTimbresUsados(timbres.get(0).getTimbresUsados() + 1);
                                    timbres.get(0).setTimbresRestantes(timbres.get(0).getTimbresRestantes() - 1);
                                    timbresFacade.edit(timbres.get(0));
                                    System.out.println("timbres");
                                    enviarCorreo(archivo);
                                    producServicio = new RnGcProductserviciosTbl();
                                    lineas = new RnGcCfdisLineasTbl();
                                    personas = new RnGcPersonasTbl();
                                    firmas = new RnGcFirmasTbl();
                                    cfdisLineas = new ArrayList<>();
                                    itemsFactura = new ArrayList<>();
                                    cfdisId = new RnGcCfdisTbl();
                                    listaCfdisRelacionados = new ArrayList<>();
                                    listaCfdis = new ArrayList<>();
                                    listaImpuestosCfdis = new ArrayList<>();
                                    listaImpuestosCfdisXLinea = new ArrayList<>();
                                    timbres = new ArrayList<>();
                                    desconfirmarCartaPorte();
                                    System.out.println("Inicializar");
                                    JsfUtil.addSuccessMessage("Timbrado correctamente");
                                } catch (Exception ex) {
                                    ex.printStackTrace();
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
                                    desconfirmarCartaPorte();
                                    JsfUtil.addErrorMessage("Error al intentar el timbrado1");
                                }
                            } else {
                                try {
                                    JsfUtil.addErrorMessage("Error al intentar el timbrado2");
                                    System.out.println(cfdisId.getRespuestaTimbrado());
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
                                    desconfirmarCartaPorte();
                                } catch (Exception ex) {
                                    JsfUtil.addErrorMessage("Error al intentar el timbrado de la factura3");
                                    producServicio = new RnGcProductserviciosTbl();
                                    lineas = new RnGcCfdisLineasTbl();
                                    personas = new RnGcPersonasTbl();
                                    firmas = new RnGcFirmasTbl();
                                    cfdisLineas = new ArrayList<>();
                                    itemsFactura = new ArrayList<>();
                                    cfdisId = new RnGcCfdisTbl();
                                    listaCfdisRelacionados = new ArrayList<>();
                                    listaCfdis = new ArrayList<>();
                                    listaImpuestosCfdis = new ArrayList<>();
                                    listaImpuestosCfdisXLinea = new ArrayList<>();
                                    timbres = new ArrayList<>();
                                    desconfirmarCartaPorte();
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
                            timbres = new ArrayList<>();
                            desconfirmarCartaPorte();
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
                        timbres = new ArrayList<>();
                        desconfirmarCartaPorte();
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
        archivo = new RnGcArchivosTbl();
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
                if (!cfdisId.getTipoComprobante().equals("P")) {
                    itemsFactura = cfdisLineas;
                }
            }
        }
        if (cfdisId.getTipoComprobante() != null) {
            if (cfdisId.getTipoComprobante().equals("P")) {
                this.itemsFactura = new ArrayList<>(1);
                this.cfdisLineas = new ArrayList<>(1);
                lineas.setId((int) (Math.random() * 999999999));
                lineas.setClaveProdServ("84111506");
                lineas.setCantidad(1.0);
                lineas.setClaveUnidad("ACT");
                lineas.setDescripcion("Pago");
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
        //setearLineas();
        /*if (!obtenerProductos(lineas)) {
            producServicioFacade.edit(producServicio);
        }*/
        System.out.println("lineas: " + lineas.getId() + " | " + lineas);
        cfdisLineas.add(lineas);
        lineas = new RnGcCfdisLineasTbl();
        selectedLinea = null;
        producServicio2 = null;
        producServicio = new RnGcProductserviciosTbl();
        return cfdisLineas;
    }
    
    public boolean validarCliente(){
        boolean var = true;
        if(personas != null && personas.getNombre() != null){
            System.out.println("nombre : " + personas.getNombre());
            System.out.println("NOMBRE : " + personas.getNombre().toUpperCase());
            if(personas.getNombre().equals(personas.getNombre().toUpperCase()))
                var = false; System.out.println("El nombre es igual");
        }
        return var;
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
        listaUsuarios = new ArrayList<>();
        listaUsuarios = usuariosFacade.obtenerListaUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        return listaUsuarios.get(0).getRfc();
    }

    public String obtenerNombreCompletoUsuario() {
        listaUsuarios = new ArrayList<>();
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

    public void ImporteImpuesto(String porcent, Double cantidad, String valorUnit, Double descuento) {
        selectedLinea.setImporteimpuesto(Double.parseDouble(calcularImporteImpuesto(porcent, cantidad, valorUnit, descuento)));
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
    
    public String trasladadoExento() {
        Double trasladosIva = 0.0;
        if (cfdisLineas != null) {
            for (int i = 0; i < cfdisLineas.size(); i++) {
                if (cfdisLineas.get(i).getImpuesto() != null && cfdisLineas.get(i).getImpuesto().equals("002") && cfdisLineas.get(i).getTipoImpuesto().equals("Traslado") && cfdisLineas.get(i).getTipoFactor().equals("Exento")) {
                    trasladosIva += cfdisLineas.get(i).getBase();
                }
                if (cfdisLineas.get(i).getImpuesto2() != null && cfdisLineas.get(i).getImpuesto2().equals("002") && cfdisLineas.get(i).getTipoImpuesto2().equals("Traslado") && cfdisLineas.get(i).getTipoFactor2().equals("Exento")) {
                    trasladosIva += cfdisLineas.get(i).getBase();
                }
                if (cfdisLineas.get(i).getImpuesto3() != null && cfdisLineas.get(i).getImpuesto3().equals("002") && cfdisLineas.get(i).getTipoImpuesto3().equals("Traslado") && cfdisLineas.get(i).getTipoFactor3().equals("Exento")) {
                    trasladosIva += cfdisLineas.get(i).getBase();
                }
                if (cfdisLineas.get(i).getImpuesto4() != null && cfdisLineas.get(i).getImpuesto4().equals("002") && cfdisLineas.get(i).getTipoImpuesto4().equals("Traslado") && cfdisLineas.get(i).getTipoFactor4().equals("Exento")) {
                    trasladosIva += cfdisLineas.get(i).getBase();
                }
            }
        }
        return new DecimalFormat("0.00").format(trasladosIva);
    }

    public String trasladadoIva() {
        Double trasladosIva = 0.0;
        if (cfdisLineas != null) {
            for (int i = 0; i < cfdisLineas.size(); i++) {
                if (cfdisLineas.get(i).getImpuesto() != null && cfdisLineas.get(i).getImpuesto().equals("002") && cfdisLineas.get(i).getTipoImpuesto().equals("Traslado") && !cfdisLineas.get(i).getTipoFactor().equals("Exento")) {
                    trasladosIva += cfdisLineas.get(i).getImporteimpuesto();
                }
                if (cfdisLineas.get(i).getImpuesto2() != null && cfdisLineas.get(i).getImpuesto2().equals("002") && cfdisLineas.get(i).getTipoImpuesto2().equals("Traslado") && !cfdisLineas.get(i).getTipoFactor2().equals("Exento")) {
                    trasladosIva += cfdisLineas.get(i).getImporteImpuesto2();
                }
                if (cfdisLineas.get(i).getImpuesto3() != null && cfdisLineas.get(i).getImpuesto3().equals("002") && cfdisLineas.get(i).getTipoImpuesto3().equals("Traslado") && !cfdisLineas.get(i).getTipoFactor3().equals("Exento")) {
                    trasladosIva += cfdisLineas.get(i).getImporteImpuesto3();
                }
                if (cfdisLineas.get(i).getImpuesto4() != null && cfdisLineas.get(i).getImpuesto4().equals("002") && cfdisLineas.get(i).getTipoImpuesto4().equals("Traslado") && !cfdisLineas.get(i).getTipoFactor4().equals("Exento")) {
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
        cfdisId.setSaldoPagado(0.0);
        cfdisId.setSaldoPagar(0.0);
        System.out.println("Total: " + total);
        return total;
    }

    public String importeLetra() {
        NumeroALetra numLetra = new NumeroALetra();
        String importeLetra = String.valueOf(new DecimalFormat("0.00").format(calcularTotal()));
        String letra = numLetra.Convertir(importeLetra, true);
        String firstLtr = letra.substring(0, 1);
        String secondLtrs = letra.substring(1, letra.length() - 5);
        String restLtrs = letra.substring(letra.length() - 5, letra.length());
        firstLtr = firstLtr.toUpperCase();
        secondLtrs = secondLtrs.toLowerCase();
        String res = firstLtr + secondLtrs + restLtrs;
        cfdisId.setImporteLetra(res);
        return res;
    }

    public boolean crearXML() throws Exception {
        System.out.println("crearXML");
        String cadOrig = "";
        boolean valor = false;
        try {
            System.out.println("Probando");
            File xslt = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/cadenaoriginal_4_0.xslt"));            //Inicio calcula cadena original
            StreamSource sourceXSL = new StreamSource(xslt);
            System.out.println("Probando2");
            /*if(complementoEscuela){
            valor = crearXmlEscuela();
            }SE COMENTA PARA OTRA NUEVA VERSION*/ 
            if (cfdisId.getTipoComprobante().equals("I") || cfdisId.getTipoComprobante().equals("E")) {
                valor = crearXmlIngEgre();
            }
            if (cfdisId.getTipoComprobante().equals("P")) {
                valor = crearXmlPago();
            }
            if (cfdisId.getTipoComprobante().equals("T") && confirmacion) {
                valor = crearXMLCartaPorte();
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
        String xml1 = xml.replace("FILTRO ACEITE", "Filtro Aceite");
        System.out.println("cfdisId: " + cfdisId.getCertificados_Id().getLlavePrivada() + " ! " + cfdisId.getCertificados_Id().getContraseniaLlavePrivada().toCharArray());
        PKCS8Key pkcs8 = new PKCS8Key(cfdisId.getCertificados_Id().getLlavePrivada(), cfdisId.getCertificados_Id().getContraseniaLlavePrivada().toCharArray());
        KeyFactory privateKeyFact = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec pkcs8Encoded = new PKCS8EncodedKeySpec(pkcs8.getDecryptedBytes());
        PrivateKey privateKey = privateKeyFact.generatePrivate(pkcs8Encoded);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        byte[] cadenaOriginalArray = xml1.getBytes();
        signature.update(cadenaOriginalArray);
        String firma = new String(Base64.getEncoder().encode(signature.sign()));
        System.out.println("firma: " + firma);
        return firma;
    }

    public boolean modificarXml(String xml, File xmlAc) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = docFactory.newDocumentBuilder();
        Document doc = builder.parse(xmlAc);
        leerCfdi(xmlAc);
        boolean valorModifica = false;
        File tempFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/xmlTemp.xml"));
        NodeList items = doc.getElementsByTagName("cfdi:Comprobante");
        for (int i = 0; i < items.getLength(); i++) {
            Element element = (Element) items.item(i);
            //if (element.getAttribute("Sello").isEmpty()) {
            element.setAttribute("Sello", crearSello(xml));
            //}
            //if (element.getAttribute("Certificado").isEmpty()) {
            element.setAttribute("Certificado", crearCertificado());
            //}
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
           Sefactura sf = new Sefactura("http://pruebas.sefactura.com.mx:3014", "VICA840114RZ41", "VICA840114RZ41"); //Desarrollo
           //Sefactura sf = new Sefactura("http://www.jonima.com.mx:3014", "VICA840114RZ41", "VICA840114RZ41"); //Desarrollo Emmanuel
           //Sefactura sf = new Sefactura("https://www.sefactura.com.mx", "AFC060520V16", "AFC060520V16"); //Produccion
           System.out.println("resultadoEmma: " + sf.toString()); 
            RespuestaTimbrado rt = sf.timbrado(xml);
            System.out.println("xmlTimbrado: " + rt.getXml());
            System.out.println("resultadoEmma2: " + rt.getResultado() + " || " + rt.getResultado().length());
            System.out.println("resultado: " + rt.getResultado());
            if (rt.getResultado() != null && rt.getResultado().length() > 0) {
                System.out.println("Error al generar timbrado: " + rt.getResultado());
                JsfUtil.addErrorMessage("Error " + rt.getResultado());
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
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();//obtener datos de XML Timbrado para  PDF
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
                cfdisId.setEstatus("Timbrado");
                cfdisId.setRespuestaTimbrado("Timbrado de forma correcta");
                System.out.println(noCertSAT + " | " + fechaTimbrado + " | " + Uuid + " | " + selloCFDI + " | " + selloSAT + " | " + rfcProvCertif);
                if (cfdisId.getTipoComprobante().equals("E") || cfdisId.getTipoComprobante().equals("I")) {
                    crearPDF(selloSAT, noCertSAT, fechaTimbrado, Uuid, selloCFDI, codQR, cadOriginal, rfcProvCertif);
                } else {
                    crearPDFPago(selloSAT, noCertSAT, fechaTimbrado, Uuid, selloCFDI, codQR, cadOriginal, rfcProvCertif);
                }
                System.out.println("ProbandoT6");
                //crearArchivo(xmltimbrado);
                valorTimbra = true;
                System.out.println("Ya termine ");
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getLocalizedMessage());
            e.printStackTrace();
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

    public void vistaPreviaPDF() throws Exception {
        if (cfdisId.getTipoComprobante().equals("I") || cfdisId.getTipoComprobante().equals("E")) {
            System.out.println("IngEgre: " + cfdisId.getTipoComprobante());
            verPDF();
        }
    }

    public void vistaPreviaComplemento() {
        System.out.println("Pago: " + cfdisId.getTipoComprobante());
        verPdfPago();
    }

    public void verPdfPago() {
        try {
            System.out.println("ENTRO A VISUALIZAR LA FACURA PREVIA Pago");
            RnGcUsuariosTbl usuario = new RnGcUsuariosTbl();
            usuario = usuariosFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            cfdisId.setNombreEmisor(usuario.getNombreCompleto());
            cfdisId.setRfcEmisor(usuario.getRfc());
            System.out.println("Usuario: " + usuario);
            System.out.println("RFCEmisor: " + cfdisId.getRfcEmisor());
            System.out.println("UsoCFDI: " + cfdisId.getUsoCfdi());
            System.out.println("RegimenFiscal: " + cfdisId.getClaveRegimenFiscal());
            FileOutputStream fos = new FileOutputStream(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/responsivo.png")));
            fos.write(obtenerImagen());
            fos.close();

            String imagenLogo = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getRealPath("/resources/images/responsivo.png");
            String imagenqr = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getRealPath("/resources/images/qr.png");
            //Crea el Map para setear los valores de la factura
            Map<String, Object> parametros = new HashMap<String, Object>();
            //parametros.put("logo", imagenLogo);
            parametros.put("RFC_Emisor", cfdisId.getRfcEmisor());
            parametros.put("Nombre_Emisor", cfdisId.getNombreEmisor());
            parametros.put("RFC_Receptor", personas.getRfc());
            parametros.put("Nombre_Receptor", personas.getNombre());
            parametros.put("Uso_CFDI", cfdisId.getUsoCfdi());
            parametros.put("Folio_Fiscal", cfdisId.getFolio());
            parametros.put("NoSerie_CSD", cfdisId.getSerie());
            parametros.put("CodigoPostal", cfdisId.getLugarExpedicion());
            parametros.put("FechaHora_Emision", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(cfdisId.getFechaExpedicion()));
            parametros.put("EfectoComprobante", "P - Pago");
            System.out.println("Parametros: " + parametros);
            parametros.put("QR", imagenqr);
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
            if (cfdisId.getUsoCfdi() != null) {
                switch (cfdisId.getUsoCfdi()) {
                    case "P01":
                        parametros.put("Uso_CFDI", "P01 - Por definir");
                        break;
                    case "S01":
                        parametros.put("Uso_CFDI", "Sin efectos fiscales");
                        break;
                    case "CP01":
                        parametros.put("Uso_CFDI", "Pagos");
                        break;
                    default:
                        parametros.put("Uso_CFDI", "-");
                        break;
                }
            }
            List<RnGcRegimenfiscalTbl> listaRegimen = regimenFacade.findAll();
            if (cfdisId.getClaveRegimenFiscal() != null) {
                for(RnGcRegimenfiscalTbl reg : listaRegimen){
                    if(cfdisId.getClaveRegimenFiscal().equals(String.valueOf(reg.getClaveRegimenFiscal())))
                        parametros.put("RegimenFiscal", reg.getClaveRegimenFiscal() + " - " + reg.getDescripcion());
                }
            }
            if (cfdisId.getTexto()!= null && !cfdisId.getTexto().isEmpty()) {
                parametros.put("texto", cfdisId.getTexto());
            }
            parametros.put("RegimenFiscalReceptor", personas.getRegimenFiscalId().getClaveRegimenFiscal() + " - " + personas.getRegimenFiscalId().getDescripcion());
            parametros.put("CodigoPostalReceptor", ""+personas.getcodigoPostal());
            System.out.println("ListaCfdis: " + listaCfdis);
            parametros.put("fechaPago", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(cfdisId.getFechaPago()));
            parametros.put("moneda", cfdisId.getMonedaP());
            parametros.put("monto", cfdisId.getMontoPago());
            parametros.put("Logo", imagenLogo);
            parametros.put("listaCfdis", listaCfdis);
            parametros.put("FormaPago", cfdisId.getFormaPagoP());
            parametros.put("RFCEmisorCuentaOrdenante", cfdisId.getRfcEmisorCuentaOrigen());
            parametros.put("NombreBancoCuentaOrdenante", cfdisId.getNombreBanco());
            parametros.put("NumeroCuentaOrdenante", cfdisId.getCuentaOrdenante());
            parametros.put("RFCEmisorCuentaBeneficiario", cfdisId.getRfcEmisorCuentaBeneficiaria());
            parametros.put("NumeroCuentaBeneficiario", cfdisId.getCuentaBeneficiaria());

            if (cfdisId.getMonedaP().equals("MXN") || cfdisId.getMonedaP().equals("XXX")) {
                parametros.put("tipoCambio", "1.0");
            } else {
                parametros.put("tipoCambio", cfdisId.getTipoCambioP());
            }

            parametros.put("importeLetra", cfdisId.getImporteLetra());
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Reports/complementoPago.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JREmptyDataSource());

            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);
            InputStream streamPdf = new ByteArrayInputStream(pdf);

            //downLoadFile = new DefaultStreamedContent(streamPdf, "document/pdf", "Factura_" + new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss").format(cfdisId.getFechaExpedicion()) + ".pdf");
            downLoadFile = new DefaultStreamedContent(streamPdf, "document/pdf",cfdisId.getSerie() + "-" + cfdisId.getFolio() + "-" + cfdisId.getNombreReceptor() + ".pdf");

        } catch (Exception ex) {
            System.out.println("Ocurrio un error en la descarga del archivo PDF");
        }
    }

    public void verPDF() throws Exception {
        try {
            System.out.println("ENTRO A VISUALIZAR LA FACURA PREVIA IngreEgre");
            System.out.println("RFCEmisor: " + cfdisId.getRfcEmisor());
            System.out.println("UsoCFDI: " + cfdisId.getUsoCfdi());
            System.out.println("RegimenFiscal: " + cfdisId.getClaveRegimenFiscal());
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
            parametros.put("RFC_Receptor", personas.getRfc());
            parametros.put("Nombre_Receptor", personas.getNombre());
            parametros.put("Uso_CFDI", cfdisId.getUsoCfdi());
            parametros.put("Folio_Fiscal", cfdisId.getFolio());
            parametros.put("NoSerie_CSD", cfdisId.getSerie());
            parametros.put("CodigoPostal", cfdisId.getLugarExpedicion());
            parametros.put("FechaHora_Emision", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(cfdisId.getFechaExpedicion()));
            parametros.put("QR", imagenqr);
            parametros.put("Logo", imagenLogo);

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
            parametros.put("RegimenFiscalReceptor", personas.getRegimenFiscalId().getClaveRegimenFiscal() + " - " + personas.getRegimenFiscalId().getDescripcion());
            parametros.put("CodigoPostalReceptor", ""+personas.getcodigoPostal());
            System.out.println("ListaConceptossss: " + cfdisLineas);
            parametros.put("conceptos", cfdisLineas);
            parametros.put("CFDIsRelacionados", listaCfdisRelacionados);
            parametros.put("Subtotal", Double.parseDouble(calcularSubtotal()));
            parametros.put("Total", calcularTotal());
            parametros.put("ImpuestosRetenidos", Double.parseDouble(new DecimalFormat("0.00").format(Double.parseDouble(retenidosIEPS()) + Double.parseDouble(retenidosISR()) + Double.parseDouble(retenidosIva()) + Double.parseDouble(retenidosLocales()))));
            parametros.put("ImpuestosTrasladados", Double.parseDouble(new DecimalFormat("0.00").format(Double.parseDouble(trasladadoIEPS()) + Double.parseDouble(trasladadoISR()) + Double.parseDouble(trasladadoIva()) + Double.parseDouble(trasladosLocales()))));
            parametros.put("totalDescuento", Double.parseDouble(new DecimalFormat("0.00").format(Double.parseDouble(calcularDescuento()))));

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

            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);
            InputStream streamPdf = new ByteArrayInputStream(pdf);

            downLoadFile4 = new DefaultStreamedContent(streamPdf, "document/pdf","CFDI-VistaPrevia-" +cfdisId.getSerie() + "-" + cfdisId.getFolio() + "-" + personas.getNombre()+ ".pdf");
            //downLoadFile4 = new DefaultStreamedContent(streamPdf, "document/pdf", "Factura_VistaPrevia_" + new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss").format(new Date()) + ".pdf");
        } catch (Exception ex) {
            System.out.println("Ocurrio un error al descargar la vista previa");
            JsfUtil.addErrorMessage("Ocurrio un error al descargar la vista previa");
        }
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
        parametros.put("FechaHora_Emision", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(cfdisId.getFechaExpedicion()));
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
        if (cfdisId.getMoneda().equals("MXN") || cfdisId.getMoneda().equals("XXX")) {
            parametros.put("tipoCambio", 1);
        } else {
            parametros.put("tipoCambio", cfdisId.getTipoCambio());
        }
        parametros.put("RegimenFiscalReceptor", personas.getRegimenFiscalId().getClaveRegimenFiscal() + " - " + personas.getRegimenFiscalId().getDescripcion());
        parametros.put("CodigoPostalReceptor", ""+personas.getcodigoPostal());
        parametros.put("Moneda", cfdisId.getMoneda());
        List<RnGcCfdisLineasTbl> lista = cfdisLineas;
        for(int i = 0; i < lista.size(); i++){
            lista.get(i).setNoCuentaPredial(lista.get(i).getProductoId().getObjetoImpId().getClave() 
                    + "-" + lista.get(i).getProductoId().getObjetoImpId().getDescripcion());
        }
        parametros.put("conceptos", lista);
        for(int i = 0; i < lista.size(); i++){
            lista.get(i).setNoCuentaPredial(null);
        }
        parametros.put("CFDIsRelacionados", listaCfdisRelacionados);
        if ((listaCfdisRelacionados != null && !listaCfdisRelacionados.isEmpty()) && listaCfdisRelacionados.get(0).getUuid() != null){
            parametros.put("uuidRel", listaCfdisRelacionados.get(0).getUuid());
            List<RnGcTiporelacionTbl> listaRel = tipoRelFacade.findAll();
            for(RnGcTiporelacionTbl relac : listaRel){
                if(cfdisId.getTipoRelacion() != null && relac.getClaveTipoRelacion() != null)
                    if(cfdisId.getTipoRelacion().equals(relac.getClaveTipoRelacion()))
                        parametros.put("motivo", relac.getClaveTipoRelacion() + " - " + relac.getDescripcion());
            }
        }
        parametros.put("Subtotal", Double.parseDouble(calcularSubtotal()));
        parametros.put("Total", calcularTotal());
        parametros.put("ImpuestosRetenidos", Double.parseDouble(new DecimalFormat("0.00").format(Double.parseDouble(retenidosIEPS()) + Double.parseDouble(retenidosISR()) + Double.parseDouble(retenidosIva()) + Double.parseDouble(retenidosLocales()))));
        parametros.put("ImpuestosTrasladados", Double.parseDouble(new DecimalFormat("0.00").format(Double.parseDouble(trasladadoIEPS()) + Double.parseDouble(trasladadoISR()) + Double.parseDouble(trasladadoIva()) + Double.parseDouble(trasladosLocales()))));
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
                    parametros.put("FormaPago", "03 - Transferencia electrónica de fondos");
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
        if (cfdisId.getTexto()!= null && !cfdisId.getTexto().isEmpty()) {
            parametros.put("texto", cfdisId.getTexto());
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
        byte[] facturaPDF = JasperExportManager.exportReportToPdf(jasperPrint);
        archivo.setArchivoPdf(facturaPDF);
        System.out.println("Archivo id "+archivo.getId()+ " || archivo cfdi_id: "+archivo.getCfdiId());
        //Imprime PDF
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zout = new ZipOutputStream(bos);

        ZipEntry zip = new ZipEntry("CFDI-" + cfdisId.getSerie() + "-" + cfdisId.getFolio() + "-" + cfdisId.getNombreReceptor() + ".pdf");
        zout.putNextEntry(zip);
        zout.write(facturaPDF);
        zout.closeEntry();

        //ZipEntry zip2 = new ZipEntry("Factura_" + cfdisId.getRfcEmisor() + new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss").format(cfdisId.getFechaExpedicion()) + ".xml");
        ZipEntry zip2 = new ZipEntry("CFDI-" + cfdisId.getSerie() + "-" + cfdisId.getFolio() + "-" + cfdisId.getNombreReceptor() + ".xml");
        byte[] xml = archivo.getArchivoXml();
        zout.putNextEntry(zip2);
        zout.write(xml);
        zout.closeEntry();

        zout.finish();
        zout.flush();
        zout.close();

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        InputStream streamPdf = bis;
        downLoadFile3 = new DefaultStreamedContent(streamPdf, "application/zip", "CFDI-" + cfdisId.getSerie() + "-" + cfdisId.getFolio() + "-" + cfdisId.getNombreReceptor() + ".zip");
        System.out.println("PDF guardado");
    }

    public void crearPDFPago(String selloSAT, String noCertSAT, String fechaTimbrado, String Uuid, String selloCFDI, byte[] codigoQR, String cadenaOrig, String rfcProvCertif) throws IOException, JRException, ParseException {
        System.out.println("ENTRO A VISUALIZAR LA FACURA PREVIA");
        System.out.println("RFCEmisor: " + cfdisId.getRfcEmisor());
        System.out.println("UsoCFDI: " + cfdisId.getUsoCfdi());
        System.out.println("RegimenFiscal: " + cfdisId.getClaveRegimenFiscal());
        FileOutputStream fos = new FileOutputStream(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/qr.png")));
        fos.write(codigoQR);
        fos.close();
        fos = new FileOutputStream(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/responsivo.png")));
        fos.write(obtenerImagen());
        fos.close();

        String imagenLogo = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRealPath("/resources/images/responsivo.png");
        String imagenqr = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRealPath("/resources/images/qr.png");
        //Crea el Map para setear los valores de la factura
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("RFC_Emisor", cfdisId.getRfcEmisor());
        parametros.put("Nombre_Emisor", cfdisId.getNombreEmisor());
        parametros.put("RFC_Receptor", personas.getRfc());
        parametros.put("Nombre_Receptor", personas.getNombre());
        parametros.put("Uso_CFDI", cfdisId.getUsoCfdi());
        parametros.put("Folio_Fiscal", cfdisId.getFolio());
        parametros.put("NoSerie_CSD", cfdisId.getSerie());
        parametros.put("CodigoPostal", cfdisId.getLugarExpedicion());
        parametros.put("FechaHora_Emision", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(cfdisId.getFechaExpedicion()));
        parametros.put("EfectoComprobante", "P - Pago");
        parametros.put("QR", imagenqr);
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
        parametros.put("RegimenFiscalReceptor", personas.getRegimenFiscalId().getClaveRegimenFiscal() + " - " + personas.getRegimenFiscalId().getDescripcion());
        parametros.put("CodigoPostalReceptor", ""+personas.getcodigoPostal());
        System.out.println("ListaCfdis: " + listaCfdis);
        parametros.put("fechaPago", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(cfdisId.getFechaPago()));
        parametros.put("moneda", cfdisId.getMonedaP());
        parametros.put("monto", cfdisId.getMontoPago());
        List<RnGcCfdisLineasTbl> lista = new ArrayList<>();
        String objetoImp = "";
        List<RnGcCfdisTbl> lista1 = listaCfdis;
        for(int i = 0; i < listaCfdis.size(); i++){
            lista = lineasCfdisFacade.obtenerCfdisLineas(listaCfdis.get(i));
            for(int j = 0; j < lista.size(); j++){
                if(!lista.get(j).getProductoId().getObjetoImpId().getClave().equals("01"))
                    objetoImp = lista.get(j).getProductoId().getObjetoImpId().getClave();
            }
        }
        if(!objetoImp.isEmpty()){
            parametros.put("baseImp", new DecimalFormat("0.00").format(cfdisId.getMontoPago() / 1.16));
            parametros.put("impuestoImp", "IVA");
            parametros.put("tipoFactorImp", "Tasa");
            parametros.put("tasaOcuotaImp", "0.160000");
            parametros.put("importeImp", new DecimalFormat("0.00").format((cfdisId.getMontoPago() / 1.16) * 0.16));
            for(int i = 0; i < lista1.size(); i++){
                lista1.get(i).setProveedorTimbres(objetoImp);
            }
        }
        parametros.put("Logo", imagenLogo);
        parametros.put("listaCfdis", lista1);
        parametros.put("FormaPago", cfdisId.getFormaPagoP());
        parametros.put("RFCEmisorCuentaOrdenante", cfdisId.getRfcEmisorCuentaOrigen());
        parametros.put("NombreBancoCuentaOrdenante", cfdisId.getNombreBanco());
        parametros.put("NumeroCuentaOrdenante", cfdisId.getCuentaOrdenante());
        parametros.put("RFCEmisorCuentaBeneficiario", cfdisId.getRfcEmisorCuentaBeneficiaria());
        parametros.put("NumeroCuentaBeneficiario", cfdisId.getCuentaBeneficiaria());

        if (cfdisId.getMonedaP().equals("MXN") || cfdisId.getMonedaP().equals("XXX")) {
            parametros.put("tipoCambio", "1.0");
        } else {
            parametros.put("tipoCambio", cfdisId.getTipoCambioP());
        }
        if (cfdisId.getTexto()!= null && !cfdisId.getTexto().isEmpty()) {
            parametros.put("texto", cfdisId.getTexto());
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
        parametros.put("rfcProvCertif", rfcProvCertif);

        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Reports/complementoPago.jasper"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JREmptyDataSource());
        byte[] facturaPDF = JasperExportManager.exportReportToPdf(jasperPrint);
        archivo.setArchivoPdf(facturaPDF);//*/

        //Imprime PDF
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zout = new ZipOutputStream(bos);

        //ZipEntry zip = new ZipEntry("ComplementoPago_" + cfdisId.getRfcEmisor() + "_" + new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss").format(cfdisId.getFechaExpedicion()) + ".pdf");
        ZipEntry zip = new ZipEntry("CFDI-" + cfdisId.getSerie() + "-" + cfdisId.getFolio() + "-" + cfdisId.getNombreReceptor() + ".pdf");
        zout.putNextEntry(zip);
        zout.write(facturaPDF);
        zout.closeEntry();

        ZipEntry zip2 = new ZipEntry("CFDI-" + cfdisId.getSerie() + "-" + cfdisId.getFolio() + "-" + cfdisId.getNombreReceptor() + ".xml");
        byte[] xml = archivo.getArchivoXml();
        zout.putNextEntry(zip2);
        zout.write(xml);
        zout.closeEntry();

        zout.finish();
        zout.flush();
        zout.close();

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        InputStream streamPdf = bis;
        downLoadFile2 = new DefaultStreamedContent(streamPdf, "application/zip", "CFDI-" + cfdisId.getSerie() + "-" + cfdisId.getFolio() + "-" + cfdisId.getNombreReceptor() + ".zip");
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
    
    public List<RnGcFolioserieTbl> obtenerFolioPorUsuarioSerieYCertificado(String serie, RnGcCertificadosTbl certificadoId) {
        System.out.println("Serie: " + serie);
        if (!serie.isEmpty()) {
            usuario = usuariosFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            itemsFolio = folioSerieFacade.folioPorUsuarioSerieYCertificado(usuario, serie, certificadoId);
        } else {
            itemsFolio = null;
        }
        return itemsFolio;
    }
    
    public List<RnGcFolioserieTbl> obtenerFolioPorUsuarioSerieCert(String serie, RnGcCertificadosTbl certificado) {
        System.out.println("Serie: " + serie);
        if (!serie.isEmpty()) {
            usuario = usuariosFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            itemsFolio = folioSerieFacade.folioPorUsuarioSerieCert(usuario, serie, certificado);
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
            personas = personas2; //RFC
        } else {
            if (personas3 != null) { //Nombre
                personas = personas3;
            }
        }
        if (!personas.getNombre().equals("-") && !personas.getRfc().equals("-")) {
            /*if (usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")) {
                JsfUtil.addSuccessMessage("El cliente " + personas.getRfc() + " se ha aactualizado");
                System.out.println("El cliente " + personas.getRfc() + " se ha aactualizado");
            } else {*/
                if (!obtenerClientes(personas.getRfc().toUpperCase())) {
                    //personas.setRfc(personas.getRfc().toUpperCase());
                    //personas.setNombre(iniMayusculas(personas.getNombre()));
                    personas.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
                    personas.setTipoPersona("Cliente");
                    personas.setFechaCreacion(new Date());
                    personas.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                    personas.setUltimaFechaActualizacion(new Date());
                    personas.setTipoPersonaId(tiposPersonasFacade.obtenerTipoPersona("Cliente"));
                    personas.setUsuarioId(usuariosFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario()));
                    //personas.setNoInt(personas.getNoInt());
                    //personas.setDomicilio(personas.getDomicilio());
                    personas = personasFacade.refreshFromDB(personas);
                    System.out.println("El cliente " + personas.getRfc() + " se ha guardado");
                    JsfUtil.addSuccessMessage("El cliente " + personas.getRfc() + " se ha guardado");
                } else {
                    System.out.println("El cliente " + personas.getRfc() + " se ha actualizado");
                    System.out.println("Direccion" + personas.getDomicilio());
                    JsfUtil.addSuccessMessage("El cliente " + personas.getRfc() + " se ha actualizado");
                    personasFacade.edit(personas);
                }
            //}
        } else {
            System.out.println("No se ha ingresado datos del receptor");
            JsfUtil.addSuccessMessage("No se ha ingresado datos del receptor");
        }
        cfdisId.setRfcEmisor(listaUsuarios.get(0).getRfc());
        cfdisId.setNombreEmisor(listaUsuarios.get(0).getNombreCompleto());
        cfdisId.setPersonaId(personas);
        System.out.println("El emisor es: " + listaUsuarios.get(0).getRfc());
        JsfUtil.addSuccessMessage("El emisor es: " + listaUsuarios.get(0).getRfc());
        personas2 = null;
        personas3 = null;
    }

    
    //// METODO AGREGADO POR EMMANUEL CM
    public void buscaCliente() {
        System.out.println("Entro a buscar Clientes nuevo metodo");
        if (personas2 != null) {
            personas = personas2; //RFC
        } else {
            if (personas3 != null) { //Nombre
                personas = personas3;
            }
        }
        if (!personas.getNombre().equals("-") && !personas.getRfc().equals("-")) {
                if (!obtenerClientes(personas.getRfc().toUpperCase())) {
                     System.out.println("Cliente encontrado");
                    JsfUtil.addSuccessMessage("Cliente encontrado");
                } else {
                    System.out.println("PERSONA ENCONTRADA" + personas.getNombre());
                    JsfUtil.addSuccessMessage("El cliente encontrado");
                    personasFacade.edit(personas);
                    
                    cfdisId.setRfcEmisor(listaUsuarios.get(0).getRfc());
                    cfdisId.setNombreEmisor(listaUsuarios.get(0).getNombreCompleto());
                    cfdisId.setPersonaId(personas);
                    System.out.println("El emisor es: " + listaUsuarios.get(0).getRfc());
                    JsfUtil.addSuccessMessage("El emisor es: " + listaUsuarios.get(0).getRfc());
                }
            //}
        } else {
            //System.out.println("No se ha ingresado datos del receptor");
            JsfUtil.addErrorMessage("No se ha ingresado datos del receptor");
        }
        
        personas2 = null;
        personas3 = null;
    }

    
        //// FINAL DEL METODO AGREGADO POR EMMANUEL CM
    
    
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

    public void guardarProdNuevo() {
        RnGcProductserviciosTbl prodSer = new RnGcProductserviciosTbl();
        prodSer.setClaveProductServ(producServicio.getClaveProductServ());
        prodSer.setClaveUnidad(producServicio.getClaveUnidad());
        prodSer.setDescripcion(producServicio.getDescripcion());
        prodSer.setFechaInicioVigencia(producServicio.getFechaInicioVigencia());
        prodSer.setImpuesto(producServicio.getImpuesto());
        prodSer.setNoIdentificacion(producServicio.getNoIdentificacion());
        List<RnGcObjetoimpuestoTbl> lista = objetoImpFacade.findAll();
        for(RnGcObjetoimpuestoTbl lis : lista){
            if(prodServicio.equals(lis.getDescripcion())){
                producServicio.setObjetoImpId(lis);
            }
        }
        prodSer.setObjetoImpId(producServicio.getObjetoImpId());
        prodSer.setPeligroso(producServicio.getPeligroso());
        prodSer.setPeso(producServicio.getPeso());
        prodSer.setTipofactor(producServicio.getTipofactor());
        prodSer.setTipoImpuesto(producServicio.getTipoImpuesto());
        prodSer.setTipoProdServ(producServicio.getTipoProdServ());
        prodSer.setTipoTasa(producServicio.getTipoTasa());
        prodSer.setUnidad(producServicio.getUnidad());
        prodSer.setValorunitario(lineas.getValorUnit());
        prodSer.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        prodSer.setFechaCreacion(new Date());
        prodSer.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        prodSer.setUltimaFechaActualizacion(new Date());
        prodSer = producServicioFacade.refreshFromDB(prodSer);
    }
    
    public void guardarProdEditado() {
        producServicio.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        producServicio.setUltimaFechaActualizacion(new Date());
        producServicio.setValorunitario(lineas.getValorUnit());
        List<RnGcObjetoimpuestoTbl> lista = objetoImpFacade.findAll();
        for(RnGcObjetoimpuestoTbl lis : lista){
            if(prodServicio.equals(lis.getDescripcion())){
                producServicio.setObjetoImpId(lis);
            }
        }
        
        producServicioFacade.edit(producServicio);
    }
    
    public void setearLineas(){
        lineas.setClaveProdServ(producServicio.getClaveProductServ());
            lineas.setNoIdentificacion(producServicio.getNoIdentificacion());
            lineas.setClaveUnidad(producServicio.getClaveUnidad());
            lineas.setUnidad(producServicio.getUnidad());
            lineas.setDescripcion(producServicio.getDescripcion());
            lineas.setTipoImpuesto(producServicio.getTipoImpuesto());
            lineas.setImpuesto(producServicio.getImpuesto());
            lineas.setTipoFactor(producServicio.getTipofactor());
            lineas.setTipoTasa(producServicio.getTipoTasa());
            lineas.setPesoUnitario(producServicio.getPeso());
            lineas.setProductoId(producServicio);
            lineas.setPeligroso(producServicio.getPeligroso());
            System.out.println("lineas.setProductoId: " + lineas.getProductoId() + " Peso: " + lineas.getPesoUnitario() + " Peligroso: " + lineas.getPeligroso());
            System.out.println(lineas.getClaveProdServ() + " | " + lineas.getNoIdentificacion() + " | " + lineas.getClaveUnidad()
                    + " | " + lineas.getUnidad() + " | " + lineas.getDescripcion() + " | " + lineas.getValorUnit()
                    + " | " + lineas.getTipoImpuesto() + " | " + lineas.getImpuesto() + " | " + lineas.getTipoFactor()
                    + " | " + lineas.getTipoTasa());
            if (producServicio.getTipoImpuesto2() != null) {
                lineas.setTipoImpuesto2(producServicio.getTipoImpuesto2());
                lineas.setImpuesto2(producServicio.getImpuesto2());
                lineas.setTipoFactor2(producServicio.getTipoFactor2());
                lineas.setTipoTasa2(producServicio.getTipoTasa2());
                System.out.println(lineas.getTipoImpuesto2() + " | " + lineas.getImpuesto2()
                        + " | " + lineas.getTipoFactor2() + " | " + lineas.getTipoTasa2());
            }
            if (producServicio.getTipoImpuesto3() != null) {
                lineas.setTipoImpuesto3(producServicio.getTipoImpuesto3());
                lineas.setImpuesto3(producServicio.getImpuesto3());
                lineas.setTipoFactor3(producServicio.getTipoFactor3());
                lineas.setTipoTasa3(producServicio.getTipoTasa3());
                System.out.println(lineas.getTipoImpuesto3() + " | " + lineas.getImpuesto3()
                        + " | " + lineas.getTipoFactor3() + " | " + lineas.getTipoTasa3());
            }
            if (producServicio.getTipoImpuesto4() != null) {
                lineas.setTipoImpuesto4(producServicio.getTipoImpuesto4());
                lineas.setImpuesto4(producServicio.getImpuesto4());
                lineas.setTipoFactor4(producServicio.getTipoFactor4());
                lineas.setTipoTasa4(producServicio.getTipoTasa4());
                System.out.println(lineas.getTipoImpuesto4() + " | " + lineas.getImpuesto4()
                        + " | " + lineas.getTipoFactor4() + " | " + lineas.getTipoTasa4());
            }
    }
    
    public void buscarProdServ() {
        if (producServicio2 != null) {
            if(producServicio2.getObjetoImpId() != null)
                prodServicio = producServicio2.getObjetoImpId().getDescripcion();
            producServicio = producServicio2;
            lineas.setValorUnit(producServicio.getValorunitario());
            setearLineas();
        }
        
    }

    public void enviarCorreo(RnGcArchivosTbl archivoId) {
        //final String username = "conta-arc@rocketnegocios.com.mx";
        //final String password = "ga1mJ7$4";
        final String username = usuario.getEmail();
        final String password = usuario.getPasswordEmail();
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.ionos.mx");
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
            if (personas.getEmail() != null && personas.getEmail().contains("@")) {
                List<String> correos = new ArrayList<>();
                correos.add(personas.getEmail());
                if (personas.getEmail2() != null && personas.getEmail2().contains("@") && personas.getEmail2() != "-") {
                    correos.add(personas.getEmail2());
                }
                InternetAddress[] destinatarios = new InternetAddress[correos.size()];
                for (int i = 0; i < correos.size(); i++) {
                    destinatarios[i] = new InternetAddress(correos.get(i));
                }
                System.out.println("Envia de " + username + " a " + destinatarios.toString());
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
                System.out.println("Archivos enviados al correo " + personas.getEmail());
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
        List<RnGcTimbresTbl> lista = timbresFacade.listaTimbresUsuario(usuario);
        listaTimbres = new ArrayList<>();
        for(RnGcTimbresTbl tim : lista){
            if(tim.getTimbresRestantes() != 0){
                listaTimbres.add(tim);
            }
        }
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

    //METODO CREADO POR EMMANUEL CONTIENE VALIDACIONES NUEVAS
    public void agregarComplementoPago() {
    System.out.println("listaPPDsSeleccionado: " + listaPPDsSeleccionado);

    for (RnGcCfdisTbl complemento : listaPPDsSeleccionado) {
        // Verificar si la factura ya está en la listaCfdis
        if (!listaCfdis.contains(complemento)) {
            // La factura no está en la lista, agregarla
            listaCfdis.add(complemento);
        } else {
            // La factura ya está en la lista, puedes mostrar un mensaje o simplemente no hacer nada
            System.out.println("La factura con UUID " + complemento.getUuid() + " ya está en la listaCfdis.");
            JsfUtil.addErrorMessage("Alerta","La factura con UUID " + complemento.getUuid() + " ya está en la lista.");
        }
    }

    listaPPDsSeleccionado = new ArrayList<>();
    System.out.println("listaCfdis: " + listaCfdis);
} 
    //nuevo metodo
    public boolean isFacturaEnLista(RnGcCfdisTbl factura) {
    if ( factura == null) {
        return false;
    }
    List<RnGcCfdisTbl> listaCfdis = getListaCfdis();
    System.out.println("Lista de facturas en isFacturaEnLista: " + listaCfdis);
    return listaCfdis.contains(factura);
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
            saldoInsoluto = cfdiRelacionado.getSaldoInsoluto() - cfdiRelacionado.getSaldoPagar() + cfdiRelacionado.getCantidadPagada();
        } else {
            saldoInsoluto = cfdiRelacionado.getSaldoInsoluto() + cfdiRelacionado.getCantidadPagada();
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
            RnGcUsuariosTbl usuario = new RnGcUsuariosTbl();
            usuario = usuariosFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            cfdisId.setNombreEmisor(usuario.getNombreCompleto());
            cfdisId.setRfcEmisor(usuario.getRfc());
            System.out.println("usuariocrearXMLPago: " + usuario);
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            //Raiz
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("cfdi:Comprobante");
            doc.appendChild(rootElement);
            //Atributos Raiz
            Attr version = doc.createAttribute("Version");
            version.setValue("4.0");
            rootElement.setAttributeNode(version);
            Attr exportacion = doc.createAttribute("Exportacion");
            exportacion.setValue("01");
            rootElement.setAttributeNode(exportacion);
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
            System.out.println("fecha expedicion: " + cfdisId.getFechaExpedicion());
            fecha.setValue(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(cfdisId.getFechaExpedicion()));
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
            if (cfdisId.getCondicionPago() != null && !cfdisId.getCondicionPago().isEmpty()) {
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
            cfdi.setValue("http://www.sat.gob.mx/cfd/4");
            rootElement.setAttributeNode(cfdi);
            Attr pago = doc.createAttribute("xmlns:pago20");
            pago.setValue("http://www.sat.gob.mx/Pagos20");
            rootElement.setAttributeNode(pago);
            Attr xsi = doc.createAttribute("xmlns:xsi");
            xsi.setValue("http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttributeNode(xsi);
            Attr esquema = doc.createAttribute("xsi:schemaLocation");
            esquema.setValue("http://www.sat.gob.mx/cfd/4 http://www.sat.gob.mx/sitio_internet/cfd/4/cfdv40.xsd http://www.sat.gob.mx/Pagos20 http://www.sat.gob.mx/sitio_internet/cfd/Pagos/Pagos20.xsd");
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
            nombreE.setValue(cfdisId.getNombreEmisor());
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
            nombreR.setValue(personas.getNombre());
            receptor.setAttributeNode(nombreR);
            Attr uso = doc.createAttribute("UsoCFDI");
            uso.setValue(cfdisId.getUsoCfdi());
            receptor.setAttributeNode(uso);
            Attr regimenReceptor = doc.createAttribute("RegimenFiscalReceptor");
            //regimenReceptor.setValue("605");
            regimenReceptor.setValue(String.valueOf(personas.getRegimenFiscalId().getClaveRegimenFiscal()));
            receptor.setAttributeNode(regimenReceptor);
            Attr domicilioReceptor = doc.createAttribute("DomicilioFiscalReceptor");
            //domicilioReceptor.setValue("90000");
            domicilioReceptor.setValue(String.valueOf(personas.getcodigoPostal()));
            receptor.setAttributeNode(domicilioReceptor);
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
            Attr objetoImp = doc.createAttribute("ObjetoImp");
            objetoImp.setValue("01");
            concepto.setAttributeNode(objetoImp);
            System.out.println("Probando5.0");
            //Nodo Complemento
            Element complemento = doc.createElement("cfdi:Complemento");
            rootElement.appendChild(complemento);
            //Nodo pagos
            Element pagos = doc.createElement("pago20:Pagos");
            complemento.appendChild(pagos);
            //Atributo Pagos
            Attr versionP = doc.createAttribute("Version");
            versionP.setValue("2.0");
            pagos.setAttributeNode(versionP);
            //Nodo Totales
            Element totales = doc.createElement("pago20:Totales");
            pagos.appendChild(totales);
            //Atributos pago
            Attr montoTotalP = doc.createAttribute("MontoTotalPagos");
            montoTotalP.setValue(String.valueOf(cfdisId.getMontoPago()));
            totales.setAttributeNode(montoTotalP);
            List<RnGcCfdisLineasTbl> listaCfd = new ArrayList<>();
            /*String TipoImpuesto = "", TipoImpuesto1 = "", TipoImpuesto2 = "", TipoImpuesto3 = "";
            String Impuesto1 = "", Impuesto2 = "", Impuesto3 = "";
            double TipoTasa = 0.0, TipoTasa1 = 0.0, TipoTasa2 = 0.0, TipoTasa3 = 0.0;
            String retenIVA = "", retenISR = "", retenIEPS = "";
            String ObjetoImpuesto = "";
            for (int a = 0; a < listaCfdis.size(); a++) {
                listaCfd = lineasCfdisFacade.obtenerCfdisLineas(listaCfdis.get(a));
                for (int b = 0; b < listaCfd.size(); b++) {
                    
                }
                ObjetoImpuesto = listaCfd.get(0).getProductoId().getObjetoImpId().getClave();
                TipoImpuesto = listaCfd.get(0).getTipoImpuesto();
                TipoTasa = listaCfd.get(0).getTipoTasa();
                if(listaCfd.get(0).getTipoTasa2() != null){
                    Impuesto1 = listaCfd.get(0).getImpuesto2();
                    TipoImpuesto1 = listaCfd.get(0).getTipoImpuesto2();
                    TipoTasa1 = listaCfd.get(0).getTipoTasa2();
                }
                if(listaCfd.get(0).getTipoTasa3() != null){
                    Impuesto2 = listaCfd.get(0).getImpuesto3();
                    TipoImpuesto2 = listaCfd.get(0).getTipoImpuesto3();
                    TipoTasa2 = listaCfd.get(0).getTipoTasa3();
                }
                if(listaCfd.get(0).getTipoTasa4() != null){
                    Impuesto3 = listaCfd.get(0).getImpuesto4();
                    TipoImpuesto3 = listaCfd.get(0).getTipoImpuesto4();
                    TipoTasa3 = listaCfd.get(0).getTipoTasa4();
                }
            }
            System.out.println("ObjetoImpuesto: " + ObjetoImpuesto);
            if(ObjetoImpuesto.equals("02")){
                System.out.println("TipoImpuesto: " + TipoImpuesto + "   TipoTasa: " + TipoTasa);
                if(TipoImpuesto.equals("Traslado") && TipoTasa == 0.16){    
                    Attr trasBase16 = doc.createAttribute("TotalTrasladosBaseIVA16");
                    trasBase16.setValue(new DecimalFormat("0.00").format(cfdisId.getMontoPago()/1.16));
                    Attr trasImporte16 = doc.createAttribute("TotalTrasladosImpuestoIVA16");
                    trasImporte16.setValue(new DecimalFormat("0.00").format(cfdisId.getMontoPago() - (cfdisId.getMontoPago()/1.16)));
                    totales.setAttributeNode(trasBase16);
                    totales.setAttributeNode(trasImporte16);
                }else if(TipoImpuesto.equals("Traslado") && TipoTasa == 0.8){    
                    Attr trasBase8 = doc.createAttribute("TotalTrasladosBaseIVA8");
                    trasBase8.setValue(new DecimalFormat("0.00").format(cfdisId.getMontoPago()/1.08));
                    Attr trasImporte8 = doc.createAttribute("TotalTrasladosImpuestoIVA8");
                    trasImporte8.setValue(new DecimalFormat("0.00").format(cfdisId.getMontoPago() - (cfdisId.getMontoPago()/1.08)));
                    totales.setAttributeNode(trasBase8);
                    totales.setAttributeNode(trasImporte8);
                } else if(TipoImpuesto.equals("Traslado") && TipoTasa == 0.0){    
                    Attr trasBase0 = doc.createAttribute("TotalTrasladosBaseIVA0");
                    trasBase0.setValue(new DecimalFormat("0.00").format(cfdisId.getMontoPago()/1.0));
                    Attr trasImporte0 = doc.createAttribute("TotalTrasladosImpuestoIVA0");
                    trasImporte0.setValue(new DecimalFormat("0.00").format(cfdisId.getMontoPago() - (cfdisId.getMontoPago()/1.0)));
                    totales.setAttributeNode(trasBase0);
                    totales.setAttributeNode(trasImporte0);
                }
                if((TipoImpuesto1.equals("Retención") && Impuesto1.equals("001")) || 
                        (TipoImpuesto2.equals("Retención") && Impuesto2.equals("001") ||
                        (TipoImpuesto3.equals("Retención") && Impuesto3.equals("001")))){
                    Attr retencionesISR = doc.createAttribute("TotalRetencionesISR");
                    if(Impuesto1.equals("001"))
                        retencionesISR.setValue(new DecimalFormat("0.00").format(cfdisId.getMontoPago() - (cfdisId.getMontoPago()/TipoTasa1)));
                    else if(Impuesto2.equals("001"))
                        retencionesISR.setValue(new DecimalFormat("0.00").format(cfdisId.getMontoPago() - (cfdisId.getMontoPago()/TipoTasa2)));
                    else if(Impuesto3.equals("001"))
                        retencionesISR.setValue(new DecimalFormat("0.00").format(cfdisId.getMontoPago() - (cfdisId.getMontoPago()/TipoTasa3)));
                    totales.setAttributeNode(retencionesISR);
                    retenISR = retencionesISR.getValue();
                }
                if((TipoImpuesto1.equals("Retención") && Impuesto1.equals("002")) || 
                        (TipoImpuesto2.equals("Retención") && Impuesto2.equals("002") ||
                        (TipoImpuesto3.equals("Retención") && Impuesto3.equals("002")))){
                    Attr retencionesIVA = doc.createAttribute("TotalRetencionesIVA");
                    if(Impuesto1.equals("002"))
                        retencionesIVA.setValue(new DecimalFormat("0.00").format(cfdisId.getMontoPago() - (cfdisId.getMontoPago()/TipoTasa1)));
                    else if(Impuesto2.equals("002"))
                        retencionesIVA.setValue(new DecimalFormat("0.00").format(cfdisId.getMontoPago() - (cfdisId.getMontoPago()/TipoTasa2)));
                    else if(Impuesto3.equals("002"))
                        retencionesIVA.setValue(new DecimalFormat("0.00").format(cfdisId.getMontoPago() - (cfdisId.getMontoPago()/TipoTasa3)));
                    totales.setAttributeNode(retencionesIVA);
                    retenIVA = retencionesIVA.getValue();
                }
                if((TipoImpuesto1.equals("Retención") && Impuesto1.equals("003")) || 
                        (TipoImpuesto2.equals("Retención") && Impuesto2.equals("003") ||
                        (TipoImpuesto3.equals("Retención") && Impuesto3.equals("003")))){
                    Attr retencionesIEPS = doc.createAttribute("TotalRetencionesIEPS");
                    if(Impuesto1.equals("003"))
                        retencionesIEPS.setValue(new DecimalFormat("0.00").format(cfdisId.getMontoPago() - (cfdisId.getMontoPago()/TipoTasa1)));
                    else if(Impuesto2.equals("003"))
                        retencionesIEPS.setValue(new DecimalFormat("0.00").format(cfdisId.getMontoPago() - (cfdisId.getMontoPago()/TipoTasa2)));
                    else if(Impuesto3.equals("003"))
                        retencionesIEPS.setValue(new DecimalFormat("0.00").format(cfdisId.getMontoPago() - (cfdisId.getMontoPago()/TipoTasa3)));
                    totales.setAttributeNode(retencionesIEPS);
                    retenIEPS = retencionesIEPS.getValue();
                }
            }*/
            //Nodo pago
            Element pago1 = doc.createElement("pago20:Pago");
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
            Attr tipoCambioP = doc.createAttribute("TipoCambioP");
            if(monedaP.getValue().equals("MXN"))
                tipoCambioP.setValue("1");
            else
                tipoCambioP.setValue(cfdisId.getTipoCambioP());
            pago1.setAttributeNode(tipoCambioP);
            System.out.println("Probando6.0");
            double baseTras = 0.0, importeTras = 0.0, importeRetIVA = 0.0, importeRetISR = 0.0, importeRetIEPS = 0.0;
            String impuestoTras = "", tasaTras = "", factorTras = "", impuestoRetIVA = "", impuestoRetISR = "", impuestoRetIEPS = "";
            double base8Tras = 0.0, importe8Tras = 0.0, base0Tras = 0.0, importe0Tras = 1.0;
            String tasa8Tras = "", factor8Tras = "", tasa0Tras = "", factor0Tras = "";
            for (int a = 0; a < listaCfdis.size(); a++) {
                listaCfd = lineasCfdisFacade.obtenerCfdisLineas(listaCfdis.get(a));
                //Nodo DocRelacionado
                Element docRelacionado = doc.createElement("pago20:DoctoRelacionado");
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
                impSaldoAnterior.setValue(new DecimalFormat("0.00").format(listaCfdis.get(a).getSaldoInsoluto() + listaCfdis.get(a).getCantidadPagada()));
                docRelacionado.setAttributeNode(impSaldoAnterior);
                Attr impPagado = doc.createAttribute("ImpPagado"); //-------------
                impPagado.setValue(new DecimalFormat("0.00").format(listaCfdis.get(a).getCantidadPagada()));
                docRelacionado.setAttributeNode(impPagado);
                Attr saldoInsoluto = doc.createAttribute("ImpSaldoInsoluto"); //-------------
                saldoInsoluto.setValue(new DecimalFormat("0.00").format(listaCfdis.get(a).getSaldoInsoluto()));
                docRelacionado.setAttributeNode(saldoInsoluto);
                Attr objetoImpDR = doc.createAttribute("ObjetoImpDR"); //-------------
                System.out.println("Probando8.0");
                Attr moneda2 = doc.createAttribute("MonedaDR"); //-------------
                moneda2.setValue(listaCfdis.get(a).getMoneda());
                docRelacionado.setAttributeNode(moneda2);
                Attr equivalencia = doc.createAttribute("EquivalenciaDR");
                if(moneda2.getValue().equals("MXN"))
                    equivalencia.setValue("1");
                else
                    equivalencia.setValue(listaCfdis.get(a).getTipoCambioP());
                docRelacionado.setAttributeNode(equivalencia);
                Attr numeroParcialidad = doc.createAttribute("NumParcialidad"); //-------------
                numeroParcialidad.setValue(String.valueOf(listaCfdis.get(a).getNumeroParcialidad()));
                docRelacionado.setAttributeNode(numeroParcialidad);
                if (listaCfdis.get(a).getSerie() != null) {
                    Attr serieP = doc.createAttribute("Serie"); //-------------
                    serieP.setValue(listaCfdis.get(a).getSerie());
                    docRelacionado.setAttributeNode(serieP);
                }
                boolean objImpues = false, impTras = false, impRet = false;
                String objImp = "";
                Double base = 0.0, impues = 0.0, baset = 0.0;
                for (int b = 0; b < listaCfd.size(); b++) {
                    if(listaCfd.get(b).getProductoId().getObjetoImpId().getClave().equals("02"))
                        objImpues = true;
                    else
                        objImp = listaCfd.get(b).getProductoId().getObjetoImpId().getClave();
                    if(listaCfd.get(b).getTipoImpuesto() != null && listaCfd.get(b).getTipoImpuesto().equals("Traslado")){
                        impues = listaCfd.get(b).getTipoTasa(); impTras = true;
                    }if(listaCfd.get(b).getTipoImpuesto2() != null && listaCfd.get(b).getTipoImpuesto2().equals("Traslado")){
                        impues = listaCfd.get(b).getTipoTasa2(); impTras = true;
                    }if(listaCfd.get(b).getTipoImpuesto3() != null && listaCfd.get(b).getTipoImpuesto3().equals("Traslado")){
                        impues = listaCfd.get(b).getTipoTasa3(); impTras = true;
                    }if(listaCfd.get(b).getTipoImpuesto4() != null && listaCfd.get(b).getTipoImpuesto4().equals("Traslado")){
                        impues = listaCfd.get(b).getTipoTasa4(); impTras = true;
                    }
                       
                    if(listaCfd.get(b).getTipoImpuesto() != null && listaCfd.get(b).getTipoImpuesto().equals("Retención")){
                        //impues -= listaCfd.get(b).getTipoTasa(); 
                        impRet = true;
                    }if(listaCfd.get(b).getTipoImpuesto2() != null && listaCfd.get(b).getTipoImpuesto2().equals("Retención")){
                        //impues -= listaCfd.get(b).getTipoTasa2(); 
                        impRet = true;
                    }if(listaCfd.get(b).getTipoImpuesto3() != null && listaCfd.get(b).getTipoImpuesto3().equals("Retención")){
                        //impues -= listaCfd.get(b).getTipoTasa3(); 
                        impRet = true;
                    }if(listaCfd.get(b).getTipoImpuesto4() != null && listaCfd.get(b).getTipoImpuesto4().equals("Retención")){
                        //impues -= listaCfd.get(b).getTipoTasa4(); 
                        impRet = true;
                    }
                }
                base = listaCfdis.get(a).getCantidadPagada() / (1 + impues);
                baset = listaCfdis.get(a).getCantidadPagada();
                if(objImpues)
                    objetoImpDR.setValue("02");
                else 
                    objetoImpDR.setValue(objImp);
                docRelacionado.setAttributeNode(objetoImpDR);
                
                if(objImpues){
                    Element impuestosDR = doc.createElement("pago20:ImpuestosDR");
                    docRelacionado.appendChild(impuestosDR);
                    Element retencionesDR = doc.createElement("pago20:RetencionesDR");
                    Element trasladosDR = doc.createElement("pago20:TrasladosDR");
                    if(impRet){
                        impuestosDR.appendChild(retencionesDR);
                    }
                    if(impTras){
                        impuestosDR.appendChild(trasladosDR);
                    }
                    for (int b = 0; b < listaCfd.size(); b++) {
                        if(listaCfd.get(b).getTipoImpuesto() != null && listaCfd.get(b).getTipoImpuesto().equals("Traslado")){
                            Element trasladoDR = doc.createElement("pago20:TrasladoDR");
                            trasladosDR.appendChild(trasladoDR);
                            Attr baseTDR = doc.createAttribute("BaseDR"); 
                            base = listaCfd.get(b).getBase();
                            baseTDR.setValue(new DecimalFormat("0.00").format(base));
                            trasladoDR.setAttributeNode(baseTDR);
                            Attr impuestoTDR = doc.createAttribute("ImpuestoDR"); 
                            impuestoTDR.setValue(listaCfd.get(b).getImpuesto());
                            trasladoDR.setAttributeNode(impuestoTDR);
                            Attr tipoFactorTDR = doc.createAttribute("TipoFactorDR"); 
                            tipoFactorTDR.setValue(listaCfd.get(b).getTipoFactor());
                            trasladoDR.setAttributeNode(tipoFactorTDR);
                            if(!tipoFactorTDR.getValue().equals("Exento")){
                                Attr tasaCuotaTDR = doc.createAttribute("TasaOCuotaDR"); 
                                tasaCuotaTDR.setValue(new DecimalFormat("0.000000").format(listaCfd.get(b).getTipoTasa()));
                                trasladoDR.setAttributeNode(tasaCuotaTDR);
                                Attr importeTDR = doc.createAttribute("ImporteDR"); 
                                importeTDR.setValue(new DecimalFormat("0.00").format(base * listaCfd.get(b).getTipoTasa()));
                                trasladoDR.setAttributeNode(importeTDR);
                                if(listaCfd.get(b).getTipoTasa() == 0.16){
                                    baseTras += base;
                                    importeTras += Double.parseDouble(importeTDR.getValue());
                                    impuestoTras = listaCfd.get(b).getImpuesto();
                                    tasaTras = new DecimalFormat("0.000000").format(listaCfd.get(b).getTipoTasa());
                                    factorTras = listaCfd.get(b).getTipoFactor();
                                }else if(listaCfd.get(b).getTipoTasa() == 0.08){
                                    base8Tras += base;
                                    importe8Tras += Double.parseDouble(importeTDR.getValue());
                                    impuestoTras = listaCfd.get(b).getImpuesto();
                                    tasa8Tras = new DecimalFormat("0.000000").format(listaCfd.get(b).getTipoTasa());
                                    factor8Tras = listaCfd.get(b).getTipoFactor();
                                }else if(listaCfd.get(b).getTipoTasa() == 0.0){
                                    base0Tras += base;
                                    importe0Tras = Double.parseDouble(importeTDR.getValue());
                                    impuestoTras = listaCfd.get(b).getImpuesto();
                                    tasa0Tras = new DecimalFormat("0.000000").format(listaCfd.get(b).getTipoTasa());
                                    factor0Tras = listaCfd.get(b).getTipoFactor();
                                }
                            }
                        }
                        if(listaCfd.get(b).getTipoImpuesto2() != null && listaCfd.get(b).getTipoImpuesto2().equals("Retención")){
                            Element retencionDR = doc.createElement("pago20:RetencionDR");
                            retencionesDR.appendChild(retencionDR);
                            Attr baseTDR = doc.createAttribute("BaseDR"); 
                            baset = listaCfd.get(b).getBase();
                            baseTDR.setValue(new DecimalFormat("0.00").format(baset));
                            retencionDR.setAttributeNode(baseTDR);
                            Attr impuestoTDR = doc.createAttribute("ImpuestoDR"); 
                            impuestoTDR.setValue(listaCfd.get(b).getImpuesto2());
                            retencionDR.setAttributeNode(impuestoTDR);
                            Attr tipoFactorTDR = doc.createAttribute("TipoFactorDR"); 
                            tipoFactorTDR.setValue(listaCfd.get(b).getTipoFactor2());
                            retencionDR.setAttributeNode(tipoFactorTDR);
                            Attr tasaCuotaTDR = doc.createAttribute("TasaOCuotaDR"); 
                            tasaCuotaTDR.setValue(new DecimalFormat("0.000000").format(listaCfd.get(b).getTipoTasa2()));
                            retencionDR.setAttributeNode(tasaCuotaTDR);
                            Attr importeTDR = doc.createAttribute("ImporteDR"); 
                            importeTDR.setValue(new DecimalFormat("0.00").format(baset * listaCfd.get(b).getTipoTasa2()));
                            retencionDR.setAttributeNode(importeTDR);
                            if(listaCfd.get(b).getImpuesto2().equals("001")){
                                importeRetISR += Double.parseDouble(importeTDR.getValue());
                                impuestoRetISR = listaCfd.get(b).getImpuesto2();
                            }else if(listaCfd.get(b).getImpuesto2().equals("002")){
                                importeRetIVA += Double.parseDouble(importeTDR.getValue());
                                impuestoRetIVA = listaCfd.get(b).getImpuesto2();
                            }else if(listaCfd.get(b).getImpuesto2().equals("003")){
                                importeRetIEPS += Double.parseDouble(importeTDR.getValue());
                                impuestoRetIEPS = listaCfd.get(b).getImpuesto2();
                            }
                        }
                        if(listaCfd.get(b).getTipoImpuesto3() != null && listaCfd.get(b).getTipoImpuesto3().equals("Retención")){
                            Element retencionDR = doc.createElement("pago20:RetencionDR");
                            retencionesDR.appendChild(retencionDR);
                            Attr baseTDR = doc.createAttribute("BaseDR");
                            baset = listaCfd.get(b).getBase();
                            baseTDR.setValue(new DecimalFormat("0.00").format(baset));
                            retencionDR.setAttributeNode(baseTDR);
                            Attr impuestoTDR = doc.createAttribute("ImpuestoDR"); 
                            impuestoTDR.setValue(listaCfd.get(b).getImpuesto3());
                            retencionDR.setAttributeNode(impuestoTDR);
                            Attr tipoFactorTDR = doc.createAttribute("TipoFactorDR"); 
                            tipoFactorTDR.setValue(listaCfd.get(b).getTipoFactor3());
                            retencionDR.setAttributeNode(tipoFactorTDR);
                            Attr tasaCuotaTDR = doc.createAttribute("TasaOCuotaDR"); 
                            tasaCuotaTDR.setValue(new DecimalFormat("0.000000").format(listaCfd.get(b).getTipoTasa3()));
                            retencionDR.setAttributeNode(tasaCuotaTDR);
                            Attr importeTDR = doc.createAttribute("ImporteDR"); 
                            importeTDR.setValue(new DecimalFormat("0.00").format(baset * listaCfd.get(b).getTipoTasa3()));
                            retencionDR.setAttributeNode(importeTDR);
                            if(listaCfd.get(b).getImpuesto3().equals("001")){
                                importeRetISR += Double.parseDouble(importeTDR.getValue());
                                impuestoRetISR = listaCfd.get(b).getImpuesto3();
                            }else if(listaCfd.get(b).getImpuesto3().equals("002")){
                                importeRetIVA += Double.parseDouble(importeTDR.getValue());
                                impuestoRetIVA = listaCfd.get(b).getImpuesto3();
                            }else if(listaCfd.get(b).getImpuesto3().equals("003")){
                                importeRetIEPS += Double.parseDouble(importeTDR.getValue());
                                impuestoRetIEPS = listaCfd.get(b).getImpuesto3();
                            }
                        }
                        if(listaCfd.get(b).getTipoImpuesto4() != null && listaCfd.get(b).getTipoImpuesto4().equals("Retención")){
                            Element retencionDR = doc.createElement("pago20:RetencionDR");
                            retencionesDR.appendChild(retencionDR);
                            Attr baseTDR = doc.createAttribute("BaseDR"); 
                            baset = listaCfd.get(b).getBase();
                            baseTDR.setValue(new DecimalFormat("0.00").format(baset));
                            retencionDR.setAttributeNode(baseTDR);
                            Attr impuestoTDR = doc.createAttribute("ImpuestoDR"); 
                            impuestoTDR.setValue(listaCfd.get(b).getImpuesto4());
                            retencionDR.setAttributeNode(impuestoTDR);
                            Attr tipoFactorTDR = doc.createAttribute("TipoFactorDR"); 
                            tipoFactorTDR.setValue(listaCfd.get(b).getTipoFactor4());
                            retencionDR.setAttributeNode(tipoFactorTDR);
                            Attr tasaCuotaTDR = doc.createAttribute("TasaOCuotaDR"); 
                            tasaCuotaTDR.setValue(new DecimalFormat("0.000000").format(listaCfd.get(b).getTipoTasa4()));
                            retencionDR.setAttributeNode(tasaCuotaTDR);
                            Attr importeTDR = doc.createAttribute("ImporteDR"); 
                            importeTDR.setValue(new DecimalFormat("0.00").format(baset * listaCfd.get(b).getTipoTasa4()));
                            retencionDR.setAttributeNode(importeTDR);
                            if(listaCfd.get(b).getImpuesto4().equals("001")){
                                importeRetISR += Double.parseDouble(importeTDR.getValue());
                                impuestoRetISR = listaCfd.get(b).getImpuesto4();
                            }else if(listaCfd.get(b).getImpuesto4().equals("002")){
                                importeRetIVA += Double.parseDouble(importeTDR.getValue());
                                impuestoRetIVA = listaCfd.get(b).getImpuesto4();
                            }else if(listaCfd.get(b).getImpuesto4().equals("003")){
                                importeRetIEPS += Double.parseDouble(importeTDR.getValue());
                                impuestoRetIEPS = listaCfd.get(b).getImpuesto4();
                            }
                        }
                    }
                }
            }
            if(importeTras > 0 || importe8Tras > 0 || importe0Tras == 0 || importeRetIVA > 0 || importeRetISR > 0 || importeRetIEPS > 0){
                Element impuestosP = doc.createElement("pago20:ImpuestosP");
                pago1.appendChild(impuestosP);
                if(importeRetIVA > 0 || importeRetISR > 0 || importeRetIEPS > 0){
                    Element retencionesP = doc.createElement("pago20:RetencionesP");
                    impuestosP.appendChild(retencionesP);
                    if(importeRetIVA > 0){
                        Attr retencionesIVA = doc.createAttribute("TotalRetencionesIVA");
                        retencionesIVA.setValue(new DecimalFormat("0.00").format(importeRetIVA));
                        totales.setAttributeNode(retencionesIVA);
                        
                        Element retencionP = doc.createElement("pago20:RetencionP");
                        retencionesP.appendChild(retencionP);
                        Attr impuestoP = doc.createAttribute("ImpuestoP"); 
                        impuestoP.setValue(impuestoRetIVA);
                        retencionP.setAttributeNode(impuestoP);
                        Attr importeP = doc.createAttribute("ImporteP"); 
                        importeP.setValue(new DecimalFormat("0.00").format(importeRetIVA));
                        retencionP.setAttributeNode(importeP);
                    }
                    if(importeRetISR > 0){
                        Attr retencionesISR = doc.createAttribute("TotalRetencionesISR");
                        retencionesISR.setValue(new DecimalFormat("0.00").format(importeRetISR));
                        totales.setAttributeNode(retencionesISR);
                        
                        Element retencionP = doc.createElement("pago20:RetencionP");
                        retencionesP.appendChild(retencionP);
                        Attr impuestoP = doc.createAttribute("ImpuestoP"); 
                        impuestoP.setValue(impuestoRetISR);
                        retencionP.setAttributeNode(impuestoP);
                        Attr importeP = doc.createAttribute("ImporteP"); 
                        importeP.setValue(new DecimalFormat("0.00").format(importeRetISR));
                        retencionP.setAttributeNode(importeP);
                    }
                    if(importeRetIEPS > 0){
                        Attr retencionesIEPS = doc.createAttribute("TotalRetencionesIEPS");
                        retencionesIEPS.setValue(new DecimalFormat("0.00").format(importeRetIEPS));
                        totales.setAttributeNode(retencionesIEPS);
                        
                        Element retencionP = doc.createElement("pago20:RetencionP");
                        retencionesP.appendChild(retencionP);
                        Attr impuestoP = doc.createAttribute("ImpuestoP"); 
                        impuestoP.setValue(impuestoRetIEPS);
                        retencionP.setAttributeNode(impuestoP);
                        Attr importeP = doc.createAttribute("ImporteP"); 
                        importeP.setValue(new DecimalFormat("0.00").format(importeRetIEPS));
                        retencionP.setAttributeNode(importeP);
                    }
                }
                if(importeTras > 0 || importe8Tras > 0 || importe0Tras == 0){ 
                    Element trasladosP = doc.createElement("pago20:TrasladosP");
                    impuestosP.appendChild(trasladosP);
                    if(importeTras > 0){ 
                        Attr trasBase16 = doc.createAttribute("TotalTrasladosBaseIVA16");
                        trasBase16.setValue(new DecimalFormat("0.00").format(baseTras));
                        Attr trasImporte16 = doc.createAttribute("TotalTrasladosImpuestoIVA16");
                        trasImporte16.setValue(new DecimalFormat("0.00").format(importeTras));
                        totales.setAttributeNode(trasBase16);
                        totales.setAttributeNode(trasImporte16);
                        
                        Element trasladoP = doc.createElement("pago20:TrasladoP");
                        trasladosP.appendChild(trasladoP);
                        Attr baseP = doc.createAttribute("BaseP"); 
                        baseP.setValue(new DecimalFormat("0.00").format(baseTras));
                        trasladoP.setAttributeNode(baseP);
                        Attr impuestoP = doc.createAttribute("ImpuestoP"); 
                        impuestoP.setValue(impuestoTras);
                        trasladoP.setAttributeNode(impuestoP);
                        Attr tipoFactorP = doc.createAttribute("TipoFactorP"); 
                        tipoFactorP.setValue(factorTras);
                        trasladoP.setAttributeNode(tipoFactorP);
                        Attr tasaCuotaP = doc.createAttribute("TasaOCuotaP"); 
                        tasaCuotaP.setValue(tasaTras);
                        trasladoP.setAttributeNode(tasaCuotaP);
                        Attr importeP = doc.createAttribute("ImporteP"); 
                        importeP.setValue(new DecimalFormat("0.00").format(importeTras));
                        trasladoP.setAttributeNode(importeP);
                    }else if(importe8Tras > 0){
                        Attr trasBase8 = doc.createAttribute("TotalTrasladosBaseIVA8");
                        trasBase8.setValue(new DecimalFormat("0.00").format(base8Tras));
                        Attr trasImporte8 = doc.createAttribute("TotalTrasladosImpuestoIVA8");
                        trasImporte8.setValue(new DecimalFormat("0.00").format(importe8Tras));
                        totales.setAttributeNode(trasBase8);
                        totales.setAttributeNode(trasImporte8);   
                    
                        Element trasladoP = doc.createElement("pago20:TrasladoP");
                        trasladosP.appendChild(trasladoP);
                        Attr baseP = doc.createAttribute("BaseP"); 
                        baseP.setValue(new DecimalFormat("0.00").format(base8Tras));
                        trasladoP.setAttributeNode(baseP);
                        Attr impuestoP = doc.createAttribute("ImpuestoP"); 
                        impuestoP.setValue(impuestoTras);
                        trasladoP.setAttributeNode(impuestoP);
                        Attr tipoFactorP = doc.createAttribute("TipoFactorP"); 
                        tipoFactorP.setValue(factor8Tras);
                        trasladoP.setAttributeNode(tipoFactorP);
                        Attr tasaCuotaP = doc.createAttribute("TasaOCuotaP"); 
                        tasaCuotaP.setValue(tasa8Tras);
                        trasladoP.setAttributeNode(tasaCuotaP);
                        Attr importeP = doc.createAttribute("ImporteP"); 
                        importeP.setValue(new DecimalFormat("0.00").format(importe8Tras));
                        trasladoP.setAttributeNode(importeP);
                    }else if(importe0Tras == 0){ 
                        Attr trasBase0 = doc.createAttribute("TotalTrasladosBaseIVA0");
                        trasBase0.setValue(new DecimalFormat("0.00").format(base0Tras));
                        Attr trasImporte0 = doc.createAttribute("TotalTrasladosImpuestoIVA0");
                        trasImporte0.setValue(new DecimalFormat("0.00").format(importe0Tras));
                        totales.setAttributeNode(trasBase0);
                        totales.setAttributeNode(trasImporte0);
                    
                        Element trasladoP = doc.createElement("pago20:TrasladoP");
                        trasladosP.appendChild(trasladoP);
                        Attr baseP = doc.createAttribute("BaseP"); 
                        baseP.setValue(new DecimalFormat("0.00").format(base0Tras));
                        trasladoP.setAttributeNode(baseP);
                        Attr impuestoP = doc.createAttribute("ImpuestoP"); 
                        impuestoP.setValue(impuestoTras);
                        trasladoP.setAttributeNode(impuestoP);
                        Attr tipoFactorP = doc.createAttribute("TipoFactorP"); 
                        tipoFactorP.setValue(factor0Tras);
                        trasladoP.setAttributeNode(tipoFactorP);
                        Attr tasaCuotaP = doc.createAttribute("TasaOCuotaP"); 
                        tasaCuotaP.setValue(tasa0Tras);
                        trasladoP.setAttributeNode(tasaCuotaP);
                        Attr importeP = doc.createAttribute("ImporteP"); 
                        importeP.setValue(new DecimalFormat("0.00").format(importe0Tras));
                        trasladoP.setAttributeNode(importeP);
                    }
                }
            }
            System.out.println("Probando9.0");
            System.out.println("crearXML3");
            System.out.println("Probando");
            File xslt = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/cadenaoriginal_4_0.xslt"));            //Inicio calcula cadena original
            StreamSource sourceXSL = new StreamSource(xslt);
            System.out.println("Probando2");

            DOMSource source = new DOMSource(doc);
            //tempFile = new File("C:\\Users\\Developer1\\Desktop\\XMLs\\probandoXML.xml");
            tempFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/xmlTemp.xml"));

            //FileWriter writer = new FileWriter(tempFile);
            System.out.println("Probando3");
            StreamResult sourceXml = new StreamResult(tempFile);
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
            ex.printStackTrace();
        } catch (TransformerException ex2) {
            System.out.println("Error2: " + ex2.getMessage());
        }
        return valor;
    }

    public boolean crearXmlIngEgre() {
        File tempFile = null;
        System.out.println("crearXmlIngEgre");
        String cadOrig = "";
        double totalPeso = 0.0;
        int totalMerc = 0;
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
            version.setValue("4.0");
            rootElement.setAttributeNode(version);
            Attr condicionesPago = doc.createAttribute("CondicionesDePago");
            condicionesPago.setValue(cfdisId.getCondicionPago());
            rootElement.setAttributeNode(condicionesPago);
            Attr exportacion = doc.createAttribute("Exportacion");
            exportacion.setValue(cfdisId.getExportacionId().getClave());
            rootElement.setAttributeNode(exportacion);
            
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
            fecha.setValue(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(cfdisId.getFechaExpedicion()));
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
            cfdi.setValue("http://www.sat.gob.mx/cfd/4");
            rootElement.setAttributeNode(cfdi);
            Attr impLocal = doc.createAttribute("xmlns:implocal");
            impLocal.setValue("http://www.sat.gob.mx/implocal");
            rootElement.setAttributeNode(impLocal);
            Attr xsi = doc.createAttribute("xmlns:xsi");
            xsi.setValue("http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttributeNode(xsi);
            Attr esquema = doc.createAttribute("xsi:schemaLocation");
            if (confirmacion && tipoCartaPorte.equals("Ingreso")){
                esquema.setValue("http://www.sat.gob.mx/cfd/4 http://www.sat.gob.mx/sitio_internet/cfd/4/cfdv40.xsd http://www.sat.gob.mx/implocal http://www.sat.gob.mx/sitio_internet/cfd/implocal/implocal.xsd http://www.sat.gob.mx/CartaPorte31 http://www.sat.gob.mx/sitio_internet/cfd/CartaPorte/CartaPorte31.xsd");
                Attr cartaPorte3 = doc.createAttribute("xmlns:cartaporte31");
                cartaPorte3.setValue("http://www.sat.gob.mx/CartaPorte31");
                rootElement.setAttributeNode(cartaPorte3);
            }else
                esquema.setValue("http://www.sat.gob.mx/cfd/4 http://www.sat.gob.mx/sitio_internet/cfd/4/cfdv40.xsd http://www.sat.gob.mx/implocal http://www.sat.gob.mx/sitio_internet/cfd/implocal/implocal.xsd");
            rootElement.setAttributeNode(esquema);
            //Nodo CfdiRelacionados
            if (cfdisId.getTipoRelacion() != null && listaCfdisRelacionados.size() > 0) {
                Element relacionados = doc.createElement("cfdi:CfdiRelacionados");
                rootElement.appendChild(relacionados);
                //Attributos CfdiRelacionados
                Attr tipoRelacion = doc.createAttribute("TipoRelacion");
                if(cfdisId.getTipoRelacion().length() > 1)
                    tipoRelacion.setValue(cfdisId.getTipoRelacion());
                else
                    tipoRelacion.setValue("0" + cfdisId.getTipoRelacion());
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
            nombreE.setValue(cfdisId.getNombreEmisor());
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
            nombreR.setValue(personas.getNombre());
            System.out.print("nombre " + personas.getNombre());
            System.out.print("nombre strip " + stripAccents(personas.getNombre()));
            receptor.setAttributeNode(nombreR);
            Attr uso = doc.createAttribute("UsoCFDI");
            uso.setValue(cfdisId.getUsoCfdi());
            receptor.setAttributeNode(uso);
            Attr regimenReceptor = doc.createAttribute("RegimenFiscalReceptor");
            //regimenReceptor.setValue("605");
            regimenReceptor.setValue(String.valueOf(personas.getRegimenFiscalId().getClaveRegimenFiscal()));
            receptor.setAttributeNode(regimenReceptor);
            Attr domicilioReceptor = doc.createAttribute("DomicilioFiscalReceptor");
            //domicilioReceptor.setValue("90000");
            domicilioReceptor.setValue(personas.getcodigoPostal());
            receptor.setAttributeNode(domicilioReceptor);
            
            System.out.println("crearXML3");
            //Nodo conceptos
            Element conceptos = doc.createElement("cfdi:Conceptos");
            rootElement.appendChild(conceptos);
            for (int i = 0; i < cfdisLineas.size(); i++) {
                //Atributos conceptos
                System.out.println("Entro a lineas**");
                Element concepto = doc.createElement("cfdi:Concepto");
                conceptos.appendChild(concepto);
                Attr claveProd = doc.createAttribute("ClaveProdServ");
                claveProd.setValue(String.valueOf(cfdisLineas.get(i).getClaveProdServ()));
                concepto.setAttributeNode(claveProd);
                Attr objetoImp = doc.createAttribute("ObjetoImp");
                objetoImp.setValue(cfdisLineas.get(i).getProductoId().getObjetoImpId().getClave());
                concepto.setAttributeNode(objetoImp);
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
                System.out.println("llego hasta impuestos..");
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
                        if(!factor1.getValue().equals("Exento")){
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
                        if(!factor2.getValue().equals("Exento")){
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
                        if(!factor3.getValue().equals("Exento")){
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
                        if(!factor4.getValue().equals("Exento")){
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
                    }
                    System.out.println("dato4");
                }
                System.out.println("salto el primer impuesto");
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
            System.out.println("Termino Impuestos");
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
            if (Double.parseDouble(trasladadoISR()) > 0.0 || (Double.parseDouble(trasladadoIva()) > 0.0 || iva0() || Double.parseDouble(trasladadoExento()) > 0.0) || Double.parseDouble(trasladadoIEPS()) > 0.0) {
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
                    Attr base6 = doc.createAttribute("Base");
                    base6.setValue(new DecimalFormat("0.00").format(baseT16));
                    traslado16.setAttributeNode(base6);
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
                    Attr base8 = doc.createAttribute("Base");
                    base8.setValue(new DecimalFormat("0.00").format(baseT8));
                    traslado8.setAttributeNode(base8);
                    Attr importe8 = doc.createAttribute("Importe");
                    importe8.setValue(new DecimalFormat("0.00").format(iva8()));
                    traslado8.setAttributeNode(importe8);
                    Attr impuesto8 = doc.createAttribute("Impuesto");
                    impuesto8.setValue("002");
                    traslado8.setAttributeNode(impuesto8);
                    Attr tasa8 = doc.createAttribute("TasaOCuota");
                    tasa8.setValue("0.080000");
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
                    Attr base0 = doc.createAttribute("Base");
                    base0.setValue(new DecimalFormat("0.00").format(baseT0));
                    traslado0.setAttributeNode(base0);
                    Attr importe0 = doc.createAttribute("Importe");
                    importe0.setValue(new DecimalFormat("0.00").format(0.0));
                    traslado0.setAttributeNode(importe0);
                    Attr impuesto0 = doc.createAttribute("Impuesto");
                    impuesto0.setValue("002");
                    traslado0.setAttributeNode(impuesto0);
                    Attr tasa0 = doc.createAttribute("TasaOCuota");
                    tasa0.setValue("0.000000");
                    traslado0.setAttributeNode(tasa0);
                    Attr factor0 = doc.createAttribute("TipoFactor");
                    factor0.setValue("Tasa");
                    traslado0.setAttributeNode(factor0);
                }
                if(Double.parseDouble(trasladadoExento()) > 0.0){
                    //nodo traslado
                    Element traslado0 = doc.createElement("cfdi:Traslado");
                    traslados.appendChild(traslado0);
                    //atributos traslado
                    Attr base0 = doc.createAttribute("Base");
                    base0.setValue(trasladadoExento());
                    traslado0.setAttributeNode(base0);
                    Attr impuesto0 = doc.createAttribute("Impuesto");
                    impuesto0.setValue("002");
                    traslado0.setAttributeNode(impuesto0);
                    Attr factor0 = doc.createAttribute("TipoFactor");
                    factor0.setValue("Exento");
                    traslado0.setAttributeNode(factor0);
                }
            }
            //Complemento
            if (listaImpuestosCfdis != null || (confirmacion && tipoCartaPorte.equals("Ingreso"))) {
            Element complemento = doc.createElement("cfdi:Complemento");
            rootElement.appendChild(complemento);
            if (listaImpuestosCfdis != null) {
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
            System.out.println("confirmacion: " + confirmacion);
            if (confirmacion && tipoCartaPorte.equals("Ingreso")){
                List<RnGcCfdisLineasTbl> itemsEncDetalle = new ArrayList<>();
                for(RnGcCfdisLineasTbl prod : listaprod1){
                    itemsEncDetalle.add(prod);
                }
                for(RnGcCfdisLineasTbl prod : listaprod2){
                    itemsEncDetalle.add(prod);
                }
                for(RnGcCfdisLineasTbl prod : listaprod3){
                    itemsEncDetalle.add(prod);
                }
                String uuidR = UUID.randomUUID().toString();
                uuidR = uuidR.toUpperCase();
                uuidR = "CCC" + uuidR.substring(3);
                System.out.println("itemsEncDetalle: " + itemsEncDetalle);
                /*NodeList items = doc.getElementsByTagName("cfdi:Complemento");
                Element complemento = doc.createElement("cfdi:Complemento");
                System.out.println("items.getLength(): " + items.getLength());
                if(items.getLength() == 0){
                    rootElement.appendChild(complemento);
                }*/
                Element carta = doc.createElement("cartaporte31:CartaPorte");
                complemento.appendChild(carta);

                Attr versi = doc.createAttribute("Version");
                versi.setNodeValue("3.1");
                carta.setAttributeNode(versi);
                Attr IdCCP = doc.createAttribute("IdCCP");
                //IdCCP.setNodeValue("CCCF0A7A-D86C-4CB7-865D-5C016AA640D4");
                IdCCP.setNodeValue(uuidR);
                carta.setAttributeNode(IdCCP);
                Attr transpor = doc.createAttribute("TranspInternac");
                transpor.setNodeValue(cartaPorte.getTransporteInter());
                carta.setAttributeNode(transpor);
                Attr totalDist = doc.createAttribute("TotalDistRec");
                totalDist.setNodeValue(cartaPorte.getTotalDistancia());
                carta.setAttributeNode(totalDist);
                Element ubicaciones = doc.createElement("cartaporte31:Ubicaciones");
                carta.appendChild(ubicaciones);

                Element ubicacion = doc.createElement("cartaporte31:Ubicacion");
                ubicaciones.appendChild(ubicacion);

                Attr tipoUbicacion = doc.createAttribute("TipoUbicacion");
                tipoUbicacion.setValue("Origen");
                ubicacion.setAttributeNode(tipoUbicacion);
                System.out.println("destinos: " + destinos);
                if(destinos.size() > 1){
                    Attr idUbicacion = doc.createAttribute("IDUbicacion");
                    idUbicacion.setValue(destinos.get(0).getOrigen());
                    ubicacion.setAttributeNode(idUbicacion);
                }
                Attr rfcRemitente = doc.createAttribute("RFCRemitenteDestinatario");
                rfcRemitente.setValue(cartaPorte.getRfcRemitente());
                ubicacion.setAttributeNode(rfcRemitente);
                Attr nombreRemitente = doc.createAttribute("NombreRemitenteDestinatario");
                nombreRemitente.setValue(cartaPorte.getNombreRemitente());
                ubicacion.setAttributeNode(nombreRemitente);
                Attr fechaSalida = doc.createAttribute("FechaHoraSalidaLlegada");
                fechaSalida.setValue(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(cartaPorte.getFechaSalida()));
                ubicacion.setAttributeNode(fechaSalida);
System.out.println("FechaSalida(): " + cartaPorte.getFechaSalida());
                Element domicilioR = doc.createElement("cartaporte31:Domicilio");
                ubicacion.appendChild(domicilioR);

                Attr municipioR = doc.createAttribute("Municipio");
                municipioR.setValue(cartaPorte.getClaveMunicipio());
                domicilioR.setAttributeNode(municipioR);
                Attr estadoR = doc.createAttribute("Estado");
                estadoR.setValue(cartaPorte.getClaveEstado());
                domicilioR.setAttributeNode(estadoR);
                Attr paisR = doc.createAttribute("Pais");
                paisR.setValue(cartaPorte.getClavePais());
                domicilioR.setAttributeNode(paisR);
                Attr codigoPostalR = doc.createAttribute("CodigoPostal");
                codigoPostalR.setValue(String.valueOf(cartaPorte.getCodPostal()));
                domicilioR.setAttributeNode(codigoPostalR);
                Attr calleR = doc.createAttribute("Calle");
                calleR.setValue(cartaPorte.getCalle());
                domicilioR.setAttributeNode(calleR);
                Attr nExteriorR = doc.createAttribute("NumeroExterior");
                nExteriorR.setValue(cartaPorte.getNoExt());
                domicilioR.setAttributeNode(nExteriorR);
                Attr coloniaR = doc.createAttribute("Colonia");
                coloniaR.setValue(cartaPorte.getClaveColonia());
                domicilioR.setAttributeNode(coloniaR);

                for(RnGcCpOrigendestinoTbl od : destinos){
                    Element ubicacionD = doc.createElement("cartaporte31:Ubicacion");
                    ubicaciones.appendChild(ubicacionD);
                    if(destinos.size() > 1){
                        Attr idUbicacionD = doc.createAttribute("IDUbicacion");
                        idUbicacionD.setValue(od.getDestino());
                        ubicacionD.setAttributeNode(idUbicacionD);
                    }
                    Attr tipoUbicacionD = doc.createAttribute("TipoUbicacion");
                    tipoUbicacionD.setValue("Destino");
                    ubicacionD.setAttributeNode(tipoUbicacionD);
                    Attr rfcDestinatario = doc.createAttribute("RFCRemitenteDestinatario");
                    rfcDestinatario.setValue(od.getRfcDestinatario());
                    ubicacionD.setAttributeNode(rfcDestinatario);
                    Attr nombreDestinatario= doc.createAttribute("NombreRemitenteDestinatario");
                    nombreDestinatario.setValue(od.getNombreDestinatario());
                    ubicacionD.setAttributeNode(nombreDestinatario);
                    Attr fechaLlegada = doc.createAttribute("FechaHoraSalidaLlegada");
                    fechaLlegada.setValue(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(od.getFechaLlegada()));
                    ubicacionD.setAttributeNode(fechaLlegada);
                    Attr distancia = doc.createAttribute("DistanciaRecorrida");
                    distancia.setValue(String.valueOf(od.getDistancia()));
                    ubicacionD.setAttributeNode(distancia);

                    Element domicilioD = doc.createElement("cartaporte31:Domicilio");
                    ubicacionD.appendChild(domicilioD);

                    Attr municipioD = doc.createAttribute("Municipio");
                    municipioD.setValue(od.getClaveMunicipio());
                    domicilioD.setAttributeNode(municipioD);
                    Attr estadoD = doc.createAttribute("Estado");
                    estadoD.setValue(od.getClaveEstado());
                    domicilioD.setAttributeNode(estadoD);
                    Attr paisD = doc.createAttribute("Pais");
                    paisD.setValue(od.getClavePais());
                    domicilioD.setAttributeNode(paisD);
                    Attr codigoPostalD = doc.createAttribute("CodigoPostal");
                    codigoPostalD.setValue(String.valueOf(od.getCodPostal()));
                    domicilioD.setAttributeNode(codigoPostalD);
                    Attr calleD = doc.createAttribute("Calle");
                    calleD.setValue(od.getCalle());
                    domicilioD.setAttributeNode(calleD);
                    Attr nExteriorD = doc.createAttribute("NumeroExterior");
                    nExteriorD.setValue(od.getNoExt());
                    domicilioD.setAttributeNode(nExteriorD);
                    Attr coloniaD = doc.createAttribute("Colonia");
                    coloniaD.setValue(od.getClaveColonia());
                    domicilioD.setAttributeNode(coloniaD);
                }
            
                Element mercancias = doc.createElement("cartaporte31:Mercancias");
                carta.appendChild(mercancias);

                Attr pesoTotal = doc.createAttribute("PesoBrutoTotal");
                Attr unidadPeso = doc.createAttribute("UnidadPeso");
                unidadPeso.setValue("KGM");
                mercancias.setAttributeNode(unidadPeso);
                Attr pesoNeto = doc.createAttribute("PesoNetoTotal");
                Attr numeroMercancias = doc.createAttribute("NumTotalMercancias");

                for (RnGcCfdisLineasTbl endDetalle : itemsEncDetalle) {
                    Element mercancia = doc.createElement("cartaporte31:Mercancia");
                    mercancias.appendChild(mercancia);
                    System.out.print("Cantidad: " + endDetalle.getCantidad() + "  Peso " + endDetalle.getProductoId().getPeso());
                    totalMerc++;
                    totalPeso += endDetalle.getCantidad() * endDetalle.getProductoId().getPeso();

                    Attr bienes = doc.createAttribute("BienesTransp");
                    bienes.setValue(endDetalle.getProductoId().getClaveProductServ());
                    mercancia.setAttributeNode(bienes);
                    Attr cantidad = doc.createAttribute("Cantidad");
                    cantidad.setValue(String.valueOf(endDetalle.getCantidad()));
                    mercancia.setAttributeNode(cantidad);
                    Attr claveUnidad = doc.createAttribute("ClaveUnidad");
                    claveUnidad.setValue(endDetalle.getProductoId().getClaveUnidad());
                    mercancia.setAttributeNode(claveUnidad);
                    Attr unidad = doc.createAttribute("Unidad");
                    unidad.setValue(endDetalle.getProductoId().getUnidad());
                    mercancia.setAttributeNode(unidad);
                    Attr descripcion = doc.createAttribute("Descripcion");
                    descripcion.setValue(endDetalle.getProductoId().getDescripcion());
                    mercancia.setAttributeNode(descripcion);
                    Attr peso = doc.createAttribute("PesoEnKg");
                    peso.setValue(new DecimalFormat("0.000").format((endDetalle.getCantidad() * endDetalle.getProductoId().getPeso())));
                    mercancia.setAttributeNode(peso);
                    Attr materialP = doc.createAttribute("MaterialPeligroso");
                    if (endDetalle.getProductoId().getPeligroso() != null && endDetalle.getProductoId().getPeligroso().equals("0"))
                        materialP.setValue("No");
                    else{
                        materialP.setValue("Si");
                        mercancia.setAttributeNode(materialP);
                    }
                    if(destinos.size() > 1){
                        Element cantidadTrans = doc.createElement("cartaporte31:CantidadTransporta");
                        mercancia.appendChild(cantidadTrans);

                        Attr cantidadT = doc.createAttribute("Cantidad");
                        cantidadT.setValue(String.valueOf(endDetalle.getCantidad()));
                        cantidadTrans.setAttributeNode(cantidadT);
                        Attr idDestino = doc.createAttribute("IDDestino");
                        Attr idOrigen = doc.createAttribute("IDOrigen");
                        if (endDetalle.getImpuesto4().equals("1")){
                            idDestino.setValue(destinos.get(0).getDestino());
                            idOrigen.setValue(destinos.get(0).getOrigen());
                        }
                        if (endDetalle.getImpuesto4().equals("2")){
                            idDestino.setValue(destinos.get(1).getDestino());
                            idOrigen.setValue(destinos.get(1).getOrigen());
                        }
                        if (endDetalle.getImpuesto4().equals("3")){
                            idDestino.setValue(destinos.get(2).getDestino());
                            idOrigen.setValue(destinos.get(2).getOrigen());
                        }
                        cantidadTrans.setAttributeNode(idDestino);
                        cantidadTrans.setAttributeNode(idOrigen);
                        
                        
                    }
                }
                pesoTotal.setValue(new DecimalFormat("0.000").format((totalPeso)));
                mercancias.setAttributeNode(pesoTotal);
                numeroMercancias.setValue(String.valueOf(totalMerc));
                mercancias.setAttributeNode(numeroMercancias);
                /*pesoNeto.setValue(new DecimalFormat("000").format((totalPeso)));
                mercancias.setAttributeNode(pesoNeto);*/

                Element auto = doc.createElement("cartaporte31:Autotransporte");
                mercancias.appendChild(auto);

                Attr permiso = doc.createAttribute("PermSCT");
                permiso.setValue(cartaPorte.getUnidadId().getTipoPermisoId().getClave());
                auto.setAttributeNode(permiso);
                Attr numeroPer = doc.createAttribute("NumPermisoSCT");
                numeroPer.setValue(cartaPorte.getUnidadId().getNumeroPermiso());
                auto.setAttributeNode(numeroPer);

                Element identificacion = doc.createElement("cartaporte31:IdentificacionVehicular");
                auto.appendChild(identificacion);

                Attr configuracion = doc.createAttribute("ConfigVehicular");
                configuracion.setValue(cartaPorte.getUnidadId().getConfigVehiculoId().getClave());
                identificacion.setAttributeNode(configuracion);
                Attr pesoVehicular = doc.createAttribute("PesoBrutoVehicular");
                pesoVehicular.setValue(String.valueOf(cartaPorte.getUnidadId().getCapacidadCarga()));
                identificacion.setAttributeNode(pesoVehicular);
                Attr placa = doc.createAttribute("PlacaVM");
                placa.setValue(cartaPorte.getUnidadId().getPlacas());
                identificacion.setAttributeNode(placa);
                Attr modelo = doc.createAttribute("AnioModeloVM");
                modelo.setValue(String.valueOf(cartaPorte.getUnidadId().getAnio()));
                identificacion.setAttributeNode(modelo);

                Element seguros = doc.createElement("cartaporte31:Seguros");
                auto.appendChild(seguros);

                Attr aseguradora = doc.createAttribute("AseguraRespCivil");
                aseguradora.setValue(cartaPorte.getUnidadId().getSeguroId().getNombreAseguradora());
                seguros.setAttributeNode(aseguradora);
                Attr poliza = doc.createAttribute("PolizaRespCivil");
                poliza.setValue(cartaPorte.getUnidadId().getSeguroId().getPoliza());
                seguros.setAttributeNode(poliza);
System.out.println("remolques");
                if(cartaPorte.getUnidadId().getTipoRemolqueId() != null && !cartaPorte.getUnidadId().getPlacasRemolque1().isEmpty() && cartaPorte.getUnidadId().getPlacasRemolque1() != null){
                    Element remolques = doc.createElement("cartaporte31:Remolques");
                    auto.appendChild(remolques);

                    Element remolque = doc.createElement("cartaporte31:Remolque");
                    remolques.appendChild(remolque);

                    Attr subtipo = doc.createAttribute("SubTipoRem");
                    subtipo.setValue(cartaPorte.getUnidadId().getTipoRemolqueId().getClave());
                    remolque.setAttributeNode(subtipo);
                    Attr placaRem = doc.createAttribute("Placa");
                    placaRem.setValue(cartaPorte.getUnidadId().getPlacasRemolque1());
                    remolque.setAttributeNode(placaRem);
                }
                Element figura = doc.createElement("cartaporte31:FiguraTransporte");
                carta.appendChild(figura);

                Element tipos = doc.createElement("cartaporte31:TiposFigura");
                figura.appendChild(tipos);

                Attr tipoFigura = doc.createAttribute("TipoFigura");
                tipoFigura.setValue(cartaPorte.getConductorId().getClaveFigura());
                tipos.setAttributeNode(tipoFigura);
                Attr rfcFigura = doc.createAttribute("RFCFigura");
                rfcFigura.setValue(cartaPorte.getConductorId().getRfc());
                tipos.setAttributeNode(rfcFigura);
                Attr licencia = doc.createAttribute("NumLicencia");
                licencia.setValue(cartaPorte.getConductorId().getNumeroLicencia());
                tipos.setAttributeNode(licencia);
                Attr nombreFigura = doc.createAttribute("NombreFigura");
                nombreFigura.setValue(cartaPorte.getConductorId().getNombre() + " " + cartaPorte.getConductorId().getApellidoPaterno() + " " + cartaPorte.getConductorId().getApellidoMaterno());
                tipos.setAttributeNode(nombreFigura);

                if(cartaPorte.getConductorId().getClaveFigura().equals("02") && cartaPorte.getConductorId().getClaveFigura().equals("03")){
                    Element domicilioC = doc.createElement("cartaporte31:Domicilio");
                    tipos.appendChild(domicilioC);

                    Attr municipioC = doc.createAttribute("Municipio");
                    municipioC.setValue(cartaPorte.getDirConductorId().getClaveMunicipio());
                    domicilioC.setAttributeNode(municipioC);
                    Attr estadoC = doc.createAttribute("Estado");
                    estadoC.setValue(cartaPorte.getDirConductorId().getClaveEstado());
                    domicilioC.setAttributeNode(estadoC);
                    Attr paisC = doc.createAttribute("Pais");
                    paisC.setValue(cartaPorte.getDirConductorId().getClavePais());
                    domicilioC.setAttributeNode(paisC);
                    Attr codigoPostalC = doc.createAttribute("CodigoPostal");
                    codigoPostalC.setValue(String.valueOf(cartaPorte.getDirConductorId().getCodigoPostal()));
                    domicilioC.setAttributeNode(codigoPostalC);
                }
            }
            }
            if (confirmacion && tipoCartaPorte.equals("Traslado")){
                crearXMLCartaPorte();
            }
            
            System.out.println("Trasladados2");
            System.out.println("Probando");
            File xslt = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/cadenaoriginal_4_0_ccp_3_1.xslt"));
            /*if (confirmacion)
                xslt = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/cadenaoriginal_4_0.xslt"));
            else
                xslt = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/cadenaoriginal_cartaporte_3_1.xslt"));
            */
            StreamSource sourceXSL = new StreamSource(xslt);
            System.out.println("Probando2");

            DOMSource source = new DOMSource(doc);
            System.out.println("Probando3");
            tempFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/xmlTemp.xml"));
            //tempFile = new File("C:\\Users\\Developer1\\Desktop\\Nueva carpeta\\NuevoPrueba.xml");
            System.out.println("Probando3.1");
            //FileWriter writer = new FileWriter(tempFile);
            System.out.println("Probando3.2");
            StreamResult sourceXml = new StreamResult(tempFile);
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
            //crearSello(cadOrig);
            //crearCertificado();
            leerCfdi(tempFile);
            if (modificarXml(cadOrig, tempFile)) {
                valor = true;
            } else {
                valor = false;
            }//*/
            System.out.println("Probando4");
        } catch (Exception ex) {
            System.out.println("ErrorIngEgrem: " + ex.getMessage() + " | " + ex.getLocalizedMessage());
        }
        return valor;
    }
    
    public boolean crearXMLCartaPorte() {
        System.out.println("***** crearXML carta porte *****");
        byte[] xml = null;
        File tempFile = null;
        String cadOrig = "";
        int totalMerc = 0;
        double totalPeso = 0.0;
        boolean valor = false;
        double subtot = 0.0, subtott = 0.0;
        double ivatotal = 0.0, ivatot = 0.0;
                List<RnGcCfdisLineasTbl> itemsEncDetalle = new ArrayList<>();
                for(RnGcCfdisLineasTbl prod : listaprod1){
                    itemsEncDetalle.add(prod);
                }
                for(RnGcCfdisLineasTbl prod : listaprod2){
                    itemsEncDetalle.add(prod);
                }
                for(RnGcCfdisLineasTbl prod : listaprod3){
                    itemsEncDetalle.add(prod);
                }

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("cfdi:Comprobante");
            doc.appendChild(rootElement);
            Attr version = doc.createAttribute("Version");
            version.setValue("4.0");
            rootElement.setAttributeNode(version);
            if (cfdisId.getSerie() != null && !cfdisId.getSerie().isEmpty()) {
                Attr serie = doc.createAttribute("Serie");
                serie.setValue(cfdisId.getSerie());
                rootElement.setAttributeNode(serie);
            }
            if (cfdisId.getFolio() != null) {
                Attr folio = doc.createAttribute("Folio");
                folio.setValue(cfdisId.getFolio().toString());
                rootElement.setAttributeNode(folio);
            }
            Attr fecha = doc.createAttribute("Fecha");
            fecha.setValue(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(cfdisId.getFechaExpedicion()));
            rootElement.setAttributeNode(fecha);
            Attr sello = doc.createAttribute("Sello");
            sello.setValue("");
            rootElement.setAttributeNode(sello);
            Attr nCertificado = doc.createAttribute("NoCertificado");
            nCertificado.setValue(cfdisId.getCertificados_Id().getNumeroCertificado());
            rootElement.setAttributeNode(nCertificado);
            Attr certificado = doc.createAttribute("Certificado");
            certificado.setValue("");
            rootElement.setAttributeNode(certificado);
            Attr moneda = doc.createAttribute("Moneda");
            moneda.setValue("XXX");
            rootElement.setAttributeNode(moneda);
            Attr comprobante = doc.createAttribute("TipoDeComprobante");
            comprobante.setValue("T");
            rootElement.setAttributeNode(comprobante);
            Attr lugar = doc.createAttribute("LugarExpedicion");
            lugar.setValue(String.valueOf(cfdisId.getLugarExpedicion()));
            rootElement.setAttributeNode(lugar);
            Attr xmlcfdi = doc.createAttribute("xmlns:cfdi");
            xmlcfdi.setValue("http://www.sat.gob.mx/cfd/4");
            rootElement.setAttributeNode(xmlcfdi);
            Attr xmlxsi = doc.createAttribute("xmlns:xsi");
            xmlxsi.setValue("http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttributeNode(xmlxsi);
            Attr xsischema = doc.createAttribute("xsi:schemaLocation");
            xsischema.setValue("http://www.sat.gob.mx/cfd/4 http://www.sat.gob.mx/sitio_internet/cfd/4/cfdv40.xsd http://www.sat.gob.mx/CartaPorte31 http://www.sat.gob.mx/sitio_internet/cfd/CartaPorte/CartaPorte31.xsd");
            rootElement.setAttributeNode(xsischema);
            Attr cartaPortex = doc.createAttribute("xmlns:cartaporte31");
            cartaPortex.setValue("http://www.sat.gob.mx/CartaPorte31");
            rootElement.setAttributeNode(cartaPortex);
            Attr exportacion = doc.createAttribute("Exportacion");
            exportacion.setValue(cfdisId.getExportacionId().getClave());
            rootElement.setAttributeNode(exportacion);
            Attr subtotal = doc.createAttribute("SubTotal");
            subtotal.setValue("0");
            rootElement.setAttributeNode(subtotal);
            Attr total = doc.createAttribute("Total");
            total.setValue("0");
            rootElement.setAttributeNode(total);
            
            Element emisor = doc.createElement("cfdi:Emisor");
            rootElement.appendChild(emisor);
            // Atributos Emisor
            Attr rfcE = doc.createAttribute("Rfc");
            rfcE.setValue(cfdisId.getRfcEmisor());
            emisor.setAttributeNode(rfcE);
            Attr regimen = doc.createAttribute("RegimenFiscal");
            regimen.setValue(cfdisId.getClaveRegimenFiscal());
            emisor.setAttributeNode(regimen);
            Attr nombreE = doc.createAttribute("Nombre");
            nombreE.setValue(cfdisId.getNombreEmisor());
            emisor.setAttributeNode(nombreE);

            Element receptor = doc.createElement("cfdi:Receptor");
            rootElement.appendChild(receptor);
            // ATributos Receptor
            Attr rfcR = doc.createAttribute("Rfc");
            rfcR.setValue(cfdisId.getRfcEmisor());
            receptor.setAttributeNode(rfcR);
            Attr uso = doc.createAttribute("UsoCFDI");
            uso.setValue("S01");
            receptor.setAttributeNode(uso);
            Attr nombreR = doc.createAttribute("Nombre");
            nombreR.setValue(cfdisId.getNombreEmisor());
            receptor.setAttributeNode(nombreR);
            Attr regimenReceptor = doc.createAttribute("RegimenFiscalReceptor");
            regimenReceptor.setValue(cfdisId.getClaveRegimenFiscal());
            receptor.setAttributeNode(regimenReceptor);
            Attr domicilioReceptor = doc.createAttribute("DomicilioFiscalReceptor");
            domicilioReceptor.setValue(String.valueOf(cfdisId.getLugarExpedicion()));
            receptor.setAttributeNode(domicilioReceptor);

            Element conceptos = doc.createElement("cfdi:Conceptos");
            rootElement.appendChild(conceptos);
            for (RnGcCfdisLineasTbl endDetalle : itemsEncDetalle) {
                Element concepto = doc.createElement("cfdi:Concepto");
                conceptos.appendChild(concepto);

                Attr claveProd = doc.createAttribute("ClaveProdServ");
                claveProd.setValue(endDetalle.getClaveProdServ());
                concepto.setAttributeNode(claveProd);
                Attr noIdenti = doc.createAttribute("NoIdentificacion");
                noIdenti.setValue(endDetalle.getNoIdentificacion());
                concepto.setAttributeNode(noIdenti);
                Attr cantidad = doc.createAttribute("Cantidad");
                cantidad.setValue(String.valueOf(endDetalle.getCantidad()));
                concepto.setAttributeNode(cantidad);
                Attr claveUnidad = doc.createAttribute("ClaveUnidad");
                claveUnidad.setValue(endDetalle.getClaveUnidad());
                concepto.setAttributeNode(claveUnidad);
                Attr unidad = doc.createAttribute("Unidad");
                unidad.setValue(endDetalle.getUnidad());
                concepto.setAttributeNode(unidad);
                Attr descripcion = doc.createAttribute("Descripcion");
                descripcion.setValue("Traslado de " + endDetalle.getDescripcion());
                concepto.setAttributeNode(descripcion);
                Attr valorUnitario = doc.createAttribute("ValorUnitario");
                valorUnitario.setValue("0");
                concepto.setAttributeNode(valorUnitario);
                Attr importe = doc.createAttribute("Importe");
                importe.setValue("0");
                concepto.setAttributeNode(importe);
                Attr objetoImp = doc.createAttribute("ObjetoImp");
                objetoImp.setValue("01");
                concepto.setAttributeNode(objetoImp);
            }
            
            Element complemento = doc.createElement("cfdi:Complemento");
            rootElement.appendChild(complemento);
            
            Element carta = doc.createElement("cartaporte31:CartaPorte");
            complemento.appendChild(carta);
            
            String uuidR = UUID.randomUUID().toString();
            uuidR = uuidR.toUpperCase();
            uuidR = "CCC" + uuidR.substring(3);
            
            Attr versi = doc.createAttribute("Version");
            versi.setNodeValue("3.1");
            carta.setAttributeNode(versi);
            Attr IdCCP = doc.createAttribute("IdCCP");
            IdCCP.setNodeValue(uuidR);
            carta.setAttributeNode(IdCCP);
            Attr transpor = doc.createAttribute("TranspInternac");
            transpor.setNodeValue(cartaPorte.getTransporteInter());
            carta.setAttributeNode(transpor);
            Attr totalDist = doc.createAttribute("TotalDistRec");
            totalDist.setNodeValue(cartaPorte.getTotalDistancia());
            carta.setAttributeNode(totalDist);
            
            Element ubicaciones = doc.createElement("cartaporte31:Ubicaciones");
            carta.appendChild(ubicaciones);
            
            Element ubicacion = doc.createElement("cartaporte31:Ubicacion");
            ubicaciones.appendChild(ubicacion);
            
                Attr tipoUbicacion = doc.createAttribute("TipoUbicacion");
                tipoUbicacion.setValue("Origen");
                ubicacion.setAttributeNode(tipoUbicacion);
                System.out.println("destinos: " + destinos);
                if(destinos.size() > 1){
                    Attr idUbicacion = doc.createAttribute("IDUbicacion");
                    idUbicacion.setValue(destinos.get(0).getOrigen());
                    ubicacion.setAttributeNode(idUbicacion);
                }
                Attr rfcRemitente = doc.createAttribute("RFCRemitenteDestinatario");
                rfcRemitente.setValue(cartaPorte.getRfcRemitente());
                ubicacion.setAttributeNode(rfcRemitente);
                Attr nombreRemitente = doc.createAttribute("NombreRemitenteDestinatario");
                nombreRemitente.setValue(cartaPorte.getNombreRemitente());
                ubicacion.setAttributeNode(nombreRemitente);
                Attr fechaSalida = doc.createAttribute("FechaHoraSalidaLlegada");
                fechaSalida.setValue(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(cartaPorte.getFechaSalida()));
                ubicacion.setAttributeNode(fechaSalida);
System.out.println("FechaSalida(): " + cartaPorte.getFechaSalida());
                Element domicilioR = doc.createElement("cartaporte31:Domicilio");
                ubicacion.appendChild(domicilioR);

                Attr municipioR = doc.createAttribute("Municipio");
                municipioR.setValue(cartaPorte.getClaveMunicipio());
                domicilioR.setAttributeNode(municipioR);
                Attr estadoR = doc.createAttribute("Estado");
                estadoR.setValue(cartaPorte.getClaveEstado());
                domicilioR.setAttributeNode(estadoR);
                Attr paisR = doc.createAttribute("Pais");
                paisR.setValue(cartaPorte.getClavePais());
                domicilioR.setAttributeNode(paisR);
                Attr codigoPostalR = doc.createAttribute("CodigoPostal");
                codigoPostalR.setValue(String.valueOf(cartaPorte.getCodPostal()));
                domicilioR.setAttributeNode(codigoPostalR);
                Attr calleR = doc.createAttribute("Calle");
                calleR.setValue(cartaPorte.getCalle());
                domicilioR.setAttributeNode(calleR);
                Attr nExteriorR = doc.createAttribute("NumeroExterior");
                nExteriorR.setValue(cartaPorte.getNoExt());
                domicilioR.setAttributeNode(nExteriorR);
                Attr coloniaR = doc.createAttribute("Colonia");
                coloniaR.setValue(cartaPorte.getClaveColonia());
                domicilioR.setAttributeNode(coloniaR);

                for(RnGcCpOrigendestinoTbl od : destinos){
                    Element ubicacionD = doc.createElement("cartaporte31:Ubicacion");
                    ubicaciones.appendChild(ubicacionD);
                    if(destinos.size() > 1){
                        Attr idUbicacionD = doc.createAttribute("IDUbicacion");
                        idUbicacionD.setValue(od.getDestino());
                        ubicacionD.setAttributeNode(idUbicacionD);
                    }
                    Attr tipoUbicacionD = doc.createAttribute("TipoUbicacion");
                    tipoUbicacionD.setValue("Destino");
                    ubicacionD.setAttributeNode(tipoUbicacionD);
                    Attr rfcDestinatario = doc.createAttribute("RFCRemitenteDestinatario");
                    rfcDestinatario.setValue(od.getRfcDestinatario());
                    ubicacionD.setAttributeNode(rfcDestinatario);
                    Attr nombreDestinatario= doc.createAttribute("NombreRemitenteDestinatario");
                    nombreDestinatario.setValue(od.getNombreDestinatario());
                    ubicacionD.setAttributeNode(nombreDestinatario);
                    Attr fechaLlegada = doc.createAttribute("FechaHoraSalidaLlegada");
                    fechaLlegada.setValue(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(od.getFechaLlegada()));
                    ubicacionD.setAttributeNode(fechaLlegada);
                    Attr distancia = doc.createAttribute("DistanciaRecorrida");
                    distancia.setValue(String.valueOf(od.getDistancia()));
                    ubicacionD.setAttributeNode(distancia);

                    Element domicilioD = doc.createElement("cartaporte31:Domicilio");
                    ubicacionD.appendChild(domicilioD);

                    Attr municipioD = doc.createAttribute("Municipio");
                    municipioD.setValue(od.getClaveMunicipio());
                    domicilioD.setAttributeNode(municipioD);
                    Attr estadoD = doc.createAttribute("Estado");
                    estadoD.setValue(od.getClaveEstado());
                    domicilioD.setAttributeNode(estadoD);
                    Attr paisD = doc.createAttribute("Pais");
                    paisD.setValue(od.getClavePais());
                    domicilioD.setAttributeNode(paisD);
                    Attr codigoPostalD = doc.createAttribute("CodigoPostal");
                    codigoPostalD.setValue(String.valueOf(od.getCodPostal()));
                    domicilioD.setAttributeNode(codigoPostalD);
                    Attr calleD = doc.createAttribute("Calle");
                    calleD.setValue(od.getCalle());
                    domicilioD.setAttributeNode(calleD);
                    Attr nExteriorD = doc.createAttribute("NumeroExterior");
                    nExteriorD.setValue(od.getNoExt());
                    domicilioD.setAttributeNode(nExteriorD);
                    Attr coloniaD = doc.createAttribute("Colonia");
                    coloniaD.setValue(od.getClaveColonia());
                    domicilioD.setAttributeNode(coloniaD);
                }
            
            Element mercancias = doc.createElement("cartaporte31:Mercancias");
            carta.appendChild(mercancias);
            
                Attr pesoTotal = doc.createAttribute("PesoBrutoTotal");
                Attr unidadPeso = doc.createAttribute("UnidadPeso");
                unidadPeso.setValue("KGM");
                mercancias.setAttributeNode(unidadPeso);
                Attr numeroMercancias = doc.createAttribute("NumTotalMercancias");

                for (RnGcCfdisLineasTbl endDetalle : itemsEncDetalle) {
                    Element mercancia = doc.createElement("cartaporte31:Mercancia");
                    mercancias.appendChild(mercancia);
                    System.out.print("Cantidad: " + endDetalle.getCantidad() + "  Peso " + endDetalle.getProductoId().getPeso());
                    totalMerc++;
                    totalPeso += endDetalle.getCantidad() * endDetalle.getProductoId().getPeso();

                    Attr bienes = doc.createAttribute("BienesTransp");
                    bienes.setValue(endDetalle.getProductoId().getClaveProductServ());
                    mercancia.setAttributeNode(bienes);
                    Attr cantidad = doc.createAttribute("Cantidad");
                    cantidad.setValue(String.valueOf(endDetalle.getCantidad()));
                    mercancia.setAttributeNode(cantidad);
                    Attr claveUnidad = doc.createAttribute("ClaveUnidad");
                    claveUnidad.setValue(endDetalle.getProductoId().getClaveUnidad());
                    mercancia.setAttributeNode(claveUnidad);
                    Attr unidad = doc.createAttribute("Unidad");
                    unidad.setValue(endDetalle.getProductoId().getUnidad());
                    mercancia.setAttributeNode(unidad);
                    Attr descripcion = doc.createAttribute("Descripcion");
                    descripcion.setValue(endDetalle.getProductoId().getDescripcion());
                    mercancia.setAttributeNode(descripcion);
                    Attr peso = doc.createAttribute("PesoEnKg");
                    peso.setValue(new DecimalFormat("0.000").format((endDetalle.getCantidad() * endDetalle.getProductoId().getPeso())));
                    mercancia.setAttributeNode(peso);
                    Attr materialP = doc.createAttribute("MaterialPeligroso");
                    if (endDetalle.getProductoId().getPeligroso() != null && endDetalle.getProductoId().getPeligroso().equals("0"))
                        materialP.setValue("No");
                    else{
                        materialP.setValue("Si");
                        mercancia.setAttributeNode(materialP);
                    }
                    if(destinos.size() > 1){
                        Element cantidadTrans = doc.createElement("cartaporte31:CantidadTransporta");
                        mercancia.appendChild(cantidadTrans);

                        Attr cantidadT = doc.createAttribute("Cantidad");
                        cantidadT.setValue(String.valueOf(endDetalle.getCantidad()));
                        cantidadTrans.setAttributeNode(cantidadT);
                        Attr idDestino = doc.createAttribute("IDDestino");
                        Attr idOrigen = doc.createAttribute("IDOrigen");
                        if (endDetalle.getImpuesto4().equals("1")){
                            idDestino.setValue(destinos.get(0).getDestino());
                            idOrigen.setValue(destinos.get(0).getOrigen());
                        }
                        if (endDetalle.getImpuesto4().equals("2")){
                            idDestino.setValue(destinos.get(1).getDestino());
                            idOrigen.setValue(destinos.get(1).getOrigen());
                        }
                        if (endDetalle.getImpuesto4().equals("3")){
                            idDestino.setValue(destinos.get(2).getDestino());
                            idOrigen.setValue(destinos.get(2).getOrigen());
                        }
                        cantidadTrans.setAttributeNode(idDestino);
                        cantidadTrans.setAttributeNode(idOrigen);
                        
                        
                    }
                }
                pesoTotal.setValue(new DecimalFormat("0.000").format((totalPeso)));
                mercancias.setAttributeNode(pesoTotal);
                numeroMercancias.setValue(String.valueOf(totalMerc));
                mercancias.setAttributeNode(numeroMercancias);

                Element auto = doc.createElement("cartaporte31:Autotransporte");
                mercancias.appendChild(auto);

                Attr permiso = doc.createAttribute("PermSCT");
                permiso.setValue(cartaPorte.getUnidadId().getTipoPermisoId().getClave());
                auto.setAttributeNode(permiso);
                Attr numeroPer = doc.createAttribute("NumPermisoSCT");
                numeroPer.setValue(cartaPorte.getUnidadId().getNumeroPermiso());
                auto.setAttributeNode(numeroPer);

                Element identificacion = doc.createElement("cartaporte31:IdentificacionVehicular");
                auto.appendChild(identificacion);

                Attr configuracion = doc.createAttribute("ConfigVehicular");
                configuracion.setValue(cartaPorte.getUnidadId().getConfigVehiculoId().getClave());
                identificacion.setAttributeNode(configuracion);
                Attr pesoVehicular = doc.createAttribute("PesoBrutoVehicular");
                pesoVehicular.setValue(String.valueOf(cartaPorte.getUnidadId().getCapacidadCarga()));
                identificacion.setAttributeNode(pesoVehicular);
                Attr placa = doc.createAttribute("PlacaVM");
                placa.setValue(cartaPorte.getUnidadId().getPlacas());
                identificacion.setAttributeNode(placa);
                Attr modelo = doc.createAttribute("AnioModeloVM");
                modelo.setValue(String.valueOf(cartaPorte.getUnidadId().getAnio()));
                identificacion.setAttributeNode(modelo);

                Element seguros = doc.createElement("cartaporte31:Seguros");
                auto.appendChild(seguros);

                Attr aseguradora = doc.createAttribute("AseguraRespCivil");
                aseguradora.setValue(cartaPorte.getUnidadId().getSeguroId().getNombreAseguradora());
                seguros.setAttributeNode(aseguradora);
                Attr poliza = doc.createAttribute("PolizaRespCivil");
                poliza.setValue(cartaPorte.getUnidadId().getSeguroId().getPoliza());
                seguros.setAttributeNode(poliza);
System.out.println("remolques");
                if(cartaPorte.getUnidadId().getTipoRemolqueId() != null && !cartaPorte.getUnidadId().getPlacasRemolque1().isEmpty() && cartaPorte.getUnidadId().getPlacasRemolque1() != null){
                    Element remolques = doc.createElement("cartaporte31:Remolques");
                    auto.appendChild(remolques);

                    Element remolque = doc.createElement("cartaporte31:Remolque");
                    remolques.appendChild(remolque);

                    Attr subtipo = doc.createAttribute("SubTipoRem");
                    subtipo.setValue(cartaPorte.getUnidadId().getTipoRemolqueId().getClave());
                    remolque.setAttributeNode(subtipo);
                    Attr placaRem = doc.createAttribute("Placa");
                    placaRem.setValue(cartaPorte.getUnidadId().getPlacasRemolque1());
                    remolque.setAttributeNode(placaRem);
                }
                Element figura = doc.createElement("cartaporte31:FiguraTransporte");
                carta.appendChild(figura);

                Element tipos = doc.createElement("cartaporte31:TiposFigura");
                figura.appendChild(tipos);

                Attr tipoFigura = doc.createAttribute("TipoFigura");
                tipoFigura.setValue(cartaPorte.getConductorId().getClaveFigura());
                tipos.setAttributeNode(tipoFigura);
                Attr rfcFigura = doc.createAttribute("RFCFigura");
                rfcFigura.setValue(cartaPorte.getConductorId().getRfc());
                tipos.setAttributeNode(rfcFigura);
                Attr licencia = doc.createAttribute("NumLicencia");
                licencia.setValue(cartaPorte.getConductorId().getNumeroLicencia());
                tipos.setAttributeNode(licencia);
                Attr nombreFigura = doc.createAttribute("NombreFigura");
                nombreFigura.setValue(cartaPorte.getConductorId().getNombre() + " " + cartaPorte.getConductorId().getApellidoPaterno() + " " + cartaPorte.getConductorId().getApellidoMaterno());
                tipos.setAttributeNode(nombreFigura);

                if(cartaPorte.getConductorId().getClaveFigura().equals("02") && cartaPorte.getConductorId().getClaveFigura().equals("03")){
                    Element domicilioC = doc.createElement("cartaporte31:Domicilio");
                    tipos.appendChild(domicilioC);

                    Attr municipioC = doc.createAttribute("Municipio");
                    municipioC.setValue(cartaPorte.getDirConductorId().getClaveMunicipio());
                    domicilioC.setAttributeNode(municipioC);
                    Attr estadoC = doc.createAttribute("Estado");
                    estadoC.setValue(cartaPorte.getDirConductorId().getClaveEstado());
                    domicilioC.setAttributeNode(estadoC);
                    Attr paisC = doc.createAttribute("Pais");
                    paisC.setValue(cartaPorte.getDirConductorId().getClavePais());
                    domicilioC.setAttributeNode(paisC);
                    Attr codigoPostalC = doc.createAttribute("CodigoPostal");
                    codigoPostalC.setValue(String.valueOf(cartaPorte.getDirConductorId().getCodigoPostal()));
                    domicilioC.setAttributeNode(codigoPostalC);
                }

            cfdisId.setSubtotal(subtot);
            cfdisId.setIvaTotal(ivatotal);
            cfdisId.setImporte(subtot + ivatotal);

            File xslt = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/cadenaoriginal_4_0_ccp_3_1.xslt"));
            tempFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/archivos/xmlTemp.xml"));

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

            cadOrig = bos.toString();
            System.out.println("cadOrig: " + cadOrig);

            leerCfdi(tempFile);

            valor = modificarXml(cadOrig, tempFile);
            
            return valor;

        } catch (Exception ex) {
            System.out.println("Error crearXML cartaPorte: " + ex.getLocalizedMessage());
            ex.printStackTrace();

            return valor;
        }
    }
    
    public boolean timbraPorte(String nombre, String cadenaOriginal, RnGcDireccionesUsuariosTbl direccRemitente) {
        System.out.println("nombre: " + nombre + " | cadenaOriginal: " + cadenaOriginal);
        boolean valorTimbra = false;
        try {
            FileInputStream fis = new FileInputStream(new java.io.File(nombre));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int cuantos = 0;
            byte[] bytes = new byte[10000];
            File xmltimbrado = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/archivos/xmlTimbrado.xml"));
            while ((cuantos = fis.read(bytes, 0, bytes.length)) >= 0) {
                baos.write(bytes, 0, cuantos);
            }
            System.out.println("timbra: AFC060520V16 " + " | AFC060520V16 " + " | https://www.sefactura.com.mx" ); //Producción
           //System.out.println("timbra: VICA840114RZ41 " + " | VICA840114RZ41 " + " | http://www.jonima.com.mx:3014" ); //Pruebas Emmanuel
            bytes = baos.toByteArray();
            baos.close();
            String xml = new String(bytes, "UTF-8");
            //Sefactura sf = new Sefactura("http://pruebas.sefactura.com.mx:3014", "VICA840114RZ41", "VICA840114RZ41"); //Pruebas
            Sefactura sf = new Sefactura("https://www.sefactura.com.mx", "AFC060520V16", "AFC060520V16"); //Produccion
            RespuestaTimbrado rt = sf.timbrado(xml);
            System.out.println("xmltimbrado: " + rt.getXml());
            System.out.println("resultado: " + rt.getResultado());
            if (rt.getResultado() != null && rt.getResultado().length() > 0) {
                System.out.println("Error al generar timbrado: " + rt.getResultado());
                JsfUtil.addErrorMessage("Error " + rt.getResultado());
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
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();//obtener datos de XML Timbrado para  PDF
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
                cfdisId.setEstatus("Timbrado");
                cfdisId.setRespuestaTimbrado("Timbrado de forma correcta");
                System.out.println(noCertSAT + " | " + fechaTimbrado + " | " + Uuid + " | " + selloCFDI + " | " + selloSAT + " | " + rfcProvCertif);
                //if (cfdisId.getTipoComprobante().equals("E") || cfdisId.getTipoComprobante().equals("I")) {
                    crearPDFCartaPorte(selloSAT, noCertSAT, fechaTimbrado, Uuid, selloCFDI, codQR, cadenaOriginal, rfcProvCertif);
                //} 
                System.out.println("ProbandoT6");
                crearArchivo(xmltimbrado);
                valorTimbra = true;
                System.out.println("Ya termine");
            }
            return valorTimbra;
        } catch (Exception ex) {
            System.out.println("Error timbra: " + ex.getLocalizedMessage());
            ex.printStackTrace();
            return valorTimbra;
        }
    }
    
    public void crearPDFCartaPorte(String selloSAT, String noCertSAT, String fechaTimbrado, String Uuid, String selloCFDI, byte[] codigoQR, String cadenaOrig, String rfcProvCertif) throws JRException, IOException, ParseException {
        System.out.println("Creacion de PDF carta porte");
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
        parametros.put("RFC_Receptor", cfdisId.getRfcReceptor());
        parametros.put("Nombre_Receptor", cfdisId.getNombreReceptor());
        parametros.put("Uso_CFDI", cfdisId.getUsoCfdi());
        parametros.put("Folio_Fiscal", cfdisId.getFolio());
        parametros.put("NoSerie_CSD", cfdisId.getSerie());
        parametros.put("CodigoPostal", cfdisId.getLugarExpedicion());
        parametros.put("FechaHora_Emision", new SimpleDateFormat("YYYY-MM-dd'T'hh:mm:ss").format(cfdisId.getFechaExpedicion()));
        parametros.put("tipoDocumento", "(T) Traslado");

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
        parametros.put("RegimenFiscalReceptor", personas.getRegimenFiscalId().getClaveRegimenFiscal() + " - " + personas.getRegimenFiscalId().getDescripcion());
        parametros.put("CodigoPostalReceptor", ""+personas.getcodigoPostal());
        if (cfdisId.getTexto()!= null && !cfdisId.getTexto().isEmpty()) {
            parametros.put("texto", cfdisId.getTexto());
        }
        parametros.put("listaDetalle_1", cfdisLineas);
        parametros.put("listaDetalle", listaprod1);
        //parametros.put("Subtotal", Double.parseDouble(calcularSubtotal()));
        //parametros.put("Total", calcularTotal());
        //parametros.put("totalDescuento", Double.parseDouble(new DecimalFormat("0.00").format(Double.parseDouble(calcularDescuento()))));
        parametros.put("QR", imagenqr);

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
        parametros.put("rfcProvCertif", rfcProvCertif);
        
        
        parametros.put("direccionSucursal", direccRemitente.getNombreCalle() + ", No." + direccRemitente.getNoExt() + ", "
                            + direccRemitente.getColonia() + ", " + direccRemitente.getMunicipio() + ", "
                            + direccRemitente.getEstado());
                    parametros.put("direccionCliente", cfdisId.getPersonaId().getDomicilio()+ ", No." + cfdisId.getPersonaId().getNoExt() + ", "
                            + cfdisId.getPersonaId().getLocalidad()+ ", " + cfdisId.getPersonaId().getCiudad() + ", "
                            + cfdisId.getPersonaId().getEstado());

                    parametros.put("distancia", origenDestino.getDistancia());
                    parametros.put("idOrigen", origenDestino.getOrigen());
                    parametros.put("idDestino", origenDestino.getDestino());
                    parametros.put("rfcRemitente", cfdisId.getRfcEmisor());
                    parametros.put("nombreRemitente", cfdisId.getNombreEmisor());
                    parametros.put("rfcDestinatario", cartaPorte.getClienteProveedorId().getRfc());
                    parametros.put("nombreDestinatario", cartaPorte.getClienteProveedorId().getNombre());
                    parametros.put("fechaSalida", origenDestino.getFechaSalida());
                    parametros.put("fechaLlegada", origenDestino.getFechaLlegada());

                    parametros.put("domicilioRemitente", direccRemitente.getNombreCalle() + ", No." + direccRemitente.getNoExt() + ", "
                            + direccRemitente.getClaveColonia() + " - " + direccRemitente.getColonia() + ", " + direccRemitente.getClaveMunicipio() + " - "
                            + direccRemitente.getMunicipio() + ", " + direccRemitente.getClaveEstado() + " - " + direccRemitente.getEstado() + ", "
                            + direccRemitente.getPais() + ". C. P. : " + direccRemitente.getCodigoPostal());
                    parametros.put("domicilioDestinatario", origenDestino.getDirClienteProveedorId().getNombreCalle() + ", No." + origenDestino.getDirClienteProveedorId().getNumeroExterior() + ", "
                            + origenDestino.getDirClienteProveedorId().getClaveColonia() + " - " + origenDestino.getDirClienteProveedorId().getColonia() + ", " + origenDestino.getDirClienteProveedorId().getClaveMunicipio() + " - "
                            + origenDestino.getDirClienteProveedorId().getMunicipio() + ", " + origenDestino.getDirClienteProveedorId().getClaveEstado() + " - " + origenDestino.getDirClienteProveedorId().getEstado() + ", "
                            + origenDestino.getDirClienteProveedorId().getPais() + ". C. P. : " + origenDestino.getDirClienteProveedorId().getCodigoPostal());

                    if(cartaPorte.getConductorId().getClaveFigura() != null && !cartaPorte.getConductorId().getClaveFigura().isEmpty()){
                        if(cartaPorte.getConductorId().getClaveFigura().equals("01")){
                            parametros.put("figura", "Operador");
                        }else if(cartaPorte.getConductorId().getClaveFigura().equals("02")){
                            parametros.put("figura", "Propietario");
                        }else if(cartaPorte.getConductorId().getClaveFigura().equals("03")){
                            parametros.put("figura", "Arrendador");
                        }else if(cartaPorte.getConductorId().getClaveFigura().equals("04")){
                            parametros.put("figura", "Notificado");
                        }
                        
                        List<RnGcCpUnidadesParteTransporteTbl> unidadTrans = parteTransporte.obtenerXunidad(cartaPorte.getUnidadId());
                        
                        if(cartaPorte.getConductorId().getClaveFigura().equals("02") || cartaPorte.getConductorId().getClaveFigura().equals("03")){
                            String palabra = "";
                            for(RnGcCpUnidadesParteTransporteTbl unidad : unidadTrans){
                                palabra = palabra + " " + unidad.getParteTransId().getDescripcion() + ",";
                            }
                            parametros.put("partesTrans", palabra);
                        }else{
                            parametros.put("partesTrans", "");
                        }
                    }
                    parametros.put("rfcOperador", cartaPorte.getConductorId().getRfc());
                    parametros.put("nombreOperador", cartaPorte.getConductorId().getNombre() + " " + cartaPorte.getConductorId().getApellidoPaterno() + " " + cartaPorte.getConductorId().getApellidoMaterno());
                    parametros.put("licencia", cartaPorte.getConductorId().getNumeroLicencia());
                    parametros.put("residenciaOperador", cartaPorte.getDirConductorId().getNombreCalle()+ ", No." + cartaPorte.getDirConductorId().getNumeroExterior()+ ", "
                            + cartaPorte.getDirConductorId().getClaveColonia() + " - " + cartaPorte.getDirConductorId().getColonia() + ", " + cartaPorte.getDirConductorId().getClaveMunicipio() + " - "
                            + cartaPorte.getDirConductorId().getMunicipio() + ", " + cartaPorte.getDirConductorId().getClaveEstado() + " - " + cartaPorte.getDirConductorId().getEstado() + ", "
                            + cartaPorte.getDirConductorId().getPais() + ". C. P. : " + cartaPorte.getDirConductorId().getCodigoPostal());
                    
                    parametros.put("permiso", cartaPorte.getUnidadId().getTipoPermisoId().getClave());
                    parametros.put("nPermiso", cartaPorte.getUnidadId().getNumeroPermiso());
                    parametros.put("aseguradora", cartaPorte.getUnidadId().getSeguroId().getNombreAseguradora());
                    parametros.put("poliza", cartaPorte.getUnidadId().getSeguroId().getPoliza());
                    parametros.put("prima", String.valueOf(cartaPorte.getUnidadId().getSeguroId().getPrimaSeguro()));
                    parametros.put("configuracion", cartaPorte.getUnidadId().getConfigVehiculoId().getClave());
                    parametros.put("placa", cartaPorte.getUnidadId().getPlacas());
                    parametros.put("modelo", String.valueOf(cartaPorte.getUnidadId().getAnio()));
                    
                    int totalMerc = 0;
                    double totalPeso = 0.0;
                    for (RnGcCfdisLineasTbl endDetalle : cfdisLineas) {
                        totalMerc++;
                        totalPeso += endDetalle.getProductoId().getPeso();
                    }
                    parametros.put("totalMerc", String.valueOf(totalMerc));
                    parametros.put("pesoNeto", String.valueOf(totalPeso));
                    parametros.put("unidad", "KGM");
        

        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Reports/formatoCartaPorte.jasper"));
                    String rutaAbsoluta = jasper.getAbsolutePath();
                    String rutaFinal = "/";
                    String[] partesRuta = rutaAbsoluta.split("\\\\"); //en windows (desarrollo)
                    //String[] partesRuta = rutaAbsoluta.split("/"); // en linux (produccion)
                    for(int i = 0; i < partesRuta.length; i++){
                        if("formatoCartaPorte.jasper".equals(partesRuta[i]))
                            partesRuta[i] = "";
                        if(!"".equals(partesRuta[i]))
                            rutaFinal = rutaFinal + partesRuta[i]  + "/";
                    }
                    System.out.println("getPath: " + jasper.getAbsolutePath());
                    System.out.println("rutaFinal: " + rutaFinal);
                    parametros.put("SUBREPORT_DIR", rutaFinal);
                    
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JREmptyDataSource());
        byte[] facturaPDF = JasperExportManager.exportReportToPdf(jasperPrint);
        archivo.setArchivoPdf(facturaPDF);
        //Imprime PDF
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zout = new ZipOutputStream(bos);

        ZipEntry zip = new ZipEntry("CFDI-" + cfdisId.getSerie() + "-" + cfdisId.getFolio() + "-" + cfdisId.getNombreReceptor() + ".pdf");
        zout.putNextEntry(zip);
        zout.write(facturaPDF);
        zout.closeEntry();

        ZipEntry zip2 = new ZipEntry("CFDI-" + cfdisId.getSerie() + "-" + cfdisId.getFolio() + "-" + cfdisId.getNombreReceptor() + ".xml");
        byte[] xml = archivo.getArchivoXml();
        zout.putNextEntry(zip2);
        zout.write(xml);
        zout.closeEntry();

        zout.finish();
        zout.flush();
        zout.close();

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        InputStream streamPdf = bis;
        downLoadFile3 = new DefaultStreamedContent(streamPdf, "application/zip", "CFDI-" + cfdisId.getSerie() + "-" + cfdisId.getFolio() + "-" + cfdisId.getNombreReceptor() + ".zip");
        System.out.println("PDF carta porte guardado ");
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
        baseT16 = 0.0;
        for (int i = 0; i < cfdisLineas.size(); i++) {
            baseT16 += cfdisLineas.get(i).getBase();
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
        baseT0 = 0.0;
        Double iva0 = 0.0;
        boolean valor = false;
        for (int i = 0; i < cfdisLineas.size(); i++) {
            baseT0 += cfdisLineas.get(i).getBase();
            if (cfdisLineas.get(i).getTipoTasa() != null && cfdisLineas.get(i).getTipoTasa() == 0.0) {
                if (cfdisLineas.get(i).getTipoImpuesto() != null && cfdisLineas.get(i).getTipoImpuesto().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto() != null && cfdisLineas.get(i).getImpuesto().equals("002")
                        && cfdisLineas.get(i).getTipoFactor() != null && cfdisLineas.get(i).getTipoFactor().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa() != null && cfdisLineas.get(i).getTipoTasa().equals(0.0)) {
                    iva0 += cfdisLineas.get(i).getImporteimpuesto();
                    valor = true;
                }
            }
            if (cfdisLineas.get(i).getTipoTasa2() != null && cfdisLineas.get(i).getTipoTasa2() == 0.0) {
                if (cfdisLineas.get(i).getTipoImpuesto2() != null && cfdisLineas.get(i).getTipoImpuesto2().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto2() != null && cfdisLineas.get(i).getImpuesto2().equals("002")
                        && cfdisLineas.get(i).getTipoFactor2() != null && cfdisLineas.get(i).getTipoFactor2().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa2() != null && cfdisLineas.get(i).getTipoTasa2().equals(0.0)) {
                    iva0 += cfdisLineas.get(i).getImporteImpuesto2();
                    valor = true;
                }
            } 
            if (cfdisLineas.get(i).getTipoTasa3() != null && cfdisLineas.get(i).getTipoTasa3() == 0.0) {
                if (cfdisLineas.get(i).getTipoImpuesto3() != null && cfdisLineas.get(i).getTipoImpuesto3().equals("Traslado")
                        && cfdisLineas.get(i).getImpuesto3() != null && cfdisLineas.get(i).getImpuesto3().equals("002")
                        && cfdisLineas.get(i).getTipoFactor3() != null && cfdisLineas.get(i).getTipoFactor3().equals("Tasa")
                        && cfdisLineas.get(i).getTipoTasa3() != null && cfdisLineas.get(i).getTipoTasa3().equals(0.0)) {
                    iva0 += cfdisLineas.get(i).getImporteImpuesto3();
                    valor = true;
                }
            }
            if (cfdisLineas.get(i).getTipoTasa4() != null && cfdisLineas.get(i).getTipoTasa4() == 0.0) {
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
        baseT8 = 0.0;
        for (int i = 0; i < cfdisLineas.size(); i++) {
            baseT8 += cfdisLineas.get(i).getBase();
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
        if(cfdisId.getPersonaId() != null && cfdisId.getClaveRegimenFiscal() != null && cfdisId.getTipoComprobante() != null
                && cfdisId.getUsoCfdi() != null && cfdisId.getExportacionId() != null && cfdisId.getCertificados_Id() != null
                && cfdisId.getMoneda() != null && cfdisId.getMetodoPago() != null && cfdisId.getFormaPago() != null
                && cfdisId.getCondicionPago() != null && !cfdisId.getCondicionPago().isEmpty()){
            if(cfdisId.getRfcReceptor() == null || cfdisId.getRfcReceptor().isEmpty()){
                cfdisId.setRfcReceptor(cfdisId.getPersonaId().getRfc());
                cfdisId.setNombreReceptor(cfdisId.getPersonaId().getNombre().toUpperCase());
            }
            if(cfdisId.getImporte() == null){
                cfdisId.setImporte(0.0);
                cfdisId.setImporteLetra("-");
                cfdisId.setSaldoPagado(0.0);
                cfdisId.setSaldoInsoluto(0.0);
                cfdisId.setSaldoPagar(0.0);
                cfdisId.setCantidadPagada(0.0);
            }
            
            cfdisId.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
            cfdisId.setFechaCreacion(new Date());
            cfdisId.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
            cfdisId.setUltimaFechaActualizacion(new Date());
            cfdisId.setEstatus("Guardado");
            if(cfdisId.getId() == null)
                cfdisId = cfdisFacade.refreshFromDB(cfdisId);
            else
                cfdisFacade.edit(cfdisId);
            for (int i = 0; i < cfdisLineas.size(); i++) {
                cfdisLineas.get(i).setCfdisId(cfdisId);
                cfdisLineas.get(i).setFechaCreacion(new Date());
                cfdisLineas.get(i).setUltimaFechaActualizacion(new Date());
                lineasCfdisFacade.edit(cfdisLineas.get(i));
            }
            System.out.println("cfdi guardado: " + cfdisId + " de: " + cfdisId.getNombreReceptor());;
            JsfUtil.addSuccessMessage("CFDI guardado correctamente");
            personas = null;
            cfdisId = null;
            cfdisLineas = null;
            itemsFactura = null;
            listaCfdisRelacionados = null;
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
        System.out.println("StrEm: " + new String(array));
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

    public String parseDate(Date fecha1, String formato) {
        return new SimpleDateFormat(formato).format(fecha1);
    }

    public void listaPPDS() {
        cfdiRelacionado.setCantidadPagada(cfdiRelacionado.getSaldoPagar());
        cfdiRelacionado.setSaldoPagar(0.0);
        cfdiRelacionado = new RnGcCfdisTbl();
        System.out.println("inicializarSaldo: " + cfdiRelacionado.getSaldoPagar());
    }

    public boolean filterByFecha(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.isEmpty()) {
            return true;
        }
        if (value == null) {
            return false;
        }

        System.out.println("value: " + value);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Date filterDate = (Date) value;
        Date dateFrom;
        Date dateTo;

        try {
            String fromPart = filterText.substring(0, filterText.indexOf("-"));
            String toPart = filterText.substring(filterText.indexOf("-") + 1);
            dateFrom = fromPart.isEmpty() ? null : df.parse(fromPart);
            dateTo = toPart.isEmpty() ? null : df.parse(toPart);
        } catch (ParseException ex) {
            Logger.getLogger(RnGcCfdisTblController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return (dateFrom == null || filterDate.after(dateFrom)) && (dateTo == null || filterDate.before(dateTo));
    }

    public double calcularImportePagado() {
        Double total = 0.0;
        DecimalFormat format = new DecimalFormat("#.##");
        if (listaCfdis != null) {
            for (RnGcCfdisTbl cfdi : listaCfdis) {
                if (cfdi.getSaldoPagado() != null) {
                    total = total + cfdi.getSaldoPagado();
                }
            }
        }
        return Double.valueOf(format.format(total));
    }

    public double calcularSaldoAnterior() {
        Double total = 0.0;
        DecimalFormat format = new DecimalFormat("#.##");
        if (listaCfdis != null) {
            for (RnGcCfdisTbl cfdi : listaCfdis) {
                if (cfdi.getSaldoPagado() != null) {
                    total = total + cfdi.getCantidadPagada() + cfdi.getSaldoInsoluto();
                }
            }
        }
        return Double.valueOf(format.format(total));
    }

    public double calcularSaldoAPagar() {
        Double total = 0.0;
        DecimalFormat format = new DecimalFormat("#.##");
        if (listaCfdis != null) {
            for (RnGcCfdisTbl cfdi : listaCfdis) {
                if (cfdi.getSaldoPagado() != null) {
                    total = total + cfdi.getCantidadPagada();
                }
            }
        }
        return Double.valueOf(format.format(total));
    }

    public double calcularSaldoInsoluto2() {
        Double total = 0.0;
        DecimalFormat format = new DecimalFormat("#.##");
        if (listaCfdis != null) {
            for (RnGcCfdisTbl cfdi : listaCfdis) {
                if (cfdi.getSaldoPagado() != null) {
                    total = total + cfdi.getSaldoInsoluto();
                }
            }
        }
        return Double.valueOf(format.format(total));
    }

    public RnGcPersonasTbl buscarPersona(RnGcCfdisTbl cfdi) {
        System.out.println("cfdi: " + cfdi);
        if (cfdi != null) {
            this.personas = personasFacade.obtenerporRfcCreadoPor(cfdi.getRfcReceptor(), usuarioFirmado.obtenerIdUsuario());
        } else {
            JsfUtil.addErrorMessage("No se ha escogido un CFDI");
        }
        System.out.println("buscarPersona: " + this.personas);
        return this.personas;
    }

    public RnGcFormaspagosTbl obtenerFormPago(String formaPago) {
        System.out.println("formaPago: " + cfdisId.getFormaPagoP() + " | " + formaPago);
        this.formaPago = formaPagoFacade.obtenerFormaPagoXClave(formaPago);
        System.out.println("formaPago2: " + this.formaPago);
        return this.formaPago;
    }

    public void limpiarCampos() {
        System.out.println("datos: " + personas + " | " + cfdisId + " | " + cfdisLineas + " | " + itemsFactura + " | " + listaCfdis + " | " + formaPago);
        personas = new RnGcPersonasTbl();
        cfdisId = new RnGcCfdisTbl();
        cfdisLineas = new ArrayList<>();
        itemsFactura = new ArrayList<>();
        listaCfdis = new ArrayList<>();
        formaPago = new RnGcFormaspagosTbl();
    }

    public void eliminarRelacionadoComplemento() {
        System.out.println("eliminarRelacionadoComplemento: " + deleteSeleccionado);
        for (RnGcCfdisTbl cfdi : deleteSeleccionado) {
            listaCfdis.remove(cfdi);
        }
        deleteSeleccionado = new ArrayList<>();
        System.out.println("eliminarRelacionadoComplemento: " + deleteSeleccionado);
    }

    public void datosComplemento(){
        cfdisId.setFechaPago(new Date());
        cfdisId.setMonedaP("MXN");
        System.out.println("fecha: " + cfdisId.getFechaPago());
    }
    
    public void buscarFacturasXCliente() {
        if (personas2 != null) {
            RnGcUsuariosTbl user = usuariosFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            listaCfdisRelacionados = cfdisFacade.obtenerXRFCReceptorGuardado(personas2.getRfc(),user.getId());
        } 
        personas2 = null;
    }
    
    public void agregarCFDI(){
        cfdisId = cfdiRelacionado;
        cfdisId.setFolio(obtenerFolioPorUsuarioSerieYCertificado(cfdisId.getSerie(), cfdisId.getCertificados_Id()).get(0).getFolio());
        cfdisId.setFechaExpedicion(new Date());
        System.out.println("CertificadoId: "+cfdisId.getCertificados_Id().getId());
        personas = cfdisId.getPersonaId();
        cfdisLineas = lineasCfdisFacade.obtenerCfdisLineas(cfdisId);
        elegirFormaPago();
        cfdiRelacionado = null;
        listaCfdisRelacionados = null;
    }
    
    public void buscarFacturasXClientePlantilla() {
        if (personas2 != null) {
            RnGcUsuariosTbl user = usuariosFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            listaCfdisRelacionados = cfdisFacade.obtenerXRFCReceptorPlantilla(personas2.getRfc(),user.getId());
        } 
        personas2 = null;
    }
    
    public void agregarCFDIPlantilla(){
        System.out.println("Entro a agregar Plantilla: " );
        System.out.println("CFDI RELASIONADO: " + cfdiRelacionado.getId()+" || "+ cfdiRelacionado.getImporteLetra());
        cfdisId = new RnGcCfdisTbl();
        cfdisId.setNombreEmisor(cfdiRelacionado.getNombreEmisor());
        cfdisId.setRfcEmisor(cfdiRelacionado.getRfcEmisor());
        cfdisId.setCertificados_Id(cfdiRelacionado.getCertificados_Id());
        cfdisId.setClaveRegimenFiscal(cfdiRelacionado.getClaveRegimenFiscal());
        cfdisId.setCondicionPago(cfdiRelacionado.getCondicionPago());
        cfdisId.setCreadoPor(cfdiRelacionado.getCreadoPor());
        cfdisId.setExportacionId(cfdiRelacionado.getExportacionId());
        cfdisId.setFormaPago(cfdiRelacionado.getFormaPago());
        cfdisId.setLugarExpedicion(cfdiRelacionado.getLugarExpedicion());
        cfdisId.setMetodoPago(cfdiRelacionado.getMetodoPago());
        cfdisId.setMoneda(cfdiRelacionado.getMoneda());
        cfdisId.setNombreReceptor(cfdiRelacionado.getNombreReceptor());
        cfdisId.setPersonaId(cfdiRelacionado.getPersonaId());
        cfdisId.setRfcReceptor(cfdiRelacionado.getRfcReceptor());
        cfdisId.setSerie(cfdiRelacionado.getSerie());
        //cfdisId.setFolio(obtenerFolioPorUsuarioSerieCert(cfdisId.getSerie(),cfdisId.getCertificados_Id()).get(0).getFolio());
        List<RnGcFolioserieTbl> listaFolioss = obtenerFolioPorUsuarioSerieCert(cfdisId.getSerie(), cfdisId.getCertificados_Id());
        // Verifica si la lista devuelta no es nula y no está vacía
        if (listaFolioss != null && !listaFolioss.isEmpty()) {
            // Obtiene el primer elemento de la lista y establece su folio en cfdisId
            cfdisId.setFolio(listaFolioss.get(0).getFolio());
        }
        cfdisId.setTipoCambio(cfdiRelacionado.getTipoCambio());
        cfdisId.setTipoComprobante(cfdiRelacionado.getTipoComprobante());
        cfdisId.setUltimaActualizacionPor(cfdiRelacionado.getUltimaActualizacionPor());
        cfdisId.setUsoCfdi(cfdiRelacionado.getUsoCfdi());
        personas = cfdiRelacionado.getPersonaId();
        
        System.out.println("Termino de agregar el cdfi relacionado a cfdi id: " );
        List<RnGcCfdisLineasTbl> listaLineas = lineasCfdisFacade.obtenerCfdisLineas(cfdiRelacionado);
        System.out.println("lineas: " + listaLineas);
        cfdisLineas = new ArrayList<>();
        for(int i = 0, tamaño = listaLineas.size(); i < tamaño; i++){
            RnGcCfdisLineasTbl linea = new RnGcCfdisLineasTbl();
            linea.setId((int) (Math.random() * 999999999));
            linea.setClaveProdServ(listaLineas.get(i).getClaveProdServ());
            linea.setNoIdentificacion(listaLineas.get(i).getNoIdentificacion());
            linea.setCantidad(listaLineas.get(i).getCantidad());
            linea.setClaveUnidad(listaLineas.get(i).getClaveUnidad());
            linea.setUnidad(listaLineas.get(i).getUnidad());
            linea.setDescripcion(listaLineas.get(i).getDescripcion());
            linea.setValorUnit(listaLineas.get(i).getValorUnit());
            linea.setImporte(listaLineas.get(i).getImporte());
            linea.setBase(listaLineas.get(i).getBase());
            linea.setDescuento(listaLineas.get(i).getDescuento());
            linea.setTipoImpuesto(listaLineas.get(i).getTipoImpuesto());
            linea.setImpuesto(listaLineas.get(i).getImpuesto());
            linea.setTipoFactor(listaLineas.get(i).getTipoFactor());
            linea.setTipoTasa(listaLineas.get(i).getTipoTasa());
            linea.setTipoImpuesto2(listaLineas.get(i).getTipoImpuesto2());
            linea.setImpuesto2(listaLineas.get(i).getImpuesto2());
            linea.setTipoFactor2(listaLineas.get(i).getTipoFactor2());
            linea.setTipoTasa2(listaLineas.get(i).getTipoTasa2());
            linea.setTipoImpuesto3(listaLineas.get(i).getTipoImpuesto3());
            linea.setImpuesto3(listaLineas.get(i).getImpuesto3());
            linea.setTipoFactor3(listaLineas.get(i).getTipoFactor3());
            linea.setTipoTasa3(listaLineas.get(i).getTipoTasa3());
            linea.setTipoImpuesto4(listaLineas.get(i).getTipoImpuesto4());
            linea.setImpuesto4(listaLineas.get(i).getImpuesto4());
            linea.setTipoFactor4(listaLineas.get(i).getTipoFactor4());
            linea.setTipoTasa4(listaLineas.get(i).getTipoTasa4());
            linea.setImporteimpuesto(listaLineas.get(i).getImporteimpuesto());
            linea.setImporteImpuesto2(listaLineas.get(i).getImporteImpuesto2());
            linea.setImporteImpuesto3(listaLineas.get(i).getImporteImpuesto3());
            linea.setImporteImpuesto4(listaLineas.get(i).getImporteImpuesto4());
            linea.setCreadoPor(listaLineas.get(i).getCreadoPor());
            linea.setFechaCreacion(new Date());
            linea.setUltimaActualizacionPor(listaLineas.get(i).getUltimaActualizacionPor());
            linea.setUltimaFechaActualizacion(new Date());
            linea.setProductoId(listaLineas.get(i).getProductoId());
            linea.setNoPedimento(listaLineas.get(i).getNoPedimento());
            linea.setNoCuentaPredial(listaLineas.get(i).getNoCuentaPredial());
            linea.setPeligroso(listaLineas.get(i).getPeligroso());
            linea.setPesoUnitario(listaLineas.get(i).getPesoUnitario());
            cfdisLineas.add(linea);
        }
        cfdiRelacionado = null;
        listaCfdisRelacionados = null;
    }
    
    public void limpiarBusqueda(){
        personas2 = null;
        cfdiRelacionado = null;
        listaCfdisRelacionados = null;
    }
    
     public boolean crearXmlEscuela() {
        File tempFile = null;
        System.out.println("crearXmlEscuela");
        String cadOrig = "";
        boolean valor = false;
        try {
            System.out.println("crearXMLEscuela2");
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            //Raiz
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("cfdi:Comprobante");
            doc.appendChild(rootElement);
            //Atributos Raiz
            Attr version = doc.createAttribute("Version");
            version.setValue("4.0");
            rootElement.setAttributeNode(version);
            Attr exportacion = doc.createAttribute("Exportacion");
            exportacion.setValue(cfdisId.getExportacionId().getClave());
            //exportacion.setValue("01");
            rootElement.setAttributeNode(exportacion);
            
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
            fecha.setValue(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(cfdisId.getFechaExpedicion()));
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
            cfdi.setValue("http://www.sat.gob.mx/cfd/4");
            rootElement.setAttributeNode(cfdi);
            Attr xsi = doc.createAttribute("xmlns:xsi");
            xsi.setValue("http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttributeNode(xsi);
            Attr esquema = doc.createAttribute("xsi:schemaLocation");
            esquema.setValue("http://www.sat.gob.mx/cfd/4 http://www.sat.gob.mx/sitio_internet/cfd/4/cfdv40.xsd http://www.sat.gob.mx/iedu http://www.sat.gob.mx/sitio_internet/cfd/iedu/iedu.xsd");
            rootElement.setAttributeNode(esquema);
            //Nodo emisor
            Element emisor = doc.createElement("cfdi:Emisor");
            rootElement.appendChild(emisor);
            //Atributos Emisor
            Attr rfcE = doc.createAttribute("Rfc");
            rfcE.setValue(stripAccents(cfdisId.getRfcEmisor()));
            emisor.setAttributeNode(rfcE);
            Attr nombreE = doc.createAttribute("Nombre");
            nombreE.setValue(cfdisId.getNombreEmisor());
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
            nombreR.setValue(personas.getNombre());
            System.out.print("nombreee " + personas.getNombre());
            System.out.print("nombre strip escuelaaa " + stripAccents(personas.getNombre()));
            receptor.setAttributeNode(nombreR);
            Attr uso = doc.createAttribute("UsoCFDI");
            uso.setValue(cfdisId.getUsoCfdi());
            receptor.setAttributeNode(uso);
            Attr regimenReceptor = doc.createAttribute("RegimenFiscalReceptor");
            //regimenReceptor.setValue("605");
            regimenReceptor.setValue(String.valueOf(personas.getRegimenFiscalId().getClaveRegimenFiscal()));
            receptor.setAttributeNode(regimenReceptor);
            Attr domicilioReceptor = doc.createAttribute("DomicilioFiscalReceptor");
            //domicilioReceptor.setValue("90000");
            domicilioReceptor.setValue(personas.getcodigoPostal());
            receptor.setAttributeNode(domicilioReceptor);
            
            System.out.println("crearXMLescuelaa3");
            //Nodo conceptos
            Element conceptos = doc.createElement("cfdi:Conceptos");
            rootElement.appendChild(conceptos);
            String nsURI = "http://www.sat.gob.mx/iedu";
            String nsPrefix = "iedu";
            doc.getDocumentElement().setAttribute("xmlns:" + nsPrefix, nsURI);
            for (int i = 0; i < cfdisLineas.size(); i++) {
                //Atributos conceptos
                System.out.println("Entro a lineas**");
                Element concepto = doc.createElement("cfdi:Concepto");
                conceptos.appendChild(concepto);
                Attr claveProd = doc.createAttribute("ClaveProdServ");
                claveProd.setValue(String.valueOf(cfdisLineas.get(i).getClaveProdServ()));
                concepto.setAttributeNode(claveProd);
                Attr objetoImp = doc.createAttribute("ObjetoImp");
                //objetoImp.setValue("02");
                objetoImp.setValue(cfdisLineas.get(i).getProductoId().getObjetoImpId().getClave());
                concepto.setAttributeNode(objetoImp);
                if (cfdisLineas.get(i).getNoIdentificacion() != null && !cfdisLineas.get(i).getNoIdentificacion().isEmpty()) {
                    Attr noIdenti = doc.createAttribute("NoIdentificacion");
                    noIdenti.setValue(cfdisLineas.get(i).getNoIdentificacion());
                    concepto.setAttributeNode(noIdenti);
                }
                 // Creamos el nodo <cfdi:ComplementoConcepto>
            Element complementoConcepto = doc.createElement("cfdi:ComplementoConcepto");
            concepto.appendChild(complementoConcepto);
            
            // Agregar namespace para el nodo complemento
            Element instEducativas = doc.createElementNS("http://www.sat.gob.mx/iedu", "iedu:instEducativas");
            complementoConcepto.appendChild(instEducativas);

// Añadimos los atributos al elemento <iedu:instEducativas>
                // Añadir atributo 'nombreAlumno'
                Attr nombreAlumno = doc.createAttribute("nombreAlumno");
                nombreAlumno.setValue("LARA VELAZQUEZ JOHANNA");
                instEducativas.setAttributeNode(nombreAlumno);

// Añadir atributo 'CURP'
                Attr CURP = doc.createAttribute("CURP");
                CURP.setValue("LAVJ041105MHGRLHA9");
                instEducativas.setAttributeNode(CURP);

// Añadir atributo 'nivelEducativo'
                Attr nivelEducativo = doc.createAttribute("nivelEducativo");
                nivelEducativo.setValue("Bachillerato o su equivalente");
                instEducativas.setAttributeNode(nivelEducativo);

// Añadir atributo 'autRVOE'
                Attr autRVOE = doc.createAttribute("autRVOE");
                autRVOE.setValue("131");
                instEducativas.setAttributeNode(autRVOE);

// Añadir atributo 'rfcPago'
                Attr rfcPago = doc.createAttribute("rfcPago");
                rfcPago.setValue("LABJ681217JL6");
                instEducativas.setAttributeNode(rfcPago);
// Añadir atributo 'version'
Attr versionesc = doc.createAttribute("version");
versionesc.setValue("1.0");
instEducativas.setAttributeNode(versionesc);

                
                
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
                
                
                
                System.out.println("salto el primer impuesto");
                if (((cfdisLineas.get(i).getTipoImpuesto() != null) && cfdisLineas.get(i).getTipoImpuesto().equals("Retención"))
                        || ((cfdisLineas.get(i).getTipoImpuesto3() != null) && cfdisLineas.get(i).getTipoImpuesto3().equals("Retención"))
                        || ((cfdisLineas.get(i).getTipoImpuesto2() != null) && cfdisLineas.get(i).getTipoImpuesto2().equals("Retención"))
                        || ((cfdisLineas.get(i).getTipoImpuesto4() != null) && cfdisLineas.get(i).getTipoImpuesto4().equals("Retención"))) {
                    //nodo retenciones
                    
                  
                    System.out.println("dato6");
                    
                    System.out.println("dato8");
                }
            }
            System.out.println("Termino Impuestos");
            //nodo impuestos
            
           //Complemento
          
            System.out.println("Trasladados2");
            System.out.println("Probando");
            File xslt = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/cadenaoriginal_4_0.xslt"));
            StreamSource sourceXSL = new StreamSource(xslt);
            System.out.println("Probando2");

            DOMSource source = new DOMSource(doc);
            System.out.println("Probando3");
            tempFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/xmlTemp.xml"));
            //tempFile = new File("C:\\Users\\Developer1\\Desktop\\Nueva carpeta\\NuevoPrueba.xml");
            System.out.println("Probando3.1");
            //FileWriter writer = new FileWriter(tempFile);
            System.out.println("Probando3.2");
            StreamResult sourceXml = new StreamResult(tempFile);
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
            
            
              // Convertir el documento XML en una cadena de texto
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(FacturarController.class.getName()).log(Level.SEVERE, null, ex);
        }
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        String xmlString = writer.getBuffer().toString();
        
        // Imprimir la cadena XML
        System.out.println("/////////////////////////////////////////////////XML generado:");
        System.out.println(xmlString);
        System.out.println("/////////////////////////////////////////////////XML generado fin:");
            
        } catch (Exception ex) {
            System.out.println("ErrorIngEgrem: " + ex.getMessage() + " | " + ex.getLocalizedMessage());
        }
                 
        
        return valor;
    }
    
private String complementoSeleccionado;
private String nombreAlumno;
private String curp;
private String nivelEducativo;
private String autRVOE;
private String rfcPago;

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNivelEducativo() {
        return nivelEducativo;
    }

    public void setNivelEducativo(String nivelEducativo) {
        this.nivelEducativo = nivelEducativo;
    }

    public String getAutRVOE() {
        return autRVOE;
    }

    public void setAutRVOE(String autRVOE) {
        this.autRVOE = autRVOE;
    }

    public String getRfcPago() {
        return rfcPago;
    }

    public void setRfcPago(String rfcPago) {
        this.rfcPago = rfcPago;
    }


      

    public String getComplementoSeleccionado() {
        return complementoSeleccionado;
    }

    public void setComplementoSeleccionado(String complementoSeleccionado) {
        this.complementoSeleccionado = complementoSeleccionado;
    }

    public void onComplementoChange() {
        // Lógica para manejar el cambio en la selección del complemento
        System.out.println("Complemento seleccionado: "+ complementoSeleccionado);
    }
    
    public void confirmarComplemento(){
        System.out.println("Complemento guardado");
}
    
    public int getActiveIndex() {
    if ("educativo".equals(complementoSeleccionado)) {
        return 0;
    } else if ("arrendamiento".equals(complementoSeleccionado)) {
        return 1;
    } else {
        return 2;
    }
}


    
}
