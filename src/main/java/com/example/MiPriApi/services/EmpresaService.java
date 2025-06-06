package com.example.MiPriApi.services;
import com.example.MiPriApi.entities.Empresa;
import com.example.MiPriApi.repositories.BaseRepository;
import com.example.MiPriApi.repositories.EmpresaRepository;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService extends BaseService<Empresa, Long>{

    public EmpresaService(EmpresaRepository empresaRepository) {
        super(empresaRepository);
    }
}
