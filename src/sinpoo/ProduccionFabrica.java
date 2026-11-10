package sinpoo;

/*
 * CASO 5: Control de producción para una fábrica de empaques
 *
 * Integrantes:
 *   - Daniel Eduardo Idárraga Gutiérrez
 *   - Laura Valentina Ochoa Mona
 *
 * Maneja 10 operarios y 5 dias de producción (de lunes a viernes).
 * Los códigos y nombres van en vectores y la producción en una matriz
 * de 10 filas (operarios) por 5 columnas (días).
 *
 * Reglas que usamos:
 *   - Ningún valor de producción puede ser negativo.
 *   - Para los calculos solo se tienen en cuenta los operarios que ya
 *     tienen la producción de la semana ingresada.
 *   - Un día es de BAJA producción cuando el total de ese día (sumando
 *     todos los operarios) es menor al 80% del promedio diario de la semana.
 */
public class ProduccionFabrica {

    // constantes
    private static final int MAX_OPERARIOS = 10;
    private static final int DIAS = 5;
    private static final String[] NOMBRES_DIAS = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
    private static final double PORCENTAJE_BAJA = 0.80;

    // vectores y matriz con los datos
    private static String[] codigos = new String[MAX_OPERARIOS];
    private static String[] nombres = new String[MAX_OPERARIOS];
    private static int[][] produccion = new int[MAX_OPERARIOS][DIAS];
    private static boolean[] tieneProduccion = new boolean[MAX_OPERARIOS];

    private static int totalOperarios = 0;

    // procedimiento principal del caso (submenú)
    public static void ejecutar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = Entrada.leerEnteroEnRango("Elija una opción: ", 0, 6);
            switch (opcion) {
                case 1:
                    registrarOperario();
                    break;
                case 2:
                    ingresarProduccion();
                    break;
                case 3:
                    mostrarProduccionSemanal();
                    break;
                case 4:
                    mostrarMejorOperario();
                    break;
                case 5:
                    mostrarPromedioGeneral();
                    break;
                case 6:
                    mostrarDiasBajaProduccion();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("------ PRODUCCIÓN FÁBRICA DE EMPAQUES ------");
        System.out.println(" 1. Registrar operario");
        System.out.println(" 2. Ingresar producción diaria de un operario");
        System.out.println(" 3. Producción semanal por operario");
        System.out.println(" 4. Mejor operario de la semana");
        System.out.println(" 5. Promedio general de producción");
        System.out.println(" 6. Detectar días con baja producción");
        System.out.println(" 0. Volver al menú principal");
        System.out.println("--------------------------------------------");
    }

    // registra un operario si todavía hay cupo
    private static void registrarOperario() {
        System.out.println("\n--- Registrar operario ---");
        if (totalOperarios >= MAX_OPERARIOS) {
            System.out.println("No hay cupo: ya se registraron los " + MAX_OPERARIOS + " operarios.");
            return;
        }

        String codigo;
        boolean repetido;
        do {
            codigo = Entrada.leerTexto("Código del operario: ").toUpperCase();
            repetido = buscarOperario(codigo) != -1;
            if (repetido) {
                System.out.println("  Error: ya existe un operario con el código " + codigo + ".");
            }
        } while (repetido);

        codigos[totalOperarios] = codigo;
        nombres[totalOperarios] = Entrada.leerTexto("Nombre: ");
        tieneProduccion[totalOperarios] = false;
        totalOperarios++;
        System.out.println("Operario registrado (" + totalOperarios + " de " + MAX_OPERARIOS + ").");
    }

    // busca un operario por código, devuelve la posición o -1
    private static int buscarOperario(String codigo) {
        for (int i = 0; i < totalOperarios; i++) {
            if (codigos[i].equalsIgnoreCase(codigo)) {
                return i;
            }
        }
        return -1;
    }

    // pide la producción de los 5 días de un operario.
    // si ya tenia producción se reemplaza (sirve por si se equivocaron)
    private static void ingresarProduccion() {
        System.out.println("\n--- Ingresar producción diaria ---");
        if (totalOperarios == 0) {
            System.out.println("Primero debe registrar operarios.");
            return;
        }

        String codigo = Entrada.leerTexto("Código del operario: ");
        int pos = buscarOperario(codigo);
        if (pos == -1) {
            System.out.println("No existe un operario con el código " + codigo.toUpperCase() + ".");
            return;
        }

        System.out.println("Operario: " + nombres[pos]);
        for (int dia = 0; dia < DIAS; dia++) {
            // como el minimo es 0 no deja meter numeros negativos
            produccion[pos][dia] = Entrada.leerEnteroEnRango(
                    "  Unidades del " + NOMBRES_DIAS[dia] + ": ", 0, 100000);
        }
        tieneProduccion[pos] = true;
        // System.out.println(Arrays.toString(produccion[pos]));
        System.out.println("Producción guardada. Total de la semana: " + calcularProduccionSemanal(pos) + " unidades.");
    }

    // suma la fila de la matriz (lo que hizo un operario en la semana)
    private static int calcularProduccionSemanal(int pos) {
        int suma = 0;
        for (int dia = 0; dia < DIAS; dia++) {
            suma = suma + produccion[pos][dia];
        }
        return suma;
    }

    // suma la columna de la matriz (el total de un día entre todos los operarios)
    private static int calcularTotalDia(int dia) {
        int suma = 0;
        for (int i = 0; i < totalOperarios; i++) {
            if (tieneProduccion[i]) {
                suma = suma + produccion[i][dia];
            }
        }
        return suma;
    }

    // cuenta cuantos operarios tienen la producción ingresada
    private static int contarOperariosConProduccion() {
        int contador = 0;
        for (int i = 0; i < totalOperarios; i++) {
            if (tieneProduccion[i]) {
                contador++;
            }
        }
        return contador;
    }

    // dice si hay datos para poder hacer los cálculos,
    // si no hay muestra el mensaje
    private static boolean hayDatos() {
        if (contarOperariosConProduccion() == 0) {
            System.out.println("Todavía no hay producción ingresada.");
            return false;
        }
        return true;
    }

    // muestra la matriz de producción con el total de cada operario
    private static void mostrarProduccionSemanal() {
        System.out.println("\n--- Producción semanal por operario ---");
        if (!hayDatos()) {
            return;
        }
        System.out.printf("%-8s %-18s", "CÓDIGO", "NOMBRE");
        for (int dia = 0; dia < DIAS; dia++) {
            System.out.printf(" %9s", NOMBRES_DIAS[dia].substring(0, 3).toUpperCase());
        }
        System.out.printf(" %9s%n", "SEMANA");

        for (int i = 0; i < totalOperarios; i++) {
            if (tieneProduccion[i]) {
                System.out.printf("%-8s %-18s", codigos[i], nombres[i]);
                for (int dia = 0; dia < DIAS; dia++) {
                    System.out.printf(" %9d", produccion[i][dia]);
                }
                System.out.printf(" %9d%n", calcularProduccionSemanal(i));
            } else {
                System.out.printf("%-8s %-18s (sin producción ingresada)%n", codigos[i], nombres[i]);
            }
        }
    }

    // devuelve la posición del operario que mas produjo en la semana
    private static int buscarMejorOperario() {
        int mejor = -1;
        for (int i = 0; i < totalOperarios; i++) {
            if (tieneProduccion[i]) {
                if (mejor == -1 || calcularProduccionSemanal(i) > calcularProduccionSemanal(mejor)) {
                    mejor = i;
                }
            }
        }
        return mejor;
    }

    // muestra el mejor operario de la semana
    private static void mostrarMejorOperario() {
        System.out.println("\n--- Mejor operario de la semana ---");
        if (!hayDatos()) {
            return;
        }
        int mejor = buscarMejorOperario();
        System.out.println("El mejor operario es " + nombres[mejor] + " (" + codigos[mejor] + ") con "
                + calcularProduccionSemanal(mejor) + " unidades en la semana.");
    }

    // suma toda la producción de la semana (toda la matriz)
    private static int calcularProduccionTotal() {
        int total = 0;
        for(int dia=0; dia<DIAS; dia++) {
            total = total + calcularTotalDia(dia);
        }
        return total;
    }

    private static void mostrarPromedioGeneral() {
        System.out.println("\n--- Promedio general de producción ---");
        if (!hayDatos()) {
            return;
        }
        int operarios = contarOperariosConProduccion();
        int total = calcularProduccionTotal();
        double promedioPorOperarioDia = (double) total / (operarios * DIAS);
        double promedioSemanalOperario = (double) total / operarios;

        System.out.println("Operarios con producción: " + operarios);
        System.out.println("Producción total de la semana: " + total + " unidades");
        System.out.printf("Promedio por operario por día: %.2f unidades%n", promedioPorOperarioDia);
        System.out.printf("Promedio semanal por operario: %.2f unidades%n", promedioSemanalOperario);
    }

    // muestra el total de cada día y marca los que tuvieron baja producción
    private static void mostrarDiasBajaProduccion() {
        System.out.println("\n--- Días con baja producción ---");
        if (!hayDatos()) {
            return;
        }
        double promedioDiario = (double) calcularProduccionTotal() / DIAS;
        double limite = promedioDiario * PORCENTAJE_BAJA;
        System.out.printf("Promedio diario de la planta: %.2f unidades%n", promedioDiario);
        System.out.printf("Se considera BAJA si el día produce menos de %.2f (80%% del promedio)%n%n", limite);

        int diasBajos = 0;
        for (int dia = 0; dia < DIAS; dia++) {
            int totalDia = calcularTotalDia(dia);
            String marca = "";
            if (totalDia < limite) {
                marca = "  <-- BAJA PRODUCCIÓN";
                diasBajos++;
            }
            System.out.printf("%-10s %8d%s%n", NOMBRES_DIAS[dia], totalDia, marca);
        }

        if (diasBajos == 0) {
            System.out.println("\nNo hubo días con baja producción esta semana.");
        } else {
            System.out.println("\nDías con baja producción: " + diasBajos);
        }
    }
}
