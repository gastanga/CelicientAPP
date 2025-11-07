package com.celicienta.celicienta.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vendedores_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public VendedorProfile(Usuario usuario, String descripcion) {
        this.usuario = usuario;
        this.nombreTienda = descripcion; // usamos la descripción como nombre de tienda, por ahora
    }


}
