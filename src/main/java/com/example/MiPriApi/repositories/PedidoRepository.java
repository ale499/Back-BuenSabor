package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.ClienteAuth0;
import com.example.MiPriApi.entities.Pedido;
import com.example.MiPriApi.entities.enums.Estado;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends BaseRepository<Pedido, Long> {

    List<Pedido> findAllByClienteAuth0_Auth0Id(String auth0Id);
    List<Pedido> findAllByEmpleadoId(Long idEmpleado);
    List<Pedido> findAllBySucursalId(Long idSucursal);

    //permite buscar pedidos por email de auth0 del cliente
    List<Pedido> findAllByClienteAuth0_Email(String email);


    @Query("SELECT COALESCE(MAX(am.tiempoEstimadoMinutos), 0) " +
            "FROM Pedido p " +
            "JOIN p.detalles d " +
            "JOIN ArticuloManufacturado am ON d.articulo = am " +
            "WHERE p.estado = com.example.MiPriApi.entities.enums.Estado.PREPARACION")
    int maxTiempoEstimadoEnCocina();

    @Query("SELECT MAX(p.numeroPedido) FROM Pedido p")
    Integer findMaxNumeroPedido();

    // Metodo para sumar el total de pedidos por estado
    @Query("SELECT SUM(p.total) FROM Pedido p WHERE p.estado = :estado")
    Double sumTotalByEstado(Estado estado);

    @Query("SELECT p.clienteAuth0.email, COUNT(p) " +
            "FROM Pedido p " +
            "WHERE p.fechaPedido BETWEEN :inicio AND :fin " +
            "GROUP BY p.clienteAuth0.email " +
            "ORDER BY COUNT(p) DESC")
    List<Object[]> countPedidosPorClienteAuth0EmailEnPeriodo(@Param("inicio") java.time.LocalDate inicio, @Param("fin") java.time.LocalDate fin);

}
