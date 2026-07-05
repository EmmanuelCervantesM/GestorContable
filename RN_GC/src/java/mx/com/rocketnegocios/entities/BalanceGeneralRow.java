/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.entities;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Aaron A Morales Hdez
 */
public class BalanceGeneralRow implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cuentaActivo;
    private String descripcionActivo;
    private BigDecimal saldoActivo;

    private String cuentaPasivo;
    private String descripcionPasivo;
    private BigDecimal saldoPasivo;

    public BalanceGeneralRow() {
    }

    public String getCuentaActivo() {
        return cuentaActivo;
    }

    public void setCuentaActivo(String cuentaActivo) {
        this.cuentaActivo = cuentaActivo;
    }

    public String getDescripcionActivo() {
        return descripcionActivo;
    }

    public void setDescripcionActivo(String descripcionActivo) {
        this.descripcionActivo = descripcionActivo;
    }

    public BigDecimal getSaldoActivo() {
        return saldoActivo;
    }

    public void setSaldoActivo(BigDecimal saldoActivo) {
        this.saldoActivo = saldoActivo;
    }

    public String getCuentaPasivo() {
        return cuentaPasivo;
    }

    public void setCuentaPasivo(String cuentaPasivo) {
        this.cuentaPasivo = cuentaPasivo;
    }

    public String getDescripcionPasivo() {
        return descripcionPasivo;
    }

    public void setDescripcionPasivo(String descripcionPasivo) {
        this.descripcionPasivo = descripcionPasivo;
    }

    public BigDecimal getSaldoPasivo() {
        return saldoPasivo;
    }

    public void setSaldoPasivo(BigDecimal saldoPasivo) {
        this.saldoPasivo = saldoPasivo;
    }
}