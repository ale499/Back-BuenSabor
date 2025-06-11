package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.UnidadMedida;

<<<<<<< HEAD
public interface UnidadMedidaRepository extends BaseRepository<UnidadMedida, Long>{
=======
import java.util.Optional;

public interface UnidadMedidaRepository extends BaseRepository<UnidadMedida, Long>{
    Optional<UnidadMedida> findByDenominacion(String denominacion);
>>>>>>> Dev
}
