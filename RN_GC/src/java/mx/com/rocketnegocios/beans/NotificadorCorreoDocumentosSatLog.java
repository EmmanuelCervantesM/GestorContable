package mx.com.rocketnegocios.beans;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import mx.com.rocketnegocios.entities.RnGcDocumentosSatJobTbl;

/**
 * Implementacion provisional: no envia correo, solo deja constancia en el log.
 * TODO(correo): reemplazar por el envio real con la cuenta global cuando se
 * implemente el sistema de correo unificado (CTR-09/16).
 */
@Stateless
public class NotificadorCorreoDocumentosSatLog implements NotificadorCorreoDocumentosSat {

    private static final Logger LOG = Logger.getLogger(NotificadorCorreoDocumentosSatLog.class.getName());

    @Override
    public void notificarLoteTerminado(Integer usuarioId, List<RnGcDocumentosSatJobTbl> trabajos) {
        LOG.log(Level.INFO, "Documentos SAT: lote terminado para el usuario {0} ({1} documento(s)); aviso por correo pendiente de implementar el envio global (CTR-09/16).",
                new Object[]{usuarioId, trabajos == null ? 0 : trabajos.size()});
    }
}
