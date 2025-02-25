package mx.com.rocketnegocios.web;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import mx.com.rocketnegocios.beans.RnGcFacturasTblFacade;
import mx.com.rocketnegocios.beans.RnGcFacturaslineasTblFacade;
import mx.com.rocketnegocios.entities.RnGcFacturasTbl;
import mx.com.rocketnegocios.entities.RnGcFacturaslineasTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import static mx.com.rocketnegocios.web.RnGcFacturaslineasTblController.getFiles;
import mx.com.rocketnegocios.web.util.JsfUtil;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named(value = "cargarFacturasController")
@SessionScoped

public class cargarFacturasController implements Serializable {

    @EJB
    private RnGcFacturaslineasTblFacade rnGcFacturaslineasFacade;

    @EJB
    private RnGcFacturasTblFacade rnGcFacturasTblFacade;

    private RnGcFacturasTbl facturaId = null;
    private List<RnGcFacturaslineasTbl> lineasConceptos = null;
    private RnGcFacturaslineasTbl lineasConcepto = null;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private UploadedFile file;

    public RnGcFacturasTbl getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(RnGcFacturasTbl facturaId) {
        this.facturaId = facturaId;
    }

    public List<RnGcFacturaslineasTbl> getLineasConceptos() {
        return lineasConceptos;
    }

    public void setLineasConceptos(List<RnGcFacturaslineasTbl> lineasConceptos) {
        this.lineasConceptos = lineasConceptos;
    }

    public RnGcFacturaslineasTbl getLineasConcepto() {
        return lineasConcepto;
    }

    public void setLineasConcepto(RnGcFacturaslineasTbl lineasConcepto) {
        this.lineasConcepto = lineasConcepto;
    }

    public cargarFacturasController() {
    }

    public void leerXML(FileUploadEvent event) {

        facturaId = new RnGcFacturasTbl(0, "", "", "", "", "", 1, new Date(), "", "", "", "", "", 0.0f, usuarioFirmado.obtenerIdUsuario(), new Date(), usuarioFirmado.obtenerIdUsuario(), new Date());
        lineasConceptos = new ArrayList<>();

        /*String pathorigen = "C:\\Users\\Developer1\\Documents\\NetBeansProjects\\RN_AdmonContable_Repo\\RN_GC\\CFDIs";
        String pathdestino = "C:\\Users\\Developer1\\Documents\\NetBeansProjects\\RN_AdmonContable_Repo\\RN_GC\\xmls_Procesados";
        System.out.println("<------ Leyendo archivos de: " + pathorigen + "------>");
        java.nio.file.Path destino = FileSystems.getDefault().getPath(pathdestino);
        String[] archivo = getFiles(pathorigen);*/
        String[] archivo = new String[1];
        if (event != null) {
            System.out.println("file: " + event.getFile().getFileName());
            archivo[0] = event.getFile().getFileName();
        }
        System.out.println("archivo[0]:" + archivo[0]);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SAXBuilder builder = new SAXBuilder();
        // Selecionar Archivos
        if (archivo != null) {
            for (int z = 0; z < archivo.length; z++) {
                //File xmlFile = new File(archivo[z]);
                System.out.println("Archivo No. " + (z + 1) + " " + archivo[z] + " leido: ");
                try {
                    //Document document = (Document) builder.build(xmlFile);
                    Document document = (Document) builder.build(event.getFile().getInputstream());
                    Element rootNode = document.getRootElement();
                    String nombreXml = (rootNode.getName());
                    String formaPago = rootNode.getAttributeValue("FormaPago");
                    String metodoPago = rootNode.getAttributeValue("MetodoPago");
                    String tipoComprobante = rootNode.getAttributeValue("TipoDeComprobante");
                    String lugarExpedicion = rootNode.getAttributeValue("LugarExpedicion");
                    facturaId.setTipoComprobante(tipoComprobante);
                    Integer lugexp = Integer.valueOf(lugarExpedicion);
                    String fecha = rootNode.getAttributeValue("Fecha");
                    String version = rootNode.getAttributeValue("Version");
                    String moneda = rootNode.getAttributeValue("Moneda");
                    facturaId.setMoneda(moneda);
                    facturaId.setFechaExpedicion(formatoFecha.parse(fecha));
                    String condicionpago = rootNode.getAttributeValue("CondicionesDePago");
                    facturaId.setFormaPago(formaPago);
                    facturaId.setMetodoPago(metodoPago);
                    facturaId.setCondicionPago(condicionpago);
                    String foli = rootNode.getAttributeValue("Folio");
                    facturaId.setFolio(foli);
                    facturaId.setLugarExpedicion(lugexp);
                    String subtotal = rootNode.getAttributeValue("SubTotal");
                    String total = rootNode.getAttributeValue("Total");
                    facturaId.setImporte(Float.valueOf(total));
                    System.out.println(nombreXml + "\n" + formaPago + "\n" + metodoPago + "\n" + tipoComprobante + "\n" + lugarExpedicion + "\n" + fecha + "\n" + version + "\n" + moneda + "\n" + condicionpago + "\n" + foli + "\n" + subtotal + "\n" + total);
                    System.out.println("fecha: " + fecha);
                    List list = rootNode.getChildren();
                    for (int i = 0; i < list.size(); i++) {
                        Element elementoCFDI = (Element) list.get(i);
                        String valor = elementoCFDI.getName();
                        //CfdiRelacionados
                        List relacion = elementoCFDI.getChildren();
                        if (valor.equals("CfdiRelacionados")) {
                            System.out.println("Relacion CFDIs");
                            System.out.println("Tipo de Relacion: " + elementoCFDI.getAttributeValue("TipoRelacion"));
                            for (int j = 0; j < relacion.size(); j++) {
                                Element dato1 = (Element) relacion.get(j);
                                String relac = dato1.getName();
                                if (relac.equals("CfdiRelacionado")) {
                                    System.out.println(dato1.getAttributeValue("UUID"));
                                    facturaId.setTipoRelacion(dato1.getAttributeValue("UUID"));
                                }
                            }
                        }
                        //Emisor
                        if (valor.equals("Emisor")) {
                            System.out.println("Datos Emisor");
                            System.out.println("RFC_E: " + elementoCFDI.getAttributeValue("Rfc"));
                            System.out.println("Nombre_E: " + elementoCFDI.getAttributeValue("Nombre"));
                            facturaId.setRfcEmisor(elementoCFDI.getAttributeValue("Rfc"));
                            facturaId.setNombreEmisor(elementoCFDI.getAttributeValue("Nombre"));
                            System.out.println("RegimenFiscal: " + elementoCFDI.getAttributeValue("RegimenFiscal"));
                            facturaId.setClaveRegimenFiscal(elementoCFDI.getAttributeValue("RegimenFiscal"));
                        }
                        //Receptor
                        if (valor.equals("Receptor")) {
                            System.out.println("Datos Receptor");
                            System.out.println("RFC_R: " + elementoCFDI.getAttributeValue("Rfc"));
                            System.out.println("Nombre_R: " + elementoCFDI.getAttributeValue("Nombre"));
                            facturaId.setRfcReceptor(elementoCFDI.getAttributeValue("Rfc"));
                            facturaId.setNombreReceptor(elementoCFDI.getAttributeValue("Nombre"));
                            System.out.println("UsoCFDI: " + elementoCFDI.getAttributeValue("UsoCFDI"));
                            facturaId.setUsoCfdi(elementoCFDI.getAttributeValue("UsoCFDI"));
                        }
                        //Conceptos
                        List conceptos = elementoCFDI.getChildren();
                        if (valor.equals("Conceptos")) {
                            for (int j = 0; j < conceptos.size(); j++) {
                                Element dato1 = (Element) conceptos.get(j);
                                String concep = dato1.getName();
                                lineasConcepto = new RnGcFacturaslineasTbl(1, 1, 0.0f, "", "", 0.0f, 0.0f, 0.0f, 1, "", usuarioFirmado.obtenerIdUsuario(), new Date(), usuarioFirmado.obtenerIdUsuario(), new Date());
                                // Concepto
                                System.out.println("Datos Concepto");
                                System.out.println("Clave Prod / Ser:" + dato1.getAttributeValue("ClaveProdServ"));
                                lineasConcepto.setClaveProdServ(Integer.valueOf(dato1.getAttributeValue("ClaveProdServ")));
                                System.out.println("No. de identificacion: " + dato1.getAttributeValue("NoIdentificacion"));
                                lineasConcepto.setNoIdentificacion(dato1.getAttributeValue("NoIdetificacion"));
                                System.out.println("Cantidad: " + dato1.getAttributeValue("Cantidad"));
                                lineasConcepto.setCantidad(Float.valueOf(dato1.getAttributeValue("Cantidad")));
                                System.out.println("Clave Unidad: " + dato1.getAttributeValue("ClaveUnidad"));
                                lineasConcepto.setClaveUnidad(dato1.getAttributeValue("ClaveUnidad"));
                                System.out.println("Unidad: " + dato1.getAttributeValue("Unidad"));
                                lineasConcepto.setUnidad(dato1.getAttributeValue("Unidad"));
                                System.out.println("Descripcion: " + dato1.getAttributeValue("Descripcion"));
                                lineasConcepto.setDescripcion(dato1.getAttributeValue("Descripcion"));
                                System.out.println("Valor Unitario: " + dato1.getAttributeValue("ValorUnitario"));
                                lineasConcepto.setValorUnit(Float.valueOf(dato1.getAttributeValue("ValorUnitario")));
                                System.out.println("Importe: " + dato1.getAttributeValue("Importe"));
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
                                        if (impues.equals("Traslados")) {
                                            for (int l = 0; l < traslado.size(); l++) {
                                                Element trasla = (Element) traslado.get(l);
                                                String tras = trasla.getName();
                                                if (tras.equals("Traslado")) {
                                                    System.out.println("Datos Traslado_Concept");
                                                    System.out.println("Base: " + trasla.getAttributeValue("Base"));
                                                    lineasConcepto.setBase(Float.valueOf(trasla.getAttributeValue("Base")));
                                                    System.out.println("Impuesto: " + trasla.getAttributeValue("Impuesto"));
                                                    lineasConcepto.setImpuesto(Integer.valueOf(trasla.getAttributeValue("Impuesto")));
                                                    System.out.println("Tipo Factor: " + trasla.getAttributeValue("TipoFactor"));
                                                    lineasConcepto.setTipoFactor(trasla.getAttributeValue("TipoFactor"));
                                                    if (trasla.getAttributeValue("TasaOCuota") != null) {
                                                        System.out.println("Tasa Cuota: " + trasla.getAttributeValue("TasaOCuota"));
                                                        lineasConcepto.setTipoTasa(Float.valueOf(trasla.getAttributeValue("TasaOCuota")));
                                                    } else {
                                                        float Tc = 0.00f;
                                                        System.out.println("Tasa Cuota: " + Tc);
                                                        lineasConcepto.setTipoTasa(Tc);
                                                    }
                                                    System.out.println("Importe: " + trasla.getAttributeValue("Importe"));
                                                    lineasConcepto.setImporteImpuesto(trasla.getAttributeValue("Importe"));

                                                }
                                            }
                                        }
                                        //Retenciones
                                        List retencion = dato3.getChildren();
                                        if (impues.equals("Retenciones")) {
                                            for (int l = 0; l < retencion.size(); l++) {
                                                Element reten = (Element) retencion.get(l);
                                                String ret = reten.getName();
                                                if (ret.equals("Retencion")) {
                                                    System.out.println("Datos Retenciones_Concept");
                                                    System.out.println("Base: " + reten.getAttributeValue("Base"));
                                                    Float base = Float.parseFloat(reten.getAttributeValue("Base"));
                                                    System.out.println("Impuesto: " + reten.getAttributeValue("Impuesto"));
                                                    Float impuesto = Float.parseFloat(reten.getAttributeValue("Impuesto"));
                                                    System.out.println("Tipo Factor: " + reten.getAttributeValue("TipoFactor"));
                                                    System.out.println("Tasa Cuota: " + reten.getAttributeValue("TasaOCuota"));
                                                    Float tasaocuot = Float.parseFloat(reten.getAttributeValue("TasaOCuota"));
                                                    System.out.println("Importe: " + reten.getAttributeValue("Importe"));
                                                    Float importe = Float.parseFloat(reten.getAttributeValue("Importe"));
                                                }
                                            }
                                        }

                                    }
                                }
                                lineasConceptos.add(lineasConcepto);
                            }
                        }
                        //Impuestos
                        List impues = elementoCFDI.getChildren();
                        if (valor.equals("Impuestos")) {
                            System.out.println("Total Impuestos Trasladados");
                            System.out.println("Total impuestos Trasladados: " + elementoCFDI.getAttributeValue("TotalImpuestosTrasladados"));
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
                                            System.out.println("Datos Retencion");
                                            System.out.println("Impuesto: " + reten.getAttributeValue("Impuesto"));
                                            System.out.println("Importe: " + reten.getAttributeValue("Importe"));
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
                                            System.out.println("Datos Traslado");
                                            System.out.println("Impuesto: " + trasla.getAttributeValue("Impuesto"));
                                            System.out.println("Tipo Factor: " + trasla.getAttributeValue("TipoFactor"));
                                            System.out.println("Tasa Cuota: " + trasla.getAttributeValue("TasaOCuota"));
                                            System.out.println("Importe: " + trasla.getAttributeValue("Importe"));
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
                                System.out.println("Datos Complemento");
                                System.out.println("UUID: " + complem.getAttributeValue("UUID"));
                                facturaId.setUuid(complem.getAttributeValue("UUID"));
                            }
                        }
                        // Mover archivos Procesados
                    }
                } catch (IOException | NumberFormatException | JDOMException ex) {
                    Logger.getLogger(cargarArchivosController.class.getName()).log(Level.SEVERE, null, ex);
                    JsfUtil.addErrorMessage(ex, "No fue posible cargar el archivo. Favor de revisar que el tipo corresponda a un CFDI");
                } catch (ParseException ex) {
                    Logger.getLogger(cargarArchivosController.class.getName()).log(Level.SEVERE, null, ex);
                    JsfUtil.addErrorMessage(ex, "No fue posible cargar el archivo. Favor de revisar el formato del CFDI");
                }
                //Files.move(origen, destino.resolve(origen.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                //System.out.println("<---Archivo " + archivo[z] + " leido--->");

                //rnGcFacturasTblFacade.edit(facturaId);
                facturaId = rnGcFacturasTblFacade.refreshFromDB(facturaId);

                System.out.println("facturaId: " + facturaId.getId());
                for (int i = 0; i < lineasConceptos.size(); i++) {
                    System.out.println("Linea " + (i + 1) + ": " + lineasConceptos.get(i).getDescripcion());
                    lineasConcepto = lineasConceptos.get(i);
                    lineasConcepto.setRngcfacturasId(facturaId);
                    rnGcFacturaslineasFacade.edit(lineasConcepto);
                    System.out.println("facturaId: " + facturaId.getId() + "lineasConceptoId: " + lineasConcepto.getId());
                    lineasConcepto = null;
                }
                JsfUtil.addSuccessMessage("Archivo Cargado Correctamente");
                lineasConceptos.clear();
                lineasConceptos = null;
                facturaId = null;
            }
        } else {
            JsfUtil.addErrorMessage("No ha sido posible cargar el archivo.");
        }

    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
