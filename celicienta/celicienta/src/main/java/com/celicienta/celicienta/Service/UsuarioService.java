package com.celicienta.celicienta.Service;
import com.celicienta.celicienta.Entity.Usuario;
import com.celicienta.celicienta.Entity.Vendedor;
import com.celicienta.celicienta.Repository.UsuarioRepo;
import com.celicienta.celicienta.Repository.VendedorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private VendedorRepo vendedorRepo;

    @Autowired
    private UsuarioRepo usuarioRepo;

    public Usuario registrarUsuario(Usuario usuario) {
        if (usuarioRepo.findByUsuario(usuario.getUsuario()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        if (usuarioRepo.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        return usuarioRepo.save(usuario);
    }

    public Usuario iniciarSesion(String usuario, String password) {
        Optional<Usuario> v = usuarioRepo.findByUsuario(usuario);
        if (v.isPresent() && v.get().getPassword().equals(password)) {
            return v.get();
        } else {
            throw new RuntimeException("Usuario o contraseña incorrectos.");
        }
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado."));
    }
}
