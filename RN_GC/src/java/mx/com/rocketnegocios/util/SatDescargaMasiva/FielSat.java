package mx.com.rocketnegocios.util.SatDescargaMasiva;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.security.auth.x500.X500Principal;
import org.apache.commons.ssl.PKCS8Key;

/**
 * CTR-06: e.firma (FIEL) lista para firmar peticiones del web service de
 * Descarga Masiva del SAT. Descifra la llave privada con PKCS8Key (misma
 * libreria ya usada en el proyecto) y firma con SHA1withRSA, que es lo que
 * exige el servicio.
 */
public class FielSat {

    /** OID x500UniqueIdentifier: el SAT guarda ahi "RFC / RFC representante". */
    private static final String OID_UNIQUE_IDENTIFIER = "2.5.4.45";

    private final byte[] certificadoDer;
    private final X509Certificate certificado;
    private final PrivateKey llavePrivada;

    public FielSat(byte[] certificadoDer, byte[] llavePrivadaDer, String contrasenia) throws DescargaMasivaSatException {
        if (certificadoDer == null || llavePrivadaDer == null || contrasenia == null) {
            throw new DescargaMasivaSatException("La e.firma esta incompleta (certificado, llave o contraseña).");
        }
        try {
            this.certificadoDer = certificadoDer.clone();
            this.certificado = (X509Certificate) CertificateFactory.getInstance("X.509")
                    .generateCertificate(new ByteArrayInputStream(certificadoDer));
            this.llavePrivada = new PKCS8Key(llavePrivadaDer, contrasenia.toCharArray()).getPrivateKey();
        } catch (GeneralSecurityException | RuntimeException ex) {
            throw new DescargaMasivaSatException("No fue posible leer la e.firma: " + ex.getMessage(), ex);
        }
    }

    /** @return true si hoy esta dentro de notBefore/notAfter del certificado. */
    public boolean isVigente() {
        try {
            certificado.checkValidity();
            return true;
        } catch (CertificateExpiredException | CertificateNotYetValidException ex) {
            return false;
        }
    }

    /**
     * RFC del titular leido del x500UniqueIdentifier del Subject. En personas
     * morales el campo trae "RFC / RFC del representante"; se toma el primero.
     */
    public String getRfc() {
        String valor = obtenerAtributoSubject(OID_UNIQUE_IDENTIFIER);
        if (valor == null) {
            return null;
        }
        String rfc = valor.split("/")[0].trim().toUpperCase();
        return rfc.isEmpty() ? null : rfc;
    }

    public String getCertificadoBase64() {
        return Base64.getEncoder().encodeToString(certificadoDer);
    }

    public String getNumeroSerieDecimal() {
        return certificado.getSerialNumber().toString();
    }

    /** Emisor en formato RFC 2253/4514 con nombres legibles para los OID del SAT. */
    public String getNombreEmisor() {
        Map<String, String> oids = new HashMap<>();
        oids.put("1.2.840.113549.1.9.1", "emailAddress");
        oids.put("1.2.840.113549.1.9.2", "unstructuredName");
        oids.put("2.5.4.17", "postalCode");
        oids.put("2.5.4.45", "x500UniqueIdentifier");
        oids.put("2.5.4.5", "serialNumber");
        return certificado.getIssuerX500Principal().getName(X500Principal.RFC2253, oids);
    }

    public X509Certificate getCertificado() {
        return certificado;
    }

    /** Firma SHA1withRSA del texto (UTF-8) y lo regresa en base64. */
    public String firmar(String texto) throws DescargaMasivaSatException {
        try {
            Signature firma = Signature.getInstance("SHA1withRSA");
            firma.initSign(llavePrivada);
            firma.update(texto.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(firma.sign());
        } catch (GeneralSecurityException ex) {
            throw new DescargaMasivaSatException("No fue posible firmar con la e.firma: " + ex.getMessage(), ex);
        }
    }

    private String obtenerAtributoSubject(String oid) {
        try {
            LdapName nombre = new LdapName(certificado.getSubjectX500Principal().getName(X500Principal.RFC2253));
            for (Rdn rdn : nombre.getRdns()) {
                if (oid.equalsIgnoreCase(rdn.getType())) {
                    return decodificarValor(rdn.getValue());
                }
            }
        } catch (InvalidNameException ex) {
            return null;
        }
        return null;
    }

    /**
     * RFC 2253 serializa los atributos sin nombre conocido como "#" + DER en
     * hexadecimal; LdapName lo entrega ya como byte[] (TLV). Se decodifica el
     * string DER (PrintableString/UTF8String/IA5String) de forma minima.
     */
    private static String decodificarValor(Object valor) {
        if (valor instanceof String) {
            return (String) valor;
        }
        if (!(valor instanceof byte[])) {
            return null;
        }
        byte[] der = (byte[]) valor;
        if (der.length < 2) {
            return null;
        }
        int posicion = 1;
        int longitud = der[posicion++] & 0xFF;
        if ((longitud & 0x80) != 0) {
            int bytesLongitud = longitud & 0x7F;
            longitud = 0;
            for (int i = 0; i < bytesLongitud && posicion < der.length; i++) {
                longitud = (longitud << 8) | (der[posicion++] & 0xFF);
            }
        }
        if (posicion + longitud > der.length) {
            return null;
        }
        return new String(der, posicion, longitud, StandardCharsets.UTF_8);
    }
}
