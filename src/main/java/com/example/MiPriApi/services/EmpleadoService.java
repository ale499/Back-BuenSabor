package com.example.MiPriApi.services;



import com.example.MiPriApi.entities.Empleado;
<<<<<<< HEAD
import com.example.MiPriApi.repositories.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService extends BaseService<Empleado, Long> {
    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        super(empleadoRepository);
    }
=======
import com.example.MiPriApi.entities.Usuario;
import com.example.MiPriApi.repositories.EmpleadoRepository;
import com.example.MiPriApi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService extends BaseService<Empleado, Long> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {super(empleadoRepository);
    }


    @Override
    public Empleado crear(Empleado empleado) throws Exception {
        if (empleado.getUsuario() != null && empleado.getUsuario().getId() == null) {
            Usuario usuarioGuardado = usuarioRepository.save(empleado.getUsuario());
            empleado.setUsuario(usuarioGuardado);
        }
        return super.crear(empleado);
    }

>>>>>>> Dev
}