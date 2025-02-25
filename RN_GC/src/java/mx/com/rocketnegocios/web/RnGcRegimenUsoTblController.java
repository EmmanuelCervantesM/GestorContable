package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcRegimenUsoTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcRegimenUsoTblFacade;

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
import mx.com.rocketnegocios.entities.RnGcCatalogosusosTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;

@Named("rnGcRegimenUsoTblController")
@SessionScoped
public class RnGcRegimenUsoTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcRegimenUsoTblFacade ejbFacade;
    private List<RnGcRegimenUsoTbl> items = null;
    private RnGcRegimenUsoTbl selected;
    private RnGcCatalogosusosTbl[] usos;
    private final UsuarioFirmado usuarioFirmado = new UsuarioFirmado();

    public RnGcRegimenUsoTblController() {
    }

    public RnGcRegimenUsoTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcRegimenUsoTbl selected) {
        this.selected = selected;
    }

    public RnGcCatalogosusosTbl[] getUsos() {
        return usos;
    }

    public void setUsos(RnGcCatalogosusosTbl[] usos) {
        this.usos = usos;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        selected.setFechaCreacion(new Date());
    }

    private RnGcRegimenUsoTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcRegimenUsoTbl prepareCreate() {
        selected = new RnGcRegimenUsoTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcRegimenUsoTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcRegimenUsoTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcRegimenUsoTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcRegimenUsoTbl> getItems() {
        items = getFacade().findAll();
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

    public RnGcRegimenUsoTbl getRnGcRegimenUsoTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcRegimenUsoTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcRegimenUsoTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcRegimenUsoTbl.class)
    public static class RnGcRegimenUsoTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcRegimenUsoTblController controller = (RnGcRegimenUsoTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcRegimenUsoTblController");
            return controller.getRnGcRegimenUsoTbl(getKey(value));
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
            if (object instanceof RnGcRegimenUsoTbl) {
                RnGcRegimenUsoTbl o = (RnGcRegimenUsoTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcRegimenUsoTbl.class.getName()});
                return null;
            }
        }

    }
    
    public void crearRelaciones() {
        //System.out.println("regimen: " + selected.getRegimenId().getClaveRegimenFiscal() + " | " + usos);
        List<RnGcRegimenUsoTbl> regimenUsoCreado = new ArrayList<>();
        try {
            for (RnGcCatalogosusosTbl documento : usos) {
                regimenUsoCreado = getFacade().obtenerXUsoRegimen(selected.getRegimenId(), documento);
                if (regimenUsoCreado == null || regimenUsoCreado.isEmpty()) {
                    RnGcRegimenUsoTbl regimenUso = new RnGcRegimenUsoTbl();
                    regimenUso.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
                    regimenUso.setFechaCreacion(new Date());
                    regimenUso.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                    regimenUso.setUltimaFechaActualizacion(new Date());
                    regimenUso.setRegimenId(selected.getRegimenId());
                    regimenUso.setUsocfdiId(documento);
                    regimenUso.setFisica(selected.getFisica());
                    regimenUso.setMoral(selected.getMoral());
                    getFacade().edit(regimenUso);
                    System.out.println("creado uso: " + regimenUso.getUsocfdiId() + " regimen: " + regimenUso.getRegimenId());
                }
            }
            JsfUtil.addSuccessMessage("Relaciones creadas correctamente.");
            selected = null;
            usos = new RnGcCatalogosusosTbl[100];
        } catch (Exception ex) {
            System.out.println("error crearRelacion: " + ex.getLocalizedMessage());
            ex.printStackTrace();
            JsfUtil.addErrorMessage("Ocurrio un error durante la creación de las relaciones. " + ex.getLocalizedMessage());
        }
    }

}
