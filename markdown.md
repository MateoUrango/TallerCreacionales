# Taller integrador: DomiExpress

Presentado por:
- Mateo Enrique Urango Morales - 20241020117
- Cristian Esteban Castañeda Vargas - 20241020094
- Santiago Nicolás Triana Mariño - 20241020115

## ¿Qué se realizó?

En este proyecto implementamos en Java un sistema para crear pedidos de comida y gestionar sus pagos correspondientes. La solución combina tres patrones creacionales: **Singleton**, **Builder** y **Factory Method**. Además, se agregó **Nequi** como nuevo medio de pago. El diagrama de clases del proyecto está en [`diagrama-clases.mmd`](diagrama-clases.mmd).

## ¿Cómo se dio solución al problema?

- `GeneradorConsecutivo` usa el patrón **Singleton** porque mantiene un único contador para los pedidos y solo permite acceder a una instancia mediante `obtenerInstancia()`. Además, su constructor es privado, lo que evita que se creen varios generadores. El ID del pedido se asigna desde `Pedido.Builder.construir()` después de validar la información, así los pedidos inválidos no consumen un número consecutivo.
- `Pedido.Builder` permite crear un pedido paso a paso, definiendo el cliente, el tipo de entrega, la dirección, los productos, las notas, el cupón y la propina. Los campos opcionales tienen valores por defecto y al momento de construir el pedido se validan los datos obligatorios y el rango del cupón (entre 0 y 100).
- La clase `Pedido` guarda la información del pedido y calcula el subtotal a partir de los ítems, aplicando luego el descuento y la propina. También incluye un método para mostrar el resumen del pedido.
- `ProcesadorPago` define el flujo general del pago y declara el método `crearPasarela()`. Las clases `ProcesadorTarjeta`, `ProcesadorPSE`, `ProcesadorEfectivo` y `ProcesadorNequi` implementan ese método para crear la pasarela apropiada según el medio de pago.
- Las clases `PasarelaTarjeta`, `PasarelaPSE`, `PasarelaEfectivo` y `PasarelaNequi` contienen la regla de aprobación específica de cada método de pago.

## ¿Por qué se hizo de esta manera?

Elegimos **Singleton** porque la numeración de los pedidos debe ser única y centralizada; de esta forma, no se crean varios contadores que puedan repetir IDs. El patrón **Builder** fue útil porque un pedido tiene varios atributos y algunos son opcionales, por lo que construirlo de forma incremental hace más clara la asignación de los valores y permite validar antes de crear el objeto. El patrón **Factory Method** fue la mejor opción porque todos los pagos siguen el mismo flujo, pero la pasarela y su regla cambian según el medio de pago. Al agregar Nequi, se incorporaron sus clases sin afectar el resto del sistema, lo que facilita la extensión del proyecto.

## Descripción breve del código

La clase `App` demuestra el funcionamiento principal del sistema: obtiene dos veces el generador y verifica que sea el mismo objeto, crea pedidos usando `Builder`, muestra sus resúmenes y rechaza pedidos inválidos, y luego procesa pagos con tarjeta, PSE y efectivo. Las clases de Nequi también están implementadas, aunque para ver su funcionamiento desde `App` se debe habilitar el bloque del punto 4 que está comentado.

El total del pedido se calcula con la fórmula `subtotal * (100 - cupon) / 100 + propina`. La tarjeta aprueba montos de hasta $500.000, PSE y efectivo aprueban siempre, mientras que Nequi solo aprueba compras de hasta $300.000.
