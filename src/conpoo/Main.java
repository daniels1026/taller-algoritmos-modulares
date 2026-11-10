package conpoo;

/*
 * Taller unidad 3 - Algoritmos modulares (VERSIÓN CON POO)
 * Pascual Bravo - Ingeniería de Software
 *
 * Integrantes:
 *   - Daniel Eduardo Idárraga Gutiérrez
 *   - Laura Valentina Ochoa Mona
 *
 * Programa principal de la versión con POO.
 * Aca se crea un objeto de cada sistema (cafetería, notas y fábrica) y
 * el main solo muestra el menú y llama al metodo ejecutar() del objeto
 * que el usuario escoja. Como los objetos se crean una sola vez, los
 * datos no se pierden cuando uno sale y vuelve a entrar a un sistema.
 */
public class Main {

    public static void main(String[] args) {
        Cafeteria cafeteria = new Cafeteria();
        ControlNotas controlNotas = new ControlNotas();
        ProduccionFabrica fabrica = new ProduccionFabrica();

        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = Entrada.leerEnteroEnRango("Elija una opción: ", 0, 3);
            switch (opcion) {
                case 1:
                    cafeteria.ejecutar();
                    break;
                case 2:
                    controlNotas.ejecutar();
                    break;
                case 3:
                    fabrica.ejecutar();
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
        System.out.println("  TALLER ALGORITMOS MODULARES - MENÚ (POO)");
        System.out.println("==============================================");
        System.out.println(" 1. Inventario de la cafetería universitaria");
        System.out.println(" 2. Control académico de notas");
        System.out.println(" 3. Producción de la fábrica de empaques");
        System.out.println(" 0. Salir");
        System.out.println("==============================================");
    }
}
