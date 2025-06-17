package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.Roles;
import com.example.MiPriApi.repositories.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleBBDDService {
    private RoleRepository roleRepository;

    public RoleBBDDService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public List<Roles> findAll() throws Exception {
        try {
            return roleRepository.findAll();
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Roles findById(String id) throws Exception {
        try {
            return roleRepository.getRolesByAuth0RoleId(id);
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Roles findByName(String name) throws Exception {
        try {
            return roleRepository.getRolesByName(name);
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Roles save(Roles entity) throws Exception {
        try {
            entity = roleRepository.save(entity);
            return entity;
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Roles update(Roles entity) throws Exception {
        try {
            if (entity.getId() == null) {
                throw new Exception("La entidad a modificar debe contener un Id.");
            }
            return roleRepository.save(entity);
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public boolean delete(String id) throws Exception {
        try {
            Optional<Roles> entityOptional = roleRepository.findByAuth0RoleId(id);
            if (entityOptional.isPresent()) {
                entityOptional
                        .get()
                        .setDeleted(true);
                roleRepository.save(entityOptional.get());
                return true;
            }else {
                throw new Exception("No existe la entidad");
            }
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    @Transactional
    public boolean deleteFisic(String id) throws Exception {
        try {
            Optional<Roles> entityOptional = roleRepository.findByAuth0RoleId(id);
            if (entityOptional.isPresent()) {
                roleRepository.delete(entityOptional.get());
                return true;
            }else {
                throw new Exception("No existe la entidad");
            }
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Page<Roles> findAll(Pageable pageable) throws Exception {
        try {
            Page<Roles> entities = roleRepository.findAll(pageable);
            return entities;
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
