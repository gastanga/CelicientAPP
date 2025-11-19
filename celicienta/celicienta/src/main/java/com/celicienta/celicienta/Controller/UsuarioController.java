package com.celicienta.celicienta.Controller;
import com.celicienta.celicienta.DTO.UsuarioDTO;
import com.celicienta.celicienta.DTO.VendedorDTO;
import com.celicienta.celicienta.Entity.*;
import com.celicienta.celicienta.Repository.VendedorProfileRepo;
import com.celicienta.celicienta.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private VendedorProfileRepo vendedorProfileRepo;


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
    public VendedorProfile activar(@PathVariable Long id, @RequestParam String nombreTienda) {
        return usuarioService.activarVendedor(id, nombreTienda);
    }

    @GetMapping("/vendedor/{id}")
    public VendedorDTO verVendedor(@PathVariable Long id) {
        VendedorProfile vp = vendedorProfileRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));
        return new VendedorDTO(vp);
    }

}
