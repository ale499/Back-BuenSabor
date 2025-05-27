package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.Base;
import com.example.MiPriApi.entities.Imagen;
import com.example.MiPriApi.services.BaseService;
import com.example.MiPriApi.services.ImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/imagenes")
public class ImagenController extends BaseController<Imagen, Long> {


    public ImagenController(ImagenService service) {
        super(service);
    }
}
