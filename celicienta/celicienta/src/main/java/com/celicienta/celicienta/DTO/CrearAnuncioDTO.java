package com.celicienta.celicienta.DTO;
import lombok.Data;

@Data
public class CrearAnuncioDTO {
    private String nombre;
    private String descripcion;
    private Double precio;
    private int plazoEntregaDias;

}
