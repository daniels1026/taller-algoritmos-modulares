package conpoo;

/*
 * Clase Producto (caso 2 con POO)
 *
 * Integrantes:
 *   - Daniel Eduardo Idárraga Gutiérrez
 *   - Laura Valentina Ochoa Mona
 *
 * Cada objeto Producto es un producto de la cafetería con sus datos.
 * Los atributos son private (encapsulamiento), entonces desde afuera
 * solo se pueden leer con los get y se cambian con los metodos vender()
 * y agregarUnidades().
 */
public class Producto {

    // atributos
    private String codigo;
    private String nombre;
    private double precio;
    private int cantidad;
    private String categoria;

    // constructor: se usa con new Producto(...) y llena los atributos
    public Producto(String codigo, String nombre, double precio, int cantidad, String categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.categoria = categoria;
    }

    // getters
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    // dice si alcanzan las unidades para vender
    public boolean hayStockSuficiente(int cantidadPedida) {
        return cantidad >= cantidadPedida;
    }

    public boolean estaAgotado() {
        return cantidad==0;
    }

    // vende unidades: las descuenta del inventario y devuelve cuánto vale la venta.
    // antes de llamarlo hay que revisar hayStockSuficiente()
    public double vender(int unidades) {
        cantidad = cantidad - unidades;
        return precio * unidades;
    }

    // le suma unidades al inventario (cuando llega mercancía)
    public void agregarUnidades(int unidades) {
        cantidad = cantidad + unidades;
    }
}
