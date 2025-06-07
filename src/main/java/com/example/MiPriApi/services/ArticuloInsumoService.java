package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.ArticuloInsumo;
import com.example.MiPriApi.entities.ArticuloManufacturado;
import com.example.MiPriApi.repositories.ArticuloInsumoRepository;
import com.example.MiPriApi.repositories.BaseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticuloInsumoService extends BaseService<ArticuloInsumo, Long>{
    public ArticuloInsumoService(ArticuloInsumoRepository articuloInsumoRepository) {
        super(articuloInsumoRepository);
    }

    @Autowired
    public ArticuloInsumoRepository articuloInsumoRepository;

    @Transactional
    public List<ArticuloInsumo> listarPorCategoria(Long idCategoria)throws Exception{
        try {
            return articuloInsumoRepository.findAllByCategoriaId(idCategoria);
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional

    public List<ArticuloInsumo> buscarPorDenominacion(String denominacion) throws Exception {
        try {
            return articuloInsumoRepository.findByDenominacionContainingIgnoreCase(denominacion);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
