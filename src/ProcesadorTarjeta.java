// ProcesadorTarjeta.java
/** Creador concreto: solo decide que pasarela se crea. El flujo lo hereda. */
public class ProcesadorTarjeta extends ProcesadorPago {

    @Override
    protected PasarelaPago crearPasarela() {
        return new PasarelaTarjeta();
    }
}