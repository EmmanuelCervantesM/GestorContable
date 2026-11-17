package mx.com.rocketnegocios.util.listanegra;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CTR-12: lectura de las páginas del Diario Oficial de la Federación (el proyecto no
 * tiene librería de HTML, así que se usan expresiones regulares sobre el marcado conocido).
 *
 * Índice del día: https://dof.gob.mx/index.php?year=AAAA&month=MM&day=DD&edicion=MAT|VES
 * Nota:            https://dof.gob.mx/nota_detalle.php?codigo=NNNNNNN&fecha=DD/MM/AAAA
 */
public final class DofParser {

    public static final String URL_BASE = "https://dof.gob.mx/";
    public static final String[] EDICIONES = {"MAT", "VES"};

    private static final Pattern NOTA = Pattern.compile(
            "nota_detalle\\.php\\?codigo=(\\d+)&(?:amp;)?fecha=([0-9]{2}/[0-9]{2}/[0-9]{4})\"[^>]*>([^<]*)<");
    private static final Pattern FILA = Pattern.compile("(?is)<tr\\b.*?</tr>");
    private static final Pattern CELDA = Pattern.compile("(?is)<td\\b.*?</td>");
    private static final Pattern ENTIDAD = Pattern.compile("&(#x?[0-9a-fA-F]+|[a-zA-Z]+);");

    private DofParser() {
    }

    /** Una liga a nota del índice de un día. */
    public static class NotaDof {

        private final String codigo;
        private final String fecha;
        private final String titulo;

        public NotaDof(String codigo, String fecha, String titulo) {
            this.codigo = codigo;
            this.fecha = fecha;
            this.titulo = titulo;
        }

        public String getCodigo() {
            return codigo;
        }

        public String getFecha() {
            return fecha;
        }

        public String getTitulo() {
            return titulo;
        }
    }

    public static String urlIndice(int anio, int mes, int dia, String edicion) {
        return String.format("%sindex.php?year=%d&month=%02d&day=%02d&edicion=%s", URL_BASE, anio, mes, dia, edicion);
    }

    public static String urlNota(String codigo, String fechaDdMmAaaa) {
        return URL_BASE + "nota_detalle.php?codigo=" + codigo + "&fecha=" + fechaDdMmAaaa;
    }

    /** Todas las ligas a notas del índice de un día, sin repetir código, con el título en texto plano. */
    public static List<NotaDof> notasDelIndice(String html) {
        List<NotaDof> notas = new ArrayList<>();
        List<String> codigos = new ArrayList<>();
        Matcher m = NOTA.matcher(html == null ? "" : html);
        while (m.find()) {
            String titulo = textoPlano(m.group(3));
            if (titulo.isEmpty() || codigos.contains(m.group(1))) {
                continue;
            }
            codigos.add(m.group(1));
            notas.add(new NotaDof(m.group(1), m.group(2), titulo));
        }
        return notas;
    }

    /**
     * Dígitos del número de oficio de un campo del CSV o de un título del DOF; es la llave para empatar:
     * "500-05-2018-16632 de fecha 01 de junio de 2018" -> "50005201816632",
     * "900 04 00 00 00 2026-254 de 12 de marzo de 2026" -> "900040000002026254".
     */
    public static String digitosOficio(String texto) {
        if (texto == null) {
            return "";
        }
        String t = textoPlano(texto);
        String norm = SemaforoListaNegra.normalizar(t);
        int inicio = norm.indexOf("oficio");
        if (inicio >= 0) {
            t = t.substring(inicio + "oficio".length());
            norm = norm.substring(inicio + "oficio".length());
        }
        int fin = indiceFin(norm);
        String numero = fin >= 0 ? t.substring(0, fin) : t;
        return numero.replaceAll("[^0-9]", "");
    }

    private static int indiceFin(String norm) {
        int fin = -1;
        for (String corte : new String[]{" de ", " del ", " mediante", " por el", ","}) {
            int i = norm.indexOf(corte);
            if (i > 0 && (fin < 0 || i < fin)) {
                fin = i;
            }
        }
        return fin;
    }

    /** Número de oficio tal como se publica, sin los espacios internos que mete el DOF ("2026- 16368"). */
    public static String numeroOficio(String texto) {
        if (texto == null) {
            return "";
        }
        String t = textoPlano(texto);
        String norm = SemaforoListaNegra.normalizar(t);
        int inicio = norm.indexOf("oficio");
        if (inicio >= 0) {
            t = t.substring(inicio + "oficio".length());
            norm = norm.substring(inicio + "oficio".length());
        }
        int fin = indiceFin(norm);
        String numero = fin >= 0 ? t.substring(0, fin) : t;
        return numero.trim().replaceAll("\\s*-\\s*", "-").replaceAll("\\s+", " ");
    }

    /** Título de un oficio de 49 Bis fracción X: "Oficio ... fracción X del artículo 49 Bis ...". */
    public static boolean es49BisX(String titulo) {
        String t = SemaforoListaNegra.normalizar(titulo);
        return t.startsWith("oficio") && t.contains("fraccion x del articulo 49 bis");
    }

    /**
     * Renglones de la tabla del "Anexo 1" de una nota de 49 Bis X: la tabla cuyo
     * encabezado empieza con "R.F.C.". Cada renglón trae 7 celdas: RFC, nombre, oficio
     * de resolución, fecha de notificación por buzón, fecha en que surtió efectos,
     * fecha de fijación en estrados y fecha en que surtió efectos. Solo regresa los
     * renglones que no son encabezado. Regresa null si la tabla no existe.
     */
    public static List<String[]> anexo49BisX(String html) {
        String h = html == null ? "" : html;
        int marca = h.indexOf("R.F.C.");
        if (marca >= 0) {
            // tabla más interna alrededor del encabezado: la nota anida tablas
            int ini = h.toLowerCase().lastIndexOf("<table", marca);
            int fin = h.toLowerCase().indexOf("</table>", marca);
            if (ini < 0 || fin < 0) {
                return null;
            }
            String tabla = h.substring(ini, fin + "</table>".length());
            List<String[]> filas = new ArrayList<>();
            Matcher tr = FILA.matcher(tabla);
            while (tr.find()) {
                List<String> celdas = new ArrayList<>();
                Matcher td = CELDA.matcher(tr.group());
                while (td.find()) {
                    celdas.add(textoPlano(td.group()));
                }
                if (celdas.isEmpty() || esEncabezado(celdas.get(0))) {
                    continue;
                }
                filas.add(celdas.toArray(new String[0]));
            }
            return filas;
        }
        return null;
    }

    private static boolean esEncabezado(String primeraCelda) {
        String t = SemaforoListaNegra.normalizar(primeraCelda);
        return t.isEmpty() || t.startsWith("r.f.c") || t.startsWith("fecha") || t.startsWith("notificacion")
                || t.startsWith("medio") || t.startsWith("estrados");
    }

    /** "de fecha 23 de junio de 2026" dentro de la nota, o null. */
    public static String fechaOficio(String html) {
        Matcher m = Pattern.compile("(?i)de fecha\\s+(\\d{1,2}\\s+de\\s+\\p{L}+\\s+de\\s+\\d{4})")
                .matcher(textoPlano(html));
        return m.find() ? m.group(1) : null;
    }

    /** Sin etiquetas, con entidades decodificadas y espacios colapsados. */
    public static String textoPlano(String html) {
        if (html == null) {
            return "";
        }
        String sinTags = html.replaceAll("(?is)<(script|style)\\b.*?</\\1>", " ")
                .replaceAll("(?i)<br\\s*/?>", " ")
                .replaceAll("<[^>]*>", " ");
        return decodificarEntidades(sinTags).replace(' ', ' ').replaceAll("\\s+", " ").trim();
    }

    static String decodificarEntidades(String texto) {
        Matcher m = ENTIDAD.matcher(texto);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String e = m.group(1);
            String r;
            if (e.startsWith("#x") || e.startsWith("#X")) {
                r = String.valueOf((char) Integer.parseInt(e.substring(2), 16));
            } else if (e.startsWith("#")) {
                r = String.valueOf((char) Integer.parseInt(e.substring(1)));
            } else {
                r = entidad(e);
            }
            m.appendReplacement(sb, Matcher.quoteReplacement(r == null ? m.group() : r));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private static String entidad(String nombre) {
        switch (nombre) {
            case "amp": return "&";
            case "lt": return "<";
            case "gt": return ">";
            case "quot": return "\"";
            case "apos": return "'";
            case "nbsp": return " ";
            case "aacute": return "á";
            case "eacute": return "é";
            case "iacute": return "í";
            case "oacute": return "ó";
            case "uacute": return "ú";
            case "Aacute": return "Á";
            case "Eacute": return "É";
            case "Iacute": return "Í";
            case "Oacute": return "Ó";
            case "Uacute": return "Ú";
            case "ntilde": return "ñ";
            case "Ntilde": return "Ñ";
            case "uuml": return "ü";
            case "Uuml": return "Ü";
            case "ordm": return "º";
            case "ordf": return "ª";
            case "deg": return "°";
            case "laquo": return "«";
            case "raquo": return "»";
            case "ldquo": return "“";
            case "rdquo": return "”";
            case "ndash": return "–";
            case "mdash": return "—";
            default: return null;
        }
    }
}
