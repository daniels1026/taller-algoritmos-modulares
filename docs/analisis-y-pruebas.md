# Análisis del problema y pruebas de escritorio

Integrantes: Daniel Eduardo Idárraga Gutiérrez y Laura Valentina Ochoa Mona

Las pruebas de escritorio las hicimos primero a mano y despues corrimos el
programa con los mismos datos para ver que diera lo mismo.

Las partes 1 a 4 estan explicadas con la versión sin POO. La versión con POO
hace lo mismo y da los mismos resultados, lo que cambia es como se guardan los
datos (eso está en la parte 5).

---

## 1. Menú principal

### Análisis

- Entrada: la opción que escoge el usuario (de 0 a 3).
- Proceso: dependiendo de la opción se llama al programa de cada caso. Cuando
  ese programa termina vuelve a salir el menú (con un `do-while`) hasta que la
  opción sea 0.
- Salida: el menú y el programa que se escogió.
- El `main` no tiene nada mas que el menú, todo lo demás está en los otros
  archivos.

### Archivo `Entrada`

Lo usan los 3 casos para no tener que repetir el mismo código de validación.

| Módulo | Tipo | Qué hace |
|---|---|---|
| `leerTexto` | Función | Lee un texto y no deja que quede vacio |
| `leerEntero` | Función | Lee un número entero, si escriben letras lo pide otra vez |
| `leerEnteroEnRango` | Función | Lee un entero que esté entre un mínimo y un máximo |
| `leerDecimal` | Función | Lee un número con decimales, sirve con punto o coma (3.5 o 3,5) |
| `leerDecimalEnRango` | Función | Lee un decimal entre un mínimo y un máximo |

### Prueba de escritorio del menú

| Lo que se escribe | Que pasa |
|---|---|
| `abc` | Sale "Error: debe escribir un número entero." y lo vuelve a pedir |
| `5` | No está entre 0 y 3, sale "Error: el valor debe estar entre 0 y 3." |
| `1` | Entra a la cafetería |
| `0` dentro de un caso | Vuelve al menú principal |
| `0` en el menú principal | Sale "Gracias por usar el sistema" y se cierra |

---

## 2. Caso 2 - Inventario de la cafetería

### Análisis del problema

La cafetería tiene que controlar hasta 20 productos, no puede vender más de lo
que tiene y quiere saber cuánto vendió en el día.

Entradas:

- De cada producto: código, nombre, precio unitario, cantidad disponible y
  categoría (Bebidas, Panadería o Snacks).
- De cada venta: el código del producto y cuántas unidades se venden.
- Para actualizar el inventario: el código y cuántas unidades llegaron.

Procesos:

- Buscar el producto por el código recorriendo el vector.
- Revisar que haya stock antes de vender.
- Subtotal de la venta = precio x cantidad.
- Restarle al inventario lo que se vendió y sumarlo al total del día.
- Cuando llega mercancía se le suman las unidades al producto.
- Recorrer los productos y mostrar los que están en 0.

Salidas:

- Mensaje de cada venta con lo que costó.
- Lista de productos agotados.
- Cuántas ventas hubo y el total vendido del día.
- Lista de todos los productos.

Validaciones:

- Máximo 20 productos.
- No se puede repetir el código.
- El precio va de $1 a $1.000.000 y la cantidad de 0 a 10.000.
- No se puede vender un producto agotado ni mas unidades de las que hay.

Estructuras de datos:

Usamos vectores paralelos de tamaño 20, osea que la posición `i` es el mismo
producto en todos los vectores.

| Vector | Tipo | Ejemplo |
|---|---|---|
| `codigos` | `String[]` | "B01" |
| `nombres` | `String[]` | "Cafe tinto" |
| `precios` | `double[]` | 1500 |
| `cantidades` | `int[]` | 50 |
| `categorias` | `String[]` | "Bebidas" |

También están las variables `totalProductos` (contador), `totalVendidoDia`
(acumulador) y `numeroVentas` (contador).

Módulos:

| Módulo | Tipo | Qué hace |
|---|---|---|
| `ejecutar` | Procedimiento | Muestra el submenú hasta que el usuario quiera volver |
| `mostrarMenu` | Procedimiento | Imprime las opciones |
| `registrarProducto` | Procedimiento | Pide los datos del producto y los guarda |
| `elegirCategoria` | Función | Devuelve la categoría que se escogió |
| `buscarProducto` | Función | Devuelve la posición del producto o -1 si no existe |
| `hayStockSuficiente` | Función | Devuelve `true` si alcanzan las unidades |
| `calcularSubtotal` | Función | Devuelve precio x cantidad |
| `realizarVenta` | Procedimiento | Revisa todo y hace la venta |
| `actualizarInventario` | Procedimiento | Le suma unidades a un producto |
| `mostrarAgotados` | Procedimiento | Muestra los productos que están en 0 |
| `mostrarTotalVendido` | Procedimiento | Muestra el total del día |
| `listarProductos` | Procedimiento | Muestra la tabla de productos |
| `formatearPesos` | Función | Pasa el número a texto con formato de plata |

### Pruebas de escritorio

Productos que registramos:

| Pos | Código | Nombre | Precio | Cantidad | Categoría |
|---|---|---|---|---|---|
| 0 | B01 | Cafe tinto | 1500 | 50 | Bebidas |
| 1 | P01 | Pandebono | 2500 | 10 | Panadería |
| 2 | S01 | Papas de limón | 3000 | 4 | Snacks |

Validaciones al registrar:

| Lo que se escribe | Que pasa |
|---|---|
| Código `B01` cuando ya existe | "Error: ya existe un producto con el código B01." y pide otro |
| Cantidad `-3` | "Error: el valor debe estar entre 0 y 10000." y la pide otra vez |

Prueba de la función `buscarProducto("S01")`:

| i | codigos[i] | ¿Es igual a "S01"? | Que hace |
|---|---|---|---|
| 0 | B01 | No | sigue |
| 1 | P01 | No | sigue |
| 2 | S01 | Si | retorna 2 |

Si se busca un código que no existe como `X99`, el ciclo pasa por las 3
posiciones, no lo encuentra y retorna -1.

Ventas que hicimos en orden:

| # | Operación | Validación | cantidades [B01, P01, S01] | numeroVentas | totalVendidoDia | Lo que muestra |
|---|---|---|---|---|---|---|
| 0 | Así empieza | - | [50, 10, 4] | 0 | 0 | - |
| 1 | Vender 4 de P01 | 10 >= 4 si | [50, 6, 4] | 1 | 10.000 | Venta realizada: 4 x Pandebono = $10.000 |
| 2 | Vender 4 de S01 | 4 >= 4 si | [50, 6, 0] | 2 | 22.000 | Venta realizada... "el producto quedó agotado" |
| 3 | Vender S01 | cantidad es 0, no | [50, 6, 0] | 2 | 22.000 | "El producto Papas de limón está AGOTADO. No se puede vender." |
| 4 | Vender X99 | buscarProducto da -1, no | [50, 6, 0] | 2 | 22.000 | "No existe un producto con el código X99." |
| 5 | Vender 60 de B01 | 50 >= 60 no | [50, 6, 0] | 2 | 22.000 | "Stock insuficiente: solo hay 50 unidades. Venta cancelada." |
| 6 | Vender 20 de B01 | 50 >= 20 si | [30, 6, 0] | 3 | 52.000 | Venta realizada: 20 x Cafe tinto = $30.000 |
| 7 | Ver agotados | - | [30, 6, 0] | 3 | 52.000 | "- S01 \| Papas de limón \| Snacks" |
| 8 | Llegan 6 de S01 | - | [30, 6, 6] | 3 | 52.000 | "Ahora hay 6 unidades." |
| 9 | Ver agotados | - | [30, 6, 6] | 3 | 52.000 | "No hay productos agotados." |
| 10 | Total del día | - | [30, 6, 6] | 3 | 52.000 | Número de ventas: 3, Total vendido: $52.000 |

Total: 10.000 + 12.000 + 30.000 = $52.000, da bien.

---

## 3. Caso 3 - Control de notas

### Análisis del problema

La coordinacion quiere registrar hasta 25 estudiantes de primer semestre,
sacar la nota definitiva de cada uno, saber si pasan o no y ver datos del
grupo.

Entradas:

- De cada estudiante: código, nombre, las 3 notas parciales y el porcentaje
  de asistencia.

Procesos:

- Definitiva = (nota1 + nota2 + nota3) / 3, redondeada a 2 decimales.
- El estado se saca así:
  - si la asistencia es menor al 80% -> Reprueba (inasistencia)
  - si la definitiva es 3.0 o más -> Aprobado
  - si está entre 2.0 y 2.99 -> Habilita
  - si es menor a 2.0 -> Reprueba
- Promedio del grupo = suma de las definitivas / número de estudiantes.
- Para el mejor y el peor se recorre buscando el mayor y el menor.

Salidas:

- Lista con las notas, la asistencia, la definitiva y el estado.
- Buscar un estudiante por código.
- Promedio general del grupo.
- El estudiante con mejor promedio y el de peor promedio.

Nota:

El taller no dice cuánto vale cada parcial ni para qué es la asistencia.
Decidimos que los 3 parciales valen lo mismo y que si el estudiante tiene
menos del 80% de asistencia reprueba, porque es la regla que mas se usa.

Validaciones:

- Máximo 25 estudiantes.
- No se puede repetir el código.
- Cada nota tiene que estar entre 0.0 y 5.0.
- La asistencia entre 0 y 100.

Estructuras de datos:

| Estructura | Tipo | Para qué |
|---|---|---|
| `codigos` | `String[25]` | Código de cada estudiante |
| `nombres` | `String[25]` | Nombre de cada estudiante |
| `notas` | `double[25][3]` | Matriz: cada fila es un estudiante y cada columna un parcial |
| `asistencias` | `double[25]` | Porcentaje de asistencia |

Constantes: `NOTA_APROBAR = 3.0`, `NOTA_HABILITAR = 2.0` y
`ASISTENCIA_MINIMA = 80`.

Módulos:

| Módulo | Tipo | Qué hace |
|---|---|---|
| `ejecutar` | Procedimiento | Submenú del caso |
| `mostrarMenu` | Procedimiento | Imprime las opciones |
| `registrarEstudiante` | Procedimiento | Pide los datos del estudiante y los valida |
| `buscarEstudiante` | Función | Devuelve la posición o -1 |
| `calcularDefinitiva` | Función | Devuelve el promedio de las 3 notas |
| `determinarEstado` | Función | Devuelve si es Aprobado, Habilita o Reprueba |
| `mostrarListado` | Procedimiento | Muestra todos los estudiantes |
| `mostrarFilaEstudiante` | Procedimiento | Imprime una fila de la tabla |
| `consultarEstudiante` | Procedimiento | Busca un estudiante y lo muestra |
| `calcularPromedioGrupo` | Función | Devuelve el promedio del grupo |
| `mostrarPromedioGrupo` | Procedimiento | Muestra el promedio del grupo |
| `buscarMejorPromedio` | Función | Devuelve la posición del mejor |
| `buscarPeorPromedio` | Función | Devuelve la posición del peor |
| `mostrarMejorYPeor` | Procedimiento | Muestra el mejor y el peor |

### Pruebas de escritorio

Datos que usamos (matriz `notas` y vector `asistencias`):

| Pos | Código | Nombre | N1 | N2 | N3 | Asistencia |
|---|---|---|---|---|---|---|
| 0 | E01 | Ana Gomez | 4.5 | 3.8 | 4.2 | 95 |
| 1 | E02 | Luis Perez | 2.5 | 2.0 | 3.1 | 90 |
| 2 | E03 | Sara Rios | 1.5 | 2.0 | 1.8 | 85 |
| 3 | E04 | Juan Mesa | 4.0 | 4.0 | 3.5 | 70 |

Validaciones:

| Lo que se escribe | Que pasa |
|---|---|
| Nota parcial 1 = `6` | "Error: el valor debe estar entre 0.0 y 5.0." y la pide otra vez |
| Nota con coma `3,8` | La toma como 3.8 |
| Ver el listado sin estudiantes | "No hay estudiantes registrados." |

Prueba de la función `calcularDefinitiva(0)` (Ana Gomez):

| j | notas[0][j] | suma |
|---|---|---|
| - | - | 0 |
| 0 | 4.5 | 4.5 |
| 1 | 3.8 | 8.3 |
| 2 | 4.2 | 12.5 |

promedio = 12.5 / 3 = 4.1666... redondeado da 4.17

Prueba de la función `determinarEstado`:

| Estudiante | Definitiva | Asistencia | ¿Asist. < 80? | ¿Def >= 3.0? | ¿Def >= 2.0? | Estado |
|---|---|---|---|---|---|---|
| E01 | 12.5 / 3 = 4.17 | 95 | No | Si | - | Aprobado |
| E02 | 7.6 / 3 = 2.53 | 90 | No | No | Si | Habilita |
| E03 | 5.3 / 3 = 1.77 | 85 | No | No | No | Reprueba |
| E04 | 11.5 / 3 = 3.83 | 70 | Si | - | - | Reprueba (inasistencia) |

Prueba de la función `calcularPromedioGrupo`:

| i | Definitiva | suma |
|---|---|---|
| - | - | 0 |
| 0 | 4.17 | 4.17 |
| 1 | 2.53 | 6.70 |
| 2 | 1.77 | 8.47 |
| 3 | 3.83 | 12.30 |

Promedio = 12.30 / 4 = 3.075, el programa lo muestra como 3.07

Prueba de `buscarMejorPromedio` y `buscarPeorPromedio`:

| i | Definitiva[i] | mejor (antes -> despues) | peor (antes -> después) |
|---|---|---|---|
| inicio | - | 0 (4.17) | 0 (4.17) |
| 1 | 2.53 | 2.53 > 4.17 no, sigue 0 | 2.53 < 4.17 si, pasa a 1 |
| 2 | 1.77 | 1.77 > 4.17 no, sigue 0 | 1.77 < 2.53 si, pasa a 2 |
| 3 | 3.83 | 3.83 > 4.17 no, sigue 0 | 3.83 < 1.77 no, sigue 2 |

Resultado: el mejor es Ana Gomez con 4.17 y el peor es Sara Rios con 1.77.

Buscar por código:

| Lo que se escribe | Que pasa |
|---|---|
| `e02` | Muestra a Luis Perez (no importa si es mayúscula o minúscula) |
| `Z10` | "No se encontró un estudiante con el código Z10." |

---

## 4. Caso 5 - Producción de la fábrica de empaques

### Análisis del problema

La fábrica quiere registrar lo que produce cada operario de lunes a viernes
(hasta 10 operarios) y sacar datos de la semana.

Entradas:

- De cada operario: código y nombre.
- Producción: las unidades que hizo el operario en cada uno de los 5 días.

Procesos:

- Producción semanal de un operario = suma de su fila en la matriz.
- Total de un día = suma de la columna de ese día.
- Mejor operario = el que tiene la producción semanal más alta.
- Promedio por operario por día = total / (operarios x 5).
- Promedio semanal por operario = total / operarios.
- Un día es de baja producción si su total es menor al 80% del promedio
  diario de la planta (promedio diario = total de la semana / 5).

Salidas:

- Tabla con la producción de cada día y el total semanal de cada operario.
- El mejor operario de la semana.
- Producción total y los promedios.
- El total de cada día, marcando los de baja producción.

Nota:

El taller no dice qué es "baja producción", entonces lo definimos como un día
que produce menos del 80% del promedio diario. Además para los cálculos solo
contamos los operarios que ya tienen la producción ingresada, porque si no los
ceros de los que faltan bajarían los promedios.

Validaciones:

- Máximo 10 operarios y 5 días.
- No se puede repetir el código.
- Ningún valor de producción puede ser negativo (va de 0 a 100.000).
- Si no hay producción ingresada no se calcula nada y sale un mensaje.

Estructuras de datos:

| Estructura | Tipo | Para qué |
|---|---|---|
| `codigos` | `String[10]` | Código del operario |
| `nombres` | `String[10]` | Nombre del operario |
| `produccion` | `int[10][5]` | Matriz: cada fila es un operario y cada columna un día |
| `tieneProduccion` | `boolean[10]` | Si ya se le ingresó la producción al operario |
| `NOMBRES_DIAS` | `String[5]` | De lunes a viernes |

Módulos:

| Módulo | Tipo | Qué hace |
|---|---|---|
| `ejecutar` | Procedimiento | Submenú del caso |
| `mostrarMenu` | Procedimiento | Imprime las opciones |
| `registrarOperario` | Procedimiento | Pide y guarda un operario |
| `buscarOperario` | Función | Devuelve la posición o -1 |
| `ingresarProduccion` | Procedimiento | Pide las unidades de los 5 días |
| `calcularProduccionSemanal` | Función | Suma la fila de un operario |
| `calcularTotalDia` | Función | Suma la columna de un día |
| `contarOperariosConProduccion` | Función | Cuenta los operarios que tienen datos |
| `hayDatos` | Función | Dice si se puede calcular o no |
| `mostrarProduccionSemanal` | Procedimiento | Muestra la matriz con los totales |
| `buscarMejorOperario` | Función | Devuelve la posición del mejor |
| `mostrarMejorOperario` | Procedimiento | Muestra el mejor operario |
| `calcularProduccionTotal` | Función | Suma toda la matriz |
| `mostrarPromedioGeneral` | Procedimiento | Muestra los promedios |
| `mostrarDiasBajaProduccion` | Procedimiento | Muestra los días y marca los bajos |

### Pruebas de escritorio

Validaciones:

| Lo que se hace | Que pasa |
|---|---|
| Ver la producción sin datos | "Todavía no hay producción ingresada." |
| Ingresar producción sin operarios | "Primero debe registrar operarios." |
| Poner `-5` en el martes de OP1 | "Error: el valor debe estar entre 0 y 100000." y lo pide otra vez |
| Buscar `op1` en minúscula | Encuentra a OP1 |

Matriz `produccion` con los datos que usamos:

| Pos | Operario | Lun | Mar | Mié | Jue | Vie |
|---|---|---|---|---|---|---|
| 0 | OP1 - Carlos Ruiz | 120 | 110 | 90 | 130 | 100 |
| 1 | OP2 - Marta Diaz | 100 | 105 | 60 | 120 | 110 |
| 2 | OP3 - Pedro Lopez | 80 | 95 | 40 | 100 | 90 |

Prueba de la función `calcularProduccionSemanal(0)` (suma de la fila 0):

| dia | produccion[0][dia] | suma |
|---|---|---|
| - | - | 0 |
| 0 | 120 | 120 |
| 1 | 110 | 230 |
| 2 | 90 | 320 |
| 3 | 130 | 450 |
| 4 | 100 | 550 |

Haciendo lo mismo, OP2 da 495 y OP3 da 405.

Prueba de `buscarMejorOperario`:

| i | Semanal[i] | Condición | mejor |
|---|---|---|---|
| inicio | - | - | -1 |
| 0 | 550 | mejor es -1 | 0 |
| 1 | 495 | 495 > 550 no | 0 |
| 2 | 405 | 405 > 550 no | 0 |

Resultado: el mejor es Carlos Ruiz (OP1) con 550 unidades.

Prueba de `calcularTotalDia` (suma de cada columna):

| Día | OP1 + OP2 + OP3 | Total |
|---|---|---|
| Lunes | 120 + 100 + 80 | 300 |
| Martes | 110 + 105 + 95 | 310 |
| Miércoles | 90 + 60 + 40 | 190 |
| Jueves | 130 + 120 + 100 | 350 |
| Viernes | 100 + 110 + 90 | 300 |
| Semana | | 1450 |

Prueba de `mostrarPromedioGeneral`:

| Variable | Cálculo | Valor |
|---|---|---|
| operarios | contarOperariosConProduccion() | 3 |
| total | 300 + 310 + 190 + 350 + 300 | 1450 |
| promedioPorOperarioDia | 1450 / (3 x 5) | 96.67 |
| promedioSemanalOperario | 1450 / 3 | 483.33 |

Prueba de `mostrarDiasBajaProduccion`:

promedioDiario = 1450 / 5 = 290, entonces el limite = 290 x 0.80 = 232

| dia | totalDia | ¿totalDia < 232? | diasBajos | Lo que muestra |
|---|---|---|---|---|
| Lunes | 300 | No | 0 | Lunes 300 |
| Martes | 310 | No | 0 | Martes 310 |
| Miércoles | 190 | Si | 1 | Miércoles 190 <-- BAJA PRODUCCIÓN |
| Jueves | 350 | No | 1 | Jueves 350 |
| Viernes | 300 | No | 1 | Viernes 300 |

Resultado: hubo 1 día con baja producción, el miércoles.

---

## 5. Versión con POO

La versión con POO está en `src/conpoo`. Resuelve los mismos 3 casos con las
mismas reglas, validaciones y menús, pero los datos se guardan en objetos.

### Clases

Producto (caso 2):

| Parte | Que tiene |
|---|---|
| Atributos (private) | `codigo`, `nombre`, `precio`, `cantidad`, `categoria` |
| Constructor | `Producto(codigo, nombre, precio, cantidad, categoria)` |
| Getters | `getCodigo()`, `getNombre()`, `getPrecio()`, `getCantidad()`, `getCategoria()` |
| Métodos | `hayStockSuficiente(cantidad)`, `estaAgotado()`, `vender(unidades)` que devuelve el valor de la venta, `agregarUnidades(unidades)` |

Estudiante (caso 3):

| Parte | Que tiene |
|---|---|
| Atributos (private) | `codigo`, `nombre`, `notas` (vector de 3), `asistencia` |
| Constructor | `Estudiante(codigo, nombre, notas, asistencia)` |
| Getters | `getCodigo()`, `getNombre()`, `getNota(posicion)`, `getAsistencia()` |
| Métodos | `calcularDefinitiva()`, `determinarEstado()` |

Operario (caso 5):

| Parte | Que tiene |
|---|---|
| Atributos (private) | `codigo`, `nombre`, `tieneProduccion` |
| Constructor | `Operario(codigo, nombre)`, empieza sin producción |
| Getters | `getCodigo()`, `getNombre()`, `tieneProduccion()` |
| Métodos | `marcarProduccionIngresada()` |

Cafeteria, ControlNotas y ProduccionFabrica:

Son las clases de cada sistema. Cada una tiene como atributo un vector de
objetos (`Producto[]`, `Estudiante[]` u `Operario[]`) y sus contadores. La
fábrica además tiene la matriz `int[10][5]` con la producción, porque el
taller pide que sea una matriz.

El `main` crea un objeto de cada sistema una sola vez
(`new Cafeteria()`, `new ControlNotas()` y `new ProduccionFabrica()`) y llama
a su metodo `ejecutar()`. Como los objetos no se vuelven a crear, los datos se
mantienen aunque uno salga y vuelva a entrar a un sistema.

### Comparación de las dos versiones

| Cosa | Sin POO | Con POO |
|---|---|---|
| Guardar un producto | 5 vectores: `codigos[i]`, `nombres[i]`, `precios[i]`... | 1 vector de objetos: `productos[i]` |
| Leer el nombre | `nombres[i]` | `productos[i].getNombre()` |
| Vender | `cantidades[pos] = cantidades[pos] - cantidad` | `producto.vender(cantidad)` |
| Buscar | devuelve la posición o -1 | devuelve el objeto o `null` |
| Definitiva | `calcularDefinitiva(pos)` recorre la fila de la matriz | `estudiante.calcularDefinitiva()` recorre su propio vector de notas |
| Métodos | todos `static` | de instancia (se llaman sobre un objeto) |

### Prueba de escritorio de la versión con POO

Corrimos la versión con POO con los mismos datos de las secciones 2, 3 y 4 y
dio exactamente lo mismo que la versión sin POO. Por ejemplo, la venta de 4
Pandebonos con objetos:

| Paso | Instrucción | Estado del objeto P01 | Resultado |
|---|---|---|---|
| 1 | `buscarProducto("P01")` | cantidad = 10 | devuelve el objeto de P01 |
| 2 | `producto.estaAgotado()` | cantidad = 10 | `false`, sigue |
| 3 | `producto.hayStockSuficiente(4)` | 10 >= 4 | `true`, sigue |
| 4 | `producto.vender(4)` | cantidad = 10 - 4 = 6 | devuelve 2500 x 4 = 10.000 |
| 5 | `totalVendidoDia = 0 + 10000` | cantidad = 6 | totalVendidoDia = 10.000 |

Y la definitiva de Ana Gomez con objetos:

| Paso | Instrucción | Resultado |
|---|---|---|
| 1 | `new Estudiante("E01", "Ana Gomez", {4.5, 3.8, 4.2}, 95)` | se crea el objeto |
| 2 | `ana.calcularDefinitiva()` | (4.5 + 3.8 + 4.2) / 3 = 4.17 |
| 3 | `ana.determinarEstado()` | asistencia 95 no es < 80, y 4.17 >= 3.0, entonces "Aprobado" |
