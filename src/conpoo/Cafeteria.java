package conpoo;

/*
 * CASO 2 con POO: Control de inventario para la cafetería de la universidad
 *
 * Integrantes:
 *   - Daniel Eduardo Idárraga Gutiérrez
 *   - Laura Valentina Ochoa Mona
 *
 * Esta clase maneja un vector de objetos Producto (hasta 20).
 * En vez de tener un vector por cada dato como en la versión sin POO,
 * aca cada posición del vector guarda un producto completo.
 */
public class Cafeteria {

    // constantes
    private static final int MAX_PRODUCTOS = 20;
    private static final String[] CATEGORIAS = {"Bebidas", "Panadería", "Snacks"};

    // atributos del objeto cafetería
    private Producto[] productos = new Producto[MAX_PRODUCTOS];
    private int totalProductos = 0;      // cuantos productos hay registrados
    private double totalVendidoDia = 0;  // plata vendida en el día
    private int numeroVentas = 0;        // cuántas ventas se hicieron

    // muestra el submenú hasta que el usuario quiera volver al menú principal
    public void ejecutar() {
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

    private void mostrarMenu() {
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

    // pide los datos, crea el objeto Producto y lo guarda en el vector
    private void registrarProducto() {
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
            repetido = buscarProducto(codigo) != null;
            if (repetido) {
                System.out.println("  Error: ya existe un producto con el código " + codigo + ".");
            }
        } while (repetido);

        String nombre = Entrada.leerTexto("Nombre: ");
        double precio = Entrada.leerDecimalEnRango("Precio unitario ($): ", 1, 1000000);
        int cantidad = Entrada.leerEnteroEnRango("Cantidad disponible: ", 0, 10000);
        String categoria = elegirCategoria();

        productos[totalProductos] = new Producto(codigo, nombre, precio, cantidad, categoria);
        totalProductos++;

        System.out.println("Producto registrado correctamente (" + totalProductos + " de " + MAX_PRODUCTOS + ").");
    }

    // muestra las categorías y devuelve la que escoja el usuario
    private String elegirCategoria() {
        System.out.println("Categorías:");
        for(int i=0; i<CATEGORIAS.length; i++) {
            System.out.println("  " + (i + 1) + ". " + CATEGORIAS[i]);
        }
        int opcion = Entrada.leerEnteroEnRango("Elija la categoría: ", 1, CATEGORIAS.length);
        return CATEGORIAS[opcion - 1];
    }

    // busca un producto por el código.
    // devuelve el objeto o null si no existe
    private Producto buscarProducto(String codigo) {
        for (int i = 0; i < totalProductos; i++) {
            if (productos[i].getCodigo().equalsIgnoreCase(codigo)) {
                return productos[i];
            }
        }
        return null;
    }

    // hace una venta revisando primero que si haya stock
    private void realizarVenta() {
        System.out.println("\n--- Realizar venta ---");
        if (totalProductos == 0) {
            System.out.println("No hay productos registrados.");
            return;
        }

        String codigo = Entrada.leerTexto("Código del producto: ");
        Producto producto = buscarProducto(codigo);
        //System.out.println(producto);
        if (producto == null) {
            System.out.println("No existe un producto con el código " + codigo.toUpperCase() + ".");
            return;
        }
        if (producto.estaAgotado()) {
            System.out.println("El producto " + producto.getNombre() + " está AGOTADO. No se puede vender.");
            return;
        }

        System.out.println("Producto: " + producto.getNombre() + " | Precio: " + formatearPesos(producto.getPrecio())
                + " | Disponibles: " + producto.getCantidad());
        int cantidad = Entrada.leerEnteroEnRango("Cantidad a vender: ", 1, 10000);

        if (!producto.hayStockSuficiente(cantidad)) {
            System.out.println("Stock insuficiente: solo hay " + producto.getCantidad() + " unidades. Venta cancelada.");
            return;
        }

        double subtotal = producto.vender(cantidad);    // el producto se descuenta solo
        totalVendidoDia = totalVendidoDia + subtotal;   // se suma a lo vendido del dia
        numeroVentas++;

        System.out.println("Venta realizada: " + cantidad + " x " + producto.getNombre() + " = " + formatearPesos(subtotal));
        System.out.println("Quedan " + producto.getCantidad() + " unidades.");
        if (producto.estaAgotado()) {
            System.out.println("Atención: el producto quedó agotado.");
        }
    }

    // le suma unidades a un producto (cuando llega mercancía)
    private void actualizarInventario() {
        System.out.println("\n--- Actualizar inventario ---");
        if (totalProductos == 0) {
            System.out.println("No hay productos registrados.");
            return;
        }

        String codigo = Entrada.leerTexto("Código del producto: ");
        Producto producto = buscarProducto(codigo);
        if (producto == null) {
            System.out.println("No existe un producto con el código " + codigo.toUpperCase() + ".");
            return;
        }

        System.out.println("Producto: " + producto.getNombre() + " | Disponibles: " + producto.getCantidad());
        int unidades = Entrada.leerEnteroEnRango("Unidades que llegaron: ", 1, 10000);
        producto.agregarUnidades(unidades);
        System.out.println("Inventario actualizado. Ahora hay " + producto.getCantidad() + " unidades.");
    }

    // muestra los productos que están agotados
    private void mostrarAgotados() {
        System.out.println("\n--- Productos agotados ---");
        int agotados = 0;
        for (int i = 0; i < totalProductos; i++) {
            if(productos[i].estaAgotado()) {
                System.out.println("- " + productos[i].getCodigo() + " | " + productos[i].getNombre()
                        + " | " + productos[i].getCategoria());
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
    private void mostrarTotalVendido() {
        System.out.println("\n--- Total vendido del día ---");
        System.out.println("Número de ventas: " + numeroVentas);
        System.out.println("Total vendido:    " + formatearPesos(totalVendidoDia));
    }

    private void listarProductos() {
        System.out.println("\n--- Listado de productos ---");
        if (totalProductos == 0) {
            System.out.println("No hay productos registrados.");
            return;
        }
        System.out.printf("%-8s %-20s %-11s %12s %10s%n", "CÓDIGO", "NOMBRE", "CATEGORÍA", "PRECIO", "CANTIDAD");
        for (int i = 0; i < totalProductos; i++) {
            Producto p = productos[i];
            System.out.printf("%-8s %-20s %-11s %12s %10d%n",
                    p.getCodigo(), p.getNombre(), p.getCategoria(), formatearPesos(p.getPrecio()), p.getCantidad());
        }
    }

    // convierte el valor a texto con formato de plata
    private String formatearPesos(double valor) {
        return String.format("$%,.0f", valor);
    }
}
