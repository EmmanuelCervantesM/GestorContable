package mx.com.rocketnegocios.util.SatDescargaMasiva;

/**
 * CTR-06: error al consumir el web service de Descarga Masiva del SAT. Cuando
 * el SAT responde con un CodEstatus distinto de exito, se conserva el codigo y
 * el mensaje originales para mostrarlos/guardarlos tal cual.
 */
public class DescargaMasivaSatException extends Exception {

    private static final long serialVersionUID = 1L;

    private final String codEstatus;
    private final String mensajeSat;
    private final int httpStatus;

    public DescargaMasivaSatException(String mensaje) {
        this(mensaje, null, null, 0, null);
    }

    public DescargaMasivaSatException(String mensaje, Throwable causa) {
        this(mensaje, null, null, 0, causa);
    }

    public DescargaMasivaSatException(String mensaje, String codEstatus, String mensajeSat, int httpStatus, Throwable causa) {
        super(mensaje, causa);
        this.codEstatus = codEstatus;
        this.mensajeSat = mensajeSat;
        this.httpStatus = httpStatus;
    }

    public String getCodEstatus() {
        return codEstatus;
    }

    public String getMensajeSat() {
        return mensajeSat;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}
