/*package com.celicienta.celicienta.Entity;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table (name = "compradores")
public class Comprador extends Usuario {
    private String direccionEntrega;
    @OneToMany (mappedBy = "comprador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos;

}
*/