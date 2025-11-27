package com.celicienta.celicienta.DTO;
import com.celicienta.celicienta.Entity.Usuario;
import com.celicienta.celicienta.Entity.VendedorProfile;
import lombok.Data;

@Data
public class VendedorPublicoDTO {
    private Long idUsuario;
    private String nombreUsuario;
    private String nombreTienda;
    private Double ratingPromedio;
    private int totalResenas;
    private int radioEntregaKm;
    private String codigoPostalTienda;

    public VendedorPublicoDTO (VendedorProfile vp) {
        Usuario usuario = vp.getUsuario();
        this.idUsuario = usuario.getId();
        this.nombreUsuario = usuario.getUsername();
        this.nombreTienda = vp.getNombreTienda();
        this.ratingPromedio = vp.getRatingPromedio();
        this.totalResenas = vp.getTotalResenas();
        this.radioEntregaKm = vp.getRadioEntregaKm();
        this.codigoPostalTienda = vp.getCodigoPostalTienda();
    }

    public VendedorPublicoDTO(Usuario usuario) {
        this.idUsuario = usuario.getId();
        this.nombreUsuario = usuario.getUsername();
        VendedorProfile vp = usuario.getVendedorProfile();
        if (vp != null) {
            this.nombreTienda = vp.getNombreTienda();
            this.ratingPromedio = vp.getRatingPromedio();
            this.totalResenas = vp.getTotalResenas();
            this.radioEntregaKm = vp.getRadioEntregaKm();
            this.codigoPostalTienda = vp.getCodigoPostalTienda();
        }
    }

    public VendedorPublicoDTO() {
    }
}
