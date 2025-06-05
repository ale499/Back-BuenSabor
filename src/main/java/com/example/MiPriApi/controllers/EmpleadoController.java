package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.Empleado;
import com.example.MiPriApi.services.EmpleadoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Empleados")
public class EmpleadoController extends BaseController<Empleado, Long> {
    public EmpleadoController(EmpleadoService empleadoService) {
        super(empleadoService);
    }
}
