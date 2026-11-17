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
