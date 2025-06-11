package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.ArticuloManufacturado;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticuloManufacturadoRepository extends BaseRepository<ArticuloManufacturado, Long>{

    List<ArticuloManufacturado> findAllByCategoriaId(Long idCategoria);

    Optional<ArticuloManufacturado> findByDenominacion(String denominacion);

    List<ArticuloManufacturado> findByDenominacionContainingIgnoreCase(String denominacion);// Método para buscar por denominación, ignorando mayúsculas y minúsculas

}