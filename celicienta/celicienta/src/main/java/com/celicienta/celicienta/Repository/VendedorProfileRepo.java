package com.celicienta.celicienta.Repository;
import com.celicienta.celicienta.Entity.VendedorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VendedorProfileRepo extends JpaRepository<VendedorProfile, Long> {
    Optional<VendedorProfile> findByUsuarioId(Long usuarioId);

}
