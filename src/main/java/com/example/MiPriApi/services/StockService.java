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
        System.out.println("Entrando a descontarStockIngredientes para pedido: " + pedido.getId());
        System.out.println("Cantidad de detalles: " + (detalles != null ? detalles.size() : "null"));
        for (DetallePedido detalle : detalles) {
            System.out.println("DetallePedido ID: " + detalle.getId() + ", Articulo: " + detalle.getArticulo());
            Articulo articulo = detalle.getArticulo();
            if (articulo == null) {
                throw new Exception("DetallePedido with id " + detalle.getId() + " has no articulo assigned.");
            }
            if (articulo instanceof ArticuloManufacturado manufacturado) {
                List<ArticuloManufacturadoDetalle> detallesManu =
                        articuloManufacturadoDetalleRepository.findAllByArticuloManufacturadoId(manufacturado.getId());
                Set<ArticuloManufacturadoDetalle> detallesActuales = manufacturado.getDetalles();
                if (detallesActuales == null) {
                    detallesActuales = new java.util.HashSet<>();
                    manufacturado.setDetalles(detallesActuales);
                }
                detallesActuales.clear();
                detallesActuales.addAll(detallesManu);
                System.out.println("Es un ArticuloManufacturado, detalles: " + (manufacturado.getDetalles() != null ? manufacturado.getDetalles().size() : "null"));
                if (manufacturado.getDetalles() == null) continue;
                for (ArticuloManufacturadoDetalle det : manufacturado.getDetalles()) {
                    ArticuloInsumo insumo = det.getArticuloInsumo();
                    double cantidadDescontar = det.getCantidad() * detalle.getCantidad();
                    System.out.println("Procesando insumo (manufacturado): " + insumo.getDenominacion() +
                            " (ID: " + insumo.getId() + "), stock actual: " + insumo.getStockActual() +
                            ", cantidad a descontar: " + cantidadDescontar);
                    if (insumo.getStockActual() < cantidadDescontar) {
                        throw new Exception("Stock insuficiente para " + insumo.getDenominacion());
                    }
                    insumo.setStockActual((int) (insumo.getStockActual() - cantidadDescontar));
                    articuloInsumoRepository.save(insumo);
                }
            } else if (articulo instanceof ArticuloInsumo insumo) {
                System.out.println("Procesando insumo (simple): " + insumo.getDenominacion() +
                        " (ID: " + insumo.getId() + "), stock actual: " + insumo.getStockActual() +
                        ", cantidad a descontar: " + detalle.getCantidad());
                if (insumo.getStockActual() < detalle.getCantidad()) {
                    throw new Exception("Stock insuficiente para " + insumo.getDenominacion());
                }
                insumo.setStockActual(insumo.getStockActual() - detalle.getCantidad());
                articuloInsumoRepository.save(insumo);
            } else {
                throw new Exception("Unknown articulo type in DetallePedido with id " + detalle.getId());
            }
        }
    }
}