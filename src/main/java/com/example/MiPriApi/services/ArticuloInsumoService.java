package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.ArticuloInsumo;
import com.example.MiPriApi.entities.ArticuloManufacturado;
import com.example.MiPriApi.entities.ArticuloManufacturadoDetalle;
import com.example.MiPriApi.repositories.ArticuloInsumoRepository;
import com.example.MiPriApi.repositories.ArticuloManufacturadoDetalleRepository;
import com.example.MiPriApi.repositories.ArticuloManufacturadoRepository;
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

    @Autowired
    public ArticuloManufacturadoRepository articuloManufacturadoRepository;

    @Autowired
    public ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository;

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

        // ... (actualiza los campos del insumo)
        existente.setPrecioCompra(insumo.getPrecioCompra());
        // ...

        ArticuloInsumo actualizado = articuloInsumoRepository.save(existente);

        // Recalcular precios de manufacturados que usan este insumo
        List<ArticuloManufacturadoDetalle> detalles = articuloManufacturadoDetalleRepository.findAllByArticuloInsumoId(id);
        for (ArticuloManufacturadoDetalle detalle : detalles) {
            ArticuloManufacturado manufacturado = detalle.getArticuloManufacturado();
            // Cargar detalles desde el repositorio para evitar lista vacía
            List<ArticuloManufacturadoDetalle> detallesManufacturado = articuloManufacturadoDetalleRepository.findAllByArticuloManufacturadoId(manufacturado.getId());
            double sumaInsumos = 0.0;
            for (ArticuloManufacturadoDetalle d : detallesManufacturado) {
                sumaInsumos += d.getArticuloInsumo().getPrecioCompra() * d.getCantidad();
            }
            double valorAgregado = manufacturado.getValorAgregado() != null ? manufacturado.getValorAgregado() : 0.0;
            double nuevoPrecio = sumaInsumos + valorAgregado;
            manufacturado.setPrecioVenta(nuevoPrecio);
            articuloManufacturadoRepository.save(manufacturado);
        }

        return actualizado;
    }

}
