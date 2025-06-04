package com.example.MiPriApi.controllers;

import com.example.MiPriApi.controllers.BaseController;
import com.example.MiPriApi.entities.Cliente;
import com.example.MiPriApi.services.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController extends BaseController<Cliente, Long> {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        super(clienteService);
        this.clienteService = clienteService;
    }

    // Endpoint para registrar nuevo cliente
    @PostMapping("/registro")
    public ResponseEntity<Cliente> registrar(@RequestBody Cliente cliente) throws Exception {
        Cliente nuevo = clienteService.registrar(cliente);
        return ResponseEntity.ok(nuevo);
    }

    // Endpoint para actualizar datos del cliente autenticado
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Long id, @RequestBody Cliente nuevosDatos) {
        Cliente actualizado = clienteService.actualizarDatosPorId(id, nuevosDatos);
        return ResponseEntity.ok(actualizado);
    }
}