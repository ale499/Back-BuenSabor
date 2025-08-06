package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.ArticuloInsumo;
import com.example.MiPriApi.entities.ArticuloManufacturado;
import com.example.MiPriApi.entities.ArticuloManufacturadoDetalle;
import com.example.MiPriApi.entities.DTO.ArticuloManufacturadoDetalleDTO;
import com.example.MiPriApi.services.*;
import com.example.MiPriApi.services.Mappers.ArticuloManufacturadoDetalleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articuloManufacturadoDetalle")
public class ArticuloManufacturadoDetalleController extends BaseController<ArticuloManufacturadoDetalle, Long> {
    public ArticuloManufacturadoDetalleController(ArticuloManufacturadoDetalleService service) {
        super(service);
    }

    @Autowired
    private ArticuloManufacturadoDetalleService articuloManufacturadoDetalleService;

    @Autowired
    private ArticuloManufacturadoDetalleMapper mapper;

    @Autowired
    private ArticuloManufacturadoService articuloManufacturadoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ArticuloInsumoService articuloInsumoService;

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

    @PostMapping("/crearArticuloManufacturado")
    public ResponseEntity<ArticuloManufacturadoDetalleDTO> crearArticuloManufacturado(@RequestBody ArticuloManufacturadoDetalleDTO articuloDTO) throws Exception {
        // Verificar stock suficiente para todos los insumos antes de crear el artículo
        for (ArticuloManufacturadoDetalleDTO.DetalleDTO detalleDTO : articuloDTO.getDetalles()) {
            ArticuloInsumo insumo = articuloInsumoService.buscarPorId(detalleDTO.getItem().getId())
                    .orElseThrow(() -> new Exception("Insumo no encontrado"));
            int cantidadAUsar = detalleDTO.getCantidad();
            int stockActual = insumo.getStockActual();
            int stockMinimo = insumo.getStockMinimo();
            if (stockActual - cantidadAUsar < stockMinimo) {
                throw new Exception("Stock insuficiente para el insumo: " + insumo.getDenominacion());
            }
        }

        // Crear el ArticuloManufacturado
        ArticuloManufacturado articuloManufacturado = new ArticuloManufacturado();
        articuloManufacturado.setDenominacion(articuloDTO.getDenominacion());
        articuloManufacturado.setCategoria(categoriaService.buscarPorId(articuloDTO.getCategoriaId()).orElseThrow(() -> new Exception("Categoría no encontrada")));
        articuloManufacturado.setDescripcion(articuloDTO.getDescripcion());
        articuloManufacturado.setTiempoEstimadoMinutos(articuloDTO.getTiempoEstimadoMinutos());
        articuloManufacturado.setPreparacion(articuloDTO.getPreparacion());

        // Calcular el precio base de los insumos usando precioCompra
        double precioTotalInsumos = 0.0;
        for (ArticuloManufacturadoDetalleDTO.DetalleDTO detalleDTO : articuloDTO.getDetalles()) {
            ArticuloInsumo insumo = articuloInsumoService.buscarPorId(detalleDTO.getItem().getId()).orElseThrow(() -> new Exception("Insumo no encontrado"));
            precioTotalInsumos += insumo.getPrecioCompra() * detalleDTO.getCantidad();
        }

        // Validar precio de venta
        if (articuloDTO.getPrecioVenta() != null) {
            if (articuloDTO.getPrecioVenta() < precioTotalInsumos) {
                throw new Exception("El precio de venta no puede ser menor a la suma de los insumos (" + precioTotalInsumos + ")");
            }
            articuloManufacturado.setPrecioVenta(articuloDTO.getPrecioVenta());
        } else {
            articuloManufacturado.setPrecioVenta(precioTotalInsumos * 3);
        }

        // Guardar el ArticuloManufacturado
        ArticuloManufacturado articuloGuardado = articuloManufacturadoService.crear(articuloManufacturado);

        // Descontar stock de los insumos y guardar los detalles
        for (ArticuloManufacturadoDetalleDTO.DetalleDTO detalleDTO : articuloDTO.getDetalles()) {
            ArticuloInsumo insumo = articuloInsumoService.buscarPorId(detalleDTO.getItem().getId()).orElseThrow(() -> new Exception("Insumo no encontrado"));


            ArticuloManufacturadoDetalle detalle = new ArticuloManufacturadoDetalle();
            detalle.setArticuloManufacturado(articuloGuardado);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setArticuloInsumo(insumo);
            articuloManufacturadoDetalleService.crear(detalle);
        }

        // Mapear la respuesta al DTO esperado, incluyendo los detalles
        List<ArticuloManufacturadoDetalle> detallesGuardados = articuloManufacturadoDetalleService.listarPorArticuloManufacturado(articuloGuardado.getId());
        ArticuloManufacturadoDetalleDTO respuestaDTO = mapper.toDTOFromArticuloManufacturado(articuloGuardado);

        List<ArticuloManufacturadoDetalleDTO.DetalleDTO> detallesDTO = detallesGuardados.stream().map(detalle -> {
            ArticuloManufacturadoDetalleDTO.DetalleDTO detalleDTO = new ArticuloManufacturadoDetalleDTO.DetalleDTO();
            detalleDTO.setTipo("INSUMO");
            detalleDTO.setCantidad(detalle.getCantidad());

            ArticuloManufacturadoDetalleDTO.DetalleDTO.ItemDTO itemDTO = new ArticuloManufacturadoDetalleDTO.DetalleDTO.ItemDTO();
            itemDTO.setId(detalle.getArticuloInsumo().getId());
            itemDTO.setDenominacion(detalle.getArticuloInsumo().getDenominacion());
            itemDTO.setCategoriaId(detalle.getArticuloInsumo().getCategoria().getId());
            itemDTO.setUnidadMedida(detalle.getArticuloInsumo().getUnidadMedida().getDenominacion());
            itemDTO.setPrecioCompra(detalle.getArticuloInsumo().getPrecioCompra());
            itemDTO.setStockActual(detalle.getArticuloInsumo().getStockActual());

            detalleDTO.setItem(itemDTO);
            return detalleDTO;
        }).collect(Collectors.toList());

        respuestaDTO.setDetalles(detallesDTO);

        return ResponseEntity.ok(respuestaDTO);
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

    @GetMapping("/manufacturables")
    public ResponseEntity<List<Object>> listarManufacturablesYBebidas() throws Exception {
        List<ArticuloManufacturado> todos = articuloManufacturadoService.listarTodos();
        List<ArticuloManufacturadoDetalleDTO> disponibles = new ArrayList<>();

        for (ArticuloManufacturado manu : todos) {
            boolean puedeHacerse = true;
            for (ArticuloManufacturadoDetalle det : manu.getDetalles()) {
                ArticuloInsumo insumo = det.getArticuloInsumo();
                if (insumo.getStockActual() < det.getCantidad()) {
                    puedeHacerse = false;
                    break;
                }
            }
            if (puedeHacerse) {
                disponibles.add(mapper.toDTOFromArticuloManufacturado(manu));
            }
        }

        List<String> categoriasPermitidas = List.of("gaseosas", "jugos", "cervezas");
        List<ArticuloInsumo> todosInsumos = articuloInsumoService.findAll();
        List<ArticuloInsumo> bebidas = todosInsumos.stream()
                .filter(insumo -> {
                    String cat = insumo.getCategoria().getDenominacion();
                    return cat != null && categoriasPermitidas.contains(cat.toLowerCase());
                })
                .collect(Collectors.toList());

        List<Object> response = new ArrayList<>();
        response.addAll(disponibles);
        response.addAll(bebidas);

        return ResponseEntity.ok(response);
    }
}