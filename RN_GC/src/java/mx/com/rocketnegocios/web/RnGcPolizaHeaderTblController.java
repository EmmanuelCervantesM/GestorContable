package mx.com.rocketnegocios.web;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import mx.com.rocketnegocios.entities.RnGcPolizaHeaderTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcPolizaHeaderTblFacade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import mx.com.rocketnegocios.beans.RnGcCatalogoCuentasTblFacade;
import mx.com.rocketnegocios.beans.RnGcPeriodosTblFacade;
import mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade;
import mx.com.rocketnegocios.entities.RnGcCatalogoCuentasTbl;
import mx.com.rocketnegocios.entities.RnGcMonedasTbl;
import mx.com.rocketnegocios.entities.RnGcPeriodosTbl;
import mx.com.rocketnegocios.entities.RnGcPolizaLineasTbl;
import mx.com.rocketnegocios.entities.RnGcTipoPoliza;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.primefaces.event.RowEditEvent;

@Named("rnGcPolizaHeaderTblController")
@SessionScoped
public class RnGcPolizaHeaderTblController implements Serializable {

    @EJB
    private RnGcPolizaHeaderTblFacade ejbFacade;

    @EJB
    private mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade usuarioFacade;

    @EJB
    private mx.com.rocketnegocios.beans.RnGcTipoPolizaFacade tipoPolizaFacade;

    @EJB
    private mx.com.rocketnegocios.beans.RnGcMonedasTblFacade monedasFacade;

    @EJB
    private mx.com.rocketnegocios.beans.RnGcPolizaLineasTblFacade polizaLineasFacade;

    @EJB
    private RnGcCatalogoCuentasTblFacade catalogoCuentasFacade;

    @EJB
    private RnGcPeriodosTblFacade periodosFacade;

    @EJB
    private RnGcUsuariosTblFacade usuariosFacade;

    // ===================================
    // CAMPOS DE ESTADO
    // ===================================

    private List<RnGcPolizaHeaderTbl> items = null;
    private RnGcPolizaHeaderTbl selected;

    // Usuario firmado (siempre lo has usado así)
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();

    // Fechas para cálculo de secuencias, etc.
    private Date fechaActual;
    private Date primerDia;
    private Date ultimoDia;
    
    private RnGcPolizaHeaderTbl polizaDetalle;
    
    private Integer tipoPolizaIdSeleccionado;

    public Integer getTipoPolizaIdSeleccionado() {
        return tipoPolizaIdSeleccionado;
    }

    public void setTipoPolizaIdSeleccionado(Integer tipoPolizaIdSeleccionado) {
        System.out.println("SET tipoPolizaIdSeleccionado = " + tipoPolizaIdSeleccionado);
        this.tipoPolizaIdSeleccionado = tipoPolizaIdSeleccionado;
    }

    public void prepararDetalle(RnGcPolizaHeaderTbl item) {
        this.polizaDetalle = item;
        this.selected = item;

        System.out.println("=== prepararDetalle() ===");
        if (item != null) {
            System.out.println("ID: " + item.getId());
            System.out.println("NumeroPoliza: " + item.getNumeroPoliza());
        }
    }

    public RnGcPolizaHeaderTbl getPolizaDetalle() {
        return polizaDetalle;
    }

    public void setPolizaDetalle(RnGcPolizaHeaderTbl polizaDetalle) {
        this.polizaDetalle = polizaDetalle;
    }

    private RnGcUsuariosTbl usuarioId = null;

    // Líneas de póliza
    private List<RnGcPolizaLineasTbl> itemsPolizaLineas;
    private RnGcPolizaLineasTbl lineaSelected;

    // Línea que se usa en el modal “Nuevo Asiento”
    private RnGcPolizaLineasTbl nuevaLinea;

    // Cuenta por defecto para onAddNew (sigue existiendo)
    private RnGcCatalogoCuentasTbl catalogodeCuentas = new RnGcCatalogoCuentasTbl(1);

    // Totales
    private Double cargos = 0.0, cargo = 0.0;
    private Double abonos = 0.0, abono = 0.0;
    private Double diferencia = 0.0;

    private String concepto = "";

    private List<RnGcPolizaHeaderTbl> listaPolizasPorUsuario = null;

    public RnGcPolizaHeaderTblController() {
    }

    @PostConstruct
    public void init() {
        nuevaLinea = new RnGcPolizaLineasTbl();
    }

    // ===================================
    // GETTERS / SETTERS BÁSICOS
    // ===================================
    
    public RnGcPolizaLineasTbl getNuevaLinea() {
        if (nuevaLinea == null) {
            nuevaLinea = new RnGcPolizaLineasTbl();
        }
        return nuevaLinea;
    }

    public void setNuevaLinea(RnGcPolizaLineasTbl nuevaLinea) {
        this.nuevaLinea = nuevaLinea;
    }

    /** Se llama al dar clic en "Agregar nuevo asiento" */
    public void prepararNuevaLinea() {
        nuevaLinea = new RnGcPolizaLineasTbl();
        nuevaLinea.setAbono(0.0);
        nuevaLinea.setCargo(0.0);

        if (selected != null) {
            nuevaLinea.setConcepto(selected.getConcepto());
            nuevaLinea.setPolizaHeaderId(selected);
        }
    }

    /** Se llama al dar clic en "Aceptar" del modal */
    public void agregarNuevaLinea() {
        if (nuevaLinea == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                 "Error", "No hay información de la nueva línea"));
            return;
        }

        nuevaLinea.setPolizaHeaderId(selected);

        if (itemsPolizaLineas == null) {
            itemsPolizaLineas = new ArrayList<>();
        }

        itemsPolizaLineas.add(nuevaLinea);

        // Recalcular totales al agregar
        calcularSuma();

        // Dejar lista una nueva línea para el siguiente asiento
        nuevaLinea = new RnGcPolizaLineasTbl();
    }
    
    // getItemsPolizaLineas debe asegurar que nunca sea null:
    public List<RnGcPolizaLineasTbl> getItemsPolizaLineas() {
        if (itemsPolizaLineas == null) {
            itemsPolizaLineas = new ArrayList<>();
        }
        return itemsPolizaLineas;
    }

    public void setItemsPolizaLineas(List<RnGcPolizaLineasTbl> itemsPolizaLineas) {
        this.itemsPolizaLineas = itemsPolizaLineas;
    }

    public RnGcPolizaHeaderTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcPolizaHeaderTbl selected) {
        this.selected = selected;
    }

    public List<RnGcPolizaHeaderTbl> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private RnGcPolizaHeaderTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcPolizaLineasTbl getLineaSelected() {
        return lineaSelected;
    }

    public void setLineaSelected(RnGcPolizaLineasTbl lineaSelected) {
        this.lineaSelected = lineaSelected;
    }

    public Double getCargos() {
        return cargos;
    }

    public Double getAbonos() {
        return abonos;
    }

    public Double getDiferencia() {
        return diferencia;
    }

    public Double getCargo() {
        return cargo;
    }

    public void setCargo(Double cargo) {
        this.cargo = cargo;
    }

    public Double getAbono() {
        return abono;
    }

    public void setAbono(Double abono) {
        this.abono = abono;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public RnGcCatalogoCuentasTbl getCatalogodeCuentas() {
        return catalogodeCuentas;
    }

    public void setCatalogodeCuentas(RnGcCatalogoCuentasTbl catalogodeCuentas) {
        this.catalogodeCuentas = catalogodeCuentas;
    }

    public List<RnGcPolizaHeaderTbl> getListaPolizasPorUsuario() {
        if (listaPolizasPorUsuario == null) {
            usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            listaPolizasPorUsuario = ejbFacade.obtenerListaPolizas(usuarioId);
        }
        return listaPolizasPorUsuario;
    }

    public void setListaPolizasPorUsuario(List<RnGcPolizaHeaderTbl> listaPolizasPorUsuario) {
        this.listaPolizasPorUsuario = listaPolizasPorUsuario;
    }

    // ===================================
    // EMBEDDABLE KEYS (HEADER)
    // ===================================

    protected void setEmbeddableKeys() {
        selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        selected.setUltimaFechaActualizacion(new Date());
    }

    protected void initializeEmbeddableKey() {
        selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        selected.setFechaCreacion(new Date());
        obtenerMoneda();
    }
    
    // ===================================
    // PREPARE CREATE (HEADER + PERÍODO)
    // ===================================

    public RnGcPolizaHeaderTbl prepareCreate() {
        selected = new RnGcPolizaHeaderTbl();
        lineaSelected = new RnGcPolizaLineasTbl();
        tipoPolizaIdSeleccionado = null;
        initializeEmbeddableKey();

        // 1. Obtener el id del usuario firmado
        Integer uid = (usuarioFirmado != null) ? usuarioFirmado.obtenerIdUsuario() : null;

        // 2. Obtener el usuario desde la BD
        RnGcUsuariosTbl user = (uid != null) ? usuariosFacade.obtenerUsuarioPorId(uid) : null;

        // 3. Obtener el periodo activo para ese usuario
        RnGcPeriodosTbl p = (user != null) ? periodosFacade.findActivo(user) : null;

        // 4. Si existe un periodo activo, setearlo en la póliza y fijar la fecha
        if (p != null) {
            selected.setPeriodoId(p);

            int mes = Integer.parseInt(p.getMes());
            int anio = p.getAnio();

            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.set(anio, mes - 1, 1); // primer día del mes

            selected.setFecha(cal.getTime());
        }

        return selected;
    }

    // ===================================
    // CRUD HEADER
    // ===================================

    public void create() {
        if (actualizarNumeroPoliza()) {
            persist(PersistAction.CREATE,
                    ResourceBundle.getBundle("/Bundle").getString("RnGcPolizaHeaderTblCreated"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;
            }
        }
    }

    public void update() {
        persist(PersistAction.UPDATE,
                ResourceBundle.getBundle("/Bundle").getString("RnGcPolizaHeaderTblUpdated"));
        if (!JsfUtil.isValidationFailed()) {
            //Actualiza Conceptos de poliza Linea
            List<RnGcPolizaLineasTbl> polizaLineasList = polizaLineasFacade.obtenerPolizaLineas(selected);
            if (polizaLineasList != null && !polizaLineasList.isEmpty()) {
                String conceptoLinea = polizaLineasList.get(0).getConcepto();
                String conceptoHeader = selected.getConcepto();
                if (conceptoLinea == null || !conceptoLinea.equals(conceptoHeader)) {
                    for (RnGcPolizaLineasTbl pl : polizaLineasList) {
                        try {
                            pl.setConcepto(conceptoHeader);
                            polizaLineasFacade.edit(pl);
                        } catch (Exception e) {
                            System.out.println("Error al actualizar el concepto en línea de póliza");
                        }
                    }
                }
            }
        }
    }

    public void destroy() {
        persist(PersistAction.DELETE,
                ResourceBundle.getBundle("/Bundle").getString("RnGcPolizaHeaderTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null;
            items = null;
        }
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
                    JsfUtil.addErrorMessage(ex,
                            ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex,
                        ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    // ===================================
    // LÍNEAS EXISTENTES (EDIT / DELETE)
    // ===================================

    protected void initializeEmbeddableKeyLinea() {
        lineaSelected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        lineaSelected.setFechaCreacion(new Date());
    }

    protected void setEmbeddableKeysLinea() {
        lineaSelected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        lineaSelected.setUltimaFechaActualizacion(new Date());
    }

    private void persistLinea(PersistAction persistAction, String successMessage) {
        if (lineaSelected != null) {
            try {
                initializeEmbeddableKeyLinea();
                setEmbeddableKeysLinea();
                if (persistAction != PersistAction.DELETE) {
                    polizaLineasFacade.edit(lineaSelected);
                    lineaSelected = new RnGcPolizaLineasTbl();
                } else {
                    itemsPolizaLineas.remove(lineaSelected);
                    lineaSelected = new RnGcPolizaLineasTbl();
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
                    JsfUtil.addErrorMessage(ex,
                            ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex,
                        ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public void updateLinea() {
        persistLinea(PersistAction.UPDATE,
                ResourceBundle.getBundle("/Bundle").getString("RnGcPolizaLineasTblUpdated"));
    }

    public void eliminarLinea() {
        persistLinea(PersistAction.DELETE,
                ResourceBundle.getBundle("/Bundle").getString("RnGcPolizaLineasTblDeleted"));
    }

    public void prepareEdit() {
        if (selected != null) {
            itemsPolizaLineas = polizaLineasFacade.obtenerPolizaLineas(selected);
            calcularSuma();
        }
    }

    // ===================================
    // CREAR / EDITAR POLIZA COMPLETA
    // ===================================

    public void editar() {
        System.out.println("selected: " + selected + " receptor: " + selected.getReceptor());
        try {
            if (actualizarNumeroPoliza()) {
                setEmbeddableKeys();
                getFacade().edit(selected);
                if (itemsPolizaLineas != null && !itemsPolizaLineas.isEmpty()) {
                    for (RnGcPolizaLineasTbl linea : itemsPolizaLineas) {
                        linea.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                        linea.setUltimaFechaActualizacion(new Date());
                        polizaLineasFacade.edit(linea);
                    }
                }
                obtenerpolizasPorUsuario();
            }
            itemsPolizaLineas = new ArrayList<>();
            selected = new RnGcPolizaHeaderTbl();
        } catch (Exception e) {
            System.out.print("ErrorEditarPoliza: " + e.getLocalizedMessage());
        }
    }

    public void crear(){
        System.out.println("selected: " + selected + " receptor: " + selected.getReceptor());
        try{
            // 1) Primero validamos que cuadre la póliza
            if (!validarPolizaCuadrada()) {
                // Si no cuadra, NO guardamos nada
                return;
            }

            // 2) Ya cuadra, ahora sí seguimos con la lógica que ya tenías
            if (actualizarNumeroPoliza()) {
                System.out.println("Dentro de If --------");
                setEmbeddableKeys();
                selected.setFechaCreacion(new Date());
                selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
                selected = getFacade().refreshFromDB(selected);
                System.out.println("Creo encabezado de poliza: " + selected.getId());
                guardarAciento();
                obtenerpolizasPorUsuario();
            }
            itemsPolizaLineas = new ArrayList<>();
            selected = new RnGcPolizaHeaderTbl();
        } catch(Exception e){
            System.out.print("ErrorCrearPoliza: "+ e.getLocalizedMessage());
        }
    }

    public void guardarAciento() {
        if (itemsPolizaLineas != null && !itemsPolizaLineas.isEmpty()) {
            for (RnGcPolizaLineasTbl linea : itemsPolizaLineas) {
                linea.setSucursal("-");
                linea.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
                linea.setFechaCreacion(new Date());
                linea.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                linea.setUltimaFechaActualizacion(new Date());
                linea.setPolizaHeaderId(selected);
                linea = polizaLineasFacade.refreshFromDB(linea);
            }
        }
    }

    // ===================================
    // UTILIDADES: SUMAS, EVENTOS TABLA
    // ===================================

    public void calcularSuma() {
        cargos = 0.0;
        abonos = 0.0;
        diferencia = 0.0;

        if (itemsPolizaLineas == null) {
            return;
        }

        for (RnGcPolizaLineasTbl item : itemsPolizaLineas) {
            cargos = cargos + item.getCargo();
            abonos = abonos + item.getAbono();
        }
        diferencia = cargos - abonos;
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edición Cancelada");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Editado Correctamente");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        calcularSuma();
    }

    // ===================================
    // PERÍODO ACTIVO (LABEL Y CALENDARIO)
    // ===================================

    public String getPeriodoActivoDescripcion() {
        Integer uid = (usuarioFirmado != null) ? usuarioFirmado.obtenerIdUsuario() : null;
        RnGcUsuariosTbl user = (uid != null) ? usuariosFacade.obtenerUsuarioPorId(uid) : null;
        RnGcPeriodosTbl p = (user != null) ? periodosFacade.findActivo(user) : null;

        if (p != null) {
            String mesStr = p.getMes();
            int anioStr = p.getAnio();
            int mesNum;
            try {
                mesNum = Integer.parseInt(mesStr);
            } catch (NumberFormatException e) {
                return mesStr + " " + anioStr;
            }
            return nombreMesEnEsp(mesNum) + " " + anioStr;
        }
        return "Sin periodo activo";
    }

    private String nombreMesEnEsp(int mes) {
        switch (mes) {
            case 1:  return "ENERO";
            case 2:  return "FEBRERO";
            case 3:  return "MARZO";
            case 4:  return "ABRIL";
            case 5:  return "MAYO";
            case 6:  return "JUNIO";
            case 7:  return "JULIO";
            case 8:  return "AGOSTO";
            case 9:  return "SEPTIEMBRE";
            case 10: return "OCTUBRE";
            case 11: return "NOVIEMBRE";
            case 12: return "DICIEMBRE";
            default: return "";
        }
    }

    public Date getMinFechaPeriodo() {
        if (selected == null || selected.getPeriodoId() == null) {
            return null;
        }
        RnGcPeriodosTbl p = selected.getPeriodoId();
        int mes = Integer.parseInt(p.getMes());
        int anio = p.getAnio();

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(anio, mes - 1, 1);
        return cal.getTime();
    }

    public Date getMaxFechaPeriodo() {
        if (selected == null || selected.getPeriodoId() == null) {
            return null;
        }
        RnGcPeriodosTbl p = selected.getPeriodoId();
        int mes = Integer.parseInt(p.getMes());
        int anio = p.getAnio();

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(anio, mes - 1, 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    // ===================================
    // OTRAS UTILIDADES (MONEDA, SECUENCIA)
    // ===================================

    public void obtenerMoneda() {
        RnGcMonedasTbl tipoMoneda = monedasFacade.obtenerMoneda();
        selected.setTipoMoneda(tipoMoneda);
        selected.setTipoCambio(1.0);
    }

    public List<RnGcPolizaHeaderTbl> obtenerpolizasPorUsuario() {
        usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        listaPolizasPorUsuario = ejbFacade.obtenerListaPolizas(usuarioId);
        return listaPolizasPorUsuario;
    }

    public void obtenerNumeroPoliza() throws ParseException {
        RnGcTipoPoliza tipopoliza = null;
        obtenerFechas();
        if (selected.getTipoPolizaId() != null) {
            tipopoliza = tipoPolizaFacade.tipoPoliza(selected.getTipoPolizaId());
            if (tipopoliza.getFechaFinSecuencia().before(fechaActual)) {
                tipopoliza.setNumeroSecuencia(0);
                tipopoliza.setFechaFinSecuencia(ultimoDia);
                tipoPolizaFacade.edit(tipopoliza);
                tipopoliza = tipoPolizaFacade.tipoPoliza(selected.getTipoPolizaId());
            }
            selected.setNumeroPoliza(tipopoliza.getNumeroSecuencia() + 1);
        } else if ("".equals(selected.getTipoPolizaId())) {
            selected.setNumeroPoliza(0);
        }
    }

    public boolean actualizarNumeroPoliza() {
        try {
            Integer idUsuario = usuarioFirmado.obtenerIdUsuario();

            System.out.println("=== actualizarNumeroPoliza() INICIO ===");
            System.out.println("Usuario logueado: " + idUsuario);

            int consecutivo = ejbFacade.obtenerConsecutivoPorUsuario(idUsuario);

            System.out.println("Consecutivo calculado: " + consecutivo);

            selected.setNumeroPoliza(consecutivo);

            System.out.println("selected.numeroPoliza asignado: " + selected.getNumeroPoliza());
            System.out.println("=== actualizarNumeroPoliza() FIN OK ===");

            return true;

        } catch (Exception e) {
            System.out.println("Error en actualizarNumeroPoliza(): " + e.getMessage());
            e.printStackTrace();
            JsfUtil.addErrorMessage("No se pudo calcular el consecutivo de la póliza");
            return false;
        }
    }
    
    public Integer obtenerNumeroConsecutivoPoliza() {
        try {
            Integer idUsuario = usuarioFirmado.obtenerIdUsuario();

            System.out.println("=== obtenerNumeroConsecutivoPoliza() INICIO ===");
            System.out.println("Usuario logueado: " + idUsuario);

            int consecutivo = ejbFacade.obtenerConsecutivoPorUsuario(idUsuario);

            System.out.println("Consecutivo obtenido desde facade: " + consecutivo);
            System.out.println("=== obtenerNumeroConsecutivoPoliza() FIN OK ===");

            return consecutivo;

        } catch (Exception e) {
            System.out.println("Error en obtenerNumeroConsecutivoPoliza(): " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void obtenerFechas() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();

        String hoy = sdf.format(calendar.getTime());
        fechaActual = sdf.parse(hoy);

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String diaUno = sdf.format(calendar.getTime());
        primerDia = sdf.parse(diaUno);

        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String ultimo = sdf.format(calendar.getTime());
        ultimoDia = sdf.parse(ultimo);
    }

    public void obtenerCatalogoCuenta() {
        catalogodeCuentas =
                catalogoCuentasFacade.obtenerCuentasCreadoPor(usuarioFirmado.obtenerIdUsuario()).get(0);
    }

    /**
     * Método antiguo que agrega una línea “rápida” sin modal. Lo puedes seguir
     * usando o eliminar si ya no se usa.
     */
    public void onAddNew() {
        System.out.println("Preparando para agregar asiento (onAddNew)");
        try {
            RnGcPolizaLineasTbl polizaLinea = new RnGcPolizaLineasTbl();
            obtenerCatalogoCuenta();
            polizaLinea.setAbono(0.0);
            polizaLinea.setCargo(0.0);
            polizaLinea.setCatalogoCuentasId(catalogodeCuentas);
            polizaLinea.setConcepto(selected.getConcepto());
            polizaLinea.setId((int) (Math.random() * 999999999));
            getItemsPolizaLineas().add(polizaLinea);
            FacesMessage msg = new FacesMessage("Nuevo asiento agregado correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println("Error en añadir asiento: " + e.getLocalizedMessage());
        }
    }

    // Converter se queda igual que ya lo tenías...
    @FacesConverter(forClass = RnGcPolizaHeaderTbl.class)
    public static class RnGcPolizaHeaderTblControllerConverter implements Converter {
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcPolizaHeaderTblController controller =
                    (RnGcPolizaHeaderTblController) facesContext.getApplication().getELResolver()
                            .getValue(facesContext.getELContext(), null, "rnGcPolizaHeaderTblController");
            return controller.getRnGcPolizaHeaderTbl(Integer.valueOf(value));
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof RnGcPolizaHeaderTbl) {
                RnGcPolizaHeaderTbl o = (RnGcPolizaHeaderTbl) object;
                return String.valueOf(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                        "object {0} is of type {1}; expected type: {2}",
                        new Object[]{object, object.getClass().getName(), RnGcPolizaHeaderTbl.class.getName()});
                return null;
            }
        }
    }

    public RnGcPolizaHeaderTbl getRnGcPolizaHeaderTbl(Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcPolizaHeaderTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcPolizaHeaderTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    
    private boolean validarPolizaCuadrada() {
        // Aseguramos que los totales estén actualizados
        calcularSuma();

        // Validar que haya al menos un asiento
        if (itemsPolizaLineas == null || itemsPolizaLineas.isEmpty()) {
            FacesMessage msg = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Sin asientos",
                    "Debes capturar al menos un asiento en la póliza."
            );
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return false;
        }

        // Diferencia entre cargos y abonos
        diferencia = cargos - abonos;

        // Tolerancia por si después usas decimales
        double tolerancia = 0.000001;
        if (Math.abs(diferencia) > tolerancia) {
            String detalle = String.format("Cargos: %.2f | Abonos: %.2f | Diferencia: %.2f",
                                           cargos, abonos, diferencia);
            FacesMessage msg = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Póliza descuadrada",
                    "El total de cargos debe ser igual al total de abonos. " + detalle
            );
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return false;
        }

        return true;
    }

    
    public void guardarPoliza() {
        System.out.println("=== guardarPoliza() INICIO ===");

        try {
            calcularSuma();

            if (cargos == null) cargos = 0.0;
            if (abonos == null) abonos = 0.0;

            if (Math.abs(cargos - abonos) > 0.0001) {
                JsfUtil.addErrorMessage("Los cargos ($" + cargos +
                        ") y abonos ($" + abonos +
                        ") no cuadran. Verifique los montos antes de guardar.");
                return;
            }

            if (selected == null) {
                JsfUtil.addErrorMessage("No existe una póliza seleccionada.");
                return;
            }

            if (tipoPolizaIdSeleccionado != null) {
                RnGcTipoPoliza tipoPoliza = tipoPolizaFacade.find(tipoPolizaIdSeleccionado);
                selected.setTipoPolizaId(tipoPoliza);
                selected.setTipoPoliza(tipoPoliza.getTipoPoliza());

                System.out.println("tipoPolizaIdSeleccionado: " + tipoPolizaIdSeleccionado);
                System.out.println("tipoPoliza seleccionado: " + tipoPoliza.getTipoPoliza());
            } else {
                System.out.println("ERROR: tipoPolizaIdSeleccionado viene NULL");
                JsfUtil.addErrorMessage("Debe seleccionar un tipo de póliza");
                return;
            }

            RnGcTipoPoliza tipoPoliza = tipoPolizaFacade.find(tipoPolizaIdSeleccionado);
            selected.setTipoPolizaId(tipoPoliza);
            selected.setTipoPoliza(tipoPoliza.getTipoPoliza());

            setEmbeddableKeys();
            selected.setFechaCreacion(new Date());
            selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());

            Integer numConsecutivo = obtenerNumeroConsecutivoPoliza();
            selected.setNumeroPoliza(numConsecutivo);

            System.out.println("tipoPolizaIdSeleccionado: " + tipoPolizaIdSeleccionado);
            System.out.println("tipoPoliza seleccionado: " + tipoPoliza.getTipoPoliza());
            System.out.println("selected.getTipoPolizaId(): " + selected.getTipoPolizaId());
            System.out.println("Numero consecutivo: " + numConsecutivo);
            
            crear();

            System.out.println("=== guardarPoliza() FIN OK ===");
            
        } catch (Exception e) {
            System.out.println("=== ERROR EN guardarPoliza() ===");
            e.printStackTrace();
            System.out.println("Mensaje: " + e.getMessage());
        }
    }
   
    private void actualizarSaldoCatalogo(RnGcPolizaLineasTbl linea) {
        if (linea == null || linea.getCatalogoCuentasId() == null) {
            return;
        }

        Integer idCuenta = linea.getCatalogoCuentasId().getId();

        BigDecimal cargo = BigDecimal.ZERO;
        BigDecimal abono = BigDecimal.ZERO;

        if (linea.getCargo() != null) {
            cargo = BigDecimal.valueOf(linea.getCargo());   // si cargo es Double
        }
        if (linea.getAbono() != null) {
            abono = BigDecimal.valueOf(linea.getAbono());   // si abono es Double
        }

        System.out.println("[ASIENTO] Cuenta=" + linea.getCatalogoCuentasId().getNumeroCuenta()
                + " saldoActual (antes) se actualizará con cargo=" + cargo + ", abono=" + abono);

        catalogoCuentasFacade.aplicarMovimientoCuenta(idCuenta, cargo, abono);
    }
    
   
   
}

