package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcFolioserieTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcFolioserieTblFacade;

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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade;
import mx.com.rocketnegocios.entities.RnGcCertificadosTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;

@Named("rnGcFolioserieTblController")
@SessionScoped
public class RnGcFolioserieTblController implements Serializable {

    @EJB
    private RnGcUsuariosTblFacade usuarioFacade;

    @EJB
    private mx.com.rocketnegocios.beans.RnGcFolioserieTblFacade ejbFacade;
    private List<RnGcFolioserieTbl> items = null;
    private List<RnGcFolioserieTbl> itemsSerie = null;
    private List<RnGcFolioserieTbl> itemsFolio = null;
    private RnGcFolioserieTbl selected;
    private RnGcUsuariosTbl usuarioId;
    private List<RnGcFolioserieTbl> itemsUsuario;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private RnGcFolioserieTbl folio;
    private RnGcCertificadosTbl certificado;

    public RnGcFolioserieTblController() {
    }

    public RnGcFolioserieTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcFolioserieTbl selected) {
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

    private RnGcFolioserieTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcFolioserieTbl prepareCreate() {
        selected = new RnGcFolioserieTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, "Folio/Serie Creado");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Folio/Serie Actualizado");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Folio/Serie Eliminado");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcFolioserieTbl> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            System.out.println(selected.getNombre() + " | " + selected.getSerie() + " | " + selected.getFolio() +
                    " | " + selected.getEstado() + " | " + selected.getUsuariosId() + " | " + selected.getCertificadosId());
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

    public RnGcFolioserieTbl getRnGcFolioserieTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcFolioserieTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcFolioserieTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcFolioserieTbl.class)
    public static class RnGcFolioserieTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcFolioserieTblController controller = (RnGcFolioserieTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcFolioserieTblController");
            return controller.getRnGcFolioserieTbl(getKey(value));
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
            if (object instanceof RnGcFolioserieTbl) {
                RnGcFolioserieTbl o = (RnGcFolioserieTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcFolioserieTbl.class.getName()});
                return null;
            }
        }

    }

    public RnGcUsuariosTbl usuarioLogeado() {
        return usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
    }
    
    public void prepararItemUsuario(RnGcCertificadosTbl certificadoId) {
        this.certificado = certificadoId;
    }
    
    public List<RnGcFolioserieTbl> getItemsCertificado() {
        this.itemsUsuario = getFacade().foliosPorCertificados(certificado);
        return itemsUsuario;
    }

    public List<RnGcFolioserieTbl> obtenerSeriePorUsuario() {
        if (usuarioFirmado.perfilUsuario().contains("ADMIINISTRADOR")) {
            itemsSerie = getFacade().findAll();
        } else {
            itemsSerie = getFacade().serieporUsuario(usuarioLogeado());
        }
        return itemsSerie;
    }

    public List<RnGcFolioserieTbl> obtenerFolioPorUsuarioSerie(String serie) {
        if(!serie.isEmpty())  {
             itemsFolio = getFacade().folioPorUsuarioSerie(usuarioLogeado(), serie);
        }
        return itemsFolio;
    }
    
    public List<RnGcFolioserieTbl> foliosPorCertifcado(RnGcCertificadosTbl certifcadoId) {
        System.out.println("certifcadoId: " + certifcadoId);
        List<RnGcFolioserieTbl> listaSerie = null;
        listaSerie = getFacade().foliosPorCertificados(certifcadoId);
        System.out.println("listaSerie: " + listaSerie);
        return listaSerie;
    }
}
