package com.example.MiPriApi.entities.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class PedidoResponseDTO {
    private Long id;
    private Integer numeroPedido;
    private Long clienteId;
    private Long empleadoId;
    private Long sucursalId;
    private String estado;
    private Double total;
    private Double totalCosto;
    private LocalDate fechaPedido;
    private LocalTime horaEstimadaFinalizacion;
    private Integer tiempoEstimadoMinutos;
    private List<DetallePedidoRequestDTO> items;
}
