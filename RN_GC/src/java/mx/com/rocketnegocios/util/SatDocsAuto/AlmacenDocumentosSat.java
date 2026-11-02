package mx.com.rocketnegocios.util.SatDocsAuto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Almacenamiento temporal en disco de los PDF de Opinión de cumplimiento y
 * Constancia de situación fiscal.
 * {@link #ttlMinutos()} minutos.
 *
 * Configuracion (opciones JVM de GlassFish):
 * <ul>
 * <li>{@code -Drn.gc.docs.sat.dir=/ruta} directorio base (default
 * {@code ${java.io.tmpdir}/rn-gc-docs-sat}).</li>
 * <li>{@code -Drn.gc.docs.sat.ttl.minutos=30} vigencia del PDF.</li>
 * </ul>
 * Estructura: {@code <base>/<usuarioId>/<tipo>-<jobId>.pdf}. Son documentos
 * fiscales del contribuyente: en sistemas POSIX las carpetas quedan en 700 y
 * los PDF en 600 (solo el usuario de GlassFish).
 */
public final class AlmacenDocumentosSat {

    private static final Logger LOG = Logger.getLogger(AlmacenDocumentosSat.class.getName());

    public static final String PROP_DIRECTORIO = "rn.gc.docs.sat.dir";
    public static final String PROP_TTL_MINUTOS = "rn.gc.docs.sat.ttl.minutos";
    public static final int TTL_MINUTOS_DEFAULT = 30;

    private static final Set<PosixFilePermission> PERMISOS_DIRECTORIO = PosixFilePermissions.fromString("rwx------");
    private static final Set<PosixFilePermission> PERMISOS_ARCHIVO = PosixFilePermissions.fromString("rw-------");

    private AlmacenDocumentosSat() {
    }

    public static Path directorioBase() {
        String dir = System.getProperty(PROP_DIRECTORIO);
        if (dir == null || dir.trim().isEmpty()) {
            return Paths.get(System.getProperty("java.io.tmpdir"), "rn-gc-docs-sat");
        }
        return Paths.get(dir.trim());
    }

    public static int ttlMinutos() {
        String valor = System.getProperty(PROP_TTL_MINUTOS);
        if (valor != null) {
            try {
                int minutos = Integer.parseInt(valor.trim());
                if (minutos > 0) {
                    return minutos;
                }
            } catch (NumberFormatException ex) {
                LOG.log(Level.WARNING, "Valor invalido en {0}: {1}; se usan {2} minutos",
                        new Object[] { PROP_TTL_MINUTOS, valor, TTL_MINUTOS_DEFAULT });
            }
        }
        return TTL_MINUTOS_DEFAULT;
    }

    public static Path rutaPara(Integer usuarioId, String tipo, Integer jobId) {
        return directorioBase().resolve(String.valueOf(usuarioId))
                .resolve(tipo.toLowerCase() + "-" + jobId + ".pdf");
    }

    /**
     * Copia el PDF generado a {@code destino} (se reemplaza si existe),
     * creando la carpeta del usuario y dejando carpetas y archivo accesibles
     * solo para el usuario del servidor.
     */
    public static void guardar(Path origen, Path destino) throws IOException {
        Path carpetaUsuario = destino.getParent();
        Files.createDirectories(carpetaUsuario);
        restringir(directorioBase(), PERMISOS_DIRECTORIO);
        restringir(carpetaUsuario, PERMISOS_DIRECTORIO);
        // El directorio temporal puede estar en otro sistema de archivos: copiar, no
        // mover.
        Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
        restringir(destino, PERMISOS_ARCHIVO);
    }

    /** Sin efecto en sistemas de archivos que no son POSIX (p. ej. Windows). */
    private static void restringir(Path ruta, Set<PosixFilePermission> permisos) throws IOException {
        try {
            Files.setPosixFilePermissions(ruta, permisos);
        } catch (UnsupportedOperationException ex) {
            LOG.log(Level.FINE, "Sistema de archivos sin permisos POSIX: {0}", ruta);
        }
    }

    /** Best-effort: borra el archivo si existe. */
    public static void borrar(String ruta) {
        if (ruta == null) {
            return;
        }
        try {
            Files.deleteIfExists(new File(ruta).toPath());
        } catch (Exception ex) {
            LOG.log(Level.WARNING, "No se pudo borrar el documento SAT temporal " + ruta, ex);
        }
    }
}
