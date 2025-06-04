package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.ArticuloInsumo;
import com.example.MiPriApi.services.ArticuloInsumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/articuloInsumo")
public class ArticuloInsumoController extends BaseController<ArticuloInsumo, Long>{

    public ArticuloInsumoController(ArticuloInsumoService service) {
        super(service);
    }

    @Autowired
    private ArticuloInsumoService articuloInsumoService;

    @RequestMapping("/categoria/{id}")
    public ResponseEntity<List<ArticuloInsumo>> listarPorCategoria(Long idCategoria) throws Exception{
        List<ArticuloInsumo> articuloInsumos = articuloInsumoService.listarPorCategoria(idCategoria);
        return ResponseEntity.ok(articuloInsumos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ArticuloInsumo>> buscarPorDenominacion(@RequestParam String denominacion) throws Exception {
        List<ArticuloInsumo> resultado = articuloInsumoService.buscarPorDenominacion(denominacion);
        return ResponseEntity.ok(resultado);
    }

}
