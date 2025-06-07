package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.Categoria;
import com.example.MiPriApi.entities.Sucursal;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SucursalRepository extends BaseRepository<Sucursal, Long>{

    Optional<Sucursal> findByNombre(String nombre);



}
