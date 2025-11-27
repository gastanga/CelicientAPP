package com.celicienta.celicienta.Service;
import com.celicienta.celicienta.Entity.*;
import com.celicienta.celicienta.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private CestaService cestaService;

    @Autowired
    private ProductoRepo productoRepo;

    @Autowired
    private PedidoRepo pedidoRepo;

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private ProductoPedidoRepo productoPedidoRepo;


    public Pedido crearPedido(Long compradorId, Pedido pedido) {

        Usuario comprador = usuarioRepo.findById(compradorId)
                .orElseThrow(() -> new RuntimeException("Comprador no encontrado"));

        pedido.setComprador(comprador);

        double total = 0.0;
        if (pedido.getDetalles() != null) {
            for (ProductoPedido detalle : pedido.getDetalles()) {
                detalle.setPedido(pedido);
                detalle.setPrecioUnitario(detalle.getProducto().getPrecio());
                detalle.calcularSubtotal();
                total += detalle.getSubtotal();
            }
        }
        pedido.setTotal(total);

        return pedidoRepo.save(pedido);
    }

    public Pedido crearPedidoDesdeCesta(Long compradorId, Pedido pedido, String metodoPago,
                                        String observaciones, String direccionEntrega) {

        Usuario comprador = usuarioRepo.findById(compradorId)
                .orElseThrow(() -> new RuntimeException("Comprador no encontrado"));

        List<Long> idsProductos = cestaService.obtenerCesta(compradorId);
        if (idsProductos.isEmpty()) {
            throw new RuntimeException("La cesta está vacía");
        }

        List<ProductoPedido> detalles = new ArrayList<>();
        double total = 0;

        for (Long idProd : idsProductos) {
            Producto producto = productoRepo.findById(idProd)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            ProductoPedido pp = new ProductoPedido();
            pp.setProducto(producto);
            pp.setCantidadPersonas(producto.getPorcionBasePersonas());
            pp.calcularSubtotal();
            pp.setPrecioUnitario(producto.getPrecio());

            total += pp.getSubtotal();
            detalles.add(pp);
        }

        pedido.setComprador(comprador);
        pedido.setDetalles(detalles);
        pedido.setTotal(total);
        pedido.setMetodoPago(metodoPago);
        pedido.setObservaciones(observaciones);
        pedido.setDireccionEntrega(direccionEntrega);

        Pedido pedidoGuardado = pedidoRepo.save(pedido);

        cestaService.vaciarCesta(compradorId);

        return pedidoGuardado;
    }

    public Pedido crearPedidoDesdeCesta(Long compradorId, int cantidadPersonas) {

        List<Long> idsProductos = cestaService.obtenerCesta(compradorId);

        if (idsProductos.isEmpty()) {
            throw new RuntimeException("La cesta está vacía");
        }

        Usuario comprador = usuarioRepo.findById(compradorId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Pedido pedido = new Pedido();
        pedido.setComprador(comprador);

        List<ProductoPedido> detalles = new ArrayList<>();
        double total = 0;

        for (Long idProd : idsProductos) {

            Producto producto = productoRepo.findById(idProd)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            ProductoPedido det = new ProductoPedido();
            det.setProducto(producto);
            det.setCantidadPersonas(cantidadPersonas);
            det.setPrecioUnitario(producto.getPrecio());
            det.setPedido(pedido);

            det.calcularSubtotal();
            total += det.getSubtotal();

            detalles.add(det);
        }

        pedido.setDetalles(detalles);
        pedido.setTotal(total);

        Pedido guardado = pedidoRepo.save(pedido);

        cestaService.vaciarCesta(compradorId);

        return guardado;
    }

    public List<Pedido> listarPedidosPorComprador(Long compradorId) {
        return pedidoRepo.findByCompradorId(compradorId);
    }

    public List<Pedido> listarPedidosPorVendedor(Long vendedorId) {
        return pedidoRepo.findByVendedorId(vendedorId);
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepo.findById(id).orElse(null);
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepo.findAll();
    }

    public Pedido obtenerPorId(Long id) {
        return pedidoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    public void eliminarPedido(Long id) {
        if (!pedidoRepo.existsById(id)) {
            throw new RuntimeException("Pedido no encontrado");
        }
        pedidoRepo.deleteById(id);
    }

    public Pedido actualizarEstado(Long id, Pedido.estadoPedido nuevoEstado) {
        Pedido pedido = pedidoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        pedido.setEstado(nuevoEstado);
        return pedidoRepo.save(pedido);
    }


}
