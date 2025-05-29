package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorRepository extends BaseRepository<Administrador, Long> {

}
