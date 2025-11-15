package com.celicienta.celicienta.DTO;
import lombok.Data;

@Data
public class RegistroDTO {
    private String usuario;
    private String password;
    private String email;
    private String nombreCompleto;
    private boolean quiereSerVendedor;
    private String nombreTienda;
}
