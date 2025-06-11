package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.Administrador;
import com.example.MiPriApi.entities.Empleado;
import com.example.MiPriApi.entities.Cliente;
import com.example.MiPriApi.services.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/administradores")
public class AdministradorController extends BaseController<Administrador, Long> {

    @Autowired
    private AdministradorService administradorService;

    public AdministradorController(AdministradorService administradorService) {
        super(administradorService);
    }

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping("/empleados/alta")
    public Empleado crearEmpleado(@RequestBody Empleado empleado) throws Exception {
        return administradorService.crearEmpleado(empleado);
    }

    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("/empleados/baja/{id}")
    public void eliminarEmpleado(@PathVariable Long id) throws Exception {
        administradorService.eliminarEmpleado(id);
    }

    @PreAuthorize("hasAuthority('Admin')")
    @PutMapping("/empleados/modificar/{id}")
    public Empleado modificarEmpleado(@PathVariable Long id, @RequestBody Empleado empleado) throws Exception {
        return administradorService.modificarEmpleado(id, empleado);
    }

    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("/clientes/baja/{id}")
    public void eliminarCliente(@PathVariable Long id) throws Exception {
        administradorService.eliminarCliente(id);
    }

    @PreAuthorize("hasAuthority('Admin')")
    @PutMapping("/clientes/modificar/{id}")
    public Cliente modificarCliente(@PathVariable Long id, @RequestBody Cliente cliente) throws Exception {
        return administradorService.modificarCliente(id, cliente);
    }
}