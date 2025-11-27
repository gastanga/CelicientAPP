package com.celicienta.celicienta.DTO;
import com.celicienta.celicienta.Entity.Producto;
import com.celicienta.celicienta.Entity.Usuario;
import lombok.Data;

@Data
public class AnuncioDTO {
    private Long idProducto;
    private String nombre;
    private String descripcion;
    private Double precio;
    private int plazoEntregaDias;
    private boolean activo;

    private VendedorPublicoDTO vendedor;

    public AnuncioDTO(Producto producto) {
        this.idProducto = producto.getId();
        this.nombre = producto.getNombre();
        this.descripcion = producto.getDescripcion();
        this.precio = producto.getPrecio();
        this.plazoEntregaDias = producto.getPlazoEntregaDias();
        this.activo = producto.isActivo();

        Usuario v = producto.getVendedor();
        if (v != null && v.getVendedorProfile() != null) {
            this.vendedor = new VendedorPublicoDTO(v);
        }
    }

    public AnuncioDTO() {
    }
}
