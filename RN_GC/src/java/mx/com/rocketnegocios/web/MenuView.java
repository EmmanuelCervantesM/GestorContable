package mx.com.rocketnegocios.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import mx.com.rocketnegocios.beans.RnGcMenusTblFacade;
import mx.com.rocketnegocios.beans.RnGcMenuslineasTblFacade;
import mx.com.rocketnegocios.beans.RnGcUsuariosPerfilesTblFacade;
import mx.com.rocketnegocios.beans.RnGcUsuariosTblFacade;
import mx.com.rocketnegocios.entities.RnGcFuncionesTbl;
import mx.com.rocketnegocios.entities.RnGcMenusTbl;
import mx.com.rocketnegocios.entities.RnGcMenuslineasTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosPerfilesTbl;
import mx.com.rocketnegocios.entities.RnGcUsuariosTbl;
import mx.com.rocketnegocios.util.UsuarioFirmado;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

@ManagedBean
public class MenuView {

    @EJB
    private RnGcUsuariosPerfilesTblFacade usuarioPerfilFacade;

    @EJB
    private RnGcMenuslineasTblFacade menufuncionesFacade;

    @EJB
    private RnGcMenusTblFacade menuFacade;

    @EJB
    private RnGcUsuariosTblFacade usuarioFacade;

    private List<RnGcUsuariosPerfilesTbl> listaPerfilesUsuario = null;
    private List<RnGcMenuslineasTbl> listaMenuslineas = null;
    private RnGcUsuariosTbl usuarioId;
    private RnGcMenusTbl menuId;
    private UsuarioFirmado usuarioFirmado = new UsuarioFirmado();
    private List<RnGcFuncionesTbl> funsiones;

    private MenuModel model;

    @PostConstruct
    public void init() {

        String listaPerfiles = "";
        model = new DefaultMenuModel();

        //Primer submenu
        /* DefaultSubMenu primerSubmenu = new DefaultSubMenu("Acciones");

        DefaultMenuItem item = new DefaultMenuItem("Cargar Transacciones");
        item.setOutcome("/serv/rnGcCfdisTbl/cargarArchivos");
        item.setIcon("ui-icon-folder-open");
        primerSubmenu.addElement(item);*/
        //Segundo submenu
        /*DefaultSubMenu segundoSubmenu = new DefaultSubMenu("Emitidos y Recibidos");
        item = new DefaultMenuItem("Emitidos");
        item.setOutcome("/serv/rnGcCfdisTbl/ingresos_List");
        segundoSubmenu.addElement(item);
        item = new DefaultMenuItem("Recibidos");
        item.setOutcome("/serv/rnGcCfdisTbl/egresos_List");
        segundoSubmenu.addElement(item);*/
        //Tercer submenu
        /*DefaultSubMenu tercerSubmenu = new DefaultSubMenu("Facturas");
        item = new DefaultMenuItem("Facturas (Emitidos y Recibidos)");
        item.setOutcome("/serv/rnGcCfdisTbl/List");
        item.setIcon("ui-icon-document");
        tercerSubmenu.addElement(item);
        item = new DefaultMenuItem("Emitidos");
        item.setOutcome("/serv/rnGcCfdisTbl/ingresos_List");
        item.setIcon("ui-icon-document");
        tercerSubmenu.addElement(item);
        item = new DefaultMenuItem("Recibidos");
        item.setOutcome("/serv/rnGcCfdisTbl/egresos_List");
        item.setIcon("ui-icon-document");
        tercerSubmenu.addElement(item);
        item = new DefaultMenuItem("Facturación");
        item.setOutcome("/serv/rnGcCfdisTbl/Facturas_1_1");
        item.setIcon("ui-icon-script");
        tercerSubmenu.addElement(item);
        item = new DefaultMenuItem("Clientes");
        item.setOutcome("/serv/rnGcPersonasTbl/clientes_List");
        item.setIcon("ui-icon-person");
        tercerSubmenu.addElement(item);
        item = new DefaultMenuItem("Productos/Servicios");
        item.setOutcome("/serv/rnGcProductserviciosTbl/List");
        item.setIcon("ui-icon-cart");
        tercerSubmenu.addElement(item);
        item = new DefaultMenuItem("Impuestos Locales");
        item.setOutcome("/serv/rnGcImpuestosLocalesTbl/List");
        item.setIcon("ui-icon-bookmark");
        tercerSubmenu.addElement(item);
        item = new DefaultMenuItem("Certificados");
        item.setOutcome("/serv/rnGcCertificadosTbl/List");
        item.setIcon("ui-icon-unlocked");
        tercerSubmenu.addElement(item);

        //Cuarto submenu
        DefaultSubMenu cuartoSubmenu = new DefaultSubMenu("Reportes");
        item = new DefaultMenuItem("Reportes");
        item.setOutcome("/serv/rnGcCfdisTbl/report");
        item.setIcon("ui-icon-document-b");
        cuartoSubmenu.addElement(item);

        //Quinto submenu
        DefaultSubMenu quintoSubmenu = new DefaultSubMenu("Usuarios");
        item = new DefaultMenuItem("Usuarios");
        item.setOutcome("/serv/rnGcUsuariosTbl/List");
        item.setIcon("ui-icon-person");
        quintoSubmenu.addElement(item);
        item = new DefaultMenuItem("Perfiles");
        item.setOutcome("/serv/rnGcPerfilesTbl/List");
        quintoSubmenu.addElement(item);
        item = new DefaultMenuItem("Menus");
        item.setOutcome("/serv/rnGcMenusTbl/List");
        quintoSubmenu.addElement(item);
        item = new DefaultMenuItem("Menús Lineas");
        item.setOutcome("/serv/rnGcMenuslineasTbl/List");
        quintoSubmenu.addElement(item);
        item = new DefaultMenuItem("Funciones");
        item.setOutcome("/serv/rnGcFuncionesTbl/List");
        quintoSubmenu.addElement(item);//
        
        //QuintoPuntoCinco
        DefaultSubMenu quintoPuntoCinco = new DefaultSubMenu("Usuarios");
        item = new DefaultMenuItem("Usuarios");
        item.setOutcome("/serv/rnGcUsuariosTbl/List");
        item.setIcon("ui-icon-person");
        quintoPuntoCinco.addElement(item);

        //QuintoCinco submenu
        DefaultSubMenu quintoCincoSubmenu = new DefaultSubMenu("Direcciones");
        item = new DefaultMenuItem("Direcciones Clientes");
        item.setOutcome("/serv/rnGcDireccionesTbl/List");
        item.setIcon("ui-icon-person");
        quintoCincoSubmenu.addElement(item);
        item = new DefaultMenuItem("Direcciones Usuarios");
        item.setOutcome("/serv/rnGcDireccionesUsuariosTbl/List");
        quintoCincoSubmenu.addElement(item);

        //Sexto submenu
        DefaultSubMenu sextoSubmenu = new DefaultSubMenu("Catálogos");
        sextoSubmenu.setIcon("ui-icon-folder-collapsed");
        item = new DefaultMenuItem("Usos");
        item.setOutcome("/serv/rnGcCatalogosusosTbl/List");
        sextoSubmenu.addElement(item);
        item = new DefaultMenuItem("Códigos Postales");
        item.setOutcome("/serv/rnGcCodigospostalesTbl/List");
        sextoSubmenu.addElement(item);
        item = new DefaultMenuItem("Formas de Pago");
        item.setOutcome("/serv/rnGcFormaspagosTbl/List");
        sextoSubmenu.addElement(item);
        item = new DefaultMenuItem("Monedas");
        item.setOutcome("/serv/rnGcMonedasTbl/List");
        sextoSubmenu.addElement(item);
        item = new DefaultMenuItem("Paises");
        item.setOutcome("/serv/rnGcPaisesTbl/List");
        sextoSubmenu.addElement(item);
        item = new DefaultMenuItem("Regimen Fiscal");
        item.setOutcome("/serv/rnGcRegimenfiscalTbl/List");
        sextoSubmenu.addElement(item);
        item = new DefaultMenuItem("Tipo de Relación");
        item.setOutcome("/serv/rnGcTiporelacionTbl/List");
        sextoSubmenu.addElement(item);
        item = new DefaultMenuItem("Tasa o Cuota");
        item.setOutcome("/serv/rnGcTasacuotaTbl/List");
        sextoSubmenu.addElement(item);
        item = new DefaultMenuItem("Impuestos");
        item.setOutcome("/serv/rnGcImpuestosTbl/List");
        sextoSubmenu.addElement(item);
        item = new DefaultMenuItem("Métodos de Pago");
        item.setOutcome("/serv/rnGcMetodospagosTbl/List");
        sextoSubmenu.addElement(item);
        item = new DefaultMenuItem("Tipo de Factor");
        item.setOutcome("/serv/rnGcTipofactorTbl/List");
        sextoSubmenu.addElement(item);
        item = new DefaultMenuItem("Tipos de Comprobante");
        item.setOutcome("/serv/rnGcTiposcfdisTbl/List");
        sextoSubmenu.addElement(item);
        item = new DefaultMenuItem("Aduanas");
        item.setOutcome("/serv/rnGcAduanasTbl/List");
        sextoSubmenu.addElement(item);*/
        //Leer variables de sesion
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, Object> map = facesContext.getExternalContext().getSessionMap();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            //System.out.println("key:value| " + key + ": " + String.valueOf(value));
            if (key.equals("listaPerfiles")) {
                listaPerfiles = String.valueOf(value);
            }
        }

        usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        listaPerfilesUsuario = usuarioPerfilFacade.obtenerPerfilesPorUsuario(usuarioId);
        //for (int i = 0; i < listaPerfilesUsuario.size(); i++) {
        /* if (listaPerfilesUsuario.get(i).getFechaFinal() != null) {
              if (listaPerfilesUsuario.get(i).getPerfilesId().getPerfilNombre().contains("ADMINISTRADOR") && listaPerfilesUsuario.get(i).getFechaFinal().after(new Date())) {
                    System.out.println("Administrador con fecha");
                    model.addElement(primerSubmenu);
                    //model.addElement(segundoSubmenu);
                    model.addElement(tercerSubmenu);
                    model.addElement(cuartoSubmenu);
                    model.addElement(quintoSubmenu);
                    model.addElement(sextoSubmenu);
                }
                if (listaPerfilesUsuario.get(i).getPerfilesId().getPerfilNombre().contains("DESPACHO") && listaPerfilesUsuario.get(i).getFechaFinal().after(new Date())) {
                    System.out.println("Despacho con fecha");
                    model.addElement(quintoPuntoCinco);
                }
                if (listaPerfilesUsuario.get(i).getPerfilesId().getPerfilNombre().contains("CONTRIBUYENTE") && listaPerfilesUsuario.get(i).getFechaFinal().after(new Date())) {
                    System.out.println("Contribuyente con fecha");
                    model.addElement(primerSubmenu);
                    //model.addElement(segundoSubmenu);
                    model.addElement(tercerSubmenu);
                    model.addElement(cuartoSubmenu);
                }
            } else {
                if (listaPerfilesUsuario.get(i).getPerfilesId().getPerfilNombre().contains("ADMINISTRADOR")) {
                    System.out.println("Administrador sin fecha");
                    model.addElement(primerSubmenu);
                    //model.addElement(segundoSubmenu);
                    model.addElement(tercerSubmenu);
                    model.addElement(cuartoSubmenu);
                    model.addElement(quintoSubmenu);
                    model.addElement(sextoSubmenu);
                }
                if (listaPerfilesUsuario.get(i).getPerfilesId().getPerfilNombre().contains("DESPACHO")) {
                    System.out.println("Despacho sin fecha");
                    model.addElement(quintoPuntoCinco);
                }
                if (listaPerfilesUsuario.get(i).getPerfilesId().getPerfilNombre().contains("CONTRIBUYENTE")) {
                    System.out.println("Contribuyente sin fecha");
                    model.addElement(primerSubmenu);
                    //model.addElement(segundoSubmenu);
                    model.addElement(tercerSubmenu);
                    model.addElement(cuartoSubmenu);
                }else{*/
        // 
        asignacionMenuModuloFunciones();
        //}
        //}

    }

    /*if (listaPerfiles.contains("ADMINISTRADOR")) {
            model.addElement(primerSubmenu);
            model.addElement(segundoSubmenu);
            model.addElement(tercerSubmenu);
            model.addElement(cuartoSubmenu);
            model.addElement(quintoSubmenu);
            model.addElement(sextoSubmenu);
            //model.addElement(quintoCincoSubmenu);
        } else {
            if (listaPerfiles.contains("DESPACHO")) {
                model.addElement(quintoSubmenu);
            }
            if (listaPerfiles.contains("CONTRIBUYENTE")) {
                model.addElement(primerSubmenu);
                model.addElement(segundoSubmenu);
                model.addElement(tercerSubmenu);
                model.addElement(cuartoSubmenu);
                //model.addElement(septimoSubmenu);
            }
        }//*/
    //   asignacionMenuModuloFunciones();
    // }
    public MenuModel getModel() {
        return model;
    }

    public void save() {
        addMessage("Success", "Data saved");
    }

    public void update() {
        addMessage("Success", "Data Updated");
    }

    public void delete() {
        addMessage("Success", "Data deleted");
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /*public void menuRN() {
        model = new DefaultMenuModel();

        //Primer submenu
        DefaultSubMenu primerSubmenu = new DefaultSubMenu("Acciones");
        DefaultMenuItem item = new DefaultMenuItem("Cargar Transacciones");
        item.setOutcome("/serv/rnGcCfdisTbl/cargarArchivos");
        primerSubmenu.addElement(item);
        item = new DefaultMenuItem("Cargar Facturas");
        item.setOutcome("/serv/rnGcCfdisTbl/cargarArchivos");
        primerSubmenu.addElement(item);

        //Segundo submenu
        DefaultSubMenu segundoSubmenu = new DefaultSubMenu("Ingresos y Egresos");
        item = new DefaultMenuItem("Ingresos");
        item.setOutcome("/serv/rnGcCfdisTbl/ingresos_List");
        segundoSubmenu.addElement(item);
        item = new DefaultMenuItem("Egresos");
        item.setOutcome("/serv/rnGcCfdisTbl/egresos_List");
        segundoSubmenu.addElement(item);

        //Tercer submenu
        DefaultSubMenu tercerSubmenu = new DefaultSubMenu("Facturas");
        item = new DefaultMenuItem("Facturas");
        item.setOutcome("/serv/rnGcCfdisTbl/List");
        tercerSubmenu.addElement(item);

        //Cuarto submenu
        DefaultSubMenu cuartoSubmenu = new DefaultSubMenu("Reportes");
        item = new DefaultMenuItem("Reportes");
        item.setOutcome("/serv/rnGcCfdisTbl/report");
        cuartoSubmenu.addElement(item);

        //Quinto submenu
        DefaultSubMenu quintoSubmenu = new DefaultSubMenu("Usuarios");
        item = new DefaultMenuItem("Usuarios");
        item.setOutcome("/serv/rnGcUsuariosTbl/List");
        quintoSubmenu.addElement(item);
        item = new DefaultMenuItem("Perfiles de Usuario");
        item.setOutcome("/serv/rnGcUsuariosPerfilesTbl/List");
        quintoSubmenu.addElement(item);
        item = new DefaultMenuItem("Perfiles");
        item.setOutcome("/serv/rnGcPerfilesTbl/List");
        quintoSubmenu.addElement(item);
        item = new DefaultMenuItem("Menus");
        item.setOutcome("/serv/rnGcMenusTbl/List");
        quintoSubmenu.addElement(item);
        item = new DefaultMenuItem("Menús Lineas");
        item.setOutcome("/serv/rnGcMenuslineasTbl/List");
        quintoSubmenu.addElement(item);
        item = new DefaultMenuItem("Funciones");
        item.setOutcome("/serv/rnGcFuncionesTbl/List");
        quintoSubmenu.addElement(item);

        //Sexto submenu
        DefaultSubMenu sextoSubmenu = new DefaultSubMenu("Personas");
        item = new DefaultMenuItem("Clientes");
        item.setOutcome("/serv/rnGcPersonasTbl/clientes_List");
        sextoSubmenu.addElement(item);
        item = new DefaultMenuItem("Proveedores");
        item.setOutcome("/serv/rnGcPersonasTbl/Proveedores_List");
        sextoSubmenu.addElement(item);

        //Agregar al modelo dependiendo el perfil asignado al usuario
        model.addElement(primerSubmenu);
        model.addElement(segundoSubmenu);
        model.addElement(tercerSubmenu);
        model.addElement(cuartoSubmenu);
        model.addElement(quintoSubmenu);
        model.addElement(sextoSubmenu);

    }*/
    public void asignacionMenuModuloFunciones() {
        model = new DefaultMenuModel();
        usuarioId = usuarioFacade.obtenerUsuarioPorId(usuarioFirmado.obtenerIdUsuario());
        listaPerfilesUsuario = usuarioPerfilFacade.obtenerPerfilesPorUsuario(usuarioId);
        for (int i = 0; i < listaPerfilesUsuario.size(); i++) {
            if (listaPerfilesUsuario.get(i).getFechaFinal() == null || validarFecha(listaPerfilesUsuario.get(i).getFechaFinal()) >= 0) {
                if (listaPerfilesUsuario.get(i).getPerfilesId().getMenuId().getId() != null) {
                    listaMenuslineas = menufuncionesFacade.obtenerFuncionesPorMenu(listaPerfilesUsuario.get(i).getPerfilesId().getMenuId());
                    DefaultSubMenu moduloSubmenu = new DefaultSubMenu(listaPerfilesUsuario.get(i).getPerfilesId().getMenuId().getMenuNombre());
                    for (int j = 0; j < listaMenuslineas.size(); j++) {
                        if (listaMenuslineas.get(j).getFuncionesId() != null) {
                            DefaultMenuItem item = new DefaultMenuItem(listaMenuslineas.get(j).getFuncionesId().getFuncionNombre());
                            // System.out.println("El nombre de la Funcion es (item): " + item.getTitle());
                            item.setOutcome(listaMenuslineas.get(j).getFuncionesId().getUrlFuncion());
                            //System.out.println("La url de la funcion es (item): " + item.getOutcome());
                            item.setIcon(listaMenuslineas.get(j).getFuncionesId().getIconoFuncion());
                            moduloSubmenu.addElement(item);
                        }
                    }
                    model.addElement(moduloSubmenu);
                }
            }
        }
    }
    
    public Integer validarFecha(Date fechaFinal){
        Integer fecha = 0;
        if(fechaFinal != null){
            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaFinal);
            cal.add(Calendar.DAY_OF_WEEK, 1);
            fecha = cal.getTime().compareTo(new Date());
        }
        return fecha;
    }
}
