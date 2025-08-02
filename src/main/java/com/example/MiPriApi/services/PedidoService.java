package com.example.MiPriApi.services;

import com.example.MiPriApi.controllers.PedidoWebSocketController;
import com.example.MiPriApi.entities.DTO.*;
import com.example.MiPriApi.entities.*;
import com.example.MiPriApi.entities.enums.Estado;
import com.example.MiPriApi.entities.enums.FormaPago;
import com.example.MiPriApi.entities.enums.TipoEnvio;
import com.example.MiPriApi.repositories.*;
import com.example.MiPriApi.services.Mappers.PedidoResponseMapper;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
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
    public Pedido crearPedidoDesdeCarrito(PedidoRequestDTO pedidoRequest) throws Exception {
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

        //Esto es para el set detalles (detalles de los pedidos)
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

        pedido.setDetalles(detalles);
        pedidoRepository.save(pedido);

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


        detallePedidoRepository.saveAll(detalles);

        pedido.setDetalles(detalles);
        pedidoRepository.save(pedido);

        // Notificar al cliente por WebSocket
        PedidoResponseDTO dto = PedidoResponseMapper.toDTO(pedido);
        pedidoWebSocketController.notificarCliente(pedido.getCliente().getId(), dto);

        return pedido;
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


        }
        PedidoResponseDTO dto = PedidoResponseMapper.toDTO(pedido);
        pedidoWebSocketController.notificarCliente(pedido.getCliente().getId(), dto);

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

    public void cancelarPedido(Long idPedido) throws Exception {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new Exception("Pedido not found with ID: " + idPedido));
        stockService.revertirStockPendiente(pedido);
        pedido.setEstado(Estado.CANCELADO);
        pedidoRepository.save(pedido);

        // Notificar al cliente por WebSocket
        PedidoResponseDTO dto = PedidoResponseMapper.toDTO(pedido);
        pedidoWebSocketController.notificarCliente(pedido.getCliente().getId(), dto);

    }

    public void eliminarPedido(Long idPedido) throws Exception {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new Exception("Pedido not found with ID: " + idPedido));
        stockService.revertirStockPendiente(pedido);
        pedidoRepository.delete(pedido);

        // Notificar al cliente por WebSocket (puedes enviar solo el ID y estado ELIMINADO)
        PedidoResponseDTO pedidoDTO = PedidoResponseMapper.toDTO(pedido);
        pedidoDTO.setEstado("ELIMINADO"); // si querés marcarlo eliminado explícitamente
        pedidoWebSocketController.notificarCliente(pedido.getCliente().getId(), pedidoDTO);

    }

    @Transactional
    @SendTo("/topic/pedidos")
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
        PedidoResponseDTO dto = PedidoResponseMapper.toDTO(pedido);
        pedidoWebSocketController.notificarCliente(pedido.getCliente().getId(), dto);
    }

    @Transactional
    public void agregarMinutosAHoraEstimada(Long idPedido, int minutos) throws Exception {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new Exception("Pedido no encontrado"));
        if (pedido.getHoraEstimadaFinalizacion() == null) {
            throw new Exception("La hora estimada de finalización no está definida");
        }
        pedido.setHoraEstimadaFinalizacion(pedido.getHoraEstimadaFinalizacion().plusMinutes(minutos));
        pedidoRepository.save(pedido);

        PedidoResponseDTO dto = PedidoResponseMapper.toDTO(pedido);
        pedidoWebSocketController.notificarCliente(pedido.getCliente().getId(), dto);
    }


    @Transactional
    public Map<String, Object> guardarPedidoConPago(Pedido pedido) throws Exception {
        // Establecer fecha del pedido
        pedido.setFechaPedido(LocalDate.now());

        // NUEVO: Verificar y guardar cliente si es necesario
        if (pedido.getCliente() != null && pedido.getCliente().getEmail() != null) {
            String email = pedido.getCliente().getEmail();
            Cliente clienteExistente = clienteRepository.findByEmail(email)
                    .orElse(null);

            if (clienteExistente == null) {
                // Si no existe, guardarlo primero
                clienteExistente = clienteRepository.save(pedido.getCliente());
                System.out.println("Cliente nuevo creado con ID: " + clienteExistente.getId());
            } else {
                System.out.println("Cliente existente encontrado con ID: " + clienteExistente.getId());
            }

            // Asignar el cliente existente o recién guardado al pedido
            pedido.setCliente(clienteExistente);
        } else if (pedido.getCliente() != null && pedido.getCliente().getId() != null) {
            // Si viene con ID, verificar que exista
            Cliente clienteExistente = clienteRepository.findById(pedido.getCliente().getId())
                    .orElseThrow(() -> new Exception("Cliente no encontrado con ID: " + pedido.getCliente().getId()));
            pedido.setCliente(clienteExistente);
        } else {
            throw new Exception("Se requiere un cliente con email o ID para crear un pedido");
        }

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
    /**
     * Cambia el estado de un pedido.
     *
     * @param idPedido    ID del pedido a modificar.
     * @param nuevoEstado Nuevo estado del pedido.
     * @throws Exception Si el pedido no existe o hay un error al guardarlo.
     */
    @Transactional
    @SendTo("/topic/pedidos")
    public void cambiarEstado(Long idPedido, Estado nuevoEstado) throws Exception {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new Exception("Pedido no encontrado"));
        pedido.setEstado(nuevoEstado);
        pedidoRepository.save(pedido);

        PedidoResponseDTO dto = PedidoResponseMapper.toDTO(pedido);
        pedidoWebSocketController.notificarCliente(pedido.getCliente().getId(), dto);


        System.out.println("Notificación WebSocket enviada al cliente ID: " + pedido.getCliente().getId());
    }

    // Metodo para convertir Pedido a PedidoRequestDTO
    private PedidoRequestDTO convertirPedidoADTO(Pedido pedido) {
        PedidoRequestDTO dto = new PedidoRequestDTO();
        dto.setId(pedido.getId());
        dto.setClienteId(pedido.getCliente().getId());
        dto.setEstado(pedido.getEstado().toString());
        dto.setTotal(pedido.getTotal());
        dto.setTotalCosto(pedido.getTotalCosto());

        // Convertir detalles si es necesario
        List<DetallePedidoRequestDTO> itemsDTO = new ArrayList<>();
        for (DetallePedido detalle : pedido.getDetalles()) {
            DetallePedidoRequestDTO itemDTO = new DetallePedidoRequestDTO();
            itemDTO.setArticuloId(detalle.getArticulo().getId());
            itemDTO.setCantidad(detalle.getCantidad());
            itemDTO.setSubTotal(detalle.getSubTotal());

            // Determinar tipo de artículo
            if (detalle.getArticulo() instanceof ArticuloInsumo) {
                itemDTO.setTipoArticulo("INSUMO");
            } else if (detalle.getArticulo() instanceof ArticuloManufacturado) {
                itemDTO.setTipoArticulo("MANUFACTURADO");
            }

            itemsDTO.add(itemDTO);
        }
        dto.setItems(itemsDTO);

        return dto;
    }
}