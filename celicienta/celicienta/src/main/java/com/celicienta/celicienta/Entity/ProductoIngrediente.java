package com.celicienta.celicienta.Entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "producto_ingrediente")
public class ProductoIngrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "ingrediente_id")
    private Ingrediente ingrediente;

    private double cantidadParaPorcionBase;
}
