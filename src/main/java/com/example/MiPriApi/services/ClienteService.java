package com.example.MiPriApi.services;

<<<<<<< HEAD
import com.example.MiPriApi.entities.Cliente;
=======
import com.example.MiPriApi.entities.DTO.CambiarPasswordRequestDTO;
import com.example.MiPriApi.entities.Cliente;
import com.example.MiPriApi.entities.Domicilio;
>>>>>>> Dev
import com.example.MiPriApi.entities.Pedido;
import com.example.MiPriApi.entities.Usuario;
import com.example.MiPriApi.entities.enums.Rol;
import com.example.MiPriApi.repositories.ClienteRepository;
<<<<<<< HEAD
=======
import com.example.MiPriApi.repositories.DomicilioRepository;
>>>>>>> Dev
import com.example.MiPriApi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import java.util.List;
=======
import java.util.HashSet;
import java.util.List;
import java.util.Set;
>>>>>>> Dev

@Service
public class ClienteService extends BaseService<Cliente, Long> {

    @Autowired
    private final ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

<<<<<<< HEAD
=======
    @Autowired
    private DomicilioRepository domicilioRepository;

>>>>>>> Dev
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

<<<<<<< HEAD
        cliente.setNombre(nuevosDatos.getNombre());
        cliente.setApellido(nuevosDatos.getApellido());
        // Agrega aquí otros campos a actualizar si es necesario
        return clienteRepository.save(cliente);
    }

=======
        // Actualizar solo los campos permitidos
        cliente.setNombre(nuevosDatos.getNombre());
        cliente.setApellido(nuevosDatos.getApellido());
        cliente.setEmail(nuevosDatos.getEmail());
        cliente.setTelefono(nuevosDatos.getTelefono());
        cliente.setFechaNacimiento(nuevosDatos.getFechaNacimiento());

        // No modificar usuario aunque venga en el JSON

        // Actualizar domicilios si vienen en el request
        if (nuevosDatos.getDomicilios() != null && !nuevosDatos.getDomicilios().isEmpty()) {
            Set<Domicilio> domiciliosActualizados = new HashSet<>();
            for (Domicilio d : nuevosDatos.getDomicilios()) {
                Domicilio domicilio = domicilioRepository.findById(d.getId())
                        .orElseThrow(() -> new RuntimeException("Domicilio no encontrado"));
                domiciliosActualizados.add(domicilio);
            }
            cliente.setDomicilios(domiciliosActualizados);
        }

        return clienteRepository.save(cliente);
    }

    // Cambiar contraseña del cliente

    public void cambiarPassword(Long clienteId, CambiarPasswordRequestDTO request) {
        if (!request.nuevaPassword.equals(request.repetirPassword)) {
            throw new RuntimeException("Las contraseñas no coinciden");
        }
        if (!esPasswordValida(request.nuevaPassword)) {
            throw new RuntimeException("La contraseña no cumple los requisitos de seguridad");
        }
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Usuario usuario = cliente.getUsuario();
        usuario.setPassword(passwordEncoder.encode(request.nuevaPassword));
        usuarioRepository.save(usuario);
    }

    private boolean esPasswordValida(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\W.*");
    }

    // Obtener historial de pedidos por username
>>>>>>> Dev
    public List<Pedido> obtenerPedidosPorUsername(String username) {
        Cliente cliente = clienteRepository.findByUsuario_Username(username)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return cliente.getPedidos();
    }
<<<<<<< HEAD

=======
>>>>>>> Dev
}