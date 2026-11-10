package conpoo;

/*
 * Clase Operario (caso 5 con POO)
 *
 * Integrantes:
 *   - Daniel Eduardo Idárraga Gutiérrez
 *   - Laura Valentina Ochoa Mona
 *
 * Cada objeto Operario tiene su código, su nombre y si ya se le ingresó
 * la producción de la semana. La producción como tal se guarda en la
 * matriz de la clase ProduccionFabrica, porque el taller pide que sea
 * una matriz.
 */
public class Operario {

    // atributos
    private String codigo;
    private String nombre;
    private boolean tieneProduccion;

    // constructor: un operario nuevo todavía no tiene producción
    public Operario(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tieneProduccion = false;
    }

    // get
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean tieneProduccion() {
        return tieneProduccion;
    }

    // se llama cuando ya se ingresaron los 5 dias del operario
    public void marcarProduccionIngresada() {
        tieneProduccion = true;
    }
}
