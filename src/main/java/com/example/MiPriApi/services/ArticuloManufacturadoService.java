package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.ArticuloManufacturado;
import com.example.MiPriApi.entities.ArticuloManufacturadoDetalle;
import com.example.MiPriApi.entities.Categoria;
import com.example.MiPriApi.repositories.ArticuloManufacturadoDetalleRepository;
import com.example.MiPriApi.repositories.ArticuloManufacturadoRepository;
import com.example.MiPriApi.repositories.CategoriaRepository;
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

    @Autowired
    private CategoriaRepository categoriaRepository;

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

    @Transactional
    public ArticuloManufacturado modificarConDetalles(Long id, ArticuloManufacturado nuevo) throws Exception {
        ArticuloManufacturado existente = articuloManufacturadoRepository.findById(id)
                .orElseThrow(() -> new Exception("Artículo manufacturado no encontrado"));

        // Update basic fields
        existente.setDenominacion(nuevo.getDenominacion());
        existente.setDescripcion(nuevo.getDescripcion());
        existente.setPrecioVenta(nuevo.getPrecioVenta());
        existente.setTiempoEstimadoMinutos(nuevo.getTiempoEstimadoMinutos());
        existente.setPreparacion(nuevo.getPreparacion());
        existente.setCategoria(nuevo.getCategoria());
        existente.setUnidadMedida(nuevo.getUnidadMedida());
        existente.setTiempoPreparacion(nuevo.getTiempoPreparacion());


        // Fetch and set full Categoria entity
        if (nuevo.getCategoria() != null && nuevo.getCategoria().getId() != null) {
            Categoria categoriaCompleta = categoriaRepository.findById(nuevo.getCategoria().getId()).orElse(null);
            existente.setCategoria(categoriaCompleta);
        } else {
            existente.setCategoria(null);
        }

        // Update details correctly
        detalleRepository.deleteAll(existente.getDetalles());
        existente.getDetalles().clear();
        for (ArticuloManufacturadoDetalle detalle : nuevo.getDetalles()) {
            detalle.setArticuloManufacturado(existente);
            detalleRepository.save(detalle);
            existente.getDetalles().add(detalle); // Add to the same collection instance
        }

        return articuloManufacturadoRepository.save(existente);
    }
}