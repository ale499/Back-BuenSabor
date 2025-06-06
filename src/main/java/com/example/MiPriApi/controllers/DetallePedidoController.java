package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.DetallePedido;

import com.example.MiPriApi.services.DetallePedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/detallePedido")
public class DetallePedidoController extends BaseController<DetallePedido, Long>{
    public DetallePedidoController(DetallePedidoService service) {
        super(service);
    }

    @Autowired
    private DetallePedidoService detallePedidoService;

    @RequestMapping("/pedido/{id}")
    public ResponseEntity <List<DetallePedido>> listarPorPedido(@PathVariable Long idPedido) throws Exception{
        List<DetallePedido>detallePedidos = detallePedidoService.listarPorPedido(idPedido);
        return ResponseEntity.ok(detallePedidos);
    }
}
