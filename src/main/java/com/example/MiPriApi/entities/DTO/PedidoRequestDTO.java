package com.example.MiPriApi.entities.DTO;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class PedidoRequestDTO {
    private long id;
    private String clienteAuth0Id;
    private Double total;
    private Integer numeroPedido;
    private Long empleadoId;
    private Long domicilioId;
    private Long sucursalId;
    private String estado;       // Si necesitas el estado como texto
    private String formaPago;    // Ej: "EFECTIVO", "TARJETA"
    private String tipoEnvio;    // Ej: "DELIVERY", "RETIRO"
    private Double totalCosto;
    private LocalDate fechaPedido;
    private List<DetallePedidoRequestDTO> items;
    private String notaAdicional;
}