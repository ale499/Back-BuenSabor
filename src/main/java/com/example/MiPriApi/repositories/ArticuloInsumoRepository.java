package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.ArticuloInsumo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticuloInsumoRepository extends BaseRepository<ArticuloInsumo, Long>{

    List<ArticuloInsumo> findAllByCategoriaId(Long idCategoria);

    List<ArticuloInsumo> findAll();

    Optional<ArticuloInsumo> findByDenominacion(String denominacion);
}
