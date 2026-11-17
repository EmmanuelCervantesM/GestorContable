package mx.com.rocketnegocios.util.listanegra;

import java.io.Serializable;

/**
 * CTR-12: referencia a la publicación en el DOF de una etapa de la lista negra. Si se
 * encontró la nota, {@link #isDirecto()} es true y {@link #getUrl()} la abre; si no, la
 * URL es el índice del DOF de ese día y el texto indica edición, fecha y oficio.
 */
public class EnlaceDof implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String etapa;
    private final String oficio;
    private final String fecha;
    private final String edicion;
    private final String url;
    private final boolean directo;

    public EnlaceDof(String etapa, String oficio, String fecha, String edicion, String url, boolean directo) {
        this.etapa = etapa;
        this.oficio = oficio;
        this.fecha = fecha;
        this.edicion = edicion;
        this.url = url;
        this.directo = directo;
    }

    public String getEtapa() {
        return etapa;
    }

    public String getOficio() {
        return oficio;
    }

    /** Fecha de publicación en el DOF, dd/mm/aaaa. */
    public String getFecha() {
        return fecha;
    }

    /** MAT, VES o null si no se conoce. */
    public String getEdicion() {
        return edicion;
    }

    public String getUrl() {
        return url;
    }

    public boolean isDirecto() {
        return directo;
    }

    /** "DOF 23/10/2018, edición matutina, oficio 500-05-2018-27105". */
    public String getTexto() {
        StringBuilder sb = new StringBuilder("DOF ").append(fecha);
        sb.append(", edición ").append(nombreEdicion(edicion));
        if (oficio != null && !oficio.isEmpty()) {
            sb.append(", oficio ").append(oficio);
        }
        return sb.toString();
    }

    public static String nombreEdicion(String edicion) {
        if ("MAT".equals(edicion)) {
            return "matutina";
        }
        if ("VES".equals(edicion)) {
            return "vespertina";
        }
        return "sin identificar";
    }
}
