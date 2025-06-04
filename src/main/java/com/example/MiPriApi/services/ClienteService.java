package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.*;
import com.example.MiPriApi.entities.enums.*;
import com.example.MiPriApi.repositories.ClienteRepository;
import com.example.MiPriApi.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService extends BaseService<Cliente, Long>{

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository) {
        super(clienteRepository);
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
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
    public Cliente actualizarDatos(String username, Cliente nuevosDatos) {
        Cliente cliente = clienteRepository.findByEmail(nuevosDatos.getEmail())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        cliente.setNombre(nuevosDatos.getNombre());
        cliente.setApellido(nuevosDatos.getApellido());
        // Agrega aquí otros campos a actualizar si es necesario
        return clienteRepository.save(cliente);
    }

    @Transactional
    public List<Pedido> obtenerPedidosPorCliente(Long idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        return cliente.getPedidos(); // Asumiendo que tienes una relación OneToMany
    }


}