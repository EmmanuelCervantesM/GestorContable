package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcPolizaLineasTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcPolizaLineasTblFacade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import mx.com.rocketnegocios.beans.RnGcCatalogoCuentasTblFacade;
import mx.com.rocketnegocios.entities.RnGcCatalogoCuentasTbl;
import mx.com.rocketnegocios.entities.RnGcPolizaHeaderTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

@Named("rnGcPolizaLineasTblController")
@SessionScoped
public class RnGcPolizaLineasTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcPolizaLineasTblFacade ejbFacade;
    
    @EJB
    private RnGcCatalogoCuentasTblFacade catalogoCuentasFacade;
    
    private List<RnGcPolizaLineasTbl> items = null;
    private RnGcPolizaLineasTbl selected;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private RnGcPolizaHeaderTblController polzaHeaderController;
    private RnGcPolizaHeaderTbl polizaHeader;
    private RnGcCatalogoCuentasTbl catalogodeCuentas = new RnGcCatalogoCuentasTbl(1);
    private Double cargos = 0.0;
    private Double abonos = 0.0;
    private Double diferencia = 0.0;

    RnGcPolizaHeaderTbl polizaHeaderId = null;
    private List<RnGcPolizaLineasTbl> itemsPolizaLineas = null;
    
    public Double getCargos() {
        return cargos;
    }

    public void setCargos(Double cargos) {
        this.cargos = cargos;
    }

    public Double getAbonos() {
        return abonos;
    }

    public void setAbonos(Double abonos) {
        this.abonos = abonos;
    }

    public RnGcPolizaLineasTblController() {
    }

    public Double getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(Double diferencia) {
        this.diferencia = diferencia;
    }

    public RnGcPolizaLineasTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcPolizaLineasTbl selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        selected.setUltimaFechaActualizacion(new Date());
    }

    protected void initializeEmbeddableKey() {
        selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        selected.setFechaCreacion(new Date());
        selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        selected.setUltimaFechaActualizacion(new Date());
    }

    private RnGcPolizaLineasTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcPolizaLineasTbl prepareCreate() {
        System.err.println("Preparando Para Crear....");
        selected = new RnGcPolizaLineasTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcPolizaLineasTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcPolizaLineasTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcPolizaLineasTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void eliminarLinea(RnGcPolizaHeaderTbl polizaId) {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcPolizaLineasTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
            if (polizaId != null) {
                prepareItemsPoliza(polizaId);
            }
        }
    }

    public List<RnGcPolizaLineasTbl> getItems() {
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

    public RnGcPolizaLineasTbl getRnGcPolizaLineasTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcPolizaLineasTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcPolizaLineasTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcPolizaLineasTbl.class)
    public static class RnGcPolizaLineasTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcPolizaLineasTblController controller = (RnGcPolizaLineasTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcPolizaLineasTblController");
            return controller.getRnGcPolizaLineasTbl(getKey(value));
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
            if (object instanceof RnGcPolizaLineasTbl) {
                RnGcPolizaLineasTbl o = (RnGcPolizaLineasTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcPolizaLineasTbl.class.getName()});
                return null;
            }
        }

    }

    public void prepareItemsPoliza(RnGcPolizaHeaderTbl polizaHeaderId2) {
        polizaHeaderId = polizaHeaderId2;
        if (polizaHeaderId2 != null) {
            System.out.println("plantillaPagosId:" + polizaHeaderId2.getReceptor());
            this.polizaHeaderId = polizaHeaderId2;
            itemsPolizaLineas = getFacade().obtenerPolizaLineas(polizaHeaderId2);
            System.err.println("Termino de consultar el tamaño de la lista es: " + itemsPolizaLineas.size());
            calcularSuma();
        }
    }

    public void calcularSuma() {
        cargos = 0.0;
        abonos = 0.0;
        diferencia = 0.0;
        for (int i = 0; i < itemsPolizaLineas.size(); i++) {
            RnGcPolizaLineasTbl item = itemsPolizaLineas.get(i);
            cargos = cargos + item.getCargo();
            abonos = abonos + item.getAbono();
        }
        diferencia = cargos - abonos;
        System.out.println("Cargos: " + cargos + " || Abonos:" + abonos);
    }

    public List<RnGcPolizaLineasTbl> getItemsPolizaLineas() {
        return itemsPolizaLineas;
    }

    public void setItemsPolizaLineas(List<RnGcPolizaLineasTbl> itemsPolizaLineas) {
        this.itemsPolizaLineas = itemsPolizaLineas;
    }
    
    public void crearLinea() {
        System.err.println("Preparando Para Crear....");
        selected = new RnGcPolizaLineasTbl();
        initializeEmbeddableKey();
    }

    public void onAddNew(RnGcPolizaHeaderTbl polizaHeader2) {
        // Add one new car to the table:
        System.err.println("Preparando Para Crear....");
        RnGcPolizaLineasTbl polizaLinea = new RnGcPolizaLineasTbl(null, "-", usuarioFirmado.obtenerIdUsuario(), new Date(), usuarioFirmado.obtenerIdUsuario(), new Date());
        System.err.println("PolizaHeaderId" + polizaHeader2.getId());
        obtenerCatalogoCuenta();
        polizaLinea.setAbono(0.0);
        polizaLinea.setCargo(0.0);
        polizaLinea.setCatalogoCuentasId(catalogodeCuentas);
        polizaLinea.setPolizaHeaderId(polizaHeader2);
        polizaLinea.setConcepto(polizaHeader2.getConcepto());
        ejbFacade.crea(polizaLinea);
        System.err.println("Creo nuevo registro");
        prepareItemsPoliza(polizaHeader2);

        System.err.println("termino de actualizar la lista");

        FacesMessage msg = new FacesMessage("Nuevo asiento agregado correctamente");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edición Cancelada");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Editado Correctamente");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        update();
        prepareItemsPoliza(polizaHeaderId);
        calcularSuma();
        //RequestContext.getCurrentInstance().update("form2:polizaLineasEdittable");
    }

    public void onCellEdit(CellEditEvent event) {
        System.err.println("Entro a cell edit");
        RnGcPolizaLineasTbl oldValue = (RnGcPolizaLineasTbl) event.getOldValue();
        RnGcPolizaLineasTbl newValue = (RnGcPolizaLineasTbl) event.getNewValue();

        System.out.println("Valor antiguo: " + oldValue.getSucursal());
        System.out.println("Valor nuevo: " + newValue.getSucursal());

        /* if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }*/
    }
    
    public void obtenerCatalogoCuenta(){
        catalogodeCuentas = catalogoCuentasFacade.obtenerCuentasCreadoPor(usuarioFirmado.obtenerIdUsuario()).get(0);
    }

}
