package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.Categoria;
import com.example.MiPriApi.entities.Sucursal;
import org.springframework.stereotype.Repository;

import java.util.List;
<<<<<<< HEAD
=======
import java.util.Optional;
>>>>>>> Dev

@Repository
public interface SucursalRepository extends BaseRepository<Sucursal, Long>{

<<<<<<< HEAD
    List<Sucursal> findAllByEmpresaId(Long idEmpresa);
=======
    Optional<Sucursal> findByNombre(String nombre);

>>>>>>> Dev


}
