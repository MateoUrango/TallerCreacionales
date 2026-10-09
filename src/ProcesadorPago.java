public abstract class ProcesadorPago {

    /** Factory Method: las subclases devuelven la pasarela que les corresponde. */
    protected abstract PasarelaPago crearPasarela();

    /** Flujo comun: crear la pasarela, mostrar que se cobra, cobrar e informar. */
    public void procesar(Pedido pedido) {
        PasarelaPago pasarela = crearPasarela();                 // 1. crear la pasarela
        double total = pedido.calcularTotal();

        System.out.println("Procesando " + pedido.getId() + " con "
                + pasarela.nombre() + " por $" + (long) total);   // 2. mostrar el cobro

        boolean aprobado = pasarela.cobrar(total);               // 3. cobrar

        System.out.println("  -> Pago " + (aprobado ? "APROBADO" : "RECHAZADO")); // 4. resultado
    }
}