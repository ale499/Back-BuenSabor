package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.ArticuloManufacturado;
import com.example.MiPriApi.entities.DTO.ArticuloManufacturadoDetalleDTO;
import com.example.MiPriApi.services.Mappers.ArticuloManufacturadoDetalleMapper;
import com.example.MiPriApi.services.ArticuloManufacturadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articulosManufacturados")
@CrossOrigin(origins = "http://localhost:5173")
public class ArticuloManufacturadoController extends BaseController<ArticuloManufacturado, Long> {
    public ArticuloManufacturadoController(ArticuloManufacturadoService service) {
        super(service);
    }

    @Autowired
    private ArticuloManufacturadoService service;

    @Autowired
    private ArticuloManufacturadoDetalleMapper mapper;

    @PostMapping("/alta")
    public ResponseEntity<ArticuloManufacturado> crearArticuloManufacturado(@RequestBody ArticuloManufacturado articuloManufacturado) throws Exception {
        return ResponseEntity.ok(service.crear(articuloManufacturado));
    }

    @DeleteMapping("/baja/{id}")
    public ResponseEntity<String> eliminarArticuloManufacturado(@PathVariable Long id) {
        try {
            service.eliminar(id);
            return ResponseEntity.ok("Artículo manufacturado eliminado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar el artículo manufacturado: " + e.getMessage());
        }
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<ArticuloManufacturado> modificarArticuloManufacturado(
            @PathVariable Long id,
            @RequestBody ArticuloManufacturado articuloManufacturado) throws Exception {
        ArticuloManufacturado actualizado = service.modificarConDetalles(id, articuloManufacturado);
        return ResponseEntity.ok(actualizado);
    }

    @GetMapping("/buscarPorDenominacion")
    public ResponseEntity<List<ArticuloManufacturado>> buscarPorDenominacion(@RequestParam String denominacion) throws Exception {
        List<ArticuloManufacturado> articulos = service.buscarPorDenominacion(denominacion);
        return ResponseEntity.ok(articulos);
    }
}