package mx.com.rocketnegocios.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * CTR-12: aviso para un usuario cuando uno de sus clientes o proveedores entra a una
 * lista negra, cambia de estado en ella o sale de ella, o cuando uno de sus CFDI (emitido o
 * recibido) tiene como contraparte a un contribuyente que está en una lista (idCfdi lleno).
 */
@Entity
@Table(name = "rn_gc_listaNegraAviso_tbl")
@NamedQueries({
    @NamedQuery(name = "RnGcListaNegraAvisoTbl.findNoVistosByUsuario", query = "SELECT r FROM RnGcListaNegraAvisoTbl r WHERE r.idUsuario = :idUsuario AND r.visto = 'N' ORDER BY r.fechaAviso DESC, r.id DESC"),
    @NamedQuery(name = "RnGcListaNegraAvisoTbl.findClavesCfdi", query = "SELECT r.idUsuario, r.idCfdi, r.tipoLista, r.estadoNuevo FROM RnGcListaNegraAvisoTbl r WHERE r.idCfdi IN :ids"),
    @NamedQuery(name = "RnGcListaNegraAvisoTbl.marcarVistosByUsuario", query = "UPDATE RnGcListaNegraAvisoTbl r SET r.visto = 'S' WHERE r.idUsuario = :idUsuario AND r.visto = 'N'")})
public class RnGcListaNegraAvisoTbl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "idUsuario")
    private Integer idUsuario;
    @Column(name = "idPersona")
    private Integer idPersona;
    @Column(name = "rfc")
    private String rfc;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "tipoLista")
    private String tipoLista;
    @Column(name = "estadoAnterior")
    private String estadoAnterior;
    @Column(name = "estadoNuevo")
    private String estadoNuevo;
    @Column(name = "fechaAviso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAviso;
    @Column(name = "visto")
    private String visto;
    @Column(name = "idCfdi")
    private Integer idCfdi;
    @Column(name = "uuid")
    private String uuid;
    @Column(name = "fechaCfdi")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCfdi;
    @Column(name = "rolCfdi")
    private String rolCfdi;
    @Column(name = "estatusCfdi")
    private String estatusCfdi;
    @Column(name = "momento")
    private String momento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoLista() {
        return tipoLista;
    }

    public void setTipoLista(String tipoLista) {
        this.tipoLista = tipoLista;
    }

    public String getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setEstadoAnterior(String estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    public String getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(String estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public Date getFechaAviso() {
        return fechaAviso;
    }

    public void setFechaAviso(Date fechaAviso) {
        this.fechaAviso = fechaAviso;
    }

    public String getVisto() {
        return visto;
    }

    public void setVisto(String visto) {
        this.visto = visto;
    }

    public Integer getIdCfdi() {
        return idCfdi;
    }

    public void setIdCfdi(Integer idCfdi) {
        this.idCfdi = idCfdi;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getFechaCfdi() {
        return fechaCfdi;
    }

    public void setFechaCfdi(Date fechaCfdi) {
        this.fechaCfdi = fechaCfdi;
    }

    public String getRolCfdi() {
        return rolCfdi;
    }

    public void setRolCfdi(String rolCfdi) {
        this.rolCfdi = rolCfdi;
    }

    public String getEstatusCfdi() {
        return estatusCfdi;
    }

    public void setEstatusCfdi(String estatusCfdi) {
        this.estatusCfdi = estatusCfdi;
    }

    /** ANTES: el CFDI es anterior a la publicación; DURANTE: se hizo ya publicado en la lista. */
    public String getMomento() {
        return momento;
    }

    public void setMomento(String momento) {
        this.momento = momento;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RnGcListaNegraAvisoTbl)) {
            return false;
        }
        RnGcListaNegraAvisoTbl other = (RnGcListaNegraAvisoTbl) object;
        return id != null && id.equals(other.id);
    }

    @Override
    public String toString() {
        return "mx.com.rocketnegocios.entities.RnGcListaNegraAvisoTbl[ id=" + id + " ]";
    }
}
