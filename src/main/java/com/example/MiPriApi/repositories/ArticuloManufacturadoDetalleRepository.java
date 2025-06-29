package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.ArticuloInsumo;
import com.example.MiPriApi.entities.ArticuloManufacturado;
import com.example.MiPriApi.entities.ArticuloManufacturadoDetalle;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticuloManufacturadoDetalleRepository extends BaseRepository<ArticuloManufacturadoDetalle, Long>{

    List<ArticuloManufacturadoDetalle> findAllByArticuloManufacturadoId(Long idArticuloManufacturado);

    List<ArticuloManufacturadoDetalle> findAllByArticuloInsumoId(Long idArticuloInsumo);

    Optional<ArticuloManufacturadoDetalle> findByArticuloManufacturadoAndArticuloInsumo(
            ArticuloManufacturado articuloManufacturado,
            ArticuloInsumo articuloInsumo
    );

    void deleteAllByArticuloManufacturadoId(Long articuloManufacturadoId);

}