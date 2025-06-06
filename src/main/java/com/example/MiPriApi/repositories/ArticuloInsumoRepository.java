package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.ArticuloInsumo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticuloInsumoRepository extends BaseRepository<ArticuloInsumo, Long>{

    List<ArticuloInsumo> findAllByCategoriaId(Long idCategoria);
    List<ArticuloInsumo> findByDenominacionContainingIgnoreCase(String denominacion);

}
