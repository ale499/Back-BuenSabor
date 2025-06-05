package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.Pedido;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends BaseRepository<Pedido, Long> {

    List<Pedido> findAllByClienteId(Long idCliente);
    List<Pedido> findAllByEmpleadoId(Long idEmpleado);
    List<Pedido> findAllBySucursalId(Long idSucursal);

}

