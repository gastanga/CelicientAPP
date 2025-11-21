package com.celicienta.celicienta.Repository;
import com.celicienta.celicienta.Entity.Producto;
import com.celicienta.celicienta.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepo extends JpaRepository <Producto, Long> {
    List<Producto> findByVendedor(Usuario vendedor);

}
