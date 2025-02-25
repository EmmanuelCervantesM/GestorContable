package mx.com.rocketnegocios.web;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import mx.com.rocketnegocios.entities.RnGcPolizaHeaderTbl;
import mx.com.rocketnegocios.web.util.JsfUtil;
import mx.com.rocketnegocios.web.util.JsfUtil.PersistAction;
import mx.com.rocketnegocios.beans.RnGcPolizaHeaderTblFacade;

import java.io.Serializable;
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
import mx.com.rocketnegocios.entities.RnGcCatalogoCuentasTbl;
import mx.com.rocketnegocios.entities.RnGcMonedasTbl;
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
    private mx.com.rocketnegocios.beans.RnGcPolizaHeaderTblFacade ejbFacade;
    private List<RnGcPolizaHeaderTbl> items = null;
    private RnGcPolizaHeaderTbl selected;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();

    private Date fechaActual;
    private Date primerDia;
    private Date ultimoDia;

    @EJB
    private mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade usuarioFacade;
    private RnGcUsuariosTbl usuarioId = null;

    @EJB
    private mx.com.rocketnegocios.beans.RnGcTipoPolizaFacade tipoPolizaFacade;

    @EJB
    private mx.com.rocketnegocios.beans.RnGcMonedasTblFacade monedasFacade;
    
    @EJB
    private mx.com.rocketnegocios.beans.RnGcPolizaLineasTblFacade polizaLineasFacade;
    
    @EJB
    private RnGcCatalogoCuentasTblFacade catalogoCuentasFacade;

    private RnGcCatalogoCuentasTbl catalogodeCuentas = new RnGcCatalogoCuentasTbl(1);
    RnGcPolizaHeaderTbl polizaHeaderId = null;
    private List<RnGcPolizaLineasTbl> itemsPolizaLineas = null;
    private RnGcPolizaLineasTbl lineaSelected;
    private Double cargos = 0.0, cargo = 0.0;
    private Double abonos = 0.0, abono = 0.0;
    private Double diferencia = 0.0;
    private String concepto = "";
    private List<RnGcPolizaHeaderTbl> listaPolizasPorUsuario = null;
    
    public RnGcPolizaHeaderTblController() {
    }

    public List<RnGcPolizaHeaderTbl> getListaPolizasPorUsuario() {
        if(listaPolizasPorUsuario == null){
            usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
            listaPolizasPorUsuario = ejbFacade.obtenerListaPolizas(usuarioId);
        }
        return listaPolizasPorUsuario;
    }

    public void setListaPolizasPorUsuario(List<RnGcPolizaHeaderTbl> listaPolizasPorUsuario) {
        this.listaPolizasPorUsuario = listaPolizasPorUsuario;
    }

    public RnGcPolizaLineasTbl getLineaSelected() {
        return lineaSelected;
    }

    public void setLineaSelected(RnGcPolizaLineasTbl lineaSelected) {
        this.lineaSelected = lineaSelected;
    }

    public RnGcCatalogoCuentasTbl getCatalogodeCuentas() {
        return catalogodeCuentas;
    }

    public void setCatalogodeCuentas(RnGcCatalogoCuentasTbl catalogodeCuentas) {
        this.catalogodeCuentas = catalogodeCuentas;
    }

    public List<RnGcPolizaLineasTbl> getItemsPolizaLineas() {
        if(itemsPolizaLineas == null){
            itemsPolizaLineas = new ArrayList<>();
        }
        return itemsPolizaLineas;
    }

    public void setItemsPolizaLineas(List<RnGcPolizaLineasTbl> itemsPolizaLineas) {
        this.itemsPolizaLineas = itemsPolizaLineas;
    }

    public Double getCargos() {
        return cargos;
    }

    public void setCargos(Double cargos) {
        this.cargos = cargos;
    }

    public Double getAbonos() {
        return abonos;
    }

    public void setAbonos(Double abonos) {
        this.abonos = abonos;
    }

    public Double getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(Double diferencia) {
        this.diferencia = diferencia;
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

    public RnGcPolizaHeaderTbl getSelected() {
        return selected;
    }

    public void setSelected(RnGcPolizaHeaderTbl selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        selected.setUltimaFechaActualizacion(new Date());
    }

    protected void initializeEmbeddableKey() {
        selected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        selected.setFechaCreacion(new Date());
        obtenerMoneda();
    }
    
    private RnGcPolizaHeaderTblFacade getFacade() {
        return ejbFacade;
    }

    public RnGcPolizaHeaderTbl prepareCreate() {
        selected = new RnGcPolizaHeaderTbl();
        lineaSelected = new RnGcPolizaLineasTbl();
        itemsPolizaLineas = new ArrayList<>();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        if (actualizarNumeroPoliza()) {
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RnGcPolizaHeaderTblCreated"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcPolizaHeaderTblUpdated"));
        if (!JsfUtil.isValidationFailed()) {
            //Actualiza Conceptos de poliza Linea
            List<RnGcPolizaLineasTbl> polizaLineasList = new ArrayList<>();
            polizaLineasList = polizaLineasFacade.obtenerPolizaLineas(selected);
            if (!polizaLineasList.isEmpty()) {
                System.out.println("Entro a revisar poliza lineas");
                String concepto = polizaLineasList.get(0).getConcepto();
                String concepto2 = selected.getConcepto();
                System.out.println("conceptos: "+concepto + " || "+concepto2);
                if (  concepto == null  || !concepto.equals(concepto2)) {
                    System.out.println("Los conceptos no son iguales");
                    for (int i = 0; i < polizaLineasList.size(); i++) {
                        try {
                            RnGcPolizaLineasTbl polizaLinea = new RnGcPolizaLineasTbl();
                            polizaLinea = polizaLineasList.get(i);
                            polizaLinea.setConcepto(selected.getConcepto());
                            polizaLineasFacade.edit(polizaLinea);
                            System.out.println("Se actualizo el concepto correctamente");

                        } catch (Exception e) {
                            System.out.println("Error al actualizar el concepto");
                        }

                    }
                }
            }
        }
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcPolizaHeaderTblDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RnGcPolizaHeaderTbl> getItems() {
        if (items == null) {
            items = getFacade().findAll();
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

    public RnGcPolizaHeaderTbl getRnGcPolizaHeaderTbl(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RnGcPolizaHeaderTbl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RnGcPolizaHeaderTbl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RnGcPolizaHeaderTbl.class)
    public static class RnGcPolizaHeaderTblControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RnGcPolizaHeaderTblController controller = (RnGcPolizaHeaderTblController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rnGcPolizaHeaderTblController");
            return controller.getRnGcPolizaHeaderTbl(getKey(value));
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
            if (object instanceof RnGcPolizaHeaderTbl) {
                RnGcPolizaHeaderTbl o = (RnGcPolizaHeaderTbl) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RnGcPolizaHeaderTbl.class.getName()});
                return null;
            }
        }

    }
    
    public void prepareEdit() {
        if(selected != null)
            itemsPolizaLineas = polizaLineasFacade.obtenerPolizaLineas(selected);
    }
    
    protected void initializeEmbeddableKeyLinea() {
        lineaSelected.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
        lineaSelected.setFechaCreacion(new Date());
    }
    
    protected void setEmbeddableKeysLinea() {
        lineaSelected.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
        lineaSelected.setUltimaFechaActualizacion(new Date());
     }
    
    public void updateLinea() {
        persistLinea(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RnGcPolizaLineasTblUpdated"));
    }
    
    public void eliminarLinea() {
        persistLinea(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RnGcPolizaLineasTblDeleted"));
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }
    
    public void editar(){
        System.out.println("selected: " + selected + " receptor: " + selected.getReceptor());
        try{
            if (actualizarNumeroPoliza()) {
                System.out.println("Dentro de If --------");
                setEmbeddableKeys();
                getFacade().edit(selected);
                System.out.println("Edito encabezado de poliza: " + selected.getId());
                if(!itemsPolizaLineas.isEmpty() && itemsPolizaLineas != null){
                    for(RnGcPolizaLineasTbl linea : itemsPolizaLineas){
                        System.out.println("linea: "+linea.getId() + " poliza: " + selected);
                        linea.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                        linea.setUltimaFechaActualizacion(new Date());
                        polizaLineasFacade.edit(linea);
                        System.out.println("Creo nuevo aciento");
                    }
                }
                obtenerpolizasPorUsuario();
            }
            itemsPolizaLineas = new ArrayList<>();
            selected = new RnGcPolizaHeaderTbl();
        }catch(Exception e){
            System.out.print("ErrorEditarPoliza: "+ e.getLocalizedMessage());
        }
    }
    
    public void crear(){
        System.out.println("selected: " + selected + " receptor: " + selected.getReceptor());
        try{
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
        }catch(Exception e){
            System.out.print("ErrorCrearPoliza: "+ e.getLocalizedMessage());
        }
    }
    
    public void guardarAciento(){
        System.out.println("Metodo guardar asiento");
        System.out.println("Lista con asientos "+itemsPolizaLineas);
        if(!itemsPolizaLineas.isEmpty() && itemsPolizaLineas != null){
            for(RnGcPolizaLineasTbl linea : itemsPolizaLineas){
                System.out.println("linea: "+linea.getId() + " poliza: " + selected);
                linea.setSucursal("-");
                linea.setCreadoPor(usuarioFirmado.obtenerIdUsuario());
                linea.setFechaCreacion(new Date());
                linea.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
                linea.setUltimaFechaActualizacion(new Date());
                linea.setPolizaHeaderId(selected);
                linea = polizaLineasFacade.refreshFromDB(linea);
                System.out.println("linea: "+linea.getId());
                System.out.println("Creo nuevo aciento");
            }
        }
    }
    
    public void onAddNew() {
        // Add one new car to the table:
        System.out.println("Preparando Para agregar asiento");
        try{
            RnGcPolizaLineasTbl polizaLinea = new RnGcPolizaLineasTbl();
            obtenerCatalogoCuenta();
            polizaLinea.setAbono(0.0);
            polizaLinea.setCargo(0.0);
            polizaLinea.setCatalogoCuentasId(catalogodeCuentas);
            polizaLinea.setConcepto(selected.getConcepto());
            polizaLinea.setId((int) (Math.random() * 999999999));
            itemsPolizaLineas.add(polizaLinea);
            System.out.println("Lista lineas "+itemsPolizaLineas);
            FacesMessage msg = new FacesMessage("Nuevo asiento agregado correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }catch(Exception e){
            System.out.println("Error en añadir asiento: " + e.getLocalizedMessage() + " | " + e.toString());
        }
    }
    
    public void obtenerCatalogoCuenta(){
        catalogodeCuentas = catalogoCuentasFacade.obtenerCuentasCreadoPor(usuarioFirmado.obtenerIdUsuario()).get(0);
    }
    
    public void calcularSuma() {
        cargos = 0.0;
        abonos = 0.0;
        diferencia = 0.0;
        for (int i = 0; i < itemsPolizaLineas.size(); i++) {
            RnGcPolizaLineasTbl item = itemsPolizaLineas.get(i);
            cargos = cargos + item.getCargo();
            abonos = abonos + item.getAbono();
        }
        diferencia = cargos - abonos;
        System.out.println("Cargos: " + cargos + " || Abonos:" + abonos);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edición Cancelada");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Editado Correctamente");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        //updateLinea();
        calcularSuma();
    }
    
    //Nuevo Reporte de reporte de Recargos
    public void generarPolizaPdf(ActionEvent actionEvent) throws JRException, IOException, SQLException, NamingException {
        System.out.println("Entro a generar la polia en pdf");
        Connection con = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:app/GC_Produccion");
            con = ds.getConnection();
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(RnGcPolizaHeaderTbl.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Crea el Map para setear los valores de la ficha
        Map<String, Object> parametros = new HashMap<String, Object>();

        parametros.put("polizaHeaderId", selected.getId());
        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/Reports/Poliza.jasper"));
        //Llena el reporte
        //JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JREmptyDataSource());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, con);
        System.out.println("Llena el reporte:" + jasper.getPath() + "|" + jasperPrint.toString());

        //Imprime la poliza
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; fileName=Poliza_" + ".pdf");

        ServletOutputStream stream = response.getOutputStream();

        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
        System.out.println("Realizo exportManager");

        stream.flush();
        stream.close();
        try {
            if (!con.isClosed()) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(RnGcPolizaHeaderTbl.class.getName()).log(Level.SEVERE, null, ex);
        }

        FacesContext.getCurrentInstance().responseComplete();
        System.out.println("responseComplete");
    }

    public List<RnGcPolizaHeaderTbl> obtenerpolizasPorUsuario() {
        usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        System.out.println("Entró a obtenerPolizasPorUsuario con el usuario: " + usuarioId.getNombreCompleto());
        listaPolizasPorUsuario = ejbFacade.obtenerListaPolizas(usuarioId);
        System.out.println("El tamaño de la listaTipoPolizaPorUsuario es: " + listaPolizasPorUsuario.size());
        return listaPolizasPorUsuario;

    }

    public void obtenerNumeroPoliza() throws ParseException {
        System.out.println("Entró a obtener el numero de la poliza con el id: " + selected.getTipoPolizaId().getId());
        RnGcTipoPoliza tipopoliza = null;
        obtenerFechas();
        if (selected.getTipoPolizaId() != null) {
            tipopoliza = tipoPolizaFacade.tipoPoliza(selected.getTipoPolizaId());
            if (tipopoliza.getFechaFinSecuencia().before(fechaActual)) {
                System.out.println("Entro a actualizar la secuencia porque la fecha de vigencia ya finalizo");
                tipopoliza.setNumeroSecuencia(0);
                tipopoliza.setFechaFinSecuencia(ultimoDia);
                try {
                    tipoPolizaFacade.edit(tipopoliza);
                    tipopoliza = tipoPolizaFacade.tipoPoliza(selected.getTipoPolizaId());
                    System.out.println("Se actualizo la secuencia correctamente");
                } catch (Exception e) {
                    System.out.println("error al actualizar la secuencia");
                }

            }
            selected.setNumeroPoliza(tipopoliza.getNumeroSecuencia() + 1);
            System.out.println("El numero de poliza asignando es: " + selected.getNumeroPoliza());
        } else if (selected.getTipoPolizaId().equals("")) {
            selected.setNumeroPoliza(0);
        }

    }

    public boolean actualizarNumeroPoliza() {
        System.out.println("Entro a Actualizar el numero de la poliza de tipo: " + selected.getTipoPolizaId().getDescripcion());
        try {
            RnGcTipoPoliza tipoPoliza = new RnGcTipoPoliza();
            tipoPoliza = selected.getTipoPolizaId();
            int secuencia = tipoPoliza.getNumeroSecuencia() + 1;
            System.out.println("El numero de secuencia a Actualizar es: " + secuencia);
            tipoPoliza.setNumeroSecuencia(secuencia);
            tipoPoliza.setUltimaActualizacionPor(usuarioFirmado.obtenerIdUsuario());
            tipoPoliza.setUltimaFechaActualizacion(new Date());
            tipoPoliza.setId(selected.getTipoPolizaId().getId());
            tipoPolizaFacade.edit(tipoPoliza);
            System.out.println("La secuencia se actualizo correctamente");

            return true;
        } catch (Exception e) {
            JsfUtil.addErrorMessage("No se pudo actualizar la secuencia para el tipo de poliza seleccionado");
            return false;
        }
    }

    public void obtenerFechas() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//Fecha actual
        Calendar calendar = Calendar.getInstance();
        String hoy = sdf.format(calendar.getTime());
        fechaActual = sdf.parse(hoy);
        System.out.println("Fecha Actual:" + fechaActual);

//A la fecha actual le pongo el día 1
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String diaUno = sdf.format(calendar.getTime());
        primerDia = sdf.parse(diaUno);
        System.out.println("Primer día del mes actual:" + primerDia);

        //Se le agrega 1 mes 
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        System.out.println("1-Último día del mes" + calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String ultimo = sdf.format(calendar.getTime());
        ultimoDia = sdf.parse(ultimo);
        System.out.println("ultimo día del mes" + ultimoDia);
    }

    public void obtenerMoneda() {
        RnGcMonedasTbl tipoMoneda = new RnGcMonedasTbl();
        tipoMoneda = monedasFacade.obtenerMoneda();
        selected.setTipoMoneda(tipoMoneda);
        selected.setTipoCambio(1.0);
    }

}
