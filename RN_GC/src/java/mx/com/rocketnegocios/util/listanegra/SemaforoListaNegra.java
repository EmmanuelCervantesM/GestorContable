package mx.com.rocketnegocios.util.listanegra;

import java.text.Normalizer;

/**
 * CTR-12: semáforo, igual para las tres listas:
 * Rojo = Definitivo, Ambar = Presunto, Verde = Desvirtuado, Gris = Sentencia Favorable.
 */
public final class SemaforoListaNegra {

    public static final String ROJO = "rojo";
    public static final String AMBAR = "ambar";
    public static final String VERDE = "verde";
    public static final String GRIS = "gris";

    private SemaforoListaNegra() {
    }

    /** Color para un valor de "situacionContribuyente"; null si el valor no se reconoce. */
    public static String color(String situacion) {
        String s = normalizar(situacion);
        if (s.startsWith("definitiv")) {
            return ROJO;
        }
        if (s.startsWith("presunt")) {
            return AMBAR;
        }
        if (s.startsWith("desvirtu")) {
            return VERDE;
        }
        if (s.startsWith("sentencia")) {
            return GRIS;
        }
        return null;
    }

    /** True mientras el contribuyente sigue en la lista (rojo o ámbar). */
    public static boolean vigente(String situacion) {
        String c = color(situacion);
        return ROJO.equals(c) || AMBAR.equals(c);
    }

    public static String normalizar(String texto) {
        if (texto == null) {
            return "";
        }
        String sinAcentos = Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return sinAcentos.trim().replaceAll("\\s+", " ").toLowerCase();
    }
}
