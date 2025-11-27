package com.celicienta.celicienta.Entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table (name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    private String nombreCompleto;

    private String dni;

    @Column (unique = true, nullable = false)
    private String email;

    private String telefonoEntrega;

    @Column (unique = true, nullable = false)
    private String username;

    @Column (nullable = false)
    private String password;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private CompradorProfile compradorProfile;

    @CollectionTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Rol> roles = new HashSet<>();

    private String provincia, ciudad, direccionEntrega;
    private String codigoPostal;
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private VendedorProfile vendedorProfile;

    private int intentosFallidos = 0;
    private boolean bloqueado = false;

    public void setPassword(String password) {
        this.password = password;
    }

    // 🔍 Método privado de validación
    public boolean esPasswordValida(String password) {
        // Regex: al menos una letra, un número, un símbolo, y mínimo 8 caracteres
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&.#_-])[A-Za-z\\d@$!%*?&.#_-]{8,}$";
        return password != null && password.matches(regex);
    }

    public boolean esVendedor() {
        return roles.contains(Rol.VENDEDOR);
    }

    public boolean esAdmin() {
        return roles.contains(Rol.ADMIN);
    }


}
