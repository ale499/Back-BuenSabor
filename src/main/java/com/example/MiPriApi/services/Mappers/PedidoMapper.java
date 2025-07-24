package com.example.MiPriApi.services.Mappers;


import com.example.MiPriApi.entities.DTO.*;
import com.example.MiPriApi.entities.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    public static DetallePedidoResponseDTO toDetalleDTO(DetallePedido detalle) {
        DetallePedidoResponseDTO dto = new DetallePedidoResponseDTO();
        dto.setNombreArticulo(detalle.getArticulo().getDenominacion());
        dto.setPrecioArticulo(detalle.getArticulo().getPrecioVenta());
        dto.setCantidad(detalle.getCantidad());
        dto.setSubTotal(detalle.getSubTotal());
        return dto;
    }

    public static List<DetallePedidoResponseDTO> toDetalleDTOList(Pedido pedido) {
        return pedido.getDetalles().stream()
                .map(PedidoMapper::toDetalleDTO)
                .collect(Collectors.toList());
    }


}
