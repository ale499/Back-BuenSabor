package com.example.MiPriApi.services;

import com.example.MiPriApi.dto.DetallePedidoRequestDTO;
import com.example.MiPriApi.dto.PedidoRequestDTO;
import com.example.MiPriApi.entities.*;
import com.example.MiPriApi.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService extends BaseService<Pedido, Long> {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ArticuloInsumoRepository articuloInsumoRepository;
    @Autowired
    private ArticuloManufacturadoRepository articuloManufacturadoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        super(pedidoRepository);
    }

    @Transactional
    public List<Pedido> listarPorCliente(Long idCliente) throws Exception {
        try {
            return pedidoRepository.findAllByClienteId(idCliente);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional
    public List<Pedido> listarPorEmpleado(Long idEmpleado) throws Exception {
        try {
            return pedidoRepository.findAllByEmpleadoId(idEmpleado);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional
    public List<Pedido> listarPorSucursal(Long idSucursal) throws Exception {
        try {
            return pedidoRepository.findAllBySucursalId(idSucursal);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional
    public Pedido buscarPedidoPorId(Long idPedido) throws Exception {
        return pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new Exception("Pedido no encontrado con ID: " + idPedido));
    }

    @Transactional
    public void crearPedidoDesdeCarrito(PedidoRequestDTO pedidoRequest) throws Exception {
        if (pedidoRequest.getClienteId() == null) {
            throw new Exception("El ID del cliente no puede ser nulo");
        }
        Cliente cliente = clienteRepository.findById(pedidoRequest.getClienteId())
                .orElseThrow(() -> new Exception("Cliente no encontrado"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setTotal(pedidoRequest.getTotal());
        pedido.setFechaPedido(LocalDate.now());
        pedido = pedidoRepository.save(pedido);

        for (DetallePedidoRequestDTO item : pedidoRequest.getItems()) {
            Articulo articulo;
            if ("INSUMO".equalsIgnoreCase(item.getTipoArticulo())) {
                if (item.getArticuloId() == null) {
                    throw new Exception("El ID del artículo no puede ser nulo");
                }
                articulo = articuloInsumoRepository.findById(item.getArticuloId())
                        .orElseThrow(() -> new Exception("Insumo no encontrado"));
            } else if ("MANUFACTURADO".equalsIgnoreCase(item.getTipoArticulo())) {
                articulo = articuloManufacturadoRepository.findById(item.getArticuloId())
                        .orElseThrow(() -> new Exception("Manufacturado no encontrado"));
            } else {
                throw new Exception("Tipo de artículo no válido");
            }

            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setArticulo(articulo);
            detalle.setCantidad(item.getCantidad());
            detalle.setSubTotal(item.getSubTotal());

            detallePedidoRepository.save(detalle);
        }
    }
}