package com.celicienta.celicienta.Repository;
import com.celicienta.celicienta.Entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepo extends JpaRepository <Producto, Long> {
}
