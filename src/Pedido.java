import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {

    private final String id;
    private final String cliente;
    private final TipoEntrega tipoEntrega;
    private final String direccion;
    private final List<ItemPedido> items;
    private final String notas;
    private final int cupon;
    private final double propina;

    private Pedido(String id, Builder b) {
        this.id = id;
        this.cliente = b.cliente;
        this.tipoEntrega = b.tipoEntrega;
        this.direccion = b.direccion;

        this.items = Collections.unmodifiableList(new ArrayList<>(b.items));
        this.notas = b.notas;
        this.cupon = b.cupon;
        this.propina = b.propina;
    }

    public String getId() {
        return id;
    }

    public double calcularTotal() {
        double subtotal = 0;
        for (ItemPedido item : items) {
            subtotal += item.getSubtotal();
        }
        return subtotal * (100 - cupon) / 100 + propina;
    }

    public void mostrarResumen() {
        String entrega = tipoEntrega.name();
        if (tipoEntrega == TipoEntrega.DOMICILIO) {
            entrega += " (" + direccion + ")";
        }
        System.out.println("Pedido " + id + " | Cliente: " + cliente + " | Entrega: " + entrega);

        for (ItemPedido item : items) {
            System.out.println("  - " + item.getCantidad() + " x " + item.getNombre()
                    + " ($" + (long) item.getPrecioUnitario() + ")");
        }

        if (!notas.isEmpty()) {
            System.out.println("  Notas: " + notas);
        }

        System.out.println("  Cupon: " + cupon + "% | Propina: $" + (long) propina
                + " | Total: $" + (long) calcularTotal());
    }

    public static class Builder {

        private String cliente;
        private TipoEntrega tipoEntrega = TipoEntrega.RECOGER;
        private String direccion = "";
        private final List<ItemPedido> items = new ArrayList<>();
        private String notas = "";
        private int cupon = 0;
        private double propina = 0;

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

            String id = GeneradorConsecutivo.obtenerInstancia().siguiente();
            return new Pedido(id, this);
        }
    }
}