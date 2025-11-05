package com.celicienta.celicienta.Entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table (name = "producto_pedido")
public class ProductoPedido {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn (name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn (name = "producto_id")
    private Producto producto;

    private int cantidadPersonas;
    private double precioUnitario;
    private double subtotal;

    public Vendedor getVendedor() {
        return producto != null ? producto.getVendedor() : null;
    }

    public void calcularSubtotal() {
        this.subtotal = this.precioUnitario * (cantidadPersonas / (double) producto.getPorcionBasePersonas());
    }

}
