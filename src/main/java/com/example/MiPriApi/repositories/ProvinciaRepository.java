package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.Provincia;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProvinciaRepository extends BaseRepository<Provincia, Long> {

    List<Provincia> findAllByPaisId(Long idPais);

    Optional<Provincia> findByNombre(String nombre);
}
