package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.Usuario;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long>{

    Optional<Usuario> findByUsername(String username);

}
