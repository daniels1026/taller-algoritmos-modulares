# Taller Algoritmos Modulares - Unidad 3

Pascual Bravo - Ingeniería de Software

Profesor: Alejandro López Oliveros

Integrantes:
- Daniel Eduardo Idárraga Gutiérrez
- Laura Valentina Ochoa Mona

## Casos

Escogimos 3 de los 5 casos:

1. Caso 2 - Inventario de la cafetería
2. Caso 3 - Control de notas
3. Caso 5 - Producción de la fábrica de empaques

El numero es la opción que tiene cada uno en el menú. Escogimos el 5 porque
usa matriz y asi alcanzabamos a usar todos los temas que pedía el taller.

El análisis del problema y las pruebas de escritorio están en
`docs/analisis-y-pruebas.md`.

## Versiones

Hicimos el taller de dos formas:

- `src/sinpoo`: la versión que pide el taller, sin POO. Todo con variables,
  vectores, matrices, funciones y procedimientos.
- `src/conpoo`: la misma solución pero con POO, para los puntos extra.

Las dos hacen lo mismo y tienen los mismos menús, entonces las pruebas de
escritorio sirven para las dos (las corrimos con los mismos datos y dieron
igual).

### Archivos de la versión sin POO (src/sinpoo)

- `Main.java`: solo el menú principal
- `Entrada.java`: funciones para leer y validar los datos
- `Cafeteria.java`: caso 2
- `ControlNotas.java`: caso 3
- `ProduccionFabrica.java`: caso 5

Aca no creamos objetos, todos los metodos son `static`. Los datos de cada caso
van en vectores paralelos, osea que la posición `i` de cada vector es el mismo
producto, estudiante u operario. Las notas y la producción van en matrices.

El `main` solo muestra el menú y llama al programa que uno escoja. Cuando ese
programa termina vuelve a salir el menú hasta que uno le de `0` para salir.

### Archivos de la versión con POO (src/conpoo)

- `Main.java`: crea los objetos de cada sistema y muestra el menú
- `Entrada.java`: el mismo de la otra versión
- `Producto.java`, `Estudiante.java`, `Operario.java`: las clases con los
  datos de cada cosa
- `Cafeteria.java`, `ControlNotas.java`, `ProduccionFabrica.java`: cada
  sistema, maneja un vector de objetos

Lo que usamos de POO: clases, objetos (`new Producto(...)`, etc), atributos
`private` con sus `get` (encapsulamiento), constructores y metodos propios de
cada objeto, por ejemplo `producto.vender(4)` o
`estudiante.calcularDefinitiva()`.

La diferencia es que en vez de tener un vector por cada dato, cada posición
del vector guarda un objeto con todos sus datos. Por ejemplo en vez de
`nombres[i]` se usa `productos[i].getNombre()`.

En la fábrica dejamos la producción en una matriz `int[10][5]` porque el
taller dice que tiene que ser matriz.

## Validaciones

Las dos versiones usan el archivo `Entrada`, que vuelve a pedir el dato si el
usuario escribe letras donde va un número, lo deja vacío o pone un valor que no
está en el rango. Así el programa no se cae.

## Como correrlo

Se necesita Java 8 o mas nuevo. Desde la terminal en la carpeta del proyecto:

```
javac -encoding UTF-8 -d out src/sinpoo/*.java
java -cp out sinpoo.Main
```

Para la versión con POO es igual pero cambiando `sinpoo` por `conpoo`.

Tambien se puede abrir la carpeta en NetBeans, IntelliJ o VS Code y correr el
`Main.java` de la versión que se quiera.
