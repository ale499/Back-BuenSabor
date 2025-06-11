package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.ArticuloManufacturadoDetalle;
import com.example.MiPriApi.repositories.ArticuloManufacturadoDetalleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticuloManufacturadoDetalleService extends BaseService<ArticuloManufacturadoDetalle, Long>{
    public ArticuloManufacturadoDetalleService(ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository) {
        super(articuloManufacturadoDetalleRepository);
    }
    @Autowired
    private ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository;

    @Transactional
    public List<ArticuloManufacturadoDetalle> listarPorArticuloInsumo(Long idArticuloInsumo) throws Exception{
        try{
            return articuloManufacturadoDetalleRepository.findAllByArticuloInsumoId(idArticuloInsumo);
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }


}
