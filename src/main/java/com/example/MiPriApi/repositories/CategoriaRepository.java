package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.Categoria;
import org.springframework.stereotype.Repository;

import java.util.List;
<<<<<<< HEAD
=======
import java.util.Optional;
>>>>>>> Dev

@Repository
public interface CategoriaRepository extends BaseRepository<Categoria, Long>{

<<<<<<< HEAD
    List<Categoria> findAllByCategoriaPadreId(Long idCategoriaPadre);

    List<Categoria> findAllBysucursalsId(Long idSucursal);
=======
    List<Categoria> findAllBysucursalesId(Long idSucursal);


    List<Categoria> findAllByCategoriaPadreId(Long idCategoriaPadre);

    Optional<Categoria> findByDenominacion(String denominacion);

    Optional<Categoria> findByDenominacionAndCategoriaPadre(String denominacion, Categoria categoriaPadre);

>>>>>>> Dev


}
