package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.DetallePedido;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePedidoRepository extends BaseRepository<DetallePedido, Long>{

    List<DetallePedido> findAllByPedidoId(Long idPedido);

    // Productos más vendidos
    @Query("SELECT d.articulo.denominacion, SUM(d.cantidad) as totalVendidos " +
            "FROM DetallePedido d GROUP BY d.articulo.denominacion ORDER BY totalVendidos DESC")
    List<Object[]> findProductosMasVendidos();

    // Total de productos vendidos
    @Query("SELECT SUM(d.cantidad) FROM DetallePedido d")
    Long findTotalProductosVendidos();

    @Query("SELECT d.articulo.denominacion, SUM(d.cantidad), SUM(d.subTotal) " +
            "FROM DetallePedido d " +
            "WHERE TYPE(d.articulo) = ArticuloManufacturado " +
            "GROUP BY d.articulo.denominacion")
    List<Object[]> findVentasPorManufacturado();
}
