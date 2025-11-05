package com.celicienta.celicienta.Repository;
import com.celicienta.celicienta.Entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepo extends JpaRepository <Pedido, Long> {
}
