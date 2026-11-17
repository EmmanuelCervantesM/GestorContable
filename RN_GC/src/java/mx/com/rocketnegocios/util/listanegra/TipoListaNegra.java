package mx.com.rocketnegocios.util.listanegra;

/**
 * CTR-12: las tres listas negras que maneja el sistema. La etiqueta es lo que se
 * guarda en rn_gc_tipoListaNegra_tbl.tipoLista.
 */
public enum TipoListaNegra {

    LISTA_69B("69-B", 20),
    LISTA_69B_BIS("69-B Bis", 12),
    LISTA_49BIS_X("49 Bis X", 0);

    private final String etiqueta;
    private final int columnasCsv;

    TipoListaNegra(String etiqueta, int columnasCsv) {
        this.etiqueta = etiqueta;
        this.columnasCsv = columnasCsv;
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
