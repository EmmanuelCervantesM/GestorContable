package mx.com.rocketnegocios.beans;

import java.util.List;
import javax.ejb.Local;
import mx.com.rocketnegocios.entities.RnGcDocumentosSatJobTbl;

/**
 * Aviso por correo al terminar un lote de Opinión de cumplimiento /
 * Constancia de situación fiscal.
 *
 * TODO (correo): el aviso sale de la cuenta global de correo (decidido, plan)
 * §9.2 El sistema de envio unificado (CTR-09/16) aun no
 * existe. Mientras tanto la unica implementacion
 * ({@link NotificadorCorreoDocumentosSatLog}) solo registra en el log y los
 * trabajos quedan con correoEnviado = false. Al implementarlo con el envio
 * global, marcar correoEnviado = true solo en los trabajos efectivamente
 * notificados.
 */
@Local
public interface NotificadorCorreoDocumentosSat {

    /**
     * @param usuarioId usuario dueño del lote.
     * @param trabajos trabajos del lote ya terminados (LISTO o ERROR).
     */
    void notificarLoteTerminado(Integer usuarioId, List<RnGcDocumentosSatJobTbl> trabajos);
}
