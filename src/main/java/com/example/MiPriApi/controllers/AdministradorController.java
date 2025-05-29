package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.Administrador;
import com.example.MiPriApi.entities.Empleado;
import com.example.MiPriApi.entities.Cliente;
import com.example.MiPriApi.services.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/administradores")
public class AdministradorController extends BaseController<Administrador, Long> {

    @Autowired
    private AdministradorService administradorService;

    public AdministradorController(AdministradorService administradorService) {
        super(administradorService);
    }

    @PostMapping("/empleados/alta")
    public Empleado crearEmpleado(@RequestBody Empleado empleado) throws Exception {
        return administradorService.crearEmpleado(empleado);
    }

    @DeleteMapping("/empleados/baja/{id}")
    public void eliminarEmpleado(@PathVariable Long id) throws Exception {
        administradorService.eliminarEmpleado(id);
    }

    @PutMapping("/empleados/modificar/{id}")
    public Empleado modificarEmpleado(@PathVariable Long id, @RequestBody Empleado empleado) throws Exception {
        return administradorService.modificarEmpleado(id, empleado);
    }

    @DeleteMapping("/clientes/baja/{id}")
    public void eliminarCliente(@PathVariable Long id) throws Exception {
        administradorService.eliminarCliente(id);
    }

    @PutMapping("/clientes/modificar/{id}")
    public Cliente modificarCliente(@PathVariable Long id, @RequestBody Cliente cliente) throws Exception {
        return administradorService.modificarCliente(id, cliente);
    }
}