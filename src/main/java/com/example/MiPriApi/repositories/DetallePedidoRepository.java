package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.DetallePedido;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
            "AND d.pedido.fechaPedido BETWEEN :inicio AND :fin " +
            "GROUP BY d.articulo.denominacion")
    List<Object[]> findVentasPorManufacturadoEnPeriodo(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);

    @Query("SELECT d.articulo.denominacion, SUM(d.cantidad) " +
            "FROM DetallePedido d " +
            "WHERE d.pedido.fechaPedido BETWEEN :inicio AND :fin " +
            "GROUP BY d.articulo.denominacion " +
            "ORDER BY SUM(d.cantidad) DESC")
    List<Object[]> findProductosMasVendidosEnPeriodo(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);
}
