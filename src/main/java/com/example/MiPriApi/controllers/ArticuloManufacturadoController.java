package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.ArticuloManufacturado;
import com.example.MiPriApi.services.ArticuloManufacturadoService;
import com.example.MiPriApi.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
<<<<<<< HEAD
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/articulosManufacturados")
=======

@RestController
@RequestMapping("/articulosManufacturados")
@CrossOrigin(origins = "http://localhost:5173")
>>>>>>> Dev
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
        articuloExistente.setDenominacion(articuloManufacturado.getDenominacion());
        articuloExistente.setPrecioVenta(articuloManufacturado.getPrecioVenta());
        articuloExistente.setCategoria(articuloManufacturado.getCategoria());
        articuloExistente.setUnidadMedida(articuloManufacturado.getUnidadMedida());
        articuloExistente.setTiempoPreparacion(articuloManufacturado.getTiempoPreparacion());
<<<<<<< HEAD
        // ...agrega aquí cualquier otro campo que quieras actualizar
=======
>>>>>>> Dev
        return ResponseEntity.ok(service.actualizar(articuloExistente));
    }
}
