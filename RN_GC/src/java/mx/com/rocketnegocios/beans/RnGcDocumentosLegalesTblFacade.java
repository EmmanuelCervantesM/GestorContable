package mx.com.rocketnegocios.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcDocumentosLegalesTbl;

// Facade para gestionar las versiones de Términos y Condiciones / Aviso de Privacidad.
// Extiende AbstractFacade para heredar create/edit/remove/find/findAll.
@Stateless
public class RnGcDocumentosLegalesTblFacade extends AbstractFacade<RnGcDocumentosLegalesTbl> {
    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    public RnGcDocumentosLegalesTblFacade() {
        super(RnGcDocumentosLegalesTbl.class);
    }
    // Regresa la versión actualmente vigente de un tipo de documento ('TERMINOS' o 'PRIVACIDAD')
    public RnGcDocumentosLegalesTbl obtenerVigentePorTipo(String tipoDocumento) {
        try {
            return em.createNamedQuery("RnGcDocumentosLegalesTbl.findVigentePorTipo", RnGcDocumentosLegalesTbl.class)
                    .setParameter("tipoDocumento", tipoDocumento)
                    .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("No se encontró un documento legal vigente para el tipo: " + tipoDocumento);
            return null;
        }
    }
    // Al publicar una versión nueva, las anteriores del mismo tipo dejan de ser vigentes
    public void marcarComoNoVigentes(String tipoDocumento) {
        em.createQuery("UPDATE RnGcDocumentosLegalesTbl r SET r.vigente = 'N' WHERE r.tipoDocumento = :tipoDocumento AND r.vigente = 'S'")
          .setParameter("tipoDocumento", tipoDocumento)
          .executeUpdate();
    }
}
