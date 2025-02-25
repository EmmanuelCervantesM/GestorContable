package mx.com.rocketnegocios.entities;

import java.util.Date;

public class ListaCuentas {
    
    private String nCuenta;
    private String dCuenta;
    private String codigo;
    private String nivel;
    private String tipo;
    private String subtipo;
    private String naturaleza;
    private String moneda;
    private String diot;
    private String rfc;
    private Date vigencia;
    

    public ListaCuentas() {
    }

    public String getnCuenta() {
        return nCuenta;
    }

    public void setnCuenta(String nCuenta) {
        this.nCuenta = nCuenta;
    }

    public String getdCuenta() {
        return dCuenta;
    }

    public void setdCuenta(String dCuenta) {
        this.dCuenta = dCuenta;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSubtipo() {
        return subtipo;
    }

    public void setSubtipo(String subtipo) {
        this.subtipo = subtipo;
    }

    public String getNaturaleza() {
        return naturaleza;
    }

    public void setNaturaleza(String naturaleza) {
        this.naturaleza = naturaleza;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getDiot() {
        return diot;
    }

    public void setDiot(String diot) {
        this.diot = diot;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Date getVigencia() {
        return vigencia;
    }

    public void setVigencia(Date vigencia) {
        this.vigencia = vigencia;
    }
    
}
