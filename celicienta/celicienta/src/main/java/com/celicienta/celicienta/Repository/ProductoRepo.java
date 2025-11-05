package com.celicienta.celicienta.Repository;
import com.celicienta.celicienta.Entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepo extends JpaRepository <Producto, Long> {
}
