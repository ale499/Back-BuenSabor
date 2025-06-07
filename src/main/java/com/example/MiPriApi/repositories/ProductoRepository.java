package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.Producto;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends BaseRepository<Producto, Long> {
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
}