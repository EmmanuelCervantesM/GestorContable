package mx.com.rocketnegocios.util.SatDocsAuto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Lógica común de los page objects del SAT con e.firma (Constancia y Opinión).
 * Las subclases conservan solo su URL, sus locators propios y su rama de descarga.
 */
public abstract class SatDocsBase {

    /** Directorio de descarga por defecto. */
    public static final String DEFAULT_DOWNLOAD_DIR = "docs/downloads/";

    /** Binario Brave por defecto; si no existe se usa el Chrome por defecto sin fallar. */
    public static final String DEFAULT_BRAVE_BINARY = "/opt/brave.com/brave/brave-browser";

    /** Espera explícita por defecto para interacciones DOM, en segundos. */
    public static final long DEFAULT_TIMEOUT_SECONDS = 30L;

    // Espera corta para pasos opcionales (pestaña e.firma): 10 s y no 5, porque con
    // arranque headless en frío la pestaña tarda más de 5 s y el timeout silencioso
    // quema luego los 30 s completos en el input del certificado (FAIL ~41 s).
    protected static final long SHORT_TIMEOUT_SECONDS = 10L;

    /** Prefijo del payload PDF en línea dentro del iframe. */
    protected static final String PDF_BASE64_PREFIX = "data:application/pdf;base64,";

    // Pestaña e.firma del login unificado (arranca en CIEC): variante más tolerante.
    public static final By EFIRMA_TAB = By.cssSelector(
            "#buttonFiel, button#buttonFiel, [id*='buttonFiel' i], [id*='fiel' i][role='tab']");

    /** Input de archivo del certificado (*.cer), oculto tras el botón "Buscar". */
    public static final By CER_INPUT = By.cssSelector(
            "#fileCertificate,"
                    + " input[type='file'][accept*='cer' i],"
                    + " input[type='file'][name*='cer' i],"
                    + " input[type='file'][id*='cer' i]");

    /** Input de archivo de la llave privada (*.key), oculto igual que el *.cer. */
    public static final By KEY_INPUT = By.cssSelector(
            "#filePrivateKey,"
                    + " input[type='file'][accept*='key' i],"
                    + " input[type='file'][name*='key' i],"
                    + " input[type='file'][id*='key' i],"
                    + " input[type='file'][name*='llave' i]");

    /** Input de la contraseña de la llave privada. */
    public static final By PRIVATE_PASSWORD_INPUT = By.cssSelector(
            "#privateKeyPassword,"
                    + " input[type='password'][name*='contras' i],"
                    + " input[type='password'][name*='password' i],"
                    + " input[type='password'][name*='clave' i],"
                    + " input[type='password'][id*='password' i],"
                    + " input[type='password']");

    /** Botón de envío del formulario e.firma (input#submit, "Enviar"). */
    public static final By SUBMIT_BUTTON = By.cssSelector(
            "#submit,"
                    + " input[type='button'][value*='Enviar' i],"
                    + " input[onclick*='firmar' i],"
                    + " input[type='submit']");

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final WebDriverWait shortWait;
    protected final Path downloadDir;

    /**
     * @param driver         WebDriver activo (de {@code buildChromeDriverConDescargas})
     * @param downloadDir    directorio de descarga; se resuelve a absoluta y se crea
     * @param timeoutSeconds espera explícita para interacciones DOM, en segundos
     */
    protected SatDocsBase(WebDriver driver, String downloadDir, long timeoutSeconds) {
        if (driver == null) {
            throw new IllegalArgumentException("driver must not be null");
        }
        this.driver = driver;
        // Selenium 4: WebDriverWait recibe java.time.Duration.
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        // Instancia compartida: cada until() reevalúa de cero, sin cambio de conducta.
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(SHORT_TIMEOUT_SECONDS));
        this.downloadDir = resolveOrCreateDir(
                downloadDir == null || downloadDir.trim().isEmpty() ? DEFAULT_DOWNLOAD_DIR : downloadDir);
    }

    /**
     * Credenciales e.firma de vida corta en disco: Selenium solo acepta rutas reales,
     * así que los bytes de la BD se escriben a un temp dir y se borran al cerrar.
     */
    public static final class TempCredentials implements AutoCloseable {
        private final Path dir;
        private final Path cerFile;
        private final Path keyFile;

        private TempCredentials(Path dir, Path cerFile, Path keyFile) {
            this.dir = dir;
            this.cerFile = cerFile;
            this.keyFile = keyFile;
        }

        /** Valida los bytes, crea el temp dir y escribe firma.cer/firma.key. */
        public static TempCredentials write(byte[] cerBytes, byte[] keyBytes) throws IOException {
            if (cerBytes == null || cerBytes.length == 0) {
                throw new IllegalArgumentException("Certificate (.cer) bytes must not be empty");
            }
            if (keyBytes == null || keyBytes.length == 0) {
                throw new IllegalArgumentException("Private key (.key) bytes must not be empty");
            }
            Path dir = Files.createTempDirectory("fiel-");
            Path cerFile = dir.resolve("firma.cer");
            Path keyFile = dir.resolve("firma.key");
            try {
                Files.write(cerFile, cerBytes);
                Files.write(keyFile, keyBytes);
            } catch (IOException | RuntimeException e) {
                deleteQuietly(cerFile);
                deleteQuietly(keyFile);
                deleteQuietly(dir);
                throw e;
            }
            return new TempCredentials(dir, cerFile, keyFile);
        }

        /** Ruta absoluta del *.cer temporal. */
        public String cerPath() {
            return cerFile.toAbsolutePath().toString();
        }

        /** Ruta absoluta del *.key temporal. */
        public String keyPath() {
            return keyFile.toAbsolutePath().toString();
        }

        /** Borra ambos archivos y el dir; best-effort, nunca lanza. */
        @Override
        public void close() {
            deleteQuietly(cerFile);
            deleteQuietly(keyFile);
            deleteQuietly(dir);
        }
    }

    /** Borrado best-effort; el temp dir del SO se reclama aunque falle. */
    private static void deleteQuietly(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException ignored) {
            // Limpieza best-effort.
        }
    }

    /**
     * Selecciona la pestaña e.firma (el login arranca en CIEC). Tolera que el
     * formulario ya esté visible. El retorno se ignora en todos los callers.
     */
    protected void seleccionarEFirma(By tabLocator) {
        try {
            WebElement tab = shortWait.until(ExpectedConditions.elementToBeClickable(tabLocator));
            tab.click();
        } catch (TimeoutException alreadyOnEFirma) {
            // Formulario ya visible o pestaña ausente; loginConEFirma muestra fallas reales.
        }
    }

    /**
     * El SAT oculta los inputs de archivo tras botones "Buscar" cuyo diálogo nativo
     * Selenium no puede manejar; mostrarlos por script y escribir la ruta es el
     * enfoque headless soportado.
     */
    protected void unhideFileInput(WebElement input) {
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].style.display='block';"
                            + "arguments[0].style.visibility='visible';"
                            + "arguments[0].style.opacity='1';"
                            + "arguments[0].removeAttribute('hidden');",
                    input);
        }
    }

    /** Decodifica un payload base64 a downloadDir/filePrefix-yyyyMMdd-HHmmss.pdf. */
    protected File saveBase64Pdf(String base64Payload, String filePrefix) {
        byte[] bytes = Base64.getDecoder().decode(base64Payload.trim());
        String stamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        Path target = downloadDir.resolve(filePrefix + stamp + ".pdf");
        try (FileOutputStream out = new FileOutputStream(target.toFile())) {
            out.write(bytes);
        } catch (IOException e) {
            throw new IllegalStateException("Could not save inline PDF to " + target, e);
        }
        return target.toFile();
    }

    /**
     * ChromeDriver cuyas descargas van directo al dir dado, sin diálogo, y cuyos PDF
     * se descargan como archivos en vez de abrirse en el visor embebido.
     *
     * @param downloadDirAbsoluteOrRelative se resuelve a absoluta y se crea
     */
    public static ChromeDriver buildChromeDriverConDescargas(String downloadDirAbsoluteOrRelative) {
        return buildChromeDriverConDescargas(downloadDirAbsoluteOrRelative, DEFAULT_BRAVE_BINARY);
    }

    /**
     * Igual que la anterior con override del binario: ruta nula/vacía o inexistente
     * usa el Chrome por defecto sin fallar. Headless activado por defecto.
     */
    public static ChromeDriver buildChromeDriverConDescargas(String downloadDirAbsoluteOrRelative,
            String browserBinaryPath) {
        return buildChromeDriverConDescargas(downloadDirAbsoluteOrRelative, browserBinaryPath, true);
    }

    /**
     * Igual que las anteriores con control explícito de headless ({@code false} para
     * depurar con ventana visible; {@code true} agrega {@code --headless=new}).
     */
    public static ChromeDriver buildChromeDriverConDescargas(String downloadDirAbsoluteOrRelative,
            String browserBinaryPath,
            boolean headless) {
        Path dir = resolveOrCreateDir(downloadDirAbsoluteOrRelative == null
                || downloadDirAbsoluteOrRelative.trim().isEmpty()
                        ? DEFAULT_DOWNLOAD_DIR
                        : downloadDirAbsoluteOrRelative);
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("download.default_directory", dir.toString());
        prefs.put("download.prompt_for_download", Boolean.FALSE);
        prefs.put("download.directory_upgrade", Boolean.TRUE);
        prefs.put("plugins.always_open_pdf_externally", Boolean.TRUE);
        prefs.put("safebrowsing.enabled", Boolean.TRUE);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        // Sin PageLoadStrategy.EAGER a propósito: con EAGER el get() vuelve en
        // DOMContentLoaded a mitad de redirects, antes de que arranque el SPA Angular,
        // y la espera de la pestaña e.firma expira en silencio (mismo FAIL ~41 s).
        if (headless) {
            options.addArguments("--headless=new", "--disable-gpu", "--window-size=1920,1080");
        }
        // Solo flags seguros de arranque/render: --disable-dev-shm-usage evita presión
        // en /dev/shm de contenedores/VMs; el resto salta tareas de primer arranque.
        // Sin --no-sandbox (debilita el sandbox) ni imágenes desactivadas (el SPA del
        // login necesita sus sub-recursos de imagen para mostrar el formulario e.firma).
        options.addArguments("--disable-dev-shm-usage",
                "--disable-extensions",
                "--no-first-run",
                "--no-default-browser-check");
        if (browserBinaryPath != null && !browserBinaryPath.trim().isEmpty()
                && new File(browserBinaryPath.trim()).isFile()) {
            options.setBinary(browserBinaryPath.trim());
        }
        return new ChromeDriver(options);
    }

    protected static Path toAbsolutePath(String dir) {
        return Paths.get(dir).toAbsolutePath().normalize();
    }

    protected static Path resolveOrCreateDir(String dir) {
        Path path = toAbsolutePath(dir);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot create directory: " + path, e);
        }
        return path;
    }

    /**
     * Valida un archivo de credencial: debe existir (en producción, temp de
     * {@code loginConEFirma(byte[], byte[], String)} desde los BLOBs) y coincidir
     * la extensión.
     */
    protected static File requireCredentialFile(String path, String expectedExtension) {
        if (path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException("Credential file path must not be empty");
        }
        File candidate = new File(path.trim());
        if (!candidate.isFile()) {
            throw new IllegalStateException("Credential file not found: '" + path + "'");
        }
        if (!candidate.getName().toLowerCase().endsWith(expectedExtension)) {
            throw new IllegalStateException("Credential file '" + candidate.getAbsolutePath()
                    + "' does not end with expected extension " + expectedExtension);
        }
        return candidate;
    }

    /** Directorio de descarga absoluto (creado al construir). */
    public Path getDownloadDir() {
        return downloadDir;
    }
}
