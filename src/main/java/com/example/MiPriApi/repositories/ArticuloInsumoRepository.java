package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.ArticuloInsumo;
import org.springframework.stereotype.Repository;

import java.util.List;
<<<<<<< HEAD
=======
import java.util.Optional;
>>>>>>> Dev

@Repository
public interface ArticuloInsumoRepository extends BaseRepository<ArticuloInsumo, Long>{

    List<ArticuloInsumo> findAllByCategoriaId(Long idCategoria);
<<<<<<< HEAD
    List<ArticuloInsumo> findByDenominacionContainingIgnoreCase(String denominacion);

=======

    List<ArticuloInsumo> findByDenominacionContainingIgnoreCase(String denominacion);

    List<ArticuloInsumo> findAll();

    Optional<ArticuloInsumo> findByDenominacion(String denominacion);

>>>>>>> Dev
}
