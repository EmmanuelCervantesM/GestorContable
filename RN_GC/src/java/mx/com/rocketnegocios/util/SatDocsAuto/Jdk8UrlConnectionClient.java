package mx.com.rocketnegocios.util.SatDocsAuto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.HttpClientName;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.WebSocket;
import java.nio.charset.StandardCharsets;

/**
 * {@link HttpClient} de Selenium respaldado por un {@link HttpURLConnection} plano,
 * seleccionado con {@code -Dwebdriver.http.factory=jdk8-urlconnection}.
 *
 * <p>Por qué existe: el runtime de GlassFish es Temurin JDK 8u504, cuyo
 * {@code rt.jar} ya no incluye {@code sun.security.ssl.Debug}. El HTTP client
 * por defecto de Selenium (Netty) construye eagerly un SSL context en su static
 * initializer ({@code NettyClient.<clinit>} &rarr; async-http-client &rarr;
 * {@code JdkSslContext} de Netty &rarr; {@code SSLEngineImpl.<clinit>}), lo que
 * lanza {@code NoClassDefFoundError: sun/security/ssl/Debug} y hace
 * que <b>todo</b> {@code new ChromeDriver()} falle en esta JVM — aunque el
 * protocolo de chromedriver en sí es HTTP plano sobre localhost y nunca necesita
 * un {@code SSLEngine}. Este client hace las mismas llamadas del protocolo WebDriver
 * sobre {@code HttpURLConnection} sin tocar la inicialización de Netty/SSL,
 * así las sesiones del driver funcionan en este runtime.
 *
 * <p>Seleccionado por nombre a través del mecanismo de servicios
 * {@code HttpClient.Factory} de Selenium: la {@link Factory} anidada está registrada en
 * {@code META-INF/services/org.openqa.selenium.remote.http.HttpClient$Factory}
 * y se elige cuando {@code webdriver.http.factory=jdk8-urlconnection}.
 * Sin ninguna dependencia nueva de terceros; solo API de Java 8.
 */
public class Jdk8UrlConnectionClient implements HttpClient {

    private final URL baseUrl;
    private final int connectionTimeoutMillis;
    private final int readTimeoutMillis;

    /**
     * Punto de entrada del service-loader elegido por {@code webdriver.http.factory}.
     * La anotación {@code HttpClientName} DEBE estar en esta clase Factory
     * (no en la clase client externa): el
     * {@code HttpClient.Factory.create(name)} de Selenium hace match contra
     * {@code factory.getClass().getAnnotation(HttpClientName.class)}.
     */
    @HttpClientName("jdk8-urlconnection")
    public static class Factory implements HttpClient.Factory {

        @Override
        public HttpClient createClient(ClientConfig config) {
            // El ChromiumDriver de Selenium 4.10 resuelve eagerly el endpoint
            // de CDP/BiDi (una URI ws:// de la capability se:cdp) a través de esta
            // misma factory. Esta app nunca usa CDP/BiDi, así que los
            // endpoints no HTTP(S) reciben un client no-op: CdpEndpointFinder captura la
            // UncheckedIOException resultante y hace fallback a un CDP NoOp.
            String scheme = config.baseUri() == null ? null : config.baseUri().getScheme();
            if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
                return new NoOpClient();
            }
            return new Jdk8UrlConnectionClient(config);
        }

        @Override
        public void cleanupIdleClients() {
            // Stateless: una conexión por request, nada que limpiar.
        }
    }

    /** Client no-op para los endpoints que esta factory no sabe hablar (ws:// de CDP). */
    private static class NoOpClient implements HttpClient {

        @Override
        public HttpResponse execute(HttpRequest req) throws UncheckedIOException {
            throw new UncheckedIOException(
                    new IOException("Non-HTTP(S) endpoint not supported by Jdk8UrlConnectionClient"));
        }

        @Override
        public WebSocket openSocket(HttpRequest request, WebSocket.Listener listener) {
            // ChromiumDriver abre el socket de CDP eagerly al construirse pero
            // nunca envía nada por él salvo que se emitan comandos CDP/BiDi
            // (esta app no emite ninguno). Un socket stub mantiene viva la construcción
            // mientras CDP queda efectivamente desconectado.
            return new NoOpWebSocket();
        }

        @Override
        public void close() {
            // Nada que cerrar.
        }
    }

    /** Stub de WebSocket: acepta los sends en silencio, nunca recibe nada. */
    private static class NoOpWebSocket implements WebSocket {

        @Override
        public WebSocket send(org.openqa.selenium.remote.http.Message message) {
            return this;
        }

        @Override
        public void close() {
            // Nada que cerrar.
        }
    }

    Jdk8UrlConnectionClient(ClientConfig config) {
        this.baseUrl = config.baseUrl();
        this.connectionTimeoutMillis = (int) Math.min(config.connectionTimeout().toMillis(),
                Integer.MAX_VALUE);
        this.readTimeoutMillis = (int) Math.min(config.readTimeout().toMillis(),
                Integer.MAX_VALUE);
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public HttpResponse execute(HttpRequest req) throws UncheckedIOException {
        try {
            URL url = new URL(baseUrl, req.getUri());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(req.getMethod().toString());
            con.setConnectTimeout(connectionTimeoutMillis);
            con.setReadTimeout(readTimeoutMillis);
            con.setInstanceFollowRedirects(false);
            for (String name : req.getHeaderNames()) {
                for (Object value : (Iterable<?>) req.getHeaders(name)) {
                    con.addRequestProperty(name, String.valueOf(value));
                }
            }
            byte[] body = contentBytes(req.getContent(), req.getContentEncoding());
            if (body != null && body.length > 0) {
                con.setDoOutput(true);
                try (OutputStream out = con.getOutputStream()) {
                    out.write(body);
                }
            }
            int status = con.getResponseCode();
            InputStream in = status >= HttpURLConnection.HTTP_BAD_REQUEST
                    ? con.getErrorStream()
                    : con.getInputStream();
            final byte[] responseBytes = readAll(in);
            HttpResponse response = new HttpResponse();
            response.setStatus(status);
            for (Map.Entry<String, List<String>> header : con.getHeaderFields().entrySet()) {
                if (header.getKey() == null) {
                    continue;
                }
                for (String value : header.getValue()) {
                    response.addHeader(header.getKey(), value);
                }
            }
            // NOTA: el supplier DEBE proveer un InputStream (no byte[]):
            // Selenium lee el response content vía Contents.bytes(), que
            // hace cast de getContent().get() a InputStream.
            response.setContent((Supplier) (() -> new ByteArrayInputStream(responseBytes)));
            return response;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public WebSocket openSocket(HttpRequest request, WebSocket.Listener listener) {
        throw new UnsupportedOperationException(
                "BiDi WebSocket is not supported by Jdk8UrlConnectionClient");
    }

    @Override
    public void close() {
        // Stateless: nada que cerrar.
    }

    private static byte[] contentBytes(Supplier<?> supplier, Charset encoding) throws IOException {
        if (supplier == null) {
            return null;
        }
        Object content = supplier.get();
        if (content == null) {
            return null;
        }
        // NOTA: Selenium setea el request content como Supplier<InputStream>
        // (ver helpers Contents.*), no como Supplier<byte[]>.
        if (content instanceof InputStream) {
            return readAll((InputStream) content);
        }
        if (content instanceof byte[]) {
            return (byte[]) content;
        }
        if (content instanceof String) {
            return ((String) content).getBytes(encoding == null ? StandardCharsets.UTF_8 : encoding);
        }
        return String.valueOf(content).getBytes(StandardCharsets.UTF_8);
    }

    private static byte[] readAll(InputStream in) throws IOException {
        if (in == null) {
            return new byte[0];
        }
        try (InputStream autoClose = in;
                ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            byte[] chunk = new byte[8192];
            int read;
            while ((read = autoClose.read(chunk)) != -1) {
                buffer.write(chunk, 0, read);
            }
            return buffer.toByteArray();
        }
    }
}
