package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.DetallePedido;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePedidoRepository extends BaseRepository<DetallePedido, Long>{

    List<DetallePedido> findAllByPedidoId(Long idPedido);
}
