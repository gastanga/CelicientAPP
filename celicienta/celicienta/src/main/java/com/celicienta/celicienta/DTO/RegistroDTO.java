package com.celicienta.celicienta.DTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistroDTO {
    @NotBlank(message = "El nombre completo es obligatorio.")
    private String nombreCompleto;

    @NotBlank(message = "El DNI es obligatorio.")
    private String dni;

    @NotBlank(message = "La dirección de entrega es obligatoria.")
    private String direccionEntregaDefault;

    @NotBlank(message = "La provincia es obligatoria.")
    private String provincia;

    @NotBlank(message = "La ciudad es obligatoria.")
    private String ciudad;

    @NotBlank(message = "El teléfono es obligatorio.")
    private String telefonoEntrega;

    @NotBlank(message = "El código postal es obligatorio.")
    private String codigoPostal;

    @Email(message = "Formato de email inválido.")
    @NotBlank(message = "El email es obligatorio.")
    private String email;

    @NotBlank(message = "El usuario es obligatorio.")
    private String usuario;

    @NotBlank(message = "La contraseña es obligatoria.")
    private String password;

    private boolean quiereSerVendedor;
    private String nombreTienda;
    private String codigoPostalTienda;
    private String direccionTienda;
    private String telefonoContacto;
    private Integer radioEntregaKm;
    private String descripcionTienda;

}
