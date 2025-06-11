package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.Pais;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
@Repository
public interface PaisRepository extends BaseRepository<Pais, Long> {
=======
import java.util.Optional;

@Repository
public interface PaisRepository extends BaseRepository<Pais, Long> {

    Optional<Pais> findByNombre(String nombre);
>>>>>>> Dev
}
