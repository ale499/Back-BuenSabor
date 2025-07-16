package com.example.MiPriApi.services;

import com.example.MiPriApi.controllers.PedidoWebSocketController;
import com.example.MiPriApi.entities.DTO.DetallePedidoRequestDTO;
import com.example.MiPriApi.entities.DTO.ItemDTO;
import com.example.MiPriApi.entities.DTO.PedidoRequestDTO;
import com.example.MiPriApi.entities.DTO.ConfirmarPedidoRequestDTO;
import com.example.MiPriApi.entities.*;
import com.example.MiPriApi.entities.enums.Estado;
import com.example.MiPriApi.entities.enums.FormaPago;
import com.example.MiPriApi.entities.enums.TipoEnvio;
import com.example.MiPriApi.repositories.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    @Autowired
    private MercadoPagoService mercadoPagoService;

    @Autowired
    private PedidoWebSocketController pedidoWebSocketController;


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

    //lista pedidos por email del cliente

    @Transactional
    public List<Pedido> listarPorClienteEmail(String email) throws Exception {
        try {
            return pedidoRepository.findAllByClienteEmail(email);
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
        if (pedidoRequest.getClienteId() == null) throw new Exception("El ID del cliente no puede ser nulo");
        if (pedidoRequest.getDomicilioId() == null) throw new Exception("El ID del domicilio no puede ser nulo");
        if (pedidoRequest.getSucursalId() == null) throw new Exception("El ID de la sucursal no puede ser nulo");

        Cliente cliente = clienteRepository.findById(pedidoRequest.getClienteId())
                .orElseThrow(() -> new Exception("Cliente no encontrado"));
        Domicilio domicilio = domicilioRepository.findById(pedidoRequest.getDomicilioId())
                .orElseThrow(() -> new Exception("Domicilio no encontrado"));
        Sucursal sucursal = sucursalRepository.findById(pedidoRequest.getSucursalId())
                .orElseThrow(() -> new Exception("Sucursal no encontrada"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDomicilio(domicilio);
        pedido.setSucursal(sucursal);

        if (pedidoRequest.getEmpleadoId() != null) {
            Empleado empleado = empleadoRepository.findById(pedidoRequest.getEmpleadoId())
                    .orElseThrow(() -> new Exception("Empleado no encontrado"));
            pedido.setEmpleado(empleado);
        }

        // Número de pedido
        if (pedidoRequest.getNumeroPedido() != null) {
            pedido.setNumeroPedido(pedidoRequest.getNumeroPedido());
        } else {
            pedido.setNumeroPedido(generarNumeroPedido());
        }

        // Fecha del pedido
        if (pedidoRequest.getFechaPedido() != null) {
            pedido.setFechaPedido(pedidoRequest.getFechaPedido());
        } else {
            pedido.setFechaPedido(LocalDate.now());
        }

        // Estado
        if (pedidoRequest.getEstado() != null) {
            pedido.setEstado(Estado.valueOf(pedidoRequest.getEstado().toUpperCase()));
        } else {
            pedido.setEstado(Estado.PREPARACION);
        }

        // Forma de pago
        if (pedidoRequest.getFormaPago() != null) {
            pedido.setFormaPago(FormaPago.valueOf(pedidoRequest.getFormaPago().toUpperCase()));
        }

        // Tipo de envío
        if (pedidoRequest.getTipoEnvio() != null) {
            pedido.setTipoEnvio(TipoEnvio.valueOf(pedidoRequest.getTipoEnvio().toUpperCase()));
        }

        pedido.setTotal(pedidoRequest.getTotal());

        // Total costo
        if (pedidoRequest.getTotalCosto() != null) {
            pedido.setTotalCosto(pedidoRequest.getTotalCosto());
        } else {
            pedido.setTotalCosto(calcularTotalCosto(pedidoRequest.getItems()));
        }

        pedido = pedidoRepository.save(pedido);

// Save DetallePedido items
        List<DetallePedido> detalles = new ArrayList<>();
        for (DetallePedidoRequestDTO item : pedidoRequest.getItems()) {
            Articulo articulo;
            if ("INSUMO".equalsIgnoreCase(item.getTipoArticulo())) {
                articulo = articuloInsumoRepository.findById(item.getArticuloId())
                        .orElseThrow(() -> new Exception("Insumo no encontrado"));
                ArticuloInsumo insumo = (ArticuloInsumo) articulo;
                int disponible = insumo.getStockActual() - insumo.getStockPendiente();
                if (disponible < item.getCantidad()) {
                    throw new Exception("Stock insuficiente para " + insumo.getDenominacion());
                }
                insumo.setStockPendiente(insumo.getStockPendiente() + item.getCantidad());
                articuloInsumoRepository.save(insumo);
            } else if ("MANUFACTURADO".equalsIgnoreCase(item.getTipoArticulo())) {
                articulo = articuloManufacturadoRepository.findById(item.getArticuloId())
                        .orElseThrow(() -> new Exception("Manufacturado no encontrado"));
                ArticuloManufacturado manufacturado = (ArticuloManufacturado) articulo;
                for (ArticuloManufacturadoDetalle det : manufacturado.getDetalles()) {
                    ArticuloInsumo insumo = det.getArticuloInsumo();
                    int cantidadTotal = det.getCantidad() * item.getCantidad();
                    int disponible = insumo.getStockActual() - insumo.getStockPendiente();
                    if (disponible < cantidadTotal) {
                        throw new Exception("Stock insuficiente para " + insumo.getDenominacion());
                    }
                    insumo.setStockPendiente(insumo.getStockPendiente() + cantidadTotal);
                    articuloInsumoRepository.save(insumo);
                }
            } else {
                throw new Exception("Tipo de artículo no válido");
            }

            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setArticulo(articulo);
            detalle.setCantidad(item.getCantidad());
            detalle.setSubTotal(item.getSubTotal());
            detalles.add(detalle);
        }
        detallePedidoRepository.saveAll(detalles);

        pedido.setDetalles(detalles);
        pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido crearPedido(PedidoRequestDTO pedidoRequest) throws Exception {
        Pedido pedido = new Pedido();
        // podés setearle datos si necesitás (fecha, estado, cliente, etc.)

        // 💾 Guardar el pedido primero para obtener el ID
        pedido = pedidoRepository.save(pedido);

        List<DetallePedido> detalles = new ArrayList<>();
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
            detalle.setPedido(pedido); // ahora sí, pedido ya tiene ID
            detalle.setArticulo(articulo);
            detalle.setCantidad(item.getCantidad());
            detalle.setSubTotal(item.getSubTotal());

            detalles.add(detalle);

            pedidoWebSocketController.notificarCliente(
                    pedido.getCliente().getId(),
                    pedidoRequest
            );
        }

        // Relación bidireccional
        pedido.setDetalles(detalles);

        // 💾 Ahora sí, guardar los detalles (gracias al cascade ALL, incluso podrías omitir esto)
        detallePedidoRepository.saveAll(detalles);

        return pedido;
    }

    private Integer generarNumeroPedido() {
        Integer maxNumero = pedidoRepository.findMaxNumeroPedido();
        return (maxNumero == null) ? 1 : maxNumero + 1;
    }

    private Double calcularTotalCosto(List<DetallePedidoRequestDTO> items) {
        double suma = 0;
        for (DetallePedidoRequestDTO item : items) {
            suma += item.getSubTotal();
        }
        return suma;
    }

    public void eliminarPedido(Long idPedido) throws Exception {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new Exception("Pedido not found with ID: " + idPedido));
        pedidoRepository.delete(pedido);
    }

    @Transactional
    public void confirmarPedido(Long idPedido, ConfirmarPedidoRequestDTO request) throws Exception {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new Exception("Pedido no encontrado"));

        pedido.setEstado(Estado.PENDIENTE);

        // Decrement stock for all insumos in the pedido
        stockService.descontarStockIngredientes(pedido);

        int minutos = tiempoEstimadoService.calcularTiempoEstimado(pedido);
        LocalTime horaEstimada = LocalTime.now().plusMinutes(minutos);
        pedido.setHoraEstimadaFinalizacion(horaEstimada);

        pedidoRepository.save(pedido);
    }


    @Transactional
    public Map<String, Object> guardarPedidoConPago(Pedido pedido) throws Exception {
        // Establecer fecha del pedido
        pedido.setFechaPedido(LocalDate.now());

        // Asociar detalles al pedido
        if (pedido.getDetalles() != null) {
            for (DetallePedido detalle : pedido.getDetalles()) {
                detalle.setPedido(pedido);

                Articulo articulo;
                if (detalle.getArticulo() instanceof ArticuloInsumo) {
                    articulo = articuloInsumoRepository.findById(detalle.getArticulo().getId())
                            .orElseThrow(() -> new Exception("Insumo no encontrado"));
                } else if (detalle.getArticulo() instanceof ArticuloManufacturado) {
                    articulo = articuloManufacturadoRepository.findById(detalle.getArticulo().getId())
                            .orElseThrow(() -> new Exception("Manufacturado no encontrado"));
                } else {
                    throw new Exception("Tipo de artículo no válido");
                }
                detalle.setArticulo(articulo);
            }
        }

        // 👉 Usa el total que viene del frontend (no lo recalcules)
        // Si por seguridad querés validar, podés comparar y lanzar error si hay mucha diferencia

        // Guardar pedido en la base de datos
        Pedido guardado = pedidoRepository.save(pedido);

        // Crear preferencia de pago en Mercado Pago usando el total del pedido
        List<ItemDTO> items = new ArrayList<>();
        items.add(new ItemDTO(
                "Pedido #" + guardado.getId(),
                1,
                BigDecimal.valueOf(guardado.getTotal()) // Usa el total enviado
        ));

        try {
            String initPoint = mercadoPagoService.procesarPago(items);
            return Map.of("id", guardado.getId(), "initPoint", initPoint);
        } catch (MPException | MPApiException e) {
            throw new RuntimeException("Error al procesar el pago con Mercado Pago: " + e.getMessage());
        }
    }
}