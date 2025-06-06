package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.Provincia;

import com.example.MiPriApi.repositories.ProvinciaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProvinciaService extends BaseService<Provincia, Long>{


    public ProvinciaService(ProvinciaRepository provinciaRepository) {
        super(provinciaRepository);
    }

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Transactional
    public List<Provincia> listarPorPais(Long idPais) throws Exception{
        try {
            return provinciaRepository.findAllByPaisId(idPais);
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
}
