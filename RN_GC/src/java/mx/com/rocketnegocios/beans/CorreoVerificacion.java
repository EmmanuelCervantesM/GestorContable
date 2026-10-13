package mx.com.rocketnegocios.beans;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.Session; 
import javax.mail.Transport; 
import javax.mail.internet.InternetAddress; 
import javax.mail.internet.MimeMessage;

@Stateless
public class CorreoVerificacion {
    @Resource (name = "mail/RNGC")
    private Session sesionCorreo;

    public void enviarCodigo(String destinatario, String codigo) throws Exception {
        MimeMessage mensaje = new MimeMessage(sesionCorreo);
        mensaje.setFrom(new InternetAddress(sesionCorreo.getProperty("mail.from")));
        mensaje.setRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
        mensaje.setSubject("Código de verificación");
        mensaje.setText("Tu código de verificación es: " + codigo);
        Transport.send(mensaje);
    }
}

