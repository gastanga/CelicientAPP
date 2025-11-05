package com.celicienta.celicienta.Entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table (name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime fechaPedido = LocalDateTime.now();
    private LocalDateTime fechaEntrega;

    @Enumerated (EnumType.STRING)
    private estadoPedido estado = estadoPedido.PENDIENTE;

    private enum estadoPedido {
        PENDIENTE,
        EN_PROCESO,
        ENVIADO,
        ENTREGADO,
        CANCELADO
    }

    @ManyToOne
    @JoinColumn(name = "comprador_id")
    private Usuario comprador;

    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    private Vendedor vendedor;

    @OneToMany (mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductoPedido> detalles;

    private int cantidadPersonas;
    private String metodoPago;
    private String observaciones;
    private String direccionEntrega;
    private double total;
}
