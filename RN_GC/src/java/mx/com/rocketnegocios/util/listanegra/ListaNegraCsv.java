package mx.com.rocketnegocios.util.listanegra;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import mx.com.rocketnegocios.entities.RnGcListaNegraTbl;

/**
 * CTR-12: lee los CSV del SAT de las listas 69-B y 69-B Bis
 * (https://www.sat.gob.mx/minisitio/DatosAbiertos/contribuyentes_publicados.html).
 *
 * Los archivos traen líneas de título antes del encabezado, los nombres pueden traer
 * saltos de línea entre comillas, el 69-B Bis viene en Windows-1252 y el 69-B en UTF-8
 * con la "Ñ" ya reemplazada por U+FFFD desde el SAT.
 */
public final class ListaNegraCsv {

    public static final Pattern RFC_VALIDO = Pattern.compile("[A-ZÑ&]{3,4}[0-9]{6}[A-Z0-9]{3}");

    private ListaNegraCsv() {
    }

    /** Resultado de leer un archivo CSV. */
    public static class Resultado {

        private final List<RnGcListaNegraTbl> filas = new ArrayList<>();
        private final List<String> errores = new ArrayList<>();
        private final List<String> repetidos = new ArrayList<>();
        private String error;

        public List<RnGcListaNegraTbl> getFilas() {
            return filas;
        }

        /** Registros que no se cargaron, con el motivo ("Registro 12: RFC ..."). */
        public List<String> getErrores() {
            return errores;
        }

        /**
         * RFC que vienen más de una vez (episodios distintos): se queda solo el episodio
         * que empezó al último y los demás se informan aquí.
         */
        public List<String> getRepetidos() {
            return repetidos;
        }

        /** Error que rechaza el archivo completo; null si el archivo se leyó. */
        public String getError() {
            return error;
        }
    }

    public static Resultado leer(byte[] datos, TipoListaNegra tipo) {
        Resultado r = new Resultado();
        if (datos == null || datos.length == 0) {
            r.error = "El archivo está vacío.";
            return r;
        }
        List<List<String>> registros = registros(decodificar(datos));
        int encabezado = -1;
        for (int i = 0; i < registros.size() && i < 10; i++) {
            List<String> reg = registros.get(i);
            if (reg.size() > 1 && "RFC".equalsIgnoreCase(reg.get(1).trim())) {
                encabezado = i;
                break;
            }
        }
        if (encabezado < 0) {
            r.error = "No se encontró el encabezado (columna RFC). El archivo no es un listado del SAT.";
            return r;
        }
        int columnas = registros.get(encabezado).size();
        if (columnas != tipo.getColumnasCsv()) {
            r.error = "El archivo tiene " + columnas + " columnas y la lista " + tipo.getEtiqueta()
                    + " lleva " + tipo.getColumnasCsv() + ". Revise que eligió la lista correcta.";
            return r;
        }
        Map<String, RnGcListaNegraTbl> porRfc = new LinkedHashMap<>();
        Map<String, Integer> registroDe = new LinkedHashMap<>();
        for (int i = encabezado + 1; i < registros.size(); i++) {
            List<String> reg = registros.get(i);
            int fila = i + 1;
            if (vacio(reg)) {
                continue;
            }
            if (reg.size() != columnas) {
                r.errores.add("Registro " + fila + ": trae " + reg.size() + " columnas en lugar de " + columnas + ".");
                continue;
            }
            String rfc = limpiarRfc(reg.get(1));
            if (!RFC_VALIDO.matcher(rfc).matches()) {
                r.errores.add("Registro " + fila + ": RFC «" + reg.get(1).trim() + "» no tiene formato válido.");
                continue;
            }
            RnGcListaNegraTbl nueva = tipo == TipoListaNegra.LISTA_69B ? mapear69B(rfc, reg) : mapear69BBis(rfc, reg);
            RnGcListaNegraTbl previa = porRfc.get(rfc);
            if (previa == null) {
                porRfc.put(rfc, nueva);
                registroDe.put(rfc, fila);
            } else if (masReciente(nueva, previa)) {
                r.repetidos.add("RFC " + rfc + ": se tomó el registro " + fila + " (" + nueva.getSituacionContribuyente()
                        + ") en lugar del " + registroDe.get(rfc) + " (" + previa.getSituacionContribuyente() + ").");
                porRfc.put(rfc, nueva);
                registroDe.put(rfc, fila);
            } else {
                r.repetidos.add("RFC " + rfc + ": se tomó el registro " + registroDe.get(rfc) + " (" + previa.getSituacionContribuyente()
                        + ") en lugar del " + fila + " (" + nueva.getSituacionContribuyente() + ").");
            }
        }
        r.filas.addAll(porRfc.values());
        return r;
    }

    /**
     * Entre dos episodios del mismo RFC, el vigente es el que empezó al último (la
     * fecha más antigua de su renglón). Si empatan, gana el estado más grave.
     */
    static boolean masReciente(RnGcListaNegraTbl a, RnGcListaNegraTbl b) {
        int cmp = Long.compare(inicio(a), inicio(b));
        if (cmp != 0) {
            return cmp > 0;
        }
        return gravedad(a.getSituacionContribuyente()) > gravedad(b.getSituacionContribuyente());
    }

    private static long inicio(RnGcListaNegraTbl ln) {
        long min = Long.MAX_VALUE;
        for (String f : new String[]{ln.getPublicacionSATpresuntos(), ln.getPublicacionDOFpresuntos(),
            ln.getPublicacionSATdesvirtuados(), ln.getPublicacionDOFdesvirtuados(),
            ln.getPublicacionSATdefinitivos(), ln.getPublicacionDOFdefinitivos(),
            ln.getPublicacionSATsentencia(), ln.getPublicacionDOFsentencia()}) {
            long t = fecha(f);
            if (t > 0 && t < min) {
                min = t;
            }
        }
        return min == Long.MAX_VALUE ? 0 : min;
    }

    /** dd/mm/aaaa como aaaammdd, 0 si no es fecha. */
    static long fecha(String ddMmAaaa) {
        if (ddMmAaaa == null || !ddMmAaaa.trim().matches("\\d{2}/\\d{2}/\\d{4}")) {
            return 0;
        }
        String[] p = ddMmAaaa.trim().split("/");
        return Long.parseLong(p[2] + p[1] + p[0]);
    }

    private static int gravedad(String situacion) {
        String c = SemaforoListaNegra.color(situacion);
        if (SemaforoListaNegra.ROJO.equals(c)) {
            return 4;
        }
        if (SemaforoListaNegra.AMBAR.equals(c)) {
            return 3;
        }
        if (SemaforoListaNegra.GRIS.equals(c)) {
            return 2;
        }
        return SemaforoListaNegra.VERDE.equals(c) ? 1 : 0;
    }

    /** RFC en mayúsculas y sin espacios; en un RFC, U+FFFD solo pudo haber sido una "Ñ". */
    public static String limpiarRfc(String rfc) {
        if (rfc == null) {
            return "";
        }
        return rfc.replace('�', 'Ñ').replaceAll("\\s+", "").toUpperCase();
    }

    private static RnGcListaNegraTbl mapear69B(String rfc, List<String> c) {
        RnGcListaNegraTbl ln = new RnGcListaNegraTbl();
        ln.setRfc(rfc);
        ln.setNombreContribuyente(texto(c.get(2)));
        ln.setSituacionContribuyente(texto(c.get(3)));
        ln.setNumeroFechaOficioGlobalPresuncionSAT(texto(c.get(4)));
        ln.setPublicacionSATpresuntos(texto(c.get(5)));
        ln.setNumeroFechaOficioGlobalPresuncionDOF(texto(c.get(6)));
        ln.setPublicacionDOFpresuntos(texto(c.get(7)));
        ln.setNumeroFechaOficioGlobalDesvirtuadoSAT(texto(c.get(8)));
        ln.setPublicacionSATdesvirtuados(texto(c.get(9)));
        ln.setNumeroFechaOficioGlobalDesvirtuadoDOF(texto(c.get(10)));
        ln.setPublicacionDOFdesvirtuados(texto(c.get(11)));
        ln.setNumeroFechaOficioGlobalDefinitivoSAT(texto(c.get(12)));
        ln.setPublicacionSATdefinitivos(texto(c.get(13)));
        ln.setNumeroFechaOficioGlobalDefinitivoDOF(texto(c.get(14)));
        ln.setPublicacionDOFdefinitivos(texto(c.get(15)));
        ln.setNumeroFechaOficioGlobalSentenciaSAT(texto(c.get(16)));
        ln.setPublicacionSATsentencia(texto(c.get(17)));
        ln.setNumeroFechaOficioGlobalSentenciaDOF(texto(c.get(18)));
        ln.setPublicacionDOFsentencia(texto(c.get(19)));
        return ln;
    }

    /** El 69-B Bis solo tiene las etapas de definitivo y sentencia favorable. */
    private static RnGcListaNegraTbl mapear69BBis(String rfc, List<String> c) {
        RnGcListaNegraTbl ln = new RnGcListaNegraTbl();
        ln.setRfc(rfc);
        ln.setNombreContribuyente(texto(c.get(2)));
        ln.setSituacionContribuyente(texto(c.get(3)));
        ln.setNumeroFechaOficioGlobalDefinitivoSAT(texto(c.get(4)));
        ln.setPublicacionSATdefinitivos(texto(c.get(5)));
        ln.setNumeroFechaOficioGlobalDefinitivoDOF(texto(c.get(6)));
        ln.setPublicacionDOFdefinitivos(texto(c.get(7)));
        ln.setNumeroFechaOficioGlobalSentenciaSAT(texto(c.get(8)));
        ln.setPublicacionSATsentencia(texto(c.get(9)));
        ln.setNumeroFechaOficioGlobalSentenciaDOF(texto(c.get(10)));
        ln.setPublicacionDOFsentencia(texto(c.get(11)));
        return ln;
    }

    private static String texto(String valor) {
        if (valor == null) {
            return null;
        }
        String t = valor.replaceAll("\\s+", " ").trim();
        return t.isEmpty() ? null : t;
    }

    private static boolean vacio(List<String> reg) {
        for (String v : reg) {
            if (v != null && !v.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /** UTF-8 si los bytes son UTF-8 válido; si no, Windows-1252. Quita el BOM. */
    public static String decodificar(byte[] datos) {
        String texto;
        try {
            texto = StandardCharsets.UTF_8.newDecoder()
                    .onMalformedInput(CodingErrorAction.REPORT)
                    .onUnmappableCharacter(CodingErrorAction.REPORT)
                    .decode(ByteBuffer.wrap(datos)).toString();
        } catch (CharacterCodingException ex) {
            texto = new String(datos, Charset.forName("windows-1252"));
        }
        if (!texto.isEmpty() && texto.charAt(0) == '﻿') {
            texto = texto.substring(1);
        }
        return texto;
    }

    /** Registros RFC 4180: los campos entre comillas pueden traer comas, "" y saltos de línea. */
    public static List<List<String>> registros(String texto) {
        List<List<String>> salida = new ArrayList<>();
        List<String> actual = new ArrayList<>();
        StringBuilder campo = new StringBuilder();
        boolean comillas = false;
        boolean hayDatos = false;
        int n = texto.length();
        for (int i = 0; i < n; i++) {
            char ch = texto.charAt(i);
            if (comillas) {
                if (ch == '"') {
                    if (i + 1 < n && texto.charAt(i + 1) == '"') {
                        campo.append('"');
                        i++;
                    } else {
                        comillas = false;
                    }
                } else {
                    campo.append(ch);
                }
                continue;
            }
            if (ch == '"') {
                comillas = true;
                hayDatos = true;
            } else if (ch == ',') {
                actual.add(campo.toString());
                campo.setLength(0);
                hayDatos = true;
            } else if (ch == '\r' || ch == '\n') {
                if (ch == '\r' && i + 1 < n && texto.charAt(i + 1) == '\n') {
                    i++;
                }
                if (hayDatos || campo.length() > 0) {
                    actual.add(campo.toString());
                    salida.add(actual);
                }
                actual = new ArrayList<>();
                campo.setLength(0);
                hayDatos = false;
            } else {
                campo.append(ch);
                hayDatos = true;
            }
        }
        if (hayDatos || campo.length() > 0) {
            actual.add(campo.toString());
            salida.add(actual);
        }
        return salida;
    }
}
