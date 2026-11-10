package conpoo;

/*
 * CASO 3 con POO: Control académico para seguimiento de notas
 *
 * Integrantes:
 *   - Daniel Eduardo Idárraga Gutiérrez
 *   - Laura Valentina Ochoa Mona
 *
 * Esta clase maneja un vector de objetos Estudiante (hasta 25).
 * Cada estudiante calcula su propia definitiva y estado, aca solo se
 * registran, se buscan y se sacan los datos del grupo.
 */
public class ControlNotas {

    // constantes
    private static final int MAX_ESTUDIANTES = 25;
    private static final double NOTA_MINIMA = 0.0;
    private static final double NOTA_MAXIMA = 5.0;

    // atributos
    private Estudiante[] estudiantes = new Estudiante[MAX_ESTUDIANTES];
    private int totalEstudiantes = 0;

    // submenú del caso
    public void ejecutar() {
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

    private void mostrarMenu() {
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

    // pide los datos, crea el objeto Estudiante y lo guarda en el vector
    private void registrarEstudiante() {
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
            repetido = buscarEstudiante(codigo) != null;
            if (repetido) {
                System.out.println("  Error: ya existe un estudiante con el código " + codigo + ".");
            }
        } while (repetido);

        String nombre = Entrada.leerTexto("Nombre: ");

        // las 3 notas se piden validando que esten entre 0 y 5
        double[] notas = new double[Estudiante.NUM_NOTAS];
        for (int j = 0; j < Estudiante.NUM_NOTAS; j++) {
            notas[j] = Entrada.leerDecimalEnRango("Nota parcial " + (j + 1) + " (0.0 a 5.0): ",
                    NOTA_MINIMA, NOTA_MAXIMA);
        }
        double asistencia = Entrada.leerDecimalEnRango("Porcentaje de asistencia (0 a 100): ", 0, 100);

        Estudiante nuevo = new Estudiante(codigo, nombre, notas, asistencia);
        estudiantes[totalEstudiantes] = nuevo;
        totalEstudiantes++;

        System.out.println("Estudiante registrado (" + totalEstudiantes + " de " + MAX_ESTUDIANTES + ").");
        System.out.printf("Definitiva: %.2f  ->  %s%n", nuevo.calcularDefinitiva(), nuevo.determinarEstado());
    }

    // busca un estudiante por código, devuelve el objeto o null si no esta
    private Estudiante buscarEstudiante(String codigo) {
        for (int i = 0; i < totalEstudiantes; i++) {
            if (estudiantes[i].getCodigo().equalsIgnoreCase(codigo)) {
                return estudiantes[i];
            }
        }
        return null;
    }

    // muestra todos los estudiantes con su definitiva y estado
    private void mostrarListado() {
        System.out.println("\n--- Listado de estudiantes ---");
        if (totalEstudiantes == 0) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }
        mostrarEncabezado();
        for (int i = 0; i < totalEstudiantes; i++) {
            mostrarFilaEstudiante(estudiantes[i]);
        }
    }

    private void mostrarEncabezado() {
        System.out.printf("%-8s %-22s %5s %5s %5s %7s %6s  %s%n",
                "CÓDIGO", "NOMBRE", "N1", "N2", "N3", "ASIST.", "DEF.", "ESTADO");
    }

    // imprime una fila con los datos de un estudiante
    private void mostrarFilaEstudiante(Estudiante e) {
        System.out.printf("%-8s %-22s %5.1f %5.1f %5.1f %6.0f%% %6.2f  %s%n",
                e.getCodigo(), e.getNombre(), e.getNota(0), e.getNota(1), e.getNota(2),
                e.getAsistencia(), e.calcularDefinitiva(), e.determinarEstado());
    }

    // busca un estudiante y muestra su información
    private void consultarEstudiante() {
        System.out.println("\n--- Consultar estudiante ---");
        if (totalEstudiantes == 0) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }
        String codigo = Entrada.leerTexto("Código a buscar: ");
        Estudiante e = buscarEstudiante(codigo);
        if (e == null) {
            System.out.println("No se encontró un estudiante con el código " + codigo.toUpperCase() + ".");
        } else {
            mostrarEncabezado();
            mostrarFilaEstudiante(e);
        }
    }

    // promedio de las definitivas de todo el grupo
    private double calcularPromedioGrupo() {
        double suma = 0;
        double promedio;
        for (int i = 0; i < totalEstudiantes; i++) {
            suma = suma + estudiantes[i].calcularDefinitiva();
        }
        return suma / totalEstudiantes;
    }

    // muestra el promedio general del grupo
    private void mostrarPromedioGrupo() {
        System.out.println("\n--- Promedio general del grupo ---");
        if (totalEstudiantes == 0) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }
        System.out.printf("Estudiantes: %d  |  Promedio general: %.2f%n",
                totalEstudiantes, calcularPromedioGrupo());
    }

    // devuelve el estudiante con la mejor definitiva
    private Estudiante buscarMejorPromedio() {
        Estudiante mejor = estudiantes[0];
        for (int i = 1; i < totalEstudiantes; i++) {
            if (estudiantes[i].calcularDefinitiva() > mejor.calcularDefinitiva()) {
                mejor = estudiantes[i];
            }
        }
        return mejor;
    }

    // devuelve el estudiante con la peor definitiva
    private Estudiante buscarPeorPromedio() {
        Estudiante peor = estudiantes[0];
        for (int i=1; i<totalEstudiantes; i++) {
            if (estudiantes[i].calcularDefinitiva() < peor.calcularDefinitiva()) {
                peor = estudiantes[i];
            }
        }
        return peor;
    }

    // muestra el mejor y el peor promedio
    private void mostrarMejorYPeor() {
        System.out.println("\n--- Mejor y peor promedio ---");
        if (totalEstudiantes == 0) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }
        Estudiante mejor = buscarMejorPromedio();
        Estudiante peor = buscarPeorPromedio();
        System.out.printf("Mejor promedio: %s (%s) con %.2f%n", mejor.getNombre(), mejor.getCodigo(), mejor.calcularDefinitiva());
        System.out.printf("Peor promedio:  %s (%s) con %.2f%n", peor.getNombre(), peor.getCodigo(), peor.calcularDefinitiva());
    }
}
