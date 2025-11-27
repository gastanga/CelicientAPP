package com.celicienta.celicienta.RestController;

import com.celicienta.celicienta.Service.CestaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cesta")
public class CestaController {
    private final CestaService cestaService;

    public CestaController(CestaService cestaService) {
        this.cestaService = cestaService;
    }

    @PostMapping("/agregar")
    public ResponseEntity<?> agregar(
            @RequestParam Long idUsuario,
            @RequestParam Long idProducto) {

        cestaService.agregarProducto(idUsuario, idProducto);
        return ResponseEntity.ok("Producto agregado");
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminar(
            @RequestParam Long idUsuario,
            @RequestParam Long idProducto) {

        cestaService.eliminarProducto(idUsuario, idProducto);
        return ResponseEntity.ok("Producto eliminado");
    }

    @GetMapping("/ver")
    public ResponseEntity<?> ver(@RequestParam Long idUsuario) {
        return ResponseEntity.ok(cestaService.obtenerCesta(idUsuario));
    }

    @DeleteMapping("/vaciar")
    public ResponseEntity<?> vaciar(@RequestParam Long idUsuario) {
        cestaService.vaciarCesta(idUsuario);
        return ResponseEntity.ok("Cesta vaciada");
    }
}
