package mx.com.rocketnegocios.util.SatDocsAuto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Genera la Opinión de cumplimiento o la Constancia de situación fiscal en el
 * portal del SAT con la e.firma (FIEL) recibida y deja el PDF en
 * {@code destino}. Es el mismo flujo que antes corria dentro de la peticion
 * JSF (RnGcDocumentosFiscalesTblController); no depende de FacesContext, por
 * lo que puede ejecutarse en segundo plano.
 *
 * Cada llamada abre y cierra su propio Chrome headless y borra su directorio
 * temporal de descargas, haya exito o error.
 */
public final class GeneradorDocumentosSat {

    public static final String TIPO_OPINION = "OPINION";
    public static final String TIPO_CONSTANCIA = "CONSTANCIA";

    private GeneradorDocumentosSat() {
    }

    /**
     * @param tipo {@link #TIPO_OPINION} o {@link #TIPO_CONSTANCIA}.
     * @param destino ruta final del PDF (se reemplaza si existe).
     * @throws Exception cualquier falla del SAT/Selenium/disco; el caller la
     * registra y la traduce a un mensaje para el usuario.
     */
    public static void generar(String tipo, byte[] cer, byte[] key, String contrasenia, Path destino) throws Exception {
        ChromeDriver driver = null;
        Path tempDownloadDir = null;
        // Perfil propio por corrida: permite cerrar exactamente los procesos de este navegador.
        Path perfilDir = Files.createTempDirectory("sat-perfil-");
        try {
            File pdf;
            if (TIPO_OPINION.equals(tipo)) {
                tempDownloadDir = Files.createTempDirectory("opinion-dl-");
                driver = MapObjectOpiniónHeadless.buildChromeDriverConDescargas(tempDownloadDir.toString(), perfilDir);
                MapObjectOpiniónHeadless page = new MapObjectOpiniónHeadless(driver, tempDownloadDir.toString());
                page.open().loginConEFirma(cer, key, contrasenia);
                pdf = page.generarYDescargar();
            } else if (TIPO_CONSTANCIA.equals(tipo)) {
                tempDownloadDir = Files.createTempDirectory("constancia-dl-");
                driver = MapObjectConstanciaHeadless.buildChromeDriverConDescargas(tempDownloadDir.toString(), perfilDir);
                MapObjectConstanciaHeadless page = new MapObjectConstanciaHeadless(driver, tempDownloadDir.toString());
                page.irAlEjecutor().loginConEFirma(cer, key, contrasenia);
                pdf = page.generarYDescargar();
            } else {
                throw new IllegalArgumentException("Tipo de documento SAT no soportado: " + tipo);
            }
            AlmacenDocumentosSat.guardar(pdf.toPath(), destino);
        } finally {
            SatDocsBase.cerrarNavegador(driver, perfilDir);
            borrarDirectorioTemporal(tempDownloadDir);
            borrarDirectorioTemporal(perfilDir);
        }
    }

    /** Best-effort recursive delete of a per-run temp dir (downloads or browser profile). */
    private static void borrarDirectorioTemporal(Path dir) {
        if (dir == null) {
            return;
        }
        borrarRecursivo(dir.toFile());
    }

    private static void borrarRecursivo(File archivo) {
        File[] hijos = archivo.isDirectory() && !Files.isSymbolicLink(archivo.toPath()) ? archivo.listFiles() : null;
        if (hijos != null) {
            for (File hijo : hijos) {
                borrarRecursivo(hijo);
            }
        }
        archivo.delete();
    }

    /** Lee el PDF almacenado; null si ya no existe (expirado o borrado). */
    public static byte[] leerSiExiste(String ruta) {
        if (ruta == null) {
            return null;
        }
        Path path = new File(ruta).toPath();
        if (!Files.isRegularFile(path)) {
            return null;
        }
        try {
            return Files.readAllBytes(path);
        } catch (IOException ex) {
            return null;
        }
    }
}
