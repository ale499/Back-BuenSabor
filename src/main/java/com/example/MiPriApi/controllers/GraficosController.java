package com.example.MiPriApi.controllers;
import com.example.MiPriApi.entities.enums.Estado;
import com.example.MiPriApi.repositories.DetallePedidoRepository;
import com.example.MiPriApi.repositories.PedidoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/grafico")
public class GraficosController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @GetMapping("/ventas/total")
    public Double getTotalVentas() {
        // Asume que el estado CONFIRMADO es un enum o string
        return pedidoRepository.sumTotalByEstado(Estado.PENDIENTE);
    }

    @GetMapping("/pedidos/total")
    public Long getTotalPedidos() {
        return pedidoRepository.count();
    }

    @GetMapping("/productos/mas-vendidos")
    public List<Map<String, Object>> getProductosMasVendidos() {
        List<Object[]> results = detallePedidoRepository.findProductosMasVendidos();
        List<Map<String, Object>> response = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("producto", row[0]);
            item.put("cantidad", row[1]);
            response.add(item);
        }
        return response;
    }

    @GetMapping("/productos/total-vendidos")
    public Long getTotalProductosVendidos() {
        return detallePedidoRepository.findTotalProductosVendidos();
    }


}
