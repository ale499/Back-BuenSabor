package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.ArticuloManufacturado;
import org.springframework.stereotype.Repository;

import java.util.List;
<<<<<<< HEAD
=======
import java.util.Optional;
>>>>>>> Dev

@Repository
public interface ArticuloManufacturadoRepository extends BaseRepository<ArticuloManufacturado, Long>{

    List<ArticuloManufacturado> findAllByCategoriaId(Long idCategoria);
<<<<<<< HEAD
=======

    Optional<ArticuloManufacturado> findByDenominacion(String denominacion);
>>>>>>> Dev
}
