package mx.com.rocketnegocios.util;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

public class CertificadoUtil {
    public static final String TIPO_FIEL="FIEL";
    public static final String TIPO_CSD="CSD";
    
    private static final String OID_CLIENT_AUTH = "1.3.6.1.5.5.7.3.2";
    public static X509Certificate parseCertificado(byte[] archivoCer) throws Exception {
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        return(X509Certificate) factory.generateCertificate(new ByteArrayInputStream(archivoCer));
    }
    public static String detectarTipoCertificado(X509Certificate cert) throws Exception {
        String subject = cert.getSubjectX500Principal().getName();
        boolean tieneOU = subject.contains("OU=");

        List<String> extendedKeyUsage = cert.getExtendedKeyUsage();
        boolean esClientAuth = extendedKeyUsage != null && extendedKeyUsage.contains(OID_CLIENT_AUTH);

        if(esClientAuth) {
            return TIPO_FIEL;
        }else if (tieneOU) {
            return TIPO_CSD;
        }else {
            return null;
        }
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
