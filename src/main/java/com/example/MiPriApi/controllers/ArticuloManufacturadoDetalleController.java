package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.ArticuloManufacturado;
import com.example.MiPriApi.entities.ArticuloManufacturadoDetalle;
import com.example.MiPriApi.entities.DTO.ArticuloManufacturadoDetalleDTO;
import com.example.MiPriApi.services.ArticuloManufacturadoDetalleMapper;
import com.example.MiPriApi.services.ArticuloManufacturadoDetalleService;
import com.example.MiPriApi.services.ArticuloManufacturadoService;
import com.example.MiPriApi.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articuloManufacturadoDetalle")
@PreAuthorize("hasAnyAuthority('Admin', 'Chef')")
public class ArticuloManufacturadoDetalleController extends BaseController<ArticuloManufacturadoDetalle, Long> {
    public ArticuloManufacturadoDetalleController(ArticuloManufacturadoDetalleService service) {
        super(service);
    }

    @Autowired
    private ArticuloManufacturadoDetalleService articuloManufacturadoDetalleService;

    @Autowired
    private ArticuloManufacturadoDetalleMapper mapper;

    @RequestMapping("/articuloInsumo/{id}")
    public ResponseEntity<List<ArticuloManufacturadoDetalle>> listarPorArticuloInsumo(@PathVariable Long idArticuloInsumo) throws Exception {
        List<ArticuloManufacturadoDetalle> articuloManufacturadoDetalles = articuloManufacturadoDetalleService.listarPorArticuloInsumo(idArticuloInsumo);
        return ResponseEntity.ok(articuloManufacturadoDetalles);

    }


    @GetMapping("/detalle/{id}")
    public ResponseEntity<ArticuloManufacturadoDetalleDTO> obtenerDetalle(@PathVariable Long id) throws Exception {
        ArticuloManufacturadoDetalle detalle = service.buscarPorId(id).orElseThrow(() -> new Exception("Detalle no encontrado"));
        ArticuloManufacturadoDetalleDTO dto = mapper.toDTO(detalle);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<ArticuloManufacturadoDetalleDTO>> listarTodos() throws Exception {
        List<ArticuloManufacturadoDetalle> detalles = service.listar();

        // Agrupar los detalles por ArticuloManufacturado
        Map<ArticuloManufacturado, List<ArticuloManufacturadoDetalle>> agrupadosPorArticulo = detalles.stream()
                .collect(Collectors.groupingBy(ArticuloManufacturadoDetalle::getArticuloManufacturado));

        // Mapear cada ArticuloManufacturado a un DTO consolidado
        List<ArticuloManufacturadoDetalleDTO> dtos = agrupadosPorArticulo.entrySet().stream()
                .map(entry -> {
                    ArticuloManufacturado articulo = entry.getKey();
                    List<ArticuloManufacturadoDetalle> detallesArticulo = entry.getValue();

                    // Crear el DTO consolidado
                    ArticuloManufacturadoDetalleDTO dto = mapper.toDTO(detallesArticulo.get(0));

                    // Mapear los insumos asociados
                    List<ArticuloManufacturadoDetalleDTO.DetalleDTO> insumos = detallesArticulo.stream()
                            .map(detalle -> {
                                ArticuloManufacturadoDetalleDTO.DetalleDTO detalleDTO = new ArticuloManufacturadoDetalleDTO.DetalleDTO();
                                detalleDTO.setTipo("INSUMO");
                                detalleDTO.setCantidad(detalle.getCantidad());

                                if (detalle.getArticuloInsumo() != null) { // Verificar si el insumo no es nulo
                                    ArticuloManufacturadoDetalleDTO.DetalleDTO.ItemDTO itemDTO = new ArticuloManufacturadoDetalleDTO.DetalleDTO.ItemDTO();
                                    itemDTO.setId(detalle.getArticuloInsumo().getId());
                                    itemDTO.setDenominacion(detalle.getArticuloInsumo().getDenominacion());
                                    itemDTO.setCategoriaId(detalle.getArticuloInsumo().getCategoria().getId());
                                    itemDTO.setUnidadMedida(detalle.getArticuloInsumo().getUnidadMedida().getDenominacion());
                                    itemDTO.setPrecioCompra(detalle.getArticuloInsumo().getPrecioCompra());
                                    itemDTO.setStockActual(detalle.getArticuloInsumo().getStockActual());

                                    detalleDTO.setItem(itemDTO);
                                }

                                return detalleDTO;
                            })
                            .collect(Collectors.toList());

                    dto.setDetalles(insumos);
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

}