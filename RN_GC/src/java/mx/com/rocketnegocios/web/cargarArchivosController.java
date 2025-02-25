package mx.com.rocketnegocios.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import mx.com.rocketnegocios.beans.RnGcArchivosTblFacade;
import mx.com.rocketnegocios.beans.RnGcCatalogoCuentasTblFacade;
import mx.com.rocketnegocios.beans.RnGcCertificadosTblFacade;
import mx.com.rocketnegocios.beans.RnGcCfdisLineasTblFacade;
import mx.com.rocketnegocios.beans.RnGcCfdisTblFacade;
import mx.com.rocketnegocios.beans.RnGcCodigoAgrupadorSatTblFacade;
import mx.com.rocketnegocios.beans.RnGcPersonasTblFacade;
import mx.com.rocketnegocios.beans.RnGcProductserviciosTblFacade;
import mx.com.rocketnegocios.beans.RnGcTipospersonasTblFacade;
import mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade;
import mx.com.rocketnegocios.entities.RnGcArchivosTbl;
import mx.com.rocketnegocios.entities.RnGcCatalogoCuentasTbl;
import mx.com.rocketnegocios.entities.RnGcCertificadosTbl;
import mx.com.rocketnegocios.entities.RnGcCfdisLineasTbl;
import mx.com.rocketnegocios.entities.RnGcCfdisTbl;
import mx.com.rocketnegocios.entities.RnGcCodigoAgrupadorSatTbl;
import mx.com.rocketnegocios.entities.RnGcPersonasTbl;
import mx.com.rocketnegocios.entities.RnGcProductserviciosTbl;
import mx.com.rocketnegocios.entities.RnGcTipospersonasTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import mx.com.rocketnegocios.web.util.JsfUtil;
import org.apache.poi.util.IOUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.primefaces.component.fileupload.FileUpload;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named(value = "cargarArchivosController")
@SessionScoped
public class cargarArchivosController implements Serializable {

    @EJB
    private RnGcCfdisLineasTblFacade rnGcCfdislineasTblFacade;

    @EJB
    private RnGcCfdisTblFacade rnGcCfdisTblFacade;

    @EJB
    private RnGcArchivosTblFacade rnGcArchivosTblFacade;

    @EJB
    private RnGcUsuariosTblFacade rnGcUsuariosTblFacade;

    @EJB
    private RnGcProductserviciosTblFacade productoServicioFacade;

    @EJB
    private RnGcCertificadosTblFacade certificadosFacade;

    @EJB
    private RnGcTipospersonasTblFacade tiposPersonasFacade;
    
    
    @EJB
    private RnGcCatalogoCuentasTblFacade catalogoCuentasFacade;

    @EJB
    private RnGcPersonasTblFacade personasFacade;
    
    @EJB
    private RnGcCodigoAgrupadorSatTblFacade codigoAgrupadorFacade;

    private RnGcCfdisTbl CfdiId = null;
    private List<RnGcCfdisLineasTbl> lineasConceptos = null;
    private RnGcCfdisLineasTbl lineasConcepto = null;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private UploadedFile file;
    private List<FileUploadEvent> listaEventos;
    private List<RnGcArchivosTbl> itemsArchivos = null;
    private RnGcUsuariosTbl usuarioId;
    private RnGcArchivosTbl selectedArchivo;
    private RnGcCfdisLineasTblController lineasController;
    private RnGcCatalogoCuentasTblController catalogoCuentasController;
    private RnGcProductserviciosTbl productoServicio;
    private List<RnGcProductserviciosTbl> listaProducServicios = null;
    private RnGcCertificadosTbl certificados;
    private List<RnGcPersonasTbl> listaPersonas;
    private RnGcPersonasTbl personas;
    private RnGcTipospersonasTbl tipoPersona;
    private List<RnGcPersonasTbl> personasRegistradas;
    private List<RnGcProductserviciosTbl> productosRegistrados;
    private List<RnGcCatalogoCuentasTbl> listaCatalogoCuentas = null;
    private RnGcCatalogoCuentasTbl catalogoCuenta;
    private List<String> listaRfc;
    private List<String> rfcSeleccionado;
    private List<String> listaNombres;
    private List<String> nombreSeleccionado;
    private String[][] listaTiposDocs = null;

    public String[][] getListaTiposDocs() {
        if(listaTiposDocs == null){
            listaTiposDocs = new String[10][2];
            for(int i = 0; i < 10; i++){
                for(int j = 0; j < 2; j++){
                    listaTiposDocs[i][j] = "";
                }
            }
        }
        return listaTiposDocs;
    }

    public void setListaTiposDocs(String[][] listaTiposDocs) {
        this.listaTiposDocs = listaTiposDocs;
    }

    public List<String> getListaNombres() {
        return listaNombres;
    }

    public void setListaNombres(List<String> listaNombres) {
        this.listaNombres = listaNombres;
    }

    public List<String> getNombreSeleccionado() {
        return nombreSeleccionado;
    }

    public void setNombreSeleccionado(List<String> nombreSeleccionado) {
        this.nombreSeleccionado = nombreSeleccionado;
    }

    public List<FileUploadEvent> getListaEventos() {
        return listaEventos;
    }

    public void setListaEventos(List<FileUploadEvent> listaEventos) {
        this.listaEventos = listaEventos;
    }

    public List<String> getRfcSeleccionado() {
        return rfcSeleccionado;
    }

    public void setRfcSeleccionado(List<String> rfcSeleccionado) {
        this.rfcSeleccionado = rfcSeleccionado;
    }

    public List<String> getListaRfc() {
        return listaRfc;
    }

    public void setListaRfc(List<String> listaRfc) {
        this.listaRfc = listaRfc;
    }

    public RnGcCatalogoCuentasTbl getCatalogoCuenta() {
        if(catalogoCuenta == null)
            catalogoCuenta = new RnGcCatalogoCuentasTbl();
        return catalogoCuenta;
    }

    public void setCatalogoCuenta(RnGcCatalogoCuentasTbl catalogoCuenta) {
        this.catalogoCuenta = catalogoCuenta;
    }

    public RnGcArchivosTbl getSelectedArchivo() {
        return selectedArchivo;
    }

    public void setSelectedArchivo(RnGcArchivosTbl selectedArchivo) {
        this.selectedArchivo = selectedArchivo;
    }

    public RnGcCfdisTbl getCfdiId() {
        return CfdiId;
    }

    public void setCfdiId(RnGcCfdisTbl CfdiId) {
        this.CfdiId = CfdiId;
    }

    public List<RnGcCfdisLineasTbl> getLineasConceptos() {
        return lineasConceptos;
    }

    public void setLineasConceptos(List<RnGcCfdisLineasTbl> lineasConceptos) {
        this.lineasConceptos = lineasConceptos;
    }

    public RnGcCfdisLineasTbl getLineasConcepto() {
        return lineasConcepto;
    }

    public void setLineasConcepto(RnGcCfdisLineasTbl lineasConcepto) {
        this.lineasConcepto = lineasConcepto;
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

    public RnGcPersonasTbl getPersonas() {
        if (personas == null) {
            this.personas = new RnGcPersonasTbl();
        }
        return personas;
    }

    public void setPersonas(RnGcPersonasTbl personas) {
        this.personas = personas;
    }

    public cargarArchivosController() {
        if (CfdiId == null) {
            CfdiId = new RnGcCfdisTbl();
        }
    }
    
    public void pruebaLeerXML(FileUploadEvent event){
        System.out.println("listaEventos1: " + listaEventos);
        if(listaEventos == null)
            listaEventos = new ArrayList<>();
        listaEventos.add(event);
        System.out.println("listaEventos2: " + listaEventos);
    }
    
    public void abrirModal(){
        System.out.println("Se abre el modal al terminar la carga de los N archivos");
        if(listaRfc != null)
            RequestContext.getCurrentInstance().execute("PF('CuentasDialog').show();");
    }
    
    public void terminado(){
        System.out.println("Carga de archivos terminado");
    }

    public void leerXML(FileUploadEvent event) throws FileNotFoundException, IOException, ParseException {
        System.out.println("leerXML: " + event.getFile().getFileName() + " | " + event.getComponent() + " | " + event.getPhaseId() + " | " + event.getSource() + " | " + event.hashCode());
        CfdiId = new RnGcCfdisTbl();
        personas = new RnGcPersonasTbl();
        this.file = event.getFile();
        selectedArchivo = new RnGcArchivosTbl();
        selectedArchivo.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        selectedArchivo.setFechaCreacion(new Date());
        selectedArchivo.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        selectedArchivo.setUltimaFechaActualizacion(new Date());
        CfdiId.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        CfdiId.setFechaCreacion(new Date());
        CfdiId.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        CfdiId.setUltimaFechaActualizacion(new Date());
        lineasConceptos = new ArrayList<>();
        listaProducServicios = new ArrayList<>();
        listaPersonas = new ArrayList<>();
        String[] archivo = new String[1];
        byte[] xml = null;
        byte[] pdf = null;
        usuarioId = rnGcUsuariosTblFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        if (event != null) {
            archivo[0] = event.getFile().getFileName();
            System.out.println("archivo[0]: " + archivo[0]);
            if (event.getFile().getContentType().equals("application/pdf")) {
                System.out.println("Guardando archivo PDF...");
                pdf = IOUtils.toByteArray(event.getFile().getInputstream());
                selectedArchivo.setArchivoPdf(pdf);
                System.out.println("PDF: " + selectedArchivo.getArchivoPdf() + " Guardado");
            } else if (event.getFile().getContentType().equals("text/xml")) {
                System.out.println("Guardando archivo XML...");
                xml = IOUtils.toByteArray(event.getFile().getInputstream());
                selectedArchivo.setArchivoXml(xml);
                System.out.println("XML: " + selectedArchivo.getArchivoXml() + " Guardado");
            }
        }
        System.out.println("archivo: " + archivo.length);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SAXBuilder builder = new SAXBuilder();
        // Selecionar Archivos
        if (archivo != null && xml != null) {
            //for (int z = 0; z < archivo.length; z++) {
                try {
                    Document document = (Document) builder.build(event.getFile().getInputstream());
                    Element rootNode = document.getRootElement();
                    String nombreXml = (rootNode.getName());
                    String formaPago = rootNode.getAttributeValue("FormaPago");
                    String metodoPago = rootNode.getAttributeValue("MetodoPago");
                    String tipoComprobante = rootNode.getAttributeValue("TipoDeComprobante");
                    String lugarExpedicion = rootNode.getAttributeValue("LugarExpedicion");
                    CfdiId.setTipoComprobante(tipoComprobante);
                    Integer lugexp = Integer.valueOf(lugarExpedicion);
                    String fecha = rootNode.getAttributeValue("Fecha");
                    String version = rootNode.getAttributeValue("Version");
                    String moneda = rootNode.getAttributeValue("Moneda");
                    CfdiId.setMoneda(moneda);
                    CfdiId.setFechaExpedicion(formatoFecha.parse(fecha));
                    String condicionpago = rootNode.getAttributeValue("CondicionesDePago");
                    CfdiId.setFormaPago(formaPago);
                    CfdiId.setMetodoPago(metodoPago);
                    CfdiId.setCondicionPago(condicionpago);
                    String foli = rootNode.getAttributeValue("Folio");
                    if(foli != null && !foli.isEmpty())
                        CfdiId.setFolio(Integer.parseInt(foli));
                    CfdiId.setLugarExpedicion(lugexp);
                    String subtotal = rootNode.getAttributeValue("SubTotal");
                    String total = rootNode.getAttributeValue("Total");
                    CfdiId.setImporte(Double.valueOf(total));
                    CfdiId.setSaldoPagar(Double.valueOf(total));
                    CfdiId.setSaldoInsoluto(Double.valueOf(total));
                    CfdiId.setSaldoPagar(0.0);
                    CfdiId.setSaldoPagado(0.0);
                    
                    double tipoCambio = Double.parseDouble(rootNode.getAttributeValue("TipoCambio"));
                    CfdiId.setTipoCambio(tipoCambio);
                    System.out.println(nombreXml + "\n" + formaPago + "\n" + metodoPago + "\n" + tipoComprobante + "\n" + lugarExpedicion + "\n" + fecha + "\n" + version + "\n" + moneda + "\n" + condicionpago + "\n" + foli + "\n" + subtotal + "\n" + total);
                    List list = rootNode.getChildren();
                    for (int i = 0; i < list.size(); i++) {
                        Element elementoCFDI = (Element) list.get(i);
                        String valor = elementoCFDI.getName();
                        //CfdiRelacionados
                        List relacion = elementoCFDI.getChildren();
                        if (valor.equals("CfdiRelacionados")) {
                            //System.out.println("Relacion CFDIs");
                            System.out.println("Tipo de Relacion: " + elementoCFDI.getAttributeValue("TipoRelacion"));
                            CfdiId.setTipoRelacion(elementoCFDI.getAttributeValue("TipoRelacion"));
                            for (int j = 0; j < relacion.size(); j++) {
                                Element dato1 = (Element) relacion.get(j);
                                String relac = dato1.getName();
                                if (relac.equals("CfdiRelacionado")) {
                                    System.out.println(dato1.getAttributeValue("UUID"));
                                    CfdiId.setUuidRelacionado(dato1.getAttributeValue("UUID"));
                                }
                            }
                        }
                        //Emisor
                        if (valor.equals("Emisor")) {
                            //System.out.println("Datos Emisor");
                            System.out.println("RFC_E: " + elementoCFDI.getAttributeValue("Rfc"));
                            System.out.println("Nombre_E: " + elementoCFDI.getAttributeValue("Nombre"));
                            CfdiId.setRfcEmisor(elementoCFDI.getAttributeValue("Rfc"));
                            CfdiId.setNombreEmisor(elementoCFDI.getAttributeValue("Nombre"));
                            System.out.println("RegimenFiscal: " + elementoCFDI.getAttributeValue("RegimenFiscal"));
                            CfdiId.setClaveRegimenFiscal(elementoCFDI.getAttributeValue("RegimenFiscal"));
                        }
                        //Receptor
                        if (valor.equals("Receptor")) {
                            //System.out.println("Datos Receptor");
                            System.out.println("RFC_R: " + elementoCFDI.getAttributeValue("Rfc"));
                            System.out.println("Nombre_R: " + elementoCFDI.getAttributeValue("Nombre"));
                            CfdiId.setRfcReceptor(elementoCFDI.getAttributeValue("Rfc"));
                            CfdiId.setNombreReceptor(elementoCFDI.getAttributeValue("Nombre"));
                            System.out.println("UsoCFDI: " + elementoCFDI.getAttributeValue("UsoCFDI"));
                            CfdiId.setUsoCfdi(elementoCFDI.getAttributeValue("UsoCFDI"));
                        }
                        System.out.println("*** usuarioId.getRfc() ****" + usuarioId.getRfc());
                            if(usuarioId.getRfc().equals(CfdiId.getRfcEmisor()) && tipoComprobante.equals("N")){
                                System.out.println("********* RFC emisor comprobante N ******************");
                                personas.setRfc(CfdiId.getRfcReceptor());
                                personas.setNombre(CfdiId.getNombreReceptor());
                                personas.setTipoPersona("Trabajador");
                                tipoPersona = tiposPersonasFacade.obtenerTipoPersona("Trabajador");
                                personas.setTipoPersonaId(tipoPersona);
                            }else if(usuarioId.getRfc().equals(CfdiId.getRfcEmisor())){
                                System.out.println("********* RFC emisor ******************");
                                personas.setRfc(CfdiId.getRfcReceptor());
                                personas.setNombre(CfdiId.getNombreReceptor());
                                personas.setTipoPersona("Cliente");
                                tipoPersona = tiposPersonasFacade.obtenerTipoPersona("Cliente");
                                personas.setTipoPersonaId(tipoPersona);
                            }else if(usuarioId.getRfc().equals(CfdiId.getRfcReceptor())){
                                System.out.println("********* RFC receptor ******************");
                                personas.setRfc(CfdiId.getRfcEmisor());
                                personas.setNombre(CfdiId.getNombreEmisor());
                                personas.setTipoPersona("Proveedor");
                                tipoPersona = tiposPersonasFacade.obtenerTipoPersona("Proveedor");
                                personas.setTipoPersonaId(tipoPersona);
                            }
                        //Conceptos
                        List conceptos = elementoCFDI.getChildren();
                        if (valor.equals("Conceptos")) {
                            System.out.println("conceptos.size: " + conceptos.size());
                            for (int j = 0; j < conceptos.size(); j++) {
                                Element dato1 = (Element) conceptos.get(j);
                                lineasConcepto = new RnGcCfdisLineasTbl();
                                productoServicio = new RnGcProductserviciosTbl();
                                productoServicio.setTipoProdServ("Servicio");
                                // Concepto                                
                                //System.out.println("Datos Concepto");
                                //System.out.println("Clave Prod / Ser:" + dato1.getAttributeValue("ClaveProdServ"));
                                System.out.println("Clave Prod/Serv " + j + ": " + dato1.getAttributeValue("ClaveProdServ"));
                                lineasConcepto.setClaveProdServ(dato1.getAttributeValue("ClaveProdServ"));
                                productoServicio.setClaveProductServ(dato1.getAttributeValue("ClaveProdServ"));
                                //System.out.println("No. de identificacion: " + dato1.getAttributeValue("NoIdentificacion"));
                                lineasConcepto.setNoIdentificacion(dato1.getAttributeValue("NoIdentificacion"));
                                productoServicio.setNoIdentificacion(dato1.getAttributeValue("NoIdentificacion"));
                                //System.out.println("Cantidad: " + dato1.getAttributeValue("Cantidad"));
                                lineasConcepto.setCantidad(Float.valueOf(dato1.getAttributeValue("Cantidad")));
                                //System.out.println("Clave Unidad: " + dato1.getAttributeValue("ClaveUnidad"));
                                lineasConcepto.setClaveUnidad(dato1.getAttributeValue("ClaveUnidad"));
                                productoServicio.setClaveUnidad(dato1.getAttributeValue("ClaveUnidad"));
                                //System.out.println("Unidad: " + dato1.getAttributeValue("Unidad"));
                                lineasConcepto.setUnidad(dato1.getAttributeValue("Unidad"));
                                productoServicio.setUnidad(dato1.getAttributeValue("Unidad"));
                                //System.out.println("Descripcion: " + dato1.getAttributeValue("Descripcion"));
                                lineasConcepto.setDescripcion(dato1.getAttributeValue("Descripcion"));
                                productoServicio.setDescripcion(dato1.getAttributeValue("Descripcion"));
                                //System.out.println("Valor Unitario: " + dato1.getAttributeValue("ValorUnitario"));
                                lineasConcepto.setValorUnit(Float.valueOf(dato1.getAttributeValue("ValorUnitario")));
                                productoServicio.setValorunitario(Double.parseDouble(dato1.getAttributeValue("ValorUnitario")));
                                //System.out.println("Importe: " + dato1.getAttributeValue("Importe"));
                                lineasConcepto.setImporte(Float.valueOf(dato1.getAttributeValue("Importe")));
                                //Conceptos
                                List concept = dato1.getChildren();
                                for (int a = 0; a < concept.size(); a++) {
                                    Element dato2 = (Element) concept.get(a);
                                    List impuestos = dato2.getChildren();
                                    for (int b = 0; b < impuestos.size(); b++) {
                                        Element dato3 = (Element) impuestos.get(b);
                                        String impues = dato3.getName();
                                        //Traslados
                                        List traslado = dato3.getChildren();
                                        System.out.println("traslado: " + traslado.size());
                                        if (impues.equals("Traslados")) {
                                            for (int l = 0; l < traslado.size(); l++) {
                                                Element trasla = (Element) traslado.get(l);
                                                String tras = trasla.getName();
                                                if (tras.equals("Traslado")) {
                                                    lineasConcepto.setBase(Double.valueOf(trasla.getAttributeValue("Base")));
                                                    lineasConcepto.setImpuesto(trasla.getAttributeValue("Impuesto"));
                                                    productoServicio.setTipoImpuesto("Traslado");
                                                    productoServicio.setImpuesto(trasla.getAttributeValue("Impuesto"));
                                                    lineasConcepto.setTipoFactor(trasla.getAttributeValue("TipoFactor"));
                                                    productoServicio.setTipofactor(trasla.getAttributeValue("TipoFactor"));
                                                    if (trasla.getAttributeValue("TasaOCuota") != null) {
                                                        lineasConcepto.setTipoTasa(Double.valueOf(trasla.getAttributeValue("TasaOCuota")));
                                                        productoServicio.setTipoTasa(Double.parseDouble(trasla.getAttributeValue("TasaOCuota")));
                                                    } else {
                                                        double Tc = 0.00d;
                                                        lineasConcepto.setTipoTasa(Tc);
                                                        productoServicio.setTipoTasa(Tc);
                                                    }
                                                    if (trasla.getAttributeValue("Importe") != null) {
                                                        lineasConcepto.setImporteimpuesto(Double.valueOf(trasla.getAttributeValue("Importe")));
                                                    } else {
                                                        double Impo = 0.00;
                                                        lineasConcepto.setImporteimpuesto(Impo);
                                                    }
                                                }
                                            }
                                        }
                                        //Retenciones
                                        List retencion = dato3.getChildren();
                                        System.out.println("retencion: " + retencion.size());
                                        if (impues.equals("Retenciones")) {
                                            for (int l = 0; l < retencion.size(); l++) {
                                                Element reten = (Element) retencion.get(l);
                                                String ret = reten.getName();
                                                if (ret.equals("Retencion")) {
                                                    //System.out.println("Datos Retenciones_Concept");
                                                    //System.out.println("Base: " + reten.getAttributeValue("Base"));
                                                    Float base = Float.parseFloat(reten.getAttributeValue("Base"));
                                                    String impuesto = reten.getAttributeValue("Impuesto");
                                                    Double tasaocuot = Double.parseDouble(reten.getAttributeValue("TasaOCuota"));
                                                    Float importe = Float.parseFloat(reten.getAttributeValue("Importe"));
                                                    String tipoFactor = reten.getAttributeValue("TipoFactor");
                                                    Double importeImpuesto2 = Double.parseDouble(reten.getAttributeValue("Importe"));
                                                    productoServicio.setTipoImpuesto2("Retención");
                                                    lineasConcepto.setTipoImpuesto2("Retención");
                                                    productoServicio.setImpuesto2(impuesto);
                                                    lineasConcepto.setImpuesto2(impuesto);
                                                    productoServicio.setTipoFactor2(tipoFactor);
                                                    lineasConcepto.setTipoFactor2(tipoFactor);
                                                    productoServicio.setTipoTasa2(tasaocuot);
                                                    lineasConcepto.setTipoTasa2(tasaocuot);
                                                    lineasConcepto.setImporteImpuesto2(importeImpuesto2);
                                                }
                                            }
                                        }

                                    }
                                }
                                lineasConceptos.add(lineasConcepto);
                                listaProducServicios.add(productoServicio);
                                listaPersonas.add(personas);
                            }
                        }
                        //Impuestos
                        List impues = elementoCFDI.getChildren();
                        if (valor.equals("Impuestos")) {
                            //System.out.println("Total Impuestos Trasladados");
                            //System.out.println("Total impuestos Trasladados: " + elementoCFDI.getAttributeValue("TotalImpuestosTrasladados"));
                            for (int k = 0; k < impues.size(); k++) {
                                Element dato2 = (Element) impues.get(k);
                                String impu = dato2.getName();
                                //Retenciones
                                List retencion = dato2.getChildren();
                                if (impu.equals("Retenciones")) {
                                    for (int l = 0; l < retencion.size(); l++) {
                                        Element reten = (Element) retencion.get(l);
                                        String ret = reten.getName();
                                        if (ret.equals("Retencion")) {
                                            //System.out.println("Datos Retencion");
                                            //System.out.println("Impuesto: " + reten.getAttributeValue("Impuesto"));
                                            //System.out.println("Importe: " + reten.getAttributeValue("Importe"));
                                        }
                                    }
                                }
                                //Traslados
                                List traslado = dato2.getChildren();
                                if (impu.equals("Traslados")) {
                                    for (int l = 0; l < traslado.size(); l++) {
                                        Element trasla = (Element) traslado.get(l);
                                        String tras = trasla.getName();
                                        if (tras.equals("Traslado")) {
                                            //System.out.println("Datos Traslado");
                                            //System.out.println("Impuesto: " + trasla.getAttributeValue("Impuesto"));
                                            //System.out.println("Tipo Factor: " + trasla.getAttributeValue("TipoFactor"));
                                            //System.out.println("Tasa Cuota: " + trasla.getAttributeValue("TasaOCuota"));
                                            //System.out.println("Importe: " + trasla.getAttributeValue("Importe"));
                                        }
                                    }
                                }
                            }
                        }
                        //Complemento
                        List complemen = elementoCFDI.getChildren();
                        if (valor.equals("Complemento")) {
                            for (int m = 0; m < complemen.size(); m++) {
                                Element complem = (Element) complemen.get(m);
                                String comp = complem.getName();
                                //System.out.println("Datos Complemento");
                                System.out.println("UUID: " + complem.getAttributeValue("UUID"));
                                CfdiId.setUuid(complem.getAttributeValue("UUID"));
                            }
                        }
                    }
                } catch (IOException | NumberFormatException | JDOMException ex) {
                    Logger.getLogger(cargarArchivosController.class.getName()).log(Level.SEVERE, null, ex);
                    JsfUtil.addErrorMessage(ex, "No fue posible cargar el archivo. Favor de revisar que el tipo corresponda a un CFDI");
                } catch (ParseException ex) {
                    Logger.getLogger(cargarArchivosController.class.getName()).log(Level.SEVERE, null, ex);
                    JsfUtil.addErrorMessage(ex, "No fue posible cargar el archivo. Favor de revisar el formato del CFDI");
                } catch (IllegalArgumentException ex) {
                    System.out.println("<--- IllegalArgumentException --->" + ex.getMessage());
                }
                if (CfdiId.getRfcEmisor().equals(usuarioId.getRfc()) || CfdiId.getRfcReceptor().equals(usuarioId.getRfc())) {
                    System.out.println("CfdiId: " + CfdiId.getRfcEmisor() + " | " + CfdiId.getNombreEmisor() + " | " + CfdiId.getRfcReceptor() + " | " + CfdiId.getNombreReceptor() + " | "
                            + CfdiId.getLugarExpedicion() + " | " + CfdiId.getFechaExpedicion() + " | " + CfdiId.getTipoComprobante() + " | " + CfdiId.getClaveRegimenFiscal() + " | "
                            + CfdiId.getUsoCfdi() + " | " + CfdiId.getFormaPago() + " | " + CfdiId.getMetodoPago() + " | " + CfdiId.getCondicionPago() + " | " + CfdiId.getMoneda() + " | "
                            + CfdiId.getImporte() + CfdiId.getCreadoPor() + " | " + CfdiId.getFechaCreacion() + " | " + CfdiId.getUltimaActualizacionPor() + " | " + CfdiId.getUltimaFechaActualizacion() + " | "
                            + CfdiId.getId() + " | " + CfdiId.getUuid());
                    CfdiId.setRespuestaTimbrado("Timbrado de forma correcta");
                    String str = new String(xml, StandardCharsets.UTF_8);
                    CfdiId.setXmlTrama(str);
                    CfdiId.setImporteLetra(importeLetra(CfdiId.getImporte()));
                    CfdiId = rnGcCfdisTblFacade.refreshFromDB(CfdiId);
                    System.out.println("*** personas.getRfc() ****" + personas.getRfc());
                        if (!obtenerClientes(personas.getRfc())) {
                            personas.setTipo("Matriz");
                            personas.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
                            personas.setFechaCreacion(new Date());
                            personas.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                            personas.setUltimaFechaActualizacion(new Date());
                            personas.setUsuarioId(usuarioId);
                            personasFacade.edit(personas);
                            personas = null;
                        }
                    for (int i = 0; i < lineasConceptos.size(); i++) {
                        lineasConcepto = lineasConceptos.get(i);
                        lineasConcepto.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
                        lineasConcepto.setFechaCreacion(new Date());
                        lineasConcepto.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                        lineasConcepto.setUltimaFechaActualizacion(new Date());
                        lineasConcepto.setCfdisId(CfdiId);
                        rnGcCfdislineasTblFacade.edit(lineasConcepto);
                        //System.out.println("Linea " + lineasConcepto.getId() + " ! " + lineasConcepto.getClaveProdServ() + " ! " + lineasConcepto.getNoIdentificacion() + " ! " + lineasConcepto.getCantidad() + " ! " + lineasConcepto.getClaveUnidad() + " ! " + lineasConcepto.getUnidad() + " ! " + lineasConcepto.getDescripcion() + " ! " + lineasConcepto.getValorUnit() + " ! " + lineasConcepto.getImporte() + " ! " + lineasConcepto.getBase() + " ! " + lineasConcepto.getImpuesto() + " ! " + lineasConcepto.getTipoFactor() + " ! " + lineasConcepto.getTipoTasa() + " ! " + lineasConcepto.getImporteimpuesto() + " ! " + lineasConcepto.getCfdisId() + " ! " + lineasConcepto.getCreadoPor() + " ! " + lineasConcepto.getFechaCreacion() + " ! " + lineasConcepto.getUltimaActualizacionPor() + " ! " + lineasConcepto.getUltimaFechaActualizacion());
                        lineasConcepto = null;
                        productoServicio = listaProducServicios.get(i);
                        if (usuarioId.getRfc().equals(CfdiId.getRfcEmisor())) {
                            if (!obtenerProductos(productoServicio.getDescripcion())) {
                                productoServicio.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
                                productoServicio.setFechaCreacion(new Date());
                                productoServicio.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                                productoServicio.setUltimaFechaActualizacion(new Date());
                                productoServicioFacade.edit(productoServicio);
                                productoServicio = null;
                            }
                        }
                        
                    }
                    selectedArchivo.setCfdiId(CfdiId);
                    selectedArchivo = rnGcArchivosTblFacade.refreshFromDB(selectedArchivo);
                    getListaTiposDocs();
                    if(listaTiposDocs != null)
                        for(int i = 0; i < 10; i++){
                            if(listaTiposDocs[i][0].isEmpty()){
                                listaTiposDocs[i][0] = CfdiId.getTipoComprobante();
                                listaTiposDocs[i][1] = String.valueOf(1);
                                System.out.println("* tipo: " + listaTiposDocs[i][0] + " cuantos: " + listaTiposDocs[i][1]);
                                break;
                            }else if(listaTiposDocs[i][0].equals(CfdiId.getTipoComprobante())){
                                int cont = Integer.valueOf(listaTiposDocs[i][1]) + 1;
                                listaTiposDocs[i][1] = String.valueOf(cont);
                                System.out.println("** tipo: " + listaTiposDocs[i][0] + " cuantos: " + listaTiposDocs[i][1]);
                                break;
                            }
                        }

                    if(CfdiId.getRfcEmisor() != null || CfdiId.getRfcReceptor() != null){
                        listaCatalogoCuentas= catalogoCuentasFacade.obtenerListaCatalogoCuentas(CfdiId.getRfcReceptor());
                        if(listaCatalogoCuentas.isEmpty()){
                        catalogoCuenta = new RnGcCatalogoCuentasTbl(1);
                        catalogoCuenta.setRfc(CfdiId.getRfcReceptor());
                            if(listaRfc == null)
                                listaRfc = new ArrayList<>();
                            if(!usuarioId.getRfc().equals(CfdiId.getRfcEmisor()))
                                listaRfc.add(CfdiId.getRfcEmisor() + "-" +CfdiId.getNombreEmisor());
                            if(!usuarioId.getRfc().equals(CfdiId.getRfcReceptor()))
                                listaRfc.add(CfdiId.getRfcReceptor() + "-" + CfdiId.getNombreReceptor());
                            RequestContext.getCurrentInstance().execute("PF('CuentasDialog').show();");
                        }else{
                            RequestContext.getCurrentInstance().execute("PF('dialogDialog').show();");
                        }
                    }                   
                    selectedArchivo = null;
                    lineasConceptos.clear();
                    lineasConceptos = null;
                    CfdiId = null;
                    //JsfUtil.addSuccessMessage("Archivo " + event.getFile().getFileName() + " Cargado Correctamente");
                    String frase = "Se cargaron los siguientes archivos: ";
                    for(int i = 0; i < 10; i++){
                        if(!listaTiposDocs[i][0].isEmpty()){
                            frase += listaTiposDocs[i][1] + " tipo " + listaTiposDocs[i][0] + " ";
                        }
                    }
                    JsfUtil.addSuccessMessage(frase);
                    System.out.println(frase);
                    
                } else {
                    JsfUtil.addErrorMessage("El RFC " + CfdiId.getRfcEmisor() + " del documento no coincide con los datos del USUARIO " + usuarioId.getNombreCompleto());
                    System.out.println("El RFC " + CfdiId.getRfcEmisor() + " del documento no coincide con los datos del usuarios " + usuarioId.getNombreCompleto() + " | " + usuarioId.getRfc());
                }
            //}
        } else {
            JsfUtil.addErrorMessage("No ha sido posible leer el archivo XML");
            System.out.println("No se ha podido leer el archivo XML");
        }//*/
    }
    
    public String importeLetra(Double numero) {
        NumeroALetra numLetra = new NumeroALetra();
        String importeLetra = String.valueOf(new DecimalFormat("0.00").format(numero));
        String letra = numLetra.Convertir(importeLetra, true);
        String firstLtr = letra.substring(0, 1);
        String secondLtrs = letra.substring(1, letra.length() - 5);
        String restLtrs = letra.substring(letra.length() - 5, letra.length());
        firstLtr = firstLtr.toUpperCase();
        secondLtrs = secondLtrs.toLowerCase();
        String res = firstLtr + secondLtrs + restLtrs;
        return res;
    }
    
    public void cancelList(){
        listaTiposDocs = null;
    }

    public RnGcCfdisTbl getRnGcCfdisTbl(java.lang.Integer id) {
        return rnGcCfdisTblFacade.find(id);
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public boolean obtenerClientes(String rfc) {
        //personasRegistradas = personasFacade.obtenerCreadoPor(usuarioFirmado.obtenerIdUsuario());
        RnGcPersonasTbl pruebaPersona = personasFacade.obtenerporRfcCreadoPor(rfc, usuarioFirmado.obtenerIdUsuario());
        //System.out.println("pruebaPersona: " + pruebaPersona.getNombre());
        boolean valor = false;
        if(pruebaPersona != null){
            System.out.println("La persona ya existe");
            valor = true;
        }else{
            System.out.println("La persona no esta registrada");
            valor = false;
        }
        /*for (int i = 0; i < personasRegistradas.size(); i++) {
            if (personasRegistradas.get(i).getRfc().equals(rfc)) {
                System.out.println("La persona ya existe");
                valor = true;
                break;
            } else {
                System.out.println("La persona no esta registrada");
                valor = false;
            }
        }//*/
        return valor;
    }

    public boolean obtenerProductos(String descripcion) {
        //productosRegistrados = productoServicioFacade.obtenerPorUsuario(usuarioFirmado.obtenerIdUsuario());
        RnGcProductserviciosTbl pruebaProdServ = productoServicioFacade.obtenerCreadoPorDescripcion(usuarioFirmado.obtenerIdUsuario(), descripcion);
        System.out.println("pruebaProdServ: " + pruebaProdServ + " | " + pruebaProdServ != null);
        boolean valor = false;
        if(pruebaProdServ != null){
            System.out.println("El producto/Servicio ya existe");
            valor = true;
        }else{
            System.out.println("El producto/Servicio no esxiste");
            valor = false;
        }
        /*for (int i = 0; i < productosRegistrados.size(); i++) {
            if (productosRegistrados.get(i).getDescripcion().equals(descripcion)) {
                System.out.println("El producto/Servicio ya existe");
                valor = true;
                break;
            } else {
                System.out.println("El producto/Servicio no esxiste");
                valor = false;
            }
        }//*/
        return valor;
    }
    
    public void abreModal(){
        System.out.println("listaRFC: " + listaRfc);
    }
    
    public void rfcSeleccionados(){
        System.out.println("rfcSeleccionados: " + rfcSeleccionado);
        RnGcCatalogoCuentasTbl cuentaNueva = new RnGcCatalogoCuentasTbl();
        List<RnGcCatalogoCuentasTbl> listaCatalogoCuentasNuevo = new ArrayList<>();
        RnGcPersonasTbl persona = new RnGcPersonasTbl();
        for(String rfc : rfcSeleccionado){
            String[] parts = rfc.split("-");
            String rfcPart = parts[0];
            persona = personasFacade.obtenerCreadoPorNombre(usuarioFirmado.obtenerIdUsuario(), rfcPart);
            listaCatalogoCuentasNuevo = catalogoCuentasFacade.obtenerCuentasCreadoPorCodigoAgrupador(usuarioFirmado.obtenerIdUsuario(), catalogoCuenta.getCodigoAgrupadorSatId());
            cuentaNueva = new RnGcCatalogoCuentasTbl();

            validarCuentaAcumulativa(catalogoCuenta);
            
            cuentaNueva.setCodigoAgrupadorSatId(catalogoCuenta.getCodigoAgrupadorSatId());
            if(listaCatalogoCuentasNuevo != null && !listaCatalogoCuentasNuevo.isEmpty()){
                listaCatalogoCuentasNuevo = catalogoCuentasFacade.obtenerCuentasCreadoPorCodigoAgrupador(usuarioFirmado.obtenerIdUsuario(), catalogoCuenta.getCodigoAgrupadorSatId());
                System.out.println("listaCatalogoCuentasNuevo: " + listaCatalogoCuentasNuevo.size() + " | " + listaCatalogoCuentasNuevo);
                cuentaNueva.setNumeroCuenta(calcularNumeroCuenta(listaCatalogoCuentasNuevo.get(listaCatalogoCuentasNuevo.size()-1).getNumeroCuenta()));
            }else{
                cuentaNueva.setNumeroCuenta(calcularNumeroCuenta2(catalogoCuenta.getCodigoAgrupadorSatId().getCodigoAgrupador()));
            }
            cuentaNueva.setDescripcionCuenta(persona.getNombre());
            cuentaNueva.setSubCuenta(0);
            if(persona.getTipoPersonaId().getTipoPersona().equals("Cliente")){
                cuentaNueva.setNaturaleza("D");
            }else{
                cuentaNueva.setNaturaleza("A");
            }
            cuentaNueva.setRfc(persona.getRfc());
            cuentaNueva.setInicioVigencia(catalogoCuenta.getInicioVigencia());
            cuentaNueva.setAdicional2(calcularNivel(cuentaNueva.getNumeroCuenta()));
            cuentaNueva.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
            cuentaNueva.setFechaCreacion(new Date());
            cuentaNueva.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
            cuentaNueva.setUltimaFechaActualizacion(new Date());
            
            catalogoCuentasFacade.edit(cuentaNueva);
            cuentaNueva = new RnGcCatalogoCuentasTbl();
            listaCatalogoCuentasNuevo = new ArrayList<>();
            listaRfc.remove(rfc);
        }
        catalogoCuenta = new RnGcCatalogoCuentasTbl();
    }
    
    public void validarCuentaAcumulativa(RnGcCatalogoCuentasTbl numeroCuenta){
        Double numero = Double.parseDouble(numeroCuenta.getCodigoAgrupadorSatId().getCodigoAgrupador());
        Integer cuenta = numero.intValue();
        RnGcCodigoAgrupadorSatTbl codigoAgrupador = new RnGcCodigoAgrupadorSatTbl();
        codigoAgrupador = codigoAgrupadorFacade.getCodigoAgrupador(cuenta.toString());
        List<RnGcCatalogoCuentasTbl> catCuentas = new ArrayList<>();
        catCuentas = catalogoCuentasFacade.obtenerCuentasCreadoPorCodigoAgrupador(usuarioFirmado.obtenerIdUsuario(), codigoAgrupador);
        if(catCuentas != null && !catCuentas.isEmpty()){
            System.out.println("La cuenta acumulativa con el codigo agrupador " + codigoAgrupador.getCodigoAgrupador() + " ya existe");
        }else{
            RnGcCatalogoCuentasTbl cuentaAcumulativa = new RnGcCatalogoCuentasTbl();
            cuentaAcumulativa.setCodigoAgrupadorSatId(codigoAgrupador);
            cuentaAcumulativa.setNumeroCuenta(cuenta.toString());
            cuentaAcumulativa.setDescripcionCuenta("Cuenta " + codigoAgrupador.getNombreCuenta().toUpperCase());
            cuentaAcumulativa.setSubCuenta(0);
            cuentaAcumulativa.setNaturaleza("A");
            cuentaAcumulativa.setRfc("SIn Registro");
            cuentaAcumulativa.setInicioVigencia(numeroCuenta.getInicioVigencia());
            cuentaAcumulativa.setAdicional2("1");
            cuentaAcumulativa.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
            cuentaAcumulativa.setFechaCreacion(new Date());
            cuentaAcumulativa.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
            cuentaAcumulativa.setUltimaFechaActualizacion(new Date());
            catalogoCuentasFacade.edit(cuentaAcumulativa);
        }
    }
    
    public String calcularNumeroCuenta2(String numeroCuenta){
        Double numero = Double.parseDouble(numeroCuenta);
        Integer cuenta = numero.intValue();
        return cuenta.toString() + "-" + "001";
    }
    
    public String calcularNumeroCuenta(String numeroCuenta){
        System.out.println("calcularNumeroCuenta: " + numeroCuenta);
        String[] parts = numeroCuenta.split("-");
        Integer ultimo = Integer.parseInt(parts[parts.length-1])+1;
        String fin = "";
        if(ultimo < 10){
            fin = "00" + ultimo;
        }else if(ultimo < 100){
            fin = "0" + ultimo;
        }else{
            fin = ultimo.toString();
        }
            
        String numeroCuentaFinal = "";
        for(int i = 0; i < parts.length; i++){
            if(i == 0){
                numeroCuentaFinal = numeroCuentaFinal + parts[0] + "-";
            }else if(i == parts.length-1){
                numeroCuentaFinal = numeroCuentaFinal + fin + "-";
            }else{
                numeroCuentaFinal = numeroCuentaFinal + parts[i] + "-";
            }
        }
        return numeroCuentaFinal.substring(0, numeroCuentaFinal.length()-1);
    }
    
    public String calcularNivel(String numeroCuenta){
        char caracter = '-';
        Integer posicion, contador = 1;
        posicion = numeroCuenta.indexOf(caracter);
        while (posicion != -1) {
            contador++;
            posicion = numeroCuenta.indexOf(caracter, posicion + 1);
        }
        return contador.toString();
    }
    
    public boolean validarRegistroInsertar(){
        RnGcUsuariosTbl usuarioId = new RnGcUsuariosTbl();
        usuarioId = rnGcUsuariosTblFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        List<RnGcCatalogoCuentasTbl> listaCuentas = new ArrayList<>();
        listaCuentas = catalogoCuentasFacade.obtenerListadeNumerosCuentas(catalogoCuenta.getNumeroCuenta(), usuarioId);
        System.out.println("Validacion: " + (listaCuentas != null) + " | " + !listaCuentas.isEmpty());
        if(listaCuentas != null && !listaCuentas.isEmpty()){
            System.out.println("No existe el registro con el numeroCuenta: " + catalogoCuenta.getNumeroCuenta());
            return false;
        }else{
            System.out.println("El registro con el numerocuenta " + catalogoCuenta.getNumeroCuenta() + " ya existe");
            return true;
        }
    }

}