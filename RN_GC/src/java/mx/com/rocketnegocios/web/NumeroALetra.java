package mx.com.rocketnegocios.web;

import java.util.Map;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;

public class NumeroALetra {

    private final String[] UNIDADES = {"", "Un ", "Dos ", "Tres ", "Cuatro ", "Cinco ", "Seis ", "Siete ", "Ocho ", "Nueve "};
    private final String[] DECENAS = {"Diez ", "Once ", "Doce ", "Trece ", "Catorce ", "Quince ", "Dieciseis ",
        "Diecisiete ", "Dieciocho ", "Diecinueve", "Veinti", "Treinta ", "Cuarenta ", "Cincuenta ", "Sesenta ", "Setenta ",
        "Ochenta ", "Noventa "};
    private final String[] CENTENAS = {"", "Ciento ", "Doscientos ", "Trescientos ", "Cuatrocientos ", "Quinientos ", "Seiscientos ",
        "Setecientos ", "Ochocientos ", "Novecientos "};

    public NumeroALetra() {
    }

    public String Convertir(String numero, boolean mayusculas) {
        String literal = "";
        String parte_decimal;
        numero = numero.replace(".", ",");
        if (numero.indexOf(",") == -1) {
            numero = numero + ",00";
        }
        if (Pattern.matches("\\d{1,9},\\d{1,2}", numero)) {
            String Num[] = numero.split(",");
            parte_decimal = "Pesos " + Num[1] + "/100 M. N.";
            if (Integer.parseInt(Num[0]) == 0) {//si el valor es cero
                literal = "Cero ";
            } else if (Integer.parseInt(Num[0]) > 999999) {//si es millon
                literal = getMillones(Num[0]);
            } else if (Integer.parseInt(Num[0]) > 999) {//si es miles
                literal = getMiles(Num[0]);
            } else if (Integer.parseInt(Num[0]) > 99) {//si es centena
                literal = getCentenas(Num[0]);
            } else if (Integer.parseInt(Num[0]) > 9) {//si es decena
                literal = getDecenas(Num[0]);
            } else {//sino unidades -> 9
                literal = getUnidades(Num[0]);
            }
            //devuelve el resultado en mayusculas o minusculas
            if (mayusculas) {
                return (literal + parte_decimal);
            } else {
                return (literal + parte_decimal);
            }
        } else {//error, no se puede convertir
            return literal = null;
        }
    }

    /* funciones para convertir los numeros a literales */
    private String getUnidades(String numero) {// 1 - 9
        String num = numero.substring(numero.length() - 1);
        return UNIDADES[Integer.parseInt(num)];
    }

    private String getDecenas(String num) {// 99                        
        int n = Integer.parseInt(num);
        if (n < 10) {//para casos como -> 01 - 09
            return getUnidades(num);
        } else if (n > 29) {//para 30...99
            String u = getUnidades(num);
            if (u.equals("")) { //para 20,30,40,50,60,70,80,90
                return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8];
            } else {
                return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8] + "y " + u;
            }
        } else if (n == 20) {
            return "Veinte ";
        } else if (n > 20 && n < 30) {
            String u = getUnidades(num);
            if (u.equals("")) { //para 21 al 29
                return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8];
            } else {
                return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8] + "" + u;
            }
        } else {//numeros entre 11 y 19
            return DECENAS[n - 10];
        }
    }

    public String perfilUsuario() {
        String listaPerfiles = "";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, Object> map = facesContext.getExternalContext().getSessionMap();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key.equals("listaPerfiles")) {
                listaPerfiles = String.valueOf(value);
                listaPerfiles = String.valueOf(value);
            }
        }
        System.out.println("perfilUsuario: " + listaPerfiles);
        return listaPerfiles;
    }

    private String getCentenas(String num) {// 999 o 099
        if (Integer.parseInt(num) > 99) {//es centena
            if (Integer.parseInt(num) == 100) {//caso especial
                return "Cien ";
            } else {
                return CENTENAS[Integer.parseInt(num.substring(0, 1))] + getDecenas(num.substring(1));
            }
        } else {//por Ej. 099 
            //se quita el 0 antes de convertir a decenas
            return getDecenas(Integer.parseInt(num) + "");
        }
    }

    private String getMiles(String numero) {// 999 999
        //obtiene las centenas
        String c = numero.substring(numero.length() - 3);
        //obtiene los miles
        String m = numero.substring(0, numero.length() - 3);
        String n = "";
        //se comprueba que miles tenga valor entero
        if (Integer.parseInt(m) > 0) {
            n = getCentenas(m);
            return n + "Mil " + getCentenas(c);
        } else {
            return "" + getCentenas(c);
        }

    }

    private String getMillones(String numero) { //000 000 000        
        //se obtiene los miles
        String miles = numero.substring(numero.length() - 6);
        //se obtiene los millones
        String millon = numero.substring(0, numero.length() - 6);
        String n = "";
        if (millon.length() > 1) {
            n = getCentenas(millon) + "Millones ";
        } else {
            n = getUnidades(millon) + "Millón ";
        }
        return n + getMiles(miles);
    }
}
