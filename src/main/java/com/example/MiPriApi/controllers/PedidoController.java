package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.Pedido;
import com.example.MiPriApi.services.BaseService;
import com.example.MiPriApi.services.PedidoService;
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


    public PedidoController(PedidoService service) {
        super(service);
    }
    @Autowired
    private PedidoService pedidoService;

    @RequestMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Pedido>> listarPorCliente(@PathVariable Long idCliente) throws Exception{
        List<Pedido> pedidos = pedidoService.listarPorCliente(idCliente);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/empleado/{idEmpleado}")
    public ResponseEntity<List<Pedido>> listarPorEmpleado(@PathVariable Long idEmpleado) throws Exception {
        List<Pedido> pedidos = pedidoService.listarPorEmpleado(idEmpleado);
        return ResponseEntity.ok(pedidos);
    }

    @RequestMapping("/sucursal/{idSucursal}")
    public ResponseEntity<List<Pedido>> listarPorSucursal(@PathVariable Long idSucursal) throws Exception{
        List<Pedido> pedidos = pedidoService.listarPorCliente(idSucursal);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/historial")
    public ResponseEntity<List<Pedido>> obtenerHistorialPedidos() throws Exception {
        // Obtener el cliente autenticado
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

        // Crear ítem correctamente
        PreferenceItemRequest item = PreferenceItemRequest.builder()
                .title("Pedido #" + pedido.getId())
                .quantity(1)
                .unitPrice(BigDecimal.valueOf(pedido.getTotal()))
                .build();

        // Crear preferencia
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(List.of(item))
                .build();

        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);

        return ResponseEntity.ok(preference.getInitPoint());
    }


}
