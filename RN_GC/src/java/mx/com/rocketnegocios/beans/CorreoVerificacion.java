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
                String mensajeTexto = "Tu código de verificación es: " + codigo + "\n\n"
                + "Expira en 10 minutos.\n\n"
                + "---\n\n"
                + "AVISO DE PRIVACIDAD CORTO\n"
                + "CONTA-ARC.COM, con domicilio en Andador de las Piadosas Edificio 4 Departamento 2, en la colonia Unidad Habitacional Santa Cruz, "
                + "en el municipio de Santa Ana Chiautempan, código postal 90802, en el estado de Tlaxcala, utilizará sus datos personales recabados "
                + "para enviarles publicidad de los productos que ofrecemos. Para mayor información acerca del tratamiento y de los derechos que "
                + "puede hacer valer, usted puede acceder al aviso de privacidad integral a través de www.conta-arc.com.";   
                // Modificar esto por el dominio correcto 
        mensaje.setText(mensajeTexto, "UTF-8");

        Transport.send(mensaje);
    }
}

