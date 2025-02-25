package mx.com.rocketnegocios.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import mx.com.rocketnegocios.entities.RnGcCertificadosTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcCertificadosTblFacade;

import java.io.Serializable;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
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
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import org.apache.poi.util.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named("rnGcCertificadosTblController")
@SessionScoped
public class RnGcCertificadosTblController implements Serializable {

    @EJB
    private RnGcUsuariosTblFacade usuarioFacade;

    @EJB
    private mx.com.rocketnegocios.beans.RnGcCertificadosTblFacade ejbFacade;
    private List<RnGcCertificadosTbl> items = null;
    private RnGcCertificadosTbl selected;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private UploadedFile file;
    private RnGcUsuariosTbl usuarioId;
    private List<RnGcCertificadosTbl> itemsUsuarios = null;

    public RnGcCertificadosTblController() {
    }

    public RnGcCertificadosTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcCertificadosTbl selected) {
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

    private RnGcCertificadosTblFacade getFacade() {
        return ejbFacade;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public RnGcCertificadosTbl prepareCreate() {
        selected = new RnGcCertificadosTbl();
        initializeEmbeddableKey();
        System.out.println("prepareCreate" + selected.getCertificadoSelloDigital() + " | " +  selected.getLlavePrivada());
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCertificadosTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCertificadosTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcCertificadosTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcCertificadosTbl> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public List<RnGcCertificadosTbl> itemsXUsuario() {
        if (usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")) {
            itemsUsuarios = getFacade().findAll();
        } else {
            usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            itemsUsuarios = getFacade().obtenerCertificadosDeUsuario(usuarioId);
        }
        return itemsUsuarios;
    }

    public List<RnGcCertificadosTbl> getItemsUsuarios() {
        if (usuarioFirmado.perfilUsuario().contains("ADMINSITRADOR")) {
            itemsUsuarios = getFacade().findAll();
        } else {
            itemsUsuarios = getFacade().obtenerCreadoPor(usuarioFirmado.obtenerIdUsuario());
        }
        return itemsUsuarios;
    }

    public List<RnGcCertificadosTbl> certificadosCreadoPor() {
        if (usuarioFirmado.perfilUsuario().contains("ADMINSITRADOR")) {
            itemsUsuarios = getFacade().findAll();
        } else {
            itemsUsuarios = getFacade().obtenerCreadoPor(usuarioFirmado.obtenerIdUsuario());
        }
        return itemsUsuarios;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            System.out.println(selected.getCertificadoSelloDigital() + " | " + selected.getContraseniaLlavePrivada() + " | "
                    + selected.getLlavePrivada() + " | " + selected.getCreadoPor() + " | " + selected.getFechaCreacion() + " | "
                    + selected.getUltimaActualizacionPor() + " | " + selected.getUltimaFechaActualizacion() + " | " + selected.getUsuariosId());
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

    public RnGcCertificadosTbl getRnGcCertificadosTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcCertificadosTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcCertificadosTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcCertificadosTbl.class)
    public static class RnGcCertificadosTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcCertificadosTblController controller = (RnGcCertificadosTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcCertificadosTblController");
            return controller.getRnGcCertificadosTbl(getKey(value));
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
            if (object instanceof RnGcCertificadosTbl) {
                RnGcCertificadosTbl o = (RnGcCertificadosTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcCertificadosTbl.class.getName()});
                return null;
            }
        }

    }

    public void fileUpload(FileUploadEvent event) throws Exception {
        byte[] certificado = null;
        byte[] llave = null;
        if (event != null) {
            if (event.getFile().getContentType().equals("application/x-x509-ca-cert")) {
                certificado = IOUtils.toByteArray(event.getFile().getInputstream());
                selected.setCertificadoSelloDigital(certificado);
                InputStream is = event.getFile().getInputstream();
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                X509Certificate certificate = (X509Certificate) cf.generateCertificate(is);
                byte[] byteArray = certificate.getSerialNumber().toByteArray();
                selected.setNumeroCertificado(new String(byteArray));
                selected.setFechaVencimiento(certificate.getNotAfter());
                System.out.println("getNotAfter: " + certificate.getNotAfter());
            } else if (event.getFile().getContentType().equals("application/octet-stream")) {
                llave = IOUtils.toByteArray(event.getFile().getInputstream());
                selected.setLlavePrivada(llave);
            }
        }
        validarFechaVencimiento();
    }
    
    public boolean validarFechaVencimiento(){
        boolean bool = true;
        if(selected != null && selected.getFechaVencimiento() != null){
            Date fecha = selected.getFechaVencimiento();
            System.out.println("fecha: " + fecha + " | " + new Date());
            int bool2 = fecha.compareTo(new Date());
            System.out.println("bool2: " + bool2);
            if(bool2 > 0)
                bool = false;
        }
        System.out.println("bool: " + bool);
        return bool;
    }

    public Date fechaVencimiento() {
        return this.selected.getFechaVencimiento();
    }

    public void prepararItemUsuario(RnGcUsuariosTbl usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<RnGcCertificadosTbl> itemsUsuario() {
        this.itemsUsuarios = getFacade().obtenerCertificadosDeUsuario(usuarioId);
        return itemsUsuarios;
    }

    public List<RnGcCertificadosTbl> listaCertificados() {
        if(usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")){
            items = getFacade().findAll();
        }else{
            usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            items = getFacade().obtenerCertificadosDeUsuario(usuarioId);
        }
        return items;
    }

    public List<RnGcCertificadosTbl> listaCertificadosActivos() {
        if (usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")) {
            items = getFacade().certificadosActivos();
        } else {
            usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            items = getFacade().obtenerCertificadosActivosDeUsuario(usuarioId);
        }
        return items;
    }

}
