package com.celicienta.celicienta.Entities;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table (name = "vendedores")
public class Vendedor extends Usuario {
    private String nombreTienda;
    private String direccionTienda;
    private String telefonoContacto;
    private int radioEntrega;
    @OneToMany (mappedBy = "vendedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Producto> productos;
}
