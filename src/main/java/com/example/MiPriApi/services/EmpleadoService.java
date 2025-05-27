package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.Empleado;
import com.example.MiPriApi.repositories.EmpleadoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService extends BaseService<Empleado, Long>{

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        super(empleadoRepository);
    }

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Transactional
    public List<Empleado> listarPorSucursal(Long idSucursal) throws Exception{
        try {
            return empleadoRepository.findAllBySucursalId(idSucursal);
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
}
