package mx.com.rocketnegocios.util.listanegra;

/**
 * CTR-12: las tres listas negras que maneja el sistema. La etiqueta es lo que se
 * guarda en rn_gc_tipoListaNegra_tbl.tipoLista.
 */
public enum TipoListaNegra {

    LISTA_69B("69-B", 20,
            "https://wu1agsprosta001.blob.core.windows.net/agsc-publicaciones/Datos_abiertos/Documents_AGAFF/Listado_completo_69-B.csv"),
    LISTA_69B_BIS("69-B Bis", 12,
            "https://wu1agsprosta001.blob.core.windows.net/agsc-publicaciones/Datos_abiertos/Documents_AGGC/Listado_69_B_Bis_Completo.csv"),
    LISTA_49BIS_X("49 Bis X", 0, null);

    private final String etiqueta;
    private final int columnasCsv;
    private final String urlCsv;

    TipoListaNegra(String etiqueta, int columnasCsv, String urlCsv) {
        this.etiqueta = etiqueta;
        this.columnasCsv = columnasCsv;
        this.urlCsv = urlCsv;
    }

    /** Liga fija del listado completo que publica el SAT en datos abiertos (null = no tiene CSV). */
    public String getUrlCsv() {
        return urlCsv;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    /** Número de columnas del CSV del SAT de esta lista (0 = no tiene CSV). */
    public int getColumnasCsv() {
        return columnasCsv;
    }

    public static TipoListaNegra porEtiqueta(String etiqueta) {
        if (etiqueta == null) {
            return null;
        }
        for (TipoListaNegra t : values()) {
            if (t.etiqueta.equalsIgnoreCase(etiqueta.trim())) {
                return t;
            }
        }
        return null;
    }
}
