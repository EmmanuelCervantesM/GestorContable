package mx.com.rocketnegocios.web.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SSLEmail {

    /**
     * Outgoing Mail (SMTP) Server requires TLS or SSL: smtp.gmail.com (use
     * authentication) Use Authentication: Yes Port for SSL: 465
     */
    public static void main(String[] args) {

        final String fromEmail = "michael.rodriguez@rocketnegocios.com.mx"; //requires valid gmail id
        final String password = "A123bc"; // correct password for gmail id
        final String toEmail = "michelfvta@gmail.com"; // can be any email id 

        System.out.println("SSLEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "rocketnegocios.com.mx"); //SMTP Host
        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", "465"); //SMTP Port
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.socketFactory.fallback", "false");

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        Session session = Session.getDefaultInstance(props, auth);
        session.setDebug(true);
        System.out.println("Session created");
        try {
            EmailUtil.sendEmail(session, toEmail, "SSLEmail Testing Subject", "SSLEmail Testing Body");

            //EmailUtil.sendAttachmentEmail(session, toEmail,"SSLEmail Testing Subject with Attachment", "SSLEmail Testing Body with Attachment");
            //EmailUtil.sendImageEmail(session, toEmail,"SSLEmail Testing Subject with Image", "SSLEmail Testing Body with Image");
        } catch (MessagingException ex) {
            Logger.getLogger(SSLEmail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SSLEmail.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /*public boolean enviarEmail(String pToEmail) {

        final String fromEmail = "michael.rodriguez@rocketnegocios.com.mx"; //requires valid gmail id
        final String password = "A123bc"; // correct password for gmail id
        final String toEmail = pToEmail; // can be any email id 

        System.out.println("SSLEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.user", fromEmail);
        props.put("mail.smtp.host", "rocketnegocios.com.mx"); //SMTP Host
        props.put("mail.smtp.port", "465"); //SMTP Port
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        //props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        //props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        //props.put("mail.smtp.socketFactory.fallback", "false");

        //SMTPAuthenticator auth = new SMTPAuthenticator();
        //Session session = Session.getInstance(props, auth);
        //session.setDebug(true);
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        //Session session = Session.getDefaultInstance(props, auth);
        Session session = Session.getInstance(props, auth);
        session.setDebug(true);
        System.out.println("Session created");
        try {
            EmailUtil.sendEmail(session, toEmail, "SSLEmail Testing Subject", "SSLEmail Testing Body");
        } catch (MessagingException ex) {
            Logger.getLogger(SSLEmail.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SSLEmail.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        //EmailUtil.sendAttachmentEmail(session, toEmail,"SSLEmail Testing Subject with Attachment", "SSLEmail Testing Body with Attachment");
        //EmailUtil.sendImageEmail(session, toEmail,"SSLEmail Testing Subject with Image", "SSLEmail Testing Body with Image");
        return true;
    }*/

    public boolean enviarCorreo(String pToEmail) {
        System.out.println("Enviar correo a: " + pToEmail);
        final String username = "conta-arc@rocketnegocios.com.mx";
        final String password = "ga1mJ7$4";
        final String toEmail = pToEmail; // can be any email id 

        System.out.println("SSLEmail Start");
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
        });//*/
        
        MimeMessage message = new MimeMessage(session);
        
        try {
            // Creo el cuerpo del correo
            session.getProperties().put("mail.smtp.strattls.enable", "true");
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setText("Envia un correo al administrador del sistema para solicitar tu cambio de contraseña");

            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(mimeBodyPart);

            // Agregar remitente al correo
            message.setFrom(new InternetAddress(username, "Cambio de Contraseña"));
            // Agregar el asunto al correo
            message.setSubject("Cambio de contraseña");            
            // Los destinatarios
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(pToEmail));
            //Contenido
            //message.setText("Envia un correo al administrador del sistema para solicitar tu cambio de contraseña");
            message.setContent(multiParte);

            //Enviar Mensaje
            Transport transport = session.getTransport("smtp");
            transport.connect(username, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("Correo enviado: " + transport + " | " + message + " | " + props);

        } catch (MessagingException | UnsupportedEncodingException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error al enviar correo"));
            System.out.println("Correo no enviado: " + ex);
            return false;
        }
        return true;
    }

}
