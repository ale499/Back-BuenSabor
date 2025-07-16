package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.ArticuloManufacturado;
import com.example.MiPriApi.repositories.ArticuloManufacturadoDetalleRepository;
import com.example.MiPriApi.repositories.ArticuloManufacturadoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticuloManufacturadoService extends BaseService<ArticuloManufacturado, Long>{
    public ArticuloManufacturadoService(ArticuloManufacturadoRepository articuloManufacturadoRepository) {
        super(articuloManufacturadoRepository);
    }

    @Autowired
    private ArticuloManufacturadoRepository articuloManufacturadoRepository;

    @Transactional
    public List<ArticuloManufacturado> listarPorCategoria(Long idCategoria)throws Exception{
        try {
            return articuloManufacturadoRepository.findAllByCategoriaId(idCategoria);
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional
    public List<ArticuloManufacturado> buscarPorDenominacion(String denominacion) throws Exception {
        try {
            return articuloManufacturadoRepository.findByDenominacionContainingIgnoreCase(denominacion);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional
    public List<ArticuloManufacturado> listarTodos() throws Exception {
        try {
            return articuloManufacturadoRepository.findAll();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Autowired
    private ArticuloManufacturadoDetalleRepository detalleRepository;

    @Override
    @Transactional
    public void eliminar(Long id) throws Exception {
        ArticuloManufacturado articulo = buscarPorId(id)
                .orElseThrow(() -> new Exception("Artículo manufacturado no encontrado"));

        // Elimina todos los detalles asociados al artículo
        detalleRepository.deleteAllByArticuloManufacturadoId(articulo.getId());

        // Ahora elimina el artículo manufacturado
        articuloManufacturadoRepository.delete(articulo);
    }

}