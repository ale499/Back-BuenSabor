package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.Domicilio;
import com.example.MiPriApi.services.BaseService;
import com.example.MiPriApi.services.DomicilioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/domicilio")
public class DomicilioController extends BaseController<Domicilio, Long>{

    public DomicilioController(DomicilioService service) {
        super(service);
    }

    @Autowired
    private DomicilioService domicilioService;

    @RequestMapping("/localidad/{idLocalidad}")
    public ResponseEntity<List<Domicilio>> listarPorLocalidad(@PathVariable Long idLocalidad) throws Exception{
        List<Domicilio> domicilios = domicilioService.listarPorLocalidad(idLocalidad);
        return ResponseEntity.ok(domicilios);
    }

    @RequestMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Domicilio>> listarPorCliente(@PathVariable Long idCliente) throws Exception{
        List<Domicilio> domicilios = domicilioService.listarPorCliente(idCliente);
        return ResponseEntity.ok(domicilios);
    }


    // actualizar un domicilio por ID
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Domicilio> actualizarDomicilio(@PathVariable Long id, @RequestBody Domicilio nuevosDatos) throws Exception {
        Domicilio domicilioExistente = domicilioService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Domicilio no encontrado"));


        domicilioExistente.setCalle(nuevosDatos.getCalle());
        domicilioExistente.setNumero(nuevosDatos.getNumero());
        domicilioExistente.setPiso(nuevosDatos.getPiso());
        domicilioExistente.setNroDpto(nuevosDatos.getNroDpto());
        domicilioExistente.setCp(nuevosDatos.getCp());
        domicilioExistente.setLocalidad(nuevosDatos.getLocalidad());


        Domicilio actualizado = domicilioService.actualizar(domicilioExistente);
        return ResponseEntity.ok(actualizado);
    }
}
