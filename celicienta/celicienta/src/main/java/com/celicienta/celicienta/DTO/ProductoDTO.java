package com.celicienta.celicienta.DTO;
import lombok.Data;

@Data
public class ProductoDTO {
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer plazoEntregaDias;

}
