package com.example.MiPriApi.repositories;
import com.example.MiPriApi.entities.Localidad;
import org.springframework.stereotype.Repository;

import java.util.List;
<<<<<<< HEAD
=======
import java.util.Optional;
>>>>>>> Dev

@Repository
public interface LocalidadRepository extends BaseRepository<Localidad, Long>{

    List<Localidad> findAllByProvinciaId(Long idProvincia);
<<<<<<< HEAD
=======

    Optional<Localidad> findByNombre(String nombre);
>>>>>>> Dev
}
