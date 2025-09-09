package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.Promocion;
import com.example.MiPriApi.repositories.DetallePromocionRepository;
import com.example.MiPriApi.repositories.PromocionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class PromocionService extends BaseService<Promocion, Long>{
    public PromocionService(PromocionRepository promocionRepository) {
        super(promocionRepository);
    }

    @Autowired
    private PromocionRepository promocionRepository;

    @Autowired
    private DetallePromocionRepository detalleRepo;

    @Transactional
    public List<Promocion> listarPorSucursal(Long sucursalId) throws Exception{
        try {
            return promocionRepository.findAllBySucursalesId(sucursalId);
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional
    public void eliminarPorId(Long id) throws Exception {
        Promocion promocion = promocionRepository.findById(id)
                .orElseThrow(() -> new Exception("Promoción no encontrada"));

        // borra en la tabla detalle_promociones los registros referidos a la promoción
        detalleRepo.deleteByPromocionId(id);

        // ahora sí, borra la promoción
        promocionRepository.delete(promocion);
    }

    public Promocion editarPromocion(Long id, Promocion promocionActualizada) throws Exception {
        Promocion promocionExistente = promocionRepository.findById(id)
                .orElseThrow(() -> new Exception("Promoción no encontrada"));
        promocionExistente.setDenominacion(promocionActualizada.getDenominacion());
        promocionExistente.setFechaDesde(promocionActualizada.getFechaDesde());
        promocionExistente.setFechaHasta(promocionActualizada.getFechaHasta());
        promocionExistente.setHoraDesde(promocionActualizada.getHoraDesde());
        promocionExistente.setHoraHasta(promocionActualizada.getHoraHasta());
        promocionExistente.setDescripcionDescuento(promocionActualizada.getDescripcionDescuento());
        promocionExistente.setPrecioPromocional(promocionActualizada.getPrecioPromocional());
        promocionExistente.setTipoPromocion(promocionActualizada.getTipoPromocion());
        promocionExistente.setImagenesPromocion(promocionActualizada.getImagenesPromocion());
        promocionExistente.setSucursales(promocionActualizada.getSucursales());
        return promocionRepository.save(promocionExistente);
    }
}
