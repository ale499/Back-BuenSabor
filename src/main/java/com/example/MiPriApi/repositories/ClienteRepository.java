package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.Cliente;
import org.springframework.stereotype.Repository;
import java.util.Optional;

<<<<<<< HEAD
@Repository
public interface ClienteRepository extends BaseRepository<Cliente, Long> {
=======
import java.util.Optional;

@Repository
public interface ClienteRepository extends BaseRepository<Cliente, Long> {

>>>>>>> Dev
    Optional<Cliente> findByUsuario_Username(String username);
}
