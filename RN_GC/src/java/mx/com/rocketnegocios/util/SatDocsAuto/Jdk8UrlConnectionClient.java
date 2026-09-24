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

/**
 * Selenium {@link HttpClient} backed by plain {@link HttpURLConnection},
 * selected with {@code -Dwebdriver.http.factory=jdk8-urlconnection}.
 *
 * <p>Why this exists: the GlassFish runtime is Temurin JDK 8u504, whose
 * {@code rt.jar} no longer ships {@code sun.security.ssl.Debug}. Selenium's
 * default Netty HTTP client eagerly builds an SSL context in its static
 * initializer ({@code NettyClient.<clinit>} &rarr; async-http-client &rarr;
 * Netty {@code JdkSslContext} &rarr; {@code SSLEngineImpl.<clinit>}), which
 * throws {@code NoClassDefFoundError: sun/security/ssl/Debug} and makes
 * <b>every</b> {@code new ChromeDriver()} fail on this JVM — even though the
 * chromedriver protocol itself is plain HTTP on localhost and never needs
 * an {@code SSLEngine}. This client performs the same WebDriver wire
 * calls over {@code HttpURLConnection} without touching Netty/SSL
 * initialization, so driver sessions work on this runtime.
 *
 * <p>Selected by name through Selenium's {@code HttpClient.Factory}
 * service mechanism: the nested {@link Factory} is registered in
 * {@code META-INF/services/org.openqa.selenium.remote.http.HttpClient$Factory}
 * and picked when {@code webdriver.http.factory=jdk8-urlconnection}.
 * No new third-party dependency; Java 8 API only.
 */
public class Jdk8UrlConnectionClient implements HttpClient {

    private final URL baseUrl;
    private final int connectionTimeoutMillis;
    private final int readTimeoutMillis;

    /**
     * Service-loader entry point picked by {@code webdriver.http.factory}.
     * The {@code HttpClientName} annotation MUST be on this Factory class
     * (not on the outer client class): Selenium's
     * {@code HttpClient.Factory.create(name)} matches
     * {@code factory.getClass().getAnnotation(HttpClientName.class)}.
     */
    @HttpClientName("jdk8-urlconnection")
    public static class Factory implements HttpClient.Factory {

        @Override
        public HttpClient createClient(ClientConfig config) {
            // Selenium 4.10's ChromiumDriver eagerly resolves the CDP/BiDi
            // endpoint (a ws:// URI from the se:cdp capability) through this
            // same factory. This app never uses CDP/BiDi, so non-HTTP(S)
            // endpoints get a no-op client: CdpEndpointFinder catches the
            // resulting UncheckedIOException and falls back to NoOp CDP.
            String scheme = config.baseUri() == null ? null : config.baseUri().getScheme();
            if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
                return new NoOpClient();
            }
            return new Jdk8UrlConnectionClient(config);
        }

        @Override
        public void cleanupIdleClients() {
            // Stateless: one connection per request, nothing to clean up.
        }
    }

    /** No-op client for endpoints this factory cannot speak (ws:// CDP). */
    private static class NoOpClient implements HttpClient {

        @Override
        public HttpResponse execute(HttpRequest req) throws UncheckedIOException {
            throw new UncheckedIOException(
                    new IOException("Non-HTTP(S) endpoint not supported by Jdk8UrlConnectionClient"));
        }

        @Override
        public WebSocket openSocket(HttpRequest request, WebSocket.Listener listener) {
            // ChromiumDriver opens the CDP socket eagerly at construction but
            // never sends through it unless CDP/BiDi commands are issued
            // (this app issues none). A stub socket keeps construction alive
            // while CDP stays effectively disconnected.
            return new NoOpWebSocket();
        }

        @Override
        public void close() {
            // Nothing to close.
        }
    }

    /** WebSocket stub: accepts sends silently, never receives anything. */
    private static class NoOpWebSocket implements WebSocket {

        @Override
        public WebSocket send(org.openqa.selenium.remote.http.Message message) {
            return this;
        }

        @Override
        public void close() {
            // Nothing to close.
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
            // NOTE: the supplier MUST provide an InputStream (not byte[]):
            // Selenium reads response content via Contents.bytes(), which
            // casts getContent().get() to InputStream.
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
        // Stateless: nothing to close.
    }

    private static byte[] contentBytes(Supplier<?> supplier, Charset encoding) throws IOException {
        if (supplier == null) {
            return null;
        }
        Object content = supplier.get();
        if (content == null) {
            return null;
        }
        // NOTE: Selenium sets request content as Supplier<InputStream>
        // (see Contents.* helpers), not Supplier<byte[]>.
        if (content instanceof InputStream) {
            return readAll((InputStream) content);
        }
        if (content instanceof byte[]) {
            return (byte[]) content;
        }
        if (content instanceof String) {
            return ((String) content).getBytes(encoding == null ? Charset.forName("UTF-8") : encoding);
        }
        return String.valueOf(content).getBytes(Charset.forName("UTF-8"));
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
