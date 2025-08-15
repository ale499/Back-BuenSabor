package com.example.MiPriApi.controllers.Auth0;

import com.example.MiPriApi.entities.ClienteAuth0;
import com.example.MiPriApi.services.ClienteAuth0Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/client", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {

    @Autowired
    private ClienteAuth0Service clienteAuth0Service;

    @GetMapping(value = "")
    public ResponseEntity<?> publicEndpoint() {
        return ResponseEntity.status(HttpStatus.OK).body("{ \"message\": \"Este es un endpoint de usuario. Podes ver esta respuesta porque te has logueado en la aplicación.\"}");
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ClienteAuth0> buscarPorEmail(@PathVariable String email) {
        try {
            ClienteAuth0 cliente = clienteAuth0Service.buscarPorEmail(email);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/auth0/{auth0Id}")
    public ResponseEntity<ClienteAuth0> buscarPorAuth0Id(@PathVariable String auth0Id) {
        try {
            // Decode the URL-encoded Auth0 ID
            String decodedAuth0Id = URLDecoder.decode(auth0Id, StandardCharsets.UTF_8);
            ClienteAuth0 cliente = clienteAuth0Service.buscarPorAuth0Id(decodedAuth0Id);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/auth0/{auth0Id}/domicilios")
    public ResponseEntity<?> asociarDomiciliosPorAuth0Id(
            @PathVariable String auth0Id,
            @RequestBody Set<Long> domicilioIds) {
        try {
            // Decode the URL-encoded Auth0 ID
            String decodedAuth0Id = URLDecoder.decode(auth0Id, StandardCharsets.UTF_8);
            ClienteAuth0 clienteActualizado = clienteAuth0Service.asociarDomiciliosPorAuth0Id(decodedAuth0Id, domicilioIds);
            return ResponseEntity.ok(clienteActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al asociar domicilios: " + e.getMessage());
        }
    }

    @PostMapping("/auth0/{auth0Id}/domicilio/{domicilioId}")
    public ResponseEntity<?> agregarDomicilio(
            @PathVariable String auth0Id,
            @PathVariable Long domicilioId) {
        try {
            // Decode the URL-encoded Auth0 ID
            String decodedAuth0Id = URLDecoder.decode(auth0Id, StandardCharsets.UTF_8);
            ClienteAuth0 clienteActualizado = clienteAuth0Service.agregarDomicilio(decodedAuth0Id, domicilioId);
            return ResponseEntity.ok(clienteActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al agregar domicilio: " + e.getMessage());
        }
    }

    @DeleteMapping("/auth0/{auth0Id}/domicilio/{domicilioId}")
    public ResponseEntity<?> removerDomicilio(
            @PathVariable String auth0Id,
            @PathVariable Long domicilioId) {
        try {
            // Decode the URL-encoded Auth0 ID
            String decodedAuth0Id = URLDecoder.decode(auth0Id, StandardCharsets.UTF_8);
            ClienteAuth0 clienteActualizado = clienteAuth0Service.removerDomicilio(decodedAuth0Id, domicilioId);
            return ResponseEntity.ok(clienteActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al remover domicilio: " + e.getMessage());
        }
    }

    @GetMapping("/auth0/{auth0Id}/exists")
    public ResponseEntity<Boolean> verificarExistencia(@PathVariable String auth0Id) {
        try {
            // Decode the URL-encoded Auth0 ID
            String decodedAuth0Id = URLDecoder.decode(auth0Id, StandardCharsets.UTF_8);
            boolean existe = clienteAuth0Service.existeCliente(decodedAuth0Id);
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
