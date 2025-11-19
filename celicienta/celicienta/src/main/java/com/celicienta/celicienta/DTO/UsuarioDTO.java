package com.celicienta.celicienta.DTO;
import com.celicienta.celicienta.Entity.Usuario;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UsuarioDTO {
    private String nombreCompleto;
    private String email;
    private String telefono;
    private String usuario;
    private String codigoPostal;
    private LocalDateTime fechaRegistro;
    private String direccionEntregaDefault;

    public UsuarioDTO(Usuario u) {
        this.nombreCompleto = u.getNombreCompleto();
        this.email = u.getEmail();
        this.telefono = u.getTelefono();
        this.usuario = u.getUsuario();
        this.codigoPostal = u.getCodigoPostal();
        this.fechaRegistro = u.getFechaRegistro();
        // Si tiene perfil de comprador
        if (u.getCompradorProfile() != null) {
            this.direccionEntregaDefault = u.getCompradorProfile().getDireccionEntregaDefault();
        }
    }
}
