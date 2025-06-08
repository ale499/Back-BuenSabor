package com.example.MiPriApi.entities.DTO;


import lombok.Data;

@Data
public class ConfirmarPedidoRequestDTO {

    private Long pedidoId;
    private String tipoEnvio; // "LOCAL" o "DOMICILIO"
    private String direccion; // solo si es domicilio
    private String telefono;  // solo si es domicilio
    private String formaPago; // "EFECTIVO" o "MERCADO_PAGO"

}
