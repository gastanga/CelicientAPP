package com.celicienta.celicienta.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private Usuario usuario;

    private String nombreTienda;
    private String direccionTienda;
    private String telefonoContacto;
    private int radioEntregaKm;
    private String descripcionTienda;
    private String codigoPostalTienda;

    private Double ratingPromedio = 0.0;
    private int totalResenas = 0;

}
