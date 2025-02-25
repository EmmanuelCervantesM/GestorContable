package mx.com.rocketnegocios.entities;

public class personaListaNegra {
    
    private RnGcPersonasTbl persona;
    private RnGcListaNegraTbl listaNegra;

    public personaListaNegra() {
    }

    public personaListaNegra(RnGcPersonasTbl persona, RnGcListaNegraTbl listaNegra) {
        this.persona = persona;
        this.listaNegra = listaNegra;
    }

    public RnGcPersonasTbl getPersona() {
        return persona;
    }

    public void setPersona(RnGcPersonasTbl persona) {
        this.persona = persona;
    }

    public RnGcListaNegraTbl getListaNegra() {
        return listaNegra;
    }

    public void setListaNegra(RnGcListaNegraTbl listaNegra) {
        this.listaNegra = listaNegra;
    }
    
    public String toString(){
        return "[Persona: " + persona + ", ListaNegra: " + listaNegra + "]";
    }
    
}
