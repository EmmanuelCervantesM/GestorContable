package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcUsuariosPerfilesTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcUsuariosPerfilesTblFacade;

import java.io.Serializable;
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
import mx.com.rocketnegocios.beans.RnGcTimbresTblFacade;
import mx.com.rocketnegocios.entities.RnGcPerfilesTbl;
import mx.com.rocketnegocios.entities.RnGcTimbresTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;

@Named(value = "rnGcUsuariosPerfilesTblController")
@SessionScoped
public class RnGcUsuariosPerfilesTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcUsuariosPerfilesTblFacade ejbFacade;

    @EJB
    private mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade usuarioFacade;

    @EJB
    private RnGcTimbresTblFacade timbresFacade;

    private List<RnGcUsuariosPerfilesTbl> items = null;
    private RnGcUsuariosPerfilesTbl selected;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();

    private List<RnGcUsuariosPerfilesTbl> itemsUsuarios = null;
    private RnGcUsuariosTbl usuarioId = null;
    private List<RnGcUsuariosTbl> listaUsuarioId = null;
    private RnGcTimbresTbl timbres;
    private List<RnGcTimbresTbl> itemsTimbres;
    private ArrayList<String> listaTiposPerfilesPorUsuario = new ArrayList<>();

    public ArrayList<String> getListaTiposPerfilesPorUsuario() {
        listaTiposPerfilesPorUsuario = new ArrayList<>();
        RnGcPerfilesTbl perfilId = (RnGcPerfilesTbl) getFacade().obtenerPerfilesPorUsuario(usuarioId);
        if (perfilId.getTipoPerfil().equals("AG")) {
            listaTiposPerfilesPorUsuario.add("A");
            listaTiposPerfilesPorUsuario.add("AD");
            listaTiposPerfilesPorUsuario.add("AC");
            listaTiposPerfilesPorUsuario.add("DE");
            listaTiposPerfilesPorUsuario.add("CE");
        } else if (perfilId.getTipoPerfil().equals("A")) {
            listaTiposPerfilesPorUsuario.add("A");
            listaTiposPerfilesPorUsuario.add("AD");
            listaTiposPerfilesPorUsuario.add("AC");
            listaTiposPerfilesPorUsuario.add("DE");
            listaTiposPerfilesPorUsuario.add("CE");
        } else if (perfilId.getTipoPerfil().equals("AD")) {
            listaTiposPerfilesPorUsuario.add("AD");
            listaTiposPerfilesPorUsuario.add("AC");
            listaTiposPerfilesPorUsuario.add("DE");
            listaTiposPerfilesPorUsuario.add("CE");
        } else if (perfilId.getTipoPerfil().equals("AC")) {
            listaTiposPerfilesPorUsuario.add("AC");
            listaTiposPerfilesPorUsuario.add("CE");
        }
        return listaTiposPerfilesPorUsuario;
    }

    public void setListaTiposPerfilesPorUsuario(ArrayList<String> listaTiposPerfilesPorUsuario) {
        this.listaTiposPerfilesPorUsuario = listaTiposPerfilesPorUsuario;
    }

    public RnGcUsuariosPerfilesTblController() {
    }

    public RnGcUsuariosPerfilesTbl getSelected() {
        if (selected == null) {
            selected = new RnGcUsuariosPerfilesTbl();
        }
        return selected;
    }

    public void setSelected(RnGcUsuariosPerfilesTbl selected) {
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

    private RnGcUsuariosPerfilesTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcUsuariosPerfilesTbl prepareCreate() {
        selected = new RnGcUsuariosPerfilesTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcUsuariosPerfilesTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcUsuariosPerfilesTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcUsuariosPerfilesTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcUsuariosPerfilesTbl> getItems() {
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

    public RnGcUsuariosPerfilesTbl getRnGcUsuariosPerfilesTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcUsuariosPerfilesTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcUsuariosPerfilesTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcUsuariosPerfilesTbl.class)
    public static class RnGcUsuariosPerfilesTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcUsuariosPerfilesTblController controller = (RnGcUsuariosPerfilesTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcUsuariosPerfilesTblController");
            return controller.getRnGcUsuariosPerfilesTbl(getKey(value));
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
            if (object instanceof RnGcUsuariosPerfilesTbl) {
                RnGcUsuariosPerfilesTbl o = (RnGcUsuariosPerfilesTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcUsuariosPerfilesTbl.class.getName()});
                return null;
            }
        }

    }

    public List<RnGcUsuariosPerfilesTbl> getItemsUsuario() {
        this.itemsUsuarios = getFacade().obtenerPerfilesPorUsuario(usuarioId);
        return itemsUsuarios;
    }

    public void prepararItemUsuario(RnGcUsuariosTbl usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<String> obtenerUsuarioPerfil(Integer Id) {
        List<String> usuarioPerfil = new ArrayList<>();
        if (Id != null) {
            usuarioId = usuarioFacade.obtenerUsuarioPorId(Id);
            itemsUsuarios = getFacade().obtenerPerfilesPorUsuario(usuarioId);
            for (int a = 0; a < itemsUsuarios.size(); a++) {
                if(itemsUsuarios.get(a).getFechaFinal() == null || validarFecha(itemsUsuarios.get(a).getFechaFinal()) >= 0)
                    usuarioPerfil.add(itemsUsuarios.get(a).getPerfilesId().getPerfilNombre());
            }
        }
        return usuarioPerfil;
    }
    
    public Integer validarFecha(Date fechaFinal){
        Integer fecha = 0;
        if(fechaFinal != null){
            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaFinal);
            cal.add(Calendar.DAY_OF_WEEK, 1);
            fecha = cal.getTime().compareTo(new Date());
        }
        return fecha;
    }

}
