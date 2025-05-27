package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.Cliente;
import com.example.MiPriApi.repositories.BaseRepository;
import com.example.MiPriApi.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class ClienteService extends BaseService<Cliente, Long>{

    public ClienteService(ClienteRepository clienteRepository) {
        super(clienteRepository);
    }
}

