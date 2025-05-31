package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.Promocion;
import com.example.MiPriApi.repositories.PromocionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromocionService extends BaseService<Promocion, Long>{
    public PromocionService(PromocionRepository promocionRepository) {
        super(promocionRepository);
    }

    @Autowired
    private PromocionRepository promocionRepository;

    @Transactional
    public List<Promocion> listarPorSucursal(Long sucursalId) throws Exception{
        try {
            return promocionRepository.findAllBySucursalesId(sucursalId);
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
}
