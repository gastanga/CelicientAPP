package com.celicienta.celicienta.Service;
import com.celicienta.celicienta.DTO.AnuncioDTO;
import com.celicienta.celicienta.DTO.VendedorPublicoDTO;
import com.celicienta.celicienta.Entity.Producto;
import com.celicienta.celicienta.Entity.Rol;
import com.celicienta.celicienta.Entity.Usuario;
import com.celicienta.celicienta.Entity.VendedorProfile;
import com.celicienta.celicienta.Repository.ProductoRepo;
import com.celicienta.celicienta.Repository.UsuarioRepo;
import com.celicienta.celicienta.Repository.VendedorProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendedorService {
    @Autowired
    private VendedorProfileRepo vendedorProfileRepo;

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private ProductoRepo productoRepo;

    public VendedorProfile activarVendedor(Long usuarioId, String nombreTienda) {
        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Evitar duplicados
        if (vendedorProfileRepo.findByUsuarioId(usuarioId).isPresent()) {
            throw new RuntimeException("El usuario ya tiene perfil de vendedor");
        }

        VendedorProfile vp = new VendedorProfile();
        vp.setUsuario(usuario);
        vp.setNombreTienda(nombreTienda);

        usuario.getRoles().add(Rol.VENDEDOR);
        usuarioRepo.save(usuario);

        return vendedorProfileRepo.save(vp);
    }

    public VendedorProfile obtenerPerfil(Long usuarioId) {
        return vendedorProfileRepo.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Perfil de vendedor no encontrado"));
    }

    public List<Producto> listarProductos(Long usuarioId) {
        VendedorProfile vp = obtenerPerfil(usuarioId);
        return productoRepo.findByVendedor(vp.getUsuario());
    }

    public List<AnuncioDTO> listarProductosDto(Long usuarioId) {
        VendedorProfile vp = obtenerPerfil(usuarioId);
        List<Producto> productos = productoRepo.findByVendedor(vp.getUsuario());

        return productos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private AnuncioDTO toDTO(Producto p) {
        AnuncioDTO dto = new AnuncioDTO();
        dto.setIdProducto(p.getId());
        dto.setNombre(p.getNombre());
        dto.setDescripcion(p.getDescripcion());
        dto.setPrecio(p.getPrecio());
        dto.setPlazoEntregaDias(p.getPlazoEntregaDias());
        dto.setActivo(p.isActivo());

        VendedorPublicoDTO vendedorDTO = new VendedorPublicoDTO();
        vendedorDTO.setIdUsuario(p.getVendedor().getId());
        vendedorDTO.setNombreUsuario(p.getVendedor().getUsername());
        if (p.getVendedor().getVendedorProfile() != null) {
            vendedorDTO.setNombreTienda(p.getVendedor().getVendedorProfile().getNombreTienda());
            vendedorDTO.setRatingPromedio(p.getVendedor().getVendedorProfile().getRatingPromedio());
        }

        dto.setVendedor(vendedorDTO);

        return dto;
    }

}
