package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.Roles;
import com.example.MiPriApi.entities.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository<Roles,Long> {
    Optional<Roles> findByAuth0RoleId(String name);
    Roles getRolesByAuth0RoleId (String id);
    Roles getRolesByName(String name);

}
