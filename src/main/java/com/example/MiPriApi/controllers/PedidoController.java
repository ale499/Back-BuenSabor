package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.Pedido;
import com.example.MiPriApi.services.BaseService;
import com.example.MiPriApi.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
