package com.celicienta.celicienta.Controller;
import com.celicienta.celicienta.DTO.AnuncioDTO;
import com.celicienta.celicienta.DTO.CrearAnuncioDTO;
import com.celicienta.celicienta.Entity.Producto;
import com.celicienta.celicienta.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AnuncioController {
    @Autowired
    private ProductoService productoService;

    // Mostrar formulario para publicar anuncio
    @GetMapping("productos/nuevo/{usuarioId}")
    public String mostrarFormulario(@PathVariable Long usuarioId, Model model) {
        model.addAttribute("crearAnuncioDTO", new CrearAnuncioDTO());
        model.addAttribute("usuarioId", usuarioId);
        return "publicar_anuncio";
    }

    // Recibir datos del formulario y guardar el producto
    @PostMapping("productos/nuevo/{usuarioId}")
    public String publicarAnuncio(
            @PathVariable Long usuarioId,
            @ModelAttribute CrearAnuncioDTO crearAnuncioDTO,
            Model model) {

        Producto producto = new Producto();
        producto.setNombre(crearAnuncioDTO.getNombre());
        producto.setDescripcion(crearAnuncioDTO.getDescripcion());
        producto.setPrecio(crearAnuncioDTO.getPrecio());
        producto.setPlazoEntregaDias(crearAnuncioDTO.getPlazoEntregaDias());
        producto.setActivo(true);

        Producto creado = productoService.publicarProducto(usuarioId, producto);
        AnuncioDTO anuncioDTO = new AnuncioDTO(creado);

        model.addAttribute("anuncio", anuncioDTO);
        return "anuncio_creado";
    }
}
