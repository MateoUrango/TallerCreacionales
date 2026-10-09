// ProcesadorPSE.java
/** Creador concreto para PSE. */
public class ProcesadorPSE extends ProcesadorPago {

    @Override
    protected PasarelaPago crearPasarela() {
        return new PasarelaPSE();
    }
}