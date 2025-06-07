package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.Producto;
import com.example.MiPriApi.services.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.MiPriApi.dto.ProductoDTO;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController extends BaseController<Producto, Long> {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        super(productoService);
        this.productoService = productoService;
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoDTO>> buscarPorNombre(@RequestParam String nombre) {
        List<ProductoDTO> productos = productoService.buscarPorNombreDTO(nombre);
        return ResponseEntity.ok(productos);
    }
}