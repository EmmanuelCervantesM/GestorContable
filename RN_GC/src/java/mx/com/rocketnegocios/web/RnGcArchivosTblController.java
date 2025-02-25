package mx.com.rocketnegocios.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import mx.com.rocketnegocios.entities.RnGcArchivosTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcArchivosTblFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
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
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import mx.com.rocketnegocios.beans.RnGcPersonasTblFacade;
import mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade;
import mx.com.rocketnegocios.entities.RnGcCfdisTbl;
import mx.com.rocketnegocios.entities.RnGcPersonasTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.StreamedContent;

@Named("rnGcArchivosTblController")
@SessionScoped
public class RnGcArchivosTblController implements Serializable {

    @EJB
    private RnGcPersonasTblFacade personasFacade;

    @EJB
    private RnGcUsuariosTblFacade usuarioFacade;

    @EJB
    private mx.com.rocketnegocios.beans.RnGcArchivosTblFacade ejbFacade;
    private List<RnGcArchivosTbl> items = null;
    private RnGcArchivosTbl selected;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private RnGcCfdisTbl cfdis;
    private List<RnGcPersonasTbl> listaCorreos;
    private List<RnGcPersonasTbl> listaPersonas;
    private RnGcPersonasTbl personas;
    private List<RnGcArchivosTbl> itemsArchivos;
    private RnGcUsuariosTbl usuario;
    private StreamedContent downLoadFile;
    private List<String> listaCorreosEnviar;
    private String correo;

    public RnGcArchivosTblController() {
    }

    public RnGcArchivosTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcArchivosTbl selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        selected.setUltimaFechaActualizacion(new Date());
    }

    public List<RnGcPersonasTbl> getListaPersonas() {
        return listaPersonas;
    }

    public List<String> getListaCorreosEnviar() {
        if (listaCorreosEnviar == null) {
            this.listaCorreosEnviar = new ArrayList<>();
        }
        return listaCorreosEnviar;
    }

    public void setListaCorreosEnviar(List<String> listaCorreosEnviar) {
        this.listaCorreosEnviar = listaCorreosEnviar;
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public String getCorreo() {
        if (correo == null) {
            this.correo = "";
        }
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public RnGcPersonasTbl getPersonas() {
        if (personas == null) {
            this.personas = new RnGcPersonasTbl();
        }
        return personas;
    }

    public void setPersonas(RnGcPersonasTbl personas) {
        this.personas = personas;
    }

    public void setListaPersonas(List<RnGcPersonasTbl> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    protected void initializeEmbeddableKey() {
        selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        selected.setFechaCreacion(new Date());
    }

    private RnGcArchivosTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcArchivosTbl prepareCreate() {
        selected = new RnGcArchivosTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcArchivosTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcArchivosTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcArchivosTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcArchivosTbl> getItems() {
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

    public RnGcArchivosTbl getRnGcArchivosTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcArchivosTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcArchivosTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcArchivosTbl.class)
    public static class RnGcArchivosTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcArchivosTblController controller = (RnGcArchivosTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcArchivosTblController");
            return controller.getRnGcArchivosTbl(getKey(value));
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
            if (object instanceof RnGcArchivosTbl) {
                RnGcArchivosTbl o = (RnGcArchivosTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcArchivosTbl.class.getName()});
                return null;
            }
        }

    }

    public StreamedContent getDownLoadFile() {
        return downLoadFile;
    }

    public void setDownLoadFile(StreamedContent downLoadFile) {
        this.downLoadFile = downLoadFile;
    }

    public List<RnGcPersonasTbl> getListaCorreos() {
        return listaCorreos;
    }

    public void setListaCorreos(List<RnGcPersonasTbl> listaCorreos) {
        this.listaCorreos = listaCorreos;
    }

    public List<RnGcPersonasTbl> personasUsuario() {
        if (usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")) {
            listaPersonas = personasFacade.findAll();
        } else {
            usuario = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            listaPersonas = personasFacade.obtenerPersonasPorUsuario(usuario);
            System.out.println("listaPersonas: " + listaPersonas);
        }
        return listaPersonas;
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Correo Editado", (String) event.getObject());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edición Cancelada", (String) event.getObject());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<RnGcArchivosTbl> getItemsArchivos() {
        return itemsArchivos;
    }

    public void setItemsArchivos(List<RnGcArchivosTbl> itemsArchivos) {
        this.itemsArchivos = itemsArchivos;
    }

    public void prepareDocs(RnGcCfdisTbl cfdisId) {
        System.out.println("cfdisId: " + cfdisId);
        if (cfdisId != null) {
            itemsArchivos = getFacade().obtenerArchivos(cfdisId);
        }
    }

    public void agregarCorreo() {
        System.out.println("correo: " + correo);
        if (correo.contains("@")) {
            listaCorreosEnviar.add(correo);
            correo = "";
            JsfUtil.addSuccessMessage("Correo agregado");
        } else {
            JsfUtil.addErrorMessage("El formato del correo no es valido");
        }
    }

    public void correosSelect() throws AddressException {
        if (listaCorreosEnviar.size() > 0) {
            InternetAddress[] detinatarios = new InternetAddress[listaCorreosEnviar.size()];
            for (int a = 0; a < listaCorreosEnviar.size(); a++) {
                if (listaCorreosEnviar.get(a).contains("@")) {
                    detinatarios[a] = new InternetAddress(listaCorreosEnviar.get(a));
                }
            }
            //final String username = "conta-arc@rocketnegocios.com.mx";
            //final String password = "ga1mJ7$4";
            usuario = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            final String username = usuario.getEmail();
            final String password = usuario.getPasswordEmail();
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.ionos.mx");//*/
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
                OutputStream fos = null;
                MimeBodyPart mimeArchivoPdf = null;
                MimeBodyPart mimeArchivoXml = null;
                MimeMultipart multipart = new MimeMultipart();
                session.getProperties().put("mail.smtp.strattls.enable", "true");
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setText("Factura timbrada correctamente");
                System.out.println("itemsArchivos: " + itemsArchivos.size());
                if (itemsArchivos.get(0).getArchivoPdf() != null) {
                    fos = new FileOutputStream(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/archivoPdf.pdf")));
                    fos.write(itemsArchivos.get(0).getArchivoPdf());
                    mimeArchivoPdf = new MimeBodyPart();
                    mimeArchivoPdf.attachFile(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/archivoPdf.pdf")));
                    multipart.addBodyPart(mimeArchivoPdf);
                }
                if (itemsArchivos.get(0).getArchivoXml() != null) {
                    fos = new FileOutputStream(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/xmlTimbrado.xml")));
                    fos.write(itemsArchivos.get(0).getArchivoXml());
                    mimeArchivoXml = new MimeBodyPart();
                    fos.close();
                    mimeArchivoXml.attachFile(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Archivos/xmlTimbrado.xml")));
                    multipart.addBodyPart(mimeArchivoXml);
                }
                System.out.println("-------------------- bodypart: ");
                multipart.addBodyPart(mimeBodyPart);
                message.setFrom(new InternetAddress(username, itemsArchivos.get(0).getCfdiId().getNombreEmisor()));
                message.setSubject(itemsArchivos.get(0).getCfdiId().getNombreEmisor());
                message.setRecipients(Message.RecipientType.TO, detinatarios);
                message.setContent(multipart);
                //Enviar Mensaje
                System.out.println("-------------------- enviar: ");
                Transport transport = session.getTransport("smtp");
                transport.connect(username, password);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Archivos enviados a correo(s) seleccionado(s)"));
                System.out.println("Archivos enviados a correos seleccionado");
            } catch (MessagingException | IOException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error al enviar archivos"));
                System.out.println("Error al enviar archivos a correos seleccionado1: " + ex);
            } catch (NullPointerException er) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error al enviar archivos"));
                System.out.println("Error al enviar archivos a correos seleccionado2: " + er);
            }
            System.out.println("listaCorreos: " + listaCorreosEnviar);
            listaCorreosEnviar.clear();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No hay archivos para ser enviado. No se escogio ningun destinatario."));
            listaCorreosEnviar.clear();
        }
    }

}
