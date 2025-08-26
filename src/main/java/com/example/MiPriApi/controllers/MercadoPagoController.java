package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.ArticuloManufacturado;
import com.example.MiPriApi.entities.DTO.ItemDTO;
import com.example.MiPriApi.entities.DTO.PedidoRequestDTO;
import com.example.MiPriApi.entities.DetallePedido;
import com.example.MiPriApi.entities.Pedido;
import com.example.MiPriApi.entities.enums.TipoEnvio;
import com.example.MiPriApi.services.MercadoPagoService;

import com.example.MiPriApi.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pagos")
public class MercadoPagoController {

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public String crearPago(@RequestBody List<ItemDTO> items) {
        try {
            return mercadoPagoService.procesarPago(items);
        } catch (Exception e) {
            return "Error al procesar el pago";
        }
    }

    @PostMapping("/prueba-pago")
    public Map<String, Object> probarGuardarPedidoConPago(@RequestBody PedidoRequestDTO pedidoRequest) throws Exception {
        return pedidoService.guardarPedidoConPagoDTO(pedidoRequest);
    }
}