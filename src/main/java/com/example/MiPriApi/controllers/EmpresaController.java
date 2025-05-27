package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.Empresa;
import com.example.MiPriApi.services.BaseService;
import com.example.MiPriApi.services.EmpresaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/empresa")
public class EmpresaController extends BaseController<Empresa, Long>{
    public EmpresaController(EmpresaService service) {
        super(service);
    }
}
