package mx.com.rocketnegocios.util.SatDocsAuto;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page object de la Constancia de Situación Fiscal con e.firma (sin captcha).
 * Entrada: hoja ejecutora con "Ejecutar en línea"; login en iframe; descarga por
 * rama SessionBroker interna (primaria) con fallbacks heredados en línea.
 */
public class MapObjectConstanciaHeadless extends SatDocsBase {

    private static final Logger LOG = Logger.getLogger(MapObjectConstanciaHeadless.class.getName());

    /** URL aplicacion heredada; responde 302 a la página informativa, solo referencia. */
    public static final String URL_FUNCIONAL = "https://www.sat.gob.mx/aplicacion/53027/genera-tu-constancia-de-situacion-fiscal";

    /** Hoja ejecutora con el enlace real "Ejecutar en línea"; punto de entrada. */
    public static final String URL_EJECUTOR = "https://wwwmat.sat.gob.mx/aplicacion/53027/genera-tu-constancia-de-situacion-fiscal";

    /** Timeout de espera del PDF en disco, en segundos. */
    public static final long DEFAULT_DOWNLOAD_TIMEOUT_SECONDS = 120L;

    /**
     * Tope para que aparezca el formulario e.firma. El portal tarda ~40 s en frío y
     * puede recargar la página completa (destruye el iframe) mientras carga.
     */
    private static final long LOGIN_FORM_TIMEOUT_SECONDS = 120L;

    /** Tope para que la app post-login (SessionBroker) muestre "Generar Constancia". */
    private static final long POST_LOGIN_TIMEOUT_SECONDS = 90L;

    /** Pausa mínima entre clics a la pestaña e.firma mientras el formulario carga. */
    private static final long EFIRMA_CLICK_INTERVAL_MILLIS = 5000L;

    /** Intervalo de sondeo del sistema de archivos, en milisegundos. */
    private static final long DOWNLOAD_POLL_MILLIS = 1000L;

    // Locators propios del flujo (verificados 2026-09-17; si falla un paso,
    // re-verificar con el inspector: todos los selectores viven en este bloque).

    /** Enlace "Ejecutar en línea" de la hoja ejecutora. */
    public static final By EJECUTAR_EN_LINEA_LINK = By.cssSelector(
            "a.actionButton,"
                    + " a[href*='aplicacion/login' i]");

    /** Iframe del login (lanzador.jsf) en la página aplicacion/login. */
    public static final By LOGIN_IFRAME = By.cssSelector(
            "iframe#iframetoload,"
                    + " iframe[src*='lanzador.jsf' i]");

    /** PDF en línea del SPA tras el login; ruta primaria heredada de Opinión. */
    public static final By PDF_IFRAME = By.cssSelector(
            "iframe[type='application/pdf'],"
                    + " iframe[src^='data:application/pdf']");

    /** Iframe SessionBroker con la app post-login (ConsultaTramite.jsf). */
    public static final By SESSION_BROKER_IFRAME = By.cssSelector(
            "iframe#iframetoload,"
                    + " iframe[src*='SessionBroker' i],"
                    + " iframe[src*='ConsultaTramite.jsf' i]");

    /** Botón "Generar Constancia" DENTRO del iframe SessionBroker (XPath: CSS no matchea texto). */
    public static final By INNER_GENERATE_BUTTON = By.xpath(
            "//*[self::button or self::a or self::input]["
                    + textContains("generar") + " and " + textContains("constancia") + "]");

    /** Botón "Generar Constancia" de nivel superior; fallback heredado solamente. */
    public static final By GENERATE_BUTTON = By.xpath(
            "//*[self::button or self::a or self::input]["
                    + textContains("generar") + " and " + textContains("constancia") + "]"
                    + " | //*[self::button or self::a or self::input][" + textContains("generar") + "]");

    /** Enlace/botón "Descargar"; fallback heredado solamente. */
    public static final By DOWNLOAD_PDF_BUTTON = By.xpath(
            "//a[" + textContains("descargar") + "]"
                    + " | //button[" + textContains("descargar") + "]"
                    + " | //a[contains(@href, '.pdf') or contains(@href, '.PDF')]");

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ\u00C1\u00C9\u00CD\u00D3\u00DA\u00DC\u00D1";
    private static final String LOWER_ALPHABET = "abcdefghijklmnopqrstuvwxyz\u00E1\u00E9\u00ED\u00F3\u00FA\u00FC\u00F1";

    // XPath 1.0 no tiene lower-case(): predicado insensible a mayúsculas con translate().
    private static String textContains(String lowercaseWord) {
        return "contains(translate(normalize-space(.), '" + UPPER + "', '" + LOWER_ALPHABET + "'), '"
                + lowercaseWord + "')";
    }

    private final long downloadTimeoutSeconds;

    public MapObjectConstanciaHeadless(WebDriver driver, String downloadDir) {
        this(driver, downloadDir,
                DEFAULT_TIMEOUT_SECONDS, DEFAULT_DOWNLOAD_TIMEOUT_SECONDS);
    }

    /**
     * @param driver                 WebDriver activo
     * @param downloadDir            directorio de descarga; se resuelve a absoluta y se crea
     * @param timeoutSeconds         espera explícita para interacciones DOM
     * @param downloadTimeoutSeconds espera del PDF en disco
     */
    public MapObjectConstanciaHeadless(WebDriver driver, String downloadDir,
            long timeoutSeconds, long downloadTimeoutSeconds) {
        super(driver, downloadDir, timeoutSeconds);
        this.downloadTimeoutSeconds = downloadTimeoutSeconds;
    }

    /** Abre la hoja ejecutora; encadenar con {@link #irAlEjecutor()}. */
    public MapObjectConstanciaHeadless open() {
        driver.get(URL_EJECUTOR);
        return this;
    }

    /**
     * Va a la hoja ejecutora, clic en "Ejecutar en línea" y espera a que la app de
     * login tome el control. Encadenar antes de {@code loginConEFirma(...)}.
     */
    public MapObjectConstanciaHeadless irAlEjecutor() {
        driver.get(URL_EJECUTOR);
        WebElement ejecutar = wait.until(ExpectedConditions.elementToBeClickable(EJECUTAR_EN_LINEA_LINK));
        ejecutar.click();
        wait.until(ExpectedConditions.urlContains("aplicacion/login"));
        return this;
    }

    /** Selecciona la pestaña e.firma; tolera que ya esté visible. */
    public MapObjectConstanciaHeadless seleccionarEFirma() {
        super.seleccionarEFirma(EFIRMA_TAB);
        return this;
    }

    /**
     * Login e.firma: entra al iframe, selecciona e.firma, sube *.cer/*.key, escribe
     * la contraseña y envía. Ambas rutas deben existir (en producción son temps de
     * {@code loginConEFirma(byte[], byte[], String)} desde los BLOBs).
     *
     * @param cerPath  ruta a un *.cer existente
     * @param keyPath  ruta a un *.key existente
     * @param password contraseña de la llave (nunca se registra)
     */
    public MapObjectConstanciaHeadless loginConEFirma(String cerPath, String keyPath, String password) {
        File cerFile = requireCredentialFile(cerPath, ".cer");
        File keyFile = requireCredentialFile(keyPath, ".key");
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("e.firma private-key password must not be empty");
        }

        WebElement cerInput = esperarInputCertificado();
        unhideFileInput(cerInput);
        cerInput.sendKeys(cerFile.getAbsolutePath());

        WebElement keyInput = wait.until(ExpectedConditions.presenceOfElementLocated(KEY_INPUT));
        unhideFileInput(keyInput);
        keyInput.sendKeys(keyFile.getAbsolutePath());

        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(PRIVATE_PASSWORD_INPUT));
        passwordInput.clear();
        passwordInput.sendKeys(password);

        WebElement submit = wait.until(ExpectedConditions.elementToBeClickable(SUBMIT_BUTTON));
        submit.click();
        return this;
    }

    /**
     * Espera el input del *.cer re-entrando al iframe de login en cada sondeo: si el
     * portal recarga la página, el frame anterior muere y una sola entrada dejaría a
     * Selenium buscando en un documento destruido. Clic a la pestaña e.firma
     * (espaciado) hasta que el formulario aparezca.
     *
     * @return el input del *.cer, con el contexto de frames ya en su documento
     */
    private WebElement esperarInputCertificado() {
        long limite = System.currentTimeMillis() + LOGIN_FORM_TIMEOUT_SECONDS * 1000L;
        long ultimoClic = 0L;
        while (true) {
            try {
                driver.switchTo().defaultContent();
                List<WebElement> frames = driver.findElements(LOGIN_IFRAME);
                if (!frames.isEmpty()) {
                    driver.switchTo().frame(frames.get(0));
                }
                List<WebElement> cer = driver.findElements(CER_INPUT);
                if (!cer.isEmpty()) {
                    return cer.get(0);
                }
                List<WebElement> tabs = driver.findElements(EFIRMA_TAB);
                long ahora = System.currentTimeMillis();
                if (!tabs.isEmpty() && tabs.get(0).isDisplayed() && ahora - ultimoClic >= EFIRMA_CLICK_INTERVAL_MILLIS) {
                    tabs.get(0).click();
                    ultimoClic = ahora;
                }
            } catch (WebDriverException recargando) {
                // Frame o elemento destruido por una recarga en curso; se reintenta.
            }
            if (System.currentTimeMillis() > limite) {
                driver.switchTo().defaultContent();
                throw new TimeoutException("El formulario e.firma del SAT no aparecio en "
                        + LOGIN_FORM_TIMEOUT_SECONDS + " s (url: " + driver.getCurrentUrl() + ")");
            }
            try {
                Thread.sleep(DOWNLOAD_POLL_MILLIS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Interrupted while waiting for the e.firma login form", e);
            }
        }
    }

    /**
     * Login e.firma desde la BD (BLOBs de RnGcCertificadosTbl). Escribe los bytes a
     * un temp dir solo durante la subida y los borra al volver, con éxito o falla.
     *
     * @param cerBytes raw *.cer (certificadoSelloDigital)
     * @param keyBytes raw *.key (llavePrivada)
     * @param password contraseña de la llave (contraseniaLlavePrivada, nunca se registra)
     */
    public MapObjectConstanciaHeadless loginConEFirma(byte[] cerBytes, byte[] keyBytes, String password) {
        try (TempCredentials creds = TempCredentials.write(cerBytes, keyBytes)) {
            return loginConEFirma(creds.cerPath(), creds.keyPath(), password);
        } catch (IOException e) {
            throw new IllegalStateException("Could not write temp e.firma credential files", e);
        }
    }

    /**
     * Guarda la constancia tras el login. Orden deliberado: rama interna
     * SessionBroker (primaria) y luego fallbacks de nivel superior (iframe base64
     * o botón Descargar) solo si no hay frame interno.
     *
     * @return el PDF de constancia guardado
     */
    public File generarYDescargar() {
        // El login ocurre en iframe; el contenido post-login puede ir en top,
        // así que siempre se reinicia el contexto de frames primero.
        String avisoSat = volverAlDocumentoPrincipal();
        try {
            return generarYDescargarSinAviso();
        } catch (RuntimeException e) {
            if (avisoSat == null) {
                throw e;
            }
            throw new IllegalStateException("El SAT mostro un aviso tras el login: " + avisoSat, e);
        }
    }

    /**
     * Reinicia el contexto de frames tolerando el alert que el SAT puede abrir tras
     * el login. Con unhandledPromptBehavior "dismiss and notify" Chrome ya lo cerró
     * al lanzar la excepción; como en el flujo manual ("Aceptar"), se continúa.
     *
     * @return el texto del aviso, o null si no hubo
     */
    private String volverAlDocumentoPrincipal() {
        try {
            driver.switchTo().defaultContent();
            return null;
        } catch (UnhandledAlertException aviso) {
            String texto = aviso.getAlertText();
            LOG.log(Level.WARNING, "Aviso del SAT tras el login de constancia (se continua): {0}", texto);
            driver.switchTo().defaultContent();
            return texto == null ? "(sin texto)" : texto;
        }
    }

    private File generarYDescargarSinAviso() {
        int pdfCountBefore = countPdfs(downloadDir);
        try {
            // Espera corta a propósito: varios flujos SAT omiten este paso tras el login.
            WebElement generate = shortWait.until(ExpectedConditions.elementToBeClickable(GENERATE_BUTTON));
            generate.click();
        } catch (TimeoutException skipped) {
            // Sin paso explícito de generación; se sigue abajo.
        }
        File innerPdf = tryGenerarEnSessionBroker(pdfCountBefore);
        if (innerPdf != null) {
            return innerPdf;
        }
        // Rama heredada de nivel superior: una sola espera a la forma post-login que
        // renderice este flujo; prefiere el iframe en línea (patrón Opinión).
        wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(PDF_IFRAME),
                ExpectedConditions.elementToBeClickable(DOWNLOAD_PDF_BUTTON)));
        if (!driver.findElements(PDF_IFRAME).isEmpty()) {
            WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(PDF_IFRAME));
            String src = iframe.getAttribute("src");
            if (src != null && src.startsWith(PDF_BASE64_PREFIX)) {
                return saveBase64Pdf(src.substring(PDF_BASE64_PREFIX.length()), "constanciaSituacionFiscal-");
            }
            throw new IllegalStateException("Inline constancia PDF iframe did not carry a base64 payload");
        }
        WebElement download = wait.until(ExpectedConditions.elementToBeClickable(DOWNLOAD_PDF_BUTTON));
        download.click();
        return waitForNewPdf(downloadDir, pdfCountBefore, downloadTimeoutSeconds);
    }

    /**
     * Rama interna de {@link #generarYDescargar()}: entra al frame SessionBroker,
     * clic en "Generar Constancia" y espera el popup y/o la descarga; luego cierra
     * el popup y devuelve el PDF.
     *
     * @return el PDF descargado, o null si no hay frame/botón interno (el caller
     *         usa la rama heredada)
     */
    private File tryGenerarEnSessionBroker(int pdfCountBefore) {
        WebElement generar = buscarEnFrame(SESSION_BROKER_IFRAME, INNER_GENERATE_BUTTON, POST_LOGIN_TIMEOUT_SECONDS);
        if (generar == null) {
            LOG.log(Level.WARNING, "Constancia: sin boton Generar en SessionBroker tras {0} s; {1}",
                    new Object[]{POST_LOGIN_TIMEOUT_SECONDS, describirPagina()});
            driver.switchTo().defaultContent();
            return null;
        }
        String mainHandle = driver.getWindowHandle();
        try {
            int handlesBefore = driver.getWindowHandles().size();
            generar.click();
            waitForPopupOrDownload(handlesBefore, pdfCountBefore, downloadTimeoutSeconds);
            // El popup DEBE seguir abierto hasta que el PDF aterriza: su pestaña lleva
            // la navegación IdcGeneraConstancia.jsf en vuelo y cerrarla antes aborta la
            // descarga. La limpieza ocurre en el finally, con el archivo ya en disco.
            return waitForNewPdf(downloadDir, pdfCountBefore, downloadTimeoutSeconds);
        } finally {
            closeNewWindows(mainHandle);
            driver.switchTo().defaultContent();
        }
    }

    /**
     * Busca un elemento visible dentro del frame, re-entrando en cada sondeo para
     * sobrevivir recargas del portal (igual que {@link #esperarInputCertificado()}).
     *
     * @return el elemento con el contexto ya en su frame, o null al agotar el tiempo
     */
    private WebElement buscarEnFrame(By frameLocator, By objetivo, long timeoutSeconds) {
        long limite = System.currentTimeMillis() + timeoutSeconds * 1000L;
        while (true) {
            try {
                driver.switchTo().defaultContent();
                List<WebElement> frames = driver.findElements(frameLocator);
                if (!frames.isEmpty()) {
                    driver.switchTo().frame(frames.get(0));
                    for (WebElement candidato : driver.findElements(objetivo)) {
                        if (candidato.isDisplayed() && candidato.isEnabled()) {
                            return candidato;
                        }
                    }
                }
            } catch (WebDriverException recargando) {
                // Frame o elemento destruido por una recarga en curso; se reintenta.
            }
            if (System.currentTimeMillis() > limite) {
                return null;
            }
            try {
                Thread.sleep(DOWNLOAD_POLL_MILLIS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Interrupted while waiting for the constancia page", e);
            }
        }
    }

    /** Resumen para el log: URL principal, frames y textos de botones/enlaces del frame. */
    private String describirPagina() {
        StringBuilder sb = new StringBuilder();
        try {
            driver.switchTo().defaultContent();
            sb.append("url=").append(driver.getCurrentUrl());
            List<WebElement> frames = driver.findElements(SESSION_BROKER_IFRAME);
            for (WebElement frame : driver.findElements(By.tagName("iframe"))) {
                sb.append(" | iframe=").append(frame.getAttribute("src"));
            }
            if (!frames.isEmpty()) {
                driver.switchTo().frame(frames.get(0));
                sb.append(" | frameUrl=").append(driver.getCurrentUrl()).append(" | acciones=[");
                int n = 0;
                for (WebElement e : driver.findElements(By.cssSelector("button, a, input[type='button'], input[type='submit']"))) {
                    String texto = e.getText().trim().isEmpty() ? e.getAttribute("value") : e.getText().trim();
                    if (texto != null && !texto.trim().isEmpty() && n++ < 25) {
                        sb.append(texto.length() > 40 ? texto.substring(0, 40) : texto).append("; ");
                    }
                }
                sb.append(']');
            }
        } catch (WebDriverException e) {
            sb.append(" | (diagnostico incompleto: ").append(e.getClass().getSimpleName()).append(')');
        } finally {
            try {
                driver.switchTo().defaultContent();
            } catch (WebDriverException ignorada) {
                // Solo diagnóstico.
            }
        }
        return sb.toString();
    }

    /**
     * Sondea hasta que el clic produce ventana nueva (popup IdcGeneraConstancia.jsf)
     * o descarga completa. Headless con always_open_pdf_externally descarga la
     * navegación del popup directo a disco dejándolo en about:blank: ambas valen.
     */
    private void waitForPopupOrDownload(int handlesBefore, int pdfsBefore, long timeoutSeconds) {
        long deadline = System.currentTimeMillis() + timeoutSeconds * 1000L;
        while (true) {
            if (driver.getWindowHandles().size() > handlesBefore) {
                return;
            }
            if (!hasPartialDownload(downloadDir) && countPdfs(downloadDir) > pdfsBefore) {
                return;
            }
            if (System.currentTimeMillis() > deadline) {
                throw new IllegalStateException(
                        "Timed out after " + timeoutSeconds
                                + "s waiting for the constancia popup/download in " + downloadDir);
            }
            try {
                Thread.sleep(DOWNLOAD_POLL_MILLIS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(
                        "Interrupted while waiting for the constancia popup/download in "
                                + downloadDir,
                        e);
            }
        }
    }

    /** Cierra toda ventana salvo la principal y vuelve a ella; best-effort. */
    private void closeNewWindows(String mainHandle) {
        Set<String> handles;
        try {
            handles = driver.getWindowHandles();
        } catch (Exception noWindows) {
            return;
        }
        for (String handle : handles) {
            if (!handle.equals(mainHandle)) {
                try {
                    driver.switchTo().window(handle).close();
                } catch (Exception alreadyClosed) {
                    // Limpieza best-effort del popup.
                }
            }
        }
        try {
            driver.switchTo().window(mainHandle);
        } catch (Exception cannotRestore) {
            // El caller reinicia a defaultContent; restauración best-effort.
        }
    }

    // Sondeo de descargas. Thread.sleep SOLO aquí, para el sistema de archivos;
    // cada interacción DOM usa WebDriverWait.

    /**
     * Sondea el dir hasta que aparece un PDF nuevo sin descarga parcial
     * ({@code *.crdownload}, {@code *.part}, {@code *.tmp}).
     *
     * @return el PDF más nuevo del directorio
     */
    public static File waitForNewPdf(Path dir, int pdfCountBefore, long timeoutSeconds) {
        long deadline = System.currentTimeMillis() + timeoutSeconds * 1000L;
        while (true) {
            if (!hasPartialDownload(dir) && countPdfs(dir) > pdfCountBefore) {
                File newest = newestPdf(dir);
                if (newest != null) {
                    return newest;
                }
            }
            if (System.currentTimeMillis() > deadline) {
                throw new IllegalStateException(
                        "Timed out after " + timeoutSeconds + "s waiting for constancia PDF in " + dir);
            }
            try {
                Thread.sleep(DOWNLOAD_POLL_MILLIS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Interrupted while waiting for constancia PDF in " + dir, e);
            }
        }
    }

    private static boolean hasPartialDownload(Path dir) {
        File[] partials = listBySuffix(dir, new String[] { ".crdownload", ".part", ".tmp" });
        return partials != null && partials.length > 0;
    }

    private static int countPdfs(Path dir) {
        File[] pdfs = listBySuffix(dir, new String[] { ".pdf" });
        return pdfs == null ? 0 : pdfs.length;
    }

    private static File newestPdf(Path dir) {
        File[] pdfs = listBySuffix(dir, new String[] { ".pdf" });
        if (pdfs == null || pdfs.length == 0) {
            return null;
        }
        File newest = pdfs[0];
        for (File pdf : pdfs) {
            if (pdf.lastModified() > newest.lastModified()) {
                newest = pdf;
            }
        }
        return newest;
    }

    private static File[] listBySuffix(Path dir, final String[] suffixes) {
        File folder = dir.toFile();
        if (!folder.isDirectory()) {
            return new File[0];
        }
        return folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File fileDir, String name) {
                String lower = name.toLowerCase();
                for (String suffix : suffixes) {
                    if (lower.endsWith(suffix)) {
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
