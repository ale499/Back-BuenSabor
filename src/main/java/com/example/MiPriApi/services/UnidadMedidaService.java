package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.UnidadMedida;
import com.example.MiPriApi.repositories.BaseRepository;
import com.example.MiPriApi.repositories.UnidadMedidaRepository;
import org.springframework.stereotype.Service;

@Service
public class UnidadMedidaService extends BaseService<UnidadMedida,Long>{
    public UnidadMedidaService(UnidadMedidaRepository unidadMedidaRepository) {
        super(unidadMedidaRepository);
    }
}
