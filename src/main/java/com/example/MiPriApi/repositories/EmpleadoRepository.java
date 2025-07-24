package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.Empleado;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends BaseRepository<Empleado, Long> {
}
