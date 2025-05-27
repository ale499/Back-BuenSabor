package com.example.MiPriApi.services;


import com.example.MiPriApi.entities.Localidad;
import com.example.MiPriApi.repositories.LocalidadRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LocalidadService extends BaseService<Localidad, Long>{


    public LocalidadService(LocalidadRepository localidadRepository) {
        super(localidadRepository);
    }

    @Autowired
    private LocalidadRepository localidadRepository;

    @Transactional
    public List<Localidad> listarPorProvincia(Long idProvincia) throws Exception{
        try {
            return localidadRepository.findAllByProvinciaId(idProvincia);
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
}
