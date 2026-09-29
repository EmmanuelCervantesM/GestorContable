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

@Named("registroPublicoController")
@SessionScoped
public class RegistroPublicoController implements Serializable {

    @EJB
    private RnGcUsuariosTblFacade rnGcUsuariosTblFacade;
    @EJB
    private RnGcCertificadosTblFacade rnGcCertificadosTblFacade;
    @EJB
    private RnGcTimbresTblFacade rnGcTimbresTblFacade;
    @EJB
    private RnGcImagenesTblFacade rnGcImagenesTblFacade;

    private static final int TIMBRES_INICIALES = 50; // provisional, ver CTR-02

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

    public String getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(String tipoCuenta) { this.tipoCuenta = tipoCuenta; }
    public String getRfc() { return rfc; }
    public void setRfc(String rfc) { this.rfc = rfc; }
    public boolean isRfcValidado() { return rfcValidado; }
    public boolean isMostrarRegistro() { return mostrarRegistro; }
    public void mostrarFormularioRegistro() { mostrarRegistro = true; }
    public void mostrarFormularioLogin() { mostrarRegistro = false; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getConfirmarPassword() { return confirmarPassword; }
    public void setConfirmarPassword(String confirmarPassword) { this.confirmarPassword = confirmarPassword; }
    public int getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(int codigoPostal) { this.codigoPostal = codigoPostal; }

    public UploadedFile getCertificadoCer() { return certificadoCer; }
    public void setCertificadoCer(UploadedFile certificadoCer) { this.certificadoCer = certificadoCer; }
    public UploadedFile getCertificadoKey() { return certificadoKey; }
    public void setCertificadoKey(UploadedFile certificadoKey) { this.certificadoKey = certificadoKey; }
    public String getCertificadoPassword() { return certificadoPassword; }
    public void setCertificadoPassword(String certificadoPassword) { this.certificadoPassword = certificadoPassword; }

    public UploadedFile getLogoDespacho() { return logoDespacho; }
    public void setLogoDespacho(UploadedFile logoDespacho) { this.logoDespacho = logoDespacho; }

    public void validarRfc() {
        rfcValidado = false;
        if (rfc == null || rfc.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Ingresa un RFC", ""));
            return;
        }
        RnGcUsuariosTbl existente = rnGcUsuariosTblFacade.obtenerRfcUsuario(rfc);
        if (existente != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Este RFC ya está registrado",
                            "Si el problema persiste, contacta a soporte"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "RFC disponible", "Completa el resto de tus datos"));
            rfcValidado = true;
        }
    }

    public String crearCuenta() {
        if (!rfcValidado) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Primero verifica tu RFC", ""));
            return null;
        }
        if (nombreCompleto == null || nombreCompleto.isEmpty()
                || email == null || email.isEmpty()
                || password == null || password.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Completa nombre, correo y contraseña", ""));
            return null;
        }
        if (!password.equals(confirmarPassword)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Las contraseñas no coinciden", ""));
            return null;
        }

        boolean certificadoRequerido = "CONTRIBUYENTE".equals(tipoCuenta);
        boolean certificadoProporcionado = certificadoCer != null && certificadoKey != null
                && certificadoPassword != null && !certificadoPassword.isEmpty();

        if (certificadoRequerido && !certificadoProporcionado) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Sube tu certificado (.cer y .key) y su contraseña", ""));
            return null;
        }

        try {
            String tipoDetectado = null;
            X509Certificate certParsed = null;
            if (certificadoProporcionado) {
                certParsed = CertificadoUtil.parseCertificado(certificadoCer.getContents());
                tipoDetectado = CertificadoUtil.detectarTipoCertificado(certParsed);
                if (tipoDetectado == null) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo determinar si el certificado es CSD o FIEL", ""));
                    return null;
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
            rnGcUsuariosTblFacade.create(usuario);

            if (certificadoProporcionado) {
                RnGcCertificadosTbl certificado = new RnGcCertificadosTbl();
                certificado.setCertificadoSelloDigital(certificadoCer.getContents());
                certificado.setLlavePrivada(certificadoKey.getContents());
                certificado.setContraseniaLlavePrivada(certificadoPassword);
                certificado.setNumeroCertificado(certParsed.getSerialNumber().toString());
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

            if ("DESPACHO".equals(tipoCuenta) && logoDespacho != null) {
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

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Cuenta creada correctamente",
                            "Ya puedes iniciar sesión con tu correo y contraseña"));
            return "/login.xhtml?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "No fue posible crear la cuenta",
                            "Intenta nuevamente"));
            return null;
        }
    }
}
