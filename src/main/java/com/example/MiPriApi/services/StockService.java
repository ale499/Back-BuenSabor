package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.*;
import com.example.MiPriApi.repositories.ArticuloInsumoRepository;
import com.example.MiPriApi.repositories.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    @Autowired
    private ArticuloInsumoRepository articuloInsumoRepository;

    public void descontarStockIngredientes(Pedido pedido) throws Exception {
        List<DetallePedido> detalles = detallePedidoRepository.findAllByPedidoId(pedido.getId());
        for (DetallePedido detalle : detalles) {
            if (detalle.getArticulo() instanceof ArticuloManufacturado manufacturado) {
                for (ArticuloManufacturadoDetalle det : manufacturado.getDetalles()) {
                    ArticuloInsumo insumo = det.getArticuloInsumo();
                    double cantidadDescontar = det.getCantidad() * detalle.getCantidad();
                    if (insumo.getStockActual() < cantidadDescontar) {
                        throw new Exception("Stock insuficiente para " + insumo.getDenominacion());
                    }
                    insumo.setStockActual((int) (insumo.getStockActual() - cantidadDescontar));
                    articuloInsumoRepository.save(insumo);
                }
            } else if (detalle.getArticulo() instanceof ArticuloInsumo insumo) {
                if (insumo.getStockActual() < detalle.getCantidad()) {
                    throw new Exception("Stock insuficiente para " + insumo.getDenominacion());
                }
                insumo.setStockActual(insumo.getStockActual() - detalle.getCantidad());
                articuloInsumoRepository.save(insumo);
            }
        }
    }
}