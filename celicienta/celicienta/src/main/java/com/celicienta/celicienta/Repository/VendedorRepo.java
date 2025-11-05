package com.celicienta.celicienta.Repository;
import com.celicienta.celicienta.Entity.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendedorRepo extends JpaRepository <Vendedor, Long> {
}
