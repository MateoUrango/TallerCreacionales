# Taller integrador: DomiExpress

Presentado por:
- Mateo Enrique Urango Morales - 20241020117
- Cristian Esteban Castañeda Vargas - 20241020094
- Santiago Nicolás Triana Mariño - 20241020115

## ¿Qué se hizo?

Hicimos un sistema en Java para crear pedidos de comida y para pagar esos pedidos. La idea era usar varios patrones que el profesor nos explicó, como Singleton, Builder y Factory Method. También agregamos Nequi como otro medio de pago.

## ¿Cómo se resolvió?

- `GeneradorConsecutivo` es el Singleton, porque lleva la cuenta de los pedidos y solo hay uno. Se usa con `obtenerInstancia()` y el constructor está privado para que no se creen varios. El número del pedido se asigna cuando se arma el pedido, así que si el pedido está mal no se gasta un número. O sea, eso sí tiene sentido, aunque al principio no entendíamos muy bien.
- `Pedido.Builder` sirve para armar el pedido poco a poco: cliente, tipo de entrega, dirección, comida, notas, cupones y propina. Hay cosas que no siempre se ponen y por eso tienen un valor por defecto. Antes de crear el pedido, se revisa que todo esté bien y que el cupón esté entre 0 y 100. Eso ayuda porque si algo está mal, te lo dice antes de continuar.
- La clase `Pedido` guarda la información del pedido, calcula el subtotal con todo lo que se pidió y luego le resta el descuento y suma la propina. También tiene un resumen para ver qué se está comprando.
- `ProcesadorPago` es la parte general del pago y tiene el método `crearPasarela()`. Después vienen `ProcesadorTarjeta`, `ProcesadorPSE`, `ProcesadorEfectivo` y `ProcesadorNequi`, que crean la forma de pago según el caso.
- Las clases `PasarelaTarjeta`, `PasarelaPSE`, `PasarelaEfectivo` y `PasarelaNequi` son las que dicen si el pago sí pasa o no, cada una con su regla.

## ¿Por qué se hizo así?

Elegimos Singleton porque el número de los pedidos no puede ir en varios lados, porque si no se repite y se vuelve un lío. Builder se usó porque un pedido tiene muchas cosas y algunas no siempre se ponen, entonces es más fácil ir armándolo paso a paso y no hacer un constructor muy largo y complicado. Factory Method se escogió porque todos los pagos tienen casi la misma forma, pero cada uno decide distinto si aprueba la compra. Además, cuando agregamos Nequi, no tuvimos que cambiar mucho lo que ya estaba hecho; solo se agregaron sus clases y el sistema siguió funcionando. En resumen, era una forma de ampliar el proyecto sin romper todo.

## Descripción corta del código

La clase `App` es la que muestra cómo funciona todo. Primero comprueba que el generador sea el mismo objeto, luego crea pedidos con `Builder`, muestra su resumen y rechaza los pedidos que están mal. Después procesa pagos con tarjeta, PSE y efectivo. Nequi también quedó hecho, pero para verlo en `App` había que descomentar un bloque del punto 4 que estaba ahí por si querían probarlo.

El total del pedido se calcula con la fórmula `subtotal * (100 - cupon) / 100 + propina`. La tarjeta aprueba montos hasta $500.000, PSE y efectivo aprueban siempre, y Nequi solo aprueba compras hasta $300.000. O sea, cada medio hace su propia validación y eso era lo que queríamos mostrar.

## Reto extra: Prototype (`AppBonus`)

También hicimos una parte extra llamada `AppBonus`, que era como una prueba del patrón Prototype. Lo que se hace ahí es crear un pedido original, hacer una copia y luego agregarle un producto extra al pedido copiado para ver que el original no se cambia. En teoría, el método `clonar()` sirve para repetir la información sin tener que escribir todo de nuevo. En la clase `AppBonus` se hace algo como esto:

- se crea un pedido con `Pedido.Builder()`
- se agrega una bandeja paisa, un cupón y una propina
- se clona el pedido con `original.clonar()`
- se añade un postre al pedido clonado
- se muestran los dos resúmenes para comparar

La idea era ver que el pedido original no se modificaba y que el nuevo sí tenía el producto extra. A nosotros al principio se nos hizo raro porque no entendíamos bien la diferencia entre copiar un objeto y crear uno nuevo, pero después se entendió que el prototype sirve para reutilizar la información que ya existe. En general, era una forma de probar que no siempre hay que construir todo desde cero.

En la práctica, el resultado del `AppBonus` era algo así: el pedido original se veía normal y el pedido repetido se veía igual, pero con algo extra. Eso demuestra que se hizo una copia y no que se estaba modificando el original.