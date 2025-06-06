package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.Provincia;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinciaRepository extends BaseRepository<Provincia, Long> {

    List<Provincia> findAllByPaisId(Long idPais);
}
