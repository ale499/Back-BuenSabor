package com.example.MiPriApi.controllers.Auth0;

import com.example.MiPriApi.entities.Cliente;
import com.example.MiPriApi.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/client", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping(value = "")
    public ResponseEntity<?> publicEndpoint() {
        return ResponseEntity.status(HttpStatus.OK).body("{ \"message\": \"Este es un endpoint de usuario. Podes ver esta respuesta porque te has logueado en la aplicación.\"}");
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Cliente> buscarPorEmail(@PathVariable String email) {
        try {
            Cliente cliente = clienteService.buscarPorEmail(email);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


}