/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle; 
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import mx.com.rocketnegocios.beans.RnGcPeriodosTblFacade;
import mx.com.rocketnegocios.entities.RnGcPeriodicidadTbl;
import mx.com.rocketnegocios.entities.RnGcPeriodosTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Aaron A Morales Hdez
 */
@Named("rnGcPeriodoContableTblController")
@SessionScoped
public class RnGcPeriodoContableTblController implements Serializable {
    
    private List<RnGcPeriodosTbl> items;
    private RnGcPeriodosTbl selected;
    
    @EJB
    private mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade usuarioFacade;

    @EJB
    private RnGcPeriodosTblFacade ejbFacade;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    
    @Inject
    private FacturarController facturarController;
    
    private Date finMin;
    private Date finMax;

    private static final String[] MESES_ES = {
        "ENERO","FEBRERO","MARZO","ABRIL","MAYO","JUNIO",
        "JULIO","AGOSTO","SEPTIEMBRE","OCTUBRE","NOVIEMBRE","DICIEMBRE"
    };
    
    private Date periodoMes;
    public Date getPeriodoMes() { return periodoMes; }
    public void setPeriodoMes(Date periodoMes) { this.periodoMes = periodoMes; }

    public void onPeriodoSelectNoArg() { aplicarPeriodoMes(); }
    
    private static final String ICON_OPEN   = "ui-icon-unlocked";
    private static final String ICON_CLOSED = "ui-icon-locked";
    
    private boolean modoSeleccionLibre;
    private boolean hayPeriodoAbierto;
    private Integer mesSeleccionado;
    private Integer anioSeleccionado;
    private String mesPropuestoNombre;
    private String periodoPropuesto;
    private List<Integer> aniosDisponibles;


    public void prepareCreate() {
        selected = new RnGcPeriodosTbl();

        // Buscar el último periodo del usuario (más reciente)
        RnGcUsuariosTbl user = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        RnGcPeriodosTbl ultimo = ejbFacade.buscarUltimoPeriodoPorUsuario(user);

        if (ultimo == null) {
            // NO hay periodos: elección libre
            modoSeleccionLibre = true;
            Calendar now = Calendar.getInstance();
            anioSeleccionado = clamp(now.get(Calendar.YEAR), 2020, 2050);
            mesSeleccionado  = now.get(Calendar.MONTH) + 1;
        } else {
            // PROPONER MES SIGUIENTE AL ÚLTIMO REGISTRADO (bloqueado)
            modoSeleccionLibre = false;

            Calendar base = Calendar.getInstance();
            base.setTime(ultimo.getFechaFinPeriodo());
            base.add(Calendar.DAY_OF_MONTH, 1);              // 01 del siguiente mes

            anioSeleccionado = base.get(Calendar.YEAR);
            mesSeleccionado  = base.get(Calendar.MONTH) + 1;
        }
        // Calcula fechas + textos y se los pone a "selected"
        onCambioMesAnio();
    }

    public void habilitarEdicionPeriodo() { modoSeleccionLibre = true; }

    // Recalcula fechas y textos al cambiar mes/año (o al preparar)
    public void onCambioMesAnio() {
        // Texto "MM/yyyy"
        periodoPropuesto = String.format("%02d/%d", mesSeleccionado, anioSeleccionado);
        mesPropuestoNombre = nombreMes(mesSeleccionado);

        // 01/MM/AAAA
        Calendar ini = Calendar.getInstance();
        ini.clear();
        ini.set(anioSeleccionado, mesSeleccionado - 1, 1, 0, 0, 0);

        // último día del mes
        Calendar fin = (Calendar) ini.clone();
        fin.set(Calendar.DAY_OF_MONTH, fin.getActualMaximum(Calendar.DAY_OF_MONTH));

        selected.setFechaInicioPeriodo(ini.getTime());
        selected.setFechaFinPeriodo(fin.getTime());

        // rellena metadatos/auxiliares si los usas
        selected.setAnio(anioSeleccionado);
        selected.setMes(String.valueOf(mesSeleccionado));
    }

    // Utilidad
    private static int clamp(int v, int min, int max){ return Math.max(min, Math.min(max, v)); }
    private static String nombreMes(int m){
        final String[] ES = {"ENERO","FEBRERO","MARZO","ABRIL","MAYO","JUNIO","JULIO",
                             "AGOSTO","SEPTIEMBRE","OCTUBRE","NOVIEMBRE","DICIEMBRE"};
        return ES[Math.max(1, m)-1];
    }

    // Getters usados por la vista
    public boolean isModoSeleccionLibre(){ return modoSeleccionLibre; }
    public Integer getMesSeleccionado(){ return mesSeleccionado; }
    public void setMesSeleccionado(Integer m){ this.mesSeleccionado = m; }
    public Integer getAnioSeleccionado(){ return anioSeleccionado; }
    public void setAnioSeleccionado(Integer a){ this.anioSeleccionado = a; }
    public String getMesPropuestoNombre(){ return mesPropuestoNombre; }
    public String getPeriodoPropuesto(){ return periodoPropuesto; }
    public List<Integer> getAniosDisponibles(){ return aniosDisponibles; }

    public String iconoEstatus(RnGcPeriodosTbl p) {
        return (p != null && "A".equals(p.getEstatus())) ? ICON_OPEN : ICON_CLOSED;
    }

    public String labelEstatus(RnGcPeriodosTbl p) {
        return (p != null && "A".equals(p.getEstatus())) ? "Periodo abierto" : "Periodo cerrado";
    }

    public String styleEstatus(RnGcPeriodosTbl p) {
        // Dale color al botón según el estado
        return (p != null && "A".equals(p.getEstatus()))
                ? "ui-button-success"   // abierto -> verde
                : "ui-button-secondary"; // cerrado -> gris (o usa ui-button-danger si quieres rojo)
    }
    

    // (opcional) asegurar que el año quede dentro del rango
    private int clampAnio(int anio) {
        return Math.max(2020, Math.min(2050, anio));
    }
    
    private Integer mesPropuesto; // 1..12

    
 
    private void aplicarPeriodoMes() {
        if (periodoMes == null) return;
        if (selected == null) selected = new RnGcPeriodosTbl();

        Calendar cal = Calendar.getInstance();
        cal.setTime(periodoMes);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        // 1er día del mes
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date inicio = cal.getTime();

        // último día del mes
        Calendar fin = (Calendar) cal.clone();
        fin.set(Calendar.DAY_OF_MONTH, fin.getActualMaximum(Calendar.DAY_OF_MONTH));

        selected.setFechaInicioPeriodo(inicio);
        selected.setFechaFinPeriodo(fin.getTime());
        selected.setAnio(cal.get(Calendar.YEAR));
        selected.setMes(String.format("%02d", cal.get(Calendar.MONTH) + 1));
    }
    
    public void toggleEstatusSelected() {
        facturarController.notificarCambio();
        if (selected == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención", "No hay registro seleccionado."));
            return;
        }

        // alterna A <-> C
        String nuevo = "A".equals(selected.getEstatus()) ? "C" : "A";
        selected.setEstatus(nuevo);
        selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        selected.setUltimaFechaActualizacion(new Date());

        ejbFacade.edit(selected);   // persiste en BD
        items = null;               // recarga la tabla

        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Listo",
                ("A".equals(nuevo) ? "Periodo reabierto" : "Periodo cerrado")));
    }


    public String descripcion(RnGcPeriodosTbl p) {
        if (p == null || p.getFechaInicioPeriodo() == null) return "";
        Calendar c = Calendar.getInstance();
        c.setTime(p.getFechaInicioPeriodo());
        return MESES_ES[c.get(Calendar.MONTH)] + " " + c.get(Calendar.YEAR);
    }

    private Date finDeMes(Date referencia) {
        Calendar c = Calendar.getInstance();
        c.setTime(referencia != null ? referencia : new Date());
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public boolean hayAbierto() {
        try {
            RnGcUsuariosTbl user = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            return ejbFacade.existeAbierto(user);
        } catch (Exception e) {
            return false;
        }
    }
    
    private void calcularRangoDesdePeriodo() {
        if (periodoMes == null) return;

        Calendar c = Calendar.getInstance();
        c.setTime(periodoMes);
        // primer día del mes
        c.set(Calendar.DAY_OF_MONTH, 1);
        truncarTiempo(c);
        selected.setFechaInicioPeriodo(c.getTime());

        // último día del mes
        Calendar f = (Calendar) c.clone();
        f.set(Calendar.DAY_OF_MONTH, f.getActualMaximum(Calendar.DAY_OF_MONTH));
        truncarTiempo(f);
        selected.setFechaFinPeriodo(f.getTime());
    }
    
    private Integer mesSel;                // 1..12
    private Integer anioSel;               // 4 dígitos
    private String periodoLabel;           // "MM/yyyy"

    public Integer getMesSel() { return mesSel; }
    public void setMesSel(Integer mesSel) { this.mesSel = mesSel; }
    public Integer getAnioSel() { return anioSel; }
    public void setAnioSel(Integer anioSel) { this.anioSel = anioSel; }
    public String getPeriodoLabel() { return periodoLabel; }
    public void setPeriodoLabel(String periodoLabel) { this.periodoLabel = periodoLabel; }
    
    private Integer mesSelect;              // 1..12
    private Integer anioSelect;             // 2020..2050
    private String  periodoStr;             // "MM/yyyy"
    private boolean editablePeriodo = true; // habilita/deshabilita selectores
    private boolean mostrarEditarPeriodo;   // muestra botón "Editar periodo"

    public Integer getMesSelect() { return mesSelect; }
    public void setMesSelect(Integer m) { this.mesSelect = m; }
    public Integer getAnioSelect() { return anioSelect; }
    public void setAnioSelect(Integer a) { this.anioSelect = a; }
    public String  getPeriodoStr() { return periodoStr; }
    public boolean isEditablePeriodo() { return editablePeriodo; }
    public boolean isMostrarEditarPeriodo() { return mostrarEditarPeriodo; }

    // Meses para el select
    public static class MesItem { public final String label; public final int value;
    public MesItem(String l, int v){ label=l; value=v; } }
    public List<MesItem> getMeses() {
      return Arrays.asList(
        new MesItem("ENERO",1),   new MesItem("FEBRERO",2),
        new MesItem("MARZO",3),   new MesItem("ABRIL",4),
        new MesItem("MAYO",5),    new MesItem("JUNIO",6),
        new MesItem("JULIO",7),   new MesItem("AGOSTO",8),
        new MesItem("SEPTIEMBRE",9),new MesItem("OCTUBRE",10),
        new MesItem("NOVIEMBRE",11),new MesItem("DICIEMBRE",12)
      );
    }

    // Años 2020..2050
    public List<Integer> getAnios() {
      List<Integer> ys = new ArrayList<>();
      for (int y=2020; y<=2050; y++) ys.add(y);
      return ys;
    }
    
    public String etiquetaEstatus(RnGcPeriodosTbl p) {
    if (p == null || p.getEstatus() == null) return "";
    return "A".equalsIgnoreCase(p.getEstatus()) ? "Periodo abierto" : "Periodo cerrado";
}


public String estiloEstatus(RnGcPeriodosTbl p) {
    // Ajusta a tus estilos/tema
    return esAbierto(p) ? "ui-button-success" : "ui-button-secondary";
}

private boolean esAbierto(RnGcPeriodosTbl p) {
    return p != null && "A".equalsIgnoreCase(p.getEstatus());
}


  

    // Habilitar edición manual (solo cuando se muestra el botón)
    public void habilitarEdicion() {
        editablePeriodo = true;
    }

    // Reacciona a cambios en mes/año
    public void onMesChange()  { recalcularPeriodoYFechas(); }
    public void onAnioChange() { recalcularPeriodoYFechas(); }

    // Arma MM/yyyy y setea fechas 1er/último día
    private void recalcularPeriodoYFechas() {
        if (anioSelect == null || mesSelect == null) return;

        periodoStr = String.format("%02d/%d", mesSelect, anioSelect);

        Calendar ini = Calendar.getInstance();
        ini.clear();
        ini.set(Calendar.YEAR, anioSelect);
        ini.set(Calendar.MONTH, mesSelect - 1);
        ini.set(Calendar.DAY_OF_MONTH, 1);

        Calendar fin = (Calendar) ini.clone();
        fin.set(Calendar.DAY_OF_MONTH, fin.getActualMaximum(Calendar.DAY_OF_MONTH));

        selected.setFechaInicioPeriodo(ini.getTime());
        selected.setFechaFinPeriodo(fin.getTime());

        selected.setAnio(anioSelect);
        selected.setMes(String.valueOf(mesSelect));
    }
    
    // Cuando cambie mes/año, arma 1er y último día del mes y llena selected
    public void onMesAnioChange() {
        if (mesSel == null || anioSel == null) return;

        if (selected == null) selected = new RnGcPeriodosTbl();

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, anioSel);
        cal.set(Calendar.MONTH, mesSel - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date inicio = cal.getTime();

        Calendar fin = (Calendar) cal.clone();
        fin.set(Calendar.DAY_OF_MONTH, fin.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date ultimo = fin.getTime();

        selected.setFechaInicioPeriodo(inicio);
        selected.setFechaFinPeriodo(ultimo);
        selected.setAnio(anioSel);
        selected.setMes(String.format("%02d", mesSel));

        periodoLabel = String.format("%02d/%04d", mesSel, anioSel);
    }

    private void truncarTiempo(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    
    public RnGcPeriodosTbl getSelected() { return selected; }
    public void setSelected(RnGcPeriodosTbl selected) { this.selected = selected; }

    public Date getFinMin() { return finMin; }
    public void setFinMin(Date finMin) { this.finMin = finMin; }

    public Date getFinMax() { return finMax; }
    public void setFinMax(Date finMax) { this.finMax = finMax; }

    public void onInicioSelect() {
        Date inicio = (selected != null) ? selected.getFechaInicioPeriodo() : null;
        if (inicio == null) {
            finMin = null; finMax = null;
            return;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(inicio);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        finMin = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        finMax = cal.getTime();

        if (selected.getFechaFinPeriodo() != null) {
            Date fin = selected.getFechaFinPeriodo();
            if (fin.before(finMin) || fin.after(finMax)) {
                selected.setFechaFinPeriodo(null);
            }
        }
    }

    public List<RnGcPeriodosTbl> getItems() {
        if (items == null) {
            items = ejbFacade.findAll();
        }
        return items;
    }

    public void recargarItems() {
        items = ejbFacade.findAll();
    }

    private void cargarAniosDisponibles() {
    if (aniosDisponibles == null) {
        aniosDisponibles = new ArrayList<>();
        for (int y = 2020; y <= 2050; y++) aniosDisponibles.add(y);
    }
}

    private void setFechasPrimerYUltimoDia(int mes1a12, int anio) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, anio);
        cal.set(Calendar.MONTH, mes1a12 - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date inicio = cal.getTime();

        Calendar fin = (Calendar) cal.clone();
        fin.set(Calendar.DAY_OF_MONTH, fin.getActualMaximum(Calendar.DAY_OF_MONTH));
        selected.setFechaInicioPeriodo(inicio);
        selected.setFechaFinPeriodo(fin.getTime());
    }

    private int mesNombreToNumber(String nombre) {
        String n = nombre.toUpperCase();
        String[] es = {"ENERO","FEBRERO","MARZO","ABRIL","MAYO","JUNIO","JULIO",
                       "AGOSTO","SEPTIEMBRE","OCTUBRE","NOVIEMBRE","DICIEMBRE"};
        for (int i=0;i<es.length;i++) if (es[i].equals(n)) return i+1;
        return 1;
    }
    


// Opciones de año 2020..2050 como SelectItem (siempre disponibles)
private List<SelectItem> yearsSelectItems;

public List<SelectItem> getYearsSelectItems() {
    if (yearsSelectItems == null) {
        yearsSelectItems = new ArrayList<>();
        for (int y = 2020; y <= 2050; y++) {
            yearsSelectItems.add(new SelectItem(y, String.valueOf(y)));
        }
    }
    return yearsSelectItems;
}

// Al abrir el diálogo (tú ya lo haces en prepareCreate)

public void setModoSeleccionLibre(boolean b) { this.modoSeleccionLibre = b; }



    public void prepareCreate(javax.faces.event.ActionEvent e) {
        prepareCreate();
    }
    
    private RnGcPeriodosTblFacade getFacade() {
        return ejbFacade;
    }

    private static Throwable rootCause(Throwable t) {
        Throwable x = t;
        while (x.getCause() != null && x.getCause() != x) x = x.getCause();
        return x;
    }

    private static void sop(String msg) {
        System.out.println("[PeriodoController] " + msg);
    }

    private String dumpSelected() {
        return "selected{"
            + "id=" + (selected != null ? selected.getId() : null)
            + ", periodoId=" + (selected != null ? selected.getPeriodoId() : null)
            + ", inicio=" + (selected != null ? selected.getFechaInicioPeriodo() : null)
            + ", fin=" + (selected != null ? selected.getFechaFinPeriodo() : null)
            + ", anio=" + (selected != null ? selected.getAnio() : null)
            + ", mes=" + (selected != null ? selected.getMes() : null)
            + ", estatus=" + (selected != null ? selected.getEstatus() : null)
            + ", creadoPor=" + (selected != null ? selected.getCreadoPor() : null)
            + "}";
    }

    public void create() {
        sop("==> Entró a create()");
        
        if (selected == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No hay registro para guardar."));
            FacesContext.getCurrentInstance().validationFailed();
            return;
        }

        try {
            // Validación básica
            List<String> faltantes = new ArrayList<>();
            if (selected.getFechaInicioPeriodo() == null) faltantes.add("Fecha Inicio");
            if (selected.getFechaFinPeriodo()    == null) faltantes.add("Fecha Fin");
            if (!faltantes.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validación", "Faltan: " + String.join(", ", faltantes)));
                FacesContext.getCurrentInstance().validationFailed();
                return;
            }

            // Usuario (FK)
            RnGcUsuariosTbl user = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            if (ejbFacade.existeAbierto(user)) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Operación no permitida", "Cierra el periodo actual antes de abrir otro."));
                FacesContext.getCurrentInstance().validationFailed();
                return;
            }
            selected.setUsuariosId(user);

            // Siguiente periodoId por usuario
            List<RnGcPeriodosTbl> lista = ejbFacade.obtenerPeriodoIdDesc(user);
            int siguiente = (lista != null && !lista.isEmpty())
                    ? lista.get(0).getPeriodoId() + 1 : 1;
            selected.setPeriodoId(siguiente);

            // Año / Mes desde fecha inicio
            Calendar cal = Calendar.getInstance();
            cal.setTime(selected.getFechaInicioPeriodo());
            selected.setAnio(cal.get(Calendar.YEAR));                     // <-- OJO: anio
            selected.setMes(String.valueOf(cal.get(Calendar.MONTH) + 1)); // "1..12"

            // Campos obligatorios restantes
            Integer uid = usuarioFirmado.obtenerIdUsuario();
            selected.setCreadoPor(uid);
            selected.setUltimaActualizacionPor(uid);
            selected.setFechaCreacion(new Date());
            selected.setUltimaFechaActualizacion(new Date());
            if (selected.getEstatus() == null)     selected.setEstatus("A");
            if (selected.getTipoPeriodo() == null) selected.setTipoPeriodo("MENSUAL");

            sop("Antes de persistir: " + dumpSelected());

            // Requisito de negocio: no permitir otro periodo ACTIVO
            if (existePeriodoActivo(user)) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Ya existe un periodo activo", "Debes cerrar el periodo ‘A’ antes de crear otro."));
                FacesContext.getCurrentInstance().validationFailed();
                return;
            }

            ejbFacade.create(selected);  // hace persist
            ejbFacade.flush();           // fuerza INSERT y posibles errores YA
            
            

            sop("Después de persistir, id=" + selected.getId()); // aquí ya deberías ver el ID

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Periodo guardado."));
            items = null; // recargar tabla
            facturarController.notificarCambio();
        } catch (Exception e) {
            Throwable rc = rootCause(e);
            sop("ERROR al guardar: " + rc);
            e.printStackTrace(System.out);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al guardar",
                    (rc.getMessage() != null ? rc.getMessage() : e.getClass().getSimpleName())));
            FacesContext.getCurrentInstance().validationFailed();
        }


    }

    /** Revisa si hay un periodo ‘A’ para el usuario */
    private boolean existePeriodoActivo(RnGcUsuariosTbl user) {
        List<RnGcPeriodosTbl> l = ejbFacade.obtenerPeriodoIdDesc(user);
        return l.stream().anyMatch(p -> "A".equalsIgnoreCase(p.getEstatus()));
    }

    
    private void persist(PersistAction persistAction, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public boolean isHayPeriodoAbierto() {
        List<RnGcPeriodosTbl> ls = getItems();
        if (ls == null) return false;
        return ls.stream().anyMatch(p -> "A".equalsIgnoreCase(p.getEstatus()));
    }

    // Cerrar el periodo 'selected' (se setea con f:setPropertyActionListener)
    public void cerrarSelected() {
        if (selected == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "No hay periodo seleccionado."));
            return;
        }
        try {
            // regla de negocio: sólo cerrar si realmente está abierto
            if (!"A".equalsIgnoreCase(selected.getEstatus())) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sin cambios", "El periodo ya está cerrado."));
                return;
            }

            selected.setEstatus("C");
            selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
            selected.setUltimaFechaActualizacion(new java.util.Date());

            // (opcional) Si quieres sellar la fecha de fin al cerrar:
            // if (selected.getFechaFinPeriodo() == null) {
            //     selected.setFechaFinPeriodo(new java.util.Date());
            // }

            ejbFacade.edit(selected);

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Periodo cerrado."));
            items = null; // forzar recarga de la tabla (y por ende de hayPeriodoAbierto)
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo cerrar el periodo."));
            FacesContext.getCurrentInstance().validationFailed();
        }
    }

    // (Opcional) Reabrir el periodo seleccionado
    public void reabrirSelected() {
        if (selected == null) return;
        try {
            if (!"C".equalsIgnoreCase(selected.getEstatus())) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sin cambios", "El periodo ya está abierto."));
                return;
            }
            // Regla de integridad: si ya hay otro abierto, no permitir reabrir.
            boolean hayOtroAbierto = getItems().stream()
                .anyMatch(p -> !"C".equalsIgnoreCase(p.getEstatus()) && !p.getId().equals(selected.getId()));
            if (hayOtroAbierto) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "No permitido",
                        "Ya existe un periodo abierto. Cierre el actual antes de reabrir otro."));
                FacesContext.getCurrentInstance().validationFailed();
                return;
            }

            selected.setEstatus("A");
            selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
            selected.setUltimaFechaActualizacion(new java.util.Date());
            ejbFacade.edit(selected);

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Periodo reabierto."));
            items = null;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo reabrir el periodo."));
            FacesContext.getCurrentInstance().validationFailed();
        }
    }


// Mensaje de confirmación
public String msgConfirm(RnGcPeriodosTbl p) {
    if (p == null) return "¿Cambiar estatus del periodo?";
    boolean abrir = !"A".equalsIgnoreCase(p.getEstatus());
    String desc = descripcion(p); // ej. "MARZO 2025" si ya tienes este método
    return (abrir ? "¿Abrir el periodo " : "¿Cerrar el periodo ")
            + (desc != null ? desc : "") + "?";
}

// Botón: alterna A<->C sobre la fila recibida
public void cambiarEstatus(RnGcPeriodosTbl row) {
    if (row == null) return;
    try {
        String nuevo = "A".equalsIgnoreCase(row.getEstatus()) ? "C" : "A";
        row.setEstatus(nuevo);
        row.setUltimaFechaActualizacion(new java.util.Date());
        row.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        ejbFacade.edit(row);

        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "OK",
                "Periodo " + ("A".equals(nuevo) ? "abierto" : "cerrado") + "."));
        items = null; // refrescar lista
    } catch (Exception e) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        FacesContext.getCurrentInstance().validationFailed();
    }
}


    @PostConstruct
    public void init() {
        try {
            items = ejbFacade.findAll();
            selected = new RnGcPeriodosTbl();
        } catch (Exception e) {
            // log simple para ver si algo truena aquí
            System.out.println("[PeriodoController] @PostConstruct ERROR: " + e);
        }
    }
    
    // ya lo tienes (adaptado de tu cambiarEstatus)
    public void eliminar(RnGcPeriodosTbl row) {
        if (row == null || row.getId() == null) return;

        try {
            // regla opcional: no borrar abiertos
            if ("A".equalsIgnoreCase(row.getEstatus())) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso",
                        "No puedes eliminar un periodo abierto. Ciérralo primero."));
                return;
            }

            RnGcPeriodosTbl managed = ejbFacade.find(row.getId());
            if (managed == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso",
                        "El periodo ya no existe."));
                items = null;
                return;
            }

            ejbFacade.remove(managed);

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "OK",
                    "Periodo eliminado correctamente."));
            items = null;           // refrescar la tabla en el próximo render
            selected = null;        // limpiar selección

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                    mensajeRaiz(e, "No se pudo eliminar el periodo. Verifica relaciones.")));
            FacesContext.getCurrentInstance().validationFailed();
        }
    }

    public void eliminarSelected() {
        eliminar(selected);   // <- ESTE es el método que reclama la vista
    }

    // util para mensajes
    private String mensajeRaiz(Throwable t, String fallback) {
        Throwable c = t;
        while (c != null) {
            if (c.getMessage() != null) return c.getMessage();
            c = c.getCause();
        }
        return fallback;
    }


}

