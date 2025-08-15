package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.ClienteAuth0;
import com.example.MiPriApi.entities.Domicilio;
import com.example.MiPriApi.repositories.ClienteAuth0Repository;
import com.example.MiPriApi.repositories.DomicilioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ClienteAuth0Service extends BaseService<ClienteAuth0, Long> {

    @Autowired
    private final ClienteAuth0Repository clienteAuth0Repository;

    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private UserAuth0Service userAuth0Service;

    public ClienteAuth0Service(ClienteAuth0Repository clienteAuth0Repository) {
        super(clienteAuth0Repository);
        this.clienteAuth0Repository = clienteAuth0Repository;
    }

    // Buscar cliente por Auth0 ID
    public ClienteAuth0 buscarPorAuth0Id(String auth0Id) throws Exception {
        return clienteAuth0Repository.findByAuth0Id(auth0Id)
                .orElseThrow(() -> new Exception("Cliente no encontrado con auth0Id: " + auth0Id));
    }

    // Buscar cliente por email
    public ClienteAuth0 buscarPorEmail(String email) throws Exception {
        return clienteAuth0Repository.findByEmail(email)
                .orElseThrow(() -> new Exception("Cliente no encontrado con email: " + email));
    }

    // Crear o obtener cliente por Auth0 ID
    public ClienteAuth0 crearOObtenerCliente(String auth0Id) throws Exception {
        // Verificar si ya existe en nuestra BD
        var clienteExistente = clienteAuth0Repository.findByAuth0Id(auth0Id);
        if (clienteExistente.isPresent()) {
            return clienteExistente.get();
        }

        // Si no existe, obtener datos de Auth0 y crear nuevo registro
        var userAuth0 = userAuth0Service.getUserById(auth0Id);
        if (userAuth0 == null) {
            throw new Exception("Usuario no encontrado en Auth0");
        }

        ClienteAuth0 nuevoCliente = ClienteAuth0.builder()
                .auth0Id(auth0Id)
                .email(userAuth0.getEmail())
                .domicilios(new HashSet<>())
                .build();

        return clienteAuth0Repository.save(nuevoCliente);
    }

    // Asociar domicilios por Auth0 ID
    public ClienteAuth0 asociarDomiciliosPorAuth0Id(String auth0Id, Set<Long> domicilioIds) throws Exception {
        // Crear o obtener el cliente
        ClienteAuth0 cliente = crearOObtenerCliente(auth0Id);

        // Obtener los domicilios
        Set<Domicilio> domicilios = new HashSet<>();
        for (Long domicilioId : domicilioIds) {
            Domicilio domicilio = domicilioRepository.findById(domicilioId)
                    .orElseThrow(() -> new RuntimeException("Domicilio no encontrado con ID: " + domicilioId));
            domicilios.add(domicilio);
        }

        // Asociar domicilios al cliente
        cliente.setDomicilios(domicilios);
        return clienteAuth0Repository.save(cliente);
    }

    // Agregar un domicilio específico
    public ClienteAuth0 agregarDomicilio(String auth0Id, Long domicilioId) throws Exception {
        ClienteAuth0 cliente = crearOObtenerCliente(auth0Id);
        Domicilio domicilio = domicilioRepository.findById(domicilioId)
                .orElseThrow(() -> new RuntimeException("Domicilio no encontrado con ID: " + domicilioId));

        // Agregar el domicilio al conjunto existente
        cliente.getDomicilios().add(domicilio);
        return clienteAuth0Repository.save(cliente);
    }

    // Remover un domicilio específico
    public ClienteAuth0 removerDomicilio(String auth0Id, Long domicilioId) throws Exception {
        ClienteAuth0 cliente = crearOObtenerCliente(auth0Id);
        Domicilio domicilio = domicilioRepository.findById(domicilioId)
                .orElseThrow(() -> new RuntimeException("Domicilio no encontrado con ID: " + domicilioId));

        // Remover el domicilio del conjunto
        cliente.getDomicilios().remove(domicilio);
        return clienteAuth0Repository.save(cliente);
    }

    // Verificar si existe un cliente por Auth0 ID
    public boolean existeCliente(String auth0Id) {
        return clienteAuth0Repository.findByAuth0Id(auth0Id).isPresent();
    }
}
