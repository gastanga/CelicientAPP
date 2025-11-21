package com.celicienta.celicienta.Controller;
import com.celicienta.celicienta.DTO.VendedorDTO;
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
    public ResponseEntity<VendedorDTO> crearPerfil(@PathVariable Long usuarioId, @RequestBody VendedorProfile perfil) {
        VendedorProfile vp = vendedorProfileService.crearPerfil(usuarioId, perfil);
        return ResponseEntity.ok(new VendedorDTO(vp));
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<VendedorDTO> obtenerPerfil(@PathVariable Long usuarioId) {
        VendedorProfile vp = vendedorProfileService.obtenerPerfil(usuarioId);
        return ResponseEntity.ok(new VendedorDTO(vp));
    }
}
