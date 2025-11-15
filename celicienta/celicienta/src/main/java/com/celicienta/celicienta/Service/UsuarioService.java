package com.celicienta.celicienta.Service;
import com.celicienta.celicienta.DTO.RegistroDTO;
import com.celicienta.celicienta.Entity.*;
import com.celicienta.celicienta.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private CompradorProfileRepo compradorProfileRepo;

    @Autowired
    private VendedorProfileRepo vendedorProfileRepo;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Usuario registrarUsuario(Usuario usuario) {
        if (usuarioRepo.existsByUsuarioOrEmail(usuario.getUsuario(), usuario.getEmail())) {
            throw new RuntimeException("Usuario o email ya registrado");
        }

        usuario.setPassword(encoder.encode(usuario.getPassword()));
        usuario.getRoles().add(Rol.COMPRADOR);

        // ⚙️ En el futuro: aquí se cifrará la contraseña con BCrypt
        Usuario guardado = usuarioRepo.save(usuario);

        // Crear perfil de comprador por defecto
        CompradorProfile cp = new CompradorProfile();
        cp.setUsuario(guardado);

        compradorProfileRepo.save(cp);

        return guardado;
    }

    public Usuario registrarUsuario(RegistroDTO dto) {
        Usuario u = new Usuario();
        u.setUsuario(dto.getUsuario());
        u.setPassword(dto.getPassword());
        u.setEmail(dto.getEmail());
        u.getRoles().add(Rol.COMPRADOR);

        Usuario guardado = usuarioRepo.save(u);

        CompradorProfile cp = new CompradorProfile();
        cp.setUsuario(guardado);
        compradorProfileRepo.save(cp);

        if (dto.isQuiereSerVendedor()) {
            activarVendedor(guardado.getId(), dto.getNombreTienda());
        }

        return guardado;
    }


    public VendedorProfile activarVendedor(Long userId, String nombreTienda){
        Usuario u = buscarPorId(userId);

        VendedorProfile vp = new VendedorProfile();
        vp.setUsuario(u);
        vp.setNombreTienda(nombreTienda);

        u.getRoles().add(Rol.VENDEDOR);
        vp.setUsuario(u);
        u.setVendedorProfile(vp);

        usuarioRepo.save(u);

        return vendedorProfileRepo.save(vp);
    }

    public Usuario iniciarSesion(String usuario, String password) {
        Usuario u = usuarioRepo.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 🚫 Si está bloqueado
        if (u.isBloqueado()) {
            throw new RuntimeException("Cuenta bloqueada por intentos fallidos. Contacte soporte.");
        }

        // ✅ Verificar contraseña cifrada
        if (!encoder.matches(password, u.getPassword())) {
            // ❌ Contraseña incorrecta → incrementar intentos
            u.setIntentosFallidos(u.getIntentosFallidos() + 1);

            if (u.getIntentosFallidos() >= 3) {
                u.setBloqueado(true);
            }

            usuarioRepo.save(u);
            throw new RuntimeException("Contraseña incorrecta.");
        }

        // ✅ Login exitoso → resetear intentos
        u.setIntentosFallidos(0);
        usuarioRepo.save(u);

        return u;
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
    }


}
