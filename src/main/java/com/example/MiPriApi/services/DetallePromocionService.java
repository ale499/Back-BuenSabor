package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.DetallePromocion;
import com.example.MiPriApi.repositories.BaseRepository;
import com.example.MiPriApi.repositories.DetallePromocionRepository;
import org.springframework.stereotype.Service;

@Service
public class DetallePromocionService extends BaseService<DetallePromocion, Long>{
    public DetallePromocionService(DetallePromocionRepository detallePromocionRepository) {
        super(detallePromocionRepository);
    }
}
