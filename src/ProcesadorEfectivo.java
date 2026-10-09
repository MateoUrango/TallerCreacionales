// ProcesadorEfectivo.java
/** Creador concreto para efectivo. */
public class ProcesadorEfectivo extends ProcesadorPago {

    @Override
    protected PasarelaPago crearPasarela() {
        return new PasarelaEfectivo();
    }
}