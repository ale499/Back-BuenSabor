package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.Articulo;
import com.example.MiPriApi.services.ArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articulos")
public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

    @PutMapping("/{id}/descuento")
    public ResponseEntity<Articulo> asignarDescuento(
            @PathVariable Long id,
            @RequestBody DescuentoRequest request) {
        Articulo updated = articuloService.asignarDescuento(id, request.getDescuento(), request.getPrecioDescuento());
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/remover-descuento")
    public ResponseEntity<Articulo> removerDescuento(@PathVariable Long id) {
        Articulo updated = articuloService.asignarDescuento(id, false, null);
        return ResponseEntity.ok(updated);
    }

    // DTO for request body
    public static class DescuentoRequest {
        private Boolean descuento;
        private Double precioDescuento;

        public Boolean getDescuento() { return descuento; }
        public void setDescuento(Boolean descuento) { this.descuento = descuento; }
        public Double getPrecioDescuento() { return precioDescuento; }
        public void setPrecioDescuento(Double precioDescuento) { this.precioDescuento = precioDescuento; }
    }
}