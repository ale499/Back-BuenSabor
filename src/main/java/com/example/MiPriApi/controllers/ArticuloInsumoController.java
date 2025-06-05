package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.ArticuloInsumo;
import com.example.MiPriApi.services.ArticuloInsumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/articuloInsumo")
public class ArticuloInsumoController extends BaseController<ArticuloInsumo, Long> {

    public ArticuloInsumoController(ArticuloInsumoService service) {
        super(service);
    }

    @Autowired
    private ArticuloInsumoService articuloInsumoService;

    @RequestMapping("/categoria/{id}")
    public ResponseEntity<List<ArticuloInsumo>> listarPorCategoria(Long idCategoria) throws Exception {
        List<ArticuloInsumo> articuloInsumos = articuloInsumoService.listarPorCategoria(idCategoria);
        return ResponseEntity.ok(articuloInsumos);
    }

    @RequestMapping("/listar")
    public ResponseEntity<List<ArticuloInsumo>> listarTodos() {
        try {
            List<ArticuloInsumo> articuloInsumos = articuloInsumoService.findAll();
            return ResponseEntity.ok(articuloInsumos);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null); // Devuelve un error 500 en caso de excepción
        }
    }

}
