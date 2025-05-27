package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.Empleado;
import com.example.MiPriApi.services.BaseService;
import com.example.MiPriApi.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController extends BaseController<Empleado, Long>{


    public EmpleadoController(EmpleadoService service) {
        super(service);
    }

    @Autowired
    private EmpleadoService empleadoService;

    @RequestMapping("/sucursal/{idSucursal}")
    public ResponseEntity<List<Empleado>> listarPorSucursal(@PathVariable Long idSucursal) throws Exception{
        List<Empleado> empleados = empleadoService.listarPorSucursal(idSucursal);
        return ResponseEntity.ok(empleados);
    }
}
