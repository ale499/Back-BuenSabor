package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.Categoria;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends BaseRepository<Categoria, Long>{

    List<Categoria> findAllByCategoriaPadreId(Long idCategoriaPadre);

    List<Categoria> findAllBysucursalsId(Long idSucursal);

    Optional<Categoria> findByDenominacion(String denominacion);

    Optional<Categoria> findByDenominacionAndCategoriaPadre(String denominacion, Categoria categoriaPadre);


}
