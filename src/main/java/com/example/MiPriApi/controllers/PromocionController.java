package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.Promocion;
import com.example.MiPriApi.services.BaseService;
import com.example.MiPriApi.services.PromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promocion")
public class PromocionController extends BaseController<Promocion, Long>{

    public PromocionController(PromocionService service) {
        super(service);
    }

    @Autowired
    private PromocionService promocionService;

    @RequestMapping("/sucursal/{idSucursal}")
    public ResponseEntity<List<Promocion>> listarPorSucursal(@PathVariable Long idSucursal) throws Exception{
        List<Promocion> promocions = promocionService.listarPorSucursal(idSucursal);
        return ResponseEntity.ok(promocions);
    }

    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity<?> eliminarPromocion(@PathVariable Long id) {
        try {
            promocionService.eliminarPorId(id);
            return ResponseEntity.ok("Promoción eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/editar")
    public ResponseEntity<?> editarPromocion(@PathVariable Long id, @RequestBody Promocion promocionActualizada) {
        try {
            Promocion promocion = promocionService.editarPromocion(id, promocionActualizada);
            return ResponseEntity.ok(promocion);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


}
