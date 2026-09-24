package mx.com.rocketnegocios.util.SatDocsAuto;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page object de la Opinión de Cumplimiento con e.firma (sin captcha ni CIEC).
 * Abre el login directo PTSC, hace login y guarda el iframe PDF base64 en línea;
 * este flujo no tiene botones ni sondeo de descarga.
 */
public class MapObjectOpiniónHeadless extends SatDocsBase {

    /** Login directo PTSC que genera la opinión en línea. */
    public static final String URL_FUNCIONAL = "https://ptsc32d.clouda.sat.gob.mx/?/reporteOpinion32DContribuyente";

    /** PDF en línea que el SPA renderiza tras el login. */
    public static final By PDF_IFRAME = By.cssSelector(
            "iframe[title='pdfReporteOpinion'],"
            + " iframe[type='application/pdf']");

    public MapObjectOpiniónHeadless(WebDriver driver, String downloadDir) {
        this(driver, downloadDir, DEFAULT_TIMEOUT_SECONDS);
    }

    /**
     * @param driver         WebDriver activo
     * @param downloadDir    directorio de descarga; se resuelve a absoluta y se crea
     * @param timeoutSeconds espera explícita para interacciones DOM, en segundos
     */
    public MapObjectOpiniónHeadless(WebDriver driver, String downloadDir, long timeoutSeconds) {
        super(driver, downloadDir, timeoutSeconds);
    }

    /** Abre la app PTSC de opinión. */
    public MapObjectOpiniónHeadless open() {
        driver.get(URL_FUNCIONAL);
        return this;
    }

    /** Selecciona la pestaña e.firma; tolera que ya esté visible. */
    public MapObjectOpiniónHeadless seleccionarEFirma() {
        super.seleccionarEFirma(EFIRMA_TAB);
        return this;
    }

    /**
     * Login e.firma: sube *.cer/*.key, escribe la contraseña y envía. Ambas rutas
     * deben existir (en producción son temps de {@code loginConEFirma(byte[],
     * byte[], String)} desde los BLOBs).
     *
     * @param cerPath  ruta a un *.cer existente
     * @param keyPath  ruta a un *.key existente
     * @param password contraseña de la llave (nunca se registra)
     */
    public MapObjectOpiniónHeadless loginConEFirma(String cerPath, String keyPath, String password) {
        seleccionarEFirma();
        File cerFile = requireCredentialFile(cerPath, ".cer");
        File keyFile = requireCredentialFile(keyPath, ".key");
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("La contraseña de la llave de e.firma no debe estar vacía");
        }

        WebElement cerInput = wait.until(ExpectedConditions.presenceOfElementLocated(CER_INPUT));
        cerInput.sendKeys(cerFile.getAbsolutePath());

        WebElement keyInput = wait.until(ExpectedConditions.presenceOfElementLocated(KEY_INPUT));
        keyInput.sendKeys(keyFile.getAbsolutePath());

        WebElement passwordInput =
                wait.until(ExpectedConditions.visibilityOfElementLocated(PRIVATE_PASSWORD_INPUT));
        passwordInput.clear();
        passwordInput.sendKeys(password);

        WebElement submit = wait.until(ExpectedConditions.elementToBeClickable(SUBMIT_BUTTON));
        submit.click();
        return this;
    }

    /**
     * Login e.firma desde la BD (BLOBs de RnGcCertificadosTbl). Escribe los bytes a
     * un temp dir solo durante la subida y los borra al volver, con éxito o falla.
     *
     * @param cerBytes raw *.cer (certificadoSelloDigital)
     * @param keyBytes raw *.key (llavePrivada)
     * @param password contraseña de la llave (contraseniaLlavePrivada, nunca se registra)
     */
    public MapObjectOpiniónHeadless loginConEFirma(byte[] cerBytes, byte[] keyBytes, String password) {
        try (TempCredentials creds = TempCredentials.write(cerBytes, keyBytes)) {
            return loginConEFirma(creds.cerPath(), creds.keyPath(), password);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudieron escribir los archivos temporales de credenciales de e.firma", e);
        }
    }

    /**
     * Lee el iframe PDF base64 tras el login y lo guarda en el download dir.
     *
     * @return el PDF de opinión guardado
     */
    public File generarYDescargar() {
        WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(PDF_IFRAME));
        String src = iframe.getAttribute("src");
        if (src != null && src.startsWith(PDF_BASE64_PREFIX)) {
            return saveBase64Pdf(src.substring(PDF_BASE64_PREFIX.length()), "opinion32D-");
        }
        throw new IllegalStateException("No se pudo encontrar el iframe PDF de opinión");
    }
}
