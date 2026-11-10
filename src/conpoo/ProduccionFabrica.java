package conpoo;

/*
 * CASO 5 con POO: Control de producción para una fábrica de empaques
 *
 * Integrantes:
 *   - Daniel Eduardo Idárraga Gutiérrez
 *   - Laura Valentina Ochoa Mona
 *
 * Esta clase maneja un vector de objetos Operario (hasta 10) y una
 * matriz de 10 x 5 con la producción. La fila i de la matriz es la
 * producción del operario que está en la posición i del vector.
 *
 * Reglas que usamos (las mismas de la versión sin POO):
 *   - Ningún valor de producción puede ser negativo.
 *   - Para los calculos solo se tienen en cuenta los operarios que ya
 *     tienen la producción de la semana ingresada.
 *   - Un día es de BAJA producción cuando el total de ese día es menor
 *     al 80% del promedio diario de la semana.
 */
public class ProduccionFabrica {

    // constantes
    private static final int MAX_OPERARIOS = 10;
    private static final int DIAS = 5;
    private static final String[] NOMBRES_DIAS = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
    private static final double PORCENTAJE_BAJA = 0.80;

    // atributos
    private Operario[] operarios = new Operario[MAX_OPERARIOS];
    private int[][] produccion = new int[MAX_OPERARIOS][DIAS];
    private int totalOperarios = 0;

    // submenú del caso
    public void ejecutar() {
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

    private void mostrarMenu() {
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

    // crea un objeto Operario y lo guarda en el vector si hay cupo
    private void registrarOperario() {
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

        String nombre = Entrada.leerTexto("Nombre: ");
        operarios[totalOperarios] = new Operario(codigo, nombre);
        totalOperarios++;
        System.out.println("Operario registrado (" + totalOperarios + " de " + MAX_OPERARIOS + ").");
    }

    // busca un operario por código, devuelve la posición o -1.
    // aca se devuelve la posición (no el objeto) porque con ella se
    // encuentra la fila de la matriz
    private int buscarOperario(String codigo) {
        for (int i = 0; i < totalOperarios; i++) {
            if (operarios[i].getCodigo().equalsIgnoreCase(codigo)) {
                return i;
            }
        }
        return -1;
    }

    // pide la producción de los 5 días de un operario.
    // si ya tenia producción se reemplaza (sirve por si se equivocaron)
    private void ingresarProduccion() {
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

        System.out.println("Operario: " + operarios[pos].getNombre());
        for (int dia = 0; dia < DIAS; dia++) {
            // como el minimo es 0 no deja meter numeros negativos
            produccion[pos][dia] = Entrada.leerEnteroEnRango(
                    "  Unidades del " + NOMBRES_DIAS[dia] + ": ", 0, 100000);
        }
        operarios[pos].marcarProduccionIngresada();
        // System.out.println(Arrays.toString(produccion[pos]));
        System.out.println("Producción guardada. Total de la semana: " + calcularProduccionSemanal(pos) + " unidades.");
    }

    // suma la fila de la matriz (lo que hizo un operario en la semana)
    private int calcularProduccionSemanal(int pos) {
        int suma = 0;
        for (int dia = 0; dia < DIAS; dia++) {
            suma = suma + produccion[pos][dia];
        }
        return suma;
    }

    // suma la columna de la matriz (el total de un día entre todos los operarios)
    private int calcularTotalDia(int dia) {
        int suma = 0;
        for (int i = 0; i < totalOperarios; i++) {
            if (operarios[i].tieneProduccion()) {
                suma = suma + produccion[i][dia];
            }
        }
        return suma;
    }

    // cuenta cuantos operarios tienen la producción ingresada
    private int contarOperariosConProduccion() {
        int contador = 0;
        for (int i = 0; i < totalOperarios; i++) {
            if (operarios[i].tieneProduccion()) {
                contador++;
            }
        }
        return contador;
    }

    // dice si hay datos para poder hacer los cálculos,
    // si no hay muestra el mensaje
    private boolean hayDatos() {
        if (contarOperariosConProduccion() == 0) {
            System.out.println("Todavía no hay producción ingresada.");
            return false;
        }
        return true;
    }

    // muestra la matriz de producción con el total de cada operario
    private void mostrarProduccionSemanal() {
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
            Operario op = operarios[i];
            if (op.tieneProduccion()) {
                System.out.printf("%-8s %-18s", op.getCodigo(), op.getNombre());
                for (int dia = 0; dia < DIAS; dia++) {
                    System.out.printf(" %9d", produccion[i][dia]);
                }
                System.out.printf(" %9d%n", calcularProduccionSemanal(i));
            } else {
                System.out.printf("%-8s %-18s (sin producción ingresada)%n", op.getCodigo(), op.getNombre());
            }
        }
    }

    // devuelve la posición del operario que mas produjo en la semana
    private int buscarMejorOperario() {
        int mejor = -1;
        for (int i = 0; i < totalOperarios; i++) {
            if (operarios[i].tieneProduccion()) {
                if (mejor == -1 || calcularProduccionSemanal(i) > calcularProduccionSemanal(mejor)) {
                    mejor = i;
                }
            }
        }
        return mejor;
    }

    // muestra el mejor operario de la semana
    private void mostrarMejorOperario() {
        System.out.println("\n--- Mejor operario de la semana ---");
        if (!hayDatos()) {
            return;
        }
        int mejor = buscarMejorOperario();
        System.out.println("El mejor operario es " + operarios[mejor].getNombre() + " (" + operarios[mejor].getCodigo()
                + ") con " + calcularProduccionSemanal(mejor) + " unidades en la semana.");
    }

    // suma toda la producción de la semana (toda la matriz)
    private int calcularProduccionTotal() {
        int total = 0;
        for(int dia=0; dia<DIAS; dia++) {
            total = total + calcularTotalDia(dia);
        }
        return total;
    }

    private void mostrarPromedioGeneral() {
        System.out.println("\n--- Promedio general de producción ---");
        if (!hayDatos()) {
            return;
        }
        int cantidadOperarios = contarOperariosConProduccion();
        int total = calcularProduccionTotal();
        double promedioPorOperarioDia = (double) total / (cantidadOperarios * DIAS);
        double promedioSemanalOperario = (double) total / cantidadOperarios;

        System.out.println("Operarios con producción: " + cantidadOperarios);
        System.out.println("Producción total de la semana: " + total + " unidades");
        System.out.printf("Promedio por operario por día: %.2f unidades%n", promedioPorOperarioDia);
        System.out.printf("Promedio semanal por operario: %.2f unidades%n", promedioSemanalOperario);
    }

    // muestra el total de cada día y marca los que tuvieron baja producción
    private void mostrarDiasBajaProduccion() {
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
