package com.example.MiPriApi.services;

import com.example.MiPriApi.dto.*;
import com.example.MiPriApi.entities.*;
import com.example.MiPriApi.entities.enums.*;
import com.example.MiPriApi.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
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
    @Autowired
    private StockService stockService;
    @Autowired
    private TiempoEstimadoService tiempoEstimadoService;
    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private DomicilioRepository domicilioRepository;
    @Autowired
    private SucursalRepository sucursalRepository;

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
        if (pedidoRequest.getEmpleadoId() == null) {
            throw new Exception("El ID del empleado no puede ser nulo");
        }
        if (pedidoRequest.getDomicilioId() == null){
            throw new Exception("El ID del domicilio no puede ser nulo");
        }
        if (pedidoRequest.getSucursalId() == null){
            throw new Exception("El ID de la sucursal no puede ser nulo");
        }

        Cliente cliente = clienteRepository.findById(pedidoRequest.getClienteId())
                .orElseThrow(() -> new Exception("Cliente no encontrado"));
        Empleado empleado = empleadoRepository.findById(pedidoRequest.getEmpleadoId())
                .orElseThrow(()-> new Exception("Empleado no encontrado"));
        Domicilio domicilio = domicilioRepository.findById(pedidoRequest.getDomicilioId())
                .orElseThrow(()-> new Exception("Domicilio no encontrado"));
        Sucursal sucursal = sucursalRepository.findById(pedidoRequest.getSucursalId())
                .orElseThrow(()->new Exception("Sucursal no encontrado"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setTotal(pedidoRequest.getTotal());
        pedido.setFechaPedido(LocalDate.now());
        pedido.setEmpleado(empleado);
        pedido.setDomicilio(domicilio);
        pedido.setSucursal(sucursal);
        pedido.setNumeroPedido(pedidoRequest.getNumeroPedido());
        pedido = pedidoRepository.save(pedido);

        for (DetallePedidoRequestDTO item : pedidoRequest.getItems()) {
            Articulo articulo;
            if ("INSUMO".equalsIgnoreCase(item.getTipoArticulo())) {
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

    @Transactional
    public void confirmarPedido(Long idPedido, ConfirmarPedidoRequestDTO request) throws Exception {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new Exception("Pedido no encontrado"));

        // Aquí puedes agregar lógica para tipo de envío y forma de pago usando el DTO si es necesario
        // pedido.setTipoEnvio(request.getTipoEnvio());
        // pedido.setFormaPago(request.getFormaPago());

        pedido.setEstado(Estado.PENDIENTE);

        // Descontar stock usando el servicio
        stockService.descontarStockIngredientes(pedido);

        // Calcular y setear tiempo estimado usando el servicio
        int minutos = tiempoEstimadoService.calcularTiempoEstimado(pedido);
        LocalTime horaEstimada = LocalTime.now().plusMinutes(minutos);
        pedido.setHoraEstimadaFinalizacion(horaEstimada);

        pedidoRepository.save(pedido);
    }
}