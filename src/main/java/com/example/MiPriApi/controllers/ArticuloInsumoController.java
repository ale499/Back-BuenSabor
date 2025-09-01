package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.ArticuloInsumo;

import com.example.MiPriApi.services.CategoriaService;
import com.example.MiPriApi.services.ArticuloInsumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.example.MiPriApi.entities.DTO.ArticuloInsumoDTO;
import com.example.MiPriApi.entities.DTO.CategoriaDTO;
import com.example.MiPriApi.services.ArticuloInsumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articuloInsumo")
public class ArticuloInsumoController extends BaseController<ArticuloInsumo, Long> {

    public ArticuloInsumoController(ArticuloInsumoService service) {
        super(service);
    }

    @Autowired
    private ArticuloInsumoService articuloInsumoService;
    @Autowired
    private CategoriaService categoriaService;

    @RequestMapping("/categoria/{id}")

    public ResponseEntity<List<ArticuloInsumo>> listarPorCategoria(@PathVariable("id") Long idCategoria) throws Exception{
        List<ArticuloInsumo> articuloInsumos = articuloInsumoService.listarPorCategoria(idCategoria);
        return ResponseEntity.ok(articuloInsumos);
    }


    @PostMapping("/crear")
    public ResponseEntity<ArticuloInsumo> crear(@RequestBody ArticuloInsumo insumo) {
        // Validación: si esParaElaborar es true, precioVenta debe ser null
        if (insumo.getEsParaElaborar() != null && insumo.getEsParaElaborar()) {
            insumo.setPrecioVenta(null);
        }
        ArticuloInsumo nuevo = categoriaService.crearInsumo(insumo);
        return ResponseEntity.ok(nuevo);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ArticuloInsumo>> buscarPorDenominacion(@RequestParam String denominacion) throws Exception {
        List<ArticuloInsumo> resultado = articuloInsumoService.buscarPorDenominacion(denominacion);
        return ResponseEntity.ok(resultado);
    }

    @RequestMapping("/listar")
    public ResponseEntity<List<ArticuloInsumoDTO>> listarTodos() {
        try {
            List<ArticuloInsumo> articuloInsumos = articuloInsumoService.findAll();

            // Mapear las entidades a DTOs
            List<ArticuloInsumoDTO> articuloInsumoDTOs = articuloInsumos.stream().map(articulo -> {
                ArticuloInsumoDTO dto = new ArticuloInsumoDTO();
                dto.setId(articulo.getId());
                dto.setDenominacion(articulo.getDenominacion());

                // Mapear la categoría
                CategoriaDTO categoriaDTO = new CategoriaDTO();
                categoriaDTO.setId(articulo.getCategoria().getId());
                categoriaDTO.setDenominacion(articulo.getCategoria().getDenominacion());
                categoriaDTO.setEsInsumo(articulo.getCategoria().getEsInsumo());
                dto.setCategoria(categoriaDTO);

                dto.setUnidadMedida(articulo.getUnidadMedida().getDenominacion());
                dto.setPrecioVenta(articulo.getPrecioVenta() != null ? articulo.getPrecioVenta() : 0.0);
                dto.setPrecioCompra(articulo.getPrecioCompra());
                dto.setStockActual(articulo.getStockActual());
                dto.setStockMaximo(articulo.getStockMaximo());
                dto.setStockMinimo(articulo.getStockMinimo());
                dto.setEsParaElaborar(articulo.getEsParaElaborar());
                dto.setStockPendiente(articulo.getStockPendiente() != null ? articulo.getStockPendiente() : 0);

                return dto;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(articuloInsumoDTOs);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500).body(null); // Devuelve un error 500 en caso de excepción
        }

    }


    @PutMapping("/modificar/{id}")
    public ResponseEntity<ArticuloInsumo> modificar(@PathVariable Long id, @RequestBody ArticuloInsumo insumo) throws Exception {
        ArticuloInsumo actualizado = articuloInsumoService.modificar(id, insumo);
        return ResponseEntity.ok(actualizado);
    }

    @GetMapping
    public ResponseEntity<List<ArticuloInsumo>> listar() {
        try {
            List<ArticuloInsumo> articulos = service.listar(); // Usar listar() no findAll()
            return ResponseEntity.ok(articulos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}