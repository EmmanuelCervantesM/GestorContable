package mx.com.rocketnegocios.beans;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mx.com.rocketnegocios.entities.RnGcListaNegraAvisoTbl;
import mx.com.rocketnegocios.entities.RnGcListaNegraDofTbl;
import mx.com.rocketnegocios.entities.RnGcListaNegraTbl;
import mx.com.rocketnegocios.entities.RnGcPersonasTbl;
import mx.com.rocketnegocios.entities.RnGcTipoListaNegraTbl;
import mx.com.rocketnegocios.util.listanegra.CoincidenciaListaNegra;
import mx.com.rocketnegocios.util.listanegra.DofHttp;
import mx.com.rocketnegocios.util.listanegra.DofParser;
import mx.com.rocketnegocios.util.listanegra.EnlaceDof;
import mx.com.rocketnegocios.util.listanegra.ListaNegraCsv;
import mx.com.rocketnegocios.util.listanegra.TipoListaNegra;

/**
 * CTR-12: carga de listas negras (CSV del SAT para 69-B / 69-B Bis, oficios del DOF para
 * 49 Bis X), cruce contra clientes y proveedores, ligas al DOF y avisos de cambio de estado.
 */
@Stateless
public class ListaNegraService {

    private static final Logger LOG = Logger.getLogger(ListaNegraService.class.getName());
    private static final int LOTE = 500;

    @PersistenceContext(unitName = "RN_GCPU")
    private EntityManager em;

    /** Resultado de una carga. */
    public static class Resumen {

        private int nuevos;
        private int actualizados;
        private int cambiosEstado;
        private int bajas;
        private int avisos;

        public int getNuevos() {
            return nuevos;
        }

        public int getActualizados() {
            return actualizados;
        }

        public int getCambiosEstado() {
            return cambiosEstado;
        }

        public int getBajas() {
            return bajas;
        }

        public int getAvisos() {
            return avisos;
        }
    }

    /** Resultado de revisar el 49 Bis X en una fecha del DOF. */
    public static class Resumen49BisX {

        private final List<String> cargados = new ArrayList<>();
        private final List<String> omitidos = new ArrayList<>();
        private final List<String> errores = new ArrayList<>();
        private int registros;
        private int avisos;

        public List<String> getCargados() {
            return cargados;
        }

        public List<String> getOmitidos() {
            return omitidos;
        }

        public List<String> getErrores() {
            return errores;
        }

        public int getRegistros() {
            return registros;
        }

        public int getAvisos() {
            return avisos;
        }
    }

    private static class Cambio {

        final String rfc;
        final String anterior;
        final String nuevo;

        Cambio(String rfc, String anterior, String nuevo) {
            this.rfc = rfc;
            this.anterior = anterior;
            this.nuevo = nuevo;
        }
    }

    // ------------------------------------------------------------------ carga

    /**
     * Carga una lista dejando un registro vigente por RFC: los registros que ya existían en
     * la misma lista se actualizan, los RFC nuevos se insertan y, si {@code listadoCompleto}
     * (un CSV completo del SAT), los RFC que ya no vienen en el archivo se inactivan. Cada
     * entrada, cambio de estado o salida genera un aviso para los usuarios que tienen ese RFC
     * como cliente o proveedor.
     */
    public Resumen importar(TipoListaNegra tipo, List<RnGcListaNegraTbl> filas, int idUsuario,
            String folio, Date fechaPublicacion, boolean listadoCompleto) {
        Date ahora = new Date();
        RnGcTipoListaNegraTbl cabecera = new RnGcTipoListaNegraTbl();
        cabecera.setTipoLista(tipo.getEtiqueta());
        cabecera.setFolioPublicacion(folio);
        cabecera.setFechaPublicacion(fechaPublicacion);
        cabecera.setCreadoPor(idUsuario);
        cabecera.setFechaCreacion(ahora);
        cabecera.setUltimaActualizacionPor(idUsuario);
        cabecera.setUltimaFechaActualizacion(ahora);
        cabecera.setEstado("A");
        em.persist(cabecera);
        em.flush();

        Map<String, RnGcListaNegraTbl> actuales = new HashMap<>();
        for (RnGcListaNegraTbl ln : em.createNamedQuery("RnGcListaNegraTbl.findActivosByTipoLista", RnGcListaNegraTbl.class)
                .setParameter("tipoLista", tipo.getEtiqueta()).getResultList()) {
            String rfc = ListaNegraCsv.limpiarRfc(ln.getRfc());
            if (actuales.containsKey(rfc)) {
                inactivar(ln, idUsuario, ahora); // duplicado que dejó una carga anterior
            } else {
                actuales.put(rfc, ln);
            }
        }

        Resumen resumen = new Resumen();
        List<Cambio> cambios = new ArrayList<>();
        Set<String> enArchivo = new HashSet<>();
        int pendientes = 0;
        for (RnGcListaNegraTbl fila : filas) {
            String rfc = fila.getRfc();
            enArchivo.add(rfc);
            RnGcListaNegraTbl actual = actuales.get(rfc);
            if (actual == null) {
                fila.setIdTipoLista(cabecera);
                fila.setCreadoPor(idUsuario);
                fila.setFechaCreacion(ahora);
                fila.setUltimaActualizacionPor(idUsuario);
                fila.setUltimaFechaActualizacion(ahora);
                fila.setEstado("A");
                em.persist(fila);
                resumen.nuevos++;
                cambios.add(new Cambio(rfc, null, fila.getSituacionContribuyente()));
            } else {
                String anterior = actual.getSituacionContribuyente();
                copiarDatos(fila, actual);
                actual.setIdTipoLista(cabecera);
                actual.setUltimaActualizacionPor(idUsuario);
                actual.setUltimaFechaActualizacion(ahora);
                resumen.actualizados++;
                if (!Objects.equals(normal(anterior), normal(fila.getSituacionContribuyente()))) {
                    resumen.cambiosEstado++;
                    cambios.add(new Cambio(rfc, anterior, fila.getSituacionContribuyente()));
                }
            }
            if (++pendientes % LOTE == 0) {
                em.flush();
            }
        }
        if (listadoCompleto) {
            for (Map.Entry<String, RnGcListaNegraTbl> e : actuales.entrySet()) {
                if (!enArchivo.contains(e.getKey())) {
                    cambios.add(new Cambio(e.getKey(), e.getValue().getSituacionContribuyente(), null));
                    inactivar(e.getValue(), idUsuario, ahora);
                    resumen.bajas++;
                }
            }
        }
        em.flush();
        resumen.avisos = generarAvisos(tipo, cambios, ahora);
        return resumen;
    }

    private static String normal(String situacion) {
        return situacion == null ? null : situacion.trim().toLowerCase();
    }

    private void inactivar(RnGcListaNegraTbl ln, int idUsuario, Date ahora) {
        ln.setEstado("I");
        ln.setUltimaActualizacionPor(idUsuario);
        ln.setUltimaFechaActualizacion(ahora);
    }

    private static void copiarDatos(RnGcListaNegraTbl de, RnGcListaNegraTbl a) {
        a.setRfc(de.getRfc());
        a.setNombreContribuyente(de.getNombreContribuyente());
        a.setSituacionContribuyente(de.getSituacionContribuyente());
        a.setNumeroFechaOficioGlobalPresuncionSAT(de.getNumeroFechaOficioGlobalPresuncionSAT());
        a.setPublicacionSATpresuntos(de.getPublicacionSATpresuntos());
        a.setNumeroFechaOficioGlobalPresuncionDOF(de.getNumeroFechaOficioGlobalPresuncionDOF());
        a.setPublicacionDOFpresuntos(de.getPublicacionDOFpresuntos());
        a.setNumeroFechaOficioGlobalDesvirtuadoSAT(de.getNumeroFechaOficioGlobalDesvirtuadoSAT());
        a.setPublicacionSATdesvirtuados(de.getPublicacionSATdesvirtuados());
        a.setNumeroFechaOficioGlobalDesvirtuadoDOF(de.getNumeroFechaOficioGlobalDesvirtuadoDOF());
        a.setPublicacionDOFdesvirtuados(de.getPublicacionDOFdesvirtuados());
        a.setNumeroFechaOficioGlobalDefinitivoSAT(de.getNumeroFechaOficioGlobalDefinitivoSAT());
        a.setPublicacionSATdefinitivos(de.getPublicacionSATdefinitivos());
        a.setNumeroFechaOficioGlobalDefinitivoDOF(de.getNumeroFechaOficioGlobalDefinitivoDOF());
        a.setPublicacionDOFdefinitivos(de.getPublicacionDOFdefinitivos());
        a.setNumeroFechaOficioGlobalSentenciaSAT(de.getNumeroFechaOficioGlobalSentenciaSAT());
        a.setPublicacionSATsentencia(de.getPublicacionSATsentencia());
        a.setNumeroFechaOficioGlobalSentenciaDOF(de.getNumeroFechaOficioGlobalSentenciaDOF());
        a.setPublicacionDOFsentencia(de.getPublicacionDOFsentencia());
        a.setOficioResolucion(de.getOficioResolucion());
        a.setNotificacionBuzon(de.getNotificacionBuzon());
        a.setNotificacionEstrados(de.getNotificacionEstrados());
    }

    /** Un aviso por usuario y RFC cuyo cliente o proveedor entró, cambió o salió de la lista. */
    private int generarAvisos(TipoListaNegra tipo, List<Cambio> cambios, Date ahora) {
        if (cambios.isEmpty()) {
            return 0;
        }
        Map<String, Cambio> porRfc = new LinkedHashMap<>();
        for (Cambio c : cambios) {
            porRfc.put(c.rfc, c);
        }
        List<String> rfcs = new ArrayList<>(porRfc.keySet());
        Set<String> generados = new HashSet<>();
        int total = 0;
        for (int i = 0; i < rfcs.size(); i += LOTE) {
            List<String> lote = rfcs.subList(i, Math.min(i + LOTE, rfcs.size()));
            for (RnGcPersonasTbl p : personasPorRfc(lote)) {
                String rfc = ListaNegraCsv.limpiarRfc(p.getRfc());
                Cambio c = porRfc.get(rfc);
                if (c == null || !generados.add(p.getCreadoPor() + "|" + rfc)) {
                    continue;
                }
                RnGcListaNegraAvisoTbl aviso = new RnGcListaNegraAvisoTbl();
                aviso.setIdUsuario(p.getCreadoPor());
                aviso.setIdPersona(p.getId());
                aviso.setRfc(rfc);
                aviso.setNombre(p.getNombre());
                aviso.setTipoLista(tipo.getEtiqueta());
                aviso.setEstadoAnterior(c.anterior);
                aviso.setEstadoNuevo(c.nuevo);
                aviso.setFechaAviso(ahora);
                aviso.setVisto("N");
                em.persist(aviso);
                total++;
            }
        }
        return total;
    }

    private List<RnGcPersonasTbl> personasPorRfc(List<String> rfcs) {
        if (rfcs.isEmpty()) {
            return Collections.emptyList();
        }
        return em.createQuery("SELECT p FROM RnGcPersonasTbl p WHERE UPPER(TRIM(p.rfc)) IN :rfcs", RnGcPersonasTbl.class)
                .setParameter("rfcs", rfcs).getResultList();
    }

    // ------------------------------------------------------------- 49 Bis X

    /**
     * Revisa las dos ediciones (MAT, VES) de una fecha del DOF y carga cada oficio de
     * 49 Bis fracción X que encuentre. Todo o nada por oficio: un oficio sin la tabla del
     * anexo o con un renglón sin RFC válido no se carga y se informa.
     */
    public Resumen49BisX importar49BisX(Date fecha, int idUsuario) {
        Resumen49BisX r = new Resumen49BisX();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        String fechaTexto = new SimpleDateFormat("dd/MM/yyyy").format(fecha);
        for (String edicion : DofParser.EDICIONES) {
            String indice;
            try {
                indice = DofHttp.get(DofParser.urlIndice(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
                        cal.get(Calendar.DAY_OF_MONTH), edicion));
            } catch (IOException ex) {
                r.errores.add("No se pudo consultar el DOF " + fechaTexto + " edición " + EnlaceDof.nombreEdicion(edicion)
                        + ": " + ex.getMessage());
                continue;
            }
            for (DofParser.NotaDof nota : DofParser.notasDelIndice(indice)) {
                if (DofParser.es49BisX(nota.getTitulo())) {
                    cargarNota49BisX(nota, edicion, fecha, idUsuario, r);
                }
            }
        }
        return r;
    }

    private void cargarNota49BisX(DofParser.NotaDof nota, String edicion, Date fecha, int idUsuario, Resumen49BisX r) {
        String oficio = DofParser.numeroOficio(nota.getTitulo());
        List<RnGcTipoListaNegraTbl> previos = em.createNamedQuery("RnGcTipoListaNegraTbl.findByFolioPublicacionCreadoPor", RnGcTipoListaNegraTbl.class)
                .setParameter("folioPublicacion", oficio).getResultList();
        if (!previos.isEmpty()) {
            r.omitidos.add("Oficio " + oficio + ": ya estaba cargado.");
            return;
        }
        String html;
        try {
            html = DofHttp.get(DofParser.urlNota(nota.getCodigo(), nota.getFecha()));
        } catch (IOException ex) {
            r.errores.add("Oficio " + oficio + ": no se pudo abrir la nota del DOF (" + ex.getMessage() + ").");
            return;
        }
        List<String[]> anexo = DofParser.anexo49BisX(html);
        if (anexo == null || anexo.isEmpty()) {
            r.errores.add("Oficio " + oficio + ": la nota no trae la tabla del Anexo 1; no se cargó.");
            return;
        }
        String fechaOficio = DofParser.fechaOficio(html);
        List<RnGcListaNegraTbl> filas = new ArrayList<>();
        for (int i = 0; i < anexo.size(); i++) {
            String[] c = anexo.get(i);
            String rfc = c.length > 0 ? ListaNegraCsv.limpiarRfc(c[0]) : "";
            if (c.length != 7 || !ListaNegraCsv.RFC_VALIDO.matcher(rfc).matches()) {
                r.errores.add("Oficio " + oficio + ": el renglón " + (i + 1) + " del anexo no trae un RFC válido; no se cargó el oficio.");
                return;
            }
            RnGcListaNegraTbl ln = new RnGcListaNegraTbl();
            ln.setRfc(rfc);
            ln.setNombreContribuyente(c[1]);
            ln.setSituacionContribuyente("Definitivo");
            ln.setNumeroFechaOficioGlobalDefinitivoDOF(oficio + (fechaOficio != null ? " de fecha " + fechaOficio : ""));
            ln.setPublicacionDOFdefinitivos(nota.getFecha());
            ln.setOficioResolucion(DofParser.numeroOficio(c[2]) + resto(c[2]));
            ln.setNotificacionBuzon(rango(c[3], c[4]));
            ln.setNotificacionEstrados(rango(c[5], c[6]));
            filas.add(ln);
        }
        Resumen res = importar(TipoListaNegra.LISTA_49BIS_X, filas, idUsuario, oficio, fecha, false);
        guardarNota(DofParser.digitosOficio(nota.getTitulo()), fecha, edicion, nota.getCodigo(), nota.getTitulo());
        r.cargados.add("Oficio " + oficio + " (" + EnlaceDof.nombreEdicion(edicion) + "): " + filas.size()
                + (filas.size() == 1 ? " contribuyente." : " contribuyentes."));
        r.registros += filas.size();
        r.avisos += res.getAvisos();
    }

    /** La parte " del 15 de junio de 2026" de "500-05-...-2026- 16368 del 15 de junio de 2026". */
    private static String resto(String texto) {
        int i = texto.indexOf(" del ");
        return i >= 0 ? texto.substring(i) : "";
    }

    private static String rango(String notificacion, String surteEfectos) {
        boolean hayN = notificacion != null && !notificacion.isEmpty();
        boolean hayS = surteEfectos != null && !surteEfectos.isEmpty();
        if (!hayN && !hayS) {
            return null;
        }
        return (hayN ? notificacion : "-") + " / " + (hayS ? surteEfectos : "-");
    }

    // ------------------------------------------------------------ consultas

    /** Registros activos de todas las listas para un RFC, con semáforo y ligas al DOF. */
    public List<CoincidenciaListaNegra> coincidencias(String rfc) {
        List<CoincidenciaListaNegra> salida = new ArrayList<>();
        String limpio = ListaNegraCsv.limpiarRfc(rfc);
        if (limpio.isEmpty()) {
            return salida;
        }
        for (RnGcListaNegraTbl ln : em.createNamedQuery("RnGcListaNegraTbl.findByRfc", RnGcListaNegraTbl.class)
                .setParameter("rfc", limpio).getResultList()) {
            salida.add(armar(ln));
        }
        return salida;
    }

    /** Clientes y proveedores de un usuario que están en alguna lista. */
    public List<CoincidenciaListaNegra> coincidenciasDeUsuario(int idUsuario) {
        List<RnGcPersonasTbl> personas = em.createNamedQuery("RnGcPersonasTbl.findByCreadoPor", RnGcPersonasTbl.class)
                .setParameter("creadoPor", idUsuario).getResultList();
        Map<String, List<RnGcPersonasTbl>> porRfc = new LinkedHashMap<>();
        for (RnGcPersonasTbl p : personas) {
            String rfc = ListaNegraCsv.limpiarRfc(p.getRfc());
            if (!rfc.isEmpty()) {
                porRfc.computeIfAbsent(rfc, k -> new ArrayList<>()).add(p);
            }
        }
        List<CoincidenciaListaNegra> salida = new ArrayList<>();
        List<String> rfcs = new ArrayList<>(porRfc.keySet());
        for (int i = 0; i < rfcs.size(); i += LOTE) {
            List<String> lote = rfcs.subList(i, Math.min(i + LOTE, rfcs.size()));
            for (RnGcListaNegraTbl ln : em.createNamedQuery("RnGcListaNegraTbl.findActivosByRfcs", RnGcListaNegraTbl.class)
                    .setParameter("rfcs", lote).getResultList()) {
                for (RnGcPersonasTbl p : porRfc.getOrDefault(ln.getRfc(), Collections.<RnGcPersonasTbl>emptyList())) {
                    salida.add(armar(ln).conPersona(p.getId(), p.getTipoPersona(), p.getNombre()));
                }
            }
        }
        return salida;
    }

    public List<RnGcListaNegraAvisoTbl> avisosNoVistos(int idUsuario) {
        return em.createNamedQuery("RnGcListaNegraAvisoTbl.findNoVistosByUsuario", RnGcListaNegraAvisoTbl.class)
                .setParameter("idUsuario", idUsuario).getResultList();
    }

    public int marcarAvisosVistos(int idUsuario) {
        return em.createNamedQuery("RnGcListaNegraAvisoTbl.marcarVistosByUsuario")
                .setParameter("idUsuario", idUsuario).executeUpdate();
    }

    private CoincidenciaListaNegra armar(RnGcListaNegraTbl ln) {
        EnlaceDof entrada = enlace("Presunción", ln.getNumeroFechaOficioGlobalPresuncionDOF(), ln.getPublicacionDOFpresuntos());
        if (entrada == null) {
            entrada = enlace("Definitivo", ln.getNumeroFechaOficioGlobalDefinitivoDOF(), ln.getPublicacionDOFdefinitivos());
        }
        EnlaceDof salida = enlace("Desvirtuado", ln.getNumeroFechaOficioGlobalDesvirtuadoDOF(), ln.getPublicacionDOFdesvirtuados());
        if (salida == null) {
            salida = enlace("Sentencia favorable", ln.getNumeroFechaOficioGlobalSentenciaDOF(), ln.getPublicacionDOFsentencia());
        }
        return new CoincidenciaListaNegra(ln, entrada, salida);
    }

    // ------------------------------------------------------------ ligas al DOF

    /**
     * Referencia al DOF de una etapa. La nota se busca en el índice del DOF de esa fecha
     * (MAT y luego VES) y se guarda en caché; si no se encuentra, la referencia queda como
     * el índice de ese día con fecha, edición (si se conoce) y oficio.
     */
    public EnlaceDof enlace(String etapa, String oficioCampo, String fechaDof) {
        Date fecha = parsear(fechaDof);
        String digitos = DofParser.digitosOficio(oficioCampo);
        if (fecha == null || digitos.isEmpty()) {
            return null;
        }
        String oficio = DofParser.numeroOficio(oficioCampo);
        String fechaTexto = fechaDof.trim();
        RnGcListaNegraDofTbl nota = buscarNota(digitos, fecha);
        if (nota == null && consultarDia(fecha)) {
            nota = buscarNota(digitos, fecha);
            if (nota == null) {
                guardar(digitos, fecha, null, null, null, RnGcListaNegraDofTbl.NO_ENCONTRADO);
            }
        }
        if (nota != null && RnGcListaNegraDofTbl.RESUELTO.equals(nota.getResuelto())) {
            return new EnlaceDof(etapa, oficio, fechaTexto, nota.getEdicion(),
                    DofParser.urlNota(nota.getCodigoNota(), fechaTexto), true);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        return new EnlaceDof(etapa, oficio, fechaTexto, null, DofParser.urlIndice(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), "MAT"), false);
    }

    private RnGcListaNegraDofTbl buscarNota(String digitos, Date fecha) {
        List<RnGcListaNegraDofTbl> r = em.createNamedQuery("RnGcListaNegraDofTbl.findByOficioFecha", RnGcListaNegraDofTbl.class)
                .setParameter("oficio", digitos).setParameter("fechaPublicacion", fecha).getResultList();
        return r.isEmpty() ? null : r.get(0);
    }

    /**
     * Lee las dos ediciones de una fecha y guarda en caché cada nota "Oficio ..." que traen.
     * Regresa false si alguna edición no se pudo leer (entonces nada se marca como no encontrado).
     */
    private boolean consultarDia(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        Set<String> conocidos = new HashSet<>();
        for (RnGcListaNegraDofTbl n : em.createNamedQuery("RnGcListaNegraDofTbl.findByFecha", RnGcListaNegraDofTbl.class)
                .setParameter("fechaPublicacion", fecha).getResultList()) {
            conocidos.add(n.getOficio());
        }
        boolean completo = true;
        for (String edicion : DofParser.EDICIONES) {
            String html;
            try {
                html = DofHttp.get(DofParser.urlIndice(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
                        cal.get(Calendar.DAY_OF_MONTH), edicion));
            } catch (IOException ex) {
                LOG.log(Level.WARNING, "CTR-12: no se pudo leer el índice del DOF {0} {1}: {2}",
                        new Object[]{fecha, edicion, ex.getMessage()});
                completo = false;
                continue;
            }
            for (DofParser.NotaDof nota : DofParser.notasDelIndice(html)) {
                if (!nota.getTitulo().toLowerCase().startsWith("oficio")) {
                    continue;
                }
                String digitos = DofParser.digitosOficio(nota.getTitulo());
                if (!digitos.isEmpty() && conocidos.add(digitos)) {
                    guardar(digitos, fecha, edicion, nota.getCodigo(), nota.getTitulo(), RnGcListaNegraDofTbl.RESUELTO);
                }
            }
        }
        em.flush();
        return completo;
    }

    private void guardarNota(String digitos, Date fecha, String edicion, String codigo, String titulo) {
        if (!digitos.isEmpty() && buscarNota(digitos, fecha) == null) {
            guardar(digitos, fecha, edicion, codigo, titulo, RnGcListaNegraDofTbl.RESUELTO);
            em.flush();
        }
    }

    private void guardar(String digitos, Date fecha, String edicion, String codigo, String titulo, String resuelto) {
        RnGcListaNegraDofTbl n = new RnGcListaNegraDofTbl();
        n.setOficio(digitos.length() > 60 ? digitos.substring(0, 60) : digitos);
        n.setFechaPublicacion(fecha);
        n.setEdicion(edicion);
        n.setCodigoNota(codigo);
        n.setTitulo(titulo);
        n.setResuelto(resuelto);
        n.setFechaConsulta(new Date());
        em.persist(n);
    }

    private static Date parsear(String ddMmAaaa) {
        if (ddMmAaaa == null || !ddMmAaaa.trim().matches("\\d{2}/\\d{2}/\\d{4}")) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        f.setLenient(false);
        try {
            return f.parse(ddMmAaaa.trim());
        } catch (ParseException ex) {
            return null;
        }
    }
}
