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
public class LibroDiarioRow implements Serializable {

    private static final long serialVersionUID = 1L;

    private String sucursal;
    private String tipoPoliza;
    private String fechaPoliza;
    private String numeroPoliza;
    private String numeroCuenta;
    private String descripcionCuenta;
    private BigDecimal cargo;
    private BigDecimal abono;
    private Integer tipoFila;

    /*
     * tipoFila:
     * 0 = Encabezado de póliza
     * 1 = Detalle de póliza
     * 2 = Total de póliza
     */

    public LibroDiarioRow() {
    }

    public LibroDiarioRow(String sucursal,
                          String tipoPoliza,
                          String fechaPoliza,
                          String numeroPoliza,
                          String numeroCuenta,
                          String descripcionCuenta,
                          BigDecimal cargo,
                          BigDecimal abono,
                          Integer tipoFila) {
        this.sucursal = sucursal;
        this.tipoPoliza = tipoPoliza;
        this.fechaPoliza = fechaPoliza;
        this.numeroPoliza = numeroPoliza;
        this.numeroCuenta = numeroCuenta;
        this.descripcionCuenta = descripcionCuenta;
        this.cargo = cargo;
        this.abono = abono;
        this.tipoFila = tipoFila;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getTipoPoliza() {
        return tipoPoliza;
    }

    public void setTipoPoliza(String tipoPoliza) {
        this.tipoPoliza = tipoPoliza;
    }

    public String getFechaPoliza() {
        return fechaPoliza;
    }

    public void setFechaPoliza(String fechaPoliza) {
        this.fechaPoliza = fechaPoliza;
    }

    public String getNumeroPoliza() {
        return numeroPoliza;
    }

    public void setNumeroPoliza(String numeroPoliza) {
        this.numeroPoliza = numeroPoliza;
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

    public Integer getTipoFila() {
        return tipoFila;
    }

    public void setTipoFila(Integer tipoFila) {
        this.tipoFila = tipoFila;
    }
}
