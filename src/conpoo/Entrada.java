package conpoo;

import java.util.Scanner;

/*
 * Integrantes: Daniel Eduardo Idárraga Gutiérrez, Laura Valentina Ochoa Mona
 *
 * Aca estan todas las funciones para leer datos del teclado.
 * Cada una vuelve a pedir el dato hasta que sea valido, asi el programa
 * no se cae si el usuario escribe letras donde va un número o pone un
 * valor que no está en el rango.
 * Los 3 programas usan este mismo archivo para no repetir el mismo
 * codigo de validación en cada uno.
 */
public class Entrada {

    // un solo scanner para todo el programa
    private static final Scanner teclado = new Scanner(System.in);

    // función para leer un texto, no deja que quede vacío
    public static String leerTexto(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = teclado.nextLine().trim();
            if(texto.isEmpty()) {
                System.out.println("  Error: este dato no puede quedar vacío.");
            }
        } while (texto.isEmpty());
        return texto;
    }

    // lee un número entero, si escriben letras sale el error y lo pide otra vez
    public static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = teclado.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("  Error: debe escribir un número entero.");
            }
        }
    }

    // lee un entero que tiene que estar entre el minimo y el maximo
    public static int leerEnteroEnRango(String mensaje, int minimo, int maximo) {
        int numero;
        boolean valido;
        do {
            numero = leerEntero(mensaje);
            valido = numero >= minimo && numero <= maximo;
            if (!valido) {
                System.out.println("  Error: el valor debe estar entre " + minimo + " y " + maximo + ".");
            }
        } while (!valido);
        return numero;
    }

    // lee un número con decimales, sirve con punto o con coma (3.5 o 3,5)
    public static double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = teclado.nextLine().trim().replace(',', '.');
            try {
                return Double.parseDouble(linea);
            } catch (NumberFormatException e) {
                System.out.println("  Error: debe escribir un número (ejemplo: 3.5).");
            }
        }
    }

    public static double leerDecimalEnRango(String mensaje, double minimo, double maximo) {
        double numero;
        boolean valido;
        do {
            numero = leerDecimal(mensaje);
            valido = numero >= minimo && numero <= maximo;
            if (!valido) {
                System.out.println("  Error: el valor debe estar entre " + minimo + " y " + maximo + ".");
            }
        } while (!valido);
        return numero;
    }
}
