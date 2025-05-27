package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.Usuario;
import com.example.MiPriApi.repositories.BaseRepository;
import com.example.MiPriApi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService extends BaseService<Usuario, Long>{


    public UsuarioService(UsuarioRepository usuarioRepository) {
        super(usuarioRepository);
    }
}
