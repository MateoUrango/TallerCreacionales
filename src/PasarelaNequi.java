// PasarelaNequi.java
/** NUEVO producto (R6): Nequi aprueba si el monto es menor o igual a 300000. */
public class PasarelaNequi implements PasarelaPago {

    private static final double LIMITE = 300000;

    @Override
    public String nombre() {
        return "Nequi";
    }

    @Override
    public boolean cobrar(double monto) {
        return monto <= LIMITE;
    }
}