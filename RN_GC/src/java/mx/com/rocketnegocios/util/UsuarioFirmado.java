package mx.com.rocketnegocios.util;

import java.util.List; 
import java.util.ArrayList; 
import java.util.Map;
import javax.faces.context.FacesContext;

public class UsuarioFirmado {

    public int obtenerIdUsuario() {
        int usuarioId = 1;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, Object> map = facesContext.getExternalContext().getSessionMap();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key.equals("usuarioId")) {
                usuarioId = Integer.valueOf(value.toString());
            }
        }
        return usuarioId;
    }

    public String perfilUsuario() {
        String listaPerfiles = "";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, Object> map = facesContext.getExternalContext().getSessionMap();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key.equals("listaPerfiles")) {
                listaPerfiles = String.valueOf(value);
            }
        }
        return listaPerfiles;
    }
    /** Si el usuario firmado tiene el perfil ADMINISTRADOR. */
    public boolean esAdministrador() {
        return esAdministrador(perfilUsuario());
    }

    /**
     * Si la lista de perfiles de la sesión (",Perfil1,Perfil2") trae exactamente el perfil
     * ADMINISTRADOR; con contains() también pasaba cualquier perfil cuyo nombre lo incluyera.
     */
    public static boolean esAdministrador(Object listaPerfiles) {
        if (listaPerfiles == null) {
            return false;
        }
        for (String perfil : String.valueOf(listaPerfiles).split(",")) {
            if (perfil.trim().equalsIgnoreCase("ADMINISTRADOR")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Si una asignación de perfil sigue vigente: sin fecha final, o con fecha final de hoy en
     * adelante (se le suma un día para que el último día cuente completo). Es la misma regla
     * con la que MenuView arma el menú; el login la usa para no cargar perfiles vencidos.
     */
    public static boolean perfilVigente(java.util.Date fechaFinal) {
        if (fechaFinal == null) {
            return true;
        }
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(fechaFinal);
        cal.add(java.util.Calendar.DAY_OF_WEEK, 1);
        return cal.getTime().compareTo(new java.util.Date()) >= 0;
    }

    // lista de ids de usuario que te pertencen: tu mismo o si eres despacho, tu y tus contribuyentes :p
    public List<Integer> obtenerIdsAlcance(){
        List<Integer> ids = new ArrayList<>();
        FacesContext facesContext = FacesContext.getCurrentInstance(); 
        Map<String, Object> map = facesContext.getExternalContext().getSessionMap();
        Object valor = map.get("idsAlcanceUsuario");
        if (valor != null){
            for (String id: String.valueOf(valor).split(",")){
                if (!id.trim().isEmpty()){
                    ids.add(Integer.valueOf(id.trim()));
                }
            }
        }
        if (ids.isEmpty()){
            ids.add(obtenerIdUsuario());
        }
        return ids; 
    }

}
