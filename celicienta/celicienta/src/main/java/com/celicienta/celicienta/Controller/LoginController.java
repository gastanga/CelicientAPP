package com.celicienta.celicienta.Controller;
import com.celicienta.celicienta.Entity.Usuario;
import com.celicienta.celicienta.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String usuario,
                              @RequestParam String password,
                              Model model) {
        try {
            Usuario u = usuarioService.iniciarSesion(usuario, password);
            model.addAttribute("mensaje", "¡Bienvenido " + u.getUsername() + "! Roles: " + u.getRoles());
        } catch (RuntimeException e) {
            model.addAttribute("mensaje", "Error: " + e.getMessage());
        }
        return "login";
    }
}
