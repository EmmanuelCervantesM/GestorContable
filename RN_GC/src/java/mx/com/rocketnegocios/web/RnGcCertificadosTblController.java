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
import mx.com.rocketnegocios.util.CertificadoTipoUtil;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import org.apache.commons.ssl.PKCS8Key;
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
        if (!validarAntesDeGuardarAlta()) {
            return;
        }
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCertificadosTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        if (!validarAntesDeGuardarEdicion()) {
            return;
        }
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcCertificadosTblUpdated"));
    }

    /**
     * CTR-13 / CTR-13.1: validaciones de alta, en el orden en que deben
     * rechazar el guardado: contraseña de la llave, tipo determinado, y
     * unicidad GLOBAL de numeroCertificado (CTR-13.1). Si es FIEL, reemplaza
     * automáticamente la FIEL vigente anterior del usuario por Inactivo.
     */
    private boolean validarAntesDeGuardarAlta() {
        if (selected == null) {
            return true;
        }
        if (!contraseniaLlaveValida(selected.getLlavePrivada(), selected.getContraseniaLlavePrivada())) {
            JsfUtil.addErrorMessage("La contraseña de la llave privada es incorrecta");
            return false;
        }
        if (selected.getTipo() == null) {
            JsfUtil.addErrorMessage("No se pudo determinar si el certificado es FIEL o CSD");
            return false;
        }
        if (getFacade().existeNumeroCertificado(selected.getNumeroCertificado(), null)) {
            JsfUtil.addErrorMessage("Ya existe un certificado registrado con el número " + selected.getNumeroCertificado());
            return false;
        }
        if (CertificadoTipoUtil.TIPO_FIEL.equals(selected.getTipo())) {
            RnGcCertificadosTbl fielVigente = getFacade().obtenerFielVigente(selected.getUsuariosId());
            if (fielVigente != null) {
                fielVigente.setEstado("Inactivo");
                fielVigente.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                fielVigente.setUltimaFechaActualizacion(new Date());
                getFacade().edit(fielVigente);
            }
        }
        return true;
    }

    /**
     * CTR-13: numeroCertificado y tipo son de solo-lectura una vez creado el
     * registro; se restauran desde BD sin importar lo que llegue del formulario.
     * Si se recarga la llave/contraseña en la edición, se vuelve a validar.
     */
    private boolean validarAntesDeGuardarEdicion() {
        if (selected == null || selected.getId() == null) {
            return true;
        }
        RnGcCertificadosTbl original = getFacade().find(selected.getId());
        if (original != null) {
            selected.setNumeroCertificado(original.getNumeroCertificado());
            selected.setTipo(original.getTipo());
        }
        if (!contraseniaLlaveValida(selected.getLlavePrivada(), selected.getContraseniaLlavePrivada())) {
            JsfUtil.addErrorMessage("La contraseña de la llave privada es incorrecta");
            return false;
        }
        return true;
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
            // CTR-13.1: se discrimina por extension, no por content-type: cada
            // browser manda content-types distintos (.cer llega como
            // application/x-x509-ca-cert, pkix-cert u octet-stream segun
            // version/SO) y el gate anterior ignoraba el archivo en silencio.
            // El contenido real igual se valida abajo (X.509 y PKCS8).
            String nombreArchivo = event.getFile().getFileName() == null ? "" : event.getFile().getFileName().toLowerCase();
            if (nombreArchivo.endsWith(".cer")) {
                certificado = IOUtils.toByteArray(event.getFile().getInputstream());
                X509Certificate certificate;
                try {
                    CertificateFactory cf = CertificateFactory.getInstance("X.509");
                    certificate = (X509Certificate) cf.generateCertificate(new java.io.ByteArrayInputStream(certificado));
                } catch (CertificateException ex) {
                    JsfUtil.addErrorMessage("El archivo .cer no es un certificado X.509 válido");
                    return;
                }
                selected.setCertificadoSelloDigital(certificado);
                byte[] byteArray = certificate.getSerialNumber().toByteArray();
                selected.setNumeroCertificado(new String(byteArray));
                selected.setFechaVencimiento(certificate.getNotAfter());
                String tipo = CertificadoTipoUtil.determinarTipo(certificate);
                selected.setTipo(tipo);
                if (tipo == null) {
                    JsfUtil.addErrorMessage("No se pudo determinar si el certificado es FIEL o CSD");
                }
                System.out.println("getNotAfter: " + certificate.getNotAfter() + " | tipo: " + tipo);
            } else if (nombreArchivo.endsWith(".key")) {
                llave = IOUtils.toByteArray(event.getFile().getInputstream());
                if (!esLlavePrivadaValida(llave)) {
                    JsfUtil.addErrorMessage("El archivo .key no es una llave privada válida");
                    return;
                }
                selected.setLlavePrivada(llave);
            } else {
                JsfUtil.addErrorMessage("Solo se permiten archivos .cer y .key");
                return;
            }
        }
        validarFechaVencimiento();
    }

    /**
     * CTR-13: valida el contenido real del .key (no solo su extensión), sin
     * requerir aún la contraseña: una llave PKCS8 (cifrada o no) es una
     * secuencia ASN.1/DER, así que se descarta cualquier archivo que no
     * arranque con el tag SEQUENCE (0x30).
     */
    private boolean esLlavePrivadaValida(byte[] llave) {
        return llave != null && llave.length > 0 && (llave[0] & 0xFF) == 0x30;
    }

    /**
     * CTR-13: valida la contraseña de la llave privada intentando abrirla
     * (reutiliza PKCS8Key, ya usado en FacturarController para descifrar la
     * llave al timbrar).
     */
    private boolean contraseniaLlaveValida(byte[] llave, String contrasenia) {
        if (llave == null || llave.length == 0 || contrasenia == null) {
            return false;
        }
        try {
            PKCS8Key pkcs8 = new PKCS8Key(llave, contrasenia.toCharArray());
            return pkcs8.getDecryptedBytes() != null;
        } catch (Exception ex) {
            return false;
        }
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

    /**
     * CTR-13 punto 7: certificados que deben ofrecerse al timbrar. Solo CSD
     * vigentes y Activos; un FIEL nunca debe listarse aqui.
     */
    public List<RnGcCertificadosTbl> listaCertificadosCsdActivos() {
        if (usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")) {
            items = getFacade().certificadosCsdActivos();
        } else {
            usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            items = getFacade().obtenerCertificadosCsdActivosDeUsuario(usuarioId);
        }
        return items;
    }

}
