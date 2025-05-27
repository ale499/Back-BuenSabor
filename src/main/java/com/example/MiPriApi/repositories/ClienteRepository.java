package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.Cliente;

import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends BaseRepository<Cliente, Long> {
}
