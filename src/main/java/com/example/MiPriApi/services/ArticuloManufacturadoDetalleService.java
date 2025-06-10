package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.ArticuloManufacturado;
import com.example.MiPriApi.entities.ArticuloManufacturadoDetalle;
import com.example.MiPriApi.repositories.ArticuloManufacturadoDetalleRepository;
import com.example.MiPriApi.repositories.ArticuloManufacturadoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticuloManufacturadoDetalleService extends BaseService<ArticuloManufacturadoDetalle, Long>{
    public ArticuloManufacturadoDetalleService(ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository) {
        super(articuloManufacturadoDetalleRepository);
    }

    @Autowired
    private ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository;

    @Autowired
    private ArticuloManufacturadoRepository articuloManufacturadoRepository;


    @Transactional
    public List<ArticuloManufacturadoDetalle> listarPorArticuloInsumo(Long idArticuloInsumo) throws Exception{
        try{
            return articuloManufacturadoDetalleRepository.findAllByArticuloInsumoId(idArticuloInsumo);
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional
    public List<ArticuloManufacturadoDetalle> listarPorArticuloManufacturado(Long idArticuloManufacturado) throws Exception{
        try{
            return articuloManufacturadoDetalleRepository.findAllByArticuloManufacturadoId(idArticuloManufacturado);
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional
    public List<ArticuloManufacturadoDetalle> buscarPorDenominacion(String denominacion) throws Exception {
        try {
            // Suponiendo que tienes acceso al repositorio de ArticuloManufacturado
            List<ArticuloManufacturado> articulos = articuloManufacturadoRepository.findByDenominacionContainingIgnoreCase(denominacion);
            List<ArticuloManufacturadoDetalle> detalles = new ArrayList<>();
            for (ArticuloManufacturado articulo : articulos) {
                detalles.addAll(articuloManufacturadoDetalleRepository.findAllByArticuloManufacturadoId(articulo.getId()));
            }
            return detalles;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

}
