package com.example.MiPriApi.services;


import com.example.MiPriApi.entities.Pedido;
import com.example.MiPriApi.repositories.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService extends BaseService<Pedido, Long>{

    public PedidoService(PedidoRepository pedidoRepository) {
        super(pedidoRepository);
    }

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public List<Pedido> listarPorCliente(Long idCliente) throws Exception{
        try {
            return pedidoRepository.findAllByClienteId(idCliente);
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional
    public List<Pedido> listarPorEmpleado(Long idEmpleado) throws Exception{
        try {
            return pedidoRepository.findAllByEmpleadoId(idEmpleado);
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional
    public List<Pedido> listarPorSucursal(Long idSucursal) throws Exception{
        try {
            return pedidoRepository.findAllBySucursalId(idSucursal);
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
}
