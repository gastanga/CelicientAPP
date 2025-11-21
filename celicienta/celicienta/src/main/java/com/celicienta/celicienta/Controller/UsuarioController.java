package com.celicienta.celicienta.Controller;
import com.celicienta.celicienta.DTO.ProductoDTO;
import com.celicienta.celicienta.DTO.UsuarioDTO;
import com.celicienta.celicienta.DTO.VendedorDTO;
import com.celicienta.celicienta.Entity.*;
import com.celicienta.celicienta.Repository.VendedorProfileRepo;
import com.celicienta.celicienta.Service.UsuarioService;
import com.celicienta.celicienta.Service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private VendedorService vendedorService;

    @PostMapping("/registrar")
    public Usuario registrar(@RequestBody Usuario usuario) {
        return usuarioService.registrarUsuario(usuario);
    }

    @PostMapping("/login")
    public Usuario login(@RequestParam String usuario, @RequestParam String password) {
        return usuarioService.iniciarSesion(usuario, password);
    }

    @GetMapping("/{id}")
    public UsuarioDTO verUsuario(@PathVariable Long id) {
        Usuario u = usuarioService.buscarPorId(id);
        return new UsuarioDTO(u);
    }

    @GetMapping("/perfil/editar/{id}")
    public Usuario obtener(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    @PostMapping("/{id}/activar-vendedor")
    public VendedorDTO activar(@PathVariable Long id, @RequestParam String nombreTienda) {
        VendedorProfile vp = usuarioService.activarVendedor(id, nombreTienda);
        return new VendedorDTO(vp);
    }

    @GetMapping("/vendedor/{id}")
    public VendedorDTO verVendedor(@PathVariable Long id) {
        VendedorProfile vp = vendedorService.obtenerPerfil(id);
        return new VendedorDTO(vp);
    }

    @GetMapping("/vendedor/{id}/productos")
    public ResponseEntity<List<ProductoDTO>> listarProductos(@PathVariable Long id) {
        List<ProductoDTO> lista = vendedorService.listarProductosDto(id);
        return ResponseEntity.ok(lista);
    }

}
