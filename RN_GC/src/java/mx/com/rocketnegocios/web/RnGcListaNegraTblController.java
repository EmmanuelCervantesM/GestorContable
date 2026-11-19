package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcListaNegraRevisionTbl;
import mx.com.rocketnegocios.entities.RnGcListaNegraTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcListaNegraTblFacade;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import mx.com.rocketnegocios.beans.RnGcPersonasTblFacade;
import mx.com.rocketnegocios.entities.RnGcPersonasTbl;
import mx.com.rocketnegocios.entities.personaListaNegra;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import org.primefaces.event.FileUploadEvent;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import mx.com.rocketnegocios.beans.ActualizadorListasNegras;
import mx.com.rocketnegocios.beans.ListaNegraService;
import mx.com.rocketnegocios.entities.RnGcListaNegraAvisoTbl;
import mx.com.rocketnegocios.util.listanegra.CoincidenciaListaNegra;
import mx.com.rocketnegocios.util.listanegra.ListaNegraCsv;
import mx.com.rocketnegocios.util.listanegra.TipoListaNegra;

@Named("rnGcListaNegraTblController")
@SessionScoped
public class RnGcListaNegraTblController implements Serializable {

    @EJB
    private mx.com.rocketnegocios.beans.RnGcListaNegraTblFacade ejbFacade;

    @EJB
    private RnGcPersonasTblFacade personasFacade;

    @EJB
    private ListaNegraService listaNegraService;
    @EJB
    private ActualizadorListasNegras actualizadorListasNegras;
    private List<RnGcListaNegraRevisionTbl> revisiones = null;

    // CTR-12: carga de CSV del SAT, revisión del DOF (49 Bis X), cruce y avisos
    private String tipoImportar = TipoListaNegra.LISTA_69B.getEtiqueta();
    private Date fecha49BisX;
    private List<CoincidenciaListaNegra> coincidenciasUsuario = null;
    private List<RnGcListaNegraAvisoTbl> avisos = null;

    private List<RnGcListaNegraTbl> items = null;
    private RnGcListaNegraTbl selected;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private List<personaListaNegra> listaPersonaLN = null;
    private personaListaNegra personaLN = new personaListaNegra();
    private String nombre;
    private String rfc;
    private List<RnGcListaNegraTbl> busqeudaManual = null;
    private RnGcListaNegraTbl selectedManual;

    public RnGcListaNegraTblController() {
    }

    public RnGcListaNegraTbl getSelectedManual() {
        return selectedManual;
    }

    public void setSelectedManual(RnGcListaNegraTbl selectedManual) {
        this.selectedManual = selectedManual;
    }

    public String getNombre() {
        return nombre;
    }

    public List<RnGcListaNegraTbl> getBusqeudaManual() {
        if(busqeudaManual == null)
            busqeudaManual = new ArrayList<>();
        return busqeudaManual;
    }

    public void setBusqeudaManual(List<RnGcListaNegraTbl> busqeudaManual) {
        this.busqeudaManual = busqeudaManual;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public RnGcListaNegraTbl getSelected() {
        return selected;
    }

    public personaListaNegra getPersonaLN() {
        return personaLN;
    }

    public void setPersonaLN(personaListaNegra personaLN) {
        this.personaLN = personaLN;
    }

    public List<personaListaNegra> getListaPersonaLN() {
        List<RnGcPersonasTbl> listaPersonas = new ArrayList<>();
        List<RnGcListaNegraTbl> listaListaNegra = new ArrayList<>();
        List<RnGcListaNegraTbl> listaNegra = new ArrayList<>();
        personaListaNegra personaLN = new personaListaNegra();
        listaPersonaLN = new ArrayList<>();

        listaPersonas = personasFacade.obtenerCreadoPor(usuarioFirmado.obtenerIdUsuario());
        for (RnGcPersonasTbl persona : listaPersonas) {
            listaNegra = getFacade().obtenerXRfc(persona.getRfc());
            if (listaNegra != null && !listaNegra.isEmpty()) {
                listaListaNegra.add(listaNegra.get(0));
                personaLN.setPersona(persona);
                personaLN.setListaNegra(listaNegra.get(0));
                listaPersonaLN.add(personaLN);
                listaNegra = new ArrayList<>();
                personaLN = new personaListaNegra();
            }else{
                listaNegra = new ArrayList<>();
                personaLN = new personaListaNegra();
            }
        }
        return listaPersonaLN;
    }

    public void setListaPersonaLN(List<personaListaNegra> listaPersonaLN) {
        this.listaPersonaLN = listaPersonaLN;
    }

    public void setSelected(RnGcListaNegraTbl selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        selected.setUltimaFechaActualizacion(new Date());
    }

    protected void initializeEmbeddableKey() {
        selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        selected.setFechaCreacion(new Date());
        selected.setEstado("A");
    }

    private RnGcListaNegraTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcListaNegraTbl prepareCreate() {
        selected = new RnGcListaNegraTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        if (!soloAdministrador()) {
            return;
        }
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcListaNegraTblCreated"));
        FacesMessage.Severity max = FacesContext.getCurrentInstance().getMaximumSeverity();
        boolean ok = max == null || max.compareTo(FacesMessage.SEVERITY_ERROR) < 0;
        if (!JsfUtil.isValidationFailed() && ok) {
            items = null;    // Invalida la lista de items para forzar un re-query.
            // CTR-12.1: persist() hace merge sin recuperar el nuevo id, por eso un segundo
            // submit (doble clic) del mismo objeto insertaría una fila duplicada.
            selected = null;
        }
    }

    public void update() {
        if (!soloAdministrador()) {
            return;
        }
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcListaNegraTblUpdated"));
    }

    public void destroy() {
        if (!soloAdministrador()) {
            return;
        }
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcListaNegraTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcListaNegraTbl> getItems() {
        // CTR-12: solo registros activos; las cargas viejas y los RFC que salieron quedan inactivos
        if (items == null) {
            items = getFacade().obtenerActivos();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public RnGcListaNegraTbl getRnGcListaNegraTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcListaNegraTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcListaNegraTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcListaNegraTbl.class)
    public static class RnGcListaNegraTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcListaNegraTblController controller = (RnGcListaNegraTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcListaNegraTblController");
            return controller.getRnGcListaNegraTbl(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof RnGcListaNegraTbl) {
                RnGcListaNegraTbl o = (RnGcListaNegraTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcListaNegraTbl.class.getName()});
                return null;
            }
        }
    }

    public List<RnGcListaNegraTbl> busqueda() {
        //System.out.println("Validaciones: " + (rfc == null) + " | " + rfc.isEmpty() + " | " + (nombre == null) + " | " + (nombre.isEmpty()));
        if(rfc != null && !rfc.isEmpty()){
            System.out.println("Busqueda por RFC");
            busqeudaManual = getFacade().obtenerXRfc(rfc);
            rfc = "";
        }else if(nombre != null && !nombre.isEmpty()){
            System.out.println("Busqueda por Nombre");
            busqeudaManual = getFacade().obtenerXNombre(nombre);
            nombre = "";
        }else if(rfc.isEmpty() && nombre.isEmpty()){
            busqeudaManual = new ArrayList<>();
        }
        return busqeudaManual;
    }


    // ------------------------------------------------------------------ CTR-12

    public String getTipoImportar() {
        return tipoImportar;
    }

    public void setTipoImportar(String tipoImportar) {
        this.tipoImportar = tipoImportar;
    }

    public Date getFecha49BisX() {
        return fecha49BisX;
    }

    public void setFecha49BisX(Date fecha49BisX) {
        this.fecha49BisX = fecha49BisX;
    }

    /** Listas que se cargan desde el CSV del SAT. */
    public List<String> getTiposCsv() {
        return Arrays.asList(TipoListaNegra.LISTA_69B.getEtiqueta(), TipoListaNegra.LISTA_69B_BIS.getEtiqueta());
    }

    /** Últimas revisiones de la actualización automática (solo administrador). */
    public List<RnGcListaNegraRevisionTbl> getRevisiones() {
        if (revisiones == null) {
            revisiones = listaNegraService.revisionesRecientes(200);
        }
        return revisiones;
    }

    public void refrescarRevisiones() {
        revisiones = null;
    }

    /** Corre en segundo plano la revisión de los CSV, del DOF y el cruce con CFDI. */
    public void revisarAhora() {
        if (!soloAdministrador()) {
            return;
        }
        actualizadorListasNegras.revisarTodo();
        revisiones = null;
        JsfUtil.addSuccessMessage("Se inició la revisión en segundo plano. Pulse \"Actualizar tabla\" en unos minutos para ver el resultado.");
    }

    /** Carga el CSV del SAT de la lista elegida (69-B o 69-B Bis). */
    public void importarCsv(FileUploadEvent event) {
        if (!soloAdministrador()) {
            return;
        }
        TipoListaNegra tipo = TipoListaNegra.porEtiqueta(tipoImportar);
        if (tipo == null || tipo.getColumnasCsv() == 0) {
            JsfUtil.addErrorMessage("Elija la lista 69-B o 69-B Bis antes de cargar el archivo.");
            return;
        }
        ListaNegraCsv.Resultado lectura;
        try (InputStream in = event.getFile().getInputstream()) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[8192];
            int n;
            while ((n = in.read(buf)) != -1) {
                out.write(buf, 0, n);
            }
            lectura = ListaNegraCsv.leer(out.toByteArray(), tipo);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CTR-12: error al leer el CSV", ex);
            JsfUtil.addErrorMessage("No se pudo leer el archivo: " + ex.getLocalizedMessage());
            return;
        }
        if (lectura.getError() != null) {
            JsfUtil.addErrorMessage(lectura.getError());
            return;
        }
        if (lectura.getFilas().isEmpty()) {
            JsfUtil.addErrorMessage("El archivo no trae registros con RFC válido; no se cargó nada.");
            return;
        }
        ListaNegraService.Resumen r;
        try {
            String folio = "CSV " + tipo.getEtiqueta() + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            r = listaNegraService.importar(tipo, lectura.getFilas(), usuarioFirmado.obtenerIdUsuario(), folio, new Date(), true);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CTR-12: error al cargar el CSV", ex);
            JsfUtil.addErrorMessage("La carga falló y no se guardó nada: " + ex.getLocalizedMessage());
            return;
        }
        items = null;
        coincidenciasUsuario = null;
        avisos = null;
        JsfUtil.addSuccessMessage("Lista " + tipo.getEtiqueta() + " cargada: " + lectura.getFilas().size() + " RFC ("
                + r.getNuevos() + " nuevos, " + r.getActualizados() + " actualizados, " + r.getSinCambios()
                + " sin cambios, " + r.getCambiosEstado()
                + " con cambio de estado, " + r.getBajas() + " ya no aparecen). Avisos generados: " + r.getAvisos() + ".");
        if (!lectura.getErrores().isEmpty()) {
            JsfUtil.addWarningMessage(lectura.getErrores().size() + " registros del archivo no se cargaron. "
                    + resumirLista(lectura.getErrores()));
        }
        if (!lectura.getRepetidos().isEmpty()) {
            JsfUtil.addWarningMessage(lectura.getRepetidos().size() + " RFC venían repetidos; se tomó el episodio más reciente. "
                    + resumirLista(lectura.getRepetidos()));
        }
    }

    /** Revisa las ediciones MAT y VES de la fecha elegida y carga los oficios de 49 Bis fracción X. */
    public void revisar49BisX() {
        if (!soloAdministrador()) {
            return;
        }
        if (fecha49BisX == null) {
            JsfUtil.addErrorMessage("Elija la fecha del DOF a revisar.");
            return;
        }
        ListaNegraService.Resumen49BisX r;
        try {
            r = listaNegraService.importar49BisX(fecha49BisX, usuarioFirmado.obtenerIdUsuario());
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CTR-12: error al revisar el DOF", ex);
            JsfUtil.addErrorMessage("La revisión del DOF falló y no se guardó nada: " + ex.getLocalizedMessage());
            return;
        }
        items = null;
        coincidenciasUsuario = null;
        avisos = null;
        String fecha = new SimpleDateFormat("dd/MM/yyyy").format(fecha49BisX);
        if (r.getCargados().isEmpty() && r.getOmitidos().isEmpty() && r.getErrores().isEmpty()) {
            JsfUtil.addSuccessMessage("Sin oficios 49 Bis X en las ediciones matutina y vespertina del DOF del " + fecha + ".");
        }
        if (!r.getCargados().isEmpty()) {
            JsfUtil.addSuccessMessage("DOF del " + fecha + ": " + r.getRegistros() + " contribuyentes cargados. "
                    + String.join(" ", r.getCargados()) + " Avisos generados: " + r.getAvisos() + ".");
        }
        if (!r.getOmitidos().isEmpty()) {
            JsfUtil.addWarningMessage(String.join(" ", r.getOmitidos()));
        }
        for (String e : r.getErrores()) {
            JsfUtil.addErrorMessage(e);
        }
    }

    private static String resumirLista(List<String> lineas) {
        int max = 5;
        String muestra = String.join(" ", lineas.subList(0, Math.min(max, lineas.size())));
        return lineas.size() > max ? muestra + " (y " + (lineas.size() - max) + " más)" : muestra;
    }

    /**
     * Clientes y proveedores del usuario (y, si es despacho, de sus contribuyentes) que están
     * en alguna lista, con semáforo y ligas al DOF.
     */
    public List<CoincidenciaListaNegra> getCoincidenciasUsuario() {
        if (coincidenciasUsuario == null) {
            coincidenciasUsuario = listaNegraService.coincidenciasDeUsuarios(usuarioFirmado.obtenerIdsAlcance());
        }
        return coincidenciasUsuario;
    }

    public void refrescarCoincidencias() {
        coincidenciasUsuario = null;
        avisos = null;
    }

    /**
     * El controlador es de sesión: sin esto, al volver a Comparación se veían las
     * coincidencias y avisos de la primera visita, aunque una carga posterior los cambiara.
     */
    public void alEntrarComparacion() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            refrescarCoincidencias();
        }
    }

    /** Avisos sin ver: clientes o proveedores que entraron, cambiaron de estado o salieron de una lista. */
    public List<RnGcListaNegraAvisoTbl> getAvisos() {
        if (avisos == null) {
            avisos = listaNegraService.avisosNoVistos(usuarioFirmado.obtenerIdUsuario());
        }
        return avisos;
    }

    public void marcarAvisosVistos() {
        listaNegraService.marcarAvisosVistos(usuarioFirmado.obtenerIdUsuario());
        avisos = null;
    }

    public String colorDe(String situacion) {
        String c = mx.com.rocketnegocios.util.listanegra.SemaforoListaNegra.color(situacion);
        return c == null ? "gris" : c;
    }


    /** CTR-12: cambiar las listas negras es solo del administrador (además del AuthorizationFilter). */
    private boolean soloAdministrador() {
        if (new UsuarioFirmado().esAdministrador()) {
            return true;
        }
        JsfUtil.addErrorMessage("Solo el administrador puede modificar las listas negras.");
        return false;
    }

}
