package mx.com.rocketnegocios.util.SatDescargaMasiva;

import java.util.Collections;
import java.util.List;

/** CTR-06: respuesta de VerificaSolicitudDescarga. */
public class ResultadoVerificacionSat {

    private final String codEstatus;
    private final String mensaje;
    private final int estadoSolicitud;
    private final Integer codigoEstadoSolicitud;
    private final int numeroCfdis;
    private final List<String> idsPaquetes;

    public ResultadoVerificacionSat(String codEstatus, String mensaje, int estadoSolicitud,
            Integer codigoEstadoSolicitud, int numeroCfdis, List<String> idsPaquetes) {
        this.codEstatus = codEstatus;
        this.mensaje = mensaje;
        this.estadoSolicitud = estadoSolicitud;
        this.codigoEstadoSolicitud = codigoEstadoSolicitud;
        this.numeroCfdis = numeroCfdis;
        this.idsPaquetes = idsPaquetes == null ? Collections.<String>emptyList() : Collections.unmodifiableList(idsPaquetes);
    }

    public String getCodEstatus() {
        return codEstatus;
    }

    public String getMensaje() {
        return mensaje;
    }

    /** 1 Aceptada, 2 En proceso, 3 Terminada, 4 Error, 5 Rechazada, 6 Vencida. */
    public int getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public Integer getCodigoEstadoSolicitud() {
        return codigoEstadoSolicitud;
    }

    public int getNumeroCfdis() {
        return numeroCfdis;
    }

    public List<String> getIdsPaquetes() {
        return idsPaquetes;
    }
}
