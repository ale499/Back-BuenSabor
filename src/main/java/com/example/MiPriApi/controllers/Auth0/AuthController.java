package com.example.MiPriApi.controllers.Auth0;

import com.example.MiPriApi.entities.Usuario;
import com.example.MiPriApi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public Long login(@RequestBody Usuario loginRequest) {
        Usuario usuario = usuarioRepository.findByUsername(loginRequest.getUsername()).orElse(null);

        // Si tienes email, puedes buscar también por email
        if (usuario == null && loginRequest.getEmail() != null) {
            usuario = usuarioRepository.findByEmail(loginRequest.getEmail()).orElse(null);
        }

        if (usuario == null) {
            usuario = Usuario.builder()
                    .username(loginRequest.getUsername())
                    .email(loginRequest.getEmail())
                    // setea otros campos si es necesario
                    .build();
            usuario = usuarioRepository.save(usuario);
        }
        return usuario.getId();
    }
}