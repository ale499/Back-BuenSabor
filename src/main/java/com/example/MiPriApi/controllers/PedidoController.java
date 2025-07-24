package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.*;
import com.example.MiPriApi.entities.DTO.*;
import com.example.MiPriApi.entities.enums.Estado;
import com.example.MiPriApi.services.*;
import com.example.MiPriApi.repositories.*;
import com.example.MiPriApi.services.Mappers.PedidoMapper;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController extends BaseController<Pedido, Long>{

    public PedidoController(PedidoService pedidoService) {
        super(pedidoService);
    }

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Pedido>> listarPorCliente(@PathVariable Long idCliente) throws Exception {
        List<Pedido> pedidos = pedidoService.listarPorCliente(idCliente);
        return ResponseEntity.ok(pedidos);
    }

    //listar pedidos por email del cliente

    @GetMapping("/cliente/email/{email}")
    public ResponseEntity<List<Pedido>> listarPorClienteEmail(@PathVariable String email) throws Exception {
        List<Pedido> pedidos = pedidoService.listarPorClienteEmail(email);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/empleado/{idEmpleado}")
    public ResponseEntity<List<Pedido>> listarPorEmpleado(@PathVariable Long idEmpleado) throws Exception {
        List<Pedido> pedidos = pedidoService.listarPorEmpleado(idEmpleado);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/sucursal/{idSucursal}")
    public ResponseEntity<List<Pedido>> listarPorSucursal(@PathVariable Long idSucursal) throws Exception {
        List<Pedido> pedidos = pedidoService.listarPorSucursal(idSucursal);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/historial")
    public ResponseEntity<List<Pedido>> obtenerHistorialPedidos() throws Exception {
        String clienteId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Pedido> pedidos = pedidoService.listarPorCliente(Long.valueOf(clienteId));
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{idPedido}/detalle")
    public ResponseEntity<Pedido> verDetallePedido(@PathVariable Long idPedido) throws Exception {
        Pedido pedido = pedidoService.buscarPedidoPorId(idPedido);
        return ResponseEntity.ok(pedido);
    }

    @PostMapping("/{idPedido}/pagar")
    public ResponseEntity<String> pagarPedido(@PathVariable Long idPedido) throws Exception {
        Pedido pedido = pedidoService.buscarPedidoPorId(idPedido);

        MercadoPagoConfig.setAccessToken("TU_ACCESS_TOKEN");

        PreferenceItemRequest item = PreferenceItemRequest.builder()
                .title("Pedido #" + pedido.getId())
                .quantity(1)
                .unitPrice(BigDecimal.valueOf(pedido.getTotal()))
                .build();

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(List.of(item))
                .build();

        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);

        return ResponseEntity.ok(preference.getInitPoint());
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearPedidoDesdeCarrito(@RequestBody PedidoRequestDTO pedidoRequest) throws Exception {
        pedidoService.crearPedidoDesdeCarrito(pedidoRequest);
        return ResponseEntity.ok("Pedido creado correctamente");
    }

    @DeleteMapping("/{idPedido}/eliminar")
    public ResponseEntity<?> eliminarPedido(@PathVariable Long idPedido) {
        try {
            pedidoService.eliminarPedido(idPedido);
            return ResponseEntity.ok("Pedido deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/{idPedido}/confirmar")
    public ResponseEntity<?> confirmarPedido(
            @PathVariable Long idPedido,
            @RequestBody ConfirmarPedidoRequestDTO request) throws Exception {
        pedidoService.confirmarPedido(idPedido, request);
        return ResponseEntity.ok("Pedido confirmado correctamente");
    }
    @GetMapping("/{id}/detalles")
    public ResponseEntity<List<DetallePedidoResponseDTO>> obtenerDetalles(@PathVariable Long id) throws Exception {
        Pedido pedido = pedidoService.buscarPorId(id)
                .orElseThrow(() -> new Exception("Pedido no encontrado"));

        List<DetallePedidoResponseDTO> dtoList = PedidoMapper.toDetalleDTOList(pedido);
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping("/{idPedido}/estado")
    public ResponseEntity<?> cambiarEstadoPedido(
            @PathVariable Long idPedido,
            @RequestParam Estado nuevoEstado) throws Exception {
        pedidoService.cambiarEstado(idPedido, nuevoEstado);
        return ResponseEntity.ok("Estado del pedido actualizado correctamente");
    }
}
