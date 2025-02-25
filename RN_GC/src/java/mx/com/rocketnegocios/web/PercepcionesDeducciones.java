package mx.com.rocketnegocios.web;

public class PercepcionesDeducciones {
    
    private String noEmpleado;
    private String nombre;
    private String apPaterno;
    private String apMaterno;
    private String tipoPercepcion;
    private String clavePercepcion;
    private String conceptoPercepcion;
    private String importeGravado;
    private String importeExcento;
    private String tipoDeduccion;
    private String claveDeduccion;
    private String conceptoDeduccion;
    private String importe;

    public PercepcionesDeducciones(){
    }
    
    public PercepcionesDeducciones(String nombre, String tipoPercepcion, String tipoDeduccion, String importeGravado, String importeExcento,
            String claveDeduccion, String clavePercepcion, String conceptoPercepcion, String conceptoDeduccion){
        this.nombre = nombre;
        this.tipoPercepcion = tipoPercepcion;
        this.tipoDeduccion = tipoDeduccion;
        this.importeGravado = importeGravado;
        this.importeExcento = importeExcento;
        this.claveDeduccion = claveDeduccion;
        this.clavePercepcion = clavePercepcion;
        this.conceptoPercepcion = conceptoPercepcion;
        this.conceptoDeduccion = conceptoDeduccion;
    }

    public String getNoEmpleado() {
        return noEmpleado;
    }

    public void setNoEmpleado(String noEmpleado) {
        this.noEmpleado = noEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public String getTipoPercepcion() {
        return tipoPercepcion;
    }

    public void setTipoPercepcion(String tipoPercepcion) {
        this.tipoPercepcion = tipoPercepcion;
    }

    public String getClavePercepcion() {
        return clavePercepcion;
    }

    public void setClavePercepcion(String clavePercepcion) {
        this.clavePercepcion = clavePercepcion;
    }

    public String getConceptoPercepcion() {
        return conceptoPercepcion;
    }

    public void setConceptoPercepcion(String conceptoPercepcion) {
        this.conceptoPercepcion = conceptoPercepcion;
    }

    public String getImporteGravado() {
        return importeGravado;
    }

    public void setImporteGravado(String importeGravado) {
        this.importeGravado = importeGravado;
    }

    public String getImporteExcento() {
        return importeExcento;
    }

    public void setImporteExcento(String importeExcento) {
        this.importeExcento = importeExcento;
    }

    public String getTipoDeduccion() {
        return tipoDeduccion;
    }

    public void setTipoDeduccion(String tipoDeduccion) {
        this.tipoDeduccion = tipoDeduccion;
    }

    public String getClaveDeduccion() {
        return claveDeduccion;
    }

    public void setClaveDeduccion(String claveDeduccion) {
        this.claveDeduccion = claveDeduccion;
    }

    public String getConceptoDeduccion() {
        return conceptoDeduccion;
    }

    public void setConceptoDeduccion(String conceptoDeduccion) {
        this.conceptoDeduccion = conceptoDeduccion;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }
    
    public String toString(){
        return "noEmpleado [" + noEmpleado + "], nombre [" + nombre + "]";
    }
    
    
    
}
