package sinpoo;

/*
 * Taller unidad 3 - Algoritmos modulares
 * Pascual Bravo - Ingeniería de Software
 *
 * Integrantes:
 *   - Daniel Eduardo Idárraga Gutiérrez
 *   - Laura Valentina Ochoa Mona
 *
 * Este es el programa principal. El main solo muestra el menu y llama
 * al programa que el usuario escoja, no tiene mas lógica.
 * Cuando ese programa termina vuelve a salir el menú hasta que el
 * usuario le de 0 para salir.
 */
public class Main {

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = Entrada.leerEnteroEnRango("Elija una opción: ", 0, 3);
            switch (opcion) {
                case 1:
                    Cafeteria.ejecutar();
                    break;
                case 2:
                    ControlNotas.ejecutar();
                    break;
                case 3:
                    ProduccionFabrica.ejecutar();
                    break;
                case 0:
                    System.out.println("\nGracias por usar el sistema. ¡Hasta luego!");
                    break;
            }
        } while (opcion != 0);
    }

    // procedimiento que imprime las opciones del menú principal
    public static void mostrarMenuPrincipal() {
        System.out.println();
        System.out.println("==============================================");
        System.out.println("     TALLER ALGORITMOS MODULARES - MENÚ");
        System.out.println("==============================================");
        System.out.println(" 1. Inventario de la cafetería universitaria");
        System.out.println(" 2. Control académico de notas");
        System.out.println(" 3. Producción de la fábrica de empaques");
        System.out.println(" 0. Salir");
        System.out.println("==============================================");
    }
}
