package com.celicienta.celicienta.Entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    private String nombreCompleto;

    @Column (unique = true, nullable = false)
    private String email;

    private String telefono;

    @Column (unique = true, nullable = false)
    private String usuario;

    @Column (nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    private String provincia, ciudad;
    private String codigoPostal;
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private VendedorProfile vendedorProfile;

}
