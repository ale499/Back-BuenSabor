package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.*;
import com.example.MiPriApi.entities.enums.TipoEnvio;
import com.example.MiPriApi.repositories.DetallePedidoRepository;
import com.example.MiPriApi.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TiempoEstimadoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;

    public int calcularTiempoEstimado(Pedido pedido) {
        int maxTiempoArticulos = 0;
        List<DetallePedido> detalles = detallePedidoRepository.findAllByPedidoId(pedido.getId());
        for (DetallePedido detalle : detalles) {
            if (detalle.getArticulo() instanceof ArticuloManufacturado manufacturado) {
                if (manufacturado.getTiempoEstimadoMinutos() != null &&
                        manufacturado.getTiempoEstimadoMinutos() > maxTiempoArticulos) {
                    maxTiempoArticulos = manufacturado.getTiempoEstimadoMinutos();
                }
            }
        }
        int maxTiempoCocina = pedidoRepository.maxTiempoEstimadoEnCocina();
        int tiempoDelivery = pedido.getTipoEnvio() == TipoEnvio.TAKEAWAY ? 10 : 0;
        return maxTiempoArticulos + maxTiempoCocina + tiempoDelivery;
    }
}