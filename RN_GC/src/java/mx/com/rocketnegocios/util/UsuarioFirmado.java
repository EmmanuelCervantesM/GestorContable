package mx.com.rocketnegocios.util;

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

}
