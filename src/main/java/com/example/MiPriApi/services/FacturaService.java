package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.Factura;
import com.example.MiPriApi.repositories.FacturaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FacturaService extends BaseService<Factura, Long>{
    public FacturaService(FacturaRepository facturaRepository) {
        super(facturaRepository);
    }

    @Autowired
    private FacturaRepository facturaRepository;


    @Transactional
    public List<Factura> listarPorPedido(Long idPedido) throws Exception{
        try {
            return facturaRepository.findAllByPedidoId(idPedido);
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
}
