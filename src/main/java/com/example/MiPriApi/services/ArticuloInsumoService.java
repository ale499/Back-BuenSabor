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

    @Transactional
    public List<ArticuloInsumo> findAll() throws Exception {
        try {
            return articuloInsumoRepository.findAll();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional
    public ArticuloInsumo modificar(Long id, ArticuloInsumo insumo) throws Exception {
        ArticuloInsumo existente = buscarPorId(id)
                .orElseThrow(() -> new Exception("Insumo no encontrado"));

        existente.setDenominacion(insumo.getDenominacion());
        existente.setCategoria(insumo.getCategoria());
        existente.setPrecioCompra(insumo.getPrecioCompra());
        existente.setPrecioVenta(insumo.getPrecioVenta());
        existente.setStockActual(insumo.getStockActual());
        existente.setStockMaximo(insumo.getStockMaximo());
        existente.setStockMinimo(insumo.getStockMinimo());
        existente.setEsParaElaborar(insumo.getEsParaElaborar());
        existente.setUnidadMedida(insumo.getUnidadMedida());

        return articuloInsumoRepository.save(existente);
    }

}
