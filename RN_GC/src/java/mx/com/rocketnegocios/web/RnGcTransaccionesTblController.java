package mx.com.rocketnegocios.web;

import java.io.File;
import java.io.IOException;
import mx.com.rocketnegocios.entities.RnGcTransaccionesTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcTransaccionesTblFacade;

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
import javax.faces.view.ViewScoped;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

@Named("rnGcTransaccionesTblController")
@ViewScoped
public class RnGcTransaccionesTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcTransaccionesTblFacade ejbFacade;
    private List<RnGcTransaccionesTbl> items = null;
    private RnGcTransaccionesTbl selected;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();

    public RnGcTransaccionesTblController() {
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
        System.out.println("Create()--");
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
                ex.printStackTrace();
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

        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SAXBuilder builder = new SAXBuilder();
        // Selecionar Archivo Correcto
        File xmlFile = new File("C:\\Users\\Developer1\\Documents\\NetBeansProjects\\RN_AdmonContable_Repo\\RN_GC\\prueba.xml");
        try {
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            String nombreXml = (rootNode.getName());
            String formaPago = rootNode.getAttributeValue("FormaPago");
            String metodoPago = rootNode.getAttributeValue("MetodoPago");
            String tipoComprobante = rootNode.getAttributeValue("TipoDeComprobante");
            String lugarExpedicion = rootNode.getAttributeValue("LugarExpedicion");
            selected.setTipoComprobante(lugarExpedicion);
            Integer lugexp = Integer.valueOf(lugarExpedicion);
            String fecha = rootNode.getAttributeValue("Fecha");
            String version = rootNode.getAttributeValue("Version");
            String moneda = rootNode.getAttributeValue("Moneda");
            selected.setMoneda(moneda);
            selected.setFechaExpedicion(formatoFecha.parse(fecha));
            String condicionpago = rootNode.getAttributeValue("CondicionesDePago");
            selected.setFormaPago(formaPago);
            selected.setMetodoPago(metodoPago);
            selected.setCondicionPago(condicionpago);
            String foli = rootNode.getAttributeValue("Folio");
            selected.setFolio(foli);
            selected.setLugarExpedicion(lugexp);
            String subtotal = rootNode.getAttributeValue("SubTotal");
            Float subtot = Float.parseFloat(subtotal);
            //trxlineas.setSubtotal(subtot);
            String total = rootNode.getAttributeValue("Total");
            //trxlineas.setTotal(Float.parseFloat(rootNode.getAttributeValue("Total")));
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
                        }
                    }
                }
                //Emisor
                if (valor.equals("Emisor")) {
                    System.out.println("Datos Emisor");
                    System.out.println("RFC_E: " + elementoCFDI.getAttributeValue("Rfc"));
                    System.out.println("Nombre_E: " + elementoCFDI.getAttributeValue("Nombre"));
                    selected.setRfcEmisor(elementoCFDI.getAttributeValue("Rfc"));
                    selected.setNombreEmisor(elementoCFDI.getAttributeValue("Nombre"));
                    System.out.println("RegimenFiscal: " + elementoCFDI.getAttributeValue("RegimenFiscal"));
                    selected.setClaveRegimenFiscal(elementoCFDI.getAttributeValue("RegimenFiscal"));
                }
                //Receptor
                if (valor.equals("Receptor")) {
                    System.out.println("Datos Receptor");
                    System.out.println("RFC_R: " + elementoCFDI.getAttributeValue("Rfc"));
                    System.out.println("Nombre_R: " + elementoCFDI.getAttributeValue("Nombre"));
                    selected.setRfcReceptor(elementoCFDI.getAttributeValue("Rfc"));
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
                        //trxlineas.setClaveProdServ(claveprodserv);
                        /*if(concep.equals("NoIdentificacion")){
                            System.out.println("No. de identificacion: " + dato1.getAttributeValue("NoIdentificacion"));
                            Integer noidentifi = Integer.valueOf(dato1.getAttributeValue("NoIdentificacion"));
                            selected.setNoIdentificacion(noidentifi);
                        } */
                        System.out.println("Cantidad: " + dato1.getAttributeValue("Cantidad"));
                        //trxlineas.setCantidad(Float.valueOf(dato1.getAttributeValue("Cantidad")));
                        System.out.println("Clave Unidad: " + dato1.getAttributeValue("ClaveUnidad"));
                        //trxlineas.setClaveUnidad(dato1.getAttributeValue("ClaveUnidad"));
                        /*if(concep.equals("Unidad")){
                            System.out.println("Unidad: " + dato1.getAttributeValue("Unidad"));
                            Integer unidad = Integer.valueOf(dato1.getAttributeValue("Unidad"));
                            selected.setUnidad(unidad);
                        } */
                        System.out.println("Descripcion: " + dato1.getAttributeValue("Descripcion"));
                        //trxlineas.setDescripcion(dato1.getAttributeValue("Descripcion"));
                        System.out.println("Valor Unitario: " + dato1.getAttributeValue("ValorUnitario"));
                        //trxlineas.setValorUnit(Float.parseFloat(dato1.getAttributeValue("ValorUnitario")));
                        System.out.println("Importe: " + dato1.getAttributeValue("Importe"));
                        //trxlineas.setImporte(Float.parseFloat(dato1.getAttributeValue("Importe")));
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
                                            //trxlineas.setBase(Float.parseFloat(trasla.getAttributeValue("Base")));
                                            System.out.println("Impuesto: " + trasla.getAttributeValue("Impuesto"));
                                            //selected.setImpuesto(Integer.valueOf(trasla.getAttributeValue("Impuesto")));
                                            System.out.println("Tipo Factor: " + trasla.getAttributeValue("TipoFactor"));
                                            //trxlineas.setTipoFactor(trasla.getAttributeValue("TipoFactor"));
                                            System.out.println("Tasa Cuota: " + trasla.getAttributeValue("TasaOCuota"));
                                            //trxlineas.setTipoTasa(Float.parseFloat(trasla.getAttributeValue("TasaOCuota")));
                                            System.out.println("Importe: " + trasla.getAttributeValue("Importe"));
                                            //selected.setImporte(Float.parseFloat(trasla.getAttributeValue("Importe")));
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
                    System.out.println("Total Impuestos Trasladados");
                    System.out.println("Total impuestos Trasladados: " + elementoCFDI.getAttributeValue("TotalImpuestosTrasladados"));
                    Float totalimpues = Float.parseFloat(elementoCFDI.getAttributeValue("TotalImpuestosTrasladados"));
                    //trxlineas.setTotalImpuesto(totalimpues);
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
                        selected.setUuid(complem.getAttributeValue("UUID"));
                    }
                }
                // Mover el archivoa Procesados
            }
        } catch (IOException | NumberFormatException | JDOMException ex) {
            ex.printStackTrace();
        }

    }

}
