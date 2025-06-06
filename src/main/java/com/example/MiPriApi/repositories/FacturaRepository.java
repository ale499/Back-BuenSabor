package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.Factura;

import java.util.List;

public interface FacturaRepository extends BaseRepository<Factura, Long>{

    List<Factura> findAllByPedidoId(Long idPedido);
}
