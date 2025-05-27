package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.Localidad;
import com.example.MiPriApi.services.BaseService;
import com.example.MiPriApi.services.LocalidadService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/localidad")
public class LocalidadController extends BaseController<Localidad, Long>{

    public LocalidadController(LocalidadService service) {
        super(service);
    }

    @Autowired
    private LocalidadService localidadService;

    @RequestMapping("/provincia/{idProvincia}")
    private ResponseEntity<List<Localidad>>listarPorProvincia(@PathVariable Long idProvincia) throws Exception{
        List<Localidad> localidads = localidadService.listarPorProvincia(idProvincia);
        return ResponseEntity.ok(localidads);
    }
}
