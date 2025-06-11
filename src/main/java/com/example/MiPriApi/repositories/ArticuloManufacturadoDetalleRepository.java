package com.example.MiPriApi.repositories;

<<<<<<< HEAD
=======
import com.example.MiPriApi.entities.ArticuloInsumo;
import com.example.MiPriApi.entities.ArticuloManufacturado;
>>>>>>> Dev
import com.example.MiPriApi.entities.ArticuloManufacturadoDetalle;
import org.springframework.stereotype.Repository;

import java.util.List;
<<<<<<< HEAD
=======
import java.util.Optional;
>>>>>>> Dev

@Repository
public interface ArticuloManufacturadoDetalleRepository extends BaseRepository<ArticuloManufacturadoDetalle, Long>{

    List<ArticuloManufacturadoDetalle> findAllByArticuloManufacturadoId(Long idArticuloManufacturado);

    List<ArticuloManufacturadoDetalle> findAllByArticuloInsumoId(Long idArticuloInsumo);
<<<<<<< HEAD
=======

    Optional<ArticuloManufacturadoDetalle> findByArticuloManufacturadoAndArticuloInsumo(
            ArticuloManufacturado articuloManufacturado,
            ArticuloInsumo articuloInsumo
    );

>>>>>>> Dev
}
