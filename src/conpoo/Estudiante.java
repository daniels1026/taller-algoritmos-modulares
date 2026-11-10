package conpoo;

/*
 * Clase Estudiante (caso 3 con POO)
 *
 * Integrantes:
 *   - Daniel Eduardo Idárraga Gutiérrez
 *   - Laura Valentina Ochoa Mona
 *
 * Cada objeto Estudiante tiene su código, nombre, sus 3 notas en un
 * vector y la asistencia. El mismo objeto sabe calcular su definitiva
 * y su estado.
 *
 * Reglas que usamos (las mismas de la versión sin POO):
 *   - La definitiva es el promedio de las 3 notas (todas valen igual).
 *   - Aprobado: definitiva de 3.0 para arriba
 *   - Habilita: definitiva entre 2.0 y 2.99
 *   - Reprueba: definitiva menor a 2.0, o si la asistencia es menor al 80%
 */
public class Estudiante {

    // constantes
    public static final int NUM_NOTAS = 3;
    private static final double NOTA_APROBAR = 3.0;
    private static final double NOTA_HABILITAR = 2.0;
    private static final double ASISTENCIA_MINIMA = 80.0;

    // atributos
    private String codigo;
    private String nombre;
    private double[] notas;
    private double asistencia;

    // constructor
    public Estudiante(String codigo, String nombre, double[] notas, double asistencia) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.notas = notas;
        this.asistencia = asistencia;
    }

    // getters
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getNota(int posicion) {
        return notas[posicion];
    }

    public double getAsistencia() {
        return asistencia;
    }

    // calcula la definitiva (promedio de las 3 notas) redondeada a 2 decimales
    public double calcularDefinitiva() {
        double suma = 0;
        for (int j = 0; j < NUM_NOTAS; j++) {
            suma = suma + notas[j];
        }
        double promedio = suma / NUM_NOTAS;
        return Math.round(promedio * 100) / 100.0;
    }

    // devuelve el estado segun la definitiva y la asistencia
    public String determinarEstado() {
        double definitiva = calcularDefinitiva();
        if (asistencia < ASISTENCIA_MINIMA) {
            return "Reprueba (inasistencia)";
        } else if (definitiva >= NOTA_APROBAR) {
            return "Aprobado";
        } else if (definitiva >= NOTA_HABILITAR) {
            return "Habilita";
        } else {
            return "Reprueba";
        }
    }
}
