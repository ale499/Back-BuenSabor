package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.DetallePromocion;
import com.example.MiPriApi.services.BaseService;
import com.example.MiPriApi.services.DetallePromocionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/detallePromocion")
public class DetallePromocionController extends BaseController<DetallePromocion, Long>{
    public DetallePromocionController(DetallePromocionService service) {
        super(service);
    }
}
