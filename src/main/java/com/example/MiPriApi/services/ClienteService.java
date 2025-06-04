package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.Cliente;
import com.example.MiPriApi.entities.Usuario;
import com.example.MiPriApi.entities.enums.Rol;
import com.example.MiPriApi.repositories.ClienteRepository;
import com.example.MiPriApi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteService extends BaseService<Cliente, Long> {

    @Autowired
    private final ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder) {
        super(clienteRepository);
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Registrar nuevo cliente
    public Cliente registrar(Cliente cliente) {
        Usuario usuario = cliente.getUsuario();
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRol(Rol.CLIENTE);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        cliente.setUsuario(usuarioGuardado);
        return clienteRepository.save(cliente);
    }

    // Actualizar datos del cliente autenticado
    public Cliente actualizarDatosPorId(Long id, Cliente nuevosDatos) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        cliente.setNombre(nuevosDatos.getNombre());
        cliente.setApellido(nuevosDatos.getApellido());
        // Agrega aquí otros campos a actualizar si es necesario
        return clienteRepository.save(cliente);
    }
}