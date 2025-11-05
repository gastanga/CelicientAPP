package com.celicienta.celicienta.Repository;
import com.celicienta.celicienta.Entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepo extends JpaRepository <Pedido, Long> {
}
