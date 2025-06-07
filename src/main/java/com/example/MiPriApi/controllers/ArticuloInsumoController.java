package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.ArticuloInsumo;
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
@PreAuthorize("hasAnyAuthority('Admin', 'Chef')")
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
                dto.setPrecioVenta(articulo.getPrecioVenta());
                dto.setPrecioCompra(articulo.getPrecioCompra());
                dto.setStockActual(articulo.getStockActual());
                dto.setStockMaximo(articulo.getStockMaximo());
                dto.setStockMinimo(articulo.getStockMinimo());
                dto.setEsParaElaborar(articulo.getEsParaElaborar());

                return dto;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(articuloInsumoDTOs);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null); // Devuelve un error 500 en caso de excepción
        }
    }

}
