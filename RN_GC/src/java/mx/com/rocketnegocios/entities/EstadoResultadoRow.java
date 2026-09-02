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
public class EstadoResultadoRow implements Serializable {
    
private static final long serialVersionUID = 1L;

    private Integer id;
    private String numeroCuenta;
    private String descripcionCuenta;
    private BigDecimal totalCargo;
    private BigDecimal totalAbono;
    private BigDecimal importeEstadoResultado;

    public EstadoResultadoRow() {
    }

    public EstadoResultadoRow(Integer id,
                              String numeroCuenta,
                              String descripcionCuenta,
                              BigDecimal totalCargo,
                              BigDecimal totalAbono,
                              BigDecimal importeEstadoResultado) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.descripcionCuenta = descripcionCuenta;
        this.totalCargo = totalCargo;
        this.totalAbono = totalAbono;
        this.importeEstadoResultado = importeEstadoResultado;
    }

    public EstadoResultadoRow(Integer id, String numeroCuenta, String descripcionCuenta, BigDecimal importe, Integer esTitulo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public BigDecimal getTotalCargo() {
        return totalCargo;
    }

    public void setTotalCargo(BigDecimal totalCargo) {
        this.totalCargo = totalCargo;
    }

    public BigDecimal getTotalAbono() {
        return totalAbono;
    }

    public void setTotalAbono(BigDecimal totalAbono) {
        this.totalAbono = totalAbono;
    }

    public BigDecimal getImporteEstadoResultado() {
        return importeEstadoResultado;
    }

    public void setImporteEstadoResultado(BigDecimal importeEstadoResultado) {
        this.importeEstadoResultado = importeEstadoResultado;
    }
}