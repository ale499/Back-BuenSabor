package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.Usuario;
import com.example.MiPriApi.services.BaseService;
import com.example.MiPriApi.services.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController extends BaseController<Usuario, Long>{


    public UsuarioController(UsuarioService service) {
        super(service);
    }
}
