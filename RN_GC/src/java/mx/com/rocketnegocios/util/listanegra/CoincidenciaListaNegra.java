package mx.com.rocketnegocios.util.listanegra;

import java.io.Serializable;
import mx.com.rocketnegocios.entities.RnGcListaNegraTbl;

/**
 * CTR-12: un RFC encontrado en una lista negra, listo para mostrar: semáforo, lista,
 * estado y las referencias del DOF de cuándo entró y cuándo salió.
 */
public class CoincidenciaListaNegra implements Serializable {

    private static final long serialVersionUID = 1L;

    private final RnGcListaNegraTbl registro;
    private final EnlaceDof entrada;
    private final EnlaceDof salida;
    private Integer idPersona;
    private String tipoPersona;
    private String nombrePersona;

    public CoincidenciaListaNegra(RnGcListaNegraTbl registro, EnlaceDof entrada, EnlaceDof salida) {
        this.registro = registro;
        this.entrada = entrada;
        this.salida = salida;
    }

    public RnGcListaNegraTbl getRegistro() {
        return registro;
    }

    public String getRfc() {
        return registro.getRfc();
    }

    public String getNombreContribuyente() {
        return registro.getNombreContribuyente();
    }

    public String getLista() {
        return registro.getIdTipoLista() != null ? registro.getIdTipoLista().getTipoLista() : null;
    }

    public String getSituacion() {
        return registro.getSituacionContribuyente();
    }

    /** rojo, ambar, verde o gris. */
    public String getColor() {
        String c = SemaforoListaNegra.color(registro.getSituacionContribuyente());
        return c == null ? SemaforoListaNegra.GRIS : c;
    }

    public boolean isVigente() {
        return SemaforoListaNegra.vigente(registro.getSituacionContribuyente());
    }

    public EnlaceDof getEntrada() {
        return entrada;
    }

    public EnlaceDof getSalida() {
        return salida;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public CoincidenciaListaNegra conPersona(Integer idPersona, String tipoPersona, String nombrePersona) {
        this.idPersona = idPersona;
        this.tipoPersona = tipoPersona;
        this.nombrePersona = nombrePersona;
        return this;
    }
}
