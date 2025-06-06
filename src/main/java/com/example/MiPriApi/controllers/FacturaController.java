package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.Factura;
import com.example.MiPriApi.services.BaseService;
import com.example.MiPriApi.services.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/factura")
public class FacturaController extends BaseController<Factura, Long>{
    public FacturaController(FacturaService service) {
        super(service);
    }

    @Autowired
    public FacturaService facturaService;

    @RequestMapping("/pedido/{idPedido}")
    public ResponseEntity<List<Factura>> listarPorPedido(@PathVariable Long idPedido) throws Exception{
        List<Factura> facturas = facturaService.listarPorPedido(idPedido);
        return ResponseEntity.ok(facturas);
    }
}
