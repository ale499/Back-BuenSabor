package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.ArticuloManufacturado;
import com.example.MiPriApi.services.ArticuloManufacturadoService;
import com.example.MiPriApi.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ArticuloManufacturado")
public class ArticuloManufacturadoController extends BaseController<ArticuloManufacturado, Long>{
    public ArticuloManufacturadoController(ArticuloManufacturadoService service) {
        super(service);
    }

    @Autowired
    private ArticuloManufacturadoService articuloManufacturadoService;

    @RequestMapping("/categoria/{id}")
    public ResponseEntity<List<ArticuloManufacturado>> listarPorCategoria(Long idCategoria) throws Exception{
        List<ArticuloManufacturado> articuloManufacturados = articuloManufacturadoService.listarPorCategoria(idCategoria);
        return ResponseEntity.ok(articuloManufacturados);
    }
}
