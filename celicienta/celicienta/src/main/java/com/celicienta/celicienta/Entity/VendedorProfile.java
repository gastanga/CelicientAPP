package com.celicienta.celicienta.Entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "vendedores_profile")
@Data
public class VendedorProfile {
    @Id
    private Long id; // mismo id que Usuario (o usa @GeneratedValue si preferís relación distinta)

    @OneToOne
    @MapsId
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String nombreTienda;
    private String direccionTienda;
    private String telefonoContacto;
    private int radioEntregaKm;

    private Double ratingPromedio = 0.0;
    private Integer totalResenas = 0;
}
