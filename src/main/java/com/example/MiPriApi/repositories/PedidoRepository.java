package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.Pedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends BaseRepository<Pedido, Long> {

    List<Pedido> findAllByClienteId(Long idCliente);
    List<Pedido> findAllByEmpleadoId(Long idEmpleado);
    List<Pedido> findAllBySucursalId(Long idSucursal);

    @Query("SELECT COALESCE(MAX(am.tiempoEstimadoMinutos), 0) " +
            "FROM Pedido p " +
            "JOIN p.detalles d " +
            "JOIN ArticuloManufacturado am ON d.articulo = am " +
            "WHERE p.estado = com.example.MiPriApi.entities.enums.Estado.PREPARACION")
    int maxTiempoEstimadoEnCocina();

<<<<<<< HEAD
=======
    @Query("SELECT MAX(p.numeroPedido) FROM Pedido p")
    Integer findMaxNumeroPedido();
>>>>>>> Dev
}

