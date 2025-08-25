package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.ClienteAuth0;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteAuth0Repository extends BaseRepository<ClienteAuth0, Long> {

    Optional<ClienteAuth0> findByAuth0Id(String auth0Id);

    Optional<ClienteAuth0> findByEmail(String email);



    boolean existsByAuth0Id(String auth0Id);
}
