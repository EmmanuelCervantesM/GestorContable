package mx.com.rocketnegocios.util;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class CertificadoUtil {
    public static final String TIPO_FIEL="FIEL";
    public static final String TIPO_CSD="CSD";
    
    public static X509Certificate parseCertificado(byte[] archivoCer) throws Exception {
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        return(X509Certificate) factory.generateCertificate(new ByteArrayInputStream(archivoCer));
    }
    // Una sola regla FIEL/CSD en toda la app: se delega a CertificadoTipoUtil
    // (la misma que usa el alta de certificados y el timbrado).
    public static String detectarTipoCertificado(X509Certificate cert) throws Exception {
        return CertificadoTipoUtil.determinarTipo(cert);
    }
    //  detectar tipo de certificado a partir del archivo .cer
    public static String detectarTipoCertificado(byte[] archivoCer) throws Exception {
        return detectarTipoCertificado(parseCertificado(archivoCer));
    }
        public static String obtenerNumeroSerie(X509Certificate cert) {
        byte[] bytesSerie = cert.getSerialNumber().toByteArray();
        StringBuilder sb = new StringBuilder();
        for (byte b : bytesSerie) {
            if (b != 0) {
                sb.append((char) b);
            }
        }
        return sb.toString();
    }

}
