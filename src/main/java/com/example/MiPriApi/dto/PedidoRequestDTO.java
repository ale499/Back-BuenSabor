package com.example.MiPriApi.dto;

import lombok.Data;
import java.util.List;

@Data
public class PedidoRequestDTO {
    private Long clienteId;
    private Double total;
    private Integer numeroPedido;
    private Long empleadoId;
    private Long domicilioId;
    private Long sucursalId;
    private List<DetallePedidoRequestDTO> items;
}