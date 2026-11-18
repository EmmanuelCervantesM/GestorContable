package mx.com.rocketnegocios.filter;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mx.com.rocketnegocios.util.UsuarioFirmado;

// "/*": todo pasa por aquí. Lo que atiende el FacesServlet ("/faces/...") se autoriza; fuera de
// él solo se bloquea el código fuente de las vistas (.xhtml) y lo demás (imágenes, css) sigue igual.
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
public class AuthorizationFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(AuthorizationFilter.class.getName());

    // Pantallas exclusivas de ADMINISTRADOR
    private static final String[] RUTAS_SOLO_ADMIN = {
        "/serv/rnGcUsuariosTbl/",
        "/serv/rnGcPerfilesTbl/",
        "/serv/rnGcUsuariosPerfilesTbl/",
        "/serv/rnGcMenusTbl/",
        "/serv/rnGcMenuslineasTbl/",
        "/serv/rnGcFuncionesTbl/",
        "/serv/rnGcTerminosCondicionesTbl/",
        "/serv/rnGcListaNegraTbl/",
        // CTR-12: alta, edición y baja de las listas negras (solo el administrador las maneja)
        "/serv/rnGcTipoListaNegraTbl/"
    };

    // Excepciones: pantallas dentro de esas carpetas que SI puede usar cualquier usuario logueado
    private static final String[] EXCEPCIONES = {
        "/serv/rnGcUsuariosTbl/cambioPwd.xhtml",
        // CTR-12 (v2.80): la consultora/usuario solo recibe avisos de sus RFC;
        // la lista completa, la importación y la edición siguen siendo de admin.
        "/serv/rnGcListaNegraTbl/Comparacion.xhtml",
        "/serv/rnGcListaNegraTbl/SemaforoListaNegra.xhtml",
        "/serv/rnGcListaNegraTbl/AlertaListaNegra.xhtml"
    };

    public AuthorizationFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                    FilterChain chain) throws IOException, ServletException {
        HttpServletRequest reqt = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        boolean permitir;
        try {
            permitir = autorizar(reqt, resp);
        } catch (RuntimeException e) {
            // Si no se pudo decidir, no se deja pasar (antes se tragaba el error y la página
            // salía en blanco, o se seguía de largo).
            LOG.log(Level.SEVERE, "AuthorizationFilter: no se pudo autorizar " + reqt.getRequestURI(), e);
            if (!resp.isCommitted()) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            return;
        }
        if (permitir) {
            // Fuera del try: los errores de la propia pantalla los maneja y registra el
            // contenedor como siempre, en lugar de perderse en un System.out.
            chain.doFilter(request, response);
        }
    }

    /** Decide si la petición sigue; si no, ya dejó la redirección o el error en la respuesta. */
    private boolean autorizar(HttpServletRequest reqt, HttpServletResponse resp) throws IOException {
        // Se compara la vista ya decodificada y normalizada, no la URI cruda: con la URI
        // cruda y contains(), rutas como "/serv/%72nGcUsuariosTbl/", "/serv//rnGc..." o
        // ".../List.xhtml;/login.xhtml" abrían pantallas de admin (o sin sesión).
        String vista = vistaNormalizada(reqt);

        if (!"/faces".equals(reqt.getServletPath())) {
            // Sin el FacesServlet, el servidor regresaba el .xhtml tal cual (código fuente de
            // las vistas, incluso de login y public/). Se compara en minúsculas: en Windows
            // "List.XHTML" también lo sirve.
            if (vista.endsWith(".xhtml")) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return false;
            }
            return true;
        }

        if (vista.equals("/login.xhtml")
                        || vista.startsWith("/public/")
                        || vista.startsWith("/javax.faces.resource/")) {
            return true;
        }

        HttpSession ses = reqt.getSession(false);
        boolean logueado = ses != null && ses.getAttribute("username") != null;
        if (!logueado) {
            resp.sendRedirect(reqt.getContextPath() + "/faces/login.xhtml");
            return false;
        }

        if (esRutaSoloAdmin(vista) && !esExcepcion(vista)
                        && !UsuarioFirmado.esAdministrador(ses.getAttribute("listaPerfiles"))) {
            resp.sendRedirect(reqt.getContextPath() + "/faces/admin.xhtml");
            return false;
        }
        return true;
    }

    /**
     * Ruta de la vista dentro de la aplicación, sin "/faces", sin parámetros de ruta (";..."),
     * sin "//", "." ni "..", y en minúsculas (en Windows el sistema de archivos no distingue
     * mayúsculas). getServletPath() y getPathInfo() ya vienen decodificados por el contenedor.
     */
    static String vistaNormalizada(HttpServletRequest reqt) {
        String ruta = (reqt.getServletPath() == null ? "" : reqt.getServletPath())
                + (reqt.getPathInfo() == null ? "" : reqt.getPathInfo());
        Deque<String> partes = new ArrayDeque<>();
        for (String parte : ruta.replace('\\', '/').split("/")) {
            int puntoYComa = parte.indexOf(';');
            if (puntoYComa >= 0) {
                parte = parte.substring(0, puntoYComa);
            }
            if (parte.isEmpty() || parte.equals(".")) {
                continue;
            }
            if (parte.equals("..")) {
                partes.pollLast();
                continue;
            }
            partes.addLast(parte);
        }
        String vista = ("/" + String.join("/", partes)).toLowerCase(Locale.ROOT);
        // Mojarra quita el prefijo repetido ("/faces/faces/serv/...") y sirve la vista igual,
        // así que aquí también se quita todas las veces que aparezca.
        while (vista.startsWith("/faces/")) {
            vista = vista.substring("/faces".length());
        }
        return vista;
    }

    private boolean esRutaSoloAdmin(String vista) {
        for (String ruta : RUTAS_SOLO_ADMIN) {
            if (vista.startsWith(ruta.toLowerCase(Locale.ROOT))) {
                return true;
            }
        }
        return false;
    }

    private boolean esExcepcion(String vista) {
        for (String excepcion : EXCEPCIONES) {
            if (vista.equals(excepcion.toLowerCase(Locale.ROOT))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {

    }    
}
