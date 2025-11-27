package com.celicienta.celicienta.RestController;
import com.celicienta.celicienta.DTO.AnuncioDTO;
import com.celicienta.celicienta.DTO.RegistroDTO;
import com.celicienta.celicienta.DTO.UsuarioDTO;
import com.celicienta.celicienta.DTO.VendedorPublicoDTO;
import com.celicienta.celicienta.Entity.*;
import com.celicienta.celicienta.Service.UsuarioService;
import com.celicienta.celicienta.Service.VendedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private VendedorService vendedorService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@Valid @RequestBody RegistroDTO dto, BindingResult result) {

        if (result.hasErrors()) {
            // Devolver todos los errores juntos
            Map<String, String> errores = new HashMap<>();

            result.getFieldErrors().forEach(err ->
                    errores.put(err.getField(), err.getDefaultMessage())
            );

            return ResponseEntity.badRequest().body(errores);
        }

        Usuario u = usuarioService.registrarUsuarioDto(dto);
        return ResponseEntity.ok(u);
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
    public VendedorPublicoDTO activar(@PathVariable Long id, @RequestParam String nombreTienda) {
        VendedorProfile vp = usuarioService.activarVendedor(id, nombreTienda);
        return new VendedorPublicoDTO(vp);
    }

    @GetMapping("/vendedor/{id}")
    public VendedorPublicoDTO verVendedor(@PathVariable Long id) {
        VendedorProfile vp = vendedorService.obtenerPerfil(id);
        return new VendedorPublicoDTO(vp);
    }

    @GetMapping("/vendedor/{id}/productos")
    public ResponseEntity<List<AnuncioDTO>> listarProductos(@PathVariable Long id) {
        List<AnuncioDTO> lista = vendedorService.listarProductosDto(id);
        return ResponseEntity.ok(lista);
    }

}
