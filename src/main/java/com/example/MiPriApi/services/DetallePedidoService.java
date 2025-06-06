package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.DetallePedido;
import com.example.MiPriApi.repositories.DetallePedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetallePedidoService extends BaseService<DetallePedido, Long>{


    public DetallePedidoService(DetallePedidoRepository detallePedidoRepository) {
        super(detallePedidoRepository);
    }

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Transactional
    public List<DetallePedido> listarPorPedido(Long idPedido) throws Exception{
        try{
            return detallePedidoRepository.findAllByPedidoId(idPedido);
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
}
