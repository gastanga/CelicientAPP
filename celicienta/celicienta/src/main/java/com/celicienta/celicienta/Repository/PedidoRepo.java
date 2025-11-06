package com.celicienta.celicienta.Repository;
import com.celicienta.celicienta.Entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepo extends JpaRepository <Pedido, Long> {
    List<Pedido> findByCompradorId(Long compradorId);

    @Query("SELECT DISTINCT p FROM Pedido p JOIN p.detalles d WHERE d.producto.vendedor.id = :vendedorId")
    List<Pedido> findByVendedorId(@Param("vendedorId") Long vendedorId);
}
