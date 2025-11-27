package com.celicienta.celicienta.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "compradores_profile")
@Data
public class CompradorProfile {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private Usuario usuario;
    private String direccionEntregaDefault;
    private String telefonoEntrega;
}
