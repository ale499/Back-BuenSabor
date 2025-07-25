package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.*;
import com.example.MiPriApi.repositories.ArticuloInsumoRepository;
import com.example.MiPriApi.repositories.ArticuloManufacturadoDetalleRepository;
import com.example.MiPriApi.repositories.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

@Service
public class StockService {

    private static final Logger log = LoggerFactory.getLogger(StockService.class);


    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    @Autowired
    private ArticuloInsumoRepository articuloInsumoRepository;

    @Autowired
    private ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository;

    public void descontarStockIngredientes(Pedido pedido) throws Exception {
        List<DetallePedido> detalles = detallePedidoRepository.findAllByPedidoId(pedido.getId());
        for (DetallePedido detalle : detalles) {
            Articulo articulo = detalle.getArticulo();
            if (articulo instanceof ArticuloManufacturado manufacturado) {
                for (ArticuloManufacturadoDetalle det : manufacturado.getDetalles()) {
                    ArticuloInsumo insumo = det.getArticuloInsumo();
                    int cantidadDescontar = det.getCantidad() * detalle.getCantidad();
                    if (insumo.getStockPendiente() < cantidadDescontar) {
                        throw new Exception("Stock pendiente insuficiente para " + insumo.getDenominacion());
                    }
                    insumo.setStockActual(insumo.getStockActual() - cantidadDescontar);
                    insumo.setStockPendiente(insumo.getStockPendiente() - cantidadDescontar);
                    articuloInsumoRepository.save(insumo);
                }
            } else if (articulo instanceof ArticuloInsumo insumo) {
                if (insumo.getStockPendiente() < detalle.getCantidad()) {
                    throw new Exception("Stock pendiente insuficiente para " + insumo.getDenominacion());
                }
                insumo.setStockActual(insumo.getStockActual() - detalle.getCantidad());
                insumo.setStockPendiente(insumo.getStockPendiente() - detalle.getCantidad());
                articuloInsumoRepository.save(insumo);
            }
        }
    }

    public void revertirStockPendiente(Pedido pedido) {
        List<DetallePedido> detalles = pedido.getDetalles();
        for (DetallePedido detalle : detalles) {
            Articulo articulo = detalle.getArticulo();
            if (articulo instanceof ArticuloInsumo insumo) {
                insumo.setStockPendiente(insumo.getStockPendiente() - detalle.getCantidad());
                articuloInsumoRepository.save(insumo);
            } else if (articulo instanceof ArticuloManufacturado manufacturado) {
                for (ArticuloManufacturadoDetalle det : manufacturado.getDetalles()) {
                    ArticuloInsumo insumo = det.getArticuloInsumo();
                    int cantidadTotal = det.getCantidad() * detalle.getCantidad();
                    insumo.setStockPendiente(insumo.getStockPendiente() - cantidadTotal);
                    articuloInsumoRepository.save(insumo);
                }
            }
        }
    }
}