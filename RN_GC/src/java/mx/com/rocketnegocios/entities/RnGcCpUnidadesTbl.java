/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author maxoc
 */
@Entity
@Table(name = "rn_gc_cp_unidades_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RnGcCpUnidadesTbl.findAll", query = "SELECT r FROM RnGcCpUnidadesTbl r")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findById", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.id = :id")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByNumeroEconomico", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.numeroEconomico = :numeroEconomico")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByLecturaInicial", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.lecturaInicial = :lecturaInicial")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByKmLecturaActual", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.kmLecturaActual = :kmLecturaActual")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByKmAcumulados", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.kmAcumulados = :kmAcumulados")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByKmPromedioDiario", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.kmPromedioDiario = :kmPromedioDiario")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByLitrosCargados", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.litrosCargados = :litrosCargados")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByRendimiento", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.rendimiento = :rendimiento")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByHoraServicio", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.horaServicio = :horaServicio")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByPlacas", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.placas = :placas")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByEstado", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.estado = :estado")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByAnio", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.anio = :anio")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByNumeroMotor", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.numeroMotor = :numeroMotor")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByMarcaMotor", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.marcaMotor = :marcaMotor")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByMarca", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.marca = :marca")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByModelo", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.modelo = :modelo")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByNumeroEjes", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.numeroEjes = :numeroEjes")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByNumeroSerie", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.numeroSerie = :numeroSerie")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByRastreoSatelital", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.rastreoSatelital = :rastreoSatelital")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByEstadoAntena", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.estadoAntena = :estadoAntena")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByTarjetaCombustible", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.tarjetaCombustible = :tarjetaCombustible")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByLongitud", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.longitud = :longitud")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByRendimientoAutorizado1", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.rendimientoAutorizado1 = :rendimientoAutorizado1")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByRendimientoAutorizado2", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.rendimientoAutorizado2 = :rendimientoAutorizado2")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByRendimientoAutorizado3", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.rendimientoAutorizado3 = :rendimientoAutorizado3")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByFactorAjustesCombustible", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.factorAjustesCombustible = :factorAjustesCombustible")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByCapacidadTanque", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.capacidadTanque = :capacidadTanque")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByObservaciones", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.observaciones = :observaciones")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByNumeroEcoRemolque1", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.numeroEcoRemolque1 = :numeroEcoRemolque1")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByNumeroEcoRemolque2", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.numeroEcoRemolque2 = :numeroEcoRemolque2")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByNumeroEcoDolly1", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.numeroEcoDolly1 = :numeroEcoDolly1")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByMaximoCobranza", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.maximoCobranza = :maximoCobranza")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByCantidadAyudantes", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.cantidadAyudantes = :cantidadAyudantes")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByCreadoPor", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.creadoPor = :creadoPor")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByFechaCreacion", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByUltimaActualizacionPor", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.ultimaActualizacionPor = :ultimaActualizacionPor")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByUltimaFechaActualizacion", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.ultimaFechaActualizacion = :ultimaFechaActualizacion")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByCombustible", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.combustible = :combustible")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByCapacidadCarga", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.capacidadCarga = :capacidadCarga")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByPesoOcupado", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.pesoOcupado = :pesoOcupado")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByPesoDisponible", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.pesoDisponible = :pesoDisponible")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByPlacasRemolque1", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.placasRemolque1 = :placasRemolque1")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByPlacasRemolque2", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.placasRemolque2 = :placasRemolque2")
    , @NamedQuery(name = "RnGcCpUnidadesTbl.findByNumeroPermiso", query = "SELECT r FROM RnGcCpUnidadesTbl r WHERE r.numeroPermiso = :numeroPermiso")})
public class RnGcCpUnidadesTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numeroEconomico")
    private int numeroEconomico;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "lecturaInicial")
    private Double lecturaInicial;
    @Column(name = "kmLecturaActual")
    private Double kmLecturaActual;
    @Column(name = "kmAcumulados")
    private Double kmAcumulados;
    @Column(name = "kmPromedioDiario")
    private Double kmPromedioDiario;
    @Column(name = "litrosCargados")
    private Double litrosCargados;
    @Size(max = 45)
    @Column(name = "rendimiento")
    private String rendimiento;
    @Column(name = "horaServicio")
    private Double horaServicio;
    @Size(max = 25)
    @Column(name = "placas")
    private String placas;
    @Size(max = 10)
    @Column(name = "estado")
    private String estado;
    @Column(name = "anio")
    private Integer anio;
    @Size(max = 25)
    @Column(name = "numeroMotor")
    private String numeroMotor;
    @Size(max = 45)
    @Column(name = "marcaMotor")
    private String marcaMotor;
    @Size(max = 45)
    @Column(name = "marca")
    private String marca;
    @Size(max = 45)
    @Column(name = "modelo")
    private String modelo;
    @Column(name = "numeroEjes")
    private Integer numeroEjes;
    @Size(max = 45)
    @Column(name = "numeroSerie")
    private String numeroSerie;
    @Size(max = 45)
    @Column(name = "rastreoSatelital")
    private String rastreoSatelital;
    @Size(max = 1)
    @Column(name = "estadoAntena")
    private String estadoAntena;
    @Size(max = 45)
    @Column(name = "tarjetaCombustible")
    private String tarjetaCombustible;
    @Size(max = 45)
    @Column(name = "longitud")
    private String longitud;
    @Size(max = 45)
    @Column(name = "rendimientoAutorizado1")
    private String rendimientoAutorizado1;
    @Size(max = 45)
    @Column(name = "rendimientoAutorizado2")
    private String rendimientoAutorizado2;
    @Size(max = 45)
    @Column(name = "rendimientoAutorizado3")
    private String rendimientoAutorizado3;
    @Size(max = 45)
    @Column(name = "factorAjustesCombustible")
    private String factorAjustesCombustible;
    @Size(max = 45)
    @Column(name = "capacidadTanque")
    private String capacidadTanque;
    @Size(max = 250)
    @Column(name = "observaciones")
    private String observaciones;
    @Size(max = 45)
    @Column(name = "numeroEcoRemolque1")
    private String numeroEcoRemolque1;
    @Size(max = 45)
    @Column(name = "numeroEcoRemolque2")
    private String numeroEcoRemolque2;
    @Size(max = 45)
    @Column(name = "numeroEcoDolly1")
    private String numeroEcoDolly1;
    @Size(max = 45)
    @Column(name = "maximoCobranza")
    private String maximoCobranza;
    @Size(max = 45)
    @Column(name = "cantidadAyudantes")
    private String cantidadAyudantes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creadoPor")
    private int creadoPor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaCreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "ultimaActualizacionPor")
    private Integer ultimaActualizacionPor;
    @Column(name = "ultimaFechaActualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaFechaActualizacion;
    @Size(max = 45)
    @Column(name = "combustible")
    private String combustible;
    @Basic(optional = false)
    @NotNull
    @Column(name = "capacidadCarga")
    private double capacidadCarga;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pesoOcupado")
    private double pesoOcupado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pesoDisponible")
    private double pesoDisponible;
    @Size(max = 25)
    @Column(name = "placasRemolque1")
    private String placasRemolque1;
    @Size(max = 25)
    @Column(name = "placasRemolque2")
    private String placasRemolque2;
    @Size(max = 50)
    @Column(name = "numeroPermiso")
    private String numeroPermiso;
    @JoinColumn(name = "conductorId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RnGcCpConductoresTbl conductorId;
    @JoinColumn(name = "configVehiculoId", referencedColumnName = "id")
    @ManyToOne
    private RnGcCpConfigautotransportesatTbl configVehiculoId;
    @JoinColumn(name = "seguroId", referencedColumnName = "id")
    @ManyToOne
    private RnGcCpSegurosTbl seguroId;
    @JoinColumn(name = "tipoPermisoId", referencedColumnName = "id")
    @ManyToOne
    private RnGcCpTipopermisosatTbl tipoPermisoId;
    @JoinColumn(name = "tipoRemolqueId", referencedColumnName = "id")
    @ManyToOne
    private RnGcCpTiporemolquesatTbl tipoRemolqueId;
    @JoinColumn(name = "tipoUnidadId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RnGcCpTipounidadTbl tipoUnidadId;
    @OneToMany(mappedBy = "unidadId")
    private Collection<RnGcCpUnidadesParteTransporteTbl> rnGcCpUnidadesParteTransporteTblCollection;
    @OneToMany(mappedBy = "unidadId")
    private Collection<RnGcCpCartaPorteTbl> rnGcCpCartaPorteTblCollection;

    public RnGcCpUnidadesTbl() {
    }

    public RnGcCpUnidadesTbl(Integer id) {
        this.id = id;
    }

    public RnGcCpUnidadesTbl(Integer id, int numeroEconomico, int creadoPor, Date fechaCreacion, double capacidadCarga, double pesoOcupado, double pesoDisponible) {
        this.id = id;
        this.numeroEconomico = numeroEconomico;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.capacidadCarga = capacidadCarga;
        this.pesoOcupado = pesoOcupado;
        this.pesoDisponible = pesoDisponible;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumeroEconomico() {
        return numeroEconomico;
    }

    public void setNumeroEconomico(int numeroEconomico) {
        this.numeroEconomico = numeroEconomico;
    }

    public Double getLecturaInicial() {
        return lecturaInicial;
    }

    public void setLecturaInicial(Double lecturaInicial) {
        this.lecturaInicial = lecturaInicial;
    }

    public Double getKmLecturaActual() {
        return kmLecturaActual;
    }

    public void setKmLecturaActual(Double kmLecturaActual) {
        this.kmLecturaActual = kmLecturaActual;
    }

    public Double getKmAcumulados() {
        return kmAcumulados;
    }

    public void setKmAcumulados(Double kmAcumulados) {
        this.kmAcumulados = kmAcumulados;
    }

    public Double getKmPromedioDiario() {
        return kmPromedioDiario;
    }

    public void setKmPromedioDiario(Double kmPromedioDiario) {
        this.kmPromedioDiario = kmPromedioDiario;
    }

    public Double getLitrosCargados() {
        return litrosCargados;
    }

    public void setLitrosCargados(Double litrosCargados) {
        this.litrosCargados = litrosCargados;
    }

    public String getRendimiento() {
        return rendimiento;
    }

    public void setRendimiento(String rendimiento) {
        this.rendimiento = rendimiento;
    }

    public Double getHoraServicio() {
        return horaServicio;
    }

    public void setHoraServicio(Double horaServicio) {
        this.horaServicio = horaServicio;
    }

    public String getPlacas() {
        return placas;
    }

    public void setPlacas(String placas) {
        this.placas = placas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getNumeroMotor() {
        return numeroMotor;
    }

    public void setNumeroMotor(String numeroMotor) {
        this.numeroMotor = numeroMotor;
    }

    public String getMarcaMotor() {
        return marcaMotor;
    }

    public void setMarcaMotor(String marcaMotor) {
        this.marcaMotor = marcaMotor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getNumeroEjes() {
        return numeroEjes;
    }

    public void setNumeroEjes(Integer numeroEjes) {
        this.numeroEjes = numeroEjes;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getRastreoSatelital() {
        return rastreoSatelital;
    }

    public void setRastreoSatelital(String rastreoSatelital) {
        this.rastreoSatelital = rastreoSatelital;
    }

    public String getEstadoAntena() {
        return estadoAntena;
    }

    public void setEstadoAntena(String estadoAntena) {
        this.estadoAntena = estadoAntena;
    }

    public String getTarjetaCombustible() {
        return tarjetaCombustible;
    }

    public void setTarjetaCombustible(String tarjetaCombustible) {
        this.tarjetaCombustible = tarjetaCombustible;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getRendimientoAutorizado1() {
        return rendimientoAutorizado1;
    }

    public void setRendimientoAutorizado1(String rendimientoAutorizado1) {
        this.rendimientoAutorizado1 = rendimientoAutorizado1;
    }

    public String getRendimientoAutorizado2() {
        return rendimientoAutorizado2;
    }

    public void setRendimientoAutorizado2(String rendimientoAutorizado2) {
        this.rendimientoAutorizado2 = rendimientoAutorizado2;
    }

    public String getRendimientoAutorizado3() {
        return rendimientoAutorizado3;
    }

    public void setRendimientoAutorizado3(String rendimientoAutorizado3) {
        this.rendimientoAutorizado3 = rendimientoAutorizado3;
    }

    public String getFactorAjustesCombustible() {
        return factorAjustesCombustible;
    }

    public void setFactorAjustesCombustible(String factorAjustesCombustible) {
        this.factorAjustesCombustible = factorAjustesCombustible;
    }

    public String getCapacidadTanque() {
        return capacidadTanque;
    }

    public void setCapacidadTanque(String capacidadTanque) {
        this.capacidadTanque = capacidadTanque;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getNumeroEcoRemolque1() {
        return numeroEcoRemolque1;
    }

    public void setNumeroEcoRemolque1(String numeroEcoRemolque1) {
        this.numeroEcoRemolque1 = numeroEcoRemolque1;
    }

    public String getNumeroEcoRemolque2() {
        return numeroEcoRemolque2;
    }

    public void setNumeroEcoRemolque2(String numeroEcoRemolque2) {
        this.numeroEcoRemolque2 = numeroEcoRemolque2;
    }

    public String getNumeroEcoDolly1() {
        return numeroEcoDolly1;
    }

    public void setNumeroEcoDolly1(String numeroEcoDolly1) {
        this.numeroEcoDolly1 = numeroEcoDolly1;
    }

    public String getMaximoCobranza() {
        return maximoCobranza;
    }

    public void setMaximoCobranza(String maximoCobranza) {
        this.maximoCobranza = maximoCobranza;
    }

    public String getCantidadAyudantes() {
        return cantidadAyudantes;
    }

    public void setCantidadAyudantes(String cantidadAyudantes) {
        this.cantidadAyudantes = cantidadAyudantes;
    }

    public int getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(int creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getUltimaActualizacionPor() {
        return ultimaActualizacionPor;
    }

    public void setUltimaActualizacionPor(Integer ultimaActualizacionPor) {
        this.ultimaActualizacionPor = ultimaActualizacionPor;
    }

    public Date getUltimaFechaActualizacion() {
        return ultimaFechaActualizacion;
    }

    public void setUltimaFechaActualizacion(Date ultimaFechaActualizacion) {
        this.ultimaFechaActualizacion = ultimaFechaActualizacion;
    }

    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }

    public double getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(double capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    public double getPesoOcupado() {
        return pesoOcupado;
    }

    public void setPesoOcupado(double pesoOcupado) {
        this.pesoOcupado = pesoOcupado;
    }

    public double getPesoDisponible() {
        return pesoDisponible;
    }

    public void setPesoDisponible(double pesoDisponible) {
        this.pesoDisponible = pesoDisponible;
    }

    public String getPlacasRemolque1() {
        return placasRemolque1;
    }

    public void setPlacasRemolque1(String placasRemolque1) {
        this.placasRemolque1 = placasRemolque1;
    }

    public String getPlacasRemolque2() {
        return placasRemolque2;
    }

    public void setPlacasRemolque2(String placasRemolque2) {
        this.placasRemolque2 = placasRemolque2;
    }

    public String getNumeroPermiso() {
        return numeroPermiso;
    }

    public void setNumeroPermiso(String numeroPermiso) {
        this.numeroPermiso = numeroPermiso;
    }

    public RnGcCpConfigautotransportesatTbl getConfigVehiculoId() {
        return configVehiculoId;
    }

    public void setConfigVehiculoId(RnGcCpConfigautotransportesatTbl configVehiculoId) {
        this.configVehiculoId = configVehiculoId;
    }

    public RnGcCpSegurosTbl getSeguroId() {
        return seguroId;
    }

    public void setSeguroId(RnGcCpSegurosTbl seguroId) {
        this.seguroId = seguroId;
    }

    public RnGcCpTipopermisosatTbl getTipoPermisoId() {
        return tipoPermisoId;
    }

    public void setTipoPermisoId(RnGcCpTipopermisosatTbl tipoPermisoId) {
        this.tipoPermisoId = tipoPermisoId;
    }

    public RnGcCpTiporemolquesatTbl getTipoRemolqueId() {
        return tipoRemolqueId;
    }

    public void setTipoRemolqueId(RnGcCpTiporemolquesatTbl tipoRemolqueId) {
        this.tipoRemolqueId = tipoRemolqueId;
    }

    public RnGcCpConductoresTbl getConductorId() {
        return conductorId;
    }

    public void setConductorId(RnGcCpConductoresTbl conductorId) {
        this.conductorId = conductorId;
    }

    public RnGcCpTipounidadTbl getTipoUnidadId() {
        return tipoUnidadId;
    }

    public void setTipoUnidadId(RnGcCpTipounidadTbl tipoUnidadId) {
        this.tipoUnidadId = tipoUnidadId;
    }

    @XmlTransient
    public Collection<RnGcCpUnidadesParteTransporteTbl> getRnGcCpUnidadesParteTransporteTblCollection() {
        return rnGcCpUnidadesParteTransporteTblCollection;
    }

    public void setRnGcCpUnidadesParteTransporteTblCollection(Collection<RnGcCpUnidadesParteTransporteTbl> rnGcCpUnidadesParteTransporteTblCollection) {
        this.rnGcCpUnidadesParteTransporteTblCollection = rnGcCpUnidadesParteTransporteTblCollection;
    }

    @XmlTransient
    public Collection<RnGcCpCartaPorteTbl> getRnGcCpCartaPorteTblCollection() {
        return rnGcCpCartaPorteTblCollection;
    }

    public void setRnGcCpCartaPorteTblCollection(Collection<RnGcCpCartaPorteTbl> rnGcCpCartaPorteTblCollection) {
        this.rnGcCpCartaPorteTblCollection = rnGcCpCartaPorteTblCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RnGcCpUnidadesTbl)) {
            return false;
        }
        RnGcCpUnidadesTbl other = (RnGcCpUnidadesTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcCpUnidadesTbl[ id=" + id + " ]";
    }
    
}
