// ProcesadorNequi.java
/** NUEVO creador (R6): agregado sin modificar ninguna clase existente (principio abierto/cerrado). */
public class ProcesadorNequi extends ProcesadorPago {

    @Override
    protected PasarelaPago crearPasarela() {
        return new PasarelaNequi();
    }
}