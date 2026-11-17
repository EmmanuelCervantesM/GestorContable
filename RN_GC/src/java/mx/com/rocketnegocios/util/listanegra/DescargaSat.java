package mx.com.rocketnegocios.util.listanegra;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * CTR-12: consulta de los CSV de datos abiertos del SAT. El SAT los publica en ligas fijas
 * de Azure Blob Storage, que responden ETag y Last-Modified; con un HEAD se sabe si el
 * archivo cambió sin bajarlo.
 */
public final class DescargaSat {

    private static final int TIEMPO_CONEXION_MS = 15000;
    private static final int TIEMPO_LECTURA_MS = 120000;

    private DescargaSat() {
    }

    /** Lo que responde el HEAD de un CSV. */
    public static class Cabecera {

        private final String etag;
        private final Date ultimaModificacion;

        Cabecera(String etag, Date ultimaModificacion) {
            this.etag = etag;
            this.ultimaModificacion = ultimaModificacion;
        }

        public String getEtag() {
            return etag;
        }

        public Date getUltimaModificacion() {
            return ultimaModificacion;
        }
    }

    public static Cabecera cabecera(String url) throws IOException {
        HttpURLConnection con = abrir(url, "HEAD");
        try {
            revisarCodigo(con, url);
            long modificado = con.getLastModified();
            return new Cabecera(con.getHeaderField("ETag"), modificado > 0 ? new Date(modificado) : null);
        } finally {
            con.disconnect();
        }
    }

    public static byte[] bajar(String url) throws IOException {
        HttpURLConnection con = abrir(url, "GET");
        try {
            revisarCodigo(con, url);
            try (InputStream in = con.getInputStream()) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[8192];
                int n;
                while ((n = in.read(buf)) != -1) {
                    out.write(buf, 0, n);
                }
                return out.toByteArray();
            }
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection abrir(String url, String metodo) throws IOException {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod(metodo);
        con.setConnectTimeout(TIEMPO_CONEXION_MS);
        con.setReadTimeout(TIEMPO_LECTURA_MS);
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; Contarec)");
        return con;
    }

    private static void revisarCodigo(HttpURLConnection con, String url) throws IOException {
        int codigo = con.getResponseCode();
        if (codigo != HttpURLConnection.HTTP_OK) {
            throw new IOException("El SAT respondió " + codigo + " para " + url);
        }
    }
}
