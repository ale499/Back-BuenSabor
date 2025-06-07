package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.Domicilio;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DomicilioRepository extends BaseRepository<Domicilio, Long> {

    List<Domicilio> findAllByLocalidadId(Long idLocalidad);

    List<Domicilio> findAllByclientesId(Long idCliente);

    Optional<Domicilio> findByCalleAndNumeroAndPisoAndNroDpto(String calle, Integer numero, Integer piso, Integer nroDpto);

}
