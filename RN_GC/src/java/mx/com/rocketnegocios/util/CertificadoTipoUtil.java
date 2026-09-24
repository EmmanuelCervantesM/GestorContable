package mx.com.rocketnegocios.util;

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * CTR-13: identifica si un certificado del SAT es FIEL (e.firma) o CSD
 * (Certificado de Sello Digital) a partir de su contenido X.509.
 *
 * Regla de negocio:
 *  - Si el Subject trae el campo OU (Organizational Unit) => CSD.
 *  - Si no trae OU pero el Extended Key Usage incluye Client Authentication
 *    (OID 1.3.6.1.5.5.7.3.2) => FIEL.
 *  - En cualquier otro caso no se puede determinar el tipo (null).
 */
public final class CertificadoTipoUtil {

    public static final String TIPO_FIEL = "FIEL";
    public static final String TIPO_CSD = "CSD";

    /** OID estandar de Extended Key Usage "Client Authentication". */
    private static final String OID_CLIENT_AUTH = "1.3.6.1.5.5.7.3.2";

    private CertificadoTipoUtil() {
    }

    /**
     * @return {@link #TIPO_CSD}, {@link #TIPO_FIEL}, o {@code null} si no se
     * pudo determinar el tipo con las reglas de negocio vigentes.
     */
    public static String determinarTipo(X509Certificate certificado) {
        if (certificado == null) {
            return null;
        }
        if (tieneOrganizationalUnit(certificado)) {
            return TIPO_CSD;
        }
        if (tieneClientAuthentication(certificado)) {
            return TIPO_FIEL;
        }
        return null;
    }

    private static boolean tieneOrganizationalUnit(X509Certificate certificado) {
        String subjectDn = certificado.getSubjectX500Principal().getName();
        if (subjectDn == null) {
            return false;
        }
        // RFC2253: los componentes van separados por coma, p.ej. "CN=..,OU=..,O=..".
        for (String componente : subjectDn.split(",")) {
            if (componente.trim().toUpperCase().startsWith("OU=")) {
                return true;
            }
        }
        return false;
    }

    private static boolean tieneClientAuthentication(X509Certificate certificado) {
        try {
            List<String> extendedKeyUsage = certificado.getExtendedKeyUsage();
            return extendedKeyUsage != null && extendedKeyUsage.contains(OID_CLIENT_AUTH);
        } catch (CertificateParsingException ex) {
            return false;
        }
    }
}
