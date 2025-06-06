package com.example.MiPriApi.services;



import com.example.MiPriApi.entities.Empleado;
import com.example.MiPriApi.repositories.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService extends BaseService<Empleado, Long> {
    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        super(empleadoRepository);
    }
}