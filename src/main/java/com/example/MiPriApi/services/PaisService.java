package com.example.MiPriApi.services;


import com.example.MiPriApi.entities.Pais;
import com.example.MiPriApi.repositories.BaseRepository;
import com.example.MiPriApi.repositories.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PaisService extends BaseService<Pais, Long>{


    public PaisService(PaisRepository paisRepository) {
        super(paisRepository);
    }
}
