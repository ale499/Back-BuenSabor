package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.UnidadMedida;
import com.example.MiPriApi.services.BaseService;
import com.example.MiPriApi.services.UnidadMedidaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/unidadmedida")

public class UnidadMedidaController extends BaseController<UnidadMedida, Long>{
    public UnidadMedidaController(UnidadMedidaService service) {
        super(service);
    }
}
