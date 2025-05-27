package com.example.MiPriApi.controllers;


import com.example.MiPriApi.entities.Pais;
import com.example.MiPriApi.services.BaseService;
import com.example.MiPriApi.services.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pais")
public class PaisController extends BaseController<Pais, Long>{


    public PaisController(PaisService service) {
        super(service);
    }
}
