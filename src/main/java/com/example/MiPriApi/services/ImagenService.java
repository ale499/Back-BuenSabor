package com.example.MiPriApi.services;


import com.example.MiPriApi.entities.Imagen;
import com.example.MiPriApi.repositories.BaseRepository;
import com.example.MiPriApi.repositories.ImagenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagenService extends BaseService<Imagen, Long>{

    public ImagenService(ImagenRepository imagenRepository) {
        super(imagenRepository);
    }
}
