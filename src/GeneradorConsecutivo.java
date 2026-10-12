public class GeneradorConsecutivo {

    private static GeneradorConsecutivo instancia;

    private int contador = 0;

    private GeneradorConsecutivo() {
        System.out.println("[Consecutivo] Instancia creada.");
    }

    public static GeneradorConsecutivo obtenerInstancia() {
        if (instancia == null) {
            instancia = new GeneradorConsecutivo();
        }
        return instancia;
    }

    public String siguiente() {
        contador++;
        return String.format("PED-%04d", contador);
    }
}