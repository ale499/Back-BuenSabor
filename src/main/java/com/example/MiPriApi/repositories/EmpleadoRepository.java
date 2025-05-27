package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.Empleado;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends BaseRepository<Empleado, Long> {

    List<Empleado> findAllBySucursalId(Long idSucursal);
}
