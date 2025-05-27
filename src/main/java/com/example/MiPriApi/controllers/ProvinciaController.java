package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.Provincia;
import com.example.MiPriApi.services.BaseService;
import com.example.MiPriApi.services.ProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/provincia")
public class ProvinciaController extends BaseController<Provincia, Long>{

    public ProvinciaController(ProvinciaService service) {
        super(service);
    }

    @Autowired
    private ProvinciaService provinciaService;

    @RequestMapping("/pais/{id}")
    public ResponseEntity<List<Provincia>> listarPorPais(@PathVariable Long idPais) throws Exception{
        List<Provincia> provincias = provinciaService.listarPorPais(idPais);
        return ResponseEntity.ok(provincias);
    }
}
