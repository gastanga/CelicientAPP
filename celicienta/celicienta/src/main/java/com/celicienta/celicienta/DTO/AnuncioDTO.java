package com.celicienta.celicienta.DTO;
import lombok.Data;

@Data
public class AnuncioDTO {
    private Long idVendedor;
    private String nombre;
    private String descripcion;
    private Double precio;
    private int plazoEntregaDias;
    private int cantidadMinimaPersonas = 4;
}
