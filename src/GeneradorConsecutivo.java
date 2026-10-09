// GeneradorConsecutivo.java
/**
 * PATRON: Singleton.
 * Es la unica fuente de numeracion de pedidos en todo el programa.
 * Si existieran dos generadores, cada uno llevaria su propia cuenta
 * y podrian repetirse numeros (dos pedidos con PED-0001).
 */
public class GeneradorConsecutivo {

    // Unica instancia. Empieza en null y se crea la primera vez que alguien la pide.
    private static GeneradorConsecutivo instancia;

    // Ultimo numero entregado. Vive dentro de la instancia unica.
    private int contador = 0;

    // Constructor PRIVADO: nadie fuera de esta clase puede hacer "new GeneradorConsecutivo()".
    private GeneradorConsecutivo() {
        System.out.println("[Consecutivo] Instancia creada.");
    }

    /** Punto de acceso global: crea la instancia la primera vez y luego reutiliza la misma. */
    public static GeneradorConsecutivo obtenerInstancia() {
        if (instancia == null) {
            instancia = new GeneradorConsecutivo();
        }
        return instancia;
    }

    /** Devuelve el siguiente numero con el formato PED-0001, PED-0002, ... */
    public String siguiente() {
        contador++;
        return String.format("PED-%04d", contador);
    }
}