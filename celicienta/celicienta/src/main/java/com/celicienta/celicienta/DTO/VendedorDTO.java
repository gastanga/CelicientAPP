package com.celicienta.celicienta.DTO;
import lombok.Data;

@Data
public class VendedorDTO {
    private String nombreTienda;
    private String direccionTienda;
    private String telefonoContacto;
    private int radioEntregaKm;
    private Double ratingPromedio;
    private int totalResenas;

    public VendedorDTO(com.celicienta.celicienta.Entity.VendedorProfile vp) {
        this.nombreTienda = vp.getNombreTienda();
        this.direccionTienda = vp.getDireccionTienda();
        this.telefonoContacto = vp.getTelefonoContacto();
        this.radioEntregaKm = vp.getRadioEntregaKm();
        this.ratingPromedio = vp.getRatingPromedio();
        this.totalResenas = vp.getTotalResenas();
    }
}
