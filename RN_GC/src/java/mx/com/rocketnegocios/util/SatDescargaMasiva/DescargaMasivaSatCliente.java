package mx.com.rocketnegocios.util.SatDescargaMasiva;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.UUID;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * CTR-06: cliente minimo (sin dependencias de JSF/EJB) del web service de
 * Descarga Masiva de CFDI del SAT, version 1.5.
 *
 * Los mensajes SOAP se arman como texto ya canonicalizado (exc-c14n), igual
 * que phpcfdi/sat-ws-descarga-masiva: el digest se calcula sobre el nodo sin
 * espacios y con los atributos de "solicitud" ordenados alfabeticamente, que
 * es exactamente la forma canonica que valida el SAT.
 *
 * Una instancia es para un solo usuario/FIEL y no es thread-safe.
 */
public class DescargaMasivaSatCliente {

    public static final String TIPO_EMITIDOS = "EMITIDOS";
    public static final String TIPO_RECIBIDOS = "RECIBIDOS";

    public static final String ESTADO_TODOS = "Todos";
    public static final String ESTADO_VIGENTE = "Vigente";
    public static final String ESTADO_CANCELADO = "Cancelado";

    /** CodEstatus de exito en solicitud, verificacion y descarga. */
    public static final String COD_ESTATUS_OK = "5000";

    private static final String URL_AUTENTICACION = "https://cfdidescargamasivasolicitud.clouda.sat.gob.mx/Autenticacion/Autenticacion.svc";
    private static final String URL_SOLICITUD = "https://cfdidescargamasivasolicitud.clouda.sat.gob.mx/SolicitaDescargaService.svc";
    private static final String URL_VERIFICACION = "https://cfdidescargamasivasolicitud.clouda.sat.gob.mx/VerificaSolicitudDescargaService.svc";
    private static final String URL_DESCARGA = "https://cfdidescargamasiva.clouda.sat.gob.mx/DescargaMasivaService.svc";

    private static final String ACCION_AUTENTICA = "http://DescargaMasivaTerceros.gob.mx/IAutenticacion/Autentica";
    private static final String ACCION_SOLICITUD = "http://DescargaMasivaTerceros.sat.gob.mx/ISolicitaDescargaService/";
    private static final String ACCION_VERIFICA = "http://DescargaMasivaTerceros.sat.gob.mx/IVerificaSolicitudDescargaService/VerificaSolicitudDescarga";
    private static final String ACCION_DESCARGA = "http://DescargaMasivaTerceros.sat.gob.mx/IDescargaMasivaTercerosService/Descargar";

    private static final String NS_SOAP = "http://schemas.xmlsoap.org/soap/envelope/";
    private static final String NS_DES = "http://DescargaMasivaTerceros.sat.gob.mx";
    private static final String NS_DSIG = "http://www.w3.org/2000/09/xmldsig#";
    private static final String NS_WSU = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
    private static final String NS_WSSE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
    private static final String X509_V3 = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3";
    private static final String BASE64_BINARY = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary";
    private static final String C14N_EXC = "http://www.w3.org/2001/10/xml-exc-c14n#";

    private static final int TIMEOUT_CONEXION_MS = 20000;
    private static final int TIMEOUT_LECTURA_MS = 60000;
    /** Los paquetes pueden pesar decenas de MB (base64): lectura mas holgada. */
    private static final int TIMEOUT_LECTURA_DESCARGA_MS = 300000;
    /** El token del SAT dura 5 minutos; se renueva antes para no rozar el limite. */
    private static final long VIGENCIA_TOKEN_MS = 4 * 60 * 1000L;

    private final FielSat fiel;
    private String token;
    private long tokenObtenidoEn;

    public DescargaMasivaSatCliente(FielSat fiel) {
        this.fiel = fiel;
    }

    /** Autentica con la FIEL y regresa el token (tambien queda cacheado). */
    public String autenticar() throws DescargaMasivaSatException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        formato.setTimeZone(TimeZone.getTimeZone("UTC"));
        long ahora = System.currentTimeMillis();
        String creado = formato.format(new Date(ahora));
        String expira = formato.format(new Date(ahora + 300000));
        String idToken = "uuid-" + UUID.randomUUID() + "-1";

        String timestamp = "<u:Timestamp xmlns:u=\"" + NS_WSU + "\" u:Id=\"_0\"><u:Created>" + creado
                + "</u:Created><u:Expires>" + expira + "</u:Expires></u:Timestamp>";
        String keyInfo = "<KeyInfo><o:SecurityTokenReference><o:Reference URI=\"#" + idToken + "\" ValueType=\""
                + X509_V3 + "\"/></o:SecurityTokenReference></KeyInfo>";
        String firma = crearFirma(timestamp, "#_0", keyInfo);

        String sobre = "<s:Envelope xmlns:s=\"" + NS_SOAP + "\" xmlns:u=\"" + NS_WSU + "\">"
                + "<s:Header><o:Security xmlns:o=\"" + NS_WSSE + "\" s:mustUnderstand=\"1\">"
                + "<u:Timestamp u:Id=\"_0\"><u:Created>" + creado + "</u:Created><u:Expires>" + expira + "</u:Expires></u:Timestamp>"
                + "<o:BinarySecurityToken u:Id=\"" + idToken + "\" ValueType=\"" + X509_V3 + "\" EncodingType=\""
                + BASE64_BINARY + "\">" + fiel.getCertificadoBase64() + "</o:BinarySecurityToken>"
                + firma + "</o:Security></s:Header>"
                + "<s:Body><Autentica xmlns=\"http://DescargaMasivaTerceros.gob.mx\"/></s:Body></s:Envelope>";

        Document respuesta = enviar(URL_AUTENTICACION, ACCION_AUTENTICA, sobre, null, TIMEOUT_LECTURA_MS);
        Element resultado = buscarElemento(respuesta.getDocumentElement(), "AutenticaResult");
        if (resultado == null || resultado.getTextContent().trim().isEmpty()) {
            throw new DescargaMasivaSatException("El SAT no regreso token de autenticacion.");
        }
        token = resultado.getTextContent().trim();
        tokenObtenidoEn = System.currentTimeMillis();
        return token;
    }

    /**
     * Solicitud de CFDI emitidos por el RFC de la FIEL.
     *
     * @param estadoComprobante Todos, Vigente o Cancelado.
     */
    public ResultadoSolicitudSat solicitarEmitidos(Date fechaInicial, Date fechaFinal, String estadoComprobante)
            throws DescargaMasivaSatException {
        Map<String, String> atributos = atributosBase(fechaInicial, fechaFinal, estadoComprobante);
        atributos.put("RfcEmisor", fiel.getRfc());
        return solicitar("SolicitaDescargaEmitidos", atributos);
    }

    /**
     * Solicitud de CFDI recibidos por el RFC de la FIEL. En v1.5 el SAT solo
     * entrega XML de recibidos Vigentes (los cancelados solo por Metadata),
     * por eso el estado se fuerza a Vigente.
     */
    public ResultadoSolicitudSat solicitarRecibidos(Date fechaInicial, Date fechaFinal) throws DescargaMasivaSatException {
        Map<String, String> atributos = atributosBase(fechaInicial, fechaFinal, ESTADO_VIGENTE);
        atributos.put("RfcReceptor", fiel.getRfc());
        return solicitar("SolicitaDescargaRecibidos", atributos);
    }

    public ResultadoVerificacionSat verificar(String idSolicitud) throws DescargaMasivaSatException {
        String atributos = "IdSolicitud=\"" + escaparAtributo(idSolicitud) + "\" RfcSolicitante=\"" + escaparAtributo(fiel.getRfc()) + "\"";
        String aDigerir = "<des:VerificaSolicitudDescarga xmlns:des=\"" + NS_DES + "\"><des:solicitud " + atributos
                + "></des:solicitud></des:VerificaSolicitudDescarga>";
        String sobre = sobreConFirma("<des:VerificaSolicitudDescarga><des:solicitud " + atributos + ">"
                + crearFirma(aDigerir, "", keyInfoX509()) + "</des:solicitud></des:VerificaSolicitudDescarga>");

        Document respuesta = enviar(URL_VERIFICACION, ACCION_VERIFICA, sobre, obtenerToken(), TIMEOUT_LECTURA_MS);
        Element resultado = buscarElemento(respuesta.getDocumentElement(), "VerificaSolicitudDescargaResult");
        if (resultado == null) {
            throw new DescargaMasivaSatException("Respuesta de verificacion del SAT sin resultado.");
        }
        String codEstatus = atributo(resultado, "CodEstatus");
        String mensaje = atributo(resultado, "Mensaje");
        if (!COD_ESTATUS_OK.equals(codEstatus)) {
            throw new DescargaMasivaSatException("El SAT rechazo la verificacion: " + codEstatus + " " + mensaje,
                    codEstatus, mensaje, 200, null);
        }
        List<String> paquetes = new ArrayList<>();
        NodeList hijos = resultado.getChildNodes();
        for (int i = 0; i < hijos.getLength(); i++) {
            Node hijo = hijos.item(i);
            if (hijo.getNodeType() == Node.ELEMENT_NODE && "IdsPaquetes".equalsIgnoreCase(hijo.getLocalName())) {
                String id = hijo.getTextContent().trim();
                if (!id.isEmpty()) {
                    paquetes.add(id);
                }
            }
        }
        return new ResultadoVerificacionSat(codEstatus, mensaje,
                entero(atributo(resultado, "EstadoSolicitud"), 0),
                entero(atributo(resultado, "CodigoEstadoSolicitud"), null),
                entero(atributo(resultado, "NumeroCFDIs"), 0),
                paquetes);
    }

    /** Descarga un paquete ya listo y regresa los bytes del ZIP. */
    public byte[] descargarPaquete(String idPaquete) throws DescargaMasivaSatException {
        String atributos = "IdPaquete=\"" + escaparAtributo(idPaquete) + "\" RfcSolicitante=\"" + escaparAtributo(fiel.getRfc()) + "\"";
        String aDigerir = "<des:PeticionDescargaMasivaTercerosEntrada xmlns:des=\"" + NS_DES + "\"><des:peticionDescarga "
                + atributos + "></des:peticionDescarga></des:PeticionDescargaMasivaTercerosEntrada>";
        String sobre = sobreConFirma("<des:PeticionDescargaMasivaTercerosEntrada><des:peticionDescarga " + atributos + ">"
                + crearFirma(aDigerir, "", keyInfoX509()) + "</des:peticionDescarga></des:PeticionDescargaMasivaTercerosEntrada>");

        Document respuesta = enviar(URL_DESCARGA, ACCION_DESCARGA, sobre, obtenerToken(), TIMEOUT_LECTURA_DESCARGA_MS);
        Element encabezado = buscarElemento(respuesta.getDocumentElement(), "respuesta");
        String codEstatus = encabezado == null ? null : atributo(encabezado, "CodEstatus");
        String mensaje = encabezado == null ? null : atributo(encabezado, "Mensaje");
        if (!COD_ESTATUS_OK.equals(codEstatus)) {
            throw new DescargaMasivaSatException("El SAT rechazo la descarga del paquete: " + codEstatus + " " + mensaje,
                    codEstatus, mensaje, 200, null);
        }
        Element paquete = buscarElemento(respuesta.getDocumentElement(), "Paquete");
        if (paquete == null || paquete.getTextContent().trim().isEmpty()) {
            throw new DescargaMasivaSatException("El SAT no regreso el contenido del paquete " + idPaquete + ".");
        }
        try {
            return Base64.getMimeDecoder().decode(paquete.getTextContent());
        } catch (IllegalArgumentException ex) {
            throw new DescargaMasivaSatException("El paquete del SAT no es base64 valido.", ex);
        }
    }

    public FielSat getFiel() {
        return fiel;
    }

    // ------------------------------------------------------------------
    // Armado de mensajes
    // ------------------------------------------------------------------

    private Map<String, String> atributosBase(Date fechaInicial, Date fechaFinal, String estadoComprobante)
            throws DescargaMasivaSatException {
        if (fechaInicial == null || fechaFinal == null || !fechaInicial.before(fechaFinal)) {
            throw new DescargaMasivaSatException("La fecha inicial debe ser menor a la fecha final.");
        }
        // TreeMap = orden alfabetico de atributos, requisito de la forma canonica.
        Map<String, String> atributos = new TreeMap<>();
        atributos.put("RfcSolicitante", fiel.getRfc());
        atributos.put("TipoSolicitud", "CFDI");
        atributos.put("FechaInicial", formatoFechaSat(fechaInicial));
        atributos.put("FechaFinal", formatoFechaSat(fechaFinal));
        if (estadoComprobante != null && !estadoComprobante.trim().isEmpty()) {
            atributos.put("EstadoComprobante", estadoComprobante);
        }
        return atributos;
    }

    private ResultadoSolicitudSat solicitar(String operacion, Map<String, String> atributosOrdenados)
            throws DescargaMasivaSatException {
        StringBuilder atributos = new StringBuilder();
        for (Map.Entry<String, String> entrada : atributosOrdenados.entrySet()) {
            if (atributos.length() > 0) {
                atributos.append(' ');
            }
            atributos.append(entrada.getKey()).append("=\"").append(escaparAtributo(entrada.getValue())).append('"');
        }
        String aDigerir = "<des:" + operacion + " xmlns:des=\"" + NS_DES + "\"><des:solicitud " + atributos
                + "></des:solicitud></des:" + operacion + ">";
        String sobre = sobreConFirma("<des:" + operacion + "><des:solicitud " + atributos + ">"
                + crearFirma(aDigerir, "", keyInfoX509()) + "</des:solicitud></des:" + operacion + ">");

        Document respuesta = enviar(URL_SOLICITUD, ACCION_SOLICITUD + operacion, sobre, obtenerToken(), TIMEOUT_LECTURA_MS);
        Element resultado = buscarElemento(respuesta.getDocumentElement(), operacion + "Result");
        if (resultado == null) {
            throw new DescargaMasivaSatException("Respuesta de solicitud del SAT sin resultado.");
        }
        String codEstatus = atributo(resultado, "CodEstatus");
        String mensaje = atributo(resultado, "Mensaje");
        String idSolicitud = atributo(resultado, "IdSolicitud");
        if (!COD_ESTATUS_OK.equals(codEstatus) || idSolicitud == null || idSolicitud.isEmpty()) {
            throw new DescargaMasivaSatException("El SAT rechazo la solicitud: " + codEstatus + " " + mensaje,
                    codEstatus, mensaje, 200, null);
        }
        return new ResultadoSolicitudSat(codEstatus, mensaje, idSolicitud);
    }

    private static String sobreConFirma(String cuerpo) {
        return "<s:Envelope xmlns:s=\"" + NS_SOAP + "\" xmlns:des=\"" + NS_DES + "\" xmlns:xd=\"" + NS_DSIG + "\">"
                + "<s:Header/><s:Body>" + cuerpo + "</s:Body></s:Envelope>";
    }

    private String keyInfoX509() {
        return "<KeyInfo><X509Data><X509IssuerSerial><X509IssuerName>" + escaparTexto(fiel.getNombreEmisor())
                + "</X509IssuerName><X509SerialNumber>" + fiel.getNumeroSerieDecimal()
                + "</X509SerialNumber></X509IssuerSerial><X509Certificate>" + fiel.getCertificadoBase64()
                + "</X509Certificate></X509Data></KeyInfo>";
    }

    /**
     * Firma XMLDSig con SHA1/RSA-SHA1 y exc-c14n. {@code aDigerir} ya viene en
     * forma canonica (sin espacios, namespaces declarados en el nodo).
     */
    private String crearFirma(String aDigerir, String uri, String keyInfo) throws DescargaMasivaSatException {
        String digest;
        try {
            digest = Base64.getEncoder().encodeToString(
                    MessageDigest.getInstance("SHA-1").digest(aDigerir.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException ex) {
            throw new DescargaMasivaSatException("SHA-1 no disponible en la JVM.", ex);
        }
        String signedInfoInterno = "<CanonicalizationMethod Algorithm=\"" + C14N_EXC + "\"></CanonicalizationMethod>"
                + "<SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"></SignatureMethod>"
                + "<Reference URI=\"" + uri + "\"><Transforms><Transform Algorithm=\"" + C14N_EXC + "\"></Transform></Transforms>"
                + "<DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"></DigestMethod>"
                + "<DigestValue>" + digest + "</DigestValue></Reference>";
        String signedInfoCanonico = "<SignedInfo xmlns=\"" + NS_DSIG + "\">" + signedInfoInterno + "</SignedInfo>";
        String valorFirma = fiel.firmar(signedInfoCanonico);
        return "<Signature xmlns=\"" + NS_DSIG + "\"><SignedInfo>" + signedInfoInterno + "</SignedInfo>"
                + "<SignatureValue>" + valorFirma + "</SignatureValue>" + keyInfo + "</Signature>";
    }

    private String obtenerToken() throws DescargaMasivaSatException {
        if (token == null || System.currentTimeMillis() - tokenObtenidoEn > VIGENCIA_TOKEN_MS) {
            autenticar();
        }
        return token;
    }

    /**
     * El SAT interpreta la fecha como hora local de Mexico sin zona. Se usa la
     * zona por defecto de la JVM, la misma con la que p:calendar convierte lo
     * que captura el usuario, para respetar la hora "de reloj" capturada.
     */
    private static String formatoFechaSat(Date fecha) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(fecha);
    }

    /** Escapado de atributos segun exc-c14n (&amp; &lt; &quot;). */
    private static String escaparAtributo(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.replace("&", "&amp;").replace("<", "&lt;").replace("\"", "&quot;");
    }

    private static String escaparTexto(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    // ------------------------------------------------------------------
    // Transporte y lectura de respuestas
    // ------------------------------------------------------------------

    private Document enviar(String url, String accion, String sobre, String tokenAcceso, int timeoutLectura)
            throws DescargaMasivaSatException {
        HttpsURLConnection conexion = null;
        try {
            conexion = (HttpsURLConnection) new URL(url).openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);
            conexion.setConnectTimeout(TIMEOUT_CONEXION_MS);
            conexion.setReadTimeout(timeoutLectura);
            conexion.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            conexion.setRequestProperty("SOAPAction", accion);
            if (tokenAcceso != null) {
                conexion.setRequestProperty("Authorization", "WRAP access_token=\"" + tokenAcceso + "\"");
            }
            try (OutputStream salida = conexion.getOutputStream()) {
                salida.write(sobre.getBytes(StandardCharsets.UTF_8));
            }
            int codigoHttp = conexion.getResponseCode();
            InputStream entrada = codigoHttp < 400 ? conexion.getInputStream() : conexion.getErrorStream();
            byte[] cuerpo = leerTodo(entrada);
            Document documento = null;
            if (cuerpo.length > 0) {
                try {
                    documento = parsear(cuerpo);
                } catch (Exception ex) {
                    if (codigoHttp < 400) {
                        throw new DescargaMasivaSatException("Respuesta del SAT no es XML valido.", ex);
                    }
                }
            }
            if (codigoHttp >= 400 || documento == null) {
                String detalle = documento == null ? null : textoDe(buscarElemento(documento.getDocumentElement(), "faultstring"));
                throw new DescargaMasivaSatException("El SAT respondio HTTP " + codigoHttp
                        + (detalle == null ? "" : ": " + detalle), null, detalle, codigoHttp, null);
            }
            return documento;
        } catch (IOException ex) {
            throw new DescargaMasivaSatException("No fue posible comunicarse con el SAT: " + ex.getMessage(), ex);
        } finally {
            if (conexion != null) {
                conexion.disconnect();
            }
        }
    }

    private static byte[] leerTodo(InputStream entrada) throws IOException {
        if (entrada == null) {
            return new byte[0];
        }
        try (InputStream in = entrada) {
            ByteArrayOutputStream salida = new ByteArrayOutputStream();
            byte[] buffer = new byte[16384];
            int leidos;
            while ((leidos = in.read(buffer)) != -1) {
                salida.write(buffer, 0, leidos);
            }
            return salida.toByteArray();
        }
    }

    private static Document parsear(byte[] xml) throws Exception {
        DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
        fabrica.setNamespaceAware(true);
        fabrica.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        fabrica.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        fabrica.setExpandEntityReferences(false);
        return fabrica.newDocumentBuilder().parse(new ByteArrayInputStream(xml));
    }

    /** Busqueda en profundidad por nombre local, sin distinguir mayusculas. */
    private static Element buscarElemento(Element raiz, String nombreLocal) {
        if (raiz == null) {
            return null;
        }
        String nombre = raiz.getLocalName() != null ? raiz.getLocalName() : raiz.getNodeName();
        if (nombreLocal.equalsIgnoreCase(nombre)) {
            return raiz;
        }
        NodeList hijos = raiz.getChildNodes();
        for (int i = 0; i < hijos.getLength(); i++) {
            if (hijos.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element encontrado = buscarElemento((Element) hijos.item(i), nombreLocal);
                if (encontrado != null) {
                    return encontrado;
                }
            }
        }
        return null;
    }

    private static String atributo(Element elemento, String nombre) {
        NamedNodeMap atributos = elemento.getAttributes();
        for (int i = 0; i < atributos.getLength(); i++) {
            Node atributo = atributos.item(i);
            String local = atributo.getLocalName() != null ? atributo.getLocalName() : atributo.getNodeName();
            if (nombre.equalsIgnoreCase(local)) {
                return atributo.getNodeValue();
            }
        }
        return null;
    }

    private static String textoDe(Element elemento) {
        return elemento == null ? null : elemento.getTextContent().trim();
    }

    private static Integer entero(String valor, Integer porDefecto) {
        if (valor == null || valor.trim().isEmpty()) {
            return porDefecto;
        }
        try {
            return Integer.valueOf(valor.trim());
        } catch (NumberFormatException ex) {
            return porDefecto;
        }
    }
}
