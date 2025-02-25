package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcPersonasTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcPersonasTblFacade;

import java.io.Serializable;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import mx.com.rocketnegocios.beans.RnGcTipospersonasTblFacade;
import mx.com.rocketnegocios.entities.RnGcDireccionesTbl;
import mx.com.rocketnegocios.entities.RnGcTipospersonasTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;

@ManagedBean(name = "rnGcPersonas_clientesTblController")
@SessionScoped
public class RnGcPersonas_clientesTblController implements Serializable {

    @EJB
    private RnGcTipospersonasTblFacade rnGcTipospersonasTblFacade;

    @EJB
    private mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade usuarioFacade;

    @EJB
    private mx.com.rocketnegocios.beans.RnGcPersonasTblFacade ejbFacade;

    private List<RnGcPersonasTbl> items = null;
    private List<RnGcPersonasTbl> itemsCliente = null;
    private RnGcPersonasTbl selected;
    private RnGcDireccionesTbl direcciones;
    private RnGcUsuariosTbl usuarioId;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private List<RnGcPersonasTbl> filteredPersonas;

    private List<RnGcDireccionesTbl> itemsDirecciones = null;
    RnGcPersonasTbl personaId = null;

    public RnGcPersonas_clientesTblController() {
    }

    public RnGcPersonasTbl getSelected() {
        if (this.selected == null) {
            this.selected = new RnGcPersonasTbl();
        }
        return selected;
    }

    public void setSelected(RnGcPersonasTbl selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        RnGcTipospersonasTbl tipoPersonaId = rnGcTipospersonasTblFacade.obtenerTipoPersona(selected.getTipoPersona());
        selected.setTipo("Matriz");
        selected.setTipoPersonaId(tipoPersonaId);
        selected.setRfc(selected.getRfc().toUpperCase());
        selected.setNombre(iniMayusculas(selected.getNombre()));
        selected.setUsuarioId(usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario()));
        selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        selected.setUltimaFechaActualizacion(new Date());
    }

    protected void initializeEmbeddableKey() {
        selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        selected.setFechaCreacion(new Date());
    }

    RnGcPersonasTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcPersonasTbl prepareCreate() {
        System.out.println("prepareCreate");
        selected = new RnGcPersonasTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        System.out.println("create() Clientes");
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcPersonasTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            itemsCliente = null;
        }
        System.out.println("Creado");
    }

    public void update() {
        System.out.println("update");
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcPersonasTblUpdated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            itemsCliente = null;
        }
        System.out.println("Creado");
    }

    public void destroy() {
        System.out.println("destroy");
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcPersonasTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
            itemsCliente = null;
        }
    }

    public List<RnGcPersonasTbl> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public List<RnGcPersonasTbl> getItemsClientes() {
        itemsCliente = getFacade().obtenerPersonasPorTipo("Cliente");
        return itemsCliente;
    }

    public List<RnGcPersonasTbl> getFilteredPersonas() {
        return filteredPersonas;
    }

    public void setFilteredPersonas(List<RnGcPersonasTbl> filteredPersonas) {
        this.filteredPersonas = filteredPersonas;
    }

    public void setItemsClientes(List<RnGcPersonasTbl> itemsClientes) {
        this.itemsCliente = itemsClientes;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            System.out.println("persistAction: " + persistAction);
            try {
                System.out.println(persistAction.equals(PersistAction.CREATE) + " | " + persistAction.equals(PersistAction.UPDATE));
                if (persistAction.equals(PersistAction.CREATE) || persistAction.equals(PersistAction.UPDATE)) {
                    if (obtenerClientes(selected) && persistAction.equals(PersistAction.CREATE)) {
                        System.out.println("persistActionIF: " + persistAction);
                        JsfUtil.addErrorMessage("El Cliente " + selected.getRfc() + " ya esta registrado en el sistema");
                    } else {
                        System.out.println("persistActionELSE: " + persistAction);
                        if(selected.getNombre() != null && !selected.getNombre().isEmpty())
                            selected.setNombre(selected.getNombre().toUpperCase());
                        if(selected.getNombreFiscal() != null && !selected.getNombreFiscal().isEmpty())
                            selected.setNombreFiscal(selected.getNombreFiscal().toUpperCase());
                        selected.setTipoPersona("Cliente");
                        getFacade().edit(selected);
                        JsfUtil.addSuccessMessage(successMessage);
                        itemsCreadoPor();
                    }

                } else {
                    getFacade().remove(selected);
                    JsfUtil.addSuccessMessage(successMessage);
                    itemsCreadoPor();
                }
                //JsfUtil.addSuccessMessage(successMessage);
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

    public List<RnGcPersonasTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcPersonasTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcPersonasTbl.class)
    public static class RnGcPersonasTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcPersonasTblController controller = (RnGcPersonasTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcPersonasTblController");
            return controller.getFacade().find(getKey(value));
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
            if (object instanceof RnGcPersonasTbl) {
                RnGcPersonasTbl o = (RnGcPersonasTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcPersonasTbl.class.getName()});
                return null;
            }
        }

    }

    public List<RnGcPersonasTbl> itemsCreadoPor() {
        try {
            if (usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")) {
                items = getFacade().findAll();
            } else {
                items = getFacade().obtenerCreadoPor(usuarioFirmado.obtenerIdUsuario());
            }
        } catch (ClassFormatError ex) {
            ex.printStackTrace();
        }
        return items;
    }

    /*public RnGcPersonasTbl obtenerPersonaDatos(String Rfc, String Nombre, Integer Id){
        System.out.println("obtenerPersonaDatos");
        itemsCliente = getFacade().obtenerPersona(Id);
        System.out.println("personaId: " + personaId);
        return personaId;
    }*/
    public boolean obtenerClientes(RnGcPersonasTbl persona) {
        List<RnGcPersonasTbl> listaPersonas = null;
        boolean valor = false;
        listaPersonas = getFacade().obtenerCreadoPor(usuarioFirmado.obtenerIdUsuario());
        for (int i = 0; i < listaPersonas.size(); i++) {
            if (selected.getRfc().toUpperCase().equals(listaPersonas.get(i).getRfc())) {
                System.out.println("La persona ya existe");
                valor = true;
                break;
            } else {
                System.out.println("La persona no existe");
                valor = false;
            }
        }
        System.out.println("valor: " + valor);
        return valor;
    }
    
    public String iniMayusculas(String nombre){
        char[] caracteres = null;
        if(nombre != null){
            nombre = nombre.toLowerCase();
            caracteres = nombre.toCharArray();
            caracteres[0] = Character.toUpperCase(caracteres[0]);
            for(int i = 0; i < nombre.length() -2; i++){
                if(caracteres[i] == ' ' || caracteres[i] == '.' || caracteres[i] == ',')
                    caracteres[i+1] = Character.toUpperCase(caracteres[i+1]);
            }
        }
        return new String(caracteres);
    }

}
