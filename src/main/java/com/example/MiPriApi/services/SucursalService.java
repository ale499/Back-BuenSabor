package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.Sucursal;
import com.example.MiPriApi.repositories.BaseRepository;
import com.example.MiPriApi.repositories.SucursalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalService extends BaseService<Sucursal, Long>{



    public SucursalService(SucursalRepository sucursalRepository) {
        super(sucursalRepository);
    }

<<<<<<< HEAD
    @Autowired
    private SucursalRepository sucursalRepository;

    @Transactional
    public List<Sucursal> listarPorEmpresa(Long idEmpresa) throws Exception{
        try {
            return sucursalRepository.findAllByEmpresaId(idEmpresa);
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
=======
>>>>>>> Dev


}
