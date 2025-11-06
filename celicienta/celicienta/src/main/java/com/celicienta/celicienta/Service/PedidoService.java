package com.celicienta.celicienta.Service;
import com.celicienta.celicienta.Entity.*;
import com.celicienta.celicienta.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepo pedidoRepo;

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private ProductoPedidoRepo productoPedidoRepo;

    public List<Pedido> listarPedidosPorComprador(Long compradorId) {
        return pedidoRepo.findByCompradorId(compradorId);
    }

    public List<Pedido> listarPedidosPorVendedor(Long vendedorId) {
        return pedidoRepo.findByVendedorId(vendedorId);
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepo.findById(id).orElse(null);
    }

    public Pedido crearPedido(Long compradorId, Pedido pedido) {

        Usuario comprador = usuarioRepo.findById(compradorId)
                .orElseThrow(() -> new RuntimeException("Comprador no encontrado"));

        pedido.setComprador(comprador);

        double total = 0.0;
        if (pedido.getDetalles() != null) {
            for (ProductoPedido detalle : pedido.getDetalles()) {
                detalle.setPedido(pedido);
                detalle.calcularSubtotal();
                total += detalle.getSubtotal();
            }
        }
        pedido.setTotal(total);

        return pedidoRepo.save(pedido);
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
