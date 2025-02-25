package mx.com.rocketnegocios.beans;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.dao.loginDAO;
import mx.com.rocketnegocios.entities.RnGcUsuariosPerfilesTbl;
import mx.com.rocketnegocios.util.TrippleDes;
import mx.com.rocketnegocios.web.util.SSLEmail;
import org.apache.commons.codec.digest.DigestUtils;

@ManagedBean
@SessionScoped
public class Login implements Serializable {
    
    @EJB
    private RnGcUsuariosPerfilesTblFacade rnGeUsuarioPerfilesTblFacade;

    private static final long serialVersionUID = 109480182528386363L;

    @EJB
    private mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade ejbFacade;
    private RnGcUsuariosTbl usuarioTbl;

    private String pwd;
    private String msg;
    private String user;
    private String nombreCompleto;
    private String urlFotoModulo;
    private String menuSeleccionado;
    /*@PersistenceContext(unitName = "RN_GC")*/
    private EntityManager em;
    @Resource
    javax.transaction.UserTransaction utx;
    HttpSession session;

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }   

    public String validateUsernamePassword() throws ClassNotFoundException, Exception {
        System.out.println("Entre a validateUsernamePassword");
        //pwd = encriptarPwd(pwd);
        boolean valid = loginDAO.validate(user, pwd);
        System.out.println("valid: " + valid);
        if (valid) {
            System.out.println("Clave de Usuario y Password CORRECTO");
            setNombreCompleto(loginDAO.getNombreUsuario(user, pwd));
            if (session == null)
                session = SessionUtils.getSession();            
            session.setAttribute("username", user);
            session.setAttribute("nombreCompleto", getNombreCompleto());
            System.out.println("Usuario: " + String.valueOf(session.getAttribute("username")));
            System.out.println("NC: " + String.valueOf(session.getAttribute("nombreCompleto")));
            cargarPerfiles();
            return "admin.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    "loginButon",
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Clave de Usuario y Contraseña incorrectos",
                                    "Por favor ingresa la clave de usuario y contraseña correctos"));
            return "login.xhtml";
        }
    }

    public String logout() {
        System.out.println("logout");
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "/login.xhtml?faces-redirect=true";
    }

    public String getMenuSeleccionado() {
        System.out.println("getMenuSeleccionado()");
        return menuSeleccionado;
    }

    public void setMenuSeleccionado(String menuSeleccionado) {
        if (session == null)
            session = SessionUtils.getSession();
        session.setAttribute("nombreMenu", menuSeleccionado);
        System.out.println("setAttribute: " + menuSeleccionado);
        this.menuSeleccionado = menuSeleccionado;
    }

    public String getUrlFotoModulo() {
        String urlIcon = "/resources/images/logoAdminContable.png";
        if (session == null)
            session = SessionUtils.getSession();
        String menuseleccionado = String.valueOf(session.getAttribute("nombreMenu"));
        switch (menuSeleccionado) {
            default:
                urlIcon = "/resources/images/logoAdminContable.png";
                break;
        }
        System.out.println("menuSeleccionado: " + menuSeleccionado);
        this.urlFotoModulo = urlIcon;
        return urlFotoModulo;
    }

    public void setUrlFotoModulo(String urlFotoModulo) {
        this.urlFotoModulo = urlFotoModulo;
    }

    public static String toSHA1(String cadena) {
        return DigestUtils.shaHex(cadena);
    }

    public RnGcUsuariosTbl getRnGcUsuariosTbl(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public String modificarPassword() throws ClassNotFoundException {
        System.out.println("Modificando Password");
        int usuarioId = loginDAO.getUsuarioId(user);
        String paginaCargar = "login.xhtml";
        usuarioTbl = getRnGcUsuariosTbl(usuarioId);
        if (usuarioTbl != null) {
            if (!usuarioTbl.getUsuarioClave().contains("@")) {
                FacesContext.getCurrentInstance().addMessage(
                        "modifpwd",
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "La clave de usuario de " + usuarioTbl.getUsuarioClave() + " no es un correo valido",
                                "Por favor solicita la actualización de tus datos"));
                paginaCargar = "login.xhtml";
                System.out.println("ModificarPassword: " + usuarioTbl.getUsuarioClave() + " No es un correo valido");
            } else {
                SSLEmail mailObject = new SSLEmail();
                System.out.println("SSLEmail mailObject: " + mailObject.toString());
                if (mailObject.enviarCorreo(usuarioTbl.getUsuarioClave())) {
                    FacesContext.getCurrentInstance().addMessage(
                            "modifPwd",
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Se ha enviado al correo " + usuarioTbl.getUsuarioClave() + " las instrucciones para cambiar la contraseña",
                                    "Porfavor revisa tu bandeja de entrada y tu carpeta de spam"));
                    paginaCargar = "postEnvio.xhtml";
                    System.out.println("modificarPassword: Correo enviado a " + usuarioTbl.getUsuarioClave());
                } else {
                    FacesContext.getCurrentInstance().addMessage(
                            "modifPwd",
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "No fue posible enviar el correo electrónico a la dirección registrada",
                                    "Porfavor solicita al administrador la actualización de tus datos"));
                    System.out.println("modificarPassword: No fue posible enviar el correo a " + usuarioTbl.getUsuarioClave());
                }
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    "modifPwd",
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "No se encuentra usuario con esta clave",
                            "Por favor verifica tu información"));
            System.out.println("El usuario no esta registrado");
        }
        return paginaCargar;
    }

    private void cargarPerfiles() throws ClassNotFoundException {
        System.out.println("Entrando a cargarPerfiles");
        String listaPerfiles = "";
        List<RnGcUsuariosPerfilesTbl> listaUsuarioPerfiles;
        int usuarioId = loginDAO.getUsuarioId(user);
        System.out.println("UsuarioId: " + usuarioId);
        usuarioTbl = getRnGcUsuariosTbl(usuarioId);
        System.out.println("usuarioTbl: " + usuarioTbl);
        if (usuarioTbl != null) {
            listaUsuarioPerfiles = rnGeUsuarioPerfilesTblFacade.obtenerPerfilesPorUsuario(usuarioTbl);
            for (int i = 0; i < listaUsuarioPerfiles.size(); i++) {
                RnGcUsuariosPerfilesTbl get = listaUsuarioPerfiles.get(i);
                listaPerfiles = listaPerfiles + "," + get.getPerfilesId().getPerfilNombre();
            }
            session.setAttribute("usuarioId", usuarioId);
            session.setAttribute("listaPerfiles", listaPerfiles);
        }
        System.out.println("listaPerfiles: " + usuarioId + listaPerfiles);
    }
    
        public void persist(Object object){
        try{
            utx.begin();
            em.persist(object);
            utx.commit();
        }catch(Exception e){
            java.util.logging.Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException (e);
        }
    }
        
         public String encriptarPwd(String plainPwd) throws Exception{
        TrippleDes td;
        td = new TrippleDes();
        String encrypted=td.encrypt(plainPwd);

        System.out.println("Texto para Encryptar: "+ plainPwd);
        System.out.println("Texto Encryptado:" + encrypted);
        desencriptarPwd();
        
        return encrypted;
    }
         /*temporal*/
         public String desencriptarPwd() throws Exception {
            String encryptedPwd = "8WJDu4FfFn6CWtJYSYXk/A==";
            TrippleDes td;
            td = new TrippleDes();
            String decrypted = td.decrypt(encryptedPwd);
            System.out.println("Texto Encryptado:" + decrypted);
            return decrypted;
        }         
         /*temporal*/

}
