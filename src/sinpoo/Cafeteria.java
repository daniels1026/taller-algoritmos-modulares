package sinpoo;

/*
 * CASO 2: Control de inventario para la cafetería de la universidad
 *
 * Integrantes:
 *   - Daniel Eduardo Idárraga Gutiérrez
 *   - Laura Valentina Ochoa Mona
 *
 * Maneja hasta 20 productos. Los guardamos en vectores paralelos, osea
 * que la posición i de cada vector es el mismo producto.
 * Se puede registrar productos, vender, actualizar el inventario,
 * ver los agotados y sacar el total vendido del día.
 * No deja vender si no hay stock suficiente.
 */
public class Cafeteria {

    // constantes
    private static final int MAX_PRODUCTOS = 20;
    private static final String[] CATEGORIAS = {"Bebidas", "Panadería", "Snacks"};

    // vectores paralelos con los datos de cada producto
    private static String[] codigos = new String[MAX_PRODUCTOS];
    private static String[] nombres = new String[MAX_PRODUCTOS];
    private static double[] precios = new double[MAX_PRODUCTOS];
    private static int[] cantidades = new int[MAX_PRODUCTOS];
    private static String[] categorias = new String[MAX_PRODUCTOS];

    // contadores y acumuladores
    private static int totalProductos = 0;      // cuantos productos hay registrados
    private static double totalVendidoDia = 0;  // plata vendida en el día
    private static int numeroVentas = 0;        // cuántas ventas se hicieron

    // procedimiento principal del caso, muestra el submenú hasta que
    // el usuario quiera volver al menú principal
    public static void ejecutar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = Entrada.leerEnteroEnRango("Elija una opción: ", 0, 6);
            switch (opcion) {
                case 1:
                    registrarProducto();
                    break;
                case 2:
                    realizarVenta();
                    break;
                case 3:
                    actualizarInventario();
                    break;
                case 4:
                    mostrarAgotados();
                    break;
                case 5:
                    mostrarTotalVendido();
                    break;
                case 6:
                    listarProductos();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("------ INVENTARIO CAFETERÍA ------");
        System.out.println(" 1. Registrar producto");
        System.out.println(" 2. Realizar venta");
        System.out.println(" 3. Actualizar inventario (entrada de mercancía)");
        System.out.println(" 4. Mostrar productos agotados");
        System.out.println(" 5. Total vendido del día");
        System.out.println(" 6. Listar todos los productos");
        System.out.println(" 0. Volver al menú principal");
        System.out.println("----------------------------------");
    }

    // registra un producto nuevo si todavía hay espacio
    private static void registrarProducto() {
        System.out.println("\n--- Registrar producto ---");
        if (totalProductos >= MAX_PRODUCTOS) {
            System.out.println("No hay espacio: ya se registraron los " + MAX_PRODUCTOS + " productos.");
            return;
        }

        // el código no se puede repetir
        String codigo;
        boolean repetido;
        do {
            codigo = Entrada.leerTexto("Código: ").toUpperCase();
            repetido = buscarProducto(codigo) != -1;
            if (repetido) {
                System.out.println("  Error: ya existe un producto con el código " + codigo + ".");
            }
        } while (repetido);

        // se guarda en la siguiente posicion libre
        int pos = totalProductos;
        codigos[pos] = codigo;
        nombres[pos] = Entrada.leerTexto("Nombre: ");
        precios[pos] = Entrada.leerDecimalEnRango("Precio unitario ($): ", 1, 1000000);
        cantidades[pos] = Entrada.leerEnteroEnRango("Cantidad disponible: ", 0, 10000);
        categorias[pos] = elegirCategoria();
        totalProductos++;

        System.out.println("Producto registrado correctamente (" + totalProductos + " de " + MAX_PRODUCTOS + ").");
    }

    // muestra las categorías y devuelve la que escoja el usuario
    private static String elegirCategoria() {
        System.out.println("Categorías:");
        for(int i=0; i<CATEGORIAS.length; i++) {
            System.out.println("  " + (i + 1) + ". " + CATEGORIAS[i]);
        }
        int opcion = Entrada.leerEnteroEnRango("Elija la categoría: ", 1, CATEGORIAS.length);
        return CATEGORIAS[opcion - 1];
    }

    // busca un producto por el código.
    // devuelve la posición donde está o -1 si no existe
    private static int buscarProducto(String codigo) {
        for (int i = 0; i < totalProductos; i++) {
            if (codigos[i].equalsIgnoreCase(codigo)) {
                return i;
            }
        }
        return -1;
    }

    // revisa si alcanza el stock
    private static boolean hayStockSuficiente(int pos, int cantidadPedida) {
        return cantidades[pos] >= cantidadPedida;
    }

    // calcula cuanto vale una venta
    private static double calcularSubtotal(double precio, int cantidad) {
        return precio * cantidad;
    }

    // hace una venta revisando primero que si haya stock
    private static void realizarVenta() {
        System.out.println("\n--- Realizar venta ---");
        if (totalProductos == 0) {
            System.out.println("No hay productos registrados.");
            return;
        }

        String codigo = Entrada.leerTexto("Código del producto: ");
        int pos = buscarProducto(codigo);
        //System.out.println("pos = " + pos);
        if (pos == -1) {
            System.out.println("No existe un producto con el código " + codigo.toUpperCase() + ".");
            return;
        }
        if (cantidades[pos] == 0) {
            System.out.println("El producto " + nombres[pos] + " está AGOTADO. No se puede vender.");
            return;
        }

        System.out.println("Producto: " + nombres[pos] + " | Precio: " + formatearPesos(precios[pos])
                + " | Disponibles: " + cantidades[pos]);
        int cantidad = Entrada.leerEnteroEnRango("Cantidad a vender: ", 1, 10000);

        if (!hayStockSuficiente(pos, cantidad)) {
            System.out.println("Stock insuficiente: solo hay " + cantidades[pos] + " unidades. Venta cancelada.");
            return;
        }

        double subtotal = calcularSubtotal(precios[pos], cantidad);
        cantidades[pos] = cantidades[pos] - cantidad;   // se descuenta del inventario
        totalVendidoDia = totalVendidoDia + subtotal;   // se suma a lo vendido del dia
        numeroVentas++;

        System.out.println("Venta realizada: " + cantidad + " x " + nombres[pos] + " = " + formatearPesos(subtotal));
        System.out.println("Quedan " + cantidades[pos] + " unidades.");
        if (cantidades[pos] == 0) {
            System.out.println("Atención: el producto quedó agotado.");
        }
    }

    // le suma unidades al inventario de un producto (cuando llega mercancía)
    private static void actualizarInventario() {
        System.out.println("\n--- Actualizar inventario ---");
        if (totalProductos == 0) {
            System.out.println("No hay productos registrados.");
            return;
        }

        String codigo = Entrada.leerTexto("Código del producto: ");
        int pos = buscarProducto(codigo);
        if (pos == -1) {
            System.out.println("No existe un producto con el código " + codigo.toUpperCase() + ".");
            return;
        }

        System.out.println("Producto: " + nombres[pos] + " | Disponibles: " + cantidades[pos]);
        int unidades = Entrada.leerEnteroEnRango("Unidades que llegaron: ", 1, 10000);
        cantidades[pos] = cantidades[pos] + unidades;
        System.out.println("Inventario actualizado. Ahora hay " + cantidades[pos] + " unidades.");
    }

    // muestra los productos que tienen cantidad 0
    private static void mostrarAgotados() {
        System.out.println("\n--- Productos agotados ---");
        int agotados = 0;
        for (int i = 0; i < totalProductos; i++) {
            if(cantidades[i]==0) {
                System.out.println("- " + codigos[i] + " | " + nombres[i] + " | " + categorias[i]);
                agotados++;
            }
        }
        if (agotados == 0) {
            System.out.println("No hay productos agotados.");
        } else {
            System.out.println("Total agotados: " + agotados);
        }
    }

    // muestra cuanto se vendio en el día
    private static void mostrarTotalVendido() {
        System.out.println("\n--- Total vendido del día ---");
        System.out.println("Número de ventas: " + numeroVentas);
        System.out.println("Total vendido:    " + formatearPesos(totalVendidoDia));
    }

    private static void listarProductos() {
        System.out.println("\n--- Listado de productos ---");
        if (totalProductos == 0) {
            System.out.println("No hay productos registrados.");
            return;
        }
        System.out.printf("%-8s %-20s %-11s %12s %10s%n", "CÓDIGO", "NOMBRE", "CATEGORÍA", "PRECIO", "CANTIDAD");
        for (int i = 0; i < totalProductos; i++) {
            System.out.printf("%-8s %-20s %-11s %12s %10d%n",
                    codigos[i], nombres[i], categorias[i], formatearPesos(precios[i]), cantidades[i]);
        }
    }

    // convierte el valor a texto con formato de plata
    private static String formatearPesos(double valor) {
        return String.format("$%,.0f", valor);
    }
}
