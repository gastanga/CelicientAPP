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
        if (usuarioRepo.existsByUsuarioOrEmail(usuario.getUsername(), usuario.getEmail())) {
            throw new RuntimeException("Usuario o email ya registrado");
        }

        usuario.setPassword(encoder.encode(usuario.getPassword()));
        usuario.getRoles().add(Rol.COMPRADOR);

        Usuario guardado = usuarioRepo.save(usuario);

        // Crear perfil de comprador por defecto
        CompradorProfile cp = new CompradorProfile();
        cp.setUsuario(guardado);

        compradorProfileRepo.save(cp);
        guardado.setCompradorProfile(cp);

        return guardado;
    }

    public Usuario registrarUsuarioDto(RegistroDTO dto) {

        // 1) Validación básica de campos obligatorios
        if (dto.getUsuario() == null || dto.getUsuario().isBlank()) {
            throw new RuntimeException("El nombre de usuario es obligatorio.");
        }

        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new RuntimeException("El email es obligatorio.");
        }

        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new RuntimeException("La contraseña es obligatoria.");
        }

        // 2) Validar duplicado de usuario o email
        if (usuarioRepo.existsByUsuarioOrEmail(dto.getUsuario(), dto.getEmail())) {
            throw new RuntimeException("Usuario o email ya registrado.");
        }

        // 3) Validar la contraseña ANTES de cifrarla
        Usuario temporal = new Usuario();
        if (!temporal.esPasswordValida(dto.getPassword())) {
            throw new RuntimeException(
                    "La contraseña debe tener 8 caracteres, 1 letra, 1 número y 1 símbolo."
            );
        }

        // 4) Crear usuario y copiar datos
        Usuario u = new Usuario();
        u.setUsername(dto.getUsuario());
        u.setEmail(dto.getEmail());
        u.setNombreCompleto(dto.getNombreCompleto());

        // 5) Cifrar contraseña recién ahora
        u.setPassword(encoder.encode(dto.getPassword()));

        // 6) Rol mínimo obligatorio: COMPRADOR
        u.getRoles().add(Rol.COMPRADOR);

        // 7) Guardar usuario
        Usuario guardado = usuarioRepo.save(u);

        // 8) Crear perfil del comprador (default)
        CompradorProfile cp = new CompradorProfile();
        cp.setUsuario(guardado);
        guardado.setCompradorProfile(cp);
        compradorProfileRepo.save(cp);

        // 9) Si marcó “quiero ser vendedor”, validar y activar
        if (dto.isQuiereSerVendedor()) {

            if (dto.getNombreTienda() == null || dto.getNombreTienda().isBlank()) {
                throw new RuntimeException("El nombre de tienda es obligatorio para vendedores.");
            }

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
