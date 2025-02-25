package mx.com.rocketnegocios.web;

import mx.com.rocketnegocios.entities.RnGcTimbresTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcTimbresTblFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;

@Named("rnGcTimbresTblController")
@SessionScoped
public class RnGcTimbresTblController implements Serializable {

    @EJB
    private RnGcUsuariosTblFacade usuarioFacade;

    @EJB
    private mx.com.rocketnegocios.beans.RnGcTimbresTblFacade ejbFacade;
    private List<RnGcTimbresTbl> items = null;
    private RnGcTimbresTbl selected;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private List<RnGcTimbresTbl> itemsUsuarios = null;
    private RnGcUsuariosTbl usuarioId = null;
    private List<RnGcTimbresTbl> timbresList;
    private List<RnGcTimbresTbl> varTimbre;
    private RnGcUsuariosTbl usuarioVar = null;
    private int totalTimbres;

    public RnGcTimbresTblController() {
    }

    public List<RnGcTimbresTbl> getVarTimbre() {
        return varTimbre;
    }

    public void setVarTimbre(List<RnGcTimbresTbl> varTimbre) {
        this.varTimbre = varTimbre;
    }

    public RnGcTimbresTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcTimbresTbl selected) {
        this.selected = selected;
    }

    public int getTotalTimbres() {
        if (totalTimbres < 0) {
            this.totalTimbres = 0;
        }
        return totalTimbres;
    }

    public void setTotalTimbres(int totalTimbres) {
        this.totalTimbres = totalTimbres;
    }

    public RnGcUsuariosTbl getUsuarioId() {
        if (usuarioId == null) {
            this.usuarioId = new RnGcUsuariosTbl();
        }
        return usuarioId;
    }

    public void setUsuarioId(RnGcUsuariosTbl usuarioId) {
        this.usuarioId = usuarioId;
    }

    protected void setEmbeddableKeys() {
        selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        selected.setUltimaFechaActualizacion(new Date());
        selected.setProveedor("Proveedor");
    }

    protected void initializeEmbeddableKey() {
        selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        selected.setFechaCreacion(new Date());
        selected.setProveedor("Proveedor");
    }

    private RnGcTimbresTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcTimbresTbl prepareCreate() {
        selected = new RnGcTimbresTbl();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcTimbresTblCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcTimbresTblUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcTimbresTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcTimbresTbl> getItems() {
        items = getFacade().findAll();
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            System.out.println("totalTimbres: " + totalTimbres);
            setEmbeddableKeys();
            try {
                if (persistAction.equals(PersistAction.CREATE)) {
                    System.out.println("Create");
                    if (usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")) {
                        selected.setTimbresTotal(totalTimbres);
                        selected.setTimbresRestantes(totalTimbres);
                        getFacade().edit(selected);
                        JsfUtil.addSuccessMessage(successMessage);
                    } else {
                        if (asignarTimbres()) {
                            getFacade().edit(selected);
                            JsfUtil.addSuccessMessage(successMessage);
                        } else {
                            JsfUtil.addErrorMessage("Se excedio el número de timbres o no tienes timbres asignados");
                        }
                    }
                }
                if (persistAction.equals(PersistAction.UPDATE)) {
                    System.out.println("Update");
                    if (usuarioFirmado.perfilUsuario().contains("ADMINISTRADOR")) {
                        selected.setTimbresRestantes(totalTimbres + selected.getTimbresRestantes());
                        selected.setTimbresTotal(selected.getTimbresRestantes());
                        getFacade().edit(selected);
                        JsfUtil.addSuccessMessage(successMessage);
                    } else {
                        if (editarTimbres()) {
                            getFacade().edit(selected);
                            JsfUtil.addSuccessMessage(successMessage);
                        } else {
                            JsfUtil.addErrorMessage("Se excedio el número de timbres o no tienes timbres asignados");
                        }
                    }
                }
                if (persistAction.equals(PersistAction.DELETE)) {
                    System.out.println("Delete");
                    getFacade().remove(selected);
                    JsfUtil.addSuccessMessage(successMessage);
                }
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
        totalTimbres = 0;
    }

    public RnGcTimbresTbl getRnGcTimbresTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcTimbresTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcTimbresTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcTimbresTbl.class)
    public static class RnGcTimbresTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcTimbresTblController controller = (RnGcTimbresTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcTimbresTblController");
            return controller.getRnGcTimbresTbl(getKey(value));
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
            if (object instanceof RnGcTimbresTbl) {
                RnGcTimbresTbl o = (RnGcTimbresTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcTimbresTbl.class.getName()});
                return null;
            }
        }
    }

    public List<RnGcTimbresTbl> getItemsUsuarios() {
        this.itemsUsuarios = getFacade().obtenerPorUsuario(usuarioId);
        return itemsUsuarios;
    }

    public void prepareItemsUsuario(RnGcUsuariosTbl usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<String> obtenerTimbres(Integer Id) {
        List<String> timbresUsuario = new ArrayList<>();
        if (Id != null) {
            usuarioVar = usuarioFacade.obtenerUsuarioPorId(Id);
            items = getFacade().obtenerPorUsuario(usuarioVar);
            for (int i = 0; i < items.size(); i++) {
                timbresUsuario.add(String.valueOf(items.get(i).getTimbresRestantes() + " / " + String.valueOf(items.get(i).getTimbresUsados())));
            }
        }
        return timbresUsuario;
    }

    public boolean asignarTimbres() {
        System.out.println("asignarTimbres()");
        long timbresTotal = 0;
        Integer timbresRestantes = 0, contador = 0;
        usuarioVar = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        varTimbre = getFacade().obtenerTimbre("Proveedor", usuarioVar);
        timbresTotal = getFacade().obtenerTotalTimbresActivosXUsuario(selected.getProveedor(), usuarioVar);
        boolean valor = false;
        System.out.println("totalTimbres: " + totalTimbres);
        if (selected != null && selected.getEstado() != null) {
            if (selected.getEstado().equals("Activo")) {
                if (totalTimbres <= timbresTotal) {
                    if (varTimbre != null && !varTimbre.isEmpty()) {
                        for (RnGcTimbresTbl timbre : varTimbre) {
                            if (timbre.getTimbresTotal() <= totalTimbres) {
                                if (contador > 0) {
                                    if (timbresRestantes >= timbre.getTimbresTotal()) {
                                        timbre.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                                        timbre.setUltimaFechaActualizacion(new Date());
                                        timbre.setTimbresRestantes(0);
                                        timbre.setTimbresUsados(timbre.getTimbresTotal());
                                        System.out.println("timbre1: " + timbre + " | " + timbre.getTimbresTotal() + " | " + timbre.getTimbresRestantes() + " | " + timbre.getTimbresUsados());
                                        valor = true;
                                        getFacade().edit(timbre);
                                    } else {
                                        timbre.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                                        timbre.setUltimaFechaActualizacion(new Date());
                                        timbre.setTimbresRestantes(timbre.getTimbresRestantes() - timbresRestantes);
                                        timbre.setTimbresUsados(timbresRestantes);
                                        System.out.println("timbre2: " + timbre + " | " + timbre.getTimbresTotal() + " | " + timbre.getTimbresRestantes() + " | " + timbre.getTimbresUsados());
                                        valor = true;
                                        getFacade().edit(timbre);
                                    }
                                    timbresRestantes = timbresRestantes - timbre.getTimbresTotal();
                                    //System.out.println("timbresRestantes: " + timbresRestantes);
                                    if (timbresRestantes < 0) {
                                        break;
                                    }
                                } else {
                                    timbresRestantes = totalTimbres - timbre.getTimbresTotal();
                                    //System.out.println("timbresRestantes: " + timbresRestantes);
                                    timbre.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                                    timbre.setUltimaFechaActualizacion(new Date());
                                    timbre.setTimbresRestantes(0);
                                    timbre.setTimbresUsados(timbre.getTimbresTotal());
                                    System.out.println("timbre3: " + timbre + " | " + timbre.getTimbresTotal() + " | " + timbre.getTimbresRestantes() + " | " + timbre.getTimbresUsados());
                                    valor = true;
                                    getFacade().edit(timbre);
                                }
                                contador++;
                            } else {
                                //System.out.println("else1: " + timbre + " | " + timbre.getTimbresRestantes() + " | " + timbre.getTimbresUsados());
                                timbre.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                                timbre.setUltimaFechaActualizacion(new Date());
                                timbre.setTimbresRestantes(timbre.getTimbresRestantes() - totalTimbres);
                                timbre.setTimbresUsados(timbre.getTimbresUsados() + totalTimbres);
                                System.out.println("else2: " + timbre + " | " + timbre.getTimbresRestantes() + " | " + timbre.getTimbresUsados());
                                valor = true;
                                getFacade().edit(timbre);
                                break;
                            }
                        }
                        selected.setTimbresRestantes(totalTimbres);
                        selected.setTimbresUsados(0);
                        selected.setTimbresTotal(totalTimbres);
                        System.out.println("selected: " + selected.getTimbresRestantes() + " | " + selected.getTimbresTotal() + " | " + selected.getTimbresUsados());
                        //valor = true;
                    }
                }
            } else {
                selected.setTimbresRestantes(totalTimbres);
                selected.setTimbresUsados(0);
                selected.setTimbresTotal(totalTimbres);
                System.out.println("selected2: " + selected.getTimbresRestantes() + " | " + selected.getTimbresTotal() + " | " + selected.getTimbresUsados());
                valor = true;
            }
        }
        System.out.println("Valor: " + valor);
        return valor;
    }

    public boolean editarTimbres() {
        System.out.println("editarTimbres()");
        long timbresrRstantes = 0, timbresTotal = 0;
        boolean valor = false;
        Integer timbresRestantes = 0, contador = 0;
        usuarioVar = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        varTimbre = getFacade().obtenerTimbre(selected.getProveedor(), usuarioVar);
        timbresrRstantes = getFacade().obtenerTotalTimbresActivosXUsuario(selected.getProveedor(), usuarioVar);
        System.out.println("selected.getTimbresRestantes(): " + timbresrRstantes + " | " + selected.getTimbresRestantes());
        if (selected.getEstado().equals("Activo")) {
            System.out.println("validacion: " + (timbresrRstantes < selected.getTimbresRestantes()));
            if (timbresrRstantes < selected.getTimbresRestantes()) {

            } else {
                if (varTimbre != null && !varTimbre.isEmpty()) {
                    for (RnGcTimbresTbl timbre : varTimbre) {
                        if (timbre.getTimbresTotal() <= selected.getTimbresRestantes()) {
                            if (contador > 0) {
                                if (timbresRestantes >= timbre.getTimbresTotal()) {
                                    timbre.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                                    timbre.setUltimaFechaActualizacion(new Date());
                                    timbre.setTimbresRestantes(0);
                                    timbre.setTimbresUsados(timbre.getTimbresTotal());
                                    valor = true;
                                    System.out.println("timbre1: " + timbre);
                                    System.out.println("timbreTotal1: " + timbre.getTimbresTotal());
                                    System.out.println("timbreRestantes1: " + timbre.getTimbresRestantes());
                                    System.out.println("timbreUsaados1: " + timbre.getTimbresUsados());
                                    getFacade().edit(timbre);
                                } else {
                                    timbre.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                                    timbre.setUltimaFechaActualizacion(new Date());
                                    timbre.setTimbresUsados(timbresRestantes);
                                    timbre.setTimbresRestantes(timbre.getTimbresTotal() - timbre.getTimbresUsados());
                                    valor = true;
                                    System.out.println("timbre2: " + timbre);
                                    System.out.println("timbreTotal2: " + timbre.getTimbresTotal());
                                    System.out.println("timbreRestantes2: " + timbre.getTimbresRestantes());
                                    System.out.println("timbreUsaados2: " + timbre.getTimbresUsados());
                                    getFacade().edit(timbre);
                                }
                                timbresRestantes = timbresRestantes - timbre.getTimbresTotal();
                                //System.out.println("timbresRestantes: " + timbresRestantes);
                                if (timbresRestantes < 0) {
                                    break;
                                }
                            } else {
                                timbresRestantes = selected.getTimbresRestantes() - timbre.getTimbresTotal();
                                //System.out.println("timbresRestantes: " + timbresRestantes);
                                timbre.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                                timbre.setUltimaFechaActualizacion(new Date());
                                timbre.setTimbresRestantes(0);
                                timbre.setTimbresUsados(timbre.getTimbresTotal());
                                valor = true;
                                System.out.println("timbre3: " + timbre);
                                System.out.println("timbreTotal3: " + timbre.getTimbresTotal());
                                System.out.println("timbreRestantes3: " + timbre.getTimbresRestantes());
                                System.out.println("timbreUsaados3: " + timbre.getTimbresUsados());
                                getFacade().edit(timbre);
                            }
                            contador++;
                        } else {
                            //System.out.println("else1: " + timbre + " | " + timbre.getTimbresRestantes() + " | " + timbre.getTimbresUsados());
                            timbre.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                            timbre.setUltimaFechaActualizacion(new Date());
                            timbre.setTimbresRestantes(timbre.getTimbresRestantes() - selected.getTimbresRestantes());
                            timbre.setTimbresUsados(timbre.getTimbresUsados() + selected.getTimbresRestantes());
                            valor = true;
                            System.out.println("timbre4: " + timbre);
                            System.out.println("timbreTotal4: " + timbre.getTimbresTotal());
                            System.out.println("timbreRestantes4: " + timbre.getTimbresRestantes());
                            System.out.println("timbreUsaados4: " + timbre.getTimbresUsados());
                            getFacade().edit(timbre);
                            break;
                        }
                    }
                }
            }
        } else {
            System.out.println("selected.getEstado():  " + selected.getEstado());
            Integer timbresDevueltos = selected.getTimbresRestantes(), contador1 = 0, contador2 = 0;
            usuarioVar = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            varTimbre = getFacade().listaTimbresUsuario(usuarioVar);
            timbresrRstantes = getFacade().obtenerTotalTimbresActivosXUsuario(selected.getProveedor(), usuarioVar);
            timbresTotal = getFacade().obtenerTotalTimbresTotalesXUsuario(selected.getProveedor(), usuarioVar);
            System.out.println("timbresrRstantes1: " + timbresrRstantes + " ! timbresTotal1: " + timbresTotal);
            timbresrRstantes = timbresrRstantes + selected.getTimbresRestantes();
            System.out.println("timbresrRstantes2: " + timbresrRstantes + " ! timbresTotal2: " + timbresTotal);
            if (timbresrRstantes <= timbresTotal) {
                System.out.println("****************************** Se regresan los timbres al administrador ******************************");
                if (varTimbre != null && !varTimbre.isEmpty()) {
                    timbresDevueltos = selected.getTimbresRestantes();
                    while (contador1 < varTimbre.size()) {
                        if (timbresDevueltos > 0) {
                            System.out.println("timbre" + contador1 + ": " + varTimbre.get(contador1) + " | " + varTimbre.get(contador1).getTimbresRestantes()
                                    + " | " + varTimbre.get(contador1).getTimbresTotal());
                            contador2 = varTimbre.get(contador1).getTimbresRestantes();
                            for (int i = contador2; i < varTimbre.get(contador1).getTimbresTotal(); i++) {
                                if (timbresDevueltos > 0) {
                                    varTimbre.get(contador1).setTimbresRestantes(varTimbre.get(contador1).getTimbresRestantes() + 1);
                                    varTimbre.get(contador1).setTimbresUsados(varTimbre.get(contador1).getTimbresUsados() - 1);
                                    timbresDevueltos--;
                                }
                            }
                            System.out.println("timbreTotal" + contador1 + ": " + varTimbre.get(contador1).getTimbresTotal());
                            System.out.println("timbreRestante" + contador1 + ": " + varTimbre.get(contador1).getTimbresRestantes());
                            System.out.println("timbreUsados" + contador1 + ": " + varTimbre.get(contador1).getTimbresUsados());//*/
                            System.out.println("contador1: " + contador1 + " | timbresRestantesPorDevolver: " + timbresDevueltos);
                            System.out.println("*****************************************************************************************************");
                            varTimbre.get(contador1).setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                            varTimbre.get(contador1).setUltimaFechaActualizacion(new Date());
                            getFacade().edit(varTimbre.get(contador1));
                            contador1++;
                            valor = true;
                        }
                    }
                }
            }
        }
        System.out.println("valor: " + valor);
        return valor;
    }

}
