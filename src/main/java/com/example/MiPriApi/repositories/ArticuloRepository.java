package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticuloRepository extends  BaseRepository<Articulo, Long> {
}
