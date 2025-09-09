package com.example.MiPriApi.services;


import com.example.MiPriApi.entities.Articulo;
import com.example.MiPriApi.repositories.ArticuloRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticuloService {

    @Autowired
    private ArticuloRepository articuloRepository;

    public Articulo asignarDescuento(Long id, Boolean descuento, Double precioDescuento) {
        Articulo articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Articulo not found"));
        articulo.setDescuento(descuento);
        articulo.setPrecioDescuento(precioDescuento);
        return articuloRepository.save(articulo);
    }

    public List<Articulo> findAll() {
        return articuloRepository.findAll();
    }
}
