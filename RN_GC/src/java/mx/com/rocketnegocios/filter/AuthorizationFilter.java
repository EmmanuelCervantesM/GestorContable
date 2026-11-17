package mx.com.rocketnegocios.filter;

import java.io.IOException;
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

@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class AuthorizationFilter implements Filter {

    // Pantallas exclusivas de ADMINISTRADOR
    private static final String[] RUTAS_SOLO_ADMIN = {
        "/serv/rnGcUsuariosTbl/",
        "/serv/rnGcPerfilesTbl/",
        "/serv/rnGcUsuariosPerfilesTbl/",
        "/serv/rnGcMenusTbl/",
        "/serv/rnGcMenuslineasTbl/",
        "/serv/rnGcFuncionesTbl/",
        "/serv/rnGcTerminosCondicionesTbl/",
        "/serv/rnGcListaNegraTbl/"
    };

    // Excepciones: pantallas dentro de esas carpetas que SI puede usar cualquier usuario logueado
    private static final String[] EXCEPCIONES = {
        "/serv/rnGcUsuariosTbl/cambioPwd.xhtml"
    };

    public AuthorizationFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                    FilterChain chain) throws IOException, ServletException {
        try {

            HttpServletRequest reqt = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession ses = reqt.getSession(false);

            String reqURI = reqt.getRequestURI();
            boolean logueado = ses != null && ses.getAttribute("username") != null;

            if (reqURI.indexOf("/login.xhtml") >= 0
                            || reqURI.indexOf("/public/") >= 0
                            || reqURI.contains("javax.faces.resource")) {
                chain.doFilter(request, response);
                return;
            }

            if (!logueado) {
                resp.sendRedirect(reqt.getContextPath() + "/faces/login.xhtml");
                return;
            }

            if (esRutaSoloAdmin(reqURI) && !esExcepcion(reqURI) && !esAdministrador(ses)) {
                resp.sendRedirect(reqt.getContextPath() + "/faces/admin.xhtml");
                return;
            }

            chain.doFilter(request, response);
        } catch (Exception e) {
                System.out.println(e.getMessage());
        }
    }

    private boolean esRutaSoloAdmin(String reqURI) {
        for (String ruta : RUTAS_SOLO_ADMIN) {
            if (reqURI.contains(ruta)) {
                return true;
            }
        }
        return false;
    }

    private boolean esExcepcion(String reqURI) {
        for (String ruta : EXCEPCIONES) {
            if (reqURI.contains(ruta)) {
                return true;
            }
        }
        return false;
    }

    private boolean esAdministrador(HttpSession ses) {
        Object listaPerfiles = ses.getAttribute("listaPerfiles");
        return listaPerfiles != null && String.valueOf(listaPerfiles).contains("ADMINISTRADOR");
    }

    @Override
    public void destroy() {

    }    
}
