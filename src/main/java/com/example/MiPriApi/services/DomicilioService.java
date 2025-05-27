package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.Domicilio;
import com.example.MiPriApi.repositories.DomicilioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DomicilioService extends BaseService<Domicilio, Long>{


    public DomicilioService(DomicilioRepository domicilioRepository) {
        super(domicilioRepository);
    }

    @Autowired
    private DomicilioRepository domicilioRepository;

    @Transactional
    public List<Domicilio> listarPorLocalidad(Long idLocalidad) throws Exception{
        try{
            return domicilioRepository.findAllByLocalidadId(idLocalidad);
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional
    public List<Domicilio> listarPorCliente(Long idCliente) throws Exception{
        try{
            return domicilioRepository.findAllByclientesId(idCliente);
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
}
