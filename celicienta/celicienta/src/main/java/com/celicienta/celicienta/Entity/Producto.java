package com.celicienta.celicienta.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table (name = "productos")
public class Producto {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;

    @Column (length = 1000)
    private String descripcion;

    private int porcionBasePersonas = 4;
    private double precio, costoProduccion;
    private int plazoEntregaDias;
    private boolean activo;

    @ManyToOne
    @JoinColumn (name = "vendedor_id")
    private Usuario vendedor;

    @OneToMany (mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductoIngrediente> receta = new ArrayList<>();

}
