package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.UnidadMedida;
import com.example.MiPriApi.services.BaseService;
import com.example.MiPriApi.services.UnidadMedidaService;

public class UnidadMedidaController extends BaseController<UnidadMedida, Long>{
    public UnidadMedidaController(UnidadMedidaService service) {
        super(service);
    }
}
