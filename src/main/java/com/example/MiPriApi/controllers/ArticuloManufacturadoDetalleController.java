package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.ArticuloManufacturadoDetalle;
import com.example.MiPriApi.services.ArticuloManufacturadoDetalleService;
import com.example.MiPriApi.services.ArticuloManufacturadoService;
import com.example.MiPriApi.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/articuloManufacturadoDetalle")
public class ArticuloManufacturadoDetalleController extends BaseController<ArticuloManufacturadoDetalle, Long>{
    public ArticuloManufacturadoDetalleController(ArticuloManufacturadoDetalleService service) {
        super(service);
    }
    @Autowired
    private ArticuloManufacturadoDetalleService articuloManufacturadoDetalleService;

    @RequestMapping("/articuloInsumo/{id}")
    public ResponseEntity<List<ArticuloManufacturadoDetalle>> listarPorArticuloInsumo(@PathVariable Long idArticuloInsumo) throws Exception{
        List<ArticuloManufacturadoDetalle> articuloManufacturadoDetalles = articuloManufacturadoDetalleService.listarPorArticuloInsumo(idArticuloInsumo);
        return ResponseEntity.ok(articuloManufacturadoDetalles);

    }

    @RequestMapping("/articuloManufacturado/{id}")
    public ResponseEntity<List<ArticuloManufacturadoDetalle>> listarPorArticuloManufacturado(@PathVariable Long idArticuloManufacturado) throws Exception{
        List<ArticuloManufacturadoDetalle> articuloManufacturadoDetalles = articuloManufacturadoDetalleService.listarPorArticuloManufacturado(idArticuloManufacturado);
        return ResponseEntity.ok(articuloManufacturadoDetalles);

    }
}
