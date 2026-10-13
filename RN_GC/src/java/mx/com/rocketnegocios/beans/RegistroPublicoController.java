package mx.com.rocketnegocios.beans;

import java.io.Serializable;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import mx.com.rocketnegocios.entities.RnGcCertificadosTbl;
import mx.com.rocketnegocios.entities.RnGcImagenesTbl;
import mx.com.rocketnegocios.entities.RnGcTimbresTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.CertificadoUtil;
import mx.com.rocketnegocios.util.TrippleDes;
import org.primefaces.model.UploadedFile;
import java.security.SecureRandom;
import java.util.List; 
import java.util.regex.Pattern; // importa la clase Pattern para usar expresiones regulares
// nuevos 
import mx.com.rocketnegocios.entities.RnGcPerfilesTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosPerfilesTbl;
// para ver los menus de los usuarios 
import java.util.ArrayList; 
import javax.annotation.Resource; // controlar las transacciones de la BD 


@Named("registroPublicoController")
@SessionScoped
public class RegistroPublicoController implements Serializable {

    @EJB
    private RnGcUsuariosTblFacade rnGcUsuariosTblFacade;
    // Controlar las transacciones en la base de datos 
    @Resource 
    private javax.transaction.UserTransaction utx;
    @EJB
    private RnGcCertificadosTblFacade rnGcCertificadosTblFacade;
    @EJB
    private RnGcTimbresTblFacade rnGcTimbresTblFacade;
    // Asignar perfiles 
    @EJB 
    private RnGcUsuariosPerfilesTblFacade rnGcUsuariosPerfilesTblFacade;
    // 
    @EJB 
    private RnGcPerfilesTblFacade rnGcPerfilesTblFacade; 
    @EJB
    private RnGcImagenesTblFacade rnGcImagenesTblFacade;
    // expresion regular para validar el RFC (3 o 4 letras, 6 digitos, 3 caracteres alfanumericos)
    private static final Pattern PATRON_RFC = Pattern.compile("^[A-ZÑ&]{3,4}\\d{6}[A-Z0-9]{3}$");


    private static final int TIMBRES_INICIALES = 50; // provisional, ver CTR-02

    // CORREO DE VERIFICACION
    @EJB
    private CorreoVerificacion correoVerificacion; 

    // despachoId para obtener el despacho al que pertenece el usuario 
    private Integer despachoId; 
    
    private String codigoIngresado; 
    private String codigoEnviado; 
    private String emailCodigo; 
    private Date codigoExpiracion;

    private String tipoCuenta;
    private String rfc;
    private boolean rfcValidado;
    private boolean mostrarRegistro;

    private String nombreCompleto;
    private String email;
    private String password;
    private String confirmarPassword;
    private int codigoPostal;

    private UploadedFile certificadoCer;
    private UploadedFile certificadoKey;
    private String certificadoPassword;

    private UploadedFile logoDespacho;

    // Getters and Setters
    public String getTipoCuenta() { return tipoCuenta; }
    // cuando se cambia el tipo de cuenta se invalida la validacion previa del RFC 
    public void setTipoCuenta(String tipoCuenta){
        if (this.tipoCuenta == null || !this.tipoCuenta.equals(tipoCuenta)){
            this.rfcValidado = false;
        }
        this.tipoCuenta = tipoCuenta;
    }
    public String getRfc() { return rfc; }
    // Cuando se cambia el RFC se invalida la validación previa
    public void setRfc(String rfc){
        if(this.rfc == null || !this.rfc.equals(rfc)){
            this.rfcValidado = false;
        }
        this.rfc = rfc;
    }
    public boolean isRfcValidado() { return rfcValidado; }
    public boolean isMostrarRegistro() { return mostrarRegistro; }
    public void mostrarFormularioRegistro() { mostrarRegistro = true; }
    public void mostrarFormularioLogin() { mostrarRegistro = false; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getEmail() {return email; }
    public void setEmail(String email) {this.email = email; }
    public String getPassword() {return password; }
    public void setPassword(String password) {this.password = password; }
    public String getConfirmarPassword() {return confirmarPassword; }
    public void setConfirmarPassword(String confirmarPassword) {this.confirmarPassword = confirmarPassword; }
    public int getCodigoPostal() {return codigoPostal; }
    public void setCodigoPostal(int codigoPostal) {this.codigoPostal = codigoPostal; }

    public UploadedFile getCertificadoCer() {return certificadoCer; }
    public void setCertificadoCer(UploadedFile certificadoCer) {this.certificadoCer = certificadoCer; }
    public UploadedFile getCertificadoKey() {return certificadoKey; }
    public void setCertificadoKey(UploadedFile certificadoKey) {this.certificadoKey = certificadoKey; }
    public String getCertificadoPassword() {return certificadoPassword; }
    public void setCertificadoPassword(String certificadoPassword) {this.certificadoPassword = certificadoPassword; }

    public UploadedFile getLogoDespacho() {return logoDespacho; }
    public void setLogoDespacho(UploadedFile logoDespacho) {this.logoDespacho = logoDespacho;}

    // Getters y Setters para despachoId
    public Integer getDespachoId() {return despachoId;}
    public void setDespachoId (Integer despachoId) {this.despachoId = despachoId;}


    // metodos para el correo de verificacion 
    public String getCodigoIngresado() { return codigoIngresado;}
    public void setCodigoIngresado(String codigoIngresado) {this.codigoIngresado = codigoIngresado;}


    public void validarRfc() {
        rfcValidado = false;
        if (rfc == null || rfc.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ingresa un RFC", ""));
            return;
        }
        rfc = rfc.trim().toUpperCase();
        if (!PATRON_RFC.matcher(rfc).matches()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El RFC no tiene un formato válido", "Debe ser un RFC de persona física (13 caracteres) o moral (12 caracteres)"));
            return;
        }
        if (tipoCuenta == null || tipoCuenta.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Selecciona un tipo de cuenta", ""));
            return;
        }
        // Verificar si el RFC ya está registrado
        List<RnGcUsuariosTbl> existentes = rnGcUsuariosTblFacade.obtenerUsuariosPorRfc(rfc);
        boolean yaTieneEsteTipo = existentes.stream().anyMatch(u -> tipoCuenta.equals(u.getTipoCuenta()) || u.getTipoCuenta() == null || u.getTipoCuenta().isEmpty());
        if (yaTieneEsteTipo) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este RFC ya tiene una cuenta de tipo " + tipoCuenta, "Un mismo RFC solo puede tener una cuenta de Despacho y una de Contribuyente"));
            return;
        } else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "RFC válido", "Puedes continuar con el registro"));
            rfcValidado = true;
        }
    }


    // metodo para enviar el correo de verificacion
    public void enviarCodigoVerificacion(){
        if (email == null || email.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ingresa un correo electrónico", ""));
            return;
        }
        codigoEnviado = String.format("%06d", new SecureRandom().nextInt(1000000)); // Genera un codigo de 6 digitos para la verificacion 
        codigoExpiracion = new Date(System.currentTimeMillis() + 10*60*1000L); // 10 minutos de expiracion
        emailCodigo = email; //guardar el correo al que se envio el codigo 
        try {
            correoVerificacion.enviarCodigo(email, codigoEnviado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Código enviado a" + email, "Revisatu bandeja de entrada y spam :)"));
            } catch (Exception e){
                e.printStackTrace(); //Si no se puede mandarel correo 
                emailCodigo = null; // limpia el error en el correo 
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo enviar el correo de verificación", "Intenta nuevamente o contacta a soporte"));
        }
    }

    private boolean correoVerificado(){
        if (codigoEnviado == null || codigoExpiracion == null || emailCodigo == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Solicita el código de verificación", ""));
            return false; // no se ha enviado un codigo 
        }
        if (!emailCodigo.equals(email)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"El correo ingresado no coincide con el que se envió el código", ""));
            return false; // el correo no coincide 
        }
        if (new Date().after(codigoExpiracion)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"El código de verificación ha expirado :(", "Solicita un nuevo código :)"));
            return false; // el codigo ha expirado
        }
        if (codigoIngresado == null || !codigoEnviado.equals(codigoIngresado)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Código incorrecto :(", "Verifica el código enviado a tu correo"));
            return false; // el codigo no coincide
        }
        return true;
    }

    public String crearCuenta() {
        if (!rfcValidado) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Primero verifica tu RFC", ""));
            return null;
        }
        // Verificar si el RFC ya está registrado
        List<RnGcUsuariosTbl> existentes =rnGcUsuariosTblFacade.obtenerUsuariosPorRfc(rfc);
        boolean yaTieneEsteTipo = existentes.stream().anyMatch(u -> tipoCuenta.equals(u.getTipoCuenta()) || u.getTipoCuenta() == null || u.getTipoCuenta().isEmpty());
        if (yaTieneEsteTipo) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este RFC ya tiene una cuenta de tipo " + tipoCuenta, "Un mismo RFC solo puede tener una cuenta de Despacho y una de Contribuyente"));
            return null;
        }


        if (nombreCompleto == null || nombreCompleto.isEmpty()
                || email == null || email.isEmpty()
                || password == null || password.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Completa nombre, correo y contraseña", ""));
            return null;
        }
        if (codigoPostal < 10000 || codigoPostal > 99999){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage (FacesMessage.SEVERITY_ERROR, "El código postal debe tener 5 dígitos", ""));
            return null;
        }
        if (!password.equals(confirmarPassword)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Las contraseñas no coinciden", ""));
            return null;
        }
        if (!correoVerificado()){
            return null; // el correo no ha sido verificado
        }

        // Validar archivos de certificado si es cuenta de contribuyente
        boolean certificadoRequerido = "CONTRIBUYENTE".equals(tipoCuenta);
        boolean certificadoProporcionado = certificadoCer != null && certificadoKey != null && certificadoPassword != null && !certificadoPassword.isEmpty();

        if (certificadoRequerido && !certificadoProporcionado) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Sube tu certificado (.cer y .key) y su contraseña", ""));
            return null;
        }

        if (certificadoProporcionado) {
            if (certificadoCer.getFileName() == null || !certificadoCer.getFileName().toLowerCase().endsWith(".cer")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El archivo de certificado debe tener extensión .cer", ""));
                return null;
            }
            if (certificadoKey.getFileName() == null || !certificadoKey.getFileName().toLowerCase().endsWith(".key")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El archivo de llave privada debe tener extensión .key", ""));
                return null;
            }
            if (certificadoCer.getContents().length == 0 || certificadoCer.getContents().length > 8192) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El archivo .cer no tiene un tamaño válido", ""));
                return null;
            }
            if (certificadoKey.getContents().length == 0 || certificadoKey.getContents().length > 8192) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El archivo .key no tiene un tamaño válido", ""));
                return null;
            }
        }

        try {
            utx.begin();
            String tipoDetectado = null;
            X509Certificate certParsed = null;
            if (certificadoProporcionado) {
                try {
                    certParsed = CertificadoUtil.parseCertificado(certificadoCer.getContents());
                } catch (Exception exParse) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El archivo .cer no es un certificado válido", "Verifica que sea un archivo .cer real del SAT"));
                    throw new IllegalStateException();
                }
                try {
                    certParsed.checkValidity();
                } catch (Exception exVigencia) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El certificado está vencido o aún no es vigente", "Sube un certificado vigente"));
                    throw new IllegalStateException();
                }
                tipoDetectado = CertificadoUtil.detectarTipoCertificado(certParsed);
                
                // Validar que el número de serie del certificado tenga 20 caracteres
                String numeroSerie = CertificadoUtil.obtenerNumeroSerie(certParsed);
                if (numeroSerie.length() != 20) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "El certificado no tiene un número de serie válido del SAT", "Verifica que sea un certificado CSD o e.firma oficial"));
                    throw new IllegalStateException();
                }


                if (tipoDetectado == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo determinar si el certificado es CSD o FIEL", ""));
                    throw new IllegalStateException();
                }
            }


            TrippleDes td = new TrippleDes();
            String contraseniaCifrada = td.encrypt(password);

            RnGcUsuariosTbl usuario = new RnGcUsuariosTbl();
            usuario.setUsuarioClave(email);
            usuario.setRfc(rfc);
            usuario.setNombreCompleto(nombreCompleto);
            usuario.setEmail(email);
            usuario.setCodigoPostal(codigoPostal);
            usuario.setFechaAlta(new Date());
            usuario.setEstado("A");
            usuario.setTipoCuenta(tipoCuenta);
            usuario.setTipoUsuario("DESPACHO".equals(tipoCuenta) ? "DE" : "CO");
            usuario.setNoUsuarios(0);
            usuario.setContrasenia(contraseniaCifrada);
            usuario.setFechaContrasenia(new Date());
            usuario.setNoIntentos(0);
            usuario.setCreadoPor(0);
            usuario.setFechaCreacion(new Date());
            usuario.setUltimaActualizacionPor(0);
            usuario.setUltimaFechaActualizacion(new Date());
            //si es cuenta de contribuyente, asociar el despacho si se proporcionó un despachoid 
            RnGcUsuariosTbl despacho = null;
            if (despachoId != null && despachoId > 0) {
                despacho = rnGcUsuariosTblFacade.obtenerUsuarioPorId(despachoId);
                if (despacho == null || !"DESPACHO".equals(despacho.getTipoCuenta())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El ID de Despacho no es válido", "Verifica el número e intenta de nuevo"));
                    throw new IllegalStateException();
                }
                usuario.setDespachoId(despacho);
            }
            rnGcUsuariosTblFacade.create(usuario);
            asignarPerfil(usuario);

            if (certificadoProporcionado) {
                RnGcCertificadosTbl certificado = new RnGcCertificadosTbl();
                certificado.setCertificadoSelloDigital(certificadoCer.getContents());
                certificado.setLlavePrivada(certificadoKey.getContents());
                certificado.setContraseniaLlavePrivada(certificadoPassword);
                // Obtener el número de serie del certificado y asignarlo al campo numeroCertificado
                certificado.setNumeroCertificado(CertificadoUtil.obtenerNumeroSerie(certParsed));
                certificado.setFechaVencimiento(certParsed.getNotAfter());
                certificado.setEstado("Activo");
                certificado.setNombreCertificado(tipoDetectado + " - " + rfc);
                certificado.setTipoCertificado(tipoDetectado);
                certificado.setUsuariosId(usuario);
                certificado.setCreadoPor(0);
                certificado.setFechaCreacion(new Date());
                certificado.setUltimaActualizacionPor(0);
                certificado.setUltimaFechaActualizacion(new Date());
                rnGcCertificadosTblFacade.create(certificado);
            }
            
            // verificar el logo que subieron 
                       // verificar el logo que subieron 
            if ("DESPACHO".equals(tipoCuenta) && logoDespacho != null && logoDespacho.getFileName() != null && !logoDespacho.getFileName().isEmpty()) {
                if (!logoDespacho.getFileName().toLowerCase().endsWith(".png")) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El logo debe ser un archivo .png", ""));
                    throw new IllegalStateException();
                }
                if (logoDespacho.getContents().length == 0 || logoDespacho.getContents().length > 512000) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El logo no debe pesar más de 500 KB", ""));
                    throw new IllegalStateException();
                }
                java.awt.image.BufferedImage imagenLogo = null;
                try {
                    imagenLogo = javax.imageio.ImageIO.read(new java.io.ByteArrayInputStream(logoDespacho.getContents()));
                } catch (Exception exImg) {
                    imagenLogo = null;
                }
                if (imagenLogo == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El archivo no es una imagen PNG válida", ""));
                    throw new IllegalStateException();
                }
                if (imagenLogo.getWidth() < 50 || imagenLogo.getHeight() < 50) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El logo debe medir al menos 50x50 px", ""));
                    throw new IllegalStateException();
                }
                RnGcImagenesTbl logo = new RnGcImagenesTbl();
                logo.setRfc(rfc);
                logo.setNombreImagen(logoDespacho.getFileName());
                logo.setFoto(logoDespacho.getContents());
                logo.setCreadoPor(0);
                logo.setFechaCreacion(new Date());
                logo.setUltimaActualizacionPor(0);
                logo.setUltimaFechaActualizacion(new Date());
                rnGcImagenesTblFacade.create(logo);
            }


            Calendar unAnioDespues = Calendar.getInstance();
            unAnioDespues.add(Calendar.YEAR, 1);

            RnGcTimbresTbl timbres = new RnGcTimbresTbl();
            timbres.setProveedor("Inicial");
            timbres.setTimbresTotal(TIMBRES_INICIALES);
            timbres.setTimbresRestantes(TIMBRES_INICIALES);
            timbres.setTimbresUsados(0);
            timbres.setEstado("Activo");
            timbres.setFechaInicio(new Date());
            timbres.setFechaFin(unAnioDespues.getTime());
            timbres.setUsuarioId(usuario);
            timbres.setCreadoPor(0);
            timbres.setFechaCreacion(new Date());
            timbres.setUltimaActualizacionPor(0);
            timbres.setUltimaFechaActualizacion(new Date());
            rnGcTimbresTblFacade.create(timbres);

            utx.commit();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Cuenta creada correctamente", "Ya puedes iniciar sesión con tu correo y contraseña"));
            return "/login.xhtml?faces-redirect=true";
        } catch (Exception e) {
            try {
                utx.rollback();
            } catch (Exception exRollback){
                exRollback.printStackTrace();
            }
            e.printStackTrace();
            return null;
        }
    }
    //Para asignaar un nuveo perfil sea despacho a contribuyente a los nuevos usuarios y ver los menus  
    private void asignarPerfil(RnGcUsuariosTbl usuario) {
        List<Integer> perfilesAAsignar = new ArrayList<>();
        if ("DESPACHO".equals(tipoCuenta)) {
            perfilesAAsignar.add(2);  // DESPACHO (permisos)
            perfilesAAsignar.add(17); // Acciones
            perfilesAAsignar.add(18); // Facturas
            perfilesAAsignar.add(19); // Reportes
            perfilesAAsignar.add(21); // Catalogos
            perfilesAAsignar.add(27); // Catalogos Nomina
            perfilesAAsignar.add(28); // Usuarios Despacho
        } else {
            perfilesAAsignar.add(3);  // CONTRIBUYENTE (permisos)
            perfilesAAsignar.add(30); // Facturas Contribuyente
            perfilesAAsignar.add(31); // Contabilidad Contribuyente
            perfilesAAsignar.add(33); // Nomina Contribuyente
            perfilesAAsignar.add(35); // Reportes Contribuyente
            perfilesAAsignar.add(36); // Acciones Contribuyente
        }
        for (Integer perfilId : perfilesAAsignar) {
            RnGcPerfilesTbl perfil = rnGcPerfilesTblFacade.find(perfilId);
            RnGcUsuariosPerfilesTbl usuarioPerfil = new RnGcUsuariosPerfilesTbl();
            usuarioPerfil.setUsuariosId(usuario);
            usuarioPerfil.setPerfilesId(perfil);
            usuarioPerfil.setFechaInicial(new Date());
            usuarioPerfil.setCreadoPor(0);
            usuarioPerfil.setFechaCreacion(new Date());
            usuarioPerfil.setUltimaActualizacionPor(0);
            usuarioPerfil.setUltimaFechaActualizacion(new Date());
            rnGcUsuariosPerfilesTblFacade.create(usuarioPerfil);
        }
    }

}
