package com.celicienta.celicienta.webController;
import com.celicienta.celicienta.Service.CestaService;
import com.celicienta.celicienta.Service.PedidoService;
import com.celicienta.celicienta.Repository.ProductoRepo;
import com.celicienta.celicienta.Entity.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController {
    @Autowired
    private ProductoRepo productoRepo;

    @Autowired
    private CestaService cestaService;

    @Autowired
    private PedidoService pedidoService;

    // Mostrar lista de productos
    @GetMapping("/")
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoRepo.findAll());
        return "productos";
    }

    @PostMapping("/agregar/{idProducto}")
    public String agregarAlaCesta(@RequestParam Long idUsuario, @PathVariable Long idProducto) {
        cestaService.agregarProducto(idUsuario, idProducto);
        return "redirect:/cesta?usuarioId=" + idUsuario;
    }

    @GetMapping("/cesta")
    public String verCesta(@RequestParam Long usuarioId, Model model) {
        model.addAttribute("ids", cestaService.obtenerCesta(usuarioId));
        model.addAttribute("usuarioId", usuarioId);
        return "cesta";
    }

    @PostMapping("/comprar")
    public String comprar(@RequestParam Long usuarioId,
                          @RequestParam int cantidadPersonas,
                          Model model) {
        Pedido pedido = pedidoService.crearPedidoDesdeCesta(usuarioId, cantidadPersonas);
        model.addAttribute("pedido", pedido);
        return "pedido-confirmado";
    }
}
