package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.Roles;
import com.example.MiPriApi.entities.User;
import com.example.MiPriApi.repositories.RoleRepository;
import com.example.MiPriApi.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserBBDDService {
    protected UserRepository userRepository;

    public UserBBDDService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public List<User> findAll() throws Exception {
        try {
            return userRepository.findAll();
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<User> findAllActive() throws Exception {
        try {
            return userRepository.findAll().stream().filter(entity -> !entity.getDeleted()).collect(Collectors.toList());
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public User findById(String id) throws Exception {
        try {
            return userRepository.getUserByAuth0Id(id);
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public User save(User entity) throws Exception {
        try {
            entity = userRepository.save(entity);
            return entity;
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public User update(User entity) throws Exception {
        try {
            if (entity.getId() == null) {
                throw new Exception("La entidad a modificar debe contener un Id.");
            }
            return userRepository.save(entity);
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public boolean delete(String id) throws Exception {
        try {
            User entityOptional = userRepository.getUserByAuth0Id(id);

            entityOptional.setDeleted(true);
            userRepository.save(entityOptional);
            return true;

        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public boolean deleteFisic(String id) throws Exception {
        try {
            User entityOptional = userRepository.getUserByAuth0Id(id);
            userRepository.delete(entityOptional);
            return true;
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Page<User> findAll(Pageable pageable) throws Exception {
        try {
            Page<User> entities = userRepository.findAll(pageable);
            return entities;
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
