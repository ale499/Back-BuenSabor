package com.example.MiPriApi.repositories;
import com.example.MiPriApi.entities.Localidad;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocalidadRepository extends BaseRepository<Localidad, Long>{

    List<Localidad> findAllByProvinciaId(Long idProvincia);

    Optional<Localidad> findByNombre(String nombre);
}
