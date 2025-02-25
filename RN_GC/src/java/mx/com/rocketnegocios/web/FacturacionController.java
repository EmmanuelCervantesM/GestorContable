package mx.com.rocketnegocios.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import mx.com.rocketnegocios.beans.RnGcCfdisLineasTblFacade;
import mx.com.rocketnegocios.beans.RnGcCfdisTblFacade;
import mx.com.rocketnegocios.beans.RnGcDireccionesTblFacade;
import mx.com.rocketnegocios.beans.RnGcPersonasTblFacade;
import mx.com.rocketnegocios.beans.RnGcTipospersonasTblFacade;
import mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade;
import mx.com.rocketnegocios.entities.RnGcCfdisLineasTbl;
import mx.com.rocketnegocios.entities.RnGcCfdisTbl;
import mx.com.rocketnegocios.entities.RnGcPersonasTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;

@SessionScoped
@ManagedBean(name = "FacturacionController")
public class FacturacionController implements Serializable {

    public FacturacionController() {
    }

    @EJB
    private RnGcCfdisTblFacade cfdisFacade;

    @EJB
    private RnGcCfdisLineasTblFacade lineasFacade;

    @EJB
    private RnGcDireccionesTblFacade direccionesFacade;

    @EJB
    private RnGcTipospersonasTblFacade tiposPersonasFacade;

    @EJB
    private RnGcPersonasTblFacade personasFacade;

    @EJB
    private RnGcUsuariosTblFacade usuariosFacade;

    private final UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private List<RnGcCfdisLineasTbl> cfdisLineas;
    private RnGcCfdisLineasTbl lineas;
    private RnGcCfdisTbl cfdisId;
    private RnGcPersonasTbl personas;
    private RnGcUsuariosTbl usuarioId;

    public RnGcUsuariosTbl getUsuarioId() {
        if (usuarioId == null) {
            this.usuarioId = new RnGcUsuariosTbl();
        }
        return usuarioId;
    }

    public void setUsuarioId(RnGcUsuariosTbl usuarioId) {
        this.usuarioId = usuarioId;
    }

    public RnGcPersonasTbl getPersonas() {
        if (personas == null) {
            this.personas = new RnGcPersonasTbl();
        }
        return personas;
    }

    public void setPersonas(RnGcPersonasTbl personas) {
        this.personas = personas;
    }

    public RnGcCfdisTbl getCfdisId() {
        if (cfdisId == null) {
            this.cfdisId = new RnGcCfdisTbl();
        }
        return cfdisId;
    }

    public void setCfdisId(RnGcCfdisTbl cfdisId) {
        this.cfdisId = cfdisId;
    }

    public RnGcCfdisLineasTbl getLineas() {
        if (lineas == null) {
            this.lineas = new RnGcCfdisLineasTbl();
        }
        return lineas;
    }

    public void setLineas(RnGcCfdisLineasTbl lineas) {
        this.lineas = lineas;
    }

    public List<RnGcCfdisLineasTbl> getCfdisLineas() {
        if (cfdisLineas == null) {
            this.cfdisLineas = new ArrayList<>();
        }
        return cfdisLineas;
    }

    public void setCfdisLineas(List<RnGcCfdisLineasTbl> cfdisLineas) {
        this.cfdisLineas = cfdisLineas;
    }

    public void prepareLineas(RnGcCfdisTbl cfdisId) {
        System.out.println("cfdisId: " + cfdisId);
        if (cfdisId != null) {
            cfdisLineas = lineasFacade.obtenerCfdisLineas(cfdisId);
        }
    }

    public void updateLinea() {
        System.out.println("update()");
        persistLinea(PersistAction.UPDATE);
    }

    public void destroyLinea() {
        System.out.println("destroy()");
        persistLinea(PersistAction.DELETE);
    }

    public void persistLinea(PersistAction persistAction) {
        if (lineas != null) {
            EmbeddableLineas();
            try {
                if (persistAction != PersistAction.DELETE) {
                    lineasFacade.edit(lineas);
                    JsfUtil.addSuccessMessage("Concepto actulizado");
                } else {
                    lineasFacade.remove(lineas);
                    JsfUtil.addSuccessMessage("Concepto eliminado");
                }
            } catch (EJBException ex) {
                JsfUtil.addErrorMessage("Error durante el proceso");
            }
        }
    }

    public void EmbeddableLineas() {
        lineas.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        lineas.setFechaCreacion(new Date());
        lineas.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        lineas.setUltimaFechaActualizacion(new Date());
    }

    public Double calcularImporte(Double cantidad, Double valorUnit) {
        Double importe = 0.0;
        if (cantidad > 0.0 && valorUnit > 0.0) {
            importe = cantidad * valorUnit;
            lineas.setBase(importe);
            lineas.setImporte(importe);
        }
        return importe;
    }

    public Double calcularImporteImpuesto(String porcent, Double cantidad, String valorUnit) {
        Double importeImpuesto = 0.0;
        Double importe = 0.0;
        if (!porcent.isEmpty()) {
            importe = calcularImporte(cantidad, Double.parseDouble(valorUnit));
            importeImpuesto = importe * (Double.parseDouble(porcent) / 100);
        }
        return importeImpuesto;
    }

}
