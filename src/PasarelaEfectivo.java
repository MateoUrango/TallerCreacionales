// PasarelaEfectivo.java
/** Producto concreto: efectivo aprueba siempre. */
public class PasarelaEfectivo implements PasarelaPago {

    @Override
    public String nombre() {
        return "Efectivo";
    }

    @Override
    public boolean cobrar(double monto) {
        return true;
    }
}