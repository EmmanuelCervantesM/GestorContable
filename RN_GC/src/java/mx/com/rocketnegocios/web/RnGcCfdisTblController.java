package mx.com.rocketnegocios.web;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import mx.com.rocketnegocios.entities.RnGcCfdisTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcCfdisTblFacade;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.persistence.NoResultException;
import mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade;
import mx.com.rocketnegocios.entities.RnGcArchivosTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named("rnGcCfdisTblController")
@SessionScoped
public class RnGcCfdisTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcCfdisTblFacade ejbFacade;

    @EJB
    private RnGcUsuariosTblFacade usuarioFacade;

    private List<RnGcCfdisTbl> items = null;
    private List<RnGcCfdisTbl> itemsRecibidos = null;
    private List<RnGcCfdisTbl> itemsEmitidos;
    private List<RnGcCfdisTbl> itemsPPD;
    private RnGcCfdisTbl selected;
    private final UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private final List<RnGcArchivosTbl> itemsDocs = null;
    private StreamedContent downLoadFile;
    private RnGcUsuariosTbl usuarioId;
    private List<RnGcCfdisTbl> filteredEmitidos;
    private List<RnGcCfdisTbl> filteredRecibidos;
    private List<RnGcCfdisTbl> filteredCfdis;
    private RnGcArchivosTbl archivoId;
    private Double total;
    private Double totalFormat;
    private Double totalEmitidos;
    private List<RnGcUsuariosTbl> listaFinalFinal = null;
    private List<RnGcUsuariosTbl> listaGlobalUsuarios = new ArrayList<>();
    private List<SelectItem> listaGruposGlobal;
    private List<RnGcCfdisTbl> listaGlobalRFCEmisor = new ArrayList<>();
    private List<RnGcCfdisTbl> listaGlobalRFCReceptor = new ArrayList<>();
    private List<SelectItem> listaUsuariosAgrupados;
    private RnGcUsuariosTbl usuario;

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getTotalFormat() {
        return totalFormat;
    }

    public void setTotalFormat(Double totalFormat) {
        this.totalFormat = totalFormat;
    }

    public Double getTotalEmitidos() {
        return totalEmitidos;
    }

    public void setTotalEmitidos(Double totalEmitidos) {
        this.totalEmitidos = totalEmitidos;
    }

    public List<RnGcUsuariosTbl> getListaFinalFinal() {
        return listaFinalFinal;
    }

    public void setListaFinalFinal(List<RnGcUsuariosTbl> listaFinalFinal) {
        this.listaFinalFinal = listaFinalFinal;
    }

    public List<RnGcUsuariosTbl> getListaGlobalUsuarios() {
        return listaGlobalUsuarios;
    }

    public void setListaGlobalUsuarios(List<RnGcUsuariosTbl> listaGlobalUsuarios) {
        this.listaGlobalUsuarios = listaGlobalUsuarios;
    }

    public List<SelectItem> getListaGruposGlobal() {
        return listaGruposGlobal;
    }

    public List<RnGcCfdisTbl> getListaGlobalRFCEmisor() {
        return listaGlobalRFCEmisor;
    }

    public void setListaGlobalRFCEmisor(List<RnGcCfdisTbl> listaGlobalRFCEmisor) {
        this.listaGlobalRFCEmisor = listaGlobalRFCEmisor;
    }

    public List<RnGcCfdisTbl> getListaGlobalRFCReceptor() {
        return listaGlobalRFCReceptor;
    }

    public void setListaGlobalRFCReceptor(List<RnGcCfdisTbl> listaGlobalRFCReceptor) {
        this.listaGlobalRFCReceptor = listaGlobalRFCReceptor;
    }

    public List<SelectItem> getListaUsuariosAgrupados() {
        return listaUsuariosAgrupados;
    }

    public void setListaUsuariosAgrupados(List<SelectItem> listaUsuariosAgrupados) {
        this.listaUsuariosAgrupados = listaUsuariosAgrupados;
    }

    public RnGcUsuariosTbl getUsuario() {
        return usuario;
    }

    public void setUsuario(RnGcUsuariosTbl usuario) {
        this.usuario = usuario;
    }

    public RnGcCfdisTblController() {
    }

    public RnGcCfdisTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcCfdisTbl selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        selected.setUltimaFechaActualizacion(new Date());
    }

    public List<RnGcCfdisTbl> getItemsRecibidos() {
        if (itemsEmitidos == null) {
            this.itemsEmitidos = new ArrayList<>();
        }
        System.out.println("itemsEmitidos: " + itemsEmitidos.size());
        return itemsRecibidos;
    }

    public void setItemsRecibidos(List<RnGcCfdisTbl> itemsRecibidos) {
        this.itemsRecibidos = itemsRecibidos;
    }

    public List<RnGcCfdisTbl> getItemsEmitidos() {
        if(itemsEmitidos == null)
            this.itemsEmitidos = new ArrayList<>();
        return itemsEmitidos;
    }

    public void setItemsEmitidos(List<RnGcCfdisTbl> itemsEmitidos) {
        this.itemsEmitidos = itemsEmitidos;
    }

    public List<RnGcCfdisTbl> getItemsPPD() {
        if(itemsPPD == null)
            this.itemsPPD = new ArrayList<>();
        return itemsPPD;
    }

    public void setItemsPPD(List<RnGcCfdisTbl> itemsPPD) {
        this.itemsPPD = itemsPPD;
    }

    protected void initializeEmbeddableKey() {
        selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        selected.setFechaCreacion(new Date());
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

    public void setFilteredRecibidos(List<RnGcCfdisTbl> filteredRecibidos) {
        this.filteredRecibidos = filteredRecibidos;
    }

    public RnGcArchivosTbl getArchivoId() {
        return archivoId;
    }

    public void setArchivoId(RnGcArchivosTbl archivoId) {
        this.archivoId = archivoId;
    }

    private RnGcCfdisTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcUsuariosTbl getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(RnGcUsuariosTbl usuarioId) {
        this.usuarioId = usuarioId;
    }

    public RnGcCfdisTbl prepareCreate() {
        selected = new RnGcCfdisTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCfdisTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCfdisTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcCfdisTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcCfdisTbl> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                System.out.println("Probando: " + selected.getId() + " | " + selected.getUuid() + " | "
                        + selected.getRfcEmisor() + " | " + selected.getNombreEmisor() + " | "
                        + selected.getRfcReceptor() + " | " + selected.getNombreReceptor() + " | "
                        + selected.getFolio() + " | " + selected.getSerie() + " | "
                        + selected.getLugarExpedicion() + " | " + selected.getFechaExpedicion() + " | "
                        + selected.getTipoComprobante() + " | " + selected.getClaveRegimenFiscal() + " | "
                        + selected.getUsoCfdi() + " | " + selected.getTipoRelacion() + " | "
                        + selected.getFormaPago() + " | " + selected.getMetodoPago() + " | "
                        + selected.getCondicionPago() + " | " + selected.getMoneda() + " | "
                        + selected.getImporte() + " | " + selected.getCreadoPor() + " | "
                        + selected.getFechaCreacion() + " | " + selected.getUltimaActualizacionPor() + " | "
                        + selected.getUltimaFechaActualizacion() + " | " + selected.getPeriodosId());
            } catch (ParseException ex) {
                Logger.getLogger(RnGcCfdisTblController.class.getName()).log(Level.SEVERE, null, ex);
            }
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

    public RnGcCfdisTbl getRnGcCfdisTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcCfdisTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcCfdisTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcCfdisTbl.class)
    public static class RnGcCfdisTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcCfdisTblController controller = (RnGcCfdisTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcCfdisTblController");
            return controller.getRnGcCfdisTbl(getKey(value));
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
            if (object instanceof RnGcCfdisTbl) {
                RnGcCfdisTbl o = (RnGcCfdisTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcCfdisTbl.class.getName()});
                return null;
            }
        }
    }

    public void descargaPdf(RnGcCfdisTbl cfdisId) {
        if (cfdisId != null) {
            try {
                System.out.println("cfdisId: " + cfdisId);
                archivoId = getFacade().obtenerArchivoId(cfdisId);
                byte[] pdf = archivoId.getArchivoPdf();
                InputStream streamPdf = new ByteArrayInputStream(pdf);
                downLoadFile = new DefaultStreamedContent(streamPdf, "document/pdf", selected.getUuid().concat(".pdf"));
                System.out.println("Archivo: " + downLoadFile.getName());
            } catch (NullPointerException ex) {
                JsfUtil.addErrorMessage("Error al intentar descargar archivo PDF. No se encontro archivo.");
                System.out.println("Error al descargar PDF: " + ex);
            }
        }
    }

    public void descargaXml(RnGcCfdisTbl cfdisId) {
        System.out.println("cfdisId: " + cfdisId);
        if (cfdisId != null) {
            try {
                archivoId = getFacade().obtenerArchivoId(cfdisId);
                byte[] xml = archivoId.getArchivoXml();
                InputStream streamXml = new ByteArrayInputStream(xml);
                downLoadFile = new DefaultStreamedContent(streamXml, "document/xml", selected.getUuid().concat(".xml"));
                System.out.println("Archivo: " + downLoadFile.getName());
            } catch (NullPointerException ex) {
                JsfUtil.addErrorMessage("Error al intentar descargar archivo XML. No se encontro archivo.");
                System.out.println("Error al descargar XML: " + ex);
            }
        }
    }

    public void descargacodQR(RnGcCfdisTbl cfdisId) {
        if (cfdisId != null) {
            archivoId = getFacade().obtenerArchivoId(cfdisId);
            byte[] xml = archivoId.getArchivoQR();
            InputStream streamXml = new ByteArrayInputStream(xml);
            downLoadFile = new DefaultStreamedContent(streamXml, "Document/png", selected.getUuid().concat(".png"));
            System.out.println("Archivo: " + downLoadFile.getName());
        }
    }

    public void leerXml(RnGcCfdisTbl cfdisId) {
        if (cfdisId != null) {
            archivoId = getFacade().obtenerArchivoId(cfdisId);
            byte[] xml = archivoId.getArchivoXml();
            System.out.println(new String(xml));
        }
    }

    public StreamedContent getDownLoadFile() {
        return downLoadFile;
    }

    public void setDownLoadFile(StreamedContent downLoadFile) {
        this.downLoadFile = downLoadFile;
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

    public List<RnGcCfdisTbl> itemCreadoPor() {
        try {
            if (usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")) {
                items = getFacade().findAll();
            } else {
                items = getFacade().obtenerCreadoPor(usuarioFirmado.obtenerIdUsuario());
            }
        } catch (ClassFormatError ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return items;
    }

    public List<RnGcCfdisTbl> obtenerEmitidos() {
        try {
            usuarioId = new RnGcUsuariosTbl();
            usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            if (usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")) {
                itemsEmitidos = getFacade().findAll();
            } else {
                itemsEmitidos = getFacade().obtenerEmitidos(usuarioId.getRfc());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return itemsEmitidos;
    }
    
    public List<RnGcCfdisTbl> obtenerParaRelacionar(String Rfc) {
        try {
            itemsEmitidos = new ArrayList<>();
            usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            itemsEmitidos = getFacade().obtenerCfdisParaComplementos(Rfc);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return itemsEmitidos;
    }
    
    public List<RnGcCfdisTbl> obtenerPPD() {
        try {
            usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            if (usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")) {
                itemsPPD = getFacade().findAll();
            } else {
                itemsPPD = getFacade().obtenerPPD(usuarioId.getRfc());
            }
        } catch (ClassFormatError ex) {
            ex.printStackTrace();
        }
        System.out.println("itemsPPD: " + itemsPPD);
        return itemsPPD;
    }
    
     public List<RnGcCfdisTbl> obtenerPPDCliente(String rfc) {
            itemsPPD = getFacade().obtenerPPDCliente(rfc);
        return itemsPPD;
    }

    public List<RnGcCfdisTbl> obtenerRecibidos() {
        try {
            usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            if (usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")) {
                itemsRecibidos = getFacade().findAll();
            } else {
                itemsRecibidos = getFacade().obtenerRecibidos(usuarioId.getRfc());
            }
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }
        return itemsRecibidos;
    }

    public Double totalRecibidos() {
        total = 0.0d;
        totalFormat = 0.0;
        DecimalFormat dec = new DecimalFormat("#.00");

        usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        if (usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")) {
            itemsRecibidos = getFacade().findAll();
            for (int i = 0; i < itemsRecibidos.size(); i++) {
                if (itemsRecibidos.get(i).getImporte() > 0) {
                    total += itemsRecibidos.get(i).getImporte();
                }
            }
        } else {
            itemsRecibidos = getFacade().obtenerRecibidos(usuarioId.getRfc());
            for (int i = 0; i < itemsRecibidos.size(); i++) {
                if (itemsRecibidos.get(i).getImporte() > 0) {
                    total += itemsRecibidos.get(i).getImporte();
                    totalFormat = Double.valueOf(dec.format(total));
                }
            }
        }
        //System.out.println(itemsRecibidos.size() + " | " + total);
        return totalFormat;
    }

    public Double totalEmitidos() {
        totalEmitidos = 0.0;
        usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        if (usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")) {
            itemsEmitidos = getFacade().findAll();
            for (int i = 0; i < itemsEmitidos.size(); i++) {
                if (itemsEmitidos.get(i).getImporte() > 0) {
                    totalEmitidos += itemsEmitidos.get(i).getImporte();
                }
            }
        } else {
            itemsEmitidos = getFacade().obtenerEmitidos(usuarioId.getRfc());
            for (int i = 0; i < itemsEmitidos.size(); i++) {
                totalEmitidos += itemsEmitidos.get(i).getImporte();
            }
        }
        System.out.println(this.itemsEmitidos.size() + " | " + totalEmitidos);
        return totalEmitidos;
    }

    public void onRowSelect(SelectEvent event) {
        System.out.println("event: " + ((RnGcCfdisTbl) event.getObject()).getUuid());
        JsfUtil.addSuccessMessage("Usuario seleccionado: " + ((RnGcCfdisTbl) event.getObject()).getUuid());
    }

    public List<RnGcCfdisTbl> getFilteredCfdis() {
        return filteredCfdis;
    }

    public void setFilteredCfdis(List<RnGcCfdisTbl> filteredCfdis) {
        this.filteredCfdis = filteredCfdis;
    }

    public void timbrar(RnGcCfdisTbl cfdiId) {
        System.out.println("cfdiId: " + cfdiId.getXmlTrama());
        System.out.println("certificadoB64: " + crearCertificado(cfdiId));
    }

    public String crearCertificado(RnGcCfdisTbl cfdiId) {
        String certificadoB64 = new String(Base64.getEncoder().encode(cfdiId.getCertificados_Id().getCertificadoSelloDigital()));
        return certificadoB64;
    }

    public List<RnGcUsuariosTbl> obtenerUsuariosFinalesExterno(RnGcUsuariosTbl usuarioId) {
        System.out.println("Entro a obtenerUsuariosFinales");
        System.out.println("La lista final final es: " + listaFinalFinal);
        if (listaFinalFinal == null) {
            List<RnGcUsuariosTbl> listaFinalUsuariosCreadosPorUsuario = null;
            System.out.println("El usuario Firmado es: " + usuarioId);
            listaFinalUsuariosCreadosPorUsuario = obtenerUsuariosCreadosPorUsuario(usuarioId);
            System.out.println("La lista es: " + listaFinalUsuariosCreadosPorUsuario);
            listaFinalFinal = listaFinalUsuariosCreadosPorUsuario.stream().distinct().collect(Collectors.toList());
            System.out.println("Lista Final Final: " + listaFinalFinal);
        }
        return listaFinalFinal;
    }

    public List<RnGcUsuariosTbl> obtenerUsuariosCreadosPorUsuario(RnGcUsuariosTbl userId) {
        System.out.println("Entró a obtenerUsuariosCreadosPorUsuario con el usuario: " + userId.getNombreCompleto());
        List<RnGcUsuariosTbl> listaUsuariosCreadosPorUsuario = new ArrayList<>();
        listaUsuariosCreadosPorUsuario = null;
        listaUsuariosCreadosPorUsuario = usuarioFacade.obtenerListaUsuario(userId);
        System.out.println("El tamaño de la listaUsuariosCreadosPorUsuario es: " + listaUsuariosCreadosPorUsuario.size());

        if (!listaUsuariosCreadosPorUsuario.isEmpty()) {
            for (int i = 0; i < listaUsuariosCreadosPorUsuario.size(); i++) {
                listaGlobalUsuarios.add(listaUsuariosCreadosPorUsuario.get(i));
            }
            for (int n = 0; n < listaUsuariosCreadosPorUsuario.size(); n++) {
                //obtenerUsuariosCreadosPorUsuario(listaUsuariosCreadosPorUsuario.get(n));
                listaGlobalUsuarios = listaGlobalUsuarios.stream().distinct().collect(Collectors.toList());
            }
        }
        System.out.println("El tamaño de La listaGlobalUsuarios es: " + listaGlobalUsuarios.size());
        return listaGlobalUsuarios;
    }

    /*@PostConstruct
    public void agrupacionUsuarios() {
        List<RnGcUsuariosTbl> listaPorUsuario = new ArrayList<>();
        List<RnGcUsuariosTbl> listaUsuariosPorUsuario = new ArrayList<>();
        List<RnGcCfdisTbl> listaCreadoPorUsuario = new ArrayList<>();
        listaGruposGlobal = new ArrayList<SelectItem>();

        usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        listaPorUsuario = obtenerUsuariosFinalesExterno(usuarioId);
        listaPorUsuario.add(usuarioId);
        if (!listaPorUsuario.isEmpty()) {
            for (int i = 0; i < listaPorUsuario.size(); i++) {
                listaUsuariosPorUsuario = new ArrayList<>();
                listaUsuariosPorUsuario = usuarioFacade.obtenerListaUsuario(listaPorUsuario.get(i));
                SelectItemGroup g1 = new SelectItemGroup(listaPorUsuario.get(i).getNombreCompleto());
                SelectItem listaGruposSeleccionable[] = new SelectItem[listaUsuariosPorUsuario.size()];

                if (!listaUsuariosPorUsuario.isEmpty()) {
                    System.out.println("El tamaño de la lista es }}}}}}}}}}}---: " + listaUsuariosPorUsuario.size());
                    for (int n = 0; n < listaUsuariosPorUsuario.size(); n++) {
                        SelectItem item = new SelectItem();
                        item.setLabel(listaUsuariosPorUsuario.get(n).getNombreCompleto());
                        item.setValue(listaUsuariosPorUsuario.get(n));
                        listaGruposSeleccionable[n] = item;

                    }
                    //g1.setSelectItems(new SelectItem[]{new SelectItem(listaUsuariosPorUsuario.get(n).getNombreCompleto(), listaUsuariosPorUsuario.get(n).getNombreCompleto())});
                    g1.setSelectItems(listaGruposSeleccionable);
                    listaGruposGlobal.add(g1); //Esta bien
                    System.out.println("El tamaño de la lista es: " + listaGruposGlobal.size());
                }
            }
        }
    }//*/

    public void buscarTodosLosUsuarioPorRFCEmitidosRecibidos() {
        total = 0.0d;
        totalFormat = 0.0;
        totalEmitidos = 0.0;
        DecimalFormat dec = new DecimalFormat("#.00");
        obtenerEmitidosCreadoPorUsuario();
        obtenerReceptorCreadoPorUsuario();
        if (!listaGlobalRFCEmisor.isEmpty()) {
            itemsEmitidos = listaGlobalRFCEmisor;
            for (int i = 0; i < listaGlobalRFCEmisor.size(); i++) {
                totalEmitidos += listaGlobalRFCEmisor.get(i).getImporte();
            }

        }
        if (!listaGlobalRFCReceptor.isEmpty()) {
            itemsRecibidos = listaGlobalRFCReceptor;
            for (int i = 0; i < listaGlobalRFCReceptor.size(); i++) {
                if (listaGlobalRFCReceptor.get(i).getImporte() > 0) {
                    total += listaGlobalRFCReceptor.get(i).getImporte();
                    totalFormat = Double.valueOf(dec.format(total));
                }
            }
        }
        // System.out.println("RFC usuario: " + usuario.getRfc() + "  itemsEmitidos: " + itemsEmitidos.size() + " itemsRecibidos: " + itemsRecibidos.size());
    }

    public List<RnGcCfdisTbl> obtenerEmitidosCreadoPorUsuario() {
        Double totalEmitidos = 0.0;
        List<RnGcUsuariosTbl> listaPorUsuario = new ArrayList<>();
        List<RnGcCfdisTbl> listaCreadoPorUsuario = new ArrayList<>();
        listaUsuariosAgrupados = new ArrayList<SelectItem>();

        usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        listaPorUsuario = obtenerUsuariosFinalesExterno(usuarioId);
        //listaPorUsuario.add(usuarioId);
        System.out.println("listaPorUsuario: " + listaPorUsuario.size());
        if (listaGlobalRFCEmisor.isEmpty()) {
            if (!listaPorUsuario.isEmpty()) {
                for (int i = 0; i < listaPorUsuario.size(); i++) {
                    listaCreadoPorUsuario = getFacade().obtenerRfcEmisorCreadoPorUsuario(listaPorUsuario.get(i).getRfc());

                    System.out.println("La listaCreadoPorUsuario Emisor es: " + listaCreadoPorUsuario.size());
                    listaCreadoPorUsuario.stream().distinct().collect(Collectors.toList());

                    for (int j = 0; j < listaCreadoPorUsuario.size(); j++) {
                        listaGlobalRFCEmisor.add(listaCreadoPorUsuario.get(j));

                    }
                }
                System.out.println("La lista listaGlobalRFCEmisor1 es: " + listaGlobalRFCEmisor.size());
            }
        }
        System.out.println("La lista listaGlobalRFCEmisor Final es: " + listaGlobalRFCEmisor.size());
        listaGlobalRFCEmisor.stream().distinct().collect(Collectors.toList());
        return listaGlobalRFCEmisor;
    }

    public List<RnGcCfdisTbl> obtenerReceptorCreadoPorUsuario() {
        List<RnGcUsuariosTbl> listaPorUsuario = new ArrayList<>();
        List<RnGcCfdisTbl> listaCreadoPorUsuario = new ArrayList<>();
        usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        listaPorUsuario = obtenerUsuariosFinalesExterno(usuarioId);
        System.out.println("listaPorUsuario: " + listaPorUsuario.size());
        if (listaGlobalRFCReceptor.isEmpty()) {
            if (!listaPorUsuario.isEmpty()) {
                for (int i = 0; i < listaPorUsuario.size(); i++) {
                    listaCreadoPorUsuario = getFacade().obtenerRfcReceptorCreadoPorUsuario(listaPorUsuario.get(i).getRfc());
                    System.out.println("La listaCreadoPorUsuario es: " + listaCreadoPorUsuario.size());
                    listaCreadoPorUsuario.stream().distinct().collect(Collectors.toList());
                    for (int j = 0; j < listaCreadoPorUsuario.size(); j++) {
                        listaGlobalRFCReceptor.add(listaCreadoPorUsuario.get(j));
                    }
                }
                System.out.println("La lista listaGlobalRFCReceptor1 es: " + listaGlobalRFCReceptor.size());
                listaGlobalRFCReceptor.stream().distinct().collect(Collectors.toList());
            }
        }
        System.out.println("La lista listaGlobalRFCEReceptor Final es: " + listaGlobalRFCReceptor.size());
        listaGlobalRFCReceptor.stream().distinct().collect(Collectors.toList());
        return listaGlobalRFCReceptor;
    }

    public void buscarUsuarioPorRFCEmitidosRecibidos() {
        total = 0.0d;
        totalFormat = 0.0;
        totalEmitidos = 0.0;
        DecimalFormat dec = new DecimalFormat("#.00");
        obtenerRecibidosPorUsuario();
        obtenerEmitidosPorUsuario();
        if (!itemsEmitidos.isEmpty()) {
            for (int i = 0; i < itemsEmitidos.size(); i++) {
                totalEmitidos += itemsEmitidos.get(i).getImporte();
            }
        }
        if (!itemsRecibidos.isEmpty()) {
            for (int i = 0; i < itemsRecibidos.size(); i++) {
                if (itemsRecibidos.get(i).getImporte() > 0) {
                    total += itemsRecibidos.get(i).getImporte();
                    totalFormat = Double.valueOf(dec.format(total));
                }
            }
        }
        System.out.println("RFC usuario: " + usuario.getRfc() + "  itemsEmitidos: " + itemsEmitidos.size() + " itemsRecibidos: " + itemsRecibidos.size());
    }

    public List<RnGcCfdisTbl> obtenerRecibidosPorUsuario() {
        try {
            if (usuario.getRfc() != null) {
                itemsRecibidos = getFacade().obtenerRecibidos(usuario.getRfc());
            }
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }
        return itemsRecibidos;
    }

    public List<RnGcCfdisTbl> obtenerEmitidosPorUsuario() {
        try {
            if (usuario.getRfc() != null) {
                itemsEmitidos = getFacade().obtenerEmitidos(usuario.getRfc());
            }
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }
        return itemsEmitidos;
    }

    public void buscarPorRFCEmitidosRecibidos() {
        total = 0.0d;
        totalFormat = 0.0;
        totalEmitidos = 0.0;
        DecimalFormat dec = new DecimalFormat("#.00");

        usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());

        obtenerRecibidos2();
        obtenerEmitidos2();

        if (!itemsEmitidos.isEmpty()) {
            for (int i = 0; i < itemsEmitidos.size(); i++) {
                totalEmitidos += itemsEmitidos.get(i).getImporte();
            }
        }
        if (!itemsRecibidos.isEmpty()) {
            for (int i = 0; i < itemsRecibidos.size(); i++) {
                if (itemsRecibidos.get(i).getImporte() > 0) {
                    total += itemsRecibidos.get(i).getImporte();
                    totalFormat = Double.valueOf(dec.format(total));
                }
            }
        }
        System.out.println("RFC usuario: " + usuarioId.getRfc() + "  itemsEmitidos: " + itemsEmitidos.size() + " itemsRecibidos: " + itemsRecibidos.size());
    }

    public List<RnGcCfdisTbl> obtenerRecibidos2() {
        try {
            System.out.println("Entro a obtenerRecibidos2");
            usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            System.out.println("El usuario firmado es: " + usuarioId.getId());
            System.out.println("El nombre del usuario es: " + usuarioId.getNombreCompleto() + " y su RFC es igual a: " + usuarioId.getRfc());

            itemsRecibidos = getFacade().obtenerRecibidosParaMisReportes(usuarioId.getRfc(), usuarioId.getId());
            System.out.println("El tamaño de la lista es: " + itemsRecibidos.size());
            for (int i = 0; i < itemsRecibidos.size(); i++) {
                System.out.println("En la posicion: " + i + " El RFC es: " + itemsRecibidos.get(i).getRfcReceptor()
                        + " El importe es: " + itemsRecibidos.get(i).getImporte() + " Id del rfc que lo creo " + itemsRecibidos.get(i).getCreadoPor());

            }
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }
        return itemsRecibidos;
    }

    public List<RnGcCfdisTbl> obtenerEmitidos2() {
        try {
            System.out.println("Entro a obtenerEmitidos2");
            usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            System.out.println("El usuario firmado es: " + usuarioId.getId());
            System.out.println("El nombre del usuario es: " + usuarioId.getNombreCompleto() + " y su RFC es igual a: " + usuarioId.getRfc());

            itemsEmitidos = getFacade().obtenerEmitidosParaMisReportes(usuarioId.getRfc(), usuarioId.getId());
            System.out.println("El tamaño de la lista es: " + itemsEmitidos.size());
            for (int i = 0; i < itemsEmitidos.size(); i++) {
                System.out.println("En la posicion: " + i + " El RFC es: " + itemsEmitidos.get(i).getRfcEmisor()
                        + " El importe es: " + itemsEmitidos.get(i).getImporte() + " Id del rfc que lo creo " + itemsEmitidos.get(i).getCreadoPor());

            }
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }
        return itemsEmitidos;
    }
}
