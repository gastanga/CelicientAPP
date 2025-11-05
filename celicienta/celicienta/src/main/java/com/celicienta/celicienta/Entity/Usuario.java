package com.celicienta.celicienta.Entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class Usuario {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    private String nombreCompleto, email, telefono, usuario, password, rol, provincia, ciudad;
    private String codigoPostal;
    private LocalDateTime fechaRegistro = LocalDateTime.now();

}
