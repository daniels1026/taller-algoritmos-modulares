package sinpoo;

/*
 * CASO 3: Control académico para seguimiento de notas
 *
 * Integrantes:
 *   - Daniel Eduardo Idárraga Gutiérrez
 *   - Laura Valentina Ochoa Mona
 *
 * Se registran hasta 25 estudiantes. El código, nombre y asistencia van
 * en vectores y las 3 notas parciales en una matriz (cada fila es un
 * estudiante y cada columna es una nota).
 *
 * Reglas que usamos:
 *   - La definitiva es el promedio de las 3 notas (todas valen igual).
 *   - Aprobado: definitiva de 3.0 para arriba
 *   - Habilita: definitiva entre 2.0 y 2.99
 *   - Reprueba: definitiva menor a 2.0, o si la asistencia es menor al 80%
 *   - Las notas tienen que estar entre 0.0 y 5.0 y la asistencia entre 0 y 100.
 */
public class ControlNotas {

    // constantes
    private static final int MAX_ESTUDIANTES = 25;
    private static final int NUM_NOTAS = 3;
    private static final double NOTA_MINIMA = 0.0;
    private static final double NOTA_MAXIMA = 5.0;
    private static final double NOTA_APROBAR = 3.0;
    private static final double NOTA_HABILITAR = 2.0;
    private static final double ASISTENCIA_MINIMA = 80.0;

    // vectores y matriz con los datos
    private static String[] codigos = new String[MAX_ESTUDIANTES];
    private static String[] nombres = new String[MAX_ESTUDIANTES];
    private static double[][] notas = new double[MAX_ESTUDIANTES][NUM_NOTAS];
    private static double[] asistencias = new double[MAX_ESTUDIANTES];

    private static int totalEstudiantes = 0;

    // procedimiento principal del caso (submenú)
    public static void ejecutar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = Entrada.leerEnteroEnRango("Elija una opción: ", 0, 5);
            switch (opcion) {
                case 1:
                    registrarEstudiante();
                    break;
                case 2:
                    mostrarListado();
                    break;
                case 3:
                    consultarEstudiante();
                    break;
                case 4:
                    mostrarPromedioGrupo();
                    break;
                case 5:
                    mostrarMejorYPeor();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("------ CONTROL ACADÉMICO DE NOTAS ------");
        System.out.println(" 1. Registrar estudiante");
        System.out.println(" 2. Listado con definitiva y estado");
        System.out.println(" 3. Consultar estudiante por código");
        System.out.println(" 4. Promedio general del grupo");
        System.out.println(" 5. Mejor y peor promedio");
        System.out.println(" 0. Volver al menú principal");
        System.out.println("----------------------------------------");
    }

    // registra un estudiante con las notas y la asistencia
    private static void registrarEstudiante() {
        System.out.println("\n--- Registrar estudiante ---");
        if (totalEstudiantes >= MAX_ESTUDIANTES) {
            System.out.println("No hay cupo: ya se registraron los " + MAX_ESTUDIANTES + " estudiantes.");
            return;
        }

        // el código no se puede repetir
        String codigo;
        boolean repetido;
        do {
            codigo = Entrada.leerTexto("Código: ").toUpperCase();
            repetido = buscarEstudiante(codigo) != -1;
            if (repetido) {
                System.out.println("  Error: ya existe un estudiante con el código " + codigo + ".");
            }
        } while (repetido);

        int pos = totalEstudiantes;
        codigos[pos] = codigo;
        nombres[pos] = Entrada.leerTexto("Nombre: ");

        // se llena la fila de la matriz con las 3 notas, validando que esten entre 0 y 5
        for (int j = 0; j < NUM_NOTAS; j++) {
            notas[pos][j] = Entrada.leerDecimalEnRango("Nota parcial " + (j + 1) + " (0.0 a 5.0): ",
                    NOTA_MINIMA, NOTA_MAXIMA);
        }
        asistencias[pos] = Entrada.leerDecimalEnRango("Porcentaje de asistencia (0 a 100): ", 0, 100);
        totalEstudiantes++;

        double definitiva = calcularDefinitiva(pos);
        System.out.println("Estudiante registrado (" + totalEstudiantes + " de " + MAX_ESTUDIANTES + ").");
        System.out.printf("Definitiva: %.2f  ->  %s%n", definitiva, determinarEstado(definitiva, asistencias[pos]));
    }

    // busca un estudiante por código, devuelve la posición o -1 si no esta
    private static int buscarEstudiante(String codigo) {
        for (int i = 0; i < totalEstudiantes; i++) {
            if (codigos[i].equalsIgnoreCase(codigo)) {
                return i;
            }
        }
        return -1;
    }

    // calcula la definitiva (promedio de las 3 notas) y la redondea a 2 decimales
    private static double calcularDefinitiva(int pos) {
        double suma = 0;
        for (int j = 0; j < NUM_NOTAS; j++) {
            suma = suma + notas[pos][j];
        }
        double promedio = suma / NUM_NOTAS;
        return Math.round(promedio * 100) / 100.0;
    }

    // devuelve el estado segun la definitiva y la asistencia
    private static String determinarEstado(double definitiva, double asistencia) {
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

    // muestra todos los estudiantes con su definitiva y estado
    private static void mostrarListado() {
        System.out.println("\n--- Listado de estudiantes ---");
        if (totalEstudiantes == 0) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }
        System.out.printf("%-8s %-22s %5s %5s %5s %7s %6s  %s%n",
                "CÓDIGO", "NOMBRE", "N1", "N2", "N3", "ASIST.", "DEF.", "ESTADO");
        for (int i = 0; i < totalEstudiantes; i++) {
            mostrarFilaEstudiante(i);
        }
    }

    private static void mostrarFilaEstudiante(int pos) {
        double definitiva = calcularDefinitiva(pos);
        System.out.printf("%-8s %-22s %5.1f %5.1f %5.1f %6.0f%% %6.2f  %s%n",
                codigos[pos], nombres[pos], notas[pos][0], notas[pos][1], notas[pos][2],
                asistencias[pos], definitiva, determinarEstado(definitiva, asistencias[pos]));
    }

    // busca un estudiante y muestra su información
    private static void consultarEstudiante() {
        System.out.println("\n--- Consultar estudiante ---");
        if (totalEstudiantes == 0) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }
        String codigo = Entrada.leerTexto("Código a buscar: ");
        int pos = buscarEstudiante(codigo);
        if (pos == -1) {
            System.out.println("No se encontró un estudiante con el código " + codigo.toUpperCase() + ".");
        } else {
            System.out.printf("%-8s %-22s %5s %5s %5s %7s %6s  %s%n",
                    "CÓDIGO", "NOMBRE", "N1", "N2", "N3", "ASIST.", "DEF.", "ESTADO");
            mostrarFilaEstudiante(pos);
        }
    }

    // promedio de las definitivas de todo el grupo
    private static double calcularPromedioGrupo() {
        double suma = 0;
        double promedio;
        for (int i = 0; i < totalEstudiantes; i++) {
            suma = suma + calcularDefinitiva(i);
        }
        return suma / totalEstudiantes;
    }

    // muestra el promedio general del grupo
    private static void mostrarPromedioGrupo() {
        System.out.println("\n--- Promedio general del grupo ---");
        if (totalEstudiantes == 0) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }
        System.out.printf("Estudiantes: %d  |  Promedio general: %.2f%n",
                totalEstudiantes, calcularPromedioGrupo());
    }

    // devuelve la posición del que tiene la mejor definitiva
    private static int buscarMejorPromedio() {
        int mejor = 0;
        for (int i = 1; i < totalEstudiantes; i++) {
            if (calcularDefinitiva(i) > calcularDefinitiva(mejor)) {
                mejor = i;
            }
        }
        return mejor;
    }

    // devuelve la posición del que tiene la peor definitiva
    private static int buscarPeorPromedio() {
        int peor = 0;
        for (int i=1; i<totalEstudiantes; i++) {
            if (calcularDefinitiva(i) < calcularDefinitiva(peor)) {
                peor = i;
            }
        }
        return peor;
    }

    // muestra el mejor y el peor promedio
    private static void mostrarMejorYPeor() {
        System.out.println("\n--- Mejor y peor promedio ---");
        if (totalEstudiantes == 0) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }
        int mejor = buscarMejorPromedio();
        int peor = buscarPeorPromedio();
        System.out.printf("Mejor promedio: %s (%s) con %.2f%n", nombres[mejor], codigos[mejor], calcularDefinitiva(mejor));
        System.out.printf("Peor promedio:  %s (%s) con %.2f%n", nombres[peor], codigos[peor], calcularDefinitiva(peor));
    }
}
