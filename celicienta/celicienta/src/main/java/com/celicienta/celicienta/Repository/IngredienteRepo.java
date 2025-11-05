package com.celicienta.celicienta.Repository;
import com.celicienta.celicienta.Entity.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredienteRepo extends JpaRepository <Ingrediente, Long> {
}
