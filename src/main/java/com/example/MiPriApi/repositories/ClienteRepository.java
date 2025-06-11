package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.Cliente;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.Optional;

@Repository
public interface ClienteRepository extends BaseRepository<Cliente, Long> {

    Optional<Cliente> findByUsuario_Username(String username);



}