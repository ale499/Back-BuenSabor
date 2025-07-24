package com.example.MiPriApi.entities.DTO;

import lombok.Data;

@Data
public class DetallePedidoRequestDTO {
    private Long articuloId;
    private String nombreArticulo;
    private Double precioArticulo;
    private Integer cantidad;
    private Double subTotal;
    private String tipoArticulo; // Puede ser "articuloManufacturado" o "articuloSimple"
}