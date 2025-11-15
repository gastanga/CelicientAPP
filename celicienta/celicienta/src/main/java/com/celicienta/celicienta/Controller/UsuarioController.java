package com.celicienta.celicienta.Controller;
import com.celicienta.celicienta.Entity.*;
import com.celicienta.celicienta.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @PostMapping("/registrar")
    public Usuario registrar(@RequestBody Usuario usuario) {
        return usuarioService.registrarUsuario(usuario);
    }

    @PostMapping("/login")
    public Usuario login(@RequestParam String usuario, @RequestParam String password) {
        return usuarioService.iniciarSesion(usuario, password);
    }

    @GetMapping("/{id}")
    public Usuario obtener(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    @PostMapping("/{id}/activar-vendedor")
    public VendedorProfile activar(@PathVariable Long id, @RequestParam String nombreTienda) {
        return usuarioService.activarVendedor(id, nombreTienda);
    }
}
