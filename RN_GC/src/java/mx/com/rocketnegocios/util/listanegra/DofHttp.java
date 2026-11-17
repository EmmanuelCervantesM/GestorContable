package mx.com.rocketnegocios.util.listanegra;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;

/** CTR-12: GET simple de una página de dof.gob.mx. */
public final class DofHttp {

    private static final int TIEMPO_ESPERA_MS = 15000;

    private DofHttp() {
    }

    public static String get(String url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setConnectTimeout(TIEMPO_ESPERA_MS);
        con.setReadTimeout(TIEMPO_ESPERA_MS);
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; Contarec)");
        con.setRequestProperty("Accept", "text/html");
        try {
            int codigo = con.getResponseCode();
            if (codigo != HttpURLConnection.HTTP_OK) {
                throw new IOException("El DOF respondió " + codigo + " para " + url);
            }
            try (InputStream in = con.getInputStream()) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[8192];
                int n;
                while ((n = in.read(buf)) != -1) {
                    out.write(buf, 0, n);
                }
                return decodificar(out.toByteArray());
            }
        } finally {
            con.disconnect();
        }
    }

    /** El DOF declara ISO-8859-1 y UTF-8 a la vez; se usa UTF-8 solo si los bytes son UTF-8 válido. */
    static String decodificar(byte[] datos) {
        try {
            return StandardCharsets.UTF_8.newDecoder()
                    .onMalformedInput(CodingErrorAction.REPORT)
                    .onUnmappableCharacter(CodingErrorAction.REPORT)
                    .decode(ByteBuffer.wrap(datos)).toString();
        } catch (CharacterCodingException ex) {
            return new String(datos, StandardCharsets.ISO_8859_1);
        }
    }
}
