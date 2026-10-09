// Pedido.java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Producto final del patron Builder.
 * Es inmutable: todos sus campos son final y su constructor es PRIVADO,
 * asi que la unica forma de obtener un Pedido es mediante Pedido.Builder.
 */
public class Pedido {

    private final String id;
    private final String cliente;
    private final TipoEntrega tipoEntrega;
    private final String direccion;
    private final List<ItemPedido> items;
    private final String notas;
    private final int cupon;
    private final double propina;

    // Constructor PRIVADO: solo el Builder (clase interna) puede invocarlo.
    private Pedido(String id, Builder b) {
        this.id = id;
        this.cliente = b.cliente;
        this.tipoEntrega = b.tipoEntrega;
        this.direccion = b.direccion;
        // Copia defensiva: el pedido no comparte la lista con el Builder,
        // asi reutilizar el Builder despues no altera pedidos ya construidos.
        this.items = Collections.unmodifiableList(new ArrayList<>(b.items));
        this.notas = b.notas;
        this.cupon = b.cupon;
        this.propina = b.propina;
    }

    public String getId() {
        return id;
    }

    /** R4: total = subtotal * (100 - cupon) / 100 + propina */
    public double calcularTotal() {
        double subtotal = 0;
        for (ItemPedido item : items) {
            subtotal += item.getSubtotal();
        }
        return subtotal * (100 - cupon) / 100 + propina;
    }

    public void mostrarResumen() {
        // Cabecera: la direccion solo se muestra si la entrega es a domicilio.
        String entrega = tipoEntrega.name();
        if (tipoEntrega == TipoEntrega.DOMICILIO) {
            entrega += " (" + direccion + ")";
        }
        System.out.println("Pedido " + id + " | Cliente: " + cliente + " | Entrega: " + entrega);

        // Un renglon por item: cantidad x nombre ($precio unitario)
        for (ItemPedido item : items) {
            System.out.println("  - " + item.getCantidad() + " x " + item.getNombre()
                    + " ($" + (long) item.getPrecioUnitario() + ")");
        }

        // Las notas son opcionales: solo se imprimen si hay algo.
        if (!notas.isEmpty()) {
            System.out.println("  Notas: " + notas);
        }

        System.out.println("  Cupon: " + cupon + "% | Propina: $" + (long) propina
                + " | Total: $" + (long) calcularTotal());
    }

    // ------------------------------------------------------------------
    // BUILDER: arma el pedido paso a paso (API fluida) y valida al final.
    // ------------------------------------------------------------------
    public static class Builder {

        // Obligatorios (sin valor por defecto) y opcionales (con valor por defecto, R2)
        private String cliente;
        private TipoEntrega tipoEntrega = TipoEntrega.RECOGER;
        private String direccion = "";
        private final List<ItemPedido> items = new ArrayList<>();
        private String notas = "";
        private int cupon = 0;
        private double propina = 0;

        // Cada metodo devuelve "this" para poder encadenar llamadas.
        public Builder conCliente(String cliente) {
            this.cliente = cliente;
            return this;
        }

        public Builder conTipoEntrega(TipoEntrega tipoEntrega) {
            this.tipoEntrega = tipoEntrega;
            return this;
        }

        public Builder conDireccion(String direccion) {
            this.direccion = direccion;
            return this;
        }

        public Builder agregarItem(ItemPedido item) {
            this.items.add(item);
            return this;
        }

        public Builder conNotas(String notas) {
            this.notas = notas;
            return this;
        }

        public Builder conCupon(int cupon) {
            // R3: un cupon fuera de rango falla de inmediato.
            if (cupon < 0 || cupon > 100) {
                throw new IllegalArgumentException("El cupon debe estar entre 0 y 100");
            }
            this.cupon = cupon;
            return this;
        }

        public Builder conPropina(double propina) {
            this.propina = propina;
            return this;
        }

        /**
         * Valida y construye el Pedido.
         * El orden de las validaciones es el que exige R3.
         * El numero de pedido se pide DESPUES de validar, para que un pedido
         * rechazado no consuma consecutivo (R1).
         */
        public Pedido construir() {
            if (cliente == null || cliente.trim().isEmpty()) {
                throw new IllegalStateException("El cliente es obligatorio");
            }
            if (items.isEmpty()) {
                throw new IllegalStateException("El pedido debe tener al menos un item");
            }
            if (tipoEntrega == TipoEntrega.DOMICILIO
                    && (direccion == null || direccion.trim().isEmpty())) {
                throw new IllegalStateException("El domicilio requiere direccion");
            }

            // Solo llegamos aqui si todo es valido: ahora si se gasta un numero.
            String id = GeneradorConsecutivo.obtenerInstancia().siguiente();
            return new Pedido(id, this);
        }
    }
}