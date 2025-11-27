package com.celicienta.celicienta.RestController;
import com.celicienta.celicienta.DTO.AnuncioDTO;
import com.celicienta.celicienta.DTO.CrearAnuncioDTO;
import com.celicienta.celicienta.Entity.Producto;
import com.celicienta.celicienta.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping ("api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping("/publicar/{usuarioId}")
    public ResponseEntity<AnuncioDTO> publicar(@PathVariable Long usuarioId, @RequestBody CrearAnuncioDTO request) {
        try {
            Producto nuevo = new Producto();
            nuevo.setNombre(request.getNombre());
            nuevo.setDescripcion(request.getDescripcion());
            nuevo.setPrecio(request.getPrecio());
            nuevo.setPlazoEntregaDias(request.getPlazoEntregaDias());
            nuevo.setActivo(true);

            Producto creado = productoService.publicarProducto(usuarioId, nuevo);

            AnuncioDTO anuncioDTO = new AnuncioDTO(creado);
            return ResponseEntity.status(201).body(anuncioDTO);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(productoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productoService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        try {
            productoService.eliminarProducto(id);
            return ResponseEntity.ok("Producto eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
        }
    }

    @PutMapping("/actualizar/{productoId}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable Long productoId,
            @RequestBody Producto productoActualizado) {
        try {
            Producto producto = productoService.actualizarProducto(productoId, productoActualizado);
            return ResponseEntity.ok(producto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
