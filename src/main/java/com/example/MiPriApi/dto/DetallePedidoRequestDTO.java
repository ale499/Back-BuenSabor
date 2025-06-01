package com.example.MiPriApi.dto;

import lombok.Data;

@Data
public class DetallePedidoRequestDTO {
    private Long articuloId;
    private Integer cantidad;
    private Double subTotal;
    private String TipoArticulo; // Puede ser "articuloManufacturado" o "articuloSimple"
}