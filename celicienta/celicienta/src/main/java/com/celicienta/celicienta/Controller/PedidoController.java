package com.celicienta.celicienta.Controller;
import com.celicienta.celicienta.Entity.*;
import com.celicienta.celicienta.Service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/crear/{compradorId}")
    public ResponseEntity<Pedido> crearPedido(
            @PathVariable Long compradorId,
            @RequestBody Pedido pedido) {
        try {
            Pedido nuevo = pedidoService.crearPedido(compradorId, pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/cesta/{compradorId}")
    public ResponseEntity<Pedido> crearPedidoDesdeCesta(
            @PathVariable Long compradorId,
            @RequestParam String metodoPago,
            @RequestParam String observaciones,
            @RequestParam String direccionEntrega) {
        try {
            Pedido pedido = new Pedido();
            Pedido nuevo = pedidoService.crearPedidoDesdeCesta(compradorId, pedido,
                    metodoPago, observaciones, direccionEntrega);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Pedido>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.listarPedidos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        try {
            pedidoService.eliminarPedido(id);
            return ResponseEntity.ok("Pedido eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido no encontrado");
        }
    }

    @PutMapping("/estado/{id}")
    public ResponseEntity<Pedido> actualizarEstado(
            @PathVariable Long id,
            @RequestParam Pedido.estadoPedido estado) {
        try {
            Pedido actualizado = pedidoService.actualizarEstado(id, estado);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/comprador/{id}")
    public ResponseEntity<List<Pedido>> verPedidosComoComprador(@PathVariable Long id) {
        List<Pedido> pedidos = pedidoService.listarPedidosPorComprador(id);
        return pedidos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(pedidos);
    }

    @GetMapping("/vendedor/{id}")
    public ResponseEntity<List<Pedido>> verPedidosComoVendedor(@PathVariable Long id) {
        List<Pedido> pedidos = pedidoService.listarPedidosPorVendedor(id);
        return pedidos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> verPedido(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        return pedido != null ? ResponseEntity.ok(pedido) : ResponseEntity.notFound().build();
    }

}
