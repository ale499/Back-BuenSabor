package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.DTO.*;
import com.example.MiPriApi.entities.*;
import com.example.MiPriApi.services.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController extends BaseController<Cliente, Long> {


    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        super(clienteService);
        this.clienteService = clienteService;
    }

    // Registrar nuevo cliente
    @PostMapping("/registro")
    public ResponseEntity<Cliente> registrar(@RequestBody Cliente cliente) throws Exception {
        Cliente nuevo = clienteService.registrar(cliente);
        return ResponseEntity.ok(nuevo);
    }

    // Actualizar datos del cliente (solo campos permitidos y domicilios)
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente nuevosDatos) {
        Cliente actualizado = clienteService.actualizarDatosPorId(id, nuevosDatos);
        return ResponseEntity.ok(actualizado);
    }

    // Actualizar la contraseña del cliente

    @PutMapping("/{id}/cambiar-password")
    public ResponseEntity<?> cambiarPassword(
            @PathVariable Long id,
            @RequestBody CambiarPasswordRequestDTO request) {
        clienteService.cambiarPassword(id, request);
        return ResponseEntity.ok("Contraseña actualizada correctamente");
    }

    // Obtener historial de pedidos por username
    @GetMapping("/pedidos")
    public ResponseEntity<List<Pedido>> obtenerHistorialPedidos(@RequestParam String username) {
        List<Pedido> pedidos = clienteService.obtenerPedidosPorUsername(username);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Cliente> getClienteByEmail(@PathVariable String email) {
        try{
            Cliente cliente = clienteService.buscarPorEmail(email);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


}