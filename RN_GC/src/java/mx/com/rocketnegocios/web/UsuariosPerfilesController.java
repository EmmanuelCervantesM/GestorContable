package mx.com.rocketnegocios.web;

import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import mx.com.rocketnegocios.beans.RnGcUsuariosPerfilesTblFacade;
import mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade;
import mx.com.rocketnegocios.entities.RnGcPerfilesTbl;
import mx.com.rocketnegocios.entities.RnGcPersonasTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosPerfilesTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;

@Named(value = "usuarioPerfilesController")
@SessionScoped
public class UsuariosPerfilesController implements Serializable {
    
    @EJB
    private mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade usuariosFacade;
    
    @EJB
    private mx.com.rocketnegocios.beans.RnGcUsuariosPerfilesTblFacade usuariosPerfilesFacade;
    
    private RnGcUsuariosTbl usuarioId;
    private RnGcUsuariosPerfilesTbl usuarioPerfilId;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private String usuarioClave;                //Usuarios
    private String Rfc;
    private String nombreCompleto;
    private String telefono;
    private String email;
    private Date fechaAlta;
    private Date fechaBaja;
    private String estado;
    private String contraseña;
    private int codigoPostal;
    private Date fechaContraseña;
    private int noIntentos;
    private int clienteId;
    private int proveedorId;
    private int creadoPor;
    private Date fechaCreacion;
    private int ultimaActualizacionPor;
    private Date ultimaFechaCreacion;
    private RnGcUsuariosTbl usuarioVar;
    private RnGcPersonasTbl personaId;      //Usuarios
    
    private RnGcPerfilesTbl perfilId;         //UsuariosPerfiles   
    private Date fechaInicial;
    private Date fechaFinal;                  //UsuariosPerfiles
    
    public UsuariosPerfilesController(){
        
    }

    public RnGcUsuariosTblFacade getUsuariosFacade() {
        return usuariosFacade;
    }

    public void setUsuariosFacade(RnGcUsuariosTblFacade usuariosFacade) {
        this.usuariosFacade = usuariosFacade;
    }

    public RnGcUsuariosPerfilesTblFacade getUsuariosPerfilesFacade() {
        return usuariosPerfilesFacade;
    }

    public void setUsuariosPerfilesFacade(RnGcUsuariosPerfilesTblFacade usuariosPerfilesFacade) {
        this.usuariosPerfilesFacade = usuariosPerfilesFacade;
    }

    public RnGcUsuariosTbl getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(RnGcUsuariosTbl usuarioId) {
        this.usuarioId = usuarioId;
    }

    public RnGcUsuariosPerfilesTbl getUsuarioPerfilId() {
        return usuarioPerfilId;
    }

    public void setUsuarioPerfilId(RnGcUsuariosPerfilesTbl usuarioPerfilId) {
        this.usuarioPerfilId = usuarioPerfilId;
    }

    public String getUsuarioClave() {
        return usuarioClave;
    }

    public void setUsuarioClave(String usuarioClave) {
        this.usuarioClave = usuarioClave;
    }

    public String getRfc() {
        return Rfc;
    }

    public void setRfc(String Rfc) {
        this.Rfc = Rfc;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaAlta() {
        this.fechaAlta =  new Date();
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Date getFechaContraseña() {
        return fechaContraseña;
    }

    public void setFechaContraseña(Date fechaContraseña) {
        this.fechaContraseña = fechaContraseña;
    }

    public int getNoIntentos() {
        return noIntentos;
    }

    public void setNoIntentos(int noIntentos) {
        this.noIntentos = noIntentos;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }

    public int getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(int creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getUltimaActualizacionPor() {
        return ultimaActualizacionPor;
    }

    public void setUltimaActualizacionPor(int ultimaActualizacionPor) {
        this.ultimaActualizacionPor = ultimaActualizacionPor;
    }

    public Date getUltimaFechaCreacion() {
        return ultimaFechaCreacion;
    }

    public void setUltimaFechaCreacion(Date ultimaFechaCreacion) {
        this.ultimaFechaCreacion = ultimaFechaCreacion;
    }

    public RnGcPersonasTbl getPersonaId() {
        return personaId;
    }

    public void setPersonaId(RnGcPersonasTbl personaId) {
        this.personaId = personaId;
    }

    public RnGcPerfilesTbl getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(RnGcPerfilesTbl perfilId) {
        this.perfilId = perfilId;
    }

    public Date getFechaInicial() {
        this.fechaInicial = new Date();
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public int getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public RnGcUsuariosTbl getUsuarioVar() {
        return usuarioVar;
    }

    public void setUsuarioVar(RnGcUsuariosTbl usuarioVar) {
        this.usuarioVar = usuarioVar;
    }
    
    public void prepararUsuario(){
        usuarioId = new RnGcUsuariosTbl();
        System.out.println("usuarioId: " + usuarioId);
        perfilId = new RnGcPerfilesTbl();
        System.out.println("perfilId: " + perfilId);
        usuarioPerfilId = new RnGcUsuariosPerfilesTbl();
        System.out.println("usuarioPerfilId: " + usuarioPerfilId);
    }
    
    public void crearUsuarioConPerfil(){
        usuarioId = new RnGcUsuariosTbl();
        perfilId = new RnGcPerfilesTbl();
        usuarioPerfilId = new RnGcUsuariosPerfilesTbl();
        
        usuarioId.setUsuarioClave(usuarioClave);
        usuarioId.setRfc(Rfc);
        usuarioId.setNombreCompleto(nombreCompleto);
        usuarioId.setTelefono(telefono);
        usuarioId.setEmail(usuarioClave);
        usuarioId.setCodigoPostal(codigoPostal);
        usuarioId.setFechaAlta(fechaAlta);
        usuarioId.setFechaBaja(fechaBaja);
        usuarioId.setEstado(estado);
        usuarioId.setContrasenia(contraseña);
        usuarioId.setFechaContrasenia(new Date());
        usuarioId.setNoIntentos(noIntentos);
        usuarioId.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        usuarioId.setFechaCreacion(new Date());
        usuarioId.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        usuarioId.setUltimaFechaActualizacion(new Date());
        usuarioId.setPersonaId(personaId);
        usuarioVar = usuariosFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        usuarioId.setUsuarioId(usuarioVar);
        usuariosFacade.edit(usuarioId);
        //usuarioId = usuariosFacade.refreshFromDB(usuarioId);
        
        System.out.println("usuarios Creado: " +
                getUsuarioClave() + " | " + getRfc() + " | " +
                getNombreCompleto() + " | " + getTelefono() + " | " +
                getEmail() + " | " + getFechaAlta() + " | " +
                getFechaBaja() + " | " + getEstado() + " | " +
                getContraseña() + " | " + getFechaContraseña() + " | " +
                getNoIntentos() + " | " + getPersonaId() + " | " +
                getUsuarioVar().getNombreCompleto() + " | " + getUsuarioId().getNombreCompleto() + " | " +
                getCodigoPostal());
        
        /*usuarioPerfilId.setUsuariosId(usuarioId);
        usuarioPerfilId.setPerfilesId(perfilId);
        usuarioPerfilId.setFechaInicial(fechaInicial);
        usuarioPerfilId.setFechaFinal(fechaFinal);
        usuarioPerfilId.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        usuarioPerfilId.setFechaCreacion(new Date());
        usuarioPerfilId.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        usuarioPerfilId.setUltimaFechaActualizacion(new Date());
        usuarioPerfilId = usuariosPerfilesFacade.refreshFromDB(usuarioPerfilId);
        
        System.out.println("Perfil Usuario Creado: " + " | " + usuarioPerfilId.getId() + " | " + 
                getUsuarioId().getNombreCompleto() + " | " + getPerfilId().getPerfilNombre() + " | " + 
                getFechaInicial() + " | " + getFechaFinal());*/
    }
    
    
}
