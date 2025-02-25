package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcTasacuotaTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcTasacuotaTblFacade;

import java.io.Serializable;
import java.util.ArrayList;
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
import mx.com.rocketnegocios.util.UsuarioFirmado;

@Named("rnGcTasacuotaTblController")
@SessionScoped
public class RnGcTasacuotaTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcTasacuotaTblFacade ejbFacade;
    private List<RnGcTasacuotaTbl> items = null;
    private RnGcTasacuotaTbl selected;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private List<RnGcTasacuotaTbl> listaTasaCuota;

    public RnGcTasacuotaTblController() {
    }

    public RnGcTasacuotaTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcTasacuotaTbl selected) {
        this.selected = selected;
    }

    public List<RnGcTasacuotaTbl> getListaTasaCuota() {
        if (listaTasaCuota == null) {
            this.listaTasaCuota = new ArrayList<>();
        }
        return listaTasaCuota;
    }

    public void setListaTasaCuota(List<RnGcTasacuotaTbl> listaTasaCuota) {
        this.listaTasaCuota = listaTasaCuota;
    }

    protected void setEmbeddableKeys() {
        selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        selected.setUltimaFechaActualizacion(new Date());
    }

    protected void initializeEmbeddableKey() {
        selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        selected.setFechaCreacion(new Date());
    }

    private RnGcTasacuotaTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcTasacuotaTbl prepareCreate() {
        selected = new RnGcTasacuotaTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcTasacuotaTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcTasacuotaTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcTasacuotaTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcTasacuotaTbl> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public List<RnGcTasacuotaTbl> obtenerPorImpuesto(double impuesto) {
        System.out.println("obtenerPorImpuesto: " + impuesto);

        if (impuesto == 1) {
            items = getFacade().tasaPorImpuesto("ISR");
        } else if (impuesto == 2) {
            items = getFacade().tasaPorImpuesto("IVA");
        } else if (impuesto == 3) {
            items = getFacade().tasaPorImpuesto("IEPS");
        } else {
            System.out.println("El valor no se encuentra");
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

    public RnGcTasacuotaTbl getRnGcTasacuotaTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcTasacuotaTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcTasacuotaTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcTasacuotaTbl.class)
    public static class RnGcTasacuotaTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcTasacuotaTblController controller = (RnGcTasacuotaTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcTasacuotaTblController");
            return controller.getRnGcTasacuotaTbl(getKey(value));
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
            if (object instanceof RnGcTasacuotaTbl) {
                RnGcTasacuotaTbl o = (RnGcTasacuotaTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcTasacuotaTbl.class.getName()});
                return null;
            }
        }

    }

    public List<String> listaTasasCuotas(String tipo, String factor, String impuesto) {
        String traslado = "-";
        String retencion = "-";
        String impuesto1 = "-";
        if (tipo != null && tipo.equals("Traslado")) {
            traslado = "Si";
        } else {
            traslado = "No";
        }
        if (tipo != null && tipo.equals("Retención")) {
            retencion = "Si";
        } else {
            retencion = "No";
        }
        switch (impuesto) {
            case "001":
                impuesto1 = "ISR";
                break;
            case "002":
                impuesto1 = "IVA";
                break;
            case "003":
                impuesto1 = "IEPS";
                break;
        }
        System.out.println("DatosTasas: " + traslado + " | " + retencion + " | " + impuesto1 + " | " + factor);
        List<String> listaPorcentajes = new ArrayList<>();
        listaTasaCuota = getFacade().listaTasas(traslado, retencion, factor, impuesto1);
        if (listaTasaCuota.size() > 0) {
            for (int i = 0; i < listaTasaCuota.size(); i++) {
                listaPorcentajes.add(String.valueOf(listaTasaCuota.get(i).getValorMaximo()));
                System.out.println("listaPorcentajes: " + listaPorcentajes.get(i));
            }
        }
        return listaPorcentajes;
    }

}
