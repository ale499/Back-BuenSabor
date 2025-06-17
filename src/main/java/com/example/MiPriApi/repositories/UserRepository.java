package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.User;

import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends BaseRepository<User,Long> {

    User getUserByAuth0Id (String id);

}