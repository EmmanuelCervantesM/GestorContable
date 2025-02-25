package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcTransaccionesTblFacade;
import mx.com.rocketnegocios.entities.RnGcTransaccionesTbl;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import java.util.ArrayList;
import mx.com.rocketnegocios.util.UsuarioFirmado;

//Transacciones
@Named("rnGcTransaccionesTblControllerAdd")
@SessionScoped
public class RnGcTransaccionesTblControllerAdd implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcTransaccionesTblFacade ejbFacade;
    private List<RnGcTransaccionesTbl> items = null;
    private RnGcTransaccionesTbl selected;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();

    public RnGcTransaccionesTblControllerAdd() {
    }

    public RnGcTransaccionesTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcTransaccionesTbl selected) {
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

    private RnGcTransaccionesTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcTransaccionesTbl prepareCreate() {
        selected = new RnGcTransaccionesTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcTransaccionesTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcTransaccionesTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcTransaccionesTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcTransaccionesTbl> getItems() {
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

    public RnGcTransaccionesTbl getRnGcTransaccionesTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcTransaccionesTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcTransaccionesTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcTransaccionesTbl.class)
    public static class RnGcTransaccionesTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcTransaccionesTblController controller = (RnGcTransaccionesTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcTransaccionesTblController");
            return controller.getRnGcTransaccionesTbl(getKey(value));
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
            if (object instanceof RnGcTransaccionesTbl) {
                RnGcTransaccionesTbl o = (RnGcTransaccionesTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcTransaccionesTbl.class.getName()});
                return null;
            }
        }
    }

    //Transacciones
    //Filtro por fecha
    public boolean filterByFecha(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.isEmpty()) {
            return true;
        }
        if (value == null) {
            return false;
        }
        //DateFormat df = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM,Locale.getDefault());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date filterDate = (Date) value;
        Date dateFrom;
        Date dateTo;
        try {
            String fromPart = filterText.substring(0, filterText.indexOf("-"));
            String toPart = filterText.substring(filterText.indexOf("-") + 1);
            //System.out.println("fromPart:"+fromPart+"|toPart:"+toPart+"|hoy:"+df.format(new Date()));
            dateFrom = fromPart.isEmpty() ? null : df.parse(fromPart);
            dateTo = toPart.isEmpty() ? null : df.parse(toPart);
        } catch (ParseException pe) {
            Logger.getLogger(RnGcTransaccionesTblController.class.getName()).log(Level.SEVERE, null, pe);
            return false;
        }
        return (dateFrom == null || filterDate.after(dateFrom)) && (dateTo == null || filterDate.before(dateTo));
    }

    public void leerXML() throws JDOMException, IOException, ParseException {
        prepareCreate();
        String pathorigen = "C:\\Users\\Developer1\\Documents\\NetBeansProjects\\RN_AdmonContable_Repo\\RN_GC\\CFDIs";
        String pathdestino = "C:\\Users\\Developer1\\Documents\\NetBeansProjects\\RN_AdmonContable_Repo\\RN_GC\\xmls_Procesados";        
        java.nio.file.Path destino = FileSystems.getDefault().getPath(pathdestino);
        String[] archivo = getFiles(pathorigen);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SAXBuilder builder = new SAXBuilder();
        // Selecionar Archivo Correcto
        if (archivo != null) {
            for (int z = 0; z < archivo.length; z++) {
                System.out.println("<------Leyendo archivos de: " + pathorigen + "------>");
                System.out.println("\nArchivo No" + (z + 1) + ": " + archivo[z]);
                java.nio.file.Path origen = FileSystems.getDefault().getPath(archivo[z]);
                File xmlFile = new File(archivo[z]);
                try {
                    Document document = (Document) builder.build(xmlFile);
                    Element rootNode = document.getRootElement();
                    String nombreXml = (rootNode.getName());
                    String formaPago = rootNode.getAttributeValue("FormaPago");
                    String metodoPago = rootNode.getAttributeValue("MetodoPago");
                    String tipoComprobante = rootNode.getAttributeValue("TipoDeComprobante");
                    selected.setTipoComprobante(tipoComprobante);
                    String lugarExpedicion = rootNode.getAttributeValue("LugarExpedicion");
                    selected.setLugarExpedicion(Integer.valueOf(lugarExpedicion));
                    String fecha = rootNode.getAttributeValue("Fecha");
                    String version = rootNode.getAttributeValue("Version");
                    String moneda = rootNode.getAttributeValue("Moneda");
                    selected.setMoneda(moneda);
                    selected.setFechaExpedicion(formatoFecha.parse(fecha));
                    String condicionpago = rootNode.getAttributeValue("CondicionesDePago");
                    selected.setFormaPago(formaPago);
                    selected.setMetodoPago(metodoPago);
                    selected.setCondicionPago(condicionpago);
                    String folio = rootNode.getAttributeValue("Folio");
                    selected.setFolio(folio);
                    String subtotal = rootNode.getAttributeValue("SubTotal");
                    Float subtot = Float.parseFloat(subtotal);
                    String total = rootNode.getAttributeValue("Total");
                    System.out.println(nombreXml + "\n" + formaPago + "\n" + metodoPago + "\n" + tipoComprobante + "\n" + lugarExpedicion + "\n" + fecha + "\n" + version + "\n" + moneda + "\n" + condicionpago + "\n" + subtotal + "\n" + total);
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
                                if (dato1.getAttributeValue("UUID") != null) {
                                    System.out.println(dato1.getAttributeValue("UUID"));
                                    selected.setTipoRelacion(dato1.getAttributeValue("UUID"));
                                }
                            }
                        }
                        //Emisor
                        if (valor.equals("Emisor")) {
                            System.out.println("Datos Emisor");
                            System.out.println("RFC_E: " + elementoCFDI.getAttributeValue("Rfc"));
                            selected.setRfcEmisor(elementoCFDI.getAttributeValue("Rfc"));
                            System.out.println("Nombre_E: " + elementoCFDI.getAttributeValue("Nombre"));
                            selected.setNombreEmisor(elementoCFDI.getAttributeValue("Nombre"));
                            System.out.println("RegimenFiscal: " + elementoCFDI.getAttributeValue("RegimenFiscal"));
                            selected.setClaveRegimenFiscal(elementoCFDI.getAttributeValue("RegimenFiscal"));
                        }
                        //Receptor
                        if (valor.equals("Receptor")) {
                            System.out.println("Datos Receptor");
                            System.out.println("RFC_R: " + elementoCFDI.getAttributeValue("Rfc"));
                            selected.setRfcReceptor(elementoCFDI.getAttributeValue("Rfc"));
                            System.out.println("Nombre_R: " + elementoCFDI.getAttributeValue("Nombre"));
                            selected.setNombreReceptor(elementoCFDI.getAttributeValue("Nombre"));
                            System.out.println("UsoCFDI: " + elementoCFDI.getAttributeValue("UsoCFDI"));
                            selected.setUsoCfdi(elementoCFDI.getAttributeValue("UsoCFDI"));
                        }
                        //Conceptos
                        List conceptos = elementoCFDI.getChildren();
                        if (valor.equals("Conceptos")) {
                            for (int j = 0; j < conceptos.size(); j++) {
                                Element dato1 = (Element) conceptos.get(j);
                                String concep = dato1.getName();
                                // Concepto
                                System.out.println("Datos Concepto");
                                System.out.println("Clave Prod / Ser:" + dato1.getAttributeValue("ClaveProdServ"));
                                Integer claveprodserv = Integer.valueOf(dato1.getAttributeValue("ClaveProdServ"));
                                if (concep.equals("NoIdentificacion")) {
                                    System.out.println("No. de identificacion: " + dato1.getAttributeValue("NoIdentificacion"));
                                    Integer noidentifi = Integer.valueOf(dato1.getAttributeValue("NoIdentificacion"));
                                }
                                System.out.println("Cantidad: " + dato1.getAttributeValue("Cantidad"));
                                System.out.println("Clave Unidad: " + dato1.getAttributeValue("ClaveUnidad"));
                                if (concep.equals("Unidad")) {
                                    System.out.println("Unidad: " + dato1.getAttributeValue("Unidad"));
                                    Integer unidad = Integer.valueOf(dato1.getAttributeValue("Unidad"));
                                }
                                System.out.println("Descripcion: " + dato1.getAttributeValue("Descripcion"));
                                System.out.println("Valor Unitario: " + dato1.getAttributeValue("ValorUnitario"));
                                float valorunit = Float.parseFloat(dato1.getAttributeValue("ValorUnitario"));
                                System.out.println("Importe: " + dato1.getAttributeValue("Importe"));
                                float flotante = Float.parseFloat(dato1.getAttributeValue("Importe"));
                                selected.setImporte(flotante);
                                //Impuestos
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
                                                    float base1 = Float.parseFloat(trasla.getAttributeValue("Base"));
                                                    System.out.println("Impuesto: " + trasla.getAttributeValue("Impuesto"));
                                                    float impuesto1 = Float.parseFloat(trasla.getAttributeValue("Impuesto"));
                                                    System.out.println("Tipo Factor: " + trasla.getAttributeValue("TipoFactor"));
                                                    if (trasla.getAttributeValue("Importe") == null) {
                                                        selected.setImporte(159);
                                                    } else {
                                                        System.out.println("Importe: " + trasla.getAttributeValue("Importe"));
                                                        float importe = Float.parseFloat(trasla.getAttributeValue("Importe"));
                                                    }

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
                            }
                        }
                        //Impuestos
                        List impues = elementoCFDI.getChildren();
                        if (valor.equals("Impuestos")) {
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
                                System.out.println("UUID: " + complem.getAttributeValue("UUID") + "\n");
                                selected.setUuid(complem.getAttributeValue("UUID"));
                            }
                        }                        
                    }
                } catch (JDOMException ex) {
                    ex.printStackTrace();
                }                
                create();
                // Mover el archivo Procesado
                Files.move(origen, destino.resolve(origen.getFileName()));
                System.out.println("Archivo: " + archivo[z] + " movido");
            }

        }
    }

    public static String[] getFiles(String dir_path) {
        String[] arr_res = null;
        File xml = new File(dir_path);
        if (xml.isDirectory()) {
            List<String> res = new ArrayList<>();
            File[] arr_content = xml.listFiles();
            int size = arr_content.length;
            for (int i = 0; i < size; i++) {
                if (arr_content[i].isFile()) {
                    res.add(arr_content[i].toString());
                }
            }
            arr_res = res.toArray(new String[0]);
        } else {
            System.err.println("Path No Valido");
        }

        return arr_res;
    }

}
