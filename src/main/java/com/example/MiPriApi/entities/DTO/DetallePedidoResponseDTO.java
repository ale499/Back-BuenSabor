package com.example.MiPriApi.entities.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetallePedidoResponseDTO {
    private String nombreArticulo;
    private Double precioArticulo;
    private Integer cantidad;
    private Double subTotal;
}

