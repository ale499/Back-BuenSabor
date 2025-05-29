package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.ArticuloManufacturado;
import com.example.MiPriApi.services.ArticuloManufacturadoService;
import com.example.MiPriApi.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articulosManufacturados")
public class ArticuloManufacturadoController extends BaseController<ArticuloManufacturado, Long> {
    public ArticuloManufacturadoController(ArticuloManufacturadoService service) {
        super(service);
    }

    @PostMapping("/alta")
    public ResponseEntity<ArticuloManufacturado> crearArticuloManufacturado(@RequestBody ArticuloManufacturado articuloManufacturado) throws Exception {
        return ResponseEntity.ok(service.crear(articuloManufacturado));
    }

    @DeleteMapping("/baja/{id}")
    public void eliminarArticuloManufacturado(@PathVariable Long id) throws Exception {
        service.eliminar(id);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<ArticuloManufacturado> modificarArticuloManufacturado(@PathVariable Long id, @RequestBody ArticuloManufacturado articuloManufacturado) throws Exception {
        ArticuloManufacturado articuloExistente = service.buscarPorId(id).orElseThrow(() -> new Exception("Artículo manufacturado no encontrado"));
        articuloExistente.setDescripcion(articuloManufacturado.getDescripcion());
        articuloExistente.setTiempoEstimadoMinutos(articuloManufacturado.getTiempoEstimadoMinutos());
        articuloExistente.setPreparacion(articuloManufacturado.getPreparacion());
        return ResponseEntity.ok(service.actualizar(articuloExistente));
    }
}
