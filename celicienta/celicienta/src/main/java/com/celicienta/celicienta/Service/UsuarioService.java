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
        if (usuarioRepo.existsByUsernameOrEmail(usuario.getUsername(), usuario.getEmail())) {
            throw new RuntimeException("Usuario o email ya registrado");
        }

        if (!usuario.esPasswordValida(usuario.getPassword())) {
            throw new RuntimeException("La contraseña debe tener 8 caracteres, 1 letra, 1 número y 1 símbolo.");
        }
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        usuario.getRoles().add(Rol.COMPRADOR);

        Usuario guardado = usuarioRepo.save(usuario);

        CompradorProfile cp = new CompradorProfile();
        cp.setUsuario(guardado);

        compradorProfileRepo.save(cp);
        guardado.setCompradorProfile(cp);

        return guardado;
    }

    public Usuario registrarUsuarioDto(RegistroDTO dto) {

        if (usuarioRepo.existsByUsernameOrEmail(dto.getUsuario(), dto.getEmail())) {
            throw new RuntimeException("Usuario o email ya registrado.");
        }

        Usuario temp = new Usuario();
        if (!temp.esPasswordValida(dto.getPassword())) {
            throw new RuntimeException("La contraseña debe tener 8 caracteres, 1 letra, 1 número y 1 símbolo.");
        }

        if (dto.isQuiereSerVendedor()) {

            if (dto.getNombreTienda() == null || dto.getNombreTienda().isBlank())
                throw new RuntimeException("El nombre de la tienda es obligatorio.");

            if (dto.getCodigoPostalTienda() == null || dto.getCodigoPostalTienda().isBlank())
                throw new RuntimeException("El código postal es obligatorio.");

            if (dto.getDireccionTienda() == null || dto.getDireccionTienda().isBlank())
                throw new RuntimeException("La dirección de la tienda es obligatoria.");

            if (dto.getTelefonoContacto() == null || dto.getTelefonoContacto().isBlank())
                throw new RuntimeException("El teléfono de contacto es obligatorio.");

            if (dto.getRadioEntregaKm() == null || dto.getRadioEntregaKm() <= 0)
                throw new RuntimeException("El radio de entrega debe ser mayor a cero.");

            if (dto.getDescripcionTienda() == null || dto.getDescripcionTienda().isBlank())
                throw new RuntimeException("La descripción de la tienda es obligatoria.");
        }

        Usuario u = new Usuario();
        u.setUsername(dto.getUsuario());
        u.setEmail(dto.getEmail());
        u.setNombreCompleto(dto.getNombreCompleto());
        u.setDni(dto.getDni());
        u.setTelefonoEntrega(dto.getTelefonoEntrega());
        u.setProvincia(dto.getProvincia());
        u.setCiudad(dto.getCiudad());
        u.setDireccionEntrega(dto.getDireccionEntregaDefault());
        u.setPassword(encoder.encode(dto.getPassword()));
        u.setCodigoPostal(dto.getCodigoPostal());
        u.getRoles().add(Rol.COMPRADOR);

        Usuario guardado = usuarioRepo.save(u);

        CompradorProfile cp = new CompradorProfile();
        cp.setUsuario(guardado);
        cp.setDireccionEntregaDefault(dto.getDireccionEntregaDefault());
        cp.setTelefonoEntrega(dto.getTelefonoEntrega());
        compradorProfileRepo.save(cp);
        guardado.setCompradorProfile(cp);

        if (dto.isQuiereSerVendedor()) {

            VendedorProfile vp = new VendedorProfile();
            vp.setUsuario(guardado);
            vp.setNombreTienda(dto.getNombreTienda());
            vp.setDireccionTienda(dto.getDireccionTienda());
            vp.setTelefonoContacto(dto.getTelefonoContacto());
            vp.setRadioEntregaKm(dto.getRadioEntregaKm());
            vp.setDescripcionTienda(dto.getDescripcionTienda());
            vp.setCodigoPostalTienda(dto.getCodigoPostalTienda());

            vendedorProfileRepo.save(vp);

            guardado.getRoles().add(Rol.VENDEDOR);
            guardado.setVendedorProfile(vp);
        }

        return usuarioRepo.save(guardado);
    }

    public VendedorProfile activarVendedor(Long idUsuario, String nombreTienda){
        Usuario u = usuarioRepo.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        VendedorProfile vp = new VendedorProfile();
        vp.setUsuario(u);
        vp.setNombreTienda(nombreTienda);
        u.getRoles().add(Rol.VENDEDOR);
        u.setVendedorProfile(vp);

        usuarioRepo.save(u);

        return vp;
    }

    public Usuario iniciarSesion(String usuario, String password) {
        Usuario u = usuarioRepo.findByUsername(usuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (u.isBloqueado()) {
            throw new RuntimeException("Cuenta bloqueada por intentos fallidos. Contacte soporte.");
        }

        if (!encoder.matches(password, u.getPassword())) {
            u.setIntentosFallidos(u.getIntentosFallidos() + 1);

            if (u.getIntentosFallidos() >= 3) {
                u.setBloqueado(true);
            }

            usuarioRepo.save(u);
            throw new RuntimeException("Contraseña incorrecta.");
        }

        u.setIntentosFallidos(0);
        usuarioRepo.save(u);

        return u;
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
    }


}
