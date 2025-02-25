package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcTipoPoliza;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcTipoPolizaFacade;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;

@Named("rnGcTipoPolizaController")
@SessionScoped
public class RnGcTipoPolizaController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcTipoPolizaFacade ejbFacade;
    private List<RnGcTipoPoliza> items = null;
    private RnGcTipoPoliza selected;
    private Date fechaActual;
    private Date primerDia;
    private Date ultimoDia;

    @EJB
    private mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade usuarioFacade;
    private RnGcUsuariosTbl usuarioId = null;

    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();

    public RnGcTipoPolizaController() {
    }

    public RnGcTipoPoliza getSelected() {
        return selected;
    }

    public void setSelected(RnGcTipoPoliza selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        selected.setUltimaFechaActualizacion(new Date());
    }

    protected void initializeEmbeddableKey() throws ParseException {
        selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        selected.setFechaCreacion(new Date());
        obtenerFechas();
        selected.setFechaFinSecuencia(ultimoDia);

    }

    private RnGcTipoPolizaFacade getFacade() {
        return ejbFacade;
    }

    public RnGcTipoPoliza prepareCreate() throws ParseException {
        selected = new RnGcTipoPoliza();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcTipoPolizaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcTipoPolizaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcTipoPolizaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcTipoPoliza> getItems() {
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

    public RnGcTipoPoliza getRnGcTipoPoliza(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcTipoPoliza> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcTipoPoliza> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcTipoPoliza.class)
    public static class RnGcTipoPolizaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcTipoPolizaController controller = (RnGcTipoPolizaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcTipoPolizaController");
            return controller.getRnGcTipoPoliza(getKey(value));
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
            if (object instanceof RnGcTipoPoliza) {
                RnGcTipoPoliza o = (RnGcTipoPoliza) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcTipoPoliza.class.getName()});
                return null;
            }
        }

    }

    public List<RnGcTipoPoliza> obtenerTipoPolizaPorUsuario() throws ParseException {
        usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        System.out.println("Entró a obtenerTipoPolizaPorUsuario con el usuario: " + usuarioId.getNombreCompleto());
        List<RnGcTipoPoliza> listaTipoPolizaPorUsuario = new ArrayList<>();
        listaTipoPolizaPorUsuario = null;
        listaTipoPolizaPorUsuario = ejbFacade.obtenerListaPolizas(usuarioId);
        try {
            if(listaTipoPolizaPorUsuario.size() < 3){
            obtenerFechas();
        RnGcTipoPoliza tipoIngreso = new RnGcTipoPoliza();
        RnGcTipoPoliza tipoEgreso = new RnGcTipoPoliza();
        RnGcTipoPoliza tipoDiario = new RnGcTipoPoliza();
        
        //Se crea tipoPoliza de tipo Ingreso
        tipoIngreso.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        tipoIngreso.setFechaCreacion(new Date());
        tipoIngreso.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        tipoIngreso.setUltimaFechaActualizacion(new Date());
        tipoIngreso.setTipoPoliza("I");
        tipoIngreso.setNumeroSecuencia(0);
        tipoIngreso.setDescripcion("Ingreso");
        tipoIngreso.setEstatus("A");
        tipoIngreso.setFechaFinSecuencia(ultimoDia);
        ejbFacade.crea(tipoIngreso);
        
        //Se crea tipoPoliza de tipo Egreso
        tipoEgreso.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        tipoEgreso.setFechaCreacion(new Date());
        tipoEgreso.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        tipoEgreso.setUltimaFechaActualizacion(new Date());
        tipoEgreso.setTipoPoliza("E");
        tipoEgreso.setNumeroSecuencia(0);
        tipoEgreso.setDescripcion("Egreso");
        tipoEgreso.setEstatus("A");
        tipoEgreso.setFechaFinSecuencia(ultimoDia);
        ejbFacade.crea(tipoEgreso);
        //Se crea tipoPoliza Diario
        
        tipoDiario.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        tipoDiario.setFechaCreacion(new Date());
        tipoDiario.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        tipoDiario.setUltimaFechaActualizacion(new Date());
        tipoDiario.setTipoPoliza("DA");
        tipoDiario.setNumeroSecuencia(0);
        tipoDiario.setDescripcion("Díario");
        tipoDiario.setEstatus("A");
        tipoDiario.setFechaFinSecuencia(ultimoDia); 
        ejbFacade.crea(tipoDiario);
          listaTipoPolizaPorUsuario = ejbFacade.obtenerListaPolizas(usuarioId);
        }
            
        } catch (Exception e) {
            System.out.println("Error al crear el tipo de Poliza");
        }
        System.out.println("El tamaño de la listaTipoPolizaPorUsuario es: " + listaTipoPolizaPorUsuario.size());
        return listaTipoPolizaPorUsuario;

    }

    public void obtenerFechas() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//Fecha actual
        Calendar calendar = Calendar.getInstance();
        String hoy = sdf.format(calendar.getTime());
        fechaActual = sdf.parse(hoy);
        System.out.println("Fecha Actual:" + fechaActual);

//A la fecha actual le pongo el día 1
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String diaUno = sdf.format(calendar.getTime());
        primerDia= sdf.parse(diaUno);
        System.out.println("Primer día del mes actual:" + primerDia);

        //Se le agrega 1 mes 
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        System.out.println("1-Último día del mes" + calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String ultimo = sdf.format(calendar.getTime());
        ultimoDia = sdf.parse(ultimo);
        System.out.println("ultimo día del mes" + ultimoDia);
    }
    
    public void actualizaSecuencia(){
        System.out.println("Entro a actualizar secuencia ");
        if(selected != null){
            System.out.println("--El numero de secuencia es: "+ selected.getNumeroSecuencia());
        }
    }

}
