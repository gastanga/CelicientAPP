package com.celicienta.celicienta.Repository;
import com.celicienta.celicienta.Entity.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredienteRepo extends JpaRepository <Ingrediente, Long> {
}
