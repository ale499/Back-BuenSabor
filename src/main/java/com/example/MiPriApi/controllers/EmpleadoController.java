package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.Empleado;
import com.example.MiPriApi.services.EmpleadoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Empleados")
@PreAuthorize("hasAuthority('Admin')")
public class EmpleadoController extends BaseController<Empleado, Long> {
    public EmpleadoController(EmpleadoService empleadoService) {
        super(empleadoService);
    }
}