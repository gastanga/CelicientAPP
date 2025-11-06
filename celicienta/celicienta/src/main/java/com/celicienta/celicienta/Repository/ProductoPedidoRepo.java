package com.celicienta.celicienta.Repository;
import com.celicienta.celicienta.Entity.Pedido;
import com.celicienta.celicienta.Entity.ProductoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoPedidoRepo extends JpaRepository <ProductoPedido, Long> {
    List<ProductoPedido> findByPedido(Pedido pedido);
}
