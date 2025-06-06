package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.Administrador;
import com.example.MiPriApi.entities.Empleado;
import com.example.MiPriApi.entities.Cliente;
import com.example.MiPriApi.entities.Usuario;
import com.example.MiPriApi.repositories.AdministradorRepository;
import com.example.MiPriApi.repositories.EmpleadoRepository;
import com.example.MiPriApi.repositories.ClienteRepository;
import com.example.MiPriApi.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministradorService extends BaseService<Administrador, Long> {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public AdministradorService(AdministradorRepository administradorRepository) {
        super(administradorRepository);
    }

    @Transactional
    public Empleado crearEmpleado(Empleado empleado) throws Exception {
        try {
            // Persistir manualmente el Usuario si no está guardado
            Usuario usuario = empleado.getUsuario();
            if (usuario != null && usuario.getId() == null) {
                usuario = usuarioRepository.save(usuario); // Guardar el Usuario
            }

            // Asociar el Usuario persistido al Empleado
            empleado.setUsuario(usuario);

            // Guardar el Empleado
            return empleadoRepository.save(empleado);
        } catch (Exception ex) {
            throw new Exception("Error al crear el empleado: " + ex.getMessage());
        }
    }

    @Transactional
    public void eliminarEmpleado(Long id) throws Exception {
        try {
            empleadoRepository.deleteById(id);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional
    public Empleado modificarEmpleado(Long id, Empleado empleado) throws Exception {
        try {
            // Buscar el empleado existente
            Empleado empleadoExistente = empleadoRepository.findById(id)
                    .orElseThrow(() -> new Exception("Empleado no encontrado"));

            // Persistir manualmente el Usuario si es necesario
            Usuario usuario = empleado.getUsuario();
            if (usuario != null && usuario.getId() == null) {
                usuario = usuarioRepository.save(usuario);
            }

            empleadoExistente.setNombre(empleado.getNombre());
            empleadoExistente.setApellido(empleado.getApellido());
            empleadoExistente.setRol(empleado.getRol());
            empleadoExistente.setUsuario(usuario);
            empleadoExistente.setImagen(empleado.getImagen());

            return empleadoRepository.save(empleadoExistente);
        } catch (Exception ex) {
            throw new Exception("Error al modificar el empleado: " + ex.getMessage());
        }
    }

    @Transactional
    public void eliminarCliente(Long id) throws Exception {
        try {
            clienteRepository.deleteById(id);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional
    public Cliente modificarCliente(Long id, Cliente cliente) throws Exception {
        try {
            // Buscar el cliente existente
            Cliente clienteExistente = clienteRepository.findById(id)
                    .orElseThrow(() -> new Exception("Cliente no encontrado"));

            // Persistir manualmente el Usuario si es necesario
            Usuario usuario = cliente.getUsuario();
            if (usuario != null && usuario.getId() == null) {
                usuario = usuarioRepository.save(usuario);
            }

            clienteExistente.setNombre(cliente.getNombre());
            clienteExistente.setApellido(cliente.getApellido());
            clienteExistente.setEmail(cliente.getEmail());
            clienteExistente.setTelefono(cliente.getTelefono());
            clienteExistente.setUsuario(usuario);
            clienteExistente.setImagen(cliente.getImagen());

         return clienteRepository.save(clienteExistente);
        } catch (Exception ex) {
            throw new Exception("Error al modificar el cliente: " + ex.getMessage());
        }
    }
}