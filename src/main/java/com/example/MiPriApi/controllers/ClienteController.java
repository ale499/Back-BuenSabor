package com.example.MiPriApi.controllers;

<<<<<<< HEAD
import com.example.MiPriApi.controllers.BaseController;
=======
import com.example.MiPriApi.entities.DTO.CambiarPasswordRequestDTO;
>>>>>>> Dev
import com.example.MiPriApi.entities.Cliente;
import com.example.MiPriApi.entities.Pedido;
import com.example.MiPriApi.services.ClienteService;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.security.core.context.SecurityContextHolder;
=======
>>>>>>> Dev
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController extends BaseController<Cliente, Long> {

<<<<<<< HEAD
=======

>>>>>>> Dev
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        super(clienteService);
        this.clienteService = clienteService;
    }

<<<<<<< HEAD
    // Endpoint para registrar nuevo cliente
=======
    // Registrar nuevo cliente
>>>>>>> Dev
    @PostMapping("/registro")
    public ResponseEntity<Cliente> registrar(@RequestBody Cliente cliente) throws Exception {
        Cliente nuevo = clienteService.registrar(cliente);
        return ResponseEntity.ok(nuevo);
    }

<<<<<<< HEAD
    // Endpoint para actualizar datos del cliente autenticado
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Long id, @RequestBody Cliente nuevosDatos) {
=======
    // Actualizar datos del cliente (solo campos permitidos y domicilios)
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente nuevosDatos) {
>>>>>>> Dev
        Cliente actualizado = clienteService.actualizarDatosPorId(id, nuevosDatos);
        return ResponseEntity.ok(actualizado);
    }

<<<<<<< HEAD
    @GetMapping("/{idCliente}/pedidos")
=======
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
>>>>>>> Dev
    public ResponseEntity<List<Pedido>> obtenerHistorialPedidos(@RequestParam String username) {
        List<Pedido> pedidos = clienteService.obtenerPedidosPorUsername(username);
        return ResponseEntity.ok(pedidos);
    }
<<<<<<< HEAD


=======
>>>>>>> Dev
}