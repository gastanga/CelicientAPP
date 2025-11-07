package com.celicienta.celicienta.Controller;
import com.celicienta.celicienta.Entity.VendedorProfile;
import com.celicienta.celicienta.Service.VendedorProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendedores")
public class VendedorProfileController {
    @Autowired
    private VendedorProfileService vendedorProfileService;

    @PostMapping("/crear/{usuarioId}")
    public ResponseEntity<VendedorProfile> crearPerfil(@PathVariable Long usuarioId, @RequestBody VendedorProfile perfil) {
        return ResponseEntity.ok(vendedorProfileService.crearPerfil(usuarioId, perfil));
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<VendedorProfile> obtenerPerfil(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(vendedorProfileService.obtenerPerfil(usuarioId));
    }
}
