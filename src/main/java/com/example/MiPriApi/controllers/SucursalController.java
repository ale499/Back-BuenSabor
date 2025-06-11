package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.Categoria;
import com.example.MiPriApi.entities.Sucursal;
import com.example.MiPriApi.services.BaseService;
import com.example.MiPriApi.services.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sucursal")
public class SucursalController extends BaseController<Sucursal, Long>{

    public SucursalController(SucursalService service) {
        super(service);
    }

<<<<<<< HEAD
    @Autowired
    private SucursalService sucursalService;

    @RequestMapping("/empresa/{idEmpresa}")
    public ResponseEntity<List<Sucursal>> listarPorEmpresa(@PathVariable Long idEmpresa) throws Exception{
        List<Sucursal> sucursals = sucursalService.listarPorEmpresa(idEmpresa);
        return ResponseEntity.ok(sucursals);
    }
=======

>>>>>>> Dev

}
