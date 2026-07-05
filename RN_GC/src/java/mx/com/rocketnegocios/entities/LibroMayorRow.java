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
public class LibroMayorRow implements Serializable {

    private static final long serialVersionUID = 1L;

    private String numeroCuenta;
    private String descripcionCuenta;
    private String mes;
    private Integer mesNumero;
    private BigDecimal cargo;
    private BigDecimal abono;
    private BigDecimal saldo;
    private Integer tipoFila;

    /*
     * tipoFila:
     * 0 = Encabezado de cuenta
     * 1 = Mes
     * 2 = Totales
     */

    public LibroMayorRow() {
    }

    public LibroMayorRow(String numeroCuenta,
                         String descripcionCuenta,
                         String mes,
                         Integer mesNumero,
                         BigDecimal cargo,
                         BigDecimal abono,
                         BigDecimal saldo,
                         Integer tipoFila) {
        this.numeroCuenta = numeroCuenta;
        this.descripcionCuenta = descripcionCuenta;
        this.mes = mes;
        this.mesNumero = mesNumero;
        this.cargo = cargo;
        this.abono = abono;
        this.saldo = saldo;
        this.tipoFila = tipoFila;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getDescripcionCuenta() {
        return descripcionCuenta;
    }

    public void setDescripcionCuenta(String descripcionCuenta) {
        this.descripcionCuenta = descripcionCuenta;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Integer getMesNumero() {
        return mesNumero;
    }

    public void setMesNumero(Integer mesNumero) {
        this.mesNumero = mesNumero;
    }

    public BigDecimal getCargo() {
        return cargo;
    }

    public void setCargo(BigDecimal cargo) {
        this.cargo = cargo;
    }

    public BigDecimal getAbono() {
        return abono;
    }

    public void setAbono(BigDecimal abono) {
        this.abono = abono;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Integer getTipoFila() {
        return tipoFila;
    }

    public void setTipoFila(Integer tipoFila) {
        this.tipoFila = tipoFila;
    }
}
