package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.Promocion;
import com.example.MiPriApi.services.BaseService;
import com.example.MiPriApi.services.PromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
