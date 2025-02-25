package mx.com.rocketnegocios.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import mx.com.rocketnegocios.beans.RnGcPerfilesTblFacade;
import mx.com.rocketnegocios.beans.RnGcTimbresTblFacade;
import mx.com.rocketnegocios.entities.RnGcPerfilesTbl;
import mx.com.rocketnegocios.entities.RnGcTimbresTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosPerfilesTbl;
import mx.com.rocketnegocios.util.TrippleDes;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import org.apache.poi.util.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

@Named("rnGcUsuariosTblController")
@SessionScoped
public class RnGcUsuariosTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade usuarioFacade;
    private RnGcUsuariosTbl usuarioId = null;

    @EJB
    private mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade ejbFacade;

    @EJB
    private mx.com.rocketnegocios.beans.RnGcUsuariosPerfilesTblFacade usuarioPerfilFacade;

    @EJB
    private RnGcPerfilesTblFacade perfilesFacade;

    @EJB
    private RnGcTimbresTblFacade timbresFacade;

    private List<RnGcUsuariosTbl> items = null;
    private RnGcUsuariosTbl selected;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private RnGcUsuariosPerfilesTbl usuarioPerfil;
    private RnGcUsuariosTbl usuarioVar;
    private RnGcPerfilesTbl perfilId;
    private Date fechaInicial;
    private Date fechaFinal;
    private List<RnGcUsuariosTbl> itemsUsuarios = null;
    private UploadedFile file;
    private List<RnGcUsuariosTbl> filteredUsuarios;
    private List<RnGcTimbresTbl> timbres;
    private List<RnGcUsuariosTbl> listaGlobalUsuarios = new ArrayList<>();
    private List<RnGcUsuariosTbl> listaFinalFinal = null;
    private RnGcUsuariosTbl usuarioRfc;
    private ArrayList<String> listaTiposUsuarios = new ArrayList<>();
    private RnGcUsuariosTbl numeroUsuarios;
    private int totalUsuarios;
    private boolean valor = true, editar = false;
    private RnGcUsuariosTbl usuarioEditar;
    private List<RnGcPerfilesTbl> listaPerfiles = null;
    private RnGcUsuariosTbl usuarioCert, user;

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    public RnGcUsuariosTbl getUser() {
        user = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        return user;
    }

    public void setUser(RnGcUsuariosTbl user) {
        this.user = user;
    }

    public RnGcUsuariosTbl getUsuarioCert() {
        return usuarioCert;
    }

    public void setUsuarioCert(RnGcUsuariosTbl usuarioCert) {
        this.usuarioCert = usuarioCert;
    }

    public boolean isValor() {
        return valor;
    }

    public void setValor(boolean valor) {
        this.valor = valor;
    }

    public RnGcUsuariosTbl getUsuarioEditar() {
        return usuarioEditar;
    }

    public void setUsuarioEditar(RnGcUsuariosTbl usuarioEditar) {
        this.usuarioEditar = usuarioEditar;
    }

    public RnGcUsuariosTbl getNumeroUsuarios() {
        return numeroUsuarios;
    }

    public void setNumeroUsuarios(RnGcUsuariosTbl numeroUsuarios) {
        this.numeroUsuarios = numeroUsuarios;
    }

    public int getTotalUsuarios() {
        return totalUsuarios;
    }

    public void setTotalUsuarios(int totalUsuarios) {
        this.totalUsuarios = totalUsuarios;
    }

    public RnGcUsuariosTbl getUsuarioRfc() {
        return usuarioRfc;
    }

    public void setUsuarioRfc(RnGcUsuariosTbl usuarioRfc) {
        this.usuarioRfc = usuarioRfc;
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

    public ArrayList<String> getListaTiposUsuarios() {
        listaTiposUsuarios = new ArrayList<>();
        RnGcUsuariosTbl usuarioId = getFacade().obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        if (usuarioId.getTipoUsuario() == null) {
            return listaTiposUsuarios;
        } else {
            if (usuarioId.getTipoUsuario().equals("AG")) {
                listaTiposUsuarios.add("AG");
                listaTiposUsuarios.add("AD");
                listaTiposUsuarios.add("CO");
                listaTiposUsuarios.add("AU");
            } else if (usuarioId.getTipoUsuario().equals("A")) {
                listaTiposUsuarios.add("AG");
                listaTiposUsuarios.add("AD");
                listaTiposUsuarios.add("CO");
                listaTiposUsuarios.add("AU");
            } else if (usuarioId.getTipoUsuario().equals("AD")) {
                listaTiposUsuarios.add("AD");
                listaTiposUsuarios.add("CO");
            } else if (usuarioId.getTipoUsuario().equals("AC")) {
                listaTiposUsuarios.add("AC");
                listaTiposUsuarios.add("CE");
            } else if (usuarioId.getTipoUsuario().equals("CO")) {
                listaTiposUsuarios.add("AU");
            }
        }
        return listaTiposUsuarios;
    }

    public void setListaTiposUsuarios(ArrayList<String> listaTiposUsuarios) {
        this.listaTiposUsuarios = listaTiposUsuarios;
    }

    public RnGcUsuariosTblController() {
    }

    public RnGcUsuariosTbl getSelected() {
        if (selected == null) {
            selected = new RnGcUsuariosTbl();
        }
        return selected;
    }

    public void setSelected(RnGcUsuariosTbl selected) {
        this.selected = selected;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public RnGcUsuariosPerfilesTbl getUsuarioPerfil() {
        return usuarioPerfil;
    }

    public void setUsuarioPerfil(RnGcUsuariosPerfilesTbl usuarioPerfil) {
        this.usuarioPerfil = usuarioPerfil;
    }

    protected void setEmbeddableKeys() {
        selected.setRfc(selected.getRfc().toUpperCase());
        selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        selected.setUltimaFechaActualizacion(new Date());
        selected.setFechaContrasenia(new Date());
        //selected.setEmail(selected.getUsuarioClave());
    }

    protected void initializeEmbeddableKey() {
        selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        selected.setFechaCreacion(new Date());
        selected.setUsuarioId(getFacade().obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario()));
    }

    private RnGcUsuariosTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcUsuariosTbl prepareCreate() {
        System.out.println("prepareCreate");
        selected = new RnGcUsuariosTbl();
        usuarioPerfil = new RnGcUsuariosPerfilesTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        if (selected != null) {            
            try {
                //Codificar la Contraseña. Modif 080923 IMUNOZ
                selected.setContrasenia(encriptarPwd(selected.getContrasenia())); 
                if (usuarioId.getTipoUsuario().equals("CO")) {
                    if (selected.getRfc().toUpperCase().equals(usuarioId.getRfc())) {
                        usuariosPermitidos();
                        //pruebasMayusculas(selected.getNombreCompleto());
                        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcUsuariosTblCreated"));
                        if (!JsfUtil.isValidationFailed()) {
                            items = null;    // Invalidate list of items to trigger re-query.
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! No se puede crear el usuario  con un RFC distinto al del Administrador Contribuyente ", usuarioId.getRfc()));
                    }
                } else {
                    usuariosPermitidos();
                    //pruebasMayusculas(selected.getNombreCompleto());
                    persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcUsuariosTblCreated"));
                    if (!JsfUtil.isValidationFailed()) {
                        items = null;    // Invalidate list of items to trigger re-query.
                    }
                }

            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! No se puede crear el usuario.", null));
            }
        }
    }

    public void update() {
        usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        if (selected != null) {
            try { 
                selected.setContrasenia(encriptarPwd(selected.getContrasenia())); 
                if (usuarioId.getTipoUsuario().equals("CO")) {
                    if (selected.getRfc().equals(usuarioId.getRfc())) {
                        eliminarUsuarioPerfil(selected);
                        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcUsuariosTblUpdated"));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! No se puede actualizar el usuario con un RFC distinto al del Administrador Contribuyente ", usuarioId.getRfc()));
                    }
                } else {
                    eliminarUsuarioPerfil(selected);
                    persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcUsuariosTblUpdated"));
                }
            } catch (Exception ex) {
                Logger.getLogger(RnGcUsuariosTblController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcUsuariosTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcUsuariosTbl> getItems() {
        items = getFacade().findAll();
        return items;
    }
    
    public void eliminarUsuarioPerfil(RnGcUsuariosTbl usuario){
        List<RnGcUsuariosPerfilesTbl> listaUsuarioPerfil = new ArrayList<>();
        listaUsuarioPerfil = usuarioPerfilFacade.obtenerPerfilesPorUsuario(usuario);
        for(RnGcUsuariosPerfilesTbl usuarioPerfil : listaUsuarioPerfil){
            usuarioPerfilFacade.remove(usuarioPerfil);
        }
    }

    public void crearUsuarioPerfil(RnGcUsuariosTbl usuario) {
        listaPerfiles = new ArrayList<>();
        listaPerfiles = perfilesFacade.obtenerXTipoUsuario(usuario.getTipoUsuario());
        for (RnGcPerfilesTbl perfil : listaPerfiles) {
            RnGcUsuariosPerfilesTbl usuarioPerfil = new RnGcUsuariosPerfilesTbl();
            usuarioPerfil.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
            usuarioPerfil.setFechaCreacion(new Date());
            usuarioPerfil.setFechaInicial(usuario.getFechaAlta());
            usuarioPerfil.setFechaFinal(usuario.getFechaBaja());
            usuarioPerfil.setPerfilesId(perfil);
            usuarioPerfil.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
            usuarioPerfil.setUltimaFechaActualizacion(new Date());
            usuarioPerfil.setUsuariosId(usuario);
            usuarioPerfilFacade.edit(usuarioPerfil);
        }
    }
    
    public void valores(){
        editar = true;
        usuarioCert = user;
        System.out.print("1 - regimen: "+ usuarioCert.getRegimenId()+ "  telefono: "+ usuarioCert.getTelefono() + "  codigo: " + usuarioCert.getCodigoPostal());
        
    }
    
    public void actualizarUsuario() {
        if(usuarioCert != null){
            usuarioCert.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
            usuarioCert.setUltimaFechaActualizacion(new Date());
            System.out.print("regimen: "+ usuarioCert.getRegimenId()+ "  telefono: "+ usuarioCert.getTelefono() + "  codigo: " 
               + usuarioCert.getCodigoPostal() + "  correo: " + usuarioCert.getEmail()+ "  contraseña: " + usuarioCert.getPasswordEmail());
            getFacade().edit(usuarioCert);
            System.out.print("Datos actualizados correctamente");
            JsfUtil.addSuccessMessage("Datos actualizados correctamente");
        }
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    selected = getFacade().refreshFromDB(selected);
                    crearUsuarioPerfil(selected);
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
                    //JsfUtil.addErrorMessage(msg);
                    JsfUtil.addErrorMessage("Transacción marcada para reversión.");
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public RnGcUsuariosTbl getRnGcUsuariosTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcUsuariosTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcUsuariosTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public List<RnGcUsuariosTbl> obtenerUsuarios() {
        if (usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")) {
            itemsUsuarios = getFacade().findAll();
        } else {
            itemsUsuarios = getFacade().buscaCreadoPor(usuarioFirmado.obtenerIdUsuario());
        }
        return itemsUsuarios;
    }

    public List<RnGcUsuariosTbl> usuarioCertifcados() {
        if (usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")) {
            itemsUsuarios = getFacade().findAll();
        } else {
            itemsUsuarios = getFacade().obtenerListaUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        }
        return itemsUsuarios;
    }

    @FacesConverter(forClass = RnGcUsuariosTbl.class)
    public static class RnGcUsuariosTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcUsuariosTblController controller = (RnGcUsuariosTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcUsuariosTblController");
            return controller.getRnGcUsuariosTbl(getKey(value));
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
            if (object instanceof RnGcUsuariosTbl) {
                RnGcUsuariosTbl o = (RnGcUsuariosTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcUsuariosTbl.class.getName()});
                return null;
            }
        }

    }

    public List<RnGcUsuariosTbl> itemCreadoPor() {
        try {
            if (usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")) {
                itemsUsuarios = getFacade().findAll();
            } else {
                itemsUsuarios = getFacade().buscaCreadoPor(usuarioFirmado.obtenerIdUsuario());
            }
        } catch (ClassFormatError ex) {
            ex.printStackTrace();
        }
        return itemsUsuarios;
    }

    public RnGcUsuariosTbl buscarUsuarioPorClave(String claveUsuario) {
        selected = getFacade().obtenerUsuarioPorClave(claveUsuario);
        return selected;
    }

    public RnGcUsuariosTbl buscarUsuarioPorId(int Id) {
        RnGcUsuariosTbl usuarioId = getFacade().obtenerUsuarioPorId(Id);
        if (usuarioId != null) {
            return usuarioId;
        } else {
            return usuarioId = getFacade().obtenerUsuarioPorId(6);
        }
    }

    public String buscarUsuario() {
        RnGcUsuariosTbl usuarioId = getFacade().obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        return usuarioId.getTipoUsuario();
    }

    public void updatePass() {
        final String username = "conta-arc@rocketnegocios.com.mx";
        final String password = "ga1mJ7$4";
        Properties props = new Properties();
        props.put("mail.smtp.host", "rocketnegocios.com.mx");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "*");
        System.out.println("props: " + props);
        //Session session = Session.getDefaultInstance(props, null);
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        MimeMessage message = new MimeMessage(session);
        try {
            if (selected.getUsuarioClave().contains("@")) {
                //Crear cuerpo del mensaje
                session.getProperties().put("mail.smtp.strattls.enable", "true");
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setText("Su contraseña fue cambiada a " + selected.getContrasenia());

                MimeMultipart multiPart = new MimeMultipart();
                multiPart.addBodyPart(mimeBodyPart);
                message.setFrom(new InternetAddress(username, "Cambio de contraseña"));                                     //Agregar remitente
                message.setSubject("Cambio de contraseña");                                                                       //Agregar asunto al correo
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(selected.getUsuarioClave()));        //Agregar destinatario(s)
                message.setContent(multiPart);                                                                                       //Agregar cuerpo del mensaje
                //Enviar mensaje
                Transport transport = session.getTransport("smtp");
                transport.connect(username, password);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcUsuariosTblUpdatedPass") + selected.getUsuarioClave());
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("El correo " + selected.getUsuarioClave() + " no es valido"));
            }
        } catch (MessagingException | UnsupportedEncodingException ex) {
            FacesContext.getCurrentInstance().addMessage(password, new FacesMessage("Error al enviar Correo"));
        }
    }

    public void onRowSelect(SelectEvent event) {
        JsfUtil.addSuccessMessage("Usuario seleccionado: " + ((RnGcUsuariosTbl) event.getObject()).getUsuarioClave());
    }

    public void fileUpload(FileUploadEvent event) throws Exception {
        byte[] certificado = null;
        byte[] llave = null;
        if (event != null) {
            if (event.getFile().getContentType().equals("application/x-x509-ca-cert")) {
                certificado = IOUtils.toByteArray(event.getFile().getInputstream());
                selected.setCertificadoSello(certificado);
                InputStream is = new FileInputStream((File) event.getFile());
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                X509Certificate certificate = (X509Certificate) cf.generateCertificate(is);
                byte[] byteArray = certificate.getSerialNumber().toByteArray();
                String noSerie = new String(byteArray);
                Date fechaFin = certificate.getNotAfter();
            } else if (event.getFile().getContentType().equals("application/octet-stream")) {
                llave = IOUtils.toByteArray(event.getFile().getInputstream());
                selected.setLlavePrivada(llave);
            }
        }
    }

    public List<RnGcUsuariosTbl> getFilteredUsuarios() {
        return filteredUsuarios;
    }

    public void setFilteredUsuarios(List<RnGcUsuariosTbl> filteredUsuarios) {
        this.filteredUsuarios = filteredUsuarios;
    }

    public RnGcUsuariosTbl obtenerUsuario() {
        selected = getFacade().obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        return selected;
    }

    public String obtenerCreador(int id) {
        selected = getFacade().obtenerUsuarioPorId(id);
        return selected.getNombreCompleto();
    }

    public List<RnGcTimbresTbl> timbresTotal() {
        usuarioVar = getFacade().obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        return timbres = timbresFacade.obtenerPorUsuario(usuarioVar);
    }

    public List<RnGcUsuariosTbl> obtenerUsuariosFinales() {
        if (usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")) {
            listaFinalFinal = new ArrayList<>();
            listaFinalFinal = getFacade().buscarTodo();
        } else {
            //if (listaFinalFinal == null) {
            List<RnGcUsuariosTbl> listaFinalUsuariosCreadosPorUsuario = null;
            usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            listaFinalUsuariosCreadosPorUsuario = obtenerUsuariosCreadosPorUsuario(usuarioId);

            listaFinalFinal = listaFinalUsuariosCreadosPorUsuario.stream().distinct().collect(Collectors.toList());
            //}
        }
        return listaFinalFinal;
    }

    public List<RnGcUsuariosTbl> obtenerUsuariosFinalesExterno(RnGcUsuariosTbl usuarioId) {
        if (listaFinalFinal == null) {
            List<RnGcUsuariosTbl> listaFinalUsuariosCreadosPorUsuario = null;
            listaFinalUsuariosCreadosPorUsuario = obtenerUsuariosCreadosPorUsuario(usuarioId);
            listaFinalFinal = listaFinalUsuariosCreadosPorUsuario.stream().distinct().collect(Collectors.toList());
        }
        return listaFinalFinal;
    }

    public List<RnGcUsuariosTbl> obtenerUsuariosCreadosPorUsuario(RnGcUsuariosTbl userId) {
        List<RnGcUsuariosTbl> listaUsuariosCreadosPorUsuario = new ArrayList<>();
        listaUsuariosCreadosPorUsuario = null;
        listaUsuariosCreadosPorUsuario = usuarioFacade.obtenerListaUsuario(userId);

        if (!listaUsuariosCreadosPorUsuario.isEmpty()) {
            for (int i = 0; i < listaUsuariosCreadosPorUsuario.size(); i++) {
                listaGlobalUsuarios.add(listaUsuariosCreadosPorUsuario.get(i));
            }
            for (int n = 0; n < listaUsuariosCreadosPorUsuario.size(); n++) {
                //obtenerUsuariosCreadosPorUsuario(listaUsuariosCreadosPorUsuario.get(n));
                listaGlobalUsuarios = listaGlobalUsuarios.stream().distinct().collect(Collectors.toList());
            }
        }
        return listaGlobalUsuarios;
    }

    public int obtenerNumeroUsuarios() {
        List<RnGcUsuariosTbl> listaUsuariosCreadosPorUsuario = new ArrayList<>();
        listaUsuariosCreadosPorUsuario = null;
        RnGcUsuariosTbl usuarioId2 = null;

        usuarioId2 = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        totalUsuarios = usuarioId2.getNoUsuarios();
        listaUsuariosCreadosPorUsuario = usuarioFacade.obtenerListaUsuario(usuarioId2);
        listaUsuariosCreadosPorUsuario.size();
        if (!listaUsuariosCreadosPorUsuario.isEmpty()) {
            totalUsuarios -= listaUsuariosCreadosPorUsuario.size();
            if (totalUsuarios > 0) {
                valor = false;
            } else {
                valor = true;
            }
        }
        return totalUsuarios;
    }

    public RnGcUsuariosTbl obtenerUsuarioParaEditar() {
        usuarioEditar = new RnGcUsuariosTbl();
        usuarioEditar = getFacade().obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        return usuarioEditar;
    }
    
    public boolean obtenerTipoUsuario(){
        String tipoUsuario = null;
        boolean bool = false;
        tipoUsuario = getFacade().obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario()).getTipoUsuario();
        if(tipoUsuario != null){
            if(tipoUsuario.contains("AG") || tipoUsuario.contains("AD"))
                bool = true;
        }
        return bool;
    }
    
    public void usuariosPermitidos(){
        RnGcUsuariosTbl usuarioRegis = new RnGcUsuariosTbl();
        usuarioRegis = obtenerUsuarioParaEditar();
        if(usuarioRegis.getTipoUsuario().contains("AD")){
            if(selected.getTipoUsuario().contains("AD")){
                selected.setNoUsuarios(50);
            }else if(selected.getTipoUsuario().contains("CO")){
                selected.setNoUsuarios(3);
            }else{
                selected.setNoUsuarios(0);
            }
        }else if(usuarioRegis.getTipoUsuario().contains("AG")){
            if(selected.getTipoUsuario().contains("AD")){
                selected.setNoUsuarios(50);
            }else if(selected.getTipoUsuario().contains("CO")){
                selected.setNoUsuarios(3);
            }else{
                selected.setNoUsuarios(0);
            }
        }
        System.out.println("noUsuarios: " + selected.getNoUsuarios());
    }
    
    public String iniMayusculas(String nombre) {
        nombre = nombre.toLowerCase();
        char[] caracteres = nombre.toCharArray();
        caracteres[0] = Character.toUpperCase(caracteres[0]);
        for (int i = 0; i < nombre.length() - 2; i++) {
            if (caracteres[i] == ' ' || caracteres[i] == '.' || caracteres[i] == ',') {
                caracteres[i + 1] = Character.toUpperCase(caracteres[i + 1]);
            }
        }
        return new String(caracteres);
    }
    
    public void pruebasMayusculas(String persona){
        String vacias[] = {"De", "Y", "Con", "Del", "El", "En", "Es", "No", "Para", "Pero", "Por", "Que", "Se", "Si", "Sin", "Solo", "Tan", "Te", "Tu"};
        persona = iniMayusculas(persona.toLowerCase());
        
        String[] parts = persona.split(" ");
        List<String> nombreFinal = new ArrayList<>();
        String nombreFin = "";
        
        for(String nombre : parts){
            nombreFinal.add(nombre);
        }
        for(int i = 0; i < nombreFinal.size(); i++){
            for(String nombre : vacias){
                if(nombreFinal.get(i).equals(nombre))
                    nombreFinal.set(i, nombre.toLowerCase());
            }
        }
        for(String nombre : nombreFinal){
            nombreFin = nombreFin + nombre + " ";
        }
        selected.setNombreCompleto(nombreFin.substring(0, nombreFin.length()-1));
        System.out.println("nombreCompleto: " + selected.getNombreCompleto());
    }

    public String encriptarPwd(String plainPwd) throws Exception{
        TrippleDes td;
        td = new TrippleDes();
        String encrypted=td.encrypt(plainPwd);

        System.out.println("String To Encrypt: "+ plainPwd);
        System.out.println("Encrypted String:" + encrypted);
        
        return encrypted;
    }

    public String desencriptarPwd(String encryptedPwd) throws Exception{
        TrippleDes td;
        td = new TrippleDes();
        String decrypted=td.decrypt(encryptedPwd);

        System.out.println("String To Decrypt: "+ encryptedPwd);
        System.out.println("Decrypted String:" + decrypted);
        
        return decrypted;
    }

}