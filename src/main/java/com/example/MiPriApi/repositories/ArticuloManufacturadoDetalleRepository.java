package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.ArticuloManufacturadoDetalle;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticuloManufacturadoDetalleRepository extends BaseRepository<ArticuloManufacturadoDetalle, Long>{

    List<ArticuloManufacturadoDetalle> findAllByArticuloManufacturadoId(Long idArticuloManufacturado);

    List<ArticuloManufacturadoDetalle> findAllByArticuloInsumoId(Long idArticuloInsumo);
}
