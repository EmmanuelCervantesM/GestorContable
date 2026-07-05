package mx.com.rocketnegocios.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class EstadoResultadoRow implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String numeroCuenta;
    private String descripcionCuenta;
    private BigDecimal cargo;
    private BigDecimal abono;
    private BigDecimal importeEstadoResultado;

    public EstadoResultadoRow() {
    }

    public EstadoResultadoRow(Integer id,
                              String numeroCuenta,
                              String descripcionCuenta,
                              BigDecimal cargo,
                              BigDecimal abono,
                              BigDecimal importeEstadoResultado) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.descripcionCuenta = descripcionCuenta;
        this.cargo = cargo;
        this.abono = abono;
        this.importeEstadoResultado = importeEstadoResultado;
    }

    public Integer getId() {
        return id;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getDescripcionCuenta() {
        return descripcionCuenta;
    }

    public BigDecimal getCargo() {
        return cargo;
    }

    public BigDecimal getAbono() {
        return abono;
    }

    public BigDecimal getImporteEstadoResultado() {
        return importeEstadoResultado;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public void setDescripcionCuenta(String descripcionCuenta) {
        this.descripcionCuenta = descripcionCuenta;
    }

    public void setCargo(BigDecimal cargo) {
        this.cargo = cargo;
    }

    public void setAbono(BigDecimal abono) {
        this.abono = abono;
    }

    public void setImporteEstadoResultado(BigDecimal importeEstadoResultado) {
        this.importeEstadoResultado = importeEstadoResultado;
    }
}