package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.Cliente;
import com.example.MiPriApi.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController extends BaseController<Cliente, Long>{

    public ClienteController(ClienteService clienteService){
        super(clienteService);
    }

}
