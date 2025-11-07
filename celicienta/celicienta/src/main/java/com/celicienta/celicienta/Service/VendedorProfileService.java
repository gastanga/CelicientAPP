package com.celicienta.celicienta.Service;
import com.celicienta.celicienta.Entity.*;
import com.celicienta.celicienta.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendedorProfileService {

    @Autowired
    private VendedorProfileRepo vendedorProfileRepo;
    @Autowired
    private UsuarioRepo usuarioRepo;

    public VendedorProfile crearPerfil(Long usuarioId, VendedorProfile perfil) {
        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        vendedorProfileRepo.findByUsuarioId(usuarioId)
                .ifPresent(v -> { throw new RuntimeException("El usuario ya tiene perfil de vendedor"); });

        perfil.setUsuario(usuario);
        return vendedorProfileRepo.save(perfil);
    }

    public VendedorProfile obtenerPerfil(Long usuarioId) {
        return vendedorProfileRepo.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Perfil de vendedor no encontrado"));
    }
}
