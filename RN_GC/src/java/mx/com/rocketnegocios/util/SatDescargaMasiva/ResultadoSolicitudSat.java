package mx.com.rocketnegocios.util.SatDescargaMasiva;

/** CTR-06: respuesta de SolicitaDescargaEmitidos / SolicitaDescargaRecibidos. */
public class ResultadoSolicitudSat {

    private final String codEstatus;
    private final String mensaje;
    private final String idSolicitud;

    public ResultadoSolicitudSat(String codEstatus, String mensaje, String idSolicitud) {
        this.codEstatus = codEstatus;
        this.mensaje = mensaje;
        this.idSolicitud = idSolicitud;
    }

    public String getCodEstatus() {
        return codEstatus;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getIdSolicitud() {
        return idSolicitud;
    }
}
