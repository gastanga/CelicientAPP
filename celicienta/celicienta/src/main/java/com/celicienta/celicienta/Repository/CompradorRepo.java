package com.celicienta.celicienta.Repository;
import com.celicienta.celicienta.Entity.Comprador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompradorRepo extends JpaRepository <Comprador, Long> {
}
